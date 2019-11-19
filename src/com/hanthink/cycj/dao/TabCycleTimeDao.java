package com.hanthink.cycj.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.DAO;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.po.Pager;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

public class TabCycleTimeDao extends DAO {

    /**
     * 条件查询节拍报表
     * 
     * @author luoshw
     * @param con
     * @param startDate
     * @param endDate
     * @param EventData13
     * @param EventData1
     * @param EventData2
     * @return
     * @throws Exception
     */
    public static ComboPager queryTabCycleTime(Connection con, Date workDate, String EventData13, String EventData1, String EventData2, Pager pager) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select * from CYCJ_tabCycleTime");
        cqExp.where();

        cqExp.filed(null == workDate ? null : "convert(varchar(10),EventDate,120)", CQExp.EQ, Tools.getTimestamp(workDate));
        cqExp.filed("".equals(EventData13) ? null : "Rtrim(EventData16)", CQExp.EQ, EventData13);
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
        //cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
        cqExp.orderByDesc("EVENTDATE");
        cqExp.orderByAsc("EventData16");
        logger.debug("冲压车间系统节拍: " + cqExp.getSql());
        return cqExp.getDynaBeanMapComboPager("TAB_CYCLE_TIME", pager, con);

    }
    
    public static int countTabCycleTime(Connection con, Date date, String EventData13, String EventData1) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(1) as  TOTAL_NUM from CYCJ_tabCycleTime");
        cqExp.where();

        cqExp.filed(null == date ? null : "convert(date,EventData,120)", CQExp.EQ, Tools.getTimestamp(date));
        cqExp.filed("".equals(EventData13) ? null : "Rtrim(EventData14)", CQExp.EQ, EventData13);
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
        cqExp.group("EventDate,EventType,EventDate1,EventDate2,EventDate3,EventDate4,EventDate5,EventDate6,EventDate7,EventDate8,EventDate9,EventDate10,EventDate11,EventDate12,EventDate13,EventDate14,EventDate15");
        cqExp.orderByAsc("EventDate14");
        logger.debug("冲压车间工位总览:tabCycleTime " + cqExp.getSql());
        logger.debug("_________________________-xsssssssss)" + cqExp.getDynaBeanMap("tabCycleTime",con));
        return Integer.parseInt(String.valueOf(0));
        
        

    }
    
    
    public static List queryTabCycleTimeBeat(Connection con, Date date, String EventData13, String EventData1, String EventData2) throws Exception {
        CQExp cqExp = CQExp.instance();
        /*cqExp.select("select * from tabCycleTime");
        cqExp.where();

        cqExp.filed(null == date ? null : "convert(date,EventData,120)", CQExp.EQ, Tools.getTimestamp(date));
        cqExp.filed("".equals(EventData13) ? null : "Rtrim(EventDate14)", CQExp.EQ, EventData13);
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(EventDate1)", CQExp.EQ, EventData1);
        cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
        cqExp.orderByAsc("EventDate14");*/
        
        cqExp.select("select * from CYCJ_tabCycleTime");
        cqExp.where();

        cqExp.filed(null == date ? null : "convert(varchar(10),EventDate,120)", CQExp.EQ, Tools.getTimestamp(date));
        cqExp.filed("".equals(EventData13) ? null : "Rtrim(EventData16)", CQExp.EQ, EventData13);
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
        //cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
        cqExp.orderByAsc("EventData16");
        
        logger.debug("冲压车间系统节拍: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("tabCycleTime", con);

    }
    
    
    public static int countTabCycleTimeBeat(Connection con, Date date, String EventData13, String EventData1,String EventData2) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from CYCJ_tabCycleTime");
        cqExp.where();

        cqExp.filed(null == date ? null : "convert(varchar(10),EventDate,120)", CQExp.EQ, Tools.getTimestamp(date));
        cqExp.filed("".equals(EventData13) ? null : "Rtrim(EventData16)", CQExp.EQ, EventData13);
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
        //cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
        cqExp.orderByAsc("EventData16");
        cqExp.group("EventDate,EventData1,EventData2,EventData3,EventData4,EventData5,EventData6,EventData7,EventData8,EventData9,EventData10,EventData11,EventData12,EventData13,EventData14,EventData15,EventData16,EventData17");
        
        logger.debug("冲压车间节拍报表: " + cqExp.getSql());
        //return Tools.getInt(String.valueOf(cqExp.getDynaBeanMap("tabCycleTime", con).get("TOTAL_NUM")),0);
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("tabCycleTime", con).get("TOTAL_NUM")));

    }
    
    public static int countTabCycleTimeBeatRandom(Connection con, Date date, String EventData13, String EventData1) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from CYCJ_tabCycleTime");
        cqExp.where();

        cqExp.filed(null == date ? null : "convert(varchar(10),EventDate,120)", CQExp.EQ, Tools.getTimestamp(date));
        cqExp.filed("".equals(EventData13) ? null : "Rtrim(EventData16)", CQExp.EQ, EventData13);
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
        //cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
        //cqExp.orderByAsc("EventData16");
        //cqExp.group("EventDate,EventData1,EventData2,EventData3,EventData4,EventData5,EventData6,EventData7,EventData8,EventData9,EventData10,EventData11,EventData12,EventData13,EventData14,EventData15,EventData16,EventData17");
        
        logger.debug("冲压车间节拍报表: " + cqExp.getSql());
        //return Tools.getInt(String.valueOf(cqExp.getDynaBeanMap("tabCycleTime", con).get("TOTAL_NUM")),0);
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("tabCycleTime", con).get("TOTAL_NUM")));

    }

    /**
     * 条件随机查询节拍报表
     * 
     * @author luoshw
     * @param con
     * @param date
     * @param EventData13
     * @param EventData1
     * @return
     * @throws Exception
     */
    public static List queryTabCycleTimeByRand(Connection con, Date date, String EventData13, String EventData1) throws Exception {
        CQExp cqExp1 = CQExp.instance();
        cqExp1.select("select distinct EventData1 from CYCJ_tabCycleTime");
        cqExp1.where();
        cqExp1.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
        cqExp1.filed("".equals(EventData13) ? null : "Rtrim(EventData16)", CQExp.EQ, EventData13);
        cqExp1.filed(null == date ? null : "convert(varchar(10),EventDate,120)", CQExp.EQ, Tools.getTimestamp(date));
        
        List list = cqExp1.getDynaBeanMapList("tabCycleTime", con);
        List lst = new LinkedList();
        for (Object object : list) {
            String station = String.valueOf(((DynaBeanMap) object).get("EventData1")).trim();
            CQExp cqExp = CQExp.instance();
            int countnum = countTabCycleTimeBeatRandom(con,date,EventData13,station);
            if(countnum > 0){
            	Random ran = new Random();
            	int r = ran.nextInt(countnum)+1;
            	String sql = "";
            	sql = "SELECT * FROM (select row_number() OVER(ORDER BY EventData1 ASC) AS ROWNUM, * from CYCJ_tabCycleTime where 1=1 ";
            	if (null != date) {
            		sql += " and convert(varchar(10),EventDate,120) = '"+Tools.getStr(Tools.getTimestamp(date)).substring(0, 10)+"'";
            	}
            	if (!"".equals(EventData13)) {
            		sql += " and Rtrim(EventData16) = '" + EventData13 + "'";
            	}
            	sql += " and Rtrim(EventData1) = '"+station+"'";
            	sql += ") A where a.ROWNUM = " + r;
            	sql += " order by a.EventData16";
            	/*cqExp.select("select top 1 * from tabCycleTime");
            cqExp.where();

            cqExp.filed(null == date ? null : "convert(varchar(10),EventDate,120)", CQExp.EQ, Tools.getTimestamp(date));
            cqExp.filed("".equals(EventData13) ? null : "Rtrim(EventData16)", CQExp.EQ, EventData13);
            cqExp.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
            cqExp.filed("Rtrim(EventData1)", CQExp.EQ, station);

            cqExp.orderByAsc("EventData16");*/
            	
            	cqExp.select(sql);
            	
            	logger.debug("冲压车间系统随机查询节拍,工位：[ " + station + "]--" + cqExp.getSql());
            	DynaBeanMap beanMap = cqExp.getDynaBeanMap("tabCycleTime", con);
            	if (beanMap != null)
            		lst.add(beanMap);
            	}
            }

        return lst;

    }
    

}
