/**
 * 
 *
 * @File name:  PmcPpKpiyearDao.java 
 * @Create on:  2014-04-03 10:15:759
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

import com.hanthink.util.Tools;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.utils.DateUtils;

/**
 * 【KPI年报表:】的Dao操作类
 * 
 */
public class PmcPpKpiyearDao extends DAO {
    /**
     * 查询所有【KPI年报表:】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-04-03
     * @author taofl
     * 
     */
    public static List queryPmcPpKpiyear(Connection con, Date ppdate,String banci,String wherSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select YYYY, PRODUCTIONLINE,PRODUCTIONLINENAME, MTTR, MTBF, EQMRATE, EQPSTOP, cast(STOPTIME/60.0 as decimal(18,2)) as STOPTIME"
        		+ ", STOPCOUNT,SHIFT,(PRODUCTIONLINENAME+'['+cast(SHIFT as varchar)+']') as FORMAT_PRODUCTLINE   from PMC_PP_KPIYEAR ");
        cqExp.where();
        cqExp.select(wherSQL);
        cqExp.filed(null == ppdate ? null : "convert(char(7),YYYY,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.filed("".equals(banci)? null : "SHIFT", CQExp.EQ, banci);
        cqExp.orderByAsc(" PRODUCTIONLINENAME");

        logger.debug(" 查询所有【KPI年报表:】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_KPIYEAR", con);
    }
    public static List queryPmcPpKpiyearAll(Connection con, Date ppdate,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT\n" +
                "\tYYYY,\n" +
                "\tPRODUCTIONLINE,\n" +
                "\tPRODUCTIONLINENAME,\n" +
                "\tSUM(MTTR) AS MTTR,\n" +
                "\tSUM(MTBF) AS MTBF,\n" +
                "\tSUM(EQMRATE)/2 AS EQMRATE,\n" +
                "\tSUM(EQPSTOP)/2 AS EQPSTOP,\n" +
                "\tSUM(cast( STOPTIME / 60.0 AS DECIMAL ( 18, 2 ) )) AS STOPTIME,\n" +
                "\t'全部' AS SHIFT,\n" +
                "\tSUM(STOPCOUNT),\n" +
                "\t  PRODUCTIONLINENAME  AS FORMAT_PRODUCTLINE \n" +
                "FROM PMC_PP_KPIYEAR ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(char(7),YYYY,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.select(" GROUP BY YYYY,PRODUCTIONLINE,PRODUCTIONLINENAME,PRODUCTIONLINENAME ");
        cqExp.orderByAsc(" PRODUCTIONLINENAME");

        logger.debug(" 查询所有【KPI年报表:】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_KPIYEAR", con);
    }

    public static List queryPmcPpKpiYearPic(Connection con, Date ppdate,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select YYYY, PRODUCTIONLINE,PRODUCTIONLINENAME, MTTR, MTBF, replace(EQMRATE,char(37),'') as EQMRATE, replace(EQPSTOP,char(37),'') as EQPSTOP, isnull(NULLIF(STOPTIME,''),0) as STOPTIME, STOPCOUNT  from  ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(char(7),YYYY,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.orderByAsc("PRODUCTIONLINENAME");

        logger.debug(" 查询所有【KPI年报表:】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("", con);
    }
    
    public static int countPmcPpKpiyear(Connection con, Date ppdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM,YYYY, PRODUCTIONLINE, PRODUCTIONLINENAME,MTTR, MTBF, EQMRATE,  EQPSTOP, STOPTIME, STOPCOUNT  from  ");
        cqExp.where();

        cqExp.filed(ppdate == null ? null : "convert(char(7),YYYY,23)", "=", DateUtils.format(ppdate, "yyyy"));
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        cqExp.group(" YYYY,PRODUCTIONLINE,PRODUCTIONLINENAME,MTTR, MTBF,EQMRATE,EQPSTOP, STOPTIME, STOPCOUNT");

        logger.debug(" 统计【KPI年报表:】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("", con).get("TOTAL_NUM")));
    }
}
