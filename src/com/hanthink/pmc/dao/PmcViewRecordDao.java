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

package com.hanthink.pmc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.CQuery;
import cn.boho.framework.po.CQueryFactoryTool;
import cn.boho.framework.po.DAO;
import cn.boho.framework.po.DynaBeanMap;
import oracle.sql.DATE;

import com.hanthink.util.Tools;

/**
 * 【意见反馈表:PMC_VIEW_RECORD】的Dao操作类
 * 
 */
public class PmcViewRecordDao extends DAO {
    /**
     * 查询所有【意见反馈表:PMC_VIEW_RECORD】信息
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
       /* cqExp.select("select ID,WORKSHOP,BEGIN_TIME,END_TIME,REST_TIME,REST_COUNT,TOTAL_TIME,WORK_DATE,SHIFT  ");
        cqExp.select(" from ZZSAIC_WORK_SCHEDUAL");
        cqExp.where();

        
        cqExp.filed(null == startPlanDate ? null : "BEGIN_TIME", CQExp.GREATER_EQ, Tools.getTimestamp(startPlanDate));
        cqExp.filed(null == endPlanDate ? null : "BEGIN_TIME", CQExp.LESS, Tools.getNextDayTimestamp(endPlanDate));
        cqExp.orderByDesc("BEGIN_TIME");*/
        cqExp.select("SELECT * from PMC_WORKDATE_TMP where Is_Delete=0 order by LastUpTime DESC");
        logger.debug(" PMC_VIEW_RECORD查询所有【休息模板:PMC_VIEW_RECORD】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_VIEW_RECORD", con);
    }
    //查询编辑的CT
    public static List queryEditCT(Connection con,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT * from EDIT_CT where 1=1 "+whereSQL+" order by PRODUCTIONNAME DESC");
        logger.debug(" 查询编辑CT:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CT", con);
    }
    //查询编辑的冲压车间实际产量
    public static List queryEditCYACPC(Connection con, String workdate,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT * FROM [LGBS].[dbo].[PMC_PP_PRODUCT] A ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.select("  AND A.[CarType]='ALL'");
        cqExp.select(" AND (A.[LineCode]='DLHD3' or A.[LineCode]='FDRDL' or A.[LineCode]='FDRDR' or A.[LineCode]='FRDL2' or A.[LineCode]='FRDL3' or A.[LineCode]='FRDR2' or A.[LineCode]='HDTG2' or A.[LineCode]='XHDTG') ");
        cqExp.filed("".equals(workdate) ? null : "[PPDATE]", CQExp.EQ, workdate);
        logger.debug(" 查询编辑冲压实际产量:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_CY_PC", con);
    }
    public static void queryDPmcViewRecord(Connection con,int id) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("update PMC_WORKDATE_TMP set Is_Delete=1 where Model_Code="+id+" ");
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        query.setCommand(sql.toString());
        logger.info("删除:" + sql);
        query.executeUpdate(con);
    }
    public static void queryUPmcViewRecord(Connection con,int Model_Code,String Work_Shop,String Templet_Name,int Shift,String WorkStart_Time,String WorkEnd_Time,String Rest_Time1,String Rest_Time2,String Rest_Time3,String Rest_Time4,String Rest_Time5,String Rest_Time6 ,String Rest_Time7,String Rest_Time8,String Rest_Time9,String Rest_Time10,String lastUpdate) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE PMC_WORKDATE_TMP SET Work_Shop='"+Work_Shop+
                "',Templet_Name='"+Templet_Name+"',Shift="+Shift+
                ",WorkStart_Time='"+WorkStart_Time+"',WorkEnd_Time='"
                +WorkEnd_Time+"',Rest_Time1='"+Rest_Time1+
                "',Rest_Time2='"+Rest_Time2+"',Rest_Time3='"
                +Rest_Time3+"',Rest_Time4='"+Rest_Time4
                +"',Rest_Time5='"+Rest_Time5+"',Rest_Time6='"
                +Rest_Time6+"',Rest_Time7='"+Rest_Time7+
                "',Rest_Time8='"+Rest_Time8+"',Rest_Time9='"
                +Rest_Time9+"',Rest_Time10='"+Rest_Time10+
                "',LastUpTime='"+lastUpdate+"'   where Model_Code="
                +Model_Code+" ");
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        query.setCommand(sql.toString());
        logger.info("修改作息时间模板:" + sql);
        query.executeUpdate(con);
    }
    public static void queryUPCT(Connection con,String production1,int ct,int jEP21,int jZP11,int jBP31,int jBP32,int jAS21,int jAS22,int jBP34,int jAS24,int jAS26,int jAS28) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE EDIT_CT SET CT="+ct+",EP21="+jEP21+",ZP11="+jZP11+",BP31="+jBP31+",BP32="+jBP32+",AS21="+jAS21+",AS22="+jAS22+",BP34="+jBP34+",AS24="+jAS24+",AS26="+jAS26+",AS28="+jAS28+" WHERE PRODUCTION='"+production1+"' ");
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        query.setCommand(sql.toString());
        logger.info("修改ct:" + sql);
        query.executeUpdate(con);
    }
    public static void UpdateWRate(Connection con,String production1,int id, int BANCI,String quyu,String YYYY_MM) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE PMC_PP_START_RATE_W  SET quyu='"+quyu+"' WHERE ID='"+id+"' ");
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        query.setCommand(sql.toString());
        logger.info("修改ct:" + sql);
        query.executeUpdate(con);
    }

    public static void AddWRate(Connection con,String ID,String WEEKL, String WORKSTARTTIME,String WORKSECTION,String AREA,String STATION
            ,String MODELS,String PROBLEMDESCRIPTION,String CAUSEANALISYS,String MEASURES,String DOWNTIMEMINUTS,String REGIONALRESPONSIBLEPERSON,String SOLVEPEOPLE,String STATEOFTHEPROBLEM,String WHERHERTHEOVERDUER) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into PMC_PP_START_RATE_W_edit(ID,WEEKL,WORKSTARTTIME,WORKSECTION,AREA,STATION,MODELS,PROBLEMDESCRIPTION,CAUSEANALISYS,MEASURES,DOWNTIMEMINUTS,REGIONALRESPONSIBLEPERSON,SOLVEPEOPLE,STATEOFTHEPROBLEM,WHERHERTHEOVERDUER)" +
                "VALUES('"+ID+"','"+WEEKL+"','"+WORKSTARTTIME+"','"+WORKSECTION+"','"+AREA+"','"+STATION+"','"+MODELS+"','"+PROBLEMDESCRIPTION+"','"+CAUSEANALISYS+"','"+MEASURES+"','"+DOWNTIMEMINUTS+"','"+REGIONALRESPONSIBLEPERSON+"','"+SOLVEPEOPLE+"','"+STATEOFTHEPROBLEM+"','"+WHERHERTHEOVERDUER+"')");
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        query.setCommand(sql.toString());
        logger.info("增加开动率汇总备注:" + sql);
        query.executeUpdate(con);
    }
    public static void queryUPPC(Connection con,String id,Integer actProduct) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE PMC_PP_PRODUCT SET ActProduct="+actProduct+" WHERE ID='"+id+"' ");
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        query.setCommand(sql.toString());
        logger.info("修改pc:" + sql);
        query.executeUpdate(con);
    }

    public static void queryIPmcViewRecord(Connection con,String Work_Shop,String Templet_Name,int Shift,String WorkStart_Time,String WorkEnd_Time,String Rest_Time1,String Rest_Time2,String Rest_Time3,String Rest_Time4,String Rest_Time5,String Rest_Time6 ,String Rest_Time7,String Rest_Time8,String Rest_Time9,String Rest_Time10,String lastUpdate) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into PMC_WORKDATE_TMP( Work_Shop,Templet_Name,Shift,WorkStart_Time,WorkEnd_Time,Rest_Time1,Rest_Time2,Rest_Time3,Rest_Time4,Rest_Time5,Rest_Time6,Rest_Time7,Rest_Time8,Rest_Time9,Rest_Time10,LastUpTime)" +
        		"VALUES('"+Work_Shop+"','"+Templet_Name+"',"+Shift+",'"+WorkStart_Time+"','"+WorkEnd_Time+"','"+Rest_Time1+"','"+Rest_Time2+"','"+Rest_Time3+"','"+Rest_Time4+"','"+Rest_Time5+"','"+Rest_Time6+"','"+Rest_Time7+"','"+Rest_Time8+"','"+Rest_Time9+"','"+Rest_Time10+"','"+lastUpdate+"')");

        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        query.setCommand(sql.toString());
        logger.info("增加作息时间模板:" + sql);
        query.executeUpdate(con);
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
        sql.append("select * from PMC_VIEW_RECORD where  ID=?");
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        query.setCommand(sql.toString());
        query.setInt(1, id);
        dynaBeanMap = query.getDynaBeanMap("PMC_VIEW_RECORD", con);
        return dynaBeanMap;
    }

    public static int countPmcViewRecord(Connection con, Date startPlanDate, Date endPlanDate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM");
        cqExp.select(" from PMC_VIEW_RECORD");
        cqExp.where();
        cqExp.orderByAsc("RECORDDATE");
        cqExp.filed(null == startPlanDate ? null : "RECORDDATE", CQExp.GREATER_EQ, Tools.getTimestamp(startPlanDate));
        cqExp.filed(null == endPlanDate ? null : "RECORDDATE", CQExp.LESS, Tools.getNextDayTimestamp(endPlanDate));
        cqExp.group("ID,FACTORY,WORKSHOP,RECORDDATE,USERNAME,RECORDCONTENT,REMARK,OPERUSER,UPDATETIME");

        logger.debug(" PMC_VIEW_RECORD统计所有【信息反馈:PMC_VIEW_RECORD】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_VIEW_RECORD", con).get("TOTAL_NUM")));
    }

    public static void updateWworkDateRecord(Connection con, int id) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append("update PMC_WORKDATE set Model_Code = 0 where Model_Code="+id+" ");
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();
        query.setCommand(sql.toString());
        logger.info("PMC_WORKDATE删除:" + sql);
        query.executeUpdate(con);
    }
    public static List queryLineDate(Connection con, String lineName) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select DISTINCT station from CSMsg2 where stationName='"+lineName+"' ");
        logger.debug(" 查询线体:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("LINENAME", con);
    }
}
