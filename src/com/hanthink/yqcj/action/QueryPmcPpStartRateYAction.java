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
import java.util.Date;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.yqcj.dao.PmcPpStartRateYDao;

public class QueryPmcPpStartRateYAction extends ActionImp {
    private Connection con = null;
    private Date yyyy;
    private String banci;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【年开动率】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = PmcPpStartRateYDao.queryPmcPpStartRateY(con, yyyy,banci);
        atx.setValue("PMC_PP_START_RATE_Y", list.toArray());
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        yyyy = DateUtils.parse(atx.getStringValue("YYYY"),"yyyy");
        banci=atx.getStringValue("BANCI","");
        return 1;
    }
}
