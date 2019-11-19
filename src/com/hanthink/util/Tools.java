package com.hanthink.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.boho.framework.po.DataBean;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.utils.DateUtils;

public class Tools {

    /**
     * 将复选框的值装换成0 1
     * @author 兰永明
     * @create_date 2012-5-30 上午10:04:02
     * @param originalValue 复选框的值
     * @return
     */
    public static String getCheckboxValue(String originalValue) {
        if ("true".equals(originalValue)
                || "1".equals(originalValue)
                || "on".equals(originalValue)) {
            return DictConstants.YES;
        }
        
        return DictConstants.NO;
    }
    
    /**
     * 将复选框的值装换成0 1
     * @author 兰永明
     * @create_date 2012-5-30 上午10:04:02
     * @param originalValue 复选框的值
     * @return
     */
    public static String getCheckboxValue(Object originalValue) {
        return getCheckboxValue((String) originalValue);
    }
    
    /**
     * 将数组装换成SQL的in条件
     * @author 兰永明
     * @create_date 2012-5-31 下午04:50:54
     * @param values
     * @return
     */
    public static String strArrayToSql(Object[] values) {
        StringBuffer value = new StringBuffer();
        String val = null;
        
        for (int i = 0; i < values.length; i++) {
            val = values[i] == null ? "" : values[i].toString().replaceAll("'", "''");
            
            value.append("'")
                 .append(val)
                 .append("'");
            if (i < values.length - 1) {
                value.append(","); 
            }
        }
        return value.toString();
    }
    
    /**
     * 四舍五入
     * @author 兰永明
     * @create_date 2012-7-7 上午11:40:06
     * @param value
     * @param scale 小数点保留位数
     * @return
     */
    public static Double rounding(Double value, int scale) {
        if (null == value) {
            return null;
        }

        BigDecimal b = new BigDecimal(value);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 转化成INT
     * @author 王家乐
     * @create_date 2012-7-7 上午11:40:06
     * @param value
     * @param scale 
     * @return
     */
    public static Integer getInt(Object value) {
        if (null == value) {
            return null;
        }
        return Double.valueOf(String.valueOf(value)).intValue();
    }
    
    /**
     * 转化成INT
     * @author 王家乐
     * @create_date 2012-7-7 上午11:40:06
     * @param value
     * @param scale 
     * @return
     */
    public static Integer getInt(Object value, Integer defaultValue) {
        if (null == value) {
            return defaultValue;
        }
        return Double.valueOf(String.valueOf(value)).intValue();
    }
    
    /**
     * 转化成double
     * @author 王家乐
     * @create_date 2012-7-7 上午11:40:06
     * @param value
     * @param scale 
     * @return
     */
    public static Double getDouble(Object value) {
        if (null == value) {
            return null;
        }
        return Double.valueOf(String.valueOf(value));
    }
    
    /**
     * 转化成double
     * @author 王家乐
     * @create_date 2012-7-7 上午11:40:06
     * @param value
     * @param scale 
     * @return
     */
    public static Double getDouble(Object value, Double defaultValue) {
        if (null == value) {
            return defaultValue;
        }
        return Double.valueOf(String.valueOf(value));
    }
    
    /**
     * 转化成long
     * @author 王家乐
     * @create_date 2012-7-7 上午11:40:06
     * @param value
     * @param scale 
     * @return
     */
    public static Long getLong(Object value) {
        if (null == value) {
            return null;
        }
        return Double.valueOf(String.valueOf(value)).longValue();
    }
    
    /**
     * 转化成long
     * @author 王家乐
     * @create_date 2012-7-7 上午11:40:06
     * @param value
     * @param scale 
     * @return
     */
    public static Long getLong(Object value, Long defaultValue) {
        if (null == value) {
            return defaultValue;
        }
        return Double.valueOf(String.valueOf(value)).longValue();
    }
    
    /**
     * 转换成string
     * @author 兰永明
     * @create_date 2013-3-22 下午04:36:34
     * @param value
     * @param defalutValue
     * @return
     */
    public static String getStr(Object value) {
        if (null == value) {
            return null;
        }
        return String.valueOf(value);
    }
    
    /**
     * 获取数据
     * @author 兰永明
     * @create_date 2013-4-7 下午02:07:01
     * @param values
     * @param index
     * @return
     */
    public static String getStr(Object[] values, int index) {
        if (!hasRequestData(values) || values.length <= index) {
            return null;
        }
        
        return getStr(values[index]);
    }
    
    /**
     * 获取数据
     * @author 兰永明
     * @create_date 2013-4-7 下午02:07:01
     * @param values
     * @param index
     * @return
     */
    public static String[] getStrs(Object[] values) {
        if (!hasRequestData(values)) {
            return null;
        }
        
        String[] vals = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            vals[i] = Tools.getStr(values[i]);
        }
        
        return vals;
    }
    
