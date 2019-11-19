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

package com.hanthink.pmc.action;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;

import com.hanthink.pmc.dao.TabproducttypeDao;

public class QueryTabproducttypeAction extends ActionImp {
    private Connection con = null;
    private String startDate;
    private String endDate;
    private Date nextStartDate;
    private Date nextEndDate;
    private java.lang.String productionLine;
    private java.lang.String sheft;
    private String banci;

    private String queryTypeT;
    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【tabProductType】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        if("1".equals(queryTypeT)){
            whereSQL=" AND (LineCode='DLHD3' or LineCode='FDRDL' or LineCode='FDRDR' or LineCode='FRDL2' or LineCode='FRDL3' or LineCode='FRDR2' or LineCode='HDTG2' or LineCode='XHDTG') ";
        }else if("2".equals(queryTypeT)){
            whereSQL=" AND LineCode!='DLHD3' AND LineCode!='FDRDL' AND LineCode!='FDRDR' AND LineCode!='FRDL2' AND LineCode!='FRDL3' AND LineCode!='FRDR2' AND LineCode !='HDTG2' AND LineCode !='XHDTG' ";
        }
        if(banci.equals("1")||banci.equals("2")) {
            List list = TabproducttypeDao.queryTabproducttype(con, startDate, endDate, productionLine, banci,whereSQL);
            atx.setValue("tabProductType", list.toArray());
            return 1;
        }else {
            List list = TabproducttypeDao.queryTabproducttypeAll(con, startDate, endDate, productionLine, banci,whereSQL);
            atx.setValue("tabProductType", list.toArray());
            return 1;
        }
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        startDate = atx.getStringValue("START_PLAN_DATE", "");
        endDate = atx.getStringValue("END_PLAN_DATE", "");
        productionLine = atx.getStringValue("EventDate18", "");
        banci=atx.getStringValue("BANCI", "");

        queryTypeT=atx.getStringValue("queryType", "");
        return 1;
    }
}
