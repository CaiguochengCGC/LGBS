/**
 * 
 *
 * @File name:  QueryPmcPpMmAction.java 
 * @Create on:  2015-11-30 09:50:40
 * @Author   :  zhyy
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

import com.hanthink.yqcj.dao.TabStopLineDao;

public class QueryTabStopLineResopnAction extends ActionImp {
    private Connection con = null;
    private String yyyyMm;
    private String banci;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【责任部门停线报表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = TabStopLineDao.queryTabStopLinePrespon(con, yyyyMm,banci);
        atx.setValue("TAB_STOP_LINE", list.toArray());
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        yyyyMm = atx.getStringValue("PPDATE","1900-01-01");
        banci=atx.getStringValue("BANCI","");
        return 1;
    }
}
