/**
 * 工段停线记录报表
 */
(function() {
    var setPanelIsLayout;
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
    shiftStore.load({
        callback : function() {
            ht.pub.store.addBlankText(shiftStore);
        }
    });

    //工段
    var sectionStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_STATION.rs',
        baseParams : {
            action : 'QUERY_PMC_PP_STATION_STOP_ACTION',
            // CODE_TYPE : 'EventData40'
        },
        fields : ['VALUE','TEXT']
    });
    sectionStore.load({
        callback : function() {
            ht.pub.store.addBlankText(sectionStore);
        }
    });

    //停线原因
    var stopReson = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.STOP_RESON.rs',
        baseParams : {
            action : 'QUERY_STOP_RESON_ACTION'
        },
        fields : ['VALUE','TEXT']
    });
    stopReson.load({
        callback : function() {
            ht.pub.store.addBlankText(stopReson);
        }
    });

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

    //停线报表
    var faultEventPanel = new ht.base.RowEditorPanel({
        region : 'center',
        queryFormConfig : {
            enableQueryButton : true,
            items : [{
                xtype: 'compositefield',
                fieldLabel: '生产日期',
                name : 'PLAN_DATE',
                columnWidth : 1/2,
                items: [ {
                    xtype : 'datetimefield',
                    format : ht.config.format.DATETIME,
                    fieldLabel : ' ',
                    columnWidth : 1/2,
                    name : 'START_PLAN_DATE',
                    //value : new Date(new Date().getTime() - (new Date().format('N') - 1) * 24 * 60 * 60 * 1000),
                    value : (new Date().getHours()>7 && new Date().getHours()<19)?new Date(new Date().getTime() - ((new Date().getHours()-8)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):((new Date().getHours()<8)?new Date(new Date().getTime() - ((new Date().getHours()+4)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):new Date(new Date().getTime() - ((new Date().getHours()-19)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000))),
                    vtype: 'defineValid',
                    vtypeText : '',
                    defineValid : function(val, fromField){
                        if(setPanelIsLayout && faultEventPanel.findQueryCompment('END_PLAN_DATE')){
                            faultEventPanel.findQueryCompment('END_PLAN_DATE').validate();
                        }
                        return true;
                    }
                }, {
                    xtype: 'displayfield',
                    width : 20,
                    style : 'text-align: center;',
                    value : '至'
                },{
                    xtype : 'datetimefield',
                    format : ht.config.format.DATETIME,
                    fieldLabel : ' ',
                    columnWidth : 1/2,
                    name : 'END_PLAN_DATE',

                    value : (new Date().getHours()>7 && new Date().getHours()<19)?new Date(new Date().getTime() - ((new Date().getHours()-19)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):((new Date().getHours()<8)?new Date(new Date().getTime() - ((new Date().getHours()-8)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):new Date(new Date().getTime() - ((new Date().getHours()-32)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000))),
                    vtype: 'comparentDate',
                    vtypeText: ht.msg.valid.compareDate,
                    comparentTo: 'START_PLAN_DATE',
                    getParentCompont : function() {
                        return faultEventPanel.ht_outputQueryForm.find('name', 'PLAN_DATE')[0].innerCt;
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
                hiddenName : 'EventDate46',
                name : 'EventDate46',
                editable : false
            },{
                xtype : 'combo',
                fieldLabel : '停线原因',
                store : stopReson,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                columnWidth : 1/4,
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'EventData3',
                name : 'EventData3',
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
            }
            ],
            action : 'QUERY_TAB_STOP_LINE_ACTION',
            outputTable : 'TAB_STOP_LINE'
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
                header : '工段',
                dataIndex : 'EventData40',
                width : 90
            },{
                header : '工段名字',
                dataIndex : 'EventData46',
                width : 150
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
            },{
                header : '发生时间',
                dataIndex : 'EventDate1',
                width : 135,
                renderer : function(value){
                	return ht.pub.date.dateTimeRenderer(value);
                },
                editor : {
                    xtype : 'datetimefield',
                    format : ht.config.format.DATETIME,
                    name : 'EventDate1',
                    updateable : false
                }

            },{
                header : '恢复时间',
                dataIndex : 'EventDate2',
                width : 135,
                renderer : function(value){
                	return ht.pub.date.dateTimeRenderer(value);
                },
                editor : {
                    xtype : 'datetimefield',
                    format : ht.config.format.DATETIME,
                    name : 'EventDate2',
                    updateable : false
                }
            }, {
                header : '停线时间(秒)',
                dataIndex : 'StopTimeTotal',
                width : 85
            }, {
                header : '停线原因',
                dataIndex : 'StopReson',
                width : 70,
                editor : {
                    xtype : 'combo',
                    name : 'StopReson',
                    fieldLabel : '停线原因',
                    width : 70,
                    store : stopReson,
                    mode : 'local',
                    triggerAction : 'all',
                    valueField : 'VALUE',
                    displayField : 'TEXT',
                    hiddenName : 'StopReson',
                    editable : true,
                    enableByRowSelected : false,
                    ingoreSeparator : true
                }
            }, {
                header : '备注',
                dataIndex : 'EventData50',
                width : 250,
                editor : {
                    xtype : 'textfield',
                    name : 'EventData50'
                }
            },{
                header : '工位',
                dataIndex : 'EventData51',
                width : 100
            }, {
                header : '责任部门',
                dataIndex : 'EventData1',
                width : 70,
                renderer : function(value){
                	return ht.pub.getValueExactByKey(responsStore, 'VALUE', value, 'TEXT');
                },
                editor : {
                    xtype : 'combo',
	                name : 'EventData1',
					fieldLabel : '责任部门',
	                width : 70,
	                store : responsStore,
	                emptyText : ht.msg.combo.emptyText,
	                mode : 'local',
	                triggerAction : 'all',
	                valueField : 'VALUE',
	                displayField : 'TEXT',
	                hiddenName : 'EventData1',
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
                },
                editor : {
                    xtype : 'datetimefield',
                    format : ht.config.format.DATETIME,
                    name : 'EventData54',
                    updateable : false
                }

            }],

            isPageAction : true,
            isMultipleSelect : false,
            storeFields : ['ID','EventDate1','EventDate2','StopTimeTotal','EventData50',
            				'EventData46', 'EventData40', 'EventData1', 'StopReson','EventData51',
            				'EventData53','EventData54','BANCI'],
            updateBtn : {
                action : 'SAVE_ALARM_LOG_ACTION',
                tagValue : ''
            },
            ctTopbarComponts : [{
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 4,
                handler : function() {
                    exportFn(ht.pub.global.YESNO.YES);
                }
            }]
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

        var url = 'json?action=EXPORT_TAB_STOP_LINE_ACTION';
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

        url += '&START_PLAN_DATE=' + faultEventPanel.ht_outputStore.baseParams['START_PLAN_DATE'];
        url += '&END_PLAN_DATE=' + faultEventPanel.ht_outputStore.baseParams['END_PLAN_DATE'];
        url += encodeURI(encodeURI('&EventDate46=' + faultEventPanel.ht_outputStore.baseParams['EventDate46']));
        url += '&EventDate1=' + faultEventPanel.ht_outputStore.baseParams['EventDate1'];
        url += '&BANCI=' + faultEventPanel.ht_outputStore.baseParams['BANCI'];

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
