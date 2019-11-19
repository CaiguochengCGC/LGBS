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

package com.hanthink.cycj.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;

import com.hanthink.util.Tools;

/**
 * 【KPI日报表:CYCJ_PP_KPIDAY】的Dao操作类
 * 
 */
public class PmcPpKpidayDao extends DAO {
    /**
     * 查询所有【KPI日报表:CYCJ_PP_KPIDAY】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-04-02
     * @author taofl
     * 
     */
    public static List queryPmcPpKpiday(Connection con, Date ppdate,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PPDATE, PRODUCTIONLINE,PRODUCTIONLINENAME, AVG(MTTR) AS MTTR,AVG(MTBF) AS MTBF,AVG(EQMRATE) AS EQMRATE,AVG(EQPSTOP) AS EQPSTOP, cast(SUM(STOPTIME)/60.0 as decimal(18,2)) as STOPTIME,SUM(STOPCOUNT) AS STOPCOUNT,AVG(BANCI) AS BANCI  from CYCJ_PP_KPIDAY ");

        cqExp.where();
        cqExp.filed(null == ppdate ? null : "PPDATE", CQExp.GREATER_EQ, Tools.getTimestamp(ppdate));
        cqExp.filed(null == ppdate ? null : "PPDATE", CQExp.LESS, Tools.getNextDayTimestamp(ppdate));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.group(" PRODUCTIONLINE,PRODUCTIONLINENAME,PPDATE,seq");
        cqExp.orderByAsc(" seq");

        logger.debug("冲压车间CYCJ_PP_KPIDAY查询所有【KPI日报表:CYCJ_PP_KPIDAY】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CYCJ_PP_KPIDAY", con);
    }
    
    public static List queryPmcPpKpidayPic(Connection con, Date ppdate,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PPDATE, PRODUCTIONLINE,PRODUCTIONLINENAME, MTTR, MTBF, replace(EQMRATE,char(37),'') as EQMRATE, replace(EQPSTOP,char(37),'') as EQPSTOP, isnull(NULLIF(STOPTIME,''),0) as STOPTIME, STOPCOUNT  from CYCJ_PP_KPIDAY ");

        cqExp.where();
        cqExp.filed(null == ppdate ? null : "PPDATE", CQExp.GREATER_EQ, Tools.getTimestamp(ppdate));
        cqExp.filed(null == ppdate ? null : "PPDATE", CQExp.LESS, Tools.getNextDayTimestamp(ppdate));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.orderByAsc("PRODUCTIONLINENAME");

        logger.debug("冲压车间CYCJ_PP_KPIDAY查询所有【KPI日报表:CYCJ_PP_KPIDAY】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CYCJ_PP_KPIDAY", con);
    }

    public static int countPmcPpKpiday(Connection con, Date ppdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM, STOPTIME, STOPCOUNT  from CYCJ_PP_KPIDAY ");

        logger.debug(ppdate);
        cqExp.where();
        cqExp.filed(ppdate == null ? null : "PPDATE", ">=", Tools.getTimestamp(ppdate));
        cqExp.filed(ppdate == null ? null : "PPDATE", "<", Tools.getNextDayTimestamp(ppdate));

        cqExp.orderByAsc("PRODUCTIONLINENAME");
        cqExp.group(" PPDATE,PRODUCTIONLINE,PRODUCTIONLINENAME,MTTR,MTBF,EQMRATE,EQPSTOP,STOPTIME,STOPCOUNT");

        logger.debug("冲压车间CYCJ_PP_KPIDAY统计【KPI日报表:CYCJ_PP_KPIDAY】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("CYCJ_PP_KPIDAY", con).get("TOTAL_NUM")));
    }
}
