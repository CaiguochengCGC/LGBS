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

package com.hanthink.cycj.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.utils.DateUtils;

/**
 * 【产量报表周报表:CYCJ_PP_WEEK】的Dao操作类
 * 
 */
public class PmcPpWeekDao extends DAO {
    /**
     * 查询所有【产量报表周报表:CYCJ_PP_WEEK】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-31
     * @author taofl
     * 
     */
    public static List queryPmcPpWeek(Connection con, Date week,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT PRODUCTIONLINE,PRODUCTIONLINENAME,sum(DATA1) as DATA1,sum(DATA2) as DATA2,sum(DATA3) as DATA3,sum(DATA4) as DATA4,sum(DATA5) as DATA5,sum(DATA6) as DATA6,sum(DATA7) as DATA7,sum(DATA8) as DATA8,sum(DATA9) as DATA9,sum(DATA10) as DATA10,sum(DATA11) as DATA11,sum(DATA12) as DATA12,sum(DATA13) as DATA13,sum(DATA14) as DATA14,sum(DATA15) as DATA15,sum(DATA16) as DATA16,sum(DATA17) as DATA17,sum(DATA18) as DATA18,sum(DATA19) as DATA19,sum(DATA20) as DATA20,sum(DATA21) as DATA21,sum(DATA22) as DATA22,sum(DATA23) as DATA23,sum(DATA24) as DATA24,sum(DATA25) as DATA25,sum(DATA26) as DATA26,sum(DATA27) as DATA27,sum(DATA28) as DATA28,sum(DATA29) as DATA29,sum(DATA30) as DATA30,sum(DATA31) as DATA31,sum(DATA32) as DATA32,sum(DATA33) as DATA33,sum(DATA34) as DATA34,sum(DATA35) as DATA35,sum(DATA36) as DATA36,sum(DATA37) as DATA37,sum(DATA38) as DATA38,sum(DATA39) as DATA39,sum(DATA40) as DATA40,sum(DATA41) as DATA41,sum(DATA42) as DATA42,sum(DATA43) as DATA43,sum(DATA44) as DATA44,sum(DATA45) as DATA45,sum(DATA46) as DATA46,sum(DATA47) as DATA47,sum(DATA48) as DATA48,sum(DATA49) as DATA49,sum(DATA50) as DATA50,sum(DATA51) as DATA51,sum(DATA52) as DATA52,sum(DATA53) as DATA53,AVG(WEEKRATE) AS WEEKRATE,SUM(YYREALP) AS YYREALP,SUM(YYPLANP) AS YYPLANP,AVG(BANCI) AS BANCI FROM CYCJ_PP_WEEK ");
        cqExp.where();

        cqExp.filed(null == week ? null : "convert(char(7),YYYY,23)", CQExp.EQ, DateUtils.format(week, "yyyy"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.group(" PRODUCTIONLINE,PRODUCTIONLINENAME,seq");
        cqExp.orderByAsc("seq");

        logger.debug("冲压车间CYCJ_PP_WEEK查询所有【产量周报表:CYCJ_PP_WEEK】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CYCJ_PP_WEEK", con);
    }

    public static int countPmcPpWeek(Connection con, Date ppdate) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM  from CYCJ_PP_WEEK ");
        cqExp.where();

        cqExp.filed(ppdate == null ? null : "convert(char(7),YYYY,23)", "=", DateUtils.format(ppdate, "yyyy"));
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        cqExp.group("YYYY,FACTORY,WORKSHOP,FPRODUCTIONLINE,PRODUCTIONLINE,PRODUCTIONLINENAME,DATA1, DATA2, DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,DATA30,DATA31,YYPLANP,YYREALP,WEEKRATE,DATA32,DATA33,DATA34,DATA35,DATA36,DATA37,DATA38,DATA39,DATA40,DATA41,DATA42,DATA43,DATA44,DATA45,DATA46,DATA47,DATA48,DATA49,DATA50,DATA51,DATA52,DATA53");

        logger.debug("冲压车间CYCJ_PP_WEEK查询所有【产量周报表:CYCJ_PP_WEEK】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("CYCJ_PP_WEEK", con).get("TOTAL_NUM")));
    }
}
