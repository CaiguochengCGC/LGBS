// 公共模块：参数页面维护

(function() {
    
   var msgResource = ht.msg.pub.SysParam;
    
     //编辑状态
    var editStateStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'EDIT_STATUS'
        },
        fields : ['VALUE','TEXT']
    });
    editStateStore.load();
    
    //参数维护
	var backPanel = new ht.base.RowEditorPanel({
		queryFormConfig : {
			action : 'QUERY_SYS_PARAMBY_PAGER_ACTION',
			outputTable : 'SYS_PARAM',
            autoQuery :  true
		},
		gridConfig : {
			columns : [new Ext.grid.RowNumberer(), {
				header : msgResource.paramCode,
				dataIndex : 'PARAM_CODE',
				width : 200,
				editor : {
					xtype : 'textfield',
					name : 'PARAM_CODE',
					updateable : false
				}
			}, {
				header : msgResource.paramGroup,
				dataIndex : 'PARAM_GROUP',
				width : 80,
				hidden : true,
				editor : {
					xtype : 'textfield',
					name : 'PARAM_GROUP',
                    updateable : false
				}
			}, {
				header : msgResource.paramName,
				dataIndex : 'PARAM_NAME',
				width : 150,
				editor : {
					xtype : 'textfield',
					name : 'PARAM_NAME',
					updateable : false
				}
			}, {
				header : msgResource.paramVal,
				dataIndex : 'PARAM_VAL',
				width : 300,
				editor : {
					xtype : 'textfield',
					name : 'PARAM_VAL',
                    allowBlank : false,
                    vtype:'defineValid',
                    vtypeText : 'none',
                    defineValid: function(v, field) {

                        var record = backPanel.getSelected(); 
                        
                        var regExp = new RegExp(record.get('UDA_1'));
                        var text = record.get('UDA_2');
                        
                        if (regExp.test(v)) {
                            return true;
                        } else {
                            field.vtypeText = text;
                            return false;
                        }
                    }
				}
			}, {
				header : msgResource.isEdit,
				dataIndex : 'IS_EDIT',
				width : 90,
				editor : {
                    xtype : 'combo',
                    store : editStateStore,
                    emptyText : ht.msg.combo.emptyText,
                    mode : 'local',
                    triggerAction : 'all',
                    valueField : 'VALUE',
                    displayField : 'TEXT',
                    hiddenName : 'IS_EDIT',
                    name : 'IS_EDIT',
                    editable : false,
                    updateable : false
				},
                renderer : function(value){
                    return ht.pub.getValueExactByKey(editStateStore, 'VALUE', value, 'TEXT');
                } 
			}, {
				header : msgResource.paramType,
				dataIndex : 'PARAM_TYPE',
				width : 90,
				editor : {
					xtype : 'textfield',
					name : 'PARAM_TYPE',
					updateable : false
				}
			}, {
				header : msgResource.note,
				dataIndex : 'NOTE',
				width : 250,
				editor : {
					xtype : 'textfield',
					name : 'NOTE'
				}
			}],
            
			storeFields : ['PARAM_CODE', 'PARAM_GROUP', 'PARAM_NAME', 'PARAM_VAL', 'IS_EDIT', 
                           'PARAM_TYPE', 'NOTE', 'UDA_1', 'UDA_2'],
            isMultipleSelect : false,
                           
            updateBtn : {
                action : 'UPDATE_SYS_PARAM_ACTION'
            },
            excelConfig : {
                excelName : msgResource.excelName
            }
		}
	});
	return backPanel;
})();
