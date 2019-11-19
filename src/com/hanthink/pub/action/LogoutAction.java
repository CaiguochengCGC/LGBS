package com.hanthink.pub.action;

import com.hanthink.util.DictConstants;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.service.MessageService;

public class LogoutAction extends ActionImp {
	@Override
	protected void doException(ActionContext atx, Exception ex) {
		if (!(ex instanceof UserOperationException)) {
			atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"), "【PUB_USER】", ex);
		}
	}

	@Override
	protected int performExecute(ActionContext atx) throws Exception {
		
		atx.removeSessionValue(DictConstants.SESSION_USER_NO);
        atx.removeSessionValue(DictConstants.SESSION_USER_BEAN);
        atx.removeSessionValue(DictConstants.SESSION_PRI_MENU);
        atx.removeSessionValue(DictConstants.SESSION_PRI_DATA);
        atx.removeSessionValue(DictConstants.SESSION_PRI_ACTION);
        atx.removeSessionValue(DictConstants.SESSION_MODULE);
        
		atx.httpSessionInvalidate();
		
		return 1;
	}

	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
	    
		return 1;
	}
}
