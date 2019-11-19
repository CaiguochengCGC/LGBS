/*
* stationAlarm工位停机记录
*/
(function () {
    var queryPanelIsLayout;
    //新增班次信息
    var bancidata = [["", "全部"], ["1", "1班"], ["2", "2班"]];
    var banciStore = new Ext.data.SimpleStore({
        auteLoad: true, //此处设置为自动加载
        data: bancidata,
        fields: ["VALUE", "TEXT"]
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

    //工位名称 联动查询
    var stationStore = new Ext.data.JsonStore({
        url : 'json',
        root : "root.PMC_PP_STATION.rs",
        baseParams : {
            action : 'QUERY_TAB_STOP_STA_ALL_ACTION',
            CODE_TYPE : 'DATA7',
            QURY_TYPE : 'null'
        },
        fields : ['VALUE','TEXT']
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

       //故障事故记录
    var faultEventPanel = new ht.base.PlainPanel({
        region: 'center',
        height: 200,
        queryFormConfig: {
            enableQueryButton: true,
            items: [{
                xtype: 'compositefield',
                fieldLabel: '时间',
                name: 'EFFECT_TIME',
                columnWidth: 1 / 2,
                items: [{
                    xtype: 'datetimefield',
                    format: ht.config.format.DATETIME,
                    fieldLabel: '开始时间',
                    columnWidth: 1 / 4,
                    value: (new Date().getHours() > 7 && new Date().getHours() < 19) ? new Date(new Date().getTime() - ((new Date().getHours() - 8) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)) : ((new Date().getHours() < 8) ? new Date(new Date().getTime() - ((new Date().getHours() + 4) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)) : new Date(new Date().getTime() - ((new Date().getHours() - 19) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000))),
                    name: 'START_EFFECT_TIME',
                    vtype: 'defineValid',
                    vtypeText: 'none',
                    defineValid: function (val, fromField) {
                        if (queryPanelIsLayout && faultEventPanel.findQueryCompment('END_EFFECT_TIME')) {
                            faultEventPanel.findQueryCompment('END_EFFECT_TIME').validate();
                        }
                        return true;
                    }
                }, {
                    xtype: 'displayfield',
                    width: 20,
                    style: 'text-align: center',
                    value: '到'
                }, {
                    xtype: 'datetimefield',
                    format: ht.config.format.DATETIME,
                    fieldLabel: '结束时间',
                    columnWidth: 1 / 4,
                    value: (new Date().getHours() > 7 && new Date().getHours() < 19) ? new Date(new Date().getTime() - ((new Date().getHours() - 19) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)) : ((new Date().getHours() < 8) ? new Date(new Date().getTime() - ((new Date().getHours() - 8) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)) : new Date(new Date().getTime() - ((new Date().getHours() - 32) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000))),
                    name: 'END_EFFECT_TIME',
                    vtype: 'comparentDate',
                    vtypeText: "终止时间应晚于起始时间",
                    comparentTo: 'START_EFFECT_TIME',
                    getParentCompont: function () {
                        return faultEventPanel.find('name', 'EFFECT_TIME')[0].innerCt;
                    }
                }]
            },{
                xtype : 'combo',
                fieldLabel : '工段名字',
                store : lineComment,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                columnWidth : 1/4,
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false,
                listeners:{
                    select : function(combo, record,index){
                        stationStore.value = "";
                        stationStore.baseParams.QURY_TYPE=combo.value;
                        stationStore.load();

                    }
                }
            }, {
                xtype: 'combo',
                fieldLabel: '工位',
                columnWidth: 1 / 4,
                store: stationStore,
                mode: 'local',
                triggerAction: 'all',
                emptyText : '请先选择工段',
                valueField: 'VALUE',
                displayField: 'TEXT',
                hiddenName: 'DATA4',
                name: 'DATA4',
                editable: true
            }, {
                    xtype: 'combo',
                    fieldLabel: '班次',
                    columnWidth: 1 / 4,
                    store: banciStore,
                    emptyText: '全部班次',
                    mode: 'local',
                    triggerAction: 'all',
                    valueField: 'VALUE',
                    displayField: 'TEXT',
                    hiddenName: 'BANCI',
                    name: 'BANCI',
                    editable: false
                }], 
            action: 'QUERY_STAIION_STOP_RECORD_ACTION',
            outputTable: 'STA_STOP_RECORD'
        },
        gridConfig: {
            columns: [new Ext.grid.RowNumberer(),{
                header: '线体名称',
                dataIndex: 'EventData7',
                width: 140
            }, {
                header: '停机开始时间',
                dataIndex: 'EventDate',
                width: 140,
                renderer: function (value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header: '停机结束时间',
                dataIndex: 'EventDate1',
                width: 140,
                renderer: function (value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header: '停机总时间',
                dataIndex: 'EventData1',
                width: 140
            }, {
                header: '停机原因',
                dataIndex: 'EventData3',
                width: 140
            }, {
                header: '停机工位',
                dataIndex: 'EventData4',
                width: 140
            }, {
                header: '班次',
                dataIndex: 'BANCI',
                width: 140,
                renderer: function (value) {
                    if (value == 1) {
                        return '一班';
                    }
                    if (value == 2) {
                        return '二班';
                    }
                    if (value == 3) {
                        return '三班';
                    }
                    return '全部';
                }
            }],

            isPageAction: true,
            isMultipleSelect: false,
            storeFields: ['EventDate', 'EventDate1', 'EventData3', 'EventData4', 'EventData7', 'BANCI','EventData1'],
            ctTopbarComponts: [{
                iconCls: 'excel',
                text: '导出',
                enableByRowSelected: false,
                enableByHasData: true,
                tagValue: '',
                position: 3,
                handler: function () {
                    exportFn();
                }
            }]
        },
        ctListeners: {},
        listeners: {
            afterLayout: function () {
                queryPanelIsLayout = true;
            }
        }
    });

    //导出excel
    var exportFn = function () {

        var url = 'json?action=QUERY_EXPORT_STAIION_STOP_RECORD_ACTION';

        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();

        var columns = faultEventPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }

            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }

        url += '&START_EFFECT_TIME=' + faultEventPanel.ht_outputStore.baseParams['START_EFFECT_TIME'];
        url += '&END_EFFECT_TIME=' + faultEventPanel.ht_outputStore.baseParams['END_EFFECT_TIME'];
        url += '&PRODUCTIONLINENAME=' + faultEventPanel.ht_outputStore.baseParams['PRODUCTIONLINENAME'];
        url += '&BANCI=' + faultEventPanel.ht_outputStore.baseParams['BANCI'];

        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');

        exportPanel.setSrc(url);
    }

    //导出面板
    var exportPanel = new Ext.ux.ManagedIFramePanel({
        region: 'east',
        width: 0,
        border: true,
        bodyBorder: false,
        autoScroll: true,
        autoLoad: false,
        defaultSrc: null
    });

    var backPanel = new Ext.Panel({
        border: false,
        layout: 'border',
        items: [faultEventPanel, exportPanel],
        listeners: {
            beforedestroy: function () {
                backPanel.removeAll(true);
            }
        }
    });

    return backPanel;
})();

