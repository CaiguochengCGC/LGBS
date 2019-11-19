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

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import com.hanthink.pmc.dao.PmcSystemReportDao;
import com.hanthink.pmc.dao.PmcViewRecordDao;

import java.sql.Connection;
import java.util.List;

public class QueryPmcCYACPCAction extends ActionImp {
    private Connection con = null;
    private String workDate;
    private String queryTypeT;


    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【PmcSystemReport】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        if("1".equals(queryTypeT)){
            whereSQL=" AND (LineCode='DLHD3' or LineCode='FDRDL' or LineCode='FDRDR' or LineCode='FRDL2' or LineCode='FRDL3' or LineCode='FRDR2' or LineCode='HDTG2' or LineCode='XHDTG') ";
        }else if("2".equals(queryTypeT)){
            whereSQL=" AND LineCode!='DLHD3' AND LineCode!='FDRDL' AND LineCode!='FDRDR' AND LineCode!='FRDL2' AND LineCode!='FRDL3' AND LineCode!='FRDR2' AND LineCode !='HDTG2' AND LineCode !='XHDTG' ";
        }
        List list = PmcViewRecordDao.queryEditCYACPC(con,workDate,whereSQL);
        atx.setValue("PMC_CY_PC", list.toArray());
        return 1;

    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        workDate = atx.getStringValue("WORKDATE", "");
        queryTypeT=atx.getStringValue("queryType", "");
        return 1;
    }
}
