/**
 * 
 *
 * @File name:  PmcEquipmentStopDao.java 
 * @Create on:  2014-03-16 16:26:969
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
import cn.boho.framework.utils.DateUtils;

import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

/**
 * 【工位设备停机表:PMC_EQUIPMENT_STOP】的Dao操作类
 * 
 */
public class PmcEquipmentStopForProductionLineDao extends DAO {
    /**
     * 瓶颈工位(停机时间)
     * @param con
     * @param pdLine
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopAgen(Connection con, Date startDate, Date endDate, java.lang.String pdLine,String shift) throws Exception {
        CQExp cqExp = CQExp.instance();
        
        cqExp.select("SELECT convert(varchar(100),eventdate,23) AS DDATE,EVENTDATA8 AS PRODUCTIONLINE,EventData7 AS PRODUCTIONLINENAME,");
        cqExp.select("EventData5 AS STATION,SUM(EventData1)/60 as STOPTIME,COUNT(1) as STOPCOUNT,BANCI FROM TABSTOPSTA");
        cqExp.where();
        cqExp.select(" and BANCI!=0");
        cqExp.filed("convert(varchar(100),eventdate,23)", cqExp.GREATER_EQ, Tools.getTimestamp(startDate));
	    cqExp.filed("convert(varchar(100),eventdate,23)", cqExp.LESS, Tools.getNextDayTimestamp(endDate));
	    cqExp.filed("".equals(pdLine) ? null : "EventData8", cqExp.EQ, pdLine);
        cqExp.filed("".equals(shift) ? null : "BANCI", cqExp.EQ, shift);
	    
	    cqExp.group("EVENTDATA8,EventData7,EventData4,BANCI,EventData5,convert(varchar(100),eventdate,23)");
	    cqExp.orderByDesc("SUM(EventData1)/60");
        logger.debug(" TABSTOPSTA查询所有【工段停线报表:TABSTOPSTA】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }

    public static List queryPmcEquipmentStopAgenDay(Connection con, Date ppdate,java.lang.String pdLine,String shift) throws Exception {
        CQExp cqExp = CQExp.instance();

        cqExp.select("SELECT convert(varchar(100),WorkDate,23) AS DDATE,EVENTDATA8 AS PRODUCTIONLINE,EventData7 AS PRODUCTIONLINENAME,");
        cqExp.select("EventData5 AS STATION,SUM(EventData1)/60 as STOPTIME,COUNT(1) as STOPCOUNT,BANCI FROM TABSTOPSTA");
        cqExp.where();
        cqExp.select(" and BANCI!=0");
        cqExp.filed(null == ppdate ? null : "convert(nvarchar(23),WorkDate,23)", CQExp.EQ,Tools.getTimestamp(ppdate));
        cqExp.filed("".equals(pdLine) ? null : "EventData8", cqExp.EQ, pdLine);
        cqExp.filed("".equals(shift) ? null : "BANCI", cqExp.EQ, shift);

        cqExp.group("EVENTDATA8,EventData7,EventData4,BANCI,EventData5,convert(varchar(100),WorkDate,23)");
        cqExp.orderByDesc("SUM(EventData1)/60");
        logger.debug(" TABSTOPSTA查询所有【工段停线报表:TABSTOPSTA】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
    /**
     * 瓶颈工位(停机次数)
     * @param con
     * @param startDate
     * @param endDate
     * @param pdLine
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopCount(Connection con, Date startDate, Date endDate, java.lang.String pdLine,String shift) throws Exception {
        CQExp cqExp = CQExp.instance();
        
        cqExp.select("SELECT EVENTDATA8 AS PRODUCTIONLINE,EventData7 AS PRODUCTIONLINENAME,");
        cqExp.select("EventData5 AS STATION,SUM(EventData1)/60 as STOPTIME,COUNT(1) as STOPCOUNT,case(BANCI) when '1' then '一班' when '2' then '二班' end as BANCI FROM TABSTOPSTA");
        cqExp.where();
        cqExp.select(" and BANCI!=0");
        cqExp.filed("CONVERT (VARCHAR(100), eventdate, 23)", cqExp.EQ, Tools.getTimestamp(startDate));
	    cqExp.filed("".equals(pdLine) ? null : "EventData8", cqExp.EQ, pdLine);
        cqExp.filed("".equals(shift) ? null : "BANCI", cqExp.EQ, shift);
	    cqExp.group("EVENTDATA8,EventData7,EventData4,BANCI,EventData5");
	    cqExp.orderByDesc("COUNT(*)");
        logger.debug(" TABSTOPSTA查询所有【工段停线报表:TABSTOPSTA】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }

    /**
     * 瓶颈工位(停机次数) Y
     * @param con
     * @param startDate
     * @param pdLine
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopCountY(Connection con, String startDate, java.lang.String pdLine,String shift) throws Exception {
        CQExp cqExp = CQExp.instance();

        cqExp.select("SELECT EVENTDATA8 AS PRODUCTIONLINE,EventData7 AS PRODUCTIONLINENAME,");
        cqExp.select("EventData5 AS STATION,SUM(EventData1)/60 as STOPTIME,COUNT(1) as STOPCOUNT,case(BANCI) when '1' then '一班' when '2' then '二班' end as BANCI FROM TABSTOPSTA");
        cqExp.where();
        cqExp.select(" and BANCI!=0");
        cqExp.filed("left(CONVERT (VARCHAR(100), eventdate, 23),4)", cqExp.EQ, startDate);
        cqExp.filed("".equals(pdLine) ? null : "EventData7", cqExp.EQ, pdLine);
        cqExp.filed("".equals(shift) ? null : "BANCI", cqExp.EQ, shift);
        cqExp.group("EVENTDATA8,EventData7,EventData5,BANCI");
        cqExp.orderByDesc("COUNT(*)");
        logger.debug(" TABSTOPSTA查询所有【工段停线报表:TABSTOPSTA】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }

    /**
     * 瓶颈工位(停机次数) M
     * @param con
     * @param startDate
     * @param pdLine
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopCountM(Connection con, String startDate, java.lang.String pdLine,String shift) throws Exception {
        CQExp cqExp = CQExp.instance();

        cqExp.select("SELECT EVENTDATA8 AS PRODUCTIONLINE,EventData7 AS PRODUCTIONLINENAME,");
        cqExp.select("EventData5 AS STATION,SUM(EventData1)/60 as STOPTIME,COUNT(1) as STOPCOUNT,case(BANCI) when '1' then '一班' when '2' then '二班' end as BANCI FROM TABSTOPSTA");
        cqExp.where();
        cqExp.select(" and BANCI!=0");
        cqExp.filed("left(CONVERT (VARCHAR(100), eventdate, 23),7)", cqExp.EQ, startDate);
        cqExp.filed("".equals(pdLine) ? null : "EventData7", cqExp.EQ, pdLine);
        cqExp.filed("".equals(shift) ? null : "BANCI", cqExp.EQ, shift);
        cqExp.group("EVENTDATA8,EventData7,EventData5,BANCI");
        cqExp.orderByDesc("COUNT(*)");
        logger.debug(" TABSTOPSTA查询所有【工段停线报表:TABSTOPSTA】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }

    /**
     * 瓶颈工位(停机次数) W
     * @param con
     * @param startDate
     * @param endDate
     * @param pdLine
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopCountW(Connection con, String startDate,String endDate, java.lang.String pdLine,String shift) throws Exception {
        CQExp cqExp = CQExp.instance();

        cqExp.select("SELECT EVENTDATA8 AS PRODUCTIONLINE,EventData7 AS PRODUCTIONLINENAME,");
        cqExp.select("EventData4 AS STATION,SUM(EventData1)/60 as STOPTIME,COUNT(1) as STOPCOUNT,case(BANCI) when '1' then '一班' when '2' then '二班' end as BANCI FROM TABSTOPSTA");
        cqExp.where();
        cqExp.select(" and BANCI!=0");
        cqExp.filed("left(CONVERT (VARCHAR(100), eventdate, 23),10)", cqExp.GREATER_EQ, startDate);
        cqExp.filed("left(CONVERT (VARCHAR(100), eventdate, 23),10)", cqExp.LESS_EQ, endDate);
        cqExp.filed("".equals(pdLine) ? null : "EventData7", cqExp.EQ, pdLine);
        cqExp.filed("".equals(shift) ? null : "BANCI", cqExp.EQ, shift);
        cqExp.group("EVENTDATA8,EventData7,EventData4,BANCI");
        cqExp.orderByDesc("COUNT(*)");
        logger.debug(" TABSTOPSTA查询所有【工段停线报表:TABSTOPSTA】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
    
    public static int countPmcEquipmentStop(Connection con, Date ppdate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from PMC_EQUIPMENT_STOP ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(varchar(20),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));
        cqExp.filed("".equals(pdLine) ? null : "PRODUCTIONLINENAME", CQExp.EQ, pdLine);
        cqExp.orderByDesc("STOPTIME");
        cqExp.group("ID,PPDATE,FACTORY,WORKSHOP,PRODUCTIONLINE,PRODUCTIONLINENAME,STATION,STOPTIME,STOPCOUNT,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,DATA30,DATA31,DATA32,DATA33,DATA34,DATA35,DATA36,DATA37,DATA38,DATA39,SHIFT,OPERUSER,UPDATETIME");

        logger.debug(" PMC_EQUIPMENT_STOP统计所有【工段停线报表:PMC_EQUIPMENT_STOP】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_EQUIPMENT_STOP", con).get("TOTAL_NUM")));
    }
    
    /**
     * 月
     * @param con
     * @param ppdate
     * @param pdLine
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopMonth(Connection con, Date ppdate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select eventdata15 as PRODUCTIONLINE,eventdata16 as PRODUCTIONLINENAME,eventdata1 as STATION,convert(numeric(8,2),sum(eventData14)/60) as STOPTIME,count(*) as STOPCOUNT from tabCycleTime ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(char(7),eventdate,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.filed("".equals(pdLine) ? null : "eventdata16", CQExp.EQ, pdLine);
        cqExp.group("eventdata15,eventdata16,eventdata1");
        cqExp.orderByDesc("sum(eventData14)/60");
        logger.debug(" PMC_EQUIPMENT_STOP查询所有【工段停线报表:PMC_EQUIPMENT_STOP】信息(月):  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
    
    public static int countPmcEquipmentStopMonth(Connection con, Date ppdate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from PMC_EQUIPMENT_STOP ");
        cqExp.where();

//        cqExp.filed(null == ppdate ? null : "convert(varchar(20),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.filed("".equals(pdLine) ? null : "PRODUCTIONLINENAME", CQExp.EQ, pdLine);
        cqExp.orderByDesc("STOPTIME");
        cqExp.group("ID,PPDATE,FACTORY,WORKSHOP,PRODUCTIONLINE,PRODUCTIONLINENAME,STATION,STOPTIME,STOPCOUNT,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,DATA30,DATA31,DATA32,DATA33,DATA34,DATA35,DATA36,DATA37,DATA38,DATA39,SHIFT,OPERUSER,UPDATETIME");

        logger.debug(" PMC_EQUIPMENT_STOP统计所有【工段停线月报表:PMC_EQUIPMENT_STOP】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_EQUIPMENT_STOP", con).get("TOTAL_NUM")));
    }
    
    /**
     * 年
     * @param con
     * @param ppdate
     * @param pdLine
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopYear(Connection con, Date ppdate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select eventdata15 as PRODUCTIONLINE,eventdata16 as PRODUCTIONLINENAME,eventdata1 as STATION,convert(numeric(10,2),sum(eventData14)/60) as STOPTIME,count(*) as STOPCOUNT from tabCycleTime ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(varchar(4),eventdate,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.filed("".equals(pdLine) ? null : "eventdata16", CQExp.EQ, pdLine);
        cqExp.group("eventdata15,eventdata16,eventdata1");
        cqExp.orderByDesc("sum(eventData14)/60");
        logger.debug(" PMC_EQUIPMENT_STOP查询所有【工段停线报表:PMC_EQUIPMENT_STOP】信息(年):  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
    
    public static int countPmcEquipmentStopYear(Connection con, Date ppdate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from PMC_EQUIPMENT_STOP ");
        cqExp.where();

//        cqExp.filed(null == ppdate ? null : "convert(varchar(20),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.filed("".equals(pdLine) ? null : "PRODUCTIONLINENAME", CQExp.EQ, pdLine);
        cqExp.orderByDesc("STOPTIME");
        cqExp.group("ID,PPDATE,FACTORY,WORKSHOP,PRODUCTIONLINE,PRODUCTIONLINENAME,STATION,STOPTIME,STOPCOUNT,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,DATA30,DATA31,DATA32,DATA33,DATA34,DATA35,DATA36,DATA37,DATA38,DATA39,SHIFT,OPERUSER,UPDATETIME");

        logger.debug(" PMC_EQUIPMENT_STOP统计所有【工段停线年报表:PMC_EQUIPMENT_STOP】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_EQUIPMENT_STOP", con).get("TOTAL_NUM")));
    }
    
    /**
     * 周
     * @param con
     * @param startDate
     * @param endDate
     * @param pdLine
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopWeek(Connection con, Date startDate, Date endDate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select eventdata15 as PRODUCTIONLINE,eventdata16 as PRODUCTIONLINENAME,eventdata1 as STATION,convert(numeric(6,2),sum(eventData14)/60) as STOPTIME,count(*) as STOPCOUNT from tabCycleTime ");
        cqExp.where();

        cqExp.filed("convert(varchar(20),eventdate,23)", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATE));
        cqExp.filed("convert(varchar(20),eventdate,23)", CQExp.LESS_EQ, DateUtils.format(endDate, DictConstants.FORMAT_DATE));
        cqExp.filed("".equals(pdLine) ? null : "eventdata16", CQExp.EQ, pdLine);
        cqExp.group("eventdata15,eventdata16,eventdata1");
        cqExp.orderByDesc("sum(eventData14)/60");
        logger.debug(" PMC_EQUIPMENT_STOP查询所有【工段停线报表:PMC_EQUIPMENT_STOP】信息(周):  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
    
    /**
     * 天
     * @param con
     * @param ppdate
     * @param pdLine
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopCtAgen(Connection con, Date ppdate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select eventdata15 as PRODUCTIONLINE,eventdata16 as PRODUCTIONLINENAME,eventdata1 as STATION,convert(numeric(6,2),sum(eventData14)/60) as STOPTIME,count(*) as STOPCOUNT from tabCycleTime ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(varchar(20),eventdate,23)", CQExp.EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));
        cqExp.filed("".equals(pdLine) ? null : "eventdata16", CQExp.EQ, pdLine);
        cqExp.group("eventdata15,eventdata16,eventdata1");
        cqExp.orderByDesc("count(*)");
        logger.debug(" PMC_EQUIPMENT_STOP查询所有【工段停线报表:PMC_EQUIPMENT_STOP】信息(天):  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
    
    public static int countPmcEquipmentStopCt(Connection con, Date ppdate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from PMC_EQUIPMENT_STOP ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(varchar(20),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));
        cqExp.filed("".equals(pdLine) ? null : "PRODUCTIONLINENAME", CQExp.EQ, pdLine);
        cqExp.orderByDesc("STOPCOUNT");
        cqExp.group("STATION,  PRODUCTIONLINENAME,PRODUCTIONLINE,STOPTIME,STOPCOUNT");
        logger.debug(" PMC_EQUIPMENT_STOP查询所有【工段停线报表:PMC_EQUIPMENT_STOP】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_EQUIPMENT_STOP", con).get("TOTAL_NUM")));
    }
    
    /**
     * 月
     * @param con
     * @param ppdate
     * @param pdLine
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopCtMonth(Connection con, Date ppdate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select eventdata15 as PRODUCTIONLINE,eventdata16 as PRODUCTIONLINENAME,eventdata1 as STATION,convert(numeric(10,2),sum(eventData14)/60) as STOPTIME,count(*) as STOPCOUNT from tabCycleTime ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(char(7),eventdate,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.filed("".equals(pdLine) ? null : "eventdata16", CQExp.EQ, pdLine);
        cqExp.group("eventdata15,eventdata16,eventdata1");
        cqExp.orderByDesc("count(*)");
        logger.debug(" PMC_EQUIPMENT_STOP查询所有【工段停线报表:PMC_EQUIPMENT_STOP】信息(月):  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
    
    public static int countPmcEquipmentStopCtMonth(Connection con, Date ppdate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from PMC_EQUIPMENT_STOP ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(varchar(20),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.filed("".equals(pdLine) ? null : "PRODUCTIONLINENAME", CQExp.EQ, pdLine);
        cqExp.orderByDesc("STOPCOUNT");
        cqExp.group("STATION,  PRODUCTIONLINENAME,PRODUCTIONLINE,STOPTIME,STOPCOUNT");
        logger.debug(" PMC_EQUIPMENT_STOP查询所有【工段停线报表:PMC_EQUIPMENT_STOP】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_EQUIPMENT_STOP", con).get("TOTAL_NUM")));
    }
    
    /**
     *年
     * @author taofl
     * @param con
     * @param ppdate
     * @return
     * @throws Exception
     */
    /*public static List queryYearPmcEquipmentStopCt(Connection con, Date ppdate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select STATION,  PRODUCTIONLINENAME,PRODUCTIONLINE,");
        cqExp.select("sum(STOPTIME) as STOPTIME, sum(STOPCOUNT) as STOPCOUNT");
        cqExp.select(" from PMC_EQUIPMENT_STOP");        
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(varchar(4),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.filed("".equals(pdLine) ? null : "PRODUCTIONLINENAME", CQExp.EQ, pdLine);
        cqExp.group("STATION,  PRODUCTIONLINENAME,PRODUCTIONLINE ");
        cqExp.orderByDesc("STOPCOUNT");
        logger.debug(" PMC_EQUIPMENT_STOP查询所有【工段停线报表:PMC_EQUIPMENT_STOP】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }*/
    
