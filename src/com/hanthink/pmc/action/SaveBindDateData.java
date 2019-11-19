
/**
 *
 *
 * @File name:  UpdatePubRoleAction.java   修改【角色:PUB_Role】
 * @Create on:  2010-06-17 10:21:734
 * @Author   :  ht
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pmc.action;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.CQuery;
import cn.boho.framework.po.CQueryFactoryTool;
import cn.boho.framework.service.MessageService;

import com.hanthink.pmc.dao.PmcCalenderDao;
import com.hanthink.util.ErrorHandler;

public class SaveBindDateData extends ActionImp {
    private Connection con = null;
    private Integer id;
    private String templetDateId;
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"), "【日历模板绑定】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {


        if("".equals(id)){
            ErrorHandler.displayError(atx, "参数丢失,保存失败！");
        }else{
            List modelID = PmcCalenderDao.queryWorkDateModelID(con,id);
            Map value  = (Map) modelID.get(0);
            Long modelId = (Long) value.get("Model_Code");
            //判断添加模板是否重复
            if(modelId!=null && modelId!=0&&templetDateId!=""){
                ErrorHandler.displayError(atx, "该工作时间段存在模板，请检查！");
            }else {
                StringBuffer sql = new StringBuffer();
                CQuery query = CQueryFactoryTool.createFactory().createCQuery();

                sql.append("update PMC_WORKDATE  set ")
                        .append("Model_Code=?")
                        .append(",LastUpTime=?")
                        .append(" where ID=?");

                query.setCommand(sql.toString());
                if ("".equalsIgnoreCase(templetDateId)) {
                    query.setLong(1, 0);
                    query.setString(2, sdf1.format(new Date().getTime()));
                    query.setLong(3, id);
                } else {
                    query.setLong(1, Integer.parseInt(templetDateId));
                    query.setString(2, sdf1.format(new Date().getTime()));
                    query.setLong(3, id);
                }

                query.executeUpdate(con);
            }
        }
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        id = Integer.parseInt(atx.getStringValue("ID", ""));
        templetDateId  = atx.getStringValue("DATE_TEMPLET_ID", "");
        return 1;
    }
}
