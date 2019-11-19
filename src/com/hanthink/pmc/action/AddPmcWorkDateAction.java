package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import com.hanthink.pmc.dao.PmcCalenderDao;
import com.hanthink.pmc.dao.TabProductHourDao;
import com.hanthink.pmc.po.PlanProductEditPO;
import com.hanthink.pmc.po.WorkDateEditPO;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AddPmcWorkDateAction extends ActionImp {
    private Connection con = null;
    WorkDateEditPO workDateEditPO = new WorkDateEditPO();
    private Pager pager = null;
    private int pageSize = 100;
    private int currentPage = 1;
    private String startTime;


    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【新增日历】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        pager = new Pager(pageSize, currentPage);
        ComboPager cp = PmcCalenderDao.queryAllLineDate(con,pager,sdf.parse(startTime));
        //判断当前是否有日历模板数据，没有就添加,前一天没数据的情况下
        if(cp.getRs().size()<1){
            List lineData = PmcCalenderDao.queryAllOfLine(con);
            for(int i = 0; i<lineData.size(); i++){
                Map lineInfo = (Map) lineData.get(i);
                workDateEditPO.setWorkShop(lineInfo.get("station").toString());
                workDateEditPO.setWorkDate(sdf.format(sdf.parse(startTime)));
                workDateEditPO.setShift(1);
                workDateEditPO.setLineName(lineInfo.get("stationName").toString());
                workDateEditPO.setLineCode(lineInfo.get("station").toString());
                workDateEditPO.setIsWorkDate(1);
                //插入上一次模板
                List lastTempDate = PmcCalenderDao.queryLastTemplet(con,lineInfo.get("station").toString(),1,lineInfo.get("stationName").toString());
                if(lastTempDate.size()>0){
                    Map modelCode = (Map) lastTempDate.get(0);
                    workDateEditPO.setModelCode(modelCode.get("Model_Code").toString());
                    workDateEditPO.setIsWorkDate(Integer.parseInt(modelCode.get("Is_WorkDate").toString()));
                }else{
                    workDateEditPO.setModelCode("0");
                    workDateEditPO.setIsWorkDate(0);
                }
                workDateEditPO.setLastUpTime(sdf1.format(new Date().getTime()));
                POUtils.insert(con, workDateEditPO, false);
                workDateEditPO.setWorkShop(lineInfo.get("station").toString());
                workDateEditPO.setWorkDate(sdf.format(sdf.parse(startTime)));
                List lastTempDate1 = PmcCalenderDao.queryLastTemplet(con,lineInfo.get("station").toString(),2,lineInfo.get("stationName").toString());
                if(lastTempDate1.size()>0){
                    Map modelCode = (Map) lastTempDate1.get(0);
                    workDateEditPO.setModelCode(modelCode.get("Model_Code").toString());
                    workDateEditPO.setIsWorkDate(Integer.parseInt(modelCode.get("Is_WorkDate").toString()));

                }else{
                    workDateEditPO.setModelCode("0");
                    workDateEditPO.setIsWorkDate(0);
                }
                workDateEditPO.setShift(2);
                workDateEditPO.setLineName(lineInfo.get("stationName").toString());
                workDateEditPO.setLineCode(lineInfo.get("station").toString());

                workDateEditPO.setLastUpTime(sdf1.format(new Date().getTime()));
                POUtils.insert(con, workDateEditPO, false);
            }
            ComboPager cp1 = PmcCalenderDao.queryAllLineDate(con,pager,sdf.parse(startTime));
            atx.setValue("CALENDER_DATE", cp1);
            return 1;
        }
        atx.setValue("CALENDER_DATE", "");
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        startTime = atx.getStringValue("START_TIME");
        return 1;
    }
}
