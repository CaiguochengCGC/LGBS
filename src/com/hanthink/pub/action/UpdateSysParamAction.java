/**
 * @File name:  UpdateSysParamAction.java   修改【SYS_PARAM系统参数:SYS_PARAM】
 * @Create on:  2011-04-11 17:22:851
 * @Author   :  郑胜军
 * @ChangeList
 * Date         Editor              ChangeReasons
 */
package com.hanthink.pub.action;

import java.sql.Connection;
import java.util.Date;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DbUtils;

import com.hanthink.pub.po.SysParamPO;
import com.hanthink.util.ClientIP;
import com.hanthink.util.SessionUtils;

public class UpdateSysParamAction extends ActionImp {
    private SysParamPO conditionSysParamPO = new SysParamPO();
    private SysParamPO valueSysParamPO = new SysParamPO();
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"), "【SYS_PARAM系统参数】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        POUtils.update(con, conditionSysParamPO, valueSysParamPO, false);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        String userName = SessionUtils.getUserNo(atx);
        String clientIP = ClientIP.getRemoteIP(atx);
        Date dbTime = DbUtils.getDbTime();

        conditionSysParamPO.setParamCode(atx.getStringValue("PARAM_CODE", ""));
        valueSysParamPO.setParamVal(atx.getStringValue("PARAM_VAL"));
        valueSysParamPO.setNote(atx.getStringValue("NOTE"));
        valueSysParamPO.setLastUpdateUsername(userName);
        valueSysParamPO.setLastUpdateIp(clientIP);
        valueSysParamPO.setLastUpdateTime(dbTime);
        
        return 1;
    }
}
