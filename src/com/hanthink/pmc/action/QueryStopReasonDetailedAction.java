
/**
 * 
 *
 * @File name:  QueryPubUserRoleByPagerAction.java 
 * @Create on:  2010-07-09 10:07:109
 * @Author   :  ht
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
import com.hanthink.pmc.dao.PmcCalenderDao;

import java.sql.Connection;

public class QueryStopReasonDetailedAction extends ActionImp {
    private Connection con = null;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private String lineName;
    private String workTime;
    private String shift;
    private String stopType;
    private String queryTypeT;


    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【停线详情】", ex);
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
        ComboPager cp = PmcCalenderDao.queryStopLineDetailed(con, pager, lineName,workTime,shift,stopType,whereSQL);
        atx.setValue("STOP_DETAILED", cp);
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
        lineName = atx.getStringValue("lineName", "").trim();
        workTime = atx.getStringValue("workTime", "").trim();
        shift = atx.getStringValue("shift", "").trim();
        stopType = atx.getStringValue("stopType", "").trim();
        queryTypeT=atx.getStringValue("queryType", "");
        return 1;
    }
}
