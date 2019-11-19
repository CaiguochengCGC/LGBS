package com.hanthink.yqcj.action;

import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.yqcj.dao.PmcEquipmentStopDao;
import com.hanthink.yqcj.dao.PmcEquipmentStoplineDao;
import com.hanthink.pub.dao.SysParamDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.JfreeChartPicturePO;
import com.hanthink.util.JfreeChartUtil;
import com.hanthink.util.Tools;

public class ExportPmcCycleSpeedWeekAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private java.lang.String proline;
    private String banci;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【循环速度周报表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {

        String fileName = "循环速度周报表";
        String title = "循环速度周报表";
        
        String[] headers = Tools.getStrs(atx.getArrayValue("HEADER"));
        String[] columns = Tools.getStrs(atx.getArrayValue("COLUMN"));
        String[] widthTmps = Tools.getStrs(atx.getArrayValue("WIDTH"));

        int maxExportPageSize = Tools.getInt(SysParamDao.getSysParamVal(con, "EXPORT_PAGE_SIZE")).intValue();
        int totalCount = 0;
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
        
        //转换日期，变为年-月-日 08:00:00
        Calendar  calendar = Calendar.getInstance();
        calendar.setTime(firstOfWeek);
        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 8, 0);
        firstOfWeek = calendar.getTime();
        calendar.setTime(lastOfWeek);
        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 8, 0);
        lastOfWeek = calendar.getTime();
        
        totalCount = PmcEquipmentStoplineDao.countPmcEquipmentStopLineWeek(con, firstOfWeek, lastOfWeek);

        if (totalCount > maxExportPageSize) {
            atx.getHttpResponse().sendRedirect("js/export/export_error.html?limit=" + maxExportPageSize);
            return 1;
        }

        for (int i = 0; i < headers.length; i++) {
            headers[i] = Tools.encodeUTF8(headers[i]);
        }

        int[] widths = new int[widthTmps.length];
        for (int i = 0; i < widthTmps.length; i++) {
            widths[i] = Tools.getInt(widthTmps[i], Integer.valueOf(100)).intValue();
        }
            
        List beanList = PmcEquipmentStoplineDao.queryWeekPmcEquipmentStopline(con, firstOfWeek, lastOfWeek,banci);

        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap localDynaBeanMap = (DynaBeanMap) beanList.get(i);
        }

        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();
        
//导出图片       
        // 创建CategoryDataset对象（准备数据）
        String[] legendKeys = new String[] {  "停机总时间" };// 图例名称
        String[] dataKeys = new String[] { "CYCLETIME" };// 数据字段名称
        String[] xAixIndex = new String[] { "PRODUCTIONLINE" };// 数据字段名称
        DefaultPieDataset dataset = JfreeChartUtil.createPieDataset(beanList, legendKeys, dataKeys, xAixIndex);

        // 根据Dataset 生成JFreeChart对象
        String[] colors = new String[] { "#0000FF", "#FF0000", "#00FF00" };
        Font fontLabel = new Font("SimSun", 10, 20); // 标题等label字体设置
        Font fontxAix = new Font("SimSun", 10, 15); // 横坐标label字体设置
        JFreeChart freeChart = JfreeChartUtil.createPieChart(dataset, fileName, "", "循环速度周报表", fontLabel, fontxAix, colors, true);

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
        
        ExcelUtil.exportExcelAll(title, headers, columns, widths, beanList, out, chartPictures, out1, 1366, 768, fileName);
        
        out.flush();

        logger.info("导出循环速度周报表");

        atx.setObjValue("", "循环速度周报表");
        
        return 1;
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),"yyyy");
        banci=atx.getStringValue("BANCI","");
        return 1;
    }
}