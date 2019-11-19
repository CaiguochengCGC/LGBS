
// 定义全局常量，一旦确定就不能修改
Ext.namespace('ht.pub.global');
ht.pub.global.FIRST_NODE_NAME = 'ALL';
ht.pub.global.CURRENT_USER_NAME = ''; // 当前用户名
ht.pub.global.CURRENT_USER_CNAME = '';// 当前用户名称
ht.pub.global.CURRENT_USER_ACTIONS = []; //当前用户的功能权限
ht.pub.global.CURRENT_DATE = null; //当前日期
ht.pub.global.IS_DEVELOP = 1;

var msgMain = ht.msg.main;

//调整表格单元格
if (!Ext.grid.GridView.prototype.templates) {
   Ext.grid.GridView.prototype.templates = {};
}
Ext.grid.GridView.prototype.templates.cell = new Ext.Template(
   '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}"style="{style}" tabIndex="0" {cellAttr}>',
   '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>',
   '</td>'
)

Ext.namespace('ht.main');

// 从cookie得到上次的主题
ht.main.renderThemeFromCookie = function() {
    var cookiesArr = document.cookie.split(';');
    var cssName = '';
    for (var i = 0; i < cookiesArr.length; i++) {
        var arr = cookiesArr[i].split('=');
        if (('' + arr[0]).trim() == 'extjs-css') {
            cssName = arr[1];
            break;
        }
    };
    if (Ext.isEmpty(cssName, false)) {
         cssName  = 'xtheme-gray';
    }
    Ext.util.CSS.swapStyleSheet('theme', 'lib/ext-3.3.0/resources/css/' + cssName + '.css');
    Ext.util.CSS.swapStyleSheet('ht_grid_theme', 'css/grid.css');
}
ht.main.renderThemeFromCookie();

//获取cookie
ht.main.renderFromCookie = function(key) {
    var cookiesArr = document.cookie.split(';');
    var value = '';

    for (var i = 0; i < cookiesArr.length; i++) {
        var arr = cookiesArr[i].split('=');
        if (('' + arr[0]).trim() == key) {
            value = arr[1];
            break;
        }
    };
    return value;
}

