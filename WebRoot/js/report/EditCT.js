/**
 * 编辑CT
 */
(function () {
    var queryType=[['','全部'],['1','冲压车间'],['2','车身车间']];
    var setPanelIsLayout;
    var stopLinePanel = new ht.base.RowEditorPanel({
        region: 'center',
        queryFormConfig: {
            enableQueryButton : true,
            items: [
                {
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
            action: 'QUERY_CT_ACTION',
            outputTable: 'CT',
            autoQuery: true
        },

        gridConfig: {
            columns: [new Ext.grid.RowNumberer(),
                {
                    header: '线体名称',
                    dataIndex: 'PRODUCTIONNAME',
                    width: 130
                }, {
                    header: '线体简称',
                    dataIndex: 'PRODUCTION',
                    width: 130
                }, {
                    header: 'JPH',
                    dataIndex: 'CT',
                    width: 130,
                    editor: {
                        xtype: 'textfield',
                        name: 'CT',
                        regex: /^[0-9]*$/
                    }
                }, {
                    header: 'EP21',
                    dataIndex: 'EP21',
                    width: 130,
                    editor: {
                        xtype: 'textfield',
                        name: 'EP21',
                        regex: /^[0-9]*$/
                    }
                }, {
                    header: 'ZP11',
                    dataIndex: 'ZP11',
                    width: 130,
                    editor: {
                        xtype: 'textfield',
                        name: 'ZP11',
                        regex: /^[0-9]*$/
                    }
                }, {
                    header: 'BP31',
                    dataIndex: 'BP31',
                    width: 130,
                    editor: {
                        xtype: 'textfield',
                        name: 'BP31',
                        regex: /^[0-9]*$/
                    }
                }, {
                    header: 'BP32',
                    dataIndex: 'BP32',
                    width: 130,
                    editor: {
                        xtype: 'textfield',
                        name: 'BP32',
                        regex: /^[0-9]*$/
                    }
                }, {
                    header: 'AS21',
                    dataIndex: 'AS21',
                    width: 130,
                    editor: {
                        xtype: 'textfield',
                        name: 'AS21',
                        regex: /^[0-9]*$/
                    }
                }, {
                    header: 'AS22',
                    dataIndex: 'AS22',
                    width: 130,
                    editor: {
                        xtype: 'textfield',
                        name: 'AS22',
                        regex: /^[0-9]*$/
                    }
                }, {
                    header: 'BP34',
                    dataIndex: 'BP34',
                    width: 130,
                    editor: {
                        xtype: 'textfield',
                        name: 'BP34',
                        regex: /^[0-9]*$/
                    }
                }, {
                    header: 'AS24',
                    dataIndex: 'AS24',
                    width: 130,
                    editor: {
                        xtype: 'textfield',
                        name: 'AS24',
                        regex: /^[0-9]*$/
                    }
                }, {
                    header: 'AS26',
                    dataIndex: 'AS26',
                    width: 130,
                    editor: {
                        xtype: 'textfield',
                        name: 'AS26',
                        regex: /^[0-9]*$/
                    }
                }, {
                    header: 'AS28',
                    dataIndex: 'AS28',
                    width: 130,
                    editor: {
                        xtype: 'textfield',
                        name: 'AS28',
                        regex: /^[0-9]*$/
                    }
                }],

            isPageAction: false,
            isMultipleSelect: false,
            storeFields: [
                'CT',
                'PRODUCTIONNAME',
                'PRODUCTION',
                'EP21',
                'ZP11',
                'BP31',
                'BP32',
                'AS21',
                'AS22',
                'BP34',
                'AS24',
                'AS26',
                'AS28'


            ],
            updateKeyFields: ['PRODUCTION'],
            //修改按钮
            updateBtn: {
                action: 'UPDATE_CT_ACTION',
                outputTable: '', //数据返回的output
                tagValue: '',
                position: 2
            }
        },
        ctListeners: {},
        listeners: {
            afterLayout: function () {
                setPanelIsLayout = true;
            }
        }
    });
    var backPanel = new Ext.Panel({
        border: false,
        layout: 'border',
        items: [stopLinePanel],
        listeners: {
            beforedestroy: function () {
                backPanel.removeAll(true);
            }
        }
    });

    return backPanel;
})();
