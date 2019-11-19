package com.hanthink.util;

import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;

public class ErrorHandler {
	public static void displayError(ActionContext atx, String message) throws UserOperationException{
		UserOperationException uoe = new UserOperationException();
		atx.setErrorContext("EMPTY_MESSAGE", message, uoe);
		throw uoe;
	}
}
