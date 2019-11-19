package com.hanthink.yqcj.dao;

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;

import com.hanthink.util.Tools;


public class PmcSystemReportDao extends DAO {

	public static List QueryPmcSystemReport(Connection con, String workdate,String banci)throws Exception {
		CQExp cqexp = CQExp.instance();
		int day = Tools.getInt(workdate.substring(workdate.lastIndexOf("-")+1));
		cqexp.select("SELECT A.PRODUCTIONLINE as  PRODUCTIONLINE,A.PRODUCTIONLINENAME as PRODUCTIONLINENAME,avg(A.BANCI) as BANCI,AVG(A.CYCLETIME) AS CYCLETIME,AVG(A.BEIZHU) AS BEIZHU,A.seq,");
		cqexp.select("ISNULL(SUM(B.ONEWORKTIME),12) as REALTIME,");
		cqexp.select("ISNULL(SUM(B.TWOWORKTIME),12) as PLANTIME, ");
		cqexp.select("SUM(D.productTotal) as EventDate29,SUM(D.BP32) as PLANCOUNT,");
		cqexp.select("AVG(C.DATA"+day+") as DDRATE,");
		cqexp.select(" cast(SUM(a.STOPTIME)/60.0 as decimal(18,2)) as STOPTIME,");
		cqexp.select(" SUM(a.STOPCOUNT) AS STOPCOUNT,");
		cqexp.select(" cast(SUM(a.DATA3)/60.0 as decimal(18,2)) as DATA3,");
		cqexp.select(" cast(SUM(a.DATA4)/60.0 as decimal(18,2)) as DATA4,");
		cqexp.select(" cast(SUM(a.DATA5)/60.0 as decimal(18,2)) as DATA5,");
		cqexp.select(" cast(SUM(a.DATA6)/60.0 as decimal(18,2)) as DATA6,");
		cqexp.select(" cast(SUM(a.DATA7)/60.0 as decimal(18,2)) as DATA7,");
		cqexp.select("cast(sum(a.DATA8)/60.0 as decimal(18,2)) as DATA8, ");
		cqexp.select(" ISNULL(cast(cast(SUM(D.productTotal)*100 as decimal(18,2))/cast(NULLIf(CONVERT(INT,SUM(D.BP32)),0) as decimal(18,2)) as decimal(18,2)),0)  channengwanclv ");
		cqexp.select(" FROM YQCJ_EQUIPMENT_STOPLINE A  ");
		cqexp.select(" LEFT JOIN YQCJ_PP_PRODUCT_TIME B ");
		cqexp.select(" ON A.PRODUCTIONLINE = B.PRODUCTLINE AND A.PRODUCTIONLINENAME = B.PRODUCTLINENAME AND A.BANCI=B.BANCI");
		cqexp.select(" and CONVERT(varchar(10),A.PPDATE,20) = CONVERT(varchar(10),B.PPDATE,20)");
		cqexp.select(" LEFT JOIN YQCJ_PP_START_RATE C ");
		cqexp.select(" ON A.PRODUCTIONLINE = C.PRODUCTIONLINE AND A.PRODUCTIONLINENAME = C.PRODUCTIONLINENAME AND A.BANCI=C.BANCI");
		cqexp.select(" and CONVERT(varchar(7),A.PPDATE,20) = C.YEAEMONTH");
		cqexp.select(" LEFT JOIN YQCJ_PP_PRODUCT D ");
		cqexp.select(" ON A.PRODUCTIONLINE = D.EventData5 AND A.BANCI = D.BANCI");
		cqexp.select(" and CONVERT(varchar(10),A.PPDATE,20) = D.PPDATE");
		cqexp.where();
		cqexp.filed("".equals(workdate) ? null : "CONVERT(varchar(10),a.ppdate,20)", CQExp.EQ, workdate);
		cqexp.filed("".equals(banci) ? null : "A.BANCI", CQExp.EQ, banci);
		cqexp.group(" A.PRODUCTIONLINE,A.PRODUCTIONLINENAME,A.seq");
		cqexp.orderByAsc("A.seq");
		
		logger.info("油漆车间查询【油漆车间总报表】信息：" + cqexp.getSql());
		
		List list = cqexp.getDynaBeanMapList("YQCJ_SYSTEM_REPORT", con);
		
		return list;
		
	}
	/**
	 * 计算实际产量
	 * @param con
	 * @param production
	 * @param workdate
	 * @return
	 * @throws Exception
	 */
	public static List getProductActNum(Connection con, String production, String workdate) throws Exception {
		CQExp cqExp = CQExp.instance();
		cqExp.select("SELECT COUNT(EVENTDATA4) AS EVENTDATA4 FROM YQCJ_tabProduct");
		cqExp.where("EVENTDATA4 = 0");
		cqExp.filed("".equals(workdate) ? null : "CONVERT(varchar(10),EventDate,20)", CQExp.EQ, workdate);
		cqExp.filed("".equals(production) ? null : "EventData5", CQExp.EQ, production);
		logger.info("油漆车间查询【油漆车间总报表】计算实际产量：" + cqExp.getSql());
		return cqExp.getDynaBeanMapList("YQCJ_SYSTEM_REPORT", con);
	}
	
