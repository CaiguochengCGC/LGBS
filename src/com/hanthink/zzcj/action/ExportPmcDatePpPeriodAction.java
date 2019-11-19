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

import com.hanthink.zzcj.dao.PmcDatePpDao;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.Tools;

public class ExportPmcDatePpPeriodAction extends ActionImp {
    private Connection con = null;
    private Date startDate;
    private Date endDate;
    private String productionLine;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【生产时间数据表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
        String[] headers = Tools.getStrs(atx.getArrayValue("HEADER"));
        String[] columns = Tools.getStrs(atx.getArrayValue("COLUMN"));
        String[] widthTmps = Tools.getStrs(atx.getArrayValue("WIDTH"));

        /*int maxExportPageSize = Tools.getInt(SysParamDao.getSysParamVal(con, "EXPORT_PAGE_SIZE")).intValue();
        int totalCount = 0;

        totalCount = PmcDatePpDao.countPmcDatePpPeriod(con, startDate, endDate, productionLine);

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

        String fileName = "实际生产时间查询";
//        List beanList = PmcDatePpDao.queryPmcDatePpPeriod(con, startDate, endDate, productionLine);

        List beanList = PmcDatePpDao.queryPmcDatePpPeriodAgen(con, startDate, endDate, productionLine);

        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap localDynaBeanMap = (DynaBeanMap) beanList.get(i);
        }

        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();
        ExcelUtil.exportExcel(fileName, headers, columns, widths, beanList, out);
        out.flush();

        return 1;
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        startDate = DateUtils.parse(atx.getStringValue("START_PLAN_DATE", ""), "yyyy-MM-dd");
        endDate = DateUtils.parse(atx.getStringValue("END_PLAN_DATE", ""), "yyyy-MM-dd");
        productionLine = java.net.URLDecoder.decode(atx.getStringValue("PRODUCTIONLINENAME", ""),"UTF-8");
        
        return 1;
    }
}