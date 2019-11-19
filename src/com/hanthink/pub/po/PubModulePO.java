package com.hanthink.pub.po;

import cn.boho.framework.po.DataBean;

/**
 * 
 * 【PUB_MODULE:PUB_MODULE】
 */

public class PubModulePO implements DataBean {
    private final static long serialVersionUID = 1l;
    private java.lang.String parentModule;// PARENT_MODULE
    private java.lang.String pkModuleCode;// PK_MODULE_CODE
    private java.lang.String moduleName;// MODULE_NAME
    private java.lang.Long sort;// SORT
    private java.lang.Integer moduleLevel;// MODULE_LEVEL
    private java.lang.String lastUpdateUsername;// LAST_UPDATE_USERNAME
    private java.lang.String lastUpdateIp;// LAST_UPDATE_IP
    private java.util.Date lastUpdateTime;// LAST_UPDATE_TIME
    private java.util.Date createTime;// CREATE_TIME

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>PARENT_MODULE【PARENT_MODULE】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">父模块代码</font>
     * 
     * @param ParentModule
     */

    public void setParentModule(java.lang.String parentModule) {
        this.parentModule = parentModule;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>PK_MODULE_CODE【PK_MODULE_CODE】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">模块代码</font>
     * 
     * @param PkModuleCode
     */

    public void setPkModuleCode(java.lang.String pkModuleCode) {
        this.pkModuleCode = pkModuleCode;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>MODULE_NAME【MODULE_NAME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">模块名称</font>
     * 
     * @param ModuleName
     */

    public void setModuleName(java.lang.String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>SORT【SORT】</u> : <br>
     * 
     * 
     * @param Sort
     */

    public void setSort(java.lang.Long sort) {
        this.sort = sort;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>MODULE_LEVEL【MODULE_LEVEL】</u> : <br>
     * 
     * 
     * @param ModuleLevel
     */

    public void setModuleLevel(java.lang.Integer moduleLevel) {
        this.moduleLevel = moduleLevel;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>LAST_UPDATE_USERNAME【LAST_UPDATE_USERNAME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">更改用户</font>
     * 
     * @param LastUpdateUsername
     */

    public void setLastUpdateUsername(java.lang.String lastUpdateUsername) {
        this.lastUpdateUsername = lastUpdateUsername;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>LAST_UPDATE_IP【LAST_UPDATE_IP】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">最后更改IP</font>
     * 
     * @param LastUpdateIp
     */

    public void setLastUpdateIp(java.lang.String lastUpdateIp) {
        this.lastUpdateIp = lastUpdateIp;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>LAST_UPDATE_TIME【LAST_UPDATE_TIME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">修改时间</font>
     * 
     * @param LastUpdateTime
     */

    public void setLastUpdateTime(java.util.Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>CREATE_TIME【CREATE_TIME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">创建时间</font>
     * 
     * @param CreateTime
     */

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>PARENT_MODULE【PARENT_MODULE】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">父模块代码</font>
     * 
     * @return
     */

    public java.lang.String getParentModule() {
        return this.parentModule;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>PK_MODULE_CODE【PK_MODULE_CODE】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">模块代码</font>
     * 
     * @return
     */
    public java.lang.String getPkModuleCode() {
        return this.pkModuleCode;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>MODULE_NAME【MODULE_NAME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">模块名称</font>
     * 
     * @return
     */

    public java.lang.String getModuleName() {
        return this.moduleName;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>SORT【SORT】</u> : <br>
     * 
     * 
     * @return
     */

    public java.lang.Long getSort() {
        return this.sort;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>MODULE_LEVEL【MODULE_LEVEL】</u> : <br>
     * 
     * 
     * @return
     */

    public java.lang.Integer getModuleLevel() {
        return this.moduleLevel;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>LAST_UPDATE_USERNAME【LAST_UPDATE_USERNAME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">更改用户</font>
     * 
     * @return
     */

    public java.lang.String getLastUpdateUsername() {
        return this.lastUpdateUsername;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>LAST_UPDATE_IP【LAST_UPDATE_IP】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">最后更改IP</font>
     * 
     * @return
     */

    public java.lang.String getLastUpdateIp() {
        return this.lastUpdateIp;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>LAST_UPDATE_TIME【LAST_UPDATE_TIME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">修改时间</font>
     * 
     * @return
     */

    public java.util.Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    /**
     * 
     * 【PUB_MODULE:PUB_MODULE】<br>
     * 
     * <u>CREATE_TIME【CREATE_TIME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">创建时间</font>
     * 
     * @return
     */

    public java.util.Date getCreateTime() {
        return this.createTime;
    }
    public StringBuffer toXMLStringBuffer() {
        return cn.boho.framework.po.BeanUtils.formatBeanToXml(this);
    }

    public StringBuffer toXMLStringBufferNoCdata() {
        return cn.boho.framework.po.BeanUtils.formatBeanToXmlNoCdata(this);
    }

    public PubModulePO clone() {

        PubModulePO obj = new PubModulePO();

        obj.setParentModule(parentModule);

        obj.setPkModuleCode(pkModuleCode);

        obj.setModuleName(moduleName);

        obj.setSort(sort);

        obj.setModuleLevel(moduleLevel);

        obj.setLastUpdateUsername(lastUpdateUsername);

        obj.setLastUpdateIp(lastUpdateIp);

        obj.setLastUpdateTime(lastUpdateTime);

        obj.setCreateTime(createTime);
        
        return obj;

    }

}