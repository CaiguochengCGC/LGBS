/**
 * 模板导入  caiiguocheng
 */
(function() {
    var setPanelIsLayout;
    var bancidata = [["1","1班"],["2","2班"]];
    var banciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:bancidata,
     fields:["VALUE","TEXT"]
    });
    
    //停线报表
    var stopLinePanel = new ht.base.RowEditorPanel({
        region : 'center',
        queryFormConfig : {
            action : 'QUERY_PMC_VIEW_RECORD_ACTION',
            outputTable : 'PMC_VIEW_RECORD',
            autoQuery :  true
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),
                {
                    header : '车间名字',
                    dataIndex : 'Work_Shop',
                    width : 130,
                    editor : {
                        xtype : 'textfield',
                        name : 'Work_Shop',
                        emptyText : '车间名字'
                    }
                } ,{
                    header : '模板名字',
                    dataIndex : 'Templet_Name',
                    width : 130,
                    editor : {
                        xtype : 'textfield',
                        name : 'Templet_Name',
                        emptyText : '模板名字'

                    }
                },{
                    header : '班次',
                    dataIndex : 'Shift',
                    width : 70,

                        renderer: function (value) {
                            if (value == 1) {
                                return '1班';
                            }
                            if (value == 2) {
                                return '2班';
                            }

                        },
                    editor : {

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
                        editable : false
                    }
                },{
                    header : '开班时间',
                    dataIndex : 'WorkStart_Time',
                    width : 70,
                    editor : {
                        xtype : 'textfield',
                        name : 'WorkStart_Time',
                        regex:/^(([0-1]\d)|(2[0-4])):[0-5]\d$/,
                        emptyText : '00:00'
                    }
                },{
                    header : '结束时间',
                    dataIndex : 'WorkEnd_Time',
                    width : 70,
                    editor : {
                        xtype : 'textfield',
                        name : 'WorkEnd_Time',
                        regex:/^(([0-1]\d)|(2[0-4])):[0-5]\d$/,
                        emptyText : '00:00'
                    }
                },{
                    header : '第一次休息',
                    dataIndex : 'Rest_Time1',
                    width : 110,
                    editor : {
                        xtype : 'textfield',
                        name : 'Rest_Time1',
                        regex:/^(([0-1]\d)|(2[0-4])):[0-5]\d-(([0-1]\d)|(2[0-4])):[0-5]\d$/,
                        emptyText : '00:00-00:00'
                    },

                },{
                    header : '第二次休息',
                    dataIndex : 'Rest_Time2',
                    width : 110,
                    editor : {
                        xtype : 'textfield',
                        name : 'Rest_Time2',
                        regex:/^(([0-1]\d)|(2[0-4])):[0-5]\d-(([0-1]\d)|(2[0-4])):[0-5]\d$/,
                        emptyText : '00:00-00:00'
                    }
                },{
                    header : '第三次休息',
                    dataIndex : 'Rest_Time3',
                    width : 110,
                    editor : {
                        xtype : 'textfield',
                        name : 'Rest_Time3',
                        regex:/^(([0-1]\d)|(2[0-4])):[0-5]\d-(([0-1]\d)|(2[0-4])):[0-5]\d$/,
                        emptyText : '00:00-00:00'
                    }
                },{
                    header : '第四次休息',
                    dataIndex : 'Rest_Time4',
                    width : 110,
                    editor : {
                        xtype : 'textfield',
                        name : 'Rest_Time4',
                        regex:/^(([0-1]\d)|(2[0-4])):[0-5]\d-(([0-1]\d)|(2[0-4])):[0-5]\d$/,
                        emptyText : '00:00-00:00'
                    }
                },{
                    header : '第五次休息',
                    dataIndex : 'Rest_Time5',
                    width : 110,
                    editor : {
                        xtype : 'textfield',
                        name : 'Rest_Time5',
                        regex:/^(([0-1]\d)|(2[0-4])):[0-5]\d-(([0-1]\d)|(2[0-4])):[0-5]\d$/,
                        emptyText : '00:00-00:00'
                    }
                },{
                    header : '第六次休息',
                    dataIndex : 'Rest_Time6',
                    width : 110,
                    editor : {
                        xtype : 'textfield',
                        name : 'Rest_Time6',
                        regex:/^(([0-1]\d)|(2[0-4])):[0-5]\d-(([0-1]\d)|(2[0-4])):[0-5]\d$/,
                        emptyText : '00:00-00:00'
                    }
                },{
                    header : '第七次休息',
                    dataIndex : 'Rest_Time7',
                    width : 110,
                    editor : {
                        xtype : 'textfield',
                        name : 'Rest_Time7',
                        regex:/^(([0-1]\d)|(2[0-4])):[0-5]\d-(([0-1]\d)|(2[0-4])):[0-5]\d$/,
                        emptyText : '00:00-00:00'
                    }
                },{
                    header : '第八次休息',
                    dataIndex : 'Rest_Time8',
                    width : 110,
                    editor : {
                        xtype : 'textfield',
                        name : 'Rest_Time8',
                        regex:/^(([0-1]\d)|(2[0-4])):[0-5]\d-(([0-1]\d)|(2[0-4])):[0-5]\d$/,
                        emptyText : '00:00-00:00'
                    }
                },{
                    header : '第九次休息',
                    dataIndex : 'Rest_Time9',
                    width : 110,
                    editor : {
                        xtype : 'textfield',
                        name : 'Rest_Time9',
                        regex:/^(([0-1]\d)|(2[0-4])):[0-5]\d-(([0-1]\d)|(2[0-4])):[0-5]\d$/,
                        emptyText : '00:00-00:00'
                    }
                },{
                    header : '第十次休息',
                    dataIndex : 'Rest_Time10',
                    width : 110,

                    editor : {
                        xtype : 'textfield',
                        name : 'Rest_Time10',
                        regex:/^(([0-1]\d)|(2[0-4])):[0-5]\d-(([0-1]\d)|(2[0-4])):[0-5]\d$/,
                        emptyText : '00:00-00:00'
                    }
                },{
                    header : '序号',
                    dataIndex : 'Model_Code',
                    width : 70,
                    hidden : true
                }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : [
                'Model_Code',
                'Work_Shop',
                'Templet_Name',
                'Shift',
                'WorkStart_Time',
                'WorkEnd_Time',
                'Rest_Time1',
                'Rest_Time2',
                'Rest_Time3',
                'Rest_Time4',
                'Rest_Time5',
                'Rest_Time6',
                'Rest_Time7',
                'Rest_Time8',
                'Rest_Time9',
                'Rest_Time10'

            	],
            deletedKeyFields : ['Model_Code'],
            updateKeyFields : ['Model_Code'],
            
            //增加按钮
	        addBtn : {
	            action : 'ADD_PMC_VIEW_RECORD_ACTION',
	            outputTable : '', //数据返回的output
	            tagValue : '',
	            position : 1
	        },
	        
	        //修改按钮
	        updateBtn : {
	            action : 'UPDATE_PMC_VIEW_RECORD_ACTION',
	            outputTable : '', //数据返回的output
	            tagValue : '',
	            position : 2
	        },
	        
	        //删除按钮
	        deleteBtn : {
	            action : 'DELETE_PMC_VIEW_RECORD_ACTION',
	            tagValue : '',
	            position : 3
	        }
        },
        ctListeners : {
           
        },
        listeners : {
            afterLayout : function(){
                setPanelIsLayout = true;
            }
        }
    });

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
    
    var backPanel =new Ext.Panel({
        border : false,
        layout : 'border',
        items : [stopLinePanel,exportPanel],
        listeners : {
            beforedestroy : function(){
                backPanel.removeAll(true);
            }   
        } 
    });
    
    return backPanel;
})();
