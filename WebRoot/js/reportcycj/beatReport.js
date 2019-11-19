/**
 * 固定报表 -> 节拍报表
 */
(function() {
    var msgResource = {};
    var totolStopTime;
    var setPanelIsLayout;
    //新增班次信息
    var bancidata = [["","全部"],["1","1班"],["2","2班"],["3","3班"]];
    var banciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:bancidata,
     fields:["VALUE","TEXT"]
    });
    //班次
    var shiftStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'SHIFT_TYPE'
        },
        fields : ['VALUE','TEXT']
    });
    shiftStore.load({
        callback : function() {
            ht.pub.store.addBlankText(shiftStore);
        }
    });
    
    //工位
/*    var stationStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_STATION.rs',
        baseParams : {
            action : 'CYCJ_QUERY_PMC_PP_STATION_CT_ACTION',
            CODE_TYPE : 'STATION',
            QURY_TYPE : ''
        },
        fields : ['VALUE','TEXT']
    });*/
    var stationStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_STATION.rs',
        baseParams : {
            action : 'CYCJ_QUERY_PMC_PP_STATION_CT_ALL_ACTION',
            CODE_TYPE : 'EventData1',
            QURY_TYPE : ''
        },
        fields : ['VALUE','TEXT']
    });
    
    //工段
    /*var sectionStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_STATION.rs',
        baseParams : {
            action : 'CYCJ_QUERY_PMC_PP_STATION_ACTION',
            CODE_TYPE : 'PRODUCTIONLINENAME'
        },
        fields : ['VALUE','TEXT']
    });
    sectionStore.load({
        callback : function() {
            ht.pub.store.addBlankText(sectionStore);
        }
    });*/
    var sectionStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_STATION.rs',
        baseParams : {
            action : 'CYCJ_QUERY_PMC_PP_STATION_CT_ALL_ACTION',
            CODE_TYPE : 'EventData16'
        },
        fields : ['VALUE','TEXT']
    });
    sectionStore.load({
        callback : function() {
            ht.pub.store.addBlankText(sectionStore);
        }
    });
    
    
    
    //节拍日报表
    var beatDailyReportPanel = new ht.base.PlainPanel({
        region : 'center',
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
                hiddenName : 'bancic',
                name : 'bancic',
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            }],
            action : 'CYCJ_QUERY_TAB_CYCLE_TIME_ACTION',
            outputTable : 'TAB_CYCLE_TIME'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '时间',
                dataIndex : 'EventDate',
                width : 130,
                renderer : function(value, p, record){
                	return ht.pub.date.dateTimeRenderer(value);
                }
            },{
                header : '工段',
                dataIndex : 'EventData15',
                width : 80
            }, {
                header : '工段名字',
                dataIndex : 'EventData16',
                width : 120
            },{
                header : '工位',
                dataIndex : 'EventData1',
                width : 100
            }, {
                header : '车型',
                dataIndex : 'EventData3',
                width : 100
            }/*, {
                header : '班次',
                dataIndex : 'EventDate2',
                width : 80
            }*/,{
                header : '工位CT（秒）',
                dataIndex : 'EventData14',
                width : 85
            },{
                header : '人工时间（秒）',
                dataIndex : 'EventData9',
                width : 95
            },{
                header : '机运时间（秒）',
                dataIndex : 'EventData5',
                width : 95
            },{
                header : '工装时间（秒）',
                dataIndex : 'EventData6',
                width : 95
            },{
                header : '机器人时间（秒）',
                dataIndex : 'EventData4',
                width : 100
            },{
                header : '待料时间（秒）',
                dataIndex : 'EventData7',
                width : 95
            },{
                header : '堵料时间（秒）',
                dataIndex : 'EventData8',
                width : 95
            },{
                header : '报警时间（秒）',
                dataIndex : 'EventData10',
                width : 95
            },{
                header : '换模/换门（秒）',
                dataIndex : 'EventData11',
                width : 100
            },{
                header : '压机时间（秒）',
                dataIndex : 'EventData12',
                width : 95
            }],
            
            isPageAction : true,
            isMultipleSelect : false, 
            storeFields : ['EventDate','EventData15', 'EventData16', 'EventData1','EventData3'
                            ,'EventData14','EventData9', 'EventData5', 'EventData6','EventData4'
                            ,'EventData7','EventData8', 'EventData10', 'EventData11','EventData12'],
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    BexportFn();
                } 
            }]
        },
        ctListeners : {
           
        },
        listeners : {
            afterLayout : function(){
                setPanelIsLayout = true;
            }
        }
    });
    
    //导出excel
    var BexportFn = function(){
        
        var url = 'json?action=EXPORT_TAB_CYCLE_TIME_BY_BEAT_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = beatDailyReportPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&WORKDATE=' + beatDailyReportPanel.ht_outputStore.baseParams['WORKDATE'];
        
        url += encodeURI(encodeURI('&EventDate14=' + beatDailyReportPanel.ht_outputStore.baseParams['EventDate14']));
        url += '&EventDate1=' + beatDailyReportPanel.ht_outputStore.baseParams['EventDate1'];
        url += '&EventDate2=' + beatDailyReportPanel.ht_outputStore.baseParams['EventDate2'];
        
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        BexportPanel.setSrc(url);
    }
    
    //导出面板
    var BexportPanel = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
    // 工位总览表
    var tableStationSummaryPanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype : 'datefield',
                format : ht.config.format.DATE,
                fieldLabel : '日期',
                name : 'EventData',
                value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
            },{
                xtype : 'combo',
                fieldLabel : '工段名字：',
                store : sectionStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'EventDate14',
                name : 'EventDate14',
                editable : false,
                listeners : {
	                select : function(c){
	                    tableStationSummaryPanel.findQueryCompment('EventDate1').setValue(null);
	                }
	            }
            },{
                xtype : 'combo',
                fieldLabel : '工位',
                store : stationStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'EventDate1',
                name : 'EventDate1',
                editable : true,
                listeners : {
                    beforeQuery : function(c){
                        delete c.combo.lastQuery;
                        var gd = tableStationSummaryPanel.findQueryCompment('EventDate14').getValue();
                        stationStore.baseParams['QURY_TYPE'] = gd;
                        stationStore.load();
                    }
                }
            }],
            action : 'CYCJ_QUERY_TAB_CYCLE_TIME_BY_RAND_ACTION',
            outputTable : 'TAB_CYCLE_TIME'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '时间',
                dataIndex : 'EventDate',
                width : 130,
                renderer : function(value, p, record){
                	return ht.pub.date.dateTimeRenderer(value);
                }
            },{
                header : '工段',
                dataIndex : 'EventData15',
                width : 80
            }/*, {
                header : '工段名字',
                dataIndex : 'EventData16',
                width : 120
            }*/,{
                header : '工位',
                dataIndex : 'EventData1',
                width : 100
            }, {
                header : '车型',
                dataIndex : 'EventData3',
                width : 100
            }/*, {
                header : '班次',
                dataIndex : 'EventDate2',
                width : 80
            }*/,{
                header : '工位CT（秒）',
                dataIndex : 'EventData14',
                width : 85
            },{
                header : '人工时间（秒）',
                dataIndex : 'EventData9',
                width : 95
            },{
                header : '机运时间（秒）',
                dataIndex : 'EventData5',
                width : 95
            },{
                header : '工装时间（秒）',
                dataIndex : 'EventData6',
                width : 95
            },{
                header : '机器人时间（秒）',
                dataIndex : 'EventData4',
                width : 100
            },{
                header : '待料时间（秒）',
                dataIndex : 'EventData7',
                width : 95
            },{
                header : '堵料时间（秒）',
                dataIndex : 'EventData8',
                width : 95
            },{
                header : '报警时间（秒）',
                dataIndex : 'EventData10',
                width : 95
            },{
                header : '换模/换门（秒）',
                dataIndex : 'EventData11',
                width : 100
            },{
                header : '压机时间（秒）',
                dataIndex : 'EventData12',
                width : 95
            }],
            
            isPageAction : false,
            isMultipleSelect : false, 
            storeFields : ['EventDate','EventData15', 'EventData16', 'EventData1','EventData3'
                            ,'EventData14','EventData9', 'EventData5', 'EventData6','EventData4'
                            ,'EventData7','EventData8', 'EventData10', 'EventData11','EventData12'],
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
           
        }
    });
    
     //导出excel
    var exportFn = function(){
        
        var url = 'json?action=EXPORT_TAB_CYCLE_TIME_BY_RAND_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = tableStationSummaryPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&EventData=' + tableStationSummaryPanel.ht_outputStore.baseParams['EventData'];
        url += encodeURI(encodeURI('&EventDate14=' + tableStationSummaryPanel.ht_outputStore.baseParams['EventDate14']));
        url += '&EventDate1=' + tableStationSummaryPanel.ht_outputStore.baseParams['EventDate1'];
        url += '&EventDate2=' + tableStationSummaryPanel.ht_outputStore.baseParams['EventDate2'];
        
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
    
    var backPanel =new Ext.TabPanel({
        activeTab: 0,
        border : false,
        items : [{
            title : '节拍报表',
            border : false,
            layout : 'border',
            items : [beatDailyReportPanel,BexportPanel]
        },{
            title : '工位总览表',
            border : false,
            layout : 'border',
            items : [tableStationSummaryPanel,exportPanel]
        }],
        listeners : {
            beforedestroy : function(){
                shiftStore.destroy();//班次
                stationStore.destroy();//工位
                sectionStore.destroy();//工段
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
