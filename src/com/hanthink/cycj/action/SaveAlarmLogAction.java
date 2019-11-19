package com.hanthink.cycj.action;

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
    private String eventDate1;
    private String eventDate2;
    private String eventData50;
    private String eventData51;
    private String userName;
    private String dbtime;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【修改停线查询表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {

        if("".equals(id)){
        	ErrorHandler.displayError(atx, "参数丢失,保存失败！");
        }else{
        	StringBuffer sql = new StringBuffer();
            CQuery query = CQueryFactoryTool.createFactory().createCQuery();

            sql.append("update CYCJ_tabStopline  set ")
            .append("EventDate1=?")
            .append(",EventDate2=?")
            .append(",EventData50=?")
            .append(",EventData51=?")
            .append(",EventData53=?")
            .append(",EventData54=?")
            .append(" where ID=?");

            query.setCommand(sql.toString());
            query.setString(1, eventDate1);
            query.setString(2, eventDate2);
            query.setString(3, eventData50);
            query.setString(4, eventData51);
            query.setString(5, userName);
            query.setString(6, dbtime);
            query.setInt(7, Tools.getInt(id,-1));
            
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
        eventDate1 = atx.getStringValue("EventDate1", "");
        eventDate2 = atx.getStringValue("EventDate2", "");
        eventData50 = atx.getStringValue("EventData50", "");
        eventData51 = atx.getStringValue("EventData51", "");
        
        return 1;
    }

}