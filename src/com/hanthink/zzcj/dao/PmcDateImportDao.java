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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.zzcj.po.PmcDateImportPO;
import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

/**
 * 【计划日期数量导入:ZZCJ_DATE_IMPORT】的Dao操作类
 * 
 */
public class PmcDateImportDao extends DAO {
    /**
     * 查询所有【计划日期数量导入:ZZCJ_DATE_IMPORT】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-16
     * @author taofl
     * 
     */
    public static List queryPmcDateImport(Connection con, Date workdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select SHIFT,WORKDATE,STARTTIME,ENDTIME, (DATEDIFF(hh,STARTTIME,ENDTIME)- RESTTIME) as LASTTIME from ZZCJ_DATE_IMPORT");
        cqExp.where();

        cqExp.filed(null == workdate ? null : "convert(varchar(10), WORKDATE ,23)", CQExp.EQ, Tools.getTimestamp(workdate));
        logger.debug(" 总装车间DATE_IMPORT计划日期数量导入:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_DATE_IMPORT", con);

    }

    /**
     * 查询所有【计划日期数量导入:ZZCJ_DATE_IMPORT】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-29
     * @author 王家乐
     * 
     */
    public static List queryPmcDateImportAndPp(Connection con, Date workdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select '生产计划时间:' as WORKDATE_TYPE, * from ZZCJ_DATE_IMPORT");
        cqExp.where();

        cqExp.filed(null == workdate ? null : "convert(varchar(20), WORKDATE ,23)", CQExp.EQ, DateUtils.format(workdate, DictConstants.FORMAT_DATE));
        logger.debug(" 总装车间DATE_IMPORT计划日期数量导入AND PP:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_DATE_IMPORT", con);

    }

    /**
     * ZZCJ_DATE_IMPORT计划日期数量导入(导入界面查询)
     * 
     * @author luoshw
     * @param con
     * @param startDate
     * @param endDate
     * @param sheft
     * @return
     * @throws Exception
     */
    public static List queryPmcDateImportImport(Connection con, java.util.Date startDate, java.util.Date endDate, java.lang.String sheft) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select * from ZZCJ_DATE_IMPORT");
        cqExp.where();

        cqExp.filed(null == startDate ? null : "convert(datetime, WORKDATE ,23)", CQExp.GREATER_EQ, Tools.getTimestamp(startDate));
        cqExp.filed(null == endDate ? null : "convert(datetime, WORKDATE ,23)", CQExp.LESS, Tools.getNextDayTimestamp(endDate));
        cqExp.filed("".equals(sheft) ? null : "SHIFT", CQExp.EQ, sheft);

        cqExp.orderByAsc(" convert(datetime, WORKDATE ,23),SHIFT");

        logger.debug(" 总装车间DATE_IMPORT计划日期数量导入(导入界面查询):  " + cqExp.getSql());

