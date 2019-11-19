package com.hanthink.cycj.action;

import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.cycj.dao.PmcPpWeekDao;
import com.hanthink.pub.dao.SysParamDao;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.JfreeChartPicturePO;
import com.hanthink.util.JfreeChartUtil;
import com.hanthink.util.Tools;

public class ExportPmcPpWeekAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private String banci;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【产量周报表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {

        String fileName = "产量周报表";
        String fileName1 = "产量周报表折线图";
        String fileName2 = "产量周报表柱状图";
        String title = "产量周报表";
        
//导出表格数据
        //String[] headers = Tools.getStrs(atx.getArrayValue("HEADER"));
        //String[] columns = Tools.getStrs(atx.getArrayValue("COLUMN"));
        String[] widthTmps = Tools.getStrs(atx.getArrayValue("WIDTH"));

        String[] columns = {"PRODUCTIONLINE","PRODUCTIONLINENAME","BANCI","YYPLANP","YYREALP","WEEKRATE","DATA1", "DATA2", "DATA3", "DATA4", "DATA5", "DATA6", "DATA7", "DATA8", "DATA9"
                , "DATA10", "DATA11", "DATA12", "DATA13", "DATA14", "DATA15", "DATA16", "DATA17", "DATA18", "DATA19", "DATA20", "DATA21"
                , "DATA22", "DATA23", "DATA24", "DATA25", "DATA26", "DATA27", "DATA28", "DATA29", "DATA30", "DATA31", "DATA32", "DATA33"
                , "DATA34", "DATA35", "DATA36", "DATA37", "DATA38", "DATA39", "DATA40", "DATA41", "DATA42", "DATA43", "DATA44", "DATA45"
                , "DATA46", "DATA47", "DATA48", "DATA49", "DATA50", "DATA51", "DATA52"};
        String[] headers = {"工段/周","工段名字","班次","计划产量","总产量","实际完成率","第1周","第2周","第3周","第4周","第5周","第6周","第7周","第8周","第9周","第10周","第11周","第12周","第13周",
        		"第14周","第15周","第16周","第17周","第18周","第19周","第20周","第21周","第22周","第23周","第24周","第25周","第26周","第27周","第28周","第29周","第30周","第31周","第32周","第33周","第34周","第35周",
        		"第36周","第37周","第38周","第39周","第40周","第41周","第42周","第43周","第44周","第45周","第46周","第47周","第48周","第49周","第50周","第51周","第52周"};
        
        int maxExportPageSize = Tools.getInt(SysParamDao.getSysParamVal(con, "EXPORT_PAGE_SIZE")).intValue();
        int totalCount = 0;
        totalCount = PmcPpWeekDao.countPmcPpWeek(con, ppdate);

        if (totalCount > maxExportPageSize) {
            atx.getHttpResponse().sendRedirect("js/export/export_error.html?limit=" + maxExportPageSize);
            return 1;
        }

        /*for (int i = 0; i < headers.length; i++) {
            headers[i] = Tools.encodeUTF8(headers[i]);
        }*/

        int[] widths = new int[widthTmps.length];
        for (int i = 0; i < widthTmps.length; i++) {
            widths[i] = Tools.getInt(widthTmps[i], Integer.valueOf(100)).intValue();
        }

        List beanList = PmcPpWeekDao.queryPmcPpWeek(con, ppdate,banci);

        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap localDynaBeanMap = (DynaBeanMap) beanList.get(i);
        }

        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();
//导出图片
     // 创建CategoryDataset对象（准备数据）
        String[] legendKeys = new String[] {  "计划周产量","实际周产量" };// 图例名称
        String[] dataKeys = new String[] { "YYPLANP","YYREALP" };// 数据字段名称
        String[] xAixIndex = new String[] { "PRODUCTIONLINE" };// 数据字段名称
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
        
        logger.info("导出产量周报表");

        atx.setObjValue("", "产量周报表");
        
        return 1; 
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        ppdate = DateUtils.parse(atx.getStringValue("YYYY"), "yyyy");
        banci=atx.getStringValue("BANCI","");
        return 1;
    }
}