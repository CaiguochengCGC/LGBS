
/**
 * @File name:  UpdatePmcViewRecordAction.java   修改【意见反馈表:PMC_VIEW_RECORD】
 * @Create on:  2014-03-29 13:46:154
 * @Author :  taofl
 * @ChangeList ---------------------------------------------------
 * Date         Editor              ChangeReasons
 */

package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.service.MessageService;
import com.hanthink.pmc.dao.PmcViewRecordDao;

import java.sql.Connection;

public class UpdatePCAction extends ActionImp {
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {

        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"), "【PC】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        String id = atx.getStringValue("ID");
        Integer actProduct=atx.getIntegerValue("ActProduct",0);
        PmcViewRecordDao.queryUPPC(con, id,actProduct);


        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        return 1;
    }
}
