/**
 * @File name:  UpdateSysParamAction.java   修改【SYS_PARAM系统参数:SYS_PARAM】
 * @Create on:  2011-04-11 17:22:851
 * @Author   :  郑胜军
 * @ChangeList
 * Date         Editor              ChangeReasons
 */
package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.CQuery;
import cn.boho.framework.po.CQueryFactoryTool;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DbUtils;

import com.hanthink.pmc.po.PlanProductEditPO;
import com.hanthink.pub.po.SysParamPO;
import com.hanthink.util.ClientIP;
import com.hanthink.util.ErrorHandler;
import com.hanthink.util.SessionUtils;

import java.sql.Connection;
import java.util.Date;

public class UpdatePlanProductAction extends ActionImp {
    private PlanProductEditPO conditionPlanProductPO = new PlanProductEditPO();
    private PlanProductEditPO valuePlanProductPO = new PlanProductEditPO();
    private Connection con = null;
    private Integer id;
    private Integer planProduct;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"), "【请检查输入格式，更新计划产量】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        POUtils.update(con, conditionPlanProductPO, valuePlanProductPO);
        if("".equals(id)||"".equals(planProduct)){
        	ErrorHandler.displayError(atx, "参数丢失,保存失败！");
        }else{
        	StringBuffer sql = new StringBuffer();
            CQuery query = CQueryFactoryTool.createFactory().createCQuery();

            sql.append("update PMC_PP_PRODUCT  set ")
            .append("PlanProduct=?")
            .append(" where ID=?");

            query.setCommand(sql.toString());
            query.setLong(1, planProduct);
            query.setLong(2, id);
            query.executeUpdate(con);
        }
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        conditionPlanProductPO.setId(atx.getStringValue("ID", ""));
        id = Integer.parseInt(atx.getStringValue("ID", ""));
        planProduct = Integer.parseInt(atx.getStringValue("PlanProduct",""));
        return 1;
    }
}
