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

public class SaveAlarmEventAction extends ActionImp {
    private Connection con = null;
    private String id;
    private String ALARMSTARTTIME;
    private String ALARMENDTIME;
    private String ALARMTIME;
    private String ALARMMSG;
    private String ATTRBUTE;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【故障记录：ALARM_LOG】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {

        if("".equals(id)){
        	ErrorHandler.displayError(atx, "参数丢失,保存失败！");
        }else{
        	StringBuffer sql = new StringBuffer();
            CQuery query = CQueryFactoryTool.createFactory().createCQuery();

            sql.append("update CYCJ_PP_ALARM  set ")
            .append("ALARMSTARTTIME=?")
            .append(",ALARMENDTIME=?")
            .append(",ALARMTIME=?")
            .append(",ALARMMSG=?")
            .append(",ATTRBUTE=?")
            .append(" where ID=?");

            query.setCommand(sql.toString());
            query.setString(1, ALARMSTARTTIME);
            query.setString(2, ALARMENDTIME);
            query.setString(3, ALARMTIME);
            query.setString(4, ALARMMSG);
            query.setString(5, ATTRBUTE);
            query.setInt(6, Tools.getInt(id,-1));
            
            query.executeUpdate(con);
        }
        
        return 1;
    }
    
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        id = atx.getStringValue("ID", "");
        ALARMSTARTTIME = atx.getStringValue("ALARMSTARTTIME", "");
        ALARMENDTIME = atx.getStringValue("ALARMENDTIME", "");
        ALARMTIME = atx.getStringValue("ALARMTIME", "");
        ALARMMSG = atx.getStringValue("ALARMMSG", "");
        ATTRBUTE = atx.getStringValue("ATTRBUTE", "");
        
        return 1;
    }

}