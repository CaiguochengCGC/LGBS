package com.hanthink.util;

public class DictConstants {

    //超级用户
    public final static String SUPERUSER = "admin";
    
    //session信息
    public final static String SESSION_USER_NO = "SESSION_USER_NO";
    public final static String SESSION_USER_BEAN = "SESSION_USER_BEAN";
    public final static String SESSION_PRI_MENU = "SESSION_PRI_MENU";
    public final static String SESSION_PRI_DATA = "SESSION_PRI_DATA";
    public final static String SESSION_PRI_ACTION = "SESSION_PRI_ACTION";
    public final static String SESSION_MODULE = "SESSION_MODULE";
    
    //日期时间格式
    public final static String FORMAT_DATE = "yyyy-MM-dd";//日期格式化
    public final static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";//日期时间格式化
    public final static String FORMAT_YYYYMMDD = "yyyyMMdd";//日期格式化
    public final static String FORMAT_HHMI = "HHmm";//时间格式化
    public final static String FORMAT_YYYYMMDDHHMI = "yyyyMMddHHmm";//日期时间格式化
    public final static long ONE_DAY = 24 * 60 * 60 * 1000;//一天
    
    //是否标记位
    public final static String YES = "1";//是
    public final static String NO = "0";//否
    
    //数据操作值
    public final static String CMD_ADD = "A";//新增
    public final static String CMD_UPDATE = "U";//修改
    public final static String CMD_DELETE = "D";//删除
    
    //权限类型
    public final static String PRIVI_TYPE_MUNU = "0";//菜单
    public final static String PRIVI_TYPE_DATA = "1";//数据过滤
    public final static String PRIVI_TYPE_ACTION = "2";//数据过滤
    
    //小数点个数
    public final static int SCALE = 2;
    public final static int STEAM_SCALE = 5;
    
    //瓶颈设备-TOP位数
    public final static int PMC_DATE_PPNECK_PAGE_SIZE = 5;
    
    public final static String QUERY_TYPE_DICT_OR_PARAM = "PARAM";
    public final static String QUERY_COMPARE_TIME = "COMPARE_TIME";
    
    
}
