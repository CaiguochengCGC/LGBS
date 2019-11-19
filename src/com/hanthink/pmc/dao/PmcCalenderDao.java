package com.hanthink.pmc.dao;
import cn.boho.framework.po.*;
import com.hanthink.util.Tools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 日历模板查询  hxt   2019-03-22
 * 
 */
public class PmcCalenderDao extends DAO {
    /**
     *
     * @param con
     * @return  返回一个车间线体工作时间
     * @throws Exception
     */

    public static ComboPager queryAllLineDate(Connection con, Pager pager, Date date) throws Exception {
        CQExp cqexp = CQExp.instance();
        cqexp.select("select pw.ID,pw.Work_Date,pw.Model_Code," +
                "pw.Shift,pw.Is_WorkDate,pw.LastUpTime,pw.Line_Name,pwt.Templet_Name" +
                " from PMC_WORKDATE pw left join PMC_WORKDATE_TMP pwt  " +
                "on pw.Model_Code = pwt.Model_Code ");
        cqexp.where();
        cqexp.select(" and pw.Is_Delete=0");
        cqexp.filed(null == date ? null : "pw.Work_Date", CQExp.EQ, date);
        logger.info("查询【日历模板配置】信息" + cqexp.getSql());

        return   cqexp.getDynaBeanMapComboPager("CALENDER_DATE",pager, con);
    }
    public static ComboPager queryAllLineDateD(Connection con, Pager pager, Date date,String lineCode,String whereSQL) throws Exception {
        CQExp cqexp = CQExp.instance();
        cqexp.select("select pw.ID,pw.Work_Date,pw.Model_Code," +
                "pw.Shift,pw.Is_WorkDate,pw.LastUpTime,pw.Line_Name,pwt.Templet_Name" +
                " from PMC_WORKDATE pw left join PMC_WORKDATE_TMP pwt  " +
                "on pw.Model_Code = pwt.Model_Code ");
        cqexp.where();
        cqexp.select(whereSQL);
        cqexp.filed(" " == lineCode ? null : "pw.Line_Code", CQExp.EQ, lineCode);
        cqexp.select(" and pw.Is_Delete=0");
        cqexp.filed(null == date ? null : "pw.Work_Date", CQExp.EQ, date);
        logger.info("查询【日历模板配置】信息" + cqexp.getSql());

        return   cqexp.getDynaBeanMapComboPager("CALENDER_DATE",pager, con);
    }
    /**
     *
     * @param con
     * @param pager
     * @param modelCode
     * @return
     * @throws Exception
     */

