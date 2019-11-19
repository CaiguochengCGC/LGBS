package com.hanthink.pmc.action;

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pmc.dao.TabProductHourDao;
import com.hanthink.util.DictConstants;

public class QueryTabProductHourAction extends ActionImp {
    private Connection con = null;
    private java.util.Date eventDate;
    private String carName;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private String line;
    private String queryType;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【TabStopLine】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        if("1".equals(queryType)){
            whereSQL=" AND (EventDate1='DLHD3' or EventDate1='FDRDL' or EventDate1='FDRDR' or EventDate1='FRDL2' or EventDate1='FRDL3' or EventDate1='FRDR2' or EventDate1='HDTG2' or EventDate1='XHDTG') ";
        }else if("2".equals(queryType)){
            whereSQL=" AND EventDate1!='DLHD3' AND EventDate1!='FDRDL' AND EventDate1!='FDRDR' AND EventDate1!='FRDL2' AND EventDate1!='FRDL3' AND EventDate1!='FRDR2' AND EventDate1 !='HDTG2' AND EventDate1 !='XHDTG' ";
        }
        pager = new Pager(pageSize, currentPage);
     /*   List cp = TabProductHourDao.queryTabStopLine(con,eventDate,carName);
        atx.setValue("TAB_PRODUCT_HOUR", cp);*/

        ComboPager cp = TabProductHourDao.queryTabStopLinePage(con,eventDate,carName,pager,line,whereSQL);
        atx.setValue("TAB_PRODUCT_HOUR", cp);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        if (atx.getStringValue("limit") != null && !atx.getStringValue("limit").equals("")) {
            pageSize = new Integer(atx.getStringValue("limit"));
        }
        if (atx.getStringValue("start") != null && !atx.getStringValue("start").equals("")) {
            currentPage = new Integer(atx.getStringValue("start")) / pageSize + 1;
        }
        eventDate = DateUtils.parse(atx.getStringValue("EventData"), DictConstants.FORMAT_DATE);
        carName = atx.getStringValue("CARNAME", "");
        line = atx.getStringValue("PRODUCTIONLINENAME", "");
        queryType=atx.getStringValue("queryType", "");
        return 1;
    }
}
