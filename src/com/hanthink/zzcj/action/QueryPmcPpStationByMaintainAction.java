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

package com.hanthink.zzcj.action;

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;

import com.hanthink.zzcj.dao.PmcPpStationDao;

public class QueryPmcPpStationByMaintainAction extends ActionImp {
    private Connection con = null;
    private java.lang.String productionName;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【生产工位表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = PmcPpStationDao.queryPmcPpStationByMaintain(con, productionName);

        atx.setValue("PMC_PP_STATION", list.toArray());

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        productionName = atx.getStringValue("PRODUCTIONNAME", "").trim();

        return 1;
    }
}
