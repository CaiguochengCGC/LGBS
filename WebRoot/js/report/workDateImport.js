/**
 * 固定报表 -> 意见反馈
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
            enableQueryButton : true,
            items : [{
                xtype: 'compositefield',
                fieldLabel: '录入时间',
                name : 'PLAN_DATE',
                items: [ {
                    xtype : 'datefield',
                    format : ht.config.format.DATE,
                    fieldLabel : ' ',
                    name : 'START_PLAN_DATE',
                    value : new Date(new Date().getTime() - (new Date().format('N') + 1) * 24 * 60 * 60 * 1000),
                    
                    vtype: 'defineValid',
                    vtypeText : '',
                    defineValid : function(val, fromField){
                        if(setPanelIsLayout && stopLinePanel.findQueryCompment('END_PLAN_DATE')){
                            stopLinePanel.findQueryCompment('END_PLAN_DATE').validate();
                        }
                        return true;
                    }
                }, {
                    xtype: 'displayfield',
                    width : 20,
                    style : 'text-align: center;',
                    value : '至'
                },{
                    xtype : 'datefield',
                    format : ht.config.format.DATE,
                    fieldLabel : ' ',
                    name : 'END_PLAN_DATE',
                    value : new Date(new Date().getTime()),
                    vtype: 'comparentDate',
                    vtypeText: ht.msg.valid.compareDate,
                    comparentTo: 'START_PLAN_DATE',
                    getParentCompont : function() {
                        return stopLinePanel.ht_outputQueryForm.find('name', 'PLAN_DATE')[0].innerCt;
                    }
                }]
            }],
            action : 'QUERY_PMC_VIEW_RECORD_ACTION',
            outputTable : 'PMC_VIEW_RECORD'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '车间名字',
                dataIndex : 'WORKSHOP',
                width : 150,
                editor : {
                    xtype : 'textfield',
                    name : 'WORKSHOP'
                }
            },{
                header : '开始工作时间',
                dataIndex : 'BEGIN_TIME',
                width : 150,
                editor : {
                    xtype : 'datetimefield',
                    name : 'BEGIN_TIME',
                },
                renderer : function(value, p,record) {
                    return ht.pub.date.dateTimeRenderer(value);//显示时间
                }  
            },{
                header : '结束工作时间',
                dataIndex : 'END_TIME',
                width : 150,
                editor : {
                    xtype : 'datetimefield',
                    name : 'END_TIME',
                   format : ht.config.format.DATETIME
                },
                renderer : function(value, p,record) {
                    return ht.pub.date.dateTimeRenderer(value);//显示时间
                } 
            },{
                header : '休息时间',
                dataIndex : 'REST_TIME',
                width : 150,
                editor : {
                    xtype : 'textfield',
                    name : 'REST_TIME',
                    regex:/^[0-9]\d*$/
                }
            },{
                header : '休息次数',
                dataIndex : 'REST_COUNT',
                width : 150,
                editor : {
                    xtype : 'textfield',
                    name : 'REST_COUNT',
                    regex:/^[0-9]\d*$/
                }
            },{
                header : '班次',
                dataIndex : 'SHIFT',
                width : 150,
                editor : {
                    xtype : 'combo',
                    name : 'SHIFT',
                    
                    stror:banciStore
                    ,
                    fieldLabel : '班次',
                    store : banciStore,
                    emptyText : '全部班次',
                    mode : 'local',
                    triggerAction : 'all',
                    valueField : 'VALUE',
                    displayField : 'TEXT',
                    hiddenName : 'BANCI',
                    editable : false,
                    listeners : {
    	                select : function(c){
    	                	//console.log(c);
    	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
    	                }
    	            }
                }
            } ],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : [
            	'ID',
            	'WORKSHOP',
            	'BEGIN_TIME',
            	'END_TIME',
            	'REST_TIME',
            	'REST_COUNT',
            	'TOTAL_TIME',
            	'WORK_DATE',
            	'SHIFT',
            	'IS_WORK_DATE',{name : 'RECORDDATE',REF_TYPE : ht.pub.date.DATE_TIME}

            	],
            deletedKeyFields : ['ID'],
            
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
    
    //导出excel
    var exportFn = function(){
        
        var url = 'json?action=EXPORT_PMC_VIEW_RECORD_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = stopLinePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&START_PLAN_DATE=' + stopLinePanel.ht_outputStore.baseParams['START_PLAN_DATE'];
        url += '&END_PLAN_DATE=' + stopLinePanel.ht_outputStore.baseParams['END_PLAN_DATE'];
        
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
