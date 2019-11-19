package com.hanthink.yqcj.action;

import java.sql.Connection;
import java.util.Date;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.yqcj.dao.TabStopLineDao;
import com.hanthink.util.DictConstants;

public class QueryTabStopLineActionCut extends ActionImp {
    private Connection con = null;
    private String EventData1;
    private String banci;
    private String shuxing;
    private String EventData40;
    private Date startDate;
    private Date endDate;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【TabStopLine】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        pager = new Pager(pageSize, currentPage);
//        System.out.println("*****&&&&*****");
//        System.out.println(startDate);
//        System.out.println(endDate);
//        System.out.println("*****&&&&*****");
        ComboPager cp = TabStopLineDao.queryTabStopLineCut(con, startDate, endDate, EventData1, banci,shuxing,EventData40, pager);
        atx.setValue("TAB_STOP_LINE", cp);

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

        startDate = DateUtils.parse(atx.getStringValue("START_PLAN_DATE"), DictConstants.FORMAT_DATETIME);
        endDate = DateUtils.parse(atx.getStringValue("END_PLAN_DATE"), DictConstants.FORMAT_DATETIME);

        EventData1 = atx.getStringValue("EventDate1", "").trim();
        banci = atx.getStringValue("BANCI", "").trim();
        shuxing=atx.getStringValue("SHUXING", "").trim();
        EventData40 = atx.getStringValue("EventDate40", "").trim();

        return 1;
    }
}
