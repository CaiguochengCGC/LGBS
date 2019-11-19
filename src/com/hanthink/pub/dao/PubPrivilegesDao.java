/**
 * 
 *
 * @File name:  PubPrivilegesDao.java 
 * @Create on:  2010-05-11 09:16:31
 * @Author   :  ht
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pub.dao;

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.CQuery;
import cn.boho.framework.po.CQueryFactoryTool;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.DAO;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.po.Pager;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pub.po.PubPrivilegesPO;
import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

/**
 * 【权限:PUB_Privileges】的Dao操作类
 * 
 */
public class PubPrivilegesDao extends DAO {

	/**
     * 用户登录时获取用户的所有权限的值
     * @author 兰永明
     * @create_date 2012-5-25 上午09:56:38
     * @param con
     * @param userName 用户名
     * @param category 模块
     * @param type 权限类型
     * @return
     * @throws Exception
     */
    public static List queryPubPrivileges(Connection con, String userName, String type) throws Exception {

        StringBuffer sql = new StringBuffer();
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        
        if(DictConstants.SUPERUSER.equals(userName)){
            sql.append("select D.PK_PRIVILEGES_ID, D.CONTENT, D.DESCRIPTION, D.CATEGORY, D.I18N_RESOURCES");
            sql.append(" from  PUB_PRIVILEGES D left join PUB_MODULE F on D.CATEGORY = F.PK_MODULE_CODE");
            sql.append(" and D.TYPE=?");
            sql.append(" order by D.SORT");
            
            query.setCommand(sql.toString());
            query.setString(1, type);
            
        }else{
           
            sql.append("select D.PK_PRIVILEGES_ID, D.CONTENT, D.DESCRIPTION, D.CATEGORY, D.I18N_RESOURCES");
            
            sql.append(" from PUB_PRIVILEGES D left join PUB_MODULE F on D.CATEGORY = F.PK_MODULE_CODE");
            sql.append(" inner join PUB_ROLE_PRIVILEGES E on E.PK_PRIVILEGES_ID = D.PK_PRIVILEGES_ID");
            sql.append(" inner join PUB_ROLE B on B.PK_ROLE_ID = E.PK_ROLE_ID");
            sql.append(" inner join PUB_USER_ROLE C on C.PK_ROLE_ID = B.PK_ROLE_ID");
            sql.append(" inner join PUB_USER A on A.PK_USER_NAME = C.PK_USER_NAME");
            sql.append(" and A.PK_USER_NAME=?");
            sql.append(" and D.TYPE=?");
            
            sql.append(" order by D.SORT");
            
            query.setCommand(sql.toString());
            query.setString(1, userName);
            query.setString(2, type);
        }
        
        logger.info("获取登录用户权限：" + sql);
        return query.getDynaBeanMapList("PUB_PRIVILEGES", con);
    }
    
    
    /**
     * 用户登录时获取用户的所有权限的值
     * @author 兰永明
     * @create_date 2012-5-25 上午09:56:38
     * @param con
     * @param userName 用户名
     * @param category 模块
     * @param type 权限类型
     * @return
     * @throws Exception
     */
    public static List queryClientPubPrivileges(Connection con, String userName, String type, String localIP) throws Exception {

        StringBuffer sql = new StringBuffer();
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        
        sql.append("select D.PK_PRIVILEGES_ID, D.CONTENT, D.DESCRIPTION, D.CATEGORY, D.I18N_RESOURCES");
        sql.append(" from PUB_USER A, PUB_ROLE B, PUB_USER_ROLE C, PUB_PRIVILEGES D, PUB_ROLE_PRIVILEGES E,PUB_MODULE F");
        
        sql.append(" where D.CATEGORY = F.PK_MODULE_CODE");
        sql.append(" and A.PK_USER_NAME = C.PK_USER_NAME ");
        sql.append(" and B.PK_ROLE_ID = C.PK_ROLE_ID"); 
        sql.append(" and B.PK_ROLE_ID = E.PK_ROLE_ID "); 
        sql.append(" and D.PK_PRIVILEGES_ID = E.PK_PRIVILEGES_ID");
        
        sql.append(" and A.PK_USER_NAME=?");
        sql.append(" and D.TYPE=?");
        sql.append(" and D.LOCAL_IP=?");
        
        sql.append(" order by D.SORT");
        
        query.setCommand(sql.toString());
        query.setString(1, userName);
        query.setString(2, type);
        query.setString(3, localIP);
        
        logger.info("获取登录用户权限：" + sql);
        return query.getDynaBeanMapList("PUB_PRIVILEGES", con);
    }
    
