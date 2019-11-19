/**
 * 国际化JS
 */
//多选框
Ext.namespace('ht.msg.combo');
ht.msg.combo = {
    emptyText : '请选择'
}

//提示框
Ext.namespace('ht.msg.msgbox');
ht.msg.msgbox = {
    infoTitle : '提示<info />',
    waringTitle : '提示<waring />',
    errorTitle : '提示<error />',
    confirmTitle : '提示<confirm />',
    waitTitle : '提示<wait />',
    processTitle : '提示<process />',
    progressText : '正在初始化...'
}

//校验信息
Ext.namespace('ht.msg.valid');
ht.msg.valid = {
    clientInvalid : '提交数据校验失败！',
    connectFailure : '服务器或网络异常！',
    error404 : '找不到指定页面！',
    connectOther : '错误代码:{0}<br />错误信息:{1}',
    maxLengthText : '长度不能超过{0}',
    compareTime : '结束时间应晚于开始时间',
    compareDate : '结束日期应不早于开始日期'
}

//分页栏
Ext.namespace('ht.msg.pagerbar');
ht.msg.pagerbar = {
    pageSizeMsg : '每页行数' + ht.config.label.SEPARATOR,
    emptyMsg : '没有记录',
    displayMsg : '显示第{0}条到{1}条记录,一共{2}条'
}

//导出Excel
Ext.namespace('ht.msg.excel');
ht.msg.excel = {
    exportText : '导出Excel',
    defaultExcelName : 'MES',
    error : {
        noGrid : '表格不存在！',
        noData : '无数据导出！',
        noSetting : '请安装Microsoft Excel 2007或将该网站增加到“受信任的站点”，并将“受信任的站点”的安全级别设置为“低”。',
        fileOpened : '{0} 正在使用，无法保存！',
        filePath : '请将该网站增加到“受信任的站点”，并将“受信任的站点”的安全级别设置为“低”。'
    }
}

//打印
Ext.namespace('ht.msg.printer');
ht.msg.printer = {
    print : '打印',
    preview : '预览',
    printConfirm : '确定要打印该表格？',
    error : {
        noTemplate : '打印出错，请与管理员联系！',
        noData : '无数据打印！'
    },
    reportPrinted : '打印完成。',
    reportUrl : '请设置REPORT_URL参数！',
    reportGet : '获取报表失败！',
    printer : '未找到打印机 {0}',
    reportPrinting : '有其他报表正在打印！',
    reportViewing : '有其他报表正在预览！'
}

//基础
Ext.namespace('ht.msg.base');
ht.msg.base = {
    processText : '正在操作中...',
    toText : '至',
    createText : '创建',
    queryText : '查询',
    dealwith : '重新处理',
    resetText : '重置',
    submitText : '确定',
    cancelText : '取消',
    refreshText : '刷新',
    
    addText : '新增',
    addSuccessText : '添加成功！',
    
    saveText : '保存',
    saveConfirmText : '确定要保存？',
    saveSuccessText : '保存成功！',
    
    deleteText : '删除',
    deleteConfirmText : '确定要删除？',
    deleteSuccessText : '删除成功！',
    
    updateText : '修改',
    updateConfirmText : '确定要修改？',
    updateSubText : '修改成功！',
    updateSuccessText : '修改成功！',
    
    clearText : '清空',
    clearFieldText : '清除',
    clearConfirmText : '确定要清空？',
    clearSuccessText : '清空成功！',
    
    closeText : '关闭',
    
    otherPropertyTitle : '行额外属性',
    
    columnEvalError : '列扩展数据转换异常/数据无效，列主键：{0}，列属性：{1}',
    columnRendererError : '列显示数据源无效，列主键：{0}',
    
    grid : {
        lockText : '锁定',
        unlockText : '解锁',
        menuFilterText : '列过滤',
        dateFilter : {
            afterText : '不早于',
            beforeText : '不晚于',
            onText : '为'
        }
    },
    
    editor : {
        saveText : '保存',
        cancelText : '取消',
        commitChangesText : '必须提交或取消所作的更改!',
        errorText : 'Errors'
    },
    
    window : {
        addText : '添加',
        updateText : '修改'
    }
}

if(Ext.DateTimePicker){
    Ext.DateTimePicker.prototype.timeLabel = '时间';
}

/**
 * (main.js)主页面
 */
Ext.namespace('ht.msg.main'),
ht.msg.main = {
    
    //页面标题
    title : '上汽PMC报表系统 - 主页', 
    
    //js加载
    loaddingData : '正在加载数据...',
    redirtPage : '{0}秒后自动跳转到登陆页面。',
    
    //标签tab
    closeTabText : '关闭',
    closeOtherTabsText : '关闭其他',
    closeAllTabsText : '关闭所有',
    portalWelcome : '欢迎',
    
    //顶部菜单
    barHelp : '帮助',
    otherSystem : '<span style="color:#0000FF;">其他系统</span>',
    currentModule : '当前模块:',
    currentUser : '账号:',
    currentUserName : '姓名:',
    modifyPassword : '<span style="color:#0000FF;">修改密码</span>',
    exit : '<span style="color:#0000FF;">退出系统</span>',
    exitConfirm : '确定退出？',
    week : ' 星期',
    weekFormat : 'D',
    
    //顶部菜单-风格下拉框
    themeEmptyText : '选择风格', 
    themeCssDefault : '默认风格',
    themeGray : '灰色风格',
    themeSlate : '墨绿风格',
    
    //左菜单
    menuTreeRoot : '功能列表',
    menuTreeTitle : '报表系统功能列表', 
    
    //修改密码
    currentPassword : '当前密码',
    newPassword : '新密码',
    conformNewPassword : '确认密码',
    conformNewPasswordVild : '两次密码不一致！',
    modifyPasswordToLogout : '修改密码成功，请用新密码登录！',
    passwordWinTitle : '修改密码',
    msgPasswordTitle : '新消息',
    msgPasswordContent : '您的密码为原始密码，请注意修改。',
    msgBoxHide : '关闭',
    userInfoValid : '有其他用户或其他业务登录，将刷新页面！'
}




