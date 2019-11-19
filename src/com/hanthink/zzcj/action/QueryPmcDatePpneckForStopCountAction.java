/**
 * 
 *
 * @File name:  QueryPmcDatePpneckAction.java 
 * @Create on:  2014-03-19 20:16:564
 * @Author   :  lanym
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
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.zzcj.dao.PmcDatePpneckDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

public class QueryPmcDatePpneckForStopCountAction extends ActionImp {
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【瓶颈设备停机次数表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
      
        Date startDate = null;
        Date endDate = null; 
        int timeType = 10;
        String yearTime = "";
        
        //获取前台参数
        String ppDate = atx.getStringValue("PPDATE");
        String queryType = atx.getStringValue("QUERY_TYPE", "").trim();
        Calendar calendar = Calendar.getInstance();
        
        if ("0".equals(queryType)) { // 日
            startDate = DateUtils.parse(ppDate, DictConstants.FORMAT_DATE);
            endDate = DateUtils.parse(ppDate, DictConstants.FORMAT_DATE);
            
        } else if ("1".equals(queryType)) { // 月
        	timeType = 7;
            startDate = DateUtils.parse(ppDate, "yyyy-MM");
            
            calendar.setTime(startDate);
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DATE, -1);
            endDate = calendar.getTime();
            
        } else if ("2".equals(queryType)) {  // 年
        	timeType = 4;
            startDate = DateUtils.parse(ppDate, "yyyy");
            
            calendar.setTime(startDate);
            calendar.add(Calendar.YEAR, 1);
            calendar.add(Calendar.DATE, -1);
            endDate = calendar.getTime();
            
        } else if ("3".equals(queryType)) { // 周
            int week = Tools.getInt(ppDate, 0);
            String year = DateUtils.format(new Date(), "yyyy");
            yearTime = year+"-";
            Date firstDate = DateUtils.parse(year, "yyyy");
            calendar.setTime(firstDate);
            
            //当年的第一天的日期
            calendar.add(Calendar.WEEK_OF_YEAR, week);
            
            int weekTemp = calendar.get(Calendar.DAY_OF_WEEK) - 2;
            calendar.add(Calendar.DAY_OF_YEAR, -weekTemp);
            
            //第week周第一天日期和第7天日期
            startDate = calendar.getTime();
            
            calendar.add(Calendar.DAY_OF_YEAR, 6);
            endDate = calendar.getTime();
            
        }
        
        logger.debug("startDate:" + DateUtils.format(startDate, DictConstants.FORMAT_DATETIME));
        logger.debug("endDate:" + DateUtils.format(endDate, DictConstants.FORMAT_DATETIME));
        
        //分页
        Pager pager = new Pager(DictConstants.PMC_DATE_PPNECK_PAGE_SIZE, 1);
        
        //获取值
        List list = PmcDatePpneckDao.queryPmcDatePpneckForStopCountByPager(con, pager, startDate, endDate, timeType,yearTime).getRs();
        
        atx.setValue("tabStopLineCount", list.toArray());
        
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        return 1;
    }
}
