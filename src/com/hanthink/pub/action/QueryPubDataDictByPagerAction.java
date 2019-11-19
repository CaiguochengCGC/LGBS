
/**
 * 
 *
 * @File name:  QueryPubDataDictByPagerAction.java 
 * @Create on:  2010-05-14 09:18:484
 * @Author   :  ht
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pub.action;

import java.sql.Connection;
import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;

import com.hanthink.pub.dao.PubDataDictDao;

public class QueryPubDataDictByPagerAction extends ActionImp {
    private Connection con = null;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private String codeTypeName;
    private String codeValueName;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【基础参数维护】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        pager = new Pager(pageSize, currentPage);
        ComboPager cp = PubDataDictDao.queryPubDataDictByPager(con, pager, codeTypeName, codeValueName);
        atx.setValue("PUB_DATA_DICT", cp);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        
        //获取数据库连接
        con = atx.getConection();
        
        //获取分页
        if (atx.getStringValue("limit") != null && !atx.getStringValue("limit").equals("")) {
            pageSize = new Integer(atx.getStringValue("limit"));
        }
        if (atx.getStringValue("start") != null && !atx.getStringValue("start").equals("")) {
            currentPage = new Integer(atx.getStringValue("start")) / pageSize + 1;
        }
        
        //获取参数值
        codeTypeName = atx.getStringValue("codeTypeName", "");
        codeValueName = atx.getStringValue("codeValueName", "");
        
        return 1;
    }
}
