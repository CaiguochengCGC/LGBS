
/**
* 获取光标所在的字符位置
* @param obj 要处理的控件, 支持文本域和输入框
* @author hotleave
*/
Ext.ns('Ext.ux.util');
Ext.ux.util.getPosition = function(obj) {
	var result = 0;
	if (obj.selectionStart) { // 非IE浏览器
		result = obj.selectionStart
	} else { // IE
		var rng;
		if (obj.tagName == 'TEXTAREA') { // 如果是文本域
			rng = obj.createTextRange();
			rng.moveToPoint(event.x, event.y);
		} else { // 输入框
			rng = document.selection.createRange();
		}
		rng.moveStart('character', -obj.value.length);
		result = rng.text.length;
	}
	return result;
}

Ext.ux.util.setPosition = function(obj, pos) {
    if (obj.selectionStart) { 
        obj.selectionStart = pos;
        obj.selectionEnd = pos;
    } else if(obj.setSelectionRange){
        obj.focus();
        obj.setSelectionRange(pos, pos);
    } else if (obj.createTextRange) {
        var range = obj.createTextRange();
        range.collapse(true);
        range.moveEnd('character', pos);
        range.moveStart('character', pos);
        range.select();
    }
}

Ext.ux.util.getCompId = function() {
    if (Ext.isEmpty(Ext.ux.util.COMP_SEQ)) {
        Ext.ux.util.COMP_SEQ = 0;
    }
    Ext.ux.util.COMP_SEQ = Ext.ux.util.COMP_SEQ + 1;
    return 'ht_comp_' + Ext.ux.util.COMP_SEQ;
}

Ext.ux.grid.TextFieldColumn = Ext.extend(Ext.grid.Column, {
    
    fieldConfig : {}, //textfield config
    
    constructor : function(config){
        Ext.apply(this, config);
        this.addEvents(
            /**
             * @event click
             * Fires when this Value is change.
             * @param {TextFieldColumn} this,
             * @param {Ext.form.TextField} field,
             * @param {String} fieldName
             * @param {Ext.data.Record} Record
             * @param {Mixed} newValue
             * @param {Mixed} oldValue
             * @param {Ext.EventObject} e
             *  
             */
            'datachange'
        );
        Ext.ux.grid.TextFieldColumn.superclass.constructor.call(this);
    },

    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     * @param{String} name
     * @param{Ext.EventObject} e
     * @param{Ext.grid.GridPanel} grid
     * @param{Number} rowIndex
     * @param{Number} colIndex
     */
    processEvent : function(name, e, grid, rowIndex, colIndex){
        if (name == 'mousedown') {
            
            var target = e.getTarget();
            if (!target || 'DIV' == target.tagName) {
                return true; 
            }
            
            var column = this;
            window.setTimeout(function(){
                
                var mousePos = 0;
                try {
                    mousePos = Ext.ux.util.getPosition(target);
                }catch(e){}
                
                var parentTarget = target.parentNode;
                if (!parentTarget.getAttribute('mousedowned')) {
                    parentTarget.setAttribute('mousedowned', '1');
                
                    parentTarget.innerHTML = '';
                    var config = column.fieldConfig;
                    config.cls = config.cls || '';
                    if (config.cls.indexOf('x-grid3-textfield-col') == -1) {
                        config.cls = config.cls + ' x-grid3-textfield-col';
                        if (config.allowBlank === false) {
                            config.cls += ' ht-field-reqire';
                        }
                    }
                    config.value = target.value;
                    config.name = column.dataIndex;
                    config.msgTarget = 'qtip';
                    config.renderTo = parentTarget;
                    config.enableKeyEvents = true;
                    
                    config.listeners = config.listeners || {};
                    config.listeners.change = function(field, newValue, oldValue) {
                        
                        if (!field.isValid()) {
                            return;
                        }
                        
                        var record = grid.store.getAt(rowIndex);
                        record.set(column.dataIndex, newValue);
                        column.fireEvent('datachange', column, field, record, column.dataIndex, newValue, oldValue);
                    }; 
                    
                    var field = new Ext.form.TextField(config);
                    field.on('keydown', function(field, e) {
                        if (Ext.EventObject.ENTER == e.getKey()) {
                            field.blur();
                        }
                    });
                    field.focus();
                    Ext.ux.util.setPosition(Ext.getDom(field.getEl()), mousePos);
                }
            },10)
            
            return false; 
        } else {
            return Ext.ux.grid.TextFieldColumn.superclass.processEvent.apply(this, arguments);
        }
    },

    renderer : function(v, p, record){
        
        this.fieldConfig = this.fieldConfig || {}
        var cls = this.fieldConfig.cls || '';
        if (this.fieldConfig.allowBlank === false) {
            cls += ' ht-field-reqire';
        }
        v = Ext.isEmpty(v) ? '' : v;
        
        return String.format('<div><input class="x-form-text x-form-field x-grid3-textfield-col {0}" value="{1}"/></div>', cls, v);
    },

    init: Ext.emptyFn
});

// register ptype. Deprecate. Remove in 4.0
Ext.preg('textfieldcolumn', Ext.ux.grid.TextFieldColumn);

// backwards compat. Remove in 4.0
Ext.grid.TextFieldColumn = Ext.ux.grid.TextFieldColumn;

// register Column xtype
Ext.grid.Column.types.textfieldcolumn = Ext.ux.grid.TextFieldColumn;

