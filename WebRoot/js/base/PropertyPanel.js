Ext.namespace('ht.base');

/**
 * 
 * @class ht.base.PropertyPanel
 * @extends Ext.Panel
 * 
 */
ht.base.PropertyPanel = Ext.extend(Ext.Panel, {
    layout : 'fit',
    border : false,
    
    /**
     * @cfg{Ext.form.FormPanel}  ht_outputFormPanel 
     * @type Ext.form.FormPanel
     * 输出form
     */
    ht_outputFormPanel : null, 
    
    /**
     * @cfg(Number) ht_outputPanelHeight
     * @type Number
     * 输出面板的高度
     */
    ht_outputPanelHeight : 0, 
    
    /**
     * 面板按钮
     * @type 
     */
    ht_outputButtons : [],
    
    /**
     * @cfg(json) initConfig
     * @type json
     * 初始化配置
     */
    initConfig : {
        
        /**
         * @cfg(Array) tagValues
         * @type Array
         * 用户所有权限数组
         */
        tagValues : [] 
    },
    
    /**
     * @cfg {json} formConfig
     * 查询面板配置
     */
    formConfig : {
        
        /**
         * @cfg(json) query
         * 查询功能配置
         */
        query : {
            
            /**
             * @cfg(Boolean) enableQueryButton
             * @type Boolean
             * 是否使用查询按钮，默认为true
             */
            enableQueryButton : true, 
            
            /**
             * @cfg(Ext.data.JsonStore) store
             * @type Ext.data.JsonStore
             * 用于查询store
             */
            store : null, 
            
            /**
             * @cfg(String) tagValue
             * @type String
             * 查询功能权限值
             */
            tagValue : '', 
            
            /**
             * @cfg(Number) queryPosition
             * @type Number
             * 查询按钮的位置，默认为第一位
             */
            queryPosition : 1, 
            
            /**
             * @cfg(Number) resetPosition
             * @type Number
             * 重置按钮的位置，默认为第二位
             */
            resetPosition : 0 
        },
        
        /**
         * @cfg(json) ctTopbarComponts
         * @type json
         * 顶部额外工具栏控件
         */
        ctTopbarComponts : [{ 
            
            /**
             * @cfg(String) tagValue
             * @type String
             * 控件权限值
             */
            tagValue : '',  
            
            /**
             * @cfg(Boolean) ingoreSeparator
             * @type Boolean
             * 是否忽略左侧分割符
             */
            ingoreSeparator : false,
            
            /**
             * @cfg(Number) position
             * @type Number
             * 控件的位置
             */
            position : 0, 
            
            /**
             * @cfg(Boolean) enableByRowSelected
             * @type Boolean
             * 是否是当选中表格行时按钮才可使用
             */
            enableByRowSelected : true 
        }],
        
        /**
         * @cfg(String) useTo
         * @type String
         * 面板用途，query :查询,property：属性,win-edit：弹出窗口，win-query：弹出窗口查询
         */
        useTo : 'query', 
        
        /**
         * @cfg(Number) panelHeight
         * @type Number
         * 查询面板的高度，用于调整面板的高度，当值大于自动高度时取该值
         */
        panelHeight : 0,
        
        /**
         * 差异高度
         * @type Number
         */
        panelDiffHeight : 0,
        
        /**
         * 非查询时，高度超出的时候，是否滚动
         * @type Boolean
         */
        autoScroll : true,
        
        /**
         * 是否文件上传
         * @type Boolean
         */
        fileUpload : false,
        
        /**
         * 标签长度
         * @type Number
         */
        labelWidth : 0,
        
        /**
         * @cfg(Array) items
         * @type Array
         */
        items : [{
            
            /**
             * @cfg(Number) columnWidth
             * @type Number
             * 占一行的百分比，默认为useTo相应的默认值,
             */
            columnWidth : 0.25, 
            
            /**
             * @cfg(Boolean) insertable
             * @type Boolean
             * 增加时是否可以编辑，disableFields方法使用
             */
            insertable : true, 
            
            /**
             * @cfg(Boolean) updateable
             * @type Boolean
             * 修改时是否可以编辑，disableFields方法使用
             */
            updateable : true,
            
            /**
             * @cfg(Boolean) autoTrim
             * @type Boolean
             * 提交前是否去左右空格
             */
            autoTrim : true,
            
            /**
             * 备注宽度
             * @type Number
             */
            remarkWidth : 0,
            
            /**
             * 备注信息
             * @type String
             */
            remarkText : '',
            
            /**
             * 备注css
             * @type String
             */
            remarkCls : '',
            
            //查询时转换成大写
            _upperCase : false
            
        },'/', '-', {
            xtype : 'tabpanel',
            items : []
        }, {
            xtype : 'fieldset',
            items : []
        }], //查询面板内容,'/'表示换行, '-'表示横行分割
        
        /**
         * @cfg(json) submit
         * @type json
         * 提交功能使用
         */
        submit : {
            
            /**
             * @cfg(Boolean) enableSubmitButton
             * @type Boolean
             * 是否使用确定按钮
             */
            enableSubmitButton : true, 
            
            /**
             * @cfg(String) action
             * @type String
             * 确定按钮的action
             */
            action : '',
            
            /**
             * @cfg(String) successMsg
             * @type String
             * 提交成功提示
             */
            successMsg : '', 
            
            /**
             * @cfg(String) tagValue
             * @type String
             * 确定按钮功能权限值
             */
            tagValue : '',
            
            /**
             * @cfg(Number) submitPosition
             * @type Number
             * 确定按钮的位置，默认为第一位
             */
            submitPosition : 0, 
            
            /**
             * @cfg(Number) cancelPosition
             * @type Number
             * 取消按钮的位置，默认为第二位
             */
            cancelPosition : 1 
        },
        
        isWindowButtons : false,
        
        /**
         * @cfg(Array) ctButtons
         * @type Array
         * 提交面板额外按钮
         */
        ctButtons : [{ 
            
            /**
             * @cfg(String) tagValue
             * @type String
             * 按钮功能权限值
             */
            tagValue : '',  
            
            /**
             * @cfg(Number) position
             * @type Number
             * 按钮位置
             */
            position : 0
        }]
        
    },
    
    /**
     * 监听事件
     * @type 
     */
    ctListeners : {
        
        /**
         * 查询前处理，如果返回false，则不继续处理
         * @param {json} values form输入控件键值对
         * @return {Boolean}
         */
        beforeQuery : function(values){return true},
        
        /**
         * 查询后处理
         */
        afterQuery : function(){}, 
        
        /**
         * 重置
         */
        afterReset : function(){}, 
        
        /**
         * 提交前处理，，如果返回false，则不继续处理
         * @param {json} values form输入控件键值对
         * @param {function} formSubmit 提交方法
         * @return {Boolean}
         */
        beforeSubmit : function(values, formSubmit){return true},
        
        /**
         * 提交成功后处理
         * @param {json} values 提交的action对象，后台返回值
         */
        afterSubmit : function(values){}, 
        
        /**
         * 查询时，行选中控件监听
         */
        enableBarBtn :function(){}  
    },
    
    /**
     * 清除校验结果
     */
    clearInvalid : function () {
        var backpanel = this;
        var tempDataFieldNames = backpanel.tempDataFieldNames;
        
        for (var i = 0; i < tempDataFieldNames.length; i++) {
            var field = backpanel.findCompments('name', tempDataFieldNames[i])[0];
            
            if (field) {
                field.clearInvalid();
            }
        }
    },
    
    /**
     * 值置空
     * @param {Boolean} isKeepHiddenValue 是否保留隐藏的值，默认为true
     */
    removeValues : function(isKeepHiddenValue) {
        var backpanel = this;
        var values = {};
        var tempDataFieldNames = backpanel.tempDataFieldNames;
        
        for (var i = 0; i < tempDataFieldNames.length; i++) {
            if (isKeepHiddenValue !== false) {
                var field = backpanel.findCompments('name', tempDataFieldNames[i])[0];
                if (field && (field.xtype == 'hidden' || field instanceof Ext.form.Hidden || field.hidden)) {
                    continue;
                }
            }
            
            values[tempDataFieldNames[i]] = '';
        }
        backpanel.ht_outputFormPanel.getForm().setValues(values);
        backpanel.clearInvalid();
        
        //调用置空监听
        backpanel.ctListeners = backpanel.ctListeners || {};
        if(backpanel.ctListeners.afterReset) {
            backpanel.ctListeners.afterReset();
        }
        
    },
    
    /**
     * 根据键值对返回查询面板组件
     * @return {Ext.form.Field[]}
     */
    findCompments : function(prop, value){
        var backpanel = this;
        return backpanel.ht_outputFormPanel.find(prop,value);
    },
    
    /**
     * 根据返回输入控件名查询面板组件
     * @return {Ext.form.Field}
     */
    findCompment : function(name){
        var backpanel = this;
        return backpanel.ht_outputFormPanel.getForm().findField(name);
    },
    
    /**
     * 根据键值对返回查询面板顶部工具栏组件
     * @return {Ext.Component[]}
     */
    findTopbarCompments : function(prop, value){
        var backpanel = this;
        return backpanel.getTopToolbar().find(prop,value);
    },
    
    /**
     * 根据键值对返回查询面板底部按钮组件
     * @return {Ext.Component[]}
     */
    findFooterbarCompments : function(prop, value){
        var backpanel = this;
        return backpanel.getFooterToolbar().find(prop,value);
    },
    
    /**
     * 获取查询条件的值
     * @return {{}}
     */
    getFormValues : function() {
        var backpanel = this;
        
        var submitValues = backpanel.ht_outputFormPanel.getForm().getValues();
        
        //替换自定义控件TextFieldButton的值
        ht.pub.getTextFieldButtonValues(backpanel.ht_outputFormPanel, backpanel.tempDataFieldNames, submitValues);
        
        //获取checkbox的值
        ht.pub.getCheckboxValues(backpanel.ht_outputFormPanel, backpanel.tempDataFieldNames, submitValues);
        
        return submitValues;
    },
    
    /**
     * 获取查询条件的值
     * @return {{}}
     */
    getFormFieldValues : function() {
        var backpanel = this;
        return backpanel.ht_outputFormPanel.getForm().getFieldValues();
    },
    
    /**
     * 重置
     */
    reset : function() {
        var backpanel = this;
        backpanel.ht_outputFormPanel.getForm().reset();
        if(backpanel.ctListeners.afterReset) {
            backpanel.ctListeners.afterReset();
        }
    },
    
    /**
     * 将当前值设置为初始值
     */
    initValues : function() {
        var backpanel = this;
        for (var i = 0; i < backpanel.tempDataFieldNames.length; i++) {
            backpanel.findCompment(backpanel.tempDataFieldNames[i]).initValue();
        }
    },
    
   /**
     * 根据是否是选中表格行禁用按钮
     * @param {} backpanel
     * @param {} enable
     */
    enableBarBtn : function(enable) {

        //禁用查询工具栏按钮
        var backpanel = this;
        var compents = new Array();
        
        var queryTmpBars = backpanel.getTopToolbar();
        if (queryTmpBars) {
            var i = 0;
            var comp = queryTmpBars.getComponent(i);
            while (comp) {
                compents[compents.length] = comp;
                i++;
                comp = queryTmpBars.getComponent(i);
            }
        }
        ht.pub.enableCompentByRowSelected(compents, enable);
        
    },
    
    /*
     * 启用、禁用表格顶部按钮
     * @param {Boolean} enable 是否启用
     * @param {String} prop 控件属性
     * @param {String} propValue 控件的值
     */
   enableTopbar : function(enable, prop, propValue) {
        
        //禁用查询工具栏按钮
        var backpanel = this;
        var compents = new Array();
        
        //禁用表格面板顶部工具栏按钮
        var tmpTopbars = backpanel.getTopToolbar();
        if (tmpTopbars) {
            var i = 0;
            var comp = tmpTopbars.getComponent(i);
            while (comp) {
                compents[compents.length] = comp;
                i++;
                comp = tmpTopbars.getComponent(i);
            }
        }
        ht.pub.enableCompents(compents, enable, prop, propValue);
    },
    
    /**
     * 将控件改为不可编辑
     * @param {} isUpdated 是否是禁用updateable=false的控件，如果false，则禁用insertable=false的控件
     */
    disableFields : function(isUpdated) {
        
        var backpanel = this;
        var fieldNames = backpanel.tempDataFieldNames;
        var formPanel = backpanel.ht_outputFormPanel;
        
        ht.pub.disableFields(formPanel, fieldNames, isUpdated);
    },
    
    /**
     * 将全部控件改为不可编辑
     * @param {} allowEdit 是否是可以使用
     */
    disableAllFields : function(allowEdit) {
        
        var backpanel = this;
        var fieldNames = backpanel.tempDataFieldNames;
        var formPanel = backpanel.ht_outputFormPanel;
        var fields;
        var subFields;
        var fields;
        var subField;
        
        for (var i = 0; i < fieldNames.length; i++) {
            fields = formPanel.find('name', fieldNames[i]);
            if (!fields || fields.length == 0) {
                continue;
            }
            
            for (var k = 0; k < fields.length; k++) {
                field = fields[k];
                
                if (field.xtype == 'compositefield') {
                    for (var ii = 0; ii < fieldNames.length; ii++) {
                        subFields = field.innerCt.find('name', fieldNames[ii]);
                        if (!subFields || subFields.length == 0) {
                            continue;
                        }
                        
                        for (var kk = 0; kk < subFields.length; kk++) {
                            subField = subFields[kk];
                            if (!allowEdit) {
                                if (Ext.isEmpty(subField.defaultInsertable)) {
                                    subField.defaultInsertable = subField.insertable === false ? false : true;
                                    subField.insertable = false;
                                }
                            } else {
                               if (!Ext.isEmpty(subField.defaultInsertable)) {
                                   subField.insertable = subField.defaultInsertable;
                                   subField.defaultInsertable = null;
                               }
                            }
                
                        }
                    }
                    
                    continue;
                }
                
                
                if (!allowEdit) {
                    if (Ext.isEmpty(field.defaultInsertable)) {
                        field.defaultInsertable = field.insertable === false ? false : true;
                        field.insertable = false;
                    }
                } else {
                   if (!Ext.isEmpty(field.defaultInsertable)) {
                       field.insertable = field.defaultInsertable;
                       field.defaultInsertable = null;
                   }
                }
    
            }
        }
        
        ht.pub.disableFields(formPanel, fieldNames, false);
    },
    
    tempDataFieldNames : [],
    tempLayouted : false,
    
    /**
     * 查询事件
     */
    queryFn : function() {
         
        //参数初始化
        var backpanel = this;
        var formPanel = backpanel.ht_outputFormPanel;
        var formConfig = backpanel.formConfig;
        var ctListeners = backpanel.ctListeners;
        var queryValues = {};
        
        //获取查询
        if (formPanel) {
            if (!formPanel.getForm().isValid()) {
                return;
            }
            
            //大写
            var fields = backpanel.findCompments('_upperCase', true);
            for (var i = 0; i < fields.length; i++) {
                fields[i].setValue(fields[i].getValue().toUpperCase());
            }
            
            queryValues = formPanel.getForm().getValues();
            
            //替换自定义控件TextFieldButton的值
            ht.pub.getTextFieldButtonValues(formPanel, backpanel.tempDataFieldNames, queryValues);
            
            //获取checkbox的值
            ht.pub.getCheckboxValues(formPanel,  backpanel.tempDataFieldNames, queryValues);
        }
        
        //禁用查询工具栏
        backpanel.enableBarBtn(false);
        
        //禁用其他
        if (ctListeners.enableBarBtn) {
            ctListeners.enableBarBtn(false);
        }
        queryValues['start'] = 0;   
        
        //监听事件
        if(backpanel.ctListeners.beforeQuery && !backpanel.ctListeners.beforeQuery(queryValues)) {
            return;
        }
        
        var store = backpanel.formConfig.query.store;
        for (var field in queryValues) {
            store.baseParams[field] = queryValues[field];
        }
        
        store.load({
            callback : function() {
               if(backpanel.ctListeners.afterQuery) {
                   backpanel.ctListeners.afterQuery();
               }
            }
        });
    },
    
    
    
    /**
     * 确定事件
     */
    submitFn : function() {

        //参数初始化
        var backpanel = this;
        var formPanel = backpanel.ht_outputFormPanel;
        var formConfig = backpanel.formConfig;
        var successMsg = formConfig.submit.successMsg;
        var submitValues = {};
        submitValues['action'] = formConfig.submit.action;
//        submitValues = formPanel.getForm().getValues();
        
        //大写
        var fields = backpanel.findCompments('_upperCase', true);
        for (var i = 0; i < fields.length; i++) {
            fields[i].setValue(fields[i].getValue().toUpperCase());
        }
        
        if (!formPanel.getForm().isValid()) {
            return;
        }
        
        //替换自定义控件TextFieldButton的值
        ht.pub.getTextFieldButtonValues(formPanel, backpanel.tempDataFieldNames, submitValues);
        
        //获取checkbox的值
        ht.pub.getCheckboxValues(formPanel,  backpanel.tempDataFieldNames, submitValues);
        
        var formSubmit = function() {
            ht.pub.wait(ht.msg.base.processText);
            formPanel.getForm().submit({
                params : submitValues ,
                success : function(form, action) {
                    if (successMsg) {
                        ht.pub.info(successMsg);   
                    }
                    
                    if(backpanel.ctListeners.afterSubmit) {
                        backpanel.ctListeners.afterSubmit(action);
                    }
                },
                failure : function(form, action) {
                    ht.pub.handleSubmitErrors(form, action);
                }
            });
        }
        
        //监听事件
        if(backpanel.ctListeners.beforeSubmit && !backpanel.ctListeners.beforeSubmit(submitValues, formSubmit)) {
            return;
        }
        
        //提交
        if (!submitValues['action']) {
            return;
        }
        
        formSubmit();
        
        
    },
    
    
    /**
     * 根据tagValue禁用按钮
     */
    enableBarBtnByTag : function() {
        
        var backpanel = this;
        var tagValues = backpanel.initConfig.tagValues;
        var compents = new Array();
        if (!tagValues) {
            return;
        }
        
        //禁用查询工具栏按钮
        var queryTmpBars = backpanel.getTopToolbar();
        if (queryTmpBars) {
            var i = 0;
            var comp = queryTmpBars.getComponent(i);
            while (comp) {
                compents[compents.length] = comp;
                i++;
                comp = queryTmpBars.getComponent(i);
            }
        }
        
        var formPanel = backpanel.ht_outputFormPanel;
        
        //禁用按钮
        var formButtons = formPanel ? formPanel.buttons : null;
        if (formButtons) {
            var i = 0;
            var comp = formButtons[i];
            while (comp) {
                compents[compents.length] = comp;
                i++;
                comp = formButtons[i];
            }
        }
        
        ht.pub.disableCompentByTag(compents, tagValues);
    },
    
    //private 
    /**
     * 
     * @param {} fieldset
     * @param {Array} fieldNames
     */
    createFieldSet : function(fieldSet, fieldNames) {
        
        //初始化参数
        var backpanel = this;
        var formConfig = backpanel.formConfig;
        
        //11111111111111
//        fieldSet = {};
        
        var USETO_QUERY = 'query';
        var USETO_WINEDIT = 'win-edit';
        var USETO_WINQUERY = 'win-query';
        var USETO_PROPERTY = 'property';
        var useTo = formConfig.useTo == undefined ? USETO_QUERY : formConfig.useTo;
        
        //数据初始化
        var TOOL_HEIGHT = 26;
        var FIELD_HEIGHT = 27;
        var FORM_PADDING = 10;
        var BUTTON_HEIGHT = 80;
        var BODY_STYLE = 'padding:0px 0px';
        var panelHeight = TOOL_HEIGHT + FORM_PADDING + BUTTON_HEIGHT;
        var COL_WIDTH = ht.config.layout.query.COLUMN_WIDTH;
        var ANCHOR = ht.config.layout.query.ANCHOR;
        
        //获取COL_WIDTH和ANCHOR
        switch(useTo) {
            case USETO_WINEDIT :
                COL_WIDTH = ht.config.layout.winedit.COLUMN_WIDTH; //弹出窗口编辑
                ANCHOR = ht.config.layout.winedit.ANCHOR;
                break;
            case USETO_WINQUERY :
                COL_WIDTH = ht.config.layout.winquery.COLUMN_WIDTH; //弹出窗口查询
                ANCHOR = ht.config.layout.winquery.ANCHOR;
                break;
            case USETO_PROPERTY :
                COL_WIDTH = ht.config.layout.property.COLUMN_WIDTH; //属性编辑
                ANCHOR = ht.config.layout.property.ANCHOR;
                break;
            default :
                break;
        }
        
        var LINE_COLS =  parseInt(1 / COL_WIDTH);
        
        //生成Form items
        var itemLen = fieldSet.items == undefined ? 0 : fieldSet.items.length;
        var formItems = new Array();
        var formItemMaxLen = 0;
        var formItem;
        var itemColWidth;
        var itemAnchor;
        var isIngoreAnchor = false;
        var labelWidth = fieldSet.labelWidth ? fieldSet.labelWidth : ht.config.label.WIDTH;
        var isHidden = false;
        
        for (var i = 0; i < itemLen; i++) {
            isHidden = false;
            formItem = fieldSet.items[i];
            isIngoreAnchor = formItem.isIngoreAnchor ? true : false;
            if ('/' == formItem) { //换行
                if (0 == formItemMaxLen % LINE_COLS) {
                    continue;
                }
                formItem = {
                    xtype : 'panel', 
                    height : FIELD_HEIGHT, 
                    border : false,
                    columnWidth : (LINE_COLS - formItemMaxLen % LINE_COLS) * COL_WIDTH
                };
            } else if (formItem.xtype == 'hidden' || formItem instanceof Ext.form.Hidden || formItem.hidden) { //隐藏输入框
                formItemMaxLen -= 1;
                isHidden = true;
            } else if ('-' == formItem) { //横线
                formItem = {
                    xtype : 'panel',
                    cls : 'horizontal-line',
                    border : true,
                    columnWidth : 1
                };
                isIngoreAnchor = true;
            } else if ('fieldset' == formItem.xtype) {
                 formItem = backpanel.createFieldSet(formItem, fieldNames);
                 isIngoreAnchor = true;
            } else if ('tabpanel' == formItem.xtype) {
            
            }
            
            itemColWidth = formItem.columnWidth == undefined ? COL_WIDTH : formItem.columnWidth;
            itemAnchor = (100 - (COL_WIDTH * (100 - parseFloat(ANCHOR))) / itemColWidth) + '%';
            formItemMaxLen += isIngoreAnchor ? parseInt(((LINE_COLS - formItemMaxLen % LINE_COLS) * COL_WIDTH + COL_WIDTH - 0.1) / COL_WIDTH): 0;
            formItemMaxLen += parseInt((itemColWidth + COL_WIDTH - 0.1) / COL_WIDTH);
            formItem.anchor = isIngoreAnchor ? '100%' : itemAnchor;
            
            formItems[formItems.length] = {
                labelAlign : ht.config.label.ALIGN,
                labelWidth : labelWidth,
                labelSeparator:  ht.config.label.SEPARATOR,
                columnWidth : itemColWidth,
                hidden : isHidden,
                items : formItem
            }
            
            //增加、修改
            if (formItem.name) {
                fieldNames[fieldNames.length] = formItem.name;
            }
        }
        
        panelHeight = panelHeight + parseInt((formItemMaxLen + LINE_COLS - 1) / LINE_COLS) * FIELD_HEIGHT;
        
        var rebackFieldSet = {
            xtype : 'fieldset',
            layout : 'form',
            bodyStyle : BODY_STYLE,
            items : [{
                layout : 'column',
                defaults : {
                    layout : 'form',
                    defaults : {
                        anchor : ANCHOR
                    }
                },
                items : formItems
            }]
        };
        
        for (var field in fieldSet) {
            if ('items' == field) {
                continue;
            }
            rebackFieldSet[field] = fieldSet[field];
        }
        
        return rebackFieldSet;
    
    },
    
    initComponent : function() {
        
        //初始化参数
        var backpanel = this;
        var formConfig = backpanel.formConfig || {};
        var itemLen = formConfig.items == undefined ? 0 : formConfig.items.length;
        if (0 == itemLen) {
            ht.base.PropertyPanel.superclass.initComponent.call(backpanel);
            backpanel.enableBarBtnByTag();
            backpanel.enableBarBtn(false);
            return;
        }
        
        backpanel.initConfig.tagValues = backpanel.initConfig.tagValues || ht.pub.global.CURRENT_USER_ACTIONS;
        
        var USETO_QUERY = 'query';
        var USETO_WINEDIT = 'win-edit';
        var USETO_WINQUERY = 'win-query';
        var USETO_PROPERTY = 'property';
        var useTo = formConfig.useTo == undefined ? USETO_QUERY : formConfig.useTo;
        
        //数据初始化
        var TOOL_HEIGHT = 26;
        var FIELD_HEIGHT = 27;
        var FORM_PADDING = 10;
        var BUTTON_HEIGHT = 80;
        var BODY_STYLE = 'padding:0px 0px';
        var panelHeight = TOOL_HEIGHT + FORM_PADDING + BUTTON_HEIGHT;
        var COL_WIDTH = ht.config.layout.query.COLUMN_WIDTH;
        var ANCHOR = ht.config.layout.query.ANCHOR;
        
        //获取COL_WIDTH和ANCHOR
        switch(useTo) {
            case USETO_WINEDIT :
                COL_WIDTH = ht.config.layout.winedit.COLUMN_WIDTH; //弹出窗口编辑
                ANCHOR = ht.config.layout.winedit.ANCHOR;
                break;
            case USETO_WINQUERY :
                COL_WIDTH = ht.config.layout.winquery.COLUMN_WIDTH; //弹出窗口查询
                ANCHOR = ht.config.layout.winquery.ANCHOR;
                break;
            case USETO_PROPERTY :
                COL_WIDTH = ht.config.layout.property.COLUMN_WIDTH; //属性编辑
                ANCHOR = ht.config.layout.property.ANCHOR;
                break;
            default :
                break;
        }
        
        var LINE_COLS =  parseInt(1 / COL_WIDTH);
        
        //生成Form items
        var fieldNames = new Array();
        var formItems = new Array();
        var formItemMaxLen = 0;
        var formItem;
        var itemColWidth;
        var itemAnchor;
        var isIngoreAnchor = false;
        var labelWidth = formConfig.labelWidth ? formConfig.labelWidth : ht.config.label.WIDTH;
        var isHidden = false;
        
        for (var i = 0; i < itemLen; i++) {
            formItem = formConfig.items[i];
            isIngoreAnchor = formItem.isIngoreAnchor ? true : false;
            isHidden = false;
            if ('/' == formItem) { //换行
                
                if (0 == formItemMaxLen % LINE_COLS) {
                    continue;
                }
                formItem = {
                    xtype : 'panel', 
                    height : FIELD_HEIGHT, 
                    border : false,
                    columnWidth : (LINE_COLS - formItemMaxLen % LINE_COLS) * COL_WIDTH
                };
            } else if (formItem.xtype == 'hidden' || formItem instanceof Ext.form.Hidden || formItem.hidden) { //隐藏输入框
                formItemMaxLen -= 1;
                isHidden = true;
            } else if ('-' == formItem) { //横线
                formItem = {
                    xtype : 'panel',
                    cls : 'horizontal-line',
                    border : true,
					columnWidth : 1
                };
                isIngoreAnchor = true;
            } else if ('fieldset' == formItem.xtype) {
                 formItem = backpanel.createFieldSet(formItem, fieldNames);
                 isIngoreAnchor = true;
            } else if ('tabpanel' == formItem.xtype) {
                isIngoreAnchor = true;
            } else if (formItem.xtype == 'compositefield') {
                if (Ext.isEmpty(formItem.name, false)) {
                    formItem.name = 'ht_composite_000';
                    
                }
                formItem.defaultMargins = '0 0 0 0';
            }
            
            itemColWidth = formItem.columnWidth == undefined ? COL_WIDTH : formItem.columnWidth;
            itemAnchor = (100 - (COL_WIDTH * (100 - parseFloat(ANCHOR))) / itemColWidth) + '%';
            formItemMaxLen += isIngoreAnchor ? parseInt(((LINE_COLS - formItemMaxLen % LINE_COLS) * COL_WIDTH + COL_WIDTH - 0.1) / COL_WIDTH): 0;
            formItemMaxLen += parseInt((itemColWidth + COL_WIDTH - 0.1) / COL_WIDTH);
            formItem.anchor = isIngoreAnchor ? '100%' : itemAnchor;
            
            formItems[formItems.length] = {
                labelAlign : ht.config.label.ALIGN,
                labelWidth : labelWidth,
                labelSeparator:  ht.config.label.SEPARATOR,
                columnWidth : itemColWidth,
                hidden : isHidden,
                items : formItem
            }
            
            //增加、修改
            if (formItem.name) {
                fieldNames[fieldNames.length] = formItem.name;
            }  
            
            if (formItem.xtype == 'compositefield') {
                for (var kk = 0; kk < formItem.items.length; kk++) {
                    if (formItem.items[kk].name) {
                        fieldNames[fieldNames.length] = formItem.items[kk].name;
                    }
                }
            }
        }
        
        panelHeight = panelHeight + parseInt((formItemMaxLen + LINE_COLS - 1) / LINE_COLS) * FIELD_HEIGHT;
        backpanel.tempDataFieldNames = fieldNames;
        
        //按钮栏
        var formButtons = new Array();
        var tmpButtons = new Array();
        if((useTo == USETO_WINEDIT) && formConfig.submit
                && formConfig.submit.enableSubmitButton !== false){
            var submitPosition = formConfig.submit.submitPosition !== undefined ? formConfig.submit.submitPosition : 0;
            tmpButtons[submitPosition] = {
                text: ht.msg.base.submitText,
                tagValue : formConfig.submit.tagValue,
                handler : function(){ backpanel.submitFn()}
            };
            var cancelPosition = formConfig.submit.cancelPosition !== undefined ? formConfig.submit.cancelPosition : submitPosition + 1;
            tmpButtons[cancelPosition] = {
                text: ht.msg.base.cancelText,
                handler : function(){
                    var win = backpanel.findParentByType(Ext.Window);
                    if (win) {
                        win.hide();
                    }
                    formPanel.getForm().reset();
                }
            };
        }
        
        //按钮栏增加额外控件
        var ctButtons = formConfig.ctButtons;
        if (ctButtons) {
            for(var i = 0; i < ctButtons.length; i++) {
                var position = ctButtons[i].position !== undefined ? ctButtons[i].position : 0;
                while(tmpButtons[position]) {
                    position += 1;
                }
                tmpButtons[position] = ctButtons[i];
            }
        }
        
        for(var i = 0; i < tmpButtons.length; i++) {
            if (tmpButtons[i]) {
               formButtons[formButtons.length] = tmpButtons[i];
            }
        }
        
        if (formButtons.length == 0) {
            formButtons = null;
            panelHeight =  panelHeight - BUTTON_HEIGHT;
        }
        
        if (formConfig.isWindowButtons) {
            backpanel.ht_outputButtons = formButtons;
        }
        
        //查询面板按键效果
        formConfig.query = formConfig.query ? formConfig.query : {};
        var queryFormPanelBindKeys = [];
        if((useTo == USETO_QUERY || useTo == USETO_WINQUERY) && (formConfig.query.enableQueryButton !== false)) {
            
            queryFormPanelBindKeys.push({
                key : Ext.EventObject.ENTER,
                fn : backpanel.queryFn,
                scope : this
             });
        }
        
        //生成form
        var formPanel = new Ext.form.FormPanel({
            region : 'center',
            url : 'json',
            fileUpload : formConfig.fileUpload,
            autoScroll : (useTo == USETO_QUERY || useTo == USETO_WINQUERY || formConfig.autoScroll === false) ? false : true,
            frame : true,
            layout : 'form',
            bodyStyle : BODY_STYLE,
            items : [{
                layout : 'column',
                defaults : {
                    layout : 'form',
                    defaults : {
                        anchor : ANCHOR
                    }
                },
                items : formItems
            }],
            buttonAlign : ht.config.layout.BUTTON_ALIGN,
            buttons : formConfig.isWindowButtons ? null : formButtons,
            keys : queryFormPanelBindKeys
        });
        backpanel.ht_outputFormPanel = formPanel;
        
        //工具栏增加查询按钮
        var queryTmpBars = new Array();
        var queryTopbar = new Array();
        
        if((useTo == USETO_QUERY || useTo == USETO_WINQUERY)
                && formConfig.query.enableQueryButton !== false){
            var queryPosition = formConfig.query.queryPosition !== undefined ? formConfig.query.queryPosition : 0;
            queryTmpBars[queryPosition] = {
                text: ht.msg.base.queryText,
                tagValue : formConfig.query.tagValue,
                enableByRowSelected : false,
                iconCls : 'query',
                handler : function(){backpanel.queryFn()}
            };
            var resetPosition = formConfig.query.resetPosition !== undefined ? formConfig.query.resetPosition : queryPosition + 1;
            queryTmpBars[resetPosition] = {
                text: ht.msg.base.resetText,
                enableByRowSelected : false,
                iconCls : 'reset',
                handler : function(){
                    formPanel.getForm().reset();
                    if(backpanel.ctListeners.afterReset) {
                        backpanel.ctListeners.afterReset();
                    }
                }
            };
        }
        
        //工具栏增加额外控件
        var ctTopbarComponts = formConfig.ctTopbarComponts;
        if (ctTopbarComponts) {
            for(var i = 0; i < ctTopbarComponts.length; i++) {
                var position = ctTopbarComponts[i].position !== undefined ? ctTopbarComponts[i].position : 0;
                while(queryTmpBars[position]) {
                    position += 1;
                }
                ctTopbarComponts[i].enableByRowSelected = ctTopbarComponts[i].enableByRowSelected !== false ? true : false;
                
                if ('->' == ctTopbarComponts[i].text) {
                     ctTopbarComponts[i] = '->';
                }
                queryTmpBars[position] = ctTopbarComponts[i];
            }
        }
        
        for(var i = 0; i < queryTmpBars.length; i++) {
            if (queryTmpBars[i]) {
                if ((queryTmpBars[i].ingoreSeparator || queryTmpBars[i] == '->')
                    && (queryTopbar[queryTopbar.length - 1] != '->')) {
                    queryTopbar[queryTopbar.length - 1] = queryTmpBars[i];
                } else {
                    queryTopbar[queryTopbar.length] = queryTmpBars[i];
                }
                if (queryTmpBars.length > (i + 1) && (queryTopbar[queryTopbar.length - 1] != '->')) {
                    queryTopbar[queryTopbar.length] = '-';
                }
            }
        }
        
        //获取panel的高度
        if (queryTopbar.length == 0) {
            queryTopbar = null;
            panelHeight =  panelHeight - TOOL_HEIGHT;
        }
        
        if (formConfig.panelDiffHeight) {
            panelHeight += formConfig.panelDiffHeight;
        }
        
        if (formConfig.panelHeight && formConfig.panelHeight > panelHeight) {
           panelHeight = formConfig.panelHeight;
        }
        backpanel.ht_outputPanelHeight = panelHeight;
        
        //panel赋属性
        backpanel.height = panelHeight;
        backpanel.tbar = queryTopbar;
        backpanel.items = [formPanel];
        
        ht.base.PropertyPanel.superclass.initComponent.call(backpanel);
        backpanel.enableBarBtnByTag();
        backpanel.enableBarBtn(false);
        
        backpanel.on('beforedestroy', function(){
            backpanel.removeAll(true);
        })
        
        backpanel.on('afterLayout', function() {
            
            backpanel.tempLayouted = true;
            
            //失去焦点时，去除输入框值的左右空格
            ht.pub.trimFields(backpanel.ht_outputFormPanel, backpanel.tempDataFieldNames);
            
            //增加备注信息
            ht.pub.remarkFields(backpanel.ht_outputFormPanel, backpanel.tempDataFieldNames);
            
            if ((useTo == USETO_QUERY || useTo == USETO_WINQUERY || useTo == USETO_PROPERTY) && formPanel) {
               ht.pub.disableFields(formPanel, fieldNames, false);
            }
        });
        
    }
});

Ext.reg('propertypanel', ht.base.PropertyPanel);

