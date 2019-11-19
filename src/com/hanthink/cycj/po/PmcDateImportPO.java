package com.hanthink.cycj.po;

            
import cn.boho.framework.po.DataBean;
            
        /**
 * 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】
 */
public class PmcDateImportPO implements DataBean {
	private final static long serialVersionUID=1l;
	private java.lang.Integer id;//ID
	private java.lang.String factory;//工厂
	private java.lang.String workshop;//车间
	private java.lang.String shift;//SHIFT
	private java.util.Date workdate;//生产日期
	private java.util.Date starttime;//工作开始时间
	private java.util.Date endtime;//工作结束时间
	private java.lang.Integer resttime;//休息时间
	private java.lang.Integer ip21;//IP21
	private java.lang.Integer ip22;//IP22
	private java.lang.Integer ip23;//IP23
	private java.lang.Integer zp11;//ZP11
	private java.lang.Integer bp31;//BP31
	private java.lang.Integer ip24;//IP24
	private java.lang.Integer bp32;//BP32
	private java.lang.Integer as21;//AS21
	private java.lang.Integer other;//AS21
	private java.lang.Integer producttotal;//总产量
	private java.lang.String remark;//备注
	private java.lang.String operuser;//操作用户
	private java.util.Date updatetime;//最后修改时间

	public java.lang.Integer getOther() {
		return other;
	}

