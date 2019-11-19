package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;
import com.hanthink.pmc.dao.TabCycleTimeDao;
import com.hanthink.util.DictConstants;

import java.sql.Connection;
import java.util.List;

public class QueryTabTypeAction extends ActionImp {
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【TabCycleTime】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List cp = TabCycleTimeDao.queryTabCarType(con);
        atx.setValue("CAR_TYPE", cp);

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        return 1;
    }
}
