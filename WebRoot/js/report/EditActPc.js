/**
 * PMC系统车身车间总报表
 */
(function() {
    var queryType=[['','全部'],['1','冲压车间'],['2','车身车间']];
    var PmcSystemReportPanel = new ht.base.RowEditorPanel({
        region : 'center',
        height : 350,
        queryFormConfig : {
            enableQueryButton : true,   //是否显示查询按钮
            items : [{
                xtype : 'datefield',
                fieldLabel : '日期',
                width : 300,
                name : 'WORKDATE',
                format : ht.config.format.DATE,
                value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000)
            },{
                xtype : 'combo',
                fieldLabel : '车间查询',
                store : queryType,
                columnWidth : 1/4,
                emptyText : '全部车间',
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'queryType',
                name : 'queryType',
                editable : false
            }],
            action : 'QUERY_PMC_CY_ACT_PC',
            outputTable : 'PMC_CY_PC'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '工段',
                dataIndex : 'LineCode',
                width : 120
            },{
                header : '工段名字',
                dataIndex : 'LineName',
                width : 160
            }, {
                header : '班次',
                dataIndex : 'Shift',
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
                header : '实际产量',
                dataIndex : 'ActProduct',
                width : 130,
                editor: {
                    xtype: 'textfield',
                    name: 'EP21',
                    regex: /^[0-9]*$/
                }
            }],
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['LineCode','LineName','Shift','ActProduct','ID'],
            updateKeyFields: ['ID'],
            //修改按钮
            updateBtn: {
                action: 'UPDATE_PC_ACTION',
                outputTable: '', //数据返回的output
                tagValue: '',
                position: 2
            }
        }
    });



    var backPanel = new Ext.Panel({
        border : false,
        layout : 'border',
        items : [PmcSystemReportPanel],
        listeners : {
            beforedestroy : function(){
                backPanel.removeAll(true);
            }
        }
    });

    return backPanel;
})();
