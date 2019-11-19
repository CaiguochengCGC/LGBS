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

package com.hanthink.zzcj.action;

import java.sql.Connection;
import java.util.Date;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.zzcj.dao.PmcAlarmLogDao;
import com.hanthink.util.DictConstants;

public class QueryAlarmLogAction extends ActionImp {
    private Connection con = null;
    private Date startEffectTime;
    private Date endEffectTime;
    private String productionline;
    private String data1;
    private String banci;
    private String data3;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【故障事故记录查询】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        pager = new Pager(pageSize, currentPage);

        ComboPager cp = PmcAlarmLogDao.queryAlarmLogImport(con, startEffectTime, endEffectTime, productionline, banci, data3, pager);
        atx.setValue("ALARM_LOG", cp);
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
        data1 = atx.getStringValue("DATA1", "").trim();
        banci = atx.getStringValue("BANCI", "").trim();
        data3 = atx.getStringValue("DATA3", "").trim();

        return 1;
    }
}
