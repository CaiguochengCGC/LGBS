
/**
 * 
 *
 * @File name:  UpdatePubUserAction.java   修改【用户:PUB_USER】
 * @Create on:  2010-07-09 09:14:484
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

import com.hanthink.pub.po.PubUserPO;
import com.hanthink.util.ClientIP;
import com.hanthink.util.SessionUtils;

public class UpdatePubUserAction extends ActionImp {
    private PubUserPO conditionPubUserPO = new PubUserPO();
    private PubUserPO valuePubUserPO = new PubUserPO();
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"), "【用户】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        POUtils.update(con, conditionPubUserPO, valuePubUserPO, false);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        String userName = SessionUtils.getUserNo(atx);
        String clientIP = ClientIP.getRemoteIP(atx);
        Date dbTime = DbUtils.getDbTime();
        
        conditionPubUserPO.setPkUserName(atx.getStringValue("PK_USER_NAME", ""));
        valuePubUserPO.setUserCname(atx.getStringValue("USER_CNAME"));
        valuePubUserPO.setLastUpdateUsername(userName);
        valuePubUserPO.setLastUpdateIp(clientIP);
        valuePubUserPO.setLastUpdateTime(dbTime);
        return 1;
    }
}
