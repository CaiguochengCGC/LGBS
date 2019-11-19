package com.hanthink.yqcj.action;

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

import com.hanthink.yqcj.dao.TabStopLineDao;
import com.hanthink.pub.dao.PubDataDictDao;
import com.hanthink.pub.dao.SysParamDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.Tools;
/*导出停线查询表*/
public class ExportTabStopLineAction extends ActionImp {
    private Connection con = null;
    private java.util.Date startPlanDate;
    private java.util.Date endPlanDate;
    private String eventdate40;
    private String eventdate1;
    private String banci;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【停线查询表】", ex);
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

        String fileName = "停线查询表";
        List beanList = TabStopLineDao.queryTabStopLine1(con, startPlanDate, endPlanDate,eventdate40,eventdate1,banci);
        //获取责任部门
        List presponList = PubDataDictDao.queryPubDataDict(con, "ARESPON", "");
        Map<String, String> presponListMap = new HashMap<String, String>();
        for (Object object : presponList) {
            DynaBeanMap status = (DynaBeanMap) object;
            presponListMap.put((String) status.get("VALUE"), (String) status.get("TEXT"));
        }
        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap localDynaBeanMap = (DynaBeanMap) beanList.get(i);
            localDynaBeanMap.put("EventData51", presponListMap.get(Tools.getStr(localDynaBeanMap.get("EventData51"))));
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
        startPlanDate = DateUtils.parse(atx.getStringValue("START_PLAN_DATE", ""), DictConstants.FORMAT_DATETIME);
        endPlanDate = DateUtils.parse(atx.getStringValue("END_PLAN_DATE", ""), DictConstants.FORMAT_DATETIME);
        eventdate1 = atx.getStringValue("EventDate1", "").trim();
        banci = atx.getStringValue("BANCI", "").trim();
        eventdate40=java.net.URLDecoder.decode(atx.getStringValue("EventDate40", "").trim(),"UTF-8");
        return 1;
    }
}