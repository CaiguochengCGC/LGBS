package com.hanthink.zzcj.action;

import java.sql.Connection;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.zzcj.dao.TabCycleTimeDao;
import com.hanthink.util.DictConstants;

public class QueryTabCycleTimeAction extends ActionImp {
    private Connection con = null;
    private String EventData13;
    private String EventData1;
    private String EventData2;
    private java.util.Date workDate;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【TabCycleTime】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        pager = new Pager(pageSize, currentPage);
        ComboPager cp = TabCycleTimeDao.queryTabCycleTime(con, workDate, EventData13, EventData1, EventData2, pager);

        atx.setValue("TAB_CYCLE_TIME", cp);

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

        workDate = DateUtils.parse(atx.getStringValue("WORKDATE"), DictConstants.FORMAT_DATE);
        EventData13 = atx.getStringValue("EventDate14", "").trim();
        EventData1 = atx.getStringValue("EventDate1", "").trim();
        EventData2 = atx.getStringValue("EventDate2", "").trim();

        return 1;
    }
}
