package com.hanthink.zzcj.action;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.zzcj.dao.TabCycleTimeDao;
import com.hanthink.pub.dao.SysParamDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.Tools;

public class ExportTabCycleTimeByBeatAction extends ActionImp {
    private Connection con = null;
    private Date date;
    private String EventData13;
    private String EventData1;
    private String EventData2;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【节拍报表：TAB_CYCLE_TIME】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
    	
    	String title = "节拍报表";
        String fileName = "节拍报表";
        
        String[] headers = Tools.getStrs(atx.getArrayValue("HEADER"));
        String[] columns = Tools.getStrs(atx.getArrayValue("COLUMN"));
        String[] widthTmps = Tools.getStrs(atx.getArrayValue("WIDTH"));

        int maxExportPageSize = Tools.getInt(SysParamDao.getSysParamVal(con, "EXPORT_PAGE_SIZE")).intValue();
        int totalCount = 0;

        totalCount = TabCycleTimeDao.countTabCycleTimeBeat(con, date, EventData13, EventData1,EventData2);

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

        List beanList = TabCycleTimeDao.queryTabCycleTimeBeat(con, date, EventData13, EventData1,EventData2);

        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap beanMap = (DynaBeanMap) beanList.get(i);
        }

        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();
        ExcelUtil.exportExcel(title, headers, columns, widths, beanList, out);
        out.flush();

        return 1;
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        date = DateUtils.parse(atx.getStringValue("WORKDATE"), DictConstants.FORMAT_DATE);
        EventData13=java.net.URLDecoder.decode(atx.getStringValue("EventDate14", "").trim(),"UTF-8");
        EventData1 = atx.getStringValue("EventDate1", "").trim();
        EventData2 = atx.getStringValue("EventDate2", "").trim();

        return 1;
    }

}