
/**
 * 
 *
 * @File name:  UpdatePmcDateImportAction.java   修改stopline信息
 * @Create on:  2015-07-25 10:37:905
 * @Author   :  张译跃
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.cycj.action;

import java.net.URLDecoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.cycj.po.tabStoplinePO;
import com.hanthink.util.DictConstants;
import com.hanthink.util.SessionUtils;
import com.hanthink.util.Tools;
      
public class UpdateStopLineAction extends ActionImp {
	private tabStoplinePO tabstoplinePO = new tabStoplinePO();
	private tabStoplinePO conditiontabStoplinePO = new tabStoplinePO();
	private Connection con=null;
	@Override
	protected void doException(ActionContext atx, Exception ex) {
		if (!(ex instanceof UserOperationException)) {
			atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"),"修改停线信息异常" ,ex);
		}
	}
	@Override
	protected int performExecute(ActionContext atx) throws Exception {
		POUtils.update(con, conditiontabStoplinePO, tabstoplinePO,false);
		return 1;
	}
	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
		con = atx.getConection();
		String updateBy = SessionUtils.getUserNo(atx);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		conditiontabStoplinePO.setId(atx.getIntegerValue("ID",-1));
		tabstoplinePO.setEventdata54(df.format(new Date()));//修改时间戳
		//tabstoplinePO.setEventdate1(DateUtils.parse(atx.getStringValue("EventDate1"), DictConstants.FORMAT_DATETIME));
		//tabstoplinePO.setEventdate2(DateUtils.parse(atx.getStringValue("EventDate2"), DictConstants.FORMAT_DATETIME));
		tabstoplinePO.setBanci(atx.getStringValue("BANCI"));
		//这个用来解决中文乱码问题
		String stopre = atx.getStringValue("StopReson","");
		String stopre2=new String(stopre.getBytes("ISO-8859-1"),"utf-8");
		tabstoplinePO.setEventdata3(stopre2);
		String beizhu = atx.getStringValue("EventData50","");
		String beizhu2=new String(beizhu.getBytes("ISO-8859-1"),"utf-8");
		tabstoplinePO.setEventdata50(beizhu2);
		tabstoplinePO.setEventdata51(atx.getStringValue("EventData51",""));
		tabstoplinePO.setEventdata53(updateBy);
		return 1;
	}
}
