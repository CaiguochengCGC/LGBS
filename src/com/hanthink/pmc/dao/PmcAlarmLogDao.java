/**
 * 
 *
 * @File name:  PmcDateImportDao.java 
 * @Create on:  2014-03-16 13:41:557
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
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.DAO;
import cn.boho.framework.po.Pager;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.util.DictConstants;

/**
 * 【故障事件记录】的Dao操作类
 * 
 */
public class PmcAlarmLogDao extends DAO {
    /**
     * 查询所有【故障事件记录】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-16
     * @author taofl
     * 
     */
    public static ComboPager queryAlarmLogImport(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String data7, String data3,
            Pager pager,String banci,String second,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select rtrim(b.DATA4) AS PRODUCTIONLINENAME,a.GENERATION_TIME,a.TIMESTAMP,b.ALARM_MESSAGE,a.RESOURCE,rtrim(b.DATA1) AS DATA1,rtrim(b.DATA2) AS DATA2,rtrim(b.DATA3) AS DATA3,rtrim(b.DATA7) AS DATA7,a.banci as BANCI, ");
        cqExp.select(" convert(decimal(10,2),datediff(second,convert(datetime,a.GENERATION_TIME,20),convert(datetime,a.TIMESTAMP,20))/1.0) WORKTIME ");
        cqExp.select(" from ALARM_LOG a , tabRefer b ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.andStr("a.alarm_id = b.alarmid");
        cqExp.filed(null == startEffectTime ? null : "a.GENERATION_TIME", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "a.GENERATION_TIME", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "B.DATA1", CQExp.EQ, productionline);
        cqExp.filed("".equals(data7) ? null : "b.DATA7", CQExp.EQ, data7);
        cqExp.filed("".equals(data3) ? null : "b.DATA2", CQExp.EQ,data3);
        cqExp.filed("".equals(banci)? null : "a.banci", CQExp.EQ, banci);
        cqExp.filed("".equals(second)? null : "CONVERT (DECIMAL (10, 2),datediff(SECOND,CONVERT (datetime,a.GENERATION_TIME,20),CONVERT (datetime, a. TIMESTAMP, 20)) / 1.0)", CQExp.GREATER, second);
        cqExp.orderByDesc("a.GENERATION_TIME");
        logger.debug(" 故障事件记录查询:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapComboPager("ALARM_LOG", pager, con);

    }
    public static ComboPager queryStationStopLog(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String station,
            Pager pager,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select(" SELECT EventDate,EventDate1,EventData3,EventData5 as EventData4,EventData7,BANCI,EventData1 FROM tabStopSta ");
        cqExp.where();
        
        cqExp.filed(null == startEffectTime ? null : "EventDate", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "EventDate", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "EventData8", CQExp.EQ, productionline);
        cqExp.filed("".equals(station) ? null : "EventData4", CQExp.LIKE, "%"+station+ "%");
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.orderByDesc("EventDate");
        logger.debug(" 工位停机记录查询:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapComboPager("STA_STOP_RECORD", pager, con);

    }
    public static List exportStationStopLog(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String station,
                                                    Pager pager, String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select(" SELECT EventDate,EventDate1,EventData3,('BRL'+substring( EventData4,2,4)) as EventData4,EventData7,BANCI,EventData1 FROM tabStopSta ");
        cqExp.where();

        cqExp.filed(null == startEffectTime ? null : "EventDate", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "EventDate", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "EventData8", CQExp.EQ, productionline);
        cqExp.filed("".equals(station) ? null : "EventData4", CQExp.LIKE, "%"+station+ "%");
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.orderByDesc("EventDate");
        logger.debug(" 导出工位停机记录查询:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("STA_STOP_RECORD", con);

    }
    public static ComboPager queryStopAndAlarm(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String station,
            Pager pager,String banci,String alarmMachine) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select(" SELECT * FROM PMC_PP_STOP_RECORD ");
        cqExp.where();
        
        cqExp.filed(null == startEffectTime ? null : "STOP_START_TIME", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "STOP_START_TIME", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "LINECODE", CQExp.EQ, productionline);
        cqExp.filed("".equals(station) ? null : "STATION", CQExp.LIKE, "%"+station+ "%");
        cqExp.filed("".equals(banci)? null : "SHIFT", CQExp.EQ, banci);
        cqExp.filed("".equals(alarmMachine)? null : "ALARM_MACHINE", CQExp.EQ, alarmMachine);
        cqExp.orderByDesc("STOP_START_TIME");
        logger.debug(" 工位停机记录查询:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapComboPager("STA_STOP_RECORD", pager, con);

    }
    /**
     * ALARM_LOG故障事件记录导出
     * @param con
     * @param startEffectTime 开始时间
     * @param endEffectTime		计算实际
     * @param productionline	工段
     * @param data3	设备
     * @return
     * @throws Exception
     */
    public static List queryAlarmLogImportExport(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String data7, String data3,String banci,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select rtrim(b.DATA4) AS PRODUCTIONLINENAME,a.BANCI as BANCI,a.GENERATION_TIME,a.TIMESTAMP,b.ALARM_MESSAGE,a.RESOURCE,rtrim(b.DATA1) AS DATA1,rtrim(b.DATA2) AS DATA2,rtrim(b.DATA3) AS DATA3,rtrim(b.DATA7) AS DATA7,a.banci as BANCI, ");
        cqExp.select(" convert(decimal(10,2),datediff(second,convert(datetime,a.GENERATION_TIME,20),convert(datetime,a.TIMESTAMP,20))/1.0) WORKTIME ");
        //cqExp.select(" from ALARM_LOG a , tabRefer b, ( select distinct PRODUCTIONLINE,PRODUCTIONLINENAME  from PMC_PP_STATION ) c ");
        cqExp.select(" from ALARM_LOG a , tabRefer b ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.andStr("a.alarm_id = b.alarmid");
        //cqExp.andStr("b.DATA1 = c.PRODUCTIONLINE");
        cqExp.filed(null == startEffectTime ? null : "a.GENERATION_TIME", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "a.GENERATION_TIME", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "B.DATA1", CQExp.EQ, productionline);
        cqExp.filed("".equals(data7) ? null : "b.DATA7", CQExp.EQ, data7);
        cqExp.filed("".equals(data3) ? null : "b.DATA3", CQExp.LIKE, "%" + data3 + "%");
        cqExp.filed("".equals(banci)? null : "a.BANCI", CQExp.EQ, banci);
        logger.debug("ALARM_LOG故障事件记录导出:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ALARM_LOG", con);

    }
    
    public static List queryAlarmLog(Connection con, Date startTime, Date endTime, String productionline, String data2, String data3)throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select  c.PRODUCTIONLINENAME,CONVERT(VARCHAR(19),a.GENERATION_TIME,20) GENERATION_TIME,CONVERT(VARCHAR(19),a.TIMESTAMP,20) TIMESTAMP,b.ALARM_MESSAGE,a.RESOURCE,b.DATA1,b.DATA2,b.DATA3, ");
        cqExp.select(" convert(decimal(10,2),datediff(second,convert(datetime,a.GENERATION_TIME,20),convert(datetime,a.TIMESTAMP,20))/1.0) WORKTIME ");
        cqExp.select(" from ALARM_LOG a , tabRefer b, ( select distinct PRODUCTIONLINE,PRODUCTIONLINENAME  from PMC_PP_STATION ) c ");
        cqExp.where();
        
        cqExp.andStr("a.alarm_id = b.alarmid");
        cqExp.andStr("b.DATA1 = c.PRODUCTIONLINE");
        cqExp.filed(null == startTime ? null : "a.GENERATION_TIME", CQExp.GREATER_EQ, DateUtils.format(startTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endTime ? null : "a.GENERATION_TIME", CQExp.LESS, DateUtils.format(endTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "c.PRODUCTIONLINENAME", CQExp.EQ, productionline);
        cqExp.filed("".equals(data2) ? null : "b.DATA2", CQExp.EQ, data2);
        cqExp.filed("".equals(data3) ? null : "b.DATA3", CQExp.LIKE, "%" + data3 + "%");
        
        logger.debug(" ALARM_LOG故障事件记录查询:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ALARM_LOG", con);

    }
    
    public static int countAlarmLog(Connection con, Date startTime, Date endTime, String productionline, String data2, String data3) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from ALARM_LOG A , tabRefer B , ( select distinct PRODUCTIONLINE,PRODUCTIONLINENAME  from PMC_PP_STATION ) C");
        cqExp.where();
        cqExp.andStr("a.alarm_id = b.alarmid");
        cqExp.andStr("b.DATA1 = c.PRODUCTIONLINE");
        cqExp.filed(startTime == null ? null : "GENERATION_TIME", CQExp.GREATER_EQ, DateUtils.format(startTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(endTime == null ? null : "GENERATION_TIME", CQExp.LESS, DateUtils.format(endTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "DATA1",CQExp.EQ, productionline);
        cqExp.filed("".equals(data2) ? null : "DATA2",CQExp.EQ, data2);
        cqExp.filed("".equals(data3) ? null : "DATA3", "like", "%" + data3 + "%");
        

        logger.debug(" ALARM_LOG故障事件记录查询:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("ALARM_LOG", con).get("TOTAL_NUM")));
    }
    
    public static ComboPager queryPpAlarmLogImport(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String data7, String data3,
            Pager pager,String banci,String attrbute,String whereSQL) throws Exception {
    	CQExp cqExp = CQExp.instance();
    	cqExp.select("SELECT * FROM PMC_PP_ALARM");
    	
    	cqExp.where();
    	cqExp.select(whereSQL);
        cqExp.select(" AND BANCI!=0");
    	cqExp.filed(null == startEffectTime ? null : "STOPSTARTTIME", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "STOPSTARTTIME", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "PRODUCTION", CQExp.EQ, productionline);
        cqExp.filed("".equals(data7) ? null : "STATION", CQExp.EQ, data7);
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.filed("".equals(attrbute)? null : "ATTRBUTE", CQExp.EQ, attrbute);
//        cqExp.filed("".equals(data3) ? null : "b.DATA3", CQExp.LIKE, "%" + data3 + "%");
        logger.debug(" 停线与事故记录查询:  " + cqExp.getSql());

        cqExp.orderByDesc("STOPSTARTTIME,ALARMSTARTTIME");
    	
    	return cqExp.getDynaBeanMapComboPager("ALARM_LOG", pager, con);
    }
    public static ComboPager queryRealTime(Connection con,
            Pager pager,String whereSQL ) throws Exception {
    	CQExp cqExp = CQExp.instance();
        cqExp.select("select PlanProduct,ActProduct,RuningTime,Convert(decimal(18,1),StopLineTime) as StopLineTime, StopLineCount,Shift,LineCode,LineName,Convert(varchar,WorkDate,23) as WorkDate,convert(varchar,WorkStartTime,20) as WorkStartTime,convert(VARCHAR,WorkEndTime,20) as WorkEndTime,PlanWorkTime,RestTime,RestCount from PMC_AGGREGATE_DATA_LIVEUPDATE WHERE 1=1 "+whereSQL+"");
        logger.debug(" 实时报表查询:  " + cqExp.getSql());

    	return cqExp.getDynaBeanMapComboPager("REALTIME", pager, con);
    }
    public static List exportRealTime(Connection con, Date startEffectTime, Date endEffectTime, String productionline ) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PlanProduct,ActProduct,RuningTime,Convert(decimal(18,1),StopLineTime) as StopLineTime, StopLineCount,Shift,LineCode,LineName,Convert(varchar,WorkDate,23) as WorkDate,convert(varchar,WorkStartTime,20) as WorkStartTime,convert(VARCHAR,WorkEndTime,20) as WorkEndTime,PlanWorkTime,RestTime,RestCount from PMC_AGGREGATE_DATA_LIVEUPDATE ");

        logger.debug(" 实时报表查询:  " + cqExp.getSql());
//        cqExp.orderByDesc("WorkDate");

        return cqExp.getDynaBeanMapList("REALTIME",con);
    }
    public static List ExprotPpAlarmLogImport(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String data7, String data3,String banci,String whereSQL) throws Exception {
    	CQExp cqExp = CQExp.instance();
    	cqExp.select("SELECT * FROM PMC_PP_ALARM");
    	
    	cqExp.where();
    	cqExp.select(whereSQL);
    	cqExp.filed(null == startEffectTime ? null : "STOPSTARTTIME", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "STOPSTARTTIME", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "[PRODUCTION]", CQExp.EQ, productionline);
        cqExp.filed("".equals(data7) ? null : "STATION", CQExp.EQ, data7);
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
//        cqExp.filed("".equals(data3) ? null : "b.DATA3", CQExp.LIKE, "%" + data3 + "%");
    	
        cqExp.orderByDesc("STOPSTARTTIME,ALARMSTARTTIME");
//        cqExp.orderByDesc("ALARMSTARTTIME");
    	return cqExp.getDynaBeanMapList("ALARM_LOG", con);
    }

    /**
     * ALARM_LOG故障事件记录导出
     * @param con
     * @param startEffectTime 开始时间
     * @param endEffectTime		计算实际
     * @param productionline	工段
     * @param data3	设备
     * @return
     * @throws Exception
     */
    public static List queryStopAlarmLogImportExport(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String data7, String data3,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select(" select * from LGBS.dbo.PMC_PP_STOP_RECORD");
        cqExp.where();
        cqExp.filed(null == startEffectTime ? null : "STOP_START_TIME", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "STOP_START_TIME", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "LINECODE", CQExp.EQ, productionline);
        cqExp.filed("".equals(data7) ? null : "STATION", CQExp.EQ, data7);
        cqExp.filed("".equals(banci)? null : "SHIFT", CQExp.EQ, banci);
        logger.debug("STATION and ALARM 导出:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_STOP_RECORD", con);

    }
    
}
