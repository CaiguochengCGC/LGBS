/**
 * 固定报表 -> 工位报警报表
 */
(function() {
    var queryType=[['','全部'],['1','冲压车间'],['2','车身车间']];
    var weekDefault;
    //新增班次信息
    var bancidata = [["","全部"],["1","1班"],["2","2班"]];
    var banciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:bancidata,
     fields:["VALUE","TEXT"]
    });

    //报表级别
    var toltlagStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'TOLTLAG'
        },
        fields : ['VALUE','TEXT']
    });
    toltlagStore.load();
    
    //周
    var weekStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'WEEK'
        },
        fields : ['VALUE','TEXT']
    });
    weekDefault = new Date().getWeekOfYear()-1;
    for(var i = 1 ; i <= 52; i++){
       weekStore.add(new Ext.data.Record({
            VALUE : i-1,
            TEXT : '第' + i + '周'
       })) 
    }



    //获得工段注释
    var lineComment = new Ext.data.JsonStore({
        url : 'json',
        root : "root.GET_COMMENT.rs",
        baseParams : {
            action : 'QUERY_LINECODE_COMMENT',
            CODE_TYPE : 'COMMENT'
        },
        fields : ['VALUE','TEXT']
    });

    lineComment.load({
        callback: function () {
            ht.pub.store.addBlankText(lineComment);
        }
    });
    
    //默认为第一个值
    weekStore.on('load',function(store,record,opts){
        if(weekStore.getCount() > 0){
            var firstValue = weekStore.getAt(0).get('TEXT');
            weekStore.findCompment('TEXT').setValue(firstValue);//选中
        }
    });
    
    //班次
    var shiftStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'SHIFT'
        },
        fields : ['VALUE','TEXT']
    });
    shiftStore.load();
    
    //工段名字
    var sectionStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_STATION.rs',
        baseParams : {
            action : 'QUERY_PMC_PP_STATION_ALL_ACTION',
            CODE_TYPE : 'EventData7'
        },
        fields : ['VALUE','TEXT']
    });
    sectionStore.load({
        callback : function() {
            ht.pub.store.addBlankText(sectionStore);
        }
    });
    
    //工位名称
    var stationStore = new Ext.data.JsonStore({
    	url : 'json',
        root : "root.PMC_PP_STATION.rs",
        baseParams : {
            action : 'QUERY_TAB_STOP_STA_ALL_ACTION',
            CODE_TYPE : 'DATA7',
            QURY_TYPE : 'null'
        },
        fields : ['VALUE','TEXT']
    });
    
    
    //计划日期面板
    var planDatePanel = new ht.base.PlainPanel({
        region : 'north',
        height : 90,
        queryFormConfig : {
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'WORKDATE',
                format : ht.config.format.DATE,
                value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
            },{
                xtype : 'combo',
                fieldLabel : '工段名字',
                store : lineComment,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false,
                listeners:{
                    select : function(combo, record,index){
                        stationStore.value = "";
                        stationStore.baseParams.QURY_TYPE=combo.value;
                        stationStore.load();

                    }
                }
            },{
                xtype : 'combo',
                fieldLabel : '工位',
                name : 'STATION',
                store : stationStore,
                editable : true,
                allowBlank : true,
                emptyText : '请先选择工段',
                mode : 'remote',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'STATION'

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
            },{
                xtype : 'combo',
                fieldLabel : '车间查询',
                store : queryType,
                emptyText : '全部车间',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'queryType',
                name : 'queryType',
                editable : false
            }]
        },
        ctListeners : {
            beforeQuery : function(values){
            	var errMsg = '';
                stopLinePanel.ht_outputStore.baseParams['PPDATE'] = values['WORKDATE'];
                stopLinePanel.ht_outputStore.baseParams['PRODUCTIONLINENAME'] = values['PRODUCTIONLINENAME'];
                stopLinePanel.ht_outputStore.baseParams['STATION'] = values['STATION'];
                stopLinePanel.ht_outputStore.baseParams['EQUIPMENTSTOP'] = values['EQUIPMENTSTOP'];
                stopLinePanel.ht_outputStore.baseParams['BANCI'] = values['BANCI'];
                stopLinePanel.ht_outputStore.baseParams['QUERY_TYPE'] = '0';
                stopLinePanel.ht_outputStore.load();
                return true;
            }
        }
    
    });

    //报警日报表
    var stopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'PPDATE',
                format : ht.config.format.DATE,
                value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
            },{
                xtype : 'combo',
                fieldLabel : '工段名字',
                store : lineComment,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false,
                listeners:{
                    select : function(combo, record,index){
                        stationStore.value = "";
                        stationStore.baseParams.QURY_TYPE=combo.value;
                        stationStore.load();

                    }
                }
            },{
                xtype : 'combo',
                fieldLabel : '工位',
                name : 'STATION',
                store : stationStore,
                editable : true,
                allowBlank : true,
                emptyText : '请先选择工段',
                mode : 'remote',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'STATION'

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
            },{
                xtype : 'combo',
                fieldLabel : '车间查询',
                store : queryType,
                emptyText : '全部车间',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'queryType',
                name : 'queryType',
                editable : false
            }],
            action : 'QUERY_PMC_EQUIPMENT_STOP_ACTION',
            outputTable : 'PMC_EQUIPMENT_STOP'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            },{
                header : '工位',
                dataIndex : 'STATION',
                width : 100
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
                header : '报警总时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '报警总次数',
                dataIndex : 'STOPCOUNT',
                width : 100
            },{
                header : 'ANDON',
                dataIndex : 'DATA8',
                width : 100
            },{
                header : 'APC故障',
                dataIndex : 'DATA9',
                width : 100
            },{
                header : 'AVI故障',
                dataIndex : 'DATA10',
                width : 100
            },{
                header : 'CO2焊故障',
                dataIndex : 'DATA11',
                width : 100
            },{
                header : 'DNET故障',
                dataIndex : 'DATA12',
                width : 100
            },{
                header : 'PLC故障',
                dataIndex : 'DATA13',
                width : 100
            },{
                header : '安全设备故障',
                dataIndex : 'DATA14',
                width : 100
            },{
                header : '冲压设备故障',
                dataIndex : 'DATA15',
                width : 100
            },{
                header : '电源故障',
                dataIndex : 'DATA16',
                width : 100
            },{
                header : '定位销故障',
                dataIndex : 'DATA17',
                width : 100
            },{
                header : '阀岛故障',
                dataIndex : 'DATA18',
                width : 100
            },{
                header : '焊机故障',
                dataIndex : 'DATA19',
                width : 100
            },{
                header : '焊枪故障',
                dataIndex : 'DATA20',
                width : 100
            },{
                header : '换枪盘故障',
                dataIndex : 'DATA21',
                width : 100
            },{
                header : '机器人',
                dataIndex : 'DATA22',
                width : 100
            },{
                header : '激光焊设备故障',
                dataIndex : 'DATA23',
                width : 100
            },{
                header : '急停',
                dataIndex : 'DATA24',
                width : 100
            },{
                header : '夹紧翻转缸故障',
                dataIndex : 'DATA25',
                width : 100
            },{
                header : '冷却水故障',
                dataIndex : 'DATA26',
                width : 100
            },{
                header : '螺柱焊故障',
                dataIndex : 'DATA27',
                width : 100
            },{
                header : '模块故障',
                dataIndex : 'DATA28',
                width : 100
            },{
                header : '气压低故障',
                dataIndex : 'DATA29',
                width : 100
            },{
                header : '视觉设备故障',
                dataIndex : 'DATA30',
                width : 100
            },{
                header : '输送设备传感器故障',
                dataIndex : 'DATA31',
                width : 100
            },{
                header : '输送设备故障',
                dataIndex : 'DATA32',
                width : 100
            },{
                header : '停放架故障',
                dataIndex : 'DATA33',
                width : 100
            },{
                header : '涂胶设备故障',
                dataIndex : 'DATA34',
                width : 100
            },{
                header : '物料传感器故障',
                dataIndex : 'DATA35',
                width : 100
            },{
                header : '修磨器故障',
                dataIndex : 'DATA36',
                width : 100
            },{
                header : '以太网故障',
                dataIndex : 'DATA37',
                width : 100
            },{
                header : '在线测量',
                dataIndex : 'DATA38',
                width : 100
            },{
                header : '转台故障',
                dataIndex : 'DATA39',
                width : 100
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PRODUCTIONLINE','STATION','PRODUCTIONLINENAME', 'STOPTIME', 'STOPCOUNT','DATA8','DATA9','DATA10','DATA11','DATA12','DATA13',
                        'DATA14','DATA15','DATA16','DATA17','DATA18','DATA19','DATA20','DATA21','DATA22','DATA23','DATA24','DATA25',
                        'DATA26','DATA27','DATA28','DATA29','DATA30','DATA31','DATA32','DATA33','DATA34','DATA35','DATA36','DATA37','DATA38','DATA39','BANCI'],
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 4,
                handler : function() {
                    exportFn();
                } 
            }]
        },
        ctListeners : {
           beforeQuery :function(values){
                values['QUERY_TYPE'] = '0';
                return true;
           }
           
        }
    });
    
    //导出excel
    var exportFn = function(){
        
        var url = 'json?action=EXPORT_PMC_EQUIPMENT_STOP_ACTION';
                    
        var params = {};
        params['WIDTH'] = new Array();
        
        var columns = stopLinePanel.gridConfig.columns;
        if (columns != null){
	        for (var i = 0; i < columns.length; i++) {
	            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
	                continue;
	            }
	            params['WIDTH'].push('WIDTH=' + columns[i].width);
	        }
        }
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + stopLinePanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        url += '&PPDATE=' + stopLinePanel.ht_outputStore.baseParams['PPDATE'];
        url += '&BANCI=' + stopLinePanel.ht_outputStore.baseParams['BANCI'];
        url += '&STATION=' + stopLinePanel.ht_outputStore.baseParams['STATION'];
        url += '&QUERY_TYPE=' + stopLinePanel.ht_outputStore.baseParams['QUERY_TYPE'];
        url += '&queryType=' + stopLinePanel.ht_outputStore.baseParams['queryType'];
        url += encodeURI(encodeURI('&EQUIPMENTSTOP=' + stopLinePanel.ht_outputStore.baseParams['EQUIPMENTSTOP']));
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
    
    //报警周报表
    var weekStopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype : 'combo',
                fieldLabel : '日期(周)',
                store : weekStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PPDATE',
                name : 'PPDATE',
                value : weekDefault,
                editable : false
            },{
                xtype : 'combo',
                fieldLabel : '工段名字',
                store : lineComment,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false,
                listeners : {
                	select : function(c,r,index){
                        weekStopLinePanel.findQueryCompment('STATION').setValue(null);
                        weekStopLinePanel.findQueryCompment('EQUIPMENTSTOP').setValue(null);
                    }
                }
            },{
                xtype : 'combo',
                fieldLabel : '工位',
                name : 'STATION',
                store : stationStore,
                editable : true,
                allowBlank : true,
                emptyText : '请选择',
                mode : 'remote',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'STATION',
                listeners : {
                    beforeQuery : function(c){
                        delete c.combo.lastQuery;
                        stationStore.baseParams['QURY_TYPE'] = weekStopLinePanel.findQueryCompment('PRODUCTIONLINENAME').getValue();
                    },
                    select : function(c,r,index){
                        weekStopLinePanel.findQueryCompment('EQUIPMENTSTOP').setValue(null);
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
            },{
                xtype : 'combo',
                fieldLabel : '车间查询',
                store : queryType,
                emptyText : '全部车间',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'queryType',
                name : 'queryType',
                editable : false
            }],
            action : 'QUERY_PMC_EQUIPMENT_STOP_ACTION',
            outputTable : 'PMC_EQUIPMENT_STOP'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            }, {
                header : '工位',
                dataIndex : 'STATION',
                width : 100
            },{
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
                header : '报警总时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '报警总次数',
                dataIndex : 'STOPCOUNT',
                width : 100
            },{
                header : 'ANDON',
                dataIndex : 'DATA8',
                width : 100
            },{
                header : 'APC故障',
                dataIndex : 'DATA9',
                width : 100
            },{
                header : 'AVI故障',
                dataIndex : 'DATA10',
                width : 100
            },{
                header : 'CO2焊故障',
                dataIndex : 'DATA11',
                width : 100
            },{
                header : 'DNET故障',
                dataIndex : 'DATA12',
                width : 100
            },{
                header : 'PLC故障',
                dataIndex : 'DATA13',
                width : 100
            },{
                header : '安全设备故障',
                dataIndex : 'DATA14',
                width : 100
            },{
                header : '冲压设备故障',
                dataIndex : 'DATA15',
                width : 100
            },{
                header : '电源故障',
                dataIndex : 'DATA16',
                width : 100
            },{
                header : '定位销故障',
                dataIndex : 'DATA17',
                width : 100
            },{
                header : '阀岛故障',
                dataIndex : 'DATA18',
                width : 100
            },{
                header : '焊机故障',
                dataIndex : 'DATA19',
                width : 100
            },{
                header : '焊枪故障',
                dataIndex : 'DATA20',
                width : 100
            },{
                header : '换枪盘故障',
                dataIndex : 'DATA21',
                width : 100
            },{
                header : '机器人',
                dataIndex : 'DATA22',
                width : 100
            },{
                header : '激光焊设备故障',
                dataIndex : 'DATA23',
                width : 100
            },{
                header : '急停',
                dataIndex : 'DATA24',
                width : 100
            },{
                header : '夹紧翻转缸故障',
                dataIndex : 'DATA25',
                width : 100
            },{
                header : '冷却水故障',
                dataIndex : 'DATA26',
                width : 100
            },{
                header : '螺柱焊故障',
                dataIndex : 'DATA27',
                width : 100
            },{
                header : '模块故障',
                dataIndex : 'DATA28',
                width : 100
            },{
                header : '气压低故障',
                dataIndex : 'DATA29',
                width : 100
            },{
                header : '视觉设备故障',
                dataIndex : 'DATA30',
                width : 100
            },{
                header : '输送设备传感器故障',
                dataIndex : 'DATA31',
                width : 100
            },{
                header : '输送设备故障',
                dataIndex : 'DATA32',
                width : 100
            },{
                header : '停放架故障',
                dataIndex : 'DATA33',
                width : 100
            },{
                header : '涂胶设备故障',
                dataIndex : 'DATA34',
                width : 100
            },{
                header : '物料传感器故障',
                dataIndex : 'DATA35',
                width : 100
            },{
                header : '修磨器故障',
                dataIndex : 'DATA36',
                width : 100
            },{
                header : '以太网故障',
                dataIndex : 'DATA37',
                width : 100
            },{
                header : '在线测量',
                dataIndex : 'DATA38',
                width : 100
            },{
                header : '转台故障',
                dataIndex : 'DATA39',
                width : 100
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PRODUCTIONLINE','PRODUCTIONLINENAME','STATION', 'STOPTIME', 'STOPCOUNT','DATA8','DATA9','DATA10','DATA11','DATA12','DATA13',
                        'DATA14','DATA15','DATA16','DATA17','DATA18','DATA19','DATA20','DATA21','DATA22','DATA23','DATA24','DATA25',
                        'DATA26','DATA27','DATA28','DATA29','DATA30','DATA31','DATA32','DATA33','DATA34','DATA35','DATA36','DATA37','DATA38','DATA39','BANCI'],        

            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    exportWeekFn();
                } 
            }]
        },
        ctListeners : {
           beforeQuery :function(values){
                values['QUERY_TYPE'] = '3';
                return true;
           }
        }
    });
    
    //导出excel
    var exportWeekFn = function(){

        var url = 'json?action=EXPORT_PMC_EQUIPMENT_STOP_ACTION';
                    
        var params = {};
        params['WIDTH'] = new Array();
        
        var columns = weekStopLinePanel.gridConfig.columns;
        if (columns != null){
	        for (var i = 0; i < columns.length; i++) {
	            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
	                continue;
	            }
	            params['WIDTH'].push('WIDTH=' + columns[i].width);
	        }
        }
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + weekStopLinePanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        url += '&PPDATE=' + weekStopLinePanel.ht_outputStore.baseParams['PPDATE'];
        url += '&BANCI=' + weekStopLinePanel.ht_outputStore.baseParams['BANCI'];
        url += '&STATION=' + weekStopLinePanel.ht_outputStore.baseParams['STATION'];
        url += '&QUERY_TYPE=' + weekStopLinePanel.ht_outputStore.baseParams['QUERY_TYPE'];
        url += '&queryType=' + weekStopLinePanel.ht_outputStore.baseParams['queryType'];
        url += encodeURI(encodeURI('&EQUIPMENTSTOP=' + weekStopLinePanel.ht_outputStore.baseParams['EQUIPMENTSTOP']));
        url += '&' + params['WIDTH'].join('&');
        
        exportWeekPanel.setSrc(url);
    }

    //导出面板
    var exportWeekPanel = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
    //报警月报表
    var monthStopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype:'datefield',
                name : 'PPDATE',
                fieldLabel : '月份',
                value : new Date(),
                format : 'Y-m',
                renderer : function(value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            },{
                xtype : 'combo',
                fieldLabel : '工段名字',
                store : lineComment,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false,
                listeners : {
                	select : function(c,r,index){
                        monthStopLinePanel.findQueryCompment('STATION').setValue(null);
                        monthStopLinePanel.findQueryCompment('EQUIPMENTSTOP').setValue(null);
                    }
                }
            },{
                xtype : 'combo',
                fieldLabel : '工位',
                name : 'STATION',
                store : stationStore,
                editable : true,
                allowBlank : true,
                emptyText : '请选择',
                mode : 'remote',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'STATION',
                listeners : {
                    beforeQuery : function(c){
                        delete c.combo.lastQuery;
                        stationStore.baseParams['QURY_TYPE'] = monthStopLinePanel.findQueryCompment('PRODUCTIONLINENAME').getValue();
                    },
                    select : function(c,r,index){
                        monthStopLinePanel.findQueryCompment('EQUIPMENTSTOP').setValue(null);
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
            },{
                xtype : 'combo',
                fieldLabel : '车间查询',
                store : queryType,
                emptyText : '全部车间',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'queryType',
                name : 'queryType',
                editable : false
            }],
            action : 'QUERY_PMC_EQUIPMENT_STOP_ACTION',
            outputTable : 'PMC_EQUIPMENT_STOP'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            },{
                header : '工位',
                dataIndex : 'STATION',
                width : 100
            },{
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
                header : '报警总时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '报警总次数',
                dataIndex : 'STOPCOUNT',
                width : 100
            },{
                header : 'ANDON',
                dataIndex : 'DATA8',
                width : 100
            },{
                header : 'APC故障',
                dataIndex : 'DATA9',
                width : 100
            },{
                header : 'AVI故障',
                dataIndex : 'DATA10',
                width : 100
            },{
                header : 'CO2焊故障',
                dataIndex : 'DATA11',
                width : 100
            },{
                header : 'DNET故障',
                dataIndex : 'DATA12',
                width : 100
            },{
                header : 'PLC故障',
                dataIndex : 'DATA13',
                width : 100
            },{
                header : '安全设备故障',
                dataIndex : 'DATA14',
                width : 100
            },{
                header : '冲压设备故障',
                dataIndex : 'DATA15',
                width : 100
            },{
                header : '电源故障',
                dataIndex : 'DATA16',
                width : 100
            },{
                header : '定位销故障',
                dataIndex : 'DATA17',
                width : 100
            },{
                header : '阀岛故障',
                dataIndex : 'DATA18',
                width : 100
            },{
                header : '焊机故障',
                dataIndex : 'DATA19',
                width : 100
            },{
                header : '焊枪故障',
                dataIndex : 'DATA20',
                width : 100
            },{
                header : '换枪盘故障',
                dataIndex : 'DATA21',
                width : 100
            },{
                header : '机器人',
                dataIndex : 'DATA22',
                width : 100
            },{
                header : '激光焊设备故障',
                dataIndex : 'DATA23',
                width : 100
            },{
                header : '急停',
                dataIndex : 'DATA24',
                width : 100
            },{
                header : '夹紧翻转缸故障',
                dataIndex : 'DATA25',
                width : 100
            },{
                header : '冷却水故障',
                dataIndex : 'DATA26',
                width : 100
            },{
                header : '螺柱焊故障',
                dataIndex : 'DATA27',
                width : 100
            },{
                header : '模块故障',
                dataIndex : 'DATA28',
                width : 100
            },{
                header : '气压低故障',
                dataIndex : 'DATA29',
                width : 100
            },{
                header : '视觉设备故障',
                dataIndex : 'DATA30',
                width : 100
            },{
                header : '输送设备传感器故障',
                dataIndex : 'DATA31',
                width : 100
            },{
                header : '输送设备故障',
                dataIndex : 'DATA32',
                width : 100
            },{
                header : '停放架故障',
                dataIndex : 'DATA33',
                width : 100
            },{
                header : '涂胶设备故障',
                dataIndex : 'DATA34',
                width : 100
            },{
                header : '物料传感器故障',
                dataIndex : 'DATA35',
                width : 100
            },{
                header : '修磨器故障',
                dataIndex : 'DATA36',
                width : 100
            },{
                header : '以太网故障',
                dataIndex : 'DATA37',
                width : 100
            },{
                header : '在线测量',
                dataIndex : 'DATA38',
                width : 100
            },{
                header : '转台故障',
                dataIndex : 'DATA39',
                width : 100
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PRODUCTIONLINE','PRODUCTIONLINENAME','STATION', 'STOPTIME', 'STOPCOUNT','DATA8','DATA9','DATA10','DATA11','DATA12','DATA13',
                        'DATA14','DATA15','DATA16','DATA17','DATA18','DATA19','DATA20','DATA21','DATA22','DATA23','DATA24','DATA25',
                        'DATA26','DATA27','DATA28','DATA29','DATA30','DATA31','DATA32','DATA33','DATA34','DATA35','DATA36','DATA37','DATA38','DATA39','BANCI'],
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    exportMonthFn();
                } 
            }]
        },
        ctListeners : {
           beforeQuery :function(values){
                values['QUERY_TYPE'] = '1';
                return true;
            }
        }
    });
    
    //导出excel
    var exportMonthFn = function(){
        
        var url = 'json?action=EXPORT_PMC_EQUIPMENT_STOP_ACTION';
                    
        var params = {};
        params['WIDTH'] = new Array();
        
        var columns = monthStopLinePanel.gridConfig.columns;
        if (columns != null){
	        for (var i = 0; i < columns.length; i++) {
	            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
	                continue;
	            }
	            
	            params['WIDTH'].push('WIDTH=' + columns[i].width);
	        }
        }
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + monthStopLinePanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        url += '&PPDATE=' + monthStopLinePanel.ht_outputStore.baseParams['PPDATE'];
        url += '&BANCI=' + monthStopLinePanel.ht_outputStore.baseParams['BANCI'];
        url += '&STATION=' + monthStopLinePanel.ht_outputStore.baseParams['STATION'];
        url += '&queryType=' + monthStopLinePanel.ht_outputStore.baseParams['queryType'];
        url += '&QUERY_TYPE=' + monthStopLinePanel.ht_outputStore.baseParams['QUERY_TYPE'];
        url += '&' + params['WIDTH'].join('&');
        
        exportMonthPanel.setSrc(url);
    }

    //导出面板
    var exportMonthPanel = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
    //报警年报表
    var yearStopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype:'datefield',
                name : 'PPDATE',
                fieldLabel : '年份',
                value : new Date(),
                format : 'Y'
            },{
                xtype : 'combo',
                fieldLabel : '工段名字',
                store : lineComment,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false,
                listeners : {
                	select : function(c,r,index){
                        yearStopLinePanel.findQueryCompment('STATION').setValue(null);
                        yearStopLinePanel.findQueryCompment('EQUIPMENTSTOP').setValue(null);
                    }
                }
            },{
                xtype : 'combo',
                fieldLabel : '工位',
                name : 'STATION',
                store : stationStore,
                editable : true,
                allowBlank : true,
                emptyText : '请选择',
                mode : 'remote',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'STATION',
                listeners : {
                    beforeQuery : function(c){
                        delete c.combo.lastQuery;
                        stationStore.baseParams['QURY_TYPE'] = yearStopLinePanel.findQueryCompment('PRODUCTIONLINENAME').getValue();
                    },
                    select : function(c,r,index){
                        yearStopLinePanel.findQueryCompment('EQUIPMENTSTOP').setValue(null);
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
            },{
                xtype : 'combo',
                fieldLabel : '车间查询',
                store : queryType,
                emptyText : '全部车间',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'queryType',
                name : 'queryType',
                editable : false
            }],
            action : 'QUERY_PMC_EQUIPMENT_STOP_ACTION',
            outputTable : 'PMC_EQUIPMENT_STOP'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            },{
                header : '工位',
                dataIndex : 'STATION',
                width : 100
            },{
                header : '班次',
                dataIndex : 'BANCI',
                width : 60,
                renderer : function(value){
                	debugger;
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
                header : '报警总时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '报警总次数',
                dataIndex : 'STOPCOUNT',
                width : 100
            },{
                header : 'ANDON',
                dataIndex : 'DATA8',
                width : 100
            },{
                header : 'APC故障',
                dataIndex : 'DATA9',
                width : 100
            },{
                header : 'AVI故障',
                dataIndex : 'DATA10',
                width : 100
            },{
                header : 'CO2焊故障',
                dataIndex : 'DATA11',
                width : 100
            },{
                header : 'DNET故障',
                dataIndex : 'DATA12',
                width : 100
            },{
                header : 'PLC故障',
                dataIndex : 'DATA13',
                width : 100
            },{
                header : '安全设备故障',
                dataIndex : 'DATA14',
                width : 100
            },{
                header : '冲压设备故障',
                dataIndex : 'DATA15',
                width : 100
            },{
                header : '电源故障',
                dataIndex : 'DATA16',
                width : 100
            },{
                header : '定位销故障',
                dataIndex : 'DATA17',
                width : 100
            },{
                header : '阀岛故障',
                dataIndex : 'DATA18',
                width : 100
            },{
                header : '焊机故障',
                dataIndex : 'DATA19',
                width : 100
            },{
                header : '焊枪故障',
                dataIndex : 'DATA20',
                width : 100
            },{
                header : '换枪盘故障',
                dataIndex : 'DATA21',
                width : 100
            },{
                header : '机器人',
                dataIndex : 'DATA22',
                width : 100
            },{
                header : '激光焊设备故障',
                dataIndex : 'DATA23',
                width : 100
            },{
                header : '急停',
                dataIndex : 'DATA24',
                width : 100
            },{
                header : '夹紧翻转缸故障',
                dataIndex : 'DATA25',
                width : 100
            },{
                header : '冷却水故障',
                dataIndex : 'DATA26',
                width : 100
            },{
                header : '螺柱焊故障',
                dataIndex : 'DATA27',
                width : 100
            },{
                header : '模块故障',
                dataIndex : 'DATA28',
                width : 100
            },{
                header : '气压低故障',
                dataIndex : 'DATA29',
                width : 100
            },{
                header : '视觉设备故障',
                dataIndex : 'DATA30',
                width : 100
            },{
                header : '输送设备传感器故障',
                dataIndex : 'DATA31',
                width : 100
            },{
                header : '输送设备故障',
                dataIndex : 'DATA32',
                width : 100
            },{
                header : '停放架故障',
                dataIndex : 'DATA33',
                width : 100
            },{
                header : '涂胶设备故障',
                dataIndex : 'DATA34',
                width : 100
            },{
                header : '物料传感器故障',
                dataIndex : 'DATA35',
                width : 100
            },{
                header : '修磨器故障',
                dataIndex : 'DATA36',
                width : 100
            },{
                header : '以太网故障',
                dataIndex : 'DATA37',
                width : 100
            },{
                header : '在线测量',
                dataIndex : 'DATA38',
                width : 100
            },{
                header : '转台故障',
                dataIndex : 'DATA39',
                width : 100
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PRODUCTIONLINE','PRODUCTIONLINENAME','STATION', 'STOPTIME', 'STOPCOUNT','DATA8','DATA9','DATA10','DATA11','DATA12','DATA13',
                        'DATA14','DATA15','DATA16','DATA17','DATA18','DATA19','DATA20','DATA21','DATA22','DATA23','DATA24','DATA25',
                        'DATA26','DATA27','DATA28','DATA29','DATA30','DATA31','DATA32','DATA33','DATA34','DATA35','DATA36','DATA37','DATA38','DATA39','BANCI'],
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    exportYearFn();
                } 
            }]
        },
        ctListeners : {
           beforeQuery :function(values){
                values['QUERY_TYPE'] = '2';
                return true;
            }
        }
    });
    
   
    
    //导出excel
    var exportYearFn = function(){
        
        var url = 'json?action=EXPORT_PMC_EQUIPMENT_STOP_ACTION';
                    
        var params = {};
        params['WIDTH'] = new Array();
        
        var columns = yearStopLinePanel.gridConfig.columns;
        if (columns != null){
	        for (var i = 0; i < columns.length; i++) {
	            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
	                continue;
	            }
	            
	            params['WIDTH'].push('WIDTH=' + columns[i].width);
	        }
        }
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + yearStopLinePanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        url += '&PPDATE=' + yearStopLinePanel.ht_outputStore.baseParams['PPDATE'];
        url += '&BANCI=' + yearStopLinePanel.ht_outputStore.baseParams['BANCI'];
        url += '&queryType=' + yearStopLinePanel.ht_outputStore.baseParams['queryType'];
        url += '&QUERY_TYPE=' + yearStopLinePanel.ht_outputStore.baseParams['QUERY_TYPE'];
        url += '&STATION=' + yearStopLinePanel.ht_outputStore.baseParams['STATION'];

        url += '&' + params['WIDTH'].join('&');
        
        exportYearPanel.setSrc(url);
    }

    //导出面板
    var exportYearPanel = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
    var backPanel =new Ext.TabPanel({
        activeTab: 0,
        border : false,
        items : [{
            title : '日报表',
            border : false,
            layout : 'border',
            items : [stopLinePanel,exportPanel]
        },{
            title : '周报表',
            border : false,
            layout : 'border',
            items : [weekStopLinePanel,exportWeekPanel]
        },{
            title : '月报表',
            border : false,
            layout : 'border',
            items : [monthStopLinePanel,exportMonthPanel]
        },{
            title : '年报表',
            border : false,
            layout : 'border',
            items : [yearStopLinePanel,exportYearPanel]}
        ],
        listeners : {
            beforedestroy : function(){
                weekStore.destroy();
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();