package com.hanthink.yqcj.action;

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.yqcj.dao.TabCycleTimeDao;
import com.hanthink.util.DictConstants;

public class QueryTabCycleTimeByRandAction extends ActionImp {
    private Connection con = null;
    private String EventData13;
    private String EventData1;
    private java.util.Date date;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【TabCycleTime】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {

        List cp = TabCycleTimeDao.queryTabCycleTimeByRand(con, date, EventData13, EventData1);

        atx.setValue("TAB_CYCLE_TIME", cp);

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        date = DateUtils.parse(atx.getStringValue("EventData"), DictConstants.FORMAT_DATE);
        EventData13 = atx.getStringValue("EventDate14", "").trim();
        EventData1 = atx.getStringValue("EventDate1", "").trim();

        return 1;
    }
}