//加载资源
ht.main.loadResource = function(node) {
    
    if (Ext.isEmpty(node, false)) {
        return;
    }
    
    var resoures = '';
    if (node instanceof Array) {
        resoures = node.join('/').toLowerCase() +  '/' + node[node.length - 1].toLowerCase();
    } else {
        resoures = node;
    }
    
    var resourcePri = 'RESOURCE_';
    var language = 'zh_CN';
    var id = resourcePri + resoures.replace(/\//ig, '_') .toUpperCase();
    
    if (ht.pub.global.YESNO.YES = ht.pub.global.IS_DEVELOP) {
        
        var oScript = document.getElementById(id);
        if (oScript) {
            oScript.parentNode.removeChild(oScript);
        }
    }
    debugger;
    if (!document.getElementById(id)){ 
        
        var oHead = document.getElementsByTagName('HEAD').item(0); 
        var oScript = document.createElement('script');
        oScript.language = 'javascript'; 
        oScript.type = 'text/javascript'; 
        oScript.id = id; 
        oScript.src = 'js/' + resoures + '_' + language + '.js'; 
        oHead.appendChild(oScript); 
    } 
}

/**
 * 展开菜单、
 * @param {} node 菜单节点
 * @return {Number}
 */
ht.main.clickTreeNode = function(node){
    if(!node.leaf){
        return 0;
    }
    
    ht.main.loadResource(node.ctPropotys.resoures);
    
    /**
     * @type {Ext.TabPanel}
     */
    var tabs = ht.main.MENU_TABS;
    var menu_tab = '_DEFINE_MENU_';
    var obj = Ext.getCmp(menu_tab + node.ctPropotys.menuId);
    if (obj) {
        tabs.setActiveTab(obj);
    } else {
        
        var menuId = '' + node.ctPropotys.menuId;
        if ('L' == menuId.charAt(menuId.length - 1)) {
            window.open(node.ctPropotys.nodeId);
            return;
        }
        
        //增加标签
        var tab = tabs.add({
            id : menu_tab + node.ctPropotys.menuId,
            title : node.text,
            ctPropotys : node.ctPropotys,
            layout : 'fit',
            closable : true
        });
        
        var loadMask = new Ext.LoadMask(tabs.body, {
            msg : msgMain.loaddingData
        });
        loadMask.show();
        
        Ext.Ajax.request({
            method : 'GET',
            url : 'js/' + node.nodeId + '.js',
            scope : this,
            success : function(response, options) {
                temp = eval(response.responseText);
                tab.add(temp);
                
                if (temp.initParamCode) {
                    temp.initParamCode(node.ctPropotys.paramCode);
                }
                loadMask.hide();
                tabs.setActiveTab(tab);
            },
            failure : function(response, options) {
                tabs.remove(node.nodeId);
                ht.pub.handleAjaxErrors(response);
                loadMask.hide();
            }
        });
    }
}

//退出系统
ht.main.logout = function() {
        
    //退出系统
    Ext.Ajax.request({
        url : 'json',
        params :{action : 'LOGOUT_ACTION'},
        success : function(response, options) {
            var result = Ext.decode(response.responseText)
            if(result.success){
                window.location = 'index.html';
            } else {
                ht.pub.error(result.errors.errmsg);
            }
        },
        failure : function(response, options) {
            ht.pub.handleAjaxErrors(response)
        }
    });
}

/**
 * 消息提示
 */
ht.pub.msgtip = function(){
    var msgMContents = [];
    var msgCt;
    var msgType = 0;
    
    /**
     * 创建消息内容
     */
    function createMsgBox(t, s, type){
        return ['<div class="msg">',
                '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
                '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">',
                '<div><h3>', t, '</h3></div>', 
                '<div class="content">', s, '</div>', 
                '<div class="hide-action"><a href="javascript:void(0)" onclick="ht.pub.msgtip.hideMsg(' + type + ')">' + msgMain.msgBoxHide + '</a></div>',
                '</div></div></div>',
                '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
                '</div>'].join('');
    }
    
    return {
        
        /**
         * 获取消息类型（防消息重复）
         */
        getMsgType : function() {
            var type = msgType;
            msgType++;
            
            return type;
        },
        
        /**
         * 创建提示框
         */
        msg : function(type, title, format){
            ht.pub.msgtip.hideMsg(type);
            type = type || 0;
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {cls:'msg-div'}, true);
            }
            var s = String.format.apply(String, Array.prototype.slice.call(arguments, 2));
            var m = Ext.DomHelper.append(msgCt, {html:createMsgBox(title, s, type)}, true);
            var mslide = m.slideIn('t');
            msgMContents[type] = mslide;
            
            //去除自动定位
            var msgCtDom = Ext.getDom(msgCt.id);
            msgCtDom.style.left = null;
            msgCtDom.style.top = null;
            
            //自动隐藏
//            window.setTimeout(function(){
//                ht.pub.msgtip.hideMsg(type);
//            }, 20 * 1000);
        },
        init : function(){
            var lb = Ext.get('lib-bar');
            if(lb){
                lb.show();
            }
        },
        
        /**
         * 隐藏提示框
         */
        hideMsg : function(type) {
            type = type || 0;
            if (msgMContents[type]) {
                msgMContents[type].ghost('tr', {remove:true});
            }
            msgMContents[type] = null;
        }
    };
}();


