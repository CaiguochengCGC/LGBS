/**
 * 
 *
 * @File name:  PmcDatePpDao.java 
 * @Create on:  2014-03-16 17:37:755
 * @Author   :  luoshw
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.zzcj.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;

import com.hanthink.util.Tools;

/**
 * 【生产时间数据表:ZZCJ_DATE_PP】的Dao操作类
 * 
 */
public class PmcDatePpDao extends DAO {
    /**
     * 查询【生产时间数据表:ZZCJ_DATE_PP】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-16
     * @author luoshw
     * 
     */
    public static List queryPmcDatePp(Connection con, java.util.Date ppDate) throws Exception {
        CQExp cqexp = CQExp.instance();

        cqexp.select("select A.*,  convert(decimal(5,2),datediff(second,convert(datetime,A.STARTTIME1,20),convert(datetime,A.ENDTIME1,20))/3600.0) WORKTIME1 ");
        cqexp.select(",  convert(decimal(5,2),datediff(second,convert(datetime,A.STARTTIME2,20),convert(datetime,A.ENDTIME2,20))/3600.0) WORKTIME2 , convert(decimal(5,2),datediff(second,convert(datetime,A.STARTTIME3,20),convert(datetime,A.ENDTIME3,20))/3600.0) WORKTIME3 ");
        cqexp.select(" from ZZCJ_DATE_PP A ");

        cqexp.where();
        cqexp.filed(null == ppDate ? null : "A.WORKDATE", CQExp.EQ, ppDate);
        cqexp.orderByAsc("A.PRODUCTIONLINENAME");
        logger.info("总装车间查询【生产时间数据表:ZZCJ_DATE_PP】信息" + cqexp.getSql());

        List list = cqexp.getDynaBeanMapList("ZZCJ_DATE_PP", con);

        return list;
    }
    
    public static int countPmcDatePp(Connection con, java.util.Date ppDate) throws Exception {
        CQExp cqexp = CQExp.instance();

        cqexp.select("select A.*,count(*) as TOTAL_NUM, convert(decimal(5,2),datediff(second,convert(datetime,A.STARTTIME1,20),convert(datetime,A.ENDTIME1,20))/3600.0) WORKTIME1 ");
        cqexp.select(",  convert(decimal(5,2),datediff(second,convert(datetime,A.STARTTIME2,20),convert(datetime,A.ENDTIME2,20))/3600.0) WORKTIME2 , convert(decimal(5,2),datediff(second,convert(datetime,A.STARTTIME3,20),convert(datetime,A.ENDTIME3,20))/3600.0) WORKTIME3 ");
        cqexp.select(" from ZZCJ_DATE_PP A ");

        cqexp.where();
        cqexp.filed(null == ppDate ? null : "A.WORKDATE", CQExp.EQ, ppDate);
        cqexp.group("A.id,A.FPRODUCTIONLINE,A.PRODUCTIONLINE,A.PRODUCTIONLINENAME,A.WORKDATE,A.SHIFT1,A.SHIFT2,A.SHIFT3,A.REMARK,A.STARTTIME1, A.ENDTIME1, A.STARTTIME2, A.ENDTIME2, A.STARTTIME3, A.ENDTIME3");

        logger.info("总装车间统计【生产时间数据表:ZZCJ_DATE_PP】信息：" + cqexp.getSql());
        return Integer.parseInt(String.valueOf(cqexp.getDynaBeanMap("ZZCJ_DATE_PP", con).get("TOTAL_NUM")));
    }
    

