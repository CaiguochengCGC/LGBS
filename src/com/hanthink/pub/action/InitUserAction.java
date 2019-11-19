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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DbUtils;

import com.hanthink.pub.dao.PubDataDictDao;
import com.hanthink.pub.dao.PubUserDao;
import com.hanthink.util.SessionUtils;

public class InitUserAction extends ActionImp {
	private Connection con = null;

	@Override
	protected void doException(ActionContext atx, Exception ex) {
		if (!(ex instanceof UserOperationException)) {
			atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"), "【PUB_USER】", ex);
		}
	}

	@Override
	protected int performExecute(ActionContext atx) throws Exception {
		String userName = SessionUtils.getUserNo(atx);
		Map<String, Object> result = new HashMap<String, Object>();

		if (null == userName) {
		    result.put("result", "no_login");
		    result.put("error", "用户未登录！");
		    atx.setObjValue("RESULT", result);
		    return 1;
		}
		
		DynaBeanMap userBeanMap = PubUserDao.getPubUserByPk(con, userName);
		if (userBeanMap == null) {
		    result.put("result", "fail");
		    result.put("error", "找不到用户信息！");
			atx.setObjValue("RESULT", result);
			return 1;
		}
		userBeanMap.remove("USER_PWD");
		
		
		//获取登录时间
		Date loginTime = DbUtils.getDbTime();
		userBeanMap.put("LOGIN_TIME", loginTime);
		
		result.put("result", "ok");
		result.put("USER", userBeanMap);
		
		logger.debug("将功能权限传到前台");
		result.put("ACTIONS", SessionUtils.getPrivilegesActions(atx));
		
		atx.setObjValue("RESULT", result);
		
		return 1;
	}

	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
	    
		con = atx.getConection();
		return 1;
	}
}
