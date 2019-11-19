/**
 * 工段停线报表
 */
(function() {
    var msgResource = {};
    var totolStopTime;
    var weekDefault;
    var chart_url = '/ext-3.3.0/resources/charts.swf';
  //新增班次信息
    var bancidata = [["","全部"],["1","1班"],["2","2班"]];
    var banciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:bancidata,
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
    
    //计划日期面板
    var planDatePanel = new ht.base.PlainPanel({
        region : 'north',
        height : 63,
        queryFormConfig : {
            enableQueryButton : true, 
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'WORKDATE',
                format : ht.config.format.DATE,
                value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
            },{
                xtype : 'combo',
                fieldLabel : '班次',
                store : banciStore,
                emptyText : '全部班次',
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
            }],
            action : 'ZZCJ_QUERY_PMC_DATE_IMPORT_ACTION',
            outputTable : 'PMC_DATE_IMPORT'
        },
        gridConfig : {
            columns : [
            ],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['WORKDATE','STARTTIME', 'ENDTIME', 'RESTTIME','LASTTIME','SHIFT']
        },
        ctListeners : {
            beforeQuery : function(values){
                equStopLinePanel.ht_outputStore.baseParams['PPDATE'] = values['WORKDATE'];
                equStopLinePanel.ht_outputStore.baseParams['QUERY_TYPE'] = '0';
                equStopLinePanel.ht_outputStore.load();
                return true;
            }
        }
    
    });
    
    //工段停线报表
    var equStopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
        	items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'PPDATE',
                format : ht.config.format.DATE,
                value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
            },{
                xtype : 'combo',
                fieldLabel : '班次',
                store : banciStore,
                emptyText : '全部班次',
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
            }],
            enableQueryButton : true,
            action : 'ZZCJ_QUERY_PMC_EQUIPMENT_STOPLINE_ACTION',
            outputTable : 'PMC_EQUIPMENT_STOPLINE'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 120
            },{
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            }, {
                header : '班次',
                dataIndex : 'BANCI',
                width : 60,
                renderer : function(value){
                	debugger;
                	if(value==1){
                		return '一班';
                	}
                	if(value==2){
                		return '二班';
                	}
                	if(value==3){
                		return '三班';
                	}
                	return '全部';
                }
            },{
                header : '工段时间（秒）',
                dataIndex : 'CYCLETIME',
                width : 150,
                renderer : function(v){
                	if(v==null){
                		return 0;
                	}
                	return parseInt(v*10)/10;
                }
            }, {
                header : '停线总时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '停线总次数',
                dataIndex : 'STOPCOUNT',
                width : 120
             }, {
                 header : 'CON',
                 dataIndex : 'DATA3',
                 width : 70
             }, {
                 header : 'ANDON',
                 dataIndex : 'DATA4',
                 width : 70
             }, {
                 header : 'FUL',
                 dataIndex : 'DATA6',
                 width : 70
             }, {
                 header : 'SHO',
                 dataIndex : 'DATA7',
                 width : 80
             }, {
                 header : 'ES',
                 dataIndex : 'DATA8',
                 width : 80
             }, {
                 header : 'PS',
                 dataIndex : 'DATA9',
                 width : 80
             }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['ID','PPDATE','FACTORY','WORKSHOP','PRODUCTIONLINENAME','BANCI','CYCLETIME', 'FPRODUCTIONLINE','PRODUCTIONLINE','STOPCOUNT',
                            'STOPTIME','STOPCOUNT','DATA3','DATA4','DATA5','DATA6','DATA7','DATA8','DATA9','DATA10'],
            deletedKeyFields : ['ID'],
            ctTopbarComponts : [{ 
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
    
    equStopLinePanel.ht_outputStore.on('load', function() {
        
        totolStopTime = 0;
        var totolStopCount = 0;
        var totalPerson = 0;
        var totalS = 0;
        var totalWite = 0;
        var totalLine = 0;
        var totalZl = 0;
        var totalQT = 0;
        
        pieStore.removeAll();
        lineStore.removeAll();
        
        for (var i = 0; i < equStopLinePanel.ht_outputStore.getCount(); i++) {
            var stopLineRecord = equStopLinePanel.ht_outputStore.getAt(i);
            
            totolStopTime += stopLineRecord.get('STOPTIME');
            totolStopCount += stopLineRecord.get('STOPCOUNT');
            totalPerson += stopLineRecord.get('DATA3');
            totalS += stopLineRecord.get('DATA4');
            totalWite += stopLineRecord.get('DATA5');
            totalLine += stopLineRecord.get('DATA6');
            totalZl += stopLineRecord.get('DATA7');
            totalQT += stopLineRecord.get('DATA8');
            pieStore.add(stopLineRecord.copy());
        }
        /*if(equStopLinePanel.ht_outputStore.getCount() > 0 ){
	        equStopLinePanel.ht_outputStore.add(new Ext.data.Record({
	            'PRODUCTIONLINENAME' : '合计', 
	            'STOPTIME' : totolStopTime, 
	            'STOPCOUNT' : totolStopCount,
	            'DATA3' : totalPerson,
	            'DATA4' : totalS,
	            'DATA5' : totalWite,
	            'DATA6' : totalLine,
	            'DATA7' : totalZl
	        }));
        }*/
        lineStore.add(new Ext.data.Record({
            'LABEL' : 'MTBF', 
            'TOTAL' : ((22 * 60) / (totolStopCount || 1)).toFixed(2)
        }));
        lineStore.add(new Ext.data.Record({
            'LABEL' : 'MTTR', 
            'TOTAL' : (totolStopTime / (totolStopCount || 1)).toFixed(2)
        }));
        
    });
    
      
     //导出excel
    var exportFn = function(){
        
        var url = 'json?action=ZZCJ_EXPORT_PMC_EQUIPMENT_STOPLINE_ACTION';
                    
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
        //班次信息
        url += '&BANCI=' + equStopLinePanel.ht_outputStore.baseParams['BANCI'];
        
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
    
    //饼图
    var pieStore = new Ext.data.JsonStore({
        url : 'json',
        fields : ['STOPTIME','STOPCOUNT']
    });
    
    var pieWeekStore = new Ext.data.JsonStore({
        url : 'json',
        fields : ['STOPTIME','STOPCOUNT']
    });
    
    var pieMonthStore = new Ext.data.JsonStore({
        url : 'json',
        fields : ['STOPTIME','STOPCOUNT']
    });
    
    var pieYearStore = new Ext.data.JsonStore({
        url : 'json',
        fields : ['STOPTIME','STOPCOUNT']
    });
    
    var piePanel = new Ext.Panel({
        title : '停线总时间（分钟）',
        region : 'center',
        border : true,
        items: {
            store: pieStore,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            xtype: 'piechart',
            dataField: 'STOPTIME',
            categoryField: 'PRODUCTIONLINE',
            extraStyle : {
				legend : {
					display: 'right',
					padding : 5,
					font : {
						size : 12
					}
				}
			},
            tipRenderer : function(chart, record, i, series){
                return '工段：' + record.get(chart.categoryField) + '\n'  + 
                        '停线总时间: ' + record.get(chart.dataField) + '分钟\n'; +
                        '百分比：' + (100 * record.get(chart.dataField) / (totolStopTime || 1)).toFixed(2) + '%';
            }
        }
    });
    
    // 柱状图
    var lineStore = new Ext.data.JsonStore({
        url : 'json',
        fields : ['STOPTIME','STOPCOUNT']
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
            },{
                xtype : 'combo',
                fieldLabel : '班次',
                store : banciStore,
                emptyText : '全部班次',
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
            }],
            action : 'ZZCJ_QUERY_PMC_EQUIPMENT_STOPLINE_ACTION',
            outputTable : 'PMC_EQUIPMENT_STOPLINE'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),  {
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 120
            }, {
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            }, {
                header : '班次',
                dataIndex : 'BANCI',
                width : 60,
                renderer : function(value){
                	if(value==1){
                		return '一班';
                	}
                	if(value==2){
                		return '二班';
                	}
                	if(value==3){
                		return '三班';
                	}
                	return '全部';
                }
            },{
                header : '工段时间（秒）',
                dataIndex : 'CYCLETIME',
                width : 150,
                renderer : function(v){
                	if(v==null){
                		return 0;
                	}
                	return parseInt(v*10)/10;
                }
            },{
                header : '停线总时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '停线总次数',
                dataIndex : 'STOPCOUNT',
                width : 120
             }, {
                 header : 'CON',
                 dataIndex : 'DATA3',
                 width : 70
             }, {
                 header : 'ANDON',
                 dataIndex : 'DATA4',
                 width : 70
             },{
                 header : 'FUL',
                 dataIndex : 'DATA6',
                 width : 70
             }, {
                 header : 'SHO',
                 dataIndex : 'DATA7',
                 width : 80
             }, {
                 header : 'ES',
                 dataIndex : 'DATA8',
                 width : 80
             }, {
                 header : 'PS',
                 dataIndex : 'DATA9',
                 width : 80
             }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['ID','PPDATE','FACTORY','WORKSHOP', 'PRODUCTIONLINENAME','BANCI','CYCLETIME','FPRODUCTIONLINE','PRODUCTIONLINE','STOPCOUNT',
                            'STOPTIME','STOPCOUNT','DATA3','DATA3','DATA4','DATA5','DATA6','DATA7','DATA8','DATA9','DATA10'],
            deletedKeyFields : ['ID'],
            ctTopbarComponts : [{ 
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

    weekEquStopLinePanel.ht_outputStore.on('load', function() {
    	totolStopTime = 0;
    	var totolStopCount = 0;
        pieWeekStore.removeAll();
        
        for (var i = 0; i < weekEquStopLinePanel.ht_outputStore.getCount(); i++) {
            var stopLineRecord = weekEquStopLinePanel.ht_outputStore.getAt(i);
            
            totolStopTime += stopLineRecord.get('STOPTIME');
            totolStopCount += stopLineRecord.get('STOPCOUNT');
            
            pieWeekStore.add(stopLineRecord.copy());
        }
    });
    
      
     //导出excel
    var weekexportFn = function(){
        
        var url = 'json?action=ZZCJ_EXPORT_PMC_EQUIPMENT_STOPLINE_WEEK_ACTION';
                    
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
    
    var weekPiePanel = new Ext.Panel({
        title : '停线总时间（分钟）',
        region : 'south',
        height : 220,
        border : true,
        items: {
            store: pieWeekStore,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            xtype: 'piechart',
            dataField: 'STOPTIME',
            categoryField: 'PRODUCTIONLINE',
            extraStyle : {
                legend : {
                    display: 'right',
                    padding : 5,
                    font : {
                        size : 12
                    }
                }
            },
            tipRenderer : function(chart, record, i, series){
                return '工位：' + record.get(chart.categoryField) + '\n'  + 
                        '停线总时间: ' + record.get(chart.dataField) + '分钟\n' +
                        '百分比：' + (100 * record.get(chart.dataField) / (totolStopTime || 1)).toFixed(2) + '%';
            }
        }
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
            },{
                xtype : 'combo',
                fieldLabel : '班次',
                store : banciStore,
                emptyText : '全部班次',
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
            }],
            action : 'ZZCJ_QUERY_PMC_EQUIPMENT_STOPLINE_ACTION',
            outputTable : 'PMC_EQUIPMENT_STOPLINE'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 120
            },{
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            }, {
                header : '班次',
                dataIndex : 'BANCI',
                width : 60,
                renderer : function(value){
                	if(value==1){
                		return '一班';
                	}
                	if(value==2){
                		return '二班';
                	}
                	if(value==3){
                		return '三班';
                	}
                	return '全部';
                }
            },{
                header : '工段时间（秒）',
                dataIndex : 'CYCLETIME',
                width : 150,
                renderer : function(v){
                	if(v==null){
                		return 0;
                	}
                	return parseInt(v*10)/10;
                }
            }, {
                header : '停线总时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '停线总次数',
                dataIndex : 'STOPCOUNT',
                width : 120
             }, {
                 header : 'CON',
                 dataIndex : 'DATA3',
                 width : 70
             }, {
                 header : 'ANDON',
                 dataIndex : 'DATA4',
                 width : 70
             }, {
                 header : 'FUL',
                 dataIndex : 'DATA6',
                 width : 70
             }, {
                 header : 'SHO',
                 dataIndex : 'DATA7',
                 width : 80
             }, {
                 header : 'ES',
                 dataIndex : 'DATA8',
                 width : 80
             }, {
                 header : 'PS',
                 dataIndex : 'DATA9',
                 width : 80
             }],
            
            isPageAction : false,
            isMultipleSelect : false,

            storeFields : ['ID','PPDATE','FACTORY','WORKSHOP','BANCI','CYCLETIME', 'FPRODUCTIONLINE','PRODUCTIONLINENAME','PRODUCTIONLINE','STOPCOUNT',
                            'STOPTIME','STOPCOUNT','DATA3','DATA3','DATA4','DATA5','DATA6','DATA7','DATA8','DATA9','DATA10'],
            deletedKeyFields : ['ID'],
            ctTopbarComponts : [{ 
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
   
    var monthPiePanel = new Ext.Panel({
        title : '停线总时间（分钟）',
        region : 'south',
        height : 220,
        border : true,
        items: {
            store: pieMonthStore,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            xtype: 'piechart',
            dataField: 'STOPTIME',
            categoryField: 'PRODUCTIONLINE',
            extraStyle : {
                legend : {
                    display: 'right',
                    padding : 5,
                    font : {
                        size : 12
                    }
                }
            },
            tipRenderer : function(chart, record, i, series){
                return '工位：' + record.get(chart.categoryField) + '\n'  + 
                        '停线总时间: ' + record.get(chart.dataField) + '分钟\n' +
                        '百分比：' + (100 * record.get(chart.dataField) / (totolStopTime || 1)).toFixed(2) + '%';
            }
        }
    });
   
    monthEquStopLinePanel.ht_outputStore.on('load', function() {
    	totolStopTime = 0;
    	var totolStopCount = 0;
    	
        pieMonthStore.removeAll();
        for (var i = 0; i < monthEquStopLinePanel.ht_outputStore.getCount(); i++) {
            var stopLineRecord = monthEquStopLinePanel.ht_outputStore.getAt(i);

            totolStopTime += stopLineRecord.get('STOPTIME');
            totolStopCount += stopLineRecord.get('STOPCOUNT');
            
            pieMonthStore.add(stopLineRecord.copy());
        }
    })
    
      
     //导出excel
    var monthexportFn = function(){
        
        var url = 'json?action=ZZCJ_EXPORT_PMC_EQUIPMENT_STOPLINE_MONTH_ACTION';
                    
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
      //班次信息
        url += '&BANCI=' + monthEquStopLinePanel.ht_outputStore.baseParams['BANCI'];
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
            },{
                xtype : 'combo',
                fieldLabel : '班次',
                store : banciStore,
                emptyText : '全部班次',
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
            }],
            action : 'ZZCJ_QUERY_PMC_EQUIPMENT_STOPLINE_ACTION',
            outputTable : 'PMC_EQUIPMENT_STOPLINE'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 120
            }, {
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            }, {
                header : '班次',
                dataIndex : 'BANCI',
                width : 60,
                renderer : function(value){
                	if(value==1){
                		return '一班';
                	}
                	if(value==2){
                		return '二班';
                	}
                	if(value==3){
                		return '三班';
                	}
                	return '全部';
                }
            },{
                header : '工段时间（秒）',
                dataIndex : 'CYCLETIME',
                width : 150,
                renderer : function(v){
                	if(v==null){
                		return 0;
                	}
                	return parseInt(v*10)/10;
                }
            },{
                header : '停线总时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '停线总次数',
                dataIndex : 'STOPCOUNT',
                width : 120
             }, {
                 header : 'CON',
                 dataIndex : 'DATA3',
                 width : 70
             }, {
                 header : 'ANDON',
                 dataIndex : 'DATA4',
                 width : 70
             }, {
                 header : 'FUL',
                 dataIndex : 'DATA6',
                 width : 70
             }, {
                 header : 'SHO',
                 dataIndex : 'DATA7',
                 width : 80
             }, {
                 header : 'ES',
                 dataIndex : 'DATA8',
                 width : 80
             }, {
                 header : 'PS',
                 dataIndex : 'DATA9',
                 width : 80
             }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['ID','PPDATE','FACTORY','WORKSHOP', 'FPRODUCTIONLINE','PRODUCTIONLINENAME','BANCI','CYCLETIME','PRODUCTIONLINE','STOPCOUNT',
                            'STOPTIME','STOPCOUNT','DATA3','DATA3','DATA4','DATA5','DATA6','DATA7','DATA8','DATA9','DATA10'],
            deletedKeyFields : ['ID'],
            ctTopbarComponts : [{ 
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
   
    var yearPiePanel = new Ext.Panel({
        title : '停线总时间（分钟）',
        region : 'south',
        height : 220,
        border : true,
        items: {
            store: pieYearStore,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            xtype: 'piechart',
            dataField: 'STOPTIME',
            categoryField: 'PRODUCTIONLINE',
            extraStyle : {
                legend : {
                    display: 'right',
                    padding : 5,
                    font : {
                        size : 12
                    }
                }
            },
            tipRenderer : function(chart, record, i, series){
                return '工位：' + record.get(chart.categoryField) + '\n'  + 
                        '停线总时间: ' + record.get(chart.dataField) + '分钟\n' +
                        '百分比：' + (100 * record.get(chart.dataField) / (totolStopTime || 1)).toFixed(2) + '%';
            }
        }
    });
    
    yearEquStopLinePanel.ht_outputStore.on('load', function() {
    	
    	totolStopTime = 0;
    	var totolStopCount = 0;
                  
        pieYearStore.removeAll();
        for (var i = 0; i < yearEquStopLinePanel.ht_outputStore.getCount(); i++) {
            var stopLineRecord = yearEquStopLinePanel.ht_outputStore.getAt(i);
            
            totolStopTime += stopLineRecord.get('STOPTIME');
            totolStopCount += stopLineRecord.get('STOPCOUNT');
            
            pieYearStore.add(stopLineRecord.copy());
        }
    })
  
    
    //导出excel
    var yearexportFn = function(){
        
        var url = 'json?action=ZZCJ_EXPORT_PMC_EQUIPMENT_STOPLINE_YEAR_ACTION';
                    
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
      //班次信息
        url += '&BANCI=' + yearEquStopLinePanel.ht_outputStore.baseParams['BANCI'];
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
            }, {
	            height : 220,
	            region : 'south',
	            layout : 'border',
	            border : false,
	            items : [piePanel]}]
        }, {
            title : '周报表',
            border : false,
            layout : 'border',
            items : [weekEquStopLinePanel, weekPiePanel,weekexportPanel]
        }
        ,{
            title : '月报表',
            border : false,
            layout : 'border',
            items : [monthEquStopLinePanel, monthPiePanel,monthexportPanel]
        }
        ,{
            title : '年报表',
            border : false,
            layout : 'border',
            items : [yearEquStopLinePanel, yearPiePanel,yearexportPanel]}
        ],
        listeners : {
            beforedestroy : function(){
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