/**
 * 
 * @class Ext.ux.grid.NumberFieldColumn
 * @extends Ext.grid.Column
 */
Ext.ux.grid.NumberFieldColumn = Ext.extend(Ext.grid.Column, {
    
    fieldConfig : {}, //numberfield config
    
    constructor : function(config){
        Ext.apply(this, config);
        this.addEvents(
            /**
             * @event click
             * Fires when this Value is change.
             * @param {NumberFieldColumn} this,
             * @param {Ext.form.NumberField} field,
             * @param {String} fieldName
             * @param {Ext.data.Record} Record
             * @param {Mixed} newValue
             * @param {Mixed} oldValue
             * @param {Ext.EventObject} e
             *  
             */
            'datachange'
        );
        Ext.ux.grid.NumberFieldColumn.superclass.constructor.call(this);
    },

    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     * @param{String} name
     * @param{Ext.EventObject} e
     * @param{Ext.grid.GridPanel} grid
     * @param{Number} rowIndex
     * @param{Number} colIndex
     */
    processEvent : function(name, e, grid, rowIndex, colIndex){
        if (name == 'mousedown') {
            
            var target = e.getTarget();
            if (!target || 'DIV' == target.tagName) {
                return true; 
            }
            
            var column = this;
            window.setTimeout(function(){
                
                var mousePos = 0;
                try {
                    mousePos = Ext.ux.util.getPosition(target);
                }catch(e){}
                
                var parentTarget = target.parentNode;
                if (!parentTarget.getAttribute('mousedowned')) {
                    parentTarget.setAttribute('mousedowned', '1');
                
                    parentTarget.innerHTML = '';
                    var config = column.fieldConfig;
                    config.cls = config.cls || '';
                    if (config.cls.indexOf('x-grid3-textfield-col') == -1) {
                        config.cls = config.cls + ' x-grid3-textfield-col';
                        if (config.allowBlank === false) {
                            config.cls += ' ht-field-reqire';
                        }
                    }
                    
                    config.value = target.value;
                    config.name = column.dataIndex;
                    config.msgTarget = 'qtip';
                    config.renderTo = parentTarget;
                    config.enableKeyEvents = true;
                    config.listeners = config.listeners || {};
                    config.listeners.change = function(field, newValue, oldValue) {
                        
                        if (!field.isValid()) {
                            return;
                        }
                        
                        var record = grid.store.getAt(rowIndex);
                        record.set(column.dataIndex, newValue);
                        column.fireEvent('datachange', column, field, record, column.dataIndex, newValue, oldValue);
                    }; 
                    
                    var field = new Ext.form.NumberField(config);
                    field.on('keydown', function(field, e) {
                        if (Ext.EventObject.ENTER == e.getKey()) {
                            field.blur();
                        }
                    });
                    field.focus();
                    Ext.ux.util.setPosition(Ext.getDom(field.getEl()), mousePos)
                }
            },10);
            
            return false; 
        } else {
            return Ext.ux.grid.NumberFieldColumn.superclass.processEvent.apply(this, arguments);
        }
    },

    renderer : function(v, p, record){
        
        this.fieldConfig = this.fieldConfig || {}
        var cls = this.fieldConfig.cls || '';
        if (this.fieldConfig.allowBlank === false) {
            cls += ' ht-field-reqire';
        }
        v = Ext.isEmpty(v) ? '' : v;
        
        return String.format('<div><input class="x-form-text x-form-field x-grid3-textfield-col {0}" value="{1}"/></div>', cls, v);
    },

    init: Ext.emptyFn
});

// register ptype. Deprecate. Remove in 4.0
Ext.preg('numberfieldcolumn', Ext.ux.grid.NumberFieldColumn);

// backwards compat. Remove in 4.0
Ext.grid.NumberFieldColumn = Ext.ux.grid.NumberFieldColumn;

// register Column xtype
Ext.grid.Column.types.numberfieldcolumn = Ext.ux.grid.NumberFieldColumn;

/**
 * 
 * @class Ext.ux.grid.ComboColumn
 * @extends Ext.grid.Column
 */
