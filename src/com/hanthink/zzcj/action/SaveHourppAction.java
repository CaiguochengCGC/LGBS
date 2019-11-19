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

public class SaveHourppAction extends ActionImp {
    private Connection con = null;
    private String id;
    private String EventDate27;
    private String EventDate28;
    private String userName;
    private String dbtime;
    private String plandata;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "更新上下线量失败", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {

        if("".equals(id)){
        	ErrorHandler.displayError(atx, "参数丢失,保存失败！");
        }else{
        	StringBuffer sql = new StringBuffer();
            CQuery query = CQueryFactoryTool.createFactory().createCQuery();

            sql.append("update ZZCJ_tabProductHour  set ")
            .append("EventDate27=?")
            .append(",EventDate28=?")
            .append(",EventDate31=?")
            //.append(",EventData54=?")
            .append(" where ID=?");

            query.setCommand(sql.toString());
            query.setString(1, EventDate27);
            query.setString(2, EventDate28);
            query.setString(3, plandata);
            //query.setString(4, dbtime);
            query.setInt(4, Tools.getInt(id,-1));
            
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
        EventDate27 = atx.getStringValue("EventDate27", "");
        EventDate28 = atx.getStringValue("EventDate28", "");
        plandata=atx.getStringValue("PLANDATA", "1");
        
        return 1;
    }

}