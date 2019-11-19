/**
 * 瓶颈设备查询（按频次）
 */
(function() {
    var msgResource = {};
    var weekDefault;

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
    for(var i = 1 ; i <= 53; i++){
       weekStore.add(new Ext.data.Record({
            VALUE : i - 1,
            TEXT : '第' + i + '周'
       })) 
    }
    
    //瓶颈设备日报表
    var dayDatePpneckPanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            enableQueryButton : true, 
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'PPDATE',
                format : ht.config.format.DATE,
                value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
            },{
                xtype:'hidden',
                name : 'QUERY_TYPE',
                value : 0
            }],
            action : 'YQCJ_QUERY_PMC_DATE_PPNECK_FOR_STOPCOUNT_ACTION',
            outputTable : 'tabStopLineCount'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '工段',
                dataIndex : 'EventData7',
                width : 140
            },{
                header : '工位',
                dataIndex : 'EventData4',
                width : 100
            },{
                header : '设备',
                dataIndex : 'EventData5',
                width : 90
            }, {
                header : '停机时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 110
            }, {
                header : '停机次数',
                dataIndex : 'STOPCOUNT',
                width : 100
            }],
            
            isMultipleSelect : false,
            isPageAction : false,
            storeFields : ['EventData7','EventData4','EventData5', 'STOPTIME', 'STOPCOUNT'],
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    dayexportFn();
                } 
            }]
        },
        ctListeners : {
            beforeQuery : function(values) {
                return true;
			}
        }
    });
    
    //日报表图
    var dayChartPanel = new Ext.Panel({
        title : '设备停机时间（分钟）TOP5',
        region : 'east',
        width : 650,
        border : true,
        layout : 'fit',
        items: {   
            xtype: 'linechart',   
            border : false,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            store:  dayDatePpneckPanel.ht_outputStore,   
            xField: 'EventData4',
            
            yAxes : [new Ext.chart.NumericAxis({
                alwaysShowZero : true,
                calculateByLabelSize : true,
                labelRenderer : Ext.util.Format.numberRenderer('0,0'),
                position : 'right',
                title : '时间'
                
            }), new Ext.chart.NumericAxis({
                order : 'secondary',
                labelRenderer : Ext.util.Format.numberRenderer('0,0'),
                position : 'left',
                title : '次数',
                alwaysShowZero : true
            })],
            
            series : [{     
                type: 'column',   
                displayName: '设备停机次数',  
                margins : '0',
                yField: 'STOPCOUNT',
                axis : 'secondary',
                style: {   
                    mode: 'stretch',
                    size : 30,
                    weight : 1,
                    alpha : .9,
                    padding : '0 0 0 0',
                    color : 0x50b7c1
                }   
            }, {     
                type: 'line',   
                displayName: '设备停机时间（分钟）',  
                margins : '0',
                yField: 'STOPTIME',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
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
    dayDatePpneckPanel.ht_outputGrid.ownerCt.add(dayChartPanel);
   
    //日报表导出excel
    var dayexportFn = function(){
        
        var url = 'json?action=EXPORT_PMC_DATE_PPNECK_FOR_STOPCOUNT_DAY_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = dayDatePpneckPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        url += '&PPDATE=' + dayDatePpneckPanel.ht_outputStore.baseParams['PPDATE'];
        url += '&QUERY_TYPE=' + dayDatePpneckPanel.ht_outputStore.baseParams['QUERY_TYPE'];
        
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        dayexportPanel.setSrc(url);
    }

    //日报表导出面板
    var dayexportPanel = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
    //停机周报表
    var weekDatePpneckPanel = new ht.base.PlainPanel({
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
                value : weekDefault,
                hiddenName : 'PPDATE',
                name : 'PPDATE',
                editable : false
            },{
                xtype:'hidden',
                name : 'QUERY_TYPE',
                value : 3
            }],
            action : 'YQCJ_QUERY_PMC_DATE_PPNECK_FOR_STOPCOUNT_ACTION',
            outputTable : 'tabStopLineCount'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '工段',
                dataIndex : 'EventData7',
                width : 140
            },{
                header : '工位',
                dataIndex : 'EventData4',
                width : 100
            },{
                header : '设备',
                dataIndex : 'EventData5',
                width : 90
            }, {
                header : '停机时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 110
            }, {
                header : '停机次数',
                dataIndex : 'STOPCOUNT',
                width : 100
            }],
            
            isMultipleSelect : false,
            isPageAction : false,
            storeFields : ['EventData7','EventData4','EventData5', 'STOPTIME', 'STOPCOUNT'],
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    weekexportFn();
                } 
            }]
        },
        ctListeners : {
            beforeQuery : function(values) {
                
                return true;
            }
        }
    });
    
    //周报表导出excel
    var weekexportFn = function(){
        
//        var url = 'json?action=EXPORT_PMC_DATE_PPNECK_FOR_STOPCOUNT_WEEK_ACTION';
        var url = 'json?action=EXPORT_PMC_DATE_PPNECK_FOR_STOPCOUNT_DAY_ACTION';
                    			
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = weekDatePpneckPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&PPDATE=' + weekDatePpneckPanel.ht_outputStore.baseParams['PPDATE'];  
        url += '&QUERY_TYPE=' + weekDatePpneckPanel.ht_outputStore.baseParams['QUERY_TYPE'];    
        
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        weekexportPanel.setSrc(url);
    }

    //导出面板
    var weekexportPanel = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
    //周报表图
    var weekChartPanel = new Ext.Panel({
        title : '设备停机时间（分钟）TOP5',
        region : 'east',
        width : 650,
        border : true,
        layout : 'fit',
        items: {   
            xtype: 'linechart',   
            border : false,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            store:  weekDatePpneckPanel.ht_outputStore,   
            xField: 'EventData4',
            
            yAxes : [new Ext.chart.NumericAxis({
                alwaysShowZero : true,
                calculateByLabelSize : true,
                labelRenderer : Ext.util.Format.numberRenderer('0,0'),
                position : 'right',
                title : '时间'
                
            }), new Ext.chart.NumericAxis({
                order : 'secondary',
                labelRenderer : Ext.util.Format.numberRenderer('0,0'),
                position : 'left',
                title : '次数',
                alwaysShowZero : true
            })],
            
            series : [{     
                type: 'column',   
                displayName: '设备停机次数',  
                margins : '0',
                yField: 'STOPCOUNT',
                axis : 'secondary',
                style: {   
                    mode: 'stretch',
                    size : 30,
                    weight : 1,
                    alpha : .9,
                    padding : '0 0 0 0',
                    color : 0x50b7c1
                }   
            }, {     
                type: 'line',   
                displayName: '设备停机时间（分钟）',  
                margins : '0',
                yField: 'STOPTIME',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
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
    weekDatePpneckPanel.ht_outputGrid.ownerCt.add(weekChartPanel);
    
    //停机月报表
    var monthDatePpneckPanel = new ht.base.PlainPanel({
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
                xtype:'hidden',
                name : 'QUERY_TYPE',
                value : 1
            }],
            action : 'YQCJ_QUERY_PMC_DATE_PPNECK_FOR_STOPCOUNT_ACTION',
            outputTable : 'tabStopLineCount'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '工段',
                dataIndex : 'EventData7',
                width : 140
            },{
                header : '工位',
                dataIndex : 'EventData4',
                width : 100
            },{
                header : '设备',
                dataIndex : 'EventData5',
                width : 90
            }, {
                header : '停机时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 110
            }, {
                header : '停机次数',
                dataIndex : 'STOPCOUNT',
                width : 100
            }],
            
            isMultipleSelect : false,
            isPageAction : false,
            storeFields : ['EventData7','EventData4','EventData5', 'STOPTIME', 'STOPCOUNT'],
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    monthexportFn();
                } 
            }]
        },
        ctListeners : {
            beforeQuery : function(values) {
                
                return true;
            }
        }
    });
    
    //月报表导出excel
    var monthexportFn = function(){
        
//        var url = 'json?action=EXPORT_PMC_DATE_PPNECK_FOR_STOPCOUNT_MONTH_ACTION';
        var url = 'json?action=EXPORT_PMC_DATE_PPNECK_FOR_STOPCOUNT_DAY_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = monthDatePpneckPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        url += '&PPDATE=' + monthDatePpneckPanel.ht_outputStore.baseParams['PPDATE'];
        url += '&QUERY_TYPE=' + monthDatePpneckPanel.ht_outputStore.baseParams['QUERY_TYPE'];
        
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        monthexportPanel.setSrc(url);
    }

    //月报表导出面板
    var monthexportPanel = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
     //月报表图
    var monthChartPanel = new Ext.Panel({
        title : '设备停机时间（分钟）TOP5',
        region : 'east',
        width : 650,
        border : true,
        layout : 'fit',
        items: {   
            xtype: 'linechart',   
            border : false,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            store:  monthDatePpneckPanel.ht_outputStore,   
            xField: 'EventData4',
            
            yAxes : [new Ext.chart.NumericAxis({
                alwaysShowZero : true,
                calculateByLabelSize : true,
                labelRenderer : Ext.util.Format.numberRenderer('0,0'),
                position : 'right',
                title : '时间'
                
            }), new Ext.chart.NumericAxis({
                order : 'secondary',
                labelRenderer : Ext.util.Format.numberRenderer('0,0'),
                position : 'left',
                title : '次数',
                alwaysShowZero : true
            })],
            
            series : [{     
                type: 'column',   
                displayName: '设备停机次数',  
                margins : '0',
                yField: 'STOPCOUNT',
                axis : 'secondary',
                style: {   
                    mode: 'stretch',
                    size : 30,
                    weight : 1,
                    alpha : .9,
                    padding : '0 0 0 0',
                    color : 0x50b7c1
                }   
            }, {     
                type: 'line',   
                displayName: '设备停机时间（分钟）',  
                margins : '0',
                yField: 'STOPTOME',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
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
    monthDatePpneckPanel.ht_outputGrid.ownerCt.add(monthChartPanel);
    
    //停机年报表
    var yearDatePpneckPanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype:'datefield',
                name : 'PPDATE',
                fieldLabel : '年份',
                value : new Date(),
                format : 'Y'
            },{
                xtype:'hidden',
                name : 'QUERY_TYPE',
                value : 2
            }],
            action : 'YQCJ_QUERY_PMC_DATE_PPNECK_FOR_STOPCOUNT_ACTION',
            outputTable : 'tabStopLineCount'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '工段',
                dataIndex : 'EventData7',
                width : 140
            },{
                header : '工位',
                dataIndex : 'EventData4',
                width : 100
            },{
                header : '设备',
                dataIndex : 'EventData5',
                width : 90
            }, {
                header : '停机时间（分钟）',
                dataIndex : 'STOPTIME',
                width : 110
            }, {
                header : '停机次数',
                dataIndex : 'STOPCOUNT',
                width : 100
            }],
            
            isMultipleSelect : false,
            isPageAction : false,
            storeFields : ['EventData7','EventData4','EventData5', 'STOPTIME', 'STOPCOUNT'],
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    yearexportFn();
                } 
            }]
        },
        ctListeners : {
            beforeQuery : function(values) {
                
                return true;
            }
        }
    });
    
    //年报表图
    var yearChartPanel = new Ext.Panel({
        title : '设备停机时间（分钟）TOP5',
        region : 'east',
        width : 650,
        border : true,
        layout : 'fit',
        items: {   
            xtype: 'linechart',   
            border : false,
            url : 'lib/ext-3.3.0/resources/charts.swf',
            store:  yearDatePpneckPanel.ht_outputStore,   
            xField: 'EventData4',
            
            yAxes : [new Ext.chart.NumericAxis({
                alwaysShowZero : true,
                calculateByLabelSize : true,
                labelRenderer : Ext.util.Format.numberRenderer('0,0'),
                position : 'right',
                title : '时间'
                
            }), new Ext.chart.NumericAxis({
                order : 'secondary',
                labelRenderer : Ext.util.Format.numberRenderer('0,0'),
                position : 'left',
                title : '次数',
                alwaysShowZero : true
            })],
            
            series : [{     
                type: 'column',   
                displayName: '设备停机次数',  
                margins : '0',
                yField: 'STOPCOUNT',
                axis : 'secondary',
                style: {   
                    mode: 'stretch',
                    size : 30,
                    weight : 1,
                    alpha : .9,
                    padding : '0 0 0 0',
                    color : 0x50b7c1
                }   
            }, {     
                type: 'line',   
                displayName: '设备停机时间（分钟）',  
                margins : '0',
                yField: 'STOPTIME',
                axis : 'primary',
                style: {   
                    mode: 'stretch',
                    size : 10,
                    padding : '0 0 0 0',
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
    yearDatePpneckPanel.ht_outputGrid.ownerCt.add(yearChartPanel);
    
    //年报表导出excel
    var yearexportFn = function(){
        
//        var url = 'json?action=EXPORT_PMC_DATE_PPNECK_FOR_STOPCOUNT_YEAR_ACTION';
        var url = 'json?action=EXPORT_PMC_DATE_PPNECK_FOR_STOPCOUNT_DAY_ACTION';
                    			
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = yearDatePpneckPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        url += '&PPDATE=' + yearDatePpneckPanel.ht_outputStore.baseParams['PPDATE'];
        url += '&QUERY_TYPE=' + yearDatePpneckPanel.ht_outputStore.baseParams['QUERY_TYPE'];
        
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        yearexportPanel.setSrc(url);
    }

    //年报表导出面板
    var yearexportPanel = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
    var backPanel = new Ext.TabPanel({
        activeTab: 0,
        border : false,
        items : [{
            title : '日报表',
            border : false,
            layout : 'border',
            items : [dayDatePpneckPanel,dayexportPanel]
        },{
            title : '周报表',
            border : false,
            layout : 'border',
            items : [weekDatePpneckPanel,weekexportPanel]
        },{
            title : '月报表',
            border : false,
            layout : 'border',
            items : [monthDatePpneckPanel,monthexportPanel]
        },{
            title : '年报表',
            border : false,
            layout : 'border',
            items : [yearDatePpneckPanel,yearexportPanel]
        }],
        listeners : {
            beforedestroy : function(){
//                weekStore.destroy();
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
