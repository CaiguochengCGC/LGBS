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

package com.hanthink.yqcj.action;

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.yqcj.dao.PmcDateImportDao;
import com.hanthink.util.DictConstants;

public class QueryPmcDateImportImportAction extends ActionImp {
    private Connection con = null;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private java.lang.String sheft;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【计划日期数量导入】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = PmcDateImportDao.queryPmcDateImportImport(con, startDate, endDate, sheft);

        atx.setValue("PMC_DATE_IMPORT", list.toArray());

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        startDate = DateUtils.parse(atx.getStringValue("START_PLAN_DATE", ""), DictConstants.FORMAT_DATE);
        endDate = DateUtils.parse(atx.getStringValue("END_PLAN_DATE", ""), DictConstants.FORMAT_DATE);
        sheft = atx.getStringValue("SHIFT", "");

        return 1;
    }
}
