package com.hanthink.cycj.dao;

import java.sql.Connection;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;

public class TabStopLineTempDao extends DAO {

    /**
     * 查询停机报表
     * @author lanym
     * @create_date 2014-2-25 下午08:32:14
     * @param con
     * @param stoptime
     * @param toltlag
     * @return
     * @throws Exception 
     */
    public static List queryTabStopLineTemp(Connection con) throws Exception {
        
        CQExp cqexp = CQExp.instance();
        cqexp.select("select STATION, STOPTIME, STOPCOUNT");
        cqexp.select(" from CYCJ_tabStopLineTemp");
        
        logger.info("冲压车间查询停机报表：" + cqexp.getSql());
        
        return cqexp.getDynaBeanMapList("tabStopLineTemp", con);
    }

}
