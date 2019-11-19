
/**
 * 
 *
 * @File name:  QueryPubUserRoleByPagerAction.java 
 * @Create on:  2010-07-09 10:07:109
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
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DbUtils;

import com.hanthink.pub.po.PubUserRolePO;
import com.hanthink.util.ClientIP;
import com.hanthink.util.SessionUtils;

public class SavePubUserRoleAction extends ActionImp {
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【用户角色关系】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        
        String userName = atx.getStringValue("PK_USER_NAME", "").trim();
        String cmd = atx.getStringValue("CMD", "").trim();
        if ("".equals(userName)) {
            return 1;
        }
        
        //定义变量
        PubUserRolePO userRole = null;
        String loginUserName = SessionUtils.getUserNo(atx);
        String clientIP = ClientIP.getRemoteIP(atx);
        Date dbTime = DbUtils.getDbTime();
        
        //增加用户角色
        Object[] roleIds = atx.getArrayValue("PK_ROLE_ID");
        if (null != roleIds && roleIds.length > 0) {
            for (int i = 0; i < roleIds.length; i++) {
                if ("".equals(roleIds[i])) {
                    continue;
                }
                
                userRole = new PubUserRolePO();
                userRole.setPkUserName(userName);
                userRole.setPkRoleId((String) roleIds[i]);
                POUtils.delete(con, userRole);

                if ("A".equals(cmd)) {
                    userRole.setLastUpdateUsername(loginUserName);
                    userRole.setLastUpdateIp(clientIP);
                    userRole.setLastUpdateTime(dbTime);
                    userRole.setCreateTime(dbTime);
                    POUtils.insert(con, userRole, false);
                }
            }
        }
        
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        return 1;
    }
}
