
/**
 * 
 *
 * @File name:  UpdatePubRoleAction.java   修改【角色:PUB_Role】
 * @Create on:  2010-06-17 10:21:734
 * @Author   :  ht
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
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

import com.hanthink.pub.po.PubRolePO;
import com.hanthink.util.ClientIP;
import com.hanthink.util.SessionUtils;

public class UpdatePubRoleAction extends ActionImp {
    private PubRolePO conditionPubRolePO = new PubRolePO();
    private PubRolePO valuePubRolePO = new PubRolePO();
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"), "【角色】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        POUtils.update(con, conditionPubRolePO, valuePubRolePO, false);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {

        // 获取系统信息
        con = atx.getConection();
        String userName = SessionUtils.getUserNo(atx);
        String clientIP = ClientIP.getRemoteIP(atx);
        Date dbTime = DbUtils.getDbTime();

        // 获取参数
        conditionPubRolePO.setPkRoleId(atx.getStringValue("PK_ROLE_ID", ""));
        valuePubRolePO.setRoleName(atx.getStringValue("ROLE_NAME"));
        valuePubRolePO.setRoleDescription(atx.getStringValue("ROLE_DESCRIPTION"));
        valuePubRolePO.setLastUpdateUsername(userName);
        valuePubRolePO.setLastUpdateIp(clientIP);
        valuePubRolePO.setLastUpdateTime(dbTime);
        return 1;
    }
}
