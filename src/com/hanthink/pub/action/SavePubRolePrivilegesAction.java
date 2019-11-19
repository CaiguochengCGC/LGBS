
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

import com.hanthink.pub.po.PubRolePrivilegesPO;
import com.hanthink.util.ClientIP;
import com.hanthink.util.SessionUtils;

public class SavePubRolePrivilegesAction extends ActionImp {
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【用户角色关系】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        
        String roleId = atx.getStringValue("PK_ROLE_ID", "").trim();
        String cmd = atx.getStringValue("CMD", "").trim();
        if ("".equals(roleId)) {
            return 1;
        }
        
        //定义变量
        PubRolePrivilegesPO rolePrivileges = null;
        String loginUserName = SessionUtils.getUserNo(atx);
        String clientIP = ClientIP.getRemoteIP(atx);
        Date dbTime = DbUtils.getDbTime();
        
        //增加用户角色
        Object[] privilegesIds = atx.getArrayValue("PK_PRIVILEGES_ID");
        if (null != privilegesIds && privilegesIds.length > 0) {
            for (int i = 0; i < privilegesIds.length; i++) {
                if ("".equals(privilegesIds[i])) {
                    continue;
                }
                
                rolePrivileges = new PubRolePrivilegesPO();
                rolePrivileges.setPkRoleId(roleId);
                rolePrivileges.setPkPrivilegesId((String) privilegesIds[i]);
                POUtils.delete(con, rolePrivileges);

                if ("A".equals(cmd)) {
                    rolePrivileges.setLastUpdateUsername(loginUserName);
                    rolePrivileges.setLastUpdateIp(clientIP);
                    rolePrivileges.setLastUpdateTime(dbTime);
                    rolePrivileges.setCreateTime(dbTime);
                    POUtils.insert(con, rolePrivileges, false);
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
