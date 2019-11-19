/**
 * 公共模块 -> 数据字典维护
 */
(function() {
    var msgResource = ht.msg.pub.DataDict;
    var ENABLE_EDIT_STATUS = 2; //可编辑的状态

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
    

    //数据字典
	var backPanel = new ht.base.PlainPanel({
		queryFormConfig : {
			items : [{
				xtype : 'textfield',
				fieldLabel : msgResource.codeTypeName,
				name : 'codeTypeName'
			}, {
				xtype : 'textfield',
				fieldLabel : msgResource.codeValueName,
				name : 'codeValueName'
			}],
			action : 'QUERY_PUB_DATA_DICTBY_PAGER_ACTION',
            outputTable : 'PUB_DATA_DICT'
		},
		gridConfig : {
			columns : [new Ext.grid.RowNumberer(), {
				header : msgResource.codeTypeName,
				dataIndex : 'CODE_TYPE_NAME',
				width : 150
			}, {
				header : msgResource.codeValue,
				dataIndex : 'CODE_VALUE',
				width : 150
			}, {
				header : msgResource.codeValueName,
				dataIndex : 'CODE_VALUE_NAME',
				width : 150
			}, {
				header : '判定时间',
				dataIndex : 'COMPARE',
				width : 80
			}, {
				header : msgResource.otherCodeValue1,
				dataIndex : 'OTHER_CODE_VALUE1',
				width : 150
			}, {
				header : msgResource.codeDesc,
				dataIndex : 'CODE_DESC',
				width : 200
			}, {
				header : msgResource.sortNo,
				dataIndex : 'SORT_NO',
				width : 100
			}],
			
			storeFields : ['PK_ID', 'CODE_TYPE', 'CODE_TYPE_NAME', 'CODE_VALUE', 'CODE_VALUE_NAME', 
                           'IS_EDIT', 'OTHER_CODE_VALUE1', 'SORT_NO', 'CODE_DESC', 'COMPARE'],
            deletedKeyFields : ['PK_ID', 'CODE_TYPE', 'CODE_VALUE'],        
                           
            addBtn : {
                action : 'ADD_PUB_DATA_DICT_ACTION',
                outputTable : 'PUB_DATA_DICT'
            },
            updateBtn : {
                action : 'UPDATE_PUB_DATA_DICT_ACTION'
            },
            deleteBtn : {
                action : 'DELETE_PUB_DATA_DICT_ACTION'
            },
            excelConfig : {
                excelName : msgResource.name
            }
		},
		editFormConfig : {
			items : [/*{
				xtype : 'textfield',
				fieldLabel : msgResource.codeType,
				allowBlank : false,
				name : 'CODE_TYPE',
                updateable : false
			}, {
				xtype : 'textfield',
				fieldLabel : msgResource.codeTypeName,
				allowBlank : false,

				name : 'CODE_TYPE_NAME'
			},*/ {
				xtype : 'textfield',
				fieldLabel : msgResource.codeValue,
				allowBlank : false,
				name : 'CODE_VALUE',
				updateable : false
			}, {
				xtype : 'textfield',
				fieldLabel : msgResource.codeValueName,
				allowBlank : false,
				name : 'CODE_VALUE_NAME'
			}, {
				xtype : 'numberfield',
				fieldLabel : '判定时间',
//				allowBlank : false,
				name : 'COMPARE'
			}/*, {
				xtype : 'textfield',
				fieldLabel : msgResource.otherCodeValue1,
				maxLength : 20,
				maxLengthText : ht.msg.valid.maxLengthText.replace('{0}', 20),
				name : 'OTHER_CODE_VALUE1'
			}*/, {
				xtype : 'textfield',
				fieldLabel : msgResource.codeDesc,
				name : 'CODE_DESC'
			}, {
				xtype : 'numberfield',
				fieldLabel : msgResource.sortNo,
				name : 'SORT_NO'
			}/*, {

				xtype : 'combo',
				fieldLabel : msgResource.isEdited,
				store : editStateStore,
				emptyText : ht.msg.combo.emptyText,
				mode : 'local',
				value : '2',
				triggerAction : 'all',
				valueField : 'VALUE',
				displayField : 'TEXT',
				hiddenName : 'IS_EDIT',
				name : 'IS_EDIT',
				editable : false,
                updateable : false
			}*/, {
				xtype : 'hidden',
				name : 'PK_ID'
			}],
            addPanel : {
                windowTitle : ht.msg.base.addText + msgResource.name
            },
            updatePanel : {
                windowTitle : ht.msg.base.updateText + msgResource.name
            }
		},
        
        ctListeners : {
            beforeShowUpdateWin : function() {
                var record = backPanel.ht_outputGrid.getSelectionModel().getSelected();
                var updateBtns = backPanel.ht_outputUpdateWindow.buttons;
                for (var i = 0; i < updateBtns.length; i++) {
                    if (updateBtns[i].text == ht.msg.base.submitText) {
                        if (ENABLE_EDIT_STATUS != record.data['IS_EDIT']) {
                            updateBtns[i].disable();
                        } else {
                            updateBtns[i].enable(); 
                        }
                        break;
                    }
                }
                
                return true;
            },
            beforeAdd : function(values, formSubmit){
            	values['CODE_TYPE'] = 'ARESPON';
            	values['CODE_TYPE_NAME'] = '责任部门';
            	values['IS_EDIT'] = '2';
            	return true
            },
            afterAdd : function(){
            	backPanel.ht_outputStore.load();
            	return true;
            }
        }
	});

	return backPanel;
})();
