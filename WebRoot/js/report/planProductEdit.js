// 计划产量录入界面

(function () {
//班次
    var ShiftData = [["", "全部"], ["1", "1班"], ["2", "2班"]];
    var shiftStore = new Ext.data.SimpleStore({
        autoLoad: true,
        data: ShiftData,
        fields: ["VALUE", "TEXT"]
    });
    //零时线体
    var LineData = [["", "全部"], ["RL1", "补焊线1"]];
    var LineDataStore = new Ext.data.SimpleStore({
        autoLoad: true,
        data: LineData,
        fields: ["VALUE", "TEXT"]
    });
    var backPanel = new ht.base.RowEditorPanel({
        queryFormConfig: {
            enableQueryButton: true,
            items: [{
                xtype: 'datefield',
                fieldLabel: '日期',
                width: 300,
                name: 'PlanDate',
                format: ht.config.format.DATE,
                value: new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
            }, {
                xtype: 'combo',
                fieldLabel: '班次：',
                store: shiftStore,
                emptyText: ht.msg.combo.emptyText,
                mode: 'local',
                triggerAction: 'all',
                valueField: 'VALUE',
                displayField: 'TEXT',
                hiddenName: 'ShiftText',
                name: 'ShiftText',
                editable: false,
            }, {
                xtype: 'combo',
                fieldLabel: '工段名字：',
                store: LineDataStore,
                emptyText: ht.msg.combo.emptyText,
                mode: 'local',
                triggerAction: 'all',
                valueField: 'VALUE',
                displayField: 'TEXT',
                hiddenName: 'LineName',
                name: 'LineName',
                editable: false,
                listeners: {
                    select: function (c) {
                        beatDailyReportPanel.findQueryCompment('LineName').setValue(null);
                    }
                }
            }
            ],

            action: 'QUERY_PLAN_PRODUCT_INFO',
            outputTable: 'PmcPlanEdit',
        }
        ,
        gridConfig: {
            columns: [new Ext.grid.RowNumberer(),
                {
                    header: 'ID',
                    dataIndex: 'ID',
                    width: 200,
                    editor: {
                        xtype: 'textfield',
                        name: 'ID',
                        updateable: false
                    }
                }, {
                    header: '时间',
                    dataIndex: 'PPDATE',
                    width: 180,
                    hidden: false,
                    editor: {
                        xtype: 'textfield',
                        name: 'PPDATE',
                        updateable: false
                    },
                    renderer: function (value) {
                        return ht.pub.date.dateTimeRenderer(value);
                    }
                }, {
                    header: '线体简称',
                    dataIndex: 'LineCode',
                    width: 150,
                    editor: {
                        xtype: 'textfield',
                        name: 'LineCode',
                        updateable: false
                    }
                },
                {
                    header: '线体名',
                    dataIndex: 'LineName',
                    width: 300,
                    editor: {
                        xtype: 'textfield',
                        name: 'LineName',
                        updateable: false
                    }
                }, {
                    header: '实际产量',
                    dataIndex: 'ActProduct',
                    width: 90,
                    editor: {
                        xtype: 'textfield',
                        name: 'ActProduct',
                        updateable: false
                    }
                }
                ,
                {
                    header: '计划产量',
                    dataIndex: 'PlanProduct',
                    width: 90,
                    editor: {
                        xtype: 'textfield',
                        name: 'PlanProduct',
                        updateable: true
                    }
                }, {
                    header: '班次',
                    dataIndex: 'Shift',
                    width: 250,
                    editor: {
                        xtype: 'textfield',
                        name: 'Shift',
                        updateable: false
                    }
                }, {
                    header: '车型',
                    dataIndex: 'CarType',
                    width: 250,
                    editor: {
                        xtype: 'textfield',
                        name: 'CarType',
                        updateable: false
                    }
                }
            ],
            isPageAction: false,
            isMultipleSelect: false,
            storeFields: ['ID', 'PPDATE', 'LineCode', 'LineName', 'PlanProduct', 'ActProduct', 'Shift', 'CarType'],

            updateBtn: {
                action: 'UPDATE_PLAN_PRODUCT_ACTION'
            },
        }
    });
    return backPanel;
})();
