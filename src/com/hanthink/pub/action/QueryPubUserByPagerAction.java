
/**
 * 
 *
 * @File name:  QueryPubUserByPagerAction.java 
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

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;

import com.hanthink.pub.dao.PubUserDao;
import com.hanthink.util.DictConstants;

public class QueryPubUserByPagerAction extends ActionImp {
    private Connection con = null;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【用户】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {

        String userName = atx.getStringValue("PK_USER_NAME", "").trim();
        String userCName = atx.getStringValue("USER_CNAME", "").trim();
        Integer status = Integer.valueOf(DictConstants.YES) ;
        pager = new Pager(pageSize, currentPage);
        ComboPager cp = PubUserDao.queryPubUserByPager(con, pager, userName, userCName, status);
        atx.setValue("PUB_USER", cp);
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
        return 1;
    }
}
