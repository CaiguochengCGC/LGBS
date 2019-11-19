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

public class SaveFaultEventAction extends ActionImp {
    private Connection con = null;
    private String id;
    private String GENERATION_TIME;
    private String TIMESTAMP;
    private String ALARM_MESSAGE;
    private String DATA3;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【故障记录：ALARM_LOG】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {

        if("".equals(id)){
        	ErrorHandler.displayError(atx, "参数丢失,保存失败！");
        }else{
        	StringBuffer sql = new StringBuffer();
            CQuery query = CQueryFactoryTool.createFactory().createCQuery();

            sql.append("update CYCJ_ALARM_LOG  set ")
            .append("GENERATION_TIME=?")
            .append(",TIMESTAMP=?")
            .append(",ALARM_MESSAGE=?")
            .append(",reference=?")
            .append(" where sequence_number=?");

            query.setCommand(sql.toString());
            query.setString(1, GENERATION_TIME);
            query.setString(2, TIMESTAMP);
            query.setString(3, ALARM_MESSAGE);
            query.setString(4, DATA3);
            query.setString(5, id);
            
            query.executeUpdate(con);
        }
        
        return 1;
    }
    
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        id = atx.getStringValue("ID", "");
        GENERATION_TIME = atx.getStringValue("GENERATION_TIME", "");
        TIMESTAMP = atx.getStringValue("TIMESTAMP", "");
        ALARM_MESSAGE = atx.getStringValue("ALARM_MESSAGE", "");
        DATA3 = atx.getStringValue("DATA3", "");
        
        return 1;
    }

}