        return cqExp.getDynaBeanMapList("ZZCJ_DATE_IMPORT", con);
    }

    public static PmcDateImportPO convertBeanMapToPO(DynaBeanMap dynaBeanMap) throws Exception {
        if (dynaBeanMap == null) {
            return null;
        } else {
            PmcDateImportPO pmcDateImportPO = new PmcDateImportPO();
            pmcDateImportPO.setId(dynaBeanMap.get("ID") == null ? null : (Integer) dynaBeanMap.get("ID"));
            pmcDateImportPO.setFactory(dynaBeanMap.get("FACTORY") == null ? null : (String) dynaBeanMap.get("FACTORY"));
            pmcDateImportPO.setWorkshop(dynaBeanMap.get("WORKSHOP") == null ? null : (String) dynaBeanMap.get("WORKSHOP"));
            pmcDateImportPO.setShift(dynaBeanMap.get("SHIFT") == null ? null : String.valueOf(Tools.getInt(String.valueOf(dynaBeanMap.get("SHIFT")))));
            pmcDateImportPO.setWorkdate(dynaBeanMap.get("WORKDATE") == null ? null : DateUtils.parse(String.valueOf(dynaBeanMap.get("WORKDATE")), "yyyy/MM/dd"));
            pmcDateImportPO.setStarttime((dynaBeanMap.get("STARTTIME") == null || dynaBeanMap.get("STARTTIME")=="0")? null : DateUtils.parse(String.valueOf(dynaBeanMap.get("WORKDATE")+" "+dynaBeanMap.get("STARTTIME")), "yyyy/MM/dd HH:mm:ss"));
            pmcDateImportPO.setEndtime(dynaBeanMap.get("ENDTIME") == null ? null : DateUtils.parse(String.valueOf(dynaBeanMap.get("WORKDATE")+" "+dynaBeanMap.get("ENDTIME")), "yyyy/MM/dd HH:mm:ss"));
            //如果是2班跨天，时间就要修改一下
            if(String.valueOf(dynaBeanMap.get("SHIFT")).trim().equals("2")){
            	Calendar  calendar = Calendar.getInstance();
            	calendar.setTime(pmcDateImportPO.getEndtime());
            	calendar.add(Calendar.DATE,1);
            	pmcDateImportPO.setEndtime(calendar.getTime());
            }
            
            int tempresttime=0;
            pmcDateImportPO.setResttime(dynaBeanMap.get("RESTTIME") == null ? null : tempresttime);
            
            pmcDateImportPO.setIp21(dynaBeanMap.get("IP21") == null ? null : Tools.getInt(dynaBeanMap.get("IP21")));
            pmcDateImportPO.setIp22(dynaBeanMap.get("IP22") == null ? null : Tools.getInt(dynaBeanMap.get("IP22")));
            pmcDateImportPO.setIp23(dynaBeanMap.get("IP23") == null ? null : Tools.getInt(dynaBeanMap.get("IP23")));
            pmcDateImportPO.setZp11(dynaBeanMap.get("ZP11") == null ? null : Tools.getInt(dynaBeanMap.get("ZP11")));
            pmcDateImportPO.setBp31(dynaBeanMap.get("BP31") == null ? null : Tools.getInt(dynaBeanMap.get("BP31")));
            pmcDateImportPO.setIp24(dynaBeanMap.get("IP24") == null ? null : Tools.getInt(dynaBeanMap.get("IP24")));
            pmcDateImportPO.setBp32(dynaBeanMap.get("BP32") == null ? null : Tools.getInt(dynaBeanMap.get("BP32")));
            pmcDateImportPO.setAs21(dynaBeanMap.get("AS21") == null ? null : Tools.getInt(dynaBeanMap.get("AS21")));
            pmcDateImportPO.setOther(dynaBeanMap.get("OTHER") == null ? null : Tools.getInt(dynaBeanMap.get("OTHER")));
            pmcDateImportPO.setProducttotal(dynaBeanMap.get("productTotal") == null ? null : Tools.getInt(dynaBeanMap.get("productTotal")));
            
            pmcDateImportPO.setRemark(dynaBeanMap.get("REMARK") == null ? null : (String) dynaBeanMap.get("REMARK"));
            pmcDateImportPO.setOperuser(dynaBeanMap.get("OPERUSER") == null ? null : (String) dynaBeanMap.get("OPERUSER"));
            pmcDateImportPO.setUpdatetime(DateUtils.TimestampToDate(dynaBeanMap.get("UPDATETIME")));
            return pmcDateImportPO;
        }
    }

    public static int countPmcDateImportImport(Connection con, Date startDate, Date endDate, String sheft) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as  TOTAL_NUM ,convert(date, WORKDATE ,23),SHIFT from ZZCJ_DATE_IMPORT");
        cqExp.where();

        cqExp.filed(startDate == null ? null : "convert(date, WORKDATE ,23)", ">=", Tools.getTimestamp(startDate));
        cqExp.filed(endDate == null ? null : "convert(date, WORKDATE ,23)", "<=", Tools.getNextDayTimestamp(endDate));
        cqExp.filed("".equals(sheft) ? null : "SHIFT", "=", sheft);

        cqExp.group(" convert(date, WORKDATE ,23),SHIFT");
        cqExp.orderByAsc(" convert(date, WORKDATE ,23),SHIFT");

        logger.debug("总装车间统计DATE_IMPORT计划日期数量导入(导入界面查询):  " + cqExp.getSql());

        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("ZZCJ_DATE_IMPORT", con).get("TOTAL_NUM")));
    }
    
    /**
     * 判断导入的数据是否存在数据
     * @param con
     * @param startDate
     * @param endDate
     * @param sheft
     * @return
     * @throws Exception
     */
    public static List queryPmcDateImport(Connection con,java.util.Date workdate, java.lang.String sheft) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select * from ZZCJ_DATE_IMPORT");
        cqExp.where();
        
//        cqExp.filed(null == startDate ? null : "convert(datetime, STARTTIME ,23)", CQExp.EQ, Tools.getTimestamp(startDate));
//        cqExp.filed(null == endDate ? null : "convert(datetime, ENDTIME ,23)", CQExp.EQ, Tools.getTimestamp(endDate));
        cqExp.filed("".equals(sheft) ? null : "SHIFT", CQExp.EQ, sheft);
        cqExp.filed(null == workdate ? null : "convert(datetime, WORKDATE, 23)", CQExp.EQ, Tools.getTimestamp(workdate));

        cqExp.orderByAsc(" convert(datetime, WORKDATE ,23),SHIFT");

        logger.debug("总装车间ATE_IMPORT计划日期数量导入(导入界面查询):  " + cqExp.getSql());

        return cqExp.getDynaBeanMapList("ZZCJ_DATE_IMPORT", con);
    }
}
