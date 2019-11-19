
/**
 * 
 *
 * @File name:  AddPmcViewRecordAction.java   添加【意见反馈表:ZZCJ_VIEW_RECORD】
 * @Create on:  2014-03-29 13:46:144
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.zzcj.action;

import java.sql.Connection;
import java.util.Date;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;
import cn.boho.framework.utils.DbUtils;

import com.hanthink.zzcj.po.PmcViewRecordPO;
import com.hanthink.util.DictConstants;

public class AddPmcViewRecordAction extends ActionImp {
    private PmcViewRecordPO pmcViewRecordPO = new PmcViewRecordPO();
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"), "【意见反馈表】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        POUtils.insert(con, pmcViewRecordPO, false);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        String createBy = atx.getStringValue("SESSION_USER_NO");
        Date dbTime = DbUtils.getDbTime();

        // 自动编号 //pmcViewRecordPO.setId(atx.getIntegerValue("ID"));
        pmcViewRecordPO.setFactory(atx.getStringValue("FACTORY"));
        pmcViewRecordPO.setWorkshop(atx.getStringValue("WORKSHOP"));
        pmcViewRecordPO.setRecorddate(DateUtils.parse(atx.getStringValue("RECORDDATE", ""), DictConstants.FORMAT_DATETIME));
        pmcViewRecordPO.setUsername(atx.getStringValue("USERNAME"));
//        pmcViewRecordPO.setRecorddate(dbTime);

        pmcViewRecordPO.setRecordcontent(atx.getStringValue("RECORDCONTENT"));
        pmcViewRecordPO.setRemark(atx.getStringValue("REMARK"));
        pmcViewRecordPO.setOperuser(atx.getStringValue("OPERUSER"));
        pmcViewRecordPO.setUpdatetime(DateUtils.parse(atx.getStringValue("UPDATETIME")));
        return 1;
    }
}
