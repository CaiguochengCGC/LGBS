package com.hanthink.zzcj.action;

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
    private String eventData51;
    private String userName;
    private String dbtime;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【故障记录：ALARM_LOG】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {

        if("".equals(id)){
        	ErrorHandler.displayError(atx, "参数丢失,保存失败！");
        }else{
        	StringBuffer sql = new StringBuffer();
            CQuery query = CQueryFactoryTool.createFactory().createCQuery();

            sql.append("update ZZCJ_tabStopline  set ")
            .append("EventData50=?")
            .append(",EventData51=?")
            .append(",EventData53=?")
            .append(",EventData54=?")
            .append(" where ID=?");

            query.setCommand(sql.toString());
            query.setString(1, eventData50);
            query.setString(2, eventData51);
            query.setString(3, userName);
            query.setString(4, dbtime);
            query.setInt(5, Tools.getInt(id,-1));
            
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
        eventData51 = atx.getStringValue("EventData51", "");
        
        return 1;
    }

}