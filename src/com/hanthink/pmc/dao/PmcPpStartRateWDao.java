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

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.utils.DateUtils;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * 【开动率年报表:PMC_PP_START_RATE_Y】的Dao操作类
 * 
 */
public class PmcPpStartRateWDao extends DAO {
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
    public static List queryPmcPpStartRateW(Connection con, Date yyyy,String banci,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select * ,YEAEMONTH YYYY_MM,cast(MMSTOPTIME/60.0 as decimal(18,2)) as MMSTOPTIMEE,(PRODUCTIONLINENAME+'['+cast(BANCI as varchar)+']') AS FORMAT_PRODUCTLINE");
        cqExp.select(" from PMC_PP_START_RATE_W ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.filed(null == yyyy ? null : "convert(char(4),YEAEMONTH,23)", CQExp.EQ, DateUtils.format(yyyy, "yyyy"));
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 开动率周报表PMC_PP_START_RATE_W:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_START_RATE_W", con);
    }
    
    //导出图片查询
    public static List queryPicPmcPpStartRateW(Connection con,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select *, replace(MMRATE,char(37),'') MMRATE,YEAEMONTH YYYY_MM");
        cqExp.select(" from PMC_PP_START_RATE_W ");
        cqExp.where();
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 开动率周报表PMC_PP_START_RATE_W:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_START_RATE_W", con);

    }

}
