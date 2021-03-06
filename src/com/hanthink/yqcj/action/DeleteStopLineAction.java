/**
 * 
 *
 * @File name:  DeletePmcDateImportAction.java   删除【计划日期数量导入:YQCJ_DATE_IMPORT】
 * @Create on:  2014-03-16 17:25:754
 * @Author   :  luoshw
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.yqcj.action;

import java.sql.Connection;
import java.util.Date;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;

import com.hanthink.yqcj.po.tabStoplinePO;
import com.hanthink.util.Tools;

public class DeleteStopLineAction extends ActionImp {
    private tabStoplinePO pmcDateImportPO = new tabStoplinePO();
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1003", MessageService.getMessage("EC_COMMON_1003"), "删除停线信息异常", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        Object[] pkids = atx.getArrayValue("ID", new String[] {});

        if (pkids != null) {
            for (int i = 0; i < pkids.length; i++) {
            	tabStoplinePO pmcDateImportPO = new tabStoplinePO();
                pmcDateImportPO.setId(Tools.getInt(pkids[i]));

                POUtils.delete(con, pmcDateImportPO);
            }
        }

        logger.info("删除操作：" + atx.getStringValue("SESSION_USER_NO") + "@" + new Date(System.currentTimeMillis()) + "删除表：【计划日期数量导入:YQCJ_DATE_IMPORT】 主键【" + atx.getStringValue("ID")
                + "|" + "】");
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        return 1;
    }
}
