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

public class QueryRealTimeReportAction extends ActionImp {
    private Connection con = null;
    /**
     * hxt 注释 20190326（实时报表用不上这些条件）
     */
//    private Date startEffectTime;
//    private Date endEffectTime;
//    private String productionline;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private String queryType;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【实时报表查询】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        pager = new Pager(pageSize, currentPage);
        String whereSQL="";
        if("1".equals(queryType)){
            whereSQL="AND (LineCode='DLHD3' or LineCode='FDRDL' or LineCode='FDRDR' or LineCode='FRDL2' or LineCode='FRDL3' or LineCode='FRDR2' or LineCode='HDTG2' or LineCode='XHDTG')";
        }else if("2".equals(queryType)){
            whereSQL="AND LineCode!='DLHD3' AND LineCode!='FDRDL' AND LineCode!='FDRDR' AND LineCode!='FRDL2' AND LineCode!='FRDL3' AND LineCode!='FRDR2' AND LineCode !='HDTG2' AND LineCode !='XHDTG'";
        }
        ComboPager cp = PmcAlarmLogDao.queryRealTime(con, pager,whereSQL);
        atx.setValue("REALTIME", cp);
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
        queryType=atx.getStringValue("queryType", "");
//        startEffectTime = DateUtils.parse(atx.getStringValue("START_EFFECT_TIME"), DictConstants.FORMAT_DATETIME);
//        endEffectTime = DateUtils.parse(atx.getStringValue("END_EFFECT_TIME"), DictConstants.FORMAT_DATETIME);
//        productionline = atx.getStringValue("PRODUCTIONLINENAME", "").trim();
        return 1;
    }
}
