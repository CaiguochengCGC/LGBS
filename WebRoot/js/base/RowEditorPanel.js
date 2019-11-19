Ext.namespace('ht.base');


/**
 * @class ht.base.RowEditorPanel
 * @extends Ext.Panel
 * <p>普通的增删改查面板，包含一个查询面板，一个表格面板，并提供标准增删改查功能</p>
 * <li>1.表格可使用冻结列视图</li>
 * <li>2.表格可使用缓冲视图(可提高前台表格数据加载速度)</li>
 */

ht.base.RowEditorPanel = Ext.extend(Ext.Panel, {
    layout : 'border',
    border : false,
    
    /**
     * @cfg(Ext.data.JsonStore) ht_outputStore
     * @type Ext.data.JsonStore 
     * (输出)查询用的store，在表格显示
     */
    ht_outputStore : null, 
    
    /**
     * @cfg(Ext.grid.GridPanel) ht_outputGrid
     * @type Ext.grid.GridPanel
     * (输出)表格
     */
    ht_outputGrid : null, //表格 
    
    /**
     * @cfg(Ext.form.FormPanel) ht_outputQueryForm
     * @type Ext.form.FormPanel
     * (输出) 查询面板form，如果没有查询则为null
     */
    ht_outputQueryForm : null, //查询form
    
     /**
     * @cfg(ht.base.PropertyPanel) ht_outputQueryPanel
     * @type ht.base.PropertyPanel 
     * 临时数据，用于封装方法之间的传递，不可使用
     */
    ht_outputQueryPanel : null,
    
    /**
     * @cfg(Ext.ux.grid.RowEditor) ht_outputEditor
     * @type Ext.ux.grid.RowEditor
     * (输出) 行编辑
     */
    ht_outputEditor : null, //editor
    
    /**
     * @cfg(Ext.form.FormPanel) ht_outputPropertyFormPanel
     * @type Ext.form.FormPanel 
     * (输出)修改功能用的form
     */
    ht_outputPropertyFormPanel : null,
    
    /**
     * 
     * @type 
     */
    initConfig : {
        tagValues : [] //所有权限
    },
    
    /**
     * @cfg {Object} queryFormConfig
     * 查询面板配置
     */
    queryFormConfig : {
        enableQueryButton : true, //不使用查询按钮
        items : [{
        
            /**
             * @cfg(Boolean) autoTrim
             * @type Boolean
             * 提交前是否去左右空格
             */
            autoTrim : true
        }], //查询面板内容
        
        /**
         * @cfg(String) useTo
         * @type String
         * 面板用途，query :查询,win-query：弹出窗口查询
         */
        useTo : 'query',
        
        action : '', //查询action
        outputTable : '', //store需要的output
        autoQuery : false, //面板初始化后自动查询
        autoQueryParam : [{fieldName :'', fieldValue : ''}],//自动查询需要的参数
        autoQueryPreCondition : {}, //自动加载的前提条件
        
        tagValue : '', //查询功能权限
        queryHeight : 0, //查询面板的高度，当值大于自动高度时取该值
        queryDiffHeight : 0,
        queryPosition : 0, //查询按钮的位置
        resetPosition : 1, //重置按钮的位置,
        
        //额外
        ctTopbarComponts : [{

            tagValue : '',  //功能权限
            ingoreSeparator : false,//忽略左侧分割符
            position : 0, //按钮位置,
            enableByRowSelected : true, //当选中表格行时才可使用
            enableByHasData : false
        }]
    },
    
    /**
     * @cfg {Object} gridConfig
     * 数据表格配置项
     */
    gridConfig : {
        
        storeFields : [], //
        deletedKeyFields : [], //删除用的field
        initFields : [{fieldName : '', fieldValue : ''}], //增加时数据初始化
       
        /**
         * @cfg(Array) columns
         * @type Array
         * 表格的列
         * enableFilter : false //是否列过滤
         * filter : {
                type: 'date',
                dateFormat : ht.config.format.DATETIME
            }
            
         *filter : {
                type: 'list',
                rendererStore : editStateStore
            }
         
         *filter : {
                type: 'string',
                rendererStore : editStateStore
            }
            
         *filter : {
                type: 'numeric'
            }
         */
        columns : [], //表格列
        bufferView: true, //是否缓冲
        defaultSortable: true, //是否列排序
        enableFilter : true, //是否支持列过滤
        pageSize: 0, //每页行数
        enablePageSize : true, //可以修改调整行数
        isPageAction : true, //是否分页
        isMultipleSelect : true,  //是否多选
        isLocalPageAction : false, //是否本地数据分页
        panelBorder : true, //表格面板边框
        panelStyle : '',
        title : '', //表格表头
        table : '', //该属性不推荐使用，推荐使用queryFormConfig.outputTable
        plugins : null,
        ctConfig : {}, //其他表格配置
        
        
        //增加按钮
        addBtn : {
            action : '', //增加的action
            outputTable : '', //数据返回的output
            tagValue : '', //增加按钮的权限
            position : 1 //增加按钮的位置
        },
        
        //修改按钮
        updateBtn : {
            action : '',//修改的action
            outputTable : '', //数据返回的output
            tagValue : '',//修改按钮的权限
            position : 2//修改按钮的位置
        },
        
        //删除按钮
        deleteBtn : {
            action : '',//删除的action
            tagValue : '',//删除按钮的权限
            position : 3 //删除按钮的位置
        },
        
        //表格顶部额外控件
        ctTopbarComponts : [{

            tagValue : '', //控件的权限
            ingoreSeparator : false, //是否忽略左侧分隔符
            position : 3, //控件的位置
            enableByRowSelected : true, //是否是选择行时有效,
            enableByHasData : false //是否是有数据时有效,
        }],
        
        /**
         * @cfg {Object} excelConfig
         * excel操作配置项
         */
        excelConfig : {
            enableExport : false, //是否导出
            tagValue : '', //导出的权限
            ingoreCols : [],
            excelName : '', //excel的名称
            rowNumPosition : 2, //每页行数的位置
            excelPosition : 3 //excel按钮的位置
        },
        
        /**
         * @cfg {Object} printConfig
         * 打印表格配置
         */
        printConfig : {
            enablePrint : false, //是否打印
            enablePrintView : false, //是否打印预览
            tagValue : '', //打印、打印预览权限
            rowNumPosition : 4, //每页行数的位置
            printPosition : 5 //打印按钮的位置,
        },
        
        //底部额外控件
        ctBottombarComponts : [{

            //
            tagValue : '', //控件的权限
            ingoreSeparator : false,//是否忽略左侧分隔符
            position : 0, //控件的位置,
            enableByHasData : false,
            enableByRowSelected : true
        }]
        
    },
    
    propertyFormConfig : {
        enablePropertyForm : false, //控制属性面板是否存在
        defaultCollapsed : true, //是否默认隐藏
        title : '',
        split : true,
        width : 0,
        action : '', //是否通过后台查询
        outputTable : '',
        items : []
    
    },
    
    /**
     * 监听事件
     * @type 
     */
    ctListeners : {
        beforeQuery : function(values){return true}, //查询前
        afterQuery : function(){}, //查询后
        afterReset : function(){}, 
        beforeAdd : function(values, formSubmit){return true}, //增加前
        afterAdd : function(values){return true}, 
        beforeUpdate : function(values, formSubmit){return true},
        afterUpdate : function(values){return true},
        beforeDelete : function(values){return true},
        afterDelete : function(values){},
        
        clickGridRow : function(agrid, rowIndex, e){},
        
        /**
         * 行不选中之前监听，如果返回false，则不继续执行
         * @return {Boolean}
         */
        beforeRowdeselect : function(sm,rowIndex, record){return true},
        
        /**
         * 行选中之前监听，如果返回false，则不继续执行
         * @return {Boolean}
         */
        beforeRowselect : function(sm,rowIndex, record){return true},
        
        afterLayoutEditor : function(){},
        
        /**
         * 修改编辑弹出前
         * @param {} value
         * @return {Boolean}
         */
        beforeUpdateEdit : function(value) {return true},
        
        afterAutoQuery : function(){},
        
        enableBarBtn : function(enable) {},
        
        validateedit : function(values){return true},
        
        afterCanceledit : function() {},
        
        /**
         * 点击增加按钮（马上处理）
         * @return {Boolean}
         */
        clickAddBtn : function() {return true},
        
        /**
         * 点击删除按钮（马上处理）
         * @return {Boolean}
         */
        clickDeleteBtn : function() {return true},
        
        /**
         * 额外属性-获取表格数据之后
         */
        afterGetGridDataToPropery : function(data){return true},
        
        /**
         * 填充额外属性之前
         */
        beforeFillPropery : function(data){return true},
        
        /**
         * 行-单元格数据属性填充前
         */
        beforeFillRowProperties : function(data){return true}
    },
    
    isAddAction : function() {
        var backpanel = this; 
        var action = backpanel.getSelected().get('editorAction');
        return 'add' == action;
    },
    
    /**
     * 停止编辑
     */
    stopEditing : function() {
        var backpanel = this;
        var editor = backpanel.ht_outputEditor;
        if(editor.editing){
            editor.stopEditing(false);
        }
        editor.lastClickIndex = -1;
    
    },
    
    /**
     * 查询后调整数据
     * private
     */
    callbackStore : function() {
        
        var backpanel = this;
        
        //格式化时间
//        ht.pub.date.formatDateFields(backpanel.gridConfig.storeFields, backpanel.ht_outputStore);
        
        if (!backpanel.ctListeners) {
            backpanel.ctListeners = {};
        }
        
        if(backpanel.ctListeners.afterQuery) {
            backpanel.ctListeners.afterQuery();
        }
        
        backpanel.enableRelativePanelBarBtn(false);
        
    },
    
    /**
     * 禁用关联面板的控件,需要重写
     * @param {} enable
     */
    enableRelativePanelBarBtn : function(enable) {
    
    
    },
    
    /**
     * 判断表格是否选中
     * @return {Boolean}
     */
    hasSelection : function() {
       var backpanel = this;
       return backpanel.ht_outputGrid.getSelectionModel().hasSelection();
    },
    
    /**
     * 返回选中的一行
     * @return {Ext.data.Record}
     */
    getSelected : function() {
        var backpanel = this;
        return backpanel.ht_outputGrid.getSelectionModel().getSelected();
    },
    
     /**
     * 返回选中行
     * @return {Ext.data.Record[]}
     */
    getSelections : function() {
        var backpanel = this;
        return backpanel.ht_outputGrid.getSelectionModel().getSelections();
    },
    
    /**
     * 返回表格顶部工具栏
     * @return {Ext.Toolbar}
     */
    getGridTopToolbar : function() {
        var backpanel = this;
        return backpanel.ht_outputGrid.ownerCt.getTopToolbar()
    },
    
    /**
     * 返回表格底部工具栏
     * @return {Ext.Toolbar}
     */
    getGridBottomToolbar : function() {
        var backpanel = this;
        return backpanel.ht_outputGrid.getBottomToolbar(); 
    },
    /**
     * 返回查询顶部工具栏
     * @return {Ext.Toolbar}
     */
    getQueryTopToolbar : function() {
        var backpanel = this;
        return backpanel.ht_outputQueryForm.ownerCt.getTopToolbar();
    },
    
    /**
     * 根据键值对返回表格顶部工具栏组件
     * @return {Ext.Component[]}
     */
    findGridTopbarCompments : function(prop, value) {
        var backpanel = this;
        return backpanel.ht_outputGrid.ownerCt.getTopToolbar().find(prop,value); 
    },
    
    /**
     * 根据键值对返回表格底部工具栏组件
     * @return {Ext.Component[]}
     */
    findGridBottombarCompments : function(prop, value) {
        var backpanel = this;
        return backpanel.ht_outputGrid.getBottomToolbar().find(prop,value); 
    },
    
    /**
     * 根据键值对返回查询面板组件
     * @return {Ext.form.Field[]}
     */
    findQueryCompments : function(prop, value){
        var backpanel = this;
        return backpanel.ht_outputQueryForm.find(prop,value);
    },
    
    /**
     * 根据返回输入控件名查询面板组件
     * @return {Ext.form.Field}
     */
    findQueryCompment : function(name){
        var backpanel = this;
        return backpanel.ht_outputQueryForm.getForm().findField(name);
    },
    
    /**
     * 根据键值对返回查询面板顶部工具栏组件
     * @return {Ext.Component[]}
     */
    findQueryTopbarCompments : function(prop, value){
        var backpanel = this;
        return backpanel.ht_outputQueryForm.ownerCt.getTopToolbar().find(prop,value);
    },
    
    /**
     * 获取查询条件的值
     * @return {{}}
     */
    getQueryFormValues : function() {
        var backpanel = this;
        return backpanel.ht_outputQueryForm.ownerCt.getFormValues();
    },
    
    /**
     * 获取查询条件的值
     * @return {{}}
     */
    getQueryFormFieldValues : function() {
        var backpanel = this;
        return backpanel.ht_outputQueryForm.ownerCt.getFormFieldValues();
    },
    
    /**
     * 修改store(有缺陷)
     * @param {Ext.data.JsonStore} store
     */
    setStore : function(store) {
        var backpanel = this;
        store.autoDestroy = true;
        
        backpanel.ht_outputStore = store;
//        backpanel.ht_outputGrid.store = backpanel.ht_outputStore;
        backpanel.ht_outputGrid.reconfigure(backpanel.ht_outputStore,  backpanel.ht_outputGrid.getColumnModel());
        backpanel.ht_outputQueryPanel.formConfig.query.store = backpanel.ht_outputStore;
        var gridTmpBottombars = backpanel.ht_outputGrid.getBottomToolbar();
        if (gridTmpBottombars 
            && (gridTmpBottombars  instanceof Ext.PagingToolbar || gridTmpBottombars.type == 'paging')) {
            gridTmpBottombars.bindStore(backpanel.ht_outputStore);
        }
        
        store.on('load', function(st, records, opt){
            backpanel.enableBarBtn(false);
            backpanel.callbackStore();
            backpanel.enableBarBtnByHasdata();
        });
    },
    
    /**
     * 根据表格行是否选择，启用、禁用表格顶部按钮
     * @param {Boolean} enable
     * @param {Boolean} isListener 是否是enableBarBtn的监听回调
     */
    enableBarBtn : function(enable, isListener) {
        
        //禁用查询工具栏按钮
        var backpanel = this;
        var compents = new Array();
        
        //禁用表格面板顶部工具栏按钮
        var gridTmpTopbars = backpanel.ht_outputGrid.ownerCt.getTopToolbar();
        if (gridTmpTopbars) {
            var i = 0;
            var comp = gridTmpTopbars.getComponent(i);
            while (comp) {
                compents[compents.length] = comp;
                i++;
                comp = gridTmpTopbars.getComponent(i);
            }
        }
        
        //表格底部工具按钮
        var gridTmpBottombars = backpanel.ht_outputGrid.getBottomToolbar();
        if (gridTmpBottombars) {
            var i = 0;
            var comp = gridTmpBottombars.getComponent(i);
            while (comp) {
                compents[compents.length] = comp;
                i++;
                comp = gridTmpBottombars.getComponent(i);
            }
        }
        
        //查询顶部工具按钮
        var queryPanelTopbar = backpanel.ht_outputQueryPanel.getTopToolbar();
        if (queryPanelTopbar) {
            var i = 0;
            var comp = queryPanelTopbar.getComponent(i);
            while (comp) {
                compents[compents.length] = comp;
                i++;
                comp = queryPanelTopbar.getComponent(i);
            }
        }
        
        ht.pub.enableCompentByRowSelected(compents, enable);
        
        //行选中关联控件监听
        if (!backpanel.ctListeners) {
            backpanel.ctListeners = {};
        }
        
        if (backpanel.ctListeners.enableBarBtn && !isListener) {
            backpanel.ctListeners.enableBarBtn(enable);
        }
        
    },
    
    /**
     * 启用、禁用表格顶部按钮
     * @param {Boolean} enable 是否启用
     * @param {String} prop 控件属性
     * @param {String} propValue 控件的值
     */
    enableGridTopbar : function(enable, prop, propValue) {
        
        //禁用查询工具栏按钮
        var backpanel = this;
        var compents = new Array();
        
        //禁用表格面板顶部工具栏按钮
        var gridTmpTopbars = backpanel.ht_outputGrid.ownerCt.getTopToolbar();
        if (gridTmpTopbars) {
            var i = 0;
            var comp = gridTmpTopbars.getComponent(i);
            while (comp) {
                compents[compents.length] = comp;
                i++;
                comp = gridTmpTopbars.getComponent(i);
            }
        }
        ht.pub.enableCompents(compents, enable, prop, propValue);
    },
    
    /**
     * 根据是否有数据，启用、禁用按钮
     */
    enableBarBtnByHasdata : function() {
        
        //禁用查询工具栏按钮
        var backpanel = this;
        var compents = new Array();
        
        //表格面板顶部工具栏按钮
        var gridTmpTopbars = backpanel.ht_outputGrid.ownerCt.getTopToolbar();
        if (gridTmpTopbars) {
            var i = 0;
            var comp = gridTmpTopbars.getComponent(i);
            while (comp) {
                compents[compents.length] = comp;
                i++;
                comp = gridTmpTopbars.getComponent(i);
            }
        }
        
        //表格底部工具按钮
        var gridTmpBottombars = backpanel.ht_outputGrid.getBottomToolbar();
        if (gridTmpBottombars) {
            var i = 0;
            var comp = gridTmpBottombars.getComponent(i);
            while (comp) {
                compents[compents.length] = comp;
                i++;
                comp = gridTmpBottombars.getComponent(i);
            }
        }
        
        //查询顶部工具按钮
        var queryPanelTopbar = backpanel.ht_outputQueryPanel.getTopToolbar();
        if (queryPanelTopbar) {
            var i = 0;
            var comp = queryPanelTopbar.getComponent(i);
            while (comp) {
                compents[compents.length] = comp;
                i++;
                comp = queryPanelTopbar.getComponent(i);
            }
        }
        ht.pub.enableCompentByHasData(compents, backpanel.ht_outputStore.getCount() != 0);
    },
    
    tempDataFieldNames : [],
    
    /**
     * 结束增加
     * @param {json} values
     */
    endAdd : function(values) {
        values['editorAction'] = '';
    },
    
    /**
     * 将控件改为不可编辑
     * @param {} isUpdated 是否是不可修改控件
     */
    disableFields : function(isUpdated) {
        
        var backpanel = this;
        var fieldNames = backpanel.tempDataFieldNames;
        var editor = backpanel.ht_outputEditor;
        
        ht.pub.disableFields(editor, fieldNames, isUpdated);
    },
    
    /**
     * 根据tagValue禁用按钮
     * @param {} backpanel
     * @param {} enable
     */
    enableBarBtnByTag : function() {
        
        var backpanel = this;
        var tagValues = backpanel.initConfig.tagValues;
        var compents = new Array();
        if (!tagValues) {
            return;
        }
        
        //禁用表格面板顶部工具栏按钮
        var gridTmpTopbars = backpanel.ht_outputGrid.ownerCt.getTopToolbar();
        if (gridTmpTopbars) {
            var i = 0;
            var comp = gridTmpTopbars.getComponent(i);
            while (comp) {
                compents[compents.length] = comp;
                i++;
                comp = gridTmpTopbars.getComponent(i);
            }
        }
        
        //禁用表格面板底部工具栏按钮
        var gridBottonbar = backpanel.ht_outputGrid.getBottomToolbar();
        if (gridBottonbar) {
            var i = 0;
            var comp = gridBottonbar.getComponent(i);
            while (comp) {
                compents[compents.length] = comp;
                i++;
                comp = gridBottonbar.getComponent(i);
            }
        }
        
        ht.pub.disableCompentByTag(compents, tagValues);
    },

    initQueryPanel : function() {
        
        var backpanel= this;
        var gridConfig = backpanel.gridConfig;
        var queryFormConfig = backpanel.queryFormConfig;
        var ctListeners = backpanel.ctListeners;
        
        var queryLen = queryFormConfig.items == undefined ? 0 : queryFormConfig.items.length;
        var queryPanel = new ht.base.PropertyPanel({
            region : 'north',
            hidden : queryLen == 0 ? true : false,
            initConfig : {
                tagValues : backpanel.initConfig.tagValues
            },
            
            formConfig : {
                query : {
                    enableQueryButton : queryFormConfig.enableQueryButton,
                    store : backpanel.ht_outputStore,
                    tagValue : queryFormConfig.tagValue, //功能权限
                    queryPosition : queryFormConfig.queryPosition, 
                    resetPosition : queryFormConfig.resetPosition 
                },
                ctTopbarComponts : queryFormConfig.ctTopbarComponts,
                useTo : queryFormConfig.useTo, 
                panelHeight : queryFormConfig.queryHeight, 
                panelDiffHeight : queryFormConfig.queryDiffHeight, 
                items : queryFormConfig.items
            },
            ctListeners : {
                beforeQuery : function(values) { 
                    backpanel.stopEditing();
                    backpanel.enableRelativePanelBarBtn(false);
                    if (ctListeners.beforeQuery && !ctListeners.beforeQuery(values)) {
                        return false;
                    }
                    return true;
                },
                afterReset : ctListeners.afterReset,
                afterQuery : function(){},
                enableBarBtn : function() {}
            }
        });
        
        backpanel.ht_outputQueryForm = queryPanel.ht_outputFormPanel;
        backpanel.ht_outputQueryPanel = queryPanel;
        
        return queryPanel;
       
    },
    
    initGridBottombar : function() {
        var backpanel = this;
        var toolPager;
        
        //初始化参数
        var gridTmpBottombars = new Array(); 
        var gridBottombar = new Array();  
        var queryFormConfig = backpanel.queryFormConfig;
        var gridConfig = backpanel.gridConfig;
        var store = backpanel.ht_outputStore;
        var queryPanel = backpanel.ht_outputQueryPanel;
        var ctListeners = backpanel.ctListeners;
        var excelConfig = backpanel.gridConfig.excelConfig ? backpanel.gridConfig.excelConfig : {};
        var printConfig = backpanel.gridConfig.printConfig ? backpanel.gridConfig.printConfig : {};
        
        //excel(1,2,3)
        var bottomPosition = 1;
        if ((excelConfig.enableExport && gridConfig.isPageAction !== false && gridConfig.enablePageSize !== false)
           || gridConfig.enablePageSize === true) {
            var rowNumPosition = excelConfig.rowNumPosition !== undefined ? excelConfig.rowNumPosition : bottomPosition;
            gridTmpBottombars[rowNumPosition] = {
                xtype : 'tbtext',
                text : ht.msg.pagerbar.pageSizeMsg
            },
            
            bottomPosition++;
            gridTmpBottombars[rowNumPosition + 1] = {
                xtype : 'numberfield',
                value : ht.config.params.PAGE_SIZE,
                width : 50,
                minValue : 1,
                maxValue : ht.config.params.MAX_PAGE_SIZE,
                allowBlank : false,
                allowDecimals : false,
                enableByRowSelected : false,
                ingoreSeparator : true,
                enableKeyEvents : true,
                listeners : {
                    keydown  : function(textField, e){
                       if(e.getKey() == Ext.EventObject.ENTER){
                           if (!textField.isValid()) {
                               return;
                           }
                           
                           backpanel.stopEditing();
                           
                           if ((backpanel.initConfig.tagValues
                                  && queryFormConfig.tagValue
                                  && (backpanel.initConfig.tagValues.indexOf(queryFormConfig.tagValue) > -1))
                              || !backpanel.initConfig.tagValues || !queryFormConfig.tagValue) {
                                
                               store.baseParams['limit'] = textField.getValue();
                               toolPager.pageSize = textField.getValue();
                               
                               backpanel.ht_outputQueryPanel.queryFn();
                           }
                       }
                    }
                 }
            };
        }
        
        if (excelConfig.enableExport) {
            bottomPosition++;
            var excelposition = excelConfig.excelPosition !== undefined ? excelConfig.excelPosition : bottomPosition;
            gridTmpBottombars[excelposition] = {
                text: ht.msg.excel.exportText,
                tagValue : excelConfig.tagValue,
                enableByHasData : true,
                enableByRowSelected : false,
                iconCls : 'excel',
                handler : function(){
                    backpanel.stopEditing();
                    
                    var excelName = excelConfig.excelName ? excelConfig.excelName : ht.msg.excel.defaultExcelName;
                    ht.pub.excel.exportExcel(backpanel.ht_outputGrid, excelName, excelConfig.ingoreCols);
                }
            };
        }
        
        //打印输入框(5,6,7)
        if ((printConfig.enablePrintView || printConfig.enablePrint)
               && excelConfig.enableExport == false && gridConfig.isPageAction !== false
               && gridConfig.enablePageSize !== false) {
            bottomPosition++;
            var rowNumPosition = printConfig.rowNumPosition !== undefined ? printConfig.rowNumPosition : bottomPosition;
            gridTmpBottombars[rowNumPosition] = {
                xtype : 'tbtext',
                text : ht.msg.pagerbar.pageSizeMsg
            },
            
            bottomPosition++;
            gridTmpBottombars[rowNumPosition + 1] = {
                xtype : 'numberfield',
                value : ht.config.params.PAGE_SIZE,
                width : 50,
                minValue : 1,
                maxValue : ht.config.params.MAX_PAGE_SIZE,
                allowBlank : false,
                allowDecimals : false,
                enableByRowSelected : false,
                ingoreSeparator : true,
                enableKeyEvents : true,
                listeners : {
                    keydown  : function(textField, e){
                       if(e.getKey() == Ext.EventObject.ENTER){
                           if (!textField.isValid()) {
                               return;
                           }
                           
                           backpanel.stopEditing();
                           
                           if ((backpanel.initConfig.tagValues
                                  && queryFormConfig.tagValue
                                  && (backpanel.initConfig.tagValues.indexOf(queryFormConfig.tagValue) > -1))
                              || !backpanel.initConfig.tagValues || !queryFormConfig.tagValue) {
                                
                               store.baseParams['limit'] = textField.getValue();
                               toolPager.pageSize = textField.getValue();
                               backpanel.ht_outputQueryPanel.queryFn();
                           }
                       }
                    }
                 }
            };
        }
        
        //打印预览
        if (printConfig.enablePrintView) {
            bottomPosition++;    
            var position = printConfig.printPosition !== undefined ? printConfig.printPosition : bottomPosition;
            gridTmpBottombars[position] = {
                text: ht.msg.printer.preview,
                tagValue : printConfig.tagValue,
                enableByHasData : true,
                enableByRowSelected : false,
                iconCls : 'print',
                handler : function(){
                    backpanel.stopEditing();
                    
                    ht.pub.printer.printPreview(backpanel.ht_outputGrid);
                }
            };
        }
        
        //打印
        if (printConfig.enablePrint) {
            bottomPosition++;    
            var position = bottomPosition;
            gridTmpBottombars[position] = {
                text: ht.msg.printer.print,
                tagValue : printConfig.tagValue,
                enableByHasData : true,
                enableByRowSelected : false,
                iconCls : 'print',
                handler : function(){
                    backpanel.stopEditing();
                    
                    ht.pub.printer.printDirect(backpanel.ht_outputGrid);
                }
            };
        }
        
        //工具栏增加额外控件
        bottomPosition++;    
        var ctBottombarComponts = gridConfig.ctBottombarComponts
        if (ctBottombarComponts) {
            for(var i = 0; i < ctBottombarComponts.length; i++) {
                var position = ctBottombarComponts[i].position !== undefined ? ctBottombarComponts[i].position : bottomPosition;
                ctBottombarComponts[i].enableByRowSelected = ctBottombarComponts[i].enableByRowSelected !== false ? true : false;
                if ('->' == ctBottombarComponts[i].text) {
                     ctBottombarComponts[i] = '->';
                }
                while(gridTmpBottombars[position]) {
                    position += 1;
                }
                gridTmpBottombars[position] = ctBottombarComponts[i];
            }
        }
        
        for(var i = 0; i < gridTmpBottombars.length; i++) {
            if (gridTmpBottombars[i]) {
                if ((gridTmpBottombars[i].ingoreSeparator || gridTmpBottombars[i] == '->')
                    && (gridBottombar[gridBottombar.length - 1] != '->')) {
                    gridBottombar[gridBottombar.length - 1] = gridTmpBottombars[i];
                } else {
                    gridBottombar[gridBottombar.length] = gridTmpBottombars[i];
                }
                if (gridTmpBottombars.length > (i + 1) && (gridBottombar[gridBottombar.length - 1] != '->')) {
                    gridBottombar[gridBottombar.length] = '-';
                }
            }
        }
        
        if (gridBottombar.length == 0) {
            gridBottombar = null;
        }
        
        if (gridConfig.isPageAction !== false) {
            if (gridBottombar) {
                gridBottombar.splice(0, 0, '-');
            }
            toolPager =  new Ext.PagingToolbar({
                store : store,
                pageSize: gridConfig.pageSize,
                displayInfo : true,
                displayMsg : ht.msg.pagerbar.displayMsg,
                emptyMsg : ht.msg.pagerbar.emptyMsg,
                items : gridBottombar
            });
            return toolPager;
        } else {
           return gridBottombar;
        }
    },
    
    initGridEditor : function(){
        var backpanel = this;
        
        //初始化参数
        var gridTmpTopbars = new Array(); 
        var gridTopbar = new Array();  
        var queryFormConfig = backpanel.queryFormConfig;
        var gridConfig = backpanel.gridConfig;
        var store = backpanel.ht_outputStore;
        var queryPanel = backpanel.ht_outputQueryPanel;
        var ctListeners = backpanel.ctListeners;
        var ACTION_ADD = 'add';
        
        
        var gridEditor = new Ext.ux.grid.RowEditor({
            monitorValid: true,
            errorSummary: false,
            focusDelay : 10,
            saveText: ht.msg.base.editor.saveText,
            cancelText: ht.msg.base.editor.cancelText,
            commitChangesText: ht.msg.base.editor.commitChangesText,
            errorText: ht.msg.base.editor.errorText,
            listeners : {
                afterlayout : function(){
                    if(ctListeners.afterLayoutEditor){
                        ctListeners.afterLayoutEditor();
                    }
                    
                    //禁用某些按键
                    ht.pub.ingoreKeysEvent(backpanel.ht_outputEditor.id);
                    
                    //失去焦点时，去除输入框值的左右空格
                    ht.pub.trimFields(backpanel.ht_outputEditor, backpanel.tempDataFieldNames);
                    
                    //控件初始化
                    var record = backpanel.ht_outputGrid.getSelectionModel().getSelected();
                    if (record.data.editorAction == ACTION_ADD) {
                        backpanel.disableFields(false);
                        backpanel.enableRelativePanelBarBtn(false);
                    } else {
                        backpanel.disableFields(true);
                    }
                },
                beforeedit: function(roweditor, rowIndex){
                    
                    var record = backpanel.ht_outputGrid.getSelectionModel().getSelected();
                    if (record.data.editorAction != ACTION_ADD) {
                        
                        //没有权限控制或有权限
                        if (backpanel.initConfig.tagValues && gridConfig.updateBtn && gridConfig.updateBtn.tagValue
                            && (backpanel.initConfig.tagValues.indexOf(gridConfig.tagValue) < 0)) {
                                return false;
                        } 
                        if(ctListeners.beforeUpdateEdit && !ctListeners.beforeUpdateEdit(record.data)) {
                                return false;
                        }
                        var store = backpanel.ht_outputStore;
                        var lastRecordInd = store.getCount() - 1;
                        var record = store.getRange(lastRecordInd, lastRecordInd + 1)[0];
                        
                        if (record.data.editorAction == ACTION_ADD) {
                            store.remove(record);
                        }
                    }
//                 
                },
                canceledit: function(roweditor, forced){
                    var record = backpanel.ht_outputStore.getAt(backpanel.ht_outputStore.getCount() - 1);
                    if (record.data.editorAction == ACTION_ADD) {
                        backpanel.ht_outputStore.remove(record);
                    }
                    if(ctListeners.afterCanceledit && !ctListeners.afterCanceledit()) {
                        
                    }
                },
                validateedit: function(roweditor, changes, record, rowIndex){
                    if(ctListeners.validateedit && !ctListeners.validateedit(record.data)) {
                        return false;
                    }
                },
                afteredit: function(roweditor, changes, record, rowIndex){
                    
                    //操作前处理
                    var params = record.data;
                    if (record.data.editorAction == ACTION_ADD) {
                        params.action = gridConfig.addBtn.action;
                        if(ctListeners.beforeAdd && !ctListeners.beforeAdd(params)) {
                            return;
                        }
                    } else {
                         params.action = gridConfig.updateBtn.action;
                         if(ctListeners.beforeUpdate && !ctListeners.beforeUpdate(params)) {
                            return;
                         }
                    }
                    
                    //格式化数据
                    var fields = record.fields;
                    fields.each(function(item, index, length){
                        if(item.REF_TYPE){
                            params[item.name] = ht.pub.date.dateFormat(record.get(item.name), item.REF_TYPE);
                        }
                    });
                    
                    ht.pub.wait(ht.msg.base.processText);
                    Ext.Ajax.request({
                        url : 'json',
                        params : params,
                        success : function(response, options) {
                            var result = Ext.decode(response.responseText);
                            var action = {result : result};
                            
                            if(result.success){
                                if(record.data.editorAction == ACTION_ADD){
                                    backpanel.endAdd(record.data);
                                    ht.pub.info(ht.msg.base.addSuccessText);
                                    backpanel.enableBarBtn(true);
                                    backpanel.enableBarBtnByHasdata();
                                    
                                    if (ctListeners.afterAdd && !ctListeners.afterAdd(action)) {
                                        backpanel.enableRelativePanelBarBtn(true);
                                        return;
                                    }
                                    var table = gridConfig.addBtn.outputTable;
                                    if(table && action.result.root && action.result.root[table] 
                                          && action.result.root[table].rs && action.result.root[table].rs.length > 0) {
                                            
                                         var result = action.result.root[table].rs[0];
                                         var fieldName = '';
                                         for (var i = 0; i < gridConfig.storeFields.length; i++) {
                                             fieldName = gridConfig.storeFields[i];
                                             if (typeof fieldName != 'string') {
                                                 fieldName = gridConfig.storeFields[i].name;
                                             }
                                             var value = result[fieldName];
                                             
                                             if(value !== undefined && value !== null) {
                                                 record.data[fieldName] = value;
                                             }
                                         }
                                    }
                                    backpanel.enableRelativePanelBarBtn(true);
                                } else {
                                    
                                    //修改
                                    ht.pub.info(ht.msg.base.updateSuccessText);
                                    if (ctListeners.afterUpdate && !ctListeners.afterUpdate(action)) {
                                        return;
                                    }
                                    
                                    var table = gridConfig.updateBtn.outputTable;
                                    if(table && action.result.root && action.result.root[table] 
                                          && action.result.root[table].rs && action.result.root[table].rs.length > 0) {
                                            
                                         var result = action.result.root[table].rs[0];
                                         var fieldName = '';
                                         for (var i = 0; i < gridConfig.storeFields.length; i++) {
                                             fieldName = gridConfig.storeFields[i];
                                             if (typeof fieldName != 'string') {
                                                 fieldName = gridConfig.storeFields[i].name;
                                             }
                                             var value = result[fieldName];
                                             
                                             if(value !== undefined && value !== null) {
                                                 record.data[fieldName] = value;
                                             }
                                         }
                                    }
                                }
                                
                                record.commit();
                            }else{
                                ht.pub.error(result.errors.errmsg, function(){
                                    gridEditor.startEditing(record);
                                    record.reject();
                                });
                            }
                        },
                        failure : function(response, options) {
                            ht.pub.handleAjaxErrors(response, function(){
                                gridEditor.startEditing(record);
                                record.reject();
                            });
                            
                        }
                    });
                }
            }
        });
        backpanel.ht_outputEditor = gridEditor;
        
        return gridEditor;
    },
    
    /**
     * 增加事件
     */
    addFn : function() {
        
        //初始化参数
        var backpanel = this;
        var gridConfig = backpanel.gridConfig;
        var editor = backpanel.ht_outputEditor;
        var grid = backpanel.ht_outputGrid;
        var store = backpanel.ht_outputStore;
        var ctListeners = backpanel.ctListeners;
        
        //点击增加
        if(ctListeners.clickAddBtn && !ctListeners.clickAddBtn()){
            return;
        }
        
        backpanel.stopEditing();
        
        //初始化控件的值
        var formValue = {editorAction : 'add'};
        var initFields = gridConfig.initFields;
        if (initFields){
            for(var i = 0; i < initFields.length; i++){
                formValue[initFields[i].fieldName] = initFields[i].fieldValue;
            }
        }
        
        //增加编辑行
        var editorRecord = Ext.data.Record.create(gridConfig.storeFields);
        var record = new editorRecord(formValue);
        var rowIndex = store.getCount();
        store.insert(rowIndex, record);
        grid.getView().refresh();
        
        grid.getView().focusRow(rowIndex);
        grid.getSelectionModel().selectRow(rowIndex);
        editor.startEditing(rowIndex);
    },
    
    /**
     * 删除事件
     */
    deleteFn : function() {
        
        var backpanel = this;
        var ctListeners = backpanel.ctListeners;
        var gridConfig = backpanel.gridConfig;
        
        //点击删除按钮
        if(ctListeners.clickDeleteBtn && !ctListeners.clickDeleteBtn()){
            return;
        }
        
        var deletedKeyFields = gridConfig.deletedKeyFields;
        if (!deletedKeyFields) {
            return;
        }
        
        backpanel.stopEditing();
        
        ht.pub.confirm(ht.msg.base.deleteConfirmText, function(btn){
            if('yes' != btn) {
                return;
            }
            var params = {};
            params['action'] = gridConfig.deleteBtn.action;
            
            //删除预处理
            if (ctListeners.beforeDelete && !ctListeners.beforeDelete(params)) {
                return;
            }
            
            //填充数据
            for (var i = 0; i < deletedKeyFields.length; i++) {
                params[deletedKeyFields[i]] = new Array();
            }
            
            var records = backpanel.ht_outputGrid.getSelectionModel().getSelections();
            for (var i = 0; i < records.length; i++) {
                for (var k = 0; k < deletedKeyFields.length; k++) {
                   params[deletedKeyFields[k]][i] = records[i].get(deletedKeyFields[k]);
                }
            }
            
            //提交数据
            ht.pub.wait(ht.msg.base.processText);
            Ext.Ajax.request({
                url : 'json',
                params : params,
                success : function(response, options) {
                    var result = Ext.decode(response.responseText)
                    if(result.success){           
                        ht.pub.info(ht.msg.base.deleteSuccessText);
                        backpanel.ht_outputStore.remove(records);
                        backpanel.enableBarBtn(false);
                        backpanel.enableRelativePanelBarBtn(false);
                        backpanel.enableBarBtnByHasdata();
                        if(ctListeners.afterDelete) {
                           ctListeners.afterDelete(result);
                        }
                    }else {
                        ht.pub.error(result.errors.errmsg);
                    }
                },
                failure : function(response, options) {
                    ht.pub.handleAjaxErrors(response);
                }
            });
        });
    },
    
    initGridPanel : function() {
        
        var backpanel = this;
        var notBlackTextRegex = /^[ ]*[^ ].*$/; //非空格
        var msgTarget = ht.config.style.roweditor.msgTarget;
        
        //初始化参数
        var gridTmpTopbars = new Array(); 
        var gridTopbar = new Array();  
        var queryFormConfig = backpanel.queryFormConfig;
        var gridConfig = backpanel.gridConfig;
        var store = backpanel.ht_outputStore;
        var queryPanel = backpanel.ht_outputQueryPanel;
        var ctListeners = backpanel.ctListeners;
        
        var fieldNames = new Array();
        var gridFilters = new Ext.ux.grid.GridFilters({
            menuFilterText : ht.msg.base.grid.menuFilterText,
            encode: true, 
            local: true   
        });
        
        //定义表格的视图：
        //1.表格默认使用一般视图
        if(gridConfig.bufferView == null){
            gridConfig.bufferView = true;
        }
        
        //定义表格每页显示数据：
        if(gridConfig.pageSize == null || gridConfig.pageSize <= 0){
            gridConfig.pageSize = ht.config.params.PAGE_SIZE;
        }
        
        //定义表格选择方式
        var smGrid;
        if (gridConfig.isMultipleSelect !== false) {
             var position = 0;
             if (gridConfig.columns[0] instanceof Ext.grid.RowNumberer) {
                 position = 1;
             }
             smGrid = new Ext.grid.CheckboxSelectionModel({
                 getEditor : function() {
                     return null;
                 },
                 listeners : {
                    rowdeselect : function(sm,rowIndex, record) {
                        
                        //行不选中之前监听
                        if(ctListeners.beforeRowdeselect && !ctListeners.beforeRowdeselect(sm,rowIndex, record)){
                            return;
                        }
                        
                        if (backpanel.ht_outputGrid.getSelectionModel().hasSelection()) {
                            return;
                        }
                        
                        //禁用查询工具栏
                        var queryPanel = backpanel.ht_outputQueryPanel;
                        if (queryPanel && !queryPanel.hidden) {
                            queryPanel.enableBarBtn(false);
                        }
                        
                        //禁用表格工具栏
                        backpanel.enableBarBtn(false);
                    },
                    rowselect : function(sm,rowIndex, record) {
                        
                        //行选中之前监听
                        if(ctListeners.beforeRowselect && !ctListeners.beforeRowselect(sm,rowIndex, record)){
                            return;
                        }
                        
                        //启动查询工具栏
                        var queryPanel = backpanel.ht_outputQueryPanel;
                        if (queryPanel && !queryPanel.hidden) {
                            queryPanel.enableBarBtn(true);
                        }
                        
                        //启动表格工具栏
                        if (record.data['editorAction']) {
                            backpanel.enableBarBtn(false);
                        } else {
                            backpanel.enableBarBtn(true);
                        }
                    }
                 }
             });
             gridConfig.columns.splice(position, 0, smGrid);
        } else {
            smGrid = new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners : {
                    rowdeselect : function(sm,rowIndex, record) {
                        
                        //行不选中之前监听
                        if(ctListeners.beforeRowdeselect && !ctListeners.beforeRowdeselect(sm,rowIndex, record)){
                            return;
                        }
                        
                        //禁用查询工具栏
                        var queryPanel = backpanel.ht_outputQueryPanel;
                        if (queryPanel && !queryPanel.hidden) {
                            queryPanel.enableBarBtn(false);
                        }
                        
                        //禁用表格工具栏
                        backpanel.enableBarBtn(false);
                    },
                    rowselect : function(sm,rowIndex, record) {
                        
                        //行选中之前监听
                        if(ctListeners.beforeRowselect && !ctListeners.beforeRowselect(sm,rowIndex, record)){
                            return;
                        }
                        
                        //启动查询工具栏
                        var queryPanel = backpanel.ht_outputQueryPanel;
                        if (queryPanel && !queryPanel.hidden) {
                            queryPanel.enableBarBtn(true);
                        }
                        
                        //启动表格工具栏
                        if (record.data['editorAction']) {
                            backpanel.enableBarBtn(false);
                        } else {
                            backpanel.enableBarBtn(true);
                        }
                    }
                 }
            });
        }
        
        var view = {};
        var cm = new Ext.grid.ColumnModel({
            defaultSortable : gridConfig.defaultSortable !== false,
            columns : gridConfig.columns
        });
        if(gridConfig.bufferView){
            view = new Ext.ux.grid.BufferView({scrollDelay : 0});
        } else {
            view = new Ext.grid.GridView();
        }
        
        //增加、修改输入控件
        var gridItem, gridItemEditor;
        for (var i = 0; i < gridConfig.columns.length; i++) {
            gridItem = gridConfig.columns[i];
            if (gridItem instanceof Ext.grid.RowNumberer 
               || gridItem instanceof Ext.grid.CheckboxSelectionModel) {
                continue;
            }
            
            //增加过滤
            if (gridItem.enableFilter && gridConfig.enableFilter !== false) {
                var filterItem = gridItem.filter || {type: 'string'};
                filterItem.dataIndex = gridItem.dataIndex;
                
                if ('list' == filterItem.type 
                    && Ext.isEmpty(filterItem.options)
                    && !Ext.isEmpty(filterItem.rendererStore)) {
                        
                        
                    var displayField = filterItem.displayField || 'TEXT';
                    var options = new Array();
                    if (filterItem.rendererStore.getCount() > 0) {
                        var records = filterItem.rendererStore.getRange();
                        for (var i = 0; i < records.length; i++) {
                            options.push(records[i].get(displayField));
                        }
                        filterItem.options = options;
                        gridFilters.addFilter(filterItem);
                        
                    } else {
                        filterItem.rendererStore.on('load', function(){
                            var records = filterItem.rendererStore.getRange();
                            for (var i = 0; i < records.length; i++) {
                                options.push(records[i].get(displayField));
                            }
                            filterItem.options = options;
                            gridFilters.addFilter(filterItem);
                        });
                    }
                } else if ('date' == filterItem.type) {
                    filterItem.afterText = ht.msg.base.grid.dateFilter.afterText;
                    filterItem.beforeText = ht.msg.base.grid.dateFilter.beforeText;
                    filterItem.onText = ht.msg.base.grid.dateFilter.onText;
                    gridFilters.addFilter(filterItem);
                } else {
                    gridFilters.addFilter(filterItem);
                }
            }
            
            //行编辑
            gridItemEditor = gridItem.editor;
            if (!gridItemEditor) {
                continue;
            }
            
            gridItemEditor.msgTarget = msgTarget;
            if (gridItemEditor.allowBlank === false && !gridItemEditor.regex 
                  && gridItemEditor.autoTrim !== false) {
               gridItemEditor.regex = notBlackTextRegex;
               gridItemEditor.regexText = gridItemEditor.blankText;
            }
            
            fieldNames[fieldNames.length] = gridItemEditor.name;
            
            
        }
        backpanel.tempDataFieldNames = fieldNames;
        
        var bottomBar = backpanel.initGridBottombar(); 
        var gridEditor = backpanel.initGridEditor();
        
        //增加插件
        var plugins = new Array();
        plugins.push(gridEditor);
        plugins.push(gridFilters);
        if (gridConfig.plugins) {
            if (Ext.isArray(gridConfig.plugins)) {
                for (var i = 0; i < gridConfig.plugins.length; i++) {
                    plugins.push(gridConfig.plugins[i]);
                }
            } else {
                plugins.push(gridConfig.plugins);
            }
        }
        
        var gridParams = {
            region : 'center',
            stripeRows : true,
            columnLines : true,
            enableColumnHide : false,
            autoScroll : true,
            plugins : plugins,
            cm : cm,
            sm : smGrid,
            view: view,
            loadMask : true,
            store : store,
            border : false,
            bbar :bottomBar,
            listeners : {
                rowclick : function(agrid, rowIndex, e) {
                    backpanel.enableRelativePanelBarBtn(agrid.getSelectionModel().hasSelection());
                    if(ctListeners.clickGridRow) {
                        ctListeners.clickGridRow(agrid, rowIndex, e);
                    }
                },
                columnresize : function(columnIndex, newSize){
                    var editor = backpanel.ht_outputEditor;
                    if(editor.rendered){
                        var editField = grid.getColumnModel().columns[columnIndex];
                        editField.setWidth(newSize);
                        editor.doLayout();
                    }
                },
                rowdblclick : function(){
                    
                }
            }
        };
        
        var ctConfig = gridConfig.ctConfig || {};
        for (var field in ctConfig) {
           gridParams[field] = ctConfig[field];
        }
        var grid = new Ext.grid.GridPanel(gridParams);
        backpanel.ht_outputGrid = grid;
        
        //查询按钮放在表格上
        var topPostion = 0;
        if ((!queryPanel || queryPanel.hidden) && queryFormConfig.enableQueryButton !== false) {
            var position = queryFormConfig.queryPosition !== undefined ? queryFormConfig.queryPosition : topPostion;
            gridTmpTopbars[position] = {
                text: ht.msg.base.queryText,
                tagValue : queryFormConfig.tagValue,
                iconCls : 'query',
                enableByRowSelected : false,
                handler : function(){ queryPanel.queryFn()}
            };
        }
        
        //表格按钮-增加
        if (gridConfig.addBtn && gridConfig.addBtn.action) {
            topPostion++;
            var position = gridConfig.addBtn.position !== undefined ? gridConfig.addBtn.position : topPostion;
            gridTmpTopbars[position] = {
                text: ht.msg.base.addText,
                tagValue : gridConfig.addBtn.tagValue,
                iconCls : 'add',
                enableByRowSelected : false,
                handler : function(){ 
                    backpanel.addFn();
                }
            };
        }
        
        //表格按钮-删除
        if (gridConfig.deleteBtn && gridConfig.deleteBtn.action) {
            topPostion++;
            var position = gridConfig.deleteBtn.position !== undefined ? gridConfig.deleteBtn.position : topPostion;
            gridTmpTopbars[position] = {
                text: ht.msg.base.deleteText,
                tagValue : gridConfig.deleteBtn.tagValue,
                iconCls : 'delete',
                enableByRowSelected : true,
                handler : function(){
                    backpanel.deleteFn();
                }
            };
        }
        
        //工具栏增加额外控件
        topPostion++;
        var ctTopbarComponts = gridConfig.ctTopbarComponts
        if (ctTopbarComponts) {
            for(var i = 0; i < ctTopbarComponts.length; i++) {
                var position = ctTopbarComponts[i].position !== undefined ? ctTopbarComponts[i].position : topPostion;
                ctTopbarComponts[i].enableByRowSelected = ctTopbarComponts[i].enableByRowSelected !== false ? true : false;
                if ('->' == ctTopbarComponts[i].text) {
                     ctTopbarComponts[i] = '->';
                }
                while(gridTmpTopbars[position]) {
                    position += 1;
                }
                gridTmpTopbars[position] = ctTopbarComponts[i];
            }
        }
        
        for(var i = 0; i < gridTmpTopbars.length; i++) {
            if (gridTmpTopbars[i]) {
                if ((gridTmpTopbars[i].ingoreSeparator || gridTmpTopbars[i] == '->')
                    && (gridTopbar[gridTopbar.length - 1] != '->')) {
                    gridTopbar[gridTopbar.length - 1] = gridTmpTopbars[i];
                } else {
                    gridTopbar[gridTopbar.length] = gridTmpTopbars[i];
                }
                if (gridTmpTopbars.length > (i + 1) && (gridTopbar[gridTopbar.length - 1] != '->')) {
                    gridTopbar[gridTopbar.length] = '-';
                }
            }
        }
        
        if (gridTopbar.length == 0) {
            gridTopbar = null;
        }
        
        var gridPanel = new Ext.Panel({
            region : 'center',
            title : gridConfig.title,
            iconCls : 'grid',
            layout : 'border',
            style : gridConfig.panelStyle,
            border : gridConfig.panelBorder === false ? false : true,
            tbar :  gridTopbar,
            items : [grid]
        });
        
        return gridPanel;
        
    },
    
    /**
     * @return {}
     */
    initPropertyPanel : function() {
        
        var backpanel= this;
        var propertyFormConfig = backpanel.propertyFormConfig;
        var ctListeners = backpanel.ctListeners;
        
        if (!propertyFormConfig.enablePropertyForm) {
            return null;
        }
        
        var propertyPanel = new ht.base.PropertyPanel({
            border : false,
            formConfig : {
                query : {
                    enableQueryButton : false
                },
                useTo : 'property', 
                items : propertyFormConfig.items
            }
        });
        
        backpanel.ht_outputPropertyFormPanel = propertyPanel.ht_outputFormPanel;
        backpanel.tempDataPropertyPanel = propertyPanel;
        
        var tabPanel = new Ext.TabPanel({
            xtype : 'tabpanel',
            activeTab: 0,
            width : propertyFormConfig.width || ht.config.layout.propertyPanelWidth,
            region : 'east',
            collapsed : propertyFormConfig.defaultCollapsed !== false,
            collapsible : true,
//            collapseMode : 'mini',
            border : true,
            split : propertyFormConfig.split !== false,
            items: [{
                title : propertyFormConfig.title || ht.msg.base.otherPropertyTitle,
                layout : 'fit',
                border : false,
                items : [propertyPanel]
            }],
            listeners : {
                resize : function() {
                    propertyPanel.doLayout();
                }
            }
        });
        tabPanel.addClass(ht.config.tab.RM_BORDER_LEFT);
        tabPanel.addClass(ht.config.tab.RM_BORDER_RIGHT);
        tabPanel.addClass(ht.config.tab.RM_BORDER_TOP);
        tabPanel.addClass(ht.config.tab.RM_BORDER_BOTTOM);
        
        return tabPanel;
       
    },
    
    initComponent : function() {
        
        //初始化参数
        var backpanel = this;
        var readOnlyColor = ht.config.style.COLOR_READONLY;// 只读输入组件的样式 
        var queryFormConfig = backpanel.queryFormConfig;
        var gridConfig = backpanel.gridConfig;
        if (!backpanel.ctListeners) {
            backpanel.ctListeners = {};
        }
        
        //属性兼容
        if (gridConfig.table && !queryFormConfig.outputTable) {
            queryFormConfig.outputTable = gridConfig.table;
        }
        
        backpanel.initConfig.tagValues = backpanel.initConfig.tagValues || ht.pub.global.CURRENT_USER_ACTIONS;
        
        //定义数据源
        var store;
        if (!backpanel.ht_outputStore) {
            if (gridConfig.isPageAction !== false) {
                if (gridConfig.isLocalPageAction) {
                    store = new Ext.ux.data.PagingArrayStore({//本地数据分页
                        autoDestroy : true,
                        baseParams : {
                            limit : gridConfig.pageSize ? gridConfig.pageSize : ht.config.params.PAGE_SIZE
                        },
                        fields : gridConfig.storeFields
                    });
                } else {
                    store = new Ext.data.JsonStore({
                        url : 'json',
                        autoDestroy : true,
                        root : 'root.' + queryFormConfig.outputTable + '.rs.rs',
                        totalProperty : 'root.' + queryFormConfig.outputTable + '.rs.pager.totalRows',
                        baseParams : {
                            action :  queryFormConfig.action,
                            limit : gridConfig.pageSize ? gridConfig.pageSize : ht.config.params.PAGE_SIZE
                        },
                        fields : gridConfig.storeFields
                    });
                }
            } else {
                store = new Ext.data.JsonStore({
                    url : 'json',
                    autoDestroy : true,
                    root : 'root.' + queryFormConfig.outputTable + '.rs',
                    baseParams : {
                        action :  queryFormConfig.action
                    },
                    fields : gridConfig.storeFields
                });
            }
            backpanel.ht_outputStore = store;
        }
        store = backpanel.ht_outputStore;
        
        //查询面板
        var queryPanel = backpanel.initQueryPanel(backpanel);
        
        //表格面板
        var gridPanel = backpanel.initGridPanel(backpanel);
        
        //扩展属性面板
        var propertyPanel = backpanel.initPropertyPanel(backpanel);
        
        backpanel.items = [];
        if (queryPanel) {
            backpanel.items.push(queryPanel);
        }
        
        if (propertyPanel) {
            backpanel.items.push({
                region : 'center',
                xtype : 'panel',
                border : false,
                layout : 'border',
                items : [gridPanel, propertyPanel]
            });
            
            backpanel.ht_outputGrid.on('rowclick', function() {
                if (1 != backpanel.ht_outputGrid.getSelectionModel().getCount()) {
                    backpanel.ht_outputPropertyFormPanel.getForm().reset();
                    return;
                }
                
                var selectData = backpanel.ht_outputGrid.getSelectionModel().getSelected().data;
                var params = {};
                for (var field in selectData) {
                    params[field] = selectData[field];
                }
                
                if (backpanel.ctListeners.afterGetGridDataToPropery && !backpanel.ctListeners.afterGetGridDataToPropery(params)) {
                    return;
                }
                
                if (!backpanel.propertyFormConfig.action) {
                    if (backpanel.ctListeners.beforeFillPropery && !backpanel.ctListeners.beforeFillPropery(params)) {
                        return;
                    }
                    
                    backpanel.ht_outputPropertyFormPanel.getForm().setValues(params);
//                    backpanel.ht_outputPropertyFormPanel.ownerCt.ownerCt.ownerCt.setActiveTab(backpanel.ht_outputPropertyFormPanel.ownerCt.ownerCt);
                } else {
                    
                    //增加后台store
                    if (!backpanel.innerProperyStore) {
                        var properyStoreFields = [];
                        var formItem;
                        
                        for (var i = 0; i < backpanel.propertyFormConfig.items.length; i++) {
                            formItem = backpanel.propertyFormConfig.items[i];
                            
                            if (formItem.xtype == 'compositefield') {
                                for (var kk = 0; kk < formItem.items.length; kk++) {
                                    if (formItem.items[kk].name) {
                                        properyStoreFields.push(formItem.items[kk].name);
                                    }
                                }
                            } else {
                                if (formItem.name) {
                                    properyStoreFields.push(formItem.name);
                                }  
                            }
                        }
                        
                        backpanel.innerProperyStore = new Ext.data.JsonStore({
                            url : 'json',
                            autoDestroy : true,
                            root : 'root.' + backpanel.propertyFormConfig.outputTable + '.rs',
                            baseParams : {
                                action :  backpanel.propertyFormConfig.action
                            },
                            fields : properyStoreFields
                        });
                    }
                    
                    //查询参数
                    for (var field in params) {
                        backpanel.innerProperyStore.baseParams[field] = params[field];
                    }
                    
                    backpanel.innerProperyStore.load({
                        callback : function() {
                            if (backpanel.innerProperyStore.getCount() == 0) {
                                return;
                            }
                        
                            var data = backpanel.innerProperyStore.getAt(0).data;
                            if (backpanel.ctListeners.beforeFillPropery && !backpanel.ctListeners.beforeFillPropery(data)) {
                                return;
                            }
                            
                            backpanel.ht_outputPropertyFormPanel.getForm().setValues(data);
//                            backpanel.ht_outputPropertyFormPanel.ownerCt.ownerCt.ownerCt.setActiveTab(backpanel.ht_outputPropertyFormPanel.ownerCt.ownerCt);
                        }
                    });
                }
                
            });
            
            backpanel.ht_outputStore.on('load', function(){
                backpanel.ht_outputPropertyFormPanel.getForm().reset();
            });
            
        } else {
            backpanel.items.push(gridPanel);
        }
        
        //增加行-单元格数据属性
        backpanel.ht_outputGrid.on('rowclick', function() {
            
            //重置所有属性
            if (1 != backpanel.ht_outputGrid.getSelectionModel().getCount()) {
                
                var columns = backpanel.gridConfig.columns;
                for (var i = 0; i < columns.length; i++) {
                    if (!columns[i].fieldConfig || !columns[i].fieldConfig.enableRowProperty) {
                        continue;
                    }
                    
                    //获取显示的TAB面板，默认行属性TAB
                    var tabPanel =  null; 
                    if (columns[i].fieldConfig.getRenderTo) {
                        tabPanel = columns[i].fieldConfig.getRenderTo();
                        tabPanel = tabPanel || backpanel.ht_outputPropertyFormPanel.ownerCt.ownerCt.ownerCt; 
                    } else {
                        tabPanel = backpanel.ht_outputPropertyFormPanel.ownerCt.ownerCt.ownerCt; //
                    }
                    
                    //获取FormPanel，并重置值
                    var formPanels = tabPanel.findByType('form');
                    for (var k = 0; k < formPanels.length; k++) {
                        var values = formPanels[k].getForm().getValues();
                        for (var field in values) {
                            values[field] = '';
                        }
                        formPanels[k].getForm().setValues(values);
                    }
                }
               
                return;
            }
            
            var selectData = backpanel.ht_outputGrid.getSelectionModel().getSelected().data;
            var params = {};
            for (var field in selectData) {
                params[field] = selectData[field];
            }
            
            if (backpanel.ctListeners.beforeFillRowProperties) {
                backpanel.ctListeners.beforeFillRowProperties(params);
            }
            
            var columns = backpanel.gridConfig.columns;
            for (var i = 0; i < columns.length; i++) {
                
                if (!columns[i].fieldConfig || !columns[i].fieldConfig.enableRowProperty) {
                    continue;
                }
                
                //获取显示的TAB面板，默认行属性TAB
                var tabPanel =  null; 
                if (columns[i].fieldConfig.getRenderTo) {
                    tabPanel = columns[i].fieldConfig.getRenderTo();
                    tabPanel = tabPanel || backpanel.ht_outputPropertyFormPanel.ownerCt.ownerCt.ownerCt; 
                } else {
                    tabPanel = backpanel.ht_outputPropertyFormPanel.ownerCt.ownerCt.ownerCt; //
                }
                
                //获取标签面板，并加载数据
                var tabItemPanel = tabPanel.find('title', columns[i].fieldConfig.title);
                tabItemPanel = tabItemPanel.length > 0 ? tabItemPanel[0] : null;
                if (tabItemPanel) {
                    var panel = tabItemPanel.getComponent(0);
                    if (panel.loadData) {
                        panel.loadData(params)
                    }
                } else {
                    var title = columns[i].fieldConfig.title;
                    Ext.Ajax.request({
                        method : 'GET',
                        url : columns[i].fieldConfig.js,
                        params : {title : title},
                        scope : this,
                        success : function(response, options) {
                            var title = options['params']['title'];
                            var panel = eval(response.responseText);
                            tabPanel.add({
                                title : title,
                                layout : 'fit',
                                items : [panel]
                            });
                            
                            if (panel.loadData) {
                                panel.loadData(params)
                            }
                        }
                    });
                }
            }
        });
        
        ht.base.RowEditorPanel.superclass.initComponent.call(backpanel);
        backpanel.enableBarBtnByTag(backpanel);
        backpanel.enableBarBtn(false);
        backpanel.enableBarBtnByHasdata();
        
        if (queryFormConfig.autoQuery) {
            //自动加载方法
            var autoQueryFn = function() {
                var autoQueryParam = queryFormConfig.autoQueryParam;
                if (autoQueryParam){
                    for(var i = 0; i < autoQueryParam.length; i++){
                        store.baseParams[autoQueryParam[i].fieldName] = autoQueryParam[i].fieldValue;
                    }
                }
                store.load({
                    callback : function() {
                        
                        //格式化时间
//                        ht.pub.date.formatDateFields(backpanel.gridConfig.storeFields, backpanel.ht_outputStore);
                        
                        if(backpanel.ctListeners.afterAutoQuery) {
                            backpanel.ctListeners.afterAutoQuery();
                        }
                    }
                });
            }
            
            //自动加载前提条件
            if (queryFormConfig.autoQueryPreCondition) {
                var autoQueryInterval = window.setInterval(function(){
                    var canAutoLoad = true;
                    for (var field in queryFormConfig.autoQueryPreCondition) {
                        if(!queryFormConfig.autoQueryPreCondition[field]) {
                            canAutoLoad = false;
                            break;
                        }
                    }
                    
                    if (canAutoLoad) {
                        window.clearInterval(autoQueryInterval);
                        autoQueryFn();
                    }
                }, 10);
            } else {
                autoQueryFn();
            }
        }
        
        
        backpanel.on('beforedestroy', function(){
            backpanel.removeAll(true);
        })
        
        
        store.on('load', function(st, records, opt){
            backpanel.enableBarBtn(false);
            backpanel.enableBarBtnByHasdata();
            backpanel.callbackStore();
        });
        
    }
});

Ext.reg('roweditorpanel', ht.base.RowEditorPanel);

