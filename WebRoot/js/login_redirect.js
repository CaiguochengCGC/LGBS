
Ext.onReady(function(){
	Ext.BLANK_IMAGE_URL = 'lib/ext3.3.0/resources/images/default/s.gif';
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
    Ext.Msg.minWidth = config.msgWidth;
    
    var msgLogin = msg;
    var cookieKey = 'REM_PSD_';
	
    //提交
    var params = {};
    var userName;
    var url = '' + window.location; //获取url中'?'符后的字串
    if (url.indexOf('?') != -1) {
        var urlSearch = url.substr(url.indexOf('?') + 1);
        var searchParams = urlSearch.split('&');
        for(var i = 0; i < searchParams.length; i++) {
            var searchParam = searchParams[i].split('=');
            params[searchParam[0]] = unescape(searchParam[1]);
        }
    }
    userName = params['UserName'];
    params['action'] = 'LOGIN_REDIRECT_ACTION';
    Ext.Ajax.request({
        url : 'json',
        params : params,
        success : function(response, options) {
            var result = Ext.decode(response.responseText)
            if(result.success){  
                            
                var date = new Date();
                date.setTime(date.getTime() + 30 * 24 * 3066 * 1000);
                document.cookie = cookieKey + '$USER_NAME$=' + userName + ';expires=' + date.toGMTString();
                
                window.location = 'main.html';
            }else {
                
                //去除加载图标
                Ext.get('loading').remove();
                Ext.get('loading-mask').fadeOut({
                    remove: true
                })
            
                //提示
                Ext.MessageBox.show({
                    title:msgLogin.errorTitle,
                    msg: result.errors.errmsg,
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.Msg.ERROR,
                    fn : function() {
                        window.location = 'index.html';
                    }
                });
            }
        },
        failure : function(response, options) {
            
            //去除加载图标
            Ext.get('loading').remove();
            Ext.get('loading-mask').fadeOut({
                remove: true
            })
        
            //提示
            Ext.MessageBox.show({
                title:msgLogin.errorTitle,
                msg: msgLogin.connectFailure,
                buttons: Ext.MessageBox.OK,
                icon: Ext.Msg.ERROR,
                fn : function() {
                    window.location = 'index.html';
                }
            });
        }
    });
    
})