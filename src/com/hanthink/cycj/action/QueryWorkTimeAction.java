/**
 * 
 *
 * @File name:  QueryPmcPpMmAction.java 
 * @Create on:  2014-04-03 14:34:429
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.cycj.action;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.CQExp;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.cycj.dao.PmcPpMmDao;

public class QueryWorkTimeAction extends ActionImp {
    private Connection con = null;
    private Date yyyyMm;
    private String banci;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【查询工作时间配置】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
    	CQExp cqExp = CQExp.instance();
        cqExp.select("select *  from CYCJ_tabWorkTime ");
        logger.debug("油漆车间查询工作时间信息:  " + cqExp.getSql());
        List list= cqExp.getDynaBeanMapList("CYCJ_WORKTIME", con);
        atx.setValue("CYCJ_WORKTIME", list.toArray());
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        yyyyMm = DateUtils.parse(atx.getStringValue("YYYY_MM"), "yyyy-MM");
        banci=atx.getStringValue("BANCI","");
        return 1;
    }
}
