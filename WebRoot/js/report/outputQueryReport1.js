/**
 * 固定报表 -> 节拍报表
 */
(function () {
    var msgResource = {};
    var totolStopTime;

    var queryType=[['','全部'],['1','冲压车间'],['2','车身车间']];
    var setPanelIsLayout;
    //新增班次信息
    var bancidata = [["","全部"],["1","1班"],["2","2班"]];
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
    var sheftQueryStore = new Ext.data.Store({
        recordType: shiftStore.recordType
    });
    shiftStore.load({
        callback : function() {
            ht.pub.store.cloneStore(shiftStore, sheftQueryStore);
            ht.pub.store.addBlankText(sheftQueryStore);
        }
    });
    //获得工段注释
    var lineComment = new Ext.data.JsonStore({
        url : 'json',
        root : "root.GET_COMMENT.rs",
        baseParams : {
            action : 'QUERY_LINECODE_COMMENT',
            CODE_TYPE : 'COMMENT'
        },
        fields : ['VALUE','TEXT']
    });

    lineComment.load({
        callback: function () {
            ht.pub.store.addBlankText(lineComment);
        }
    });

    //工段
    var sectionStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_STATION.rs',
        baseParams : {
            action : 'QUERY_PMC_PP_STATION_ACTION',
            CODE_TYPE : 'PRODUCTIONLINENAME'
        },
        fields : ['VALUE','TEXT']
    });
    sectionStore.load({
        callback : function() {
            ht.pub.store.addBlankText(sectionStore);
        }
    });
    //节拍日报表
    var faultEventPanel = new ht.base.RowEditorPanel({
        region: 'center',
        queryFormConfig: {
            enableQueryButton: true,
            items: [{
                xtype: 'compositefield',
                fieldLabel: '时间',
                name: 'EFFECT_TIME',
                columnWidth: 1 / 2,
                items: [{
                    xtype : 'datefield',
                    fieldLabel : '日期',
                    name : 'START_PLAN_DATE',
                    columnWidth : 1/2,
                    format : ht.config.format.DATE,
                    value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
                }, {
                    xtype: 'displayfield',
                    width: 20,
                    style: 'text-align: center;',
                    value: '到'
                },{
                    xtype : 'datefield',
                    fieldLabel : '日期',
                    name : 'END_PLAN_DATE',
                    columnWidth : 1/2,
                    format : ht.config.format.DATE,
                    value : new Date(new Date().getTime())
                }]
            },{
                xtype : 'combo',
                fieldLabel : '工段名字',
                store : lineComment,
                columnWidth : 1/4,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'EventDate18',
                name : 'EventDate18',
                editable : false
            },{
                xtype : 'combo',
                fieldLabel : '班次',
                store : banciStore,
                emptyText : '全部班次',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                columnWidth : 1/4,
                displayField : 'TEXT',
                hiddenName : 'BANCI',
                name : 'BANCI',
                editable : false
            },{
                xtype : 'combo',
                fieldLabel : '车间查询',
                store : queryType,
                columnWidth : 1/4,
                emptyText : '全部车间',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'queryType',
                name : 'queryType',
                editable : false
            }],
            action : 'QUERY_tabProductType_ACTION',
            outputTable : 'tabProductType'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工段',
                dataIndex : 'EventData5',
                width : 150
            },{
                header : '工段名字',
                dataIndex : 'EventData6',
                width : 160
            }, {
                header : '班次',
                dataIndex : 'Shift',
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
            }, {
                header : '车型',
                dataIndex : 'CarType',
                width : 80
            },{
                header : '实际产量',
                dataIndex : 'ActProduct',
                width : 80
            }],

            isMultipleSelect : false,
            isPageAction : false,
            storeFields : ['ID','EventData5','EventData6','ActProduct','PlanProduct','Shift','CarType'],
            ctTopbarComponts : [{
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 5,
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
        var url = 'json?action=EXPORT_TAB_PRODUCT_TYPE_ACTION';

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

        url += encodeURI(encodeURI('&EventDate18=' + workTimePanel.ht_outputStore.baseParams['EventDate18']));
        url += '&START_PLAN_DATE=' + workTimePanel.ht_outputStore.baseParams['START_PLAN_DATE'];
        url += '&END_PLAN_DATE=' + workTimePanel.ht_outputStore.baseParams['END_PLAN_DATE'];
        url += '&BANCI=' + workTimePanel.ht_outputStore.baseParams['BANCI'];
        url += '&queryType=' + workTimePanel.ht_outputStore.baseParams['queryType'];

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
    var backPanel = new Ext.TabPanel({
        activeTab: 0,
        border: false,
        items: [{
            title: '分车型产量报表',
            border: false,
            layout: 'border',
            items: [faultEventPanel, exportPanel]
        }],
        listeners: {
            beforedestroy: function () {
                backPanel.removeAll(true);
            }
        }
    });
    return backPanel;
})();
