/**
 * 固定报表 -> 实际生产时间报表
 */
(function() {
    var msgResource = {};
    var totolStopTime;

    //班次
    var shiftStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'SHIFT'
        },
        fields : ['VALUE','TEXT']
    });
    shiftStore.load();
    
    //生产时间报表
    var workTimePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            enableQueryButton : false,
            action : 'CYCJ_QUERY_PMC_DATE_PP_ACTION',
            outputTable : 'PMC_DATE_PP'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),
                {
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 100,
                renderer: function(value){
                    return value||'';
                }
            },{
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            }, {
                header : '1班实际开班',
                dataIndex : 'STARTTIME1',
                width : 130,
                renderer: function(value){
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '1班实际结束',
                dataIndex : 'ENDTIME1',
                width : 130,
                renderer: function(value){
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '1班历时(h)',
                dataIndex : 'WORKTIME1',
                width : 80
            }, {
                header : '2班实际开班',
                dataIndex : 'STARTTIME2',
                width : 130,
                renderer: function(value){
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '2班实际结束',
                dataIndex : 'ENDTIME2',
                width : 130,
                renderer: function(value){
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '2班历时(h)',
                dataIndex : 'WORKTIME2',
                width : 80
            }, {
                header : '3班实际开班',
                dataIndex : 'STARTTIME3',
                width : 130,
                renderer: function(value){
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '3班实际结束',
                dataIndex : 'ENDTIME3',
                width : 130,
                renderer: function(value){
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '3班历时(h)',
                dataIndex : 'WORKTIME3',
                width : 80
            }],
            
            isMultipleSelect : false,
            isPageAction : false,
            storeFields : ['ID','WORKSHOP','FPRODUCTIONLINE','PRODUCTIONLINENAME', 'PRODUCTIONLINE'
                            , 'STARTTIME1','ENDTIME1','STARTTIME2','ENDTIME2','STARTTIME3','ENDTIME3'
                            ,'WORKTIME1','WORKTIME2','WORKTIME3'],
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
    
    //计划日期面板
    var planDatePanel = new ht.base.PlainPanel({
        region : 'north',
        height : 200,
        queryFormConfig : {
            enableQueryButton : true, 
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'WORKDATE',
                format : ht.config.format.DATE,
                value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
            }],
            action : 'CYCJ_QUERY_PMC_DATE_IMPORT_ACTION',
            outputTable : 'PMC_DATE_IMPORT'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '生产日期',
                dataIndex : 'WORKDATE',
                width : 120,
                renderer: function(value){
                    return ht.pub.date.dateRenderer(value);
                }
            }, {
                header : '开始时间',
                dataIndex : 'STARTTIME',
                width : 130,
                renderer: function(value){
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '结束时间',
                dataIndex : 'ENDTIME',
                width : 130,
                renderer: function(value){
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '计划生产历时',
                dataIndex : 'LASTTIME',
                width : 120
            }, {
                header : '班次',
                dataIndex : 'SHIFT',
                width : 70,
                renderer : function(value){
                    return ht.pub.getValuesExactByKeyDefault(shiftStore,value)
                }
            }],
            
            isMultipleSelect : false,
            isPageAction : false,
            storeFields : ['WORKDATE','STARTTIME', 'ENDTIME', 'LASTTIME','SHIFT']
        },
        ctListeners : {
            beforeQuery : function(values){
                workTimePanel.ht_outputStore.baseParams['PPDATE'] = values['WORKDATE'];
                workTimePanel.ht_outputStore.load();
                return true; 
            }
        }
    
    });
    
    //导出excel
    var exportFn = function(){
        
        var url = 'json?action=EXPORT_PMC_DATE_PP_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = workTimePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&WORKDATE=' + planDatePanel.ht_outputStore.baseParams['WORKDATE'];
        
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
   
    var backPanel =new Ext.Panel({
        border : false,
        layout : 'border',
        items : [workTimePanel, planDatePanel,exportPanel],
        listeners : {
            beforedestroy : function(){
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
