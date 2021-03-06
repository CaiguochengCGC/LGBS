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

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;

import com.hanthink.pmc.dao.PmcPpStationDao;

public class QueryPmcPpStationByProductNameAction extends ActionImp {
    private Connection con = null;
    private java.lang.String codeType;
    private java.lang.String queryCode;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【生产工位表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = PmcPpStationDao.queryPmcPpStationByProductName(con, codeType, queryCode);

        atx.setValue("PMC_PP_STATION", list.toArray());

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        codeType = atx.getStringValue("CODE_TYPE", "").trim();
        queryCode = atx.getStringValue("QUERY_CODE", "").trim();

        return 1;
    }
}
