
/**
 * 
 *
 * @File name:  QueryPubPrivilegesByPagerAction.java 
 * @Create on:  2010-05-11 09:16:31
 * @Author   :  ht
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pub.action;

import java.sql.Connection;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;

import com.hanthink.pub.dao.PubPrivilegesDao;

public class QueryPubPrivilegesByPagerAction extends ActionImp {
    private Connection con = null;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private String type;
    private String category;
    private String content;
    private String description;
    private String pkRoleId;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【权限】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        pager = new Pager(pageSize, currentPage);
        ComboPager cp = PubPrivilegesDao.queryPubPrivilegesByPager(con, pager, 
                type, category, content, description, pkRoleId, false);
        atx.setValue("PUB_PRIVILEGES", cp);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        if (atx.getStringValue("limit") != null && !atx.getStringValue("limit").equals("")) {
            pageSize = new Integer(atx.getStringValue("limit"));
        }
        if (atx.getStringValue("start") != null && !atx.getStringValue("start").equals("")) {
            currentPage = new Integer(atx.getStringValue("start")) / pageSize + 1;
        }
        
        type = atx.getStringValue("Q_TYPE", "").trim();
        category = atx.getStringValue("Q_CATEGORY", "").trim();
        content = atx.getStringValue("Q_CONTENT", "").trim();
        description = atx.getStringValue("Q_DESCRIPTION", "").trim();
        pkRoleId = atx.getStringValue("PK_ROLE_ID", "").trim();
        return 1;
    }
}
