package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;
import com.hanthink.pmc.dao.TabStopLineDao;
import com.hanthink.pub.dao.PubDataDictDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.Tools;

import javax.servlet.ServletOutputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportTabStopLineDetailAction extends ActionImp {
    private Connection con = null;
    private java.util.Date startTime;
    private java.util.Date endTime;
    private String lineCode;
    private String stopType;
    private String shift;
    private String queryTypeT;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【导出停线查询表】", ex);
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
    	
        for (int i = 0; i < headers.length; i++) {
            headers[i] = Tools.encodeUTF8(headers[i]);
        }

        int[] widths = new int[widthTmps.length];
        for (int i = 0; i < widthTmps.length; i++) {
            widths[i] = Tools.getInt(widthTmps[i], Integer.valueOf(100)).intValue();
        }

        String fileName = "停线详情报表";

            List beanList1= TabStopLineDao.exportqueryStopDetail1(con, startTime, endTime, lineCode, stopType,shift,whereSQL);
            atx.getHttpResponse().setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xlsx");
            ServletOutputStream out = atx.getHttpResponse().getOutputStream();
            ExcelUtil.exportExcelXlsx(fileName, headers, columns, widths, beanList1, out);
            return 1;
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        startTime = DateUtils.parse(atx.getStringValue("START_TIME"), DictConstants.FORMAT_DATE);
        endTime   = DateUtils.parse(atx.getStringValue("END_TIME"), DictConstants.FORMAT_DATE);
        lineCode = atx.getStringValue("LINECODE", "").trim();
        stopType = atx.getStringValue("STOPTYPE", "").trim();
        shift = atx.getStringValue("SHIFT", "").trim();
        queryTypeT=atx.getStringValue("queryType", "");
        return 1;
    }
}
