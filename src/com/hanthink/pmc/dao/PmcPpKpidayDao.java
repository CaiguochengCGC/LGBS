/**
 * 
 *
 * @File name:  PmcPpKpidayDao.java 
 * @Create on:  2014-04-02 09:36:616
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

import com.hanthink.util.Tools;

/**
 * 【KPI日报表:PMC_PP_KPIDAY】的Dao操作类
 * 
 */
public class PmcPpKpidayDao extends DAO {
    /**
     * 查询所有【KPI日报表:PMC_PP_KPIDAY】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-04-02
     * @author taofl
     * 
     */
    public static List queryPmcPpKpiday(Connection con, Date ppdate,String banci,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PPDATE, PRODUCTIONLINE,PRODUCTIONLINENAME, MTTR, MTBF, EQMRATE"
        		+ ", EQPSTOP, cast(STOPTIME/60.0 as decimal(18,2)) as STOPTIME,SHIFT,STOPCOUNT"
        		+ ",(PRODUCTIONLINENAME+'['+cast(SHIFT as varchar)+']') AS FORMAT_PRODUCTLINE   from PMC_PP_KPIDAY ");
        
        logger.debug(ppdate);
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == ppdate ? null : "PPDATE", CQExp.GREATER_EQ, Tools.getTimestamp(ppdate));
        cqExp.filed(null == ppdate ? null : "PPDATE", CQExp.LESS, Tools.getNextDayTimestamp(ppdate));
        cqExp.filed("".equals(banci)? null : "SHIFT", CQExp.EQ, banci);
        cqExp.orderByDesc(" PRODUCTIONLINENAME,PPDATE");

        logger.debug(" PMC_PP_KPIDAY查询所有【KPI日报表:PMC_PP_KPIDAY】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_KPIDAY", con);
    }
    public static List queryPmcPpKpidayAll(Connection con, Date ppdate,String banci,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT\n" +
                "\tPPDATE,\n" +
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
                "FROM\n" +
                "\tPMC_PP_KPIDAY  ");

        logger.debug(ppdate);
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == ppdate ? null : "PPDATE", CQExp.GREATER_EQ, Tools.getTimestamp(ppdate));
        cqExp.filed(null == ppdate ? null : "PPDATE", CQExp.LESS, Tools.getNextDayTimestamp(ppdate));
        cqExp.select(" GROUP BY PPDATE,PRODUCTIONLINE,PRODUCTIONLINENAME,PRODUCTIONLINENAME ");
        cqExp.orderByDesc(" PRODUCTIONLINENAME,PPDATE");

        logger.debug(" PMC_PP_KPIDAY查询所有【KPI日报表:PMC_PP_KPIDAY】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_KPIDAY", con);
    }
    public static List queryPmcPpKpidayPic(Connection con, Date ppdate,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PPDATE, PRODUCTIONLINE,PRODUCTIONLINENAME, MTTR, MTBF, replace(EQMRATE,char(37),'') as EQMRATE, replace(EQPSTOP,char(37),'') as EQPSTOP, (isnull(NULLIF(STOPTIME,''),0)/60) as STOPTIME,SHIFT,STOPCOUNT  from PMC_PP_KPIDAY ");
        cqExp.where();
        cqExp.filed(null == ppdate ? null : "PPDATE", CQExp.GREATER_EQ, Tools.getTimestamp(ppdate));
        cqExp.filed(null == ppdate ? null : "PPDATE", CQExp.LESS, Tools.getNextDayTimestamp(ppdate));
        cqExp.filed("".equals(banci)? null : "SHIFT", CQExp.EQ, banci);
        cqExp.orderByDesc("PRODUCTIONLINENAME,PPDATE");

        logger.info(" PMC_PP_KPIDAY查询所有【KPI日报表:PMC_PP_KPIDAY】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_KPIDAY", con);
    }

}
