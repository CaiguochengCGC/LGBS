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
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;
import com.hanthink.pmc.dao.PmcPpStationDao;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuerBeatAllAction extends ActionImp {
    private Connection con = null;
    private String codeType;
    private String quryType;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【CYCLETIME】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = PmcPpStationDao.queryBeatStaAll(con, codeType, quryType);
        DynaBeanMap map=new DynaBeanMap();
        map.put("TEXT","全部");
        map.put("VALUE","  ");
        list.add(0,map);
        atx.setValue("PMC_ALM_STATION", list.toArray());

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        codeType = atx.getStringValue("CODE_TYPE", "").trim();
        quryType = atx.getStringValue("QURY_TYPE", "").trim();

        return 1;
    }
}
