
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

package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import com.hanthink.pmc.dao.PmcCalenderDao;
import com.hanthink.pub.dao.PubPrivilegesDao;

import java.sql.Connection;

public class QueryAllDateTempletAction extends ActionImp {
    private Connection con = null;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private String modelName;
    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【所有可用日历模板】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        pager = new Pager(pageSize, currentPage);
        ComboPager cp = PmcCalenderDao.queryAllDateTemplet(con, pager,modelName);
        atx.setValue("ALL_TEMPLET_DATE", cp);
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
        modelName = atx.getStringValue("MODEL_NAME", "").trim();
        return 1;
    }
}
