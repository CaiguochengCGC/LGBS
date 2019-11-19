
/**
 * 
 *
 * @File name:  QueryPmcPpYyAction.java 
 * @Create on:  2014-04-03 14:37:303
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.zzcj.action;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.zzcj.dao.PmcPpYyDao;

public class QueryPmcPpYyAction extends ActionImp {
    private Connection con = null;
    private Date yyyy;
    private String banci;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【产量年报表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = PmcPpYyDao.queryPmcPpYy(con,yyyy,banci);
        atx.setValue("PMC_PP_YY", list.toArray());
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        yyyy = DateUtils.parse(atx.getStringValue("YYYY"), "yyyy");
        banci=atx.getStringValue("BANCI","");
        return 1;
    }
}
