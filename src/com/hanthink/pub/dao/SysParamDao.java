/**
 * @File name:  SysParamDao.java 
 * @Create on:  2011-04-11 17:22:898
 * @Author   :  郑胜军
 * @ChangeList
 * Date         Editor              ChangeReasons
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
/**
 * 【SYS_PARAM系统参数:SYS_PARAM】的Dao操作类
 * 
 */
public class SysParamDao extends DAO {
	
    /**
     * 根据参数名集合得到参数集合
     * @date 2011-05-06
     * @author 兰永明
     */
    public static List querySysParams(Connection con, Object[] paramCodes) throws Exception {
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("SELECT PARAM_CODE, PARAM_VAL, PARAM_TYPE FROM SYS_PARAM");
        cqexp.where();
        cqexp.filed("PARAM_CODE", CQExp.IN, paramCodes);

        logger.info("根据参数名集合得到参数集合:" + cqexp.getSql());
        return cqexp.getDynaBeanMapList("SYS_PARAM", con);
    }
    
    
    /**
     * 根据参数名得到参数（只得到一个）
     * @date 2011-05-06
     * @author 兰永明
     */
    public static String getSysParamVal(Connection con, String paramCode) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT PARAM_VAL FROM SYS_PARAM WHERE PARAM_CODE=? ");
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        query.setCommand(sql.toString());
        query.setString(1, paramCode);
        logger.info("根据参数名得到参数:" + sql);
        
        DynaBeanMap dynaBeanMap = query.getDynaBeanMap("PARAM", con);
        if (null == dynaBeanMap) {
            return null;
        }
        
        return (String) dynaBeanMap.get("PARAM_VAL");
    }
    
    /**
     * 分页查询所有【SYS_PARAM系统参数:SYS_PARAM】信息
     * @param con
     * @param pager
     * @return
     * @throws Exception
     * @date 2011-04-11
     * @author 郑胜军
     *
     */
     public static ComboPager querySysParamByPager(Connection con,Pager pager)throws Exception{
         StringBuffer sql = new StringBuffer();
         sql.append(" SELECT PARAM_CODE, PARAM_GROUP, PARAM_NAME, PARAM_VAL, IS_EDIT, PARAM_TYPE, NOTE, UDA_1, UDA_2, UDA_3 FROM SYS_PARAM order by UDA_3,PARAM_CODE");
         CQuery query = CQueryFactoryTool.createFactory().createCQuery();
         query.setCommand(sql.toString());
         logger.info("参数查询:" + sql);
         return query.getDynaBeanMapComboPager("SYS_PARAM", pager,con);
     }
	
}
