
             
/**
 * 
 *
 * @File name:  UpdatePubDataDictAction.java   修改【基础参数维护:PUB_DATA_DICT】
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
import cn.boho.framework.utils.DbUtils;

import com.hanthink.pub.dao.PubDataDictDao;
import com.hanthink.pub.po.PubDataDictPO;
import com.hanthink.util.ClientIP;
import com.hanthink.util.ErrorHandler;
import com.hanthink.util.SessionUtils;
import com.hanthink.util.Tools;
            
        
public class UpdatePubDataDictAction extends ActionImp {
	private PubDataDictPO conditionPubDataDictPO = new PubDataDictPO();
	private PubDataDictPO valuePubDataDictPO = new PubDataDictPO();
	private Connection con=null;
	@Override
	protected void doException(ActionContext atx, Exception ex) {
		if (!(ex instanceof UserOperationException)) {
			atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"),"【基础参数维护】" ,ex);
		}
	}
	
	@Override
	protected int performExecute(ActionContext atx) throws Exception {
		POUtils.update(con, conditionPubDataDictPO, valuePubDataDictPO,false);
		return 1;
	}
	
	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
		con = atx.getConection();
		String userName = SessionUtils.getUserNo(atx);
        String clientIP = ClientIP.getRemoteIP(atx);
        Date dbTime = DbUtils.getDbTime();
		
		conditionPubDataDictPO.setPkId(atx.getStringValue("PK_ID",""));//新增
		valuePubDataDictPO.setCodeValue(atx.getStringValue("CODE_VALUE",""));//修改
		valuePubDataDictPO.setCodeTypeName(atx.getStringValue("CODE_TYPE_NAME"));
		valuePubDataDictPO.setCodeValueName(atx.getStringValue("CODE_VALUE_NAME"));
		valuePubDataDictPO.setOtherCodeValue1(atx.getStringValue("OTHER_CODE_VALUE1"));
		valuePubDataDictPO.setCodeDesc(atx.getStringValue("CODE_DESC"));
		valuePubDataDictPO.setSortNo(atx.getIntegerValue("SORT_NO"));
		valuePubDataDictPO.setLastUpdateUsername(userName);
		valuePubDataDictPO.setLastUpdateIp(clientIP);
		valuePubDataDictPO.setLastUpdateTime(dbTime);
		valuePubDataDictPO.setCompare(Tools.getInt(atx.getStringValue("COMPARE"),0));
		
		return 1;
	}
}
