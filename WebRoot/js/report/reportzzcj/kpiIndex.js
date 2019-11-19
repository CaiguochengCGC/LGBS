/**
 * KPI报表
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

    
    //KPI日报表
    var equStopLinePanel = new ht.base.PlainPanel({
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
            }],
            enableQueryButton : true,
            action : 'ZZCJ_QUERY_PMC_PP_KPIDAY_ACTION',
            outputTable : 'PMC_PP_KPIDAY'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 120
            }, {
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
                header : '当日MTTR(分)',
                dataIndex : 'MTTR',
                width : 120
            }, {
                header : '当日MTBF(分)',
                dataIndex : 'MTBF',
                width : 120
            }, {
                header : '当日工段利用率(%)',
                dataIndex : 'EQMRATE',
                width : 120,
                renderer : function(value, p, record){
                	return value+'%';
                }
            }, {
                header : '当日工段停机率(%)',
                dataIndex : 'EQPSTOP',
                width : 120,
                renderer : function(value, p, record){
                	return value+'%';
                }
            }, {
                header : '当日停线时间(分)',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '当日故障次数',
                dataIndex : 'STOPCOUNT',
                width : 120
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['ID','PPDATE','PRODUCTIONLINE','PRODUCTIONLINENAME','BANCI','MTTR','MTBF','EQMRATE','EQPSTOP','STOPTIME','STOPCOUNT','FORMAT_PRODUCTIONLINE'],
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
            }/*,{ 
                iconCls : 'excel',
                text : '图形导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 4,              
                handler : function() {
                    picexportFn();
                } 
            }*/]
        },
        ctListeners : {
            beforeQuery :function(values){

                return true;
            }
           
        }
    });
    
     //导出excel
    var exportFn = function(){
        var url = 'json?action=ZZCJ_EXPORT_PMC_PP_KPIDAY_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = equStopLinePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&PPDATE=' + equStopLinePanel.ht_outputStore.baseParams['PPDATE'];
        //加入班次信息
        url += '&BANCI=' + equStopLinePanel.ht_outputStore.baseParams['BANCI'];
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        exportPanel.setSrc(url);
    }
    
    //图形导出函数
    /*var picexportFn = function(){

        var url = 'json?action=EXPORT_PMC_PP_KPIDAY_PIC_ACTION';

        url += '&PPDATE=' + equStopLinePanel.ht_outputStore.baseParams['PPDATE'];
      
        exportPanel.setSrc(url);
    }*/

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
    
    //日报表图
    var piePanel = new Ext.Panel({
        title : 'KPI',
        region : 'south',
        height : 200,
        border : true,
        items: {   
            xtype: 'linechart',   
            border : false,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            store:  equStopLinePanel.ht_outputStore,   
            xField: 'FORMAT_PRODUCTIONLINE',
            
            yAxes : [new Ext.chart.NumericAxis({
                alwaysShowZero : true,
                calculateByLabelSize : true,
                order : 'primary',
                labelRenderer : Ext.util.Format.numberRenderer('000.00'),
                position : 'right'
//                title : '时间'
                
            }), new Ext.chart.NumericAxis({
                order : 'secondary',
                labelRenderer : Ext.util.Format.numberRenderer('00.00'),
                position : 'left',
                title : '百分比',
                alwaysShowZero : true
            })],
            
            series : [{     
                type: 'line',   
                margins : '0',
                yField: 'MTTR',
                displayName: '当日MTTR(分)',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0x50b7c1
                }   
            }, {     
                type: 'line',   
                margins : '0',
                yField: 'MTBF',
                displayName: '当日MTBF(分)',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0x00BB00
                } 
            }, {     
                type: 'line',   
                margins : '0',
                yField: 'EQMRATE',
                displayName: '当日工段利用率',
                axis : 'secondary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xFFD700
                } 
             }, {     
                type: 'line',   
                margins : '0',
                yField: 'EQPSTOP',
                displayName: '当日工段停机率',
                axis : 'secondary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xFFB6C1
                } 
            }, {     
                type: 'line',   
                margins : '0',
                yField: 'STOPTIME',
                displayName: '当日停线时间(分)',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xFF0000
                } 
            }, {     
                type: 'line',   
                margins : '0',
                yField: 'STOPCOUNT',
                displayName: '当日故障次数',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xC0FF3E
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
    
    //月报表
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
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            }],
            action : 'ZZCJ_QUERY_PMC_PP_KPIMONTH_ACTION',
            outputTable : 'PMC_PP_KPIMONTH'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 120
            },{
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            },{
                header : '班次',
                dataIndex : 'BANCI',
                width : 150,
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
            },  {
                header : '当月MTTR(分)',
                dataIndex : 'MTTR',
                width : 120
            }, {
                header : '当月MTBF(分)',
                dataIndex : 'MTBF',
                width : 120
            }, {
                header : '当月工段利用率(%)',
                dataIndex : 'EQMRATE',
                width : 120,
                renderer : function(value, p, record){
                	return value+'%';
                }
            }, {
                header : '当月工段停机率(%)',
                dataIndex : 'EQPSTOP',
                width : 120,
                renderer : function(value, p, record){
                	return value+'%';
                }
            }, {
                header : '当月停线时间(分)',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '当月故障次数',
                dataIndex : 'STOPCOUNT',
                width : 120
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['ID','YYYY_MM','PRODUCTIONLINE','PRODUCTIONLINENAME','BANCI','MTTR','MTBF','EQMRATE','EQPSTOP','STOPTIME','STOPCOUNT'],
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
            }/*,{ 
                iconCls : 'excel',
                text : '图形导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 4,              
                handler : function() {
                    picexportFn1();
                } 
            }*/]
        },
        ctListeners : {
            beforeQuery :function(values){
                return true;
            }
           
        }
    });
   
    //按月导出excel
    var exportFn1 = function(){
        
        var url = 'json?action=ZZCJ_EXPORT_PMC_PP_KPIMONTH_ACTION';
                    
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
      //加入班次信息
        url += '&BANCI=' + monthEquStopLinePanel.ht_outputStore.baseParams['BANCI'];
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        exportPanel1.setSrc(url);
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
    var monthPiePanel = new Ext.Panel({
        title : 'KPI',
        region : 'south',
        height : 200,
        border : true,
        items: {   
            xtype: 'linechart',   
            border : false,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            store:  monthEquStopLinePanel.ht_outputStore,   
            xField: 'PRODUCTIONLINE',
            
            yAxes : [new Ext.chart.NumericAxis({
                alwaysShowZero : true,
                calculateByLabelSize : true,
                order : 'primary',
                labelRenderer : Ext.util.Format.numberRenderer('000.00'),
                position : 'right'
//                title : '时间'
                
            }), new Ext.chart.NumericAxis({
                order : 'secondary',
                labelRenderer : Ext.util.Format.numberRenderer('00.00'),
                position : 'left',
                title : '百分比',
                alwaysShowZero : true
            })],
            
            series : [{     
                type: 'line',   
                margins : '0',
                yField: 'MTTR',
                displayName: '当月MTTR(分)',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0x50b7c1
                }   
            }, {     
                type: 'line',   
                margins : '0',
                yField: 'MTBF',
                displayName: '当月MTBF(分)',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0x00BB00
                } 
            }, {     
                type: 'line',   
                margins : '0',
                yField: 'EQMRATE',
                displayName: '当月工段利用率',
                axis : 'secondary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xFFD700
                } 
             }, {     
                type: 'line',   
                margins : '0',
                yField: 'EQPSTOP',
                displayName: '当月工段停机率',
                axis : 'secondary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xFFB6C1
                } 
            }, {     
                type: 'line',   
                margins : '0',
                yField: 'STOPTIME',
                displayName: '当月停线时间(分)',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xFF0000
                } 
            }, {     
                type: 'line',   
                margins : '0',
                yField: 'STOPCOUNT',
                displayName: '当月故障次数',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xC0FF3E
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
     
    //年报表
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
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            }],
            action : 'ZZCJ_QUERY_PMC_PP_KPIYEAR_ACTION',
            outputTable : 'PMC_PP_KPIYEAR'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 120
            }, {
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 150
            }, {
                header : '班次',
                dataIndex : 'BANCI',
                width : 150,
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
                header : '当年MTTR(分)',
                dataIndex : 'MTTR',
                width : 120
            }, {
                header : '当年MTBF(分)',
                dataIndex : 'MTBF',
                width : 120
            }, {
                header : '当年工段利用率(%)',
                dataIndex : 'EQMRATE',
                width : 120,
                renderer : function(value, p, record){
                	return value+'%';
                }
            }, {
                header : '当年工段停机率(%)',
                dataIndex : 'EQPSTOP',
                width : 120,
                renderer : function(value, p, record){
                	return value+'%';
                }
            }, {
                header : '当年停线时间(分)',
                dataIndex : 'STOPTIME',
                width : 120
            }, {
                header : '当年故障次数',
                dataIndex : 'STOPCOUNT',
                width : 120
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['ID','YYYY','PRODUCTIONLINE','PRODUCTIONLINENAME','BANCI','MTTR','MTBF','EQMRATE','EQPSTOP','STOPTIME','STOPCOUNT'],
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
            }/*,{ 
                iconCls : 'excel',
                text : '图形导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    picexportFn2();
                } 
            }*/]
        },
        ctListeners : {
            beforeQuery :function(values){
                return true;
            }
           
        }
    });
   
     //按年导出excel
    var exportFn2 = function(){
        
        var url = 'json?action=ZZCJ_EXPORT_PMC_PP_KPIYEAR_ACTION';
                    
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
      //加入班次信息
        url += '&BANCI=' + yearEquStopLinePanel.ht_outputStore.baseParams['BANCI'];
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        exportPanel2.setSrc(url);
    }
    
    //导出面板
    var exportPanel2 = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
    var yearPiePanel = new Ext.Panel({
        title : 'KPI',
        region : 'south',
        height : 200,
        border : true,
        items: {   
            xtype: 'linechart',   
            border : false,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            store:  yearEquStopLinePanel.ht_outputStore,   
            xField: 'PRODUCTIONLINE',
            
            yAxes : [new Ext.chart.NumericAxis({
                alwaysShowZero : true,
                calculateByLabelSize : true,
                order : 'primary',
                labelRenderer : Ext.util.Format.numberRenderer('000.00'),
                position : 'right'
//                title : '时间'
                
            }), new Ext.chart.NumericAxis({
                order : 'secondary',
                labelRenderer : Ext.util.Format.numberRenderer('00.00'),
                position : 'left',
                title : '百分比',
                alwaysShowZero : true
            })],
            
            series : [{     
                type: 'line',   
                margins : '0',
                yField: 'MTTR',
                displayName: '当年MTTR(分)',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0x50b7c1
                }   
            }, {     
                type: 'line',   
                margins : '0',
                yField: 'MTBF',
                displayName: '当年MTBF(分)',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0x00BB00
                } 
            }, {     
                type: 'line',   
                margins : '0',
                yField: 'EQMRATE',
                displayName: '当年工段利用率',
                axis : 'secondary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xFFD700
                } 
             }, {     
                type: 'line',   
                margins : '0',
                yField: 'EQPSTOP',
                displayName: '当年工段停线率',
                axis : 'secondary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xFFB6C1
                } 
            }, {     
                type: 'line',   
                margins : '0',
                yField: 'STOPTIME',
                displayName: '当年停线时间(分)',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xFF0000
                } 
            }, {     
                type: 'line',   
                margins : '0',
                yField: 'STOPCOUNT',
                displayName: '当年故障次数',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
                    color : 0xC0FF3E
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
     
    var backPanel = new Ext.TabPanel({
        activeTab: 0,
        border : false,
        items : [{
            title : '日报表',
            border : false,
            layout : 'border',
            //items : [{
                //region : 'center',
                //layout : 'border',
                items : [equStopLinePanel,piePanel, exportPanel]
                //items : [planDatePanel,equStopLinePanel,exportPanel]
            //}, piePanel]
        },{
            title : '月报表',
            border : false,
            layout : 'border',
            items : [monthEquStopLinePanel, monthPiePanel,exportPanel1]
        },{
            title : '年报表',
            border : false,
            layout : 'border',
            items : [yearEquStopLinePanel, yearPiePanel,exportPanel2]}
        ],
        listeners : {
            beforedestroy : function(){
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
