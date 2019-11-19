/**
 * 
 *
 * @File name:  PmcAndonStoplineDao.java 
 * @Create on:  2014-03-16 16:40:101
 * @Author   :  LEEYAO
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
 * 【工段停线报表:ZZCJ_andon_STOPLINE】的Dao操作类
 * 
 */
public class PmcAndonStoplineDao extends DAO {
    /**
     * 查询所有【工段停线报表:ZZCJ_Andon_STOPLINE】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-16
     * @author leeyao
     * @param newbanci 
     * 
     */
    public static List queryPmcAndonStopline(Connection con, Date ppdate, Date nextPpdates,Integer banci, String newbanci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select top(900) cast(round(sum(DATEDIFF(SS,generation_time,timestamp))/60.0,2) as numeric(12,2)) as TIME,");
        cqExp.select("resource+reference as GW,prev_state as GW1,prev_state as GW2,prev_state as GW3,prev_state as GW4,prev_state as GW5,prev_state as TIME1,prev_state as TIME2,prev_state as TIME3,prev_state as TIME4,prev_state as TIME5,");
        cqExp.select("final_state as GD ");
        cqExp.select(" from ZZCJ_ALARM_LOG ");
        cqExp.where();
        cqExp.filed(null == ppdate ? null : "generation_time", CQExp.GREATER_EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATETIME));
        cqExp.filed(null == ppdate ? null : "generation_time", CQExp.LESS, DateUtils.format(nextPpdates, DictConstants.FORMAT_DATETIME));
        cqExp.filed("".equals(banci)? null : "alarm_class", CQExp.EQ, banci);
        cqExp.filed("".equals(newbanci)? null : "BANCI", CQExp.EQ, newbanci);
        cqExp.group("resource,reference,final_state,prev_state");
        cqExp.orderByDesc("final_state,TIME");
        logger.debug("总装车间ZZCJ_Andon_STOPLINE查询所有【工段停线报表:ZZCJ_Andon_STOPLINE】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_Andon_STOPLINE", con);
    }

    
    /**
     * 月
     * @author taofl
     * @param con
     * @param ppdate
     * @param newbanci 
     * @return
     * @throws Exception
     */
    public static List queryMonPmcAndonStopline(Connection con, Date ppdate,Integer banci, String newbanci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select top(900) cast(round(sum(DATEDIFF(SS,generation_time,timestamp))/60.0,2) as numeric(12,2)) as TIME,");
        cqExp.select("resource+reference as GW,prev_state as GW1,prev_state as GW2,prev_state as GW3,prev_state as GW4,prev_state as GW5,prev_state as TIME1,prev_state as TIME2,prev_state as TIME3,prev_state as TIME4,prev_state as TIME5,");
        cqExp.select("final_state as GD ");
        cqExp.select(" from ZZCJ_ALARM_LOG ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(char(7),generation_time,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.filed("".equals(banci)? null : "alarm_class", CQExp.EQ, banci);
        cqExp.filed("".equals(newbanci)? null : "BANCI", CQExp.EQ, newbanci);
        cqExp.group("resource,reference,final_state,prev_state");
        cqExp.orderByDesc("final_state,TIME");
        logger.debug("总装车间ZZCJ_Andon_STOPLINE查询所有【工段停线月报表:ZZCJ_Andon_STOPLINE】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_Andon_STOPLINE", con);
    }
    

    
    /**
     * 年
     * @author taofl
     * @param con
     * @param ppdate
     * @param newbanci 
     * @return
     * @throws Exception
     */
    public static List queryYearPmcAndonStopline(Connection con, Date ppdate,Integer banci, String newbanci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select top(900) cast(round(sum(DATEDIFF(SS,generation_time,timestamp))/60.0,2) as numeric(12,2)) as TIME,");
        cqExp.select("resource+reference as GW,prev_state as GW1,prev_state as GW2,prev_state as GW3,prev_state as GW4,prev_state as GW5,prev_state as TIME1,prev_state as TIME2,prev_state as TIME3,prev_state as TIME4,prev_state as TIME5,");
        cqExp.select("final_state as GD ");
        cqExp.select(" from ZZCJ_ALARM_LOG ");
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(varchar(4),generation_time,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.filed("".equals(banci)? null : "alarm_class", CQExp.EQ, banci);
        cqExp.filed("".equals(newbanci)? null : "BANCI", CQExp.EQ, newbanci);
        cqExp.group("resource,reference,final_state,prev_state");
        cqExp.orderByDesc("final_state,TIME");
        logger.debug("总装车间ZZCJ_Andon_STOPLINE查询所有【工段停线年报表:ZZCJ_Andon_STOPLINE】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_Andon_STOPLINE", con);
    }

    
    /**
     * 周
     * @author taofl
     * @param con
     * @param newbanci 
     * @param ppdate
     * @return
     * @throws Exception
     */
    public static List queryWeekPmcAndonStopline(Connection con, Date startDate,Date lastDate,Integer banci, String newbanci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select top(900) cast(round(sum(DATEDIFF(SS,generation_time,timestamp))/60.0,2) as numeric(12,2)) as TIME,");
        cqExp.select("resource+reference as GW,prev_state as GW1,prev_state as GW2,prev_state as GW3,prev_state as GW4,prev_state as GW5,prev_state as TIME1,prev_state as TIME2,prev_state as TIME3,prev_state as TIME4,prev_state as TIME5,");
        cqExp.select("final_state as GD ");
        cqExp.select(" from ZZCJ_ALARM_LOG ");
        cqExp.where();

        cqExp.filed("convert(varchar(20),generation_time,23)", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATE));
        cqExp.filed("convert(varchar(20),generation_time,23)", CQExp.LESS_EQ, DateUtils.format(lastDate, DictConstants.FORMAT_DATE));
        cqExp.filed("".equals(banci)? null : "alarm_class", CQExp.EQ, banci);
        cqExp.filed("".equals(newbanci)? null : "BANCI", CQExp.EQ, newbanci);
        cqExp.group("resource,reference,final_state,prev_state");
        cqExp.orderByDesc("final_state,TIME");
        logger.debug("总装车间ZZCJ_Andon_STOPLINE查询所有【工段停线周报表:ZZCJ_Andon_STOPLINE】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_Andon_STOPLINE", con);
    }
    

}
