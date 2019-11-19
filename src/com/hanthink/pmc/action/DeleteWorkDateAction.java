
/**
 * 
 *
 * @File name:  DeletePubRoleAction.java   删除【角色:PUB_Role】
 * @Create on:  2010-06-17 10:21:734
 * @Author   :  ht
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
import cn.boho.framework.po.CQuery;
import cn.boho.framework.po.CQueryFactoryTool;
import cn.boho.framework.service.MessageService;

import java.sql.Connection;

public class DeleteWorkDateAction extends ActionImp {
    private Connection con = null;
    private Integer id;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1003", MessageService.getMessage("EC_COMMON_1003"), "【删除工作日历】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        // 删除角色
        StringBuffer sql = new StringBuffer();
        CQuery query = CQueryFactoryTool.createFactory().createCQuery();

        sql.append("update PMC_WORKDATE  set ")
                .append("Is_Delete=?")
                .append(" where ID=?");

        query.setCommand(sql.toString());
        query.setLong(1, 1);
        query.setLong(2, id);
        query.executeUpdate(con);

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        id = Integer.parseInt(atx.getStringValue("ID", ""));
        return 1;
    }
}
