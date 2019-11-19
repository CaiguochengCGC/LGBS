/**
 * 
 *
 * @File name:  QueryPmcEquipmentStoplineAction.java 
 * @Create on:  2014-03-16 16:40:100
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.zzcj.action;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.zzcj.dao.PmcEquipmentStopDao;
import com.hanthink.zzcj.dao.PmcEquipmentStoplineDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

public class QueryPmcEquipmentStoplineKpiAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private java.lang.String queryType;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【工段停线报表】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
//        List list = PmcEquipmentStoplineDao.queryPmcEquipmentStopline(con, ppdate);
        List list = new LinkedList();
        
        if("0".equals(queryType)){  //日
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),DictConstants.FORMAT_DATE);
            
            list = PmcEquipmentStoplineDao.queryPmcEquipmentStoplineKpi(con, ppdate);
        }else if("1".equals(queryType)){
            //月
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),"yyyy-MM");
            list = PmcEquipmentStoplineDao.queryMonPmcEquipmentStoplineKpi(con, ppdate);
        }else if("2".equals(queryType)){
            //年
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),"yyyy");
            list = PmcEquipmentStoplineDao.queryYearPmcEquipmentStoplineKpi(con, ppdate);
        }
        atx.setValue("PMC_EQUIPMENT_STOPLINE", list.toArray());
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        
        queryType = atx.getStringValue("QUERY_TYPE","").trim();
        
        return 1;
    }
}
