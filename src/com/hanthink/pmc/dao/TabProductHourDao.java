package com.hanthink.pmc.dao;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.DAO;

import cn.boho.framework.po.Pager;
import com.hanthink.util.Tools;

public class TabProductHourDao extends DAO {
	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
    /**
     * 生产小时报表
     * 
     * @author taofl
     * @create_date 2014-3-29 下午08:41:12
     * @param con
     * @throws Exception
     */
    public static List queryTabStopLine(Connection con, Date eventDate,String carType) throws Exception {
        CQExp cqExp = CQExp.instance();
        
        cqExp.select(" SELECT carType, EventDate1,CONVERT (INT, EventDate3) AS EventDate3,CONVERT (INT, EventDate4) EventDate4,CONVERT (INT, EventDate5) EventDate5,CONVERT (INT, EventDate6) EventDate6,CONVERT (INT, EventDate7) EventDate7,CONVERT (INT, EventDate8) EventDate8, ");
        cqExp.select(" CONVERT (INT, EventDate9) EventDate9,CONVERT (INT, EventDate10) EventDate10,CONVERT (INT, EventDate11) EventDate11,CONVERT (INT, EventDate12) EventDate12,CONVERT (INT, EventDate13) EventDate13,CONVERT (INT, EventDate14) EventDate14,CONVERT (INT, EventDate15) EventDate15,CONVERT (INT, EventDate16) EventDate16, ");
        cqExp.select(" CONVERT (INT, EventDate17) EventDate17,CONVERT (INT, EventDate18) EventDate18,CONVERT (INT, EventDate19) EventDate19,CONVERT (INT, EventDate20) EventDate20,CONVERT (INT, EventDate21) EventDate21,CONVERT (INT, EventDate22) EventDate22,CONVERT (INT, EventDate23) EventDate23,CONVERT (INT, EventDate24) EventDate24, ");
        cqExp.select(" CONVERT (INT, EventDate25) EventDate25,CONVERT (INT, EventDate26) EventDate26,CONVERT (INT, EventDate29) EventDate29,EventDate30 FROM ");
        cqExp.select(" tabProductHour ");
        cqExp.where();
        cqExp.filed(null == eventDate ? null : "convert(varchar(10),EventData)", CQExp.EQ, Tools.getTimestamp(eventDate));
//        cqExp.filed(carType == null ? null : "carType", "=", carType);
        cqExp.filed("".equals(carType) ? null : "Rtrim(carType)", CQExp.EQ, carType);
        logger.debug(" 生产小时报表: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("tabProductHour", con);

    }
    //分页查询
    public static ComboPager queryTabStopLinePage(Connection con, Date eventDate, String carType, Pager pager,String line,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();

        cqExp.select(" SELECT carType, EventDate1,CONVERT (INT, EventDate3) AS EventDate3,CONVERT (INT, EventDate4) EventDate4,CONVERT (INT, EventDate5) EventDate5,CONVERT (INT, EventDate6) EventDate6,CONVERT (INT, EventDate7) EventDate7,CONVERT (INT, EventDate8) EventDate8, ");
        cqExp.select(" CONVERT (INT, EventDate9) EventDate9,CONVERT (INT, EventDate10) EventDate10,CONVERT (INT, EventDate11) EventDate11,CONVERT (INT, EventDate12) EventDate12,CONVERT (INT, EventDate13) EventDate13,CONVERT (INT, EventDate14) EventDate14,CONVERT (INT, EventDate15) EventDate15,CONVERT (INT, EventDate16) EventDate16, ");
        cqExp.select(" CONVERT (INT, EventDate17) EventDate17,CONVERT (INT, EventDate18) EventDate18,CONVERT (INT, EventDate19) EventDate19,CONVERT (INT, EventDate20) EventDate20,CONVERT (INT, EventDate21) EventDate21,CONVERT (INT, EventDate22) EventDate22,CONVERT (INT, EventDate23) EventDate23,CONVERT (INT, EventDate24) EventDate24, ");
        cqExp.select(" CONVERT (INT, EventDate25) EventDate25,CONVERT (INT, EventDate26) EventDate26,CONVERT (INT, EventDate29) EventDate29,EventDate30 FROM ");
        cqExp.select(" tabProductHour ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == eventDate ? null : "convert(varchar(10),EventData)", CQExp.EQ, Tools.getTimestamp(eventDate));
//        cqExp.filed(carType == null ? null : "carType", "=", carType);
        cqExp.filed("".equals(carType) ? null : "Rtrim(carType)", CQExp.EQ, carType);
        cqExp.filed("".equals(line)?null:"EventDate1", cqExp.EQ, line);
        logger.debug(" 生产小时报表: " + cqExp.getSql());
        return cqExp.getDynaBeanMapComboPager("TAB_PRODUCT_HOUR",pager, con);

    }
    //导出分时报表
    public static List expQueryTabStopLine(Connection con, Date eventDate,String carType) throws Exception {
    	CQExp cqExp = CQExp.instance();
    	 cqExp.select(" SELECT carType, EventDate1,CONVERT (INT, EventDate3) AS EventDate3,CONVERT (INT, EventDate4) EventDate4,CONVERT (INT, EventDate5) EventDate5,CONVERT (INT, EventDate6) EventDate6,CONVERT (INT, EventDate7) EventDate7,CONVERT (INT, EventDate8) EventDate8, ");
         cqExp.select(" CONVERT (INT, EventDate9) EventDate9,CONVERT (INT, EventDate10) EventDate10,CONVERT (INT, EventDate11) EventDate11,CONVERT (INT, EventDate12) EventDate12,CONVERT (INT, EventDate13) EventDate13,CONVERT (INT, EventDate14) EventDate14,CONVERT (INT, EventDate15) EventDate15,CONVERT (INT, EventDate16) EventDate16, ");
         cqExp.select(" CONVERT (INT, EventDate17) EventDate17,CONVERT (INT, EventDate18) EventDate18,CONVERT (INT, EventDate19) EventDate19,CONVERT (INT, EventDate20) EventDate20,CONVERT (INT, EventDate21) EventDate21,CONVERT (INT, EventDate22) EventDate22,CONVERT (INT, EventDate23) EventDate23,CONVERT (INT, EventDate24) EventDate24, ");
         cqExp.select(" CONVERT (INT, EventDate25) EventDate25,CONVERT (INT, EventDate26) EventDate26,CONVERT (INT, EventDate29) EventDate29,EventDate30 FROM ");
         cqExp.select(" tabProductHour ");
         cqExp.where("");
        cqExp.select(" Rtrim(carType) = 'ALL' ");
         cqExp.filed(null == eventDate ? null : "convert(varchar(10),EventData)", CQExp.EQ, Tools.getTimestamp(eventDate));
         logger.debug(" 导出生产小时报表: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("tabProductHour", con);

    }

    public static int countTabStopLine(Connection con, Date eventDate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM, EventData, EventDate1,  EventDate3, EventDate4, EventDate5, EventDate6,EventDate7,");
        cqExp.select("EventDate8, EventDate9, EventDate10, EventDate11, EventDate12, ");
        cqExp.select("EventDate13,EventDate14,EventDate15,EventDate16,EventDate17,EventDate18,EventDate19,EventDate20,EventDate21,EventDate22,EventDate23,EventDate24,");
        cqExp.select("EventDate25,EventDate26,EventDate27,EventDate28,EventDate29,EventDate30,");
        // cqExp.select("sum(convert(int,EventDate27)) as REALDATA , ");
        // dbo.USF_GET_PLANCOUNT(GETDATE(),getdate())
        cqExp.select(" (dbo.USF_GET_PLANCOUNT(EventDate1,convert(date,EventData,23),convert(date,EventData,23))) as PLANDATA , ");
        // cqExp.select(" dbo.USF_GET_PLANCOUNT(convert(date,EventData,23),convert(date,EventData,23)) as REALDATA , ");
        cqExp.select(" cast(round(convert(int,EventDate29) /( nullif(dbo.USF_GET_PLANCOUNT(EventDate1,convert(date,EventData,23),convert(date,EventData,23)),0) + 0.0)*100,2) as numeric(10,2))  as RATE ");

        cqExp.select(" from tabProductHour");

        cqExp.where();

        cqExp.filed(eventDate == null ? null : "convert(date,EventData,23)", "=", Tools.getTimestamp(eventDate));
        cqExp.group(" EventData, EventDate1,  EventDate3, EventDate4, EventDate5, EventDate6,EventDate7,EventDate8, EventDate9, EventDate10, EventDate11, EventDate12,EventDate13,EventDate14,EventDate15,EventDate16,EventDate17,EventDate18,EventDate19,EventDate20,EventDate21,EventDate22,EventDate23,EventDate24,EventDate25,EventDate26,EventDate27,EventDate28,EventDate29,EventDate30 ");
        cqExp.orderByAsc("EventDate30");
        
        logger.debug(" 产量日报表: " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("tabProductHour", con).get("TOTAL_NUM")));
    }

    /**
     * PMC_PP_PRODUCT
     *
     * @author hxt
     * @create_date 2019-3-7 下午03:41:12
     * @param con
     * @throws Exception
     */
    public static List queryPlanProductData(Connection con, String planDate,String lineCode,String shift) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select ID,PPDATE,LineCode,LineName,PlanProduct,ActProduct,Shift,CarType from PMC_PP_PRODUCT " );
        cqExp.where();
        cqExp.filed("" == planDate ? null : "PPDATE", "=", planDate);
        cqExp.filed(lineCode == "" ? null : "LineCode", "=", lineCode);
        cqExp.filed("".equals(shift) ? null : "Rtrim(Shift)", CQExp.EQ, shift);
        logger.debug(" 计划产量编辑报表: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PmcPlanEdit", con);

    }

}
