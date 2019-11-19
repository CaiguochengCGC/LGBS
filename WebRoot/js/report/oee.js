/**
 * 开动率报表
 */
(function() {
    var queryType=[['','全部'],['1','冲压车间'],['2','车身车间']];
    //新增班次信息
    var bancidata = [["","全部"],["1","1班"],["2","2班"]];
    var banciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:bancidata,
     fields:["VALUE","TEXT"]
    });
    var monthEquStopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'YYYY_MM',
                format : 'Y-m',
                value : new Date()
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
            action : 'QUERY_PMC_PP_START_RATE_ACTION',
            outputTable : 'PMC_PP_START_RATE'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
               header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 120
            },{
               header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
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
                header : '本月开动率(%)',
                dataIndex : 'MMRATE',
                width : 100,
                renderer : function(value){
                    return value+'%'
                }
            }, {
                header : '本月停线时间（分钟）',
                dataIndex : 'MMSTOPTIMEE',
                width : 130
            }, {
                header : '本月实际产量',
                dataIndex : 'MMREALP',
                width : 100
            }, {
                header : '本月计划产量',
                dataIndex : 'MMPLANP',
                width : 100
            },{
                header : '1日',
                dataIndex : 'DATA1',
                width : 50
            }, {
                header : '2日',
                dataIndex : 'DATA2',
                width : 50
            }, {
                header : '3日',
                dataIndex : 'DATA3',
                width : 50
            }, {
                header : '4日',
                dataIndex : 'DATA4',
                width : 50
            }, {
                header : '5日',
                dataIndex : 'DATA5',
                width : 50
            }, {
                header : '6日',
                dataIndex : 'DATA6',
                width : 50
            }, {
                header : '7日',
                dataIndex : 'DATA7',
                width : 50
            }, {
                header : '8日',
                dataIndex : 'DATA8',
                width : 50
            }, {
                header : '9日',
                dataIndex : 'DATA9',
                width : 50
            }, {
                header : '10日',
                dataIndex : 'DATA10',
                width : 50
            }, {
                header : '11日',
                dataIndex : 'DATA11',
                width : 50
            }, {
                header : '12日',
                dataIndex : 'DATA12',
                width : 50
            }, {
                header : '13日',
                dataIndex : 'DATA13',
                width : 50
            }, {
                header : '14日',
                dataIndex : 'DATA14',
                width : 50
            }, {
                header : '15日',
                dataIndex : 'DATA15',
                width : 50
            }, {
                header : '16日',
                dataIndex : 'DATA16',
                width : 50
            }, {
                header : '17日',
                dataIndex : 'DATA17',
                width : 50
            }, {
                header : '18日',
                dataIndex : 'DATA18',
                width : 50
            }, {
                header : '19日',
                dataIndex : 'DATA19',
                width : 50
            }, {
                header : '20日',
                dataIndex : 'DATA20',
                width : 50
            }, {
                header : '21日',
                dataIndex : 'DATA21',
                width : 50
            }, {
                header : '22日',
                dataIndex : 'DATA22',
                width : 50
            }, {
                header : '23日',
                dataIndex : 'DATA23',
                width : 50
            }, {
                header : '24日',
                dataIndex : 'DATA24',
                width : 50
            }, {
                header : '25日',
                dataIndex : 'DATA25',
                width : 50
            }, {
                header : '26日',
                dataIndex : 'DATA26',
                width : 50
            }, {
                header : '27日',
                dataIndex : 'DATA27',
                width : 50
            }, {
                header : '28日',
                dataIndex : 'DATA28',
                width : 50
            }, {
                header : '29日',
                dataIndex : 'DATA29',
                width : 50
            }, {
                header : '30日',
                dataIndex : 'DATA30',
                width : 50
            }, {
                header : '31日',
                dataIndex : 'DATA31',
                width : 50
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['ID','PRODUCTIONLINE','PRODUCTIONLINENAME','DATA1','DATA2','DATA3','DATA4','DATA5',
            				'DATA6','DATA7','DATA8','DATA9','DATA10','DATA11','DATA12'
            				,'DATA13','DATA14','DATA15','DATA16','DATA17','DATA18','DATA19',
            				'DATA20','DATA21','DATA22','DATA23','DATA24','DATA25','DATA26','DATA27'
            				,'DATA28','DATA29','DATA30','DATA31','MMRATE','MMSTOPTIMEE','MMREALP','MMPLANP','BANCI','FORMAT_PRODUCTLINE',],
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
        
        var url = 'json?action=EXPORT_PMC_PP_START_RATE_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = monthEquStopLinePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&YYYY_MM=' + monthEquStopLinePanel.ht_outputStore.baseParams['YYYY_MM'];
        url += '&BANCI=' + monthEquStopLinePanel.ht_outputStore.baseParams['BANCI'];
        url += '&queryType=' + monthEquStopLinePanel.ht_outputStore.baseParams['queryType'];
        
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
   
    var monthPiePanel = new Ext.Panel({
        title : '本月开动率(%)',
        region : 'south',
        height : 350,
        border : true,
        items: {   
            xtype: 'linechart',   
            border : false,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            store:  monthEquStopLinePanel.ht_outputStore,   
            xField: 'FORMAT_PRODUCTLINE',
           
            yAxes : [new Ext.chart.NumericAxis({
                alwaysShowZero : true,
                calculateByLabelSize : true,
                labelRenderer : Ext.util.Format.numberRenderer('00.00'),
                position : 'left',
				title : '百分比'        
            })],
            
            series : [{     
                type: 'line',   
                margins : '0',
                yField: 'MMRATE',
                displayName: '本月开动率',
                axis : 'primary',
                style: {   
                	mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xFF0000
                }   
            },{
                type: 'column',
                margins : '0',
                yField: 'MMRATE',
                displayName: '本月开动率',
                style: {
                    mode: 'stretch',
                    size : 30,
                    padding : '0 60 0 0',
                    color : 0x00BB00
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

    var yearEquStopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'YYYY',
                format : 'Y',
                value : new Date()
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
            action : 'QUERY_PMC_PP_START_RATE_Y_ACTION',
            outputTable : 'PMC_PP_START_RATE_Y'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
               header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 120
            },{
               header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
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
                header : '本年开动率(%)',
                dataIndex : 'YYRATE',
                width : 100,
                renderer : function(value){
                    return value+'%'
                }
            }, {
                header : '本年停线时间（分钟）',
                dataIndex : 'YYSTOPTIMEE',
                width : 130
            }, {
                header : '本年实际产量',
                dataIndex : 'YYREALP',
                width : 100
            }, {
                header : '本年计划产量',
                dataIndex : 'YYPLANP',
                width : 100
            },{
                header : '1月',
                dataIndex : 'DATA1',
                width : 50,
                renderer : function(value){
                	if(value.length > 0){
                		return value+'%';
                	}else{
                		return value;
                	}
                }
            }, {
                header : '2月',
                dataIndex : 'DATA2',
                width : 50,
                renderer : function(value){
                	if(value.length > 0){
                		return value+'%';
                	}else{
                		return value;
                	}
                }
            }, {
                header : '3月',
                dataIndex : 'DATA3',
                width : 50,
                renderer : function(value){
                	if(value.length > 0){
                		return value+'%';
                	}else{
                		return value;
                	}
                }
            }, {
                header : '4月',
                dataIndex : 'DATA4',
                width : 50,
                renderer : function(value){
                	if(value.length > 0){
                		return value+'%';
                	}else{
                		return value;
                	}
                }
            }, {
                header : '5月',
                dataIndex : 'DATA5',
                width : 50,
                renderer : function(value){
                	if(value.length > 0){
                		return value+'%';
                	}else{
                		return value;
                	}
                }
            }, {
                header : '6月',
                dataIndex : 'DATA6',
                width : 50,
                renderer : function(value){
                	if(value.length > 0){
                		return value+'%';
                	}else{
                		return value;
                	}
                }
            }, {
                header : '7月',
                dataIndex : 'DATA7',
                width : 50,
                renderer : function(value){
                	if(value.length > 0){
                		return value+'%';
                	}else{
                		return value;
                	}
                }
            }, {
                header : '8月',
                dataIndex : 'DATA8',
                width : 50,
                renderer : function(value){
                	if(value.length > 0){
                		return value+'%';
                	}else{
                		return value;
                	}
                }
            }, {
                header : '9月',
                dataIndex : 'DATA9',
                width : 50,
                renderer : function(value){
                	if(value.length > 0){
                		return value+'%';
                	}else{
                		return value;
                	}
                }
            }, {
                header : '10月',
                dataIndex : 'DATA10',
                width : 50,
                renderer : function(value){
                	if(value.length > 0){
                		return value+'%';
                	}else{
                		return value;
                	}
                }
            }, {
                header : '11月',
                dataIndex : 'DATA11',
                width : 50,
                renderer : function(value){
                	if(value.length > 0){
                		return value+'%';
                	}else{
                		return value;
                	}
                }
            }, {
                header : '12月',
                dataIndex : 'DATA12',
                width : 50,
                renderer : function(value){
                	if(value.length > 0){
                		return value+'%';
                	}else{
                		return value;
                	}
                }
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PRODUCTIONLINE','PRODUCTIONLINENAME','DATA1','DATA2','DATA3','DATA4','DATA5',
            				'DATA6','DATA7','DATA8','DATA9','DATA10','DATA11','DATA12',
            				'YYRATE','YYSTOPTIMEE','YYREALP','YYPLANP','BANCI','FORMAT_PRODUCTLINE'],
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    exportFn1();
                    } 
            }]
        }
    });
   
    
    //导出excel
    var exportFn1 = function(){
        
        var url = 'json?action=EXPORT_PMC_PP_START_RATE_Y_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = yearEquStopLinePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&YYYY=' + yearEquStopLinePanel.ht_outputStore.baseParams['YYYY'];
        url += '&BANCI=' + yearEquStopLinePanel.ht_outputStore.baseParams['BANCI'];
        url += '&queryType=' + yearEquStopLinePanel.ht_outputStore.baseParams['queryType'];
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        exportPanel.setSrc(url);
    }

    //导出面板
    var exportPanel1 = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
   
    var yearPiePanel = new Ext.Panel({
        title : '开动率(%)',
        region : 'south',
        height : 350,
        border : true,
        items: {   
            xtype: 'linechart',   
            border : false,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            store:  yearEquStopLinePanel.ht_outputStore,   
            xField: 'FORMAT_PRODUCTLINE',
            
            yAxes : [new Ext.chart.NumericAxis({
                alwaysShowZero : true,
                calculateByLabelSize : true,
                labelRenderer : Ext.util.Format.numberRenderer('00.00'),
                position : 'left'

            })],
            
            series : [{     
                type: 'line',   
                margins : '0',
                yField: 'YYRATE',
                displayName: '本年开动率',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xFF0000 
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
                    },
                    labelRotation : -30,
                    labelRenderer : function(v){
                        return v;
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

    var weekEquStopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'YYYY',
                format : 'Y',
                value : new Date()
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
            action : 'QUERY_PMC_PP_START_RATE_W_ACTION',
            outputTable : 'PMC_PP_START_RATE_W'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 120
            },{
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
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
            },{
                header : '第一周',
                dataIndex : 'DATA1',
                width : 80
            }, {
                header : '第二周',
                dataIndex : 'DATA2',
                width : 80
            }, {
                header : '第三周',
                dataIndex : 'DATA3',
                width : 80
            }, {
                header : '第四周',
                dataIndex : 'DATA4',
                width : 80
            }, {
                header : '第五周',
                dataIndex : 'DATA5',
                width : 80
            }, {
                header : '第六周',
                dataIndex : 'DATA6',
                width : 80
            }, {
                header : '第七周',
                dataIndex : 'DATA7',
                width : 80
            }, {
                header : '第八周',
                dataIndex : 'DATA8',
                width : 80
            }, {
                header : '第九周',
                dataIndex : 'DATA9',
                width : 80
            }, {
                header : '第十周',
                dataIndex : 'DATA10',
                width : 80
            }, {
                header : '第十一周',
                dataIndex : 'DATA11',
                width : 80
            }, {
                header : '第十二周',
                dataIndex : 'DATA12',
                width : 80
            }, {
                header : '第十三周',
                dataIndex : 'DATA13',
                width : 80
            }, {
                header : '第十四周',
                dataIndex : 'DATA14',
                width : 80
            }, {
                header : '第十五周',
                dataIndex : 'DATA15',
                width : 80
            }, {
                header : '第十六周',
                dataIndex : 'DATA16',
                width : 80
            }, {
                header : '第十七周',
                dataIndex : 'DATA17',
                width : 80
            }, {
                header : '第十八周',
                dataIndex : 'DATA18',
                width : 80
            }, {
                header : '第十九周',
                dataIndex : 'DATA19',
                width : 80
            }, {
                header : '第二十周',
                dataIndex : 'DATA20',
                width : 80
            }, {
                header : '第二十一周',
                dataIndex : 'DATA21',
                width : 80
            }, {
                header : '第二十二周',
                dataIndex : 'DATA22',
                width : 80
            }, {
                header : '第二十三周',
                dataIndex : 'DATA23',
                width : 80
            }, {
                header : '第二十四周',
                dataIndex : 'DATA24',
                width : 80
            }, {
                header : '第二十五周',
                dataIndex : 'DATA25',
                width : 80
            }, {
                header : '第二十六周',
                dataIndex : 'DATA26',
                width : 80
            }, {
                header : '第二十七周',
                dataIndex : 'DATA27',
                width : 80
            }, {
                header : '第二十八周',
                dataIndex : 'DATA28',
                width : 80
            }, {
                header : '第二十九周',
                dataIndex : 'DATA29',
                width : 80
            }, {
                header : '第三十周',
                dataIndex : 'DATA30',
                width : 80
            }, {
                header : '第三十一周',
                dataIndex : 'DATA31',
                width : 80
            }, {
                header : '第三十二周',
                dataIndex : 'DATA32',
                width : 80
            }, {
                header : '第三十三周',
                dataIndex : 'DATA33',
                width : 80
            }, {
                header : '第三十四周',
                dataIndex : 'DATA34',
                width : 80
            }, {
                header : '第三十五周',
                dataIndex : 'DATA35',
                width : 80
            }, {
                header : '第三十六周',
                dataIndex : 'DATA36',
                width : 80
            }, {
                header : '第三十七周',
                dataIndex : 'DATA37',
                width : 80
            }, {
                header : '第三十八周',
                dataIndex : 'DATA38',
                width : 80
            }, {
                header : '第三十九周',
                dataIndex : 'DATA39',
                width :80
            }, {
                header : '第四十周',
                dataIndex : 'DATA40',
                width : 80
            }, {
                header : '第四十一周',
                dataIndex : 'DATA41',
                width : 80
            }, {
                header : '第四十二周',
                dataIndex : 'DATA42',
                width : 80
            }, {
                header : '第四十三周',
                dataIndex : 'DATA43',
                width : 80
            }, {
                header : '第四十四周',
                dataIndex : 'DATA44',
                width : 80
            }, {
                header : '第四十五周',
                dataIndex : 'DATA45',
                width : 80
            }, {
                header : '第四十六周',
                dataIndex : 'DATA46',
                width : 80
            }, {
                header : '第四十七周',
                dataIndex : 'DATA47',
                width : 80
            }, {
                header : '第四十八周',
                dataIndex : 'DATA48',
                width : 80
            }, {
                header : '第四十九周',
                dataIndex : 'DATA49',
                width : 80
            }, {
                header : '第五十周',
                dataIndex : 'DATA50',
                width : 80
            }, {
                header : '第五十一周',
                dataIndex : 'DATA51',
                width : 80
            }, {
                header : '第五十二周',
                dataIndex : 'DATA52',
                width : 80
            }],

            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['ID','PRODUCTIONLINE','PRODUCTIONLINENAME','DATA1','DATA2','DATA3','DATA4','DATA5',
                'DATA6','DATA7','DATA8','DATA9','DATA10','DATA11','DATA12'
                ,'DATA13','DATA14','DATA15','DATA16','DATA17','DATA18','DATA19',
                'DATA20','DATA21','DATA22','DATA23','DATA24','DATA25','DATA26','DATA27'
                ,'DATA28','DATA29','DATA30','DATA31',
                'DATA32','DATA33','DATA34','DATA35','DATA36','DATA37','DATA38','DATA39',
                'DATA40','DATA41','DATA42','DATA43','DATA44','DATA45','DATA46','DATA47',
                'DATA48','DATA49','DATA50','DATA51','DATA52'
                ,'BANCI'],
            ctTopbarComponts : [{
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    exportFn2();
                }
            }]
        }
    });

    //导出excel
    var exportFn2 = function () {

        var url = 'json?action=EXPORT_PMC_PP_START_RATE_W_ACTION';

        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();

        var columns = weekEquStopLinePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }

            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }

        url += '&YYYY=' + weekEquStopLinePanel.ht_outputStore.baseParams['YYYY'];
        url += '&BANCI=' + weekEquStopLinePanel.ht_outputStore.baseParams['BANCI'];
        url += '&queryType=' + weekEquStopLinePanel.ht_outputStore.baseParams['BANCI'];
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        exportPanel.setSrc(url);
    }

    //导出面板
    var exportPanel2 = new Ext.ux.ManagedIFramePanel({
        region: 'east',
        width: 0,
        border: true,
        bodyBorder: false,
        autoScroll: true,
        autoLoad: false,
        defaultSrc: null
    });

    /*var weekPiePanel = new Ext.Panel({
        title : '开动率(%)',
        region : 'south',
        height : 350,
        border : true,
        items: {
            xtype: 'linechart',
            border : false,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            store:  weekEquStopLinePanel.ht_outputStore,
            xField: 'FORMAT_PRODUCTLINE',

            yAxes : [new Ext.chart.NumericAxis({
                alwaysShowZero : true,
                calculateByLabelSize : true,
                labelRenderer : Ext.util.Format.numberRenderer('00.00'),
                position : 'left'

            })],

            series : [{
                type: 'line',
                margins : '0',
                yField: 'MMRATE',
                displayName: '周开动率',
                axis : 'primary',
                style: {
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xFF0000
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
                    },
                    labelRotation : -30,
                    labelRenderer : function(v){
                        return v;
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
    });*/

    var backPanel = new Ext.TabPanel({
        activeTab: 0,
        border : false,
        items : [{
            title : '月报表',
            border : false,
            layout : 'border',
            items : [monthEquStopLinePanel, monthPiePanel,exportPanel]
        },{
            title : '周报表',
            border : false,
            layout : 'border',
            // items : [weekEquStopLinePanel,weekPiePanel, exportPane2]
            items : [weekEquStopLinePanel, exportPanel2]
        },{
            title : '年报表',
            border : false,
            layout : 'border',
            items : [yearEquStopLinePanel, yearPiePanel,exportPanel1]
        }
        ],
        listeners : {
            beforedestroy : function(){
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
