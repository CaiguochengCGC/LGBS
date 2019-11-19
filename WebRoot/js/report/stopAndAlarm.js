/*
* 工位停机与报警对应
*/
(function () {
    var queryPanelIsLayout;
    //新增班次信息
    var bancidata = [["", "全部"], ["1", "1班"], ["2", "2班"]];
    var banciStore = new Ext.data.SimpleStore({
        auteLoad: true,
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

    //工段
    var sectionStore1 = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_STATION.rs',
        baseParams : {
            action : 'QUERY_PMC_PP_STATION_ACTION',
            CODE_TYPE : 'PRODUCTIONLINENAME'
        },
        fields : ['VALUE','TEXT']
    });
    sectionStore1.load({
        callback : function() {
            ht.pub.store.addBlankText(sectionStore);
        }
    });
    var alarmMachine = new Ext.data.JsonStore({
        url: 'json',
        root: 'root.ALARM_EQUMENT.rs',
        baseParams: {
            action: 'QUERY_ALARM_EQUMENT_ACTION',
            QURY_TYPE : 'null',
            LINE_TYPE : 'null'
        },
        fields: ['VALUE', 'TEXT']
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
                    value:new Date(new Date().getTime() - ((new Date().getHours() +16) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)),
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
                    style: 'text-align: center;',
                    value: '到'
                }, {
                    xtype: 'datetimefield',
                    format: ht.config.format.DATETIME,
                    fieldLabel: '结束时间',
                    columnWidth: 1 / 4,
                    value:new Date(new Date().getTime() - ((new Date().getHours() +5) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)),
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
                    alarmMachine.baseParams.LINE_TYPE=combo.value;
                    }
                }
            }, {
                xtype: 'combo',
                fieldLabel: '工位',
                columnWidth: 1 / 4,
                store: stationStore,
                emptyText: ht.msg.combo.emptyText,
                mode: 'local',
                triggerAction: 'all',
                valueField: 'VALUE',
                displayField: 'TEXT',
                hiddenName: 'DATA4',
                name: 'DATA4',
                editable: true,
                listeners:{
                    select : function(combo, record,index){
                        alarmMachine.value = "";
                        alarmMachine.baseParams.QURY_TYPE=combo.value;
                        alarmMachine.load();

                    }
                }
            },
                {
                    xtype: 'combo',
                    fieldLabel: '设备',
                    columnWidth: 1 / 4,
                    store: alarmMachine,
                    emptyText: ht.msg.combo.emptyText,
                    mode: 'local',
                    triggerAction: 'all',
                    emptyText: '请先选择工位',
                    valueField: 'VALUE',
                    displayField: 'TEXT',
                    hiddenName: 'ALARM_MACHINE',
                    name: 'ALARM_MACHINE',
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
            action: 'QUERY_STOP_AND_ALARM_ACTION',
            outputTable: 'STA_STOP_RECORD'
        },
        gridConfig: {
            columns: [new Ext.grid.RowNumberer({width: 30}),  {
                header: '线体名称',
                dataIndex: 'LINENAME',
                width: 80
            },{
                header: '停机开始时间',
                dataIndex: 'STOP_START_TIME',
                width: 140,
                renderer: function (value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header: '停机结束时间',
                dataIndex: 'STOP_END_TIME',
                width: 140,
                renderer: function (value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            },{
                header: '停机总时间',
                dataIndex: 'STOP_TIME',
                width: 80
            }, {
                header: '停机工位',
                dataIndex: 'STATION',
                width: 80
            },{
                header: '停机原因',
                dataIndex: 'STOP_RESON',
                width: 100
            } , {
                header: '班次',
                dataIndex: 'SHIFT',
                width: 80,
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
                    return '未定义';
                }
            }, {
                header: '报警开始时间',
                dataIndex: 'ALARM_START_TIME',
                width: 140,
                renderer: function (value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header: '报警结束时间',
                dataIndex: 'ALARM_END_TIME',
                width: 140,
                renderer: function (value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header: '报警总时间',
                dataIndex: 'ALARM_TIME',
                width: 80
            },{
                header: '报警工位',
                dataIndex: 'ALARM_STATION',
                width: 80
            }, {
                header: '报警设备',
                dataIndex: 'ALARM_MACHINE',
                width: 80
            }, {
                header: '报警类别',
                dataIndex: 'ALARM_CLASS',
                width: 100
            }, {
                header: '报警信息',
                dataIndex: 'ALARM_MESSAGE',
                width: 550
            }],

            isPageAction: true,
            isMultipleSelect: false,
            storeFields: [
                'ID',
                'STOP_START_TIME',
                'STOP_END_TIME',
                'LINECODE',
                'LINENAME',
                'STATION',
                'STOP_RESON',
                'STOP_TIME',
                'SHIFT',
                'ALARM_START_TIME',
                'ALARM_END_TIME',
                'ALARM_TIME',
                'ALARM_MESSAGE',
                'ALARM_MACHINE',
                'ALARM_CLASS',
                'ALARM_STATION'

            ],
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

        var url = 'json?action=EXPORT_STOP_AND_ALARM_LOG_ACTION';

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
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + faultEventPanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        url += '&PRODUCTIONLINENAME=' + faultEventPanel.ht_outputStore.baseParams['PRODUCTIONLINENAME'];
        url += '&BANCI=' + faultEventPanel.ht_outputStore.baseParams['BANCI'];
        url += encodeURI(encodeURI('&DATA3=' + faultEventPanel.ht_outputStore.baseParams['DATA3']));

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

