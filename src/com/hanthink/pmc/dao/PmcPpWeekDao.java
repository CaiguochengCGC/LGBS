/**
 * 
 *
 * @File name:  PmcPpWeekDao.java 
 * @Create on:  2014-03-31 18:24:588
 * @Author   :  taofl
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
import cn.boho.framework.utils.DateUtils;

/**
 * 【产量报表周报表:PMC_PP_WEEK】的Dao操作类
 * 
 */
public class PmcPpWeekDao extends DAO {
    /**
     * 查询所有【产量报表周报表:PMC_PP_WEEK】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-31
     * @author taofl
     * 
     */
    public static List queryPmcPpWeek(Connection con, Date week,String banci,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select *,case(SHIFT) when '1' then '一班' when '2' then '二班' end as SHIFTT   from PMC_PP_WEEK  ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == week ? null : "convert(char(7),YYYY,23)", CQExp.EQ, DateUtils.format(week, "yyyy"));
        cqExp.filed("".equals(banci)? null : "SHIFT", CQExp.EQ, banci);
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");

        logger.debug(" PMC_PP_WEEK查询所有【产量周报表:PMC_PP_WEEK】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_WEEK", con);
    }

    public static int countPmcPpWeek(Connection con, Date ppdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM  from PMC_PP_WEEK ");
        cqExp.where();

        cqExp.filed(ppdate == null ? null : "convert(char(7),YYYY,23)", "=", DateUtils.format(ppdate, "yyyy"));
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        cqExp.group("YYYY,FPRODUCTIONLINE,PRODUCTIONLINE,PRODUCTIONLINENAME,DATA1, DATA2, DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,DATA30,DATA31,YYPLANP,YYREALP,WEEKRATE,DATA32,DATA33,DATA34,DATA35,DATA36,DATA37,DATA38,DATA39,DATA40,DATA41,DATA42,DATA43,DATA44,DATA45,DATA46,DATA47,DATA48,DATA49,DATA50,DATA51,DATA52,DATA53");

        logger.debug(" PMC_PP_WEEK查询所有【产量周报表:PMC_PP_WEEK】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_PP_WEEK", con).get("TOTAL_NUM")));
    }
}
