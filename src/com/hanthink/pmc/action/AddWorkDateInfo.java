
/**
 * @File name:  QueryPubRoleByPagerAction.java
 * @Create on:  2010-06-17 10:21:765
 * @Author :  ht
 * @ChangeList ---------------------------------------------------
 * Date         Editor              ChangeReasons
 */

package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.ComboPager;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.po.Pager;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;
import com.hanthink.pmc.dao.PmcCalenderDao;
import com.hanthink.pmc.dao.PmcViewRecordDao;
import com.hanthink.pmc.po.WorkDateEditPO;
import com.hanthink.util.DictConstants;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddWorkDateInfo extends ActionImp {
    private Connection con = null;
    WorkDateEditPO workDateEditPO = new WorkDateEditPO();
    private String workDate;
    private String modelCode;
    private String shift;
    private String isWorkDate;
    private String lineName;
    private String lineCode;
    private String workShop;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【日历模板配置】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        workDateEditPO.setWorkShop(lineCode);
        workDateEditPO.setWorkDate(workDate);
        workDateEditPO.setModelCode(modelCode);
        workDateEditPO.setShift(Integer.parseInt(shift));
        workDateEditPO.setLineName(lineName);
        workDateEditPO.setLineCode(lineCode);
        workDateEditPO.setIsWorkDate(Integer.parseInt(isWorkDate));
        workDateEditPO.setLastUpTime(sdf1.format(new Date().getTime()));
        POUtils.insert(con, workDateEditPO, false);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        modelCode = atx.getStringValue("Model_Code", "");
        shift = atx.getStringValue("Shift", "");
        isWorkDate = atx.getStringValue("Is_WorkDate", "");
        lineName = atx.getStringValue("Line_Name", "");
        lineCode = atx.getStringValue("Line_Code", "");
        workDate = atx.getStringValue("Work_Date", "");
        workShop = atx.getStringValue("Work_Shop", "");
        if(lineName!="") {
            List list = PmcViewRecordDao.queryLineDate(con, lineName);
            if(list.size()>0){
                Map map = (Map)list.get(0);
                lineCode = (String)map.get("station");
            }
        }
        return 1;
    }
}
