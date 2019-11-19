/**
 * 
 *
 * @File name:  QueryPmcPpKpimonthAction.java 
 * @Create on:  2014-04-03 10:15:788
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.cycj.action;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.cycj.dao.PmcPpKpimonthDao;

public class QueryPmcPpKpimonthAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private String banci;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【KPI月报表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = PmcPpKpimonthDao.queryPmcPpKpimonth(con, ppdate,banci);
        atx.setValue("PMC_PP_KPIMONTH", list.toArray());
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        ppdate = DateUtils.parse(atx.getStringValue("YYYY_MM"), "yyyy-MM");
        banci = atx.getStringValue("BANCI","");
        return 1;
    }
}
