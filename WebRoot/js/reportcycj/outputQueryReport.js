/**
 * 固定报表 -> 产量查询
 */
(function() {
    var msgResource = {};
    var totolStopTime;
    //新增班次信息
    var bancidata = [["","全部"],["1","1班"],["2","2班"],["3","3班"]];
    var banciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:bancidata,
     fields:["VALUE","TEXT"]
    });
  //新增模具号信息
    var mojudata=[["","全部"]];
    for (var i = 1; i <100; i++) {
    	 
    	mojudata.push([i,i]);
     
    }
    var mojuStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:mojudata,
     fields:["VALUE","TEXT"]
    });
    var setPanelIsLayout;

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
    
    //工段
    var sectionStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_STATION.rs',
        baseParams : {
            action : 'CYCJ_QUERY_PMC_PP_STATION_STOP_ACTION',
            CODE_TYPE : 'PRODUCTIONLINENAME'
        },
        fields : ['VALUE','TEXT']
    });
    sectionStore.load({
        callback : function() {
            ht.pub.store.addBlankText(sectionStore);
        }
    });
    
    //生产时间报表
    var workTimePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            enableQueryButton : true, 
            items : [{
                xtype: 'compositefield',
                fieldLabel: '生产日期',
                name : 'PLAN_DATE',
                columnWidth : 1/2,
                items: [ {
                    xtype : 'datefield',
                    format : ht.config.format.DATE,
                    fieldLabel : ' ',
                    columnWidth : 1/2,
                    name : 'START_PLAN_DATE',
                    //value : new Date(new Date().getTime() - (new Date().format('N') - 1) * 24 * 60 * 60 * 1000),
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
                    format : ht.config.format.DATE,
                    fieldLabel : ' ',
                    columnWidth : 1/2,
                    name : 'END_PLAN_DATE',
                    
//                    value : new Date(),
                    value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000),
                    vtype: 'comparentDate',
                    vtypeText: ht.msg.valid.compareDate,
                    comparentTo: 'START_PLAN_DATE',
                    getParentCompont : function() {
                        return workTimePanel.ht_outputQueryForm.find('name', 'PLAN_DATE')[0].innerCt;
                    }
                }]
            },{
                xtype : 'combo',
                fieldLabel : '工段名字',
                store : sectionStore,
                columnWidth : 1/4,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'EventDate18',
                name : 'EventDate18',
                editable : false
            },{
                xtype : 'combo',
                fieldLabel : '模具号',
                store : mojuStore,
                columnWidth : 1/4,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'mojuhao',
                name : 'mojuhao',
                editable : false
            },{
                xtype : 'combo',
                fieldLabel : '班次',
                store : banciStore,
                emptyText : '全部班次',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'BANCI',
                name : 'BANCI',
                editable : false
                //listeners : {
	                //select : function(c){
	                	//equStopLinePanel.findQueryCompment('BANCI').setValue('wawawa');
	                //}
	            //}
            }],
            action : 'CYCJ_QUERY_tabProductType_ACTION',
            outputTable : 'tabProductType'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工段',
                dataIndex : 'EventData5',
                width : 150
            },{
                header : '工段名字',
                dataIndex : 'EventData6',
                width : 160
            }, {
                header : '班次',
                dataIndex : 'BANCI',
                width : 60,
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
                	return '全部';
                }
            }, {
                header : '模具号',
                dataIndex : 'IP21',
                width : 80
            }, {
                header : '总产量',
                dataIndex : 'productTotal',
                width : 80
            }],
            
            isMultipleSelect : false, 
            isPageAction : false,
            storeFields : ['ID','EventData5','EventData6','BANCI','IP21','IP22', 'IP23','ZP11','BP31','IP24','BP32'
                            ,'AS21','productTotal'],
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 5,
                handler : function() {
                    exportFn();
                } 
            }]
        },
        ctListeners : {
           
        }
    });
    
    //导出excel
    var exportFn = function(){
        var url = 'json?action=CYCJ_EXPORT_TAB_PRODUCT_TYPE_ACTION';
                    
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
        
        url += encodeURI(encodeURI('&EventDate18=' + workTimePanel.ht_outputStore.baseParams['EventDate18']));
        url += '&START_PLAN_DATE=' + workTimePanel.ht_outputStore.baseParams['START_PLAN_DATE'];
        url += '&END_PLAN_DATE=' + workTimePanel.ht_outputStore.baseParams['END_PLAN_DATE'];
        url += '&BANCI=' + workTimePanel.ht_outputStore.baseParams['BANCI'];
        url += '&MOJUHAO=' + workTimePanel.ht_outputStore.baseParams['mojuhao'];
        
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
                sectionStore.destroy();
                shiftStore.destroy();
                sheftQueryStore.destroy();
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
