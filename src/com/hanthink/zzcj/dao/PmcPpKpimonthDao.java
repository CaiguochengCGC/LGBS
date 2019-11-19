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

package com.hanthink.zzcj.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.hanthink.util.Tools;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.utils.DateUtils;

/**
 * 【KPI月报表:ZZCJ_PP_KPIMONTH】的Dao操作类
 * 
 */
public class PmcPpKpimonthDao extends DAO {
    /**
     * 查询所有【KPI月报表:ZZCJ_PP_KPIMONTH】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-04-03
     * @author taofl
     * 
     */
    public static List queryPmcPpKpimonth(Connection con, Date ppdate,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select YYYY_MM, PRODUCTIONLINE, PRODUCTIONLINENAME,AVG(MTTR) AS MTTR, AVG(MTBF) AS MTBF, AVG(EQMRATE) AS EQMRATE, AVG(EQPSTOP) AS EQPSTOP, cast(SUM(STOPTIME)/60.0 as decimal(18,2)) as STOPTIME, SUM(STOPCOUNT) AS STOPCOUNT, BANCI from ZZCJ_PP_KPIMONTH ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(char(7),YYYY_MM,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.group(" PRODUCTIONLINE,PRODUCTIONLINENAME,YYYY_MM,seq,BANCI");
       // cqExp.filed(null == ppdate ? null : "convert(char(7),YYYY_MM,23)", CQExp.LESS, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.orderByAsc("seq");
        logger.debug("总装车间ZZCJ_PP_KPIMONTH查询所有【KPI月报表:ZZCJ_PP_KPIMONTH】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_KPIMONTH", con);
    }

    public static List queryPmcPpKpiMonthPic(Connection con, Date ppdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select YYYY_MM, PRODUCTIONLINE,PRODUCTIONLINENAME, MTTR, MTBF, replace(EQMRATE,char(37),'') as EQMRATE, replace(EQPSTOP,char(37),'') as EQPSTOP, isnull(NULLIF(STOPTIME,''),0) as STOPTIME, STOPCOUNT  from ZZCJ_PP_KPIMONTH ");
        cqExp.where();
        
        cqExp.filed(null == ppdate ? null : "convert(char(7),YYYY_MM,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.orderByAsc("PRODUCTIONLINENAME");

        logger.debug("总装车间ZZCJ_PP_KPIDAY查询所有【KPI月报表:ZZCJ_PP_KPIDAY】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_KPIMONTH", con);
    }
    
    public static int countPmcPpKpimonth(Connection con, Date ppdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM  from ZZCJ_PP_KPIMONTH ");
        cqExp.where();

        cqExp.filed(ppdate == null ? null : "convert(char(7),YYYY_MM,23)", "=", DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        cqExp.group("YYYY_MM,PRODUCTIONLINE,PRODUCTIONLINENAME, MTTR, MTBF,EQMRATE,EQPSTOP, STOPTIME, STOPCOUNT");

        logger.debug("总装车间ZZCJ_PP_KPIMONTH查询所有【KPI月报表:ZZCJ_PP_KPIMONTH】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("ZZCJ_PP_KPIMONTH", con).get("TOTAL_NUM")));
    }
}
