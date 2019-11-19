/**
 * 
 *
 * @File name:  AddPubDataDictAction.java   添加【基础参数维护:PUB_DATA_DICT】
 * @Create on:  2010-05-14 09:18:437
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
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DbUtils;

import com.hanthink.pub.dao.PubDataDictDao;
import com.hanthink.pub.po.PubDataDictPO;
import com.hanthink.util.ClientIP;
import com.hanthink.util.ErrorHandler;
import com.hanthink.util.OracleSequence;
import com.hanthink.util.SessionUtils;
import com.hanthink.util.Tools;

public class AddPubDataDictAction extends ActionImp {
    private PubDataDictPO pubDataDictPO = new PubDataDictPO();
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"), "【基础参数维护】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
    	
    	//根据类型和编码查找是否存在该数据
    	if (null != PubDataDictDao.getPubDataDictByValue(con, pubDataDictPO.getCodeType(), pubDataDictPO.getCodeValue())) {
    		ErrorHandler.displayError(atx, "该编码已存在，请重新输入！");
    	}
    	
        POUtils.insert(con, pubDataDictPO, false);

        // 返回主键
        DynaBeanMap back = new DynaBeanMap();
        back.put("PK_ID", pubDataDictPO.getPkId());
        atx.setObjValue("PUB_DATA_DICT", back);

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        String userName = SessionUtils.getUserNo(atx);
        String clientIP = ClientIP.getRemoteIP(atx);
        Date dbTime = DbUtils.getDbTime();

        logger.debug("类型是:" + atx.getStringValue("CODE_TYPE"));
        logger.debug("类型名称是:" + atx.getStringValue("CODE_TYPE_NAME"));
        logger.debug("编码是:" + atx.getStringValue("CODE_VALUE"));
        logger.debug("编码名称是:" + atx.getStringValue("CODE_VALUE_NAME"));
        logger.debug("第三方系统代码值是:" + atx.getStringValue("OTHER_CODE_VALUE1"));
        logger.debug("排序是:" + atx.getIntegerValue("SORT_NO"));
        logger.debug("是否可编辑是:" + atx.getStringValue("IS_EDIT"));

        String pkID = OracleSequence.getNextSeq(con, "S_PUB_DATA_DICT");
        pubDataDictPO.setPkId(pkID);
        pubDataDictPO.setCodeType(atx.getStringValue("CODE_TYPE"));
        pubDataDictPO.setCodeTypeName(atx.getStringValue("CODE_TYPE_NAME"));
        pubDataDictPO.setCodeValue(atx.getStringValue("CODE_VALUE"));
        pubDataDictPO.setCodeValueName(atx.getStringValue("CODE_VALUE_NAME"));
        pubDataDictPO.setOtherCodeValue1(atx.getStringValue("OTHER_CODE_VALUE1"));
        pubDataDictPO.setIsEdit(Integer.parseInt(atx.getStringValue("IS_EDIT")));
        pubDataDictPO.setCodeDesc(atx.getStringValue("CODE_DESC"));
        pubDataDictPO.setSortNo(atx.getIntegerValue("SORT_NO"));
        pubDataDictPO.setLastUpdateUsername(userName);
        pubDataDictPO.setLastUpdateIp(clientIP);
        pubDataDictPO.setLastUpdateTime(dbTime);
        pubDataDictPO.setCreateTime(dbTime);
        pubDataDictPO.setCompare(Tools.getInt(atx.getStringValue("COMPARE"),0));
        
        return 1;
    }
}
