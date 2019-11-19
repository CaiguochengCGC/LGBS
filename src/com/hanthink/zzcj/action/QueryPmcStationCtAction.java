
             
/**
 * 
 *
 * @File name:  QueryPmcStationCtAction.java 
 * @Create on:  2014-03-19 21:01:760
 * @Author   :  林辉
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */
           
        
package com.hanthink.zzcj.action;

            
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.zzcj.dao.PmcEquipmentStoplineDao;
import com.hanthink.zzcj.dao.PmcStationCtDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

public class QueryPmcStationCtAction extends ActionImp {
	private Connection con=null;
	private Date ppdate;
	private String productionline;
	private String queryType;
	@Override
	protected void doException(ActionContext atx, Exception ex) {
		atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"),"【工位CT报表】" ,ex);
	}
	@Override
	protected int performExecute(ActionContext atx) throws Exception {
		List list = new LinkedList();
        
        if("0".equals(queryType)){  //日
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),DictConstants.FORMAT_DATE);
            list = PmcStationCtDao.queryDayPmcStationCt(con, ppdate,productionline);
        }else if("1".equals(queryType)){
            //月
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),"yyyy-MM");
            list = PmcStationCtDao.queryMonthPmcStationCt(con, ppdate,productionline);
        }else if("2".equals(queryType)){
            //年
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),"yyyy");
            list = PmcStationCtDao.queryYearPmcStationCt(con, ppdate,productionline);
        }else if("3".equals(queryType)){
            //周
            int week  = Tools.getInt(atx.getStringValue("PPDATE",""),0);
            Date date = new Date();
            Calendar ca = Calendar.getInstance();
            String year = DateUtils.format(date,"yyyy");
            String firstDay = year + "-01-01";
            Date first = DateUtils.parse(firstDay,"yyyy-MM-dd");
            ca.setTime(first);
            ca.add(Calendar.WEEK_OF_YEAR, week);
            int weekTemp = ca.get(Calendar.DAY_OF_WEEK) - 2;
            ca.add(Calendar.DAY_OF_YEAR, -weekTemp);
            Date firstOfWeek = ca.getTime();
            ca.add(Calendar.DAY_OF_YEAR, 6);
            Date lastOfWeek = ca.getTime();
            logger.debug("---------开始日期：--------"+firstOfWeek+"----------------结束时间：------"+lastOfWeek);
            list = PmcStationCtDao.queryWeekPmcStationCt(con, firstOfWeek,lastOfWeek,productionline);
        }
        
		atx.setValue("PMC_STATION_CT", list.toArray());
		return 1;
	}
	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
		con = atx.getConection();
		productionline = atx.getStringValue("PRODUCTIONLINENAME","").trim();
		queryType = atx.getStringValue("QUERY_TYPE","").trim();
		return 1;
	}
}
