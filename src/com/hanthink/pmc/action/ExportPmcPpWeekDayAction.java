package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;
import com.hanthink.pmc.dao.PmcPpStartRateDao;
import com.hanthink.pmc.dao.PmcWeekDayStartRateDao;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.JfreeChartPicturePO;
import com.hanthink.util.JfreeChartUtil;
import com.hanthink.util.Tools;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import javax.servlet.ServletOutputStream;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExportPmcPpWeekDayAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private String banci;
    private String queryType;
    private String YYYY;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【开动率月报表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        if("1".equals(queryType)){
            whereSQL=" AND (PRODUCTIONLINE='DLHD3' or PRODUCTIONLINE='FDRDL' or PRODUCTIONLINE='FDRDR' or PRODUCTIONLINE='FRDL2' or PRODUCTIONLINE='FRDL3' or PRODUCTIONLINE='FRDR2' or PRODUCTIONLINE='HDTG2' or PRODUCTIONLINE='XHDTG') ";
        }else if("2".equals(queryType)){
            whereSQL=" AND PRODUCTIONLINE!='DLHD3' AND PRODUCTIONLINE!='FDRDL' AND PRODUCTIONLINE!='FDRDR' AND PRODUCTIONLINE!='FRDL2' AND PRODUCTIONLINE!='FRDL3' AND PRODUCTIONLINE!='FRDR2' AND PRODUCTIONLINE !='HDTG2' AND PRODUCTIONLINE !='XHDTG' ";
        }
    	String fileName = "开动率汇总报表";
    	String title = "开动率汇总报表";

//导出表格
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

        List beanList = PmcWeekDayStartRateDao.queryWeekDayStartRateD(con,YYYY,"",whereSQL);
        atx.getHttpResponse().setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xlsx");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();
        ExcelUtil.exportExcelXlsx(fileName, headers, columns, widths, beanList, out);
        out.flush();
        return 1;
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        queryType=atx.getStringValue("queryType", "");
        YYYY=atx.getStringValue("YYYY","");
        return 1;
    }
}