Ext.onReady(function() {
    
	//设置
    Ext.Ajax.timeout = 3 * 60 * 1000;
	Ext.BLANK_IMAGE_URL = 'lib/ext-3.3.0/resources/images/default/s.gif';
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = ht.config.style.form.msgTarget;
    Ext.Msg.minWidth = ht.config.msgbox.MessageBoxWidth;
    
    //标签tab
	var tabs = new Ext.TabPanel({
		id : 'tabs',
		region : 'center',
		margins : '0 5 0 0',
		minTabWidth : 135,
		plugins: new Ext.ux.TabCloseMenu({
			closeTabText: msgMain.closeTabText,
			closeOtherTabsText: msgMain.closeOtherTabsText,
			closeAllTabsText: msgMain.closeAllTabsText
		}),
		enableTabScroll : true,
		layoutOnTabChange : true,
		activeTab : 0,
		items : []
	});
    ht.main.MENU_TABS = tabs;
    
    //标签tab，标签改变事件
    tabs.on('tabchange',function(tabs){
        
        var params = {};
        params.action = 'INIT_MODULE_ACTION';
        params.MODULE_CODE = Ext.isEmpty(tabs.getActiveTab().ctPropotys) ? '' : tabs.getActiveTab().ctPropotys.category;
        
        Ext.Ajax.request({
            method : 'GET',
            url : 'json',
            params : params,
            scope : this,
            success : function(response, options) {
                var o = Ext.decode(response.responseText);
                if (o.root.MODULE) {
                    var text = '<span style="color:#0000FF;">' + o.root.MODULE.rs[0] + '</span>&nbsp;';
                    menuToolBar.find('name', 'currentModule')[0].setText(text,false);
                }
            },
            failure : function(response, options) {
                ht.pub.handleAjaxErrors(response);
            }
        }); 
    });
    
    //顶部菜单工具栏
	var menuToolBar = new Ext.Toolbar({
		renderTo : 'toolBar',
		items : ['->', /*{
            text : msgMain.barHelp,
            iconCls : 'help',
            handler : function(){
                new Ext.Window({
                    title : msgMain.barHelp,  
                    width : 500,
                    constrain : true,
                    height : 500
                }).show()
            }
        },'-',{
	  		text : msgMain.otherSystem,
	  		iconCls : 'link',
	  		handler : function(){
                
	  		}
	  	}, '-', */{
            xtype : 'tbtext',
            text : msgMain.currentModule
		 },{
            xtype : 'tbtext',
            name : 'currentModule'
         },'-',{
	  		iconCls : 'date'
	  	 },{
            xtype : 'tbtext',
            name : 'currentDate'
		 },'-', {
		  		iconCls : 'user'
		 },{
            xtype : 'tbtext',
	  		text : msgMain.currentUser
	  	},{
            xtype : 'tbtext',
            name : 'currentUser'
        },{
            xtype : 'tbtext',
            text : msgMain.currentUserName
        },{
            xtype : 'tbtext',
            name : 'currentUserName'
        },'-',{
            text : msgMain.modifyPassword,
            iconCls : 'modifyPsd',
            handler : function(){
                ht.main.passwordWin.show();
            }
        },'-',{
			text : msgMain.exit,
			iconCls : 'logout',
			labelAlign : 'center',
			handler : function(){
                ht.pub.confirm(msgMain.exitConfirm, function(btn) {
                    if (btn != 'yes') {
                        return;
                    }
                    ht.main.logout();
                });
			}
		 },'-',{
            xtype : 'combo',
			width : 100,
			triggerAction : 'all',
			mode : 'local',
            editable : false,
			emptyText : msgMain.themeEmptyText,
			valueField : 'value',
            value : ht.main.renderFromCookie('extjs-css'),
			displayField : 'text',
			store : new Ext.data.SimpleStore({
				fields : ['value', 'text'],
				data : [
					['xtheme-gray', msgMain.themeGray],
					['xtheme-slate', msgMain.themeSlate],
                    ['ext-all', msgMain.themeCssDefault]
				]
			}),
            listeners : {
                select : function(combo){
                    var name = combo.getValue();
                    var date = new Date();
                    date.setTime(date.getTime() + 30 * 24 * 3066 * 1000);
                    
                    // 设置cookies
                    document.cookie = 'extjs-css=' + name + ';expires=' + date.toGMTString();
                    
                    Ext.util.CSS.swapStyleSheet('theme', 'lib/ext-3.3.0/resources/css/' + name + '.css');
                    Ext.util.CSS.swapStyleSheet('ht_grid_theme', 'css/grid.css');
                }   
            }
		}]

	});
    
    //左侧菜单树
	var treeLoader = new Ext.tree.TreeLoader({dataUrl: 'json?action=GET_MENU_ACTION'})
	var menuTree = new Ext.tree.TreePanel({
		title : msgMain.menuTreeTitle,
		iconCls : 'menu',
		frame : true,
		region : 'west',
		width : 200,
		minSize : 150,
		split : true,
		collapsible : true,
		autoScroll : true,
		loader : treeLoader,
		root: new Ext.tree.AsyncTreeNode({
            text : msgMain.menuTreeRoot,
            
            leaf : false
        })
        
	});
    ht.pub.global.MENU_TREE = menuTree;
    menuTree.getRootNode().ctPropotys = {category : ht.pub.global.FIRST_NODE_NAME};
	menuTree.on('beforeload',function(node){
        var category = Ext.isEmpty(node.ctPropotys) ? '' : node.ctPropotys.category;
        
		treeLoader.dataUrl = 'json?action=GET_MENU_ACTION&MODULE_CODE=' +  category;
	});
    
    //菜单是否展开
    menuTree.getRootNode().expand(false, true);
    
    //树点击事件     
    menuTree.on('click',function(node){
        switch (node.text) {
            case 'exit' :
                window.location = node.nodeId + '.ashx';
                break;
            default :
                ht.main.clickTreeNode(node);
        }
    }); 

    
	//首页布局
	new Ext.Viewport({
		id : 'westView',
		layout : 'border',
		items : [
			new Ext.BoxComponent({
				region : 'north',
				el : 'panelTop',
				margins : '0 0 0 0'
			}), menuTree, tabs
		]
	});

    //获取用户信息，显示在顶部菜单栏
	Ext.Ajax.request({
		method : 'GET',
		url : 'json?action=INIT_USER_ACTION',
		scope : this,
		success : function(response, options) {
			var o = Ext.decode(response.responseText);
			if(o.success){
				if(o.root.RESULT.rs[0].result == 'ok'){
                    
                    var user = o.root.RESULT.rs[0].USER;
                    
                    ht.pub.global.CURRENT_USER_NAME = user.PK_USER_NAME;
                    ht.pub.global.CURRENT_USER_CNAME = user.USER_CNAME;
                    
                    //用户
                    var text = '<span style="color:#0000FF;">' + ht.pub.global.CURRENT_USER_NAME + '</span>&nbsp;&nbsp;';
                    menuToolBar.find('name', 'currentUser')[0].setText(text,false);
                    
                    //用户姓名
                    text = '<span style="color:#0000FF;">' + user.USER_CNAME + '</span>&nbsp;&nbsp;';
                    menuToolBar.find('name', 'currentUserName')[0].setText(text,false);
                    
                    //登录时间
                    var loginTime = user.LOGIN_TIME;
                    var currentDate = Date.parseDate(ht.pub.date.dateRenderer(loginTime), ht.config.format.DATE);
                    ht.pub.global.CURRENT_DATE = currentDate;
                    
                    var text = currentDate.format(ht.config.format.DATE)+ msgMain.week + currentDate.format(msgMain.weekFormat) 
                    menuToolBar.find('name', 'currentDate')[0].setText(text, false);
                    
                    //信息提示
                    if (ht.pub.global.YESNO.NO == user.IS_UPDATE_PWD) {
                        var msgType = ht.pub.msgtip.getMsgType();
                        ht.pub.msgtip.msg(msgType, msgMain.msgPasswordTitle, msgMain.msgPasswordContent);
                    }
                    
                    //功能权限
                    ht.pub.global.CURRENT_USER_ACTIONS = o.root.RESULT.rs[0].ACTIONS;
                    
				}else if(o.root.RESULT.rs[0].result == 'fail'){
                    ht.pub.error(o.root.RESULT.rs[0].error);
                    
				}else if(o.root.RESULT.rs[0].result == 'no_login'){
                    
                    var errorMsg =o.root.RESULT.rs[0].error;
                    var redirtPageSec = ht.config.params.redirtPageSec;
                    
                    var timeInv = window.setInterval(function() {
                        
                        //跳转页面
                        if (redirtPageSec < 0) {
                            window.location = 'index.html';
                            window.clearInterval(timeInv);
                            return;
                        }
                        
                        var msg = msgMain.redirtPage.replace('{0}', redirtPageSec);
                        ht.pub.error(errorMsg + msg);
                        redirtPageSec--;
                    }, 1000);
				}
			} else {
                
                var errorMsg = result.errors.errmsg;
                var redirtPageSec = ht.config.params.redirtPageSec;
                
                var timeInv = window.setInterval(function() {
                    
                    //跳转页面
                    if (redirtPageSec < 0) {
                        window.location = 'index.html';
                        window.clearInterval(timeInv);
                        return;
                    }
                    
                    var msg = msgMain.redirtPage.replace('{0}', redirtPageSec);
                    ht.pub.error(errorMsg + msg);
                    redirtPageSec--;
                }, 1000);
			}
		},
		failure : function(response, options) {
			ht.pub.handleAjaxErrors(response);
		}
	});	
    
    // 首页欢迎Portal
    var tab = tabs.add({
        title : msgMain.portalWelcome,
        layout : 'fit'
    });
    
    var loadMask = new Ext.LoadMask(tabs.body, {
        msg : msgMain.loaddingData
    });
    loadMask.show();
    
    //加载欢迎Portal
    Ext.Ajax.request({
        method : 'GET',
        url : 'js/welcome.js',
        scope : this,
        success : function(response, options) {
            temp = eval(response.responseText);
            tab.add(temp);
            loadMask.hide();
            tabs.setActiveTab(tab);
        },
        failure : function(response, options) {
            tabs.remove('welcomepage');
            ht.pub.handleAjaxErrors(response);
            loadMask.hide();
        }
    });
    
    //加载参数
    var paramCodes = ['MSGBOX_SHOW_TIME', 'PAGE_SIZE', 'MAX_PAGE_SIZE', 'SH_MONITOR_TIME'];
    var initSysParamStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.SYS_PARAM.rs',
        baseParams : {
            action : 'INIT_SYSPARAM_ACTION'
        },
        fields : ['PARAM_CODE', 'PARAM_VAL', 'PARAM_TYPE']
    });
    initSysParamStore.baseParams['PARAM_CODE'] = paramCodes;
    initSysParamStore.load({
        callback : function() {
            
            for (var i = 0; i < paramCodes.length; i++) {
                var value = ht.pub.getValueExactByKey(initSysParamStore, 'PARAM_CODE', paramCodes[i], 'PARAM_VAL');
                var type = ht.pub.getValueExactByKey(initSysParamStore, 'PARAM_CODE', paramCodes[i], 'PARAM_TYPE');
                if (!Ext.isEmpty(value)) {
                    if (type == 'NUMERIC') {
                        value = parseInt(value);
                    }
                    ht.config.params[paramCodes[i]] = value;
                }
            }
        }
    });
    
    //判断用户
    var cookieKey = 'REM_PSD_';
    var loginInfo = cookieKey + '${0}${1}$={2};expires=' + new Date(new Date().getTime() + 30 * 24 * 3066 * 1000).toGMTString();
    var cookieUserName = ht.main.renderFromCookie(cookieKey + '$USER_NAME$');
    document.cookie = String.format(loginInfo, cookieUserName, '', 0);
    
    var checkUserIntv = window.setInterval(function(){
        
        //判断当前用户是否存在
        var CURRENT_USER_NAME = ht.pub.global.CURRENT_USER_NAME;
        if (Ext.isEmpty(CURRENT_USER_NAME, false)) {
            return;
        }
        
        //是否已经验证完成
        var loginFlg = ht.main.renderFromCookie(cookieKey + '$' + CURRENT_USER_NAME + '$$');
        if (1 == loginFlg) {
            return;
        }
        
        //判断cookie存储
        var cookieUserName = ht.main.renderFromCookie(cookieKey + '$USER_NAME$');
        if (cookieUserName != CURRENT_USER_NAME) {
            
            //获取session的信息
            Ext.Ajax.request({
                method : 'GET',
                url : 'json?action=INIT_USER_ACTION',
                scope : this,
                success : function(response, options) {
                    var o = Ext.decode(response.responseText);
                    if(o.success){
                        if(o.root.RESULT.rs[0].result == 'ok'){
                            var user = o.root.RESULT.rs[0].USER;
                            if (user.PK_USER_NAME != CURRENT_USER_NAME) {
                                
                                window.clearInterval(checkUserIntv);
                                ht.pub.warning(msgMain.userInfoValid, function(){
                                    window.location = 'main.html';
                                });
                            } else {
                                document.cookie = String.format(loginInfo, user.PK_USER_NAME, '', 1);
                            }
                            
                        }else if(o.root.RESULT.rs[0].result == 'fail'){
                            ht.pub.error(o.root.RESULT.rs[0].error);
                            
                        }else if(o.root.RESULT.rs[0].result == 'no_login'){
                            
                            var errorMsg =o.root.RESULT.rs[0].error;
                            var redirtPageSec = ht.config.params.redirtPageSec;
                            
                            var timeInv = window.setInterval(function() {
                                
                                //跳转页面
                                if (redirtPageSec < 0) {
                                    window.location = 'index.html';
                                    window.clearInterval(timeInv);
                                    return;
                                }
                                
                                var msg = msgMain.redirtPage.replace('{0}', redirtPageSec);
                                ht.pub.error(errorMsg + msg);
                                redirtPageSec--;
                            }, 1000);
                        }
                    } else {
                        
                        var errorMsg = result.errors.errmsg;
                        var redirtPageSec = ht.config.params.redirtPageSec;
                        
                        var timeInv = window.setInterval(function() {
                            
                            //跳转页面
                            if (redirtPageSec < 0) {
                                window.location = 'index.html';
                                window.clearInterval(timeInv);
                                return;
                            }
                            
                            var msg = msgMain.redirtPage.replace('{0}', redirtPageSec);
                            ht.pub.error(errorMsg + msg);
                            redirtPageSec--;
                        }, 1000);
                    }
                },
                failure : function(response, options) {
                    ht.pub.handleAjaxErrors(response);
                }
            });
        }
        
    }, 500);
    
})

