/**
 * 
 *
 * @File name:  AddTDataDictAction.java   添加【T_DATA_DICT:T_DATA_DICT】
 * @Create on:  2009-08-07 15:59:484
 * @Author   :  ht
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pub.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.exception.UserOperationException;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.po.POUtils;
import cn.boho.framework.service.MessageService;

import com.hanthink.pub.dao.PubModuleDao;
import com.hanthink.pub.dao.PubPrivilegesDao;
import com.hanthink.pub.dao.PubUserDao;
import com.hanthink.pub.po.PubPrivilegesPO;
import com.hanthink.pub.po.PubUserPO;
import com.hanthink.util.DictConstants;
import com.hanthink.util.ErrorHandler;

public class LoginRedirectAction extends ActionImp {
	private Connection con = null;
	private final String rootModule= "ALL";

	@Override
	protected void doException(ActionContext atx, Exception ex) {
		if (!(ex instanceof UserOperationException)) {
			atx.setErrorContext("EC_COMMON_1001", MessageService.getMessage("EC_COMMON_1001"), "【PUB_USER】", ex);
		}
	}

	@Override
	protected int performExecute(ActionContext atx) throws Exception {
	    
	    
		String userName = atx.getStringValue("UserName", "").trim();
		String validKey = atx.getStringValue("ValidKey", "").trim();
		
		//获取用户
		DynaBeanMap userBeanMap = PubUserDao.getPubUserByPk(con, userName);
		PubUserPO userPO = PubUserDao.convertBeanMapToPO(userBeanMap);
		
		//判断用户
		if (userPO == null || Integer.parseInt(DictConstants.NO) == userPO.getUserStatus()) {
		    ErrorHandler.displayError(atx, "用户不存在！");
		}
		if (!validKey.equals(userPO.getDevision())) {
		    ErrorHandler.displayError(atx, "验证码无效！");
		}
		
		//修改验证码
		PubUserPO keyPO = new PubUserPO();
	    PubUserPO valuePO = new PubUserPO();
	    keyPO.setPkUserName(userName);
	    valuePO.setDevision(UUID.randomUUID().toString());
	    POUtils.update(con, keyPO, valuePO, false);
		
		//获取用户权限
		logger.debug("获取登录用户的菜单权限");
        List<PubPrivilegesPO> menuList = this.getPOList(PubPrivilegesDao.queryPubPrivileges(con, userName, DictConstants.PRIVI_TYPE_MUNU));
        if (menuList.size() == 0) {
            ErrorHandler.displayError(atx, "用户没有访问权限！");
        }
        
        logger.debug("获取登录用户的数据权限");
        List<String> dataList = this.getPrivilegesIDList(PubPrivilegesDao.queryPubPrivileges(con, userName, DictConstants.PRIVI_TYPE_DATA));
        
        logger.debug("获取登录用户的功能权限");
        List<String> actionList = this.getPrivilegesIDList(PubPrivilegesDao.queryPubPrivileges(con, userName, DictConstants.PRIVI_TYPE_ACTION));
        
        logger.debug("获取登录用户的模块权限");
        Set<String> menuModules = this.getMenuModule(menuList);
        List userModules = PubModuleDao.queryUserModule(con, menuModules, rootModule);
        menuModules = new HashSet<String>();
        for (int i = 0; i < userModules.size(); i++) {
            menuModules.add((String) ((DynaBeanMap) userModules.get(i)).get("A_MODULE"));
            menuModules.add((String) ((DynaBeanMap) userModules.get(i)).get("B_MODULE"));
            menuModules.add((String) ((DynaBeanMap) userModules.get(i)).get("C_MODULE"));
        }
        
        //将数据写入Action
        atx.setSessionValue(DictConstants.SESSION_USER_NO, userName);
        atx.setSessionValue(DictConstants.SESSION_USER_BEAN, userPO);
        atx.setSessionValue(DictConstants.SESSION_PRI_MENU, menuList);
		atx.setSessionValue(DictConstants.SESSION_PRI_DATA, dataList);
        atx.setSessionValue(DictConstants.SESSION_PRI_ACTION, actionList);
        atx.setSessionValue(DictConstants.SESSION_MODULE, menuModules);
		
		return 1;
	}
	
	//将beanMap转换成PO
	public List<PubPrivilegesPO> getPOList(List beanMapList) {
	    List<PubPrivilegesPO> POList = new ArrayList<PubPrivilegesPO>();
	    for (int i = 0; i < beanMapList.size(); i++) {
	        POList.add(PubPrivilegesDao.convertBeanMapToPO((DynaBeanMap) beanMapList.get(i)));
	    }
	    
	    return POList;
	}
	
	//将beanMap转换成PO
    public List<String> getPrivilegesIDList(List beanMapList) {
        List<String> codeList = new ArrayList<String>();
        for (int i = 0; i < beanMapList.size(); i++) {
            codeList.add(PubPrivilegesDao.convertBeanMapToPO((DynaBeanMap) beanMapList.get(i)).getPkPrivilegesId());
        }
        
        return codeList;
    }

	@Override
	protected int verifyParameters(ActionContext atx) throws Exception {
		con = atx.getConection();
		return 1;
	}
	
	/**
     * 获取用户的菜单中的模块
     * @author 兰永明
     * @create_date 2013-4-11 上午08:36:48
     * @return
     */
    private Set<String> getMenuModule(List<PubPrivilegesPO> menuList) {
        
        Set<String> menuModules = new HashSet<String>();
        for (int i = 0; i < menuList.size(); i++) {
            menuModules.add(menuList.get(i).getCategory());
        }
        
        return menuModules;
        
    }
}
