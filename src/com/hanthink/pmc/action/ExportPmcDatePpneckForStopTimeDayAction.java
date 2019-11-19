package com.hanthink.pmc.action;

import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pmc.dao.PmcDatePpneckDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.JfreeChartPicturePO;
import com.hanthink.util.JfreeChartUtil;
import com.hanthink.util.Tools;

public class ExportPmcDatePpneckForStopTimeDayAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private java.lang.String proline;
    private String banci;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "按停机次数Top查询【瓶颈设备表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {

        String fileName = "按停机时间查询瓶颈设备";
        String title = "按停机时间查询瓶颈设备";
        
        String[] headers = Tools.getStrs(atx.getArrayValue("HEADER"));
        String[] columns = Tools.getStrs(atx.getArrayValue("COLUMN"));
        String[] widthTmps = Tools.getStrs(atx.getArrayValue("WIDTH"));

        Date startDate = null;
        Date endDate = null;
        int timeType = 10;
        String yearTime = "";
        //获取前台参数
        String ppDate = atx.getStringValue("PPDATE");
        String queryType = atx.getStringValue("QUERY_TYPE", "").trim();
        Calendar calendar = Calendar.getInstance();
        
        if ("0".equals(queryType)) { // 日
        	fileName = "按停机时间查询瓶颈设备日报表";
        	title = "按停机时间查询瓶颈设备日报表";
            startDate = DateUtils.parse(ppDate, DictConstants.FORMAT_DATE);
            endDate = DateUtils.parse(ppDate, DictConstants.FORMAT_DATE);
            
        } else if ("1".equals(queryType)) { // 月
        	fileName = "按停机时间查询瓶颈设备月报表";
        	title = "按停机时间查询瓶颈设备月报表";
        	timeType = 7;
            startDate = DateUtils.parse(ppDate, "yyyy-MM");
            
            calendar.setTime(startDate);
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DATE, -1);
            endDate = calendar.getTime();
            
        } else if ("2".equals(queryType)) {  // 年
        	fileName = "按停机时间查询瓶颈设备年报表";
        	title = "按停机时间查询瓶颈设备年报表";
        	timeType = 4;
            startDate = DateUtils.parse(ppDate, "yyyy");
            
            calendar.setTime(startDate);
            calendar.add(Calendar.YEAR, 1);
            calendar.add(Calendar.DATE, -1);
            endDate = calendar.getTime();
            
        } else if ("3".equals(queryType)) { // 周
        	fileName = "按停机时间查询瓶颈设备周报表";
        	title = "按停机时间查询瓶颈设备周报表";
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
        List beanList = PmcDatePpneckDao.queryPmcDatePpneckForStopTimeByPager(con, pager, startDate, endDate, timeType,yearTime,banci).getRs();
        
        
        for (int i = 0; i < headers.length; i++) {
            headers[i] = Tools.encodeUTF8(headers[i]);
        }

        int[] widths = new int[widthTmps.length];
        for (int i = 0; i < widthTmps.length; i++) {
            widths[i] = Tools.getInt(widthTmps[i], Integer.valueOf(100)).intValue();
        }

//        List beanList = PmcDatePpneckDao.queryPmcDatePpneckForStopTime(con, ppdate);

        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap localDynaBeanMap = (DynaBeanMap) beanList.get(i);
        }

        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();

//        ExcelUtil.exportExcel(fileName, headers, columns, widths, beanList, out);
//        out.flush();
//
//        return 1;
//导出图片       
        // 创建柱状图CategoryDataset对象（准备数据）
        String[] legendKeys = new String[] {  "设备停机次数" };// 图例名称
        String[] dataKeys = new String[] { "STOPCOUNT" };// 数据字段名称
        String[] xAixIndex = new String[] { "EventData4" };// 数据字段名称
        CategoryDataset dataset = JfreeChartUtil.createDataset(beanList, legendKeys, dataKeys, xAixIndex);

        // 创建折线图CategoryDataset对象（准备数据）
        String[] legendKeys1 = new String[] {  "设备停机时间（分钟）" };// 图例名称
        String[] dataKeys1 = new String[] { "STOPTIME" };// 数据字段名称
        String[] xAixIndex1 = new String[] { "EventData4" };// 数据字段名称
        CategoryDataset datasetLine = JfreeChartUtil.createDataset(beanList, legendKeys1, dataKeys1, xAixIndex1);

        
        // 根据Dataset 生成JFreeChart对象
        String[] colors = new String[] { "#0000FF", "#FF0000", "#00FF00" };
        Font fontLabel = new Font("SimSun", 10, 20); // 标题等label字体设置
        Font fontxAix = new Font("SimSun", 10, 15); // 横坐标label字体设置
        JFreeChart freeChart = JfreeChartUtil.createBarAndLineChart(dataset,datasetLine, fileName, "", "次数", fontLabel, fontxAix, colors, true);

        HSSFClientAnchor anchor = new HSSFClientAnchor(beanList.size(), 0, beanList.size()+200, 0, (short) 0, beanList.size()+5, (short) 10, beanList.size()+40);
        anchor.setAnchorType(2);

        JfreeChartPicturePO chartPicturePO = new JfreeChartPicturePO();
        chartPicturePO.setFreeChart(freeChart);
        chartPicturePO.setAnchor(anchor);
        List<JfreeChartPicturePO> chartPictures = new ArrayList<JfreeChartPicturePO>();
        chartPictures.add(chartPicturePO);

        // 导出
        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out1 = atx.getHttpResponse().getOutputStream();

        //ExcelUtil.exportPicturesExcel(chartPictures, out, 1366, 768, fileName);
        
        ExcelUtil.exportExcelAll(title, headers, columns, widths, beanList, out, chartPictures, out1, 1366, 768, fileName);
        
        out.flush();

        logger.info("导出按停机时间查询瓶颈设备日报表");

        atx.setObjValue("", "按停机时间查询瓶颈设备日报表");
        
        return 1;
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        banci=atx.getStringValue("BANCI", "");
//        ppdate = DateUtils.parse(atx.getStringValue("PPDATE"), "yyyy-MM-dd");
        
        return 1;
    }
}