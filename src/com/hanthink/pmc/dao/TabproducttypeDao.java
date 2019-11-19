/**
 * 产量报表
 *
 * @File name:  TabproducttypeDao.java 
 * @Create on:  2014-03-16 17:39:628
 * @Author   :  luoshw
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pmc.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;

import com.hanthink.util.Tools;

/**
 * 【tabProductType:tabProductType】的Dao操作类
 * 
 */
public class TabproducttypeDao extends DAO {
    /**
     * 查询所有【tabProductType:tabProductType】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-16
     * @author luoshw
     * 
     */
    public static List queryTabproducttype(Connection con, String startDate, String endDate, java.lang.String productionLine,String banci,String whereSQL)
            throws Exception {
        CQExp cqexp = CQExp.instance();

        cqexp.select("select LineCode as EventData5,LineName as EventData6,sum(ActProduct) as ActProduct," +
                "Shift,sum(PlanProduct) as PlanProduct,CarType from PMC_PP_PRODUCT");
        cqexp.where();
        cqexp.select(whereSQL);
        cqexp.filed("".equals(startDate) ? null : "PPDATE", CQExp.GREATER_EQ, startDate);
        cqexp.filed("".equals(endDate) ? null : "PPDATE", CQExp.LESS_EQ, endDate);
        cqexp.filed("".equals(productionLine) ? null : "LineCode", CQExp.EQ, productionLine);
        cqexp.filed("".equals(banci)? null : "Shift", CQExp.EQ, banci);
        
        cqexp.group(" LineCode,LineName,CarType,Shift");
        cqexp.orderByAsc("LineName");
        
        logger.info("查询所有【tabProductType:tabProductType】信息：" + cqexp.getSql());

        List list = cqexp.getDynaBeanMapList("tabProductType", con);

        return list;
    }
    public static List queryTabproducttypeAll(Connection con, String startDate, String endDate, java.lang.String productionLine,String banci,String whereSQL)
            throws Exception {
        CQExp cqexp = CQExp.instance();

        cqexp.select("select LineCode as EventData5,LineName as EventData6,sum(ActProduct) as ActProduct," +
                " '全部' as Shift,sum(PlanProduct) as PlanProduct,CarType from PMC_PP_PRODUCT");
        cqexp.where();
        cqexp.select(whereSQL);
        cqexp.filed("".equals(startDate) ? null : "PPDATE", CQExp.GREATER_EQ, startDate);
        cqexp.filed("".equals(endDate) ? null : "PPDATE", CQExp.LESS_EQ, endDate);
        cqexp.filed("".equals(productionLine) ? null : "LineCode", CQExp.EQ, productionLine);
//        cqexp.select(" AND CarType='ALL'");

        cqexp.group(" LineCode,LineName,CarType");
        cqexp.orderByAsc("LineName");

        logger.info("查询所有【tabProductType:tabProductType】信息：" + cqexp.getSql());

        List list = cqexp.getDynaBeanMapList("tabProductType", con);

        return list;
    }

    public static int countTabproducttype(Connection con, Date startDate, Date endDate, String productionLine) throws Exception {
        CQExp cqexp = CQExp.instance();

        cqexp.select("select count(*) as TOTAL_NUM,ID, CONVERT(date,EventData,23),EventDate1, EventDate2,EventDate3,EventDate4,EventDate5");
        cqexp.select(", EventDate17 ");
        cqexp.select(" from tabProductType ");

        cqexp.where();
        cqexp.filed(null == startDate ? null : "CONVERT(date,EventData,20)", CQExp.GREATER_EQ, Tools.getTimestamp(startDate));
        cqexp.filed(null == endDate ? null : "CONVERT(date,EventData,20)", CQExp.LESS_EQ, Tools.getNextDayTimestamp(endDate));
        cqexp.filed("".equals(productionLine) ? null : "EventDate18", CQExp.EQ, productionLine);
        cqexp.group("ID,CONVERT(date,EventData,23),EventDate1,EventDate2,EventDate3,EventDate4,EventDate5,EventDate6,EventDate7,EventDate8,EventDate9,EventDate10,EventDate11,EventDate12,EventDate13,EventDate14,EventDate15,EventDate16,EventDate17,EventDate18,EventDate19");
        cqexp.orderByAsc("EventDate18");
        
        logger.info("统计【tabProductType:tabProductType】信息：" + cqexp.getSql());
        return Integer.parseInt(String.valueOf(cqexp.getDynaBeanMap("tabProductType", con).get("TOTAL_NUM")));
    }

}
