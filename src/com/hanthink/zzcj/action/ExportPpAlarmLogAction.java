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

import com.hanthink.zzcj.dao.PmcAlarmLogDao;
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

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【故障记录：ALARM_LOG】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {

        String fileName = "工段停线与报警对应表";
        String title = "工段停线与报警对应表";
        
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
        List beanList = PmcAlarmLogDao.ExprotPpAlarmLogImport(con, startTime, endTime, proLine, data7, data3,banci);
        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap beanMap = (DynaBeanMap) beanList.get(i);
            //判断ALARMSTARTTIME是否为空，如果为空，此处会报错
            if(beanMap.get("ALARMSTARTTIME")!=null) {
                if (beanMap.get("ALARMSTARTTIME").toString().indexOf("1900") != -1) {
                    beanMap.put("ALARMSTARTTIME", "");
                }
            }
            if(beanMap.get("ALARMENDTIME")!=null)
                if (beanMap.get("ALARMENDTIME").toString().indexOf("1900") != -1) {
                    beanMap.put("ALARMENDTIME", "");
                }
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

        startTime = DateUtils.parse(atx.getStringValue("START_EFFECT_TIME"), DictConstants.FORMAT_DATETIME);
        endTime = DateUtils.parse(atx.getStringValue("END_EFFECT_TIME"), DictConstants.FORMAT_DATETIME);
        proLine=java.net.URLDecoder.decode(atx.getStringValue("PRODUCTIONLINENAME", "").trim(),"UTF-8");
        data3=java.net.URLDecoder.decode(atx.getStringValue("DATA3", "").trim(),"UTF-8");
        data7 = atx.getStringValue("DATA7", "").trim();
        banci = atx.getStringValue("BANCI", "").trim();

        return 1;
    }

}