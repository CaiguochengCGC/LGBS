/**
 * 
 *
 * @File name:  PmcPpYyDao.java 
 * @Create on:  2014-04-03 14:37:305
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
 * 【产量年报表:CYCJ_PP_YY】的Dao操作类
 * 
 */
public class PmcPpYyDao extends DAO {
    /**
     * 查询所有【产量年报表:CYCJ_PP_YY】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-04-03
     * @author taofl
     * 
     */
    public static List queryPmcPpYy(Connection con, Date yyyy,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("SELECT PRODUCTIONLINE,PRODUCTIONLINENAME,sum(DATA1) as DATA1,sum(DATA2) as DATA2,sum(DATA3) as DATA3,sum(DATA4) as DATA4,sum(DATA5) as DATA5,sum(DATA6) as DATA6,sum(DATA7) as DATA7,sum(DATA8) as DATA8,sum(DATA9) as DATA9,sum(DATA10) as DATA10,sum(DATA11) as DATA11,sum(DATA12) as DATA12,AVG(YYRATE) AS YYRATE,SUM(YYREALP) AS YYREALP,SUM(YYPLANP) AS YYPLANP,AVG(BANCI) AS BANCI FROM CYCJ_PP_YY ");
        cqExp.where();

        cqExp.filed(null == yyyy ? null : "convert(char(4),YYYY,23)", CQExp.EQ, DateUtils.format(yyyy, "yyyy"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.group(" PRODUCTIONLINE,PRODUCTIONLINENAME,seq");
        cqExp.orderByAsc("seq");

        logger.debug("冲压车间CYCJ_PP_YY查询所有【产量年报表:CYCJ_PP_YY】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CYCJ_PP_YY", con);
    }

    public static int countPmcPpYy(Connection con, Date yyyy) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM  from CYCJ_PP_YY ");
        cqExp.where();

        cqExp.filed(yyyy == null ? null : "convert(char(4),YYYY,23)", CQExp.EQ, DateUtils.format(yyyy, "yyyy"));
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        cqExp.group("YYYY,FACTORY,WORKSHOP,FPRODUCTIONLINE,PRODUCTIONLINE,PRODUCTIONLINENAME,DATA1, DATA2, DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,YYRATE,YYREALP,YYPLANP");

        logger.debug("冲压车间CYCJ_PP_YY查询所有【产量年报表:CYCJ_PP_YY】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("CYCJ_PP_YY", con).get("TOTAL_NUM")));
    }
}
