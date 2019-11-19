package com.hanthink.pmc.action;

import java.sql.Connection;
import java.util.Date;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.CQuery;
import cn.boho.framework.po.CQueryFactoryTool;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DbUtils;

import com.hanthink.util.ErrorHandler;
import com.hanthink.util.SessionUtils;
import com.hanthink.util.Tools;

public class SaveAlarmLogAction extends ActionImp {
    private Connection con = null;
    private String id;
    private String eventData50;
    private String eventData1;
    private String userName;
    private String dbtime;
    private  String stopReson;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【故障记录：ALARM_LOG】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {

        if("".equals(id)){
        	ErrorHandler.displayError(atx, "参数丢失,保存失败！");
        }else{
        	StringBuffer sql = new StringBuffer();
            CQuery query = CQueryFactoryTool.createFactory().createCQuery();

            sql.append("update tabStopline  set ")
            .append("EventData50=?")
            .append(",EventData1=?")
            .append(",EventData53=?")
            .append(",EventData54=?")
            .append(",EventData3=?")
            .append(" where ID=?");

            query.setCommand(sql.toString());
            query.setString(1, eventData50);
            query.setString(2, eventData1);
            query.setString(3, userName);
            query.setString(4, dbtime);
            query.setString(5, stopReson);
            query.setInt(6, Tools.getInt(id,-1));
            
            query.executeUpdate(con);
        }
        
        return 1;
    }
    
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        userName = SessionUtils.getUserNo(atx);
        dbtime = Tools.getStr(Tools.getTimestamp(DbUtils.getDbTime(con)),"");
//        System.out.println("--------------------------------"+Tools.getTimestamp(dbtime));
//        System.out.println("--------------------------------"+Tools.getStr(Tools.getTimestamp(dbtime),""));
        id = atx.getStringValue("ID", "");
        eventData50 = atx.getStringValue("EventData50", "");
        eventData1 = atx.getStringValue("EventData1", "");
        stopReson = atx.getStringValue("StopReson", "");
        return 1;
    }

}
