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
import cn.boho.framework.utils.MD5Util;

import com.hanthink.pub.dao.PubUserDao;
import com.hanthink.pub.po.PubUserPO;
import com.hanthink.util.ClientIP;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ErrorHandler;
import com.hanthink.util.SessionUtils;

public class ModifyPubUserPasswordAction   extends ActionImp {
    private Connection con=null;
    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"),"【用户】", ex);
        }
    }
    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        logger.debug("修改用户密码。"); 
        
        //获取参数
        String userName = SessionUtils.getUserNo(atx);
        String currentPassword = atx.getStringValue("CURRENT_PASSWORD","").trim();
        String newPassword = atx.getStringValue("NEW_PASSWORD","").trim();
        String clientIP = ClientIP.getRemoteIP(atx);
        Date dbTime = DbUtils.getDbTime();
        
        //获取用户
        DynaBeanMap userBeanMap = PubUserDao.getPubUserByPk(con, userName);
        PubUserPO userPO = PubUserDao.convertBeanMapToPO(userBeanMap);
        if (null == userPO || Integer.parseInt(DictConstants.NO) == userPO.getUserStatus()) {
            ErrorHandler.displayError(atx, "用户不存在！");
        }
        String password = userPO.getUserPwd();
        
        if ( !MD5Util.getMD5String(currentPassword).equals(password) ) {
            ErrorHandler.displayError(atx, "当前密码不正确，请确认！");
        }
        
        //修改
        PubUserPO keyUserPO = new PubUserPO();
        keyUserPO.setPkUserName(userName);
        
        PubUserPO valueUserPO = new PubUserPO();
        valueUserPO.setUserPwd(MD5Util.getMD5String(newPassword));
        valueUserPO.setIsUpdatePwd(Integer.parseInt(DictConstants.YES));
        valueUserPO.setLastUpdateUsername(userName);
        valueUserPO.setLastUpdateIp(clientIP);
        valueUserPO.setLastUpdateTime(dbTime);
        
        POUtils.update(con, keyUserPO,valueUserPO ,false);
        return 1;
    }
    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        return 1;
    }
}
