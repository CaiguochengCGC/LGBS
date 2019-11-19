package com.hanthink.pmc.dao;

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
     * @param con
     * @param startDate
     * @param endDate
     * @param EventData13
     * @param EventData1
     * @param EventData2
     * @return
     * @throws Exception
     * @author luoshw
     */
    public static ComboPager queryTabCycleTime(Connection con, Date startTime, String EventData13, String EventData1, String EventData2, Pager pager, String banci, Date endTime, String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
//        cqExp.select("select * from PMC_CYCLETIME");
        cqExp.select(" SELECT ID ");
        cqExp.select(" ,TIMESTAMP ");
        cqExp.select(" ,LINE_ENG ");
        cqExp.select("  ,LINE_CHS ");
        cqExp.select("  ,STATION ");
        cqExp.select("  ,CAR_TYPE ");
        cqExp.select("  ,SHIFT ");
        cqExp.select("  ,CT_TOTAL/10 as CT_TOTAL ");
        cqExp.select("   ,CT_MANUL/10 as CT_MANUL ");
        cqExp.select("   ,CT_CONVR/10 as CT_CONVR ");
        cqExp.select("   ,CT_EQUIP/10 as CT_EQUIP ");
        cqExp.select("   ,CT_ROBOT/10 as CT_ROBOT ");
        cqExp.select("  ,CT_STARV/10 as CT_STARV ");
        cqExp.select("  ,CT_BLOCK/10 as CT_BLOCK ");
        cqExp.select("  ,CT_FAULT/10 as CT_FAULT ");
        cqExp.select("  ,CT_CHANG/10 as CT_CHANG ");
        cqExp.select("  ,CT_PRESS/10 as CT_PRESS ");
        cqExp.select("   ,CT_FIXTL/10 as CT_FIXTL ");
        cqExp.select("  FROM PMC_CYCLETIME ");


        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == startTime ? null : "convert(varchar(20),TIMESTAMP,120)", CQExp.GREATER_EQ, Tools.getTimestamp(startTime));
        cqExp.filed(null == endTime ? null : "convert(varchar(20),TIMESTAMP,120)", CQExp.LESS, Tools.getTimestamp(endTime));
        cqExp.filed("".equals(EventData13) ? null : "Rtrim(LINE_ENG)", CQExp.EQ, EventData13);
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(STATION)", CQExp.EQ, EventData1);
        cqExp.filed("".equals(banci) ? null : "SHIFT", CQExp.EQ, banci);
        //cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
        cqExp.orderByDesc("TIMESTAMP");
        cqExp.orderByAsc("LINE_ENG");
        logger.debug(" PMC系统节拍: " + cqExp.getSql());
        return cqExp.getDynaBeanMapComboPager("TAB_CYCLE_TIME", pager, con);

    }

    public static List exportCT(Connection con, Date startTime, String EventData13, String EventData1, String banci, Date endTime) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select(" SELECT ID ");
        cqExp.select(" ,TIMESTAMP ");
        cqExp.select(" ,LINE_ENG ");
        cqExp.select("  ,LINE_CHS ");
        cqExp.select("  ,STATION ");
        cqExp.select("  ,CAR_TYPE ");
        cqExp.select("  ,SHIFT ");
        cqExp.select("  ,CT_TOTAL/10 as CT_TOTAL ");
        cqExp.select("   ,CT_MANUL/10 as CT_MANUL ");
        cqExp.select("   ,CT_CONVR/10 as CT_CONVR ");
        cqExp.select("   ,CT_EQUIP/10 as CT_EQUIP ");
        cqExp.select("   ,CT_ROBOT/10 as CT_ROBOT ");
        cqExp.select("  ,CT_STARV/10 as CT_STARV ");
        cqExp.select("  ,CT_BLOCK/10 as CT_BLOCK ");
        cqExp.select("  ,CT_FAULT/10 as CT_FAULT ");
        cqExp.select("  ,CT_CHANG/10 as CT_CHANG ");
        cqExp.select("  ,CT_PRESS/10 as CT_PRESS ");
        cqExp.select("   ,CT_FIXTL/10 as CT_FIXTL ");
        cqExp.select("  FROM PMC_CYCLETIME ");


        cqExp.where();

        cqExp.filed(null == startTime ? null : "convert(varchar(20),TIMESTAMP,120)", CQExp.GREATER_EQ, Tools.getTimestamp(startTime));
        cqExp.filed(null == endTime ? null : "convert(varchar(20),TIMESTAMP,120)", CQExp.LESS, Tools.getTimestamp(endTime));
        cqExp.filed("".equals(EventData13) ? null : "Rtrim(LINE_ENG)", CQExp.EQ, EventData13);
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(STATION)", CQExp.EQ, EventData1);
        cqExp.filed("".equals(banci) ? null : "SHIFT", CQExp.EQ, banci);
        //cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
        cqExp.orderByDesc("TIMESTAMP");
        cqExp.orderByAsc("LINE_CHS");
        logger.debug(" 导出PMC系统节拍: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("TAB_CYCLE_TIME", con);

    }

    public static int countTabCycleTime(Connection con, Date date, String EventData13, String EventData1) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(1) as  TOTAL_NUM from tabCycleTime");
        cqExp.where();

        cqExp.filed(null == date ? null : "convert(date,EventData,120)", CQExp.EQ, Tools.getTimestamp(date));
        cqExp.filed("".equals(EventData13) ? null : "Rtrim(EventData14)", CQExp.EQ, EventData13);
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
        cqExp.group("EventDate,EventType,EventDate1,EventDate2,EventDate3,EventDate4,EventDate5,EventDate6,EventDate7,EventDate8,EventDate9,EventDate10,EventDate11,EventDate12,EventDate13,EventDate14,EventDate15");
        cqExp.orderByAsc("EventDate14");
        logger.debug(" 工位总览: " + cqExp.getSql());
        logger.debug("_________________________-xsssssssss)" + cqExp.getDynaBeanMap("tabCycleTime", con));
        return Integer.parseInt(String.valueOf(0));
    }

    public static List queryTabCarType(Connection con) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select DISTINCT carType as VALUE,carType as TEXT from tabProductHour where carType is not null");
        logger.debug("分时候产量查询之车型：" + con);
        return cqExp.getDynaBeanMapList("CAR_TYPE", con);
    }


    public static List queryTabCycleTimeBeat(Connection con, Date startTime, String EventData13, String EventData1, String EventData2, Pager pager, String banci, Date endTime,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select(" SELECT ID ");
        cqExp.select(" ,TIMESTAMP ");
        cqExp.select(" ,LINE_ENG ");
        cqExp.select("  ,LINE_CHS ");
        cqExp.select("  ,STATION ");
        cqExp.select("  ,CAR_TYPE ");
        cqExp.select("  ,SHIFT ");
        cqExp.select("  ,convert(decimal(18,2),CT_TOTAL/10 )as CT_TOTAL ");
        cqExp.select("   ,convert(decimal(18,2),CT_MANUL/10) as CT_MANUL ");
        cqExp.select("   ,convert(decimal(18,2),CT_CONVR/10) as CT_CONVR ");
        cqExp.select("   ,convert(decimal(18,2),CT_EQUIP/10) as CT_EQUIP ");
        cqExp.select("   ,convert(decimal(18,2),CT_ROBOT/10) as CT_ROBOT ");
        cqExp.select("  ,convert(decimal(18,2),CT_STARV/10) as CT_STARV ");
        cqExp.select("  ,convert(decimal(18,2),CT_BLOCK/10) as CT_BLOCK ");
        cqExp.select("  ,convert(decimal(18,2),CT_FAULT/10) as CT_FAULT ");
        cqExp.select("  ,convert(decimal(18,2),CT_CHANG/10) as CT_CHANG ");
        cqExp.select("  ,convert(decimal(18,2),CT_PRESS/10) as CT_PRESS ");
        cqExp.select("   ,convert(decimal(18,2),CT_FIXTL/10) as CT_FIXTL ");
        cqExp.select("  FROM PMC_CYCLETIME ");


        cqExp.where();
cqExp.select(whereSQL);
        cqExp.filed(null == startTime ? null : "convert(varchar(20),TIMESTAMP,120)", CQExp.GREATER_EQ, Tools.getTimestamp(startTime));
        cqExp.filed(null == endTime ? null : "convert(varchar(20),TIMESTAMP,120)", CQExp.LESS, Tools.getTimestamp(endTime));
        cqExp.filed("".equals(EventData13) ? null : "Rtrim(LINE_ENG)", CQExp.EQ, EventData13);
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(STATION)", CQExp.EQ, EventData1);
        cqExp.filed("".equals(banci) ? null : "SHIFT", CQExp.EQ, banci);
        //cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
        cqExp.orderByDesc("TIMESTAMP");
        cqExp.orderByAsc("LINE_CHS");
        logger.debug(" 导出PMC系统节拍: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("TAB_CYCLE_TIME", con);

    }


    public static int countTabCycleTimeBeat(Connection con, Date date, String EventData13, String EventData1, String EventData2) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from tabCycleTime");
        cqExp.where();

        cqExp.filed(null == date ? null : "convert(varchar(10),EventDate,120)", CQExp.EQ, Tools.getTimestamp(date));
        cqExp.filed("".equals(EventData13) ? null : "Rtrim(EventData16)", CQExp.EQ, EventData13);
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
        //cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
        cqExp.orderByAsc("EventData16");
        cqExp.group("EventDate,EventData1,EventData2,EventData3,EventData4,EventData5,EventData6,EventData7,EventData8,EventData9,EventData10,EventData11,EventData12,EventData13,EventData14,EventData15,EventData16,EventData17");

        logger.debug(" 节拍报表: " + cqExp.getSql());
        //return Tools.getInt(String.valueOf(cqExp.getDynaBeanMap("tabCycleTime", con).get("TOTAL_NUM")),0);
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("tabCycleTime", con).get("TOTAL_NUM")));

    }

    public static int countTabCycleTimeBeatRandom(Connection con, Date date, String EventData13, String EventData1) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM from tabCycleTime");
        cqExp.where();

        cqExp.filed(null == date ? null : "convert(varchar(10),EventDate,120)", CQExp.EQ, Tools.getTimestamp(date));
        cqExp.filed("".equals(EventData13) ? null : "Rtrim(EventData16)", CQExp.EQ, EventData13);
        cqExp.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
        //cqExp.filed("".equals(EventData2) ? null : "Rtrim(EventDate2)", CQExp.EQ, EventData2);
        //cqExp.orderByAsc("EventData16");
        //cqExp.group("EventDate,EventData1,EventData2,EventData3,EventData4,EventData5,EventData6,EventData7,EventData8,EventData9,EventData10,EventData11,EventData12,EventData13,EventData14,EventData15,EventData16,EventData17");

        logger.debug(" 节拍报表: " + cqExp.getSql());
        //return Tools.getInt(String.valueOf(cqExp.getDynaBeanMap("tabCycleTime", con).get("TOTAL_NUM")),0);
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("tabCycleTime", con).get("TOTAL_NUM")));

    }

    /**
     * 条件随机查询节拍报表
     *
     * @param con
     * @param date
     * @param EventData13
     * @param EventData1
     * @return
     * @throws Exception
     * @author luoshw
     */
    public static List queryTabCycleTimeByRand(Connection con, Date date, String EventData13, String EventData1, String banci) throws Exception {
        CQExp cqExp1 = CQExp.instance();
        cqExp1.select("select distinct EventData1 from tabCycleTime");
        cqExp1.where();
        cqExp1.filed("".equals(EventData1) ? null : "Rtrim(EventData1)", CQExp.EQ, EventData1);
        cqExp1.filed("".equals(EventData13) ? null : "Rtrim(EventData16)", CQExp.EQ, EventData13);
        cqExp1.filed("".equals(banci) ? null : "EventData2", CQExp.EQ, banci);
        cqExp1.filed(null == date ? null : "convert(varchar(10),EventDate,120)", CQExp.EQ, Tools.getTimestamp(date));

        List list = cqExp1.getDynaBeanMapList("tabCycleTime", con);
        List lst = new LinkedList();
        for (Object object : list) {
            String station = String.valueOf(((DynaBeanMap) object).get("EventData1")).trim();
            CQExp cqExp = CQExp.instance();
            int countnum = countTabCycleTimeBeatRandom(con, date, EventData13, station);
            if (countnum > 0) {
                Random ran = new Random();
                int r = ran.nextInt(countnum) + 1;
                String sql = "";
                sql = "SELECT * FROM (select row_number() OVER(ORDER BY EventData1 ASC) AS ROWNUM, * from tabCycleTime where 1=1 ";
                if (null != date) {
                    sql += " and convert(varchar(10),EventDate,120) = '" + Tools.getStr(Tools.getTimestamp(date)).substring(0, 10) + "'";
                }
                if (!"".equals(EventData13)) {
                    sql += " and Rtrim(EventData16) = '" + EventData13 + "'";
                }
                sql += " and Rtrim(EventData1) = '" + station + "'";
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

                logger.debug(" PMC系统随机查询节拍,工位：[ " + station + "]--" + cqExp.getSql());
                DynaBeanMap beanMap = cqExp.getDynaBeanMap("tabCycleTime", con);
                if (beanMap != null)
                    lst.add(beanMap);
            }
        }

        return lst;

    }


}
