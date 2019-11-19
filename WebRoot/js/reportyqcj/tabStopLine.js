/**
 * 固定报表 -> 工位停机报表
 */
(function() {
    var msgResource = {};
    var totolStopTime;
    var weekDefault;

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
            action : 'YQCJ_GET_CODE_VALUE_ACTION',
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
            action : 'YQCJ_GET_CODE_VALUE_ACTION',
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
            action : 'YQCJ_QUERY_PMC_PP_STATION_ALL_ACTION',
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
            action : 'YQCJ_QUERY_TAB_STOP_STA_ALL_ACTION',
            CODE_TYPE : 'EventData4',
            QURY_TYPE : ''
        },
        fields : ['VALUE','TEXT']
    });
    stationStore.load();
    
    //设备
    var equipmentStopStore = new Ext.data.JsonStore({
    	url : 'json',
        root : "root.PMC_PP_STATION.rs",
        baseParams : {
            action : 'YQCJ_QUERY_PMC_PP_STATION_EQUIPMENT_ACTION'
        },
        fields : ['VALUE','TEXT']
    });
    equipmentStopStore.load();
    
    //计划日期面板
    var planDatePanel = new ht.base.PlainPanel({
        region : 'north',
        height : 90,
        queryFormConfig : {
            enableQueryButton : true, 
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'WORKDATE',
                format : ht.config.format.DATE,
                value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
            },{
                xtype : 'combo',
                fieldLabel : '工段名字',
                store : sectionStore,
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
                        planDatePanel.findQueryCompment('STATION').setValue(null);
                        planDatePanel.findQueryCompment('EQUIPMENTSTOP').setValue(null);
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
                        stationStore.baseParams['QURY_TYPE'] = planDatePanel.findQueryCompment('PRODUCTIONLINENAME').getValue();
                    },
                    select : function(c,r,index){
                        planDatePanel.findQueryCompment('EQUIPMENTSTOP').setValue(null);
                    }
                }
            },{
                xtype : 'combo',
                fieldLabel : '设备',
                name : 'EQUIPMENTSTOP',
                store : equipmentStopStore,
                editable : true,
                allowBlank : true,
                emptyText : '请选择',
                mode : 'remote',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'EQUIPMENTSTOP',
                listeners : {
                    beforeQuery : function(c){
                        delete c.combo.lastQuery;
                        equipmentStopStore.baseParams['STATION'] = planDatePanel.findQueryCompment('STATION').getValue();
                    }
                }
            }],
            action : 'YQCJ_QUERY_PMC_DATE_IMPORT_ACTION',
            outputTable : 'PMC_DATE_IMPORT'
        },
        gridConfig : {
            columns : [
/*            new Ext.grid.RowNumberer(),{
                header : '生产日期',
                dataIndex : 'WORKDATE',
                width : 250,
                renderer : function(value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '开始时间',
                dataIndex : 'STARTTIME',
                width : 250,
                renderer : function(value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '结束时间',
                dataIndex : 'ENDTIME',
                width : 250,
                renderer : function(value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '历时',
                dataIndex : 'LASTTIME',
                width : 170,
                renderer : function(value) {
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '班次',
                dataIndex : 'SHIFT',
                width : 170
            }*/
            ],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['WORKDATE','STARTTIME', 'ENDTIME', 'RESTTIME','LASTTIME','SHIFT']
        },
        ctListeners : {
            beforeQuery : function(values){
            	var errMsg = '';
                stopLinePanel.ht_outputStore.baseParams['PPDATE'] = values['WORKDATE'];
                stopLinePanel.ht_outputStore.baseParams['PRODUCTIONLINENAME'] = values['PRODUCTIONLINENAME'];
                stopLinePanel.ht_outputStore.baseParams['STATION'] = values['STATION'];
                stopLinePanel.ht_outputStore.baseParams['EQUIPMENTSTOP'] = values['EQUIPMENTSTOP'];
                stopLinePanel.ht_outputStore.baseParams['QUERY_TYPE'] = '0';
                if(values['STATION']!=''){
            		if(values['PRODUCTIONLINENAME']==''){
            			errMsg += '◇ 请选择工段！<br />';
            		}
            	}
            	if(values['EQUIPMENTSTOP']!=''){
            		if(values['STATION']==''){
            			errMsg += '◇ 请选择工位！<br />';
            		}
            	}
            	if(errMsg.length > 0){
            		ht.pub.error(errMsg);
            		return false;
            	}
                stopLinePanel.ht_outputStore.load();
                return true;
            }
        }
    
    });

    //停机日报表
    var stopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            enableQueryButton : false,
            action : 'YQCJ_QUERY_PMC_EQUIPMENT_STOP_ACTION',
            outputTable : 'PMC_EQUIPMENT_STOP'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),/*{
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 150
            }, */{
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            },{
                header : '工位',
                dataIndex : 'STATION',
                width : 100
            },{
                header : '设备',
                dataIndex : 'EVENTDATA5',
                width : 100
            }, {
                header : '停机总时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '停机总次数',
                dataIndex : 'STOPCOUNT',
                width : 100
            },{
                header : '动力电源',
                dataIndex : 'DATA8',
                width : 100
            },{
                header : '控制电源',
                dataIndex : 'DATA9',
                width : 100
            },{
                header : 'I/O模块',
                dataIndex : 'DATA10',
                width : 100
            },{
                header : 'DNET网络',
                dataIndex : 'DATA11',
                width : 100
            },{
                header : '以太网',
                dataIndex : 'DATA12',
                width : 100
            },{
                header : '物料传感器',
                dataIndex : 'DATA13',
                width : 100
            },{
                header : '安全设备',
                dataIndex : 'DATA14',
                width : 100
            },{
                header : '急停',
                dataIndex : 'DATA15',
                width : 100
            },{
                header : '机器人',
                dataIndex : 'DATA16',
                width : 100
            },{
                header : '点焊设备',
                dataIndex : 'DATA17',
                width : 100
            },{
                header : '螺柱焊',
                dataIndex : 'DATA18',
                width : 100
            },{
                header : '修磨器',
                dataIndex : 'DATA19',
                width : 100
            },{
                header : '换抢盘',
                dataIndex : 'DATA20',
                width : 100
            },{
                header : '停放架',
                dataIndex : 'DATA21',
                width : 100
            },{
                header : '气源',
                dataIndex : 'DATA22',
                width : 100
            },{
                header : '冷却水',
                dataIndex : 'DATA23',
                width : 100
            },{
                header : '夹头',
                dataIndex : 'DATA24',
                width : 100
            },{
                header : '定位销',
                dataIndex : 'DATA25',
                width : 100
            },{
                header : '气缸',
                dataIndex : 'DATA26',
                width : 100
            },{
                header : '阀岛',
                dataIndex : 'DATA27',
                width : 100
            },{
                header : '压机',
                dataIndex : 'DATA28',
                width : 100
            },{
                header : '输送设备',
                dataIndex : 'DATA29',
                width : 100
            },{
                header : '工装',
                dataIndex : 'DATA30',
                width : 100
            },{
                header : '转台',
                dataIndex : 'DATA31',
                width : 100
            },{
                header : 'ANDON',
                dataIndex : 'DATA32',
                width : 100
            },{
                header : 'AVI',
                dataIndex : 'DATA33',
                width : 100
            },{
                header : '激光焊',
                dataIndex : 'DATA34',
                width : 100
            },{
                header : '视觉防错',
                dataIndex : 'DATA35',
                width : 100
            },{
                header : '滑台',
                dataIndex : 'DATA36',
                width : 100
            },{
                header : '换电极帽',
                dataIndex : 'DATA37',
                width : 100
            },{
                header : 'CO2焊',
                dataIndex : 'DATA38',
                width : 100
            },{
                header : '涂胶设备',
                dataIndex : 'DATA39',
                width : 100
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PRODUCTIONLINE','STATION','PRODUCTIONLINENAME','EVENTDATA5', 'STOPTIME', 'STOPCOUNT','DATA8','DATA9','DATA10','DATA11','DATA12','DATA13',
                        'DATA14','DATA15','DATA16','DATA17','DATA18','DATA19','DATA20','DATA21','DATA22','DATA23','DATA24','DATA25',
                        'DATA26','DATA27','DATA28','DATA29','DATA30','DATA31','DATA32','DATA33','DATA34','DATA35','DATA36','DATA37','DATA38','DATA39'],
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
        //params['HEADER'] = new Array();
        //params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = stopLinePanel.gridConfig.columns;
        if (columns != null){
	        for (var i = 0; i < columns.length; i++) {
	            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
	                continue;
	            }
	            
	            //params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
	            //params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
	            params['WIDTH'].push('WIDTH=' + columns[i].width);
	        }
        }
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + stopLinePanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        url += '&PPDATE=' + stopLinePanel.ht_outputStore.baseParams['PPDATE'];
        url += '&STATION=' + stopLinePanel.ht_outputStore.baseParams['STATION'];
        url += encodeURI(encodeURI('&EQUIPMENTSTOP=' + stopLinePanel.ht_outputStore.baseParams['EQUIPMENTSTOP']));
        
        //url += '&' + params['HEADER'].join('&');
        //url += '&' + params['COLUMN'].join('&');
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
    
    //停机周报表
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
                store : sectionStore,
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
                fieldLabel : '设备',
                name : 'EQUIPMENTSTOP',
                store : equipmentStopStore,
                editable : true,
                allowBlank : true,
                emptyText : '请选择',
                mode : 'remote',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'EQUIPMENTSTOP',
                listeners : {
                    beforeQuery : function(c){
                        delete c.combo.lastQuery;
                        equipmentStopStore.baseParams['STATION'] = weekStopLinePanel.findQueryCompment('STATION').getValue();
                    }
                }
            }],
            action : 'YQCJ_QUERY_PMC_EQUIPMENT_STOP_ACTION',
            outputTable : 'PMC_EQUIPMENT_STOP'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),/*{
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 150
            },*/ {
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            }, {
                header : '工位',
                dataIndex : 'STATION',
                width : 100
            },{
                header : '设备',
                dataIndex : 'EVENTDATA5',
                width : 100
            }, {
                header : '停机总时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '停机总次数',
                dataIndex : 'STOPCOUNT',
                width : 100
            },{
                header : '动力电源',
                dataIndex : 'DATA8',
                width : 100
            },{
                header : '控制电源',
                dataIndex : 'DATA9',
                width : 100
            },{
                header : 'I/O模块',
                dataIndex : 'DATA10',
                width : 100
            },{
                header : 'DNET网络',
                dataIndex : 'DATA11',
                width : 100
            },{
                header : '以太网',
                dataIndex : 'DATA12',
                width : 100
            },{
                header : '物料传感器',
                dataIndex : 'DATA13',
                width : 100
            },{
                header : '安全设备',
                dataIndex : 'DATA14',
                width : 100
            },{
                header : '急停',
                dataIndex : 'DATA15',
                width : 100
            },{
                header : '机器人',
                dataIndex : 'DATA16',
                width : 100
            },{
                header : '点焊设备',
                dataIndex : 'DATA17',
                width : 100
            },{
                header : '螺柱焊',
                dataIndex : 'DATA18',
                width : 100
            },{
                header : '修磨器',
                dataIndex : 'DATA19',
                width : 100
            },{
                header : '换抢盘',
                dataIndex : 'DATA20',
                width : 100
            },{
                header : '停放架',
                dataIndex : 'DATA21',
                width : 100
            },{
                header : '气源',
                dataIndex : 'DATA22',
                width : 100
            },{
                header : '冷却水',
                dataIndex : 'DATA23',
                width : 100
            },{
                header : '夹头',
                dataIndex : 'DATA24',
                width : 100
            },{
                header : '定位销',
                dataIndex : 'DATA25',
                width : 100
            },{
                header : '气缸',
                dataIndex : 'DATA26',
                width : 100
            },{
                header : '阀岛',
                dataIndex : 'DATA27',
                width : 100
            },{
                header : '压机',
                dataIndex : 'DATA28',
                width : 100
            },{
                header : '输送设备',
                dataIndex : 'DATA29',
                width : 100
            },{
                header : '工装',
                dataIndex : 'DATA30',
                width : 100
            },{
                header : '转台',
                dataIndex : 'DATA31',
                width : 100
            },{
                header : 'ANDON',
                dataIndex : 'DATA32',
                width : 100
            },{
                header : 'AVI',
                dataIndex : 'DATA33',
                width : 100
            },{
                header : '激光焊',
                dataIndex : 'DATA34',
                width : 100
            },{
                header : '视觉防错',
                dataIndex : 'DATA35',
                width : 100
            },{
                header : '滑台',
                dataIndex : 'DATA36',
                width : 100
            },{
                header : '换电极帽',
                dataIndex : 'DATA37',
                width : 100
            },{
                header : 'CO2焊',
                dataIndex : 'DATA38',
                width : 100
            },{
                header : '涂胶设备',
                dataIndex : 'DATA39',
                width : 100
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PRODUCTIONLINE','PRODUCTIONLINENAME','EVENTDATA5','STATION', 'STOPTIME', 'STOPCOUNT','DATA8','DATA9','DATA10','DATA11','DATA12','DATA13',
                        'DATA14','DATA15','DATA16','DATA17','DATA18','DATA19','DATA20','DATA21','DATA22','DATA23','DATA24','DATA25',
                        'DATA26','DATA27','DATA28','DATA29','DATA30','DATA31','DATA32','DATA33','DATA34','DATA35','DATA36','DATA37','DATA38','DATA39'],        

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
        
        var url = 'json?action=EXPORT_PMC_EQUIPMENT_STOP_WEEK_ACTION';
                    
        var params = {};
        //params['HEADER'] = new Array();
        //params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = weekStopLinePanel.gridConfig.columns;
        if (columns != null){
	        for (var i = 0; i < columns.length; i++) {
	            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
	                continue;
	            }
	            
	            //params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
	            //params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
	            params['WIDTH'].push('WIDTH=' + columns[i].width);
	        }
        }
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + weekStopLinePanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        url += '&PPDATE=' + weekStopLinePanel.ht_outputStore.baseParams['PPDATE'];
        url += '&STATION=' + weekStopLinePanel.ht_outputStore.baseParams['STATION'];
        url += encodeURI(encodeURI('&EQUIPMENTSTOP=' + weekStopLinePanel.ht_outputStore.baseParams['EQUIPMENTSTOP']));
        
        //url += '&' + params['HEADER'].join('&');
        //url += '&' + params['COLUMN'].join('&');
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
    
    //停机月报表
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
                store : sectionStore,
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
                fieldLabel : '设备',
                name : 'EQUIPMENTSTOP',
                store : equipmentStopStore,
                editable : true,
                allowBlank : true,
                emptyText : '请选择',
                mode : 'remote',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'EQUIPMENTSTOP',
                listeners : {
                    beforeQuery : function(c){
                        delete c.combo.lastQuery;
                        equipmentStopStore.baseParams['STATION'] = monthStopLinePanel.findQueryCompment('STATION').getValue();
                    }
                }
            }],
            action : 'YQCJ_QUERY_PMC_EQUIPMENT_STOP_ACTION',
            outputTable : 'PMC_EQUIPMENT_STOP'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),/*{
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 150
            },*/ {
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            },{
                header : '工位',
                dataIndex : 'STATION',
                width : 100
            },{
                header : '设备',
                dataIndex : 'EVENTDATA5',
                width : 100
            }, {
                header : '停机总时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '停机总次数',
                dataIndex : 'STOPCOUNT',
                width : 100
            },{
                header : '动力电源',
                dataIndex : 'DATA8',
                width : 100
            },{
                header : '控制电源',
                dataIndex : 'DATA9',
                width : 100
            },{
                header : 'I/O模块',
                dataIndex : 'DATA10',
                width : 100
            },{
                header : 'DNET网络',
                dataIndex : 'DATA11',
                width : 100
            },{
                header : '以太网',
                dataIndex : 'DATA12',
                width : 100
            },{
                header : '物料传感器',
                dataIndex : 'DATA13',
                width : 100
            },{
                header : '安全设备',
                dataIndex : 'DATA14',
                width : 100
            },{
                header : '急停',
                dataIndex : 'DATA15',
                width : 100
            },{
                header : '机器人',
                dataIndex : 'DATA16',
                width : 100
            },{
                header : '点焊设备',
                dataIndex : 'DATA17',
                width : 100
            },{
                header : '螺柱焊',
                dataIndex : 'DATA18',
                width : 100
            },{
                header : '修磨器',
                dataIndex : 'DATA19',
                width : 100
            },{
                header : '换抢盘',
                dataIndex : 'DATA20',
                width : 100
            },{
                header : '停放架',
                dataIndex : 'DATA21',
                width : 100
            },{
                header : '气源',
                dataIndex : 'DATA22',
                width : 100
            },{
                header : '冷却水',
                dataIndex : 'DATA23',
                width : 100
            },{
                header : '夹头',
                dataIndex : 'DATA24',
                width : 100
            },{
                header : '定位销',
                dataIndex : 'DATA25',
                width : 100
            },{
                header : '气缸',
                dataIndex : 'DATA26',
                width : 100
            },{
                header : '阀岛',
                dataIndex : 'DATA27',
                width : 100
            },{
                header : '压机',
                dataIndex : 'DATA28',
                width : 100
            },{
                header : '输送设备',
                dataIndex : 'DATA29',
                width : 100
            },{
                header : '工装',
                dataIndex : 'DATA30',
                width : 100
            },{
                header : '转台',
                dataIndex : 'DATA31',
                width : 100
            },{
                header : 'ANDON',
                dataIndex : 'DATA32',
                width : 100
            },{
                header : 'AVI',
                dataIndex : 'DATA33',
                width : 100
            },{
                header : '激光焊',
                dataIndex : 'DATA34',
                width : 100
            },{
                header : '视觉防错',
                dataIndex : 'DATA35',
                width : 100
            },{
                header : '滑台',
                dataIndex : 'DATA36',
                width : 100
            },{
                header : '换电极帽',
                dataIndex : 'DATA37',
                width : 100
            },{
                header : 'CO2焊',
                dataIndex : 'DATA38',
                width : 100
            },{
                header : '涂胶设备',
                dataIndex : 'DATA39',
                width : 100
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PRODUCTIONLINE','PRODUCTIONLINENAME','EVENTDATA5','STATION', 'STOPTIME', 'STOPCOUNT','DATA8','DATA9','DATA10','DATA11','DATA12','DATA13',
                        'DATA14','DATA15','DATA16','DATA17','DATA18','DATA19','DATA20','DATA21','DATA22','DATA23','DATA24','DATA25',
                        'DATA26','DATA27','DATA28','DATA29','DATA30','DATA31','DATA32','DATA33','DATA34','DATA35','DATA36','DATA37','DATA38','DATA39'],
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
        
        var url = 'json?action=EXPORT_PMC_EQUIPMENT_STOP_MONTH_ACTION';
                    
        var params = {};
        //params['HEADER'] = new Array();
       // params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = monthStopLinePanel.gridConfig.columns;
        if (columns != null){
	        for (var i = 0; i < columns.length; i++) {
	            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
	                continue;
	            }
	            
	            //params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
	            //params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
	            params['WIDTH'].push('WIDTH=' + columns[i].width);
	        }
        }
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + monthStopLinePanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        url += '&PPDATE=' + monthStopLinePanel.ht_outputStore.baseParams['PPDATE'];
        url += '&STATION=' + monthStopLinePanel.ht_outputStore.baseParams['STATION'];
        url += encodeURI(encodeURI('&EQUIPMENTSTOP=' + monthStopLinePanel.ht_outputStore.baseParams['EQUIPMENTSTOP']));
        
        //url += '&' + params['HEADER'].join('&');
       // url += '&' + params['COLUMN'].join('&');
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
    
    //停机年报表
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
                store : sectionStore,
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
                fieldLabel : '设备',
                name : 'EQUIPMENTSTOP',
                store : equipmentStopStore,
                editable : true,
                allowBlank : true,
                emptyText : '请选择',
                mode : 'remote',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'EQUIPMENTSTOP',
                listeners : {
                    beforeQuery : function(c){
                        delete c.combo.lastQuery;
                        equipmentStopStore.baseParams['STATION'] = yearStopLinePanel.findQueryCompment('STATION').getValue();
                    }
                }
            }],
            action : 'YQCJ_QUERY_PMC_EQUIPMENT_STOP_ACTION',
            outputTable : 'PMC_EQUIPMENT_STOP'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),/*{
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 150
            },*/ {
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            },{
                header : '工位',
                dataIndex : 'STATION',
                width : 100
            },{
                header : '设备',
                dataIndex : 'EVENTDATA5',
                width : 100
            }, {
                header : '停机总时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '停机总次数',
                dataIndex : 'STOPCOUNT',
                width : 100
            },{
                header : '动力电源',
                dataIndex : 'DATA8',
                width : 100
            },{
                header : '控制电源',
                dataIndex : 'DATA9',
                width : 100
            },{
                header : 'I/O模块',
                dataIndex : 'DATA10',
                width : 100
            },{
                header : 'DNET网络',
                dataIndex : 'DATA11',
                width : 100
            },{
                header : '以太网',
                dataIndex : 'DATA12',
                width : 100
            },{
                header : '物料传感器',
                dataIndex : 'DATA13',
                width : 100
            },{
                header : '安全设备',
                dataIndex : 'DATA14',
                width : 100
            },{
                header : '急停',
                dataIndex : 'DATA15',
                width : 100
            },{
                header : '机器人',
                dataIndex : 'DATA16',
                width : 100
            },{
                header : '点焊设备',
                dataIndex : 'DATA17',
                width : 100
            },{
                header : '螺柱焊',
                dataIndex : 'DATA18',
                width : 100
            },{
                header : '修磨器',
                dataIndex : 'DATA19',
                width : 100
            },{
                header : '换抢盘',
                dataIndex : 'DATA20',
                width : 100
            },{
                header : '停放架',
                dataIndex : 'DATA21',
                width : 100
            },{
                header : '气源',
                dataIndex : 'DATA22',
                width : 100
            },{
                header : '冷却水',
                dataIndex : 'DATA23',
                width : 100
            },{
                header : '夹头',
                dataIndex : 'DATA24',
                width : 100
            },{
                header : '定位销',
                dataIndex : 'DATA25',
                width : 100
            },{
                header : '气缸',
                dataIndex : 'DATA26',
                width : 100
            },{
                header : '阀岛',
                dataIndex : 'DATA27',
                width : 100
            },{
                header : '压机',
                dataIndex : 'DATA28',
                width : 100
            },{
                header : '输送设备',
                dataIndex : 'DATA29',
                width : 100
            },{
                header : '工装',
                dataIndex : 'DATA30',
                width : 100
            },{
                header : '转台',
                dataIndex : 'DATA31',
                width : 100
            },{
                header : 'ANDON',
                dataIndex : 'DATA32',
                width : 100
            },{
                header : 'AVI',
                dataIndex : 'DATA33',
                width : 100
            },{
                header : '激光焊',
                dataIndex : 'DATA34',
                width : 100
            },{
                header : '视觉防错',
                dataIndex : 'DATA35',
                width : 100
            },{
                header : '滑台',
                dataIndex : 'DATA36',
                width : 100
            },{
                header : '换电极帽',
                dataIndex : 'DATA37',
                width : 100
            },{
                header : 'CO2焊',
                dataIndex : 'DATA38',
                width : 100
            },{
                header : '涂胶设备',
                dataIndex : 'DATA39',
                width : 100
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PRODUCTIONLINE','PRODUCTIONLINENAME','EVENTDATA5','STATION', 'STOPTIME', 'STOPCOUNT','DATA8','DATA9','DATA10','DATA11','DATA12','DATA13',
                        'DATA14','DATA15','DATA16','DATA17','DATA18','DATA19','DATA20','DATA21','DATA22','DATA23','DATA24','DATA25',
                        'DATA26','DATA27','DATA28','DATA29','DATA30','DATA31','DATA32','DATA33','DATA34','DATA35','DATA36','DATA37','DATA38','DATA39'],
