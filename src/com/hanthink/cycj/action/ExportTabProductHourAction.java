package com.hanthink.cycj.action;

import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.cycj.dao.PmcPpKpidayDao;
import com.hanthink.cycj.dao.TabProductHourDao;
import com.hanthink.pub.dao.SysParamDao;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.JfreeChartPicturePO;
import com.hanthink.util.JfreeChartUtil;
import com.hanthink.util.Tools;

public class ExportTabProductHourAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private String banci;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【产量日报表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {

        String fileName = "产量日报表";
        String fileName1 = "产量日报表折线图";
        String fileName2 = "产量日报表柱状图";
        String title = "产量日报表";
        
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
        List beanList = TabProductHourDao.queryTabStopLine(con, ppdate,banci);

        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap localDynaBeanMap = (DynaBeanMap) beanList.get(i);
        }

        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();
        
        
//导出图片         
        // 创建CategoryDataset对象（准备数据）
        String[] legendKeys = new String[] {  "计划产量","实际产量" };// 图例名称
        String[] dataKeys = new String[] { "PLANDATA","EventDate29" };// 数据字段名称
        String[] xAixIndex = new String[] { "EventDate1" };// 数据字段名称
        CategoryDataset dataset = JfreeChartUtil.createDataset(beanList, legendKeys, dataKeys, xAixIndex);

        // 根据Dataset 生成JFreeChart对象
        String[] colors = new String[] { "#0000FF", "#FF0000", "#00FF00" };
        Font fontLabel = new Font("SimSun", 10, 20); // 标题等label字体设置
        Font fontxAix = new Font("SimSun", 10, 15); // 横坐标label字体设置
        JFreeChart freeChart = JfreeChartUtil.createLineChart(dataset, fileName1, "", "", fontLabel, fontxAix, colors, true);
        JFreeChart freeChart1 = JfreeChartUtil.createBarChart(dataset, fileName2, "", "", fontLabel, fontxAix, colors, true);
        
        HSSFClientAnchor anchor = new HSSFClientAnchor(beanList.size(), 0, beanList.size()+200, 0, (short) 0, beanList.size()+5, (short) 10, beanList.size()+40);
        anchor.setAnchorType(2);
        HSSFClientAnchor anchor1 = new HSSFClientAnchor(beanList.size(), 10, beanList.size()+200, 10, (short) 10, beanList.size()+5, (short) 20, beanList.size()+40);
        anchor1.setAnchorType(2);

        JfreeChartPicturePO chartPicturePO = new JfreeChartPicturePO();
        chartPicturePO.setFreeChart(freeChart);
        chartPicturePO.setAnchor(anchor);
        JfreeChartPicturePO chartPicturePO1 = new JfreeChartPicturePO();
        chartPicturePO1.setFreeChart(freeChart1);
        chartPicturePO1.setAnchor(anchor1);
        
        List<JfreeChartPicturePO> chartPictures = new ArrayList<JfreeChartPicturePO>();
        chartPictures.add(chartPicturePO);
        chartPictures.add(chartPicturePO1);

        // 导出
        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out1 = atx.getHttpResponse().getOutputStream();
       
        ExcelUtil.exportExcelAll(title, headers, columns, widths, beanList, out, chartPictures, out1, 1366, 768, fileName);

        out.flush();
        out1.flush();
        
        logger.info("导出产量日报表");

        atx.setObjValue("", "产量日报表");
        
        return 1; 
        
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        ppdate = DateUtils.parse(atx.getStringValue("EventData"), "yyyy-MM-dd");
        banci = atx.getStringValue("BANCI", "");
        return 1;
    }
}