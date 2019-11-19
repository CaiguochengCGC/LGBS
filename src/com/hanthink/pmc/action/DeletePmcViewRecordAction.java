
/**
 * 
 *
 * @File name:  DeletePmcViewRecordAction.java   删除【意见反馈表:PMC_VIEW_RECORD】
 * @Create on:  2014-03-29 13:46:152
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pmc.action;

import java.sql.Connection;
import java.util.Date;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;

import com.hanthink.pmc.dao.PmcViewRecordDao;
import com.hanthink.pmc.po.PmcViewRecordPO;

public class DeletePmcViewRecordAction extends ActionImp {
    private PmcViewRecordPO pmcViewRecordPO = new PmcViewRecordPO();
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1003", MessageService.getMessage("EC_COMMON_1003"), "【休息时间模板】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
    	int id=(int)atx.getIntegerValue("Model_Code");
        PmcViewRecordDao.queryDPmcViewRecord(con, id);
        PmcViewRecordDao.updateWworkDateRecord(con, id);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        pmcViewRecordPO.setId(atx.getIntegerValue("ID", -1));
        return 1;
    }
}
