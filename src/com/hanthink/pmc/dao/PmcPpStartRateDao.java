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

package com.hanthink.pmc.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.utils.DateUtils;

/**
 * 开动率报表
 * 
 */
public class PmcPpStartRateDao extends DAO {
    /**
     * 查询所有【计划日期数量导入:PMC_DATE_IMPORT】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-16
     * @author taofl
     * 
     */
    public static List queryPmcPpStartRate(Connection con, Date yyyyMm,String banci,String whereSQL) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select * ,YEAEMONTH YYYY_MM,cast(MMSTOPTIME/60.0 as decimal(18,2)) as MMSTOPTIMEE,(PRODUCTIONLINENAME+'['+cast(PMC_PP_START_RATE.BANCI as varchar)+']') AS FORMAT_PRODUCTLINE,b.ONEWORKTIME*60 as test");
        cqExp.select(" from PMC_PP_START_RATE ");
        cqExp.select("    left join \n" +
                " (\n" +
                " SELECT\n" +
                "       convert(char(7),[PPDATE],23) [PPDATE]\n" +
                "      ,[PRODUCTLINE]\n" +
                "      ,sum([ONEWORKTIME]) [ONEWORKTIME]\n" +
                "      ,[BANCI]\n" +
                "  FROM [LGBS].[dbo].[PMC_PP_PRODUCT_TIME]\n" +
                "  \n" +
                "group by convert(char(7),[PPDATE],23),[PRODUCTLINE],[BANCI]\n" +
                " \n" +
                " )b on PMC_PP_START_RATE.BANCI=b.BANCI and PMC_PP_START_RATE.PRODUCTIONLINE=b.PRODUCTLINE and PMC_PP_START_RATE.YEAEMONTH=b.PPDATE ");
        cqExp.where();
        cqExp.select(whereSQL);
        cqExp.filed(null == yyyyMm ? null : "convert(char(7),YEAEMONTH,23)", CQExp.EQ, DateUtils.format(yyyyMm, "yyyy-MM"));
        cqExp.filed("".equals(banci)? null : "PMC_PP_START_RATE.BANCI", CQExp.EQ, banci);
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 开动率月报表PMC_PP_START_RATE:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_START_RATE", con);

    }

    //查询图片导出
    public static List queryPicPmcPpStartRate(Connection con, Date yyyyMm,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select *, replace(MMRATE,char(37),'') MMRATE,YEAEMONTH YYYY_MM");
        cqExp.select(" from PMC_PP_START_RATE ");
        cqExp.where();
        cqExp.filed(null == yyyyMm ? null : "convert(char(7),YEAEMONTH,23)", CQExp.EQ, DateUtils.format(yyyyMm, "yyyy-MM"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 开动率月报表PMC_PP_START_RATE:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_PP_START_RATE", con);
    }
    
    public static int countPmcPpStartRate(Connection con, Date yyyyMm) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM ,substring(MMRATE,0,LEN(MMRATE)) MMRATE,YEAEMONTH YYYY_MM");
        cqExp.select(" from PMC_PP_START_RATE ");
        cqExp.where();
        cqExp.filed(yyyyMm == null ? null : "convert(char(7),YEAEMONTH,23)", "=", DateUtils.format(yyyyMm, "yyyy-MM"));
        cqExp.group("YEAEMONTH,MMRATE,MMSTOPTIME,MMREALP,MMPLANP,SHIFT,FACTORY,WORKSHOP,FPRODUCTIONLINE,PRODUCTIONLINE,PRODUCTIONLINENAME,DATA1, DATA2, DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,DATA20,DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,DATA30,DATA31,seq");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 开动率月报表PMC_PP_START_RATE:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_PP_START_RATE", con).get("TOTAL_NUM")));
    }
}
