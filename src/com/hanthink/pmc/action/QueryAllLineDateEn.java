package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import com.hanthink.pmc.dao.PmcCalenderDao;

import java.sql.Connection;
import java.util.List;

public class QueryAllLineDateEn extends ActionImp {
    private Connection con = null;
    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【查询所有线体】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List lineDate = PmcCalenderDao.queryAllLineEn(con);
        atx.setValue("PMC_PP_LINE_EN", lineDate);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        return 1;
    }
}
