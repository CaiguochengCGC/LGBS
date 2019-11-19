/**
 * 固定报表 -> 实际生产时间查询
 */
(function() {
    var msgResource = {};
    var totolStopTime;
    
     var setPanelIsLayout;

    //工段
    var sectionStore = new Ext.data.JsonStore({
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
    });
    
    //生产线
    var productionLineStore = new Ext.data.JsonStore({
        url : "json",
        root : "root.PUB_DATA_DICT.rs",
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'PRODUCTION_LINE'

        },
        fields : ["VALUE", "TEXT"]
    });
    var productionLineQueryStore = new Ext.data.Store({ 
        recordType: productionLineStore.recordType 
    });
    productionLineStore.load({
        callback : function() {
            ht.pub.store.cloneStore(productionLineStore, productionLineQueryStore);
            ht.pub.store.addBlankText(productionLineQueryStore);
        }
    });
    
    //生产时间报表
    var workTimePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            enableQueryButton : true, 
            items : [{
                xtype: 'compositefield',
                fieldLabel: '生产日期',
                name : 'PLAN_DATE',
                items: [{
                    xtype : 'datefield',
                    format : ht.config.format.DATE,
                    fieldLabel : ' ',
                    name : 'START_PLAN_DATE',
                    value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000),
                    
                    vtype: 'defineValid',
                    vtypeText : '',
                    defineValid : function(val, fromField){
                        if(setPanelIsLayout && workTimePanel.findQueryCompment('END_PLAN_DATE')){
                            workTimePanel.findQueryCompment('END_PLAN_DATE').validate();
                        }
                        return true;
                    }
                }, {
                    xtype: 'displayfield',
                    width : 20,
                    style : 'text-align: center;',
                    value : '至'
                },{
                    xtype : 'datefield',
                    format : ht.config.format.DATE,
                    fieldLabel : ' ',
                    name : 'END_PLAN_DATE',
//                    value : new Date().getTime(),
                    value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000),
                    vtype: 'comparentDate',
                    vtypeText: ht.msg.valid.compareDate,
                    comparentTo: 'START_PLAN_DATE',
                    getParentCompont : function() {
                        return workTimePanel.ht_outputQueryForm.find('name', 'PLAN_DATE')[0].innerCt;
                    }
                }]
            }/*,{
                xtype : 'combo',
                fieldLabel : '工段名字：',
                store : sectionStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false
            }*/],
            action : 'CYCJ_QUERY_PMC_DATE_PP_PERIOD_ACTION',
            outputTable : 'PMC_DATE_PP'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),
                /*{
                header : '线体归类',
                dataIndex : 'FPRODUCTIONLINE',
                width : 100,
                renderer: function(value){
                    return value||'';
                }
            }, */{
                header : '生产日期',
                dataIndex : 'PPDATE',
                width : 80,
                renderer: function(value){
                    return ht.pub.date.dateRenderer(value);
                }
            }, {
                header : '工段',
                dataIndex : 'PRODUCTLINE',
                width : 80,
                renderer: function(value){
                    return value||'';
                }
            },{
                header : '工段名字',
                dataIndex : 'PRODUCTLINENAME',
                width : 150
            }, {
                header : '工位',
                dataIndex : 'PRODUCTTYPE',
                width : 130
            }, {
                header : '1班历时(小时)',
                dataIndex : 'ONEWORKTIME',
                width : 90
            }, {
                header : '2班历时(小时)',
                dataIndex : 'TWOWORKTIME',
                width : 90
            }, {
                header : '3班历时(小时)',
                dataIndex : 'THREEWORKTIME',
                width : 90
            }],
            
            isMultipleSelect : false,
            isPageAction : false,
            storeFields : ['ID','PPDATE','PRODUCTLINE','PRODUCTTYPE','ONEWORKTIME', 'TWOWORKTIME'
                            , 'THREEWORKTIME','PRODUCTLINENAME'],
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 4,
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
        
        var url = 'json?action=EXPORT_PMC_DATE_PP_PERIOD_ACTION';
                    
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
        
//        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + workTimePanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        url += '&START_PLAN_DATE=' + workTimePanel.ht_outputStore.baseParams['START_PLAN_DATE'];
        url += '&END_PLAN_DATE=' + workTimePanel.ht_outputStore.baseParams['END_PLAN_DATE'];
        
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
        items : [workTimePanel,exportPanel],
        listeners : {
            beforedestroy : function(){
                sectionStore.destroy();
                productionLineStore.destroy();
                productionLineQueryStore.destroy();
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
