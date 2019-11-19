
             
/**
 * 
 *
 * @File name:  AddPmcDateImportAction.java   添加【YQCJ_DATE_IMPORT 计划日期数量导入:YQCJ_DATE_IMPORT】
 * @Create on:  2015-07-25 10:37:874
 * @Author   :  张译跃
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */
           
        
package com.hanthink.yqcj.action;

            
import java.sql.Connection;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.yqcj.po.PmcDateImportPO;
import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

public class AddPmcDateImportAction extends ActionImp {
	private PmcDateImportPO pmcDateImportPO = new PmcDateImportPO();
	private Connection con=null;
	@Override
	protected void doException(ActionContext atx, Exception ex) {
		if (!(ex instanceof UserOperationException)) {
			atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"),"【YQCJ_DATE_IMPORT 计划日期数量导入】", ex);
		}
	}
	@Override
	protected int performExecute(ActionContext atx) throws Exception {
		POUtils.insert(con, pmcDateImportPO,false);
		return 1;
	}
	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
		con = atx.getConection();
		String createBy = atx.getStringValue("SESSION_USER_NO");
		//自动编号 //pmcDateImportPO.setId(atx.getIntegerValue("ID"));
		pmcDateImportPO.setFactory(atx.getStringValue("FACTORY"));
		pmcDateImportPO.setWorkshop(atx.getStringValue("WORKSHOP"));
		pmcDateImportPO.setShift(atx.getStringValue("SHIFT"));
		pmcDateImportPO.setWorkdate(DateUtils.parse(atx.getStringValue("WORKDATE"), DictConstants.FORMAT_DATE));
		pmcDateImportPO.setStarttime(DateUtils.parse(atx.getStringValue("STARTTIME"), DictConstants.FORMAT_DATETIME));
		pmcDateImportPO.setEndtime(DateUtils.parse(atx.getStringValue("ENDTIME"), DictConstants.FORMAT_DATETIME));
		pmcDateImportPO.setResttime(atx.getIntegerValue("RESTTIME"));
		pmcDateImportPO.setIp21(Tools.getInt(atx.getStringValue("IP21"), 0));
		pmcDateImportPO.setIp22(Tools.getInt(atx.getStringValue("IP22"), 0));
		pmcDateImportPO.setIp23(Tools.getInt(atx.getStringValue("IP23"), 0));
		pmcDateImportPO.setZp11(Tools.getInt(atx.getStringValue("ZP11"), 0));
		pmcDateImportPO.setBp31(Tools.getInt(atx.getStringValue("BP31"), 0));
		pmcDateImportPO.setIp24(Tools.getInt(atx.getStringValue("IP24"), 0));
		pmcDateImportPO.setBp32(Tools.getInt(atx.getStringValue("BP32"), 0));
		pmcDateImportPO.setAs21(Tools.getInt(atx.getStringValue("AS21"), 0));
		pmcDateImportPO.setOther(Tools.getInt(atx.getStringValue("OTHER"), 0));
		pmcDateImportPO.setProducttotal(Tools.getInt(atx.getStringValue("productTotal"), 0));
		pmcDateImportPO.setRemark(atx.getStringValue("REMARK"));
		pmcDateImportPO.setOperuser(atx.getStringValue("OPERUSER"));
		pmcDateImportPO.setUpdatetime(DateUtils.parse(atx.getStringValue("UPDATETIME")));
		return 1;
	}
}
