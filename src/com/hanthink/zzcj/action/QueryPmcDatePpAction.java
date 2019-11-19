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

public class QueryPmcDatePpAction extends ActionImp {
    private Connection con = null;
    private java.util.Date ppDate;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【生产时间数据表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = PmcDatePpDao.queryPmcDatePp(con, ppDate);

        atx.setValue("PMC_DATE_PP", list.toArray());

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        ppDate = DateUtils.parse(atx.getStringValue("PPDATE", ""), DictConstants.FORMAT_DATE);

        return 1;
    }
}
