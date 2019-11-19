package com.hanthink.pub.acl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hanthink.pub.po.PubUserPO;
import com.hanthink.util.DictConstants;

public class PermissionFilter implements Filter {
    
    private final String[] ingoreActions = {"LOGIN_ACTION", "LOGOUT_ACTION", "INIT_CODE_VALUE_ACTION", "LOGIN_REDIRECT_ACTION"};

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) request;
		HttpServletResponse hres = (HttpServletResponse) response;
		HttpSession session = hreq.getSession(false);
		String action = hreq.getParameter("action");
		
		for (int i = 0; i < ingoreActions.length; i++) {
		    if (ingoreActions[i].equals(action)) {
		        chain.doFilter(request, response);
		        return;
		    }
		}
        
        // 会话失效
        if (session == null) {
            this.redirectIndex(hres,action);
            return;
        }
        
        
        
		String userNo = (String) session.getAttribute(DictConstants.SESSION_USER_NO);
		PubUserPO user = (PubUserPO) session.getAttribute(DictConstants.SESSION_USER_BEAN);
		if (userNo != null && null != user && userNo.equals(user.getPkUserName())) {

		    try {
			    chain.doFilter(request, response);
		    } catch (Exception e) {
                throw new ServletException(action);
            }
		} else {
			this.redirectIndex(hres,action);
		}

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	
	public void redirectIndex(HttpServletResponse hres, String action) throws IOException, ServletException {
	    
	    if((null == action) || "".equals(action)){ // 刷新页面
            hres.sendRedirect("index.html");
        } else { // ajax提交
//          hres.setCharacterEncoding("utf-8");
            hres.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = hres.getWriter();          
//          logger.info("json页面跳转");
            writer.print("{\"success\":false,\"errors\":{\"errorcode\":\"NO_LOGIN\",\"errmsg\":\"会话失效，请重新登录！\"}}");
            writer.close();
            hres.flushBuffer();
         }
        
	}

}
