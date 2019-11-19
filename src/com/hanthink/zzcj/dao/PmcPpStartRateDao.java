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

package com.hanthink.zzcj.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.utils.DateUtils;

/**
 * 【计划日期数量导入:ZZCJ_DATE_IMPORT】的Dao操作类
 * 
 */
public class PmcPpStartRateDao extends DAO {
    /**
     * 查询所有【计划日期数量导入:ZZCJ_DATE_IMPORT】信息
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
        cqExp.select("select PRODUCTIONLINE,PRODUCTIONLINENAME,CAST(AVG(DATA1) AS DECIMAL(18,2)) AS DATA1,CAST(AVG(DATA2) AS DECIMAL(18,2)) AS DATA2,CAST(AVG(DATA3) AS DECIMAL(18,2)) AS DATA3,CAST(AVG(DATA4) AS DECIMAL(18,2)) AS DATA4,CAST(AVG(DATA5) AS DECIMAL(18,2)) AS DATA5,CAST(AVG(DATA6) AS DECIMAL(18,2)) AS DATA6,CAST(AVG(DATA7) AS DECIMAL(18,2)) AS DATA7,CAST(AVG(DATA8) AS DECIMAL(18,2)) AS DATA8,CAST(AVG(DATA9) AS DECIMAL(18,2)) AS DATA9,CAST(AVG(DATA10) AS DECIMAL(18,2)) AS DATA10,CAST(AVG(DATA11) AS DECIMAL(18,2)) AS DATA11,CAST(AVG(DATA12) AS DECIMAL(18,2)) AS DATA12,CAST(AVG(DATA13) AS DECIMAL(18,2)) AS DATA13,CAST(AVG(DATA14) AS DECIMAL(18,2)) AS DATA14,CAST(AVG(DATA15) AS DECIMAL(18,2)) AS DATA15,CAST(AVG(DATA16) AS DECIMAL(18,2)) AS DATA16,CAST(AVG(DATA17) AS DECIMAL(18,2)) AS DATA17,CAST(AVG(DATA18) AS DECIMAL(18,2)) AS DATA18,CAST(AVG(DATA19) AS DECIMAL(18,2)) AS DATA19,CAST(AVG(DATA20) AS DECIMAL(18,2)) AS DATA20,CAST(AVG(DATA21) AS DECIMAL(18,2)) AS DATA21,CAST(AVG(DATA22) AS DECIMAL(18,2)) AS DATA22,CAST(AVG(DATA23) AS DECIMAL(18,2)) AS DATA23,CAST(AVG(DATA24) AS DECIMAL(18,2)) AS DATA24,CAST(AVG(DATA25) AS DECIMAL(18,2)) AS DATA25,CAST(AVG(DATA26) AS DECIMAL(18,2)) AS DATA26,CAST(AVG(DATA27) AS DECIMAL(18,2)) AS DATA27,CAST(AVG(DATA28) AS DECIMAL(18,2)) AS DATA28,CAST(AVG(DATA29) AS DECIMAL(18,2)) AS DATA29,CAST(AVG(DATA30) AS DECIMAL(18,2)) AS DATA30,CAST(AVG(DATA31) AS DECIMAL(18,2)) AS DATA31,BANCI,CAST(AVG(MMRATE) AS decimal(18,2)) AS MMRATE,SUM(MMREALP) AS MMREALP,SUM(MMPLANP) AS MMPLANP,YEAEMONTH YYYY_MM,cast(SUM(MMSTOPTIME)/60.0 as decimal(18,2)) as MMSTOPTIMEE");
    	cqExp.select(", CONCAT(PRODUCTIONLINE,'[',BANCI,']') as FORMAT_PRODUCTIONLINE "); 
        cqExp.select(" from ZZCJ_PP_START_RATE ");
        cqExp.where();
        cqExp.filed(null == yyyyMm ? null : "convert(char(7),YEAEMONTH,23)", CQExp.EQ, DateUtils.format(yyyyMm, "yyyy-MM"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.group(" PRODUCTIONLINE,PRODUCTIONLINENAME,seq,YEAEMONTH,BANCI");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 总装车间开动率月报表ZZCJ_PP_START_RATE:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_START_RATE", con);

    }

    //查询图片导出
    public static List queryPicPmcPpStartRate(Connection con, Date yyyyMm,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
//        cqExp.select("select PRODUCTIONLINE,PRODUCTIONLINENAME, replace(MMRATE,char(37),'') MMRATE,MMSTOPTIME,MMREALP,MMPLANP,DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,DATA30,DATA31,YEAEMONTH YYYY_MM");
        cqExp.select("select *, replace(MMRATE,char(37),'') MMRATE,YEAEMONTH YYYY_MM");
        cqExp.select(" from ZZCJ_PP_START_RATE ");
        cqExp.where();
        cqExp.filed(null == yyyyMm ? null : "convert(char(7),YEAEMONTH,23)", CQExp.EQ, DateUtils.format(yyyyMm, "yyyy-MM"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug("总装车间开动率月报表ZZCJ_PP_START_RATE:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_START_RATE", con);
    }
    
    public static int countPmcPpStartRate(Connection con, Date yyyyMm) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM,YEAEMONTH YYYY_MM");
        cqExp.select(" from ZZCJ_PP_START_RATE ");
        cqExp.where();
        cqExp.filed(yyyyMm == null ? null : "convert(char(7),YEAEMONTH,23)", "=", DateUtils.format(yyyyMm, "yyyy-MM"));
        cqExp.group("YEAEMONTH,MMRATE,MMSTOPTIME,MMREALP,MMPLANP,SHIFT,FACTORY,WORKSHOP,FPRODUCTIONLINE,PRODUCTIONLINE,PRODUCTIONLINENAME,DATA1, DATA2, DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,DATA30,DATA31,seq");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug("总装车间开动率月报表ZZCJ_PP_START_RATE:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("ZZCJ_PP_START_RATE", con).get("TOTAL_NUM")));
    }
}
