/**
 * 
 *
 * @File name:  PubModuleDao.java 
 * @Create on:  2010-07-07 09:35:421
 * @Author   :  ht
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pub.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.CQuery;
import cn.boho.framework.po.CQueryFactoryTool;
import cn.boho.framework.po.DAO;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pub.po.PubModulePO;
import com.hanthink.util.Tools;

/**
 * 【模块表:PUB_MODULE】的Dao操作类
 */
public class PubModuleDao extends DAO {
    
    /**
     * 查询所有【模块表:PUB_MODULE】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2010-07-07
     * @author bxp
     * 
     */
    public static List queryPubModule(Connection con) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select PK_MODULE_CODE VALUE,MODULE_NAME TEXT from PUB_MODULE order by MODULE_LEVEL, SORT");
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        query.setCommand(sql.toString());
        return query.getDynaBeanMapList("PUB_MODULE", con);
    }
    
    /**
     * 获取用户拥有的模块
     * @author 兰永明
     * @create_date 2013-4-11 上午08:42:20
     * @param con
     * @param menuModules
     * @return
     */
    public static List queryUserModule(Connection con, Set<String> menuModules, String rootModule) throws Exception {
        
        List beans = new ArrayList();
        
        //第一级
        CQExp cqexp = CQExp.instance();
        cqexp.select("select A.PK_MODULE_CODE AS A_MODULE");
        cqexp.select(" from PUB_MODULE A");
        
        cqexp.where();
        cqexp.filed("A.PARENT_MODULE", CQExp.EQ, rootModule);
        cqexp.filed("A.PK_MODULE_CODE", CQExp.IN, menuModules);
        beans.addAll(cqexp.getDynaBeanMapList("PUB_MODULE", con));
        logger.info("获取用户拥有的第一级模块：" + cqexp.getSql());
        
        //第二级
        cqexp = CQExp.instance();
        cqexp.select("select A.PK_MODULE_CODE AS A_MODULE, B.PK_MODULE_CODE AS B_MODULE");
        cqexp.select(" from PUB_MODULE A, PUB_MODULE B");
       
        cqexp.where();
        cqexp.filed("A.PARENT_MODULE", CQExp.EQ, rootModule);
        cqexp.andStr("B.PARENT_MODULE = A.PK_MODULE_CODE");
        cqexp.filed("B.PK_MODULE_CODE", CQExp.IN, menuModules);
        beans.addAll(cqexp.getDynaBeanMapList("PUB_MODULE", con));
        logger.info("获取用户拥有的第二级模块：" + cqexp.getSql());
        
        //第三级
        cqexp = CQExp.instance();
        cqexp.select("select A.PK_MODULE_CODE AS A_MODULE, B.PK_MODULE_CODE AS B_MODULE, C.PK_MODULE_CODE AS C_MODULE");
        cqexp.select(" from PUB_MODULE A, PUB_MODULE B, PUB_MODULE C");
       
        cqexp.where();
        cqexp.filed("A.PARENT_MODULE", CQExp.EQ, rootModule);
        cqexp.andStr("B.PARENT_MODULE = A.PK_MODULE_CODE");
        cqexp.andStr("C.PARENT_MODULE = B.PK_MODULE_CODE");
        cqexp.filed("C.PK_MODULE_CODE", CQExp.IN, menuModules);
        beans.addAll(cqexp.getDynaBeanMapList("PUB_MODULE", con));
        logger.info("获取用户拥有的第三级模块：" + cqexp.getSql());
        
        return beans;
    }
    
    /**
     * 获取用户拥有的模块
     * @author 兰永明
     * @create_date 2012-5-25 上午10:36:29
     * @param con
     * @param user
     * @param parentModule
     * @return
     * @throws Exception
     */
    public static List queryPubModule(Connection con, String user, String parentModule) throws Exception {
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select PK_MODULE_CODE,MODULE_NAME from PUB_MODULE");
        cqexp.where();
        cqexp.filed("PARENT_MODULE", CQExp.EQ, parentModule);
        cqexp.select(" order by MODULE_LEVEL, SORT");
        return cqexp.getDynaBeanMapList("PUB_MODULE", con);
    }
    
    /**
     * 根据主键【PK_MODULE_CODE:PK_MODULE_CODE|】获得信息
     * @param con
     * @param pkModuleCode PK_MODULE_CODE
     * @return
     * @throws Exception
     * @date 2013-02-26
     * @author 兰永明
     *
     */
     public static DynaBeanMap getPubModuleByPk(Connection con,java.lang.String pkModuleCode)throws Exception{
         DynaBeanMap dynaBeanMap = null;
         StringBuffer sql = new StringBuffer();
         sql.append("select * from PUB_MODULE where  PK_MODULE_CODE=?");
         CQuery query = CQueryFactoryTool.createFactory().createCQuery();
         query.setCommand(sql.toString());
         query.setString(1,pkModuleCode);
         dynaBeanMap = query.getDynaBeanMap("PUB_MODULE", con);
         return dynaBeanMap;
     }
     
     /**
     * 根据主键【PK_MODULE_CODE:PK_MODULE_CODE|】获得PO对象
     * @param con
     * @param pkModuleCode PK_MODULE_CODE
     * @return
     * @throws Exception
     * @date 2013-02-26
     * @author 兰永明
     *
     */
    public static PubModulePO convertBeanMapToPO(DynaBeanMap dynaBeanMap) {
        if (dynaBeanMap == null) {
            return null;
        }
        PubModulePO pubModulePO = new PubModulePO();
        pubModulePO.setParentModule(Tools.getStr(dynaBeanMap.get("PARENT_MODULE")));
        pubModulePO.setPkModuleCode(Tools.getStr(dynaBeanMap.get("PK_MODULE_CODE")));
        pubModulePO.setModuleName(Tools.getStr(dynaBeanMap.get("MODULE_NAME")));
        pubModulePO.setSort(Tools.getLong(dynaBeanMap.get("SORT")));
        pubModulePO.setModuleLevel(Tools.getInt(dynaBeanMap.get("MODULE_LEVEL")));
        pubModulePO.setLastUpdateUsername(Tools.getStr(dynaBeanMap.get("LAST_UPDATE_USERNAME")));
        pubModulePO.setLastUpdateIp(Tools.getStr(dynaBeanMap.get("LAST_UPDATE_IP")));
        pubModulePO.setLastUpdateTime(DateUtils.TimestampToDate(dynaBeanMap.get("LAST_UPDATE_TIME")));
        pubModulePO.setCreateTime(DateUtils.TimestampToDate(dynaBeanMap.get("CREATE_TIME")));

        return pubModulePO;
    }
	
}
