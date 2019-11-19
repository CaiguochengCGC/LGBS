
package com.hanthink.zzcj.action;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.fileserver.UploadFilePO;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;
import cn.boho.framework.utils.DbUtils;

import com.hanthink.zzcj.dao.PmcDateImportDao;
import com.hanthink.zzcj.po.PmcDateImportPO;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.SessionUtils;
import com.hanthink.util.Tools;

public class SavePmcDateImportAction extends ActionImp {
    private Connection con = null;
    private String regex;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        if (!(ex instanceof UserOperationException)) {
            atx.setErrorContext("EC_COMMON_1005", MessageService.getMessage("EC_COMMON_1005"), "【PP_PLAN_IMPORT(计划导入表)】", ex);
        }
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        UploadFilePO filePO = atx.getUploadFilePO("PMC_DATE_IMPORT_FILE");

        try {

            // 获取用户信息
            String userName = SessionUtils.getUserNo(atx);
            Date dbTime = DbUtils.getDbTime(con);
            String factory = atx.getStringValue("FACTORY", "");
            String workshop = atx.getStringValue("WORKSHOP", "");

            // 获取excel解析对应的列
            String[] columns = null;
            columns = new String[] { "WORKDATE", "STARTTIME", "ENDTIME", "RESTTIME", "SHIFT", "IP21", "IP22", "IP23","ZP11","BP31","IP24","BP32","AS21","OTHER", "productTotal" };

            // 获取解析的数据
            //List excelDataList = ExcelUtil.importExcel(filePO.getFileItem().getInputStream(), columns, 2, 1);
            List excelDataList = ExcelUtil.importExcelForCycj(filePO.getFileItem().getInputStream(), columns, 29, 3);

            for (int i = 0; i < excelDataList.size(); i++) {
            	
                PmcDateImportPO keyInofPO = new PmcDateImportPO();
                PmcDateImportPO valueInofPO = null;
                DynaBeanMap beanMap = (DynaBeanMap) excelDataList.get(i);
                valueInofPO = PmcDateImportDao.convertBeanMapToPO(beanMap);
                valueInofPO.setFactory(factory);
                valueInofPO.setWorkshop(workshop);
                valueInofPO.setOperuser(userName);
                valueInofPO.setUpdatetime(dbTime);
                keyInofPO.setWorkdate(DateUtils.parse(String.valueOf(beanMap.get("WORKDATE")), "yyyy/MM/dd"));
                keyInofPO.setShift(Tools.getStr(beanMap.get("SHIFT")));
                keyInofPO.setFactory(Tools.getStr(beanMap.get("FACTORY")));
                keyInofPO.setWorkshop(Tools.getStr(beanMap.get("WORKSHOP")));
                POUtils.delete(con, keyInofPO);
                POUtils.insert(con, valueInofPO, false);
            }

            atx.setValue("PMC_DATE_IMPORT_INFO", DictConstants.YES);

        } catch (Exception err) {
        	throw(err);
//            atx.setValue("PMC_DATE_IMPORT_INFO", "请确认工作时间和日期为文本格式");
        } finally {
            filePO.getFileItem().delete();
        }

        logger.info("计划日期数量导入");

        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();

        regex = atx.getStringValue("REGEX", "");

        return 1;
    }

}