    /**
     * 转换成string
     * @author 兰永明
     * @create_date 2013-3-22 下午04:36:34
     * @param value
     * @param defalutValue
     * @return
     */
    public static String getStr(Object value, String defalutValue) {
        if (null == value) {
            return defalutValue;
        }
        return String.valueOf(value);
    }
    
    /**
     * 获取对象的属性的值
     * @author 兰永明
     * @create_date 2013-3-27 下午08:47:31
     * @param dataBean 对象
     * @param fieldName 属性名
     * @return
     */
    public static String getValueByFieldName(DataBean dataBean,String fieldName){
    	Object obj="";
    	try {
    		StringBuffer methodName = new StringBuffer();
    		methodName.append("get");
    		String[] fields = fieldName.split("_");
    		
    		for(int i=0;i<fields.length;i++){
    			if(fields[i].length()==1){
    				methodName.append(fields[i].substring(0,1).toUpperCase());
    			}else if(fields[i].length()>1){
    				methodName.append(fields[i].substring(0,1).toUpperCase()+fields[i].substring(1).toLowerCase());
    			}
			}
			obj=dataBean.getClass().getMethod(methodName.toString(), new Class[]{}).invoke(dataBean, new Object[]{});
		} catch (Exception e) {
			obj="";
		} 
    	return Tools.getStr(obj,"");
    };
    
    /**
     * 合并规则
     * @author 兰永明
     * @create_date 2013-3-27 下午08:47:04
     * @param dataBean
     * @param mergeRuleField
     * @return
     */
    public static String getMergeRuleValue(DataBean dataBean,String mergeRuleField){
		final String separator = "#";
		StringBuffer strBuf = new StringBuffer();
		String[] fields = mergeRuleField.split(",");
		for(int i=0;i<fields.length;i++){
			strBuf.append(getValueByFieldName(dataBean,fields[i]));
			strBuf.append(separator);
		}
		return Tools.getStr(strBuf,"");
	}
    
    /**
     * 计算包裹数
     * @author 兰永明
     * @create_date 2012-7-9 下午04:55:04
     * @param packageQty 包装量
     * @param curQty 个数
     * @return
     */
    public static int calPackageNum(Integer packageQty, int curQty) {
        if (null == packageQty || packageQty < 1) {
            return curQty;
        }
        
        return (curQty + packageQty -1) / packageQty;
    }
    
