/**
 *BUFFER报表
 */
(function() {
    var msgResource = {};
    //新增班次信息
    var bancidata = [["","全部"],["1","1班"],["2","2班"]];
    var banciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:bancidata,
     fields:["VALUE","TEXT"]
    });
  //新增班次信息
    var QURY_MODE = [["","全部"],["1","BUFFERIN"],["2","BUFFEROUT"],["3","空橇量"]];
    var ModeStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:QURY_MODE,
     fields:["VALUE","TEXT"]
    });
  //数量日报表
    var dayYieldPanel = new ht.base.PlainPanel({
        region : 'north',
        height : 350,
        queryFormConfig : {
            enableQueryButton : true, 
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'EventData',
                format : ht.config.format.DATE,
                value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
            },{
                xtype : 'combo',
                fieldLabel : '筛选条件',
                store : ModeStore,
                emptyText : '全部',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'MODE',
                name : 'MODE',
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            }],
            action : 'ZZCJ_QUERY_TAB_BUFFER_HOUR_ACTION',
            outputTable : 'TAB_PRODUCT_HOUR'
        
//            enableQueryButton : false,
//            action : 'QUERY_TAB_BUFFER_HOUR_ACTION',
//            outputTable : 'TAB_BUFFER_HOUR'
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
                header : '工段/小时',
                dataIndex : 'EventDate1',
                width : 100
            }, {
                header : '工段名字',
                dataIndex : 'EventDate30',
                width : 130
            },{
                header : '筛选条件',
                dataIndex : 'EventDate28',
                width : 100,
                renderer : function(value){
                	if(value==1){
                		return 'BUFFERIN';
                	}
                	if(value==2){
                		return 'BUFFEROUT';
                	}
                	if(value==3){
                		return '空橇量';
                	}
                	return '全部';
                }
            },{
                header : '9:00',
                dataIndex : 'EventDate3',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            }, {
                header : '10:00',
                dataIndex : 'EventDate4',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            }, {
                header : '11:00',
                dataIndex : 'EventDate5',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '12:00',
                dataIndex : 'EventDate6',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '13:00',
                dataIndex : 'EventDate7',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '14:00',
                dataIndex : 'EventDate8',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '15:00',
                dataIndex : 'EventDate9',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '16:00',
                dataIndex : 'EventDate10',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '17:00',
                dataIndex : 'EventDate11',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '18:00',
                dataIndex : 'EventDate12',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '19:00',
                dataIndex : 'EventDate13',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '20:00',
                dataIndex : 'EventDate14',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '21:00',
                dataIndex : 'EventDate15',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '22:00',
                dataIndex : 'EventDate16',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '23:00',
                dataIndex : 'EventDate17',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '00:00',
                dataIndex : 'EventDate18',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '01:00',
                dataIndex : 'EventDate19',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '02:00',
                dataIndex : 'EventDate20',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '03:00',
                dataIndex : 'EventDate21',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '04:00',
                dataIndex : 'EventDate22',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '05:00',
                dataIndex : 'EventDate23',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '06:00',
                dataIndex : 'EventDate24',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '07:00',
                dataIndex : 'EventDate25',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            },{
                header : '08:00',
                dataIndex : 'EventDate26',
                width : 100,
                renderer : function(value){
                	if(parseInt(value)>1000){
                		return 0;
                	}
                	return value;
                }
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['ID','EventData','EventDate1','EventDate3','BANCI', 'EventDate4', 'EventDate5','EventDate6','EventDate7','EventDate8','EventDate9','EventDate10','EventDate11','EventDate12',
                        'EventDate13','EventDate14','EventDate15','EventDate16','EventDate17','EventDate18','EventDate19','EventDate20','EventDate21','EventDate22','EventDate23','EventDate24'
                        ,'EventDate25','EventDate26','EventDate27','EventDate28','EventDate29','EventDate30','EventDate31','EventDate32','PLANDATA','RATE','avgBuffer','avgBuffer','fomatXais'],
            updateBtn : {
                            action : 'ZZCJ_SAVE_FSPP_ACTION',
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
           beforeQuery :function(values){
                return true;
            }
        },
        listeners : {
            afterLayout : function(){
//            	alert(new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000));
//            	alert(new Date(new Date().getTime() - (((new Date().getHours()-8)* 60 * 60 * 1000 + new Date().getMinutes()* 60 * 1000 + new Date().getSeconds()* 1000))));
//            	alert(new Date().getMinutes()* 60 * 1000);
//            	alert(new Date().getSeconds()* 1000);
                setPanelIsLayout = true;
            }
        }
    });
    
    
    //导出excel
    var exportFn = function(){
        
        var url = 'json?action=ZZCJ_EXPORT_TAB_BUFFER_HOUR_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = dayYieldPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&EventData=' + dayYieldPanel.ht_outputStore.baseParams['EventData'];
        //班次信息
        url += '&MODE=' + dayYieldPanel.ht_outputStore.baseParams['MODE'];
        
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
   
    //折线图
    debugger;
    var columnPanel = new Ext.Panel({
        title : 'BUFFER分时统计（折线图）',
        region : 'center',
        width : '50%',
        border : true,
        layout : 'fit',
        items: {  
            store: dayYieldPanel.ht_outputStore,   
            xtype: 'linechart',   
            url : 'lib/ext-3.3.0/resources/charts.swf',
            xField: 'fomatXais',
            extraStyle: {
                yAxis: {
                    majorGridLines: {
                        color: 0xdddddd
                    }
                }
            },
            series: [{     
                type: 'line',   
                displayName: '平均buffer',  
                margins : '5',
                yField: 'avgBuffer',
                style: {   
                    mode: 'stretch',
                    size : 5,
                    padding : '0 60 0 0',
                    color : 0xBA55D3
                }   
            }],
            chartStyle : {
                padding : 10,
                animationEnabled : true,
                dataTip : {
                    padding : 5,
                    border : {
                        color : 0x99bbe8,
                        size : 1
                    },
                    background : {
                        color : 0xDAE7F6,
                        alpha : .9
                    },
                    font : {
                        name : 'Tahoma',
                        color : 0x15428B,
                        size : 10,
                        bold : true
                    }
                },
                font : {
                    name : 'Tahoma',
                    color : 0x444444,
                    size : 11
                },
                legend: {
                    display: 'botton',
                    border: {
                        color: 0x99bbe8,
                        size:1
                    },
                    font : {
                      size : 12
                    }
                },
                yAxis : {
                    labelDistance : 0,
                    titleRotation : -90,
                    titleFont : {
                        color : 0x69aBc8
                    },
                    color : 0x69aBc8,
                    majorTicks : {
                        color : 0x69aBc8,
                        length : 4
                    },
                    minorTicks : {
                        color : 0x69aBc8,
                        length : 2
                    },
                    majorGridLines : {
                        size : 1,
                        color : 0xdfe8f6
                    }
                },
                secondaryYAxis : {
                    titleRotation : 90,
                    color : 0x69aBc8,
                    titleFont : {
                        color : 0x69aBc8
                    }
                },
                xAxis : {
                    labelRotation : -90,
                    labelRenderer : function(v){
                        return v;
                    },
                    color : 0x69aBc8,
                    majorTicks : {
                        color : 0x69aBc8,
                        length : 4
                    },
                    minorTicks : {
                        color : 0x69aBc8,
                        length : 2
                    },
                    majorGridLines : {
                        size : 1,
                        color : 0xeeeeee
                    }
                }
            },
            listeners : {
                itemclick : function(o) {
                },
                beforerender : function(comp){
                     comp.chartStyle.dataTip.font.size = 12;
                } 
            },
            tipRenderer : function(chart, record, i, series){
                return record.get(chart.xField) + ': ' + record.get(series.yField);
            }  
        } 
    });
    
    var backPanel = new Ext.Panel({       
        border : false,
        layout : 'border',
        items : [dayYieldPanel, columnPanel, exportPanel],
        listeners : {
            beforedestroy : function(){
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
