
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
import com.hanthink.pmc.dao.PmcViewRecordDao;
import com.hanthink.pmc.dao.PmcWeekDayStartRateDao;

import java.sql.Connection;
import java.util.List;

public class AddPmcWeekDayAction extends ActionImp {
    private Connection con = null;
    private String ID;
    String WEEKL;
    String WORKSTARTTIME;
    String WORKSECTION;
    String AREA;
    String STATION;
    String MODELS;
    String PROBLEMDESCRIPTION;
    String CAUSEANALISYS;
    String MEASURES;
    String DOWNTIMEMINUTS;
    String REGIONALRESPONSIBLEPERSON;
    String SOLVEPEOPLE;
    String STATEOFTHEPROBLEM;
    String WHERHERTHEOVERDUER;


    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "添加【周开动率备注】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        PmcViewRecordDao.AddWRate(con,ID,WEEKL,WORKSTARTTIME,WORKSECTION,AREA,STATION,MODELS,PROBLEMDESCRIPTION,CAUSEANALISYS,MEASURES,DOWNTIMEMINUTS,
                REGIONALRESPONSIBLEPERSON,SOLVEPEOPLE,STATEOFTHEPROBLEM,WHERHERTHEOVERDUER);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        ID=atx.getStringValue("ID");
        WEEKL=atx.getStringValue("WEEKL");
        WORKSTARTTIME=atx.getStringValue("WORKSTARTTIME");
        WORKSECTION=atx.getStringValue("WORKSECTION");
        AREA=atx.getStringValue("AREA");
        STATION=atx.getStringValue("STATION");
        MODELS=atx.getStringValue("MODELS");
        PROBLEMDESCRIPTION=atx.getStringValue("PROBLEMDESCRIPTION");
        CAUSEANALISYS=atx.getStringValue("CAUSEANALISYS");
        MEASURES=atx.getStringValue("MEASURES");
        DOWNTIMEMINUTS=atx.getStringValue("DOWNTIMEMINUTS");
        REGIONALRESPONSIBLEPERSON=atx.getStringValue("REGIONALRESPONSIBLEPERSON");
        SOLVEPEOPLE=atx.getStringValue("SOLVEPEOPLE");
        STATEOFTHEPROBLEM=atx.getStringValue("STATEOFTHEPROBLEM");
        WHERHERTHEOVERDUER=atx.getStringValue("WHERHERTHEOVERDUER");
        return 1;
    }
}
