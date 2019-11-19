/**
 * @File name:  PmcPpMmDao.java
 * @Create on:  2014-04-03 14:34:436
 * @Author :  taofl
 * @ChangeList ---------------------------------------------------
 * Date         Editor              ChangeReasons
 */

package com.hanthink.pmc.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.utils.DateUtils;

/**
 * 【产量月报表:PMC_PP_MM】的Dao操作类
 *
 */
public class PmcPpMmDao extends DAO {
    /**
     * 查询所有【产量月报表:PMC_PP_MM】信息
     *
     * @param con
     * @return
     * @throws Exception
     * @date 2014-04-03
     * @author taofl
     *
     */
    public static List queryPmcPpMm(Connection con, Date yyyyMm, String banci, String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select *,case(BANCI) when '1' then '一班' when '2' then '二班' end as BANCII   from PMC_PP_MM ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == yyyyMm ? null : "convert(char(7),YYYY_MM,23)", CQExp.EQ, DateUtils.format(yyyyMm, "yyyy-MM"));
        cqExp.filed("".equals(banci) ? null : "BANCI", CQExp.EQ, banci);
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");

        logger.debug(" PMC_PP_MM查询所有【产量月报表:PMC_PP_MM】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_MM", con);

    }

    public static int countPmcPpMm(Connection con, Date yyyyMm) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM  from PMC_PP_MM ");
        cqExp.where();

        cqExp.filed(yyyyMm == null ? null : "convert(char(7),YYYY_MM,23)", "=", DateUtils.format(yyyyMm, "yyyy-MM"));
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        cqExp.group("YYYY_MM,FPRODUCTIONLINE,PRODUCTIONLINE,PRODUCTIONLINENAME,DATA1, DATA2, DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,DATA30,DATA31,MMRATE,MMREALP,MMPLANP");

        logger.debug(" PMC_PP_MM查询所有【产量月报表:PMC_PP_MM】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_PP_MM", con).get("TOTAL_NUM")));
    }
}
