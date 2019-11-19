/**
 * 
 *
 * @File name:  PubRoleDao.java 
 * @Create on:  2010-06-17 10:21:781
 * @Author   :  ht
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pub.dao;

import java.sql.Connection;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.DAO;
import cn.boho.framework.po.Pager;

/**
 * 【角色:PUB_Role】的Dao操作类
 * 
 */
public class PubRoleDao extends DAO {

	/**
	 * 分页查询所有【角色:PUB_Role】信息
	 * 
	 * @param con
	 * @param pager
	 * @return
	 * @throws Exception
	 * @date 2010-06-17
	 * @author 郑映
	 * 
	 */
	public static ComboPager queryPubRoleByPager(Connection con, Pager pager, String roleName, String userName, boolean userExists) throws Exception {
	    
	    CQExp cqexp = CQExp.instance();
	    cqexp.select("select ROLE_NAME, ROLE_DESCRIPTION, PK_ROLE_ID from PUB_ROLE A");
	    cqexp.where();
		cqexp.filed("".equals(roleName) ? null : "ROLE_NAME", CQExp.LIKE, "%" + roleName + "%");
		
		if (!"".equals(userName)) {
		    if (!userExists) {
		        cqexp.andStr("not exists(select 1 from PUB_USER_ROLE B");
		    } else {
		        cqexp.andStr("exists(select 1 from PUB_USER_ROLE B");
		    }
		    cqexp.where("A.PK_ROLE_ID=B.PK_ROLE_ID");
		    cqexp.filed("PK_USER_NAME", CQExp.EQ, userName);
		    cqexp.select(")");
		}
		
		cqexp.orderByAsc("ROLE_NAME");
		
		return cqexp.getDynaBeanMapComboPager("PUB_ROLE", pager, con);
	}
}
