/**
 * 在停线时间内的故障记录表
 */
(function() {
    var msgResource = {};
    var totolStopTime;
    var queryPanelIsLayout;
    //新增班次信息
    var bancidata = [["","全部"],["1","1班"],["2","2班"]];
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
            action : 'ZZCJ_QUERY_TAB_REFER_CT_ACTION',
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
            action : 'ZZCJ_QUERY_PMC_PP_STATION_STOP_ACTION',
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
    var faultEventPanel = new ht.base.PlainPanel({
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
					value : (new Date().getHours()>7 && new Date().getHours()<19)?new Date(new Date().getTime() - ((new Date().getHours()-8)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):((new Date().getHours()<8)?new Date(new Date().getTime() - ((new Date().getHours()+4)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):new Date(new Date().getTime() - ((new Date().getHours()-19)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000))),
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
					value : (new Date().getHours()>7 && new Date().getHours()<19)?new Date(new Date().getTime() - ((new Date().getHours()-19)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):((new Date().getHours()<8)?new Date(new Date().getTime() - ((new Date().getHours()-8)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):new Date(new Date().getTime() - ((new Date().getHours()-32)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000))),
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
	                    faultEventPanel.findQueryCompment('DATA7').setValue(null);
	                }
                }
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
            }/*,{ 
                xtype : 'textfield',
                fieldLabel : '属性',
                columnWidth : 1/2,
                name : 'DATA3'
            }*/],
            action : 'ZZCJ_QUERY_PP_ALARM_LOG_ACTION',
            outputTable : 'ALARM_LOG'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工段',
                dataIndex : 'PRODUCTION',
                width : 70
            },{
                header : '工段名字',
                dataIndex : 'PRODUCTIONNAME',
                width : 130
            },{
                header : '班次',
                dataIndex : 'BANCI',
                width : 70,
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
                header : '停线开始时间',
                dataIndex : 'STOPSTARTTIME',
                width : 140,
                renderer : function(value){
                	return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '停线结束时间',
                dataIndex : 'STOPENDTIME',
                width : 140,
                renderer : function(value){
                	return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '停线历时（秒）',
                dataIndex : 'STOPTIME',
                width : 90
            }, {
                header : '停线原因',
                dataIndex : 'STOPRESON',
                width : 80
            },{
                header : '报警开始时间',
                dataIndex : 'ALARMSTARTTIME',
                width : 140,
                renderer : function(value){
                	if(value<0){
                		return "";
                	}
                	return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '报警结束时间',
                dataIndex : 'ALARMENDTIME',
                width : 140,
                renderer : function(value){
                	if(value<0){
                		return "";
                	}
                	return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '报警历时（秒）',
                dataIndex : 'ALARMTIME',
                width : 90
            },{
                header : '报警详细内容',
                dataIndex : 'ALARMMSG',
                width : 120
            }, {
                header : '属性',
                dataIndex : 'ATTRBUTE',
                width : 70
            }],

            isPageAction : true,
            isMultipleSelect : false, 
            storeFields : ['PRODUCTION','PRODUCTIONNAME','BANCI','STOPSTARTTIME','STOPENDTIME','STOPTIME','STOPRESON',
            				'ALARMSTARTTIME','ALARMENDTIME','ALARMTIME','ALARMMSG','EQUIPMENT','ATTRBUTE'],
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
        
        var url = 'json?action=ZZCJ_EXPORT_PP_ALARM_LOG_ACTION';
                    
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
//        url += encodeURI(encodeURI('&DATA3=' + faultEventPanel.ht_outputStore.baseParams['DATA3']));
        
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
