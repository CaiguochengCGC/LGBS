package com.hanthink.pub.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;

import com.hanthink.pub.dao.PubModuleDao;
import com.hanthink.pub.po.PubPrivilegesPO;
import com.hanthink.util.SessionUtils;

public class GetUserMenuAction extends ActionImp {
    private Connection con = null;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【系统后台用户】", ex);
    }

    @Override
    protected int performExecute(ActionContext atx) throws Exception {
        con = atx.getConection();
        
        String moduleCodeParam = atx.getStringValue("MODULE_CODE","").trim();
        String userName = SessionUtils.getUserNo(atx);
        Map moduleMap = new HashMap();
        
        // 如果是管理员用户，绕过权限控制，只显示系统管理页面
        List<DynaBeanMap> retList = new ArrayList<DynaBeanMap>();
        List<PubPrivilegesPO> menuList = SessionUtils.getPrivilegesMenus(atx);
        List moduleList = PubModuleDao.queryPubModule(con, userName, moduleCodeParam);
        int moduleLen = moduleList.size();
        int menuLen = menuList.size();
        Set<String> modules = SessionUtils.getModules(atx);
    
        //增加模块
        for (int i = 0; i < moduleLen; i++) {
            DynaBeanMap moduleBean = (DynaBeanMap) moduleList.get(i);
            String moduleCode = (String) moduleBean.get("PK_MODULE_CODE");
            String moduleName = (String) moduleBean.get("MODULE_NAME");
            
            if (modules.contains(moduleCode)) {
                DynaBeanMap<String, Object> element = new DynaBeanMap<String, Object>();
                element.put("text", moduleName);
                element.put("nodeId", moduleCode);
                element.put("category", moduleCode);
                element.put("leaf", false);
                retList.add(element);
            }
        }
        
        //增加菜单
        for (int i = 0; i < menuLen; i++) {
            PubPrivilegesPO menuBean =  menuList.get(i);
            if (menuBean.getCategory().equals(moduleCodeParam)) {
                DynaBeanMap<String, Object> element = new DynaBeanMap<String, Object>();
                element.put("text", menuBean.getDescription());
                element.put("menuId", menuBean.getPkPrivilegesId());
                element.put("nodeId", menuBean.getContent());
                element.put("paramCode", menuBean.getParamCode());
                element.put("category", menuBean.getCategory());
                element.put("resoures", menuBean.getI18nResources());
                element.put("leaf", true);
                retList.add(element);
            }
        }
    
        atx.setValue("MENU", retList);
            
        return 1;
    }
}