	/**
	 * 计划总产量
	 * @param con
	 * @param production
	 * @param workdate
	 * @return
	 * @throws Exception
	 */
	public static List getProductPlanNum(Connection con, String production, String workdate) throws Exception {
		CQExp cqExp = CQExp.instance();
		cqExp.select("SELECT SUM(CONVERT(INT,ISNULL(EventDate31,0))) AS EventDate31 FROM YQCJ_tabProductHour");
		cqExp.where();
		cqExp.filed("".equals(workdate) ? null : "CONVERT(varchar(10),EventData,20)", CQExp.EQ, workdate);
		cqExp.filed("".equals(production) ? null : "EventDate1", CQExp.EQ, production);
		logger.info("查询【油漆车间总报表】计划总产量：" + cqExp.getSql());
		return cqExp.getDynaBeanMapList("YQCJ_SYSTEM_REPORT", con);
	}
	
	 public static int countPmcSystemReport(Connection con, java.util.Date workdate) throws Exception {
	    CQExp cqexp = CQExp.instance();
	    cqexp.select("select count(*) as TOTAL_NUM  from YQCJ_EQUIPMENT_STOPLINE a  , YQCJ_tabProductHour b ,YQCJ_PP_START_RATE_DAY c where a.PRODUCTIONLINE = b.EventDate1 ");
	    cqexp.select(" and a.PRODUCTIONLINE = c.PRODUCTIONLINE ");

	    cqexp.filed(null == workdate ? null : "CONVERT(varchar(100), a.PPDATE, 23)", CQExp.EQ, Tools.getTimestamp(workdate));
		cqexp.filed(null == workdate ? null : "CONVERT(varchar(100),CONVERT(datetime,EventData), 23)", CQExp.EQ, Tools.getTimestamp(workdate));
		
		logger.info("查询【油漆车间总报表】总数：" + cqexp.getSql());

	    return Integer.parseInt(String.valueOf(cqexp.getDynaBeanMap("YQCJ_SYSTEM_REPORT", con).get("TOTAL_NUM")));
	    
	    }
	 
	 /**
	  * 获取休息时间
	  * @param con
	  * @param workdate
	  * @return
	  * @throws Exception
	  */
	 public static List getRestTime(Connection con,String workdate) throws Exception {
		CQExp cqexp = CQExp.instance();
		cqexp.select("select SUM(ISNULL(RESTTiME,0)) AS RESTTIME ");
		cqexp.select(" from dbo.PMC_DATE_IMPORT ");
		cqexp.where();
		cqexp.filed(null == workdate ? null : "CONVERT(varchar(10), WORKDATE, 23)", CQExp.EQ, workdate);
		
		logger.info("查询【油漆车间总报表】休息时间：" + cqexp.getSql());
		
		return cqexp.getDynaBeanMapList("YQCJ_SYSTEM_REPORT", con);
	 }

}
