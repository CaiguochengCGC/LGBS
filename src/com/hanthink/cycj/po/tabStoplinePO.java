package com.hanthink.cycj.po;

import cn.boho.framework.po.DataBean;

/**
 * 【停线查询表】
 */
public class tabStoplinePO implements DataBean {
    private final static long serialVersionUID = 1l;
    private java.lang.Integer id;// ID
    private java.lang.String eventdate1;
    private java.lang.String eventdate2;
    private java.lang.String eventdata3;
    private java.lang.String eventdata40;
    private java.lang.String eventdata46;
    private java.lang.String eventdata50;
    private java.lang.String eventdata51;
    private java.lang.String eventdata53;
    private java.lang.String eventdata54;
    private java.lang.String banci;//班次

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	
	

	public java.lang.String getEventdata3() {
		return eventdata3;
	}

	public void setEventdata3(java.lang.String eventdata3) {
		this.eventdata3 = eventdata3;
	}

	public java.lang.String getEventdata50() {
		return eventdata50;
	}

	public void setEventdata50(java.lang.String eventdata50) {
		this.eventdata50 = eventdata50;
	}

	public java.lang.String getEventdata51() {
		return eventdata51;
	}

	public void setEventdata51(java.lang.String eventdata51) {
		this.eventdata51 = eventdata51;
	}

	public java.lang.String getEventdata53() {
		return eventdata53;
	}

	public void setEventdata53(java.lang.String eventdata53) {
		this.eventdata53 = eventdata53;
	}

	public java.lang.String getEventdata54() {
		return eventdata54;
	}

	public void setEventdata54(java.lang.String eventdata54) {
		this.eventdata54 = eventdata54;
	}

	public java.lang.String getBanci() {
		return banci;
	}

	public void setBanci(java.lang.String banci) {
		this.banci = banci;
	}

	public StringBuffer toXMLStringBuffer() {
        return cn.boho.framework.po.BeanUtils.formatBeanToXml(this);
    }

    public StringBuffer toXMLStringBufferNoCdata() {
        return cn.boho.framework.po.BeanUtils.formatBeanToXmlNoCdata(this);
    }
    
    
    public java.lang.String getEventdate1() {
		return eventdate1;
	}

	public void setEventdate1(java.lang.String eventdate1) {
		this.eventdate1 = eventdate1;
	}

	public java.lang.String getEventdate2() {
		return eventdate2;
	}

	public void setEventdate2(java.lang.String eventdate2) {
		this.eventdate2 = eventdate2;
	}

	public java.lang.String getEventdata40() {
		return eventdata40;
	}

	public void setEventdata40(java.lang.String eventdata40) {
		this.eventdata40 = eventdata40;
	}

	public java.lang.String getEventdata46() {
		return eventdata46;
	}

	public void setEventdata46(java.lang.String eventdata46) {
		this.eventdata46 = eventdata46;
	}

	public tabStoplinePO clone() {
        tabStoplinePO obj = new tabStoplinePO();
        obj.setId(id);
        obj.setEventdata3(eventdata3);
        obj.setEventdata50(eventdata50);
        obj.setEventdata51(eventdata51);
        obj.setEventdata53(eventdata53);
        obj.setEventdata54(eventdata54);
        obj.setBanci(banci);
        return obj;
    }
}