/*            excelConfig : {
            	enableExport : true,
                excelName : '工位设备停机年报表'                    
            },*/
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
            }/*,{ 
                iconCls : 'excel',
                text : '下载',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 4,
                handler : function() {
                    downloadYearFn();
                } 
            }*/]
        },
        ctListeners : {
           beforeQuery :function(values){
                values['QUERY_TYPE'] = '2';
                return true;
            }
        }
    });
    
    /*//下载excel
    var downloadYearFn = function(){
       
    	Ext.MessageBox.confirm('提示','确定要导出用户吗?',function(btn){
    		if(btn=="yes"){  
    			Ext.Ajax.request({
      			url:"json?action=EXPORT_PMC_EQUIPMENT_STOP_YEAR_ACTION",
      			success:function(res){
           			var obj =Ext.decode(res.responseText);
       				//alert(res.responseText);
       				window.location.href =obj.path;
     		 	}
     			});
    		}
  		})          
    }*/
    
    //导出excel
    var exportYearFn = function(){
        
        var url = 'json?action=EXPORT_PMC_EQUIPMENT_STOP_YEAR_ACTION';
                    
        var params = {};
        //params['HEADER'] = new Array();
        //params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = yearStopLinePanel.gridConfig.columns;
        if (columns != null){
	        for (var i = 0; i < columns.length; i++) {
	            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
	                continue;
	            }
	            
	            //params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
	            //params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
	            params['WIDTH'].push('WIDTH=' + columns[i].width);
	        }
        }
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + yearStopLinePanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));
        url += '&PPDATE=' + yearStopLinePanel.ht_outputStore.baseParams['PPDATE'];
        url += '&STATION=' + yearStopLinePanel.ht_outputStore.baseParams['STATION'];
        url += encodeURI(encodeURI('&EQUIPMENTSTOP=' + yearStopLinePanel.ht_outputStore.baseParams['EQUIPMENTSTOP']));
        
        //url += '&' + params['HEADER'].join('&');
        //url += '&' + params['COLUMN'].join('&');
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
            items : [stopLinePanel, planDatePanel,exportPanel]
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
