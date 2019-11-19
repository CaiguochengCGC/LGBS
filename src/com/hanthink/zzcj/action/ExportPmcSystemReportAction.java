package com.hanthink.zzcj.action;

import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;

import com.hanthink.zzcj.dao.PmcSystemReportDao;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.JfreeChartPicturePO;
import com.hanthink.util.JfreeChartUtil;
import com.hanthink.util.Tools;

public class ExportPmcSystemReportAction extends ActionImp {
	private Connection con = null;
    private String workDate;
    private String banci;
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "导出总装车间总报表", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
    	
        String fileName = "总装车间总报表";
        String fileName1 = "总装车间总报表柱状图";
        String title = "总装车间总报表";
        
//导出表格数据        
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

        List beanList = PmcSystemReportDao.QueryPmcSystemReport(con, workDate,banci);
      //查询休息时间
        List restTime = PmcSystemReportDao.getRestTime(con, workDate);
        DynaBeanMap dbmRest = (DynaBeanMap)restTime.get(0);
//        for (int i=0; i<beanList.size();i++) {
//        	DynaBeanMap dbm = (DynaBeanMap) beanList.get(i);
//        	double restTimeD = Tools.getDouble(dbmRest.get("RESTTIME"),0.0);
//        	double banCi = Tools.getDouble(dbm.get("BANCI"),0.0);
//        	dbm.remove("BANCI");
//        	if(banCi==1.0){
//        		dbm.put("BANCI", "一班");
//        	}
//        	else if(banCi==2.0){
//        		dbm.put("BANCI", "二班");
//        	}
//        	else{
//        		dbm.put("BANCI", "全部");
//        	}
//        	/*开动率为空值时不要报故障*/
//        	double tempddrate;
//        	if (dbm.get("DDRATE") == null){
//        		tempddrate=0.001;
//        	}else{
//        		tempddrate=Tools.getDouble(dbm.get("DDRATE"), 0.001);
//        	}
//        	double ddrate = Tools.rounding(tempddrate, 2);
//        	dbm.put("DDRATE", ddrate);
//        	//除去休息的停线时间 
//        	double stopRest = Tools.rounding((Tools.getDouble(dbm.get("STOPTIME"),0.0) - restTimeD),2);
//        	if (stopRest <= 0) {
//        		stopRest = 0;
//        	}
//        	dbm.put("RESTTIME", stopRest);
//        }
        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap localDynaBeanMap = (DynaBeanMap) beanList.get(i);
        }

        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();

//导出图片
        
     // 创建CategoryDataset对象（准备数据）
        String[] legendKeys = new String[] {  "开动率","产能完成率" };// 图例名称
        String[] dataKeys = new String[] { "DDRATE","channengwanclv" };// 数据字段名称
        String[] xAixIndex = new String[] { "PRODUCTIONLINE" };// 数据字段名称
        CategoryDataset dataset = JfreeChartUtil.createDataset(beanList, legendKeys, dataKeys, xAixIndex);

        // 根据Dataset 生成JFreeChart对象
        String[] colors = new String[] { "#0000FF", "#FF0000", "#00FF00" };
        Font fontLabel = new Font("SimSun", 10, 20); // 标题等label字体设置
        Font fontxAix = new Font("SimSun", 10, 15); // 横坐标label字体设置
        JFreeChart freeChart = JfreeChartUtil.createBarChart(dataset, fileName1, "", "", fontLabel, fontxAix, colors, true);
        
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
        out1.flush();
        
        logger.info("导出PMC车身车间总报表");

        atx.setObjValue("", "PMC车身车间总报表");
        
        return 1; 
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
//        WORKDATE = DateUtils.parse(atx.getStringValue("WORKDATE"),DictConstants.FORMAT_DATE);
        workDate = atx.getStringValue("WORKDATE","");
        banci=atx.getStringValue("PRODUCTIONLINENAME","");
        return 1;
    }
}