    /**
     * 根据时间段、工段查询【生产时间数据表:ZZCJ_DATE_PP】信息
     * 
     * @author luoshw
     * @param con
     * @param ppDate
     * @return
     * @throws Exception
     */
    /*public static List queryPmcDatePpPeriod(Connection con, java.util.Date startDate, java.util.Date endDate, java.lang.String productionLine) throws Exception {
        CQExp cqexp = CQExp.instance();

        cqexp.select("select A.*, convert(decimal(5,2),datediff(second,convert(datetime,A.STARTTIME1,20),convert(datetime,A.ENDTIME1,20))/3600.0) WORKTIME1 ");
        cqexp.select(", convert(decimal(5,2),datediff(second,convert(datetime,A.STARTTIME2,20),convert(datetime,A.ENDTIME2,20))/3600.0) WORKTIME2 ,convert(decimal(5,2),datediff(second,convert(datetime,A.STARTTIME3,20),convert(datetime,A.ENDTIME3,20))/3600.0) WORKTIME3 ");
        cqexp.select(" from ZZCJ_DATE_PP A ");

        cqexp.where();
        cqexp.filed(null == startDate ? null : "convert(datetime,A.WORKDATE,20)", CQExp.GREATER_EQ, Tools.getTimestamp(startDate));
        cqexp.filed(null == endDate ? null : "convert(datetime,A.WORKDATE,20)", CQExp.LESS, Tools.getNextDayTimestamp(endDate));
        cqexp.filed("".equals(productionLine) ? null : "LTrim(A.PRODUCTIONLINENAME)", CQExp.EQ, productionLine);
        cqexp.orderByAsc("PRODUCTIONLINENAME");
        logger.info("根据时间段、工段查询【生产时间数据表:ZZCJ_DATE_PP】信息：" + cqexp.getSql());

        List list = cqexp.getDynaBeanMapList("ZZCJ_DATE_PP", con);

        return list;
    }*/
    
    public static List queryPmcDatePpPeriodAgen(Connection con, java.util.Date startDate, java.util.Date endDate, java.lang.String productionLine) throws Exception {
        CQExp cqexp = CQExp.instance();

        cqexp.select("select PPDATE,PRODUCTLINE,PRODUCTLINENAME,PRODUCTTYPE,ONEWORKTIME,TWOWORKTIME,THREEWORKTIME from ZZCJ_PP_PRODUCT_TIME");

        cqexp.where();
        cqexp.filed(null == startDate ? null : "PPDATE", CQExp.GREATER_EQ, Tools.getTimestamp(startDate));
        cqexp.filed(null == endDate ? null : "PPDATE", CQExp.LESS, Tools.getNextDayTimestamp(endDate));
        cqexp.filed("".equals(productionLine) ? null : "LTrim(PRODUCTLINENAME)", CQExp.EQ, productionLine);
        cqexp.orderByAsc("PPDATE,convert(int,UDA_1)");
        logger.info("总装车间根据时间段、工段查询【生产时间数据表:ZZCJ_DATE_PP】信息：" + cqexp.getSql());

        List list = cqexp.getDynaBeanMapList("ZZCJ_DATE_PP", con);

        return list;
    }

    public static int countPmcDatePpPeriod(Connection con, Date startDate, Date endDate, String productionLine) throws Exception {
        CQExp cqexp = CQExp.instance();

        cqexp.select("select count(*) as TOTAL_NUM, A.*, convert(decimal(5,3),datediff(second,convert(datetime,A.STARTTIME1,20),convert(datetime,A.ENDTIME1,20))/3600.0) WORKTIME1 ");
        cqexp.select(", convert(decimal(5,2),datediff(second,convert(datetime,A.STARTTIME2,20),convert(datetime,A.ENDTIME2,20))/3600.0) WORKTIME2 ,convert(decimal(5,2),datediff(second,convert(datetime,A.STARTTIME3,20),convert(datetime,A.ENDTIME3,20))/3600.0) WORKTIME3 ");
        cqexp.select(" from ZZCJ_DATE_PP A ");

        cqexp.where();
        cqexp.filed(startDate == null ? null : "convert(date,A.WORKDATE,23)", ">=", Tools.getTimestamp(startDate));
        cqexp.filed(endDate == null ? null : "convert(date,A.WORKDATE,23)", "<=", Tools.getNextDayTimestamp(endDate));
        cqexp.filed("".equals(productionLine) ? null : "LTrim(A.PRODUCTIONLINENAME)", "=", productionLine);
        cqexp.group("A.id,A.FPRODUCTIONLINE,A.PRODUCTIONLINE,A.PRODUCTIONLINENAME,A.WORKDATE,A.SHIFT1,A.SHIFT2,A.SHIFT3,A.REMARK,A.STARTTIME1, A.ENDTIME1, A.STARTTIME2, A.ENDTIME2, A.STARTTIME3, A.ENDTIME3");
        cqexp.orderByAsc("PRODUCTIONLINENAME");
        logger.info("总装车间统计【生产时间数据表:ZZCJ_DATE_PP】信息：" + cqexp.getSql());
        return Integer.parseInt(String.valueOf(cqexp.getDynaBeanMap("ZZCJ_DATE_PP", con).get("TOTAL_NUM")));
    }

}
