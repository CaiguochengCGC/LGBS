
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

import com.hanthink.cycj.po.PmcPpStationPO;

public class UpdatePmcPpStationAction extends ActionImp {
    private PmcPpStationPO conditionPmcPpStationPO = new PmcPpStationPO();
    private PmcPpStationPO valuePmcPpStationPO = new PmcPpStationPO();
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"), "【意见反馈表】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        POUtils.update(con, conditionPmcPpStationPO, valuePmcPpStationPO, false);
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        conditionPmcPpStationPO.setPseq(atx.getStringValue("PSEQ"));
        if("23".equals(atx.getStringValue("PSEQ"))){
        	conditionPmcPpStationPO.setProductionlinename(atx.getStringValue("PRODUCTIONLINENAME"));
        }
        
        valuePmcPpStationPO.setRemark(atx.getStringValue("REMARK"));
        
        return 1;
    }
}
