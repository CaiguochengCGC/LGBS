/**
 * 配置文件JS
 */
//标签
Ext.namespace('ht.config.label');
ht.config.label = {
    ALIGN : 'right',
    WIDTH : 100,
    SEPARATOR : ''
}

//提示框最少宽度
Ext.namespace('ht.config.msgbox');
ht.config.msgbox = {
    MessageBoxWidth : 300
}

//布局、样式
Ext.namespace('ht.config.layout');
ht.config.layout = {
    query : {
        COLUMN_WIDTH : 1 / 3, //一般查询
        ANCHOR : '90%'
    },
    winedit : {
        COLUMN_WIDTH : 1 / 2, //弹出窗口编辑
        ANCHOR : ''
    },
    winquery : {
        COLUMN_WIDTH : 1 / 2, //弹出窗口查询
        ANCHOR : ''
    },
    property : {
        COLUMN_WIDTH : 1, //属性编辑
        ANCHOR : ''
    },
    BUTTON_ALIGN : 'right',
    propertyPanelWidth : 280
}
ht.config.layout.getAnchor = function(colWidth) {
    var queryColWidth = ht.config.layout.query.COLUMN_WIDTH;
    var queryAnchor = ht.config.layout.query.ANCHOR;
    return (100 - (queryColWidth * (100 - parseFloat(queryAnchor))) / colWidth) + '%';
}
ht.config.layout.winedit.ANCHOR = ht.config.layout.getAnchor(ht.config.layout.winedit.COLUMN_WIDTH);
ht.config.layout.winquery.ANCHOR = ht.config.layout.getAnchor(ht.config.layout.winquery.COLUMN_WIDTH);
ht.config.layout.property.ANCHOR = ht.config.layout.getAnchor(ht.config.layout.property.COLUMN_WIDTH);

Ext.namespace('ht.config.style');
ht.config.style = {
    CLS_REQUIRE : 'ht-field-reqire',
    CLS_READONLY : 'ht-field-readonly',

    form : {
        msgTarget : 'side' //弹窗控件错误提示
    },
    roweditor : {
        msgTarget : 'qtip' //行编辑控件错误提示
    }
}

//表格样式
Ext.namespace('ht.config.grid');
ht.config.grid = {
    REMOVE_BORDER_LEFT : 'ht-gridpanel-border-left',
    REMOVE_BORDER_RIGHT : 'ht-gridpanel-border-right',
    REMOVE_BORDER_TOP : 'ht-gridpanel-border-top',
    REMOVE_BORDER_BOTTOM : 'x-rm-grid-border-bottom',
    RM_BORDER_LEFT : 'x-rm-grid-border-left',
    RM_BORDER_RIGHT : 'x-rm-grid-border-right',
    RM_BORDER_TOP : 'x-rm-grid-border-top',
    RM_BORDER_BOTTOM : 'x-rm-grid-border-bottom',
    CELL_EIDT_ABLE : 'ht-grid3-editable'
}

Ext.namespace('ht.config.tab');
ht.config.tab = {
    RM_BORDER_LEFT : 'x-rm-tab-border-left',
    RM_BORDER_RIGHT : 'x-rm-tab-border-right',
    RM_BORDER_TOP : 'x-rm-tab-border-top',
    RM_BORDER_BOTTOM : 'x-rm-tab-border-bottom'
}
//格式化
Ext.namespace('ht.config.format');
ht.config.format = {
    DATE : 'Y-m-d',
    DATETIME : 'Y-m-d H:i:s',
    TIME : 'H:i:s',
    HOURMI : 'H:i',
    HOURI : 'Hi'
}

//按钮
Ext.namespace('ht.config.button');
ht.config.button = {
    queryBtnName : 'query',
    resetBtnName : 'reset',
    addBtnName : 'add',
    updateBtnName : 'update',
    deleteBtnName : 'delete',
    excelBtnName : 'excel',
    printBtnName : 'print',
    previewBtnName : 'preview'
}

//window
Ext.namespace('ht.config.window');
ht.config.window = {
    width : 700,
    maxHeight : 500
}

//参数(可以通过数据库修改，但只允许改一次)
Ext.namespace('ht.config.params');
ht.config.params = {
    MSGBOX_SHOW_TIME : 1000, //弹出框显示时间(毫秒)
    PAGE_SIZE : 100, // 页面显示的行数 
    MAX_PAGE_SIZE : 500, //页面显示的最大行数
    redirtPageSec : 3, //会话失效，多长时间跳转其他页面（秒）
    DOUBLE_FIX : 3 //保留小数点数
}

//上传图片的格式
Ext.namespace('ht.config.picture');
ht.config.picture = {
    FORMAT : /.+\.((jpg)||(jpeg)||(gif)||(bmp))$/i
}
