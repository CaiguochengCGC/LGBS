
/**
 * 
 *
 * @File name:  AddPmcViewRecordAction.java   添加【意见反馈表:PMC_VIEW_RECORD】
 * @Create on:  2014-03-29 13:46:144
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
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;
import cn.boho.framework.utils.DbUtils;

import com.hanthink.pmc.dao.PmcViewRecordDao;
import com.hanthink.pmc.po.PmcViewRecordPO;
import com.hanthink.util.DictConstants;

public class AddPmcViewRecordAction extends ActionImp {
    private Connection con = null;
    SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String lastUpTime;
    @Override
    protected void doException(ActionContext atx, Exception ex) {
            if (!(ex instanceof UserOperationException)) {
                atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"), "【请插入正确数据】", ex);
            }
        }

        @Override
        protected int performExecute(ActionContext atx) throws Exception {
            String Work_Shop="";
           // int Shift=(int)atx.getIntegerValue("Shift");
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
            int shift;
            if (atx.getIntegerValue("Shift")==null){
                shift=1;
            }else{
                 shift=(int)atx.getIntegerValue("Shift");
            }
            PmcViewRecordDao.queryIPmcViewRecord(con,Work_Shop,Templet_Name,shift,WorkStart_Time,WorkEnd_Time,Rest_Time1,Rest_Time2,Rest_Time3,Rest_Time4,Rest_Time5,Rest_Time6,Rest_Time7,Rest_Time8,Rest_Time9,Rest_Time10,lastUpTime);

        	
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        lastUpTime = sdf1.format(new Date().getTime());


        return 1;
    }
}

