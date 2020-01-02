package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import com.hanthink.pmc.dao.PmcWeekDayStartRateDao;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.Tools;

import javax.servlet.ServletOutputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class ExportPmcPpWeekRemarkDayAction extends ActionImp {
    private Connection con = null;
    private int ID;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【开动率月报表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
    	String fileName = "开动率汇总备注报表";
    	String title = "开动率汇总备注报表";

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

        List beanList = PmcWeekDayStartRateDao.queryWeekDayStartRateDExcel(con,ID,whereSQL);
        atx.getHttpResponse().setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xlsx");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();
        ExcelUtil.exportExcelXlsx(fileName, headers, columns, widths, beanList, out);
        out.flush();
        return 1;
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        ID=atx.getIntegerValue("ID");
        return 1;
    }
}
