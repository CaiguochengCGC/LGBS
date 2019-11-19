/**
 * 
 *
 * @File name:  QueryPmcPpStationAction.java 
 * @Create on:  2014-03-20 09:31:72
 * @Author   :  luoshw
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import com.hanthink.pmc.dao.PmcPpStationDao;

import java.sql.Connection;
import java.util.List;

public class QueryStopCountResonAction extends ActionImp {
    private Connection con = null;
    private String codeType;
    private String quryType;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【停线原因】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = PmcPpStationDao.queryStopCountReson(con);

        atx.setValue("STOP_RESON", list.toArray());

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        return 1;
    }
}