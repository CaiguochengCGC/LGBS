/**
 * 
 *
 * @File name:  QueryPmcViewRecordAction.java 
 * @Create on:  2014-03-29 13:46:161
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.zzcj.action;

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.zzcj.dao.PmcViewRecordDao;
import com.hanthink.util.DictConstants;

public class QueryPmcViewRecordAction extends ActionImp {
    private Connection con = null;
    private java.util.Date startPlanDate;
    private java.util.Date endPlanDate;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【意见反馈表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = PmcViewRecordDao.queryPmcViewRecord(con,startPlanDate,endPlanDate);
        atx.setValue("PMC_VIEW_RECORD", list.toArray());
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        startPlanDate = DateUtils.parse(atx.getStringValue("START_PLAN_DATE", ""), DictConstants.FORMAT_DATE);
        endPlanDate = DateUtils.parse(atx.getStringValue("END_PLAN_DATE", ""), DictConstants.FORMAT_DATE);
        return 1;
    }
}