Ext.ux.grid.ComboColumn = Ext.extend(Ext.grid.Column, {
    
    fieldConfig : {}, //combo config + rendererStore : null
    
    constructor : function(config){
        Ext.apply(this, config);
        this.addEvents(
            /**
             * @event click
             * Fires when this Value is change.
             * @param {ComboColumn} this,
             * @param {Ext.form.TextField} field,
             * @param {String} fieldName
             * @param {Ext.data.Record} Record
             * @param {Mixed} newValue
             * @param {Mixed} oldValue
             * @param {Ext.EventObject} e
             *  
             */
            'datachange'
        );
        Ext.ux.grid.ComboColumn.superclass.constructor.call(this);
    },

    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     * @param{String} name
     * @param{Ext.EventObject} e
     * @param{Ext.grid.GridPanel} grid
     * @param{Number} rowIndex
     * @param{Number} colIndex
     */
    processEvent : function(name, e, grid, rowIndex, colIndex){
        if (name == 'mousedown') {
            
            var target = e.getTarget();
            if (!target || 'DIV' == target.tagName) {
                return true; 
            }
            
            var clickTarget = target;
            var parentTarget = target.parentNode;
            if ('INPUT' != target.tagName) {
                target = parentTarget.getElementsByTagName('INPUT')[0];
            }
            
            var column = this;
            window.setTimeout(function(){
                
                var mousePos = 0;
                try {
                    mousePos = Ext.ux.util.getPosition(clickTarget);
                }catch(e){}
                
                if (!parentTarget.getAttribute('mousedowned')) {
                    parentTarget.setAttribute('mousedowned', '1');
                
                    parentTarget.innerHTML = '';
                    var record = grid.store.getAt(rowIndex);
                    var config = column.fieldConfig;
                    config.cls = config.cls || '';
                    if (config.cls.indexOf('x-grid3-textfield-col') == -1) {
                        config.cls = config.cls + ' x-grid3-textfield-col';
                        if (config.allowBlank === false) {
                            config.cls += ' ht-field-reqire';
                        }
                    }
                    
                    config.value = record.get(column.dataIndex);
                    config.name = column.dataIndex;
                    config.enableKeyEvents = true;
                    config.msgTarget = 'qtip';
                    config.renderTo = parentTarget;

                    var field = new Ext.form.ComboBox(config);
                    field.setWidth(column.width - 10);
                    field.on('keydown', function(field, e) { //按键事件
                        
                        var record = grid.store.getAt(rowIndex);
                        var oldValue = '' + record.get(column.dataIndex);
                        var newValue = field.getValue();
                        
                        if (Ext.EventObject.ENTER == e.getKey() && oldValue != newValue) {
                            if (!field.isValid()) {
                                return;
                            }
                            record.set(column.dataIndex, field.getValue());
                            column.fireEvent('datachange', column, field, record, column.dataIndex, newValue, oldValue);
                            
                        }
                    });
                    field.on('select', function(field) { //选择事件
                        
                        var record = grid.store.getAt(rowIndex);
                        var oldValue = '' + record.get(column.dataIndex);
                        var newValue = field.getValue();
                        
                        if (oldValue != newValue) {
                            if (!field.isValid()) {
                                return;
                            }
                            record.set(column.dataIndex, field.getValue());
                            column.fireEvent('datachange', column, field, record, column.dataIndex, newValue, oldValue);
                            
                        }
                    });
                    field.focus();
                    
                    if ('INPUT' == clickTarget.tagName && field.editable !== false) {
                    } else {
                        window.setTimeout(function(){
                            field.onTriggerClick();
                        }, 10);
                    }
                    Ext.ux.util.setPosition(Ext.getDom(field.getEl()), mousePos);
                }
            },10);
                
            
            return false; 
        } else {
            return Ext.ux.grid.ComboColumn.superclass.processEvent.apply(this, arguments);
        }
    },

    renderer : function(v, p, record){
        
        this.fieldConfig = this.fieldConfig || {}
        var cls = this.fieldConfig.cls || '';
        if (this.fieldConfig.allowBlank === false) {
            cls += ' ht-field-reqire';
        }
        
        var store = record.get(this.dataIndex + '_STORE') || this.fieldConfig.store || this.fieldConfig.rendererStore;
        var valueField = this.fieldConfig.valueField;
        var displayField = this.fieldConfig.displayField;
        valueField = valueField || displayField;
        displayField = displayField || valueField;
        
        if (!Ext.isEmpty(store)) {
            
            var index = store.data.findIndexBy(function(rec){
                 return ('' + rec.get(valueField)) === ('' + v);
            }, store);
            
            if (index > -1) {
                v = store.getAt(index).get(displayField);
            }
        }
        var elWidth = this.width;
        if (elWidth > 27) {
            elWidth = elWidth - 27;
        }
        
        var html = '<div class="x-form-field-wrap x-form-field-trigger-wrap"><input class="x-form-text x-form-field x-grid3-combo-col {0}" style="width:{1}px" value="{2}"/>';
        html += '<img class="x-form-trigger x-form-arrow-trigger" src="lib/ext-3.3.0/resources/images/default/s.gif"/></div>';
        v = Ext.isEmpty(v) ? '' : v;
        
        return String.format(html, cls, elWidth, v);
    },

    init: Ext.emptyFn
});

// register ptype. Deprecate. Remove in 4.0
Ext.preg('combocolumn', Ext.ux.grid.ComboColumn);

// backwards compat. Remove in 4.0
Ext.grid.ComboColumn = Ext.ux.grid.ComboColumn;

// register Column xtype
Ext.grid.Column.types.combocolumn = Ext.ux.grid.ComboColumn;



/**
 * 
 * @class Ext.ux.grid.DateFieldColumn
 * @extends Ext.grid.Column
 */
