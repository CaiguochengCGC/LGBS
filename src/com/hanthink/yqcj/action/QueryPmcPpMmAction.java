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

package com.hanthink.yqcj.action;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.yqcj.dao.PmcPpMmDao;

public class QueryPmcPpMmAction extends ActionImp {
    private Connection con = null;
    private Date yyyyMm;
    private String banci;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【产量月报表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = PmcPpMmDao.queryPmcPpMm(con, yyyyMm,banci);
        atx.setValue("PMC_PP_MM", list.toArray());
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
