
/**
 * 
 *
 * @File name:  UpdatePmcViewRecordAction.java   修改【意见反馈表:PMC_VIEW_RECORD】
 * @Create on:  2014-03-29 13:46:154
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.service.MessageService;
import com.hanthink.pmc.dao.PmcCalenderDao;
import com.hanthink.pmc.dao.PmcViewRecordDao;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateWorkDateAction extends ActionImp {
    private Connection con = null;
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String id;
    private String workDate;
    private String modelCode;
    private String workShop;
    private String shift;
    private String isWorkDate;
    private String lineName;

    @Override
    protected void doException(ActionContext atx, Exception ex) {

        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"), "【工作日历更新】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        String date  = sdf1.format(new Date().getTime());
        int status = PmcCalenderDao.updateWorkDateInfo(con,id,workDate,modelCode,shift,
                isWorkDate, lineName,date);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
    	con = atx.getConection();
        id = atx.getStringValue("ID", "").trim();
        workDate = atx.getStringValue("Work_Date", "").trim();
        modelCode = atx.getStringValue("Model_Code", "").trim();
        workShop = atx.getStringValue("Work_Shop", "").trim();
        shift = atx.getStringValue("Shift", "").trim();
        isWorkDate = atx.getStringValue("Is_WorkDate", "").trim();
        lineName = atx.getStringValue("Line_Name", "").trim();
        return 1;
    }
}
