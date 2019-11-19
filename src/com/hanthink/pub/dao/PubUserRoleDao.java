/**
 * 
 *
 * @File name:  PubUserRoleDao.java 
 * @Create on:  2010-07-09 10:07:109
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
 * 【用户角色关系:PUB_USER_ROLE】的Dao操作类
 * 
 */
public class PubUserRoleDao extends DAO {
	
	/**
     * 查询所有【用户角色关系:PUB_USER_ROLE】信息
     * 
     * @param con
     * @param pager
     * @return
     * @throws Exception
     * @date 2010-07-09
     * @author 郑映
     * 
     */
    public static ComboPager queryPubUserRoleByPager(Connection con, Pager pager, String userName, String roleName) throws Exception {
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select B.PK_ROLE_ID, B.ROLE_NAME, B.ROLE_DESCRIPTION");
        cqexp.select(" FROM PUB_USER A, PUB_ROLE B, PUB_USER_ROLE C");
        cqexp.where("A.PK_USER_NAME = C.PK_USER_NAME AND B.PK_ROLE_ID= C.PK_ROLE_ID");
        cqexp.filed("A.PK_USER_NAME", CQExp.EQ, userName);
        cqexp.filed("".equals(roleName) ? null : "B.ROLE_NAME", CQExp.LIKE, "%" + roleName + "%");
        
        cqexp.orderByAsc("B.ROLE_NAME");
        
        return cqexp.getDynaBeanMapComboPager("PUB_USER_ROLE", pager, con);
    }


	
}
