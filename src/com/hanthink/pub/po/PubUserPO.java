package com.hanthink.pub.po;

import cn.boho.framework.po.DataBean;

/**
 * 
 * 【PUB_USER:PUB_USER】
 */

public class PubUserPO implements DataBean {
    private final static long serialVersionUID = 1l;
    private java.lang.String pkUserName;// PK_USER_NAME
    private java.lang.String userCname;// USER_CNAME
    private java.lang.String userPwd;// USER_PWD
    private java.lang.Integer userStatus;// USER_STATUS
    private java.lang.Integer isUpdatePwd;// IS_UPDATE_PWD
    private java.lang.String devision;// DEVISION
    private java.lang.String lastUpdateUsername;// LAST_UPDATE_USERNAME
    private java.lang.String lastUpdateIp;// LAST_UPDATE_IP
    private java.util.Date lastUpdateTime;// LAST_UPDATE_TIME
    private java.util.Date createTime;// CREATE_TIME

    /**
     * 
     * 【PUB_USER:PUB_USER】<br>
     * 
     * <u>PK_USER_NAME【PK_USER_NAME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">用户名</font>
     * 
     * @param PkUserName
     */

    public void setPkUserName(java.lang.String pkUserName) {
        this.pkUserName = pkUserName;
    }

    /**
     * 
     * 【PUB_USER:PUB_USER】<br>
     * 
     * <u>USER_CNAME【USER_CNAME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">用户姓名</font>
     * 
     * @param UserCname
     */

    public void setUserCname(java.lang.String userCname) {
        this.userCname = userCname;
    }

    /**
     * 
     * 【PUB_USER:PUB_USER】<br>
     * 
     * <u>USER_PWD【USER_PWD】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">密码</font>
     * 
     * @param UserPwd
     */

    public void setUserPwd(java.lang.String userPwd) {
        this.userPwd = userPwd;
    }

    /**
     * 
     * 【PUB_USER:PUB_USER】<br>
     * 
     * <u>USER_STATUS【USER_STATUS】</u> : <br>
     * 
     * 
     * @param UserStatus
     */

    public void setUserStatus(java.lang.Integer userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * 
     * 【PUB_USER:PUB_USER】<br>
     * 
     * <u>IS_UPDATE_PWD【IS_UPDATE_PWD】</u> : <br>
     * 
     * 
     * @param IsUpdatePwd
     */

    public void setIsUpdatePwd(java.lang.Integer isUpdatePwd) {
        this.isUpdatePwd = isUpdatePwd;
    }

    /**
     * 
     * 【PUB_USER:PUB_USER】<br>
     * 
     * <u>DEVISION【DEVISION】</u> : <br>
     * 
     * 
     * @param Devision
     */

    public void setDevision(java.lang.String devision) {
        this.devision = devision;
    }

    /**
     * 
     * 【PUB_USER:PUB_USER】<br>
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
     * 【PUB_USER:PUB_USER】<br>
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
     * 【PUB_USER:PUB_USER】<br>
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
     * 【PUB_USER:PUB_USER】<br>
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
     * 【PUB_USER:PUB_USER】<br>
     * 
     * <u>PK_USER_NAME【PK_USER_NAME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">用户名</font>
     * 
     * @return
     */

    public java.lang.String getPkUserName() {
        return this.pkUserName;
    }

    /**
     * 
     * 【PUB_USER:PUB_USER】<br>
     * 
     * <u>USER_CNAME【USER_CNAME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">用户姓名</font>
     * 
     * @return
     */

    public java.lang.String getUserCname() {
        return this.userCname;
    }

    /**
     * 
     * 【PUB_USER:PUB_USER】<br>
     * 
     * <u>USER_PWD【USER_PWD】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">密码</font>
     * 
     * @return
     */

    public java.lang.String getUserPwd() {
        return this.userPwd;
    }

    /**
     * 
     * 【PUB_USER:PUB_USER】<br>
     * 
     * <u>USER_STATUS【USER_STATUS】</u> : <br>
     * 
     * 
     * @return
     */

    public java.lang.Integer getUserStatus() {
        return this.userStatus;
    }

    /**
     * 
     * 【PUB_USER:PUB_USER】<br>
     * 
     * <u>IS_UPDATE_PWD【IS_UPDATE_PWD】</u> : <br>
     * 
     * 
     * @return
     */

    public java.lang.Integer getIsUpdatePwd() {
        return this.isUpdatePwd;
    }

    /**
     * 
     * 【PUB_USER:PUB_USER】<br>
     * 
     * <u>DEVISION【DEVISION】</u> : <br>
     * 
     * 
     * @return
     */

    public java.lang.String getDevision() {
        return this.devision;
    }

    /**
     * 
     * 【PUB_USER:PUB_USER】<br>
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
     * 【PUB_USER:PUB_USER】<br>
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
     * 【PUB_USER:PUB_USER】<br>
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
     * 【PUB_USER:PUB_USER】<br>
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

    public PubUserPO clone() {

        PubUserPO obj = new PubUserPO();

        obj.setPkUserName(pkUserName);

        obj.setUserCname(userCname);

        obj.setUserPwd(userPwd);

        obj.setUserStatus(userStatus);

        obj.setIsUpdatePwd(isUpdatePwd);

        obj.setDevision(devision);

        obj.setLastUpdateUsername(lastUpdateUsername);

        obj.setLastUpdateIp(lastUpdateIp);

        obj.setLastUpdateTime(lastUpdateTime);

        obj.setCreateTime(createTime);

        return obj;

    }

}