/**
 * 修改停线查询
 */
(function() {
    var planNoTemp;
    var setPanelIsLayout;
  //责任部门
    var responsStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'ARESPON'
        }, 
        fields : ['VALUE','TEXT']
    });
    responsStore.load({
        callback : function() {
            ht.pub.store.addBlankText(responsStore);
        }
    });
    //表单
    var fm = Ext.form;
  //工段
    var sectionStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_STATION.rs',
        baseParams : {
            action : 'ZZCJ_QUERY_PMC_PP_STATION_STOP_ACTION',
            CODE_TYPE : 'EventData40'
        },
        fields : ['VALUE','TEXT']
    });
    sectionStore.load({
        callback : function() {
            ht.pub.store.addBlankText(sectionStore);
        }
    });
  //新增班次信息
    var bancidata = [["","全部"],["1","1班"],["2","2班"]];
    var banciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:bancidata,
     fields:["VALUE","TEXT"]
    });
    
    //班次
    var shiftStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'SHIFT_TYPE'
        },
        fields : ['VALUE','TEXT']
    });
    var sheftQueryStore = new Ext.data.Store({ 
        recordType: shiftStore.recordType 
    });
    shiftStore.load({
        callback : function() {
            ht.pub.store.cloneStore(shiftStore, sheftQueryStore);
            ht.pub.store.addBlankText(sheftQueryStore);
        }
    });
    
    //生产时间报表
    var workTimePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            enableQueryButton : true, 
            items : [{
                xtype: 'compositefield',
                columnWidth : 1/2,
                fieldLabel: '日期',
                name : 'PLAN_DATE',
                items: [{
                    xtype : 'datefield',
                    width : 155,
                    format : ht.config.format.DATETIME,
                    fieldLabel : ' ',
                    name : 'START_PLAN_DATE',
                    value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000),
                    vtype: 'defineValid',
                    vtypeText : '',
                    defineValid : function(val, fromField){
                        if(setPanelIsLayout && workTimePanel.findQueryCompment('END_PLAN_DATE')){
                            workTimePanel.findQueryCompment('END_PLAN_DATE').validate();
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
                    width : 155,
                    format : ht.config.format.DATETIME,
                    fieldLabel : ' ',
                    name : 'END_PLAN_DATE',
                    value : new Date(),
                    vtype: 'comparentDate',
                    vtypeText: ht.msg.valid.compareDate,
                    comparentTo: 'START_PLAN_DATE',
                    getParentCompont : function() {
                        return workTimePanel.ht_outputQueryForm.find('name', 'PLAN_DATE')[0].innerCt;
                    }
                }]
            },{
                xtype : 'combo',
                width : 100,
                fieldLabel : '班次',
                store : banciStore,
                emptyText : '全部班次',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'BANCI',
                name : 'BANCI',
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            }],
            action : 'ZZCJ_QUERY_TAB_STOP_LINE_ACTION',
            outputTable : 'TAB_STOP_LINE'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : 'ID',
                dataIndex : 'ID',
                width : 80
            }, {
                header : '工段',
                dataIndex : 'EventData46',
                width : 90
            },{
                header : '工段名字',
                dataIndex : 'EventData40',
                width : 150
            },{
                header : '班次',
                dataIndex : 'BANCI',
                width : 70,
                renderer : function(value){
                	if(value==1){
                		return '一班';
                	}
                	if(value==2){
                		return '二班';
                	}
                	if(value==3){
                		return '三班';
                	}
                	return '一班';
                }
            },{
                header : '发生时间',
                dataIndex : 'EventDate1',
                width : 130,
                renderer: function(value){
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '恢复时间',
                dataIndex : 'EventDate2',
                width : 130,
                renderer: function(value){
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '停线时间(秒)',
                dataIndex : 'StopTimeTotal',
                width : 85
            }, {
                header : '停线原因',
                dataIndex : 'StopReson',
                css : 'background: #00FA9A;', 
                width : 90,
                editor: {
                    xtype: "textfield",
                    selectOnFocus: true
                }
            }, {
                header : '备注',
                dataIndex : 'EventData50',
                css : 'background: #00FA9A;', 
                width : 250,
                editor: {
                    xtype: "textfield",
                    selectOnFocus: true
                }
            }, {
                header : '责任部门',
                dataIndex : 'EventData51',
                width : 70,
                css : 'background: #00FA9A;',
                renderer : function(value){
                	return ht.pub.getValueExactByKey(responsStore, 'VALUE', value, 'TEXT');
                },
                editor: {
                    xtype : 'combo',
	                name : 'EventData51',
					fieldLabel : '责任部门',
	                width : 70,
	                store : responsStore,
	                emptyText : ht.msg.combo.emptyText,
	                mode : 'local',
	                triggerAction : 'all',
	                valueField : 'VALUE',
	                displayField : 'TEXT',
	                hiddenName : 'EventData51',
	                editable : true,
	                enableByRowSelected : false,
	                ingoreSeparator : true
                }
            }, {
                header : '最后操作人',
                dataIndex : 'EventData53',
                width : 90
            },{
                header : '最后操作时间',
                dataIndex : 'EventData54',
                width : 135,
                renderer : function(value){
                	return ht.pub.date.dateTimeRenderer(value);
                }               
            }],
            clicksToEdit: 1,
            isPageAction : true,
            storeFields : ['ID','EventDate1','EventDate2','BANCI','StopTimeTotal','EventData50',
           				'EventData46', 'EventData40', 'EventData1', 'StopReson','EventData51',
        				'EventData53','EventData54']
        }
    });
    
    //导入计划日期数量
    var importFileFn = function(field){
        var params = {};
        params['action'] = 'SAVE_PMC_DATE_IMPORT_ACTION';
        params['FACTORY'] = '上汽';
        params['WORKSHOP'] = '车间';
        
        ht.pub.wait('正在导入中...');
        Ext.Ajax.request({
            form: Ext.getDom(field.id).parentNode,
            url: 'json',
            method: 'post',
            params: params,
            isUpload: true,
            success : function(response, options) {
                var result = Ext.decode(response.responseText)
                if(result.success){    
                    if (result.root['PMC_DATE_IMPORT_INFO'].rs[0] == ht.pub.global.YESNO.YES) { 
                        ht.pub.info('导入成功。');
                        
                        workTimePanel.ht_outputQueryPanel.queryFn();
                    } else {
                        ht.pub.warning('导入失败,'+result.root['PMC_DATE_IMPORT_INFO'].rs+'。');
                    }
                }else {
                    ht.pub.error(result.errors.errmsg);
                }
                
                field.reset();
            }
        });
    }
    
     //导出excel
    var exportFn = function(){
        
        var url = 'json?action=EXPORT_PMC_DATE_IMPORT_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = workTimePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&SHIFT=' + workTimePanel.ht_outputStore.baseParams['SHIFT'];
        url += '&START_PLAN_DATE=' + workTimePanel.ht_outputStore.baseParams['START_PLAN_DATE'];
        url += '&END_PLAN_DATE=' + workTimePanel.ht_outputStore.baseParams['END_PLAN_DATE'];
        
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
        items : [workTimePanel,exportPanel],
        listeners : {
            beforedestroy : function(){
                shiftStore.destroy();
                sheftQueryStore.destroy();
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
