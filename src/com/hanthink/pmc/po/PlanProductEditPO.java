package com.hanthink.pmc.po;

import cn.boho.framework.po.DataBean;

/**
 * 【planProduct:planProduct】
 */
public class PlanProductEditPO implements DataBean {
    private final static long serialVersionUID = 1l;
    private String id;// ID
    private String ppdate;// PPDATE
    private String linecode;// LineCode
    private String linename;// LineName
    private int planproduct;// PlanProduct
    private int actprocudt;// ActProduct
    private int shift;// Shift
    private String cartype;// CarType

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPpdate() {
        return ppdate;
    }

    public void setPpdate(String ppdate) {
        this.ppdate = ppdate;
    }

    public String getLinecode() {
        return linecode;
    }

    public void setLinecode(String linecode) {
        this.linecode = linecode;
    }

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
    }

    public int getPlanproduct() {
        return planproduct;
    }

    public void setPlanproduct(int planproduct) {
        this.planproduct = planproduct;
    }

    public int getActprocudt() {
        return actprocudt;
    }

    public void setActprocudt(int actprocudt) {
        this.actprocudt = actprocudt;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public StringBuffer toXMLStringBuffer() {
        return cn.boho.framework.po.BeanUtils.formatBeanToXml(this);
    }

    public StringBuffer toXMLStringBufferNoCdata() {
        return cn.boho.framework.po.BeanUtils.formatBeanToXmlNoCdata(this);
    }


    public PlanProductEditPO clone() {
        PlanProductEditPO obj = new PlanProductEditPO();
        obj.setId(id);
        obj.setPpdate(ppdate);
        obj.setLinecode(linecode);
        obj.setLinename(linename);
        obj.setActprocudt(actprocudt);
        obj.setPlanproduct(planproduct);
        obj.setShift(shift);
        obj.setCartype(cartype);
        return obj;
    }
}