
             
/**
 * 
 *
 * @File name:   添加tabstopline表】
 * @Create on:  2016-07-25 10:37:874
 * @Author   :  李垚
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
import cn.boho.framework.utils.DateUtils;

import com.hanthink.cycj.po.tabStoplinePO;
import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

public class AddStopLineAction extends ActionImp {
	private tabStoplinePO tabstoplinePO = new tabStoplinePO();
	private Connection con=null;
	@Override
	protected void doException(ActionContext atx, Exception ex) {
		if (!(ex instanceof UserOperationException)) {
			atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"),"新增停线信息异常", ex);
		}
	}
	@Override
	protected int performExecute(ActionContext atx) throws Exception {
		POUtils.insert(con, tabstoplinePO,false);
		return 1;
	}
	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
		con = atx.getConection();
		String createBy = atx.getStringValue("SESSION_USER_NO");
//		tabstoplinePO.setEventdata54(atx.getStringValue("EventData54"));
//		tabstoplinePO.setEventdate1(DateUtils.parse(atx.getStringValue("EventDate1"), DictConstants.FORMAT_DATETIME));
//		tabstoplinePO.setEventdate2(DateUtils.parse(atx.getStringValue("EventDate2"), DictConstants.FORMAT_DATETIME));
		tabstoplinePO.setEventdate1(atx.getStringValue("EventDate1"));
		tabstoplinePO.setEventdate2(atx.getStringValue("EventDate2"));
		tabstoplinePO.setBanci(atx.getStringValue("BANCI"));
		tabstoplinePO.setEventdata3(atx.getStringValue("StopReson"));
		tabstoplinePO.setEventdata46(atx.getStringValue("EventData46").trim());
		tabstoplinePO.setEventdata40(atx.getStringValue("EventData46").trim());
		tabstoplinePO.setEventdata50(atx.getStringValue("EventData50"));
		tabstoplinePO.setEventdata51(atx.getStringValue("EventData51"));
		tabstoplinePO.setEventdata53(atx.getStringValue("EventData53"));
		return 1;
	}
}
