/**
 * 
 *
 * @File name:  PmcPpStationDao.java 
 * @Create on:  2014-03-20 09:31:113
 * @Author   :  luoshw
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.zzcj.dao;

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;

/**
 * 【生产工位表:ZZCJ_PP_STATION】的Dao操作类
 * 
 */
public class PmcPpStationDao extends DAO {
	
	public static List queryPmcPpStationByMaintain(Connection con, java.lang.String productionName) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select DISTINCT PRODUCTIONLINE,PRODUCTIONLINENAME,REMARK,PSEQ ");
        cqExp.select(" from ZZCJ_PP_STATION");

        cqExp.where();
        cqExp.filed("".equals(productionName) ? null : "PRODUCTIONLINENAME", cqExp.EQ, productionName);
        cqExp.orderByAsc("PSEQ");
        logger.debug(" 总装车间查询【生产工位表:ZZCJ_PP_STATION】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_STATION", con);
    }
	
    /**
     * 查询【生产工位表:ZZCJ_PP_STATION】,作为combo工段数据源
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-20
     * @author luoshw
     * 
     */
    public static List queryPmcPpStation(Connection con, java.lang.String codeType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct DATA1 as VALUE, DATA1 as TEXT ");
        cqExp.select(" from ZZCJ_tabRefer");

        cqExp.where();

        logger.debug(" 总装车间查询【生产工位表:ZZCJ_PP_STATION】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_STATION", con);
    }
    
    /**
     * 查询【生产工位表:ZZCJ_PP_ATTR】,作为combo工段数据源
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-20
     * @author luoshw
     * 
     */
    public static List queryPmcPpAttr(Connection con, java.lang.String codeType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct DATA3 as VALUE, DATA3 as TEXT ");
        cqExp.select(" from ZZCJ_tabRefer");
        cqExp.where();
        logger.debug(" 总装车间查询【生产工位表:ZZCJ_PP_STATION】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_STATION", con);
    }

    
    
    
    
    public static List queryPmcPpStationByProductName(Connection con, java.lang.String codeType, java.lang.String queryType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct " + codeType + " as VALUE, " + codeType + " as TEXT ");
        cqExp.select(" from ZZCJ_PP_STATION");

        cqExp.where();
        cqExp.filed("".equals(queryType) ? null : "PRODUCTIONLINENAME", cqExp.EQ, queryType);
        logger.debug(" 总装车间查询【生产工位表:ZZCJ_PP_STATION】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_STATION", con);
    }
    
    public static List queryPmcPpStationAll(Connection con, java.lang.String codeType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct " + codeType + " as VALUE, " + codeType + " as TEXT ");
        cqExp.select(" from ZZCJ_tabStopSta");

        cqExp.where("EventData7 != ''");
        cqExp.orderByAsc("EventData7");
        logger.debug("总装车间查询【生产工位表:ZZCJ_tabStopSta】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_STATION", con);
    }
    
    /**
     * 根据工段查询工位
     * @param con
     * @param codeType
     * @return
     * @throws Exception
     */
    public static List queryPmcPpStationByS(Connection con, java.lang.String codeType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct EVENTDATA4 AS VALUE,EVENTDATA4 AS TEXT ");
        cqExp.select(" from ZZCJ_tabStopSta");

        cqExp.where();
        cqExp.filed("".equals(codeType)?null:"eventdata7", cqExp.EQ, codeType);
        
        logger.debug("总装车间查询【生产工位表:ZZCJ_tabStopSta】站点信息,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_STATION", con);
    }
    
    /**
     * 根据工位查询设备
     * @param con
     * @param codeType
     * @return
     * @throws Exception
     */
    public static List queryPmcPpStationByEquiment(Connection con, java.lang.String codeType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct eventData5 as VALUE,eventData5 as TEXT ");
        cqExp.select(" from ZZCJ_tabStopSta");

        cqExp.where();
        cqExp.filed("".equals(codeType)?null:"eventData4", cqExp.EQ, codeType);
        
        logger.debug("总装车间查询【ZZCJ_tabStopSta】设备信息,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_STATION", con);
    }
    /**
     * 查询【生产工位表:ZZCJ_PP_STATION】,作为combo工位数据源
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-20
     * @author luoshw
     * 
     */
    public static List queryPmcPpStationCt(Connection con, java.lang.String codeType, java.lang.String quryType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct " + codeType + " as VALUE, " + codeType + " as TEXT");
        cqExp.select(" from ZZCJ_PP_STATION");

        cqExp.where();

        cqExp.filed("".equals(quryType) ? null : "PRODUCTIONLINENAME", CQExp.EQ, quryType);
        logger.debug(" 总装车间查询【生产工位表:ZZCJ_PP_STATION】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_STATION", con);
    }
    
    /**
     * 查询CT工位
     * @param con
     * @param codeType
     * @param quryType
     * @return
     * @throws Exception
     */
    public static List queryPmcPpStationCtAll(Connection con, java.lang.String codeType, java.lang.String quryType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct " + codeType + " as VALUE, " + codeType + " as TEXT");
        cqExp.select(" from ZZCJ_tabCycleTime");

        cqExp.where();
        cqExp.andStr(" EventData1 != ''");
        cqExp.andStr(" EventData1 != ''");
        cqExp.andStr(" EventData1 != '123'");

        cqExp.filed("".equals(quryType) ? null : "EventData16", CQExp.EQ, quryType);
        cqExp.orderByAsc(codeType);
        logger.debug(" 总装车间查询【生产工位表:ZZCJ_tabCycleTime】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_STATION", con);
    }
    
    /**
     * 查询tabStopSta工位
     * @param con
     * @param codeType
     * @param quryType
     * @return
     * @throws Exception
     */
    public static List queryTabStopStaAll(Connection con, java.lang.String codeType, java.lang.String quryType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct DATA4 as VALUE, DATA4 as TEXT");
        cqExp.select(" from ZZCJ_tabRefer");

        cqExp.where();

        cqExp.filed("".equals(quryType) ? null : "EventData7", CQExp.EQ, quryType);
        cqExp.orderByAsc("EventData4");
        logger.debug("总装车间查询【生产工位表:ZZCJ_tabStopSta】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_STATION", con);
    }
    
    /**
     * 查询TabRefer工位
     * @param con
     * @param codeType
     * @param quryType
     * @return
     * @throws Exception
     */
    public static List queryTabReferAll(Connection con, java.lang.String codeType, java.lang.String quryType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct " + codeType + " as VALUE, " + codeType + " as TEXT");
        cqExp.select(" from ZZCJ_tabRefer");

        cqExp.where();

        cqExp.filed("".equals(quryType) ? null : "DATA4", CQExp.EQ, quryType);
        cqExp.orderByAsc(codeType);
        logger.debug("总装车间查询【生产工位表:ZZCJ_tabRefer】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_STATION", con);
    }
    
    public static List queryTabStopLineStationAll(Connection con, java.lang.String codeType, java.lang.String quryType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct EventData40 as VALUE, EventData40 as TEXT");
        cqExp.select(" from ZZCJ_tabStopline");

        cqExp.where();

       // cqExp.filed("".equals(quryType) ? null : "DATA4", CQExp.EQ, quryType);
       // cqExp.orderByAsc(codeType);
        logger.debug("总装车间查询【生产工位表:ZZCJ_tabStopline】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_STATION", con);
    }

}
