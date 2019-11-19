/**
 * 
 *
 * @File name:  QueryPmcDateImportAction.java 
 * @Create on:  2014-03-16 13:41:509
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pmc.action;

import java.sql.Connection;
import java.util.Date;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pmc.dao.PmcAlarmLogDao;
import com.hanthink.util.DictConstants;

public class QueryStopAndAlarmAction extends ActionImp {
    private Connection con = null;
    private Date startEffectTime;
    private Date endEffectTime;
    private String productionline;
    private String station;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private String banci;
    private String alarmMachine;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【工位停机与报警详情记录】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        pager = new Pager(pageSize, currentPage);

        ComboPager cp = PmcAlarmLogDao.queryStopAndAlarm(con, startEffectTime, endEffectTime, productionline, station, pager,banci,alarmMachine);
        atx.setValue("STA_STOP_RECORD", cp);
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
        startEffectTime = DateUtils.parse(atx.getStringValue("START_EFFECT_TIME"), DictConstants.FORMAT_DATETIME);
        endEffectTime = DateUtils.parse(atx.getStringValue("END_EFFECT_TIME"), DictConstants.FORMAT_DATETIME);
        productionline = atx.getStringValue("PRODUCTIONLINENAME", "").trim();
        station = atx.getStringValue("DATA4", "").trim();
        banci=atx.getStringValue("BANCI", "");
        alarmMachine = atx.getStringValue("ALARM_MACHINE", "").trim();
        return 1;
    }
}
