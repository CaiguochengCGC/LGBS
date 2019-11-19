
/**
 * 
 *
 * @File name:  PmcDatePpneckDao.java 
 * @Create on:  2014-03-19 20:16:593
 * @Author   :  lanym
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
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.DAO;
import cn.boho.framework.po.Pager;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.util.Tools;

/**
 * 【瓶颈设备表:CYCJ_DATE_PPNECK】的Dao操作类
 * 
 */
public class PmcDatePpneckDao extends DAO {
	
	//瓶颈设备停机时间查询
	public static ComboPager queryPmcDatePpneckForStopTimeByPager(Connection con,Pager pager, Date startDate, Date endDate, int timeType, String yearType)throws Exception{
	    
	    CQExp cqexp = CQExp.instance();
	    
	    cqexp.select("SELECT EventData7,EventData4,EventData5,");
	    cqexp.select("SUM(EventData1)/60 as STOPTIME,COUNT(1) as STOPCOUNT FROM CYCJ_TABSTOPSTA ");
	    cqexp.where("EventData5 != '全部' AND EventData5 != '其他'");
	    cqexp.filed("convert(varchar(100),eventdate,20)", CQExp.GREATER_EQ, Tools.getTimestamp(startDate));	  
	    cqexp.filed("convert(varchar(100),eventdate,20)", CQExp.LESS, Tools.getNextDayTimestamp(endDate));
	    //if("".equals(yearType)){
	    	cqexp.group("EventData7,EventData4,EventData5");
	    /*}else{
	    	cqexp.group(yearType+"convert(varchar(2),datepart(week,convert(datetime,eventdate,20))),eventdata4");
	    }*/
	    
	    cqexp.orderByDesc("sum(eventdata1)/60");
	    
	    logger.info("冲压车间瓶颈设备停机次数表：" + cqexp.getSql());
	    
		return cqexp.getDynaBeanMapComboPager("tabStopLineCount", pager,con);
	}
	
