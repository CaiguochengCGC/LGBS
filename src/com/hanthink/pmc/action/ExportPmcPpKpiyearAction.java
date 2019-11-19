package com.hanthink.pmc.action;

import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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

import com.hanthink.pmc.dao.PmcPpKpidayDao;
import com.hanthink.pmc.dao.PmcPpKpiyearDao;
import com.hanthink.pub.dao.SysParamDao;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.JfreeChartPicturePO;
import com.hanthink.util.JfreeChartUtil;
import com.hanthink.util.Tools;

public class ExportPmcPpKpiyearAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private String banci;
    private String queryType;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【KPI年报表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        if("1".equals(queryType)){
            whereSQL=" AND (PRODUCTIONLINE='DLHD3' or PRODUCTIONLINE='FDRDL' or PRODUCTIONLINE='FDRDR' or PRODUCTIONLINE='FRDL2' or PRODUCTIONLINE='FRDL3' or PRODUCTIONLINE='FRDR2' or PRODUCTIONLINE='HDTG2' or PRODUCTIONLINE='XHDTG') ";
        }else if("2".equals(queryType)){
            whereSQL=" AND PRODUCTIONLINE!='DLHD3' AND PRODUCTIONLINE!='FDRDL' AND PRODUCTIONLINE!='FDRDR' AND PRODUCTIONLINE!='FRDL2' AND PRODUCTIONLINE!='FRDL3' AND PRODUCTIONLINE!='FRDR2' AND PRODUCTIONLINE !='HDTG2' AND PRODUCTIONLINE !='XHDTG' ";
        }
    	
    	String title = "KPI年报表";
    	String fileName = "KPI年报表";
    	
//导出表格
        String[] headers = Tools.getStrs(atx.getArrayValue("HEADER"));
        String[] columns = Tools.getStrs(atx.getArrayValue("COLUMN"));
        String[] widthTmps = Tools.getStrs(atx.getArrayValue("WIDTH"));

        int maxExportPageSize = Tools.getInt(SysParamDao.getSysParamVal(con, "EXPORT_PAGE_SIZE")).intValue();
        int totalCount = 0;
        totalCount = PmcPpKpiyearDao.countPmcPpKpiyear(con, ppdate);

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
   
        List beanList = PmcPpKpiyearDao.queryPmcPpKpiyear(con, ppdate,banci,whereSQL);

        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap localDynaBeanMap = (DynaBeanMap) beanList.get(i);
        }

        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();
        
//导出图片       
        List list = new LinkedList();
        list = PmcPpKpiyearDao.queryPmcPpKpiYearPic(con, ppdate,banci);

        // 创建CategoryDataset对象（准备数据）
        String[] legendKeys = new String[] {  "当年MTTR(分)","当年MTBF(分)","当年设备利用率","当年设备停线率","当年停线时间(分)","当年停线次数"};// 图例名称
        String[] dataKeys = new String[] { "MTTR","MTBF","EQMRATE","EQPSTOP","STOPTIME","STOPCOUNT" };// 数据字段名称
        String[] xAixIndex = new String[] { "PRODUCTIONLINE" };// 数据字段名称
        CategoryDataset dataset = JfreeChartUtil.createDataset(list, legendKeys, dataKeys, xAixIndex);

        // 根据Dataset 生成JFreeChart对象
        String[] colors = new String[] { "#0000FF", "#FF0000", "#00FF00" };
        Font fontLabel = new Font("SimSun", 10, 20); // 标题等label字体设置
        Font fontxAix = new Font("SimSun", 10, 15); // 横坐标label字体设置
        JFreeChart freeChart = JfreeChartUtil.createLineChart(dataset, fileName, "", "KPI", fontLabel, fontxAix, colors, true);

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

        logger.info("导出KPI年报表");

        atx.setObjValue("", "KPI年报表");
        
        return 1;
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        queryType=atx.getStringValue("queryType", "");
        ppdate = DateUtils.parse(atx.getStringValue("YYYY"), "yyyy");
        banci=atx.getStringValue("BANCI", "");
        return 1;
    }
}