Ext.ux.grid.DateFieldColumn = Ext.extend(Ext.grid.Column, {
    
    fieldConfig : {}, //datefield config
    
    constructor : function(config){
        Ext.apply(this, config);
        this.addEvents(
            /**
             * @event click
             * Fires when this Value is change.
             * @param {DateFieldColumn} this,
             * @param {Ext.form.TextField} field,
             * @param {String} fieldName
             * @param {Ext.data.Record} Record
             * @param {Mixed} newValue
             * @param {Mixed} oldValue
             * @param {Ext.EventObject} e
             *  
             */
            'datachange'
        );
        Ext.ux.grid.DateFieldColumn.superclass.constructor.call(this);
    },

    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     * @param{String} name
     * @param{Ext.EventObject} e
     * @param{Ext.grid.GridPanel} grid
     * @param{Number} rowIndex
     * @param{Number} colIndex
     */
    processEvent : function(name, e, grid, rowIndex, colIndex){
        if (name == 'mousedown') {
            
            var target = e.getTarget();
            if (!target || 'DIV' == target.tagName) {
                return true; 
            }
            
            var clickTarget = target;
            var parentTarget = target.parentNode;
            if ('INPUT' != target.tagName) {
                target = parentTarget.getElementsByTagName('INPUT')[0];
            }
            
            var column = this;
            window.setTimeout(function(){
                
                var mousePos = 0;
                try {
                    mousePos = Ext.ux.util.getPosition(clickTarget);
                }catch(e){}
                
                if (!parentTarget.getAttribute('mousedowned')) {
                    parentTarget.setAttribute('mousedowned', '1');
                
                    parentTarget.innerHTML = '';
                    var record = grid.store.getAt(rowIndex);
                    var config = column.fieldConfig;
                    config.cls = config.cls || '';
                    if (config.cls.indexOf('x-grid3-textfield-col') == -1) {
                        config.cls = config.cls + ' x-grid3-textfield-col';
                        if (config.allowBlank === false) {
                            config.cls += ' ht-field-reqire';
                        }
                    }
                    
                    config.value = record.get(column.dataIndex);
                    config.name = column.dataIndex;
                    config.enableKeyEvents = true;
                    config.msgTarget = 'qtip';
                    config.renderTo = parentTarget;
                    
                    var field = new Ext.form.DateField(config);
                    field.setWidth(column.width - 10);
                    field.on('keydown', function(field, e) { //按键事件
                        
                        var record = grid.store.getAt(rowIndex);
                        var oldValue = '' + record.get(column.dataIndex);
                        var newValue = field.getValue();
                        
                        if (Ext.EventObject.ENTER == e.getKey() && oldValue != newValue) {
                            if (!field.isValid()) {
                                return;
                            }
                            record.set(column.dataIndex, field.getValue());
                            column.fireEvent('datachange', column, field, record, column.dataIndex, newValue, oldValue);
                            
                        }
                    });
                    field.on('change', function(field) { //选择事件
                        
                        var record = grid.store.getAt(rowIndex);
                        var oldValue = '' + record.get(column.dataIndex);
                        var newValue = field.getValue();
                        
                        if (oldValue != newValue) {
                            if (!field.isValid()) {
                                return;
                            }
                            record.set(column.dataIndex, field.getValue());
                            column.fireEvent('datachange', column, field, record, column.dataIndex, newValue, oldValue);
                            
                        }
                    });
                    field.focus();
                    
                    if ('INPUT' == clickTarget.tagName && field.editable !== false) {
                    } else {
                        window.setTimeout(function(){
                            field.onTriggerClick();
                        }, 10);
                    }
                    
                    Ext.ux.util.setPosition(Ext.getDom(field.getEl()), mousePos);
                }
            },10);
            
            return false; 
        } else {
            return Ext.ux.grid.DateFieldColumn.superclass.processEvent.apply(this, arguments);
        }
    },

    renderer : function(v, p, record){
        
        this.fieldConfig = this.fieldConfig || {}
        if (Ext.isEmpty(v, false)) {
            v = '';
        } else if (typeof v == 'object' ) {
            v = v.format(this.fieldConfig.format);
        } else if (typeof v == 'number') {
            v = new Date(v).format(this.fieldConfig.format);
        }
        
        var cls = this.fieldConfig.cls || '';
        if (this.fieldConfig.allowBlank === false) {
            cls += ' ht-field-reqire';
        }
        
        var elWidth = this.width;
        if (elWidth > 27) {
            elWidth = elWidth - 27;
        }
        
        var html = '<div class="x-form-field-wrap x-form-field-trigger-wrap"><input class="x-form-text x-form-field x-grid3-combo-col {0}" style="width:{1}px" value="{2}"/>';
        html += '<img class="x-form-trigger x-form-date-trigger" src="lib/ext-3.3.0/resources/images/default/s.gif"/></div>';
        
        return String.format(html, cls, elWidth, v);
    },

    init: Ext.emptyFn
});

// register ptype. Deprecate. Remove in 4.0
Ext.preg('datefieldcolumn', Ext.ux.grid.DateFieldColumn);

// backwards compat. Remove in 4.0
Ext.grid.DateFieldColumn = Ext.ux.grid.DateFieldColumn;

// register Column xtype
Ext.grid.Column.types.datefieldcolumn = Ext.ux.grid.DateFieldColumn;


/**
 * 
 * @class Ext.ux.grid.RadioGroupColumn
 * @extends Ext.grid.Column
 */
