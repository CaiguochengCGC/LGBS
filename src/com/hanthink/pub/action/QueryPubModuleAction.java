
/**
 * 
 *
 * @File name:  QueryPubModuleByPagerAction.java 
 * @Create on:  2010-07-07 09:35:421
 * @Author   :  ht
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pub.action;

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;

import com.hanthink.pub.dao.PubModuleDao;

public class QueryPubModuleAction extends ActionImp {
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【模块表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List cp = PubModuleDao.queryPubModule(con);
        atx.setValue("PUB_MODULE", cp);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        return 1;
    }
}
