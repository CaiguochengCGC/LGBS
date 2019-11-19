/**
 * 
 *
 * @File name:  PubRolePrivilegesDao.java 
 * @Create on:  2010-06-17 10:41:421
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
 * 【角色权限关系:PUB_Role_Privileges】的Dao操作类
 * 
 */
public class PubRolePrivilegesDao extends DAO {

    
    /**
     * 查询所有【角色权限关系:PUB_ROLE_PRIVILEGES】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2012-05-25
     * @author 兰永明
     * 
     */
    public static ComboPager queryPubRolePrivilegesByPager(Connection con, Pager pager, 
            String roleID, String category, String description) throws Exception {
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select B.PK_PRIVILEGES_ID, B.CATEGORY, B.DESCRIPTION, B.TYPE");
        cqexp.select(" FROM PUB_ROLE A, PUB_PRIVILEGES B, PUB_ROLE_PRIVILEGES C");
        cqexp.where("A.PK_ROLE_ID = C.PK_ROLE_ID AND B.PK_PRIVILEGES_ID = C.PK_PRIVILEGES_ID");
        cqexp.filed("A.PK_ROLE_ID", CQExp.EQ, roleID);
        cqexp.filed("".equals(category) ? null : "B.CATEGORY", CQExp.EQ, category);
        cqexp.filed("".equals(description) ? null : "B.DESCRIPTION", CQExp.LIKE, "%" + description + "%");
        
        cqexp.orderByAsc("CATEGORY");
        cqexp.orderByAsc("TYPE");
        cqexp.orderByAsc("SORT");
        
        logger.info("查询角色权限：" + cqexp.getSql());
        
        return cqexp.getDynaBeanMapComboPager("PUB_ROLE_PRIVILEGES", pager, con);
    }
	

}
