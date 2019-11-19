
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

package com.hanthink.cycj.action;

import java.sql.Connection;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.cycj.po.PmcViewRecordPO;

public class UpdatePmcViewRecordAction extends ActionImp {
    private PmcViewRecordPO conditionPmcViewRecordPO = new PmcViewRecordPO();
    private PmcViewRecordPO valuePmcViewRecordPO = new PmcViewRecordPO();
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"), "【意见反馈表】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        POUtils.update(con, conditionPmcViewRecordPO, valuePmcViewRecordPO, false);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        String updateBy = atx.getStringValue("SESSION_USER_NO");
        conditionPmcViewRecordPO.setId(atx.getIntegerValue("ID", -1));
        valuePmcViewRecordPO.setFactory(atx.getStringValue("FACTORY"));
        valuePmcViewRecordPO.setWorkshop(atx.getStringValue("WORKSHOP"));
        valuePmcViewRecordPO.setRecorddate(DateUtils.parse(atx.getStringValue("RECORDDATE")));
        valuePmcViewRecordPO.setUsername(atx.getStringValue("USERNAME"));
        valuePmcViewRecordPO.setRecordcontent(atx.getStringValue("RECORDCONTENT"));
        valuePmcViewRecordPO.setRemark(atx.getStringValue("REMARK"));
        valuePmcViewRecordPO.setOperuser(atx.getStringValue("OPERUSER"));
        valuePmcViewRecordPO.setUpdatetime(DateUtils.parse(atx.getStringValue("UPDATETIME")));
        return 1;
    }
}
