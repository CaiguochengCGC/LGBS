/**
 * 固定报表 -> 节拍报表
 */
(function () {
    var queryType=[['','全部'],['1','冲压车间'],['2','车身车间']];
    var setPanelIsLayout;
    //新增班次信息
    var bancidata = [["", "全部"], ["1", "1班"], ["2", "2班"]];
    var banciStore = new Ext.data.SimpleStore({
        auteLoad: true, //此处设置为自动加载
        data: bancidata,
        fields: ["VALUE", "TEXT"]
    });
//工位名称 联动查询
    var stationStore = new Ext.data.JsonStore({
        url : 'json',
        root : "root.PMC_ALM_STATION.rs",
        baseParams : {
            action : 'QUERY_BEAT_STATION_ACTION',
            CODE_TYPE : 'STATION',
            QURY_TYPE : 'null'
        },
        fields : ['VALUE','TEXT']
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

    //班次
    var shiftStore = new Ext.data.JsonStore({
        url: 'json',
        root: 'root.PUB_DATA_DICT.rs',
        baseParams: {
            action: 'GET_CODE_VALUE_ACTION',
            CODE_TYPE: 'SHIFT_TYPE'
        },
        fields: ['VALUE', 'TEXT']
    });
    shiftStore.load({
        callback: function () {
            ht.pub.store.addBlankText(shiftStore);
        }
    });

    var sectionStore = new Ext.data.JsonStore({
        url: 'json',
        root: 'root.PMC_PP_STATION.rs',
        baseParams: {
            action: 'QUERY_PMC_PP_STATION_CT_ALL_ACTION',
            CODE_TYPE: 'LINE_CHS'
        },
        fields: ['VALUE', 'TEXT']
    });
    sectionStore.load({
        callback: function () {
            ht.pub.store.addBlankText(sectionStore);
        }
    });
    //节拍日报表
    var beatDailyReportPanel = new ht.base.RowEditorPanel({
        region: 'center',
        queryFormConfig: {
            enableQueryButton: true,
            items: [{
                xtype: 'compositefield',
                fieldLabel: '生产日期',
                name: 'PLAN_DATE',
                columnWidth: 1 / 2,
                items: [{
                    xtype: 'datetimefield',
                    format: ht.config.format.DATETIME,
                    columnWidth: 1 / 2,
                    name: 'START_PLAN_DATE',
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
                    name: 'END_PLAN_DATE',
                    value: (new Date().getHours() > 7 && new Date().getHours() < 19) ? new Date(new Date().getTime() - ((new Date().getHours() - 19) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)) : ((new Date().getHours() < 8) ? new Date(new Date().getTime() - ((new Date().getHours() - 8) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)) : new Date(new Date().getTime() - ((new Date().getHours() - 32) * 60 * 60 * 1000 + new Date().getMinutes() * 60 * 1000 + new Date().getSeconds() * 1000)))
                }]
            }, {
                xtype: 'combo',
                fieldLabel: '工段名字：',
                store: lineComment,
                emptyText: ht.msg.combo.emptyText,
                mode: 'local',
                triggerAction: 'all',
                valueField: 'VALUE',
                displayField: 'TEXT',
                hiddenName: 'CT_CHANG',
                name: 'CT_CHANG',
                editable: false,
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
                hiddenName: 'CAR_TYPE',
                name: 'CAR_TYPE',
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
            },{
                xtype : 'combo',
                fieldLabel : '车间查询',
                store : queryType,
                emptyText : '全部车间',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'queryType',
                name : 'queryType',
                editable : false
            }],
            action: 'QUERY_TAB_CYCLE_TIME_ACTION',
            outputTable: 'TAB_CYCLE_TIME'
        },
        gridConfig: {
            columns: [new Ext.grid.RowNumberer(), {
                header: '时间',
                dataIndex: 'TIMESTAMP',
                width: 130,
                renderer: function (value, p, record) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header: '工段',
                dataIndex: 'LINE_ENG',
                width: 80
            }, {
                header: '工段名字',
                dataIndex: 'LINE_CHS',
                width: 120
            }, {
                header: '工位',
                dataIndex: 'STATION',
                width: 100
            }, {
                header: '车型',
                dataIndex: 'CAR_TYPE',
                width: 100
            }, {
                header: '班次',
                dataIndex: 'SHIFT',
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
                header: '工位CT（秒）',
                dataIndex: 'CT_TOTAL',
                width: 85
            }, {
                header: '人工时间（秒）',
                dataIndex: 'CT_MANUL',
                width: 95
            }, {
                header: '机运时间（秒）',
                dataIndex: 'CT_CONVR',
                width: 95
            }, {
                header: '工装时间（秒）',
                dataIndex: 'CT_EQUIP',
                width: 95
            }, {
                header: '机器人时间（秒）',
                dataIndex: 'CT_ROBOT',
                width: 100
            }, {
                header: '待料时间（秒）',
                dataIndex: 'CT_STARV',
                width: 95
            }, {
                header: '堵料时间（秒）',
                dataIndex: 'CT_BLOCK',
                width: 95
            }, {
                header: '故障时间（秒）',
                dataIndex: 'CT_FAULT',
                width: 95
            }
            ],
            isPageAction: true,
            isMultipleSelect: false,
            storeFields: ['TIMESTAMP', 'LINE_ENG', 'LINE_CHS', 'CAR_TYPE', 'STATION', 'SHIFT'
                , 'CT_CHANG', 'CT_BLOCK', 'CT_ROBOT', 'CT_CONVR', 'CT_TOTAL'
                , 'CT_EQUIP', 'CT_STARV', 'CT_MANUL', 'CT_FAULT', 'CT_PRESS'],
            ctTopbarComponts: [{
                iconCls: 'excel',
                text: '导出',
                enableByRowSelected: false,
                enableByHasData: true,
                tagValue: '',
                position: 3,
                handler: function () {
                    BexportFn();
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
    var BexportFn = function () {
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
        url += '&START_PLAN_DATE=' + beatDailyReportPanel.ht_outputStore.baseParams['START_PLAN_DATE'];
        url += '&END_PLAN_DATE=' + beatDailyReportPanel.ht_outputStore.baseParams['END_PLAN_DATE'];
        url += '&BANCI=' + beatDailyReportPanel.ht_outputStore.baseParams['BANCI'];
        url += encodeURI(encodeURI('&EventDate14=' + beatDailyReportPanel.ht_outputStore.baseParams['EventDate14']));
        url += '&CT_CHANG=' + beatDailyReportPanel.ht_outputStore.baseParams['CT_CHANG'];
        url += '&CAR_TYPE=' + beatDailyReportPanel.ht_outputStore.baseParams['CAR_TYPE'];
        url += '&queryType=' + beatDailyReportPanel.ht_outputStore.baseParams['queryType'];
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        BexportPanel.setSrc(url);
    }
    //导出面板
    var BexportPanel = new Ext.ux.ManagedIFramePanel({
        region: 'east',
        width: 0,
        border: true,
        bodyBorder: false,
        autoScroll: true,
        autoLoad: false,
        defaultSrc: null
    });
    //导出excel
    var exportFn = function () {
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
        url += '&START_PLAN_DATE=' + tableStationSummaryPanel.ht_outputStore.baseParams['START_PLAN_DATE'];
        url += '&END_PLAN_DATE=' + tableStationSummaryPanel.ht_outputStore.baseParams['END_PLAN_DATE'];
        url += '&EventData=' + tableStationSummaryPanel.ht_outputStore.baseParams['EventData'];
        url += encodeURI(encodeURI('&EventDate14=' + tableStationSummaryPanel.ht_outputStore.baseParams['EventDate14']));
        url += '&EventDate1=' + tableStationSummaryPanel.ht_outputStore.baseParams['EventDate1'];
        url += '&EventDate2=' + tableStationSummaryPanel.ht_outputStore.baseParams['EventDate2'];
        url += '&BANCI=' + tableStationSummaryPanel.ht_outputStore.baseParams['BANCI'];
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
            title: '节拍报表',
            border: false,
            layout: 'border',
            items: [beatDailyReportPanel, BexportPanel]
        }],
        listeners: {
            beforedestroy: function () {
                shiftStore.destroy();//班次
                sectionStore.destroy();//工段
                backPanel.removeAll(true);
            }
        }
    });
    return backPanel;
})();
