(function() {
	var PPMCMonitor =new Ext.Panel({  
        region:'center',
        closable:true,
        width: 1300,
        height: 900,
        defaults:{autoScroll:true}, 
        layout:'fit',
        items:[],
        enableTabScroll:true  
	}); 
	
	var urlm ='';
	
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
    		urlm = pmcUrlStore.getAt(0).get('TEXT');
    		var mitem = [{
    		       id:'idxm',
    		       html:"<iframe scrolling='auto' frameborder='0' width='100%' height='100%' src='"+urlm+"'> </iframe>"
    	        }];
    		PPMCMonitor.add(mitem[0]);
    		PPMCMonitor.doLayout();
    		
    	}
    });


    return PPMCMonitor;
   
})();