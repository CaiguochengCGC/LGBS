/**
 * 固定报表 -> 最后工位维护
 */
(function() {
    
    //工段
    var sectionStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PMC_PP_STATION.rs',
        baseParams : {
            action : 'YQCJ_QUERY_PMC_PP_STATION_ACTION',
            CODE_TYPE : 'PRODUCTIONLINENAME'
        },
        fields : ['VALUE','TEXT']
    });
    sectionStore.load({
        callback : function() {
            ht.pub.store.addBlankText(sectionStore);
        }
    });
    
    //生产时间报表
    var backPanel = new ht.base.RowEditorPanel({
        region : 'center',
        queryFormConfig : {
            enableQueryButton : true, 
            items : [{
                xtype : 'combo',
                fieldLabel : '工段',
                store : sectionStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'PRODUCTIONNAME',
                name : 'PRODUCTIONNAME',
                editable : false
            }],
            action : 'YQCJ_QUERY_PMC_STATION_ACTION',
            outputTable : 'PMC_PP_STATION'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(), {
                header : '序号',
                dataIndex : 'PSEQ',
                width : 130,
                hidden : true,
                editor : {
                	xtype : 'textfield',
                	name : 'PSEQ',
                	updateable : false
                }
            },{
                header : '工段',
                dataIndex : 'PRODUCTIONLINE',
                width : 80,
                editor : {
                	xtype : 'textfield',
                	name : 'PRODUCTIONLINE',
                	updateable : false
                }
            }, {
                header : '工段名称',
                dataIndex : 'PRODUCTIONLINENAME',
                width : 130,
                editor : {
                	xtype : 'textfield',
                	name : 'PRODUCTIONLINENAME',
                	updateable : false
                }
            }, {
                header : '最后工位',
                dataIndex : 'REMARK',
                width : 130,
                editor : {
                	xtype : 'textfield',
                	name : 'REMARK'
                }
            }],
            
            isPageAction : false,
            isMultipleSelect : false,
            storeFields : ['PRODUCTIONLINE','PRODUCTIONLINENAME','REMARK','PSEQ'],
            updateBtn : {
                action : 'YQCJ_UPDATE_PMC_STATION_ACTION',
                position : 3
            }
        }
    });
    
    return backPanel;
})();
