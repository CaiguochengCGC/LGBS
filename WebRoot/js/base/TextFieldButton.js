
// 自定义输入框与按钮组合组件
Ext.ns('ht.base');
ht.base.TextFieldButton = Ext.extend(Ext.form.TextField,  {
    /**
     * @cfg {String} buttonText The button text to display on the upload button (defaults to
     * 'Browse...').  Note that if you supply a value for {@link #buttonCfg}, the buttonCfg.text
     * value will be used instead if available.
     */
    buttonText: '',
    
    /**
     * 只读时，隐藏按钮
     * @type Boolean
     */
    hideButtonOnReadOnly : true,
    
    /**
     * @cfg {Boolean} buttonOnly True to display the file upload field as a button with no visible
     * text field (defaults to false).  If true, all inherited TextField members will still be available.
     */
    buttonOnly: false,
    /**
     * @cfg {Number} buttonOffset The number of pixels of space reserved between the button and the text field
     * (defaults to 3).  Note that this only applies if {@link #buttonOnly} = false.
     */
    buttonOffset: 3,
    /**
     * @cfg {Object} buttonCfg A standard {@link Ext.Button} config object.
     */

    // private
    readOnly: false,
    
    isCombo : false, //类似下拉框功能
    isLovCombo : false, //类似下拉多选框功能
    comboStore : null, //下拉框store
    comboValueField : '', //值字段
    comboTextField : '', //文本字段

    /**
     * @hide
     * @method autoSize
     */
    autoSize: Ext.emptyFn,
    
    // private
    initComponent: function(){
        var fieldStyle = this.style ? this.style : '';
        this.style = 'float:left;margin-top:0.5px;' + fieldStyle;
        this.addClass('ht-textfield-field');
        ht.base.TextFieldButton.superclass.initComponent.call(this);
        
    },

    // private
    onRender : function(ct, position){
        ht.base.TextFieldButton.superclass.onRender.call(this, ct, position);

        this.wrap = this.el.wrap({cls:'x-form-field-wrap x-form-field-trigger-wrap ht-textfield-form'});
        this.createFileInput();
        
        var buttonCfg = this.buttonCfg || {};
        var iconCls = buttonCfg.iconCls || '';
        
        //过度
        iconCls = 'query' == iconCls ? 'query-small' : iconCls; //过度
        
        iconCls = 'ht-textfield-button ' + iconCls;
        buttonCfg.iconCls = iconCls;
        
        var btnCfg = Ext.applyIf(buttonCfg, {
            text: this.buttonText
        });
        
        var x_form_fieldBtn = this;
        this.fieldID = this.id;
        this.button = new Ext.Button(Ext.apply(btnCfg, {
            renderTo: this.wrap,
            cls: 'x-form-file-btn ' + (btnCfg.iconCls ? ' x-btn-icon' : ''),
            style : 'float:left;',
            width : 16,
            handler : this.btnFn,
            scope : x_form_fieldBtn
        }));

        if(this.buttonOnly){
            this.el.hide();
            this.wrap.setWidth(this.button.getEl().getWidth());
        }

        this.bindListeners();
        this.resizeEl = this.positionEl = this.wrap;
    },
    
    bindListeners: function(){
        this.fileInput.on({
            scope: this,
            mouseenter: function() {
                this.button.addClass(['x-btn-over','x-btn-focus'])
            },
            mouseleave: function(){
                this.button.removeClass(['x-btn-over','x-btn-focus','x-btn-click'])
            },
            mousedown: function(){
                this.button.addClass('x-btn-click')
            },
            mouseup: function(){
                this.button.removeClass(['x-btn-over','x-btn-focus','x-btn-click'])
            },
            change: function(){
                var v = this.fileInput.dom.value;
                this.setValue(v);
//                this.fireEvent('fileselected', this, v);    
            }
        }); 
    },
    
    createFileInput : function() {
        this.fileInput = this.wrap.createChild({
            id: this.getFileInputId(),
            name: this.getFileInputId(),
            cls: 'x-form-file',
            tag: 'input',
            type: 'text',
            style : 'display : none;',
            size: 1
        });
    },
    
    reset : function(){
        this.fileInput.remove();
        this.createFileInput();
        this.bindListeners();
        ht.base.TextFieldButton.superclass.reset.call(this);
    },
    
    hideButton : function(hiddenButton) {
        if (hiddenButton) {
            this.button.hide();
        } else {
            this.button.show();
        }
        this.onResize(this.getWidth(), this.getHeight());
    },

    // private
    getFileInputId: function(){
        return this.id + '-field-button';
    },

    // private
    onResize : function(w, h){
        ht.base.TextFieldButton.superclass.onResize.call(this, w, h);
        
        if(!this.buttonOnly){
            var fieldDom = Ext.getDom(this.id);
            if (!this.button.hidden) {
                w += 3; //空格
                var w = w - this.button.getEl().getWidth() - this.buttonOffset;
                fieldDom.style.marginTop = '0.5px';
                fieldDom.style.marginBottom = '0px';
            } else {
                fieldDom.style.marginTop = '0px';
                fieldDom.style.marginBottom = '0.5px';
            }
            this.el.setWidth(w);
        } else {
            this.wrap.setWidth(w);
        }
    },

    // private
    onDestroy: function(){
        ht.base.TextFieldButton.superclass.onDestroy.call(this);
        Ext.destroy(this.fileInput, this.button, this.wrap);
    },
    
    onDisable: function(){
        ht.base.TextFieldButton.superclass.onDisable.call(this);
        this.doDisable(true);
    },
    
    onEnable: function(){
        ht.base.TextFieldButton.superclass.onEnable.call(this);
        this.doDisable(false);

    },
    
    // private
    doDisable: function(disabled){
        this.fileInput.dom.disabled = disabled;
        this.button.setDisabled(disabled);
    },
    
    getValue : function() {
       var text = ht.base.TextFieldButton.superclass.getValue.call(this);
       if ((this.isCombo || this.isLovCombo) && !Ext.isEmpty(text, false)) {
          var v = this.comboValue;
          if (Ext.isEmpty(v)) {
              v = text;
          }
          return v;
       } else {
          return text;
       }
    },
    
    setValue : function(value) {
       var startValue = this.getValue();
       ht.base.TextFieldButton.superclass.setValue.call(this, value);
       if (this.isCombo) {
          this.comboValue = value;
          var text = value;
          
          if (!Ext.isEmpty(value, false) && this.comboStore) {
              var comboValueField = this.comboValueField;
              
              var index = this.comboStore.data.findIndexBy(function(rec){
                     return '' + rec.get(comboValueField) === '' + value;
                }, this);
                
              if (index > -1) {
                  text = this.comboStore.getAt(index).get(this.comboTextField);
              }
          }
          this.setRawValue(text);
          
       } else if (this.isLovCombo) {
          this.comboValue = value;
          var text = value;
          
          if (!Ext.isEmpty(value, false) && this.comboStore) {
              text = '';
              var comboValueField = this.comboValueField;
              var vals = value.split(',');
              for (var i = 0; i < vals.length; i++) {
                  value = vals[i];
                  if (Ext.isEmpty(value, false)) {
                      continue;
                  }
                  var index = this.comboStore.data.findIndexBy(function(rec){
                        return '' + rec.get(comboValueField) === '' + value;
                    }, this, 0);
                  if (index > -1) {
                      text += this.comboStore.getAt(index).get(this.comboTextField) + ',';
                  } else {
                      text += value + ',';
                  }
              }
              text += ',,,';
              text = text.replace(',,,,', '').replace(',,,', '');
          }
          
          
          this.setRawValue(text);
       }
       var endValue = this.getValue();
       
       if(String(endValue) !== String(startValue)){
            this.fireEvent('change', this, endValue, startValue);
       }
    },
    
    // private
    preFocus : Ext.emptyFn,

    // private
    alignErrorIcon : function(){
        this.errorIcon.alignTo(this.wrap, 'tl-tr', [2, 0]);
    }

});

Ext.reg('textfieldbutton', ht.base.TextFieldButton);