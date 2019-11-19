package com.hanthink.pmc.po;

import cn.boho.framework.po.DataBean;

import java.util.Date;

/**
 * 【日历模板】
 */
public class WorkDateEditPO implements DataBean {
    private String workDate;
    private String modelCode;
    private String workShop;
    private int shift;
    private int isWorkDate;
    private String lastUpTime;
    private String lineName;
    private String lineCode;

    public String getLastUpTime() {
        return lastUpTime;
    }

    public void setLastUpTime(String lastUpTime) {
        this.lastUpTime = lastUpTime;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getWorkShop() {
        return workShop;
    }

    public void setWorkShop(String workShop) {
        this.workShop = workShop;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int getIsWorkDate() {
        return isWorkDate;
    }

    public void setIsWorkDate(int isWorkDate) {
        this.isWorkDate = isWorkDate;
    }



    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public StringBuffer toXMLStringBuffer() {
        return cn.boho.framework.po.BeanUtils.formatBeanToXml(this);
    }

    public StringBuffer toXMLStringBufferNoCdata() {
        return cn.boho.framework.po.BeanUtils.formatBeanToXmlNoCdata(this);
    }


    public WorkDateEditPO clone() {
        WorkDateEditPO obj = new WorkDateEditPO();
        obj.setModelCode(modelCode);
        obj.setIsWorkDate(isWorkDate);
        obj.setLastUpTime(lastUpTime);
        obj.setLineCode(lineCode);
        obj.setLineName(lineName);
        obj.setShift(shift);
        obj.setWorkDate(workDate);
        obj.setWorkShop(workShop);
        return obj;
    }
}