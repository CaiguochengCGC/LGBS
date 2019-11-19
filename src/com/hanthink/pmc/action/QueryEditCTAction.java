/**
 * 
 *
 * @File name:  QueryPmcViewRecordAction.java 
 * @Create on:  2014-03-29 13:46:161
 * @Author   :  taofl
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
import cn.boho.framework.utils.DateUtils;
import com.hanthink.pmc.dao.PmcViewRecordDao;
import com.hanthink.util.DictConstants;

import java.sql.Connection;
import java.util.List;

public class QueryEditCTAction extends ActionImp {
    private Connection con = null;
    private String queryTypeT;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【计划时间录入】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        if("1".equals(queryTypeT)){
            whereSQL=" AND (PRODUCTION='DLHD3' or PRODUCTION='FDRDL' or PRODUCTION='FDRDR' or PRODUCTION='FRDL2' or PRODUCTION='FRDL3' or PRODUCTION='FRDR2' or PRODUCTION='HDTG2' or PRODUCTION='XHDTG') ";
        }else if("2".equals(queryTypeT)){
            whereSQL=" AND PRODUCTION!='DLHD3' AND PRODUCTION!='FDRDL' AND PRODUCTION!='FDRDR' AND PRODUCTION!='FRDL2' AND PRODUCTION!='FRDL3' AND PRODUCTION!='FRDR2' AND PRODUCTION !='HDTG2' AND PRODUCTION !='XHDTG' ";
        }
        List list = PmcViewRecordDao.queryEditCT(con,whereSQL);
        atx.setValue("CT", list.toArray());
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        queryTypeT=atx.getStringValue("queryType", "");

        return 1;
    }
}
