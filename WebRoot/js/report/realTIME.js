/**
 * 在停线时间内的故障记录表
 */
(function() {
    var queryPanelIsLayout;

    var queryType=[['','全部'],['1','冲压车间'],['2','车身车间']];
    //故障事故记录
    var faultEventPanel = new ht.base.PlainPanel({
        region : 'center',
        height : 200,
        queryFormConfig : {
            enableQueryButton : true,
            items : [{
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
            }
            ],
            action : 'QUERY_REAL_TIME_REPORT_ACTION',
            outputTable : 'REALTIME',
            autoQuery :  true
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '生产日期',
                dataIndex : 'WorkDate',
                width : 120,
                renderer : function(value){
                    return ht.pub.date.dateTimeRenderer(value);
                }
            },{
                header : '线体英文名',
                dataIndex : 'LineCode',
                width : 80
            }, {
                header : '线体中文名',
                dataIndex : 'LineName',
                width : 80
            },  {
                header : '班次',
                dataIndex : 'Shift',
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
                dataIndex : 'PlanProduct',
                width : 70
            },{
                header : '实际产量',
                dataIndex : 'ActProduct',
                width : 80
            }, {
                header : '计划生产时间',
                dataIndex : 'PlanWorkTime',
                width : 100
            },{
                header : '生产历时',
                dataIndex : 'RuningTime',
                width : 80
            },{
                header : '停线时间',
                dataIndex : 'StopLineTime',
                width : 90
            },{
                header : '停线次数',
                dataIndex : 'StopLineCount',
                width : 90
            },{
                header : '班次开始时间',
                dataIndex : 'WorkStartTime',
                width : 120
            },{
                header : '班次结束时间',
                dataIndex : 'WorkEndTime',
                width : 120
            },{
                header : '休息总时间',
                dataIndex : 'RestTime',
                width : 80
            },{
                header : '休息总次数',
                dataIndex : 'RestCount',
                width : 80
            }],
            isPageAction : true,
            isMultipleSelect : false,
            storeFields : ['PlanProduct','ActProduct','RuningTime','StopLineTime','StopLineCount','Shift','WorkDate','LineCode','LineName',
            'WorkStartTime','WorkEndTime','RestCount','RestTime','PlanWorkTime'],
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

        var url = 'json?action=EXPORT_REAL_TIME_REPORT_ACTION';

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
        url += '&PRODUCTIONLINENAME=' + faultEventPanel.ht_outputStore.baseParams['PRODUCTIONLINENAME'];
        url += encodeURI(encodeURI('&PRODUCTIONLINENAME=' + faultEventPanel.ht_outputStore.baseParams['PRODUCTIONLINENAME']));

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
