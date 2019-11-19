package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;
import com.hanthink.pmc.dao.TabStopLineDao;
import com.hanthink.util.DictConstants;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class QueryStopCountDetaileAction extends ActionImp {
    private Connection con = null;
    private java.util.Date startTime;
    private java.util.Date endTime;
    private String lineCode;
    private String stopType;
    private String station;
    private String shift;
    private String queryTypeT;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【TabStopLine】", "请选择线体");
    }
    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        if("1".equals(queryTypeT)){
            whereSQL=" AND (EventData8='DLHD3' or EventData8='FDRDL' or EventData8='FDRDR' or EventData8='FRDL2' or EventData8='FRDL3' or EventData8='FRDR2' or EventData8='HDTG2' or EventData8='XHDTG') ";
        }else if("2".equals(queryTypeT)){
            whereSQL=" AND EventData8!='DLHD3' AND EventData8!='FDRDL' AND EventData8!='FDRDR' AND EventData8!='FRDL2' AND EventData8!='FRDL3' AND EventData8!='FRDR2' AND EventData8 !='HDTG2' AND EventData8 !='XHDTG' ";
        }
        List cp = TabStopLineDao.queryStopCountDetail(con, startTime, endTime, lineCode, stopType,shift,station,whereSQL,queryTypeT);
        atx.setValue("PMC_STOP_RESON_PLANEL", cp);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        startTime = DateUtils.parse(atx.getStringValue("START_TIME"), DictConstants.FORMAT_DATE);
        endTime   = DateUtils.parse(atx.getStringValue("END_TIME"), DictConstants.FORMAT_DATE);
        lineCode = atx.getStringValue("LINECODE", "").trim();
        stopType = atx.getStringValue("STOPTYPE", "").trim();
        shift = atx.getStringValue("SHIFT", "").trim();
        station = atx.getStringValue("STATION", "").trim();
        queryTypeT=atx.getStringValue("queryType", "");

        return 1;
    }
}
