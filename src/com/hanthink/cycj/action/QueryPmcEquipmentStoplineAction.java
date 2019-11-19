/**
 * 
 *
 * @File name:  QueryPmcEquipmentStoplineAction.java 
 * @Create on:  2014-03-16 16:40:100
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.cycj.action;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.cycj.dao.PmcEquipmentStopDao;
import com.hanthink.cycj.dao.PmcEquipmentStoplineDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

public class QueryPmcEquipmentStoplineAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private Date nextPpdates;
    private java.lang.String queryType;
    private String banci;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【工段停线报表】", ex);
    }

    @SuppressWarnings("deprecation")
	@Override
    protected int performExecute(ActionContext atx) throws Exception {
//        List list = PmcEquipmentStoplineDao.queryPmcEquipmentStopline(con, ppdate);
        List list = new LinkedList();
        
        if("0".equals(queryType)){  //日
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),DictConstants.FORMAT_DATE);
          
            //转换日期，变为年-月-日 08:00:00
            Calendar  calendar = Calendar.getInstance();
            calendar.setTime(ppdate);
            calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 8, 0);
            ppdate = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 1); 
            calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 8, 0);
            nextPpdates = calendar.getTime();
            
            list = PmcEquipmentStoplineDao.queryPmcEquipmentStopline(con, ppdate, nextPpdates,banci);
        }else if("1".equals(queryType)){
            //月
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),"yyyy-MM");
            list = PmcEquipmentStoplineDao.queryMonPmcEquipmentStopline(con, ppdate,banci);
        }else if("2".equals(queryType)){
            //年
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),"yyyy");
            list = PmcEquipmentStoplineDao.queryYearPmcEquipmentStopline(con, ppdate,banci);
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
            
            list = PmcEquipmentStoplineDao.queryWeekPmcEquipmentStopline(con, firstOfWeek, lastOfWeek,banci);
        }
        atx.setValue("PMC_EQUIPMENT_STOPLINE", list.toArray());
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        
        queryType = atx.getStringValue("QUERY_TYPE","").trim();
        banci=atx.getStringValue("BANCI","");
        return 1;
    }
}
