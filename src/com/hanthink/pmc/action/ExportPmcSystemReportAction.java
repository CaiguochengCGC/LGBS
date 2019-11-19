package com.hanthink.pmc.action;

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

import com.hanthink.pmc.dao.PmcSystemReportDao;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.JfreeChartPicturePO;
import com.hanthink.util.JfreeChartUtil;
import com.hanthink.util.Tools;

public class ExportPmcSystemReportAction extends ActionImp {
	private Connection con = null;
//    private java.util.Date WORKDATE;
    private String workDate;
    private String banci;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【PMC车身车间总报表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
    	
        String fileName = "PMC车身车间总报表";
        String fileName1 = "PMC车身车间总报表柱状图";
        String title = "PMC车身车间总报表";
        
//导出表格数据        
        String[] headers = Tools.getStrs(atx.getArrayValue("HEADER"));
        String[] columns = Tools.getStrs(atx.getArrayValue("COLUMN"));
        String[] widthTmps = Tools.getStrs(atx.getArrayValue("WIDTH"));

        /*int maxExportPageSize = Tools.getInt(SysParamDao.getSysParamVal(con, "EXPORT_PAGE_SIZE")).intValue();
        int totalCount = 0;
        totalCount = PmcSystemReportDao.countPmcSystemReport(con, WORKDATE);

        System.out.println("******************************");
        System.out.println(totalCount);
        System.out.println("******************************");
        
        if (totalCount > maxExportPageSize) {
            atx.getHttpResponse().sendRedirect("js/export/export_error.html?limit=" + maxExportPageSize);
            return 1;
        }*/

        for (int i = 0; i < headers.length; i++) {
            headers[i] = Tools.encodeUTF8(headers[i]);
        }

        int[] widths = new int[widthTmps.length];
        for (int i = 0; i < widthTmps.length; i++) {
            widths[i] = Tools.getInt(widthTmps[i], Integer.valueOf(100)).intValue();
        }

        List beanList = PmcSystemReportDao.QueryPmcSystemReportExport(con, workDate,banci);
      //查询休息时间
        List restTime = PmcSystemReportDao.getRestTime(con, workDate);
        DynaBeanMap dbmRest = (DynaBeanMap)restTime.get(0);
        for (int i=0; i<beanList.size();i++) {
        	DynaBeanMap dbm = (DynaBeanMap) beanList.get(i);
//        	double restTimeD = Tools.getDouble(dbmRest.get("RESTTIME"),0.0);
//        	/*//实际生产时间
//        	double acttime = ((Tools.getDouble(dbm.get("REALTIME"),0.0)*60.0) - restTimeD)/60.0;
//        	if (acttime <= 0) {
//        		acttime = 0;
//        	}
//        	dbm.put("REALTIME", acttime);*/
//        	//double banci=Tools.rounding(Tools.getDouble(dbm.get("BANCI"), 0.0), 2);
//        	double ddrate = Tools.rounding(Tools.getDouble(dbm.get("DDRATE"), 0.0), 2);
//        	dbm.put("DDRATE", ddrate);
//        	//除去休息的停线时间 
//        	double stopRest = Tools.rounding((Tools.getDouble(dbm.get("STOPTIME"),0.0) - restTimeD),2);
//        	if (stopRest <= 0) {
//        		stopRest = 0;
//        	}
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
//        	dbm.put("RESTTIME", stopRest);
        	if("ZPTG".equals(dbm.get("PRODUCTIONLINE"))){
        		//实际产量
        		List zpAct = PmcSystemReportDao.getProductActNum(con, "ZPTG", workDate);
        		//计划产量
        		List zpPlan = PmcSystemReportDao.getProductPlanNum(con, "ZPTG", workDate);
        		//实际产量
        		dbm.put("EventDate29", ((DynaBeanMap)zpAct.get(0)).get("EVENTDATA4"));
        		//计划产量
        		dbm.put("PLANCOUNT", ((DynaBeanMap)zpPlan.get(0)).get("EventDate31"));
        		
        	}else if ("ASHDL".equals(dbm.get("PRODUCTIONLINE"))){
        		List asAct = PmcSystemReportDao.getProductActNum(con, "ASHDL", workDate);
        		List asPlan = PmcSystemReportDao.getProductPlanNum(con, "ASHDL", workDate);
        		//实际产量
        		dbm.put("EventDate29", ((DynaBeanMap)asAct.get(0)).get("EVENTDATA4"));
        		//计划产量
        		dbm.put("PLANCOUNT", ((DynaBeanMap)asPlan.get(0)).get("EventDate31"));
        	}
        }
//        for (int i = 0; i < beanList.size(); i++) {
//            DynaBeanMap localDynaBeanMap = (DynaBeanMap) beanList.get(i);
//        }

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
        banci=atx.getStringValue("BANCI","");
        return 1;
    }
}