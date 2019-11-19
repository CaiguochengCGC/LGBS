/**
 * 
 *
 * @File name:  TgJobTaskDao.java 
 * @Create on:  2010-06-03 15:00:265
 * @Author   :  ht
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.util;

import java.sql.Connection;
import java.util.UUID;

/**
 * PUB模块，获取ORACLE序列的Dao操作类
 * 
 */
public class OracleSequence {
	/**
	 * 获取下一个序号
	 * 
	 * @param con
	 * @return
	 * @throws Exception
	 * @date 2011-03-25
	 * @author gqg 
	 * 
	 */
	public static String getNextSeq(Connection con,String seqName) throws Exception {
		return getNextSeq(con,seqName,"");
	}

	/**
	 * 获取下一个序号
	 * 
	 * @param con
	 * @return
	 * @throws Exception
	 * @date 2011-03-25
	 * @author gqg 
	 * 
	 */
	public static String getNextSeq(Connection con,String seqName, String userName) throws Exception {
//	    String seqId="";
//        StringBuffer sql = new StringBuffer();
//        sql.append("select to_char(");
//        if (!"".equals(userName)) {
//            sql.append(userName + ".");
//        }
//        
//        sql.append(seqName+".Nextval) AS SEQ_ID from dual");
//        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
//        query.setCommand(sql.toString());
//        DynaBeanMap dynaBeanMap = query.getDynaBeanMap(null, con);
//        if(dynaBeanMap!=null){
//            seqId=dynaBeanMap.get("SEQ_ID").toString();
//        }
//        return seqId;
        
		return UUID.randomUUID().toString();
	}
}
