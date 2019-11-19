
/**
 * 
 *
 * @File name:  AddPubRoleAction.java   添加【角色:PUB_Role】
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
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DbUtils;

import com.hanthink.pub.po.PubRolePO;
import com.hanthink.util.ClientIP;
import com.hanthink.util.OracleSequence;
import com.hanthink.util.SessionUtils;

public class AddPubRoleAction extends ActionImp {
    private PubRolePO pubRolePO = new PubRolePO();
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"), "【角色】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        POUtils.insert(con, pubRolePO, false);

        // 返回主键
        DynaBeanMap back = new DynaBeanMap();
        back.put("PK_ROLE_ID", pubRolePO.getPkRoleId());
        atx.setObjValue("PUB_ROLE", back);

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
        pubRolePO.setPkRoleId(OracleSequence.getNextSeq(con, "S_PUB_ROLE"));
        pubRolePO.setRoleName(atx.getStringValue("ROLE_NAME"));
        pubRolePO.setRoleDescription(atx.getStringValue("ROLE_DESCRIPTION"));
        pubRolePO.setLastUpdateUsername(userName);
        pubRolePO.setLastUpdateIp(clientIP);
        pubRolePO.setLastUpdateTime(dbTime);
        pubRolePO.setCreateTime(dbTime);
        return 1;
    }
}
