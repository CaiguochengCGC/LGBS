package com.hanthink.pub.action;

import java.sql.Connection;
import java.util.Date;

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

public class ResetUserPasswordAction extends ActionImp {
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"), "【用户】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        logger.debug("重置用户密码。");
        
        //获取参数
        String userName = atx.getStringValue("PK_USER_NAME");
        Integer userStatus = Integer.valueOf(DictConstants.YES);
        String loginUserName = SessionUtils.getUserNo(atx);
        String clientIP = ClientIP.getRemoteIP(atx);
        Date dbTime = DbUtils.getDbTime();

        if (!PubUserDao.existsPubUserByPk(con, userName, userStatus)) {
            ErrorHandler.displayError(atx, "用户不存在！");
        }
        
        //修改用户
        PubUserPO keyUserPO = new PubUserPO();
        keyUserPO.setPkUserName(userName);

        PubUserPO valueUserPO = new PubUserPO();
        String defaultPWD = SysParamDao.getSysParamVal(con, "DEFAULT_PWD");
        valueUserPO.setUserPwd(MD5Util.getMD5String(defaultPWD));
        valueUserPO.setIsUpdatePwd(Integer.valueOf(DictConstants.NO));
        valueUserPO.setLastUpdateUsername(loginUserName);
        valueUserPO.setLastUpdateIp(clientIP);
        valueUserPO.setLastUpdateTime(dbTime);

        POUtils.update(con, keyUserPO, valueUserPO, false);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        return 1;
    }
}
