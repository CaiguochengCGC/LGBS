package com.hanthink.pmc.action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;

import com.hanthink.pmc.dao.TabproducttypeDao;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.Tools;

public class ExportTabproducttypeAction extends ActionImp {
    private Connection con = null;
    private String startDate;
    private String endDate;
    private String productionLine;
    private String sheft;
    private String banci;
    private String queryTypeT;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【tabProductType】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
        String[] headers = Tools.getStrs(atx.getArrayValue("HEADER"));
        String[] columns = Tools.getStrs(atx.getArrayValue("COLUMN"));
        String[] widthTmps = Tools.getStrs(atx.getArrayValue("WIDTH"));
        String whereSQL="";
        if("1".equals(queryTypeT)){
            whereSQL=" AND (LineCode='DLHD3' or LineCode='FDRDL' or LineCode='FDRDR' or LineCode='FRDL2' or LineCode='FRDL3' or LineCode='FRDR2' or LineCode='HDTG2' or LineCode='XHDTG') ";
        }else if("2".equals(queryTypeT)){
            whereSQL=" AND LineCode!='DLHD3' AND LineCode!='FDRDL' AND LineCode!='FDRDR' AND LineCode!='FRDL2' AND LineCode!='FRDL3' AND LineCode!='FRDR2' AND LineCode !='HDTG2' AND LineCode !='XHDTG' ";
        }
        //int maxExportPageSize = Tools.getInt(SysParamDao.getSysParamVal(con, "EXPORT_PAGE_SIZE")).intValue();
        /*int totalCount = 0;

        totalCount = TabproducttypeDao.countTabproducttype(con, startDate, endDate, productionLine);

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

        String fileName = "产量查询信息";
        List beanList = TabproducttypeDao.queryTabproducttype(con, startDate, endDate, productionLine,banci,whereSQL);

        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap beanMap = (DynaBeanMap) beanList.get(i);

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

        startDate = atx.getStringValue("START_PLAN_DATE", "");
        endDate = atx.getStringValue("END_PLAN_DATE", "");
        productionLine=java.net.URLDecoder.decode(atx.getStringValue("EventDate18", ""),"UTF-8");
        banci=atx.getStringValue("BANCI", "");
        queryTypeT=atx.getStringValue("queryType", "");
        return 1;
    }
}
