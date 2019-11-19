/**
 * 开动率报表
 */
(function() {
    var msgResource = {};
    var totolStopTime;
  //新增班次信息
    var bancidata = [["","全部"],["1","1班"],["2","2班"]];
    var banciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:bancidata,
     fields:["VALUE","TEXT"]
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
    
    var monthEquStopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'PPDATE',
                format : 'Y-m',
                value : new Date(),
                allowBlank : false
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
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            }],
            action : 'ZZCJ_QUERY_TAB_STOP_LINE_RESPON_ACTION',
            outputTable : 'TAB_STOP_LINE'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
               header : '责任部门',
                dataIndex : 'EventData51',
                width : 70,
                renderer : function(value){
                	return ht.pub.getValueExactByKey(responsStore, 'VALUE', value, 'TEXT');
                }
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
                header : '判定时间',
                dataIndex : 'COMPARE',
                width : 80,
                hidden : true
            },{
                header : '1日',
                dataIndex : '01',
                width : 50
            }, {
                header : '2日',
                dataIndex : '02',
                width : 50
            }, {
                header : '3日',
                dataIndex : '03',
                width : 50
            }, {
                header : '4日',
                dataIndex : '04',
                width : 50
            }, {
                header : '5日',
                dataIndex : '05',
                width : 50
            }, {
                header : '6日',
                dataIndex : '06',
                width : 50
            }, {
                header : '7日',
                dataIndex : '07',
                width : 50
            }, {
                header : '8日',
                dataIndex : '08',
                width : 50
            }, {
                header : '9日',
                dataIndex : '09',
                width : 50
            }, {
                header : '10日',
                dataIndex : '10',
                width : 50
            }, {
                header : '11日',
                dataIndex : '11',
                width : 50
            }, {
                header : '12日',
                dataIndex : '12',
                width : 50
            }, {
                header : '13日',
                dataIndex : '13',
                width : 50
            }, {
                header : '14日',
                dataIndex : '14',
                width : 50
            }, {
                header : '15日',
                dataIndex : '15',
                width : 50
            }, {
                header : '16日',
                dataIndex : '16',
                width : 50
            }, {
                header : '17日',
                dataIndex : '17',
                width : 50
            }, {
                header : '18日',
                dataIndex : '18',
                width : 50
            }, {
                header : '19日',
                dataIndex : '19',
                width : 50
            }, {
                header : '20日',
                dataIndex : '20',
                width : 50
            }, {
                header : '21日',
                dataIndex : '21',
                width : 50
            }, {
                header : '22日',
                dataIndex : '22',
                width : 50
            }, {
                header : '23日',
                dataIndex : '23',
                width : 50
            }, {
                header : '24日',
                dataIndex : '24',
                width : 50
            }, {
                header : '25日',
                dataIndex : '25',
                width : 50
            }, {
                header : '26日',
                dataIndex : '26',
                width : 50
            }, {
                header : '27日',
                dataIndex : '27',
                width : 50
            }, {
                header : '28日',
                dataIndex : '28',
                width : 50
            }, {
                header : '29日',
                dataIndex : '29',
                width : 50
            }, {
                header : '30日',
                dataIndex : '30',
                width : 50
            }, {
                header : '31日',
                dataIndex : '31',
                width : 50
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['EventData51','BANCI','01','02','03','04','05','06','07','08','09','10'
            				,'11','12','13','14','15','16','17','18','19','20'
            				,'21','22','23','24','25','26','27','28','29','30','31','COMPARE'],
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
        }
    });
    
    //导出excel
    var exportFn = function(){
        
        var url = 'json?action=ZZCJ_EXPORT_TAB_STOP_LINE_RESPON_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = monthEquStopLinePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false)) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&PPDATE=' + monthEquStopLinePanel.ht_outputStore.baseParams['PPDATE'];
        url += '&BANCI=' + monthEquStopLinePanel.ht_outputStore.baseParams['BANCI'];
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
        items : [monthEquStopLinePanel,exportPanel],
        listeners : {
            beforedestroy : function(){
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
