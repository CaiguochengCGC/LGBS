package com.hanthink.util;

import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.Pager;

public class PagerUtils {
    
    /**
     * 根据上下文分页数据返回分页对象
     * @author 兰永明
     * @create_date 2013-9-1 上午12:11:28
     * @param atx 上下文对象
     * @return
     */
    public static Pager getPager(ActionContext atx) {
        
        int pageSize = 100;
        int currentPage = 1;
        
        if (atx.getStringValue("limit") != null && !atx.getStringValue("limit").equals("")) {
            pageSize = new Integer(atx.getStringValue("limit"));
        }
        if (atx.getStringValue("start") != null && !atx.getStringValue("start").equals("")) {
            currentPage = new Integer(atx.getStringValue("start")) / pageSize + 1;
        }
        
        return new Pager(pageSize, currentPage);
    }
}
