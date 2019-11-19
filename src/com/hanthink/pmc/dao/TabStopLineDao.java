package com.hanthink.pmc.dao;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.DAO;
import cn.boho.framework.po.Pager;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

public class TabStopLineDao extends DAO {

    /**
     * 生成停机报表
     * 
     * @author taofl
     * @create_date 2014-3-29 下午08:41:12
     * @param con
     * @throws Exception
     */
    public static ComboPager queryTabStopLine(Connection con, Date startDate, Date endDate, String EventData1, String EventData2, String EventData46, Pager pager,String banci,String stopReson,String EventData3,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT ID, EventData46,EventData40,EventDate1,EventDate2,EventData1,EventData50,CASE WHEN  ltrim(rtrim( EventData51 ))  != '' THEN  LEFT(EventData51,LEN(EventData51)-1) END AS  EventData51,EventData53,CONVERT(DATETIME,EventData54) as EventData54,BANCI,");
        cqExp.select("datediff(ss, EventDate1,  EventDate2) StopTimeTotal, ");
        cqExp.select("CASE WHEN ltrim(rtrim(EventData3)) != '' THEN EventData3 ");
        cqExp.select(" WHEN ltrim(rtrim(EventData4)) != '' THEN EventData4");
        cqExp.select(" WHEN ltrim(rtrim(EventData5)) != '' THEN EventData5");
        cqExp.select(" WHEN ltrim(rtrim(EventData6)) != '' THEN EventData6");
        cqExp.select(" WHEN ltrim(rtrim(EventData7)) != '' THEN EventData7");
        cqExp.select(" END AS StopReson from tabStopline");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.select(" and BANCI!=0");
        cqExp.filed(null == startDate ? null : "EventDate1", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endDate ? null : "EventDate1", CQExp.LESS_EQ, DateUtils.format(endDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
        //cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
        cqExp.filed("".equals(EventData46) ? null : "Rtrim(EventData40)", CQExp.EQ, EventData46);
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.filed("".equals(EventData3)? null : "EventData3", CQExp.EQ, EventData3);
        cqExp.orderByDesc("EventDate1");
        cqExp.orderByDesc("EventDate2");

        logger.debug(" 停线分页查询: " + cqExp.getSql());
        return cqExp.getDynaBeanMapComboPager("tabStopLine", pager, con);

    }

    public static List queryTabStopLinePrespon(Connection con, String ppdate,String banci) throws Exception{
    	CQExp cqExp = CQExp.instance();
       	//班次合并显示
    	if("".equals(banci)){
    		cqExp.select("select * from");
        	cqExp.select(" (select  SUBSTRING(CONVERT(varchar(100), EventDate1, 23),9,10) tday,EventData51,");
        	cqExp.select(" CONVERT(DECIMAL(18,2),SUM(datediff(ss, EventDate1,  EventDate2))/60.0) stoptimetotal from tabStopline");
        	cqExp.select(" where eventData51 is not null AND eventData51 != ''");
        	cqExp.select(" AND CONVERT(VARCHAR(7),EventDate1,23) = '"+ppdate+"'");
        	cqExp.select(" group by SUBSTRING(CONVERT(varchar(100), EventDate1, 23),9,10),EventData51");
        	cqExp.select(" ) t");
        	cqExp.select(" pivot (sum(stoptimetotal) for tday in ([01],[02],[03],[04],[05],[06],[07],[08],[09],[10]");
        	cqExp.select(" ,[11],[12],[13],[14],[15],[16],[17],[18],[19],[20]");
        	cqExp.select(" ,[21],[22],[23],[24],[25],[26],[27],[28],[29],[30],[31])) as ourpivot");
        	logger.info("总装车间责任部门："+cqExp.getSql());
        	
        	return cqExp.getDynaBeanMapList("tabStopLine", con);
    	}
    	
    	
    	cqExp.select("select * from");
    	cqExp.select(" (select  SUBSTRING(CONVERT(varchar(100), EventDate1, 23),9,10) tday,EventData51,BANCI,");
    	cqExp.select(" CONVERT(DECIMAL(18,2),SUM(datediff(ss, EventDate1,  EventDate2))/60.0) stoptimetotal from tabStopline");
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
    	
    	logger.info("总装车间责任部门："+cqExp.getSql());
    	
    	return cqExp.getDynaBeanMapList("tabStopLine", con);
    }
    
    public static List queryTabStopLine1(Connection con, java.util.Date startDate, java.util.Date endDate, java.lang.String eventdate46, java.lang.String eventdate1,
            java.lang.String eventdate3,String banci,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT ID, EventData46,EventData40,EventDate1,EventDate2,EventData1,EventData50,CASE WHEN  ltrim(rtrim( EventData51 ))  != '' THEN  LEFT(EventData51,LEN(EventData51)-1) END AS  EventData51,EventData53,CONVERT(DATETIME,EventData54) as EventData54,");
        cqExp.select("datediff(ss, EventDate1,  EventDate2) StopTimeTotal,BANCI, ");
        cqExp.select("CASE WHEN ltrim(rtrim(EventData3)) != '' THEN EventData3 ");
        cqExp.select(" WHEN ltrim(rtrim(EventData4)) != '' THEN EventData4");
        cqExp.select(" WHEN ltrim(rtrim(EventData5)) != '' THEN EventData5");
        cqExp.select(" WHEN ltrim(rtrim(EventData6)) != '' THEN EventData6");
        cqExp.select(" WHEN ltrim(rtrim(EventData7)) != '' THEN EventData7");
        cqExp.select(" END AS StopReson from tabStopline");
        cqExp.where();
cqExp.select(whereSQL);
        cqExp.filed(null == startDate ? null : "EventDate1", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endDate ? null : "EventDate1", CQExp.LESS_EQ, DateUtils.format(endDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(eventdate3)? null : "EventData3", CQExp.EQ, eventdate3);
        cqExp.filed("".equals(eventdate46) ? null : "Rtrim(eventdata40)", CQExp.EQ, eventdate46);
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.orderByDesc("EventDate1");
        cqExp.orderByDesc("EventDate2");

        logger.info("停线查询信息：" + cqExp.getSql());

        List list = cqExp.getDynaBeanMapList("tabStopLine", con);

        return list;
    }

    public static int countTabStopLine(Connection con, java.util.Date startDate, java.util.Date endDate, java.lang.String eventdate46, java.lang.String eventdate1,
            java.lang.String eventdate3,String whereSQL) throws Exception {

        CQExp cqExp = CQExp.instance();

        cqExp.select("SELECT count(*) as TOTAL_NUM from tabStopline");
        cqExp.where();
cqExp.select(whereSQL);
        cqExp.filed(null == startDate ? null : "EventDate1", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endDate ? null : "EventDate1", CQExp.LESS_EQ, DateUtils.format(endDate, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(eventdate1) ? null : "Rtrim(EventData1)", CQExp.EQ, eventdate1);
        cqExp.filed("".equals(eventdate3)? null : "EventData3", CQExp.EQ, eventdate3);
        cqExp.filed("".equals(eventdate46) ? null : "Rtrim(EventData40)", CQExp.EQ, eventdate46);
        logger.info("统计【tabStopLine】信息：" + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("tabStopLine", con).get("TOTAL_NUM")));
    }



    /**
     * 停线详情
     *
     * @author  hxt
     * @create_date 2019-08-26  hxt
     * @param con
     * @throws Exception
     */
    public static List queryStopDetail(Connection con, Date startTime, Date endTime, String lineCode, String stopType, String shift,String whereSQL,String queryTypeT) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select '"+queryTypeT+"'AS TYPE,convert(nvarchar(23),EventDate1,23) as WORKDATE,sum(datediff(second,EventDate1,EventDate2)) as STOPTIME,");
        cqExp.select(" EventData40 as LINECODE,EventData46 as LINENAME, EventData3 as STOPTYPE,BANCI as SHIFT");
        cqExp.select("  from LGBS.dbo.tabStopline");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == startTime ? null : "convert(nvarchar(23),EventDate1,23)", CQExp.GREATER_EQ,Tools.getTimestamp(startTime));
        cqExp.filed(null == endTime ? null : "convert(nvarchar(23),EventDate1,23)", CQExp.LESS_EQ, Tools.getTimestamp(endTime));
        cqExp.filed("".equals(lineCode) ? null : "Rtrim(EventData40)", CQExp.EQ, lineCode);
        cqExp.filed("".equals(stopType) ? null : "Rtrim(EventData3)", CQExp.EQ, stopType);
        cqExp.filed("".equals(shift)? null : "BANCI", CQExp.EQ, shift);
        cqExp.select(" group by convert(nvarchar(23),EventDate1,23),EventData40,EventData46,BANCI,EventData3");
        cqExp.orderByDesc("convert(nvarchar(23),EventDate1,23)");
        logger.debug(" 停线详情: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_STOP_RESON_PLANEL", con);

    }
    /**
     * 停线详情
     *
     * @author  hxt
     * @create_date 2019-08-26
     * @param con
     * @throws Exception
     */
    public static List queryStopDetail1(Connection con, Date startTime, Date endTime, String lineCode, String stopType, String shift,String whereSQL,String queryTypeT) throws Exception {
        CQExp cqExp = CQExp.instance();
        String filed = "";
        String filedGroup = "";

        if(!"".equals(lineCode)){
            filed += ",EventData46 as LINENAME";
            filedGroup += ",EventData46";
        }else{
            filed += ",'全部' as LINENAME";
        }
        if(!"".equals(stopType)){
            filed += ",EventData3 as STOPTYPE";
            filedGroup += ",EventData3";
        }else{
            filed += ",'全部' as STOPTYPE";
        }
        if(!"".equals(shift)){
            filed += ",BANCI as SHIFT";
            filedGroup += ",BANCI";
        }else{
            filed += ",'全部' as SHIFT";
        }



        cqExp.select("select  '"+queryTypeT+"'AS TYPE,convert(nvarchar(23),EventDate1,23) as WORKDATE,sum(datediff(second,EventDate1,EventDate2)) as STOPTIME" +filed);
        cqExp.select(" from LGBS.dbo.tabStopline");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == startTime ? null : "EventDate1", CQExp.GREATER_EQ, DateUtils.format(startTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endTime ? null : "EventDate1",CQExp.LESS_EQ, DateUtils.format(endTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(lineCode) ? null : "Rtrim(EventData40)", CQExp.EQ, lineCode);
        cqExp.filed("".equals(stopType) ? null : "Rtrim(EventData3)", CQExp.EQ, stopType);
        cqExp.filed("".equals(shift)? null : "BANCI", CQExp.EQ, shift);
        cqExp.select(" group by convert(nvarchar(23),EventDate1,23)" +filedGroup);
        cqExp.orderByDesc("convert(nvarchar(23),EventDate1,23)");
        logger.debug(" 停线详情: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_STOP_RESON_PLANEL", con);
    }
//    工段停线频次分析
    public static List queryStopDetail1Cpunt(Connection con, Date startTime, Date endTime, String lineCode, String stopType, String shift,String whereSQL,String queryTypeT) throws Exception {
        CQExp cqExp = CQExp.instance();
        String filed = "";
        String filedGroup = "";

        if(!"".equals(lineCode)){
            filed += ",EventData46 as LINENAME";
            filedGroup += ",EventData46";
        }else{
            filed += ",'全部' as LINENAME";
        }
        if(!"".equals(stopType)){
            filed += ",EventData3 as STOPTYPE";
            filedGroup += ",EventData3";
        }else{
            filed += ",'全部' as STOPTYPE";
        }
        if(!"".equals(shift)){
            filed += ",BANCI as SHIFT";
            filedGroup += ",BANCI";
        }else{
            filed += ",'全部' as SHIFT";
        }



        cqExp.select("select '"+queryTypeT+"' AS TYPE,convert(nvarchar(23),EventDate1,23) as WORKDATE,count(*) as STOPCOUNT" +filed);
        cqExp.select(" from LGBS.dbo.tabStopline");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == startTime ? null : "EventDate1", CQExp.GREATER_EQ, DateUtils.format(startTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endTime ? null : "EventDate1",CQExp.LESS_EQ, DateUtils.format(endTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(lineCode) ? null : "Rtrim(EventData40)", CQExp.EQ, lineCode);
        cqExp.filed("".equals(stopType) ? null : "Rtrim(EventData3)", CQExp.EQ, stopType);
        cqExp.filed("".equals(shift)? null : "BANCI", CQExp.EQ, shift);
        cqExp.select(" group by convert(nvarchar(23),EventDate1,23)" +filedGroup);
        cqExp.orderByDesc("convert(nvarchar(23),EventDate1,23)");
        logger.debug(" 停线详情: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_STOP_RESON_PLANEL", con);
    }
    /**
     * 停线次数详情
     *
     * @author  hxt
     * @create_date 2019-08-26  hxt
     * @param con
     * @throws Exception
     */
    public static List queryStopCountDetail(Connection con, Date startTime, Date endTime, String lineCode, String stopType, String shift,String station,String whereSQL,String queryTypeT) throws Exception {

        CQExp cqExp = CQExp.instance();

        String filed = "";
        String filedGroup = "";

        if(!"".equals(lineCode)){
            filed += ",EVENTDATA7 as LINENAME";
            filedGroup += ",EVENTDATA7";
        }else{
            filed += ",'全部' as LINENAME";
        }
        if(!"".equals(stopType)){
            filed += ",EventData3 as STOPRESON";
            filedGroup += ",EventData3";
        }else{
            filed += ",'全部' as STOPRESON";
        }
        if(!"".equals(station)){
            filed += ",EventData5 as STATION ";
            filedGroup += ",EventData5";
        }else{
            filed += ",'全部' as STATION";
        }
        if(!"".equals(shift)){
            filed += ",BANCI as SHIFT";
            filedGroup += ",BANCI";
        }else{
            filed += ",'全部' as SHIFT";
        }


        cqExp.select(" select '"+queryTypeT+"' AS TYPE,count(*)  as STOPCOUNT,convert(nvarchar(23),WorkDate,23) as WORKDATE,sum(EventData1) as STOPTIME "+filed);
        cqExp.select("  from  LGBS.dbo.TABSTOPSTA ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == startTime ? null : "convert(nvarchar(23),WorkDate,23)", CQExp.GREATER_EQ,Tools.getTimestamp(startTime));
        cqExp.filed(null == endTime ? null : "convert(nvarchar(23),WorkDate,23)", CQExp.LESS_EQ, Tools.getTimestamp(endTime));
        cqExp.filed("".equals(lineCode) ? null : "Rtrim([EventData8])", CQExp.EQ, lineCode);
        cqExp.filed("".equals(stopType) ? null : "Rtrim([EventData3])", CQExp.EQ, stopType);
        cqExp.filed("".equals(station) ? null : "Rtrim([EventData5])", CQExp.EQ, station);
        cqExp.filed("".equals(shift)? null : "[BANCI]", CQExp.EQ, shift);
        cqExp.select(" group by WorkDate" +filedGroup);
        cqExp.orderByDesc("WORKDATE");
        logger.debug(" StopCount Detail: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_STOP_RESON_PLANEL", con);

    }


    /**
     * 产量详情
     *
     * @author  hxt
     * @create_date 2019-08-26  hxt
     * @param con
     * @throws Exception
     */

    public static List queryCarDetail(Connection con, Date startTime, Date endTime, String lineCode, String stopType, String shift,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        String filed = "";
        String filedGroup = "";

        if(!"".equals(lineCode)){
            filed += ",LineName as LINENAME";
            filedGroup += ",LineName";
        }else{
            filed += ",'全部' as LINENAME";
        }
        if(!"".equals(stopType)){
            filed += ",CarType as CARTYPE";
            filedGroup += ",CarType";
        }else{
            stopType="ALL";
            filed += ",'全部' as CARTYPE";
        }
        if(!"".equals(shift)){
            filed += ",SHIFT as SHIFT";
            filedGroup += ",SHIFT";
        }else{
            filed += ",'全部' as SHIFT";
        }

        cqExp.select("select  sum(ActProduct) as TOTALCOUNT,convert(nvarchar(23),PPDATE,23) as WORKDATE " +filed);
        cqExp.select(" from [LGBS].[dbo].[PMC_PP_PRODUCT]");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == startTime ? null : "convert(nvarchar(23),PPDATE,23)", CQExp.GREATER_EQ,Tools.getTimestamp(startTime));
        cqExp.filed(null == endTime ? null : "convert(nvarchar(23),PPDATE,23)", CQExp.LESS_EQ, Tools.getTimestamp(endTime));
        cqExp.filed("".equals(lineCode) ? null : "Rtrim(LineCode)", CQExp.EQ, lineCode);
        cqExp.filed("".equals(stopType) ? null : "Rtrim(CarType)", CQExp.EQ, stopType);
        cqExp.filed("".equals(shift)? null : "SHIFT", CQExp.EQ, shift);
        cqExp.select(" group by  PPDATE" + filedGroup);
        cqExp.orderByDesc("PPDATE");
        logger.debug(" 产量详情: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PRODUCT_PLANEL", con);

    }


    /**
     * 停线次数详情
     *
     * @author  hxt
     * @create_date 2019-08-26  hxt
     * @param con
     * @throws Exception
     */
    public static List expStopCountDetail(Connection con, Date startTime, Date endTime, String lineCode, String stopType, String shift,String station,String whereSQ0L) throws Exception {
        CQExp cqExp = CQExp.instance();

        String filed = "";
        String filedGroup = "";

        if(!"".equals(lineCode)){
            filed += ",EVENTDATA7 as LINENAME";
            filedGroup += ",EVENTDATA7";
        }else{
            filed += ",'全部' as LINENAME";
        }
        if(!"".equals(stopType)){
            filed += ",EventData3 as STOPRESON";
            filedGroup += ",EventData3";
        }else{
            filed += ",'全部' as STOPRESON";
        }
        if(!"".equals(station)){
            filed += ",EventData5 as STATION ";
            filedGroup += ",EventData5";
        }else{
            filed += ",'全部' as STATION";
        }
        if(!"".equals(shift)){
            filed += ",BANCI as SHIFT";
            filedGroup += ",BANCI";
        }else{
            filed += ",'全部' as SHIFT";
        }
        cqExp.select(" select count(*)  as STOPCOUNT,convert(nvarchar(23),WorkDate,23) as WORKDATE,sum(EventData1) as STOPTIME "+filed);
        cqExp.select("  from  LGBS.dbo.TABSTOPSTA ");
        cqExp.where();
        cqExp.select(whereSQ0L);
        cqExp.filed(null == startTime ? null : "convert(nvarchar(23),EventDate,23)", CQExp.GREATER_EQ,Tools.getTimestamp(startTime));
        cqExp.filed(null == endTime ? null : "convert(nvarchar(23),EventDate,23)", CQExp.LESS_EQ, Tools.getTimestamp(endTime));
        cqExp.filed("".equals(lineCode) ? null : "Rtrim([EventData8])", CQExp.EQ, lineCode);
        cqExp.filed("".equals(stopType) ? null : "Rtrim([EventData3])", CQExp.EQ, stopType);
        cqExp.filed("".equals(station) ? null : "Rtrim([EventData5])", CQExp.EQ, station);
        cqExp.filed("".equals(shift)? null : "[BANCI]", CQExp.EQ, shift);
        cqExp.select(" group by WorkDate" +filedGroup);
        cqExp.orderByDesc("WORKDATE");
        logger.debug(" export: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_STOP_RESON_PLANEL", con);

    }

    /**
     * 停线详情
     *
     * @author  hxt
     * @create_date 2019-08-26
     * @param con
     * @throws Exception
     */
    public static List exportqueryStopDetail1(Connection con, Date startTime, Date endTime, String lineCode, String stopType, String shift,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        String filed = "";
        String filedGroup = "";

        if(!"".equals(lineCode)){
            filed += ",EventData46 as LINENAME";
            filedGroup += ",EventData46";
        }else{
            filed += ",'全部' as LINENAME";
        }
        if(!"".equals(stopType)){
            filed += ",EventData3 as STOPTYPE";
            filedGroup += ",EventData3";
        }else{
            filed += ",'全部' as STOPTYPE";
        }
        if(!"".equals(shift)){
            filed += ",BANCI as SHIFT";
            filedGroup += ",BANCI";
        }else{
            filed += ",'全部' as SHIFT";
        }

        cqExp.select("select convert(nvarchar(23),EventDate1,23) as WORKDATE,sum(datediff(second,EventDate1,EventDate2)) as STOPTIME" +filed);
        cqExp.select(" from LGBS.dbo.tabStopline");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == startTime ? null : "convert(nvarchar(23),EventDate1,23)", CQExp.GREATER_EQ, Tools.getTimestamp(startTime));
        cqExp.filed(null == endTime ? null : "convert(nvarchar(23),EventDate1,23)", CQExp.LESS_EQ,Tools.getTimestamp(endTime));
        cqExp.filed("".equals(lineCode) ? null : "Rtrim(EventData40)", CQExp.EQ, lineCode);
        cqExp.filed("".equals(stopType) ? null : "Rtrim(EventData3)", CQExp.EQ, stopType);
        cqExp.filed("".equals(shift)? null : "BANCI", CQExp.EQ, shift);
        cqExp.select(" group by convert(nvarchar(23),EventDate1,23)" +filedGroup);
        cqExp.orderByDesc("convert(nvarchar(23),EventDate1,23)");
        logger.debug(" 导出停线详情: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_STOP_RESON_PLANEL", con);
    }

    /**
     * 产量详情
     *
     * @author  hxt
     * @create_date 2019-08-26  hxt
     * @param con
     * @throws Exception
     */

    public static List exportCarDetail(Connection con, Date startTime, Date endTime, String lineCode, String stopType, String shift) throws Exception {
        CQExp cqExp = CQExp.instance();
        String filed = "";
        String filedGroup = "";

        if(!"".equals(lineCode)){
            filed += ",LineName as LINENAME";
            filedGroup += ",LineName";
        }else{
            filed += ",'全部' as LINENAME";
        }
        if(!"".equals(stopType)){
            filed += ",CarType as CARTYPE";
            filedGroup += ",CarType";
        }else{
            stopType="ALL";
            filed += ",'全部' as CARTYPE";
        }
        if(!"".equals(shift)){
            filed += ",SHIFT as SHIFT";
            filedGroup += ",SHIFT";
        }else{
            filed += ",'全部' as SHIFT";
        }

        cqExp.select("select  sum(ActProduct) as TOTALCOUNT,convert(nvarchar(23),PPDATE,23) as WORKDATE " +filed);
        cqExp.select(" from PMC_PP_PRODUCT");
        cqExp.where();
        cqExp.filed(null == startTime ? null : "convert(nvarchar(23),PPDATE,23)", CQExp.GREATER_EQ,Tools.getTimestamp(startTime));
        cqExp.filed(null == endTime ? null : "convert(nvarchar(23),PPDATE,23)", CQExp.LESS_EQ, Tools.getTimestamp(endTime));
        cqExp.filed("".equals(lineCode) ? null : "Rtrim(LineCode)", CQExp.EQ, lineCode);
        cqExp.filed("".equals(stopType) ? null : "Rtrim(CarType)", CQExp.EQ, stopType);
        cqExp.filed("".equals(shift)? null : "SHIFT", CQExp.EQ, shift);
        cqExp.select(" group by  PPDATE" + filedGroup);
        cqExp.orderByDesc("PPDATE");
        logger.debug(" 导出产量详情: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PRODUCT_PLANEL", con);

    }
}