    /**
     * 用户登录时获取用户的所有权限的值
     * @author 兰永明
     * @create_date 2012-5-25 上午09:56:38
     * @param con
     * @param userName 用户名
     * @param category 模块
     * @param type 权限类型
     * @return
     * @throws Exception
     */
    public static List queryFnPubPrivileges(Connection con, String userName, String type, String localIP) throws Exception {

        StringBuffer sql = new StringBuffer();
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        
        sql.append("select D.PK_PRIVILEGES_ID, D.CONTENT, D.DESCRIPTION, D.CATEGORY, D.I18N_RESOURCES");
        sql.append(" from PUB_USER A, PUB_ROLE B, PUB_USER_ROLE C, PUB_PRIVILEGES D, PUB_ROLE_PRIVILEGES E,PUB_MODULE F");
        
        sql.append(" where D.CATEGORY = F.PK_MODULE_CODE");
        sql.append(" and A.PK_USER_NAME = C.PK_USER_NAME ");
        sql.append(" and B.PK_ROLE_ID = C.PK_ROLE_ID"); 
        sql.append(" and B.PK_ROLE_ID = E.PK_ROLE_ID "); 
        sql.append(" and D.PK_PRIVILEGES_ID = E.PK_PRIVILEGES_ID");
        
        sql.append(" and A.PK_USER_NAME=?");
        sql.append(" and D.TYPE=?");
        sql.append(" and D.PK_PRIVILEGES_ID=?");
        
        sql.append(" order by D.SORT");
        
        query.setCommand(sql.toString());
        query.setString(1, userName);
        query.setString(2, type);
        query.setString(3, localIP);
        
        logger.info("获取登录用户权限：" + sql);
        return query.getDynaBeanMapList("PUB_PRIVILEGES", con);
    }
    
    /**
     * 分页查询所有【权限:PUB_PRIVILEGES】信息
     * 
     * @param con
     * @param pager
     * @return
     * @throws Exception
     * @date 2010-05-11
     * @author 郑映
     * 
     */
    public static ComboPager queryPubPrivilegesByPager(Connection con, Pager pager, String type, 
            String category, String content, String description, String pkRoleId, boolean roleExists) throws Exception {
        
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select TYPE, CATEGORY, CONTENT, DESCRIPTION, SORT,");
        cqexp.select(" PK_PRIVILEGES_ID");
        cqexp.select(" from PUB_PRIVILEGES A");
        
        cqexp.where();
        cqexp.filed("".equals(type) ? null : "TYPE", CQExp.EQ, type);
        cqexp.filed("".equals(category) ? null : "CATEGORY", CQExp.EQ, category);
        cqexp.filed("".equals(content) ? null : "CONTENT", CQExp.LIKE, "%" + content + "%");
        cqexp.filed("".equals(description) ? null : "DESCRIPTION", CQExp.LIKE, "%" + description + "%");
        
        if (!"".equals(pkRoleId)) {
            if (!roleExists) {
                cqexp.andStr("not exists(select 1 from PUB_ROLE_PRIVILEGES B");
            } else {
                cqexp.andStr("exists(select 1 from PUB_ROLE_PRIVILEGES B");
            }
            cqexp.where("A.PK_PRIVILEGES_ID=B.PK_PRIVILEGES_ID");
            cqexp.filed("PK_ROLE_ID", CQExp.EQ, pkRoleId);
            cqexp.select(")");
        }
        
        
        cqexp.orderByAsc("CATEGORY");
        cqexp.orderByAsc("TYPE");
        cqexp.orderByAsc("SORT");
        logger.info("查询权限：" + cqexp.getSql());
        
        return cqexp.getDynaBeanMapComboPager("PUB_PRIVILEGES", pager, con);
    }
    
    /**
     * 根据菜单路径(唯一值)获得PO对象
     * 
     * @param con
     * @param content
     *            权限ID
     * @return
     * @throws Exception
     * @date 2010-05-11
     * @author 林辉
     * 
     */
    public static PubPrivilegesPO convertBeanMapToPO(DynaBeanMap dynaBeanMap) {
        if (null == dynaBeanMap) {
            return null;
        }
        
        PubPrivilegesPO pubPrivilegesPO = new PubPrivilegesPO();
        pubPrivilegesPO.setPkPrivilegesId(Tools.getStr(dynaBeanMap.get("PK_PRIVILEGES_ID")));
        pubPrivilegesPO.setPrivilegesCode(Tools.getStr(dynaBeanMap.get("PRIVILEGES_CODE")));
        pubPrivilegesPO.setType(Tools.getStr(dynaBeanMap.get("TYPE")));
        pubPrivilegesPO.setContent(Tools.getStr(dynaBeanMap.get("CONTENT")));
        pubPrivilegesPO.setDescription(Tools.getStr(dynaBeanMap.get("DESCRIPTION")));
        pubPrivilegesPO.setCategory(Tools.getStr(dynaBeanMap.get("CATEGORY")));
        pubPrivilegesPO.setSort(Tools.getLong(dynaBeanMap.get("SORT")));
        pubPrivilegesPO.setLastUpdateUsername(Tools.getStr(dynaBeanMap.get("LAST_UPDATE_USERNAME")));
        pubPrivilegesPO.setLastUpdateIp(Tools.getStr(dynaBeanMap.get("LAST_UPDATE_IP")));
        pubPrivilegesPO.setLastUpdateTime(DateUtils.TimestampToDate(dynaBeanMap.get("LAST_UPDATE_TIME")));
        pubPrivilegesPO.setCreateTime(DateUtils.TimestampToDate(dynaBeanMap.get("CREATE_TIME")));
        pubPrivilegesPO.setI18nResources(Tools.getStr(dynaBeanMap.get("I18N_RESOURCES")));
        
        return pubPrivilegesPO;
    }
}
