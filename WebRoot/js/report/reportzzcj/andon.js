/**
 * 工段停线报表
 */
(function() {
    var msgResource = {};
    var totolStopTime;
    var weekDefault;
    var columwidth=50;
    var chart_url = '/ext-3.3.0/resources/charts.swf';
  //分类查询信息
    var bancidata = [["5","安灯"],["6","急停"],["4","QCOS"]];
    var banciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:bancidata,
     fields:["VALUE","TEXT"]
    });
  //新增班次信息
    var newbancidata = [["","全部"],["1","1班"],["2","2班"]];
    var newbanciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:newbancidata,
     fields:["VALUE","TEXT"]
    });
    //周
    var weekStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'WEEK'
        },
        fields : ['VALUE','TEXT']
    });
    weekDefault = new Date().getWeekOfYear()-1;
    for(var i = 1 ; i <= 52; i++){
       weekStore.add(new Ext.data.Record({
            VALUE : i-1,
            TEXT : '第' + i + '周'
       })) 
    }
    

    
    //工段日停线报表
    var equStopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
        	items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'PPDATE',
                format : ht.config.format.DATE,
                value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
            },
            {
                xtype : 'combo',
                fieldLabel : '选择停线分类',
                store : banciStore,
                emptyText : '默认查andon',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'BANCI',
                name : 'BANCI',
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            },{
                xtype : 'combo',
                fieldLabel : '班次',
                store : newbanciStore,
                emptyText : '全部班次',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'NEWBANCI',
                name : 'NEWBANCI',
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            }],
            enableQueryButton : true,
            action : 'ZZCJ_QUERY_ANDON_STOPLINE_ACTION',
            outputTable : 'PMC_ANDON_STOPLINE'
        },
        gridConfig : {
        	defaultSortable : false,
            columns : [new Ext.grid.RowNumberer(),{
                header : '工位',
                dataIndex : 'GW1',
                width : columwidth
            },{
                header : '停线时间',
                dataIndex : 'TIME1',
                width : columwidth*1.5,
                renderer : function(v){
                	return Number(v)>0?Number(v).toFixed(2):"";
                }
            }, {
                header : '工位',
                dataIndex : 'GW2',
                width : columwidth
            },{
                header : '停线时间',
                dataIndex : 'TIME2',
                width : columwidth*1.5,
                renderer : function(v){
                	return Number(v)>0?Number(v).toFixed(2):"";
                }
            }, {
                header : '工位',
                dataIndex : 'GW3',
                width : columwidth
            }, {
                header : '停线时间',
                dataIndex : 'TIME3',
                width : columwidth*1.5,
                renderer : function(v){
                	return Number(v)>0?Number(v).toFixed(2):"";
                }
             }, {
                 header : '工位',
                 dataIndex : 'GW4',
                 width : columwidth
             }, {
                 header : '停线时间',
                 dataIndex : 'TIME4',
                 width : columwidth*1.5,
                 renderer : function(v){
                 	return Number(v)>0?Number(v).toFixed(2):"";
                 }
             }, {
                 header : '工位',
                 dataIndex : 'GW5',
                 width : columwidth
             }, {
                 header : '停线时间',
                 dataIndex : 'TIME5',
                 width : columwidth*1.5,
                 renderer : function(v){
                 	return Number(v)>0?Number(v).toFixed(2):"";
                 }
             }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['GW1','GW2','GW3','GW4','GW5','TIME1','TIME2','TIME3','TIME4','TIME5'],
            deletedKeyFields : ['ID'],
            ctTopbarComponts : [{text:'A',width:columwidth*0.4},
                                {text:'内饰1',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'内饰2',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'底盘1',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'底盘2',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'最终线',enableByHasData : true,width:columwidth*2.3+2},
               { 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    exportFn();
                } 
            }]
        },
        ctListeners : {
            beforeQuery :function(values){
                values['QUERY_TYPE'] = '0';
                return true;
            }
           
        }
    });
    
      
     //导出excel
    var exportFn = function(){
        
        var url = 'json?action=ZZCJ_EXPORT_ANDON_STOPLINE_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = equStopLinePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&PPDATE=' + equStopLinePanel.ht_outputStore.baseParams['PPDATE'];
        //分类信息
        url += '&BANCI=' + equStopLinePanel.ht_outputStore.baseParams['BANCI'];
      //班次信息
        url += '&NEWBANCI=' + equStopLinePanel.ht_outputStore.baseParams['NEWBANCI'];
      //类型
        url += '&QUERY_TYPE=' + '0';
        
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        exportPanel.setSrc(url);
    }
    
    //导出面板
    var exportPanel = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
   
    var weekEquStopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype : 'combo',
                fieldLabel : '日期',
                store : weekStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PPDATE',
                name : 'PPDATE',
                value : weekDefault,
                editable : false
            },
            {
                xtype : 'combo',
                fieldLabel : '选择停线分类',
                store : banciStore,
                emptyText : '默认查andon',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'BANCI',
                name : 'BANCI',
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            },{
                xtype : 'combo',
                fieldLabel : '班次',
                store : newbanciStore,
                emptyText : '全部班次',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'NEWBANCI',
                name : 'NEWBANCI',
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            }],
            action : 'ZZCJ_QUERY_ANDON_STOPLINE_ACTION',
            outputTable : 'PMC_ANDON_STOPLINE'
        },
        gridConfig : {
        	defaultSortable : false,
            columns : [new Ext.grid.RowNumberer(),{
                header : '工位',
                dataIndex : 'GW1',
                width : columwidth
            },{
                header : '停线时间',
                dataIndex : 'TIME1',
                width : columwidth*1.5,
                renderer : function(v){
                	return Number(v)>0?Number(v).toFixed(2):"";
                }
            }, {
                header : '工位',
                dataIndex : 'GW2',
                width : columwidth
            },{
                header : '停线时间',
                dataIndex : 'TIME2',
                width : columwidth*1.5,
                renderer : function(v){
                	return Number(v)>0?Number(v).toFixed(2):"";
                }
            }, {
                header : '工位',
                dataIndex : 'GW3',
                width : columwidth
            }, {
                header : '停线时间',
                dataIndex : 'TIME3',
                width : columwidth*1.5,
                renderer : function(v){
                	return Number(v)>0?Number(v).toFixed(2):"";
                }
             }, {
                 header : '工位',
                 dataIndex : 'GW4',
                 width : columwidth
             }, {
                 header : '停线时间',
                 dataIndex : 'TIME4',
                 width : columwidth*1.5,
                 renderer : function(v){
                 	return Number(v)>0?Number(v).toFixed(2):"";
                 }
             }, {
                 header : '工位',
                 dataIndex : 'GW5',
                 width : columwidth
             }, {
                 header : '停线时间',
                 dataIndex : 'TIME5',
                 width : columwidth*1.5,
                 renderer : function(v){
                 	return Number(v)>0?Number(v).toFixed(2):"";
                 }
             }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['GW1','GW2','GW3','GW4','GW5','TIME1','TIME2','TIME3','TIME4','TIME5'],
            deletedKeyFields : ['ID'],
            ctTopbarComponts : [{text:'A',width:columwidth*0.4},
                                {text:'内饰1',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'内饰2',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'底盘1',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'底盘2',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'最终线',enableByHasData : true,width:columwidth*2.3+2},
               { 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    weekexportFn();
                } 
            }]
        },
        ctListeners : {
           beforeQuery :function(values){
                values['QUERY_TYPE'] = '3';
                return true;
           }
        }
    });
    
      
     //导出excel
    var weekexportFn = function(){
        
        var url = 'json?action=ZZCJ_EXPORT_ANDON_STOPLINE_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = weekEquStopLinePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&PPDATE=' + weekEquStopLinePanel.ht_outputStore.baseParams['PPDATE'];
      //班次信息
        url += '&BANCI=' + weekEquStopLinePanel.ht_outputStore.baseParams['BANCI'];
      //分类信息
        url += '&NEWBANCI=' + weekEquStopLinePanel.ht_outputStore.baseParams['NEWBANCI'];
      //类型
        url += '&QUERY_TYPE=' + '3';
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        weekexportPanel.setSrc(url);
    }
    
    //导出面板
    var weekexportPanel = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
    var monthEquStopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'PPDATE',
                format : 'Y-m',
                value : new Date()
            },
            {
                xtype : 'combo',
                fieldLabel : '选择停线分类',
                store : banciStore,
                emptyText : '默认查andon',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'BANCI',
                name : 'BANCI',
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            },{
                xtype : 'combo',
                fieldLabel : '班次',
                store : newbanciStore,
                emptyText : '全部班次',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'NEWBANCI',
                name : 'NEWBANCI',
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            }],
            action : 'ZZCJ_QUERY_ANDON_STOPLINE_ACTION',
            outputTable : 'PMC_ANDON_STOPLINE'
        },
        gridConfig : {
        	defaultSortable : false,
            columns : [new Ext.grid.RowNumberer(),{
                header : '工位',
                dataIndex : 'GW1',
                width : columwidth
            },{
                header : '停线时间',
                dataIndex : 'TIME1',
                width : columwidth*1.5,
                renderer : function(v){
                	return Number(v)>0?Number(v).toFixed(2):"";
                }
            }, {
                header : '工位',
                dataIndex : 'GW2',
                width : columwidth
            },{
                header : '停线时间',
                dataIndex : 'TIME2',
                width : columwidth*1.5,
                renderer : function(v){
                	return Number(v)>0?Number(v).toFixed(2):"";
                }
            }, {
                header : '工位',
                dataIndex : 'GW3',
                width : columwidth
            }, {
                header : '停线时间',
                dataIndex : 'TIME3',
                width : columwidth*1.5,
                renderer : function(v){
                	return Number(v)>0?Number(v).toFixed(2):"";
                }
             }, {
                 header : '工位',
                 dataIndex : 'GW4',
                 width : columwidth
             }, {
                 header : '停线时间',
                 dataIndex : 'TIME4',
                 width : columwidth*1.5,
                 renderer : function(v){
                 	return Number(v)>0?Number(v).toFixed(2):"";
                 }
             }, {
                 header : '工位',
                 dataIndex : 'GW5',
                 width : columwidth
             }, {
                 header : '停线时间',
                 dataIndex : 'TIME5',
                 width : columwidth*1.5,
                 renderer : function(v){
                 	return Number(v)>0?Number(v).toFixed(2):"";
                 }
             }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['GW1','GW2','GW3','GW4','GW5','TIME1','TIME2','TIME3','TIME4','TIME5'],
            deletedKeyFields : ['ID'],
            ctTopbarComponts : [{text:'A',width:columwidth*0.4},
                                {text:'内饰1',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'内饰2',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'底盘1',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'底盘2',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'最终线',enableByHasData : true,width:columwidth*2.3+2},{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    monthexportFn();
                } 
            }]
        },
        ctListeners : {
           beforeQuery :function(values){
                values['QUERY_TYPE'] = '1';
                return true;
           }
        }
    });
    
      
     //导出excel
    var monthexportFn = function(){
        
        var url = 'json?action=ZZCJ_EXPORT_ANDON_STOPLINE_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = monthEquStopLinePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&PPDATE=' + monthEquStopLinePanel.ht_outputStore.baseParams['PPDATE'];
      //分类信息
        url += '&BANCI=' + monthEquStopLinePanel.ht_outputStore.baseParams['BANCI'];
      //班次信息
        url += '&NEWBANCI=' + monthEquStopLinePanel.ht_outputStore.baseParams['NEWBANCI'];
      //类型
        url += '&QUERY_TYPE=' + '1';
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        monthexportPanel.setSrc(url);
    }
    
    //导出面板
    var monthexportPanel = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
    var yearEquStopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'PPDATE',
                format : 'Y',
                value : new Date()
            },
            {
                xtype : 'combo',
                fieldLabel : '选择停线分类',
                store : banciStore,
                emptyText : '默认查andon',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'BANCI',
                name : 'BANCI',
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            },{
                xtype : 'combo',
                fieldLabel : '班次',
                store : newbanciStore,
                emptyText : '全部班次',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'NEWBANCI',
                name : 'NEWBANCI',
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            }],
            action : 'ZZCJ_QUERY_ANDON_STOPLINE_ACTION',
            outputTable : 'PMC_ANDON_STOPLINE'
        },
        gridConfig : {
        	defaultSortable : false,
            columns : [new Ext.grid.RowNumberer(),{
                header : '工位',
                dataIndex : 'GW1',
                width : columwidth
            },{
                header : '停线时间',
                dataIndex : 'TIME1',
                width : columwidth*1.5,
                renderer : function(v){
                	return Number(v)>0?Number(v).toFixed(2):"";
                }
            }, {
                header : '工位',
                dataIndex : 'GW2',
                width : columwidth
            },{
                header : '停线时间',
                dataIndex : 'TIME2',
                width : columwidth*1.5,
                renderer : function(v){
                	return Number(v)>0?Number(v).toFixed(2):"";
                }
            }, {
                header : '工位',
                dataIndex : 'GW3',
                width : columwidth
            }, {
                header : '停线时间',
                dataIndex : 'TIME3',
                width : columwidth*1.5,
                renderer : function(v){
                	return Number(v)>0?Number(v).toFixed(2):"";
                }
             }, {
                 header : '工位',
                 dataIndex : 'GW4',
                 width : columwidth
             }, {
                 header : '停线时间',
                 dataIndex : 'TIME4',
                 width : columwidth*1.5,
                 renderer : function(v){
                 	return Number(v)>0?Number(v).toFixed(2):"";
                 }
             }, {
                 header : '工位',
                 dataIndex : 'GW5',
                 width : columwidth
             }, {
                 header : '停线时间',
                 dataIndex : 'TIME5',
                 width : columwidth*1.5,
                 renderer : function(v){
                 	return Number(v)>0?Number(v).toFixed(2):"";
                 }
             }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['GW1','GW2','GW3','GW4','GW5','TIME1','TIME2','TIME3','TIME4','TIME5'],
            deletedKeyFields : ['ID'],
            ctTopbarComponts : [{text:'A',width:columwidth*0.4},
                                {text:'内饰1',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'内饰2',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'底盘1',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'底盘2',enableByHasData : true,width:columwidth*2.3+2},
                                {text:'最终线',enableByHasData : true,width:columwidth*2.3+2},{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    yearexportFn();
                } 
            }]
        },
        ctListeners : {
           beforeQuery :function(values){
                values['QUERY_TYPE'] = '2';
                return true;
            }
        }
    });
  
    
    //导出excel
    var yearexportFn = function(){
        
        var url = 'json?action=ZZCJ_EXPORT_ANDON_STOPLINE_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = yearEquStopLinePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&PPDATE=' + yearEquStopLinePanel.ht_outputStore.baseParams['PPDATE'];
      //分类信息
        url += '&BANCI=' + yearEquStopLinePanel.ht_outputStore.baseParams['BANCI'];
      //班次信息
        url += '&NEWBANCI=' + yearEquStopLinePanel.ht_outputStore.baseParams['NEWBANCI'];
      //类型
        url += '&QUERY_TYPE=' + '2';
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        yearexportPanel.setSrc(url);
    }
    
    //导出面板
    var yearexportPanel = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
    var backPanel = new Ext.TabPanel({
        activeTab: 0,
        border : false,
        items : [{
            title : '日报表',
            border : false,
            layout : 'border',
            items : [{
                region : 'center',
                layout : 'border',
                items : [equStopLinePanel,exportPanel]
            }]
        }, {
            title : '周报表',
            border : false,
            layout : 'border',
            items : [weekEquStopLinePanel,weekexportPanel]
        }
        ,{
            title : '月报表',
            border : false,
            layout : 'border',
            items : [monthEquStopLinePanel,monthexportPanel]
        }
        ,{
            title : '年报表',
            border : false,
            layout : 'border',
            items : [yearEquStopLinePanel,yearexportPanel]}
        ],
        listeners : {
            beforedestroy : function(){
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
