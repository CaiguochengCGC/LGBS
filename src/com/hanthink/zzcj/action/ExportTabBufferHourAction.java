package com.hanthink.zzcj.action;

import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.zzcj.dao.PmcPpKpidayDao;
import com.hanthink.zzcj.dao.TabProductHourDao;
import com.hanthink.pub.dao.SysParamDao;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.JfreeChartPicturePO;
import com.hanthink.util.JfreeChartUtil;
import com.hanthink.util.Tools;

public class ExportTabBufferHourAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private String MODE;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【BUFFER日报表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {

        String fileName = "BUFFER日报表";
        String fileName1 = "BUFFER日报表折线图";
        String fileName2 = "BUFFER日报表柱状图";
        String title = "BUFFER日报表";
        
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
        
        List beanList = TabProductHourDao.queryTabStopLine2(con, ppdate,MODE);
        for (int i = 0; i < beanList.size(); i++) {
            DynaBeanMap localDynaBeanMap = (DynaBeanMap) beanList.get(i);
            String gwdata=localDynaBeanMap.get("EventDate28").toString().trim();
            switch(Integer.parseInt(gwdata)){
            case 1:localDynaBeanMap.put("EventDate28", "BUFFERIN");break;
            case 2:localDynaBeanMap.put("EventDate28", "BUFFEROUT");break;
            case 3:localDynaBeanMap.put("EventDate28", "空橇量");break;
            }

        }

        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();

        	ExcelUtil.exportExcel(title, headers, columns, widths, beanList, out);
            out.flush();
            return 1;

        
        

        
    }

    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        ppdate = DateUtils.parse(atx.getStringValue("EventData"), "yyyy-MM-dd");
        MODE = atx.getStringValue("MODE","");
        return 1;
    }
}