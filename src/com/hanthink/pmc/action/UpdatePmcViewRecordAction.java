
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

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.pmc.dao.PmcViewRecordDao;
import com.hanthink.pmc.po.PmcViewRecordPO;

public class UpdatePmcViewRecordAction extends ActionImp {
    private Connection con = null;
    private String lastUpTime;
    SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doException(ActionContext atx, Exception ex) {

        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"), "【请查询作息时间模板之后，再修改】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        String Work_Shop="";
        int Shift=(int)atx.getIntegerValue("Shift");
        String Templet_Name=atx.getStringValue("Templet_Name").trim();
        String WorkStart_Time=atx.getStringValue("WorkStart_Time").trim();
        String WorkEnd_Time=atx.getStringValue("WorkEnd_Time").trim();
        String Rest_Time1=atx.getStringValue("Rest_Time1").trim();
        String Rest_Time2=atx.getStringValue("Rest_Time2").trim();
        String Rest_Time3=atx.getStringValue("Rest_Time3").trim();
        String Rest_Time4=atx.getStringValue("Rest_Time4").trim();
        String Rest_Time5=atx.getStringValue("Rest_Time5").trim();
        String Rest_Time6=atx.getStringValue("Rest_Time6").trim();
        String Rest_Time7=atx.getStringValue("Rest_Time7").trim();
        String Rest_Time8=atx.getStringValue("Rest_Time8").trim();
        String Rest_Time9=atx.getStringValue("Rest_Time9").trim();
        String Rest_Time10=atx.getStringValue("Rest_Time10").trim();

        int Model_Code=atx.getIntegerValue("Model_Code");
            PmcViewRecordDao.queryUPmcViewRecord(con,Model_Code,Work_Shop,Templet_Name,Shift,WorkStart_Time,WorkEnd_Time,Rest_Time1,Rest_Time2,Rest_Time3,Rest_Time4,Rest_Time5,Rest_Time6,Rest_Time7,Rest_Time8,Rest_Time9,Rest_Time10,lastUpTime);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
    	con = atx.getConection();
        lastUpTime = sdf1.format(new Date().getTime());
        return 1;
    }
}
