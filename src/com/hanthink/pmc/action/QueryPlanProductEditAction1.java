package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import com.hanthink.pmc.dao.TabProductHourDao;
import com.hanthink.pmc.po.PlanProductEditPO;
import java.sql.Connection;
import java.util.List;

public class QueryPlanProductEditAction1 extends ActionImp {
    private PlanProductEditPO planProductEditPO= new PlanProductEditPO();
    private Connection con = null;
    private String planDate;
    private String lineCode;
    private String shift;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【计划产量查询】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        planDate = atx.getStringValue("PlanDate");
        lineCode = atx.getStringValue("LineName", "");
        shift = atx.getStringValue("Shift", "");


        return 1;
    }
}
