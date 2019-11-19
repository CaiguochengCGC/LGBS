/**
 * 
 *
 * @File name:  PubUserDao.java 
 * @Create on:  2010-07-09 09:14:484
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
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.po.Pager;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pub.po.PubUserPO;
import com.hanthink.util.Tools;

/**
 * 【用户:PUB_USER】的Dao操作类
 * 
 */
public class PubUserDao extends DAO {
    
    /**
     * 获取用户的登录信息
     * 
     * @param con
     * @param pkUserName 用户名
     * @return
     * @throws Exception
     * @date 2012-05-22
     * @author 兰永明
     * 
     */
    public static DynaBeanMap getPubUserByPk(Connection con, String userName) throws Exception {
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select A.PK_USER_NAME, A.USER_CNAME, A.USER_PWD, A.USER_STATUS, A.IS_UPDATE_PWD, A.DEVISION");
        cqexp.select(" from PUB_USER A");
        
        cqexp.where();
        cqexp.filed(userName.equals("") ? null : "A.PK_USER_NAME", CQExp.EQ, userName);
        return cqexp.getDynaBeanMap("PUB_USER", con);
    }
    
    /**
     * 分页查询所有【用户:PUB_USER】信息
     * 
     * @param con
     * @param pager
     * @return
     * @throws Exception
     * @date 2011-04-23
     * @author 兰永明
     * 
     */
    public static ComboPager queryPubUserByPager(Connection con, Pager pager ,String userName,String userCName, Integer userStatus) throws Exception {
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select A.PK_USER_NAME, A.USER_CNAME");
        cqexp.select(" from PUB_USER A");
        
        cqexp.where();
        cqexp.filed(userName.equals("") ? null : "A.PK_USER_NAME", CQExp.LIKE, "%" + userName + "%" );
        cqexp.filed(userCName.equals("") ? null : "A.USER_CNAME", CQExp.LIKE, "%" + userCName + "%" );
        cqexp.filed(null == userStatus ? null : "A.USER_STATUS", CQExp.EQ, userStatus );
        cqexp.orderByAsc("PK_USER_NAME");
        logger.debug("检索用户：" + cqexp.getSql());
        return cqexp.getDynaBeanMapComboPager("PUB_USER", pager, con);
    }
    
    /**
     * 根据主键【用户名:PK_USER_NAME|】获得信息
     * 
     * @param con
     * @param pkUserName
     *            用户名
     * @return
     * @throws Exception
     * @date 2010-07-09
     * @author 郑映
     * 
     */
    public static boolean existsPubUserByPk(Connection con, java.lang.String userName, Integer userStatus) throws Exception {
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select 1 from PUB_USER");
        cqexp.where();
        cqexp.filed(userName.equals("") ? null : "PK_USER_NAME", CQExp.EQ, userName);
        cqexp.filed(null == userStatus ? null : "USER_STATUS", CQExp.EQ, userStatus );
        
        if(null == cqexp.getDynaBeanMap("PUB_USER", con)) {
            return false;
        }
        return true;
    }
    
    /**
     * 根据主键【用户名:PK_USER_NAME|】获得PO对象
     * 
     * @param con
     * @param pkUserName 用户名
     * @return
     * @throws Exception
     * @date 2010-07-09
     * @author 郑映
     * 
     */
    public static PubUserPO convertBeanMapToPO(DynaBeanMap dynaBeanMap) {
        if (dynaBeanMap == null) {
            return null;
        }
        
        PubUserPO pubUserPO = new PubUserPO();
        pubUserPO.setPkUserName(Tools.getStr(dynaBeanMap.get("PK_USER_NAME")));
        pubUserPO.setUserCname(Tools.getStr(dynaBeanMap.get("USER_CNAME")));
        pubUserPO.setUserPwd(Tools.getStr(dynaBeanMap.get("USER_PWD")));
        pubUserPO.setUserStatus(Tools.getInt(dynaBeanMap.get("USER_STATUS")));
        pubUserPO.setIsUpdatePwd(Tools.getInt(dynaBeanMap.get("IS_UPDATE_PWD")));
        pubUserPO.setDevision(Tools.getStr(dynaBeanMap.get("DEVISION")));
        pubUserPO.setLastUpdateUsername(Tools.getStr(dynaBeanMap.get("LAST_UPDATE_USERNAME")));
        pubUserPO.setLastUpdateIp(Tools.getStr(dynaBeanMap.get("LAST_UPDATE_IP")));
        pubUserPO.setLastUpdateTime(DateUtils.TimestampToDate(dynaBeanMap.get("LAST_UPDATE_TIME")));
        pubUserPO.setCreateTime(DateUtils.TimestampToDate(dynaBeanMap.get("CREATE_TIME")));
        
        return pubUserPO;

    }
}
