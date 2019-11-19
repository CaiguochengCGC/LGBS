/**
 * 
 *
 * @File name:  PmcPpMmDao.java 
 * @Create on:  2014-04-03 14:34:436
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.yqcj.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.utils.DateUtils;

/**
 * 【产量月报表:YQCJ_PP_MM】的Dao操作类
 * 
 */
public class PmcPpMmDao extends DAO {
    /**
     * 查询所有【产量月报表:YQCJ_PP_MM】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-04-03
     * @author taofl
     * 
     */
    public static List queryPmcPpMm(Connection con, Date yyyyMm,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT PRODUCTIONLINE,PRODUCTIONLINENAME,sum(DATA1) as DATA1,sum(DATA2) as DATA2,sum(DATA3) as DATA3,sum(DATA4) as DATA4,sum(DATA5) as DATA5,sum(DATA6) as DATA6,sum(DATA7) as DATA7,sum(DATA8) as DATA8,sum(DATA9) as DATA9,sum(DATA10) as DATA10,sum(DATA11) as DATA11,sum(DATA12) as DATA12,sum(DATA13) as DATA13,sum(DATA14) as DATA14,sum(DATA15) as DATA15,sum(DATA16) as DATA16,sum(DATA17) as DATA17,sum(DATA18) as DATA18,sum(DATA19) as DATA19,sum(DATA20) as DATA20,sum(DATA21) as DATA21,sum(DATA22) as DATA22,sum(DATA23) as DATA23,sum(DATA24) as DATA24,sum(DATA25) as DATA25,sum(DATA26) as DATA26,sum(DATA27) as DATA27,sum(DATA28) as DATA28,sum(DATA29) as DATA29,sum(DATA30) as DATA30,sum(DATA31) as DATA31,AVG(MMRATE) AS MMRATE,SUM(MMREALP) AS MMREALP,SUM(MMPLANP) AS MMPLANP,AVG(BANCI) AS BANCI FROM YQCJ_PP_MM ");
        cqExp.where();

        cqExp.filed(null == yyyyMm ? null : "convert(char(7),YYYY_MM,23)", CQExp.EQ, DateUtils.format(yyyyMm, "yyyy-MM"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.group(" PRODUCTIONLINE,PRODUCTIONLINENAME,seq");
        cqExp.orderByAsc("seq");

        logger.debug("油漆车间CYCJ_PP_MM查询所有【产量月报表:CYCJ_PP_MM】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("YQCJ_PP_MM", con);

    }

    public static int countPmcPpMm(Connection con, Date yyyyMm) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM  from YQCJ_PP_MM ");
        cqExp.where();

        cqExp.filed(yyyyMm == null ? null : "convert(char(7),YYYY_MM,23)", "=", DateUtils.format(yyyyMm, "yyyy-MM"));
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        cqExp.group("YYYY_MM,FACTORY,WORKSHOP,FPRODUCTIONLINE,PRODUCTIONLINE,PRODUCTIONLINENAME,DATA1, DATA2, DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,DATA30,DATA31,MMRATE,MMREALP,MMPLANP");

        logger.debug("油漆车间CYCJ_PP_MM查询所有【产量月报表:CYCJ_PP_MM】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("YQCJ_PP_MM", con).get("TOTAL_NUM")));
    }
}
