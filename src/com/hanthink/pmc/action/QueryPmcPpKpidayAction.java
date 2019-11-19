/**
 * 
 *
 * @File name:  QueryPmcPpKpidayAction.java 
 * @Create on:  2014-04-02 09:36:603
 * @Author   :  taofl
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
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pmc.dao.PmcPpKpidayDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

public class QueryPmcPpKpidayAction extends ActionImp {
    private Connection con = null;
    private java.util.Date ppdate;
    private String banci;
    private String queryType;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【KPI日报表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        if("1".equals(queryType)){
            whereSQL=" AND (PRODUCTIONLINE='DLHD3' or PRODUCTIONLINE='FDRDL' or PRODUCTIONLINE='FDRDR' or PRODUCTIONLINE='FRDL2' or PRODUCTIONLINE='FRDL3' or PRODUCTIONLINE='FRDR2' or PRODUCTIONLINE='HDTG2' or PRODUCTIONLINE='XHDTG') ";
        }else if("2".equals(queryType)){
            whereSQL=" AND PRODUCTIONLINE!='DLHD3' AND PRODUCTIONLINE!='FDRDL' AND PRODUCTIONLINE!='FDRDR' AND PRODUCTIONLINE!='FRDL2' AND PRODUCTIONLINE!='FRDL3' AND PRODUCTIONLINE!='FRDR2' AND PRODUCTIONLINE !='HDTG2' AND PRODUCTIONLINE !='XHDTG' ";
        }
        if(banci.equals("1")||banci.equals("2")){
        List cp = PmcPpKpidayDao.queryPmcPpKpiday(con, ppdate,banci,whereSQL);
        atx.setValue("PMC_PP_KPIDAY", cp);
        return 1;
        }else {List cp = PmcPpKpidayDao.queryPmcPpKpidayAll(con, ppdate,banci,whereSQL);
            atx.setValue("PMC_PP_KPIDAY", cp);
            return 1;
        }
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        queryType=atx.getStringValue("queryType", "");
        ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),DictConstants.FORMAT_DATE);
        banci=banci=atx.getStringValue("BANCI", "");
        return 1;
    }
}
