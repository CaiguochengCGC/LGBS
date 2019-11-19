/**
 * 
 *
 * @File name:  PmcDateImportDao.java 
 * @Create on:  2014-03-16 13:41:557
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pmc.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.utils.DateUtils;

/**
 * 【开动率年报表:PMC_PP_START_RATE_Y】的Dao操作类
 * 
 */
public class PmcPpStartRateYDao extends DAO {
    /**
     * 查询所有【计划日期数量导入:PMC_DATE_IMPORT】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-16
     * @author taofl
     * 
     */
    public static List queryPmcPpStartRateY(Connection con, Date yyyy,String banci,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select *,cast(YYSTOPTIME/60.0 as decimal(18,2)) as YYSTOPTIMEE,SHIFT as BANCI,(PRODUCTIONLINENAME+'['+cast(SHIFT as varchar)+']') AS FORMAT_PRODUCTLINE");
        cqExp.select(" from PMC_PP_START_RATE_Y ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == yyyy ? null : "convert(char(4),YYYY,23)", CQExp.EQ, DateUtils.format(yyyy, "yyyy"));
        cqExp.filed("".equals(banci)? null : "SHIFT", CQExp.EQ, banci);
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 开动率年报表PMC_PP_START_RATE_Y:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_START_RATE_Y", con);
    }
    
    //导出图片查询
    public static List queryPicPmcPpStartRateY(Connection con, Date yyyy,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select * ,replace(YYRATE,char(37),'') YYRATE,SHIFT AS BANCI");
        cqExp.select(" from PMC_PP_START_RATE_Y ");
        cqExp.where();
        cqExp.filed(null == yyyy ? null : "convert(char(4),YYYY,23)", CQExp.EQ, DateUtils.format(yyyy, "yyyy"));
        cqExp.filed("".equals(banci)? null : "SHIFT", CQExp.EQ, banci);
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 开动率年报表PMC_PP_START_RATE_Y:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_START_RATE_Y", con);

    }

}
