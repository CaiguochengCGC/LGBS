
/**
 * 
 *
 * @File name:  DeletePmcViewRecordAction.java   删除【意见反馈表:CYCJ_VIEW_RECORD】
 * @Create on:  2014-03-29 13:46:152
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.cycj.action;

import java.sql.Connection;
import java.util.Date;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;

import com.hanthink.cycj.po.PmcViewRecordPO;

public class DeletePmcViewRecordAction extends ActionImp {
    private PmcViewRecordPO pmcViewRecordPO = new PmcViewRecordPO();
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1003", MessageService.getMessage("EC_COMMON_1003"), "【意见反馈表】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        POUtils.delete(con, pmcViewRecordPO);
        logger.info("删除操作：" + atx.getStringValue("SESSION_USER_NO") + "@" + new Date(System.currentTimeMillis()) + "删除表：【意见反馈表:CYCJ_VIEW_RECORD】 主键【" + atx.getStringValue("ID")
                + "|" + "】");
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        pmcViewRecordPO.setId(atx.getIntegerValue("ID", -1));
        return 1;
    }
}
