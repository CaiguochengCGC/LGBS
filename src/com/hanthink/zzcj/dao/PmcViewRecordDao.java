/**
 * 
 *
 * @File name:  PmcViewRecordDao.java 
 * @Create on:  2014-03-29 13:46:206
 * @Author   :  郑映
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
import cn.boho.framework.po.CQuery;
import cn.boho.framework.po.CQueryFactoryTool;
import cn.boho.framework.po.DAO;
import cn.boho.framework.po.DynaBeanMap;

import com.hanthink.util.Tools;

/**
 * 【意见反馈表:ZZCJ_VIEW_RECORD】的Dao操作类
 * 
 */
public class PmcViewRecordDao extends DAO {
    /**
     * 查询所有【意见反馈表:ZZCJ_VIEW_RECORD】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-29
     * @author 郑映
     * 
     */
    public static List queryPmcViewRecord(Connection con, Date startPlanDate, Date endPlanDate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select *");
        cqExp.select(" from ZZCJ_VIEW_RECORD");
        cqExp.where();

        cqExp.orderByAsc("RECORDDATE");
        cqExp.filed(null == startPlanDate ? null : "RECORDDATE", CQExp.GREATER_EQ, Tools.getTimestamp(startPlanDate));
        cqExp.filed(null == endPlanDate ? null : "RECORDDATE", CQExp.LESS, Tools.getNextDayTimestamp(endPlanDate));
        logger.debug("总装车间ZZCJ_VIEW_RECORD查询所有【信息反馈:ZZCJ_VIEW_RECORD】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_VIEW_RECORD", con);
    }

    /**
     * 根据主键【ID:ID|】获得信息
     * 
     * @param con
     * @param id
     *            ID
     * @return
     * @throws Exception
     * @date 2014-03-29
     * @author 郑映
     * 
     */
    public static DynaBeanMap getPmcViewRecordByPk(Connection con, java.lang.Integer id) throws Exception {
        DynaBeanMap dynaBeanMap = null;
        StringBuffer sql = new StringBuffer();
        sql.append("select * from ZZCJ_VIEW_RECORD where  ID=?");
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        query.setCommand(sql.toString());
        query.setInt(1, id);
        dynaBeanMap = query.getDynaBeanMap("ZZCJ_VIEW_RECORD", con);
        return dynaBeanMap;
    }

    public static int countPmcViewRecord(Connection con, Date startPlanDate, Date endPlanDate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM");
        cqExp.select(" from ZZCJ_VIEW_RECORD");
        cqExp.where();
        cqExp.orderByAsc("RECORDDATE");
        cqExp.filed(null == startPlanDate ? null : "RECORDDATE", CQExp.GREATER_EQ, Tools.getTimestamp(startPlanDate));
        cqExp.filed(null == endPlanDate ? null : "RECORDDATE", CQExp.LESS, Tools.getNextDayTimestamp(endPlanDate));
        cqExp.group("ID,FACTORY,WORKSHOP,RECORDDATE,USERNAME,RECORDCONTENT,REMARK,OPERUSER,UPDATETIME");

        logger.debug("总装车间ZZCJ_VIEW_RECORD统计所有【信息反馈:ZZCJ_VIEW_RECORD】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("ZZCJ_VIEW_RECORD", con).get("TOTAL_NUM")));
    }
}
