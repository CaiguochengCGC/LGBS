/**
 * 固定报表 -> 计划日期数量导入
 */
(function() {
    var planNoTemp;
    var setPanelIsLayout;
    
    //班次
    var shiftStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'SHIFT_TYPE'
        },
        fields : ['VALUE','TEXT']
    });
    var sheftQueryStore = new Ext.data.Store({ 
        recordType: shiftStore.recordType 
    });
    shiftStore.load({
        callback : function() {
            ht.pub.store.cloneStore(shiftStore, sheftQueryStore);
            ht.pub.store.addBlankText(sheftQueryStore);
        }
    });
    
    //生产时间报表
    var workTimePanel = new ht.base.PlainPanel({
        region : 'center',
        queryFormConfig : {
            enableQueryButton : true, 
            items : [{
                xtype: 'compositefield',
                fieldLabel: '工作时间',
                name : 'PLAN_DATE',
                items: [{
                    xtype : 'datefield',
                    format : ht.config.format.DATE,
                    fieldLabel : ' ',
                    name : 'START_PLAN_DATE',
                    value : new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000),
                    vtype: 'defineValid',
                    vtypeText : '',
                    defineValid : function(val, fromField){
                        if(setPanelIsLayout && workTimePanel.findQueryCompment('END_PLAN_DATE')){
                            workTimePanel.findQueryCompment('END_PLAN_DATE').validate();
                        }
                        return true;
                    }
                }, {
                    xtype: 'displayfield',
                    width : 20,
                    style : 'text-align: center;',
                    value : '至'
                },{
                    xtype : 'datefield',
                    format : ht.config.format.DATE,
                    fieldLabel : ' ',
                    name : 'END_PLAN_DATE',
                    value : new Date(),
                    vtype: 'comparentDate',
                    vtypeText: ht.msg.valid.compareDate,
                    comparentTo: 'START_PLAN_DATE',
                    getParentCompont : function() {
                        return workTimePanel.ht_outputQueryForm.find('name', 'PLAN_DATE')[0].innerCt;
                    }
                }]
            },{
                xtype : 'combo',
                fieldLabel : '班次',
                store : sheftQueryStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'SHIFT',
                name : 'SHIFT',
                editable : false
            }],
            action : 'ZZCJ_QUERY_PMC_DATE_IMPORT_IMPORT_ACTION',
            outputTable : 'PMC_DATE_IMPORT'
        },
        gridConfig : {
            columns : [new Ext.grid.RowNumberer(),{
                header : '工作日期',
                dataIndex : 'WORKDATE',
                width : 80,
                renderer: function(value){
                    return ht.pub.date.dateRenderer(value);
                }
            }, {
                header : '工作开始时间',
                dataIndex : 'STARTTIME',
                width : 130,
                renderer: function(value){
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '工作结束时间',
                dataIndex : 'ENDTIME',
                width : 130,
                renderer: function(value){
                    return ht.pub.date.dateTimeRenderer(value);
                }
            }, {
                header : '休息时间(分)',
                dataIndex : 'RESTTIME',
                width : 90
            }, {
                header : '班次',
                dataIndex : 'SHIFT',
                width : 40,
                renderer: function(value){
                    return ht.pub.getValueExactByKeyDefault(shiftStore, value);
                }
            }, {
                header : 'CHASSIS',
                dataIndex : 'IP21',
                width : 100
            }, {
                header : 'DOOR',
                dataIndex : 'IP22',
                width : 50
            }, {
                header : 'FINAL',
                dataIndex : 'IP23',
                width : 50
            }, {
                header : 'TRIM',
                dataIndex : 'ZP11',
                width : 50
            }, {
                header : 'ENGINE',
                dataIndex : 'BP31',
                width : 100
            }, {
                header : 'SUBFRAME',
                dataIndex : 'IP24',
                width : 100
            }, {
                header : 'PBS',
                dataIndex : 'BP32',
                width : 50
            }, {
                header : 'SA',
                dataIndex : 'AS21',
                width : 50
            }, {
                header : '备用',
                dataIndex : 'OTHER',
                width : 50
            }, {
                header : '总产量',
                dataIndex : 'productTotal',
                width : 60/*,
                renderer: function(value,s,r){
                	
                    return r.get('IP21')+r.get('IP22')+r.get('IP23')+r.get('ZP11')+r.get('BP31')+r.get('IP24')+r.get('BP32')+r.get('AS21')+r.get('OTHER');
                }*/
            }],
            
            isPageAction : false,
            isMultipleSelect : true,
            storeFields : ['ID','FACTORY','WORKSHOP','SHIFT','WORKDATE', 'STARTTIME'
                            , 'ENDTIME','IP21','IP22', 'IP23','ZP11','BP31','IP24','BP32'
                            ,'AS21','OTHER','productTotal',
                            'PLANTOTAL','REMARK','RESTTIME'],
            deletedKeyFields : ['ID'],
            addBtn : {
                action : 'ZZCJ_ADD_PMC_DATE_IMPORT_ACTION',
                outputTable : 'PMC_DATE_IMPORT',
                position : 2
            },
            updateBtn : {
                action : 'ZZCJ_UPDATE_PMC_DATE_IMPORT_ACTION',
                position : 3
            },
            deleteBtn : {
                action : 'ZZCJ_DELETE_PMC_DATE_IMPORT_ACTION',
                position : 4
            },
            ctTopbarComponts : [{
                xtype : 'fileuploadfield', //文件上传按钮
                style : 'display : none;', //隐藏元素
                name : 'PMC_DATE_IMPORT_FILE',
                 _isAutoSumbit : true,
                buttonOnly : true,
                enableByRowSelected : false, //是否当选中表格行时才可使用
                buttonOffset : 0,
                tagValue : '',
                position : 1,
                buttonText : '导入',
                buttonCfg : {
                   iconCls : 'excel'
                },
                listeners : {
                    fileselected : function(field) {
                        if (!/\.(xls)$/i.test(field.getValue())) {
                            ht.pub.error('必须是扩展名为 xlsx的Excel文件');
                            field.reset();
                            return;
                        }
                        
                        importFileFn(field);
                    }
                }
                
            },{ 
                iconCls : 'excel',
                text : '导出',
                enableByRowSelected : false,
                enableByHasData : true,
                tagValue : '',
                position : 4,
                handler : function() {
                    exportFn();
                } 
            }]
        },
       
        editFormConfig : {
            items : [{
                xtype : 'datefield',
                format : ht.config.format.DATE,
                fieldLabel : '工作日期',
                allowBlank : false,
                name : 'WORKDATE'
            },{
                xtype : 'datetimefield',
                format : ht.config.format.DATETIME,
                fieldLabel : '工作开始时间',
                allowBlank : false,
                name : 'STARTTIME'
                
            },{
                xtype : 'datetimefield',
                format : ht.config.format.DATETIME,
                fieldLabel : '工作结束时间',
                allowBlank : false,
                name : 'ENDTIME'
            },{
                xtype : 'numberfield',
                fieldLabel : '休息时间',
                allowBlank : false,
                name : 'RESTTIME',
                value:0,
                minValue : 0,
                allowDecimals : false
            },{
                xtype : 'combo',
                fieldLabel : '班别',
                store : shiftStore,
                emptyText : ht.msg.combo.emptyText,
                mode : 'local',
                triggerAction : 'all',
                valueField : 'VALUE',
                displayField : 'TEXT',
                hiddenName : 'SHIFT',
                name : 'SHIFT',
                allowBlank : false,
                editable : false
            },{
                xtype : 'numberfield',
                fieldLabel : 'CHASSIS',
                allowBlank : false,
                name : 'IP21',
                minValue : 0,
                allowDecimals : false,
                enableKeyEvents : true,
                listeners : {
                    keyup : function(textField, e){
//                    	if( 0 != textField.getValue()){
                        var newValue = textField.getValue();
                        if (0 == newValue){
                        	newValue = 0;
                        }
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue();
                                        /*+ workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue();*/
                        workTimePanel.getEditPropertyPanel().find('name','productTotal')[0].setValue(newValue);
//                    }
                    }
                }
            },{
                xtype : 'numberfield',
                fieldLabel : 'DOOR',
                allowBlank : false,
                name : 'IP22',
                minValue : 0,
                allowDecimals : false,
                enableKeyEvents : true,
                listeners : {
                    keyup : function(textField, e){
//                    	if( 0 != textField.getValue()){
                        var newValue = textField.getValue();
                        if (0 == newValue){
                        	newValue = 0;
                        }
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue();
                                        /*+ workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue();*/
                        workTimePanel.getEditPropertyPanel().find('name','productTotal')[0].setValue(newValue);
//                    }
                    }
                }
            },{
                xtype : 'numberfield',
                fieldLabel : 'FINAL',
                allowBlank : false,
                name : 'IP23',
                minValue : 0,
                allowDecimals : false,
                enableKeyEvents : true,
                listeners : {
                    keyup : function(textField, e){
//                    	if( 0 != textField.getValue()){
                        var newValue = textField.getValue();
                        if (0 == newValue){
                        	newValue = 0;
                        }
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue();
                                        /*+ workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue();*/
                        workTimePanel.getEditPropertyPanel().find('name','productTotal')[0].setValue(newValue);
//                    }
                    }
                }
            },{
                xtype : 'numberfield',
                fieldLabel : 'TRIM',
                allowBlank : false,
                name : 'ZP11',
                minValue : 0,
                allowDecimals : false,
                enableKeyEvents : true,
                listeners : {
                    keyup : function(textField, e){
//                    	if( 0 != textField.getValue()){
                        var newValue = textField.getValue();
                        if (0 == newValue){
                        	newValue = 0;
                        }
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue();
                                        /*+ workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue();*/
                        workTimePanel.getEditPropertyPanel().find('name','productTotal')[0].setValue(newValue);
//                    }
                    }
                }
            },{
                xtype : 'numberfield',
                fieldLabel : 'ENGINE',
                allowBlank : false,
                name : 'BP31',
                minValue : 0,
                allowDecimals : false,
                enableKeyEvents : true,
                listeners : {
                    keyup : function(textField, e){
//                    	if( 0 != textField.getValue()){
                        var newValue = textField.getValue();
                        if (0 == newValue){
                        	newValue = 0;
                        }
                        
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue();
                        	
                                        /*+ workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue();*/
                        workTimePanel.getEditPropertyPanel().find('name','productTotal')[0].setValue(newValue);
//                    }
                    }
                }
            },{
                xtype : 'numberfield',
                fieldLabel : 'SUBFRAME',
                allowBlank : false,
                name : 'IP24',
                minValue : 0,
                allowDecimals : false,
                enableKeyEvents : true,
                listeners : {
                    keyup : function(textField, e){
//                    	if( 0 != textField.getValue()){
                        var newValue = textField.getValue();
                        if (0 == newValue){
                        	newValue = 0;
                        }
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue();
                                        /*+ workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', '')[0].getValue();*/
                        workTimePanel.getEditPropertyPanel().find('name','productTotal')[0].setValue(newValue);
//                    }
                    }
                }
            },{
                xtype : 'numberfield',
                fieldLabel : 'PBS',
                allowBlank : false,
                name : 'BP32',
                minValue : 0,
                allowDecimals : false,
                enableKeyEvents : true,
                listeners : {
                    keyup : function(textField, e){
//                    	if( 0 != textField.getValue()){
                        var newValue = textField.getValue();
                        if (0 == newValue){
                        	newValue = 0;
                        }
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue();
                        	
                                      /*  + workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue();*/
                        workTimePanel.getEditPropertyPanel().find('name','productTotal')[0].setValue(newValue);
//                    }
                    }
                }
            },{
                xtype : 'numberfield',
                fieldLabel : 'SA',
                allowBlank : false,
                name : 'AS21',
                minValue : 0,
                value:0,
                allowDecimals : false,
                enableKeyEvents : true,
                listeners : {
                    keyup : function(textField, e){
//                    	if( 0 != textField.getValue()){
                        var newValue = textField.getValue();
                        if (0 == newValue){
                        	newValue = 0;
                        }
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue();
                        if (workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue() != 0)
                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue();
//                        workTimePanel.getEditPropertyPanel().find('name','productTotal')[0].setValue(newValue);
                        /*
                                        + workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue()
                                        + workTimePanel.getEditPropertyPanel().find('name', 'OTHER')[0].getValue();*/
                        workTimePanel.getEditPropertyPanel().find('name','productTotal')[0].setValue(newValue);
//                    }
                    }
                }
            },{
                xtype : 'numberfield',
                fieldLabel : '备用',
                allowBlank : false,
                name : 'OTHER',
                minValue : 0,
                value:0,
                allowDecimals : false,
                enableKeyEvents : true,
                listeners : {
                    keyup : function(textField, e){
//                    	if( 0 != textField.getValue()){
	                        var newValue = textField.getValue();
	                        if (0 == newValue){
	                        	newValue = 0;
	                        }
	                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue() != 0)
	                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP21')[0].getValue();
	                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue() != 0)
	                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP22')[0].getValue();
	                        if (workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue() != 0)
	                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'ZP11')[0].getValue();
	                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue() != 0)
	                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP31')[0].getValue();
	                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue() != 0)
	                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP24')[0].getValue();
	                        if (workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue() != 0)
	                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'BP32')[0].getValue();
	                        if (workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue() != 0)
	                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'IP23')[0].getValue();
	                        if (workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue() != 0)
	                        	newValue += workTimePanel.getEditPropertyPanel().find('name', 'AS21')[0].getValue();
	                        workTimePanel.getEditPropertyPanel().find('name','productTotal')[0].setValue(newValue);
