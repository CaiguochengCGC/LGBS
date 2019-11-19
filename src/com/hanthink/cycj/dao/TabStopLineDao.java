package com.hanthink.cycj.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.DAO;
import cn.boho.framework.po.Pager;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.util.DictConstants;

public class TabStopLineDao extends DAO {

    /**
     * 生成停机报表
     * 
     * @author taofl
     * @create_date 2014-3-29 下午08:41:12
     * @param con
     * @param stoptime
     * @param toltlag
     * @throws Exception
     */
    public static ComboPager queryTabStopLine(Connection con, Date startDate, Date endDate, String EventData1, String banci,String shuxing,String EventData40, Pager pager) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT ID, EventData46,EventData40,EventDate1,EventDate2,EventData1,EventData50,EventData51,EventData53,BANCI,CONVERT(DATETIME,EventData54) as EventData54,");
        cqExp.select("datediff(ss, EventDate1,  EventDate2) StopTimeTotal, ");
        cqExp.select("EventData3 AS StopReson from CYCJ_tabStopline");
        cqExp.where();

//        System.out.println(startDate);
//        System.out.println(endDate);
//        System.out.println(DateUtils.format(endDate, DictConstants.FORMAT_DATETIME));
//        System.out.println(DateUtils.format(startDate, DictConstants.FORMAT_DATETIME));
        
