/**
 * 故障事故记录
 */
(function() {
    var msgResource = {};
    var totolStopTime;
    var queryPanelIsLayout;
    //新增班次信息
    var bancidata = [["","全部"],["1","1班"],["2","2班"],["3","3班"]];
    var banciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:bancidata,
     fields:["VALUE","TEXT"]
    });
    //工位
    var stationStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_STATION.rs',
        baseParams : {
            action : 'CYCJ_QUERY_TAB_REFER_CT_ACTION',
            CODE_TYPE : 'data7',
            QURY_CODE : ''
        },
        fields : ['VALUE','TEXT']
    });
    
    //工段
    var sectionStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_STATION.rs',
        baseParams : {
            action : 'CYCJ_QUERY_PMC_PP_STATION_ACTION',
//            action : 'QUERY_PMC_PP_STATION_CT_ALL_ACTION',
//            CODE_TYPE : 'EventData16'
            CODE_TYPE : 'PRODUCTIONLINENAME'
        },
        fields : ['VALUE','TEXT']
    });
    sectionStore.load({
        callback : function() {
            ht.pub.store.addBlankText(sectionStore);
        }
    });
     
    //故障事故记录
    var faultEventPanel = new ht.base.RowEditorPanel({
        region : 'center',
        height : 200,
        queryFormConfig : {
            enableQueryButton : true, 
            items : [{
                xtype: 'compositefield',
                fieldLabel: '时间',
                name : 'EFFECT_TIME',
                columnWidth : 1/2,
                items: [{
                    xtype : 'datetimefield',
					format : ht.config.format.DATETIME,
					fieldLabel : '开始时间',
                    columnWidth : 1/2,
//					value : new Date(new Date().getTime() - 4 * 24 * 60 * 60 * 1000),
					value : (new Date().getHours()>7 && new Date().getHours()<20)?new Date(new Date().getTime() - ((new Date().getHours()-8)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):((new Date().getHours()<8)?new Date(new Date().getTime() - ((new Date().getHours()+4)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):new Date(new Date().getTime() - ((new Date().getHours()-20)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000))),
					name : 'START_EFFECT_TIME',
					vtype: 'defineValid',
                    vtypeText : 'none',
                    defineValid : function(val, fromField){
                    	if(queryPanelIsLayout && faultEventPanel.findQueryCompment('END_EFFECT_TIME')){
                    		faultEventPanel.findQueryCompment('END_EFFECT_TIME').validate();
                    	}
                    	return true;
                    }
                }, {
                    xtype: 'displayfield',
                    width : 20,
                    style : 'text-align: center;',
                    value : '到'
                },{
                    xtype : 'datetimefield',
					format : ht.config.format.DATETIME,
					fieldLabel : '结束时间',
                    columnWidth : 1/2,
					value : (new Date().getHours()>7 && new Date().getHours()<20)?new Date(new Date().getTime() - ((new Date().getHours()-20)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):((new Date().getHours()<8)?new Date(new Date().getTime() - ((new Date().getHours()-8)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):new Date(new Date().getTime() - ((new Date().getHours()-32)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000))),
					name : 'END_EFFECT_TIME',
					vtype: 'comparentDate',
                    vtypeText: "终止时间应晚于起始时间",
			        comparentTo: 'START_EFFECT_TIME',
				    getParentCompont : function() {
				    	return faultEventPanel.find('name', 'EFFECT_TIME')[0].innerCt;
				    }
                }]
            },{
                xtype : 'combo',
                fieldLabel : '工段名字',
                store : sectionStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                columnWidth : 1/4,
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false,
                listeners : {
	                select : function(c,r,index){
	                    //faultEventPanel.findQueryCompment('DATA7').setValue(null);
	                }
                }
            },{
                xtype : 'combo',
                fieldLabel : '班次',
                columnWidth : 1/4,
                store : banciStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'BANCI',
                name : 'BANCI',
                editable : false,
                listeners : {
                    beforeQuery : function(c){
                        //delete c.combo.lastQuery;
                        //var gd = faultEventPanel.findQueryCompment('PRODUCTIONLINENAME').getValue();
                        //stationStore.baseParams['QURY_TYPE'] = gd;
                        
                    }
                }
            },{ 
                xtype : 'textfield',
                fieldLabel : '属性',
                columnWidth : 1/4,
                name : 'DATA3'
            }],
            action : 'CYCJ_QUERY_ALARM_LOG_ACTION',
            outputTable : 'ALARM_LOG'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : 'ID',
                dataIndex : 'ID',
                width : 150,
                hidden : true,
                editor : {
                    xtype : 'textfield',
                    name : 'ID'
                }
            },{
                header : '报警时间',
                dataIndex : 'GENERATION_TIME',
                width : 140,
                renderer : function(value){
                	return ht.pub.date.dateTimeRenderer(value);
                },
                editor : {
                    xtype : 'datetimefield',
                    format : ht.config.format.DATETIME,
                    name : 'GENERATION_TIME',
                    updateable : true
                }
            }, {
                header : '恢复时间',
                dataIndex : 'TIMESTAMP',
                width : 140,
                renderer : function(value){
                	return ht.pub.date.dateTimeRenderer(value);
                },
                editor : {
                    xtype : 'datetimefield',
                    format : ht.config.format.DATETIME,
                    name : 'TIMESTAMP',
                    updateable : true
                }
            }, {
                header : '历时（秒）',
                dataIndex : 'WORKTIME',
                width : 80
            }, {
                header : '班次',
                dataIndex : 'BANCI',
                width : 60,
                renderer : function(value){
                	if(value==2){
                		return '二班';
                	}
                	if(value==3){
                		return '三班';
                	}
                	return '一班';
                }
            },{
                header : '报警详细内容',
                dataIndex : 'ALARM_MESSAGE',
                width : 120,
                editor : {
                    xtype : 'textfield',
                    name : 'ALARM_MESSAGE'
                }
            }, {
                header : '工段',
                dataIndex : 'DATA1',
                width : 70
            },{
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 130,
                renderer : function(value){
                    return ht.pub.getValuesExactByKeyDefault(sectionStore,value)
                }
            }, {
                header : '属性',
                dataIndex : 'DATA3',
                width : 70,
                editor : {
                    xtype : 'textfield',
                    name : 'DATA3'
                }
            }],

            isPageAction : true,
            isMultipleSelect : false, 
            storeFields : ['ID','PRODUCTIONLINENAME','WORKTIME','GENERATION_TIME','TIMESTAMP','BANCI','ALARM_MESSAGE','RESOURCE','DATA1','DATA2','DATA3','DATA7'],
            updateBtn : {
                action : 'CYCJ_SAVE_FAULT_EVENT_ACTION',
                tagValue : ''
            },
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    exportFn();
                } 
            }]
        },
        ctListeners : {
        },
	    listeners : {
            afterLayout : function(){
                queryPanelIsLayout = true;
            }
        }
    });
    
    //导出excel
    var exportFn = function(){
        
        var url = 'json?action=CYCJ_EXPORT_ALARM_LOG_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = faultEventPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&START_EFFECT_TIME=' + faultEventPanel.ht_outputStore.baseParams['START_EFFECT_TIME'];
        url += '&END_EFFECT_TIME=' + faultEventPanel.ht_outputStore.baseParams['END_EFFECT_TIME'];
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + faultEventPanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        url += '&DATA7=' + faultEventPanel.ht_outputStore.baseParams['DATA7'];
        url += '&BANCI=' + faultEventPanel.ht_outputStore.baseParams['BANCI'];
        url += encodeURI(encodeURI('&DATA3=' + faultEventPanel.ht_outputStore.baseParams['DATA3']));
        
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
        items : [faultEventPanel,exportPanel],
        listeners : {
            beforedestroy : function(){
                backPanel.removeAll(true);
            }   
        } 
    });
    
    return backPanel;
})();
