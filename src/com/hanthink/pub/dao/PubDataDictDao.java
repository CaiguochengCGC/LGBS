/**
 * @File name:  PubDataDictDao.java 
 * @Create on:  2010-05-14 09:18:484
 * @Author   :  ht
 * @ChangeList
 * Date         Editor              ChangeReasons
 */
package com.hanthink.pub.dao;
import java.sql.Connection;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.DAO;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.po.Pager;
/**
 * 【基础参数维护:PUB_DATA_DICT】的Dao操作类
 */
public class PubDataDictDao extends DAO {
    
	/**
	 * 查询所有【基础参数维护:PUB_DATA_DICT】信息
	 * @param con
	 * @return
	 * @throws Exception
	 * @date 2010-05-14
	 * @author 王家乐
	 */
	public static List queryPubDataDict(Connection con, String codeType, String codeValue) throws Exception {
	    
	    CQExp cqexp = CQExp.instance();
	    cqexp.select("select CODE_VALUE VALUE, CODE_VALUE_NAME TEXT, OTHER_CODE_VALUE1 from PUB_DATA_DICT");
	    
	    cqexp.where();
	    cqexp.filed("CODE_TYPE", CQExp.EQ, codeType);
	    cqexp.filed(codeValue == null || codeValue.equals("") ? null : "CODE_VALUE", CQExp.LIKE, codeValue + "%");
	    
	    cqexp.orderByAsc("SORT_NO");
		return cqexp.getDynaBeanMapList("PUB_DATA_DICT", con);
	}
	
	/**
	 * 查询所有【基础参数维护:PUB_DATA_DICT】信息
	 * @param con
	 * @return
	 * @throws Exception
	 * @date 2010-05-14
	 * @author 王家乐
	 */
	public static List querySysParam(Connection con, String codeType, String codeValue) throws Exception {
	    
	    CQExp cqexp = CQExp.instance();
	    cqexp.select("select PARAM_VAL VALUE,PARAM_VAL TEXT from SYS_PARAM");
	    
	    cqexp.where();
	    cqexp.filed("PARAM_CODE", CQExp.EQ, codeType);
	    
		return cqexp.getDynaBeanMapList("PUB_DATA_DICT", con);
	}
	
	/**
	 * 根据分组和值获取值名称
	 * @author 兰永明
	 * @create_date 2013-3-22 下午03:35:24
	 * @param con
	 * @param codeType
	 * @param codeValue
	 * @return
	 * @throws Exception
	 */
	public static DynaBeanMap getPubDataDictByValue(Connection con, String codeType, String codeValue) throws Exception {
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select CODE_VALUE VALUE, CODE_VALUE_NAME TEXT, OTHER_CODE_VALUE1 from PUB_DATA_DICT");
        
        cqexp.where();
        cqexp.filed("CODE_TYPE", CQExp.EQ, codeType);
        cqexp.filed(codeValue == null || codeValue.equals("") ? null : "CODE_VALUE", CQExp.EQ, codeValue);
        
        cqexp.orderByAsc("SORT_NO");
        return cqexp.getDynaBeanMap("PUB_DATA_DICT", con);
    }
	
	/**
     * 分页查询所有【基础参数维护:PUB_DATA_DICT】信息
     * 
     * @param con
     * @param pager
     * @return
     * @throws Exception
     * @date 2010-05-14
     * @author bxp
     * 
     */
    public static ComboPager queryPubDataDictByPager(Connection con, Pager pager, String codeTypeName, String codeValueName) throws Exception {
        
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PK_ID, CODE_TYPE, CODE_TYPE_NAME, CODE_VALUE, CODE_VALUE_NAME,");
        cqExp.select(" IS_EDIT, OTHER_CODE_VALUE1, CODE_DESC, SORT_NO, COMPARE");
        cqExp.select(" from PUB_DATA_DICT");
        cqExp.where();
        cqExp.filed("".equals(codeTypeName) ? null : "CODE_TYPE_NAME", CQExp.LIKE, "%" + codeTypeName + "%");
        cqExp.filed("".equals(codeValueName) ? null : "CODE_VALUE_NAME", CQExp.LIKE, "%" + codeValueName + "%");
        cqExp.filed("IS_EDIT", CQExp.NOT_EQ, 0);
        cqExp.orderByAsc("CODE_TYPE");
        cqExp.orderByAsc("SORT_NO");
        
        logger.info("获取数据字典分页的sql语句是:" + cqExp.getSql());
        return cqExp.getDynaBeanMapComboPager("PUB_DATA_DICT", pager, con);
    }
    
    public static List queryPubDataDictByStop(Connection con, String codeType, String codeValue) throws Exception {
	    
	    CQExp cqexp = CQExp.instance();
	    cqexp.select("select CODE_VALUE VALUE, CODE_VALUE_NAME TEXT, COMPARE from PUB_DATA_DICT");
	    
	    cqexp.where();
	    cqexp.filed("CODE_TYPE", CQExp.EQ, codeType);
	    cqexp.filed(codeValue == null || codeValue.equals("") ? null : "CODE_VALUE", CQExp.LIKE, codeValue + "%");
	    
	    cqexp.orderByAsc("SORT_NO");
		return cqexp.getDynaBeanMapList("PUB_DATA_DICT", con);
	}
}
