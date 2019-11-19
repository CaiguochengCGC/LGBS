/**
 * 公共模块：角色与权限对应关系维护
 */

(function() {

    var msgResource = ht.msg.pub.Role;
    var barFieldLableStyle = 'padding: 0px 5px 0px 10px;';
    var roleIdGridSelected;
    
    //模块
    var moduleStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_MODULE.rs',
        baseParams : {
            action : 'QUERY_PUB_MODULE_ACTION'
        },
        fields : ['VALUE', 'TEXT']
    });
    moduleStore.load({
        callback : function() {
           ht.pub.store.addBlankText(moduleStore);
        }
    });
    
    //权限类型 
    var privilegeTypeStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'PRIVI_TYPE'
        },
        fields : ['VALUE','TEXT']
    });
    privilegeTypeStore.load();
    
    /**
     * 查询面板
     */
    var queryPanel = new ht.base.PropertyPanel({
		region : 'north',
		border : false,
		formConfig : {
			items : [{
				xtype : 'textfield',
				fieldLabel : msgResource.roleName,
				name : 'ROLE_NAME'
			}]
		},
		ctListeners : {
			beforeQuery : function(values) {
                
                //角色权限
                roleInfoPanel.enableRelativePanelBarBtn(false);
                
				// 禁用角色工具栏按钮
				roleInfoPanel.enableBarBtn(false);
				roleInfoPanel.stopEditing();
                
				// 查询角色信息
				var roleInfoStore = roleInfoPanel.ht_outputStore;
				for (var field in values) {
					roleInfoStore.baseParams[field] = values[field];
				}

				roleInfoStore.load();
				return false
			}
		}
	});
    
    /**
     * 角色信息
     */
    var roleInfoPanel = new ht.base.RowEditorPanel({
        region : 'west',
        width : 450,
        queryFormConfig : {
            enableQueryButton : false,
            action : 'QUERY_PUB_ROLEBY_PAGER_ACTION',
            outputTable : 'PUB_ROLE'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : msgResource.roleName,
                dataIndex : 'ROLE_NAME',
                width : 120,
                editor : {
                    xtype : 'textfield',
                    name : 'ROLE_NAME',
                    maxLength : 10,
                    maxLengthText : ht.msg.valid.maxLengthText.replace('{0}', 10),
                    allowBlank : false
                }
            }, {
                header : msgResource.roleDescription,
                dataIndex : 'ROLE_DESCRIPTION',
                width : 200,
                editor : {
                    xtype : 'textfield',
                    name : 'ROLE_DESCRIPTION',
                    maxLength : 45,
                    maxLengthText : ht.msg.valid.maxLengthText.replace('{0}', 45),
                    allowBlank : false
                }
            }],
            
            storeFields : ['PK_ROLE_ID', 'ROLE_NAME', 'ROLE_DESCRIPTION'],
            deletedKeyFields : ['PK_ROLE_ID'],
            isMultipleSelect : false,
            enablePageSize : false, 

            addBtn : {
                action : 'ADD_PUB_ROLE_ACTION',
                tagValue : '',
                outputTable : 'PUB_ROLE'
            },

            updateBtn : {
                action : 'UPDATE_PUB_ROLE_ACTION',
                tagValue : ''
            },

            deleteBtn : {
                action : 'DELETE_PUB_ROLE_ACTION',
                tagValue : ''
            },

            excelConfig : {
                tagValue : '',
                excelName : msgResource.roleExcelName
            },
            printConfig : {
                enablePrintView : false
            }
        },
        ctListeners : {
            afterAdd : function(action) {
                
                //获取角色ID
                var table = roleInfoPanel.gridConfig.addBtn.outputTable;
                var result = action.result.root[table].rs[0];
                roleIdGridSelected = result['PK_ROLE_ID'];
                return true;
            }
        },
        enableRelativePanelBarBtn : function(enable) {
            
            //权限
            var topbar = rolePrivilegesPanel.ht_outputGrid.ownerCt.getTopToolbar();
            var addBtn = topbar.find('text', ht.msg.base.addText)[0];
            var privilegesNameField = topbar.find('name', 'DESCRIPTION')[0];
            addBtn.setDisabled(!enable);
            privilegesNameField.setDisabled(!enable);
            
            var categoryCombo = topbar.find('name', 'CATEGORY')[0];
            categoryCombo.setDisabled(!enable);
            rolePrivilegesPanel.enableBarBtn(false);
            
            if (!enable) {
                rolePrivilegesPanel.ht_outputStore.removeAll();
            } else {
                
                //获取选中角色
                var record = roleInfoPanel.ht_outputGrid.getSelectionModel().getSelected();
                roleIdGridSelected = record.get('PK_ROLE_ID');
                
                //加载角色权限
                var rolePrivilegesStore = rolePrivilegesPanel.ht_outputStore;
                rolePrivilegesStore.baseParams['PK_ROLE_ID'] = roleIdGridSelected;
                rolePrivilegesStore.load();
                
                //权限
                var privilegesStore = privilegesPanel.ht_outputStore;
                privilegesStore.baseParams['PK_ROLE_ID'] = roleIdGridSelected;
                
            }
        }
    });
    
    /**
     * 角色权限
     */
    var rolePrivilegesPanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            enableQueryButton : false,
            action : 'QUERY_PUB_ROLE_PRIVILEGESBY_PAGER_ACTION',
            outputTable : 'PUB_ROLE_PRIVILEGES'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),
                {
                header : msgResource.category,
                dataIndex : 'CATEGORY',
                width : 180,
                renderer : function(value){
                    return ht.pub.getValueExactByKey(moduleStore, 'VALUE', value, 'TEXT');
                }
            },
                {
                header : msgResource.type,
                dataIndex : 'TYPE',
                width : 100,
                renderer : function(value){
                    return ht.pub.getValueExactByKeyDefault(privilegeTypeStore, value);
                }
            }, {
                header : msgResource.description,
                dataIndex : 'DESCRIPTION',
                width : 200
            }],
            
            storeFields : ['PK_PRIVILEGES_ID', 'DESCRIPTION', 'CATEGORY', 'TYPE'],
            deletedKeyFields : ['PK_PRIVILEGES_ID'],
            
            deleteBtn : {
                action : 'SAVE_PUB_ROLE_PRIVILEGES_ACTION',
                position : 9
            },
            
            ctTopbarComponts : [{
                xtype : 'tbtext',
                style : barFieldLableStyle,
                text : msgResource.category
            },{
                xtype : 'combo',
                name : 'CATEGORY',
                width : 100,
                store : moduleStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'CATEGORY',
                editable : false,
                enableByRowSelected : false,
                ingoreSeparator : true,
                listeners : {
                    select : function(combo) {
                        var rolePrivilegesStore = rolePrivilegesPanel.ht_outputStore;
                        rolePrivilegesStore.baseParams['CATEGORY'] = combo.getValue();
                        rolePrivilegesStore.load();
                    }
                
                }
            },{
                xtype : 'tbtext',
                style : barFieldLableStyle,
                text : msgResource.description
            },{
                xtype : 'textfield',
                name : 'DESCRIPTION',
                width : 100,
                enableByRowSelected : false,
                ingoreSeparator : true,
                enableKeyEvents : true,
                listeners : {
                    keydown : function(field, e){
                        if(e.getKey() == Ext.EventObject.ENTER){
                            var rolePrivilegesStore = rolePrivilegesPanel.ht_outputStore;
                            rolePrivilegesStore.baseParams['DESCRIPTION'] = field.getValue();
                            rolePrivilegesStore.load();
                        }
                    }
                }
            },{
                text : ht.msg.base.addText,
                iconCls : 'add',
                enableByRowSelected : false,
                handler : function() {
                    privilegesPanel.enableBarBtn(false);
                    privilegesPanel.ht_outputStore.load();
                    privilegesWin.show();
                }
            }],
            
            excelConfig : {
                enableExport : false
            },
            printConfig : {
                enablePrintView : false,
                enablePrint : false
            }
        },
        ctListeners : {
            beforeDelete : function(values){
                values['PK_ROLE_ID'] = roleIdGridSelected;
                values['CMD'] = 'D';
                return true;
            }
        }
    });
    
    /**
     *权限面板
     */
    var privilegesPanel = new ht.base.PlainPanel({
        border : true,
        region : 'center',
        queryFormConfig : {
            action : 'QUERY_PUB_PRIVILEGESBY_PAGER_ACTION',
            outputTable : 'PUB_PRIVILEGES',
            items : [{
                xtype : 'combo',
                fieldLabel : msgResource.category,
                name : 'Q_CATEGORY',
                store : moduleStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'Q_CATEGORY',
                editable : false
            },{
                xtype : 'textfield',
                fieldLabel : msgResource.description,
                name : 'Q_DESCRIPTION'
            }],
            useTo : 'win-query'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : msgResource.category,
                dataIndex : 'CATEGORY',
                width : 100,
                renderer : function(value){
                    return ht.pub.getValueExactByKey(moduleStore, 'VALUE', value, 'TEXT');
                }
            }, {
                header : msgResource.type,
                dataIndex : 'TYPE',
                width : 100,
                renderer : function(value){
                    return ht.pub.getValueExactByKeyDefault(privilegeTypeStore, value);
                }
            }, {
                header : msgResource.description,
                dataIndex : 'DESCRIPTION',
                width : 250
            }],
            
            storeFields : ['PK_PRIVILEGES_ID', 'DESCRIPTION', 'CATEGORY', 'TYPE'],
            
            excelConfig : {
                enableExport : false
            },
            printConfig : {
                enablePrintView : false,
                enablePrint : false
            }
        },
        ctListeners : {
            afterQuery : function(){
                
            },
            enableBarBtn : function(enable) {
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
        width : 700,
        height : 500,
        title : msgResource.privilegesWinTitle,
        layout : 'fit',
        plain : true,
        closable : true,
        resizable : false,
        frame : true,
        constrain : true,
        closeAction : 'hide',
        border : false,
        modal : true,
        items : privilegesPanel,
        buttons : [{
            text : ht.msg.base.submitText,
            disabled : true,
            enableByRowSelected : true,
            handler : function() {
                
                //获取参数
                var params = {};
                params['PK_ROLE_ID'] = roleIdGridSelected;
                params['CMD'] = 'A';
                params['action'] = 'SAVE_PUB_ROLE_PRIVILEGES_ACTION';
                params['PK_PRIVILEGES_ID'] = new Array();
                
                var records = privilegesPanel.ht_outputGrid.getSelectionModel().getSelections();
                for (var i = 0; i < records.length; i++) {
                    params['PK_PRIVILEGES_ID'][i] = records[i].get('PK_PRIVILEGES_ID');
                }
                
                //提交
                Ext.Ajax.request({
                    url : 'json',
                    params : params,
                    success : function(response, options) {
                        var result = Ext.decode(response.responseText)
                        if (result.success) {
                            
                            privilegesWin.hide();
                            ht.pub.info(ht.msg.base.addSuccessText);
                            
                            var records = privilegesPanel.ht_outputGrid.getSelectionModel().getSelections();
                            for (var i = 0; i < records.length; i++) {
                                rolePrivilegesPanel.ht_outputStore.add(new Ext.data.Record(records[i].data));
                            }
                        }else {   
                            ht.pub.error(result.errors.errmsg);
                        }
                    },
                    failure : function(response, options) {
                        ht.pub.handleAjaxErrors(response);
                    }
                });
            }
        },{
            text : ht.msg.base.cancelText,
            enableByRowSelected : false,
            handler : function() {
                privilegesWin.hide();
            }
        }]
    })
    
    
    
	//返回面板
    var backPanel = new Ext.Panel({
        layout : 'border',
        border : false,
        items : [queryPanel, {
            region : 'center',
            xtype : 'panel',
            layout : 'border',
            border : false,
            items : [roleInfoPanel, rolePrivilegesPanel]
        }],
        listeners : {
            beforedestroy : function(){
                
                privilegesWin.hide();
                privilegesWin.destroy();
                backPanel.removeAll(true);
            }   
        }
    });
    
    roleInfoPanel.enableRelativePanelBarBtn(false);
	
	return backPanel;
})();
