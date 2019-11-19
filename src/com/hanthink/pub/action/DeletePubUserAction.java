
             
/**
 * 
 *
 * @File name:  DeletePubUserAction.java   删除【用户:PUB_USER】
 * @Create on:  2010-07-09 09:14:484
 * @Author   :  ht
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */
           
        
package com.hanthink.pub.action;

            
import java.sql.Connection;
import java.util.Date;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;

import com.hanthink.pub.po.PubUserPO;
import com.hanthink.pub.po.PubUserRolePO;
import com.hanthink.util.DictConstants;
import com.hanthink.util.SessionUtils;
            
        

public class DeletePubUserAction extends ActionImp {
	private Connection con=null;
	@Override
	protected void doException(ActionContext atx, Exception ex) {
		if (!(ex instanceof UserOperationException)) {
			atx.setErrorContext("EC_COMMON_1003", MessageService.getMessage("EC_COMMON_1003"),"【用户】", ex);
		}
	}
	@Override
	protected int performExecute(ActionContext atx) throws Exception {
	    
	    //删除用户角色
	    PubUserRolePO userRole = new PubUserRolePO();
	    userRole.setPkUserName(atx.getStringValue("PK_USER_NAME", ""));
	    POUtils.delete(con, userRole);
	    
	    //禁用用户
	    PubUserPO conditionPubUserPO = new PubUserPO();
	    conditionPubUserPO.setPkUserName(atx.getStringValue("PK_USER_NAME", ""));
	    
	    PubUserPO valuePubUserPO = new PubUserPO();
	    valuePubUserPO.setUserStatus(Integer.valueOf(DictConstants.NO));
	    POUtils.update(con, conditionPubUserPO, valuePubUserPO);
	    
		logger.info("删除操作：" + SessionUtils.getUserNo(atx) + "@"+ new Date(System.currentTimeMillis())+ "删除表：【用户:PUB_USER】 主键【"+ atx.getStringValue("PK_USER_NAME") + "|"+"】");
		return 1;
	}
	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
		con = atx.getConection();
		return 1;
	}
}
