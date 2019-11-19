/**
 * 工作时间维护
 */
(function() {
    var planNoTemp;
    var setPanelIsLayout;
    var mytoday=new Date();
    var mytodaystr=mytoday.getFullYear()+'/'+(mytoday.getMonth()+1)+'/'+mytoday.getDate();
    var myyestoday=new Date(new Date().getTime() + 1 * 24 * 60 * 60 * 1000);
    var myyestodaystr=myyestoday.getFullYear()+'/'+(myyestoday.getMonth()+1)+'/'+myyestoday.getDate();

    //刷新工作时间
    var refreshworktimeFn = function(){
    	//防止缓存
    	var Rand = Math.random();
    //初始化填写旧的数据
    Ext.Ajax.request( {
    	  url : 'json?action=YQCJ_GET_WORKTIME_ACTION&'+Rand,
    	  method : 'get',
    	  params : {
    	   para1 : "",
    	   para2 : ""
    	  },
    	  success : function(response, options) {
    	   var o = Ext.util.JSON.decode(response.responseText);
    	   var o2= o.root.YQCJ_WORKTIME.rs[0];
    	   workTimePanel.find('name', 'ban1start')[0].setValue(o2.BAN1S);
    	   workTimePanel.find('name', 'ban1end')[0].setValue(o2.BAN1E);
    	   workTimePanel.find('name', 'ban2start')[0].setValue(o2.BAN2S);
    	   workTimePanel.find('name', 'ban2end')[0].setValue(o2.BAN2E);
    	   workTimePanel.find('name', 'ban3start')[0].setValue(o2.BAN3S);
    	   workTimePanel.find('name', 'ban3end')[0].setValue(o2.BAN3E);
    	  },
    	  failure : function() {
    		  alert("失败");
    	  }
    	 });
    }
    //更新工作时间
    var updateworktimeFn = function(){
        
        var url = 'json?action=YQCJ_UPDATE_WORKTIME_ACTION';
        
        url += '&BAN1S=' + workTimePanel.find('name', 'ban1start')[0].getValue();
        url += '&BAN1E=' + workTimePanel.find('name', 'ban1end')[0].getValue();
        url += '&BAN2S=' + workTimePanel.find('name', 'ban2start')[0].getValue();
        url += '&BAN2E=' + workTimePanel.find('name', 'ban2end')[0].getValue();
        url += '&BAN3S=' + workTimePanel.find('name', 'ban3start')[0].getValue();
        url += '&BAN3E=' + workTimePanel.find('name', 'ban3end')[0].getValue();
        Ext.Ajax.request( {
      	  url : url,
      	  method : 'get',
      	  params : {
      	   para1 : "",
      	   para2 : ""
      	  },
      	  success : function(response, options) {
      	   var o = Ext.util.JSON.decode(response.responseText);
      	   if(o.success){
      		 Ext.Msg.alert("提示", "修改成功");
      	   }else{
      		 Ext.Msg.alert("提示", "操作失败");
      	   }
      	//刷新画面
      	    refreshworktimeFn();
      	  },
      	  failure : function() {
      		  alert("失败");
      	  }
      	 });

    }
    //刷新画面
    refreshworktimeFn();
    //生产时间报表
    var workTimePanel = new Ext.Panel({
        title:"请您在此修改工作时间按排",
        //renderTo:Ext.getBody(),
        frame:true,
        width:560,
        height:300,
        layout:"form",
        lableWidth:200,
        items:[{xtype:"numberfield",name:"ban1start",width:50,minValue:0,maxValue:23,fieldLabel:"一班开始时间"+mytodaystr+"(单位小时)",labelStyle : "text-align:right;width:200;"},
               {xtype:"numberfield",name:"ban1end",width:50,minValue:0,maxValue:23,fieldLabel:"一班结束时间"+mytodaystr+"(单位小时)",labelStyle : "text-align:right;width:200;"},
               {xtype:"numberfield",name:"ban2start",width:50,minValue:0,maxValue:23,fieldLabel:"二班开始时间"+mytodaystr+"(单位小时)",labelStyle : "text-align:right;width:200;"},
               {xtype:"numberfield",name:"ban2end",width:50,minValue:0,maxValue:23,fieldLabel:"二班结束时间"+myyestodaystr+"(单位小时)",labelStyle : "text-align:right;width:200;"},
               {xtype:"numberfield",name:"ban3start",width:50,minValue:0,maxValue:23,fieldLabel:"三班开始时间"+mytodaystr+"(单位小时)",labelStyle : "text-align:right;width:200;"},
               {xtype:"numberfield",name:"ban3end",width:50,minValue:0,maxValue:23,fieldLabel:"三班结束时间"+mytodaystr+"(单位小时)",labelStyle : "text-align:right;width:200;"}],
        buttons:[{text:"修改",
        	handler:updateworktimeFn	
        }],
        listeners : {
            beforedestroy : function(){
                workTimePanel.removeAll(true);
            }   
        }
        });
    

    return workTimePanel;
})();
