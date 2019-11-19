/**
 * 
 *
 * @File name:  PmcDateImportDao.java 
 * @Create on:  2014-03-16 13:41:557
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
 * 【开动率年报表:YQCJ_PP_START_RATE_Y】的Dao操作类
 * 
 */
public class PmcPpStartRateYDao extends DAO {
    /**
     * 查询所有【计划日期数量导入:YQCJ_DATE_IMPORT】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-16
     * @author taofl
     * 
     */
    public static List queryPmcPpStartRateY(Connection con, Date yyyy,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PRODUCTIONLINE,PRODUCTIONLINENAME,AVG(DATA1) AS DATA1,AVG(DATA2) AS DATA2,AVG(DATA3) AS DATA3,AVG(DATA4) AS DATA4,AVG(DATA5) AS DATA5,AVG(DATA6) AS DATA6,AVG(DATA7) AS DATA7,AVG(DATA8) AS DATA8,AVG(DATA9) AS DATA9,AVG(DATA10) AS DATA10,AVG(DATA11) AS DATA11,AVG(DATA12) AS DATA12,AVG(BANCI) AS BANCI,AVG(YYRATE) AS YYRATE,SUM(YYREALP) AS YYREALP,SUM(YYPLANP) AS YYPLANP,cast(sum(YYSTOPTIME)/60.0 as decimal(18,2)) as YYSTOPTIMEE");
        cqExp.select(" from YQCJ_PP_START_RATE_Y ");
        cqExp.where();
        cqExp.filed(null == yyyy ? null : "convert(char(4),YYYY,23)", CQExp.EQ, DateUtils.format(yyyy, "yyyy"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.group(" PRODUCTIONLINE,PRODUCTIONLINENAME,seq,YYYY");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 油漆车间开动率年报表CYCJ_PP_START_RATE_Y:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("YQCJ_PP_START_RATE_Y", con);

    }
    
    //导出图片查询
    public static List queryPicPmcPpStartRateY(Connection con, Date yyyy,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select * ,replace(YYRATE,char(37),'') YYRATE");
        cqExp.select(" from YQCJ_PP_START_RATE_Y ");
        cqExp.where();
        cqExp.filed(null == yyyy ? null : "convert(char(4),YYYY,23)", CQExp.EQ, DateUtils.format(yyyy, "yyyy"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 油漆车间开动率年报表CYCJ_PP_START_RATE_Y:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("YQCJ_PP_START_RATE_Y", con);

    }

    public static int countPmcPpStartRateY(Connection con, Date yyyy) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM");
        cqExp.select(" from YQCJ_PP_START_RATE_Y ");
        cqExp.where();
        cqExp.filed(null == yyyy ? null : "convert(char(4),YYYY,23)", CQExp.EQ, DateUtils.format(yyyy, "yyyy"));
        cqExp.group("YYYY,YYRATE,YYSTOPTIME,YYREALP,YYPLANP,SHIFT,FACTORY,WORKSHOP,FPRODUCTIONLINE,PRODUCTIONLINE,PRODUCTIONLINENAME,DATA1, DATA2, DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,seq");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 油漆车间开动率年报表CYCJ_PP_START_RATE_Y:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("YQCJ_PP_START_RATE_Y", con).get("TOTAL_NUM")));
    }
}
