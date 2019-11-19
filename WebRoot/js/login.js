app={};

Ext.onReady(function(){
	Ext.BLANK_IMAGE_URL = 'lib/ext3.3.0/resources/images/default/s.gif';
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
    Ext.Msg.minWidth = config.msgWidth;
    
    var msgLogin = msg;
    var cookieKey = 'REM_PSD_';
    
    //登录form
	app.loginForm = new Ext.form.FormPanel({
		labelWidth: 92,
		labelAlign: config.label.ALIGN,
        labelSeparator : config.label.SEPARATOR,
		buttonAlign: 'center',
//        frame : true,
        border : false,
		defaults: {
			anchor: '90%'
		},
		items: [{
			xtype: 'textfield',
			name: 'USER_NAME',
			id: 'USER_NAME',
			fieldLabel: '',
			allowBlank: false,
            ctCls : 'ht-large-textfield'
		},{
			xtype: 'textfield',
			inputType: 'password',
			name: 'USER_PWD',
			fieldLabel: '',
			allowBlank: false,
            ctCls : 'ht-large-textfield ht-login-psd'
		}, {
            xtype: 'checkbox',
            name: 'COOKIE_PASSWORD',
            hidden : true,
            checked : false
        }],
        buttonAlign : 'center',
		buttons: [{
			id: 'submitButton',
			text: msgLogin.submitText,
			scope: this,
            width : 200,
            height : 40,
			handler: function(){
				app.submit();
			}
		}]
	});
    
    //登录面板
	new Ext.Panel({
		renderTo : 'loginpanel',
		layout : 'fit',
		width: 425,
        height: 200,
		border : false,
		defaults : {
			border : false
		},
		items : [app.loginForm]
	});
    
    //按键关联
	var map = new Ext.KeyMap(app.loginForm.getEl(), {  
         key : 13,  
         fn : function() {  
             app.submit(); 
         },
         scope : app.loginForm
     }); 
	
    //提交
	app.submit = function(){
		if(app.loginForm.form.isValid()){
            
            var values =  app.loginForm.getForm().getValues();
            var userName = values['USER_NAME'];
            var cookiePsd = values['COOKIE_PASSWORD'];
            
			app.loginForm.getForm().submit({
				url: 'json?action=LOGIN_ACTION',
				method: 'post',
				success: function(form, action){
                    
                    //COOKIE在main.html有使用到
                    if (!Ext.isEmpty(cookiePsd, false)) {
                        var date = new Date();
                        date.setTime(date.getTime() + 30 * 24 * 3066 * 1000);
                        document.cookie = cookieKey + '$USER_NAME$=' + userName + ';expires=' + date.toGMTString();
                    }
                    
					window.location = 'main.html';
				},
				failure: function(form, action){
                    Ext.MessageBox.show({
                        title:msgLogin.errorTitle,
                        msg: action.result.errors.errmsg,
                        buttons: Ext.MessageBox.OK,
                        icon: Ext.Msg.ERROR
                    });
				}
			})
		}
	};
	
    //定时删除加载
	setTimeout(function(){
		Ext.get('loading').remove();
		Ext.get('loading-mask').fadeOut({
			remove: true
		})
	}, 250);
    
    var renderFromCookie = function(userName) {
        var cookiesArr = document.cookie.split(';');
        var value = '';

        for (var i = 0; i < cookiesArr.length; i++) {
            var arr = cookiesArr[i].split('=');
            if (('' + arr[0]).trim() == cookieKey + userName) {
                value = arr[1];
                break;
            }
        };
        return value;
    }
    
    Ext.get('loginpanel').setStyle('position', 'absolute').center(Ext.getBody());
})