/**
 * 固定报表 -> 产量报表
 */
(function() {
    var msgResource = {};
    var totolStopTime;
    //新增班次信息
    var bancidata = [["","全部"],["1","1班"],["2","2班"],["3","3班"]];
    var banciStore = new Ext.data.SimpleStore({
     auteLoad:true, //此处设置为自动加载
     data:bancidata,
     fields:["VALUE","TEXT"]
    });

    //产量日报表
    var dayYieldPanel = new ht.base.RowEditorPanel({
        region : 'center',
        queryFormConfig : {
            enableQueryButton : true, 
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                name : 'EventData',
                format : ht.config.format.DATE,
                value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
            }],
            action : 'YQCJ_QUERY_TAB_PRODUCT_HOUR_ACTION',
            outputTable : 'TAB_PRODUCT_HOUR'
        
//            enableQueryButton : false,
//            action : 'QUERY_TAB_PRODUCT_HOUR_ACTION',
//            outputTable : 'TAB_PRODUCT_HOUR'
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
                header : '计划产量',
                dataIndex : 'PLANDATA',
                width : 100,
                editor : {
                    xtype : 'numberfield',
                    name : 'PLANDATA',
                    minVaule:1
                }
            },{
                header : '实际产量',
                dataIndex : 'EventDate29',
                width : 100
            },{
                header : '下线量',
                dataIndex : 'EventDate27',
                width : 100,
                editor : {
                    xtype : 'numberfield',
                    name : 'EventDate27'
                }
            },{
                header : '上线量',
                dataIndex : 'EventDate28',
                width : 100,
                editor : {
                    xtype : 'numberfield',
                    name : 'EventDate28'
                }
            },{
                header : '实际完成率',
                dataIndex : 'RATE',
                width : 100,
                renderer : function(value, p, record) {
                    return record.get('RATE') +  '%';
                }
            },{
                header : '8:00-9:00',
                dataIndex : 'EventDate3',
                width : 100
            }, {
                header : '9:00-10:00',
                dataIndex : 'EventDate4',
                width : 100
            }, {
                header : '10:00-11:00',
                dataIndex : 'EventDate5',
                width : 100
            },{
                header : '11:00-12:00',
                dataIndex : 'EventDate6',
                width : 100
            },{
                header : '12:00-13:00',
                dataIndex : 'EventDate7',
                width : 100
            },{
                header : '13:00-14:00',
                dataIndex : 'EventDate8',
                width : 100
            },{
                header : '14:00-15:00',
                dataIndex : 'EventDate9',
                width : 100
            },{
                header : '15:00-16:00',
                dataIndex : 'EventDate10',
                width : 100
            },{
                header : '16:00-17:00',
                dataIndex : 'EventDate11',
                width : 100
            },{
                header : '17:00-18:00',
                dataIndex : 'EventDate12',
                width : 100
            },{
                header : '18:00-19:00',
                dataIndex : 'EventDate13',
                width : 100
            },{
                header : '19:00-20:00',
                dataIndex : 'EventDate14',
                width : 100
            },{
                header : '20:00-21:00',
                dataIndex : 'EventDate15',
                width : 100
            },{
                header : '21:00-22:00',
                dataIndex : 'EventDate16',
                width : 100
            },{
                header : '22:00-23:00',
                dataIndex : 'EventDate17',
                width : 100
            },{
                header : '23:00-00:00',
                dataIndex : 'EventDate18',
                width : 100
            },{
                header : '00:00-01:00',
                dataIndex : 'EventDate19',
                width : 100
            },{
                header : '01:00-02:00',
                dataIndex : 'EventDate20',
                width : 100
            },{
                header : '02:00-03:00',
                dataIndex : 'EventDate21',
                width : 100
            },{
                header : '03:00-04:00',
                dataIndex : 'EventDate22',
                width : 100
            },{
                header : '04:00-05:00',
                dataIndex : 'EventDate23',
                width : 100
            },{
                header : '05:00-06:00',
                dataIndex : 'EventDate24',
                width : 100
            },{
                header : '06:00-07:00',
                dataIndex : 'EventDate25',
                width : 100
            },{
                header : '07:00-08:00',
                dataIndex : 'EventDate26',
                width : 100
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['ID','EventData','EventDate1','EventDate3','BANCI', 'EventDate4', 'EventDate5','EventDate6','EventDate7','EventDate8','EventDate9','EventDate10','EventDate11','EventDate12',
                        'EventDate13','EventDate14','EventDate15','EventDate16','EventDate17','EventDate18','EventDate19','EventDate20','EventDate21','EventDate22','EventDate23','EventDate24'
                        ,'EventDate25','EventDate26','EventDate27','EventDate28','EventDate29','EventDate30','EventDate31','EventDate32','PLANDATA','RATE'],
            updateBtn : {
                            action : 'YQCJ_SAVE_FSPP_ACTION',
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
        
        var url = 'json?action=YQCJ_EXPORT_TAB_PRODUCT_HOUR_ACTION';
                    
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
        //拼接班次信息
        url += '&BANCI=' + dayYieldPanel.ht_outputStore.baseParams['BANCI'];
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
    
    //日柱状图
    var daycolumnPanel = new Ext.Panel({
        title : '产量（柱状图）',
        region : 'center',
        width : '50%',
        border : true,
        layout : 'fit',
        items: {   
            store: dayYieldPanel.ht_outputStore,   
            xtype: 'columnchart',   
            url : 'lib/ext-3.3.0/resources/charts.swf',
            xField: 'EventDate1',
            extraStyle: {
                yAxis: {
                    majorGridLines: {
                        color: 0xdddddd
                    }
                }
            },
            series: [{     
                type: 'column',   
                displayName: '计划产量',  
                margins : '20',
                yField: 'PLANDATA',
                style: {   
                    mode: 'stretch',
                    size : 30,
                    padding : '0 60 0 0',
                    color : 0x00BB00
                }   
            },{     
                type: 'column',   
                displayName: '实际产量',  
                margins : '20',
                yField: 'EventDate29',
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

    //日折线图
    var daylinePanel = new Ext.Panel({
        title : '产量（折线图）',
        region : 'west',
        width : "50%",
        border : true,
        items: {  
            store: dayYieldPanel.ht_outputStore,   
            xtype: 'linechart',   
            url : 'lib/ext-3.3.0/resources/charts.swf',
            xField: 'EventDate1',
            extraStyle: {
                yAxis: {
                    majorGridLines: {
                        color: 0xdddddd
                    }
                }
            },
            series: [{     
                type: 'line',   
                displayName: '计划产量',  
                margins : '5',
                yField: 'PLANDATA',
                style: {   
                    mode: 'stretch',
                    size : 5,
                    padding : '0 60 0 0',
                    color : 0x00BB00
                }   
            },{     
                type: 'line',   
                displayName: '实际产量',  
                margins : '5',
                yField: 'EventDate29',
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
    
    //产量周报表
    var weekYieldPanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype:'datefield',
                name : 'YYYY',
                fieldLabel : '年份',
                value : new Date(),
                format : 'Y'
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
            action : 'YQCJ_QUERY_PMC_PP_WEEK_ACTION',
            outputTable : 'PMC_PP_WEEK'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工段/周',
                dataIndex : 'PRODUCTIONLINE',
                width : 100
            }, {
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 130
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
                header : '计划产量',
                dataIndex : 'YYPLANP',
                width : 80
            },{
                header : '总产量',
                dataIndex : 'YYREALP',
                width : 80
            },{
                header : '实际完成率',
                dataIndex : 'WEEKRATE',
                width : 80
            },{
                header : '第1周',
                dataIndex : 'DATA1',
                width : 70
            }, {
                header : '第2周',
                dataIndex : 'DATA2',
                width : 70
            }, {
                header : '第3周',
                dataIndex : 'DATA3',
                width : 70
            },{
                header : '第4周',
                dataIndex : 'DATA4',
                width : 70
            },{
                header : '第5周',
                dataIndex : 'DATA5',
                width : 70
            },{
                header : '第6周',
                dataIndex : 'DATA6',
                width : 70
            },{
                header : '第7周',
                dataIndex : 'DATA7',
                width : 100
            },{
                header : '第8周',
                dataIndex : 'DATA8',
                width : 70
            },{
                header : '第9周',
                dataIndex : 'DATA9',
                width : 70
            },{
                header : '第10周',
                dataIndex : 'DATA10',
                width : 70
            },{
                header : '第11周',
                dataIndex : 'DATA11',
                width : 70
            },{
                header : '第12周',
                dataIndex : 'DATA12',
                width : 70
            },{
                header : '第13周',
                dataIndex : 'DATA13',
                width : 70
            },{
                header : '第14周',
                dataIndex : 'DATA14',
                width : 70
            },{
                header : '第15周',
                dataIndex : 'DATA15',
                width : 70
            },{
                header : '第16周',
                dataIndex : 'DATA16',
                width : 70
            },{
                header : '第17周',
                dataIndex : 'DATA17',
                width : 70
            },{
                header : '第18周',
                dataIndex : 'DATA18',
                width : 70
            },{
                header : '第19周',
                dataIndex : 'DATA19',
                width : 70
            },{
                header : '第20周',
                dataIndex : 'DATA20',
                width : 70
            },{
                header : '第21周',
                dataIndex : 'DATA21',
                width : 70
            },{
                header : '第22周',
                dataIndex : 'DATA22',
                width : 70
            },{
                header : '第23周',
                dataIndex : 'DATA23',
                width : 70
            },{
                header : '第24周',
                dataIndex : 'DATA24',
                width : 70
            },{
                header : '第25周',
                dataIndex : 'DATA25',
                width : 70
            },{
                header : '第26周',
                dataIndex : 'DATA26',
                width : 70
            },{
                header : '第27周',
                dataIndex : 'DATA27',
                width : 70
            },{
                header : '第28周',
                dataIndex : 'DATA28',
                width : 70
            },{
                header : '第29周',
                dataIndex : 'DATA29',
                width : 70
            },{
                header : '第30周',
                dataIndex : 'DATA30',
                width : 70
            },{
                header : '第31周',
                dataIndex : 'DATA31',
                width : 70
            },{
                header : '第32周',
                dataIndex : 'DATA32',
                width : 70
            },{
                header : '第33周',
                dataIndex : 'DATA33',
                width : 70
            },{
                header : '第34周',
                dataIndex : 'DATA34',
                width : 70
            },{
                header : '第35周',
                dataIndex : 'DATA35',
                width : 70
            }, {
                header : '第36周',
                dataIndex : 'DATA36',
                width : 70
            }, {
                header : '第37周',
                dataIndex : 'DATA37',
                width : 70
            }, {
                header : '第38周',
                dataIndex : 'DATA38',
                width : 70
            },{
                header : '第39周',
                dataIndex : 'DATA39',
                width : 70
            },{
                header : '第40周',
                dataIndex : 'DATA40',
                width : 70
            },{
                header : '第41周',
                dataIndex : 'DATA41',
                width : 70
            },{
                header : '第42周',
                dataIndex : 'DATA42',
                width : 70
            },{
                header : '第43周',
                dataIndex : 'DATA43',
                width : 70
            },{
                header : '第44周',
                dataIndex : 'DATA44',
                width : 70
            },{
                header : '第45周',
                dataIndex : 'DATA45',
                width : 70
            },{
                header : '第46周',
                dataIndex : 'DATA46',
                width : 70
            },{
                header : '第47周',
                dataIndex : 'DATA47',
                width : 70
            },{
                header : '第48周',
                dataIndex : 'DATA48',
                width : 70
            },{
                header : '第49周',
                dataIndex : 'DATA49',
                width : 70
            },{
                header : '第50周',
                dataIndex : 'DATA50',
                width : 70
            },{
                header : '第51周',
                dataIndex : 'DATA51',
                width : 70
            },{
                header : '第52周',
                dataIndex : 'DATA52',
                width : 60
            }],
            
            isMultipleSelect : false,
            isPageAction : false,
            storeFields : ['PRODUCTIONLINE','PRODUCTIONLINENAME','BANCI','DATA1', 'DATA2', 'DATA3', 'DATA4', 'DATA5', 'DATA6', 'DATA7', 'DATA8', 'DATA9'
            , 'DATA10', 'DATA11', 'DATA12', 'DATA13', 'DATA14', 'DATA15', 'DATA16', 'DATA17', 'DATA18', 'DATA19', 'DATA20', 'DATA21'
            , 'DATA22', 'DATA23', 'DATA24', 'DATA25', 'DATA26', 'DATA27', 'DATA28', 'DATA29', 'DATA30', 'DATA31', 'DATA32', 'DATA33'
            , 'DATA34', 'DATA35', 'DATA36', 'DATA37', 'DATA38', 'DATA39', 'DATA40', 'DATA41', 'DATA42', 'DATA43', 'DATA44', 'DATA45'
            , 'DATA46', 'DATA47', 'DATA48', 'DATA49', 'DATA50', 'DATA51', 'DATA52','YYPLANP','YYREALP','WEEKRATE'],
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 1,
                handler : function() {
                   exportFn1();
                } 
            }]
        },
        ctListeners : {
           
        }
    });
    
    //导出excel
    var exportFn1 = function(){
        
        var url = 'json?action=YQCJ_EXPORT_PMC_PP_WEEK_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = weekYieldPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
//            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
//            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&YYYY=' + weekYieldPanel.ht_outputStore.baseParams['YYYY'];
        url += '&BANCI=' + weekYieldPanel.ht_outputStore.baseParams['BANCI'];
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        exportPanelWeek.setSrc(url);
    }

    //导出面板
    var exportPanelWeek = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
    //周柱状图
    var weekColumnPanel = new Ext.Panel({
        title : '周产量（柱状图）',
        region : 'center',
        width : "50%",
        border : true,
        layout : 'fit',
        items: {   
            xtype: 'columnchart',   
            url : 'lib/ext-3.3.0/resources/charts.swf',
            store : weekYieldPanel.ht_outputStore,  
            xField: 'PRODUCTIONLINE',
            extraStyle: {
                yAxis: {
                    majorGridLines: {
                        color: 0xdddddd
                    }
                }
            },
            series: [{     
                type: 'column',   
                displayName: '计划周产量',  
                margins : '20',
                yField: 'YYPLANP',
                style: {   
                    mode: 'stretch',
                    size : 30,
                    padding : '0 60 0 0',
                    color : 0x00BB00
                }   
            },{     
                type: 'column',   
                displayName: '实际周产量',  
                margins : '20',
                yField: 'YYREALP',
                style: {   
                    mode: 'stretch',
                    size : 30,
                    padding : '0 60 0 0',
                    color : 0x002400
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
    
    //周折线图
    var weekLinePanel = new Ext.Panel({
        title : '周产量（折线图）',
        region : 'west',
        width : "50%",
        border : true,
        items: {   
            xtype: 'linechart',   
            url : 'lib/ext-3.3.0/resources/charts.swf',
            store : weekYieldPanel.ht_outputStore,   
            xField: 'PRODUCTIONLINE',
            extraStyle: {
                yAxis: {
                    majorGridLines: {
                        color: 0xdddddd
                    }
                }
            },
            series: [{     
                type: 'line',   
                displayName: '计划周产量',  
                margins : '20',
                yField: 'YYPLANP',
                style: {   
                    mode: 'stretch',
                    size : 5,
                    padding : '0 60 0 0',
                    color : 0x00BB00
                }   
            },{     
                type: 'line',   
                displayName: '总周产量',  
                margins : '20',
                yField: 'YYREALP',
                style: {   
                    mode: 'stretch',
                    size : 5,
                    padding : '0 60 0 0',
                    color : 0x002400
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
    
    //产量月报表
    var monthYieldPanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype:'datefield',
                name : 'YYYY_MM',
                fieldLabel : '月份',
                value : new Date(),
                format : 'Y-m',
                renderer : function(value) {
                    return ht.pub.date.dateTimeRenderer(value);
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
                editable : false,
                listeners : {
	                select : function(c){
	                	//console.log(c);
	                	//PmcSystemReportPanel.findQueryCompment('EventDate1').setValue('wawawa');
	                }
	            }
            }],
            action : 'YQCJ_QUERY_PMC_PP_MM_ACTION',
            outputTable : 'PMC_PP_MM'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工段/日',
                dataIndex : 'PRODUCTIONLINE',
                width : 100
            },{
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
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
            },{
                header : '计划月产量',
                dataIndex : 'MMPLANP',
                width : 80
            },{
                header : '实际月产量',
                dataIndex : 'MMREALP',
                width : 80
            },{
                header : '本月完成率',
                dataIndex : 'MMRATE',
                width : 80
            },{
                header : '1日',
                dataIndex : 'DATA1',
                width : 55
            }, {
                header : '2日',
                dataIndex : 'DATA2',
                width : 55
            }, {
                header : '3日',
                dataIndex : 'DATA3',
                width : 55
            },{
                header : '4日',
                dataIndex : 'DATA4',
                width : 55
            },{
                header : '5日',
                dataIndex : 'DATA5',
                width : 55
            },{
                header : '6日',
                dataIndex : 'DATA6',
                width : 55
            },{
                header : '7日',
                dataIndex : 'DATA7',
                width : 55
            },{
                header : '8日',
                dataIndex : 'DATA8',
                width : 55
            },{
                header : '9日',
                dataIndex : 'DATA9',
                width : 55
            },{
                header : '10日',
                dataIndex : 'DATA10',
                width : 55
            },{
                header : '11日',
                dataIndex : 'DATA11',
                width : 55
            },{
                header : '12日',
                dataIndex : 'DATA12',
                width : 55
            },{
                header : '13日',
                dataIndex : 'DATA13',
                width : 55
            },{
                header : '14日',
                dataIndex : 'DATA14',
                width : 55
            },{
                header : '15日',
                dataIndex : 'DATA15',
                width : 55
            },{
                header : '16日',
                dataIndex : 'DATA16',
                width : 55
            },{
                header : '17日',
                dataIndex : 'DATA17',
                width : 55
            },{
                header : '18日',
                dataIndex : 'DATA18',
                width : 55
            },{
                header : '19日',
                dataIndex : 'DATA19',
                width : 55
            },{
                header : '20日',
                dataIndex : 'DATA20',
                width : 55
            },{
                header : '21日',
                dataIndex : 'DATA21',
                width : 55
            },{
                header : '22日',
                dataIndex : 'DATA22',
                width : 55
            },{
                header : '23日',
                dataIndex : 'DATA23',
                width : 55
            },{
                header : '24日',
                dataIndex : 'DATA24',
                width : 55
            },{
                header : '25日',
                dataIndex : 'DATA25',
                width : 55
            },{
                header : '26日',
                dataIndex : 'DATA26',
                width : 55
            },{
                header : '27日',
                dataIndex : 'DATA27',
                width : 55
            },{
                header : '28日',
                dataIndex : 'DATA28',
                width : 55
            },{
                header : '29日',
                dataIndex : 'DATA29',
                width : 55
            },{
                header : '30日',
                dataIndex : 'DATA30',
                width : 55
            },{
                header : '31日',
                dataIndex : 'DATA31',
                width : 55
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['YYYY_MM','PRODUCTIONLINE','PRODUCTIONLINENAME','BANCI','DATA1', 'DATA2', 'DATA3','DATA4','DATA5','DATA6','DATA7','DATA8'
            ,'DATA9','DATA10','DATA11','DATA12','DATA13','DATA14','DATA15','DATA16','DATA17','DATA18','DATA19','DATA20','DATA21'
            ,'DATA22','DATA23','DATA24','DATA25','DATA26','DATA27','DATA28','DATA29','DATA30','DATA31','MMPLANP','MMREALP','MMRATE'],
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
        },
        ctListeners : {
           
        }
    });
    
     //导出excel
    var exportFn2 = function(){
        
        var url = 'json?action=YQCJ_EXPORT_PMC_PP_MM_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = monthYieldPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&YYYY_MM=' + monthYieldPanel.ht_outputStore.baseParams['YYYY_MM'];
        url += '&BANCI=' + monthYieldPanel.ht_outputStore.baseParams['BANCI'];
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
    
    //月柱状图
    var monthColumnPanel = new Ext.Panel({
        title : '产量（柱状图）',
        region : 'center',
        width : "50%",
        border : true,
        layout : 'fit',
        items: {   
            store: monthYieldPanel.ht_outputStore,   
            xtype: 'columnchart',   
            url : 'lib/ext-3.3.0/resources/charts.swf',
            xField: 'PRODUCTIONLINE',
            extraStyle: {
                yAxis: {
                    majorGridLines: {
                        color: 0xdddddd
                    }
                }
            },
            series: [{     
                type: 'column',   
                displayName: '计划月产量',  
                margins : '20',
                yField: 'MMPLANP',
                style: {   
                    mode: 'stretch',
                    size : 30,
                    padding : '0 60 0 0',
                    color : 0x00BB00
                }   
            },{     
                type: 'column',   
                displayName: '实际月产量',  
                margins : '20',
                yField: 'MMREALP',
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
    
    //月折线图
    var monthLinePanel = new Ext.Panel({
        title : '产量（折线图）',
        region : 'west',
        width : "50%",
        border : true,
        items: {  
            store: monthYieldPanel.ht_outputStore,   
            xtype: 'linechart',   
            url : 'lib/ext-3.3.0/resources/charts.swf',
            xField: 'PRODUCTIONLINE',
            extraStyle: {
                yAxis: {
                    majorGridLines: {
                        color: 0xdddddd
                    }
                }
            },
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
            series: [{     
                type: 'line',   
                displayName: '计划月产量',  
                margins : '5',
                yField: 'MMPLANP',
                style: {   
                    mode: 'stretch',
                    size : 5,
                    padding : '0 60 0 0',
                    color : 0x00BB00
                }   
            },{     
                type: 'line',   
                displayName: '实际月产量',  
                margins : '5',
                yField: 'MMREALP',
                style: {   
                    mode: 'stretch',
                    size : 5,
                    padding : '0 60 0 0',
                    color : 0xBA55D3
                }   
            }],
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
    
    //产量年报表
    var yearYieldPanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            items : [{
                xtype:'datefield',
                name : 'YYYY',
                fieldLabel : '年份',
                value : new Date(),
                format : 'Y'
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
            action : 'YQCJ_QUERY_PMC_PP_YY_ACTION',
            outputTable : 'PMC_PP_YY'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工段/月',
                dataIndex : 'PRODUCTIONLINE',
                width : 150
            },{
                header : '工段名字',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 130
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
                header : '本年计划产量',
                dataIndex : 'YYPLANP',
                width : 80
            },{
                header : '本年实际产量',
                dataIndex : 'YYREALP',
                width : 80
            },{
                header : '本年完成率',
                dataIndex : 'YYRATE',
                width : 80
            }, {
                header : '第1月',
                dataIndex : 'DATA1',
                width : 50
            }, {
                header : '第2月',
                dataIndex : 'DATA2',
                width : 50
            }, {
                header : '第3月',
                dataIndex : 'DATA3',
                width : 50
            },{
                header : '第4月',
                dataIndex : 'DATA4',
                width : 50
            },{
                header : '第5月',
                dataIndex : 'DATA5',
                width : 50
            },{
                header : '第6月',
                dataIndex : 'DATA6',
                width : 50
            },{
                header : '第7月',
                dataIndex : 'DATA7',
                width : 50
            },{
                header : '第8月',
                dataIndex : 'DATA8',
                width : 50
            },{
                header : '第9月',
                dataIndex : 'DATA9',
                width : 50
            },{
                header : '第10月',
                dataIndex : 'DATA10',
                width : 50
            },{
                header : '第11月',
                dataIndex : 'DATA11',
                width : 50
            },{
                header : '第12月',
                dataIndex : 'DATA12',
                width : 50
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['YYYY','PRODUCTIONLINE','PRODUCTIONLINENAME','BANCI','DATA1', 'DATA2', 'DATA3','DATA4','DATA5','DATA6','DATA7','DATA8'
            ,'DATA9','DATA10','DATA11','DATA12','YYPLANP','YYREALP','YYRATE'],
            ctTopbarComponts : [{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 3,
                handler : function() {
                    exportFn3();
                } 
            }]
        },
        ctListeners : {
           
        }
    });
    
    //导出excel
    var exportFn3 = function(){
        
        var url = 'json?action=YQCJ_EXPORT_PMC_PP_YY_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = yearYieldPanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&YYYY=' + yearYieldPanel.ht_outputStore.baseParams['YYYY'];
        url += '&BANCI=' + yearYieldPanel.ht_outputStore.baseParams['BANCI'];
        
        url += '&' + params['HEADER'].join('&');
        url += '&' + params['COLUMN'].join('&');
        url += '&' + params['WIDTH'].join('&');
        
        exportPanel3.setSrc(url);
    }

    //导出面板
    var exportPanel3 = new Ext.ux.ManagedIFramePanel({
        region : 'east',
        width :0,
        border : true,
        bodyBorder : false,
        autoScroll : true,
        autoLoad : false,
        defaultSrc : null
    });
    
    //年柱状图
    var yearColumnPanel = new Ext.Panel({
        title : '产量（柱状图）',
        region : 'center',
        width : "50%",
        border : true,
        layout : 'fit',
        items: {   
            store: yearYieldPanel.ht_outputStore,   
            xtype: 'columnchart',   
            url : 'lib/ext-3.3.0/resources/charts.swf',
            xField: 'PRODUCTIONLINE',
            extraStyle: {
                yAxis: {
                    majorGridLines: {
                        color: 0xdddddd
                    }
                }
            },
            series: [{     
                type: 'column',   
                displayName: '计划产量',  
                margins : '20',
                yField: 'YYPLANP',
                style: {   
                    mode: 'stretch',
                    size : 30,
                    padding : '0 60 0 0',
                    color : 0x00BB00
                }   
            },{     
                type: 'column',   
                displayName: '实际产量',  
                margins : '20',
                yField: 'YYREALP',
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

    //年折线图
    var yearLinePanel = new Ext.Panel({
        title : '产量（折线图）',
        region : 'west',
        width : "50%",
        border : true,
        items: {  
            store: yearYieldPanel.ht_outputStore,   
            xtype: 'linechart',   
            url : 'lib/ext-3.3.0/resources/charts.swf',
            xField: 'PRODUCTIONLINE',
            extraStyle: {
                yAxis: {
                    majorGridLines: {
                        color: 0xdddddd
                    }
                }
            },
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
            series: [{     
                type: 'line',   
                displayName: '计划产量',  
                margins : '5',
                yField: 'YYPLANP',
                style: {   
                    mode: 'stretch',
                    size : 5,
                    padding : '0 60 0 0',
                    color : 0x00BB00
                }   
            },{     
                type: 'line',   
                displayName: '实际产量',  
                margins : '5',
                yField: 'YYREALP',
                style: {   
                    mode: 'stretch',
                    size : 5,
                    padding : '0 60 0 0',
                    color : 0xBA55D3
                }   
            }],
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
    
    var backPanel =new Ext.TabPanel({
        activeTab: 0,
        border : false,
        items : [{
            title : '日报表',
            border : false,
            layout : 'border',
            items : [{
                region : 'center',
                layout : 'border',
                border : false,
//                items :[dayYieldPanel, planDatePanel,exportPanel]
                items :[dayYieldPanel,exportPanel]
                },{
	                height : 250,
	                region : 'south',
	                layout : 'border',
	                border : false,
	                items : [daycolumnPanel, daylinePanel]}]
	        },{
            title : '周报表',
            border : false,
            layout : 'border',
            items : [{
	            region : 'center',
	            layout : 'border',
	            border : false,
	            items :[weekYieldPanel,exportPanelWeek]
            },{
                height : 250,
                region : 'south',
                layout : 'border',
                border : false,
                items : [weekColumnPanel, weekLinePanel]}]
	        },{
            title : '月报表',
            border : false,
            layout : 'border',
            items : [{
                region : 'center',
                layout : 'border',
                border : false,
                items :[monthYieldPanel,exportPanel2]
                },{
                    height : 250,
                    region : 'south',
                    layout : 'border',
                    border : false,
                    items : [monthColumnPanel, monthLinePanel]}]
	        },{
	            title : '年报表',
	            border : false,
	            layout : 'border',
	            items : [{
                region : 'center',
                layout : 'border',
                border : false,
                items :[yearYieldPanel,exportPanel3]
                },{
                    height : 250,
                    region : 'south',
                    layout : 'border',
                    border : false,
                    items : [yearColumnPanel, yearLinePanel]}]
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
