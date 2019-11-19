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

package com.hanthink.zzcj.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;

import com.hanthink.util.Tools;

/**
 * 【ZZCJ_tabProductType:tabProductType】的Dao操作类
 * 
 */
public class TabproducttypeDao extends DAO {
    /**
     * 查询所有【ZZCJ_tabProductType:tabProductType】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-16
     * @author luoshw
     * 
     */
    public static List queryTabproducttype(Connection con, String startDate, String endDate, java.lang.String productionLine,java.lang.String banci)
            throws Exception {
        CQExp cqexp = CQExp.instance();

        cqexp.select("select EventData5,EventData6,avg(BANCI) AS BANCI,SUM(IP21) AS IP21,SUM(IP22) AS IP22,SUM(IP23) AS IP23,SUM(ZP11) AS ZP11,SUM(BP31) AS BP31,SUM(IP24) AS IP24,SUM(BP32) AS BP32,SUM(AS21) AS AS21,SUM(PRODUCTTOTAL) AS productTotal from ZZCJ_PP_PRODUCT");
        cqexp.where();
        cqexp.filed("".equals(startDate) ? null : "PPDATE", CQExp.GREATER_EQ, startDate);
        cqexp.filed("".equals(endDate) ? null : "PPDATE", CQExp.LESS_EQ, endDate);
        cqexp.filed("".equals(productionLine) ? null : "eventdata6", CQExp.EQ, productionLine);
        cqexp.filed("".equals(banci) ? null : "BANCI", CQExp.EQ, banci);
        cqexp.group(" eventdata5,eventdata6,seq");
        cqexp.orderByAsc("seq,eventdata6");
        
        logger.info("总装车间查询所有【ZZCJ_tabProductType:ZZCJ_tabProductType】信息：" + cqexp.getSql());

        List list = cqexp.getDynaBeanMapList("tabProductType", con);

        return list;
    }

    public static int countTabproducttype(Connection con, Date startDate, Date endDate, String productionLine) throws Exception {
        CQExp cqexp = CQExp.instance();

        cqexp.select("select count(*) as TOTAL_NUM,ID, CONVERT(date,EventData,23),EventDate1, EventDate2,EventDate3,EventDate4,EventDate5");
        cqexp.select(", EventDate17 ");
        cqexp.select(" from ZZCJ_tabProductType ");

        cqexp.where();
        cqexp.filed(null == startDate ? null : "CONVERT(date,EventData,20)", CQExp.GREATER_EQ, Tools.getTimestamp(startDate));
        cqexp.filed(null == endDate ? null : "CONVERT(date,EventData,20)", CQExp.LESS_EQ, Tools.getNextDayTimestamp(endDate));
        cqexp.filed("".equals(productionLine) ? null : "EventDate18", CQExp.EQ, productionLine);
        cqexp.group("ID,CONVERT(date,EventData,23),EventDate1,EventDate2,EventDate3,EventDate4,EventDate5,EventDate6,EventDate7,EventDate8,EventDate9,EventDate10,EventDate11,EventDate12,EventDate13,EventDate14,EventDate15,EventDate16,EventDate17,EventDate18,EventDate19");
        cqexp.orderByAsc("EventDate18");
        
        logger.info("总装车间统计【ZZCJ_tabProductType:CYCJtabProductType】信息：" + cqexp.getSql());
        return Integer.parseInt(String.valueOf(cqexp.getDynaBeanMap("tabProductType", con).get("TOTAL_NUM")));
    }

}
