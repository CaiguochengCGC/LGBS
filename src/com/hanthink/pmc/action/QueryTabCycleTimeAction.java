package com.hanthink.pmc.action;

import java.sql.Connection;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pmc.dao.TabCycleTimeDao;
import com.hanthink.util.DictConstants;

public class QueryTabCycleTimeAction extends ActionImp {
    private Connection con = null;
    private String EventData13;
    private String EventData1;
    private String EventData2;
    private java.util.Date startTime;
    private java.util.Date endTime;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private String banci;
    private String queryTypeT;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【TabCycleTime】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        pager = new Pager(pageSize, currentPage);
        String whereSQL="";
        if("1".equals(queryTypeT)){
            whereSQL=" AND (LINE_ENG='DLHD3' or LINE_ENG='FDRDL' or LINE_ENG='FDRDR' or LINE_ENG='FRDL2' or LINE_ENG='FRDL3' or LINE_ENG='FRDR2' or LINE_ENG='HDTG2' or LINE_ENG='XHDTG') ";
        }else if("2".equals(queryTypeT)){
            whereSQL=" AND LINE_ENG!='DLHD3' AND LINE_ENG!='FDRDL' AND LINE_ENG!='FDRDR' AND LINE_ENG!='FRDL2' AND LINE_ENG!='FRDL3' AND LINE_ENG!='FRDR2' AND LINE_ENG !='HDTG2' AND LINE_ENG !='XHDTG' ";
        }
        ComboPager cp = TabCycleTimeDao.queryTabCycleTime(con, startTime, EventData13, EventData1, EventData2, pager,banci,endTime,whereSQL);

        atx.setValue("TAB_CYCLE_TIME", cp);

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        queryTypeT=atx.getStringValue("queryType", "");
        if (atx.getStringValue("limit") != null && !atx.getStringValue("limit").equals("")) {
            pageSize = new Integer(atx.getStringValue("limit"));
        }
        if (atx.getStringValue("start") != null && !atx.getStringValue("start").equals("")) {
            currentPage = new Integer(atx.getStringValue("start")) / pageSize + 1;
        }

        startTime = DateUtils.parse(atx.getStringValue("START_PLAN_DATE"), DictConstants.FORMAT_DATETIME);
        endTime = DateUtils.parse(atx.getStringValue("END_PLAN_DATE"), DictConstants.FORMAT_DATETIME);
        EventData13 = atx.getStringValue("CT_CHANG", "").trim();
        EventData1 = atx.getStringValue("CAR_TYPE", "").trim();
        EventData2 = atx.getStringValue("STATION", "").trim();
        banci=atx.getStringValue("BANCI", "");

        return 1;
    }
}
