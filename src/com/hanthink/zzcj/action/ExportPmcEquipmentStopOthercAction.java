package com.hanthink.zzcj.action;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.zzcj.dao.PmcEquipmentStopForProductionLineDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.Tools;

public class ExportPmcEquipmentStopOthercAction extends ActionImp {
    private Connection con = null;
    private String pdLine;
    private String queryType;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【瓶颈工位查询停机次数日报表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
        String[] headers = Tools.getStrs(atx.getArrayValue("HEADER"));
        String[] columns = Tools.getStrs(atx.getArrayValue("COLUMN"));
        String[] widthTmps = Tools.getStrs(atx.getArrayValue("WIDTH"));

        for (int i = 0; i < headers.length; i++) {
            headers[i] = Tools.encodeUTF8(headers[i]);
        }

        int[] widths = new int[widthTmps.length];
        for (int i = 0; i < widthTmps.length; i++) {
            widths[i] = Tools.getInt(widthTmps[i], Integer.valueOf(100)).intValue();
        }
        Date startDate = null;
        Date endDate = null;
        int timeType = 10;
        String yearTime = "";
        //获取前台参数
        String ppDate = atx.getStringValue("PPDATE");
        System.out.println(ppDate);
        Calendar calendar = Calendar.getInstance();
//        pdLine = atx.getStringValue("PRODUCTIONLINENAME", "").trim();
        String fileName = "";
        if ("0".equals(queryType)) { // 日
//            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"), DictConstants.FORMAT_DATE);
        	fileName = "瓶颈工位查询停机次数日报表";
            startDate = DateUtils.parse(ppDate, DictConstants.FORMAT_DATE);
            endDate = DateUtils.parse(ppDate, DictConstants.FORMAT_DATE);
            
        } else if ("1".equals(queryType)) { // 月
        	fileName = "瓶颈工位查询停机次数月报表";
        	timeType = 7;
            startDate = DateUtils.parse(ppDate, "yyyy-MM");
            
            calendar.setTime(startDate);
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DATE, -1);
            endDate = calendar.getTime();
        } else if ("2".equals(queryType)) {  // 年
        	fileName = "瓶颈工位查询停机次数年报表";
        	timeType = 4;
            startDate = DateUtils.parse(ppDate, "yyyy");
            
            calendar.setTime(startDate);
            calendar.add(Calendar.YEAR, 1);
            calendar.add(Calendar.DATE, -1);
            endDate = calendar.getTime();
        } else if ("3".equals(queryType)) { // 周
        	fileName = "瓶颈工位查询停机次数周报表";
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
        System.out.println(startDate+"-----------"+endDate);
        List beanList = PmcEquipmentStopForProductionLineDao.queryPmcEquipmentStopCount(con, startDate, endDate, pdLine);
        
        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap localDynaBeanMap = (DynaBeanMap) beanList.get(i);
        }

        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();

        ExcelUtil.exportExcel(fileName, headers, columns, widths, beanList, out);
        out.flush();

        return 1;
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        pdLine=java.net.URLDecoder.decode(atx.getStringValue("PRODUCTIONLINENAME", "").trim(),"UTF-8");
        queryType = atx.getStringValue("QUERY_TYPE", "").trim();
        return 1;
    }
}