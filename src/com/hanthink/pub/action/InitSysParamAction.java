/**
 * 
 *
 * @File name:  AddTDataDictAction.java   添加【T_DATA_DICT:T_DATA_DICT】
 * @Create on:  2009-08-07 15:59:484
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
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.service.MessageService;

import com.hanthink.pub.dao.SysParamDao;

public class InitSysParamAction extends ActionImp {
	private Connection con = null;

	@Override
	protected void doException(ActionContext atx, Exception ex) {
		if (!(ex instanceof UserOperationException)) {
			atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"), "【PUB_USER】", ex);
		}
	}

	@Override
	protected int performExecute(ActionContext atx) throws Exception {
	    
	    Object[] paramCodes = atx.getArrayValue("PARAM_CODE");
		List beanMapList = SysParamDao.querySysParams(con, paramCodes);
		atx.setValue("SYS_PARAM", beanMapList);
		return 1;
	}

	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
	    
		con = atx.getConection();
		return 1;
	}
}
