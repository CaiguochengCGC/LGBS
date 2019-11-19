/**
 * 公共模块：用户与角色对应关系维护
 */

(function() {

    var msgResource = ht.msg.pub.User;
    var barFieldLableStyle = 'padding: 0px 5px 0px 10px;';
    var userNameGridSelected; //选中的用户名
    var currentField; //弹窗控件
    
    /**
     * 查询面板
     */
    var queryPanel = new ht.base.PropertyPanel({
		region : 'north',
		border : false,
		formConfig : {
			items : [{
				xtype : 'textfield',
				fieldLabel : msgResource.userName,
				name : 'PK_USER_NAME'
			}, {
				xtype : 'textfield',
				fieldLabel : msgResource.userCName,
				name : 'USER_CNAME'
			}]
		},
		ctListeners : {
			beforeQuery : function(values) {
                
                //用户角色
                userInfoPanel.enableRelativePanelBarBtn(false);

                // 禁用用户工具栏按钮
				userInfoPanel.enableBarBtn(false);
				userInfoPanel.stopEditing();
                
				// 查询用户信息
				var userInfoStore = userInfoPanel.ht_outputStore;
				for (var field in values) {
					userInfoStore.baseParams[field] = values[field];
				}

				userInfoStore.load();
				return false
			}
		}
	});
    
    /**
     * 用户信息
     */
    var userInfoPanel = new ht.base.RowEditorPanel({
        region : 'west',
        width : '60%',
        queryFormConfig : {
            enableQueryButton : false,
            action : 'QUERY_PUB_USERBY_PAGER_ACTION',
            outputTable : 'PUB_USER'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : msgResource.userName,
                dataIndex : 'PK_USER_NAME',
                width : 120,
                editor : {
                    xtype : 'textfield',
                    name : 'PK_USER_NAME',
                    maxLength : 20,
                    maxLengthText : ht.msg.valid.maxLengthText.replace('{0}', 20),
                    allowBlank : false,
                    updateable : false
                }
            }, {
                header : msgResource.userCName,
                dataIndex : 'USER_CNAME',
                width : 180,
                editor : {
                    xtype : 'textfield',
                    name : 'USER_CNAME',
                    maxLength : 10,
                    maxLengthText : ht.msg.valid.maxLengthText.replace('{0}', 10),
                    allowBlank : false
                }
            }],
            
            storeFields : ['PK_USER_NAME', 'USER_CNAME'],
            deletedKeyFields : ['PK_USER_NAME'],
            isMultipleSelect : false,
            enablePageSize : false, 

            addBtn : {
                action : 'ADD_PUB_USER_ACTION',
                tagValue : ''
            },

            updateBtn : {
                action : 'UPDATE_PUB_USER_ACTION',
                tagValue : ''
            },

            deleteBtn : {
                action : 'DELETE_PUB_USER_ACTION',
                tagValue : ''
            },
            
            //表格顶部额外控件
            ctTopbarComponts : [{
                text : msgResource.resetPsdActionText,
				iconCls : 'reset',
				handler : function() {
					ht.pub.confirm(msgResource.resetPsdConfirm, function(btn) {
						if (btn != 'yes') {
                            return;
                        }
						var resetParams = {};
						resetParams['PK_USER_NAME'] = userNameGridSelected;
						resetParams['action'] = 'RESER_PUB_USER_PASSWORD_ACTION';
						Ext.Ajax.request({
							url : 'json',
							params : resetParams,
							success : function(response, options) {
								var result = Ext.decode(response.responseText)
								if (result.success) {
                                    ht.pub.info(msgResource.resetPsdSuccess);
								} else {
                                    ht.pub.error(result.errors.errmsg);
								}
							},
                            failure : function(response, options) {
                                ht.pub.handleAjaxErrors(response);
                            }
						});
					});
				}
            }],

            excelConfig : {
                tagValue : '',
                excelName : msgResource.userExcelName
            },
            printConfig : {
                enablePrintView : false
            }
        },
        ctListeners : {
            beforeAdd : function(values, formSubmit){
                
                return true
            }, 
            beforeUpdate : function(values, formSubmit){
                return true
            },
            afterLayoutEditor : function(){
                
            }
        },
        enableRelativePanelBarBtn : function(enable) {
            
            var topbar = userRolePanel.ht_outputGrid.ownerCt.getTopToolbar();
            
            var addBtn = topbar.find('text', ht.msg.base.addText)[0];
            addBtn.setDisabled(!enable);
            
            var roleNameField = topbar.find('name', 'ROLE_NAME')[0];
            roleNameField.setDisabled(!enable);
            
            userRolePanel.enableBarBtn(false);
            
            if (!enable) {
                userRolePanel.ht_outputStore.removeAll();
            } else {
                
                //获取选中用户
                var record = userInfoPanel.ht_outputGrid.getSelectionModel().getSelected();
                userNameGridSelected = record.get('PK_USER_NAME');
                
                //加载用户角色
                var userRoleStore = userRolePanel.ht_outputStore;
                userRoleStore.baseParams['PK_USER_NAME'] = userNameGridSelected;
                userRoleStore.load();
                
                //角色
                var roleStore = rolePanel.ht_outputStore;
                roleStore.baseParams['PK_USER_NAME'] = userNameGridSelected;
            }
        }
    });
    
    /**
     * 用户角色
     */
    var userRolePanel = new ht.base.PlainPanel({
        region : 'center',
		height : 250,
		border : false,
        queryFormConfig : {
            enableQueryButton : false,
            action : 'QUERY_PUB_USER_ROLEBY_PAGER_ACTION',
            outputTable : 'PUB_USER_ROLE'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : msgResource.roleName,
                dataIndex : 'ROLE_NAME',
                width : 180
            }, {
                header : msgResource.roleDescription,
                dataIndex : 'ROLE_DESCRIPTION',
                width : 200
            }],
            
            storeFields : ['PK_ROLE_ID', 'ROLE_NAME', 'ROLE_DESCRIPTION'],
            deletedKeyFields : ['PK_ROLE_ID'],
            
            deleteBtn : {
                action : 'SAVE_PUB_USER_ROLE_ACTION',
                position : 9
            },
            
            ctTopbarComponts : [{
                xtype : 'tbtext',
                style : barFieldLableStyle,
                text : msgResource.roleName
            },{
                xtype : 'textfield',
                name : 'ROLE_NAME',
                width : 100,
                enableByRowSelected : false,
                ingoreSeparator : true,
                enableKeyEvents : true,
                listeners : {
                    keydown : function(field, e){
                        if(e.getKey() == Ext.EventObject.ENTER){
                            var userRoleStore = userRolePanel.ht_outputStore;
                            userRoleStore.baseParams['ROLE_NAME'] = field.getValue();
                            userRoleStore.load();
                        }
                    }
                }
            },{
                text : ht.msg.base.addText,
                iconCls : 'add',
                enableByRowSelected : false,
                handler : function() {
                    rolePanel.enableBarBtn(false);
                    rolePanel.ht_outputStore.load();
                    roleWin.show();
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
                values['PK_USER_NAME'] = userNameGridSelected;
                values['CMD'] = 'D';
                return true;
            }
        }
    });
    
    /**
     *角色面板
     */
    var rolePanel = new ht.base.PlainPanel({
        border : true,
        region : 'center',
        queryFormConfig : {
            action : 'QUERY_PUB_ROLEBY_PAGER_ACTION',
            outputTable : 'PUB_ROLE',
            items : [{
                xtype : 'textfield',
                fieldLabel : msgResource.roleName,
                name : 'ROLE_NAME'
            }],
            useTo : 'win-query'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : msgResource.roleName,
                dataIndex : 'ROLE_NAME',
                width : 180
            }, {
                header : msgResource.roleDescription,
                dataIndex : 'ROLE_DESCRIPTION',
                width : 200
            }],
            
            storeFields : ['PK_ROLE_ID', 'ROLE_NAME', 'ROLE_DESCRIPTION'],
            
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
                if (roleWin) {
                    ht.pub.enableCompentByRowSelected(roleWin.buttons, enable);
                }
            }
        }
    });
    
    /**
     * 角色窗口
     */
    var roleWin = new Ext.Window({
        width : 500,
        height : 500,
        title : msgResource.roleWinTitle,
        layout : 'fit',
        plain : true,
        closable : true,
        resizable : false,
        frame : true,
        constrain : true,
        closeAction : 'hide',
        border : false,
        modal : true,
        items : rolePanel,
        buttons : [{
            text : ht.msg.base.submitText,
            disabled : true,
            enableByRowSelected : true,
            handler : function() {
                
                //获取参数
                var params = {};
                params['PK_USER_NAME'] = userNameGridSelected;
                params['CMD'] = 'A';
                params['action'] = 'SAVE_PUB_USER_ROLE_ACTION';
                params['PK_ROLE_ID'] = new Array();
                
                var records = rolePanel.ht_outputGrid.getSelectionModel().getSelections();
                for (var i = 0; i < records.length; i++) {
                    params['PK_ROLE_ID'][i] = records[i].get('PK_ROLE_ID');
                }
                
                //提交
                Ext.Ajax.request({
                    url : 'json',
                    params : params,
                    success : function(response, options) {
                        var result = Ext.decode(response.responseText)
                        if (result.success) {
                            
                            roleWin.hide();
                            ht.pub.info(ht.msg.base.addSuccessText);
                            
                            var records = rolePanel.ht_outputGrid.getSelectionModel().getSelections();
                            for (var i = 0; i < records.length; i++) {
                                userRolePanel.ht_outputStore.add(new Ext.data.Record(records[i].data));
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
                roleWin.hide();
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
            items : [userInfoPanel, userRolePanel]
        }],
        listeners : {
            beforedestroy : function(){
                roleWin.hide();
                roleWin.destroy();
                backPanel.removeAll(true);
            }   
        }
    });
    
    userInfoPanel.enableRelativePanelBarBtn(false);
	
	return backPanel;
})();
