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

public class QueryStopCountAction extends ActionImp {
    private Connection con = null;
    private Date startTime;
    private Date endTime;
    private String lineCode;
    private String stopType;
    private String shift;
    private String queryTypeT;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【TabStopLine】", "请选择线体");
    }
    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        if(stopType=="" || stopType==null){
//            List cp = TabStopLineDao.queryStopDetail1(con, startTime, endTime, lineCode, stopType,shift);
//            atx.setValue("PMC_STOP_RESON_PLANEL", cp);

            return 1;
        }
        String whereSQL="";
        if("1".equals(queryTypeT)){
            whereSQL=" AND (EventData40='DLHD3' or EventData40='FDRDL' or EventData40='FDRDR' or EventData40='FRDL2' or EventData40='FRDL3' or EventData40='FRDR2' or EventData40='HDTG2' or EventData40='XHDTG') ";
        }else if("2".equals(queryTypeT)){
            whereSQL=" AND EventData40!='DLHD3' AND EventData40!='FDRDL' AND EventData40!='FDRDR' AND EventData40!='FRDL2' AND EventData40!='FRDL3' AND EventData40!='FRDR2' AND EventData40 !='HDTG2' AND EventData40 !='XHDTG' ";
        }
        List cp = TabStopLineDao.queryStopDetail(con, startTime, endTime, lineCode, stopType,shift,whereSQL,queryTypeT);
        atx.setValue("PMC_STOP_RESON_PLANEL", cp);

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        startTime = DateUtils.parse(atx.getStringValue("startTime"), DictConstants.FORMAT_DATETIME);
        endTime   = DateUtils.parse(atx.getStringValue("endTime"), DictConstants.FORMAT_DATETIME);
        lineCode = atx.getStringValue("LINECODE", "").trim();
        stopType = atx.getStringValue("STOPTYPE", "").trim();
        shift = atx.getStringValue("SHIFT", "").trim();

        queryTypeT=atx.getStringValue("queryType", "");
        return 1;
    }
}
