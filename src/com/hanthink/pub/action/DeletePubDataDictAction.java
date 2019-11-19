
             
/**
 * 
 *
 * @File name:  DeletePubDataDictAction.java   删除【基础参数维护:PUB_DATA_DICT】
 * @Create on:  2010-05-14 09:18:453
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

import com.hanthink.pub.po.PubDataDictPO;
import com.hanthink.util.SessionUtils;
            
        

public class DeletePubDataDictAction extends ActionImp {
	private Connection con=null;
	@Override
	protected void doException(ActionContext atx, Exception ex) {
		if (!(ex instanceof UserOperationException)) {
			atx.setErrorContext("EC_COMMON_1003", MessageService.getMessage("EC_COMMON_1003"),"【基础参数维护】", ex);
		}
	}
	@Override
	protected int performExecute(ActionContext atx) throws Exception {
	    PubDataDictPO conditionPubDataDictPO = null;
        Object[] pkids = atx.getArrayValue("PK_ID", new String[] {});
        Object[] codeValues = atx.getArrayValue("CODE_VALUE");
        Object[] codeTypes = atx.getArrayValue("CODE_TYPE");
        
        if (pkids != null){
            for (int i = 0; i < pkids.length; i++) {
                conditionPubDataDictPO = new PubDataDictPO();
                conditionPubDataDictPO.setPkId(pkids[i].toString());
    
                POUtils.delete(con, conditionPubDataDictPO);
                logger.info("删除数据字典操作：" + SessionUtils.getUserNo(atx) + "@" + new Date(System.currentTimeMillis()) 
                        + ",【类型:" + codeTypes[i] + ";编码:" + codeValues[i] + "】");
            }
        }
        
		return 1;
	}
	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
		con = atx.getConection();

		return 1;
	}
}
