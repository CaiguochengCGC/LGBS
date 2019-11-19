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

import com.hanthink.pmc.dao.PmcAlarmLogDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.Tools;

public class ExportPpAlarmLogAction extends ActionImp {
    private Connection con = null;
    private Date startTime;
    private Date endTime;
    private String proLine;
    private String data7;
    private String data3;
    private String banci;
    private String queryTypeT;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【故障记录：ALARM_LOG】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
        String whereSQL="";
        if("1".equals(queryTypeT)){
            whereSQL=" AND (PRODUCTION='DLHD3' or PRODUCTION='FDRDL' or PRODUCTION='FDRDR' or PRODUCTION='FRDL2' or PRODUCTION='FRDL3' or PRODUCTION='FRDR2' or PRODUCTION='HDTG2' or PRODUCTION='XHDTG') ";
        }else if("2".equals(queryTypeT)){
            whereSQL=" AND PRODUCTION!='DLHD3' AND PRODUCTION!='FDRDL' AND PRODUCTION!='FDRDR' AND PRODUCTION!='FRDL2' AND PRODUCTION!='FRDL3' AND PRODUCTION!='FRDR2' AND PRODUCTION !='HDTG2' AND PRODUCTION !='XHDTG' ";
        }
        String fileName = "工段停线与报警对应";
        String title = "工段停线与报警对应";
        
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

//        List beanList = PmcAlarmLogDao.queryAlarmLog(con, startTime, endTime,proLine, data2, data3);
        List beanList = PmcAlarmLogDao.ExprotPpAlarmLogImport(con, startTime, endTime, proLine, data7, data3,banci,whereSQL);
        /*for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap beanMap = (DynaBeanMap) beanList.get(i);
        }*/

        atx.getHttpResponse().setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xlsx");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();
        ExcelUtil.exportExcelXlsx(fileName, headers, columns, widths, beanList, out);
        out.flush();   
        return 1;
    }
    
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        startTime = DateUtils.parse(atx.getStringValue("START_EFFECT_TIME"), DictConstants.FORMAT_DATETIME);
        endTime = DateUtils.parse(atx.getStringValue("END_EFFECT_TIME"), DictConstants.FORMAT_DATETIME);
        proLine=java.net.URLDecoder.decode(atx.getStringValue("PRODUCTIONLINENAME", "").trim(),"UTF-8");
        data3=java.net.URLDecoder.decode(atx.getStringValue("DATA3", "").trim(),"UTF-8");
        data7 = atx.getStringValue("DATA7", "").trim();
        banci=atx.getStringValue("BANCI", "");
        queryTypeT=atx.getStringValue("queryType", "");
        return 1;
    }

}