//	                    }
                    }
                }
            },{
                xtype : 'numberfield',
                fieldLabel : '总产量',
                allowBlank : false,
                name : 'productTotal',
                minValue : 0,
                allowDecimals : false,
                insertable : false,
                updateable : false
            }],
            addPanel : {
                windowTitle : ht.msg.base.addText + '油漆车间计划数量',
                panelDiffHeight : 25,
                autoScroll : false
            },
            updatePanel : {
                windowTitle : ht.msg.base.updateText + '油漆车间计划数量',
                panelDiffHeight : 25,
                autoScroll : false
            }
        },
        ctListeners : {
            dbclickGridRow : function(agrid, rowIndex, e){
                planNoTemp = workTimePanel.ht_outputStore.getAt(rowIndex).get('ID');
            },
            beforeUpdate : function(values) {
                
                //传值
                values['ID'] = planNoTemp;
                
                return true;
            }
        }
    });
    
    //导入计划日期数量
    var importFileFn = function(field){
        var params = {};
        params['action'] = 'ZZCJ_SAVE_PMC_DATE_IMPORT_ACTION';
        params['FACTORY'] = '上汽';
        params['WORKSHOP'] = '车间';
        
        ht.pub.wait('正在导入中...');
        Ext.Ajax.request({
            form: Ext.getDom(field.id).parentNode,
            url: 'json',
            method: 'post',
            params: params,
            isUpload: true,
            success : function(response, options) {
                var result = Ext.decode(response.responseText)
                if(result.success){    
                    if (result.root['PMC_DATE_IMPORT_INFO'].rs[0] == ht.pub.global.YESNO.YES) { 
                        ht.pub.info('导入成功。');
                        
                        workTimePanel.ht_outputQueryPanel.queryFn();
                    } else {
                        ht.pub.warning('导入失败,'+result.root['PMC_DATE_IMPORT_INFO'].rs+'。');
                    }
                }else {
                    ht.pub.error(result.errors.errmsg);
                }
                
                field.reset();
            }
        });
    }
    
     //导出excel
    var exportFn = function(){
        
        var url = 'json?action=ZZCJ_EXPORT_PMC_DATE_IMPORT_ACTION';
                    
        var params = {};
        params['HEADER'] = new Array();
        params['COLUMN'] = new Array();
        params['WIDTH'] = new Array();
        
        var columns = workTimePanel.gridConfig.columns;
        for (var i = 0; i < columns.length; i++) {
            if (Ext.isEmpty(columns[i].dataIndex, false) || columns[i].hidden) {
                continue;
            }
            
            params['HEADER'].push('HEADER=' + encodeURIComponent(columns[i].header));
            params['COLUMN'].push('COLUMN=' + columns[i].dataIndex);
            params['WIDTH'].push('WIDTH=' + columns[i].width);
        }
        
        url += '&SHIFT=' + workTimePanel.ht_outputStore.baseParams['SHIFT'];
        url += '&START_PLAN_DATE=' + workTimePanel.ht_outputStore.baseParams['START_PLAN_DATE'];
        url += '&END_PLAN_DATE=' + workTimePanel.ht_outputStore.baseParams['END_PLAN_DATE'];
        
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
        items : [workTimePanel,exportPanel],
        listeners : {
            beforedestroy : function(){
                shiftStore.destroy();
                sheftQueryStore.destroy();
                backPanel.removeAll(true);
            }   
        } 
    });

    return backPanel;
})();
