package com.hanthink.pmc.po;

import cn.boho.framework.po.DataBean;

/**
 * 【意见反馈表:PMC_VIEW_RECORD】
 */
public class PmcViewRecordPO implements DataBean {
    private final static long serialVersionUID = 1l;
    private java.lang.Integer id;// ID
    private java.lang.String factory;// 工厂
    private java.lang.String workshop;// 车间
    private java.util.Date recorddate;// 录入日期
    private java.lang.String username;// 用户名
    private java.lang.String recordcontent;// 意见反馈
    private java.lang.String remark;// 备注
    private java.lang.String operuser;// 操作用户
    private java.util.Date updatetime;// 最后修改时间

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>ID【ID】</u> : <br>
     * 
     * @param Id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>工厂【FACTORY】</u> : <br>
     * 
     * @param Factory
     */
    public void setFactory(java.lang.String factory) {
        this.factory = factory;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>车间【WORKSHOP】</u> : <br>
     * 
     * @param Workshop
     */
    public void setWorkshop(java.lang.String workshop) {
        this.workshop = workshop;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>录入日期【RECORDDATE】</u> : <br>
     * 
     * @param Recorddate
     */
    public void setRecorddate(java.util.Date recorddate) {
        this.recorddate = recorddate;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>用户名【USERNAME】</u> : <br>
     * 
     * @param Username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>意见反馈【RECORDCONTENT】</u> : <br>
     * 
     * @param Recordcontent
     */
    public void setRecordcontent(java.lang.String recordcontent) {
        this.recordcontent = recordcontent;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>备注【REMARK】</u> : <br>
     * 
     * @param Remark
     */
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>操作用户【OPERUSER】</u> : <br>
     * 
     * @param Operuser
     */
    public void setOperuser(java.lang.String operuser) {
        this.operuser = operuser;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>最后修改时间【UPDATETIME】</u> : <br>
     * 
     * @param Updatetime
     */
    public void setUpdatetime(java.util.Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>ID【ID】</u> : <br>
     * 
     * @return
     */
    public java.lang.Integer getId() {
        return this.id;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>工厂【FACTORY】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getFactory() {
        return this.factory;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>车间【WORKSHOP】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getWorkshop() {
        return this.workshop;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>录入日期【RECORDDATE】</u> : <br>
     * 
     * @return
     */
    public java.util.Date getRecorddate() {
        return this.recorddate;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>用户名【USERNAME】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getUsername() {
        return this.username;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>意见反馈【RECORDCONTENT】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getRecordcontent() {
        return this.recordcontent;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>备注【REMARK】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getRemark() {
        return this.remark;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>操作用户【OPERUSER】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getOperuser() {
        return this.operuser;
    }

    /**
     * 【意见反馈表:PMC_VIEW_RECORD】<br>
     * <u>最后修改时间【UPDATETIME】</u> : <br>
     * 
     * @return
     */
    public java.util.Date getUpdatetime() {
        return this.updatetime;
    }

    public StringBuffer toXMLStringBuffer() {
        return cn.boho.framework.po.BeanUtils.formatBeanToXml(this);
    }

    public StringBuffer toXMLStringBufferNoCdata() {
        return cn.boho.framework.po.BeanUtils.formatBeanToXmlNoCdata(this);
    }

    public PmcViewRecordPO clone() {
        PmcViewRecordPO obj = new PmcViewRecordPO();
        obj.setId(id);
        obj.setFactory(factory);
        obj.setWorkshop(workshop);
        obj.setRecorddate(recorddate);
        obj.setUsername(username);
        obj.setRecordcontent(recordcontent);
        obj.setRemark(remark);
        obj.setOperuser(operuser);
        obj.setUpdatetime(updatetime);
        return obj;
    }
}