    /**
     * 判断页面是否传入该参数
     * @author 兰永明
     * @create_date 2013-3-27 下午08:48:55
     * @param obj
     * @return
     */
    public static boolean hasRequestData(Object[] objs) {
        
        if (null == objs || objs.length == 0 || (objs.length == 1 && "".equals(objs[0]))) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 判断是否为空、或为空格（字符串）
     * @author 兰永明
     * @create_date 2013-3-29 下午05:43:16
     * @return
     */
    public static boolean isNull(Object obj) {
        if (null == obj || "".equals(obj)) {
            return true;
        }
        return false;
    }
    
    /**
     * 将Date转换成Timestamp
     * @author 兰永明
     * @create_date 2013-4-7 上午09:29:26
     * @param value
     * @return
     */
    public static Timestamp getTimestamp(Date value) {
        if (null == value) {
            return null;
        }
        
        return new Timestamp(value.getTime());
    }
    
    /**
     * 获取下一天
     * @author 兰永明
     * @create_date 2013-4-7 上午09:30:13
     * @param value
     * @return
     */
    public static Timestamp getNextDayTimestamp(Date value) {
        if (null == value) {
            return null;
        }
        
        return new Timestamp(value.getTime() + DictConstants.ONE_DAY);
    }
    
    /**
     * 获取下一天
     * @author 兰永明
     * @create_date 2013-4-7 上午09:30:13
     * @param value
     * @return
     */
    public static Timestamp getPrevitDayTimestamp(Date value) {
        if (null == value) {
            return null;
        }
        
        return new Timestamp(value.getTime() - DictConstants.ONE_DAY);
    }
    /**
     * 获取日期,去掉时分秒
     * @author 兰永明
     * @create_date 2013-4-7 上午09:30:13
     * @param value
     * @return
     */
    public static Date getDate(Date value) throws Exception{
    	String dateStr=DateUtils.format(value,DictConstants.FORMAT_DATE);
		return DateUtils.parse(dateStr, DictConstants.FORMAT_DATE);
    }
    
    /**
     * 检查数字
     * @author 兰永明
     * @create_date 2013-11-12 下午10:12:52
     * @param value 数字
     * @param allowDimcal 是否允许小数点
     * @return
     */
    public static boolean checkNumber(String value, boolean allowDimcal){
        
        String regex = "^[0-9]+";
        if (allowDimcal) {
            regex += "(\\.[0-9]+)?"; 
        } else {
            regex += "(\\.[0])?"; 
        }
        regex += "$";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
    
    /**
     * 转换成UTF-8码
     * @author 兰永明
     * @create_date 2013-8-9 上午11:29:22
     * @param value
     * @return
     */
    public static String encodeUTF8(String value)  throws Exception {
        if (null == value) {
            return null;
        }
        
        return new String(value.getBytes("ISO_8859_1"), "UTF-8");
    }
    
    /**
     * 格式化数据(日期类型)
     * @author 兰永明
     * @create_date 2013-8-2 下午04:21:52
     * @param datas
     * @param datePatter
     * @throws Exception 
     */
    public static void formatDatas(List datas, String key, String datePattern) throws Exception {
        
        Map dataMap = null;
        Object data = null;
        for (int i = 0; i < datas.size(); i++) {
            dataMap = (Map) datas.get(i);
            data = dataMap.get(key);
            
            if (data instanceof java.util.Date) {
                dataMap.put(key, DateUtils.format((Date) data, datePattern));
            }
        }
    }

	/**
     * 格式化数据(数据字典类型)
     * @author 兰永明
     * @create_date 2013-8-2 下午04:21:52
     * @param datas
     * @param dicts
     * @throws Exception 
     */
    public static void formatDatas(List datas, String key, List dataDicts) throws Exception {
        
        //获取数据字典值键对
        Map dataDictMap = new HashMap();
        DynaBeanMap dataDictBeanMap = null;
        for (int i = 0; i < dataDicts.size(); i++) {
            dataDictBeanMap = (DynaBeanMap) dataDicts.get(i);
            dataDictMap.put(dataDictBeanMap.get("VALUE"), dataDictBeanMap.get("TEXT"));
        }
        
        //转换
        Map dataMap = null;
        Object data = null;
        for (int i = 0; i < datas.size(); i++) {
            dataMap = (Map) datas.get(i);
            data = dataMap.get(key);
            
            dataMap.put(key, dataDictMap.get(Tools.getStr(data, "")));
        }
    }
    
    /**
     * 判断出入的字符是否为数字
     * @param str
     * @return 为数字则为TRUE  反之为FALSE
     */
    public static boolean getIsNumber(String str){
    	boolean flag = true;
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			flag = false; 
		} 
		return flag;
    }
    
}
