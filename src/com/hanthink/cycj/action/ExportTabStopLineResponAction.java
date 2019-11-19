package com.hanthink.cycj.action;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;

import com.hanthink.cycj.dao.TabStopLineDao;
import com.hanthink.pub.dao.PubDataDictDao;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.Tools;

public class ExportTabStopLineResponAction extends ActionImp {
    private Connection con = null;
    private String ppDate;
    private String banci;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【停线查询责任者表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
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
        
        /*String[] headers = new String[32];
        String[] columns = new String[32];
        int[] widths = new int[32];
        headers[0] = "责任部门";
        columns[0] = "EventData51";
        widths[0] = 65;
        for (int i = 1; i < 32; i++) {
            widths[i] = 85;
            columns[i] = Tools.getStr(i<10 ? "0"+i:i);
            headers[i] = Tools.getStr(i);
        }*/
        
        String fileName = "责任部门";
        List beanList = TabStopLineDao.queryTabStopLinePrespon(con, ppDate,banci);
        List presponList = PubDataDictDao.queryPubDataDict(con, "ARESPON", "");
        //获取各个责任部门的标准停线时间
        List stopTime = PubDataDictDao.queryPubDataDictByStop(con, "ARESPON", "");
        Map<String, Integer> stopTimeMap = new HashMap<String, Integer>();
        for (Object object : stopTime) {
            DynaBeanMap status = (DynaBeanMap) object;
            stopTimeMap.put((String) status.get("VALUE"), Tools.getInt(status.get("COMPARE"),0));
        }
        Map<String, String> presponListMap = new HashMap<String, String>();
        for (Object object : presponList) {
            DynaBeanMap status = (DynaBeanMap) object;
            presponListMap.put((String) status.get("VALUE"), (String) status.get("TEXT"));
        }
        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap presponDBM = (DynaBeanMap) beanList.get(i);
            presponDBM.put("COMPARE", stopTimeMap.get(presponDBM.get("EventData51")));
            presponDBM.put("EventData51",presponListMap.get(presponDBM.get("EventData51")));
        }

        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();
        ExcelUtil.exportExcel(fileName, headers, columns, widths, beanList, out, 0.0);
        out.flush();

        return 1;
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        ppDate = atx.getStringValue("PPDATE", "1900-01-01");
        banci=atx.getStringValue("BANCI", "");
        return 1;
    }
}