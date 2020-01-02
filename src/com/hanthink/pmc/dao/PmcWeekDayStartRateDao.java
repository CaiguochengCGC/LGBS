package com.hanthink.pmc.dao;
import cn.boho.framework.po.*;
import cn.boho.framework.utils.DateUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 查询【周开动率】  huo   2019-12-18
 * 
 */
public class PmcWeekDayStartRateDao extends DAO {
    /**
     *
     * @param con
     * @return  返回线体和对应周开动率
     * @throws Exception
     */

    public static List queryWeekDayStartRateD(Connection con, String yyyy ,String PRODUCTIONLINENAME ,String whereSQL) throws Exception {

        CQExp cqExp = CQExp.instance();
        cqExp.select("select * ,YEAEMONTH YYYY_MM,cast(MMSTOPTIME/60.0 as decimal(18,2)) as MMSTOPTIMEE,(PRODUCTIONLINENAME+'['+cast(BANCI as varchar)+']') AS FORMAT_PRODUCTLINE");
        cqExp.select(" from PMC_PP_START_RATE_W ");
        cqExp.where();
        cqExp.select(whereSQL);
        //cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.filed("".equals(yyyy)? null : "YEAEMONTH", CQExp.EQ, yyyy);
        cqExp.filed("".equals(PRODUCTIONLINENAME)? null : "PRODUCTIONLINE", CQExp.EQ, PRODUCTIONLINENAME);
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.info("查询【周开动率】" + cqExp.getSql());

        return cqExp.getDynaBeanMapList("CALENDER_DATE", con);
    }

    public static List queryWeekDayStartRateDExcel(Connection con, int ID ,String whereSQL) throws Exception {

        CQExp cqExp = CQExp.instance();
        cqExp.select("select * ");
        cqExp.select(" from PMC_PP_START_RATE_W_edit ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed("".equals(ID)? null : "ID", CQExp.EQ, ID);
        logger.info("查询【周开动率】" + cqExp.getSql());

        return cqExp.getDynaBeanMapList("CALENDER_DATE", con);
    }

    public static List queryWeekDayStartRateNoteD(Connection con, String  ID ,String whereSQL) throws Exception {

        CQExp cqExp = CQExp.instance();
        cqExp.select("select * ");
        cqExp.select(" from PMC_PP_START_RATE_W_edit ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed("".equals(ID)? null : "ID", CQExp.EQ, ID);
//        cqExp.orderByAsc("setDate");
        logger.info("查询【周开动率备注】" + cqExp.getSql());

        return cqExp.getDynaBeanMapList("weekDayStartRateNoteR", con);
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
