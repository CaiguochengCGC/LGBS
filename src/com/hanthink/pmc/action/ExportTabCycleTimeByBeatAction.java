package com.hanthink.pmc.action;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pmc.dao.TabCycleTimeDao;
import com.hanthink.pub.dao.SysParamDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.Tools;

public class ExportTabCycleTimeByBeatAction extends ActionImp {
    private Connection con = null;
    private String EventData13;
    private String EventData1;
    private String EventData2;
    private java.util.Date startTime;
    private java.util.Date endTime;
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private String banci;
    private String queryTypeT;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【节拍报表：TAB_CYCLE_TIME】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        if("1".equals(queryTypeT)){
            whereSQL=" AND (LINE_ENG='DLHD3' or LINE_ENG='FDRDL' or LINE_ENG='FDRDR' or LINE_ENG='FRDL2' or LINE_ENG='FRDL3' or LINE_ENG='FRDR2' or LINE_ENG='HDTG2' or LINE_ENG='XHDTG') ";
        }else if("2".equals(queryTypeT)){
            whereSQL=" AND LINE_ENG!='DLHD3' AND LINE_ENG!='FDRDL' AND LINE_ENG!='FDRDR' AND LINE_ENG!='FRDL2' AND LINE_ENG!='FRDL3' AND LINE_ENG!='FRDR2' AND LINE_ENG !='HDTG2' AND LINE_ENG !='XHDTG' ";
        }
    	String title = "节拍报表";
        String fileName = "节拍报表";
        
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

        List beanList = TabCycleTimeDao.queryTabCycleTimeBeat(con, startTime, EventData13, EventData1, EventData2, pager,banci,endTime,whereSQL);
        atx.getHttpResponse().setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xlsx");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();
        ExcelUtil.exportExcelXlsx(title, headers, columns, widths, beanList, out);
        out.flush();

        return 1;
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        queryTypeT=atx.getStringValue("queryType", "");
        startTime = DateUtils.parse(atx.getStringValue("START_PLAN_DATE"), DictConstants.FORMAT_DATETIME);
        endTime = DateUtils.parse(atx.getStringValue("END_PLAN_DATE"), DictConstants.FORMAT_DATETIME);
        EventData13 = atx.getStringValue("CT_CHANG", "").trim();
        EventData1 = atx.getStringValue("CAR_TYPE", "").trim();
        EventData2 = atx.getStringValue("STATION", "").trim();
        banci=atx.getStringValue("BANCI", "");
        return 1;
    }

}
