package com.hanthink.yqcj.po;

import cn.boho.framework.po.DataBean;

/**
 * 【生产工位表:YQCJ_PP_STATION】
 */
public class PmcPpStationPO implements DataBean {
    private final static long serialVersionUID = 1l;
    private java.lang.Integer id;// ID
    private java.lang.String factory;// 工厂
    private java.lang.String workshop;// 车间
    private java.lang.String fproductionline;// 线体归类
    private java.lang.String productionline;// 工段
    private java.lang.String productionlinename;// 工段名字
    private java.lang.String station;// 工位
    private java.lang.String remark;// 备注
    private java.lang.String operuser;// 操作用户
    private java.util.Date updatetime;// 最后修改时间
    private java.lang.String pseq;//

    public java.lang.String getPseq() {
		return pseq;
	}

	public void setPseq(java.lang.String pseq) {
		this.pseq = pseq;
	}

	/**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>ID【ID】</u> : <br>
     * 
     * @param Id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>工厂【FACTORY】</u> : <br>
     * 
     * @param Factory
     */
    public void setFactory(java.lang.String factory) {
        this.factory = factory;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>车间【WORKSHOP】</u> : <br>
     * 
     * @param Workshop
     */
    public void setWorkshop(java.lang.String workshop) {
        this.workshop = workshop;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>线体归类【FPRODUCTIONLINE】</u> : <br>
     * 
     * @param Fproductionline
     */
    public void setFproductionline(java.lang.String fproductionline) {
        this.fproductionline = fproductionline;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>工段【PRODUCTIONLINE】</u> : <br>
     * 
     * @param Productionline
     */
    public void setProductionline(java.lang.String productionline) {
        this.productionline = productionline;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>工段名字【PRODUCTIONLINENAME】</u> : <br>
     * 
     * @param Productionlinename
     */
    public void setProductionlinename(java.lang.String productionlinename) {
        this.productionlinename = productionlinename;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>工位【STATION】</u> : <br>
     * 
     * @param Station
     */
    public void setStation(java.lang.String station) {
        this.station = station;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>备注【REMARK】</u> : <br>
     * 
     * @param Remark
     */
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>操作用户【OPERUSER】</u> : <br>
     * 
     * @param Operuser
     */
    public void setOperuser(java.lang.String operuser) {
        this.operuser = operuser;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>最后修改时间【UPDATETIME】</u> : <br>
     * 
     * @param Updatetime
     */
    public void setUpdatetime(java.util.Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>ID【ID】</u> : <br>
     * 
     * @return
     */
    public java.lang.Integer getId() {
        return this.id;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>工厂【FACTORY】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getFactory() {
        return this.factory;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>车间【WORKSHOP】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getWorkshop() {
        return this.workshop;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>线体归类【FPRODUCTIONLINE】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getFproductionline() {
        return this.fproductionline;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>工段【PRODUCTIONLINE】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getProductionline() {
        return this.productionline;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>工段名字【PRODUCTIONLINENAME】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getProductionlinename() {
        return this.productionlinename;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>工位【STATION】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getStation() {
        return this.station;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>备注【REMARK】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getRemark() {
        return this.remark;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
     * <u>操作用户【OPERUSER】</u> : <br>
     * 
     * @return
     */
    public java.lang.String getOperuser() {
        return this.operuser;
    }

    /**
     * 【生产工位表:YQCJ_PP_STATION】<br>
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

    public PmcPpStationPO clone() {
        PmcPpStationPO obj = new PmcPpStationPO();
        obj.setId(id);
        obj.setFactory(factory);
        obj.setWorkshop(workshop);
        obj.setFproductionline(fproductionline);
        obj.setProductionline(productionline);
        obj.setProductionlinename(productionlinename);
        obj.setStation(station);
        obj.setRemark(remark);
        obj.setOperuser(operuser);
        obj.setUpdatetime(updatetime);
        return obj;
    }
}