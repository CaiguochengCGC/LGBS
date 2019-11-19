package com.hanthink.pub.acl;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.boho.framework.acl.ACLPolicy;
import cn.boho.framework.acl.AccessControlManage;
import cn.boho.framework.acl.ResourceImpl;
import cn.boho.framework.acl.RoleImpl;
import cn.boho.framework.acl.UserImpl;
import cn.boho.framework.context.DataContext;
import cn.boho.framework.log.LogService;
import cn.boho.framework.log.Logger;

public class JSonACLPolicy implements ACLPolicy {

	private static Logger logger = LogService.newInstance().getLogger(JSonACLPolicy.class.getName());

	public int isAccess(ServletRequest request, ServletResponse response, DataContext context) {
		
		return AccessControlManage.PASS_OK;
	}

	public UserImpl getAnonymousUser(Connection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResourceImpl[] getResourcesByRoleUid(Connection connection, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public RoleImpl[] getRolesByUserUid(Connection connection, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserImpl getSystemUser(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	public void init(Map map) {
		// TODO Auto-generated method stub

	}

	public boolean needAccessControl(UserImpl userimpl) {
		// TODO Auto-generated method stub
		return false;
	}

}