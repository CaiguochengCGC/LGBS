package com.hanthink.pub.po;

import cn.boho.framework.po.DataBean;

/**
 * 
 * 【SYS_PARAM:SYS_PARAM】
 */

public class SysParamPO implements DataBean {
    private final static long serialVersionUID = 1l;
    private java.lang.String paramCode;// PARAM_CODE
    private java.lang.String paramGroup;// PARAM_GROUP
    private java.lang.String paramName;// PARAM_NAME
    private java.lang.String paramVal;// PARAM_VAL
    private java.lang.String isEdit;// IS_EDIT
    private java.lang.String paramType;// PARAM_TYPE
    private java.lang.String note;// NOTE
    private java.lang.String lastUpdateUsername;// LAST_UPDATE_USERNAME
    private java.lang.String lastUpdateIp;// LAST_UPDATE_IP
    private java.util.Date lastUpdateTime;// LAST_UPDATE_TIME
    private java.util.Date createTime;// CREATE_TIME

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>PARAM_CODE【PARAM_CODE】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">参数代码</font>
     * 
     * @param ParamCode
     */

    public void setParamCode(java.lang.String paramCode) {
        this.paramCode = paramCode;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>PARAM_GROUP【PARAM_GROUP】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">参数分组</font>
     * 
     * @param ParamGroup
     */

    public void setParamGroup(java.lang.String paramGroup) {
        this.paramGroup = paramGroup;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>PARAM_NAME【PARAM_NAME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">参数名称</font>
     * 
     * @param ParamName
     */

    public void setParamName(java.lang.String paramName) {
        this.paramName = paramName;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>PARAM_VAL【PARAM_VAL】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">参数值</font>
     * 
     * @param ParamVal
     */

    public void setParamVal(java.lang.String paramVal) {
        this.paramVal = paramVal;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>IS_EDIT【IS_EDIT】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">是否可编辑：0-不可看，1-可看不可编辑，2-可看可编辑</font>
     * 
     * @param IsEdit
     */

    public void setIsEdit(java.lang.String isEdit) {
        this.isEdit = isEdit;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>PARAM_TYPE【PARAM_TYPE】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">用于控制界面输入控件：<br>
     * &nbsp;&nbsp;DATE=日期控件<br>
     * &nbsp;&nbsp;CHAR=输入字符串（数字和字符）<br>
     * &nbsp;&nbsp;NUMERIC=只能输入数值型数)</font>
     * 
     * @param ParamType
     */

    public void setParamType(java.lang.String paramType) {
        this.paramType = paramType;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>NOTE【NOTE】</u> : <br>
     * 
     * 
     * @param Note
     */

    public void setNote(java.lang.String note) {
        this.note = note;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
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
     * 【SYS_PARAM:SYS_PARAM】<br>
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
     * 【SYS_PARAM:SYS_PARAM】<br>
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
     * 【SYS_PARAM:SYS_PARAM】<br>
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
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>PARAM_CODE【PARAM_CODE】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">参数代码</font>
     * 
     * @return
     */

    public java.lang.String getParamCode() {
        return this.paramCode;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>PARAM_GROUP【PARAM_GROUP】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">参数分组</font>
     * 
     * @return
     */

    public java.lang.String getParamGroup() {
        return this.paramGroup;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>PARAM_NAME【PARAM_NAME】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">参数名称</font>
     * 
     * @return
     */

    public java.lang.String getParamName() {
        return this.paramName;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>PARAM_VAL【PARAM_VAL】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">参数值</font>
     * 
     * @return
     */

    public java.lang.String getParamVal() {
        return this.paramVal;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>IS_EDIT【IS_EDIT】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">是否可编辑：0-不可看，1-可看不可编辑，2-可看可编辑</font>
     * 
     * @return
     */

    public java.lang.String getIsEdit() {
        return this.isEdit;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>PARAM_TYPE【PARAM_TYPE】</u> : <br>
     * &nbsp;&nbsp;<font color="#3300CC">用于控制界面输入控件：<br>
     * &nbsp;&nbsp;DATE=日期控件<br>
     * &nbsp;&nbsp;CHAR=输入字符串（数字和字符）<br>
     * &nbsp;&nbsp;NUMERIC=只能输入数值型数)</font>
     * 
     * @return
     */

    public java.lang.String getParamType() {
        return this.paramType;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
     * 
     * <u>NOTE【NOTE】</u> : <br>
     * 
     * 
     * @return
     */

    public java.lang.String getNote() {
        return this.note;
    }

    /**
     * 
     * 【SYS_PARAM:SYS_PARAM】<br>
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
     * 【SYS_PARAM:SYS_PARAM】<br>
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
     * 【SYS_PARAM:SYS_PARAM】<br>
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
     * 【SYS_PARAM:SYS_PARAM】<br>
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

    public SysParamPO clone() {

        SysParamPO obj = new SysParamPO();

        obj.setParamCode(paramCode);

        obj.setParamGroup(paramGroup);

        obj.setParamName(paramName);

        obj.setParamVal(paramVal);

        obj.setIsEdit(isEdit);

        obj.setParamType(paramType);

        obj.setNote(note);

        obj.setLastUpdateUsername(lastUpdateUsername);

        obj.setLastUpdateIp(lastUpdateIp);

        obj.setLastUpdateTime(lastUpdateTime);

        obj.setCreateTime(createTime);

        return obj;

    }

}