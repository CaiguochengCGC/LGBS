
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

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;

import com.hanthink.pub.dao.PubModuleDao;
import com.hanthink.pub.po.PubModulePO;

public class InitModuleAction extends ActionImp {
	private Connection con = null;

	@Override
	protected void doException(ActionContext atx, Exception ex) {
		if (!(ex instanceof UserOperationException)) {
			atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"), "【PUB_USER】", ex);
		}
	}

	@Override
	protected int performExecute(ActionContext atx) throws Exception {
	    
	    //获取参数
		String pkModuleCode = atx.getStringValue("MODULE_CODE", "").trim();

		//获取模块名称
		DynaBeanMap beanMap = PubModuleDao.getPubModuleByPk(con, pkModuleCode);
		PubModulePO modulePO = PubModuleDao.convertBeanMapToPO(beanMap);
		String moduleName = "";
		if (null != modulePO) {
		    moduleName = modulePO.getModuleName();
		}
		atx.setStringValue("MODULE", moduleName);
		
		return 1;
	}

	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
		con = atx.getConection();
		return 1;
	}
}
