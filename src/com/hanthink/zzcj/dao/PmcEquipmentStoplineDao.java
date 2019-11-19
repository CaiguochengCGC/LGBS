/**
 * 
 *
 * @File name:  PmcEquipmentStoplineDao.java 
 * @Create on:  2014-03-16 16:40:101
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

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.util.DictConstants;

/**
 * 【工段停线报表:ZZCJ_EQUIPMENT_STOPLINE】的Dao操作类
 * 
 */
public class PmcEquipmentStoplineDao extends DAO {
    /**
     * 查询所有【工段停线报表:ZZCJ_EQUIPMENT_STOPLINE】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-16
     * @author taofl
     * 
     */
    public static List queryPmcEquipmentStopline(Connection con, Date ppdate, Date nextPpdates,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PRODUCTIONLINE,PRODUCTIONLINENAME,BANCI,cast(cast(avg(CYCLETIME)*10 as int) as float)/10 as  CYCLETIME, ");
        cqExp.select("cast(cast(SUM(STOPTIME)/6.0 as int) as float)/10 as STOPTIME,");
        cqExp.select("SUM(STOPCOUNT) AS STOPCOUNT,");
        cqExp.select("cast(SUM(DATA3)/60.0 as decimal(18,2)) as DATA3,");
        cqExp.select("cast(SUM(DATA4)/60.0 as decimal(18,2)) as DATA4,");
        cqExp.select("cast(SUM(DATA5)/60.0 as decimal(18,2)) as DATA5,");
        cqExp.select("cast(SUM(DATA6)/60.0 as decimal(18,2)) as DATA6,");
        cqExp.select("cast(SUM(DATA7)/60.0 as decimal(18,2)) as DATA7,");
        cqExp.select("cast(sum(DATA8)/60.0 as decimal(18,2)) as DATA8, ");
        cqExp.select("cast(sum(DATA9)/60.0 as decimal(18,2)) as DATA9, ");
        cqExp.select("cast(sum(DATA10)/60.0 as decimal(18,2)) as DATA10 ");
        cqExp.select(" from ZZCJ_EQUIPMENT_STOPLINE ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(varchar(16),PPDATE,23)", CQExp.GREATER_EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));
        cqExp.filed(null == ppdate ? null : "convert(varchar(16),PPDATE,23)", CQExp.LESS, DateUtils.format(nextPpdates, DictConstants.FORMAT_DATE));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.filed("CYCLETIME", CQExp.NOT_EQ, 0);
        cqExp.group(" PRODUCTIONLINE,PRODUCTIONLINENAME,BANCI");
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        logger.debug("总装车间ZZCJ_EQUIPMENT_STOPLINE查询所有【工段停线报表:ZZCJ_EQUIPMENT_STOPLINE】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_EQUIPMENT_STOPLINE", con);
    }
    
    //日报表统计
    public static int countPmcEquipmentStopLine(Connection con, Date ppdate, Date nextPpdates) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM ");
        cqExp.select(" from ZZCJ_EQUIPMENT_STOPLINE");
        cqExp.where();
        
        cqExp.filed(null == ppdate ? null : "convert(varchar(16),PPDATE,23)", CQExp.GREATER_EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));
        cqExp.filed(null == ppdate ? null : "convert(varchar(16),PPDATE,23)", CQExp.LESS, DateUtils.format(nextPpdates, DictConstants.FORMAT_DATE));
        cqExp.group(" seq,ID,PRODUCTIONLINENAME,FACTORY,WORKSHOP,PRODUCTIONLINE,SHIFT,OPERUSER,UPDATETIME, STOPTIME, STOPCOUNT,DATA3,DATA4,DATA5, DATA6, DATA7 ");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug("总装车间ZZCJ_EQUIPMENT_STOPLINE统计所有【工段停线日报表:ZZCJ_EQUIPMENT_STOPLINE】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("ZZCJ_EQUIPMENT_STOPLINE", con).get("TOTAL_NUM")));
    }
    
    /**
     * 月
     * @author taofl
     * @param con
     * @param ppdate
     * @return
     * @throws Exception
     */
    public static List queryMonPmcEquipmentStopline(Connection con, Date ppdate,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PRODUCTIONLINE, PRODUCTIONLINENAME,BANCI,");
        cqExp.select("cast(cast(avg(CYCLETIME)*10 as int) as float)/10 as  CYCLETIME,");
        cqExp.select("cast(sum(STOPTIME)/60.0 as decimal(18,2)) as  STOPTIME,");
        cqExp.select("sum(STOPCOUNT) as STOPCOUNT,");
        cqExp.select("cast(sum(DATA3)/60.0 as decimal(18,2)) as DATA3,");
        cqExp.select("cast(sum(DATA4)/60.0 as decimal(18,2)) as DATA4,");
        cqExp.select("cast(sum(DATA5)/60.0 as decimal(18,2)) as DATA5,");
        cqExp.select("cast(sum(DATA6)/60.0 as decimal(18,2)) as DATA6,");
        cqExp.select("cast(sum(DATA7)/60.0 as decimal(18,2)) as DATA7, ");
        cqExp.select("cast(sum(DATA8)/60.0 as decimal(18,2)) as DATA8, ");
        cqExp.select("cast(sum(DATA9)/60.0 as decimal(18,2)) as DATA9, ");
        cqExp.select("cast(sum(DATA10)/60.0 as decimal(18,2)) as DATA10 ");
        //cqExp.select("sum(STOPTIME) as STOPTIME, sum(STOPCOUNT) as STOPCOUNT, sum(DATA3) as DATA3, sum(DATA4) as DATA4, sum(DATA5) as DATA5, sum(DATA6) as DATA6, sum(DATA7) as DATA7");
        cqExp.select(" from ZZCJ_EQUIPMENT_STOPLINE");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(char(7),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.filed("CYCLETIME", CQExp.NOT_EQ, 0);
        cqExp.group("PRODUCTIONLINE, PRODUCTIONLINENAME,BANCI");
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        logger.debug("总装车间ZZCJ_EQUIPMENT_STOPLINE查询所有【工段停线月报表:ZZCJ_EQUIPMENT_STOPLINE】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_EQUIPMENT_STOPLINE", con);
    }
    
    //月报表统计
    public static int countPmcEquipmentStopLineMonth(Connection con, Date ppdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM ");
        cqExp.select(" from ZZCJ_EQUIPMENT_STOPLINE");
        cqExp.where();
        
        cqExp.filed(null == ppdate ? null : "convert(char(7),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.group(" seq,ID,PRODUCTIONLINENAME,FACTORY,WORKSHOP,PRODUCTIONLINE,SHIFT,OPERUSER,UPDATETIME, STOPTIME, STOPCOUNT,DATA3,DATA4,DATA5, DATA6, DATA7 ");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug("总装车间ZZCJ_EQUIPMENT_STOPLINE统计所有【工段停线月报表:ZZCJ_EQUIPMENT_STOPLINE】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("ZZCJ_EQUIPMENT_STOPLINE", con).get("TOTAL_NUM")));
    }
    
    /**
     * 年
     * @author taofl
     * @param con
     * @param ppdate
     * @return
     * @throws Exception
     */
    public static List queryYearPmcEquipmentStopline(Connection con, Date ppdate,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PRODUCTIONLINE, PRODUCTIONLINENAME,BANCI,");
        cqExp.select("cast(cast(avg(CYCLETIME)*10 as int) as float)/10 as  CYCLETIME,");
        cqExp.select("cast(sum(STOPTIME)/60.0 as decimal(18,2)) as  STOPTIME,");
        cqExp.select("sum(STOPCOUNT) as STOPCOUNT,");
        cqExp.select("cast(sum(DATA3)/60.0 as decimal(18,2)) as DATA3,");
        cqExp.select("cast(sum(DATA4)/60.0 as decimal(18,2)) as DATA4,");
        cqExp.select("cast(sum(DATA5)/60.0 as decimal(18,2)) as DATA5,");
        cqExp.select("cast(sum(DATA6)/60.0 as decimal(18,2)) as DATA6,");
        cqExp.select("cast(sum(DATA7)/60.0 as decimal(18,2)) as DATA7, ");
        cqExp.select("cast(sum(DATA8)/60.0 as decimal(18,2)) as DATA8, ");
        cqExp.select("cast(sum(DATA9)/60.0 as decimal(18,2)) as DATA9, ");
        cqExp.select("cast(sum(DATA10)/60.0 as decimal(18,2)) as DATA10, ");
        cqExp.select("CONCAT(PRODUCTIONLINENAME,'[',BANCI,']') as FORMAT_PRODUCTLINE ");
        //cqExp.select("sum(STOPTIME) as STOPTIME, sum(STOPCOUNT) as STOPCOUNT, sum(DATA3) as DATA3, sum(DATA4) as DATA4, sum(DATA5) as DATA5, sum(DATA6) as DATA6, sum(DATA7) as DATA7 ");
        cqExp.select(" from ZZCJ_EQUIPMENT_STOPLINE");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(varchar(4),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.filed("CYCLETIME", CQExp.NOT_EQ, 0);
        cqExp.group("PRODUCTIONLINE, PRODUCTIONLINENAME,BANCI");
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        logger.debug("总装车间ZZCJ_EQUIPMENT_STOPLINE查询所有【工段停线年报表:ZZCJ_EQUIPMENT_STOPLINE】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_EQUIPMENT_STOPLINE", con);
    }
    
    //年报表统计
    public static int countPmcEquipmentStopLineYear(Connection con, Date ppdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM ");
        cqExp.select(" from ZZCJ_EQUIPMENT_STOPLINE");
        cqExp.where();
        
        cqExp.filed(null == ppdate ? null : "convert(varchar(4),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.group("seq,ID,PRODUCTIONLINENAME,FACTORY,WORKSHOP,PRODUCTIONLINE,SHIFT,OPERUSER,UPDATETIME, STOPTIME, STOPCOUNT,DATA3,DATA4,DATA5, DATA6, DATA7 ");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug("总装车间ZZCJ_EQUIPMENT_STOPLINE统计所有【工段停线年报表:ZZCJ_EQUIPMENT_STOPLINE】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("ZZCJ_EQUIPMENT_STOPLINE", con).get("TOTAL_NUM")));
    }
    
    /**
     * 周
     * @author taofl
     * @param con
     * @param ppdate
     * @return
     * @throws Exception
     */
    public static List queryWeekPmcEquipmentStopline(Connection con, Date startDate,Date lastDate,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PRODUCTIONLINE, PRODUCTIONLINENAME,BANCI,");
        cqExp.select("cast(cast(avg(CYCLETIME)*10 as int) as float)/10 as  CYCLETIME,");
        cqExp.select("cast(sum(STOPTIME)/60.0 as decimal(18,2)) as  STOPTIME,");
        cqExp.select("sum(STOPCOUNT) as STOPCOUNT,");
        cqExp.select("cast(sum(DATA3)/60.0 as decimal(18,2)) as DATA3,");
        cqExp.select("cast(sum(DATA4)/60.0 as decimal(18,2)) as DATA4,");
        cqExp.select("cast(sum(DATA5)/60.0 as decimal(18,2)) as DATA5,");
        cqExp.select("cast(sum(DATA6)/60.0 as decimal(18,2)) as DATA6,");
        cqExp.select("cast(sum(DATA7)/60.0 as decimal(18,2)) as DATA7,");
        cqExp.select("cast(sum(DATA8)/60.0 as decimal(18,2)) as DATA8, ");
        cqExp.select("cast(sum(DATA9)/60.0 as decimal(18,2)) as DATA9, ");
        cqExp.select("cast(sum(DATA10)/60.0 as decimal(18,2)) as DATA10 ");
        //cqExp.select("sum(STOPTIME) as STOPTIME, sum(STOPCOUNT) as STOPCOUNT, sum(DATA3) as DATA3, sum(DATA4) as DATA4, sum(DATA5) as DATA5, sum(DATA6) as DATA6, sum(DATA7) as DATA7");
        cqExp.select(" from ZZCJ_EQUIPMENT_STOPLINE");
        cqExp.where();

        cqExp.filed("convert(varchar(20),PPDATE,23)", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATE));
        cqExp.filed("convert(varchar(20),PPDATE,23)", CQExp.LESS_EQ, DateUtils.format(lastDate, DictConstants.FORMAT_DATE));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.filed("CYCLETIME", CQExp.NOT_EQ, 0);
        cqExp.group("PRODUCTIONLINE, PRODUCTIONLINENAME,BANCI");
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        logger.debug("总装车间ZZCJ_EQUIPMENT_STOPLINE查询所有【工段停线周报表:ZZCJ_EQUIPMENT_STOPLINE】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_EQUIPMENT_STOPLINE", con);
    }
    
    //周报表统计
    public static int countPmcEquipmentStopLineWeek(Connection con, Date startDate,Date lastDate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM ");
        cqExp.select(" from ZZCJ_EQUIPMENT_STOPLINE");
        cqExp.where();
        
        cqExp.filed("convert(varchar(20),PPDATE,23)", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATE));
        cqExp.filed("convert(varchar(20),PPDATE,23)", CQExp.LESS_EQ, DateUtils.format(lastDate, DictConstants.FORMAT_DATE));
        cqExp.group("seq,ID,PRODUCTIONLINENAME,FACTORY,WORKSHOP,PRODUCTIONLINE,SHIFT,OPERUSER,UPDATETIME, STOPTIME, STOPCOUNT,DATA3,DATA4,DATA5, DATA6, DATA7 ");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug("总装车间ZZCJ_EQUIPMENT_STOPLINE统计所有【工段停线周报表:ZZCJ_EQUIPMENT_STOPLINE】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("ZZCJ_EQUIPMENT_STOPLINE", con).get("TOTAL_NUM")));
    }
    
    /**
     * 查询所有【工段停线报表:ZZCJ_EQUIPMENT_STOPLINE】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-16
     * @author 王家乐
     * 
     */
    public static List queryPmcEquipmentStoplineKpi(Connection con, Date ppdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select A.PRODUCTIONLINE,A.PRODUCTIONLINENAME,STOPTIME/STOPCOUNT MTTR,  ");
        cqExp.select(" (ISNULL(datediff(minute,b.STARTTIME1,b.ENDTIME1),0)+ISNULL(datediff(minute,b.STARTTIME2,b.ENDTIME2),0)+ISNULL(datediff(minute,b.STARTTIME3,b.ENDTIME3),0))/STOPCOUNT MTBF  ,");
        cqExp.select(" (ISNULL(datediff(minute,b.STARTTIME1,b.ENDTIME1),0)+ISNULL(datediff(minute,b.STARTTIME2,b.ENDTIME2),0)+ISNULL(datediff(minute,b.STARTTIME3,b.ENDTIME3),0))/(ISNULL(datediff(minute,C.STARTTIME,C.ENDTIME),0)-C.RESTTIME)*100 OEE,");
        cqExp.select(" STOPTIME/(ISNULL(datediff(minute,b.STARTTIME1,b.ENDTIME1),0)+ISNULL(datediff(minute,b.STARTTIME2,b.ENDTIME2),0)+ISNULL(datediff(minute,b.STARTTIME3,b.ENDTIME3),0))*100 PTM ,");
        cqExp.select(" STOPTIME,STOPCOUNT");
        cqExp.select(" from ZZCJ_EQUIPMENT_STOPLINE A,ZZCJ_DATE_PP B,ZZCJ_DATE_IMPORT C");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(varchar(20),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));
        cqExp.filed(null == ppdate ? null : "convert(varchar(20),B.WORKDATE,23)", CQExp.EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));
        cqExp.andStr(" B.WORKDATE  = C.WORKDATE ");
        cqExp.andStr(" A.PRODUCTIONLINE = b.PRODUCTIONLINE"); 
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        logger.debug("总装车间ZZCJ_EQUIPMENT_STOPLINE查询所有【工段停线报表:ZZCJ_EQUIPMENT_STOPLINE】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_EQUIPMENT_STOPLINE", con);
    }
    
    /**
     * 月
     * @author taofl
     * @param con
     * @param ppdate
     * @return
     * @throws Exception
     */
    public static List queryMonPmcEquipmentStoplineKpi(Connection con, Date ppdate) throws Exception {
    	CQExp cqExp = CQExp.instance();
        cqExp.select("select A.PRODUCTIONLINE,A.PRODUCTIONLINENAME,sum(STOPTIME)/sum(STOPCOUNT) MTTR,  ");
        cqExp.select(" sum(ISNULL(datediff(minute,b.STARTTIME1,b.ENDTIME1),0)+ISNULL(datediff(minute,b.STARTTIME2,b.ENDTIME2),0)+ISNULL(datediff(minute,b.STARTTIME3,b.ENDTIME3),0))/sum(STOPCOUNT) MTBF  ,");
        cqExp.select(" sum(ISNULL(datediff(minute,b.STARTTIME1,b.ENDTIME1),0)+ISNULL(datediff(minute,b.STARTTIME2,b.ENDTIME2),0)+ISNULL(datediff(minute,b.STARTTIME3,b.ENDTIME3),0))/sum(ISNULL(datediff(minute,C.STARTTIME,C.ENDTIME),0)-C.RESTTIME)*100 OEE,");
        cqExp.select(" sum(STOPTIME)/sum(ISNULL(datediff(minute,b.STARTTIME1,b.ENDTIME1),0)+ISNULL(datediff(minute,b.STARTTIME2,b.ENDTIME2),0)+ISNULL(datediff(minute,b.STARTTIME3,b.ENDTIME3),0))*100 PTM ,");
        cqExp.select(" sum(STOPTIME) STOPTIME,sum(STOPCOUNT) STOPCOUNT");
        cqExp.select(" from ZZCJ_EQUIPMENT_STOPLINE A,ZZCJ_DATE_PP B,ZZCJ_DATE_IMPORT C");
        cqExp.where();

        cqExp.andStr(" B.WORKDATE  = C.WORKDATE ");
        cqExp.andStr(" A.PRODUCTIONLINE = b.PRODUCTIONLINE"); 
        cqExp.filed(null == ppdate ? null : "convert(char(7),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.group("A.PRODUCTIONLINE ,A.PRODUCTIONLINENAME");
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        logger.debug("ZZCJ_EQUIPMENT_STOPLINE查询所有【工段停线报表:ZZCJ_EQUIPMENT_STOPLINE】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_EQUIPMENT_STOPLINE", con);
    }
    
    /**
     * 年
     * @author taofl
     * @param con
     * @param ppdate
     * @return
     * @throws Exception
     */
    public static List queryYearPmcEquipmentStoplineKpi(Connection con, Date ppdate) throws Exception {
    	CQExp cqExp = CQExp.instance();
        cqExp.select("select A.PRODUCTIONLINE,A.PRODUCTIONLINENAME,sum(STOPTIME)/sum(STOPCOUNT) MTTR,  ");
        cqExp.select(" sum(ISNULL(datediff(minute,b.STARTTIME1,b.ENDTIME1),0)+ISNULL(datediff(minute,b.STARTTIME2,b.ENDTIME2),0)+ISNULL(datediff(minute,b.STARTTIME3,b.ENDTIME3),0))/sum(STOPCOUNT) MTBF  ,");
        cqExp.select(" sum(ISNULL(datediff(minute,b.STARTTIME1,b.ENDTIME1),0)+ISNULL(datediff(minute,b.STARTTIME2,b.ENDTIME2),0)+ISNULL(datediff(minute,b.STARTTIME3,b.ENDTIME3),0))/sum(ISNULL(datediff(minute,C.STARTTIME,C.ENDTIME),0)-C.RESTTIME)*100 OEE,");
        cqExp.select(" sum(STOPTIME)/sum(ISNULL(datediff(minute,b.STARTTIME1,b.ENDTIME1),0)+ISNULL(datediff(minute,b.STARTTIME2,b.ENDTIME2),0)+ISNULL(datediff(minute,b.STARTTIME3,b.ENDTIME3),0))*100 PTM ,");
        cqExp.select(" sum(STOPTIME) STOPTIME,sum(STOPCOUNT) STOPCOUNT");
        cqExp.select(" from ZZCJ_EQUIPMENT_STOPLINE A,ZZCJ_DATE_PP B,ZZCJ_DATE_IMPORT C");
        cqExp.where();

        cqExp.andStr(" B.WORKDATE  = C.WORKDATE ");
        cqExp.andStr(" A.PRODUCTIONLINE = b.PRODUCTIONLINE"); 
        cqExp.filed(null == ppdate ? null : "convert(char(4),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.group("A.PRODUCTIONLINE ,A.PRODUCTIONLINENAME");
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        logger.debug("总装车间ZZCJ_EQUIPMENT_STOPLINE查询所有【工段停线报表:ZZCJ_EQUIPMENT_STOPLINE】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_EQUIPMENT_STOPLINE", con);
    }
}