	//瓶颈设备查询（停机时间）日报表导出时查询
	public static List queryPmcDatePpneckForStopTime(Connection con, Date ppdate)throws Exception{
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select * from (");
        
        cqexp.select("select EventData1, sum(convert(int,EventData2)) EventData2, sum(convert(int,EventData3)) EventData3");
        cqexp.select(" from CYCJ_tabStopLineCount");
        
        cqexp.where();
        cqexp.filed(null == ppdate ? null : "EventDate", CQExp.GREATER_EQ, Tools.getTimestamp(ppdate));
        cqexp.filed(null == ppdate ? null : "EventDate", CQExp.LESS, Tools.getNextDayTimestamp(ppdate));
        
        cqexp.group("EventData1");
        cqexp.orderByDesc("EventData2");
        
        cqexp.select(") A");
        
        logger.info("冲压车间瓶颈设备停机次数表：" + cqexp.getSql());
        
        return cqexp.getDynaBeanMapList("tabStopLineCount", con);
    }
	
	//瓶颈设备查询（停机时间）日报表导出时总数
	public static int countPmcDatePpneckForStopTime(Connection con, Date ppdate)throws Exception{
	        
	        CQExp cqexp = CQExp.instance();
	        cqexp.select("select count(*) as TOTAL_NUM from CYCJ_tabStopLineCount ");
	        
	        cqexp.where();
	        
	        cqexp.group(" ID,EventDate,EventData1,EventData2,EventData3,EventData4");
	        cqexp.orderByDesc(" EventData2");
	        
	        
	        logger.info("冲压车间瓶颈设备停机时间表(日)：" + cqexp.getSql());
	        
	        return Integer.parseInt(String.valueOf(cqexp.getDynaBeanMap("tabStopLineCount", con).get("TOTAL_NUM")));
    }
	
	//瓶颈设备查询（停机时间、停机频次）周报表导出时查询
	public static List queryPmcDatePpneckForStopWeekTime(Connection con, Date startDate, Date endDate)throws Exception{
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select * from (");
        
        cqexp.select("select EventData1, sum(convert(int,EventData2)) EventData2, sum(convert(int,EventData3)) EventData3");
        cqexp.select(" from CYCJ_tabStopLineCount");
        
        cqexp.where();
        cqexp.filed(null == startDate ? null : "EventDate", CQExp.GREATER_EQ, Tools.getTimestamp(startDate));
        cqexp.filed(null == endDate ? null : "EventDate", CQExp.LESS, Tools.getNextDayTimestamp(endDate));
        
        cqexp.group("EventData1");
        cqexp.orderByDesc("EventData2");
        
        cqexp.select(") A");
        
        logger.info("冲压车间瓶颈设备停机次数表：" + cqexp.getSql());
        
        return cqexp.getDynaBeanMapList("tabStopLineCount", con);
    }

	//瓶颈设备查询（停机时间）月报表导出时查询
	public static List queryPmcDatePpneckForStopTimeMonth(Connection con, Date ppdate)throws Exception{
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select * from (");
        
        cqexp.select("select EventData1, sum(convert(int,EventData2)) EventData2, sum(convert(int,EventData3)) EventData3");
        cqexp.select(" from CYCJ_tabStopLineCount");
        
        cqexp.where();

        cqexp.filed(null == ppdate ? null : "convert(char(7),EventDate,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        
        cqexp.group("EventData1");
        cqexp.orderByDesc("EventData2");
        
        cqexp.select(") A");
        
        logger.info("冲压车间瓶颈设备停机次数表(月)：" + cqexp.getSql());
        
        return cqexp.getDynaBeanMapList("tabStopLineCount", con);
    }
	
	//瓶颈设备查询（停机时间）月报表导出时总数
	public static int countPmcDatePpneckForStopTimeMonth(Connection con, Date ppdate)throws Exception{
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select count(*) as TOTAL_NUM from CYCJ_tabStopLineCount ");
        
        cqexp.where();
        
        cqexp.group(" ID,EventDate,EventData1,EventData2,EventData3,EventData4");
        cqexp.orderByDesc(" EventData2");
        
        
        logger.info("冲压车间瓶颈设备停机时间表(月)：" + cqexp.getSql());
        
        return Integer.parseInt(String.valueOf(cqexp.getDynaBeanMap("tabStopLineCount", con).get("TOTAL_NUM")));
	}
	
	//瓶颈设备查询（停机时间）年报表导出时查询
	public static List queryPmcDatePpneckForStopTimeYear(Connection con, Date ppdate)throws Exception{
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select * from (");
        
        cqexp.select("select EventData1, sum(convert(int,EventData2)) EventData2, sum(convert(int,EventData3)) EventData3");
        cqexp.select(" from CYCJ_tabStopLineCount");
        
        cqexp.where();
        cqexp.filed(null == ppdate ? null : "convert(char(4),EventDate,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));

        cqexp.group("EventData1");
        cqexp.orderByDesc("EventData2");
        
        cqexp.select(") A");
        
        logger.info("冲压车间瓶颈设备停机次数表(年)：" + cqexp.getSql());
        
        return cqexp.getDynaBeanMapList("tabStopLineCount", con);
    }

	//瓶颈设备查询（停机时间）年报表、周报表导出时总数
	public static int countPmcDatePpneckForStopTimeYear(Connection con, Date ppdate)throws Exception{
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select count(*) as TOTAL_NUM from CYCJ_tabStopLineCount ");
        
        cqexp.where();
        
        cqexp.group(" ID,EventDate,EventData1,EventData2,EventData3,EventData4");
        cqexp.orderByDesc(" EventData2");
        
        
        logger.info("冲压车间瓶颈设备停机时间表(年)：" + cqexp.getSql());
        
        return Integer.parseInt(String.valueOf(cqexp.getDynaBeanMap("tabStopLineCount", con).get("TOTAL_NUM")));
    }
	
	//瓶颈设备停机频率 查询 
    public static ComboPager queryPmcDatePpneckForStopCountByPager(Connection con,Pager pager, Date startDate, Date endDate, int timeType, String yearType)throws Exception{
        
        CQExp cqexp = CQExp.instance();
        
        cqexp.select("SELECT EventData7,EventData4,EventData5,");
	    cqexp.select("SUM(EventData1)/60 as STOPTIME,COUNT(1) as STOPCOUNT FROM CYCJ_TABSTOPSTA ");
	    cqexp.where(" EventData5 != '全部' AND EventData5 != '其他'");
	    cqexp.filed("convert(varchar(100),eventdate,20)", CQExp.GREATER_EQ, Tools.getTimestamp(startDate));	  
	    cqexp.filed("convert(varchar(100),eventdate,20)", CQExp.LESS, Tools.getNextDayTimestamp(endDate));
	    //if("".equals(yearType)){
	    	cqexp.group("EventData7,EventData4,EventData5");
	    /*}else{
	    	cqexp.group(yearType+"convert(varchar(2),datepart(week,convert(datetime,eventdate,20))),eventdata4");
	    }*/
	    
	    cqexp.orderByDesc("count(*)");
        
        logger.info("冲压车间瓶颈设备停机次数频率：" + cqexp.getSql());
        
        return cqexp.getDynaBeanMapComboPager("tabStopLineCount", pager,con);
    }

    //瓶颈设备查询（停机频次）日报表导出时查询
    public static List queryPmcDatePpneckForStopCount(Connection con, Date ppdate)throws Exception{
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select * from (");
        
        cqexp.select("select  EventData1, sum(convert(int,EventData2)) EventData2, sum(convert(int,EventData3)) EventData3");
        cqexp.select(" from CYCJ_tabStopLineCount");
        
        cqexp.where();
        cqexp.filed(null == ppdate ? null : "EventDate", CQExp.GREATER_EQ, Tools.getTimestamp(ppdate));
        cqexp.filed(null == ppdate ? null : "EventDate", CQExp.LESS, Tools.getNextDayTimestamp(ppdate));
        
        cqexp.group("EventData1");
        cqexp.orderByDesc("EventData3");
        
        cqexp.select(") A");
        
        logger.info("冲压车间瓶颈设备停机次数表(日)：" + cqexp.getSql());
        
        return cqexp.getDynaBeanMapList("tabStopLineCount", con);
    }
    
    //瓶颈设备查询（停机频次）日报表导出时总数
    public static int countPmcDatePpneckForStopCount(Connection con, Date ppdate)throws Exception{
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select count(*) as TOTAL_NUM from CYCJ_tabStopLineCount ");
        
//        cqexp.select("select  EventData1, sum(convert(int,EventData2)) EventData2, sum(convert(int,EventData3)) EventData3");
//        cqexp.select(" from tabStopLineCount");
        cqexp.where();
        
//        cqexp.filed(null == ppdate ? null : "convert(varchar(20),EventDate,23)", CQExp.EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));


        cqexp.group(" ID,EventDate,EventData1,EventData2,EventData3,EventData4");
        cqexp.orderByDesc(" EventData3");
        
//        cqexp.select(") A");
        
        logger.info("冲压车间瓶颈设备停机次数表(日)：" + cqexp.getSql());
        
        return Integer.parseInt(String.valueOf(cqexp.getDynaBeanMap("tabStopLineCount", con).get("TOTAL_NUM")));
    }
       
    //瓶颈设备查询（停机频次）月报表导出时总数
    public static int countPmcDatePpneckForStopCountMonth(Connection con, Date ppdate)throws Exception{
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select count(*) as TOTAL_NUM from CYCJ_tabStopLineCount ");
        
//        cqexp.select("select  EventData1, sum(convert(int,EventData2)) EventData2, sum(convert(int,EventData3)) EventData3");
//        cqexp.select(" from tabStopLineCount");
        cqexp.where();
        
//        cqexp.filed(null == ppdate ? null : "convert(varchar(20),EventDate,23)", CQExp.EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));


        cqexp.group(" ID,EventDate,EventData1,EventData2,EventData3,EventData4");
        cqexp.orderByDesc(" EventData3");
        
//        cqexp.select(") A");
        
        logger.info("冲压车间瓶颈设备停机次数表(月)：" + cqexp.getSql());
        
        return Integer.parseInt(String.valueOf(cqexp.getDynaBeanMap("tabStopLineCount", con).get("TOTAL_NUM")));
    }
    
    //瓶颈设备查询（停机频次）月报表导出时查询wf
    public static List queryPmcDatePpneckForStopCountMonth(Connection con, Date ppdate)throws Exception{
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select * from (");
        
        cqexp.select("select  EventData1, sum(convert(int,EventData2)) EventData2, sum(convert(int,EventData3)) EventData3");
        cqexp.select(" from CYCJ_tabStopLineCount");
        
        cqexp.where();
        cqexp.filed(null == ppdate ? null : "convert(char(7),EventDate,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));

        cqexp.group("EventData1");
        cqexp.orderByDesc("EventData3");
        
        cqexp.select(") A");
        
        logger.info("冲压车间瓶颈设备停机次数表(月)：" + cqexp.getSql());
        
        return cqexp.getDynaBeanMapList("tabStopLineCount", con);
    }
    
    //瓶颈设备查询（停机频次）年报表导出时查询wf
    public static List queryPmcDatePpneckForStopCountYear(Connection con, Date ppdate)throws Exception{
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select * from (");
        
        cqexp.select("select  EventData1, sum(convert(int,EventData2)) EventData2, sum(convert(int,EventData3)) EventData3");
        cqexp.select(" from CYCJ_tabStopLineCount");
        
        cqexp.where();
        cqexp.filed(null == ppdate ? null : "convert(char(4),EventDate,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));

        cqexp.group("EventData1");
        cqexp.orderByDesc("EventData3");
        
        cqexp.select(") A");
        
        logger.info("冲压车间瓶颈设备停机次数表(年)：" + cqexp.getSql());
        
        return cqexp.getDynaBeanMapList("tabStopLineCount", con);
    }
    
    //瓶颈设备查询（停机频次）年报表、周报表导出时总数wf
    public static int countPmcDatePpneckForStopCountYear(Connection con, Date ppdate)throws Exception{
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select count(*) as TOTAL_NUM from CYCJ_tabStopLineCount ");
        
//        cqexp.select("select  EventData1, sum(convert(int,EventData2)) EventData2, sum(convert(int,EventData3)) EventData3");
//        cqexp.select(" from tabStopLineCount");
        cqexp.where();
        
//        cqexp.filed(null == ppdate ? null : "convert(varchar(20),EventDate,23)", CQExp.EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));


        cqexp.group(" ID,EventDate,EventData1,EventData2,EventData3,EventData4");
        cqexp.orderByDesc(" EventData3");
        
//        cqexp.select(") A");
        
        logger.info("冲压车间瓶颈设备停机次数表(年)：" + cqexp.getSql());
        
        return Integer.parseInt(String.valueOf(cqexp.getDynaBeanMap("tabStopLineCount", con).get("TOTAL_NUM")));
    }
}
