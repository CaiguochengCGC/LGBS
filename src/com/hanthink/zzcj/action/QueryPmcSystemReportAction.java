/**
 * 
 *
 * @File name:  QueryTabproducttypeAction.java 
 * @Create on:  2014-03-16 17:39:625
 * @Author   :  luoshw
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.zzcj.action;

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;

import com.hanthink.zzcj.dao.PmcSystemReportDao;
import com.hanthink.util.Tools;

public class QueryPmcSystemReportAction extends ActionImp {
    private Connection con = null;
    private java.util.Date WORKDATE;
    private String workDate;
    private String banci;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【PmcSystemReport】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = PmcSystemReportDao.QueryPmcSystemReport(con, workDate,banci);
        //查询休息时间
//        List restTime = PmcSystemReportDao.getRestTime(con, workDate);
//        DynaBeanMap dbmRest = (DynaBeanMap)restTime.get(0);
//        for (int i=0; i<list.size();i++) {
//        	DynaBeanMap dbm = (DynaBeanMap) list.get(i);
//        	double restTimeD = Tools.getDouble(dbmRest.get("RESTTIME"),0.0);
//        	//除去休息的停线时间 
//        	double stopRest = (Tools.getDouble(dbm.get("STOPTIME"),0.0) - restTimeD);
//        	if (stopRest <= 0) {
//        		stopRest = 0;
//        	}
//        	dbm.put("RESTTIME", stopRest);
//        	if("ZPTG".equals(dbm.get("PRODUCTIONLINE"))){
//        		//实际产量
//        		List zpAct = PmcSystemReportDao.getProductActNum(con, "ZPTG", workDate);
//        		//计划产量
//        		List zpPlan = PmcSystemReportDao.getProductPlanNum(con, "ZPTG", workDate);
//        		//实际产量
//        		dbm.put("EventDate29", ((DynaBeanMap)zpAct.get(0)).get("EVENTDATA4"));
//        		//计划产量
//        		dbm.put("PLANCOUNT", ((DynaBeanMap)zpPlan.get(0)).get("EventDate31"));
//        		
//        	}else if ("ASHDL".equals(dbm.get("PRODUCTIONLINE"))){
//        		List asAct = PmcSystemReportDao.getProductActNum(con, "ASHDL", workDate);
//        		List asPlan = PmcSystemReportDao.getProductPlanNum(con, "ASHDL", workDate);
//        		//实际产量
//        		dbm.put("EventDate29", ((DynaBeanMap)asAct.get(0)).get("EVENTDATA4"));
//        		//计划产量
//        		dbm.put("PLANCOUNT", ((DynaBeanMap)asPlan.get(0)).get("EventDate31"));
//        	}
//        }
        atx.setValue("PMC_SYSTEM_REPORT", list.toArray());
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        //WORKDATE = DateUtils.parse(atx.getStringValue("WORKDATE"),DictConstants.FORMAT_DATE);
        workDate = atx.getStringValue("WORKDATE", "");
        banci=atx.getStringValue("PRODUCTIONLINENAME", "");
        return 1;
    }
}