     public static ComboPager queryWorkTemplent(Connection con, Pager pager, String modelCode) throws Exception {
        CQExp cqexp = CQExp.instance();

        cqexp.select("select * from PMC_WORKDATE_TMP  where Model_Code = (select Model_Code from PMC_WORKDATE where ID="+modelCode+" and Is_Delete=0) and Is_Delete=0");
        logger.info("查询【日历模板绑定】信息" + cqexp.getSql());

        return   cqexp.getDynaBeanMapComboPager("WORK_DATE_MODEL",pager, con);
    }
    //查询工段停线时间分析详情
    public static ComboPager queryStopLineDetailed(Connection con, Pager pager, String lineName, String workTime, String shift, String stopType,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        String whereP="";
        if(!"全部".equals(lineName)){
            whereP+=" AND Rtrim(EventData46)='"+lineName+"' ";
        }
        if(!"全部".equals(shift)){
            whereP+=" AND BANCI='"+shift+"' ";
        }
        if(!"全部".equals(stopType)){
            whereP+=" AND Rtrim(EventData3)='"+stopType+"' ";
        }
        if(!"".equals(workTime)){
            whereP+=" AND CONVERT ( nvarchar ( 23 ), EventDate1, 23 ) = '"+workTime+"'";
        }
        cqExp.select("select EventDate1 as startTime,EventDate2 as endTime,EventData46 as lineName,BANCI as shift,EventData3 as stopType, datediff(second,EventDate1,EventDate2) as stopTime" );
        cqExp.select(" from LGBS.dbo.tabStopline");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.select( whereP);
       /* cqExp.filed("".equals(lineName) ? null : "Rtrim(EventData46)", CQExp.EQ, lineName);
        cqExp.filed("".equals(stopType) ? null : "Rtrim(EventData3)", CQExp.EQ, stopType);
        cqExp.filed("".equals(shift)? null : "BANCI", CQExp.EQ, shift);
        cqExp.filed("".equals(workTime)? null : "convert(nvarchar(23),EventDate1,23)", CQExp.EQ, workTime);*/
        cqExp.orderByDesc(" EventDate1");


        logger.info("查询【工段停线时间分析详情】信息" + cqExp.getSql());

        return   cqExp.getDynaBeanMapComboPager("STOP_DETAILED",pager, con);
    }
    //查询工位报警频次分析详情
    public static ComboPager queryStopLineStationDetailed(Connection con, Pager pager, String lineName, String workTime, String shift, String stopType,String stopStation,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        String whereP="";
        if(!"全部".equals(lineName)){
            whereP+=" AND Rtrim(EventData7)='"+lineName+"' ";
        }
        if(!"全部".equals(shift)){
            whereP+=" AND BANCI='"+shift+"' ";
        }
        if(!"全部".equals(stopType)){
            whereP+=" AND Rtrim(EventData3)='"+stopType+"' ";
        }
        if(!"".equals(workTime)){
            whereP+=" AND Rtrim(WorkDate) = '"+workTime+"'";
        }
        if(!"全部".equals(stopStation)){
            whereP+=" AND Rtrim(EventData5)='"+stopStation+"' ";
        }
        cqExp.select("select EventDate as startTime,EventDate1 as endTime,EventData8 as lineName,BANCI as shift,EventData3 as stopType, datediff(second,EventDate,EventDate1) as stopTime,EventData5 as stopStation" );
        cqExp.select(" from LGBS.dbo.TABSTOPSTA");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.select( whereP);
        cqExp.orderByDesc(" EventDate1");


        logger.info("查询【工位报警频次分析详情】信息" + cqExp.getSql());

        return   cqExp.getDynaBeanMapComboPager("STOP_DETAILED",pager, con);
    }
    /**
     * 查询所有可用日历模板
     * @param con
     * @param pager
     * @return
     * @throws Exception
     */
    public static ComboPager queryAllDateTemplet(Connection con, Pager pager,String modelName) throws Exception {
        CQExp cqexp = CQExp.instance();
        cqexp.select("select * from PMC_WORKDATE_TMP where Is_Delete=0");
        if (!"".equals(modelName)){
            cqexp.select(" AND Templet_Name='"+modelName+"'");
        }
        logger.info("查询【可用日历模板】信息" + cqexp.getSql());
        return   cqexp.getDynaBeanMapComboPager("ALL_TEMPLET_DATE",pager, con);
    }
    public static List queryWorkDateModelID(Connection con, Integer id) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select * from PMC_WORKDATE");
        cqExp.where();

        cqExp.andStr(" ID = "+id+" ");
        logger.debug(" 查询所有【查询工作日期绑定的模板信息】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_WORKDATE", con);
    }
    //查询所有线体
    public static List queryAllOfLine(Connection con) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select * from CSMsg2");

        logger.debug(" 查询所有【CSMsg2】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CSMsg2", con);
    }

    public static int updateWorkDateInfo(Connection con, String id, String workDate, String modelCode,
                                          String shift, String isWorkDate, String lineName,String date) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE PMC_WORKDATE set  Work_Date = '"+workDate+"',Model_Code = '"+modelCode+"'," +
                "Shift = '"+shift+"',Is_WorkDate = '"+isWorkDate+"',Line_Name = '"+lineName+"',LastUpTime = '"+date+"' where ID = '"+id+"'");
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        query.setCommand(sql.toString());
        logger.info("工作日历更新:" + sql);
        query.executeUpdate(con);
        return 1;
    }

    public static List queryLastTemplet(Connection con, String bs1, int shift, String stationName) throws SQLException {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select top 1 Model_Code,Is_WorkDate from PMC_WORKDATE " );
        cqExp.where();
        cqExp.select(" and Work_Shop = '"+bs1+"'  and Shift="+shift+" and Line_Name='"+stationName+"' and Is_Delete=0 order by Work_Date DESC");
        logger.debug(" 查询所有【模板ID】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_WORKDATE", con);
    }
    //查询所有线体名称
    public static List queryAllLine(Connection con) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select stationName as VALUE,stationName as TEXT from CSMsg2");
        cqExp.where();
        return cqExp.getDynaBeanMapList("PMC_PP_LINE", con);
    }
    //查询模板名称
    public static List queryModelName(Connection con) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select DISTINCT Templet_Name AS TEXT,Templet_Name AS 'VALUE' from PMC_WORKDATE_TMP where Is_Delete =0");
        return cqExp.getDynaBeanMapList("MODEL_NAME", con);
    }
    //查询所有线体名称en
    public static List queryAllLineEn(Connection con) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select station as VALUE,stationName as TEXT from CSMsg2");
        cqExp.where();
        return cqExp.getDynaBeanMapList("PMC_PP_LINE_EN", con);
    }
}
