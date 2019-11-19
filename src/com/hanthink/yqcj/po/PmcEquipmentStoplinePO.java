package com.hanthink.yqcj.po;

import cn.boho.framework.po.DataBean;

/**
 * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】
 */
public class PmcEquipmentStoplinePO implements DataBean {
    private final static long serialVersionUID = 1l;
    private java.lang.Integer id;// ID
    private java.util.Date ppdate;// 生产日期
    private java.lang.String factory;// 工厂
    private java.lang.String workshop;// 车间
    private java.lang.String fproductionline;// 线体归类
    private java.lang.String productionline;// 工段(线体)
    private java.lang.String productionlinename;// 工段(线体)名字
    private java.lang.Integer stoptime;// 停机总时间(分)
    private java.lang.Integer stopcount;// 停机总次数
    private java.lang.Integer data3;// DATA3
    private java.lang.Integer data4;// DATA4
    private java.lang.Integer data5;// DATA5
    private java.lang.Integer data6;// DATA6
    private java.lang.Integer data7;// DATA7
    private java.lang.String shift;// SHIFT
    private java.lang.String operuser;// 操作用户
    private java.util.Date updatetime;// 最后修改时间

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>ID【ID】</u> : <br>
     * 
     * @param Id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>生产日期【PPDATE】</u> : <br>
     * 
     * @param Ppdate
     */
    public void setPpdate(java.util.Date ppdate) {
        this.ppdate = ppdate;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>工厂【FACTORY】</u> : <br>
     * 
     * @param Factory
     */
    public void setFactory(java.lang.String factory) {
        this.factory = factory;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>车间【WORKSHOP】</u> : <br>
     * 
     * @param Workshop
     */
    public void setWorkshop(java.lang.String workshop) {
        this.workshop = workshop;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>线体归类【FPRODUCTIONLINE】</u> : <br>
     * 
     * @param Fproductionline
     */
    public void setFproductionline(java.lang.String fproductionline) {
        this.fproductionline = fproductionline;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>工段(线体)【PRODUCTIONLINE】</u> : <br>
     * 
     * @param Productionline
     */
    public void setProductionline(java.lang.String productionline) {
        this.productionline = productionline;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>工段(线体)名字【PRODUCTIONLINENAME】</u> : <br>
     * 
     * @param Productionlinename
     */
    public void setProductionlinename(java.lang.String productionlinename) {
        this.productionlinename = productionlinename;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>停机总时间(分)【STOPTIME】</u> : <br>
     * 
     * @param Stoptime
     */
    public void setStoptime(java.lang.Integer stoptime) {
        this.stoptime = stoptime;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>停机总次数【STOPCOUNT】</u> : <br>
     * 
     * @param Stopcount
     */
    public void setStopcount(java.lang.Integer stopcount) {
        this.stopcount = stopcount;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>DATA3【DATA3】</u> : <br>
     * 
     * @param Data3
     */
    public void setData3(java.lang.Integer data3) {
        this.data3 = data3;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>DATA4【DATA4】</u> : <br>
     * 
     * @param Data4
     */
    public void setData4(java.lang.Integer data4) {
        this.data4 = data4;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>DATA5【DATA5】</u> : <br>
     * 
     * @param Data5
     */
    public void setData5(java.lang.Integer data5) {
        this.data5 = data5;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>DATA6【DATA6】</u> : <br>
     * 
     * @param Data6
     */
    public void setData6(java.lang.Integer data6) {
        this.data6 = data6;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>DATA7【DATA7】</u> : <br>
     * 
     * @param Data7
     */
    public void setData7(java.lang.Integer data7) {
        this.data7 = data7;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>SHIFT【SHIFT】</u> : <br>
     * 
     * @param Shift
     */
    public void setShift(java.lang.String shift) {
        this.shift = shift;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>操作用户【OPERUSER】</u> : <br>
     * 
     * @param Operuser
     */
    public void setOperuser(java.lang.String operuser) {
        this.operuser = operuser;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>最后修改时间【UPDATETIME】</u> : <br>
     * 
     * @param Updatetime
     */
    public void setUpdatetime(java.util.Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>ID【ID】</u> : <br>
     * 
     * @return
     */
    public java.lang.Integer getId() {
        return this.id;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>生产日期【PPDATE】</u> : <br>
     * 
     * @return
     */
    public java.util.Date getPpdate() {
        return this.ppdate;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>工厂【FACTORY】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getFactory() {
        return this.factory;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>车间【WORKSHOP】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getWorkshop() {
        return this.workshop;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>线体归类【FPRODUCTIONLINE】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getFproductionline() {
        return this.fproductionline;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>工段(线体)【PRODUCTIONLINE】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getProductionline() {
        return this.productionline;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>工段(线体)名字【PRODUCTIONLINENAME】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getProductionlinename() {
        return this.productionlinename;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>停机总时间(分)【STOPTIME】</u> : <br>
     * 
     * @return
     */
    public java.lang.Integer getStoptime() {
        return this.stoptime;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>停机总次数【STOPCOUNT】</u> : <br>
     * 
     * @return
     */
    public java.lang.Integer getStopcount() {
        return this.stopcount;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>DATA3【DATA3】</u> : <br>
     * 
     * @return
     */
    public java.lang.Integer getData3() {
        return this.data3;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>DATA4【DATA4】</u> : <br>
     * 
     * @return
     */
    public java.lang.Integer getData4() {
        return this.data4;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>DATA5【DATA5】</u> : <br>
     * 
     * @return
     */
    public java.lang.Integer getData5() {
        return this.data5;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>DATA6【DATA6】</u> : <br>
     * 
     * @return
     */
    public java.lang.Integer getData6() {
        return this.data6;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>DATA7【DATA7】</u> : <br>
     * 
     * @return
     */
    public java.lang.Integer getData7() {
        return this.data7;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>SHIFT【SHIFT】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getShift() {
        return this.shift;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
     * <u>操作用户【OPERUSER】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getOperuser() {
        return this.operuser;
    }

    /**
     * 【工段停线报表:YCQJ_EQUIPMENT_STOPLINE】<br>
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

    public PmcEquipmentStoplinePO clone() {
        PmcEquipmentStoplinePO obj = new PmcEquipmentStoplinePO();
        obj.setId(id);
        obj.setPpdate(ppdate);
        obj.setFactory(factory);
        obj.setWorkshop(workshop);
        obj.setFproductionline(fproductionline);
        obj.setProductionline(productionline);
        obj.setProductionlinename(productionlinename);
        obj.setStoptime(stoptime);
        obj.setStopcount(stopcount);
        obj.setData3(data3);
        obj.setData4(data4);
        obj.setData5(data5);
        obj.setData6(data6);
        obj.setData7(data7);
        obj.setShift(shift);
        obj.setOperuser(operuser);
        obj.setUpdatetime(updatetime);
        return obj;
    }
}