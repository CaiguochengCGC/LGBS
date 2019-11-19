package com.hanthink.pmc.action;

import java.sql.Connection;
import java.util.Date;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pmc.dao.TabStopLineDao;
import com.hanthink.util.DictConstants;

public class QueryTabStopLineAction extends ActionImp {
    private Connection con = null;
    private String EventData1;
    private String EventData2;
    private String EventData46;
    private String EventData3;
    private String stopReson;
    private Date startDate;
    private Date endDate;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private String banci;
    private String queryTypeT;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【TabStopLine】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        if("1".equals(queryTypeT)){
            whereSQL=" AND (EventData40='DLHD3' or EventData40='FDRDL' or EventData40='FDRDR' or EventData40='FRDL2' or EventData40='FRDL3' or EventData40='FRDR2' or EventData40='HDTG2' or EventData40='XHDTG') ";
        }else if("2".equals(queryTypeT)){
            whereSQL=" AND EventData40!='DLHD3' AND EventData40!='FDRDL' AND EventData40!='FDRDR' AND EventData40!='FRDL2' AND EventData40!='FRDL3' AND EventData40!='FRDR2' AND EventData40 !='HDTG2' AND EventData40 !='XHDTG' ";
        }
        pager = new Pager(pageSize, currentPage);
//        System.out.println("*****&&&&*****");
//        System.out.println(startDate);
//        System.out.println(endDate);
//        System.out.println("*****&&&&*****");
        ComboPager cp = TabStopLineDao.queryTabStopLine(con, startDate, endDate, EventData1, EventData2,EventData46, pager,banci,stopReson,EventData3,whereSQL);
        atx.setValue("TAB_STOP_LINE", cp);

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

        startDate = DateUtils.parse(atx.getStringValue("START_PLAN_DATE"), DictConstants.FORMAT_DATETIME);
        endDate = DateUtils.parse(atx.getStringValue("END_PLAN_DATE"), DictConstants.FORMAT_DATETIME);

        EventData1 = atx.getStringValue("EventDate1", "").trim();
        EventData2 = atx.getStringValue("EventDate2", "").trim();
        EventData46 = atx.getStringValue("EventDate46", "").trim();
        EventData3 = atx.getStringValue("EventData3", "").trim();
        banci=atx.getStringValue("BANCI", "");
        stopReson = atx.getStringValue("STOPRESON", "");
        queryTypeT=atx.getStringValue("queryType", "");
        return 1;
    }
}
