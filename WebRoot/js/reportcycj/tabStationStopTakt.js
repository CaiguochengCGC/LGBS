/**
 * 瓶颈工位停机节拍报表
 */
(function() {
    var msgResource = {};
    var totolStopTime;
    var weekDefault;

    //工段
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

    //报表级别
    var toltlagStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'TOLTLAG'
        },
        fields : ['VALUE','TEXT']
    });
    toltlagStore.load();
    
    weekDefault = new Date().getWeekOfYear()-1;
    
    //周
    var weekStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'CYCJ_GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'WEEK'
        },
        fields : ['VALUE','TEXT']
    });
    for(var i = 1 ; i <= 53; i++){
       weekStore.add(new Ext.data.Record({
            VALUE : i-1,
            TEXT : '第' + i + '周'
       })) 
    }
    
    //停机日报表
    var stationStopDatePanel = new ht.base.PlainPanel({
        region : 'center',
        height : 200,
        queryFormConfig : {
            enableQueryButton : true, 
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期:',
                name : 'PPDATE',
                format : ht.config.format.DATE,
                value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000),
                style : 'text-align: left;'
            },{
                xtype : 'combo',
                fieldLabel : '工段名字:',
                store : sectionStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false,
                style : 'text-align: left;'
            }],
            action : 'CYCJ_QUERY_PMC_STATION_CT_ACTION',
            outputTable : 'PMC_STATION_CT'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 100
            }, {
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            },{
                header : '工位',
                dataIndex : 'STATION',
                width : 90
            },{
                header : '车型',
                dataIndex : 'MODEL',
                width : 80
            }, {
                header : '工位CT',
                dataIndex : 'DATA14',
                width : 80
            }, {
                header : '机器人（秒）',
                dataIndex : 'DATA4',
                width : 80
            }, {
                header : '机运（秒）',
                dataIndex : 'DATA5',
                width : 80
            }, {
                header : '工装（秒）',
                dataIndex : 'DATA6',
                width : 80
            }, {
                header : '等待（秒）',
                dataIndex : 'DATA7',
                width : 80
            }, {
                header : '堵料（秒）',
                dataIndex : 'DATA8',
                width : 80
            }, {
                header : '人工（秒）',
                dataIndex : 'DATA9',
                width : 80
            }, {
                header : '停机（秒）',
                dataIndex : 'DATA10',
                width : 80
            }, {
                header : '换模/换门（秒）',
                dataIndex : 'DATA11',
                width : 100
            }, {
                header : '压机（秒）',
                dataIndex : 'DATA12',
                width : 80
            }],

            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PPDATE','PRODUCTIONLINE','PRODUCTIONLINENAME','STATION', 'MODEL', 'DATA12',
            				'DATA4','DATA5','DATA6','DATA7','DATA8','DATA9','DATA10','DATA11','DATA14'],
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
           beforeQuery : function(values) {
				values['QUERY_TYPE'] = '0'
				return true
			}
        }
    
    });
    
    //导出excel
    var exportFn = function(){
        
        var url = 'json?action=EXPORT_PMC_STATION_CT_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = stationStopDatePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&PPDATE=' + stationStopDatePanel.ht_outputStore.baseParams['PPDATE'];
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + stationStopDatePanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        
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
   
    //停机周报表
    var stationStopWeekPanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype : 'combo',
                fieldLabel : '日期(周):',
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
                fieldLabel : '工段名字:',
                store : sectionStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false,
                style : 'text-align: left;'
            }],
            action : 'CYCJ_QUERY_PMC_STATION_CT_ACTION',
            outputTable : 'PMC_STATION_CT'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 150
            },{
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            }, {
                header : '工位',
                dataIndex : 'STATION',
                width : 100
            }, {
                header : '车型',
                dataIndex : 'MODEL',
                width : 120
            }, {
                header : '工位CT',
                dataIndex : 'DATA14',
                width : 80
            }, {
                header : '机器人（秒）',
                dataIndex : 'DATA4',
                width : 80
            }, {
                header : '机运（秒）',
                dataIndex : 'DATA5',
                width : 80
            }, {
                header : '工装（秒）',
                dataIndex : 'DATA6',
                width : 80
            }, {
                header : '等待（秒）',
                dataIndex : 'DATA7',
                width : 80
            }, {
                header : '堵料（秒）',
                dataIndex : 'DATA8',
                width : 80
            }, {
                header : '人工（秒）',
                dataIndex : 'DATA9',
                width : 80
            }, {
                header : '停机（秒）',
                dataIndex : 'DATA10',
                width : 80
            }, {
                header : '换模/换门（秒）',
                dataIndex : 'DATA11',
                width : 100
            }, {
                header : '压机（秒）',
                dataIndex : 'DATA12',
                width : 80
            }],

            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PPDATE','PRODUCTIONLINE','PRODUCTIONLINENAME','STATION', 'MODEL', 'DATA12',
            				'DATA4','DATA5','DATA6','DATA7','DATA8','DATA9','DATA10','DATA11','DATA14'],
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
           beforeQuery : function(values){
           	values['QUERY_TYPE'] = '3'
           return true}
        }
    });
    
    //周报表导出excel
    var weekexportFn = function(){
        
        var url = 'json?action=EXPORT_PMC_STATION_CT_WEEK_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = stationStopWeekPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&PPDATE=' + stationStopWeekPanel.ht_outputStore.baseParams['PPDATE'];
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + stationStopWeekPanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        
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
    
    //停机月报表
    var stationStopMonthPanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype:'datefield',
                name : 'PPDATE',
                fieldLabel : '月份:',
                value : new Date(),
                format : 'Y-m',
                renderer : function(value) {
                    return ht.pub.date.dateRenderer(value);
                }
            },{
                xtype : 'combo',
                fieldLabel : '工段名字:',
                store : sectionStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false,
                style : 'text-align: left;'
            }],
            action : 'CYCJ_QUERY_PMC_STATION_CT_ACTION',
            outputTable : 'PMC_STATION_CT'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 150
            }, {
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            },{
                header : '工位',
                dataIndex : 'STATION',
                width : 100
            }, {
                header : '车型',
                dataIndex : 'MODEL',
                width : 120
            }, {
                header : '工位CT',
                dataIndex : 'DATA14',
                width : 80
            }, {
                header : '机器人（秒）',
                dataIndex : 'DATA4',
                width : 80
            }, {
                header : '机运（秒）',
                dataIndex : 'DATA5',
                width : 80
            }, {
                header : '工装（秒）',
                dataIndex : 'DATA6',
                width : 80
            }, {
                header : '等待（秒）',
                dataIndex : 'DATA7',
                width : 80
            }, {
                header : '堵料（秒）',
                dataIndex : 'DATA8',
                width : 80
            }, {
                header : '人工（秒）',
                dataIndex : 'DATA9',
                width : 80
            }, {
                header : '停机（秒）',
                dataIndex : 'DATA10',
                width : 80
            }, {
                header : '换模/换门（秒）',
                dataIndex : 'DATA11',
                width : 100
            }, {
                header : '压机（秒）',
                dataIndex : 'DATA12',
                width : 80
            }],

            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PPDATE','PRODUCTIONLINE','PRODUCTIONLINENAME','STATION', 'MODEL', 'DATA12',
            				'DATA4','DATA5','DATA6','DATA7','DATA8','DATA9','DATA10','DATA11','DATA14'],
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
           beforeQuery : function(values){
           	values['QUERY_TYPE'] = '1'
           return true}
        }
    });
    
    //月报表导出excel
    var monthexportFn = function(){
        
        var url = 'json?action=EXPORT_PMC_STATION_CT_MONTH_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = stationStopMonthPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&PPDATE=' + stationStopMonthPanel.ht_outputStore.baseParams['PPDATE'];
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + stationStopMonthPanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        
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
    
    //停机年报表
    var stationStopYearPanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype:'datefield',
                name : 'PPDATE',
                fieldLabel : '年份:',
                value : new Date(),
                format : 'Y'
            },{
                xtype : 'combo',
                fieldLabel : '工段名字:',
                store : sectionStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false,
                style : 'text-align: left;'
            }],
            action : 'CYCJ_QUERY_PMC_STATION_CT_ACTION',
            outputTable : 'PMC_STATION_CT'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 150
            }, {
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            },{
                header : '工位',
                dataIndex : 'STATION',
                width : 100
            }, {
                header : '车型',
                dataIndex : 'MODEL',
                width : 120
            }, {
                header : '工位CT',
                dataIndex : 'DATA14',
                width : 80
            }, {
                header : '机器人（秒）',
                dataIndex : 'DATA4',
                width : 80
            }, {
                header : '机运（秒）',
                dataIndex : 'DATA5',
                width : 80
            }, {
                header : '工装（秒）',
                dataIndex : 'DATA6',
                width : 80
            }, {
                header : '等待（秒）',
                dataIndex : 'DATA7',
                width : 80
            }, {
                header : '堵料（秒）',
                dataIndex : 'DATA8',
                width : 80
            }, {
                header : '人工（秒）',
                dataIndex : 'DATA9',
                width : 80
            }, {
                header : '停机（秒）',
                dataIndex : 'DATA10',
                width : 80
            }, {
                header : '换模/换门（秒）',
                dataIndex : 'DATA11',
                width : 100
            }, {
                header : '压机（秒）',
                dataIndex : 'DATA12',
                width : 80
            }],

            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PPDATE','PRODUCTIONLINE','PRODUCTIONLINENAME','STATION', 'MODEL', 'DATA12',
            				'DATA4','DATA5','DATA6','DATA7','DATA8','DATA9','DATA10','DATA11','DATA14'],
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
           beforeQuery : function(values){
           	values['QUERY_TYPE'] = '2'
           return true}
        }
    });
    
    //年报表导出excel
    var yearexportFn = function(){
        
        var url = 'json?action=EXPORT_PMC_STATION_CT_YEAR_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = stationStopYearPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&PPDATE=' + stationStopYearPanel.ht_outputStore.baseParams['PPDATE'];
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + stationStopYearPanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        
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
    

    var backPanel =new Ext.TabPanel({
        activeTab: 0,
        border : false,
        items : [{
            title : '日报表',
            border : false,
            layout : 'border',
            items : [stationStopDatePanel,exportPanel]
        },{
            title : '周报表',
            border : false,
            layout : 'border',
            items : [stationStopWeekPanel,weekexportPanel]
        },{
            title : '月报表',
            border : false,
            layout : 'border',
            items : [stationStopMonthPanel,monthexportPanel]
        },{
            title : '年报表',
            border : false,
            layout : 'border',
            items : [stationStopYearPanel,yearexportPanel]}
        ],
        listeners : {
            beforedestroy : function(){
                sectionStore.destroy();
                weekStore.destroy();
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
