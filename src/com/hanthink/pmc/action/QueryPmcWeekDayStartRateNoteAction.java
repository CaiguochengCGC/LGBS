
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
import com.hanthink.pmc.dao.PmcWeekDayStartRateDao;
import java.sql.Connection;
import java.util.List;

public class QueryPmcWeekDayStartRateNoteAction extends ActionImp {
    private Connection con = null;
    private String ID;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "查询【周开动率】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        List list = PmcWeekDayStartRateDao.queryWeekDayStartRateNoteD(con,ID,whereSQL);
        atx.setValue("weekDayStartRateNoteR", list);
        return 1;
    }
    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        ID = atx.getStringValue("ID");
        return 1;
    }
}
