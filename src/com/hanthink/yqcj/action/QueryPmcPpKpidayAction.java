/**
 * 
 *
 * @File name:  QueryPmcPpKpidayAction.java 
 * @Create on:  2014-04-02 09:36:603
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

import com.hanthink.yqcj.dao.PmcPpKpidayDao;
import com.hanthink.util.DictConstants;

public class QueryPmcPpKpidayAction extends ActionImp {
    private Connection con = null;
    private java.util.Date ppdate;
    private String banci;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【KPI日报表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List cp = PmcPpKpidayDao.queryPmcPpKpiday(con, ppdate,banci);
        
        atx.setValue("PMC_PP_KPIDAY", cp);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),DictConstants.FORMAT_DATE);
        banci=atx.getStringValue("BANCI","");
        return 1;
    }
}
