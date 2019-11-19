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

package com.hanthink.cycj.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.utils.DateUtils;

/**
 * 【计划日期数量导入:CYCJ_DATE_IMPORT】的Dao操作类
 * 
 */
public class PmcPpStartRateDao extends DAO {
    /**
     * 查询所有【计划日期数量导入:CYCJ_DATE_IMPORT】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-16
     * @author taofl
     * 
     */
    public static List queryPmcPpStartRate(Connection con, Date yyyyMm,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PRODUCTIONLINE,PRODUCTIONLINENAME,AVG(DATA1) AS DATA1,AVG(DATA2) AS DATA2,AVG(DATA3) AS DATA3,AVG(DATA4) AS DATA4,AVG(DATA5) AS DATA5,AVG(DATA6) AS DATA6,AVG(DATA7) AS DATA7,AVG(DATA8) AS DATA8,AVG(DATA9) AS DATA9,AVG(DATA10) AS DATA10,AVG(DATA11) AS DATA11,AVG(DATA12) AS DATA12,AVG(DATA13) AS DATA13,AVG(DATA14) AS DATA14,AVG(DATA15) AS DATA15,AVG(DATA16) AS DATA16,AVG(DATA17) AS DATA17,AVG(DATA18) AS DATA18,AVG(DATA19) AS DATA19,AVG(DATA20) AS DATA20,AVG(DATA21) AS DATA21,AVG(DATA22) AS DATA22,AVG(DATA23) AS DATA23,AVG(DATA24) AS DATA24,AVG(DATA25) AS DATA25,AVG(DATA26) AS DATA26,AVG(DATA27) AS DATA27,AVG(DATA28) AS DATA28,AVG(DATA29) AS DATA29,AVG(DATA30) AS DATA30,AVG(DATA31) AS DATA31,AVG(BANCI) AS BANCI,AVG(MMRATE) AS MMRATE,SUM(MMREALP) AS MMREALP,SUM(MMPLANP) AS MMPLANP,YEAEMONTH YYYY_MM,cast(SUM(MMSTOPTIME)/60.0 as decimal(18,2)) as MMSTOPTIMEE");
        cqExp.select(" from CYCJ_PP_START_RATE ");
        cqExp.where();
        cqExp.filed(null == yyyyMm ? null : "convert(char(7),YEAEMONTH,23)", CQExp.EQ, DateUtils.format(yyyyMm, "yyyy-MM"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.group(" PRODUCTIONLINE,PRODUCTIONLINENAME,seq,YEAEMONTH");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 冲压车间开动率月报表CYCJ_PP_START_RATE:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CYCJ_PP_START_RATE", con);

    }

    //查询图片导出
    public static List queryPicPmcPpStartRate(Connection con, Date yyyyMm,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
//        cqExp.select("select PRODUCTIONLINE,PRODUCTIONLINENAME, replace(MMRATE,char(37),'') MMRATE,MMSTOPTIME,MMREALP,MMPLANP,DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,DATA30,DATA31,YEAEMONTH YYYY_MM");
        cqExp.select("select *, replace(MMRATE,char(37),'') MMRATE,YEAEMONTH YYYY_MM");
        cqExp.select(" from CYCJ_PP_START_RATE ");
        cqExp.where();
        cqExp.filed(null == yyyyMm ? null : "convert(char(7),YEAEMONTH,23)", CQExp.EQ, DateUtils.format(yyyyMm, "yyyy-MM"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug("冲压车间开动率月报表CYCJ_PP_START_RATE:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("CYCJ_PP_START_RATE", con);
    }
    
    public static int countPmcPpStartRate(Connection con, Date yyyyMm) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM,YEAEMONTH YYYY_MM");
        cqExp.select(" from CYCJ_PP_START_RATE ");
        cqExp.where();
        cqExp.filed(yyyyMm == null ? null : "convert(char(7),YEAEMONTH,23)", "=", DateUtils.format(yyyyMm, "yyyy-MM"));
        cqExp.group("YEAEMONTH,MMRATE,MMSTOPTIME,MMREALP,MMPLANP,SHIFT,FACTORY,WORKSHOP,FPRODUCTIONLINE,PRODUCTIONLINE,PRODUCTIONLINENAME,DATA1, DATA2, DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,DATA30,DATA31,seq");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug("冲压车间开动率月报表CYCJ_PP_START_RATE:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("CYCJ_PP_START_RATE", con).get("TOTAL_NUM")));
    }
}
