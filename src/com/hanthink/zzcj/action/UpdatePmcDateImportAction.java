
/**
 * 
 *
 * @File name:  UpdatePmcDateImportAction.java   修改【PMC_DATE_IMPORT 计划日期数量导入:PMC_DATE_IMPORT】
 * @Create on:  2015-07-25 10:37:905
 * @Author   :  张译跃
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.zzcj.action;

import java.sql.Connection;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.zzcj.po.PmcDateImportPO;
import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;
      
public class UpdatePmcDateImportAction extends ActionImp {
	private PmcDateImportPO conditionPmcDateImportPO = new PmcDateImportPO();
	private PmcDateImportPO valuePmcDateImportPO = new PmcDateImportPO();
	private Connection con=null;
	@Override
	protected void doException(ActionContext atx, Exception ex) {
		if (!(ex instanceof UserOperationException)) {
			atx.setErrorContext("EC_COMMON_1002", MessageService.getMessage("EC_COMMON_1002"),"【PMC_DATE_IMPORT 计划日期数量导入】" ,ex);
		}
	}
	@Override
	protected int performExecute(ActionContext atx) throws Exception {
		POUtils.update(con, conditionPmcDateImportPO, valuePmcDateImportPO,false);
		return 1;
	}
	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
		con = atx.getConection();
		String updateBy = atx.getStringValue("SESSION_USER_NO");
		conditionPmcDateImportPO.setId(atx.getIntegerValue("ID",-1));
		valuePmcDateImportPO.setFactory(atx.getStringValue("FACTORY"));
		valuePmcDateImportPO.setWorkshop(atx.getStringValue("WORKSHOP"));
		valuePmcDateImportPO.setShift(atx.getStringValue("SHIFT"));
		valuePmcDateImportPO.setWorkdate(DateUtils.parse(atx.getStringValue("WORKDATE"), DictConstants.FORMAT_DATE));
		valuePmcDateImportPO.setStarttime(DateUtils.parse(atx.getStringValue("STARTTIME"), DictConstants.FORMAT_DATETIME));
		valuePmcDateImportPO.setEndtime(DateUtils.parse(atx.getStringValue("ENDTIME"), DictConstants.FORMAT_DATETIME));
		valuePmcDateImportPO.setResttime(atx.getIntegerValue("RESTTIME"));
		valuePmcDateImportPO.setIp21(Tools.getInt(atx.getStringValue("IP21"), 0));
		valuePmcDateImportPO.setIp22(Tools.getInt(atx.getStringValue("IP22"), 0));
		valuePmcDateImportPO.setIp23(Tools.getInt(atx.getStringValue("IP23"), 0));
		valuePmcDateImportPO.setZp11(Tools.getInt(atx.getStringValue("ZP11"), 0));
		valuePmcDateImportPO.setBp31(Tools.getInt(atx.getStringValue("BP31"), 0));
		valuePmcDateImportPO.setIp24(Tools.getInt(atx.getStringValue("IP24"), 0));
		valuePmcDateImportPO.setBp32(Tools.getInt(atx.getStringValue("BP32"), 0));
		valuePmcDateImportPO.setAs21(Tools.getInt(atx.getStringValue("AS21"), 0));
		valuePmcDateImportPO.setOther(Tools.getInt(atx.getStringValue("OTHER"), 0));
		valuePmcDateImportPO.setProducttotal(Tools.getInt(atx.getStringValue("productTotal"), 0));
		valuePmcDateImportPO.setRemark(atx.getStringValue("REMARK"));
		valuePmcDateImportPO.setOperuser(atx.getStringValue("OPERUSER"));
		valuePmcDateImportPO.setUpdatetime(DateUtils.parse(atx.getStringValue("UPDATETIME")));
		return 1;
	}
}
