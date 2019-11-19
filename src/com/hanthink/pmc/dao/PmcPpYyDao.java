/**
 * @File name:  PmcPpYyDao.java
 * @Create on:  2014-04-03 14:37:305
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
 * 【产量年报表:PMC_PP_YY】的Dao操作类
 *
 */
public class PmcPpYyDao extends DAO {
    /**
     * 查询所有【产量年报表:PMC_PP_YY】信息
     *
     * @param con
     * @return
     * @throws Exception
     * @date 2014-04-03
     * @author taofl
     *
     */
    public static List queryPmcPpYy(Connection con, Date yyyy, String banci, String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select * ,case(SHIFT) when '1' then '一班' when '2' then '二班' end as SHIFTT  from PMC_PP_YY ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == yyyy ? null : "convert(char(4),YYYY,23)", CQExp.EQ, DateUtils.format(yyyy, "yyyy"));
        cqExp.filed("".equals(banci) ? null : "SHIFT", CQExp.EQ, banci);
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");

        logger.debug(" PMC_PP_YY查询所有【产量年报表:PMC_PP_YY】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_YY", con);
    }

    public static int countPmcPpYy(Connection con, Date yyyy) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM  from PMC_PP_YY ");
        cqExp.where();

        cqExp.filed(yyyy == null ? null : "convert(char(4),YYYY,23)", CQExp.EQ, DateUtils.format(yyyy, "yyyy"));
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        cqExp.group("YYYY,FPRODUCTIONLINE,PRODUCTIONLINE,PRODUCTIONLINENAME,DATA1, DATA2, DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,YYRATE,YYREALP,YYPLANP");

        logger.debug(" PMC_PP_YY查询所有【产量年报表:PMC_PP_YY】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_PP_YY", con).get("TOTAL_NUM")));
    }
}
