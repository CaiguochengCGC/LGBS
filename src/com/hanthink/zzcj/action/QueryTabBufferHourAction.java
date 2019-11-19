package com.hanthink.zzcj.action;

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.zzcj.dao.TabProductHourDao;
import com.hanthink.util.DictConstants;

public class QueryTabBufferHourAction extends ActionImp {
    private Connection con = null;
    private java.util.Date eventDate;
    private String MODE;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【TabStopLine】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {

        List cp = TabProductHourDao.queryTabStopLine2(con,eventDate,MODE);

        atx.setValue("TAB_PRODUCT_HOUR", cp);

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        eventDate = DateUtils.parse(atx.getStringValue("EventData"), DictConstants.FORMAT_DATE);
        MODE=atx.getStringValue("MODE","");
        return 1;
    }
}
