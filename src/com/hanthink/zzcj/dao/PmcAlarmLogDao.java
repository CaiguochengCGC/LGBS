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

package com.hanthink.zzcj.dao;

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
 * 【总装车间故障事件记录】的Dao操作类
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
    public static ComboPager queryAlarmLogImport(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String banci, String data3,
            Pager pager) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select rtrim(b.DATA4) AS PRODUCTIONLINENAME,a.BANCI,a.GENERATION_TIME,a.TIMESTAMP,b.ALARM_MESSAGE,a.RESOURCE,rtrim(b.DATA1) AS DATA1,rtrim(b.DATA2) AS DATA2,rtrim(b.DATA3) AS DATA3,rtrim(b.DATA7) AS DATA7, ");
        cqExp.select(" convert(decimal(10,2),datediff(second,convert(datetime,a.GENERATION_TIME,20),convert(datetime,a.TIMESTAMP,20))/1.0) WORKTIME ");
        //cqExp.select(" from ALARM_LOG a , tabRefer b, ( select distinct PRODUCTIONLINE,PRODUCTIONLINENAME  from PMC_PP_STATION ) c ");
        cqExp.select(" from ZZCJ_ALARM_LOG a , ZZCJ_tabRefer b ");
        cqExp.where();
        
        cqExp.andStr("a.alarm_id = b.alarmid");
        //cqExp.andStr("b.DATA1 = c.PRODUCTIONLINE");
        cqExp.filed(null == startEffectTime ? null : "a.GENERATION_TIME", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "a.GENERATION_TIME", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "B.DATA1", CQExp.EQ, productionline);
        cqExp.filed("".equals(banci) ? null : "a.BANCI", CQExp.EQ, banci);
        cqExp.filed("".equals(data3) ? null : "b.DATA3", CQExp.LIKE, "%" + data3 + "%");
        cqExp.orderByDesc("a.GENERATION_TIME");
        logger.debug(" 总装车间ALARM_LOG故障事件记录查询:  " + cqExp.getSql());
        
        
        
        return cqExp.getDynaBeanMapComboPager("ALARM_LOG", pager, con);

    }

