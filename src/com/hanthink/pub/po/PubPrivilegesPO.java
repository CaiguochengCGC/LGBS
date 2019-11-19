package com.hanthink.pub.po;

            
import cn.boho.framework.po.DataBean;
            
        /**

 * 【PUB_PRIVILEGES:PUB_PRIVILEGES】

 */

public class PubPrivilegesPO implements DataBean {
    private final static long serialVersionUID=1l;
    private java.lang.String pkPrivilegesId;//PK_PRIVILEGES_ID
    private java.lang.String privilegesCode;//PRIVILEGES_CODE
    private java.lang.String type;//TYPE
    private java.lang.String content;//CONTENT
    private java.lang.String description;//DESCRIPTION
    private java.lang.String category;//CATEGORY
    private java.lang.String paramCode;//PARAM_CODE
    private java.lang.Long sort;//SORT
    private java.lang.String lastUpdateUsername;//LAST_UPDATE_USERNAME
    private java.lang.String lastUpdateIp;//LAST_UPDATE_IP
    private java.util.Date lastUpdateTime;//LAST_UPDATE_TIME
    private java.util.Date createTime;//CREATE_TIME
    private java.lang.String i18nResources;

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>PK_PRIVILEGES_ID【PK_PRIVILEGES_ID】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">权限ID</font>

    * @param PkPrivilegesId

    */

    public void setPkPrivilegesId(java.lang.String pkPrivilegesId){
         this.pkPrivilegesId = pkPrivilegesId;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>PRIVILEGES_CODE【PRIVILEGES_CODE】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">权限代码</font>

    * @param PrivilegesCode

    */

    public void setPrivilegesCode(java.lang.String privilegesCode){
         this.privilegesCode = privilegesCode;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>TYPE【TYPE】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">权限类型</font>

    * @param Type

    */

    public void setType(java.lang.String type){
         this.type = type;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>CONTENT【CONTENT】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">权限内容</font>

    * @param Content

    */

    public void setContent(java.lang.String content){
         this.content = content;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>DESCRIPTION【DESCRIPTION】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">权限描述</font>

    * @param Description

    */

    public void setDescription(java.lang.String description){
         this.description = description;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>CATEGORY【CATEGORY】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">分类</font>

    * @param Category

    */

    public void setCategory(java.lang.String category){
         this.category = category;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>PARAM_CODE【PARAM_CODE】</u> : <br>
    * 

    * @param ParamCode

    */

    public void setParamCode(java.lang.String paramCode){
         this.paramCode = paramCode;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>SORT【SORT】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">排序</font>

    * @param Sort

    */

    public void setSort(java.lang.Long sort){
         this.sort = sort;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>LAST_UPDATE_USERNAME【LAST_UPDATE_USERNAME】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">更改用户</font>

    * @param LastUpdateUsername

    */

    public void setLastUpdateUsername(java.lang.String lastUpdateUsername){
         this.lastUpdateUsername = lastUpdateUsername;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>LAST_UPDATE_IP【LAST_UPDATE_IP】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">最后更改IP</font>
    * @param LastUpdateIp

    */

    public void setLastUpdateIp(java.lang.String lastUpdateIp){
         this.lastUpdateIp = lastUpdateIp;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>LAST_UPDATE_TIME【LAST_UPDATE_TIME】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">修改时间</font>

    * @param LastUpdateTime

    */

    public void setLastUpdateTime(java.util.Date lastUpdateTime){
         this.lastUpdateTime = lastUpdateTime;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>CREATE_TIME【CREATE_TIME】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">创建时间</font>

    * @param CreateTime

    */

    public void setCreateTime(java.util.Date createTime){
         this.createTime = createTime;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>PK_PRIVILEGES_ID【PK_PRIVILEGES_ID】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">权限ID</font>

    * @return 

    */

    public java.lang.String getPkPrivilegesId(){
         return this.pkPrivilegesId;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>PRIVILEGES_CODE【PRIVILEGES_CODE】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">权限代码</font>

    * @return 

    */

    public java.lang.String getPrivilegesCode(){
         return this.privilegesCode;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>TYPE【TYPE】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">权限类型</font>

    * @return 

    */

    public java.lang.String getType(){
         return this.type;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>CONTENT【CONTENT】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">权限内容</font>

    * @return 

    */

    public java.lang.String getContent(){
         return this.content;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>DESCRIPTION【DESCRIPTION】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">权限描述</font>

    * @return 

    */

    public java.lang.String getDescription(){
         return this.description;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>CATEGORY【CATEGORY】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">分类</font>

    * @return 

    */

    public java.lang.String getCategory(){
         return this.category;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>PARAM_CODE【PARAM_CODE】</u> : <br>
    * 

    * @return 

    */

    public java.lang.String getParamCode(){
         return this.paramCode;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>SORT【SORT】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">排序</font>

    * @return 

    */

    public java.lang.Long getSort(){
         return this.sort;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>LAST_UPDATE_USERNAME【LAST_UPDATE_USERNAME】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">更改用户</font>

    * @return 

    */

    public java.lang.String getLastUpdateUsername(){
         return this.lastUpdateUsername;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>LAST_UPDATE_IP【LAST_UPDATE_IP】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">最后更改IP</font>

    * @return 

    */

    public java.lang.String getLastUpdateIp(){
         return this.lastUpdateIp;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>LAST_UPDATE_TIME【LAST_UPDATE_TIME】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">修改时间</font>

    * @return 

    */

    public java.util.Date getLastUpdateTime(){
         return this.lastUpdateTime;
    }

    public java.lang.String getI18nResources() {
        return i18nResources;
    }

    public void setI18nResources(java.lang.String i18nResources) {
        this.i18nResources = i18nResources;
    }

    /**

    * 【PUB_PRIVILEGES:PUB_PRIVILEGES】<br>

    * <u>CREATE_TIME【CREATE_TIME】</u> : <br>
    * &nbsp;&nbsp;<font color="#3300CC">创建时间</font>

    * @return 

    */

    public java.util.Date getCreateTime(){
         return this.createTime;
    }

    public StringBuffer toXMLStringBuffer() {
        return cn.boho.framework.po.BeanUtils.formatBeanToXml(this);
    }
    public StringBuffer toXMLStringBufferNoCdata() {
        return cn.boho.framework.po.BeanUtils.formatBeanToXmlNoCdata(this);
    }
    public PubPrivilegesPO clone() {

        PubPrivilegesPO obj=new PubPrivilegesPO();

        obj.setPkPrivilegesId(pkPrivilegesId);

        obj.setPrivilegesCode(privilegesCode);

        obj.setType(type);

        obj.setContent(content);

        obj.setDescription(description);

        obj.setCategory(category);

        obj.setParamCode(paramCode);

        obj.setSort(sort);

        obj.setLastUpdateUsername(lastUpdateUsername);

        obj.setLastUpdateIp(lastUpdateIp);

        obj.setLastUpdateTime(lastUpdateTime);

        obj.setCreateTime(createTime);
        
        obj.setI18nResources(i18nResources);

        return obj;

    }

}