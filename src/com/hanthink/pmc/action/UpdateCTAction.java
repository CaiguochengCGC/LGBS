
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
import com.hanthink.pmc.dao.PmcViewRecordDao;

import java.sql.Connection;

public class UpdateCTAction extends ActionImp {
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {

        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"), "【CT】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        String production1=atx.getStringValue("PRODUCTION");
        int ct=(int)atx.getIntegerValue("CT",0);
        int jEP21=atx.getIntegerValue("EP21",0);
        int jZP11=atx.getIntegerValue("ZP11",0);
        int jBP31=atx.getIntegerValue("BP31",0);
        int jBP32=atx.getIntegerValue("BP32",0);
        int jAS21=atx.getIntegerValue("AS21",0);
        int jAS22=atx.getIntegerValue("AS22",0);
        int jBP34=atx.getIntegerValue("BP34",0);
        int jAS24=atx.getIntegerValue("AS24",0);
        int jAS26=atx.getIntegerValue("AS26",0);
        int jAS28=atx.getIntegerValue("AS28",0);

            PmcViewRecordDao.queryUPCT(con,production1,ct,jEP21,jZP11,jBP31,jBP32,jAS21,jAS22,jBP34,jAS24,jAS26,jAS28);



        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
    	con = atx.getConection();
        return 1;
    }
}
