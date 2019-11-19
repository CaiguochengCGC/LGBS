
/**
 * 
 *
 * @File name:  GetPubDataDictAction.java   根据主键查询【基础参数维护:PUB_DATA_DICT】
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
import java.util.LinkedList;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;

import com.hanthink.pub.dao.PubDataDictDao;
import com.hanthink.util.DictConstants;

public class QueryPubDataDictAction extends ActionImp {
    private Connection con = null;
    private String codeType;
    private String codeValue;
    private String queryType;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【基础参数维护】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
    	List dbm = new LinkedList();
    	if(DictConstants.QUERY_TYPE_DICT_OR_PARAM.equals(queryType)){
    		dbm = PubDataDictDao.querySysParam(con, codeType, codeValue);
    	}else{
    		dbm = PubDataDictDao.queryPubDataDict(con, codeType, codeValue);
    	}
        atx.setObjValue("PUB_DATA_DICT", dbm);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        codeType = atx.getStringValue("CODE_TYPE", "").trim();
        codeValue = atx.getStringValue("CODE_VALUE", "").trim();
        queryType = atx.getStringValue("QUERY_TYPE", "");
        return 1;
    }
}
