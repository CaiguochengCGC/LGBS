package com.hanthink.pmc.action;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pmc.dao.PmcStationCtDao;
import com.hanthink.pub.dao.SysParamDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.Tools;

public class ExportPmcStationCtAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private java.lang.String proline;
    private String banci;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【工位CT表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
        String[] headers = Tools.getStrs(atx.getArrayValue("HEADER"));
        String[] columns = Tools.getStrs(atx.getArrayValue("COLUMN"));
        String[] widthTmps = Tools.getStrs(atx.getArrayValue("WIDTH"));

        int maxExportPageSize = Tools.getInt(SysParamDao.getSysParamVal(con, "EXPORT_PAGE_SIZE")).intValue();
        int totalCount = 0;
        totalCount = PmcStationCtDao.countDayPmcStationCt(con, ppdate, proline);

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

        String fileName = "瓶颈工位查询按停机节拍查询日报表";
        List beanList = PmcStationCtDao.queryDayPmcStationCt(con, ppdate, proline,banci);

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
        ppdate = DateUtils.parse(atx.getStringValue("PPDATE"), DictConstants.FORMAT_DATE);
        proline=java.net.URLDecoder.decode(atx.getStringValue("PRODUCTIONLINENAME", "").trim(),"UTF-8");
        banci=atx.getStringValue("BANCI", "");
        return 1;
    }
}