Ext.ux.grid.RadioGroupColumn = Ext.extend(Ext.grid.Column, {
    
    fieldConfig : {
        store : null,
        rendererStore : null,
        valueField : '',
        displayField : ''
    },
    
    constructor : function(config){
        Ext.apply(this, config);
        this.addEvents(
            /**
             * @event click
             * Fires when this Value is change.
             * @param {RadioGroupColumn} this,
             * @param {dom} field,
             * @param {String} fieldName
             * @param {Ext.data.Record} Record
             * @param {Mixed} newValue
             * @param {Mixed} oldValue
             * @param {Ext.EventObject} e
             *  
             */
            'datachange'
        );
        Ext.ux.grid.RadioGroupColumn.superclass.constructor.call(this);
    },

    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     * @param{String} name
     * @param{Ext.EventObject} e
     * @param{Ext.grid.GridPanel} grid
     * @param{Number} rowIndex
     * @param{Number} colIndex
     */
    processEvent : function(name, e, grid, rowIndex, colIndex){
        if (name == 'mousedown') {
            
            var target = e.getTarget();
            if (!target || 'DIV' == target.tagName) {
                return true; 
            }
            
            var clickTarget = target;
            var parentTarget = target.parentNode;
            var column = this;
            var record = grid.store.getAt(rowIndex);
            var oldValue = record.get(column.dataIndex);
            var newValue;
            var field;
            
            window.setTimeout(function(){
                var radios = parentTarget.getElementsByTagName('INPUT');
                for (var i = 0; i < radios.length; i++) {
                    if (radios[i].checked) {
                        field = radios[i];
                        newValue = radios[i].value;
                    }
                }
            
                if (newValue != oldValue) {
                    record.set(column.dataIndex, newValue);
                    column.fireEvent('datachange', column, field, record, column.dataIndex, newValue, oldValue);
                }
            }, 200);
            
            return false; 
        } else {
            return Ext.ux.grid.RadioGroupColumn.superclass.processEvent.apply(this, arguments);
        }
    },

    renderer : function(v, p, record){
        
        this.fieldConfig = this.fieldConfig || {}
        
        var store = record.get(this.dataIndex + '_STORE') || this.fieldConfig.store || this.fieldConfig.rendererStore;
        var valueField = this.fieldConfig.valueField;
        var displayField = this.fieldConfig.displayField;
        valueField = valueField || displayField;
        displayField = displayField || valueField;
        
        var html = '';
        var id = Ext.ux.util.getCompId();
        var name = 'radio_' + id;
        var formatHtml = '<input type="radio" name="{0}" id="{1}" value="{2}" {3} /><label class="x-grid3-radiogroup-col" for="{1}">{4}</label>';
        if (!Ext.isEmpty(store)) {
            for (var i = 0; i < store.getCount(); i++) {
                var record = store.getAt(i);
                html += String.format(formatHtml, name, id, record.get(valueField), v == record.get(valueField) ? 'checked=""' : '', record.get(displayField));
                id = Ext.ux.util.getCompId();
            }
           
        }
        
       
        return html;
    },

    init: Ext.emptyFn
});

// register ptype. Deprecate. Remove in 4.0
Ext.preg('radiogroupcolumn', Ext.ux.grid.RadioGroupColumn);

// backwards compat. Remove in 4.0
Ext.grid.RadioGroupColumn = Ext.ux.grid.RadioGroupColumn;

// register Column xtype
Ext.grid.Column.types.radiogroupcolumn = Ext.ux.grid.RadioGroupColumn;


/**
 * 
 * @class Ext.ux.grid.CheckboxGroupColumn
 * @extends Ext.grid.Column
 */
Ext.ux.grid.CheckboxGroupColumn = Ext.extend(Ext.grid.Column, {
    
    fieldConfig : {
        store : null,
        rendererStore : null,
        valueField : '',
        displayField : ''
    },
    
    constructor : function(config){
        Ext.apply(this, config);
        this.addEvents(
            /**
             * @event click
             * Fires when this Value is change.
             * @param {CheckboxGroupColumn} this,
             * @param {Array} field,
             * @param {String} fieldName
             * @param {Ext.data.Record} Record
             * @param {Mixed} newValue
             * @param {Mixed} oldValue
             * @param {Ext.EventObject} e
             *  
             */
            'datachange'
        );
        Ext.ux.grid.CheckboxGroupColumn.superclass.constructor.call(this);
    },

    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     * @param{String} name
     * @param{Ext.EventObject} e
     * @param{Ext.grid.GridPanel} grid
     * @param{Number} rowIndex
     * @param{Number} colIndex
     */
    processEvent : function(name, e, grid, rowIndex, colIndex){
        if (name == 'mousedown') {
            
            var target = e.getTarget();
            if (!target || 'DIV' == target.tagName) {
                return true; 
            }
            
            var clickTarget = target;
            var parentTarget = target.parentNode;
            var column = this;
            var record = grid.store.getAt(rowIndex);
            var oldValue = record.get(column.dataIndex);
            var newValue;
            var newValues = new Array();
            var fields = new Array();
            
            window.setTimeout(function(){
                var checkboxs = parentTarget.getElementsByTagName('INPUT');
                for (var i = 0; i < checkboxs.length; i++) {
                    if (checkboxs[i].checked) {
                        fields.push(checkboxs[i]);
                        newValues.push(checkboxs[i].value);
                    }
                }
                newValue = newValues.join(',');
                
                record.set(column.dataIndex, newValue);
                column.fireEvent('datachange', column, fields, record, column.dataIndex, newValue, oldValue);
            }, 200);
            
            return false; 
        } else {
            return Ext.ux.grid.CheckboxGroupColumn.superclass.processEvent.apply(this, arguments);
        }
    },

    renderer : function(v, p, record){
        
        this.fieldConfig = this.fieldConfig || {}
        v = Ext.isEmpty(v) ? '' : '' + v;
        var vs = v.split(',');
        
        var store = record.get(this.dataIndex + '_STORE') || this.fieldConfig.store || this.fieldConfig.rendererStore;
        var valueField = this.fieldConfig.valueField;
        var displayField = this.fieldConfig.displayField;
        valueField = valueField || displayField;
        displayField = displayField || valueField;
        
        var html = '';
        var id = Ext.ux.util.getCompId();
        var name = 'checkbox_' + id;
        var formatHtml = '<input type="checkbox" name="{0}" id="{1}" value="{2}" {3} /><label class="x-grid3-checkboxgroup-col" for="{1}">{4}</label>';
        if (!Ext.isEmpty(store)) {
            for (var i = 0; i < store.getCount(); i++) {
                var record = store.getAt(i);
                html += String.format(formatHtml, name, id, record.get(valueField), vs.indexOf('' + record.get(valueField)) > -1 ? 'checked=""' : '', record.get(displayField));
                id = Ext.ux.util.getCompId();
            }
           
        }
        
       
        return html;
    },

    init: Ext.emptyFn
});

