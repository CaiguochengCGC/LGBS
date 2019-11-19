/**
 * 
 *
 * @File name:  QueryPmcEquipmentStopAction.java 
 * @Create on:  2014-03-16 16:26:909
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pmc.action;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pmc.dao.PmcEquipmentStopForProductionLineDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

public class QueryPmcEquipmentStopforProductionLineAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private java.lang.String queryType;
    private java.lang.String pdLine;
    private String shift;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【工位设备停机表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        List list = new LinkedList();

        Date startDate = null;
        Date endDate = null;
        int timeType = 10;
        String yearTime = "";
        //获取前台参数
        String ppDate = atx.getStringValue("PPDATE");
        Calendar calendar = Calendar.getInstance();
        pdLine = atx.getStringValue("PRODUCTIONLINENAME", "").trim();
        
        if ("0".equals(queryType)) { // 日
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"), DictConstants.FORMAT_DATE);


            list = PmcEquipmentStopForProductionLineDao.queryPmcEquipmentStopAgenDay(con,ppdate,  pdLine, shift);

            atx.setValue("PMC_EQUIPMENT_STOP", list.toArray());
            return 1;
        } else {
            if ("1".equals(queryType)) { // 月
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
                yearTime = year + "-";
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
            list = PmcEquipmentStopForProductionLineDao.queryPmcEquipmentStopAgen(con, startDate, endDate, pdLine, shift);

            atx.setValue("PMC_EQUIPMENT_STOP", list.toArray());
            return 1;
        }
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        queryType = atx.getStringValue("QUERY_TYPE", "").trim();
        shift = atx.getStringValue("BANCI", "").trim();
        return 1;
    }
}
