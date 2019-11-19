package com.hanthink.pmc.action;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pmc.dao.TabStopLineDao;
import com.hanthink.pub.dao.PubDataDictDao;
import com.hanthink.pub.dao.SysParamDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.Tools;

public class ExportTabStopLineAction extends ActionImp {
    private Connection con = null;
    private java.util.Date startPlanDate;
    private java.util.Date endPlanDate;
    private String eventdate46;
    private String eventdate1;
    private String eventdate3;
    private String banci;

    private String queryTypeT;
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【工段停线记录表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        if("1".equals(queryTypeT)){
            whereSQL=" AND (EventData40='DLHD3' or EventData40='FDRDL' or EventData40='FDRDR' or EventData40='FRDL2' or EventData40='FRDL3' or EventData40='FRDR2' or EventData40='HDTG2' or EventData40='XHDTG') ";
        }else if("2".equals(queryTypeT)){
            whereSQL=" AND EventData40!='DLHD3' AND EventData40!='FDRDL' AND EventData40!='FDRDR' AND EventData40!='FRDL2' AND EventData40!='FRDL3' AND EventData40!='FRDR2' AND EventData40 !='HDTG2' AND EventData40 !='XHDTG' ";
        }
        String[] headers = Tools.getStrs(atx.getArrayValue("HEADER"));
        String[] columns = Tools.getStrs(atx.getArrayValue("COLUMN"));
        String[] widthTmps = Tools.getStrs(atx.getArrayValue("WIDTH"));

        int maxExportPageSize = Tools.getInt(SysParamDao.getSysParamVal(con, "EXPORT_PAGE_SIZE")).intValue();
        int totalCount = 0;

        totalCount = TabStopLineDao.countTabStopLine(con, startPlanDate, endPlanDate,eventdate46,eventdate1,eventdate3,whereSQL);
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

        String fileName = "工段停线记录表";
        List beanList = TabStopLineDao.queryTabStopLine1(con, startPlanDate, endPlanDate,eventdate46,eventdate1,eventdate3,banci,whereSQL);
        //获取责任部门
        List presponList = PubDataDictDao.queryPubDataDict(con, "ARESPON", "");
        Map<String, String> presponListMap = new HashMap<String, String>();
        for (Object object : presponList) {
            DynaBeanMap status = (DynaBeanMap) object;
            presponListMap.put((String) status.get("VALUE"), (String) status.get("TEXT"));
        }
        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap localDynaBeanMap = (DynaBeanMap) beanList.get(i);
            localDynaBeanMap.put("EventData1", presponListMap.get(Tools.getStr(localDynaBeanMap.get("EventData1"))));
        }

        atx.getHttpResponse().setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xlsx");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();
        ExcelUtil.exportExcelXlsx(fileName, headers, columns, widths, beanList, out);
        out.flush();
        return 1;
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        startPlanDate = DateUtils.parse(atx.getStringValue("START_PLAN_DATE", ""), DictConstants.FORMAT_DATETIME);
        endPlanDate = DateUtils.parse(atx.getStringValue("END_PLAN_DATE", ""), DictConstants.FORMAT_DATETIME);
        eventdate1 = atx.getStringValue("EventDate1", "").trim();
        eventdate3=java.net.URLDecoder.decode(atx.getStringValue("EventData3", "").trim(),"UTF-8");

        eventdate46=java.net.URLDecoder.decode(atx.getStringValue("EventDate46", "").trim(),"UTF-8");
        banci=atx.getStringValue("BANCI", "");
        queryTypeT=atx.getStringValue("queryType", "");
        return 1;
    }
}
