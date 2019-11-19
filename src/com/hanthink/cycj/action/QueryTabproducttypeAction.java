/**
 * 
 *
 * @File name:  QueryTabproducttypeAction.java 
 * @Create on:  2014-03-16 17:39:625
 * @Author   :  luoshw
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

import com.hanthink.cycj.dao.TabproducttypeDao;

public class QueryTabproducttypeAction extends ActionImp {
    private Connection con = null;
    private String startDate;
    private String endDate;
    private Date nextStartDate;
    private Date nextEndDate;
    private java.lang.String productionLine;
    private java.lang.String banci;
    private java.lang.String mojuhao;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【tabProductType】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        
        List list = TabproducttypeDao.queryTabproducttype(con, startDate, endDate, productionLine,banci,mojuhao);
        atx.setValue("tabProductType", list.toArray());
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        startDate = atx.getStringValue("START_PLAN_DATE", "");
        endDate = atx.getStringValue("END_PLAN_DATE", "");
        productionLine = atx.getStringValue("EventDate18", "");
        banci=atx.getStringValue("BANCI", "");
        mojuhao=atx.getStringValue("mojuhao", "");
        return 1;
    }
}
