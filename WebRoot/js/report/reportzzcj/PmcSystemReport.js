/**
 *总装车间总报表
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
    var PmcSystemReportPanel = new ht.base.PlainPanel({
        region : 'north',
        height : 350,
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
                fieldLabel : '班次',
                store : banciStore,
                emptyText : '全部班次',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONLINENAME',
                name : 'PRODUCTIONLINENAME',
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            }],
            action : 'ZZCJ_QUERY_PMC_SYSTEM_REPORT_ACTION',
            outputTable : 'PMC_SYSTEM_REPORT'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
               header : '工段/日',
                dataIndex : 'PRODUCTIONLINE',
                width : 60
            },{
               header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 90
            }, {
                header : '班次',
                dataIndex : 'BANCI',
                width : 50,
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
                header : '平均节拍（秒）',
                dataIndex : 'CYCLETIME',
                width : 100, 
                renderer : function(v){
                	if(v==null){
                		return 0;
                	}
                	return parseInt(v*10)/10;
                }
            }, {
                header : '实际生产时间（小时）',
                dataIndex : 'REALTIME',
                width : 100,
                renderer : function(v){
                	if(v==null){
                		return 0;
                	}
                	return v.toFixed(2);
                }
            }, {
                header : '计划生产时间（小时）',
                dataIndex : 'PLANTIME',
                width : 100,
                renderer : function(v){
                	if(v==null){
                		return 0;
                	}
                	return v.toFixed(2);
                }
            }, {
                header : '停线时间（扣除休息）',
                dataIndex : 'RESTTIME',
                width : 120,
                renderer : function(v){
                	if(v==null){
                		return 0;
                	}
                	return v.toFixed(2);
                }
            }, {
                header : '开动率（%）',
                dataIndex : 'DDRATE',
                width : 100,
                renderer : function(value){
                    return value+'%'
                }
            }, {
                header : '实际产量',
                dataIndex : 'EventDate29',
                width : 100
            }, {
                header : '计划产量',
                dataIndex : 'PLANCOUNT',
                width : 100
            },{
                header : '产能完成率（%）',
                dataIndex : 'channengwanclv',
                width : 120,
                renderer : function(value){
                    return value+'%'
                }
            }, {
                header : '停线总时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '故障总次数',
                dataIndex : 'STOPCOUNT',
                width : 100
            }, {
                header : 'CON',
                dataIndex : 'DATA3',
                width : 70
            }, {
                header : 'Andon',
                dataIndex : 'DATA4',
                width : 70
            }, {
                header : 'QCOS',
                dataIndex : 'DATA5',
                width : 70
            }, {
                header : 'FUL',
                dataIndex : 'DATA6',
                width : 70
            }, {
                header : 'SHO',
                dataIndex : 'DATA7',
                width : 80
            }, {
                header : 'ES',
                dataIndex : 'DATA8',
                width : 80
            }, {
                header : 'PS',
                dataIndex : 'DATA9',
                width : 100
            }, {
                header : '其它',
                dataIndex : 'DATA10',
                width : 80
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['ID','PRODUCTIONLINE','PRODUCTIONLINENAME','BANCI','CYCLETIME','BEIZHU','REALTIME','PLANTIME','DDRATE','DATA3','DATA4','DATA5',
            				'DATA6','DATA7','DATA8','DATA9','DATA10','EventDate29','PLANCOUNT','channengwanclv','STOPTIME','STOPCOUNT','RESTTIME','FORMAT_PRODUCTIONLINE'],
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
        
        var url = 'json?action=ZZCJ_EXPORT_PMC_SYSTEM_REPORT_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = PmcSystemReportPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&WORKDATE=' + PmcSystemReportPanel.ht_outputStore.baseParams['WORKDATE'];
        //班次信息
        url += '&PRODUCTIONLINENAME=' + PmcSystemReportPanel.ht_outputStore.baseParams['PRODUCTIONLINENAME'];
        
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
   

    //柱状图
    var columnPanel = new Ext.Panel({
       title : 'PMC总装车间总报表（%）',
        region : 'center',
        width : '50%',
        border : true,
        layout : 'fit',
        items: {   
            store: PmcSystemReportPanel.ht_outputStore,   
            xtype: 'columnchart',   
            url : 'lib/ext-3.3.0/resources/charts.swf',
            xField: 'FORMAT_PRODUCTIONLINE',
            extraStyle: {
                yAxis: {
                    majorGridLines: {
                        color: 0xdddddd
                    }
                }
            },
            series: [{     
                type: 'column',   
                displayName: '开动率',  
                margins : '20',
                yField: 'DDRATE',
                style: {   
                    mode: 'stretch',
                    size : 30,
                    padding : '0 60 0 0',
                    color : 0x00BB00
                }   
            },{     
                type: 'column',   
                displayName: '产能完成率',  
                margins : '20',
                yField: 'channengwanclv',
                style: {   
                    mode: 'stretch',
                    size : 30,
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
        items : [PmcSystemReportPanel, columnPanel, exportPanel],
        listeners : {
            beforedestroy : function(){
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