// register ptype. Deprecate. Remove in 4.0
Ext.preg('checkboxgroupcolumn', Ext.ux.grid.CheckboxGroupColumn);

// backwards compat. Remove in 4.0
Ext.grid.CheckboxGroupColumn = Ext.ux.grid.CheckboxGroupColumn;

// register Column xtype
Ext.grid.Column.types.checkboxgroupcolumn = Ext.ux.grid.CheckboxGroupColumn;


/**
 * 返回的propertyPanel必须有
 *  loadData : function(params) {
           
    },
    clearData : function() {
    
    }
 * @class Ext.ux.grid.LinkColumn
 * @extends Ext.grid.Column
 */
Ext.ux.grid.LinkColumn = Ext.extend(Ext.grid.Column, {
    
    fieldConfig : {
        url : '',
        js : '',
        store : null,
        rendererStore : null,
        valueField : '',
        displayField : ''
    },
    windowConfig : {}, //window config
    propertyConfig : {
        title : '',
        getRenderTo : function(){}
    },
    
    constructor : function(config){
        Ext.apply(this, config);
        this.addEvents(
            /**
             * @event click
             * Fires when this Value is change.
             * @param {LinkColumn} this,
             * @param {Record} record
             * @param {String} fieldName
             * @param {Mixed} value
             * @param {json} params  
             */
            'linkclick'
        );
        Ext.ux.grid.LinkColumn.superclass.constructor.call(this);
    },

    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     * @param{String} name
     * @param{Ext.EventObject} e
     * @param{Ext.grid.GridPanel} grid
     * @param{Number} rowIndex
     * @param{Number} colIndex
     */
    processEvent : function(name, e, grid, rowIndex, colIndex){
        if (name == 'mousedown') {
            
            var target = e.getTarget();
            if (!target || 'DIV' == target.tagName) {
                return true; 
            }
            
            var column = this;
            var record = grid.store.getAt(rowIndex);
            var value = record.get(column.dataIndex);
            
            var params = {};
            params[column.dataIndex] = value;
            if(false === column.fireEvent('linkclick', column, record, column.dataIndex, value, params)) {
                return false;
            }
            
            if (column.fieldConfig.url) {
                var url = column.fieldConfig.url;
                var urlParams = new Array();
                for (var field in params) {
                    if (Ext.isArray(params[field])) {
                        for (var i = 0; i < params[field].length; i++) {
                            urlParams.push(field + '=' + params[field][i]);
                        }
                    } else {
                        urlParams.push(field + '=' + params[field]);
                    }
                }
                
                if (url.indexOf('?') > -1) {
                    url = url + '&' + urlParams.join('&');
                } else {
                    url = url + '?' + urlParams.join('&');
                }
                
                if (!column.openWindow) {
                    var win = column.getOpenWindow();
                    grid.on('beforedestroy', function() {
                        win.hide();
                        win.destroy();
                    });
                }
                
                var win = column.getOpenWindow();
                win.setTitle(column.windowConfig.title);
                win.show();
                
                var framePanel = column.getOpenFrame();
                framePanel.setSrc(url);
            } else if (column.fieldConfig.js) {
                column.initPropertyPanel(params, grid);
            }
            
            return false; 
        } else {
            return Ext.ux.grid.LinkColumn.superclass.processEvent.apply(this, arguments);
        }
    },
    
    getOpenWindow : function() {
        if (this.openWindow) {
            return this.openWindow;
        } else {
            this.initOpenWindow();
            return this.openWindow;
        }
    },
    
    getOpenFrame : function() {
        if (this.openWindow) {
            return this.openWindow.find('xtype', 'iframepanel')[0];
        } else {
            this.initOpenWindow();
            return this.openWindow.find('xtype', 'iframepanel')[0];
        }
    },
    
    initOpenWindow : function() {
        var framePanel = new Ext.ux.ManagedIFramePanel({
            xtype : 'iframepanel',
            border : false,
            bodyBorder : false,
            autoScroll : true,
            autoLoad : false,
            defaultSrc : null,
            frame : false,
            listeners : {
                domready : function(frame) {
                    
                }
            }
        });
        this.windowConfig.layout = 'fit';
        this.windowConfig.closeAction = 'hide';
        this.openWindow = new Ext.Window(this.windowConfig);
        this.openWindow.add(framePanel)
        
    },
    initPropertyPanel  : function(params, grid) {
        var column = this;
        if (!column.propertyPanel) {
            Ext.Ajax.request({
                method : 'GET',
                url : column.fieldConfig.js,
                scope : this,
                success : function(response, options) {
                    column.propertyPanel = eval(response.responseText);
                    grid.on('beforedestroy', function() {
                        column.propertyPanel.destroy();
                    });
                    grid.getStore().on('load', function(){
                        column.propertyPanel.clearData();
                    });
                    column.rendererPropertyPanel(params);
                }
            });
        } else {
            column.rendererPropertyPanel(params);
        }
    },
    rendererPropertyPanel : function(params) {
        var column = this;
        if (Ext.isString(column.propertyConfig.getRenderTo())) {
           var dom = Ext.getDom(column.propertyConfig.getRenderTo());
           column.propertyPanel.render(dom);
        } else if (Ext.isElement(column.propertyConfig.getRenderTo())) {
            column.propertyPanel.render(column.propertyConfig.getRenderTo());
        } else {
            var renderToPanel = column.propertyConfig.getRenderTo();
            if (!column.propertyConfig.propertyPanel) {
                if (renderToPanel.xtype == 'tabpanel' || renderToPanel instanceof Ext.TabPanel) {
                    renderToPanel.add({
                        title : column.propertyConfig.title,
                        layout : 'fit',
                        items : [column.propertyPanel]
                    });
                    renderToPanel.setActiveTab(column.propertyPanel.ownerCt);
                } else {
                    renderToPanel.add(column.propertyPanel);
                    renderToPanel.doLayout();
                }
            } else {
                 if (renderToPanel.xtype == 'tabpanel' || renderToPanel instanceof Ext.TabPanel) {
                    renderToPanel.setActiveTab(column.propertyPanel.ownerCt);
                 }
            }
            column.propertyConfig.propertyPanel = true;
        }
        column.propertyPanel.loadData(params);
    },

    renderer : function(v, p, record){
        
        this.fieldConfig = this.fieldConfig || {}
        var store = record.get(this.dataIndex + '_STORE') || this.fieldConfig.store || this.fieldConfig.rendererStore;
        var valueField = this.fieldConfig.valueField;
        var displayField = this.fieldConfig.displayField;
        valueField = valueField || displayField;
        displayField = displayField || valueField;
        
        if (!Ext.isEmpty(store)) {
            var index = store.data.findIndexBy(function(rec){
                 return ('' + rec.get(valueField)) === ('' + v);
            }, store);
            
            if (index > -1) {
                v = store.getAt(index).get(displayField);
            }
        }
        v = Ext.isEmpty(v) ? '' : v;
        return String.format('<a href="javascript:void(0)" class="x-grid3-link-col">{0}</a>', v);
    },

    init: Ext.emptyFn
});

