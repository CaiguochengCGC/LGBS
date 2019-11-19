package com.hanthink.util;

import java.util.List;
import java.util.Set;

import cn.boho.framework.context.ActionContext;

import com.hanthink.pub.po.PubPrivilegesPO;
import com.hanthink.pub.po.PubUserPO;

public class SessionUtils {

    public static String getUserNo(ActionContext atx) {
        return (String) atx.getSessionValue(DictConstants.SESSION_USER_NO);
    }
    
    public static PubUserPO getUser(ActionContext atx) {
        return (PubUserPO) atx.getSessionValue(DictConstants.SESSION_USER_BEAN);
    }
    
    public static List<PubPrivilegesPO> getPrivilegesMenus(ActionContext atx) {
        return (List<PubPrivilegesPO>) atx.getSessionValue(DictConstants.SESSION_PRI_MENU);
    }
    
    public static List<String> getPrivilegesDatas(ActionContext atx) {
        return (List<String>) atx.getSessionValue(DictConstants.SESSION_PRI_DATA);
    }
    
    public static List<String> getPrivilegesActions(ActionContext atx) {
        return (List<String>) atx.getSessionValue(DictConstants.SESSION_PRI_ACTION);
    }
    
    public static Set<String> getModules(ActionContext atx) {
        return (Set<String>) atx.getSessionValue(DictConstants.SESSION_MODULE);
    }
    
}
