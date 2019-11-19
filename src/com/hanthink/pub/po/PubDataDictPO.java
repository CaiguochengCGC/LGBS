package com.hanthink.pub.po;

import cn.boho.framework.po.DataBean;

/**
 * 【基础参数维护:PUB_DATA_DICT】
 */
public class PubDataDictPO implements DataBean {
	private final static long serialVersionUID = 1l;
	private java.lang.String pkId;// 主键PK_ID
	private java.lang.String codeType;// 类型
	private java.lang.String codeTypeName;// 类型名称
	private java.lang.String codeValue;// 编码
	private java.lang.String codeValueName;// 编码名称
	private java.lang.Integer isEdit;//是否可编辑，0-不可见，1-可见不可编辑，2-可见可编辑
	private java.lang.String otherCodeValue1;//第三方系统代码
	private java.lang.String codeDesc;// 备注
	private java.lang.Integer sortNo;// 显示顺序
	private java.lang.Integer compare;// 判定时间
	private java.lang.String lastUpdateUsername;// 更改用户
	private java.lang.String lastUpdateIp;// 最后更改IP
	private java.util.Date lastUpdateTime;// 修改时间
	private java.util.Date createTime;// 创建时间

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>类型【CODE_TYPE】</u> : <br>
	 * 
	 * @param PkCodeType
	 *//*
	public void setPkCodeType(java.lang.String pkCodeType) {
		this.pkCodeType = pkCodeType;
	}*/

	public java.lang.Integer getCompare() {
		return compare;
	}

	public void setCompare(java.lang.Integer compare) {
		this.compare = compare;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>类型名称【CODE_TYPE_NAME】</u> : <br>
	 * 
	 * @param CodeTypeName
	 */
	public void setCodeTypeName(java.lang.String codeTypeName) {
		this.codeTypeName = codeTypeName;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>编码【CODE_VALUE】</u> : <br>
	 * 
	 * @param PkCodeValue
	 *//*
	public void setPkCodeValue(java.lang.String pkCodeValue) {
		this.pkCodeValue = pkCodeValue;
	}*/

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>编码名称【CODE_VALUE_NAME】</u> : <br>
	 * 
	 * @param CodeValueName
	 */
	public void setCodeValueName(java.lang.String codeValueName) {
		this.codeValueName = codeValueName;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>备注【CODE_DESC】</u> : <br>
	 * 
	 * @param CodeDesc
	 */
	public void setCodeDesc(java.lang.String codeDesc) {
		this.codeDesc = codeDesc;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>显示顺序【SORT_NO】</u> : <br>
	 * 
	 * @param SortNo
	 */
	public void setSortNo(java.lang.Integer sortNo) {
		this.sortNo = sortNo;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>更改用户【LAST_UPDATE_USERNAME】</u> : <br>
	 * 
	 * @param LastUpdateUsername
	 */
	public void setLastUpdateUsername(java.lang.String lastUpdateUsername) {
		this.lastUpdateUsername = lastUpdateUsername;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>最后更改IP【LAST_UPDATE_IP】</u> : <br>
	 * 
	 * @param LastUpdateIp
	 */
	public void setLastUpdateIp(java.lang.String lastUpdateIp) {
		this.lastUpdateIp = lastUpdateIp;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>修改时间【LAST_UPDATE_TIME】</u> : <br>
	 * 
	 * @param LastUpdateTime
	 */
	public void setLastUpdateTime(java.util.Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>创建时间【CREATE_TIME】</u> : <br>
	 * 
	 * @param CreateTime
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>类型【CODE_TYPE】</u> : <br>
	 * 
	 * @return
	 *//*
	public java.lang.String getPkCodeType() {
		return this.pkCodeType;
	}*/

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>类型名称【CODE_TYPE_NAME】</u> : <br>
	 * 
	 * @return
	 */
	public java.lang.String getCodeTypeName() {
		return this.codeTypeName;
	}

	public java.lang.String getCodeType() {
		return codeType;
	}

	public void setCodeType(java.lang.String codeType) {
		this.codeType = codeType;
	}

	public java.lang.String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(java.lang.String codeValue) {
		this.codeValue = codeValue;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>编码【CODE_VALUE】</u> : <br>
	 * 
	 * @return
	 *//*
	public java.lang.String getPkCodeValue() {
		return this.pkCodeValue;
	}
*/
	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>编码名称【CODE_VALUE_NAME】</u> : <br>
	 * 
	 * @return
	 */
	public java.lang.String getCodeValueName() {
		return this.codeValueName;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>备注【CODE_DESC】</u> : <br>
	 * 
	 * @return
	 */
	public java.lang.String getCodeDesc() {
		return this.codeDesc;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>显示顺序【SORT_NO】</u> : <br>
	 * 
	 * @return
	 */
	public java.lang.Integer getSortNo() {
		return this.sortNo;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>更改用户【LAST_UPDATE_USERNAME】</u> : <br>
	 * 
	 * @return
	 */
	public java.lang.String getLastUpdateUsername() {
		return this.lastUpdateUsername;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>最后更改IP【LAST_UPDATE_IP】</u> : <br>
	 * 
	 * @return
	 */
	public java.lang.String getLastUpdateIp() {
		return this.lastUpdateIp;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>修改时间【LAST_UPDATE_TIME】</u> : <br>
	 * 
	 * @return
	 */
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	/**
	 * 【基础参数维护:PUB_DATA_DICT】<br>
	 * <u>创建时间【CREATE_TIME】</u> : <br>
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

	public java.lang.Integer getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(java.lang.Integer isEdit) {
		this.isEdit = isEdit;
	}

	public java.lang.String getOtherCodeValue1() {
		return otherCodeValue1;
	}

	public void setOtherCodeValue1(java.lang.String otherCodeValue1) {
		this.otherCodeValue1 = otherCodeValue1;
	}

	public java.lang.String getPkId() {
		return pkId;
	}

	public void setPkId(java.lang.String pkId) {
		this.pkId = pkId;
	}
}