//禁用某些按键
ht.pub.ingoreKeysEvent(document);


//新密码与确认密码比较（QuickTips提示）
Ext.apply(Ext.form.VTypes,{
    password:function(val,field){
       if(field.confirmTo){
          var pwd = Ext.get(field.confirmTo);
          return (val == pwd.getValue());
       }
      return true;
    },
    
    /** 比较开始时间和结束时间
     * fromField : {comparentTo : '', getParentCompont : function(){}}
     * @param {} fromTime
     * @param {} fromField
     * @return {Boolean}
     */
    comparentTime : function(fromTime, fromField){
       if(fromField.comparentTo && fromField.getParentCompont()){
          var toTime = fromField.getParentCompont().find('name', fromField.comparentTo)[0].value;
          var format = fromField.format;
          fromTime = Date.parseDate(fromTime, format);
          toTime = Date.parseDate(toTime, format)
          
          if (!fromTime || !toTime) {
              return true;
          }
          return fromTime.getTime() > toTime.getTime();
        
       }
       return true;
    },
    
    /** 比较开始日期、结束日期
     * fromField : {comparentTo : '', getParentCompont : function(){}}
     * @param {} fromTime
     * @param {} fromField
     * @return {Boolean}
     */
    comparentDate : function(fromTime, fromField){
       if(fromField.comparentTo && fromField.getParentCompont()){
          var toTime = fromField.getParentCompont().find('name', fromField.comparentTo)[0].value;
          var format = fromField.format;
          fromTime = Date.parseDate(fromTime, format);
          toTime = Date.parseDate(toTime, format)
          
          if (!fromTime || !toTime) {
              return true;
          }
          return fromTime.getTime() >= toTime.getTime();
        
       }
       return true;
    },
    
     /** 比较生效时间和失效时间
     * fromField : {comparentTo : '', getParentCompont : function(){}}
     * @param {} fromTime
     * @param {} fromField
     * @return {Boolean}
     */
     comparisonTime : function(fromTime, fromField){
       if(fromField.comparentTo){
          var toTime = fromField.getParentCompont().find('name', fromField.comparentTo)[0].value;
          var format = fromField.format;
          fromTime = Date.parseDate(fromTime, format);
          toTime = Date.parseDate(toTime, format)
          
          if (!fromTime || !toTime) {
              return true;
          }
          return fromTime.getTime() < toTime.getTime();
        
       }
       return true;
    },
       /** 比较甲方与乙方
     * fromField : {comparentTo : '', getParentCompont : function(){}}
     * @param {} fromTime
     * @param {} fromField
     * @return {Boolean}
     */
    noEqual : function(val, fromField){
    	val = fromField.getValue();
        if(fromField.comparentTo){
            var carrier = fromField.getParentCompont().find('name', fromField.comparentTo)[0].getValue();
          
            if (Ext.isEmpty(val, false) || Ext.isEmpty(carrier, false)) {
                return true;
            }
            return val !== carrier;
        
         }
         return true;
    },
    defineValid : function(val, fromField){ //控件自定义方法校验
        return fromField.defineValid(val, fromField);
    }
});

