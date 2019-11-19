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
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;

import com.hanthink.pmc.dao.PmcSystemReportDao;
import com.hanthink.util.Tools;

public class QueryPmcSystemReportAction extends ActionImp {
    private Connection con = null;
    private java.util.Date WORKDATE;
    private String workDate;
    private String banci;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private String queryType;
    private String vv;


    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【PmcSystemReport】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        pager = new Pager(pageSize, currentPage);
//        List list = PmcSystemReportDao.QueryPmcSystemReport(con, workDate,banci,pager);
        //查询休息时间
        String whereSQL="";
        if("1".equals(queryType)){
          whereSQL="AND (A.PRODUCTIONLINE='DLHD3' or A.PRODUCTIONLINE='FDRDL' or A.PRODUCTIONLINE='FDRDR' or A.PRODUCTIONLINE='FRDL2' or A.PRODUCTIONLINE='FRDL3' or A.PRODUCTIONLINE='FRDR2' or A.PRODUCTIONLINE='HDTG2' or A.PRODUCTIONLINE='XHDTG')";
        }else if("2".equals(queryType)){
            whereSQL="AND A.PRODUCTIONLINE !='DLHD3' AND A.PRODUCTIONLINE!='FDRDL' AND A.PRODUCTIONLINE!='FDRDR' AND A.PRODUCTIONLINE!='FRDL2' AND A.PRODUCTIONLINE!='FRDL3' AND A.PRODUCTIONLINE!='FRDR2' AND A.PRODUCTIONLINE !='HDTG2' AND A.PRODUCTIONLINE !='XHDTG'";
        }
        if (banci.equals("1")||banci.equals("2")) {
            ComboPager cp = PmcSystemReportDao.QueryPmcSystemReport(con, workDate, banci, pager,whereSQL);
            atx.setValue("PMC_SYSTEM_REPORT", cp);
            return 1;
        }else {
            ComboPager cp = PmcSystemReportDao.QueryPmcSystemReportAll(con, workDate, pager,whereSQL);
            atx.setValue("PMC_SYSTEM_REPORT", cp);
            return 1;
        }
//        atx.setValue("PMC_SYSTEM_REPORT", list.toArray());

    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        if (atx.getStringValue("limit") != null && !atx.getStringValue("limit").equals("")) {
            pageSize = new Integer(atx.getStringValue("limit"));
        }

        //WORKDATE = DateUtils.parse(atx.getStringValue("WORKDATE"),DictConstants.FORMAT_DATE);
        workDate = atx.getStringValue("WORKDATE", "");
        banci=atx.getStringValue("BANCI", "");
        queryType=atx.getStringValue("queryType", "");
        return 1;
    }
}
