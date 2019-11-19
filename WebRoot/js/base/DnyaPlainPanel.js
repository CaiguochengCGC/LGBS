Ext.namespace('ht.base');


/**
 * @class ht.base.DnyaPlainPanel
 * @extends Ext.Panel
 * <p>普通的增删改查面板，包含一个查询面板，一个表格面板，并提供标准增删改查功能</p>
 * <li>1.表格可使用冻结列视图</li>
 * <li>2.表格可使用缓冲视图(可提高前台表格数据加载速度)</li>
 * <li>3.表格的冻结列视图和缓冲视图无法同时使用</li>
 */

ht.base.DnyaPlainPanel = Ext.extend(Ext.Panel, {
    layout : 'border',
    border : false,
    panelCode : '',
    
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
    ht_outputGrid : null,
    
    /**
     * @cfg(Ext.form.FormPanel) ht_outputQueryForm
     * @type Ext.form.FormPanel
     * (输出) 查询面板form，如果没有查询则为null
     */
    ht_outputQueryForm : null, 
    
    /**
     * @cfg(ht.base.PropertyPanel) ht_outputQueryPanel
     * @type ht.base.PropertyPanel 
     * 临时数据，用于封装方法之间的传递，不可使用
     */
    ht_outputQueryPanel : null,
    
    /**
     * @cfg(Ext.Window) ht_outputAddWindow
     * @type Ext.Window
     * (输出)增加功能用的window
     */
    ht_outputAddWindow : null, 
    
    /**
     * @cfg(Ext.form.FormPanel) ht_outputAddFormPanel
     * @type Ext.form.FormPanel 
     * (输出)增加功能用的form
     */
    ht_outputAddFormPanel : null,
    
    /**
     * @cfg(Ext.Window) ht_outputUpdateWindow
     * @type Ext.Window 
     * (输出)修改功能用的window
     */
    ht_outputUpdateWindow : null,
    
    /**
     * @cfg(Ext.form.FormPanel) ht_outputUpdateFormPanel
     * @type Ext.form.FormPanel 
     * (输出)修改功能用的form
     */
    ht_outputUpdateFormPanel : null,
    
    /**
     * @cfg(Ext.form.FormPanel) ht_outputPropertyFormPanel
     * @type Ext.form.FormPanel 
     * (输出)修改功能用的form
     */
    ht_outputPropertyFormPanel : null,
    
    /**
     * 数据配置
     * {DATA_DICT : STORE}
     */
    localData : {}, //store
    
    //{DATA_DICT : function(){}}
    localFn : {}, //return store
    
    //{DATA_DICT : STORE}
    globalData : {}, //store
    
    //{DATA_DICT : function(){}}
    globalFn : {}, //return store
    
    /**
     * @cfg(json) initConfig
     * @type json
     * 初始化配置
     */
    initConfig : {
        
        /**
         * @cfg(Array) tagValues
         * @type Array
         * 用户所有权限
         */
        tagValues : []
    },
    
    /**
     * @cfg {Object} queryFormConfig
     * 查询面板配置
     */
    queryFormConfig : {
        
        /**
         * @cfg(Boolean) enableQueryButton
         * @type Boolean
         * 是否使用查询按钮
         */
        enableQueryButton : true, 
        
        /**
         * @cfg(Array) items
         * @type Array
         * 查询面板内容
         */
        items : [{
            
            /**
            * @cfg(Boolean) autoTrim
            * @type Boolean
            * 提交前是否去左右空格
            */
            autoTrim : true
        }], 
        
        /**
         * @cfg(String) useTo
         * @type String
         * 面板用途，query :查询,win-query：弹出窗口查询
         */
        useTo : 'query',
        
        /**
         * @cfg(String) action
         * @type String
         * 查询action
         */
        action : '', 
        
        /**
         * @cfg(String) outputTable
         * @type String
         * 查询store需要的output
         */
        outputTable : '', 
        
        /** 
         * @cfg(Boolean) autoQuery
         * @type Boolean
         * 是否自动查询
         */
        autoQuery : false, 
        
        /**
         * @cfg(Array) autoQueryParam
         * @type Array
         * 自动查询参数
         */
        autoQueryParam : [{fieldName :'', fieldValue : ''}], 
        
        /**
         * 自动加载的前提条件
         * @type 
         */
        autoQueryPreCondition : {},
        
        /**
         * @cfg(String) tagValue
         * @type String
         * 查询功能权限
         */
        tagValue : '', 
        
        /**
         * @cfg(Number) queryHeight
         * @type Number
         * 查询面板的高度，当值大于自动高度时取该值
         */
        queryHeight : 0, 
        
        queryDiffHeight : 0,
        
        /**
         * @cfg(Number) queryPosition
         * @type Number
         * 查询按钮的位置
         */
        queryPosition : 1, 
        
        /**
         * @cfg(Number) resetPosition
         * @type Number
         * 重置按钮的位置
         */
        resetPosition : 0, 
        
        /**
         * @cfg(Arrray) ctTopbarComponts
         * @type Arrray
         * 查询顶部工具栏额外控件
         */
        ctTopbarComponts : [{

            /**
             * @cfg(String) tagValue
             * @type String
             * 按钮功能权限
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
             * 按钮位置,
             */
            position : 0, 
            
            /**
             * @cfg(Boolean) enableByRowSelected
             * @type Boolean
             * 是否当选中表格行时才可使用
             */
            enableByRowSelected : true,
            
            enableByHasData : false
        }]
    },
    
    /**
     * @cfg {json} gridConfig
     * 数据表格配置项
     */
    gridConfig : {
        
        style : '',
        
        /**
         * @cfg(Array) storeFields
         * @type Array
         * store的fields，内容可以包含json
         */
        storeFields : [], 
        
        /**
         * @cfg(Array) deletedKeyFields
         * @type Array
         * 删除的主键，内容只能是字符串
         */
        deletedKeyFields : [], 
        
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
        columns : [], 
        
        /**
         * @cfg(Boolean) lockingView
         * @type Boolean
         * 是否冻结表格
         */
        lockingView: false, 
        
        /**
         * @cfg(Boolean) bufferView
         * @type Boolean
         * 是否缓冲表格
         */
        bufferView: true, 
        
        /**
         * @cfg(Boolean) defaultSortable
         * @type Boolean
         * 是否支持排序
         */
        defaultSortable : true, 
        
        /**
         * 是否支持列过滤
         * @type Boolean
         */
        enableFilter : true,
        
        /**
         * @cfg(Number) pageSize
         * @type Number
         * 每页显示行数
         */
        pageSize: 0, 
        
        /**
         * @cfg(Boolean) enablePageSize
         * @type Boolean
         * 是否可以修改调整行数
         */
        enablePageSize : true, 
        
        /**
         * 是否首列加行号
         * @type Boolean
         */
        enableRowNumberer : true,
        
        /**
         * @cfg(Boolean) isPageAction
         * @type Boolean
         * 是否支持分页
         */
        isPageAction : true, 
        
        /**
         * @cfg(Boolean) isPageAction
         * @type Boolean
         * 表格面板边框
         */
        panelBorder : true, 
        
        panelStyle : '',
        
        /**
         * @cfg(Boolean) isMultipleSelect
         * @type Boolean
         * 是否支持行多选
         */
        isMultipleSelect : true,  
        
        isLocalPageAction : false, //是否本地数据分页
        
        /**
         * @cfg(String) title
         * @type String
         * 表格面板名称
         */
        title : '',  
        
        plugins : null,
        
        table : '', //该属性不推荐使用，推荐使用queryFormConfig.outputTable
        
        ctConfig : {}, //其他表格配置
        
        
        /**
         * @cfg(json) addBtn
         * @type json
         * 新增按钮
         */
        addBtn : {
            
            /**
             * @cfg(String) action
             * @type String
             * 新增action
             */
            action : '', 
            
            /**
             * @cfg(String) outputTable
             * @type String
             * 新增数据返回的output
             */
            outputTable : '', 
            
            /**
             * @cfg(String) tagValue
             * @type String
             * 新增按钮权限
             */
            tagValue : '', 
            
            /**
             * @cfg(Number) position
             * @type Number
             * 新增按钮位置
             */
            position : 1,
            
            /**
             * 自动创建增加窗口
             * @type Boolean
             */
            autoCreateWindow : true
        },
        
        /**
         * @cfg(json) updateBtn
         * 修改按钮
         */
        updateBtn : {
            
            /**
             * @cfg(String) action
             * @type String
             * 修改action
             */
            action : '',
            
            /**
             * @cfg(String) outputTable
             * @type String
             * 修改数据返回的output
             */
            outputTable : '', 
            
            /**
             * @cfg(String) tagValue
             * @type String
             * 修改按钮权限
             */
            tagValue : '', 
            
            /**
             * @cfg(Number) position
             * @type Number
             * 修改按钮位置
             */
            position : 2,
            
             /**
             * 自动创建修改窗口
             * @type Boolean
             */
            autoCreateWindow : true
        },
        
        /**
         * @cfg(json) deleteBtn
         * 删除按钮
         */
        deleteBtn : {
            
            /**
             * @cfg(String) action
             * @type String
             * 删除action
             */
            action : '',
            
            /**
             * @cfg(String) tagValue
             * @type String
             * 删除按钮权限
             */
            tagValue : '',
            
            /**
             * @cfg(Number) position
             * @type Number
             * 删除按钮位置
             */
            position : 3
        },
        
        
        /**
         * @cfg(Array) ctTopbarComponts
         * 表格额外按钮
         */
        ctTopbarComponts : [{

            /**
             * @cfg(String) tagValue
             * @type String
             * 按钮权限
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
             * 按钮位置,
             */
            position : 3,
            
            /**
             * @cfg(Boolean) enableByRowSelected
             * @type Boolean
             * 是否当选中表格行时才可使用
             */
            enableByRowSelected : true,
            
            enableByHasData : false
        }],
        
        /**
         * @cfg {json} excelConfig
         * excel操作配置项
         */
        excelConfig : {
            
            /**
             * @cfg(Boolean) enableExport
             * @type Boolean
             * 是否启用导出excel
             */
            enableExport : false, 
            
            ingoreCols : [],
            
            /**
             * @cfg(String) tagValue
             * @type String
             * 导出excel权限
             */
            tagValue : '', 
            
            /**
             * @cfg(String) excelName
             * @type String
             * 导出excel的名称
             */
            excelName : '', 
            
            /**
             * @cfg(Number) rowNumPosition
             * @type Number
             * 每页行数输入框位置
             */
            rowNumPosition : 2, 
            
            /**
             * @cfg(Number) excelPosition
             * @type Number
             * 导出excel输入框位置
             */
            excelPosition : 3 
        },
        
        /**
         * @cfg {json} printConfig
         * 打印表格配置
         */
        printConfig : {
            
            /**
             * @cfg(Boolean) enablePrint
             * @type Boolean
             * 是否启用打印
             */
            enablePrint : false, 
            
            /**
             * @cfg(Boolean) enablePrintView
             * @type Boolean
             * 是否启用打印预览
             */
            enablePrintView : false, 
            
            /**
             * @cfg(String) tagValue
             * @type String
             * 打印、打印预览权限
             */
            tagValue : '', 
            
            /**
             * @cfg(Number) rowNumPosition
             * @type Number
             * 每页行数输入框位置（当enableExport=false）
             */
            rowNumPosition : 4, 
            
            /**
             * @cfg(Number) printPosition
             * @type Number
             * 打印位置
             */
            printPosition : 5 
        },
        
        
        
        /**
         * @cfg(Array) ctBottombarComponts
         * @type Array
         * 表格额外底部控件
         */
        ctBottombarComponts : [{

            /**
             * @cfg(String) tagValue
             * @type String
             * 控件权限
             */
            tagValue : '', 
            
            /**
             * @cfg(Boolean) ingoreSeparator
             * @type Boolean
             * 是否忽略左侧分割符
             */
            ingoreSeparator : false, 
            
            enableByHasData : false,
            
            enableByRowSelected : true,
            
            /**
             * @cfg(Number) position
             * @type Number
             * 按钮位置
             */
            position : 0 
        }]
    },

    /**
     * 行扩展属性面板
     * @type 
     */
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
     * @cfg {json} editFormConfig
     * 编辑面板配置项
     */
    editFormConfig : {
        
        /**
         * @cfg(Array) items
         * @type Array
         */
        items : [{ 
            
            /**
             * @cfg(Number) columnWidth
             * @type Number
             * 占一行的百分比，默认为useTo的默认值,
             */
            columnWidth : 0.25, 
            
            /**
             * @cfg(Boolean) insertable
             * @type Boolean
             * 增加时是否可以编辑
             */
            insertable : true, 
            
            /**
             * @cfg(Boolean) updateable
             * @type Boolean
             * 修改时是否可以编辑
             */
            updateable : true,
            
            /**
             * @cfg(Boolean) autoTrim
             * @type Boolean
             * 提交前是否去左右空格
             */
            autoTrim : true
            
        },'/'], //'/'表示换行
        
        
        /**
         * @cfg(json) addPanel
         * @type json
         * 增加面板
         */
        addPanel : {
            
            windowTitle : '',
            
            panelHeight : 0,
            
            panelDiffHeight : 0,
            
            /**
             * @cfg(Array) items
             * @type Array
             * 优先级比editFormConfig.items高
             */
            items : [{ 
                
                /**
                 * @cfg(Number) columnWidth
                 * @type Number
                 * 占一行的百分比，默认为useTo的默认值,
                 */
                columnWidth : 0.25, 
                
                /**
                 * @cfg(Boolean) insertable
                 * @type Boolean
                 * 增加时是否可以编辑
                 */
                insertable : true, 
                
                /**
                 * @cfg(Boolean) updateable
                 * @type Boolean
                 * 修改时是否可以编辑
                 */
                updateable : true,
                
                /**
                 * @cfg(Boolean) autoTrim
                 * @type Boolean
                 * 提交前是否去左右空格
                 */
                autoTrim : true
            },'/'], //'/'表示换行
            
            /**
             * @cfg(Array) ctTopbarComponts
             * @type Array
             * 额外顶部工具栏控件
             */
            ctTopbarComponts : [{ 
            
                /**
                 * @cfg(String) tagValue
                 * @type String
                 * 功能权限
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
                 * 按钮位置
                 */
                position : 0, 
                
                /**
                 * @cfg(Boolean) enableByRowSelected
                 * @type Boolean
                 * 是否当选中表格行时才可使用
                 */
                enableByRowSelected : true 
            }],
            
            /**
             * @cfg(Array) ctButtons
             * @type Array
             * 额外按钮
             */
            ctButtons : [{ 
            
                /**
                 * @cfg(String) tagValue
                 * @type String
                 * 功能权限
                 */
                tagValue : '',  
                
                /**
                 * @cfg(Number) position
                 * @type Number
                 * 按钮位置
                 */
                position : 0 
            }],
            
            /**
             * @cfg(Boolean) enableSubmitButton
             * @type Boolean
             * 是否使用确定按钮
             */
            enableSubmitButton : true, 
            
            /**
             * @cfg(String) tagValue
             * @type String
             * 确定功能权限
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
             * 取消按钮的位置，默认为第二位,
             */
            cancelPosition : 1 
        },
        
        
        /**
         * @cfg(json) updatePanel
         * @type json
         * 修改面板
         */
        updatePanel : {
            
            windowTitle : '',
            
            panelHeight : 0,
            
            panelDiffHeight : 0,
            
            /**
             * @cfg(Array) items
             * @type Array
             * 优先级比editFormConfig.items高
             */
            items : [{ 
                
                /**
                 * @cfg(Number) columnWidth
                 * @type Number
                 * 占一行的百分比，默认为useTo的默认值,
                 */
                columnWidth : 0.25, 
                
                /**
                 * @cfg(Boolean) insertable
                 * @type Boolean
                 * 增加时是否可以编辑
                 */
                insertable : true, 
                
                /**
                 * @cfg(Boolean) updateable
                 * @type Boolean
                 * 修改时是否可以编辑
                 */
                updateable : true,
                
                /**
                 * @cfg(Boolean) autoTrim
                 * @type Boolean
                 * 提交前是否去左右空格
                 */
                autoTrim : true
            },'/'], //'/'表示换行
            
            /**
             * @cfg(Array) ctTopbarComponts
             * @type Array
             * 额外顶部工具栏控件
             */
            ctTopbarComponts : [{ 
            
                /**
                 * @cfg(String) tagValue
                 * @type String
                 * 功能权限
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
                 * 按钮位置
                 */
                position : 0, 
                
                /**
                 * @cfg(Boolean) enableByRowSelected
                 * @type Boolean
                 * 是否当选中表格行时才可使用
                 */
                enableByRowSelected : true 
            }],
            
            /**
             * @cfg(Array) ctButtons
             * @type Array
             * 额外按钮
             */
            ctButtons : [{ 
            
                /**
                 * @cfg(String) tagValue
                 * @type String
                 * 功能权限
                 */
                tagValue : '',  
                
                /**
                 * @cfg(Number) position
                 * @type Number
                 * 按钮位置
                 */
                position : 0 
            }],
            
            /**
             * @cfg(Boolean) enableSubmitButton
             * @type Boolean
             * 是否使用确定按钮
             */
            enableSubmitButton : true, 
            
            /**
             * @cfg(String) tagValue
             * @type String
             * 确定功能权限
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
        }
    },
    
    /**
     * 监听事件
     * @type 
     */
    ctListeners : {
        
        /**
         * 查询前监听，如果返回false，则不继续执行
         * @param {json} values form键值对
         * @return {Boolean} 
         */
        beforeQuery : function(values){return true},
        
        /**
         * 查询成功后监听
         */
        afterQuery : function(){}, 
        
        afterReset : function(){}, 
        
        /**
         * 增加前监听，如果返回false，则不继续执行
         * @param {json} values form键值对
         * @param {function} formSubmit 提交方法（已封装）
         * @return {Boolean}
         */
        beforeAdd : function(values, formSubmit){return true}, 
        
        /**
         * 增加成功后监听，如果返回false，则不继续执行
         * @param {json} values 提交的action对象，后台返回值
         * @return {Boolean}
         */
        afterAdd : function(values){return true}, 
        
        /**
         * 修改前监听，如果返回false，则不继续执行
         * @param {} values
         * @param {} formSubmit
         * @return {Boolean}
         */
        beforeUpdate : function(values, formSubmit){return true},
        
        /**
         * 修改成功后监听，如果返回false，则不继续执行
         * @param {} values 提交的action对象，后台返回值
         * @return {Boolean}
         */
        afterUpdate : function(values){return true}, 
        
        /**
         * 删除前监听，如果返回false，则不继续执行
         * @param {} values
         * @return {Boolean}
         */
        beforeDelete : function(values){return true},
        
        /**
         * 删除成功后监听
         * @param {} values  提交的action对象，后台返回值
         */
        afterDelete : function(values){}, 
        
        /**
         * 单击表格
         * @param {Ext.grid.GridPanel} agrid 事件表格
         * @param {Number} rowIndex 事件行号
         * @param {Ext.EventObject} e 事件
         */
        clickGridRow : function(agrid, rowIndex, e){}, 
        
        /**
         * 双击表格
         * @param {Ext.grid.GridPanel} agrid 事件表格
         * @param {Number} rowIndex 事件行号
         * @param {Ext.EventObject} e 事件
         */
        dbclickGridRow : function(agrid, rowIndex, e){},
        
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
        
        /**
         * 增加window初始化后监听
         */
        afterLayoutAddWin : function(){},
        
        /**
         * 修改window初始化后监听
         */
        afterLayoutUpdateWin : function(){}, 
        
        /**
         * 自动查询后监听
         */
        afterAutoQuery : function(){}, 
        
        /**
         * 增加window显示前监听，如果返回false，则不继续执行
         * @return {Boolean}
         */
        beforeShowAddWin : function(){return true}, 
        
        /**
         * 修改window显示前监听，如果返回false，则不继续执行
         * @return {Boolean}
         */
        beforeShowUpdateWin : function(){return true},
        
        /**
         * 增加window显示后监听
         */
        afterShowAddWin : function(){},
        
        /**
         * 修改window显示后监听
         */
        afterShowUpdateWin : function(){},
        
        /**
         * 增加window隐藏前监听，如果返回false，则不继续执行
         * @return {Boolean}
         */
        beforeHideAddWin : function(){return true},
        
        /**
         * 修改window隐藏前监听，如果返回false，则不继续执行
         * @return {Boolean}
         */
        beforeHideUpdateWin : function(){return true},
        
        /**
         * 增加window隐藏后监听
         */
        afterHideAddWin : function(){},
        
        /**
         * 修改window隐藏后监听
         */
        afterHideUpdateWin : function(){},
        
        /**
         * 选中、未选中行的关联控件监听
         * @param {Boolean} enable
         */
        enableBarBtn : function(enable) {},
        
        /**
         * 点击增加按钮（马上处理）
         * @return {Boolean}
         */
        clickAddBtn : function() {return true},
        
        /**
         * 点击修改按钮（马上处理）
         * @return {Boolean}
         */
        clickUpdateBtn : function() {return true},
        
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
    
    /**
     * 查询后调整数据
     * private
     */
    callbackStore : function(backpanel) {
        
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
     * 根据表格行是否选择，启用、禁用表格顶部按钮
     * @param {Boolean} enable 表格行是否选择
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
    
    /**
     * 根据权限值(tagValue)禁用按钮
     */
    enableBarBtnByTag : function() {
        
        var backpanel = this;
        var tagValues = backpanel.initConfig.tagValues || [];
        var compents = new Array();
        if (tagValues.length == 0) {
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
    
    /**
     * 动态列、动态查询时获取显示store
     * @return {Ext.data.Store}
     */
    getRendererStore : function(dataSrc, dataDict, isInit) {
        var store;
        var backpanel = this;
        var queryFormConfig = backpanel.queryFormConfig;
        
        //获取store
        if (2 == dataSrc) { //本地数据
            backpanel.localData = backpanel.localData || {};
            store = backpanel.localData[dataDict];
        } else if (3 == dataSrc) { //本地函数
            backpanel.localData = backpanel.localFn || {};
            store = backpanel.localFn[dataDict]();
        } else if (4 == dataSrc) { //全局数据
            backpanel.localData = backpanel.globalData || {};
            store = backpanel.globalData[dataDict];
        } else if (5 == dataSrc) { //全局函数
            backpanel.localData = backpanel.globalFn || {};
            store = backpanel.globalFn[dataDict]();
        } else { //远程查询(字典)
            backpanel.remoteData = backpanel.remoteData || {};
            store = backpanel.remoteData[dataDict];
            
            if (!store) {
                store = new Ext.data.JsonStore({
                    url : 'json',
                    root : 'root.PUB_DATA_DICT.rs',
                    baseParams : {
                        action : 'GET_CODE_VALUE_ACTION',
                        CODE_TYPE : dataDict
                    },
                    fields : ['VALUE', 'TEXT']
                });
                backpanel.remoteData[dataDict] = store;
            }
        }
        
        //加载数据
        if ('0' !== isInit && store) {
            queryFormConfig.autoQueryPreCondition[dataDict] = false;
            if (store.getCount() > 0) {
                queryFormConfig.autoQueryPreCondition[dataDict] = true;
            } else {
                store.load({
                    callback : function() {
                        queryFormConfig.autoQueryPreCondition[dataDict] = true;
                    }
                })
            }
        }

        return store;
    
    },
    
    /**
     * private
     */
    loadColumnStore : function(panelCode, configName) {
        var backpanel = this;
        var outputStore = backpanel.ht_outputStore;
        var outputGrid = backpanel.ht_outputGrid;
        var queryFormConfig = backpanel.queryFormConfig;
        backpanel.gridConfig.deletedKeyFields = backpanel.gridConfig.deletedKeyFields || [];
        
        //初始化表格列Store
        if (!backpanel.TEMP_COLUMN_STORE) {
            backpanel.TEMP_COLUMN_STORE = new Ext.data.JsonStore({
                url : 'json',
                autoDestroy : true,
                root : 'root.RESULT.rs',
                baseParams : {
                    action :  'QUERY_PUB_GRID_COLUMN_USER_CONFIG_ACTION'
                },
                fields : ['COLUMN_HEADER', 'WIDTH', 'XTYPE', 'DATA_TYPE', 'DATA_SRC',
                          'DATA_DICT', 'DEFAULT_COLUMN_CODE', 'COLUMN_CODE', 'DEFAULT_COLUMN_HEADER', 
                          'COLUMN_ID', 'DEFAULT_WIDTH', 'KEYS', 'EXT_PROPERTIES']
            });
        }
        
        //加载表格列数据
        backpanel.TEMP_COLUMN_STORE.baseParams['panelCode'] = panelCode || '';
        backpanel.TEMP_COLUMN_STORE.baseParams['configName'] = configName || '';
        backpanel.TEMP_COLUMN_STORE.load({
            callback : function() {
                if (backpanel.TEMP_COLUMN_STORE.getCount() == 0) {
                    return;
                }
                
                //获取数据或初始化数据
                var records = backpanel.TEMP_COLUMN_STORE.getRange();
                var record;
                var dataIndex;
                var columnIndex;
                var cm = outputGrid.getColumnModel();
                var columns = cm.columns;
                var columnId;
                var columnExtProperty;
                
                //隐藏列
                for (var i = 0; i < columns.length; i++) {
                    if (columns[i].allowHidden !== false && columns[i].dataIndex) {
                        columns[i].hidden = true;
                    }
                }
                
                //捕获转换异常
                try {
                    for (var i = 0; i < records.length; i++) {
                        record = records[i];
                        dataIndex = record.get('COLUMN_CODE') || record.get('DEFAULT_COLUMN_CODE');
                        columnId = record.get('COLUMN_ID');
                        
                        //增加列
                        columnIndex = cm.findColumnIndex(dataIndex);
                        if (columnIndex == -1) {
                            var column = {
                                header : record.get('COLUMN_HEADER') || record.get('DEFAULT_COLUMN_HEADER'),
                                dataIndex : dataIndex,
                                width : record.get('WIDTH') || record.get('DEFAULT_WIDTH'),
                                xtype : record.get('XTYPE')
                            };
                            
                            //表格数据显示
                            var dataType = record.get('DATA_TYPE');
                            if (3 == dataType) { //date
                                column.renderer = function(value){
                                    return ht.pub.date.dateRenderer(value);
                                } 
                            } else if (4 == dataType) { //datetime
                                column.renderer = function(value){
                                    return ht.pub.date.dateTimeRenderer(value);
                                }
                            } else if (5 == dataType) { //单数据转换
                                var dataSrc = record.get('DATA_SRC');
                                var dataDict = record.get('DATA_DICT');
                                var store = backpanel.getRendererStore(dataSrc, dataDict);
                                
                                if (Ext.isEmpty(store)) {
                                    ht.pub.warning(String.format(ht.msg.base.columnRendererError, columnId));
                                }
                                column.renderer = function(value){
                                    return ht.pub.getValueExactByKey(store, 'VALUE', value, 'TEXT');
                                }
                            } else if (6 == dataType) { //多数据转换
                                var dataSrc = record.get('DATA_SRC');
                                var dataDict = record.get('DATA_DICT');
                                var store = backpanel.getRendererStore(dataSrc, dataDict);
                                
                                if (Ext.isEmpty(store)) {
                                    ht.pub.warning(String.format(ht.msg.base.columnRendererError, columnId));
                                }
                                column.renderer = function(value){
                                    return ht.pub.getValuesExactByKey(store, 'VALUE', value, 'TEXT');
                                }
                            }
                            
                            //扩展属性
                            var extProperties = record.get('EXT_PROPERTIES');
                            if (!Ext.isEmpty(extProperties, false)) {
                                var isObject;
                                var pValue;
                                for (var kk = 0; kk < extProperties.length; kk++) {
                                    columnExtProperty = extProperties[kk].PROPERTY;
                                    isObject = extProperties[kk].IS_OBJECT;
                                    pValue = extProperties[kk].VALUE;
                                    
                                    if (1 == isObject && !Ext.isEmpty(pValue, false)) {
                                        pValue = pValue.trim();
                                        if (pValue.indexOf('{') == 0) { //json数据
                                            pValue = eval('[' + pValue + ']')[0];
                                        } else {
                                            pValue = eval(pValue);
                                        }
                                        
                                        if (Ext.isEmpty(pValue)) {
                                            ht.pub.warning(String.format(ht.msg.base.columnEvalError, columnId, columnExtProperty));
                                        }
                                    }
                                    column[columnExtProperty] = pValue;
                                    
                                }
                            }
                            
                            columns.push(column);
                        } else {
                            columns[columnIndex].hidden = false;
                        }
                        
                        //增加解析字段
                        if (backpanel.gridConfig.storeFields.indexOf(dataIndex) == -1) {
                            var field = new Ext.data.Field(dataIndex);
                            outputStore.reader.meta.fields.push(dataIndex);
                            outputStore.reader.recordType.prototype.fields.add(field);
                            outputStore.recordType.prototype.fields.add(field);
                        }
                    }
                } catch(e) {
                    ht.pub.warning(String.format(ht.msg.base.columnEvalError, columnId, columnExtProperty));
                }
                
                //增加主键
                if (records.length > 0) {
                    var keys = records[0].get('KEYS');
                    var key;
                    
                    //增加解析字段
                    if (!Ext.isEmpty(keys)) {
                        for (var k = 0; k < keys.length; k++) {
                            key = keys[k].COLUMN_CODE || keys[k].DEFAULT_COLUMN_CODE;
                            if (backpanel.gridConfig.storeFields.indexOf(key) == -1) {
                                var field = new Ext.data.Field(key);
                                outputStore.reader.meta.fields.push(key);
                                outputStore.reader.recordType.prototype.fields.add(field);
                                outputStore.recordType.prototype.fields.add(field);
                                
                                backpanel.gridConfig.deletedKeyFields.push(key);
                            }
                        }
                    }
                } //end 增加主键
                
                //刷新表格列、store配置
                cm.setConfig(columns);
                delete outputStore.reader.ef;
                outputStore.reader.ef = null;
                outputStore.reader.buildExtractors();
                
                //加载表格列过滤
                backpanel.initGridFilters();
                
                //自动加载条件完成
                queryFormConfig.autoQueryPreCondition.columnStore = true;
            }
        });
    },
    
    /**
     * 增加事件
     */     
    addFn : function() {
        
        var backpanel = this;
        var ctListeners = backpanel.ctListeners;
        
        //点击增加
        if(ctListeners.clickAddBtn && !ctListeners.clickAddBtn()){
            return;
        }

        backpanel.enableRelativePanelBarBtn(false);
        
        //窗口显示前处理
        if(ctListeners.beforeShowAddWin && !ctListeners.beforeShowAddWin()){
            return;
        }
        
        if(backpanel.ht_outputAddWindow) {
            backpanel.ht_outputAddWindow.show();
        
            //窗口显示后处理
            if(ctListeners.afterShowAddWin){
                ctListeners.afterShowAddWin()
            }
        }
    },
    
    /**
     * 修改事件
     */
    updateFn :function() {
        
        var backpanel = this;
        var ctListeners = backpanel.ctListeners;
        
        //点击修改按钮
        if(ctListeners.clickUpdateBtn && !ctListeners.clickUpdateBtn()){
            return;
        }
        
        //窗口显示前处理
        if(ctListeners.beforeShowUpdateWin && !ctListeners.beforeShowUpdateWin()){
            return;
        }
        
        if(backpanel.ht_outputUpdateWindow) {
            
            var record = backpanel.ht_outputGrid.getSelectionModel().getSelected();
            backpanel.ht_outputUpdateFormPanel.form.setValues(record.data);
            
            backpanel.ht_outputUpdateWindow.show();
            
            //窗口显示后处理
            if(ctListeners.afterShowUpdateWin){
                ctListeners.afterShowUpdateWin()
            }
        }
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
    
    /**
     * 初始化表格列过滤
     */
    initGridFilters : function() {
        var backpanel = this;
        var gridConfig = backpanel.gridConfig;
        var gridFilters = backpanel.TMEP_GRID_FILTERS;
        
        //增加过滤控件
        var gridItem;
        for (var i = 0; i < gridConfig.columns.length; i++) {
            gridItem = gridConfig.columns[i];
            if (gridItem instanceof Ext.grid.RowNumberer 
               || gridItem instanceof Ext.grid.CheckboxSelectionModel) {
                continue;
            }
            if (gridItem.enableFilter && gridConfig.enableFilter !== false) {
                var filterItem = gridItem.filter || {type: 'string'};
                filterItem.dataIndex = gridItem.dataIndex;
                
                //如果过滤已存在，则取消
                if (gridFilters.getFilter(filterItem.dataIndex)){
                    continue;
                }
                
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
        }
    },
    
    initGridPanel : function() {
        
        var backpanel = this;
        
        //初始化参数
        var gridTmpTopbars = new Array(); 
        var gridTopbar = new Array();  
        var queryFormConfig = backpanel.queryFormConfig;
        var gridConfig = backpanel.gridConfig;
        var store = backpanel.ht_outputStore;
        var queryPanel = backpanel.ht_outputQueryPanel;
        var ctListeners = backpanel.ctListeners;
        gridConfig.columns = gridConfig.columns || [];
        
        //列过滤
        var gridFilters = new Ext.ux.grid.GridFilters({
            menuFilterText : ht.msg.base.grid.menuFilterText,
            encode: true, 
            local: true   
        });
        backpanel.TMEP_GRID_FILTERS = gridFilters;
        
        //定义表格的视图：
        //1.表格默认使用一般视图
        //2.如果使用冻结列，则使用冻结列视图，不使用缓冲视图
        if(gridConfig.lockingView == null){
            gridConfig.lockingView = false;
        }
        
        if(gridConfig.bufferView == null){
            gridConfig.bufferView = true;
        }
        if(gridConfig.lockingView){
            gridConfig.bufferView = false;
        }
        
        //定义表格每页显示数据：
        if(gridConfig.pageSize == null || gridConfig.pageSize <= 0){
            gridConfig.pageSize = ht.config.params.PAGE_SIZE;
        }
        
        //加行号列
        if (gridConfig.enableRowNumberer !== false
            && !(gridConfig.columns[0] instanceof Ext.grid.RowNumberer)) {
                
                 gridConfig.columns.splice(0, 0, new Ext.grid.RowNumberer());
        }
        
        //定义表格选择方式
        var smGrid;
        if (gridConfig.isMultipleSelect !== false) {
             var position = 0;
             if (gridConfig.columns[0] instanceof Ext.grid.RowNumberer) {
                 position = 1;
             }
             smGrid = new Ext.grid.CheckboxSelectionModel({
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
                        backpanel.enableBarBtn(true);
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
                        backpanel.enableBarBtn(true);
                    }
                 }
            });
        }
        
        var cm = {};
        var view = {};
        if(gridConfig.lockingView){
            cm = new Ext.ux.grid.LockingColumnModel({
                defaultSortable : gridConfig.defaultSortable !== false,
                columns : gridConfig.columns
            });
            view = new Ext.ux.grid.LockingGridView({
                lockText:ht.msg.base.grid.lockText,
                unlockText : ht.msg.base.grid.unlockText
            });
        }else{
            cm = new Ext.grid.ColumnModel({
                defaultSortable : gridConfig.defaultSortable !== false,
                columns : gridConfig.columns
            });
            if(gridConfig.bufferView){
                view = new Ext.ux.grid.BufferView({scrollDelay : 0});
            } else {
                view = new Ext.grid.GridView();
            }
        }
        
        //初始化表格列过滤控件
        backpanel.initGridFilters();
        
        var bottomBar = backpanel.initGridBottombar(); 
        
        //增加插件
        var plugins = new Array();
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
            style : gridConfig.style,
            plugins : plugins,
            stripeRows : true,
            columnLines : true,
            enableColumnHide : false,
            autoScroll : true,
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
                rowdblclick : function(agrid, rowIndex, e){
                    if(ctListeners.dbclickGridRow) {
                        ctListeners.dbclickGridRow(agrid, rowIndex, e);
                    }
                    
                    //没有权限控制或有权限
                    if (!backpanel.initConfig.tagValues || !gridConfig.updateBtn || !gridConfig.updateBtn.tagValue
                        || (backpanel.initConfig.tagValues.indexOf(gridConfig.updateBtn.tagValue) > -1)) {
                       backpanel.updateFn();
                    }
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
        
        //表格按钮-修改
        if (gridConfig.updateBtn && gridConfig.updateBtn.action) {
            topPostion++;
            var position = gridConfig.updateBtn.position !== undefined ? gridConfig.updateBtn.position : topPostion;
            gridTmpTopbars[position] = {
                text: ht.msg.base.updateText,
                tagValue : gridConfig.updateBtn.tagValue,
                iconCls : 'save',
                enableByRowSelected : true,
                handler : function(){backpanel.updateFn(); }
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
        
        //按钮事件
        return gridPanel;
        
    },
    
    initAddWindow : function() {
        
         //初始化参数
        var backpanel = this;
        var gridConfig = backpanel.gridConfig;
        var store = backpanel.ht_outputStore;
        var ctListeners = backpanel.ctListeners;
        var editFormConfig = backpanel.editFormConfig;
        
        
        if (!gridConfig.addBtn || !gridConfig.addBtn.action || gridConfig.addBtn.autoCreateWindow === false) {
             return null;
        }
        
        //创建增加面板
        var addPanel = editFormConfig.addPanel ? editFormConfig.addPanel : {};
        var editItems = addPanel.items ? addPanel.items : editFormConfig.items;
        var editLen = editItems == undefined ? 0 : editItems.length;
        
        var editPanel = new ht.base.PropertyPanel({
            region : 'center',
            hidden : editLen == 0 ? true : false,
            initConfig : {
                tagValues : backpanel.initConfig.tagValues
            },
            formConfig : {
                submit : {
                    action : gridConfig.addBtn.action,
                    successMsg : ht.msg.base.addSuccessText, 
                    tagValue : addPanel.tagValue,
                    submitPosition : addPanel.submitPosition, 
                    cancelPosition : addPanel.cancelPosition 
                },
                isWindowButtons : true,
                ctTopbarComponts : addPanel.ctTopbarComponts,
                useTo : 'win-edit', 
                panelHeight : addPanel.panelHeight,
                panelDiffHeight : addPanel.panelDiffHeight, 
                items : editItems,
                ctButtons : addPanel.ctButtons
            },
            
            ctListeners : {
                beforeSubmit : ctListeners.beforeAdd,
                afterSubmit :  function(action){ 
                    if (ctListeners.afterAdd && !ctListeners.afterAdd(action)) {
                        return;
                    }
                    
                    //store的处理,处理后台数据，后台output->type="object",action:setvalue(outputTable, Map)
                    var formValues = editPanel.ht_outputFormPanel.getForm().getValues();
                    
                    //替换自定义控件TextFieldButton的值
                    ht.pub.getTextFieldButtonValues(editPanel.ht_outputFormPanel, editPanel.tempDataFieldNames, formValues);
                    
                    var table = gridConfig.addBtn.outputTable;
                    if(table && action.result 
                         && action.result.root && action.result.root[table] 
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
                                 formValues[fieldName] = value;
                             }
                         }
                    }
                    
                    store.add(new Ext.data.Record(formValues));
                    backpanel.enableBarBtnByHasdata();
                    if (ctListeners.beforeHideAddWin && !ctListeners.beforeHideAddWin()) {
                        return;
                    }
                    
                    backpanel.ht_outputAddWindow.hide();
                    
                    if (ctListeners.afterHideAddWin) {
                        ctListeners.afterHideAddWin();
                    }
                    
                    editPanel.ht_outputFormPanel.getForm().reset();
                    
                }
            }
        });
        
        backpanel.ht_outputAddFormPanel = editPanel.ht_outputFormPanel;
        var winHeight = editPanel.ht_outputPanelHeight > ht.config.window.maxHeight ? ht.config.window.maxHeight : editPanel.ht_outputPanelHeight;

        //创建window
        var winTitle = addPanel.windowTitle ? addPanel.windowTitle : ht.msg.base.window.addText;
        var addWin = new Ext.Window({
            width: ht.config.window.width,
            height: winHeight,
            title: winTitle,
            plain: true,
            closable: true,
            resizable: false,
            frame: true,
            constrain : true,
            layout: 'fit',
            closeAction: 'hide',
            border: false,
            modal: true,
            items: editPanel,
            buttonAlign : ht.config.layout.BUTTON_ALIGN,
            listeners : {
                afterlayout : function(component) {
                    backpanel.ht_outputAddFormPanel.ownerCt.disableFields(false); //增加时控件禁用
                    if (ctListeners.afterLayoutAddWin) {
                        ctListeners.afterLayoutAddWin();
                    }
                }
            },
            buttons : editPanel.ht_outputButtons
        });
        
        backpanel.ht_outputAddWindow = addWin;
    },
    
    initUpdateWindow : function() {
        
         //初始化参数
        var backpanel = this;
        var gridConfig = backpanel.gridConfig;
        var store = backpanel.ht_outputStore;
        var ctListeners = backpanel.ctListeners;
        var editFormConfig = backpanel.editFormConfig;
        
        
        if (!gridConfig.updateBtn || !gridConfig.updateBtn.action || gridConfig.updateBtn.autoCreateWindow === false) {
             return null;
        }
        
        //创建增加面板
        var updatePanel = editFormConfig.updatePanel ? editFormConfig.updatePanel : {};
        var editItems = updatePanel.items ? updatePanel.items : editFormConfig.items; 
        var editLen = editItems == undefined ? 0 : editItems.length;
        
        
        var editPanel = new ht.base.PropertyPanel({
            region : 'center',
            hidden : editLen == 0 ? true : false,
            initConfig : {
                tagValues : backpanel.initConfig.tagValues
            },
            formConfig : {
                submit : {
                    action : gridConfig.updateBtn.action,
                    successMsg : ht.msg.base.updateSuccessText, 
                    tagValue : updatePanel.tagValue,
                    submitPosition : updatePanel.submitPosition, 
                    cancelPosition : updatePanel.cancelPosition 
                },
                isWindowButtons : true,
                ctTopbarComponts : updatePanel.ctTopbarComponts,
                useTo : 'win-edit', 
                panelHeight : updatePanel.panelHeight,
                panelDiffHeight : updatePanel.panelDiffHeight, 
                items : editItems,
                ctButtons : updatePanel.ctButtons
            },
            
            ctListeners : {
                beforeSubmit : ctListeners.beforeUpdate,
                afterSubmit :  function(action){ 
                    if (ctListeners.afterUpdate && !ctListeners.afterUpdate(action)) {
                        return;
                    }

                    //store的处理,处理后台数据，后台output->type="object",action:setvalue(outputTable, Map)
                    var formValues = editPanel.ht_outputFormPanel.getForm().getValues();
                    
                    //替换自定义控件TextFieldButton的值
                    ht.pub.getTextFieldButtonValues(editPanel.ht_outputFormPanel, editPanel.tempDataFieldNames, formValues);
                    
                    var table = gridConfig.updateBtn.outputTable;
                    if(table && action.result 
                         && action.result.root && action.result.root[table] 
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
                                 formValues[fieldName] = value;
                             }
                         }
                    }
                    
                    var record = backpanel.ht_outputGrid.getSelectionModel().getSelected();
                    for (var field in formValues) {
                        record.data[field] = formValues[field];
                    }
                    record.commit();
                    
                    if (ctListeners.beforeHideUpdateWin && !ctListeners.beforeHideUpdateWin()) {
                        return;
                    }
                    
                    backpanel.ht_outputUpdateWindow.hide();
                    
                    if (ctListeners.afterHideUpdateWin) {
                        ctListeners.afterHideUpdateWin();
                    }
                    editPanel.ht_outputFormPanel.getForm().reset();
                    
                }
            }
        });
        
        backpanel.ht_outputUpdateFormPanel = editPanel.ht_outputFormPanel;
        var winHeight = editPanel.ht_outputPanelHeight > ht.config.window.maxHeight ? ht.config.window.maxHeight : editPanel.ht_outputPanelHeight;
        
        //创建window
        var winTitle = updatePanel.windowTitle ? updatePanel.windowTitle : ht.msg.base.window.updateText;
        var updateWin = new Ext.Window({
            width: ht.config.window.width,
            height: winHeight,
            title: winTitle,
            plain: true,
            closable: true,
            resizable: false,
            frame: true,
            constrain : true,
            layout: 'fit',
            closeAction: 'hide',
            border: false,
            modal: true,
            items: editPanel,
            buttonAlign : ht.config.layout.BUTTON_ALIGN,
            listeners : {
                afterlayout : function(component) {
                    backpanel.ht_outputUpdateFormPanel.ownerCt.disableFields(true); //修改时控件禁用
                    if (ctListeners.afterLayoutUpdateWin) {
                        ctListeners.afterLayoutUpdateWin();
                    }
                }
            },
            buttons : editPanel.ht_outputButtons
        });
        
        backpanel.ht_outputUpdateWindow = updateWin;
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
        gridConfig.storeFields = gridConfig.storeFields || [];
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
        
        //动态加载表格列、查询条件
        if (backpanel.panelCode) {
            queryFormConfig.autoQueryPreCondition = queryFormConfig.autoQueryPreCondition || {};
            queryFormConfig.autoQueryPreCondition.columnStore = false;
            backpanel.loadColumnStore(backpanel.panelCode);
        }
        
        //扩展属性面板
        var propertyPanel = backpanel.initPropertyPanel(backpanel);
        
        
        //增加窗口、修改窗口
        backpanel.initAddWindow();
        backpanel.initUpdateWindow();
        
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
        //
        
        ht.base.DnyaPlainPanel.superclass.initComponent.call(backpanel);
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
        
        //面板销毁前事件
        backpanel.on('beforedestroy', function(){
            
            var addWin = backpanel.ht_outputAddWindow;
            if(addWin){
                addWin.close();
                addWin.destroy();
                addWin = null;
            }
            
            var updateWin = backpanel.ht_outputUpdateWindow;
            if(updateWin){
                updateWin.close();
                updateWin.destroy();
                updateWin = null;
            }
            backpanel.removeAll(true);
        })
        
        store.on('load', function(st, records, opt){
            backpanel.enableBarBtn(false);
            backpanel.callbackStore();
            backpanel.enableBarBtnByHasdata();
        });
    }
});

Ext.reg('dnyaplainpanel', ht.base.DnyaPlainPanel);

