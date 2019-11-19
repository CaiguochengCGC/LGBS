(function () {
    var roleIdGridSelected;
    var queryType=[['','全部'],['1','冲压车间'],['2','车身车间']];
    var bancidata = [["1","1班"],["2","2班"]];
    var banciStore = new Ext.data.SimpleStore({
        auteLoad:true, //此处设置为自动加载
        data:bancidata,
        fields:["VALUE","TEXT"]
    });
    var workDate = [["1","是"],["0","否"]];
    var workDateStore = new Ext.data.SimpleStore({
        auteLoad:true, //此处设置为自动加载
        data:workDate,
        fields:["VALUE","TEXT"]
    });

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
    //获取所有生产线
    var lineStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_LINE.rs',
        baseParams : {
            action : 'QUERY_PMC_LINE_DATE'
        },
        fields : ['VALUE','TEXT']
    });
    lineStore.load({
        callback : function() {
            ht.pub.store.addBlankText(lineStore);
        }
    });
    //获取所有模板名称
    var modelName = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.MODEL_NAME.rs',
        baseParams : {
            action : 'QUERY_MODEL_NAME'
        },
        fields : ['VALUE','TEXT']
    });
    modelName.load({
        callback : function() {
            ht.pub.store.addBlankText(modelName);
        }
    });
    /**
     * 模板查询面板
     */
    var queryPanel = new ht.base.PropertyPanel({
        region: 'north',
        border: false,
        formConfig: {
            items: [
                {
                    xtype: 'datefield',
                    fieldLabel: '开始日期',
                    columnWidth : 1/4,
                    name: 'START_TIME',
                    format: ht.config.format.DATE,
                    value: new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
                },{
                    xtype : 'combo',
                    fieldLabel : '工段名字:',
                    store : lineComment,
                    emptyText : ht.msg.combo.emptyText,
                    mode : 'local',
                    triggerAction : 'all',
                    valueField : 'VALUE',
                    columnWidth : 1/4,
                    displayField : 'TEXT',
                    hiddenName : 'PRODUCTIONLINENAME',
                    name : 'PRODUCTIONLINENAME',
                    editable : false,
                    style : 'text-align: left'
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
            ctTopbarComponts: [{
                text: '一键新增',
                iconCls: 'add',
                enableByRowSelected: false,
                handler: function () {
                    //获取参数
                    var params = {};
                    params['START_TIME']=document.getElementsByName("START_TIME")[0].value;
                    params['action'] = 'ADD_NEW_WORKDATE';
                    Ext.Ajax.request({
                        url: 'json',
                        params: params,
                        success: function (response, options) {
                            debugger;
                            var test = JSON.parse(response.responseText);
                            var test1 = test.root.CALENDER_DATE.rs;
                            var result = Ext.decode(response.responseText)
                            if (result.success) {
                                for (var i = 0; i < test1.rs.length; i++) {
                                    calenderPanel.ht_outputStore.add(new Ext.data.Record(test.root.CALENDER_DATE.rs.rs[i]));
                                }
                            } else {
                                ht.pub.error(result.errors.errmsg);
                            }
                        }

                    });
                }
            }]
        },
        ctListeners: {
            beforeQuery: function (values) {

                calenderPanel.enableRelativePanelBarBtn(false);

                calenderPanel.enableBarBtn(false);
                calenderPanel.stopEditing();
                var roleInfoStore = calenderPanel.ht_outputStore;
                for (var field in values) {
                    roleInfoStore.baseParams[field] = values[field];
                }

                roleInfoStore.load();
                return false
            }
        }
    });

    /**
     * 日历信息
     */
    var calenderPanel = new ht.base.RowEditorPanel({
        region: 'center',
        // width: 750,
        queryFormConfig: {
            enableQueryButton: false,
            action: 'QUERY_PMC_CALENDAR',
            outputTable: 'CALENDER_DATE'
        },
        gridConfig: {
            columns: [new Ext.grid.RowNumberer(),
            {
                header: '工作日',
                dataIndex: 'Work_Date',
                width: 150,
                editor: {
                    xtype: 'textfield',
                    name: 'Work_Date',
                    maxLength: 10,
                    maxLengthText: ht.msg.valid.maxLengthText.replace('{0}', 10),
                    allowBlank: false,
                    regex:/^((?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)-02-29)$/
                }
            }
            , {
                    header: '生产线',
                    dataIndex: 'Line_Name',
                    width: 150,
                    editor: {
                        xtype : 'combo',
                        name : 'Line_Name',
                        stror:banciStore,
                        fieldLabel : '生产线',
                        store : lineStore,
                        mode : 'local',
                        triggerAction : 'all',
                        valueField : 'VALUE',
                        displayField : 'TEXT',
                        hiddenName : 'Line_Name',
                        editable : false,
                        allowBlank: false
                    }
                },
                {
                    header: '模板',
                    dataIndex: 'Templet_Name',
                    width: 150
                },
                {
                    header: '模板ID',
                    dataIndex: 'Model_Code',
                    width: 80
                },
                {
                    header: '班次',
                    dataIndex: 'Shift',
                    width: 80,
                    renderer: function (value) {
                        if (value == 1) {
                            return '1班';
                        }
                        if (value == 2) {
                            return '2班';
                        }

                    },
                    editor: {

                        xtype : 'combo',
                        name : 'Shift',
                        stror:banciStore,
                        fieldLabel : '班次',
                        store : banciStore,
                        mode : 'local',
                        triggerAction : 'all',
                        valueField : 'VALUE',
                        displayField : 'TEXT',
                        hiddenName : 'Shift',
                        editable : false,
                        allowBlank: false
                    }
                }, {
                    header: '是否为工作日',
                    dataIndex: 'Is_WorkDate',
                    width: 80,
                    renderer: function (value) {
                        if (value == 1) {
                            return '是';
                        }
                        if (value == 0) {
                            return '否';
                        }
                    },
                    editor: {
                        xtype : 'combo',
                        name : 'Is_WorkDate',
                        stror:workDateStore,
                        fieldLabel : '是否为工作日',
                        store : workDateStore,
                        mode : 'local',
                        triggerAction : 'all',
                        valueField : 'VALUE',
                        displayField : 'TEXT',
                        hiddenName : 'Is_WorkDate',
                        editable : false,
                        allowBlank: false
                    }
                }, {
                    header: '更新日期',
                    dataIndex: 'LastUpTime',
                    width: 150
                }
            ],

            storeFields: ['ID','Work_Date','Templet_Name', 'Model_Code','Work_Shop','Shift','Is_WorkDate','LastUpTime','Line_Name','Line_Code'],
            deletedKeyFields: ['ID'],
            isMultipleSelect: false,
            enablePageSize: false,
            updateBtn : {
                action : 'UPDATE_WORK_DATE_ACTION'
            }

        },
         ctListeners: {
             afterAdd: function (action) {
                 var table = calenderPanel.gridConfig.addBtn.outputTable;
                 var result = action.result.root[table].rs[0];
                 roleIdGridSelected = result['ID'];
                 return true;
             }
         },
         enableRelativePanelBarBtn: function (enable) {

             dateTmpBindInfo.enableBarBtn(false);
             if (!enable) {
                 dateTmpBindInfo.ht_outputStore.removeAll();
             } else {
                 var record = calenderPanel.ht_outputGrid.getSelectionModel().getSelected();
                 roleIdGridSelected = record.get('ID');
                 var rolePrivilegesStore = dateTmpBindInfo.ht_outputStore;
                 rolePrivilegesStore.baseParams['Model_Code'] = roleIdGridSelected;
                 rolePrivilegesStore.load();
                 var privilegesStore = privilegesPanel.ht_outputStore;
                 privilegesStore.baseParams['ID'] = roleIdGridSelected;

             }
         }
    });

    /**
     * 查询模板
     */
    var dateTmpBindInfo = new ht.base.PlainPanel({
        // region: 'center',
        region: 'south',
        // width: 750,

        height : 200,
        queryFormConfig: {
            enableQueryButton: false,
            action: 'QUERY_WORK_DATE_BIND_MODEL',
            outputTable: 'WORK_DATE_MODEL'
        },
        gridConfig: {
            columns: [new Ext.grid.RowNumberer(), {
                header: '模板ID ',
                dataIndex: 'Model_Code',
                width: 100
            },
                {
                header: '模板 ',
                dataIndex: 'Templet_Name',
                width: 180
            },
            {
                header: '班次',
                dataIndex: 'Shift',
                width: 100
            }, {
                header: '开班时间',
                dataIndex: 'WorkStart_Time',
                width: 100
            },{
                header: '结束时间',
                dataIndex: 'WorkEnd_Time',
                width: 100
            },{
                header: '休息阶段一',
                dataIndex: 'Rest_Time1',
                width: 100
            },{
                header: '休息阶段二',
                dataIndex: 'Rest_Time2',
                width: 100
            },{
                header: '休息阶段三',
                dataIndex: 'Rest_Time3',
                width: 100
            },{
                    header: '休息阶段四',
                    dataIndex: 'Rest_Time4',
                    width: 100
                },{
                    header: '休息阶段五',
                    dataIndex: 'Rest_Time5',
                    width: 100
                },{
                    header: '休息阶段六',
                    dataIndex: 'Rest_Time6',
                    width: 100
                },{
                    header: '休息阶段七',
                    dataIndex: 'Rest_Time7',
                    width: 100
                },{
                    header: '休息阶段八',
                    dataIndex: 'Rest_Time8',
                    width: 100
                },{
                    header: '休息阶段九',
                    dataIndex: 'Rest_Time9',
                    width: 100
                },{
                    header: '休息阶段十',
                    dataIndex: 'Rest_Time10',
                    width: 100
                }],

            storeFields: ['Model_Code','Work_Shop','Templet_Name', 'Shift', 'WorkStart_Time', 'WorkEnd_Time','Rest_Time1',
            'Rest_Time2','Rest_Time3','Rest_Time4','Rest_Time5','Rest_Time6','Rest_Time7',
                'Rest_Time8','Rest_Time9','Rest_Time10'],
            deletedKeyFields: ['Model_Code'],

          deleteBtn: {
                action: 'UPDATE_DATE_BIND',
                position: 9
            },

            ctTopbarComponts: [{
                text: ht.msg.base.addText,
                iconCls: 'add',
                enableByRowSelected: false,
                handler: function () {
                    privilegesPanel.enableBarBtn(false);
                    privilegesPanel.ht_outputStore.load();
                    privilegesWin.show();
                }
            }],

            excelConfig: {
                enableExport: false
            },
            printConfig: {
                enablePrintView: false,
                enablePrint: false
            }
        },
        ctListeners: {
            beforeDelete: function (values) {
                values['ID'] = roleIdGridSelected;
                values['CMD'] = 'D';
                return true;
            }
        }
    });

    /**
     *
     */
    var privilegesPanel = new ht.base.PlainPanel({
        border: true,
        region: 'center',
        queryFormConfig: {
            action: 'QUERY_ALL_DATE_TEMPLET',
            outputTable: 'ALL_TEMPLET_DATE',
            items: [ {
                xtype : 'combo',
                fieldLabel : '模板名称',
                store : modelName,
                emptyText : '模板名称',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'MODEL_NAME',
                name : 'MODEL_NAME',
                editable : false
            }],
            enableQueryButton : true
            // useTo: 'win-query'
        },
        gridConfig: {
            columns: [new Ext.grid.RowNumberer(), {
                header: '模板ID',
                dataIndex: 'Model_Code',
                width: 60
            }, {
                header: '模板名称',
                dataIndex: 'Templet_Name',
                width: 80
            }, {
                header: '班次',
                dataIndex: 'Shift',
                width: 60
            }, {
                header: '工作开始时间',
                dataIndex: 'WorkStart_Time',
                width: 100
            }, {
                header: '工作结束时间',
                dataIndex: 'WorkEnd_Time',
                width: 100
            }, {
                header: '休息时间阶段一',
                dataIndex: 'Rest_Time1',
                width: 100
            }, {
                header: '休息时间阶段二',
                dataIndex: 'Rest_Time2',
                width: 100
            }, {
                header: '休息时间阶段三',
                dataIndex: 'Rest_Time3',
                width: 100
            }, {
                header: '休息时间阶段四',
                dataIndex: 'Rest_Time4',
                width: 100
            }, {
                header: '休息时间阶段五',
                dataIndex: 'Rest_Time5',
                width: 100
            }, {
                header: '休息时间阶段六',
                dataIndex: 'Rest_Time6',
                width: 100
            }, {
                header: '休息时间阶段七',
                dataIndex: 'Rest_Time7',
                width: 100
            }, {
                header: '休息时间阶段八',
                dataIndex: 'Rest_Time8',
                width: 100
            }, {
                header: '休息时间阶段九',
                dataIndex: 'Rest_Time9',
                width: 100
            }, {
                header: '休息时间阶段十',
                dataIndex: 'Rest_Time10',
                width: 100
            }],

            storeFields: ['Model_Code', 'Work_Shop', 'Templet_Name', 'Shift',
            'WorkStart_Time','WorkEnd_Time','Rest_Time1','Rest_Time2','Rest_Time3',
            'Rest_Time4','Rest_Time5','Rest_Time6','Rest_Time7','Rest_Time8','Rest_Time9',
            'Rest_Time10'],

            excelConfig: {
                enableExport: false
            },
            printConfig: {
                enablePrintView: false,
                enablePrint: false
            }
        },
        ctListeners: {
            enableBarBtn: function (enable) {
                if (privilegesWin) {
                    ht.pub.enableCompentByRowSelected(privilegesWin.buttons, enable);
                }
            }
        }
    });

    /**
     * 权限窗口
     */
    var privilegesWin = new Ext.Window({
        width: 1300,
        height: 500,
        layout: 'fit',
        plain: true,
        closable: true,
        resizable: false,
        frame: true,
        constrain: true,
        closeAction: 'hide',
        border: false,
        modal: true,
        items: privilegesPanel,
        buttons: [{
            text: ht.msg.base.submitText,
            disabled: true,
            enableByRowSelected: true,
            handler: function () {

                //获取参数
                var params = {};
                params['ID'] = roleIdGridSelected;
                params['CMD'] = 'A';
                params['action'] = 'UPDATE_DATE_BIND';
                var records = privilegesPanel.ht_outputGrid.getSelectionModel().getSelections();
                for (var i = 0; i < records.length; i++) {
                    params['DATE_TEMPLET_ID'] = records[i].get('Model_Code');
                }
                if(records.length>1){
                    ht.pub.error('一个线体只允许添加一个模板');
                    return;
                }

                //提交
                Ext.Ajax.request({
                    url: 'json',
                    params: params,
                    success: function (response, options) {
                        var result = Ext.decode(response.responseText)
                        if (result.success) {

                            privilegesWin.hide();
                            ht.pub.info(ht.msg.base.addSuccessText);
                            var records = privilegesPanel.ht_outputGrid.getSelectionModel().getSelections();
                            for (var i = 0; i < records.length; i++) {
                                dateTmpBindInfo.ht_outputStore.add(new Ext.data.Record(records[i].data));
                            }
                        } else {
                            ht.pub.error(result.errors.errmsg);
                        }
                    },
                    failure: function (response, options) {
                        debugger;
                        ht.pub.handleAjaxErrors(response);
                    }
                });
            }
        }, {
            text: ht.msg.base.cancelText,
            enableByRowSelected: false,
            handler: function () {
                privilegesWin.hide();
            }
        }]
    })


    //返回面板
    var backPanel = new Ext.Panel({
        layout: 'border',
        border: false,
        items: [queryPanel, {
            region: 'center',
            xtype: 'panel',
            layout: 'border',
            border: false,
            items: [calenderPanel, dateTmpBindInfo]
        }],
        listeners: {
            beforedestroy: function () {

                privilegesWin.hide();
                privilegesWin.destroy();
                backPanel.removeAll(true);
            }
        }
    });

    calenderPanel.enableRelativePanelBarBtn(false);

    return backPanel;
})();
