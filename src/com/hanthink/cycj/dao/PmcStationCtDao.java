
             
/**
 * 
 *
 * @File name:  PmcStationCtDao.java 
 * @Create on:  2014-03-19 21:01:818
 * @Author   :  林辉
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
import cn.boho.framework.utils.DateUtils;

import com.hanthink.util.DictConstants;
            
        /**
 * 【工位CT报表:CYCJ_STATION_CT】的Dao操作类
 * 
 */
public class PmcStationCtDao extends DAO {
	/**
	* 查询所有【工位CT报表:CYCJ_STATION_CT】信息
	* @param con
	* @return
	* @throws Exception
	* @date 2014-03-19
	* @author 王家乐
	*
	*/
    
    
	public static List queryDayPmcStationCt(Connection con,Date ppdate,String productionline)throws Exception{
		CQExp cqExp = CQExp.instance();
		/*cqExp.select("select distinct a.PRODUCTIONLINE, a.PRODUCTIONLINENAME,a.STATION,a.MODEL,a.DATA14,a.DATA4,a.DATA5,a.DATA6,a.DATA7,a.DATA8,a.DATA9,a.DATA10,a.DATA11,a.DATA12");
        cqExp.select(" from CYCJ_STATION_CT as a , ");
        cqExp.select(" (select PRODUCTIONLINE, MAX(DATA12) AS DATA12  FROM   CYCJ_STATION_CT ");
        cqExp.select(" where convert(varchar(20),PPDATE,23) = '" + DateUtils.format(ppdate, DictConstants.FORMAT_DATE) + "' ");
        cqExp.select(" GROUP  BY PRODUCTIONLINE) AS B where A.PRODUCTIONLINE = B.PRODUCTIONLINE and A.DATA12 = B.DATA12 ");
        cqExp.filed("".equals(productionline) ? null : "PRODUCTIONLINENAME" , cqExp.EQ, productionline);
        cqExp.filed(null == ppdate ? null : "convert(varchar(20),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));
        cqExp.orderByDesc("DATA12");*/
        
        cqExp.select("select distinct a.PRODUCTIONLINE, a.PRODUCTIONLINENAME,a.STATION,a.MODEL,a.DATA14,a.DATA4,a.DATA5,a.DATA6,a.DATA7,a.DATA8,a.DATA9,a.DATA10,a.DATA11,a.DATA12");
        cqExp.select(" from CYCJ_STATION_CT as a");
        
        cqExp.where();
        cqExp.filed("".equals(productionline) ? null : "PRODUCTIONLINENAME" , cqExp.EQ, productionline);
        cqExp.filed(null == ppdate ? null : "convert(varchar(20),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));
        cqExp.orderByDesc("DATA14");
        cqExp.orderByAsc("a.PRODUCTIONLINE,a.STATION");
        
        logger.debug("冲压车间CYCJ_STATION_CT查询所有【工位CT报表:CYCJ_STATION_CT】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CYCJ_STATION_CT", con);
	}
	
	public static int countDayPmcStationCt(Connection con,Date ppdate,String productionline)throws Exception{
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from CYCJ_STATION_CT  ");
        cqExp.where();
        
        cqExp.filed("".equals(productionline) ? null : "PRODUCTIONLINENAME" , cqExp.EQ, productionline);
        cqExp.filed(null == ppdate ? null : "convert(varchar(20),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));
        cqExp.group("PRODUCTIONLINE, PRODUCTIONLINENAME,STATION,MODEL,DATA12,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10");
        logger.debug("冲压车间CYCJ_STATION_CT查询所有【工位CT报表:CYCJ_STATION_CT】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("CYCJ_STATION_CT", con).get("TOTAL_NUM")));
    }
	
	public static List queryMonthPmcStationCt(Connection con,Date ppdate,String productionline) throws Exception {
        CQExp cqExp = CQExp.instance();
        /*cqExp.select("select a.PRODUCTIONLINE, a.PRODUCTIONLINENAME,a.STATION,a.MODEL,a.DATA14,a.DATA4,a.DATA5,a.DATA6,a.DATA7,a.DATA8,a.DATA9,a.DATA10,a.DATA11,a.DATA12");
        cqExp.select(" from CYCJ_STATION_CT as a , ");
        cqExp.select(" (select PRODUCTIONLINE, MAX(DATA12) AS DATA12  FROM   CYCJ_STATION_CT ");
        cqExp.select(" where convert(char(7),PPDATE,23) = '" + DateUtils.format(ppdate, "yyyy-MM") + "' ");
        cqExp.select(" GROUP  BY PRODUCTIONLINE) AS B where A.PRODUCTIONLINE = B.PRODUCTIONLINE and A.DATA12 = B.DATA12 ");
        cqExp.filed("".equals(productionline) ? null : "PRODUCTIONLINENAME" , cqExp.EQ, productionline);
        cqExp.filed(null == ppdate ? null : "convert(char(7),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        //cqExp.group("PRODUCTIONLINE, PRODUCTIONLINENAME,STATION,MODEL,DATA12,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10");
        cqExp.orderByDesc("DATA12");*/
        
        cqExp.select("select a.PRODUCTIONLINE, a.PRODUCTIONLINENAME,a.STATION,a.MODEL,a.DATA14,a.DATA4,a.DATA5,a.DATA6,a.DATA7,a.DATA8,a.DATA9,a.DATA10,a.DATA11,a.DATA12");
        cqExp.select(" from CYCJ_STATION_CT as a");
        
        cqExp.where();
        cqExp.filed("".equals(productionline) ? null : "PRODUCTIONLINENAME" , cqExp.EQ, productionline);
        cqExp.filed(null == ppdate ? null : "convert(char(7),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.orderByDesc("DATA14");
        cqExp.orderByAsc("a.PRODUCTIONLINE,a.STATION");
        
        logger.debug("冲压车间CYCJ_STATION_CT查询所有【工段停线报表:CYCJ_STATION_CT】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CYCJ_STATION_CT", con);
    }
	
	public static int countMonthPmcStationCt(Connection con,Date ppdate,String productionline)throws Exception{
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from CYCJ_STATION_CT  ");
        cqExp.where();
        
        cqExp.filed("".equals(productionline) ? null : "PRODUCTIONLINENAME" , cqExp.EQ, productionline);
//        cqExp.filed(null == ppdate ? null : "convert(varchar(20),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.group("PRODUCTIONLINE, PRODUCTIONLINENAME,STATION,MODEL,DATA12,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10");
        logger.debug("冲压车间CYCJ_STATION_CT查询所有【工位CT报表:CYCJ_STATION_CT】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("CYCJ_STATION_CT", con).get("TOTAL_NUM")));
    }
	
	public static List queryYearPmcStationCt(Connection con,Date ppdate,String productionline) throws Exception {
        CQExp cqExp = CQExp.instance();
        /*cqExp.select("select a.PRODUCTIONLINE, a.PRODUCTIONLINENAME,a.STATION,a.MODEL,a.DATA14,a.DATA4,a.DATA5,a.DATA6,a.DATA7,a.DATA8,a.DATA9,a.DATA10,a.DATA11,a.DATA12");
        cqExp.select(" from CYCJ_STATION_CT as a , ");
        cqExp.select(" (select PRODUCTIONLINE, MAX(DATA12) AS DATA12  FROM   CYCJ_STATION_CT ");
        cqExp.select(" where convert(varchar(4),PPDATE,23) = '" + DateUtils.format(ppdate, "yyyy") + "' ");
        cqExp.select(" GROUP  BY PRODUCTIONLINE) AS B where A.PRODUCTIONLINE = B.PRODUCTIONLINE and A.DATA12 = B.DATA12 ");
        cqExp.filed("".equals(productionline) ? null : "PRODUCTIONLINENAME" , cqExp.EQ, productionline);
        cqExp.filed(null == ppdate ? null : "convert(varchar(4),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.orderByDesc("DATA12");*/
        
        cqExp.select("select a.PRODUCTIONLINE, a.PRODUCTIONLINENAME,a.STATION,a.MODEL,a.DATA14,a.DATA4,a.DATA5,a.DATA6,a.DATA7,a.DATA8,a.DATA9,a.DATA10,a.DATA11,a.DATA12");
        cqExp.select(" from CYCJ_STATION_CT as a");
        cqExp.where();
        cqExp.filed("".equals(productionline) ? null : "PRODUCTIONLINENAME" , cqExp.EQ, productionline);
        cqExp.filed(null == ppdate ? null : "convert(varchar(4),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.orderByDesc("DATA14");
        cqExp.orderByAsc("a.PRODUCTIONLINE,a.STATION");
        
        logger.debug("冲压车间CYCJ_STATION_CT查询所有【工段停线报表:CYCJ_STATION_CT】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CYCJ_STATION_CT", con);
    }
	
	public static int countYearPmcStationCt(Connection con,Date ppdate,String productionline)throws Exception{
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from CYCJ_STATION_CT  ");
        cqExp.where();
        
        cqExp.filed("".equals(productionline) ? null : "PRODUCTIONLINENAME" , cqExp.EQ, productionline);
//        cqExp.filed(null == ppdate ? null : "convert(varchar(20),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.group("PRODUCTIONLINE, PRODUCTIONLINENAME,STATION,MODEL,DATA12,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10");
        logger.debug("冲压车间CYCJ_STATION_CT查询所有【工位CT报表:冲压车间CYCJ_STATION_CT】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("CYCJ_STATION_CT", con).get("TOTAL_NUM")));
    }
	
	public static List queryWeekPmcStationCt(Connection con, Date startDate,Date lastDate,String productionline) throws Exception {
        CQExp cqExp = CQExp.instance();
        /*cqExp.select("select distinct a.PRODUCTIONLINE, a.PRODUCTIONLINENAME,a.STATION,a.MODEL,a.DATA14,a.DATA4,a.DATA5,a.DATA6,a.DATA7,a.DATA8,a.DATA9,a.DATA10,a.DATA11,a.DATA12");
        cqExp.select(" from CYCJ_STATION_CT as a , ");
        cqExp.select(" (select PRODUCTIONLINE, MAX(DATA12) AS DATA12  FROM   CYCJ_STATION_CT ");
        cqExp.select(" where convert(varchar(20),PPDATE,23) >= '" + DateUtils.format(startDate, DictConstants.FORMAT_DATE) + "' and convert(varchar(20),PPDATE,23) <= '" + DateUtils.format(lastDate, DictConstants.FORMAT_DATE) + "'");
        cqExp.select(" GROUP  BY PRODUCTIONLINE) AS B where A.PRODUCTIONLINE = B.PRODUCTIONLINE and A.DATA12 = B.DATA12 ");
        cqExp.filed("".equals(productionline) ? null : "PRODUCTIONLINENAME" , cqExp.EQ, productionline);
        cqExp.filed("convert(varchar(20),PPDATE,23)", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATE));
        cqExp.filed("convert(varchar(20),PPDATE,23)", CQExp.LESS_EQ, DateUtils.format(lastDate, DictConstants.FORMAT_DATE));
        cqExp.orderByDesc("DATA12");*/
        
        cqExp.select("select distinct a.PRODUCTIONLINE, a.PRODUCTIONLINENAME,a.STATION,a.MODEL,a.DATA14,a.DATA4,a.DATA5,a.DATA6,a.DATA7,a.DATA8,a.DATA9,a.DATA10,a.DATA11,a.DATA12");
        cqExp.select(" from CYCJ_STATION_CT as a");
        
        cqExp.where();
        
        cqExp.filed("".equals(productionline) ? null : "PRODUCTIONLINENAME" , cqExp.EQ, productionline);
        cqExp.filed("convert(varchar(20),PPDATE,23)", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATE));
        cqExp.filed("convert(varchar(20),PPDATE,23)", CQExp.LESS_EQ, DateUtils.format(lastDate, DictConstants.FORMAT_DATE));
        cqExp.orderByDesc("DATA14");
        cqExp.orderByAsc("a.PRODUCTIONLINE,a.STATION");
        
        logger.debug("冲压车间CYCJ_STATION_CT查询所有【工段停线报表:CYCJ_STATION_CT】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CYCJ_STATION_CT", con);
    }
}