	public void setOther(java.lang.Integer other) {
		this.other = other;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>ID【ID】</u> : <br>
	* 
	* @param Id
	*/
	public void setId(java.lang.Integer id){
		 this.id = id;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>工厂【FACTORY】</u> : <br>
	* 
	* @param Factory
	*/
	public void setFactory(java.lang.String factory){
		 this.factory = factory;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>车间【WORKSHOP】</u> : <br>
	* 
	* @param Workshop
	*/
	public void setWorkshop(java.lang.String workshop){
		 this.workshop = workshop;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>SHIFT【SHIFT】</u> : <br>
	* 
	* @param Shift
	*/
	public void setShift(java.lang.String shift){
		 this.shift = shift;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>生产日期【WORKDATE】</u> : <br>
	* 
	* @param Workdate
	*/
	public void setWorkdate(java.util.Date workdate){
		 this.workdate = workdate;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>工作开始时间【STARTTIME】</u> : <br>
	* 
	* @param Starttime
	*/
	public void setStarttime(java.util.Date starttime){
		 this.starttime = starttime;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>工作结束时间【ENDTIME】</u> : <br>
	* 
	* @param Endtime
	*/
	public void setEndtime(java.util.Date endtime){
		 this.endtime = endtime;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>休息时间【RESTTIME】</u> : <br>
	* 
	* @param Resttime
	*/
	public void setResttime(java.lang.Integer resttime){
		 this.resttime = resttime;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>IP21【IP21】</u> : <br>
	* 
	* @param Ip21
	*/
	public void setIp21(java.lang.Integer ip21){
		 this.ip21 = ip21;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>IP22【IP22】</u> : <br>
	* 
	* @param Ip22
	*/
	public void setIp22(java.lang.Integer ip22){
		 this.ip22 = ip22;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>IP23【IP23】</u> : <br>
	* 
	* @param Ip23
	*/
	public void setIp23(java.lang.Integer ip23){
		 this.ip23 = ip23;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>ZP11【ZP11】</u> : <br>
	* 
	* @param Zp11
	*/
	public void setZp11(java.lang.Integer zp11){
		 this.zp11 = zp11;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>BP31【BP31】</u> : <br>
	* 
	* @param Bp31
	*/
	public void setBp31(java.lang.Integer bp31){
		 this.bp31 = bp31;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>IP24【IP24】</u> : <br>
	* 
	* @param Ip24
	*/
	public void setIp24(java.lang.Integer ip24){
		 this.ip24 = ip24;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>BP32【BP32】</u> : <br>
	* 
	* @param Bp32
	*/
	public void setBp32(java.lang.Integer bp32){
		 this.bp32 = bp32;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>AS21【AS21】</u> : <br>
	* 
	* @param As21
	*/
	public void setAs21(java.lang.Integer as21){
		 this.as21 = as21;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>总产量【productTotal】</u> : <br>
	* 
	* @param Producttotal
	*/
	public void setProducttotal(java.lang.Integer producttotal){
		 this.producttotal = producttotal;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>备注【REMARK】</u> : <br>
	* 
	* @param Remark
	*/
	public void setRemark(java.lang.String remark){
		 this.remark = remark;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>操作用户【OPERUSER】</u> : <br>
	* 
	* @param Operuser
	*/
	public void setOperuser(java.lang.String operuser){
		 this.operuser = operuser;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>最后修改时间【UPDATETIME】</u> : <br>
	* 
	* @param Updatetime
	*/
	public void setUpdatetime(java.util.Date updatetime){
		 this.updatetime = updatetime;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>ID【ID】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.Integer getId(){
		 return this.id;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>工厂【FACTORY】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.String getFactory(){
		 return this.factory;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>车间【WORKSHOP】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.String getWorkshop(){
		 return this.workshop;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>SHIFT【SHIFT】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.String getShift(){
		 return this.shift;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>生产日期【WORKDATE】</u> : <br>
	* 
	* @return 
	*/
	public java.util.Date getWorkdate(){
		 return this.workdate;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>工作开始时间【STARTTIME】</u> : <br>
	* 
	* @return 
	*/
	public java.util.Date getStarttime(){
		 return this.starttime;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>工作结束时间【ENDTIME】</u> : <br>
	* 
	* @return 
	*/
	public java.util.Date getEndtime(){
		 return this.endtime;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>休息时间【RESTTIME】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.Integer getResttime(){
		 return this.resttime;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>IP21【IP21】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.Integer getIp21(){
		 return this.ip21;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>IP22【IP22】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.Integer getIp22(){
		 return this.ip22;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>IP23【IP23】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.Integer getIp23(){
		 return this.ip23;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>ZP11【ZP11】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.Integer getZp11(){
		 return this.zp11;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>BP31【BP31】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.Integer getBp31(){
		 return this.bp31;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>IP24【IP24】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.Integer getIp24(){
		 return this.ip24;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>BP32【BP32】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.Integer getBp32(){
		 return this.bp32;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>AS21【AS21】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.Integer getAs21(){
		 return this.as21;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>总产量【productTotal】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.Integer getProducttotal(){
		 return this.producttotal;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>备注【REMARK】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.String getRemark(){
		 return this.remark;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>操作用户【OPERUSER】</u> : <br>
	* 
	* @return 
	*/
	public java.lang.String getOperuser(){
		 return this.operuser;
	}

	/**
	* 【CYCJ_DATE_IMPORT 计划日期数量导入:CYCJ_DATE_IMPORT】<br>
	* <u>最后修改时间【UPDATETIME】</u> : <br>
	* 
	* @return 
	*/
	public java.util.Date getUpdatetime(){
		 return this.updatetime;
	}

	public StringBuffer toXMLStringBuffer() {
		return cn.boho.framework.po.BeanUtils.formatBeanToXml(this);
	}
	public StringBuffer toXMLStringBufferNoCdata() {
		return cn.boho.framework.po.BeanUtils.formatBeanToXmlNoCdata(this);
	}
	public PmcDateImportPO clone() {
		PmcDateImportPO obj=new PmcDateImportPO();
		obj.setId(id);
		obj.setFactory(factory);
		obj.setWorkshop(workshop);
		obj.setShift(shift);
		obj.setWorkdate(workdate);
		obj.setStarttime(starttime);
		obj.setEndtime(endtime);
		obj.setResttime(resttime);
		obj.setIp21(ip21);
		obj.setIp22(ip22);
		obj.setIp23(ip23);
		obj.setZp11(zp11);
		obj.setBp31(bp31);
		obj.setIp24(ip24);
		obj.setBp32(bp32);
		obj.setAs21(as21);
		obj.setProducttotal(producttotal);
		obj.setRemark(remark);
		obj.setOperuser(operuser);
		obj.setUpdatetime(updatetime);
		return obj;
	}
}