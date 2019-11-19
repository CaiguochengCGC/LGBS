
/**
 *
 *
 * @File name:  QueryPubRoleByPagerAction.java
 * @Create on:  2010-06-17 10:21:765
 * @Author   :  ht
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pmc.action;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;

import cn.boho.framework.utils.DateUtils;
import com.hanthink.pmc.dao.PmcCalenderDao;
import com.hanthink.pmc.po.WorkDateEditPO;
import com.hanthink.pub.dao.PubRoleDao;
import com.hanthink.util.DictConstants;

public class QueryPmcCalendarAction extends ActionImp {
    private Connection con = null;
    WorkDateEditPO workDateEditPO = new WorkDateEditPO();
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private Date startTime;
    private String lineCode;
    private String queryTypeT;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【日历模板配置】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String whereSQL="";
        if("1".equals(queryTypeT)){
            whereSQL=" AND (pw.Line_Code='DLHD3' or pw.Line_Code='FDRDL' or pw.Line_Code='FDRDR' or pw.Line_Code='FRDL2' or pw.Line_Code='FRDL3' or pw.Line_Code='FRDR2' or pw.Line_Code='HDTG2' or pw.Line_Code='XHDTG') ";
        }else if("2".equals(queryTypeT)){
            whereSQL=" AND pw.Line_Code!='DLHD3' AND pw.Line_Code!='FDRDL' AND pw.Line_Code!='FDRDR' AND pw.Line_Code!='FRDL2' AND pw.Line_Code!='FRDL3' AND pw.Line_Code!='FRDR2' AND pw.Line_Code !='HDTG2' AND pw.Line_Code !='XHDTG' ";
        }
        pager = new Pager(pageSize, currentPage);
        ComboPager cp = PmcCalenderDao.queryAllLineDateD(con,pager,startTime,lineCode,whereSQL);
        //判断当前是否有日历模板数据，没有就添加,前一天没数据的情况下
//        if(cp.getRs().size()<1){
//            List lineData = PmcCalenderDao.queryAllOfLine(con);
//            for(int i = 0; i<lineData.size(); i++){
//                Map lineInfo = (Map) lineData.get(i);
//                workDateEditPO.setWorkShop("BS1");
//                workDateEditPO.setWorkDate(sdf.format(startTime));
//                workDateEditPO.setShift(1);
//                workDateEditPO.setLineName(lineInfo.get("stationName").toString());
//                workDateEditPO.setLineCode(lineInfo.get("station").toString());
//                workDateEditPO.setIsWorkDate(1);
//                //插入上一次模板
//                List lastTempDate = PmcCalenderDao.queryLastTemplet(con,"BS1",1,lineInfo.get("stationName").toString());
//                if(lastTempDate.size()>0){
//                    Map modelCode = (Map) lastTempDate.get(0);
//                    workDateEditPO.setModelCode(modelCode.get("Model_Code").toString());
//                }else{
//                    workDateEditPO.setModelCode("0");
//                }
//                workDateEditPO.setLastUpTime(sdf1.format(new Date().getTime()));
//                POUtils.insert(con, workDateEditPO, false);
//                workDateEditPO.setWorkShop("BS1");
//                workDateEditPO.setWorkDate(sdf.format(startTime));
//                List lastTempDate1 = PmcCalenderDao.queryLastTemplet(con,"BS1",2,lineInfo.get("stationName").toString());
//                if(lastTempDate1.size()>0){
//                    Map modelCode = (Map) lastTempDate1.get(0);
//                    workDateEditPO.setModelCode(modelCode.get("Model_Code").toString());
//
//                }else{
//                    workDateEditPO.setModelCode("0");
//                }
//                workDateEditPO.setShift(2);
//                workDateEditPO.setLineName(lineInfo.get("stationName").toString());
//                workDateEditPO.setLineCode(lineInfo.get("station").toString());
//                workDateEditPO.setIsWorkDate(1);
//                workDateEditPO.setLastUpTime(sdf1.format(new Date().getTime()));
//                POUtils.insert(con, workDateEditPO, false);
//            }
//            ComboPager cp1 = PmcCalenderDao.queryAllLineDate(con,pager,startTime);
//            atx.setValue("CALENDER_DATE", cp1);
//            return 1;
//        }
        atx.setValue("CALENDER_DATE", cp);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        if (atx.getStringValue("limit") != null && !atx.getStringValue("limit").equals("")) {
            pageSize = new Integer(atx.getStringValue("limit"));
        }
        if (atx.getStringValue("start") != null && !atx.getStringValue("start").equals("")) {
            currentPage = new Integer(atx.getStringValue("start")) / pageSize + 1;
        }
        startTime = DateUtils.parse(atx.getStringValue("START_TIME"),DictConstants.FORMAT_DATE);
        lineCode = atx.getStringValue("PRODUCTIONLINENAME"," ");
        queryTypeT=atx.getStringValue("queryType", "");

        return 1;
    }
}
