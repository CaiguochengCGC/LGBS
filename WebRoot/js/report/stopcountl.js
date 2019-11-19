/**
 * 报警次数
 */
(function() {

    var queryType=[['','全部'],['1','冲压车间'],['2','车身车间']];
    var SHIFTDATA = [["","全部"],["1","1班"],["2","2班"]];
    var SHIFTSTORE = new Ext.data.SimpleStore({
        auteLoad:true,
        data:SHIFTDATA,
        fields:["VALUE","TEXT"]
    });
    //报警原因(类别)
    var stopReson = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.STOP_RESON.rs',
        baseParams : {
            action : 'QUERY_STOP_COUNT_ACTION'
        },
        fields : ['VALUE','TEXT']
    });
    stopReson.load({
        callback : function() {
            ht.pub.store.addBlankText(stopReson);
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

    var queryPanel = new ht.base.PropertyPanel({
        region: 'north',
        border: false,
        formConfig: {
            items : [{
                xtype: 'compositefield',
                fieldLabel: '时间',
                name: 'EFFECT_TIME',
                columnWidth: 1 / 2,
                items: [{
                    xtype : 'datefield',
                    fieldLabel : '日期',
                    name : 'START_TIME',
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
                    name : 'END_TIME',
                    columnWidth : 1/2,
                    format : ht.config.format.DATE,
                    value : new Date(new Date().getTime())
                }]
            }, {
                xtype : 'combo',
                fieldLabel : '工段名字',
                store : lineComment,
                mode : 'local',
                columnWidth : 1/4,
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'LINECODE',
                name : 'LINECODE',
                editable : false,
                listeners:{
                    select : function(combo, record,index){
                        stationStore.value = "";
                        stationStore.baseParams.QURY_TYPE=combo.value;
                        stationStore.load();

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
                hiddenName: 'STATION',
                name: 'STATION',
                editable: true
            },{
                xtype : 'combo',
                fieldLabel : '报警类别',
                store : stopReson,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                columnWidth : 1/4,
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'STOPTYPE',
                name : 'STOPTYPE',
                editable : false
            },{
                xtype : 'combo',
                columnWidth : 1/4,
                fieldLabel : '班次',
                store : SHIFTSTORE,
                emptyText : '全部班次',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'SHIFT',
                name : 'SHIFT',
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
            }]
        },
        ctListeners: {
            beforeQuery: function (values) {

                STOPDETAILPANEL.enableRelativePanelBarBtn(false);

                STOPDETAILPANEL.enableBarBtn(false);
                // STOPDETAILPANEL.stopEditing();
                var roleInfoStore = STOPDETAILPANEL.ht_outputStore;
                for (var field in values) {
                    roleInfoStore.baseParams[field] = values[field];
                }

                roleInfoStore.load();
                return false
            }
        }
    });
    //报警次数详情
    var STOPDETAILPANEL = new ht.base.PlainPanel({

        region: 'west',
        width: 750,
        queryFormConfig : {
            enableQueryButton : false,
            action : 'QUERY_STOP_COUNT_RESON_TABLE_ACTION',
            outputTable : 'PMC_STOP_RESON_PLANEL'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),  {
                header : '工段名字',
                dataIndex : 'LINENAME',
                width : 160
            },{
                header : '工作日',
                dataIndex : 'WORKDATE',
                width : 120
            },{
                header : '班次',
                dataIndex : 'SHIFT',
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
            },{
                header : '报警次数',
                dataIndex : 'STOPCOUNT',
                width : 120
            },{
                header : '报警工位',
                dataIndex : 'STATION',
                width : 120
            },{
                header : '报警类别',
                dataIndex : 'STOPRESON',
                width : 120
            }],

            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['STOPCOUNT','WORKDATE','STOPTIME','LINENAME','LINECODE','STOPRESON','SHIFT','STATION','TYPE'],
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
        /*ctListeners : {
            beforeQuery :function(values){
                return true;
            }

        }*/
        enableRelativePanelBarBtn: function (enable) {

            dateTmpBindInfo.enableBarBtn(false);
            if (!enable) {
                dateTmpBindInfo.ht_outputStore.removeAll();
            } else {
                var record = STOPDETAILPANEL.ht_outputGrid.getSelectionModel().getSelected();
                var lineName = record.get('LINENAME');
                var workTime = record.get('WORKDATE');
                var shift = record.get('SHIFT');
                var stopType = record.get('STOPRESON');
                var stopStation=record.get('STATION');
                var queryType=record.get('TYPE');
                var rolePrivilegesStore = dateTmpBindInfo.ht_outputStore;
                rolePrivilegesStore.baseParams['lineName'] = lineName;
                rolePrivilegesStore.baseParams['workTime'] = workTime;
                rolePrivilegesStore.baseParams['shift'] = shift;
                rolePrivilegesStore.baseParams['stopType'] = stopType;
                rolePrivilegesStore.baseParams['stopStation'] = stopStation;
                rolePrivilegesStore.baseParams['queryType'] = queryType;
                rolePrivilegesStore.load();

            }
        }
    });

    var dateTmpBindInfo = new ht.base.PlainPanel({
        region: 'center',

        height: 200,
        queryFormConfig: {
            enableQueryButton: false,
            action: 'QUERY_STOP_COUNT_REASON_DETAILED_TABLE_ACTION',
            outputTable: 'STOP_DETAILED'
        },
        gridConfig: {
            columns: [new Ext.grid.RowNumberer(),
                {
                    header: '工段名字',
                    dataIndex: 'lineName',
                    width: 100
                }, {
                    header: '班次',
                    dataIndex: 'shift',
                    width: 100,
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
                },{
                    header: '报警工位',
                    dataIndex: 'stopStation',
                    width: 100
                }, {
                    header: '报警类别',
                    dataIndex: 'stopType',
                    width: 140
                },  {
                    header: '开始时间 ',
                    dataIndex: 'startTime',
                    width: 140,
                    renderer: function (value) {
                        return ht.pub.date.dateTimeRenderer(value);
                    }
                },
                {
                    header: '结束时间 ',
                    dataIndex: 'endTime',
                    width: 140,
                    renderer: function (value) {
                        return ht.pub.date.dateTimeRenderer(value);
                    }
                }, {
                    header: '报警时间',
                    dataIndex: 'stopTime',
                    width: 100
                }],

            storeFields: ['lineName', 'shift', 'startTime', 'endTime', 'stopType', 'stopTime','stopStation'],
            excelConfig: {
                enableExport: false
            },
            printConfig: {
                enablePrintView: false,
                enablePrint: false
            }
        }
    });
    //导出excel
    var exportFn = function(){
        var url = 'json?action=EXPORT_PMC_STOP_COUNT_DETAIL_ACTION';

        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();

        var columns = STOPDETAILPANEL.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }

            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }

        url += '&START_TIME=' + STOPDETAILPANEL.ht_outputStore.baseParams['START_TIME'];
        url += '&END_TIME=' + STOPDETAILPANEL.ht_outputStore.baseParams['END_TIME'];
        url += '&LINECODE=' + STOPDETAILPANEL.ht_outputStore.baseParams['LINECODE'];
        url += '&STOPTYPE=' + STOPDETAILPANEL.ht_outputStore.baseParams['STOPTYPE'];
        url += '&STATION=' + STOPDETAILPANEL.ht_outputStore.baseParams['STATION'];
        url += '&SHIFT=' + STOPDETAILPANEL.ht_outputStore.baseParams['SHIFT'];
        url += '&queryType=' + STOPDETAILPANEL.ht_outputStore.baseParams['queryType'];
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

    //日报表图
    var piePanel = new Ext.Panel({
        title : '报警详情报表',
        region : 'south',
        height : 350,
        border : true,
        items: {
            xtype: 'linechart',
            border : false,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            store:  STOPDETAILPANEL.ht_outputStore,
            xField: 'WORKDATE',
            yAxes : [new Ext.chart.NumericAxis({
                alwaysShowZero : true,
                calculateByLabelSize : true,
                order : 'primary',
                labelRenderer : Ext.util.Format.numberRenderer('000.00'),
                position : 'right'
            }), new Ext.chart.NumericAxis({
                order : 'secondary',
                labelRenderer : Ext.util.Format.numberRenderer('00.00'),
                position : 'left',
                title : '报警次数',
                alwaysShowZero : true
            })],

            series : [{
                type: 'line',
                margins : '0',
                yField: 'STOPCOUNT',
                displayName: '报警次数',
                axis : 'primary',
                style: {
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0x50b7c1
                }
            }],
            chartStyle : {
                padding : 10,
                animationEnabled : true,
                dataTip : {
                    padding : 5,
                    border : {
                        color : 0x99bbe8,
                        size : 1
                    },
                    background : {
                        color : 0xDAE7F6,
                        alpha : .9
                    },
                    font : {
                        name : 'Tahoma',
                        color : 0x15428B,
                        size : 10,
                        bold : true
                    }
                },
                font : {
                    name : 'Tahoma',
                    color : 0x444444,
                    size : 11
                },
                legend: {
                    display: 'botton',
                    border: {
                        color: 0x99bbe8,
                        size:1
                    },
                    font : {
                        size : 12
                    }
                },
                yAxis : {
                    labelDistance : 0,
                    titleRotation : -90,
                    titleFont : {
                        color : 0x69aBc8
                    },
                    color : 0x69aBc8,
                    majorTicks : {
                        color : 0x69aBc8,
                        length : 4
                    },
                    minorTicks : {
                        color : 0x69aBc8,
                        length : 2
                    },
                    majorGridLines : {
                        size : 1,
                        color : 0xdfe8f6
                    }
                },
                secondaryYAxis : {
                    titleRotation : 90,
                    color : 0x69aBc8,
                    titleFont : {
                        color : 0x69aBc8
                    }
                },
                xAxis : {
                    labelRotation : -90,
                    labelRenderer : function(v){
                        return v;
                    },
                    color : 0x69aBc8,
                    majorTicks : {
                        color : 0x69aBc8,
                        length : 4
                    },
                    minorTicks : {
                        color : 0x69aBc8,
                        length : 2
                    },
                    majorGridLines : {
                        size : 1,
                        color : 0xeeeeee
                    },
                    labelRotation : -30,
                    labelRenderer : function(v){
                        return v;
                    }
                }
            },

            listeners : {
                itemclick : function(o) {
                },
                beforerender : function(comp){
                    comp.chartStyle.dataTip.font.size = 12;
                }
            },
            tipRenderer : function(chart, record, i, series){
                return record.get(chart.xField) + ': ' + record.get(series.yField);
            }
        }
    });

    var backPanel = new Ext.TabPanel({
        activeTab: 0,
        border : false,
        items : [{
            title : '日报表',
            border : false,
            layout : 'border',
            items : [queryPanel,STOPDETAILPANEL,dateTmpBindInfo, piePanel,exportPanel]
        }
        ],
        listeners : {
            beforedestroy : function(){
                backPanel.removeAll(true);
            }
        }
    });
    STOPDETAILPANEL.enableRelativePanelBarBtn(false);
    return backPanel;
})();
