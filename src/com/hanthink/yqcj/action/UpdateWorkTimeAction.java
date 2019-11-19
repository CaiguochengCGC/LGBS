package com.hanthink.yqcj.action;

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

public class UpdateWorkTimeAction extends ActionImp {
    private Connection con = null;
    private String ban1s;
    private String ban1e;
    private String ban2s;
    private String ban2e;
    private String ban3s;
    private String ban3e;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "修改工作时间", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {

    	if("".equals(ban1s)||"".equals(ban1e)||"".equals(ban2s)||"".equals(ban2e)||"".equals(ban3s)||"".equals(ban3e)){
        	ErrorHandler.displayError(atx, "参数丢失,保存失败！");
        }else{
        	StringBuffer sql = new StringBuffer();
            CQuery query = CQueryFactoryTool.createFactory().createCQuery();

            sql.append("update YQCJ_tabWorkTime  set ")
            .append("BAN1S=?")
            .append(",BAN1E=?")
            .append(",BAN2S=?")
            .append(",BAN2E=?")
            .append(",BAN3S=?")
            .append(",BAN3E=?");

            query.setCommand(sql.toString());
            query.setString(1, ban1s);
            query.setString(2, ban1e);
            query.setString(3, ban2s);
            query.setString(4, ban2e);
            query.setString(5, ban3s);
            query.setString(6, ban3e);
            
            query.executeUpdate(con);
        }
        
        return 1;
    }
    
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        ban1s = atx.getStringValue("BAN1S", "");
        ban1e = atx.getStringValue("BAN1E", "");
        ban2s = atx.getStringValue("BAN2S", "");
        ban2e = atx.getStringValue("BAN2E", "");
        ban3s = atx.getStringValue("BAN3S", "");
        ban3e = atx.getStringValue("BAN3E", "");
        return 1;
    }

}