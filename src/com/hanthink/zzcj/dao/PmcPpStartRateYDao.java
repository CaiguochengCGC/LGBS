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
 * 【开动率年报表:ZZCJ_PP_START_RATE_Y】的Dao操作类
 * 
 */
public class PmcPpStartRateYDao extends DAO {
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
    public static List queryPmcPpStartRateY(Connection con, Date yyyy,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PRODUCTIONLINE,PRODUCTIONLINENAME,CAST(AVG(DATA1) AS DECIMAL(18,2)) AS DATA1,CAST(AVG(DATA2) AS DECIMAL(18,2)) AS DATA2,CAST(AVG(DATA3) AS DECIMAL(18,2)) AS DATA3,CAST(AVG(DATA4) AS DECIMAL(18,2)) AS DATA4,CAST(AVG(DATA5) AS DECIMAL(18,2)) AS DATA5,CAST(AVG(DATA6) AS DECIMAL(18,2)) AS DATA6,CAST(AVG(DATA7) AS DECIMAL(18,2)) AS DATA7,CAST(AVG(DATA8) AS DECIMAL(18,2)) AS DATA8,CAST(AVG(DATA9) AS DECIMAL(18,2)) AS DATA9,CAST(AVG(DATA10) AS DECIMAL(18,2)) AS DATA10,CAST(AVG(DATA11) AS DECIMAL(18,2)) AS DATA11,CAST(AVG(DATA12) AS DECIMAL(18,2)) AS DATA12,BANCI,CAST(AVG(YYRATE) AS decimal(18,2)) AS YYRATE,SUM(YYREALP) AS YYREALP,SUM(YYPLANP) AS YYPLANP,cast(sum(YYSTOPTIME)/60.0 as decimal(18,2)) as YYSTOPTIMEE");
        cqExp.select(" ,CONCAT(PRODUCTIONLINE,'[',BANCI,']') as FORMAT_PRODUCTIONLINE "); 
        cqExp.select(" from ZZCJ_PP_START_RATE_Y ");
        cqExp.where();
        cqExp.filed(null == yyyy ? null : "convert(char(4),YYYY,23)", CQExp.EQ, DateUtils.format(yyyy, "yyyy"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.group(" PRODUCTIONLINE,PRODUCTIONLINENAME,seq,YYYY,BANCI");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 总装车间开动率年报表CYCJ_PP_START_RATE_Y:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_START_RATE_Y", con);

    }
    
    //导出图片查询
    public static List queryPicPmcPpStartRateY(Connection con, Date yyyy,String banci) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select * ,replace(YYRATE,char(37),'') YYRATE");
        cqExp.select(" from ZZCJ_PP_START_RATE_Y ");
        cqExp.where();
        cqExp.filed(null == yyyy ? null : "convert(char(4),YYYY,23)", CQExp.EQ, DateUtils.format(yyyy, "yyyy"));
        cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 总装车间开动率年报表CYCJ_PP_START_RATE_Y:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("ZZCJ_PP_START_RATE_Y", con);

    }

    public static int countPmcPpStartRateY(Connection con, Date yyyy) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM");
        cqExp.select(" from ZZCJ_PP_START_RATE_Y ");
        cqExp.where();
        cqExp.filed(null == yyyy ? null : "convert(char(4),YYYY,23)", CQExp.EQ, DateUtils.format(yyyy, "yyyy"));
        cqExp.group("YYYY,YYRATE,YYSTOPTIME,YYREALP,YYPLANP,SHIFT,FACTORY,WORKSHOP,FPRODUCTIONLINE,PRODUCTIONLINE,PRODUCTIONLINENAME,DATA1, DATA2, DATA3,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12,seq");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" 总装车间开动率年报表CYCJ_PP_START_RATE_Y:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("ZZCJ_PP_START_RATE_Y", con).get("TOTAL_NUM")));
    }
}
