package com.hanthink.cycj.po;

import cn.boho.framework.po.DataBean;

/**
 * 【生产时间数据表:CYCJ_DATE_PP】
 */
public class PmcDatePpPO implements DataBean {
    private final static long serialVersionUID = 1l;
    private java.lang.Integer id;// ID
    private java.lang.String factory;// 工厂
    private java.lang.String workshop;// 车间
    private java.lang.String fproductionline;// 线体归类
    private java.lang.String productionline;// 工段
    private java.lang.String productionlinename;// 工段名字
    private java.util.Date workdate;// 生产日期
    private java.lang.String shift1;// 班次1
    private java.util.Date starttime1;// 1工作开始时间
    private java.util.Date endtime1;// 1工作结束时间
    private java.lang.String shift2;// 班次2
    private java.util.Date starttime2;// 2工作开始时间
    private java.util.Date endtime2;// 2工作结束时间
    private java.lang.String shift3;// 班次3
    private java.util.Date starttime3;// 3工作开始时间
    private java.util.Date endtime3;// 3工作结束时间
    private java.lang.String remark;// 备注
    private java.lang.String operuser;// 操作用户
    private java.util.Date updatetime;// 最后修改时间

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>ID【ID】</u> : <br>
     * 
     * @param Id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>工厂【FACTORY】</u> : <br>
     * 
     * @param Factory
     */
    public void setFactory(java.lang.String factory) {
        this.factory = factory;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>车间【WORKSHOP】</u> : <br>
     * 
     * @param Workshop
     */
    public void setWorkshop(java.lang.String workshop) {
        this.workshop = workshop;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>线体归类【FPRODUCTIONLINE】</u> : <br>
     * 
     * @param Fproductionline
     */
    public void setFproductionline(java.lang.String fproductionline) {
        this.fproductionline = fproductionline;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>工段【PRODUCTIONLINE】</u> : <br>
     * 
     * @param Productionline
     */
    public void setProductionline(java.lang.String productionline) {
        this.productionline = productionline;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>工段名字【PRODUCTIONLINENAME】</u> : <br>
     * 
     * @param Productionlinename
     */
    public void setProductionlinename(java.lang.String productionlinename) {
        this.productionlinename = productionlinename;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>生产日期【WORKDATE】</u> : <br>
     * 
     * @param Workdate
     */
    public void setWorkdate(java.util.Date workdate) {
        this.workdate = workdate;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>班次1【SHIFT1】</u> : <br>
     * 
     * @param Shift1
     */
    public void setShift1(java.lang.String shift1) {
        this.shift1 = shift1;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>1工作开始时间【STARTTIME1】</u> : <br>
     * 
     * @param Starttime1
     */
    public void setStarttime1(java.util.Date starttime1) {
        this.starttime1 = starttime1;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>1工作结束时间【ENDTIME1】</u> : <br>
     * 
     * @param Endtime1
     */
    public void setEndtime1(java.util.Date endtime1) {
        this.endtime1 = endtime1;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>班次2【SHIFT2】</u> : <br>
     * 
     * @param Shift2
     */
    public void setShift2(java.lang.String shift2) {
        this.shift2 = shift2;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>2工作开始时间【STARTTIME2】</u> : <br>
     * 
     * @param Starttime2
     */
    public void setStarttime2(java.util.Date starttime2) {
        this.starttime2 = starttime2;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>2工作结束时间【ENDTIME2】</u> : <br>
     * 
     * @param Endtime2
     */
    public void setEndtime2(java.util.Date endtime2) {
        this.endtime2 = endtime2;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>班次3【SHIFT3】</u> : <br>
     * 
     * @param Shift3
     */
    public void setShift3(java.lang.String shift3) {
        this.shift3 = shift3;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>3工作开始时间【STARTTIME3】</u> : <br>
     * 
     * @param Starttime3
     */
    public void setStarttime3(java.util.Date starttime3) {
        this.starttime3 = starttime3;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>3工作结束时间【ENDTIME3】</u> : <br>
     * 
     * @param Endtime3
     */
    public void setEndtime3(java.util.Date endtime3) {
        this.endtime3 = endtime3;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>备注【REMARK】</u> : <br>
     * 
     * @param Remark
     */
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>操作用户【OPERUSER】</u> : <br>
     * 
     * @param Operuser
     */
    public void setOperuser(java.lang.String operuser) {
        this.operuser = operuser;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>最后修改时间【UPDATETIME】</u> : <br>
     * 
     * @param Updatetime
     */
    public void setUpdatetime(java.util.Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>ID【ID】</u> : <br>
     * 
     * @return
     */
    public java.lang.Integer getId() {
        return this.id;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>工厂【FACTORY】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getFactory() {
        return this.factory;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>车间【WORKSHOP】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getWorkshop() {
        return this.workshop;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>线体归类【FPRODUCTIONLINE】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getFproductionline() {
        return this.fproductionline;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>工段【PRODUCTIONLINE】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getProductionline() {
        return this.productionline;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>工段名字【PRODUCTIONLINENAME】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getProductionlinename() {
        return this.productionlinename;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>生产日期【WORKDATE】</u> : <br>
     * 
     * @return
     */
    public java.util.Date getWorkdate() {
        return this.workdate;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>班次1【SHIFT1】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getShift1() {
        return this.shift1;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>1工作开始时间【STARTTIME1】</u> : <br>
     * 
     * @return
     */
    public java.util.Date getStarttime1() {
        return this.starttime1;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>1工作结束时间【ENDTIME1】</u> : <br>
     * 
     * @return
     */
    public java.util.Date getEndtime1() {
        return this.endtime1;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>班次2【SHIFT2】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getShift2() {
        return this.shift2;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>2工作开始时间【STARTTIME2】</u> : <br>
     * 
     * @return
     */
    public java.util.Date getStarttime2() {
        return this.starttime2;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>2工作结束时间【ENDTIME2】</u> : <br>
     * 
     * @return
     */
    public java.util.Date getEndtime2() {
        return this.endtime2;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>班次3【SHIFT3】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getShift3() {
        return this.shift3;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>3工作开始时间【STARTTIME3】</u> : <br>
     * 
     * @return
     */
    public java.util.Date getStarttime3() {
        return this.starttime3;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>3工作结束时间【ENDTIME3】</u> : <br>
     * 
     * @return
     */
    public java.util.Date getEndtime3() {
        return this.endtime3;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>备注【REMARK】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getRemark() {
        return this.remark;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
     * <u>操作用户【OPERUSER】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getOperuser() {
        return this.operuser;
    }

    /**
     * 【生产时间数据表:CYCJ_DATE_PP】<br>
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

    public PmcDatePpPO clone() {
        PmcDatePpPO obj = new PmcDatePpPO();
        obj.setId(id);
        obj.setFactory(factory);
        obj.setWorkshop(workshop);
        obj.setFproductionline(fproductionline);
        obj.setProductionline(productionline);
        obj.setProductionlinename(productionlinename);
        obj.setWorkdate(workdate);
        obj.setShift1(shift1);
        obj.setStarttime1(starttime1);
        obj.setEndtime1(endtime1);
        obj.setShift2(shift2);
        obj.setStarttime2(starttime2);
        obj.setEndtime2(endtime2);
        obj.setShift3(shift3);
        obj.setStarttime3(starttime3);
        obj.setEndtime3(endtime3);
        obj.setRemark(remark);
        obj.setOperuser(operuser);
        obj.setUpdatetime(updatetime);
        return obj;
    }
}