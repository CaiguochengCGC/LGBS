(function () {
    var roleIdGridSelected;
    var queryType = [['', '全部'], ['1', '冲压车间'], ['2', '车身车间']];
    var bancidata = [["1", "1班"], ["2", "2班"]];
    var banciStore = new Ext.data.SimpleStore({
        auteLoad: true, //此处设置为自动加载
        data: bancidata,
        fields: ["VALUE", "TEXT"]
    });

    var lineComment = new Ext.data.JsonStore({
        url: 'json',
        root: "root.GET_COMMENT.rs",
        baseParams: {
            action: 'QUERY_LINECODE_COMMENT',
            CODE_TYPE: 'COMMENT'
        },
        fields: ['VALUE', 'TEXT']
    });
    lineComment.load({
        callback: function () {
            ht.pub.store.addBlankText(lineComment);
        }
    });

    /**
     * 查询条件面板
     */
    var queryPanel = new ht.base.PropertyPanel({
        region: 'north',
        border: false,
        formConfig: {
            items: [
                {
                    xtype: 'datefield',
                    fieldLabel: '日期',
                    name: 'YYYY',
                    format: 'Y',
                    value: new Date()
                }, {
                    xtype: 'combo',
                    fieldLabel: '线体',
                    store: lineComment,
                    emptyText: ht.msg.combo.emptyText,
                    mode: 'local',
                    triggerAction: 'all',
                    valueField: 'VALUE',
                    displayField: 'TEXT',
                    hiddenName: 'PRODUCTIONLINENAME',
                    name: 'PRODUCTIONLINENAME',
                    editable: false,
                    style: 'text-align: left'
                }, {
                    xtype: 'combo',
                    fieldLabel: '车间查询',
                    store: queryType,
                    emptyText: '全部车间',
                    mode: 'local',
                    triggerAction: 'all',
                    valueField: 'VALUE',
                    displayField: 'TEXT',
                    hiddenName: 'queryType',
                    name: 'queryType',
                    editable: false
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
                return false;
            }
        }
    });

    /**
     * 上面板，开动率信息面板
     */
    var calenderPanel = new ht.base.RowEditorPanel({
        region: 'center',
        // width: 750,
        queryFormConfig: {
            enableQueryButton: false,
            action: 'QUERY_PMC_WeekDay_StartRate',
            outputTable: 'weekDayStartRateR'
        },
        gridConfig: {
            columns: [new Ext.grid.RowNumberer(), {
                header: '工段',
                dataIndex: 'PRODUCTIONLINE',
                width: 120
            }, {
                header: '线体',
                dataIndex: 'PRODUCTIONLINENAME',
                width: 160
            }, {
                header: '班次',
                dataIndex: 'BANCI',
                width: 60
            }, {
                header: '区域',
                dataIndex: 'quyu',
                width: 60,
                editor: {
                    xtype: 'textfield',
                    name: 'quyu'
                }
            }, {
                header: '第一周',
                dataIndex: 'DATA1',
                width: 80
            }, {
                header: '第二周',
                dataIndex: 'DATA2',
                width: 80
            }, {
                header: '第三周',
                dataIndex: 'DATA3',
                width: 80
            }, {
                header: '第四周',
                dataIndex: 'DATA4',
                width: 80
            }, {
                header: '第五周',
                dataIndex: 'DATA5',
                width: 80
            }, {
                header: '第六周',
                dataIndex: 'DATA6',
                width: 80
            }, {
                header: '第七周',
                dataIndex: 'DATA7',
                width: 80
            }, {
                header: '第八周',
                dataIndex: 'DATA8',
                width: 80
            }, {
                header: '第九周',
                dataIndex: 'DATA9',
                width: 80
            }, {
                header: '第十周',
                dataIndex: 'DATA10',
                width: 80
            }, {
                header: '第十一周',
                dataIndex: 'DATA11',
                width: 80
            }, {
                header: '第十二周',
                dataIndex: 'DATA12',
                width: 80
            }, {
                header: '第十三周',
                dataIndex: 'DATA13',
                width: 80
            }, {
                header: '第十四周',
                dataIndex: 'DATA14',
                width: 80
            }, {
                header: '第十五周',
                dataIndex: 'DATA15',
                width: 80
            }, {
                header: '第十六周',
                dataIndex: 'DATA16',
                width: 80
            }, {
                header: '第十七周',
                dataIndex: 'DATA17',
                width: 80
            }, {
                header: '第十八周',
                dataIndex: 'DATA18',
                width: 80
            }, {
                header: '第十九周',
                dataIndex: 'DATA19',
                width: 80
            }, {
                header: '第二十周',
                dataIndex: 'DATA20',
                width: 80
            }, {
                header: '第二十一周',
                dataIndex: 'DATA21',
                width: 80
            }, {
                header: '第二十二周',
                dataIndex: 'DATA22',
                width: 80
            }, {
                header: '第二十三周',
                dataIndex: 'DATA23',
                width: 80
            }, {
                header: '第二十四周',
                dataIndex: 'DATA24',
                width: 80
            }, {
                header: '第二十五周',
                dataIndex: 'DATA25',
                width: 80
            }, {
                header: '第二十六周',
                dataIndex: 'DATA26',
                width: 80
            }, {
                header: '第二十七周',
                dataIndex: 'DATA27',
                width: 80
            }, {
                header: '第二十八周',
                dataIndex: 'DATA28',
                width: 80
            }, {
                header: '第二十九周',
                dataIndex: 'DATA29',
                width: 80
            }, {
                header: '第三十周',
                dataIndex: 'DATA30',
                width: 80
            }, {
                header: '第三十一周',
                dataIndex: 'DATA31',
                width: 80
            }, {
                header: '第三十二周',
                dataIndex: 'DATA32',
                width: 80
            }, {
                header: '第三十三周',
                dataIndex: 'DATA33',
                width: 80
            }, {
                header: '第三十四周',
                dataIndex: 'DATA34',
                width: 80
            }, {
                header: '第三十五周',
                dataIndex: 'DATA35',
                width: 80
            }, {
                header: '第三十六周',
                dataIndex: 'DATA36',
                width: 80
            }, {
                header: '第三十七周',
                dataIndex: 'DATA37',
                width: 80
            }, {
                header: '第三十八周',
                dataIndex: 'DATA38',
                width: 80
            }, {
                header: '第三十九周',
                dataIndex: 'DATA39',
                width: 80
            }, {
                header: '第四十周',
                dataIndex: 'DATA40',
                width: 80
            }, {
                header: '第四十一周',
                dataIndex: 'DATA41',
                width: 80
            }, {
                header: '第四十二周',
                dataIndex: 'DATA42',
                width: 80
            }, {
                header: '第四十三周',
                dataIndex: 'DATA43',
                width: 80
            }, {
                header: '第四十四周',
                dataIndex: 'DATA44',
                width: 80
            }, {
                header: '第四十五周',
                dataIndex: 'DATA45',
                width: 80
            }, {
                header: '第四十六周',
                dataIndex: 'DATA46',
                width: 80
            }, {
                header: '第四十七周',
                dataIndex: 'DATA47',
                width: 80
            }, {
                header: '第四十八周',
                dataIndex: 'DATA48',
                width: 80
            }, {
                header: '第四十九周',
                dataIndex: 'DATA49',
                width: 80
            }, {
                header: '第五十周',
                dataIndex: 'DATA50',
                width: 80
            }, {
                header: '第五十一周',
                dataIndex: 'DATA51',
                width: 80
            }, {
                header: '第五十二周',
                dataIndex: 'DATA52',
                width: 80
            }],
            isPageAction: false,
            isMultipleSelect: false,
            storeFields: ['ID', 'PRODUCTIONLINE', 'PRODUCTIONLINENAME', 'DATA1', 'DATA2', 'DATA3', 'DATA4', 'DATA5',
                'DATA6', 'DATA7', 'DATA8', 'DATA9', 'DATA10', 'DATA11', 'DATA12'
                , 'DATA13', 'DATA14', 'DATA15', 'DATA16', 'DATA17', 'DATA18', 'DATA19',
                'DATA20', 'DATA21', 'DATA22', 'DATA23', 'DATA24', 'DATA25', 'DATA26', 'DATA27'
                , 'DATA28', 'DATA29', 'DATA30', 'DATA31',
                'DATA32', 'DATA33', 'DATA34', 'DATA35', 'DATA36', 'DATA37', 'DATA38', 'DATA39',
                'DATA40', 'DATA41', 'DATA42', 'DATA43', 'DATA44', 'DATA45', 'DATA46', 'DATA47',
                'DATA48', 'DATA49', 'DATA50', 'DATA51', 'DATA52'
                , 'BANCI', 'quyu', 'YYYY_MM'],
            ctTopbarComponts: [{
                iconCls: 'excel',
                text: '导出',
                enableByRowSelected: false,
                enableByHasData: true,
                tagValue: '',
                position: 3,
                handler: function () {
                    exportFnUP();
                }
            }],
            updateKeyFields: ['ID'],
            updateBtn: {
                action: 'UPDATE_W_Rate_Note_ACTION'
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
                rolePrivilegesStore.baseParams['ID'] = roleIdGridSelected;
                rolePrivilegesStore.load();
            }
        }
    });
    /**
     * 导出上面板
     */
    var exportFnUP = function () {
        var url = 'json?action=QUERY_PMC_WeekDay_StartRate_Excle';

        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();

        var columns = calenderPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }

            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }

        url += '&YYYY=' + calenderPanel.ht_outputStore.baseParams['YYYY'];
        /* url += '&BANCI=' + calenderPanel.ht_outputStore.baseParams['BANCI'];
         url += '&queryType=' + calenderPanel.ht_outputStore.baseParams['queryType'];*/

        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');

        exportPanel.setSrc(url);
    };
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

    /**
     * 下面板，用户添加开动率备注信息面板
     */
    var dateTmpBindInfo = new ht.base.RowEditorPanel({
        // region: 'center',
        region: 'south',
        width: 750,
        height: 200,
        queryFormConfig: {
            enableQueryButton: false,
            //QUERY_PMC_WeekDay_StartRate_note
            action: 'QUERY_PMC_WeekDay_StartRate_note',
            outputTable: 'weekDayStartRateNoteR'
        },
        gridConfig: {
            columns: [new Ext.grid.RowNumberer(),
                {
                    header: 'ID ',
                    hidden: true,
                    dataIndex: 'ID',
                    width: 100
                },
                {
                    header: '周次 ',
                    dataIndex: 'WEEKL',
                    width: 100,
                    editor: {
                        xtype: 'textfield',
                        name: 'WEEKL',
                        emptyText: '周次',
                        allowBlank: false
                    }
                },
                {
                    header: '发生日期 ',
                    dataIndex: 'WORKSTARTTIME',
                    width: 180,
                    editor: {
                        xtype: 'textfield',
                        name: 'WORKSTARTTIME',
                        emptyText: '0000/00/00(年:月:日)'
                    }
                },
                {
                    header: '工段',
                    dataIndex: 'WORKSECTION',
                    width: 100,
                    editor: {
                        xtype: 'textfield',
                        name: 'WORKSECTION',
                        emptyText: '工段'
                    }
                },
                {
                    header: '区域',
                    dataIndex: 'AREA',
                    width: 100,
                    editor: {
                        xtype: 'textfield',
                        name: 'AREA',
                        emptyText: '区域'
                    }
                },
                {
                    header: '工位',
                    dataIndex: 'STATION',
                    width: 100,
                    editor: {
                        xtype: 'textfield',
                        name: 'STATION',
                        emptyText: '工位'
                    }
                },
                {
                    header: '车型',
                    dataIndex: 'MODELS',
                    width: 100,
                    editor: {
                        xtype: 'textfield',
                        name: 'MODELS',
                        emptyText: '车型'
                    }
                },
                {
                    header: '问题描述',
                    dataIndex: 'PROBLEMDESCRIPTION',
                    width: 150,
                    editor: {
                        xtype: 'textfield',
                        name: 'PROBLEMDESCRIPTION',
                        emptyText: '问题描述'
                    }
                },
                {
                    header: '原因分析',
                    dataIndex: 'CAUSEANALISYS',
                    width: 170,
                    editor: {
                        xtype: 'textfield',
                        name: 'CAUSEANALISYS',
                        emptyText: '原因分析'
                    }
                },
                {
                    header: '解决措施',
                    dataIndex: 'MEASURES',
                    width: 150,
                    editor: {
                        xtype: 'textfield',
                        name: 'MEASURES',
                        emptyText: '解决措施'
                    }
                },
                {
                    header: '停机时间(分钟)',
                    dataIndex: 'DOWNTIMEMINUTS',
                    width: 110,
                    editor: {
                        xtype: 'numberfield',
                        name: 'DOWNTIMEMINUTS',
                        emptyText: '停机时间(分钟)'
                    }
                },
                {
                    header: '区域责任人',
                    dataIndex: 'REGIONALRESPONSIBLEPERSON',
                    width: 100,
                    editor: {
                        xtype: 'textfield',
                        name: 'REGIONALRESPONSIBLEPERSON',
                        emptyText: '区域责任人'
                    }
                },
                {
                    header: '解决人',
                    dataIndex: 'SOLVEPEOPLE',
                    width: 100,
                    editor: {
                        xtype: 'textfield',
                        name: 'SOLVEPEOPLE',
                        emptyText: '解决人'
                    }
                },
                {
                    header: '问题状态',
                    dataIndex: 'STATEOFTHEPROBLEM',
                    width: 100,
                    editor: {
                        xtype: 'textfield',
                        name: 'STATEOFTHEPROBLEM',
                        emptyText: '问题状态'
                    }
                },
                {
                    header: '是否逾期',
                    dataIndex: 'WHERHERTHEOVERDUER',
                    width: 100,
                    editor: {
                        xtype: 'textfield',
                        name: 'WHERHERTHEOVERDUER',
                        emptyText: '是否逾期'
                    }
                }
            ],
            storeFields: ['ID', 'WEEKL', 'WORKSTARTTIME', 'WORKSECTION', 'AREA', 'STATION', 'MODELS', 'PROBLEMDESCRIPTION', 'CAUSEANALISYS', 'MEASURES', 'DOWNTIMEMINUTS'
                , 'REGIONALRESPONSIBLEPERSON', 'SOLVEPEOPLE', 'STATEOFTHEPROBLEM', 'WHERHERTHEOVERDUER'],
            ctTopbarComponts: [{
                iconCls: 'excel',
                text: '导出',
                enableByRowSelected: false,
                enableByHasData: true,
                tagValue: '',
                position: 3,
                handler: function () {
                    exportFnDown();
                }
            }],
            deletedKeyFields: ['ID'],
            isPageAction: false,
            isMultipleSelect: false,

            /*            deleteBtn: {
                            action: 'UPDATE_DATE_BIND',
                            position: 9
                        },*/

            //增加按钮
            addBtn: {
                hidden:true,
                action: 'add_PMC_WeekDay_StartRate_note',
                outputTable: '', //数据返回的output
                tagValue: '',
                position: 1
            },
            excelConfig: {
                enableExport: false
            },

            printConfig: {
                enablePrintView: false,
                enablePrint: false
            }
        },
        ctListeners: {
            /*beforeDelete: function (values) {
               // values['ID'] = roleIdGridSelected;
                //values['CMD'] = 'D';
                return true;
            },*/
            beforeAdd: function (values) {
                // debugger
                let record = calenderPanel.ht_outputGrid.getSelectionModel().getSelected();
                roleIdGridSelected = record.get('ID');
                // var rolePrivilegesStore = dateTmpBindInfo.ht_outputStore;
                // rolePrivilegesStore.baseParams['ID'] = roleIdGridSelected;
                values['ID'] = roleIdGridSelected;
                return true;
            }
        }
        /*,
        listeners : {
            afterLayout : function(){
                setPanelIsLayout = true;
            }
        }*/
    });

    /**
     * 导出下面板
     */
    var exportFnDown = function () {
        var url = 'json?action=QUERY_PMC_WeekDay_StartRate_Down_Excle';

        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();

        var columns = dateTmpBindInfo.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }

            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }

        // url += '&ID=' + dateTmpBindInfo.ht_outputStore.baseParams['ID'];

        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        url += '&ID=' + dateTmpBindInfo.ht_outputStore.baseParams['ID'];

        exportPanel.setSrc(url);
    };

    //导出面板
    var exportPanel1 = new Ext.ux.ManagedIFramePanel({
        region: 'east',
        width: 0,
        border: true,
        bodyBorder: false,
        autoScroll: true,
        autoLoad: false,
        defaultSrc: null
    });

    //返回面板
    var backPanel = new Ext.Panel({
        layout: 'border',
        border: false,
        items: [queryPanel, {
            region: 'center',
            xtype: 'panel',
            layout: 'border',
            border: false,
            items: [calenderPanel, dateTmpBindInfo, exportPanel, exportPanel1]
        }],
        listeners: {
            beforedestroy: function () {
                backPanel.removeAll(true);
            }
        }
    });

    calenderPanel.enableRelativePanelBarBtn(false);

    return backPanel;
})();
