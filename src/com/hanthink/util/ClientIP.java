package com.hanthink.util;

import javax.servlet.http.HttpServletRequest;

import cn.boho.framework.context.ActionContext;



public class ClientIP {
	
	public static String getClientIP(HttpServletRequest request){
		String ip = "";
	 	ip = request.getLocalAddr();
		return ip;
	}
	
	public static String getRemoteIP(HttpServletRequest request){
        String ip = "";
        ip = request.getRemoteAddr();
        return ip;
    }
	
	public static String getRemoteIP(ActionContext atx){
        return atx.getHttpRequest().getRemoteAddr();
    }
}
