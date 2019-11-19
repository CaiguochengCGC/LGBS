/**
 * 故障事故记录
 */
(function() {
    var msgResource = {};
    var totolStopTime;
    var queryPanelIsLayout;
    //新增班次信息
    var bancidata = [["","全部"],["JIYUN","机运"],["ANDON","安灯"]];
    var banciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:bancidata,
     fields:["VALUE","TEXT"]
    });
    
    //工段
    var gongduandata = [["","全部"],["1","内饰一"],["2","内饰二"],["3","底盘一1"],["4","底盘一2"],["5","底盘一3"],["6","底盘二"],["7","最终线"]];
    var sectionStore = new Ext.data.JsonStore({
        auteLoad:true, //此处设置为自动加载
        data:gongduandata,
        fields:["VALUE","TEXT"]
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
					value:(new Date().getHours()>7 && new Date().getHours()<20)?new Date(new Date().getTime() - ((new Date().getHours()-8)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):((new Date().getHours()<8)?new Date(new Date().getTime() - ((new Date().getHours()+4)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000)):new Date(new Date().getTime() - ((new Date().getHours()-20)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000))),
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
                fieldLabel : '工段',
                columnWidth : 1/4,
                store : gongduandata,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false,
                listeners : {
                    beforeQuery : function(c){
                        //delete c.combo.lastQuery;
                        //var gd = faultEventPanel.findQueryCompment('PRODUCTIONLINENAME').getValue();
                        //stationStore.baseParams['QURY_TYPE'] = gd;
                        
                    }
                }
            },{
                xtype : 'combo',
                fieldLabel : '信号源',
                columnWidth : 1/4,
                store : banciStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'SOURCE',
                name : 'SOURCE',
                editable : false,
                listeners : {
                    beforeQuery : function(c){
                        //delete c.combo.lastQuery;
                        //var gd = faultEventPanel.findQueryCompment('PRODUCTIONLINENAME').getValue();
                        //stationStore.baseParams['QURY_TYPE'] = gd;
                        
                    }
                }
            }],
            action : 'ZZCJ_QUERY_FPS_ACTION',
            outputTable : 'ALARM_LOG'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '信号开始时间',
                dataIndex : '_TIME',
                width : 140,
                renderer : function(value){
                	return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '信号结束时间',
                dataIndex : 'TIMESTAMP',
                width : 140,
                renderer : function(value){
                	return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '历时（秒）',
                dataIndex : 'LASTTIME',
                width : 80
            }, {
                header : '值',
                dataIndex : 'VALUE',
                width : 80
            }, {
                header : '信号源',
                dataIndex : 'SOURCE',
                width : 60
            },{
                header : '信号地址',
                dataIndex : 'SOURCE_ADDRESS',
                width : 120
            },{
                header : '工段名字',
                dataIndex : 'GONGDUAN',
                width : 130
            }],

            isPageAction : true,
            isMultipleSelect : false, 
            storeFields : ['GONGDUAN','SOURCE_ADDRESS','_TIME','TIMESTAMP','LASTTIME','VALUE','SOURCE'],
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
        
        var url = 'json?action=ZZCJ_EXPORT_FPS_ACTION';
                    
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
        url += '&SOURCE=' + faultEventPanel.ht_outputStore.baseParams['SOURCE'];
        
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
