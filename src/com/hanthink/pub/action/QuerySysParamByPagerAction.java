/**
 * @File name:  QuerySysParamByPagerAction.java 
 * @Create on:  2011-04-11 17:22:898
 * @Author   :  郑胜军
 * @ChangeList
 * Date         Editor              ChangeReasons
 */
package com.hanthink.pub.action;

import java.sql.Connection;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;

import com.hanthink.pub.dao.SysParamDao;

public class QuerySysParamByPagerAction extends ActionImp {
    private Connection con = null;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【SYS_PARAM系统参数】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        pager = new Pager(pageSize, currentPage);
        ComboPager cp = SysParamDao.querySysParamByPager(con, pager);
        atx.setValue("SYS_PARAM", cp);
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
