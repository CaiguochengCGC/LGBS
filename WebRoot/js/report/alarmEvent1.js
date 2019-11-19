/**
 * 固定报表 -> 节拍报表
 */
(function () {
    var queryType=[['','全部'],['1','冲压车间'],['2','车身车间']];
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
//获得停线原因
    var stopReason = new Ext.data.JsonStore({
        url : 'json',
        root : "root.STOP_REASON.rs",
        baseParams : {
            action : 'QUERY_STOP_REASON'
        },
        fields : ['VALUE','TEXT']
    });

    stopReason.load({
        callback: function () {
            ht.pub.store.addBlankText(stopReason);
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
                xtype: 'combo',
                fieldLabel: '工段名字',
                store: lineComment,
                emptyText: ht.msg.combo.emptyText,
                mode: 'local',
                columnWidth: 1 / 4,
                triggerAction: 'all',
                valueField: 'VALUE',
                displayField: 'TEXT',
                hiddenName: 'PRODUCTIONLINENAME',
                name: 'PRODUCTIONLINENAME',
                editable: false
            }, {
                xtype: 'combo',
                fieldLabel: '停线原因',
                store: stopReason,
                emptyText: ht.msg.combo.emptyText,
                mode: 'local',
                columnWidth: 1 / 4,
                triggerAction: 'all',
                valueField: 'VALUE',
                displayField: 'TEXT',
                hiddenName: 'ATTRBUTE',
                name: 'ATTRBUTE',
                editable: false
            }, {
                xtype: 'combo',
                fieldLabel: '班次',
                store: banciStore,
                emptyText: '全部班次',
                columnWidth: 1 / 4,
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
            action: 'QUERY_PP_ALARM_LOG_ACTION',
            outputTable: 'ALARM_LOG'
        },
        gridConfig: {
            columns: [new Ext.grid.RowNumberer(), {
                header: '工段名字',
                dataIndex: 'PRODUCTIONNAME',
                width: 130
            }, {
                header: '工位',
                dataIndex: 'STATION',
                width: 70
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
                header: '停线开始时间',
                dataIndex: 'STOPSTARTTIME',
                width: 140,
                renderer: function (value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header: '停线结束时间',
                dataIndex: 'STOPENDTIME',
                width: 140,
                renderer: function (value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header: '停线历时（秒）',
                dataIndex: 'STOPTIME',
                width: 110
            }
                /* , {
                     header: '停线原因',
                     dataIndex: 'STOPRESON',
                     width: 80
                 }, {
                     header: '属性',
                     dataIndex: 'ATTRBUTE',
                     width: 100
                 }*/
                , {
                    header: '停线原因',
                    dataIndex: 'ATTRBUTE',
                    width: 100
                }, {
                    header: '报警开始时间',
                    dataIndex: 'ALARMSTARTTIME',
                    width: 140,
                    renderer: function (value) {
                        return ht.pub.date.dateTimeRenderer(value);
                    }
                }, {
                    header: '报警结束时间',
                    dataIndex: 'ALARMENDTIME',
                    width: 140,
                    renderer: function (value) {
                        return ht.pub.date.dateTimeRenderer(value);
                    }
                }, {
                    header: '报警历时（秒）',
                    dataIndex: 'ALARMTIME',
                    width: 130
                }, {
                    header: '设备',
                    dataIndex: 'EQUIPMENT',
                    width: 80
                }, {
                    header: '报警详细内容',
                    dataIndex: 'ALARMMSG',
                    width: 300
                }],
            isPageAction: true,
            isMultipleSelect: false,
            storeFields: ['PRODUCTION', 'PRODUCTIONNAME', 'STATION', 'STOPSTARTTIME', 'STOPENDTIME', 'STOPTIME', 'STOPRESON', 'ALARMSTARTTIME', 'ALARMENDTIME', 'ALARMTIME', 'ALARMMSG', 'EQUIPMENT', 'ATTRBUTE', 'BANCI', 'STATION'],
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
        var url = 'json?action=EXPORT_PP_ALARM_LOG_ACTION';
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
        url += '&BANCI=' + faultEventPanel.ht_outputStore.baseParams['BANCI'];
        url += '&queryType=' + faultEventPanel.ht_outputStore.baseParams['queryType'];
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + faultEventPanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
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
            title: '工段停线与报警对应',
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
