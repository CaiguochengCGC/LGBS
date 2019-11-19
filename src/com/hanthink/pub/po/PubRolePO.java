package com.hanthink.pub.po;

import cn.boho.framework.po.DataBean;

/**
 * 【角色:PUB_Role】
 */
public class PubRolePO implements DataBean {
	private final static long serialVersionUID = 1l;
	private java.lang.String pkRoleId;// 角色编号
	private java.lang.String roleName;// 角色名称
	private java.lang.String roleDescription;// 角色描述
	private java.lang.String lastUpdateUsername;// 更改用户
	private java.lang.String lastUpdateIp;// 最后更改IP
	private java.util.Date lastUpdateTime;// 修改时间
	private java.util.Date createTime;// 创建时间

	/**
	 * 【角色:PUB_Role】<br>
	 * <u>角色编号【PK_ROLE_ID】</u> : <br>
	 * 
	 * @param PkRoleId
	 */
	public void setPkRoleId(java.lang.String pkRoleId) {
		this.pkRoleId = pkRoleId;
	}

	/**
	 * 【角色:PUB_Role】<br>
	 * <u>角色名称【ROLE_NAME】</u> : <br>
	 * 
	 * @param RoleName
	 */
	public void setRoleName(java.lang.String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 【角色:PUB_Role】<br>
	 * <u>角色描述【ROLE_DESCRIPTION】</u> : <br>
	 * 
	 * @param RoleDescription
	 */
	public void setRoleDescription(java.lang.String roleDescription) {
		this.roleDescription = roleDescription;
	}

	/**
	 * 【角色:PUB_Role】<br>
	 * <u>更改用户【LAST_UPDATE_USERNAME】</u> : <br>
	 * 
	 * @param LastUpdateUsername
	 */
	public void setLastUpdateUsername(java.lang.String lastUpdateUsername) {
		this.lastUpdateUsername = lastUpdateUsername;
	}

	/**
	 * 【角色:PUB_Role】<br>
	 * <u>最后更改IP【LAST_UPDATE_IP】</u> : <br>
	 * 
	 * @param LastUpdateIp
	 */
	public void setLastUpdateIp(java.lang.String lastUpdateIp) {
		this.lastUpdateIp = lastUpdateIp;
	}

	/**
	 * 【角色:PUB_Role】<br>
	 * <u>修改时间【LAST_UPDATE_TIME】</u> : <br>
	 * 
	 * @param LastUpdateTime
	 */
	public void setLastUpdateTime(java.util.Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * 【角色:PUB_Role】<br>
	 * <u>创建时间【CREATE_TIME】</u> : <br>
	 * 
	 * @param CreateTime
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 【角色:PUB_Role】<br>
	 * <u>角色编号【PK_ROLE_ID】</u> : <br>
	 * 
	 * @return
	 */
	public java.lang.String getPkRoleId() {
		return this.pkRoleId;
	}

	/**
	 * 【角色:PUB_Role】<br>
	 * <u>角色名称【ROLE_NAME】</u> : <br>
	 * 
	 * @return
	 */
	public java.lang.String getRoleName() {
		return this.roleName;
	}

	/**
	 * 【角色:PUB_Role】<br>
	 * <u>角色描述【ROLE_DESCRIPTION】</u> : <br>
	 * 
	 * @return
	 */
	public java.lang.String getRoleDescription() {
		return this.roleDescription;
	}

	/**
	 * 【角色:PUB_Role】<br>
	 * <u>更改用户【LAST_UPDATE_USERNAME】</u> : <br>
	 * 
	 * @return
	 */
	public java.lang.String getLastUpdateUsername() {
		return this.lastUpdateUsername;
	}

	/**
	 * 【角色:PUB_Role】<br>
	 * <u>最后更改IP【LAST_UPDATE_IP】</u> : <br>
	 * 
	 * @return
	 */
	public java.lang.String getLastUpdateIp() {
		return this.lastUpdateIp;
	}

	/**
	 * 【角色:PUB_Role】<br>
	 * <u>修改时间【LAST_UPDATE_TIME】</u> : <br>
	 * 
	 * @return
	 */
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	/**
	 * 【角色:PUB_Role】<br>
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
}