    /**
     * 年
     * @param con
     * @param ppdate
     * @param pdLine
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopCtYear(Connection con, Date ppdate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select eventdata15 as PRODUCTIONLINE,eventdata16 as PRODUCTIONLINENAME,eventdata1 as STATION,convert(numeric(10,2),sum(eventData14)/60) as STOPTIME,count(*) as STOPCOUNT from tabCycleTime ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(varchar(4),eventdate,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.filed("".equals(pdLine) ? null : "eventdata16", CQExp.EQ, pdLine);
        cqExp.group("eventdata15,eventdata16,eventdata1");
        cqExp.orderByDesc("count(*)");
        logger.debug(" PMC_EQUIPMENT_STOP查询所有【工段停线报表:PMC_EQUIPMENT_STOP】信息(年):  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
    
    public static int countPmcEquipmentStopCtYear(Connection con, Date ppdate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from PMC_EQUIPMENT_STOP ");
        cqExp.where();

        //cqExp.filed(null == ppdate ? null : "convert(varchar(20),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.filed("".equals(pdLine) ? null : "PRODUCTIONLINENAME", CQExp.EQ, pdLine);
        cqExp.orderByDesc("STOPCOUNT");
        cqExp.group("STATION,  PRODUCTIONLINENAME,PRODUCTIONLINE,STOPTIME,STOPCOUNT");
        logger.debug(" PMC_EQUIPMENT_STOP查询所有【工段停线报表:PMC_EQUIPMENT_STOP】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_EQUIPMENT_STOP", con).get("TOTAL_NUM")));
    }
    
    
    /**
     * 周
     * @param con
     * @param startDate
     * @param endDate
     * @param pdLine
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopCtWeek(Connection con, Date startDate, Date endDate, java.lang.String pdLine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select eventdata15 as PRODUCTIONLINE,eventdata16 as PRODUCTIONLINENAME,eventdata1 as STATION,convert(numeric(6,2),sum(eventData14)/60) as STOPTIME,count(*) as STOPCOUNT from tabCycleTime ");
        cqExp.where();

        cqExp.filed("convert(varchar(20),eventdate,23)", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATE));
        cqExp.filed("convert(varchar(20),eventdate,23)", CQExp.LESS_EQ, DateUtils.format(endDate, DictConstants.FORMAT_DATE));
        cqExp.filed("".equals(pdLine) ? null : "eventdata16", CQExp.EQ, pdLine);
        cqExp.group("eventdata15,eventdata16,eventdata1");
        cqExp.orderByDesc("count(*)");
        logger.debug(" PMC_EQUIPMENT_STOP查询所有【工段停线报表:PMC_EQUIPMENT_STOP】信息(周):  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
}
