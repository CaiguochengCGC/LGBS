/**
 * 
 *
 * @File name:  PmcPpKpimonthDao.java 
 * @Create on:  2014-04-03 10:15:844
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
 * 【KPI月报表:PMC_PP_KPIMONTH】的Dao操作类
 * 
 */
public class PmcPpKpimonthDao extends DAO {
    /**
     * 查询所有【KPI月报表:PMC_PP_KPIMONTH】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-04-03
     * @author taofl
     * 
     */
    public static List queryPmcPpKpimonth(Connection con, String ppdate,String banci,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select YYYY_MM, PRODUCTIONLINE, PRODUCTIONLINENAME,MTTR, MTBF, EQMRATE, EQPSTOP, cast(STOPTIME/60.0 as decimal(18,2)) as STOPTIME"
        		+ ", STOPCOUNT,SHIFT,(PRODUCTIONLINENAME+'['+cast(SHIFT as varchar)+']') as FORMAT_PRODUCTLINE  from PMC_PP_KPIMONTH ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == ppdate ? null : "convert(varchar(7),YYYY_MM,23)",CQExp.EQ, ppdate);
        cqExp.filed("".equals(banci)? null : "SHIFT", CQExp.EQ, banci);
        cqExp.orderByDesc("YYYY_MM, PRODUCTIONLINENAME");

        logger.debug(" PMC_PP_KPIMONTH查询所有【KPI月报表:PMC_PP_KPIMONTH】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_KPIMONTH", con);
    }
    public static List queryPmcPpKpimonthAll(Connection con, String ppdate,String banci,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT\n" +
                "\tYYYY_MM,\n" +
                "\tPRODUCTIONLINE,\n" +
                "\tPRODUCTIONLINENAME,\n" +
                "\tSUM( MTTR ) AS MTTR,\n" +
                "\tSUM( MTBF ) AS MTBF,\n" +
                "\tSUM( cast(EQMRATE  as decimal(18,2))) / 2 AS EQMRATE,\n" +
                "\tSUM( cast(EQPSTOP as decimal(18,2))) / 2 AS EQPSTOP,\n" +
                "\tSUM( cast( STOPTIME / 60.0 AS DECIMAL ( 18, 2 ) ) ) AS STOPTIME,\n" +
                "\t'全部' AS SHIFT,\n" +
                "\tSUM( STOPCOUNT ),\n" +
                "\tPRODUCTIONLINENAME AS FORMAT_PRODUCTLINE \n" +
                "FROM\n" +
                "PMC_PP_KPIMONTH  ");
        cqExp.where();
cqExp.select(whereSQL);
        cqExp.filed(null == ppdate ? null : "convert(varchar(7),YYYY_MM,23)",CQExp.EQ, ppdate);
        cqExp.select(" GROUP BY YYYY_MM,PRODUCTIONLINE,PRODUCTIONLINENAME,PRODUCTIONLINENAME ");
        cqExp.orderByDesc("YYYY_MM,PRODUCTIONLINENAME");

        logger.debug(" PMC_PP_KPIMONTH查询所有【KPI月报表:PMC_PP_KPIMONTH】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_KPIMONTH", con);
    }

    /**
     * 导出月报表
     *
     * @param con
     * @return
     * @throws Exception
     * @date 2014-04-03
     * @author taofl
     *
     */
    public static List expPmcPpKpimonth(Connection con, Date ppdate,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select YYYY_MM, PRODUCTIONLINE, PRODUCTIONLINENAME,MTTR, MTBF, EQMRATE, EQPSTOP, cast(STOPTIME/60.0 as decimal(18,2)) as STOPTIME"
                + ", STOPCOUNT,SHIFT,(PRODUCTIONLINENAME+'['+cast(SHIFT as varchar)+']') as FORMAT_PRODUCTLINE  from PMC_PP_KPIMONTH ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(varchar(7),YYYY_MM,23)",CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.filed("".equals(banci)? null : "SHIFT", CQExp.EQ, banci);
        cqExp.orderByDesc("YYYY_MM,CONVERT(int,OPERUSER),PRODUCTIONLINENAME");

        logger.debug(" PMC_PP_KPIMONTH查询所有【KPI月报表:PMC_PP_KPIMONTH】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_KPIMONTH", con);
    }

    public static List queryPmcPpKpiMonthPic(Connection con, Date ppdate,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select YYYY_MM, PRODUCTIONLINE,PRODUCTIONLINENAME, MTTR, MTBF, replace(EQMRATE,char(37),'') as EQMRATE, replace(EQPSTOP,char(37),'') as EQPSTOP, (isnull(NULLIF(STOPTIME,''),0)/60) as STOPTIME, STOPCOUNT  from PMC_PP_KPIMONTH ");
        cqExp.where();
        
        cqExp.filed(null == ppdate ? null : "convert(char(7),YYYY_MM,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.filed("".equals(banci)? null : "SHIFT", CQExp.EQ, banci);
        cqExp.orderByAsc("PRODUCTIONLINENAME");

        logger.debug(" PMC_PP_KPIDAY查询所有【KPI月报表:PMC_PP_KPIDAY】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_KPIMONTH", con);
    }
    
    public static int countPmcPpKpimonth(Connection con, Date ppdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM,YYYY_MM, PRODUCTIONLINE,PRODUCTIONLINENAME, MTTR, MTBF, substring(EQMRATE,0,LEN(EQMRATE)) as EQMRATE, substring(EQPSTOP,0,LEN(EQPSTOP)) as EQPSTOP, STOPTIME, STOPCOUNT  from PMC_PP_KPIMONTH ");
        cqExp.where();

        cqExp.filed(ppdate == null ? null : "convert(char(7),YYYY_MM,23)", "=", DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.orderByDesc("YYYY_MM,PRODUCTIONLINENAME");
        cqExp.group("YYYY_MM,PRODUCTIONLINE,PRODUCTIONLINENAME, MTTR, MTBF,EQMRATE,EQPSTOP, STOPTIME, STOPCOUNT");

        logger.debug(" PMC_PP_KPIMONTH查询所有【KPI月报表:PMC_PP_KPIMONTH】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_PP_KPIMONTH", con).get("TOTAL_NUM")));
    }
}
