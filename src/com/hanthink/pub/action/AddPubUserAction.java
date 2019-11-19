/**
 * @File name:  AddPubUserAction.java   添加【用户:PUB_USER】
 * @Create on:  2010-07-09 09:14:484
 * Date         Editor              ChangeReasons
 */
package com.hanthink.pub.action;

import java.sql.Connection;
import java.util.Date;
import java.util.UUID;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DbUtils;
import cn.boho.framework.utils.MD5Util;

import com.hanthink.pub.dao.PubUserDao;
import com.hanthink.pub.dao.SysParamDao;
import com.hanthink.pub.po.PubUserPO;
import com.hanthink.util.ClientIP;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ErrorHandler;
import com.hanthink.util.SessionUtils;


public class AddPubUserAction extends ActionImp {
    private PubUserPO keyUser = new PubUserPO();
    private PubUserPO valueUser = new PubUserPO();
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"), "【用户】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        
        if (PubUserDao.existsPubUserByPk(con, keyUser.getPkUserName(), null)) {
            if (PubUserDao.existsPubUserByPk(con, keyUser.getPkUserName(), Integer.valueOf(DictConstants.NO))) {
                valueUser.setUserStatus(Integer.valueOf(DictConstants.YES));
                POUtils.update(con, keyUser, valueUser, false);
            } else {
                ErrorHandler.displayError(atx, "用户 " + keyUser.getPkUserName() + " 已存在！");
            }

        } else {
            valueUser.setUserStatus(Integer.valueOf(DictConstants.YES));
            valueUser.setCreateTime(valueUser.getLastUpdateTime());
            valueUser.setPkUserName(keyUser.getPkUserName());
            valueUser.setDevision(UUID.randomUUID().toString());

            POUtils.insert(con, valueUser, false);
        }
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        
        String defaultPWD = SysParamDao.getSysParamVal(con, "DEFAULT_PWD");
        String userName = SessionUtils.getUserNo(atx);
        String clientIP = ClientIP.getRemoteIP(atx);
        Date dbTime = DbUtils.getDbTime();

        // 添加时当用户名在数据表存在并删除状态为0时更新用户信息
        keyUser.setPkUserName(atx.getStringValue("PK_USER_NAME", ""));

        valueUser.setUserCname(atx.getStringValue("USER_CNAME"));
        valueUser.setUserPwd(MD5Util.getMD5String(defaultPWD));
        valueUser.setIsUpdatePwd(Integer.valueOf(DictConstants.NO));
        valueUser.setLastUpdateUsername(userName);
        valueUser.setLastUpdateIp(clientIP);
        valueUser.setLastUpdateTime(dbTime);

        return 1;
    }
}
