/**
 * 
 *
 * @File name:  QueryPmcDatePpAction.java 
 * @Create on:  2014-03-16 17:37:739
 * @Author   :  luoshw
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

import com.hanthink.zzcj.dao.PmcDatePpDao;
import com.hanthink.util.DictConstants;

public class QueryPmcDatePpPeriodAction extends ActionImp {
    private Connection con = null;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private java.lang.String productionLine;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【生产时间数据表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
//        List list = PmcDatePpDao.queryPmcDatePpPeriod(con, startDate, endDate, productionLine);
        List list = PmcDatePpDao.queryPmcDatePpPeriodAgen(con, startDate, endDate, productionLine);

        atx.setValue("PMC_DATE_PP", list.toArray());

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        startDate = DateUtils.parse(atx.getStringValue("START_PLAN_DATE", ""), DictConstants.FORMAT_DATE);
        endDate = DateUtils.parse(atx.getStringValue("END_PLAN_DATE", ""), DictConstants.FORMAT_DATE);
        productionLine = atx.getStringValue("PRODUCTIONLINENAME", "");

        return 1;
    }
}
