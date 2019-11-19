/**
 * 
 *
 * @File name:  QueryPmcPpKpiyearAction.java 
 * @Create on:  2014-04-03 10:15:755
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.cycj.action;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.cycj.dao.PmcPpKpiyearDao;

public class QueryPmcPpKpiyearAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private String banci;
    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【KPI年报表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = PmcPpKpiyearDao.queryPmcPpKpiyear(con, ppdate,banci);
        atx.setValue("", list.toArray());
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        ppdate = DateUtils.parse(atx.getStringValue("YYYY"), "yyyy");
        banci = atx.getStringValue("BANCI","");
        return 1;
    }
}
