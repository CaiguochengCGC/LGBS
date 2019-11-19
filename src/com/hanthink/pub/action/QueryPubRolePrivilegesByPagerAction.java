
             
/**
 * 
 *
 * @File name:  QueryPubRolePrivilegesByPagerAction.java 
 * @Create on:  2010-06-17 10:41:406
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

import com.hanthink.pub.dao.PubRolePrivilegesDao;

public class QueryPubRolePrivilegesByPagerAction extends ActionImp {
	private Connection con=null;
	private Pager pager=null;
	private int pageSize = 100;
	private int currentPage = 1;
	private String roleId;
	private String category;
	private String description;
	
	@Override
	protected void doException(ActionContext atx, Exception ex) {
		atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"),"【角色权限关系】" ,ex);
	}
	
	@Override
	protected int performExecute(ActionContext atx) throws Exception {
		pager = new Pager(pageSize, currentPage);
		ComboPager cp = PubRolePrivilegesDao.queryPubRolePrivilegesByPager(con, pager, roleId, category, description);
		atx.setValue("PUB_ROLE_PRIVILEGES", cp);
		return 1;
	}
	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
		con = atx.getConection();
		if(atx.getStringValue("limit")!=null&&!atx.getStringValue("limit").equals("")){
			pageSize = new Integer(atx.getStringValue("limit"));
		}
		if(atx.getStringValue("start")!=null&&!atx.getStringValue("start").equals("")){
			currentPage = new Integer(atx.getStringValue("start"))/pageSize+1;
		}
		
		roleId = atx.getStringValue("PK_ROLE_ID","").trim();
		category = atx.getStringValue("CATEGORY","").trim();
		description = atx.getStringValue("DESCRIPTION","").trim();
		return 1;
	}
}
