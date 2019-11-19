(function() {
	//用户点击打开页面
	var newWin = window.open('main.html');
	//获取监控地址
	var pmcUrlStore = new Ext.data.JsonStore({
        url : 'json',
        root : 'root.PUB_DATA_DICT.rs',
        baseParams : {
            action : 'GET_CODE_VALUE_ACTION',
            CODE_TYPE : 'PMC_URL',
            QUERY_TYPE : 'PARAM'
        },
        fields : ['VALUE','TEXT']
    });
    pmcUrlStore.load({
    	callback : function() {
    		debugger;
//    		alert(pmcUrlStore.getAt(0).get('TEXT'));
    		document.write("<script type='text/javascript'>");
			//document.write("window.location.href='main.html';");
			//document.write("window.open('"+pmcUrlStore.getAt(0).get('TEXT')+"')");	//PMC实时监控界面地址
			document.write("window.location.href='"+pmcUrlStore.getAt(0).get('TEXT')+"';");	//PMC实时监控界面地址
			document.write("</script>");
    	}
    });
/*document.write("<script type='text/javascript'>");
document.write("window.location.href='main.html';");
document.write("window.open('http://10.92.23.164/proficywebspace/main.html')");	//PMC实时监控界面地址
document.write("</script>");*/
    /*var urlPanel = Ext.create('Ext.ux.IFrame', {
        region : 'center',
        border : true,
        autoScroll : true,
        src : 'jsp/Pmc.jsp'
    });
    
    return urlPanel;*/
})();