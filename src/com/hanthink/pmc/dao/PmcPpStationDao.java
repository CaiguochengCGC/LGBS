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

package com.hanthink.pmc.dao;

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;

/**
 * 【生产工位表:PMC_PP_STATION】的Dao操作类
 * 
 */
public class PmcPpStationDao extends DAO {
	
	public static List queryPmcPpStationByMaintain(Connection con, java.lang.String productionName) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select DISTINCT PRODUCTIONLINE,PRODUCTIONLINENAME,REMARK,PSEQ ");
        cqExp.select(" from PMC_PP_STATION");

        cqExp.where();
        cqExp.filed("".equals(productionName) ? null : "PRODUCTIONLINENAME", cqExp.EQ, productionName);
        cqExp.orderByAsc("PSEQ");
        logger.debug(" 查询【生产工位表:PMC_PP_STATION】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STATION", con);
    }
	
    /**
     * 查询【生产工位表:PMC_PP_STATION】,作为combo工段数据源
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
        //cqExp.select("select distinct " + codeType + " as VALUE, " + codeType + " as TEXT ");
        //cqExp.select(" from PMC_PP_START_RATE_Y");
//      cqExp.select("select distinct DATA4 as VALUE, DATA4 as TEXT ");
//      cqExp.select(" from tabRefer");
        //huxiaotian，直接从PMC_PP_PRODUCT表中获取工段筛选条件
        cqExp.select("select distinct stationName as VALUE,stationName as TEXT");
        cqExp.select(" from CSMsg2 ");
        cqExp.where();

        logger.debug(" 查询【生产工位表:PMC_PP_STATION】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STATION", con);
    }
    public static List queryStopReson(Connection con) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct TagDescription as VALUE,TagDescription as TEXT");
        cqExp.select(" from LINE_STOP_LOG_TagTable WHERE TagDescription  <>'运行' ");


        logger.debug(" 查询【生产工位表:STOP_RESON】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("STOP_RESON", con);
    }
    public static List queryEQUMENT(Connection con, java.lang.String quryType, java.lang.String lineType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct DATA2 as VALUE,DATA2 as TEXT");
        cqExp.select(" from tabRefer  ");
        cqExp.where();
        cqExp.filed("".equals(quryType) ? null : "DATA7", CQExp.EQ, quryType);
        cqExp.filed("".equals(lineType) ? null : "DATA1", CQExp.EQ, lineType);
        cqExp.orderByAsc("DATA2");
        logger.debug(" 查询【停线设备:STOP_EQUMENT】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("STOP_EQUMENT", con);
    }
    public static List queryAlarmEQUMENT(Connection con, java.lang.String quryType, java.lang.String lineType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct ALARM_MACHINE as VALUE,ALARM_MACHINE as TEXT");
        cqExp.select(" from PMC_PP_STOP_RECORD  ");
        cqExp.where();
        cqExp.filed("".equals(quryType) ? null : "STATION", CQExp.EQ, quryType);
        cqExp.filed("".equals(lineType) ? null : "LINECODE", CQExp.EQ, lineType);
        cqExp.orderByAsc("ALARM_MACHINE");
        logger.debug(" 查询【停线设备:PMC_PP_STOP_RECORD】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ALARM_EQUMENT", con);
    }
    public static List queryPmcPpStationByProductName(Connection con, java.lang.String codeType, java.lang.String queryType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct " + codeType + " as VALUE, " + codeType + " as TEXT ");
        cqExp.select(" from PMC_PP_STATION");

        cqExp.where();
        cqExp.filed("".equals(queryType) ? null : "PRODUCTIONLINENAME", cqExp.EQ, queryType);
        logger.debug(" 查询【生产工位表:PMC_PP_STATION】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STATION", con);
    }
    
    public static List queryPmcPpStationAll(Connection con, java.lang.String codeType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct " + codeType + " as VALUE, " + codeType + " as TEXT ");
        cqExp.select(" from tabStopSta");

        cqExp.where("EventData7 != ''");
        cqExp.orderByAsc("EventData7");
        logger.debug(" 查询【生产工位表:tabStopSta】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STATION", con);
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
        cqExp.select(" from tabStopSta");

        cqExp.where();
        cqExp.filed("".equals(codeType)?null:"eventdata7", cqExp.EQ, codeType);
        
        logger.debug(" 查询【生产工位表:tabStopSta】站点信息,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STATION", con);
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
        cqExp.select(" from tabStopSta");

        cqExp.where();
        cqExp.filed("".equals(codeType)?null:"eventData4", cqExp.EQ, codeType);
        
        logger.debug(" 查询【tabStopSta】设备信息,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STATION", con);
    }
    /**
     * 查询【生产工位表:PMC_PP_STATION】,作为combo工位数据源
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
        cqExp.select(" from PMC_PP_STATION");

        cqExp.where();

        cqExp.filed("".equals(quryType) ? null : "PRODUCTIONLINENAME", CQExp.EQ, quryType);
        logger.debug(" 查询【生产工位表:PMC_PP_STATION】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STATION", con);
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
        cqExp.select(" from PMC_CYCLETIME");
        cqExp.where();
        cqExp.filed("".equals(quryType) ? null : "order by", quryType, "asc");
        logger.debug(" 查询【生产工位表:tabCycleTime】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STATION", con);
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
        cqExp.select("select distinct " + codeType + " as VALUE, " + codeType + " as TEXT");
        cqExp.select(" from LGBS.dbo.tabRefer ");

        cqExp.where();

        cqExp.filed("".equals(quryType) ? null : "DATA1", CQExp.EQ, quryType);
        cqExp.orderByAsc("DATA7");
        logger.debug(" 查询【生产工位表:tabRefer】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STOP_RECORD", con);
    }
    /**
     * 查询tabStopSta工位
     * @param con
     * @param codeType
     * @param quryType
     * @return
     * @throws Exception
     */
    public static List queryALARMANDSTOPStaAll(Connection con, java.lang.String codeType, java.lang.String quryType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct " + codeType + " as VALUE, " + codeType + " as TEXT");
        cqExp.select(" from LGBS.dbo.STAT_STOP_LOG_TagTable ");

        cqExp.where();

        cqExp.filed("".equals(quryType) ? null : "english", CQExp.EQ, quryType);
        cqExp.orderByAsc("shebei");
        logger.debug(" 查询【生产工位表:tabStopSta】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STOP_RECORD", con);
    }


    /**
     * 查询ALAM工位
     * @param con
     * @param codeType
     * @param quryType
     * @return
     * @throws Exception
     */
    public static List queryAlmStation(Connection con, java.lang.String codeType, java.lang.String quryType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct " + codeType + " as VALUE, " + codeType + " as TEXT");
        cqExp.select(" from STAT_STOP_LOG_TagTable ");

        cqExp.where();

        cqExp.filed("".equals(quryType) ? null : "english", CQExp.EQ, quryType);
        cqExp.orderByAsc("shebei");
        logger.debug(" 查询【报警工位表:STAT_STOP_LOG_TagTable】： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_ALM_STATION", con);
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
        cqExp.select(" from tabRefer");

        cqExp.where();

        cqExp.filed("".equals(quryType) ? null : "DATA4", CQExp.EQ, quryType);
        cqExp.orderByAsc(codeType);
        logger.debug(" 查询【生产工位表:tabStopSta】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STATION", con);
    }
    
    public static List queryTabStopLineStationAll(Connection con, java.lang.String codeType, java.lang.String quryType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct " + codeType + " as VALUE, " + codeType + " as TEXT");
        cqExp.select(" from tabStopline");

        cqExp.where();

        cqExp.filed("".equals(quryType) ? null : "EventData40", CQExp.EQ, quryType);
        cqExp.orderByAsc(codeType);
        logger.debug(" 查询【生产工位表:tabStopline】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STATION", con);
    }

    
    public static List queryPmcPpAttr(Connection con, java.lang.String codeType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct data3 as VALUE, data3 as TEXT");
        cqExp.select(" from tabRefer");

        cqExp.where();

        logger.debug(" 查询【生产属性表:refer】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STATION", con);
    }

    /**
     * 查询Alarm工位
     * @param con
     * @return
     * @throws Exception
     */
    public static List queryAlarmStationAll(Connection con) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct EQUIPMENT as VALUE, EQUIPMENT as TEXT");
        cqExp.select(" from PMC_PP_ALARM");
        logger.debug(" 查询【生产工位表:tabStopSta】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STATION", con);
    }


    /**
     * 查询StopLine原因
     * @param con
     * @return
     * @throws Exception
     */
    public static List queryStopLineReson(Connection con) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct EventData3 as VALUE, EventData3 as TEXT");
        cqExp.select(" from tabStopline");
        logger.debug(" 查询【停线原因:tabStopline】： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STATION", con);
    }


    /**
     * 查询线体注释
     * @param con
     * @return
     * @throws Exception
     */
    public static List queryLineComment(Connection con) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct LineCode as VALUE, LineName as TEXT");
        cqExp.select(" from PMC_LINE_COMMENT");
        logger.debug(" 查询【生产工位表:PMC_LINE_COMMENT】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("GET_COMMENT", con);
    }
    /**
     * 查询工段停线与报警对应停线原因
     * @param con
     * @return
     * @throws Exception
     */
    public static List queryStopReasonComment(Connection con) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT DISTINCT ATTRBUTE AS 'VALUE',ATTRBUTE AS TEXT FROM PMC_PP_ALARM WHERE  ATTRBUTE !=''");
        logger.debug(" 查询【报警表:PMC_PP_ALARM】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("GET_COMMENT", con);
    }
    /**
     * 查询Beat工位
     * @param con
     * @param codeType
     * @param quryType
     * @return
     * @throws Exception
     */
    public static List queryBeatStaAll(Connection con, java.lang.String codeType, java.lang.String quryType) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct " + codeType + " as VALUE, " + codeType + " as TEXT");
        cqExp.select(" from LGBS.dbo.PMC_CYCLETIME ");

        cqExp.where();

        cqExp.filed("".equals(quryType) ? null : "LINE_ENG", CQExp.EQ, quryType);
        cqExp.orderByAsc("STATION");
        logger.debug(" 查询【生产工位表:PMC_CYCLETIME】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_ALM_STATION", con);
    }

    public static List queryStopCountReson(Connection con) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select distinct [fenlei] as VALUE,[fenlei] as TEXT from [LGBS].[dbo].[STAT_STOP_LOG_TagTable] where [english]='BLD1'");
        logger.debug(" 查询【生产工位表:STAT_STOP_LOG_TagTable】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("STOP_RESON", con);
    }

    public static List queryCarType(Connection con) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("   select distinct carType as VALUE,carType as TEXT  from [LGBS].[dbo].[PROD_COUNT_LOG_TagTable] where short='BLD1'");
        logger.debug(" 查询【生产工位表:[PROD_COUNT_LOG_TagTable]】,作为combo数据源： " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CAR_TYPE", con);
    }
    
}
