/**
 * 开动率报表
 */
(function() {    
    var monthEquStopLinePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            action : 'QUERY_PLAN_PRODUCT_INFO',
            outputTable : 'PmcPlanEdit'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
               header : '责任部门',
                dataIndex : 'LineName',
                width : 70,
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['LineName'],
        }
    });

    var backPanel =new Ext.Panel({
        border : false,
        layout : 'border',
        items : [monthEquStopLinePanel],
    });

    return backPanel;
})();
