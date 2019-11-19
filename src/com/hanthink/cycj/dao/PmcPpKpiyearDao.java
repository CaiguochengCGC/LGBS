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

package com.hanthink.cycj.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.hanthink.util.Tools;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.utils.DateUtils;

/**
 * 【KPI年报表:CYCJ_PP_KPIYEAR】的Dao操作类
 * 
 */
public class PmcPpKpiyearDao extends DAO {
    /**
     * 查询所有【KPI年报表:CYCJ_PP_KPIYEAR】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-04-03
     * @author taofl
     * 
     */
    public static List queryPmcPpKpiyear(Connection con, Date ppdate,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select YYYY, PRODUCTIONLINE,PRODUCTIONLINENAME, AVG(MTTR) AS MTTR, AVG(MTBF) AS MTBF, AVG(EQMRATE) AS EQMRATE, AVG(EQPSTOP) AS EQPSTOP, cast(SUM(STOPTIME)/60.0 as decimal(18,2)) as STOPTIME, SUM(STOPCOUNT) AS STOPCOUNT,AVG(BANCI) AS BANCI from CYCJ_PP_KPIYEAR ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(char(7),YYYY,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.group(" PRODUCTIONLINE,PRODUCTIONLINENAME,YYYY,seq");
        cqExp.orderByAsc("seq");
        logger.debug("冲压车间CYCJ_PP_KPIYEAR查询所有【KPI年报表:CYCJ_PP_KPIYEAR】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CYCJ_PP_KPIYEAR", con);
    }

    public static List queryPmcPpKpiYearPic(Connection con, Date ppdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select YYYY, PRODUCTIONLINE,PRODUCTIONLINENAME, MTTR, MTBF, replace(EQMRATE,char(37),'') as EQMRATE, replace(EQPSTOP,char(37),'') as EQPSTOP, isnull(NULLIF(STOPTIME,''),0) as STOPTIME, STOPCOUNT  from CYCJ_PP_KPIYEAR ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(char(7),YYYY,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.orderByAsc("PRODUCTIONLINENAME");

        logger.debug("冲压车间CYCJ_PP_KPIYEAR查询所有【KPI年报表:CYCJ_PP_KPIYEAR】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CYCJ_PP_KPIYEAR", con);
    }
    
    public static int countPmcPpKpiyear(Connection con, Date ppdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM  from CYCJ_PP_KPIYEAR ");
        cqExp.where();

        cqExp.filed(ppdate == null ? null : "convert(char(7),YYYY,23)", "=", DateUtils.format(ppdate, "yyyy"));
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        cqExp.group(" YYYY,PRODUCTIONLINE,PRODUCTIONLINENAME,MTTR, MTBF,EQMRATE,EQPSTOP, STOPTIME, STOPCOUNT");

        logger.debug("冲压车间CYCJ_PP_KPIYEAR统计【KPI年报表:CYCJ_PP_KPIYEAR】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("CYCJ_PP_KPIYEAR", con).get("TOTAL_NUM")));
    }
}
