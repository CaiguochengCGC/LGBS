package com.hanthink.cycj.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;

import com.hanthink.util.Tools;

public class TabProductHourDao extends DAO {

    /**
     * 生产小时报表
     * 
     * @author taofl
     * @create_date 2014-3-29 下午08:41:12
     * @param con
     * @param stoptime
     * @param toltlag
     * @throws Exception
     */
    public static List queryTabStopLine(Connection con, Date eventDate,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select ID,EventData, EventDate1,  EventDate3,BANCI, EventDate4, EventDate5, EventDate6,EventDate7,");
        cqExp.select("EventDate8, EventDate9, EventDate10, EventDate11, EventDate12, ");
        cqExp.select("EventDate13,EventDate14,EventDate15,EventDate16,EventDate17,EventDate18,EventDate19,EventDate20,EventDate21,EventDate22,EventDate23,EventDate24,");
        cqExp.select("EventDate25,EventDate26,EventDate27,EventDate28,EventDate29,EventDate30,CONVERT(INT,EventDate31) AS PLANDATA ,");
        cqExp.select(" CASE WHEN CONVERT(INT,EventDate31) != 0 THEN ISNULL(cast(round( EventDate29/CONVERT(numeric(7,2),EventDate31)*100,2) as numeric(10,2)),0) WHEN CONVERT(INT,EventDate31) = 0 THEN CONVERT(INT,EventDate31) END as RATE ");

        cqExp.select(" from CYCJ_tabProductHour");

        cqExp.where();

        cqExp.filed(null == eventDate ? null : "convert(varchar(10),EventData)", CQExp.EQ, Tools.getTimestamp(eventDate));
//        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.group(" ID,EventData, EventDate1,EventDate31,  EventDate3, EventDate4, EventDate5, EventDate6,EventDate7,EventDate8, EventDate9, EventDate10, EventDate11, EventDate12,EventDate13,EventDate14,EventDate15,EventDate16,EventDate17,EventDate18,EventDate19,EventDate20,EventDate21,EventDate22,EventDate23,EventDate24,EventDate25,EventDate26,EventDate27,EventDate28,EventDate29,EventDate30,seq,BANCI ");
        cqExp.orderByAsc("seq");
        logger.debug("冲压车间生产小时报表: " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CYCJ_tabProductHour", con);

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

        cqExp.select(" from CYCJ_tabProductHour");

        cqExp.where();

        cqExp.filed(eventDate == null ? null : "convert(date,EventData,23)", "=", Tools.getTimestamp(eventDate));
        cqExp.group(" EventData, EventDate1,  EventDate3, EventDate4, EventDate5, EventDate6,EventDate7,EventDate8, EventDate9, EventDate10, EventDate11, EventDate12,EventDate13,EventDate14,EventDate15,EventDate16,EventDate17,EventDate18,EventDate19,EventDate20,EventDate21,EventDate22,EventDate23,EventDate24,EventDate25,EventDate26,EventDate27,EventDate28,EventDate29,EventDate30 ");
        cqExp.orderByAsc("EventDate30");
        
        logger.debug("冲压车间产量日报表: " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("CYCJ_tabProductHour", con).get("TOTAL_NUM")));
    }

}
