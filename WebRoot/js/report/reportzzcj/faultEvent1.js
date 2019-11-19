/**
 * 固定报表 -> 节拍报表
 */
(function () {
    var setPanelIsLayout;
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

    //新增显示大于几秒的数据
    var secondData = [["", "全部"], ["1", "1"], ["2", "2"], ["5", "5"], ["10", "10"], ["20", "20"]];
    var secondStore = new Ext.data.SimpleStore({
        auteLoad: true, //此处设置为自动加载
        data: secondData,
        fields: ["VALUE", "TEXT"]
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
        url: 'json',
        root: 'root.PMC_PP_STATION.rs',
        baseParams: {
            action: 'QUERY_PMC_PP_STATION_ACTION',
            CODE_TYPE: 'PRODUCTIONLINENAME'
        },
        fields: ['VALUE', 'TEXT']
    });
    //属性
    var attrStore = new Ext.data.JsonStore({
        url: 'json',
        root: 'root.STOP_EQUMENT.rs',
        baseParams: {
            action: 'QUERY_EQUMENT_ACTION',
            CODE_TYPE: 'DATA3',
            QURY_TYPE : 'null',
            LINE_TYPE : 'null'
        },
        fields: ['VALUE', 'TEXT']
    });
    sectionStore.load({
        callback: function () {
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
                fieldLabel: '生产日期',
                name: 'EFFECT_TIME',
                columnWidth: 1 / 2,
                items: [{
                    xtype: 'datetimefield',
                    format: ht.config.format.DATETIME,
                    columnWidth: 1 / 2,
                    name: 'START_EFFECT_TIME',
                    value: (new Date().getHours() > 7 && new Date().getHours() < 19) ? new Date(new Date().getTime() - ((new Date().getHours() - 8) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)) : ((new Date().getHours() < 8) ? new Date(new Date().getTime() - ((new Date().getHours() + 4) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)) : new Date(new Date().getTime() - ((new Date().getHours() - 19) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)))
                }, {
                    xtype: 'displayfield',
                    width: 20,
                    style: 'text-align: center',
                    value: '到'
                }, {
                    xtype: 'datetimefield',
                    format: ht.config.format.DATETIME,
                    columnWidth: 1 / 2,
                    name: 'END_EFFECT_TIME',
                    value: (new Date().getHours() > 7 && new Date().getHours() < 19) ? new Date(new Date().getTime() - ((new Date().getHours() - 19) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)) : ((new Date().getHours() < 8) ? new Date(new Date().getTime() - ((new Date().getHours() - 8) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)) : new Date(new Date().getTime() - ((new Date().getHours() - 32) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)))
                }]
            }, {
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
                        attrStore.baseParams.LINE_TYPE=combo.value;

                    }
                }
            },{
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
                editable: true,
                listeners:{
                    select : function(combo, record,index){
                        attrStore.value = "";
                        attrStore.baseParams.QURY_TYPE=combo.value;
                        attrStore.load();

                    }
                }
            }, {
                xtype: 'combo',
                fieldLabel: '设备',
                columnWidth: 1 / 4,
                store: attrStore,
                emptyText: ht.msg.combo.emptyText,
                mode: 'local',
                triggerAction: 'all',
                emptyText: '请先选择工位',
                valueField: 'VALUE',
                displayField: 'TEXT',
                hiddenName: 'DATA3',
                name: 'DATA3',
                editable: true
            }, {
                xtype: 'combo',
                fieldLabel: '班次',
                store: banciStore,
                emptyText: '全部班次',
                mode: 'local',
                triggerAction: 'all',
                valueField: 'VALUE',
                displayField: 'TEXT',
                hiddenName: 'BANCI',
                name: 'BANCI',
                editable: false
            }, {
                xtype: 'combo',
                fieldLabel: '大于(S)',
                store: secondStore,
                emptyText: '全部',
                mode: 'local',
                triggerAction: 'all',
                valueField: 'VALUE',
                displayField: 'TEXT',
                hiddenName: 'SECOND',
                name: 'SECOND',
                editable: true
            }],
            action: 'QUERY_ALARM_LOG_ACTION',
            outputTable: 'ALARM_LOG'
        },
        gridConfig: {
            columns: [new Ext.grid.RowNumberer(), {
                header: '工段',
                dataIndex: 'DATA1',
                width: 70
            },{
                header: '工段名字',
                dataIndex: 'PRODUCTIONLINENAME',
                width: 100,
                renderer: function (value) {
                    return ht.pub.getValuesExactByKeyDefault(sectionStore, value);
                }
            }, {
                header: '工位',
                dataIndex: 'DATA7',
                width: 70,
                renderer: function (value) {
                    return ht.pub.getValuesExactByKeyDefault(stationStore, value);
                }
            }, {
                header: '班次',
                dataIndex: 'BANCI',
                width: 60,
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
            }, {
                header: '报警时间',
                dataIndex: 'GENERATION_TIME',
                width: 140,
                renderer: function (value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header: '恢复时间',
                dataIndex: 'TIMESTAMP',
                width: 140,
                renderer: function (value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header: '历时（秒）',
                dataIndex: 'WORKTIME',
                width: 70
            }, {
                header: '设备',
                dataIndex: 'DATA2',
                width: 100
            }, {
                header: '属性',
                dataIndex: 'DATA3',
                width: 100
            }, {
                header: '报警详细内容',
                dataIndex: 'ALARM_MESSAGE',
                width: 650
            }
            ],
            isPageAction: true,
            isMultipleSelect: false,
            storeFields: ['PRODUCTIONLINENAME', 'WORKTIME', 'GENERATION_TIME', 'TIMESTAMP', 'ALARM_MESSAGE', 'RESOURCE', 'DATA1', 'DATA2', 'DATA3', 'DATA7', 'BANCI'],
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
                setPanelIsLayout = true;
            }
        }
    });
    //导出excel
    var exportFn = function () {

        var url = 'json?action=EXPORT_ALARM_LOG_ACTION';

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
        url += '&DATA7=' + faultEventPanel.ht_outputStore.baseParams['DATA7'];
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
    var backPanel = new Ext.TabPanel({
        activeTab: 0,
        border: false,
        items: [{
            title: '详细报警报表',
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