// 修改密码的表单
ht.main.passwordFormPanel = new ht.base.PropertyPanel({
    formConfig : {
        useTo : 'win-edit',
        items : [{
            xtype: 'textfield',
            inputType: 'password',
            fieldLabel : msgMain.currentPassword,
            name : 'CURRENT_PASSWORD',
            allowBlank : false,
            columnWidth : 1
        }, {
            xtype : 'textfield',
            inputType: 'password',
            fieldLabel : msgMain.newPassword,
            name : 'MAIN_NEW_PASSWORD',
            allowBlank : false,
            columnWidth : 1
        },{
            xtype : 'textfield',
            inputType: 'password',
            fieldLabel : msgMain.conformNewPassword,
            name : 'CONFIRM_PASSWORD',
            allowBlank : false,
            vtype:'password',
            vtypeText: msgMain.conformNewPasswordVild,
            confirmTo:'MAIN_NEW_PASSWORD',
            columnWidth : 1
        }],
        submit : {
            action : 'MODIFY_PUB_USER_PASSWORD_ACTION'
        }
    },
    ctListeners : {
        beforeSubmit : function(values) {
            values['PK_USER_NAME'] = ht.pub.global.CURRENT_USER_NAME;
            values['CURRENT_PASSWORD'] = ht.main.passwordFormPanel.ht_outputFormPanel.getForm().findField('CURRENT_PASSWORD').getValue();
            values['NEW_PASSWORD'] = ht.main.passwordFormPanel.ht_outputFormPanel.getForm().findField('MAIN_NEW_PASSWORD').getValue();
            return true;
        },
        afterSubmit : function(action) {
            ht.pub.info(msgMain.modifyPasswordToLogout, function(){
                ht.main.logout();
            }, false)
        }
    }
});

// 修改密码的窗口
ht.main.passwordWin = new Ext.Window({
      width: 350,
      height: 170,
      title: msgMain.passwordWinTitle,
      plain: true,
      closable: true,
      resizable: false,
      frame: true,
      constrain : true,
      layout: 'fit',
      closeAction: 'hide',
      border: false,
      modal: true,
      items: ht.main.passwordFormPanel,
      listeners : {
          afterLayout : function () {
              ht.main.passwordFormPanel.disableFields(false);
          }
      }
});