// register ptype. Deprecate. Remove in 4.0
Ext.preg('linkcolumn', Ext.ux.grid.LinkColumn);

// backwards compat. Remove in 4.0
Ext.grid.LinkColumn = Ext.ux.grid.LinkColumn;

// register Column xtype
Ext.grid.Column.types.linkcolumn = Ext.ux.grid.LinkColumn;


/**
 * loadData : function(params) {
           
    },
    clearData : function() {
    
    }
 * @class Ext.ux.grid.LinksColumn
 * @extends Ext.grid.Column
 */
Ext.ux.grid.LinksColumn = Ext.extend(Ext.grid.Column, {
    
    fieldConfig : {
        url : '',
        js : '',
        store : null,
        rendererStore : null,
        valueField : '',
        displayField : ''
    },
    windowConfig : {}, //window config
    propertyConfig : {
        title : '',
        getRenderTo : function(){}
    },
    
    constructor : function(config){
        Ext.apply(this, config);
        this.addEvents(
            /**
             * @event click
             * Fires when this Value is change.
             * @param {LinkColumn} this,
             * @param {Record} record
             * @param {String} fieldName
             * @param {Mixed} value
             * @param {json} params 
             */
            'linkclick'
        );
        Ext.ux.grid.LinksColumn.superclass.constructor.call(this);
    },

    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     * @param{String} name
     * @param{Ext.EventObject} e
     * @param{Ext.grid.GridPanel} grid
     * @param{Number} rowIndex
     * @param{Number} colIndex
     */
    processEvent : function(name, e, grid, rowIndex, colIndex){
        if (name == 'mousedown') {
            
            var target = e.getTarget();
            if (!target || 'DIV' == target.tagName) {
                return true; 
            }
            
            var clickTarget = target;
            var parentTarget = target.parentNode.parentNode;
            var column = this;
            var record = grid.store.getAt(rowIndex);
            var value;
            
            window.setTimeout(function(){
                var radios = parentTarget.getElementsByTagName('INPUT');
                for (var i = 0; i < radios.length; i++) {
                    if (radios[i].checked) {
                        value = radios[i].value;
                    }
                }
                
                var params = {};
                params[column.dataIndex] = value;
                if(false === column.fireEvent('linkclick', column, record, column.dataIndex, value, params)) {
                    return false;
                }
                
                if (column.fieldConfig.url) {
                    var url = column.fieldConfig.url;
                    var urlParams = new Array();
                    for (var field in params) {
                        if (Ext.isArray(params[field])) {
                            for (var i = 0; i < params[field].length; i++) {
                                urlParams.push(field + '=' + params[field][i]);
                            }
                        } else {
                            urlParams.push(field + '=' + params[field]);
                        }
                    }
                    
                    if (url.indexOf('?') > -1) {
                        url = url + '&' + urlParams.join('&');
                    } else {
                        url = url + '?' + urlParams.join('&');
                    }
                    
                    if (!column.openWindow) {
                        var win = column.getOpenWindow();
                        grid.on('beforedestroy', function() {
                            win.hide();
                            win.destroy();
                        });
                    }
                    
                    var win = column.getOpenWindow();
                    win.setTitle(column.windowConfig.title);
                    win.show();
                    
                    var framePanel = column.getOpenFrame();
                    framePanel.setSrc(url);
                } else if (column.fieldConfig.js) {
                    column.initPropertyPanel(params, grid);
                }
            }, 200);
            
            return false; 
        } else {
            return Ext.ux.grid.LinksColumn.superclass.processEvent.apply(this, arguments);
        }
    },
    
    getOpenWindow : function() {
        if (this.openWindow) {
            return this.openWindow;
        } else {
            this.initOpenWindow();
            return this.openWindow;
        }
    },
    
    getOpenFrame : function() {
        if (this.openWindow) {
            return this.openWindow.find('xtype', 'iframepanel')[0];
        } else {
            this.initOpenWindow();
            return this.openWindow.find('xtype', 'iframepanel')[0];
        }
    },
    
    initOpenWindow : function() {
        var framePanel = new Ext.ux.ManagedIFramePanel({
            xtype : 'iframepanel',
            border : false,
            bodyBorder : false,
            autoScroll : true,
            autoLoad : false,
            defaultSrc : null,
            frame : false,
            listeners : {
                domready : function(frame) {
                    
                }
            }
        });
        this.windowConfig.layout = 'fit';
        this.windowConfig.closeAction = 'hide';
        this.openWindow = new Ext.Window(this.windowConfig);
        this.openWindow.add(framePanel)
        
    },
    
    initPropertyPanel  : function(params, grid) {
        var column = this;
        if (!column.propertyPanel) {
            Ext.Ajax.request({
                method : 'GET',
                url : column.fieldConfig.js,
                scope : this,
                success : function(response, options) {
                    column.propertyPanel = eval(response.responseText);
                    grid.on('beforedestroy', function() {
                        column.propertyPanel.destroy();
                    });
                    grid.getStore().on('load', function(){
                        column.propertyPanel.clearData();
                    });
                    column.rendererPropertyPanel(params);
                }
            });
        } else {
            column.rendererPropertyPanel(params);
        }
    },
    rendererPropertyPanel : function(params) {
        var column = this;
        if (Ext.isString(column.propertyConfig.getRenderTo())) {
           var dom = Ext.getDom(column.propertyConfig.getRenderTo());
           column.propertyPanel.render(dom);
        } else if (Ext.isElement(column.propertyConfig.getRenderTo())) {
            column.propertyPanel.render(column.propertyConfig.getRenderTo());
        } else {
            var renderToPanel = column.propertyConfig.getRenderTo();
            if (!column.propertyConfig.propertyPanel) {
                if (renderToPanel.xtype == 'tabpanel' || renderToPanel instanceof Ext.TabPanel) {
                    renderToPanel.add({
                        title : column.propertyConfig.title,
                        layout : 'fit',
                        items : [column.propertyPanel]
                    });
                    renderToPanel.setActiveTab(column.propertyPanel.ownerCt);
                } else {
                    renderToPanel.add(column.propertyPanel);
                    renderToPanel.doLayout();
                }
            } else {
                 if (renderToPanel.xtype == 'tabpanel' || renderToPanel instanceof Ext.TabPanel) {
                    renderToPanel.setActiveTab(column.propertyPanel.ownerCt);
                 }
            }
            column.propertyConfig.propertyPanel = true;
        }
        column.propertyPanel.loadData(params);
    },

    renderer : function(v, p, record){
        
        this.fieldConfig = this.fieldConfig || {}
        v = Ext.isEmpty(v) ? '' : '' + v;
        var vs = v.split(',');
        
        var store = record.get(this.dataIndex + '_STORE') || this.fieldConfig.store || this.fieldConfig.rendererStore;
        var valueField = this.fieldConfig.valueField;
        var displayField = this.fieldConfig.displayField;
        valueField = valueField || displayField;
        displayField = displayField || valueField;
        
        var htmls = new Array();
        var id = Ext.ux.util.getCompId();
        var name = 'radio_' + id; 
        var formatHtml = '<span><input type="radio" name="{0}" id="{1}" value="{2}" style="width:0px;' + (Ext.isIE ? '' : 'display:none;') + '" /></span><a href="javascript:void(0)"  class="x-grid3-link-col"><label for="{1}">{3}</label></a>';
        
        for (var i = 0; i < vs.length; i++) {
            
            var text = vs[i];
            if (!Ext.isEmpty(store)) {
                var index = store.data.findIndexBy(function(rec){
                     return ('' + rec.get(valueField)) === ('' + vs[i]);
                }, store);
                
                if (index > -1) {
                    text = store.getAt(index).get(displayField);
                }
            }
            
            htmls.push(String.format(formatHtml, name, id, vs[i], text));
            id = Ext.ux.util.getCompId();
        }
        
       
        return htmls.join(', ');
    },

    init: Ext.emptyFn
});

// register ptype. Deprecate. Remove in 4.0
Ext.preg('linkscolumn', Ext.ux.grid.LinksColumn);

// backwards compat. Remove in 4.0
Ext.grid.LinksColumn = Ext.ux.grid.LinksColumn;

// register Column xtype
Ext.grid.Column.types.linkscolumn = Ext.ux.grid.LinksColumn;