//	public static ComboPager queryAlarmLogImport(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String banci, String data3,
//            Pager pager) throws Exception {
//        CQExp cqExp = CQExp.instance();
//        cqExp.select("select rtrim(b.DATA4) AS PRODUCTIONLINENAME,a.BANCI,a.GENERATION_TIME,a.TIMESTAMP,b.ALARM_MESSAGE,a.RESOURCE,rtrim(b.DATA1) AS DATA1,rtrim(b.DATA2) AS DATA2,rtrim(b.DATA3) AS DATA3,rtrim(b.DATA7) AS DATA7, ");
//        cqExp.select(" convert(decimal(10,2),datediff(second,convert(datetime,a.GENERATION_TIME,20),convert(datetime,a.TIMESTAMP,20))/1.0) WORKTIME ");
//        //cqExp.select(" from ALARM_LOG a , tabRefer b, ( select distinct PRODUCTIONLINE,PRODUCTIONLINENAME  from PMC_PP_STATION ) c ");
//        cqExp.select(" from ZZCJ_ALARM_LOG a inner join ZZCJ_tabRefer b on a.alarm_id = b.alarmid");
//        cqExp.where();
//        //cqExp.andStr("b.DATA1 = c.PRODUCTIONLINE");
//        cqExp.filed(null == startEffectTime ? null : "a.GENERATION_TIME", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
//        cqExp.filed(null == endEffectTime ? null : "a.GENERATION_TIME", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
//        cqExp.filed("".equals(productionline) ? null : "B.DATA1", CQExp.EQ, productionline);
//        cqExp.filed("".equals(banci) ? null : "a.BANCI", CQExp.EQ, banci);
//        cqExp.filed("".equals(data3) ? null : "b.DATA3", CQExp.LIKE, "%" + data3 + "%");
//        cqExp.orderByDesc("a.GENERATION_TIME");
//        logger.debug(" 总装车间ALARM_LOG故障事件记录查询:  " + cqExp.getSql());
//        
//        
//        
//        return cqExp.getDynaBeanMapComboPager("ALARM_LOG", pager, con);
//
//    }
//    
    
    
    
    /**
     * ALARM_LOG故障事件记录导出
     * @param con
     * @param startEffectTime 开始时间
     * @param endEffectTime		计算实际
     * @param productionline	工段
     * @param data2	工位
     * @param data3	设备
     * @return
     * @throws Exception
     */
    public static List queryAlarmLogImportExport(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String data7, String data3,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select rtrim(b.DATA4) AS PRODUCTIONLINENAME,a.GENERATION_TIME,a.TIMESTAMP,b.ALARM_MESSAGE,a.RESOURCE,a.BANCI,rtrim(b.DATA1) AS DATA1,rtrim(b.DATA2) AS DATA2,rtrim(b.DATA3) AS DATA3,rtrim(b.DATA7) AS DATA7, ");
        cqExp.select(" convert(decimal(10,2),datediff(second,convert(datetime,a.GENERATION_TIME,20),convert(datetime,a.TIMESTAMP,20))/1.0) WORKTIME ");
        //cqExp.select(" from ALARM_LOG a , tabRefer b, ( select distinct PRODUCTIONLINE,PRODUCTIONLINENAME  from PMC_PP_STATION ) c ");
        cqExp.select(" from ZZCJ_ALARM_LOG a , ZZCJ_tabRefer b ");
        cqExp.where();
        
        cqExp.andStr("a.alarm_id = b.alarmid");
        //cqExp.andStr("b.DATA1 = c.PRODUCTIONLINE");
        cqExp.filed(null == startEffectTime ? null : "a.GENERATION_TIME", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "a.GENERATION_TIME", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "b.DATA1", CQExp.EQ, productionline);
        //cqExp.filed("".equals(data7) ? null : "b.DATA7", CQExp.EQ, data7);
        cqExp.filed("".equals(banci) ? null : "a.BANCI", CQExp.EQ, banci);
        cqExp.filed("".equals(data3) ? null : "b.DATA3", CQExp.LIKE, "%" + data3 + "%");
        
        logger.debug("总装车间ALARM_LOG故障事件记录导出:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ALARM_LOG", con);

    }
    
    public static java.util.List queryAlarmLog(Connection con, Date startTime, Date endTime, String productionline, String data2, String data3)throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select  c.PRODUCTIONLINENAME,CONVERT(VARCHAR(19),a.GENERATION_TIME,20) GENERATION_TIME,CONVERT(VARCHAR(19),a.TIMESTAMP,20) TIMESTAMP,a.ALARM_MESSAGE,a.RESOURCE,b.DATA1,b.DATA2,b.DATA3, ");
        cqExp.select(" convert(decimal(10,2),datediff(second,convert(datetime,a.GENERATION_TIME,20),convert(datetime,a.TIMESTAMP,20))/1.0) WORKTIME ");
        cqExp.select(" from ZZCJ_YALARM_LOG a , ZZCJ_tabRefer b, ( select distinct PRODUCTIONLINE,PRODUCTIONLINENAME  from ZZCJ_PP_STATION ) c ");
        cqExp.where();
        
        cqExp.andStr("a.alarm_id = b.alarmid");
        cqExp.andStr("b.DATA1 = c.PRODUCTIONLINE");
        cqExp.filed(null == startTime ? null : "a.GENERATION_TIME", CQExp.GREATER_EQ, DateUtils.format(startTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endTime ? null : "a.GENERATION_TIME", CQExp.LESS, DateUtils.format(endTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "c.PRODUCTIONLINENAME", CQExp.EQ, productionline);
        cqExp.filed("".equals(data2) ? null : "b.DATA2", CQExp.EQ, data2);
        cqExp.filed("".equals(data3) ? null : "b.DATA3", CQExp.LIKE, "%" + data3 + "%");
        
        logger.debug(" 总装车间ALARM_LOG故障事件记录查询:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ALARM_LOG", con);

    }
    
    public static int countAlarmLog(Connection con, Date startTime, Date endTime, String productionline, String data2, String data3) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from ZZCJ_ALARM_LOG A , ZZCJ_tabRefer B , ( select distinct PRODUCTIONLINE,PRODUCTIONLINENAME  from ZZCJ_PP_STATION ) C");
        cqExp.where();
        cqExp.andStr("a.alarm_id = b.alarmid");
        cqExp.andStr("b.DATA1 = c.PRODUCTIONLINE");
        cqExp.filed(startTime == null ? null : "GENERATION_TIME", CQExp.GREATER_EQ, DateUtils.format(startTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(endTime == null ? null : "GENERATION_TIME", CQExp.LESS, DateUtils.format(endTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "DATA1",CQExp.EQ, productionline);
        cqExp.filed("".equals(data2) ? null : "DATA2",CQExp.EQ, data2);
        cqExp.filed("".equals(data3) ? null : "DATA3", "like", "%" + data3 + "%");
        

        logger.debug(" 总装车间ALARM_LOG故障事件记录查询:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("ALARM_LOG", con).get("TOTAL_NUM")));
    }
    
    public static ComboPager queryPpAlarmLogImport(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String banci, String data3,
            Pager pager) throws Exception {
    	CQExp cqExp = CQExp.instance();
    	cqExp.select("SELECT * FROM ZZCJ_PP_ALARM");
    	
    	cqExp.where();
    	cqExp.filed(null == startEffectTime ? null : "STOPSTARTTIME", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "STOPSTARTTIME", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "PRODUCTIONNAME", CQExp.EQ, productionline);
        cqExp.filed("".equals(banci) ? null : "BANCI", CQExp.EQ, banci);
//        cqExp.filed("".equals(data3) ? null : "b.DATA3", CQExp.LIKE, "%" + data3 + "%");

        cqExp.orderByDesc("STOPSTARTTIME");
    	
    	return cqExp.getDynaBeanMapComboPager("ALARM_LOG", pager, con);
    }
    
    public static List ExprotPpAlarmLogImport(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String data7, String data3,String banci) throws Exception {
    	CQExp cqExp = CQExp.instance();
    	cqExp.select("SELECT * FROM ZZCJ_PP_ALARM");
    	
    	cqExp.where();
    	cqExp.filed(null == startEffectTime ? null : "STOPSTARTTIME", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "STOPSTARTTIME", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "PRODUCTIONNAME", CQExp.EQ, productionline);
        cqExp.filed("".equals(banci) ? null : "BANCI", CQExp.EQ, banci);
//        cqExp.filed("".equals(data3) ? null : "b.DATA3", CQExp.LIKE, "%" + data3 + "%");
    	
        cqExp.orderByDesc("STOPSTARTTIME");
        logger.debug(" 总装车间停线与故障事件对应表 :  " + cqExp.getSql());
    	return cqExp.getDynaBeanMapList("ALARM_LOG", con);
    }
    
    
    /**
     * 查询所有【FPS记录】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2017-05-22
     * @author 李垚
     * 
     */
    public static ComboPager queryFPS(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String source,
            Pager pager) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT datediff(ss,a.[_TIME],a.[timestamp]) as LASTTIME,a.[timestamp] as TIMESTAMP,a.[point_id] as SOURCE_ADDRESS,a.[_TIME] as _TIME,a._PREV as VALUE,b.TagDescription as GONGDUAN,b.TagDataType as gdcode,b.Shuxing as SOURCE");
        //cqExp.select(" from ALARM_LOG a , tabRefer b, ( select distinct PRODUCTIONLINE,PRODUCTIONLINENAME  from PMC_PP_STATION ) c ");
        cqExp.select(" from FPS a , FPS_TagTable b ");
        cqExp.where();
        
        cqExp.andStr("a.point_id = b.TagName");
        //cqExp.andStr("b.DATA1 = c.PRODUCTIONLINE");
        cqExp.filed(null == startEffectTime ? null : "a.timestamp", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "a.timestamp", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "b.TagDataType", CQExp.EQ, productionline);
        cqExp.filed("".equals(source) ? null : "b.Shuxing", CQExp.EQ, source);
        cqExp.filed("a._PREV", CQExp.NOTNULL, 1);
        cqExp.orderByDesc("_TIME");
        logger.debug(" 总装车间FPS记录查询:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapComboPager("ALARM_LOG", pager, con);

    }
    
    /**
     * FPS记录导出
     * @param con
     * @param startEffectTime 开始时间
     * @param endEffectTime		计算实际
     * @param productionline	工段
     * @param SOURCE 数据源
     * @param 
     * @return
     * @throws Exception
     */
    public static List queryFPSExport(Connection con, Date startEffectTime, Date endEffectTime, String productionline, String source) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT datediff(ss,a.[_TIME],a.[timestamp]) as LASTTIME,a.[timestamp] as TIMESTAMP,a.[point_id] as SOURCE_ADDRESS,a.[_TIME] as _TIME,a._PREV as VALUE,b.TagDescription as GONGDUAN,b.TagDataType as gdcode,b.Shuxing as SOURCE ");
        //cqExp.select(" from ALARM_LOG a , tabRefer b, ( select distinct PRODUCTIONLINE,PRODUCTIONLINENAME  from PMC_PP_STATION ) c ");
        cqExp.select(" from FPS a, FPS_TagTable b");
        cqExp.where();
        
        cqExp.andStr("a.point_id = b.TagName");
        cqExp.filed(null == startEffectTime ? null : "a.timestamp", CQExp.GREATER_EQ, DateUtils.format(startEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == endEffectTime ? null : "a.timestamp", CQExp.LESS, DateUtils.format(endEffectTime, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(productionline) ? null : "b.TagDataType", CQExp.EQ, productionline);
        cqExp.filed("".equals(source) ? null : "b.Shuxing", CQExp.EQ, source);
        cqExp.filed("a._PREV", CQExp.NOTNULL, 1);
        cqExp.orderByDesc("_TIME");
        
        logger.debug("总装车间FPS记录导出:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ALARM_LOG", con);

    }
    
}