        cqExp.filed(null == startDate ? null : "EventDate1", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endDate ? null : "EventDate1", CQExp.LESS_EQ, DateUtils.format(endDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
        cqExp.filed("".equals(banci) ? null : "BANCI", CQExp.EQ, banci);
        cqExp.filed("".equals(shuxing) ? null : "EventData3", CQExp.LIKE, "%" +shuxing+"%");
        cqExp.filed("".equals(EventData40) ? null : "Rtrim(EventData40)", CQExp.EQ, EventData40);
        cqExp.orderByDesc("EventDate1");
//        cqExp.orderByAsc("EventDate2");

        logger.debug("冲压车间停线分页查询: " + cqExp.getSql());
        return cqExp.getDynaBeanMapComboPager("tabStopLine", pager, con);

    }

    public static List queryTabStopLinePrespon(Connection con, String ppdate,String banci) throws Exception{
    	CQExp cqExp = CQExp.instance();
    	
    	//班次合并显示
    	if("".equals(banci)){
    		cqExp.select("select * from");
        	cqExp.select(" (select  SUBSTRING(CONVERT(varchar(100), EventDate1, 23),9,10) tday,EventData51,");
        	cqExp.select(" CONVERT(DECIMAL(18,2),SUM(datediff(ss, EventDate1,  EventDate2))/60.0) stoptimetotal from CYCJ_tabStopline");
        	cqExp.select(" where eventData51 is not null AND eventData51 != ''");
        	cqExp.select(" AND CONVERT(VARCHAR(7),EventDate1,23) = '"+ppdate+"'");
        	cqExp.select(" group by SUBSTRING(CONVERT(varchar(100), EventDate1, 23),9,10),EventData51");
        	cqExp.select(" ) t");
        	cqExp.select(" pivot (sum(stoptimetotal) for tday in ([01],[02],[03],[04],[05],[06],[07],[08],[09],[10]");
        	cqExp.select(" ,[11],[12],[13],[14],[15],[16],[17],[18],[19],[20]");
        	cqExp.select(" ,[21],[22],[23],[24],[25],[26],[27],[28],[29],[30],[31])) as ourpivot");
        	logger.info("冲压车间责任部门："+cqExp.getSql());
        	
        	return cqExp.getDynaBeanMapList("tabStopLine", con);
    	}
    	
    	
    	cqExp.select("select * from");
    	cqExp.select(" (select  SUBSTRING(CONVERT(varchar(100), EventDate1, 23),9,10) tday,EventData51,BANCI,");
    	cqExp.select(" CONVERT(DECIMAL(18,2),SUM(datediff(ss, EventDate1,  EventDate2))/60.0) stoptimetotal from CYCJ_tabStopline");
    	cqExp.select(" where eventData51 is not null AND eventData51 != ''");
    	if("".equals(banci)){
        	//cqExp.select(" AND EventData46 = 'FL'");
    	}else{
        	cqExp.select(" AND BANCI = '"+banci+"'");
    	}
    	cqExp.select(" AND CONVERT(VARCHAR(7),EventDate1,23) = '"+ppdate+"'");
    	cqExp.select(" group by SUBSTRING(CONVERT(varchar(100), EventDate1, 23),9,10),EventData51,BANCI");
    	cqExp.select(" ) t");
    	cqExp.select(" pivot (sum(stoptimetotal) for tday in ([01],[02],[03],[04],[05],[06],[07],[08],[09],[10]");
    	cqExp.select(" ,[11],[12],[13],[14],[15],[16],[17],[18],[19],[20]");
    	cqExp.select(" ,[21],[22],[23],[24],[25],[26],[27],[28],[29],[30],[31])) as ourpivot");
    	
    	logger.info("冲压车间责任部门："+cqExp.getSql());
    	
    	return cqExp.getDynaBeanMapList("tabStopLine", con);
    }
    
    /**
     * 生成停机报表
     * 
     * @author taofl
     * @create_date 2014-3-29 下午08:41:12
     * @param con
     * @param stoptime
     * @param toltlag
     * @throws Exception
     */
    public static ComboPager queryTabStopLineCut(Connection con, Date startDate, Date endDate, String EventData1, String banci,String shuxing,String EventData40, Pager pager) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT ID, EventData46,EventData40,EventDate1,EventDate2,EventData1,EventData50,EventData51,EventData53,BANCI,CONVERT(DATETIME,EventData54) as EventData54,");
        cqExp.select("datediff(ss, EventDate1,  EventDate2) StopTimeTotal, ");
        cqExp.select("EventData3 AS StopReson,EventData5 from CYCJ_tabStopline_cut");
        cqExp.where();   
        cqExp.filed(null == startDate ? null : "EventDate1", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endDate ? null : "EventDate1", CQExp.LESS_EQ, DateUtils.format(endDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
        cqExp.filed("".equals(banci) ? null : "BANCI", CQExp.EQ, banci);
        cqExp.filed("".equals(shuxing) ? null : "EventData3", CQExp.LIKE, "%" +shuxing+"%");
        cqExp.filed("".equals(EventData40) ? null : "Rtrim(EventData40)", CQExp.EQ, EventData40);
        cqExp.orderByDesc("EventDate1");
//        cqExp.orderByAsc("EventDate2");

        logger.debug("总装车间停线分页查询: " + cqExp.getSql());
        return cqExp.getDynaBeanMapComboPager("tabStopLine", pager, con);

    }
    
    public static List queryTabStopLine1(Connection con, java.util.Date startDate, java.util.Date endDate, java.lang.String eventdate40, java.lang.String eventdate1,
            java.lang.String banci) throws Exception {
        CQExp cqExp = CQExp.instance();

        /*cqExp.select("SELECT EventData46,EventData40,EventDate1,EventDate2,EventData1,EventData50,");
        cqExp.select("datediff(ss, EventDate1,  EventDate2) StopTimeTotal, ");
        cqExp.select("CASE WHEN ltrim(rtrim(EventData3)) != '' THEN EventData3 ");
        cqExp.select(" WHEN EventData4 IS NOT NULL THEN EventData4");
        cqExp.select(" WHEN EventData5 IS NOT NULL THEN EventData5");
        cqExp.select(" WHEN EventData6 IS NOT NULL THEN EventData6");
        cqExp.select(" WHEN EventData7 IS NOT NULL THEN EventData7");
        cqExp.select(" END AS StopReson from tabStopline");
        cqExp.where();
        */
        cqExp.select("SELECT ID,BANCI,EventData46,EventData40,EventDate1,EventDate2,EventData1,EventData50,EventData51,EventData53,CONVERT(DATETIME,EventData54) as EventData54,");
        cqExp.select("datediff(ss, EventDate1,  EventDate2) StopTimeTotal, ");
        cqExp.select("CASE WHEN ltrim(rtrim(EventData3)) != '' THEN EventData3 ");
        cqExp.select(" WHEN ltrim(rtrim(EventData4)) != '' THEN EventData4");
        cqExp.select(" WHEN ltrim(rtrim(EventData5)) != '' THEN EventData5");
        cqExp.select(" WHEN ltrim(rtrim(EventData6)) != '' THEN EventData6");
        cqExp.select(" WHEN ltrim(rtrim(EventData7)) != '' THEN EventData7");
        cqExp.select(" END AS StopReson from CYCJ_tabStopline");
        cqExp.where();
        
        cqExp.filed(null == startDate ? null : "EventDate1", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endDate ? null : "EventDate1", CQExp.LESS_EQ, DateUtils.format(endDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(banci) ? null : "Rtrim(BANCI)", CQExp.EQ, banci);
        //cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
        cqExp.filed("".equals(eventdate40) ? null : "Rtrim(EventData40)", CQExp.EQ, eventdate40);
        cqExp.orderByDesc("EventDate1");
//        cqExp.orderByAsc("EventDate2");
        
        logger.info("冲压车间停线查询信息：" + cqExp.getSql());

        List list = cqExp.getDynaBeanMapList("tabStopLine", con);

        return list;
    }
    
//  消除重复时间段后的停线查询导出
 public static List queryTabStopLine2(Connection con, java.util.Date startDate, java.util.Date endDate, java.lang.String eventdate40, java.lang.String eventdate1,
          java.lang.String banci) throws Exception {
      CQExp cqExp = CQExp.instance();
      cqExp.select("SELECT ID,BANCI,EventData46,EventData40,EventDate1,EventDate2,EventData1,EventData50,EventData51,EventData53,CONVERT(DATETIME,EventData54) as EventData54,");
      cqExp.select("datediff(ss, EventDate1,  EventDate2) StopTimeTotal, ");
      cqExp.select(" EventData3 AS StopReson,EventData5 from CYCJ_tabStopline_cut");
      cqExp.where();
      
      cqExp.filed(null == startDate ? null : "EventDate1", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATETIME));
      cqExp.filed(null == endDate ? null : "EventDate1", CQExp.LESS_EQ, DateUtils.format(endDate, DictConstants.FORMAT_DATETIME));
      cqExp.filed("".equals(banci) ? null : "Rtrim(BANCI)", CQExp.EQ, banci);
      //cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
      cqExp.filed("".equals(eventdate40) ? null : "Rtrim(EventData40)", CQExp.EQ, eventdate40);
      cqExp.orderByDesc("EventDate1");
//      cqExp.orderByAsc("EventDate2");
      
      logger.info("总装车间停线查询信息：" + cqExp.getSql());

      List list = cqExp.getDynaBeanMapList("tabStopLine", con);

      return list;
  }


    public static int countTabStopLine(Connection con, java.util.Date startDate, java.util.Date endDate, java.lang.String eventdate40, java.lang.String eventdate1,
            java.lang.String eventdate2) throws Exception {

        CQExp cqExp = CQExp.instance();

        cqExp.select("SELECT count(*) as TOTAL_NUM from CYCJ_tabStopline");
        cqExp.where();

        cqExp.filed(null == startDate ? null : "EventDate1", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endDate ? null : "EventDate1", CQExp.LESS_EQ, DateUtils.format(endDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(eventdate1) ? null : "Rtrim(EventData1)", CQExp.EQ, eventdate1);
        //cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
        cqExp.filed("".equals(eventdate40) ? null : "Rtrim(EventData40)", CQExp.EQ, eventdate40);
        //cqExp.orderByAsc("EventDate1");
        //cqExp.orderByAsc("EventDate2");
        logger.info("冲压车间统计【CYCJ_tabStopLine】信息：" + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("tabStopLine", con).get("TOTAL_NUM")));
    }

}
