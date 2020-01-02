
/**
 *
 *
 * @File name:  QueryPmcWeekDayStartRateAction.java
 * @Create on:  2019-12-18 10:21:765
 * @Author   :  huo
 *
 */

package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;
import com.hanthink.pmc.dao.PmcWeekDayStartRateDao;
import com.hanthink.util.DictConstants;

import java.security.PrivateKey;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class QueryPmcWeekDayStartRateAction extends ActionImp {
    private Connection con = null;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private String PRODUCTIONLINENAME;

    private String queryType;
    private String YYYY;


    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "查询【周开动率】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        if("1".equals(queryType)){
            whereSQL=" AND (PRODUCTIONLINE='EP21D' or PRODUCTIONLINE='DLHD3' or PRODUCTIONLINE='FDRDL' or PRODUCTIONLINE='FDRDR' or PRODUCTIONLINE='FRDL2' or PRODUCTIONLINE='FRDL3' or PRODUCTIONLINE='FRDR2' or PRODUCTIONLINE='HDTG2' or PRODUCTIONLINE='XHDTG') ";
        }else if("2".equals(queryType)){
            whereSQL=" AND PRODUCTIONLINE!='EP21D' AND PRODUCTIONLINE!='DLHD3' AND PRODUCTIONLINE!='FDRDL' AND PRODUCTIONLINE!='FDRDR' AND PRODUCTIONLINE!='FRDL2' AND PRODUCTIONLINE!='FRDL3' AND PRODUCTIONLINE!='FRDR2' AND PRODUCTIONLINE !='HDTG2' AND PRODUCTIONLINE !='XHDTG' ";
        }
        pager = new Pager(pageSize, currentPage);

        List list = PmcWeekDayStartRateDao.queryWeekDayStartRateD(con,YYYY,PRODUCTIONLINENAME,whereSQL);
        atx.setValue("weekDayStartRateR", list);

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
/*        if (atx.getStringValue("limit") != null && !atx.getStringValue("limit").equals("")) {
            pageSize = new Integer(atx.getStringValue("limit"));
        }
        if (atx.getStringValue("start") != null && !atx.getStringValue("start").equals("")) {
            currentPage = new Integer(atx.getStringValue("start")) / pageSize + 1;
        }
        startTime = DateUtils.parse(atx.getStringValue("START_TIME"),DictConstants.FORMAT_DATE);
        lineCode = atx.getStringValue("PRODUCTIONLINENAME"," ");*/
        YYYY=atx.getStringValue("YYYY", "");
        PRODUCTIONLINENAME=atx.getStringValue("PRODUCTIONLINENAME", "");
        queryType=atx.getStringValue("queryType", "");
        return 1;
    }
}
