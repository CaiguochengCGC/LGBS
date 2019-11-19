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

public class QueryProductDetaileAction extends ActionImp {
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
        String whereSQL="";
        if("1".equals(queryTypeT)){
            whereSQL=" AND (LineCode='DLHD3' or LineCode='FDRDL' or LineCode='FDRDR' or LineCode='FRDL2' or LineCode='FRDL3' or LineCode='FRDR2' or LineCode='HDTG2' or LineCode='XHDTG') ";
        }else if("2".equals(queryTypeT)){
            whereSQL=" AND LineCode!='DLHD3' AND LineCode!='FDRDL' AND LineCode!='FDRDR' AND LineCode!='FRDL2' AND LineCode!='FRDL3' AND LineCode!='FRDR2' AND LineCode !='HDTG2' AND LineCode !='XHDTG' ";
        }
        List cp = TabStopLineDao.queryCarDetail(con, startTime, endTime, lineCode, stopType,shift,whereSQL);
        atx.setValue("PMC_PRODUCT_PLANEL", cp);

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        startTime = DateUtils.parse(atx.getStringValue("START_TIME"),DictConstants.FORMAT_DATE);
        endTime   = DateUtils.parse(atx.getStringValue("END_TIME"),DictConstants.FORMAT_DATE);
        lineCode = atx.getStringValue("LINECODE", "").trim();
        stopType = atx.getStringValue("STOPTYPE", "").trim();
        shift = atx.getStringValue("SHIFT", "").trim();
        queryTypeT=atx.getStringValue("queryType", "");

        return 1;
    }
}
