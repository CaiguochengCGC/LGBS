package com.hanthink.pmc.action;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import com.hanthink.pmc.dao.TabProductHourDao;
import com.hanthink.pmc.po.PlanProductEditPO;
import java.sql.Connection;
import java.util.List;

public class QueryPlanProductEditAction extends ActionImp {
    private PlanProductEditPO planProductEditPO= new PlanProductEditPO();
    private Connection con = null;
    private String planDate;
    private String lineCode;
    private String shift;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【计划产量查询】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {

        List cp = TabProductHourDao.queryPlanProductData(con,planDate,lineCode,shift);
        atx.setValue("PmcPlanEdit", cp);
        //判断计划产量是否为空
        if(cp.size()>0){
            atx.setValue("PmcPlanEdit", cp.toArray());
        }else{
            //在PMC_PP_PRODUCT插入20条空数据
            for(int i = 0; i < 22; i++) {
                planProductEditPO.setPpdate(planDate);
                planProductEditPO.setLinecode("RL1");
                planProductEditPO.setLinename("补焊线1");
                planProductEditPO.setPlanproduct(0);
                planProductEditPO.setActprocudt(0);
                //每10次换班次，换车型
                if(i==1) {
                    planProductEditPO.setCartype("AS21");
                    planProductEditPO.setShift(1);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==2) {
                    planProductEditPO.setCartype("AS22");
                    planProductEditPO.setShift(1);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==3) {
                    planProductEditPO.setCartype("AS24");
                    planProductEditPO.setShift(1);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==4) {
                    planProductEditPO.setCartype("AS26");
                    planProductEditPO.setShift(1);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==5) {
                    planProductEditPO.setCartype("AS28");
                    planProductEditPO.setShift(1);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==6) {
                    planProductEditPO.setCartype("BP31");
                    planProductEditPO.setShift(1);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==7) {
                    planProductEditPO.setCartype("BP32");
                    planProductEditPO.setShift(1);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==8) {
                    planProductEditPO.setCartype("BP34");
                    planProductEditPO.setShift(1);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==9) {
                    planProductEditPO.setCartype("EP21");
                    planProductEditPO.setShift(1);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==10) {
                    planProductEditPO.setCartype("ZP11");
                    planProductEditPO.setShift(1);
                    POUtils.insert(con, planProductEditPO, false);
                }
                //2班次
                if(i==12) {
                    planProductEditPO.setCartype("AS21");
                    planProductEditPO.setShift(2);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==13) {
                    planProductEditPO.setCartype("AS22");
                    planProductEditPO.setShift(2);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==14) {
                    planProductEditPO.setCartype("AS24");
                    planProductEditPO.setShift(2);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==15) {
                    planProductEditPO.setCartype("AS26");
                    planProductEditPO.setShift(2);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==16) {
                    planProductEditPO.setCartype("AS28");
                    planProductEditPO.setShift(2);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==17) {
                    planProductEditPO.setCartype("BP31");
                    planProductEditPO.setShift(2);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==18) {
                    planProductEditPO.setCartype("BP32");
                    planProductEditPO.setShift(2);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==19) {
                    planProductEditPO.setCartype("BP34");
                    planProductEditPO.setShift(2);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==20) {
                    planProductEditPO.setCartype("EP21");
                    planProductEditPO.setShift(2);
                    POUtils.insert(con, planProductEditPO, false);
                }
                if(i==21) {
                    planProductEditPO.setCartype("ZP11");
                    planProductEditPO.setShift(2);
                    POUtils.insert(con, planProductEditPO, false);
                }
            }
            List cp1 = TabProductHourDao.queryPlanProductData(con,planDate,lineCode,shift);
            atx.setValue("PmcPlanEdit", cp1.toArray());
        }

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        planDate = atx.getStringValue("PlanDate");
        lineCode = atx.getStringValue("LineName", "");
        shift = atx.getStringValue("ShiftText", "");


        return 1;
    }
}
