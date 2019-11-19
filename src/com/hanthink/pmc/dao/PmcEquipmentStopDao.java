/**
 * 
 *
 * @File name:  PmcEquipmentStopDao.java 
 * @Create on:  2014-03-16 16:26:969
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.pmc.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import cn.boho.framework.po.CQExp;
import cn.boho.framework.po.DAO;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

/**
 * 【工位设备停机表:PMC_EQUIPMENT_STOP】的Dao操作类
 * 
 */
public class PmcEquipmentStopDao extends DAO {
    /**
     * 查询所有【工位设备停机表:PMC_EQUIPMENT_STOP】信息
     * 
     * @param con
     * @return
     * @throws Exception
     * @date 2014-03-16
     * @author taofl
     * 
     */
    /**
     * 查询停机时间和次数
     * @param con
     * @param ppdate
     * @param proline
     * @param station
     * @param equipment
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopAgen(Connection con, String ppdate,String proline,String station,String equipment,String banci,String whereSQL) throws Exception {
    	CQExp cqExp = CQExp.instance();

    	String filed = "";
    	
    	if(!"".equals(proline)){
    		filed += ",EVENTDATA4";
    	}
    	if(!"".equals(station)){
    		filed += ",EVENTDATA5";
    	}
    	cqExp.select("select EVENTDATA7,EVENTDATA8,");
    	cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,BANCI,EVENTDATA5,");
    	cqExp.select("count(*) stopcount"+filed);
    	cqExp.select(" from tabStopSta");
    	
    	cqExp.where();
    	cqExp.select(whereSQL);
    	cqExp.filed(ppdate == null ? null : "WORKDATE", cqExp.EQ, ppdate);
    	cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
    	cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
    	cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
    	cqExp.group("EVENTDATA7,BANCI,EVENTDATA8,EVENTDATA5"+filed);
    	logger.info("查询停机时间和次数："+cqExp.getSql());
    	return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
	public static List queryPmcEquipmentStopAgenAll(Connection con, String ppdate,String proline,String station,String equipment,String whereSQL) throws Exception {
		CQExp cqExp = CQExp.instance();

		String filed = "";

		if(!"".equals(proline)){
			filed += ",EVENTDATA4";
		}
		if(!"".equals(station)){
			filed += ",EVENTDATA5";
		}
		cqExp.select("select EVENTDATA7,EVENTDATA8,");
		cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,'全部' as BANCI,EVENTDATA5,");
		cqExp.select("count(*) stopcount"+filed);
		cqExp.select(" from tabStopSta");

		cqExp.where();
		cqExp.select(whereSQL);
		cqExp.filed(ppdate == null ? null : "WORKDATE", cqExp.EQ, ppdate);
		cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
		cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
		cqExp.group("EVENTDATA7,EVENTDATA8,EVENTDATA5"+filed);
		logger.info("查询停机时间和次数："+cqExp.getSql());
		return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
	}
    /**
     * 查询设备停机时间
     * @param con
     * @param ppdate
     * @param proline
     * @param station
     * @param equipment
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopAgenEq(Connection con, String ppdate,String proline,String station,String equipment,String banci,String whereSQL) throws Exception {
    	CQExp cqExp = CQExp.instance();
    	
    	String filed = "";
    	if(!"".equals(proline)){
    		filed += ",EVENTDATA4";
    	}
    	if(!"".equals(station)){
    		filed += ",EVENTDATA5";
    	}
    	cqExp.select("select EVENTDATA7,eventdata3,EVENTDATA8,");
    	cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,BANCI,EVENTDATA5,");
    	cqExp.select("count(*) stopcount"+filed);
    	cqExp.select(" from tabStopSta");
    	cqExp.select(whereSQL);
    	cqExp.where();
    	cqExp.filed(ppdate == null ? null : "WORKDATE", cqExp.EQ, ppdate);
    	cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
    	cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
    	cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
    	cqExp.group("EVENTDATA7,EVENTDATA8,BANCI,eventdata3,EVENTDATA5"+filed);
    	logger.info("查询设备停机时间："+cqExp.getSql());
    	return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
	public static List queryPmcEquipmentStopAgenEqAll(Connection con, String ppdate,String proline,String station,String equipment,String whereSQL ) throws Exception {
		CQExp cqExp = CQExp.instance();

		String filed = "";
		if(!"".equals(proline)){
			filed += ",EVENTDATA4";
		}
		if(!"".equals(station)){
			filed += ",EVENTDATA5";
		}
		cqExp.select("select EVENTDATA7,eventdata3,EVENTDATA8,");
		cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,'全部' as BANCI,EVENTDATA5,");
		cqExp.select("count(*) stopcount"+filed);
		cqExp.select(" from tabStopSta");

		cqExp.where();
		cqExp.select(whereSQL);
		cqExp.filed(ppdate == null ? null : "WORKDATE", cqExp.EQ, ppdate);
		cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
		cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
		cqExp.group("EVENTDATA7,EVENTDATA8,eventdata3,EVENTDATA5"+filed);
		logger.info("查询设备停机时间："+cqExp.getSql());
		return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
	}
    
    
    
    //日统计
    public static int countPmcEquipmentStop(Connection con, Date ppdate,String proline) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM ");
        cqExp.select(" from PMC_EQUIPMENT_STOP");
        cqExp.where();
        
        cqExp.filed(null == ppdate ? null : "convert(varchar(20),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, DictConstants.FORMAT_DATE));
        cqExp.filed("".equals(proline) ? null : "PRODUCTIONLINENAME", CQExp.EQ, proline);
        cqExp.group(" ID,STATION,PRODUCTIONLINENAME,PRODUCTIONLINE,STATION, STOPTIME, STOPCOUNT,DATA8,DATA9,DATA10, DATA11, DATA12, DATA13,DATA14,DATA15,DATA16, DATA17, DATA18, DATA19,DATA20,DATA21,DATA22, DATA23, DATA24, DATA25,DATA26,DATA27,DATA28, DATA29, DATA30, DATA31,DATA32,DATA33,DATA34, DATA35, DATA36, DATA37,DATA38,DATA39 ");
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        logger.debug(" PMC_EQUIPMENT_STOP统计所有【工位设备停机报表:PMC_EQUIPMENT_STOP】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_EQUIPMENT_STOP", con).get("TOTAL_NUM")));
    }
    
    
    /**
     * 查询停机时间和次数(月)
     * @param con
     * @param ppdate
     * @param proline
     * @param station
     * @param equipment
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopMonth(Connection con, Date ppdate,String proline,String station,String equipment,String banci,String whereSQL) throws Exception {
    	CQExp cqExp = CQExp.instance();

    	String filed = "";
    	if(!"".equals(proline)){
    		filed += ",EVENTDATA4";
    	}
    	if(!"".equals(station)){
    		filed += ",EVENTDATA5";
    	}
    	cqExp.select("select EVENTDATA7,EVENTDATA8,");
    	cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,BANCI,EVENTDATA5,");
    	cqExp.select("count(*) stopcount"+filed);
    	cqExp.select(" from tabStopSta");

    	cqExp.where();
		cqExp.select(whereSQL);
    	cqExp.filed(ppdate == null ? null : "convert(char(7),WORKDATE,23)", cqExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
    	cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
    	cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
    	cqExp.filed("".equals(equipment)?null:"EVENTDATA5", cqExp.EQ, equipment);
    	cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
    	cqExp.group("EVENTDATA7,BANCI,EVENTDATA8,EVENTDATA5"+filed);
    	logger.info("查询停机时间和次数(月)："+cqExp.getSql());
    	return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
	public static List queryPmcEquipmentStopMonthAll(Connection con, Date ppdate,String proline,String station,String equipment,String whereSQL ) throws Exception {
		CQExp cqExp = CQExp.instance();

		String filed = "";
		if(!"".equals(proline)){
			filed += ",EVENTDATA4";
		}
		if(!"".equals(station)){
			filed += ",EVENTDATA5";
		}
		cqExp.select("select EVENTDATA7,EVENTDATA8,");
		cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,'全部' as BANCI,EVENTDATA5, ");
		cqExp.select("count(*) stopcount"+filed);
		cqExp.select(" from tabStopSta");

		cqExp.where();
		cqExp.select(whereSQL);
		cqExp.filed(ppdate == null ? null : "convert(char(7),WORKDATE,23)", cqExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
		cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
		cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
//		cqExp.filed("".equals(equipment)?null:"EVENTDATA5", cqExp.EQ, equipment);
		cqExp.group("EVENTDATA7,EVENTDATA8,EVENTDATA5"+filed);
		logger.info("查询停机时间和次数(月)："+cqExp.getSql());
		return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
	}

    /**
     * 查询设备停机时间(月)
     * @param con
     * @param ppdate
     * @param proline
     * @param station
     * @param equipment
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopMonthEq(Connection con, Date ppdate,String proline,String station,String equipment,String banci,String whereSQL) throws Exception {
    	CQExp cqExp = CQExp.instance();
    	
    	String filed = "";
    	if(!"".equals(proline)){
    		filed += ",EVENTDATA4";
    	}
    	if(!"".equals(station)){
    		filed += ",EVENTDATA5";
    	}
    	cqExp.select("select EVENTDATA7,eventdata3,EVENTDATA8,");
    	cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,BANCI,EVENTDATA5,");
    	cqExp.select("count(*) stopcount"+filed);
    	cqExp.select(" from tabStopSta");
    	
    	cqExp.where();
		cqExp.select(whereSQL);
    	cqExp.filed(ppdate == null ? null : "convert(char(7),WORKDATE,23)", cqExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
    	cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
    	cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
    	cqExp.filed("".equals(equipment)?null:"EVENTDATA5", cqExp.EQ, equipment);
    	cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
    	cqExp.group("EVENTDATA7,EVENTDATA8,BANCI,eventdata3,EVENTDATA5"+filed);
    	logger.info("查询设备停机时间(月)："+cqExp.getSql());
    	return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
	public static List queryPmcEquipmentStopMonthEqAll(Connection con, Date ppdate,String proline,String station,String equipment,String whereSQL ) throws Exception {
		CQExp cqExp = CQExp.instance();

		String filed = "";
		if(!"".equals(proline)){
			filed += ",EVENTDATA4";
		}
		if(!"".equals(station)){
			filed += ",EVENTDATA5";
		}
		cqExp.select("select EVENTDATA7,eventdata3,EVENTDATA8,");
		cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,'全部' as BANCI,EVENTDATA5,");
		cqExp.select("count(*) stopcount"+filed);
		cqExp.select(" from tabStopSta");

		cqExp.where();
		cqExp.select(whereSQL);
		cqExp.filed(ppdate == null ? null : "convert(char(7),WORKDATE,23)", cqExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
		cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
		cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
		cqExp.filed("".equals(equipment)?null:"EVENTDATA5", cqExp.EQ, equipment);
		cqExp.group("EVENTDATA7,EVENTDATA8,eventdata3,EVENTDATA5"+filed);
		logger.info("查询设备停机时间(月)："+cqExp.getSql());
		return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
	}
    
   //月统计
    public static int countPmcEquipmentStopMonth(Connection con, Date ppdate,String proline) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM ");
        cqExp.select(" from PMC_EQUIPMENT_STOP");
        cqExp.where();
        
        cqExp.filed(null == ppdate ? null : "convert(char(7),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy-MM"));
        cqExp.filed("".equals(proline) ? null : "PRODUCTIONLINENAME", CQExp.EQ, proline);
        cqExp.group(" ID,STATION,PRODUCTIONLINENAME,PRODUCTIONLINE,STATION, STOPTIME, STOPCOUNT,DATA8,DATA9,DATA10, DATA11, DATA12, DATA13,DATA14,DATA15,DATA16, DATA17, DATA18, DATA19,DATA20,DATA21,DATA22, DATA23, DATA24, DATA25,DATA26,DATA27,DATA28, DATA29, DATA30, DATA31,DATA32,DATA33,DATA34, DATA35, DATA36, DATA37,DATA38,DATA39 ");
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        logger.debug(" PMC_EQUIPMENT_STOP统计所有【工位设备停机报表:PMC_EQUIPMENT_STOP】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_EQUIPMENT_STOP", con).get("TOTAL_NUM")));
    }
    
    /**
     * 年
     * @author taofl
     * @param con
     * @param ppdate
     * @return
     * @throws Exception
     */
    /*public static List queryYearPmcEquipmentStop(Connection con, Date ppdate,String proline) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select PRODUCTIONLINENAME,PRODUCTIONLINE, STATION,");
        cqExp.select("sum(STOPTIME) as STOPTIME, sum(STOPCOUNT) as STOPCOUNT, sum(DATA8) as DATA8, sum(DATA9) as DATA9, sum(DATA10) as DATA10, sum(DATA11) as DATA11, sum(DATA12) as DATA12, ");
        cqExp.select("sum(DATA13) as DATA13, sum(DATA14) as DATA14, sum(DATA15) as DATA15, sum(DATA16) as DATA16, sum(DATA17) as DATA17, sum(DATA18) as DATA18, sum(DATA19) as DATA19, sum(DATA20) as DATA20, sum(DATA21) as DATA21, sum(DATA22) as DATA22, ");
        cqExp.select("sum(DATA23) as DATA23, sum(DATA24) as DATA24, sum(DATA25) as DATA25, sum(DATA26) as DATA26, sum(DATA27) as DATA27, sum(DATA28) as DATA18, sum(DATA29) as DATA29, sum(DATA30) as DATA30, sum(DATA31) as DATA31, sum(DATA32) as DATA32, ");
        cqExp.select("sum(DATA33) as DATA33, sum(DATA34) as DATA34, sum(DATA35) as DATA35, sum(DATA36) as DATA36, sum(DATA37) as DATA37, sum(DATA38) as DATA38, sum(DATA39) as DATA39 ");

        cqExp.select(" from PMC_EQUIPMENT_STOP");        
        cqExp.where();

        cqExp.filed(null == ppdate ? null : "convert(varchar(4),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.filed("".equals(proline) ? null : "PRODUCTIONLINENAME", CQExp.EQ, proline);
        cqExp.group("seq,PRODUCTIONLINENAME,PRODUCTIONLINE, STATION");
        cqExp.orderByAsc("seq,PRODUCTIONLINENAME");
        logger.debug(" PMC_EQUIPMENT_STOP查询所有【工位设备停机报表:PMC_EQUIPMENT_STOP】信息:  " + cqExp.getSql());
        return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }*/
    
    /**
     * 查询停机时间和次数(年)
     * @param con
     * @param ppdate
     * @param proline
     * @param station
     * @param equipment
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopYear(Connection con, Date ppdate,String proline,String station,String equipment,String banci,String whereSQL) throws Exception {
    	CQExp cqExp = CQExp.instance();
    	
    	String filed = "";
    	if(!"".equals(proline)){
    		filed += ",EVENTDATA4";
    	}
    	if(!"".equals(station)){
    		filed += ",EVENTDATA5";
    	}
    	cqExp.select("select EVENTDATA7,EVENTDATA8,");
    	cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,BANCI,EVENTDATA5,");
    	cqExp.select("count(*) stopcount"+filed);
    	cqExp.select(" from tabStopSta");
    	
    	cqExp.where();
		cqExp.select(whereSQL);
    	cqExp.filed(ppdate == null ? null : "convert(varchar(4),WORKDATE,23)", cqExp.EQ, DateUtils.format(ppdate, "yyyy"));
    	cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
    	cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
    	cqExp.filed("".equals(equipment)?null:"EVENTDATA5", cqExp.EQ, equipment);
    	cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
    	cqExp.group("EVENTDATA7,BANCI,EVENTDATA8,EVENTDATA5"+filed);
    	logger.info("查询停机时间和次数(年)："+cqExp.getSql());
    	return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
	public static List queryPmcEquipmentStopYearAll(Connection con, Date ppdate,String proline,String station,String equipment,String whereSQL) throws Exception {
		CQExp cqExp = CQExp.instance();

		String filed = "";
		if(!"".equals(proline)){
			filed += ",EVENTDATA4";
		}
		if(!"".equals(station)){
			filed += ",EVENTDATA5";
		}
		cqExp.select("select EVENTDATA7,EVENTDATA8,");
		cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,'全部' as BANCI,EVENTDATA5,");
		cqExp.select("count(*) stopcount"+filed);
		cqExp.select(" from tabStopSta");

		cqExp.where();
		cqExp.select(whereSQL);
		cqExp.filed(ppdate == null ? null : "convert(varchar(4),WORKDATE,23)", cqExp.EQ, DateUtils.format(ppdate, "yyyy"));
		cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
		cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
		cqExp.filed("".equals(equipment)?null:"EVENTDATA5", cqExp.EQ, equipment);
		cqExp.group("EVENTDATA7,EVENTDATA8,EVENTDATA5"+filed);
		logger.info("查询停机时间和次数(年)："+cqExp.getSql());
		return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
	}
    
    /**
     * 查询设备停机时间(年)
     * @param con
     * @param ppdate
     * @param proline
     * @param station
     * @param equipment
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopYearEq(Connection con, Date ppdate,String proline,String station,String equipment,String banci,String whereSQL) throws Exception {
    	CQExp cqExp = CQExp.instance();
    	
    	String filed = "";
    	if(!"".equals(proline)){
    		filed += ",EVENTDATA4";
    	}
    	if(!"".equals(station)){
    		filed += ",EVENTDATA5";
    	}
    	cqExp.select("select EVENTDATA7,eventdata3,EVENTDATA8,");
    	cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,BANCI,EVENTDATA5,");
    	cqExp.select("count(*) stopcount"+filed);
    	cqExp.select(" from tabStopSta");
    	
    	cqExp.where();
		cqExp.select(whereSQL);
    	cqExp.filed(ppdate == null ? null : "convert(varchar(4),WORKDATE,23)", cqExp.EQ, DateUtils.format(ppdate, "yyyy"));
    	cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
    	cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
    	cqExp.filed("".equals(equipment)?null:"EVENTDATA5", cqExp.EQ, equipment);
    	cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
    	cqExp.group("EVENTDATA7,EVENTDATA8,BANCI,eventdata3,EVENTDATA5"+filed);
    	logger.info("查询设备停机时间(年)："+cqExp.getSql());
    	return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
	public static List queryPmcEquipmentStopYearEqAll(Connection con, Date ppdate,String proline,String station,String equipment,String whereSQL) throws Exception {
		CQExp cqExp = CQExp.instance();

		String filed = "";
		if(!"".equals(proline)){
			filed += ",EVENTDATA4";
		}
		if(!"".equals(station)){
			filed += ",EVENTDATA5";
		}
		cqExp.select("select EVENTDATA7,eventdata3,EVENTDATA8,");
		cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,'全部' AS BANCI,EVENTDATA5,");
		cqExp.select("count(*) stopcount"+filed);
		cqExp.select(" from tabStopSta");

		cqExp.where();
		cqExp.select(whereSQL);
		cqExp.filed(ppdate == null ? null : "convert(varchar(4),WORKDATE,23)", cqExp.EQ, DateUtils.format(ppdate, "yyyy"));
		cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
		cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
		cqExp.filed("".equals(equipment)?null:"EVENTDATA5", cqExp.EQ, equipment);
		cqExp.group("EVENTDATA7,EVENTDATA8,eventdata3,EVENTDATA5"+filed);
		logger.info("查询设备停机时间(年)："+cqExp.getSql());
		return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
	}
    
    //年统计
    public static int countPmcEquipmentStopYear(Connection con, Date ppdate,String proline) throws Exception {
        CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM ");
        cqExp.select(" from PMC_EQUIPMENT_STOP");
        cqExp.where();
        
        cqExp.filed(null == ppdate ? null : "convert(char(4),PPDATE,23)", CQExp.EQ, DateUtils.format(ppdate, "yyyy"));
        cqExp.filed("".equals(proline) ? null : "PRODUCTIONLINENAME", CQExp.EQ, proline);
        cqExp.group(" ID,STATION,PRODUCTIONLINENAME,PRODUCTIONLINE,STATION, STOPTIME, STOPCOUNT,DATA8,DATA9,DATA10, DATA11, DATA12, DATA13,DATA14,DATA15,DATA16, DATA17, DATA18, DATA19,DATA20,DATA21,DATA22, DATA23, DATA24, DATA25,DATA26,DATA27,DATA28, DATA29, DATA30, DATA31,DATA32,DATA33,DATA34, DATA35, DATA36, DATA37,DATA38,DATA39 ");
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        logger.debug(" PMC_EQUIPMENT_STOP统计所有【工位设备停机报表:PMC_EQUIPMENT_STOP】信息:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_EQUIPMENT_STOP", con).get("TOTAL_NUM")));
    }
    
    /**
     * 周数据查询
     * @param con
     * @param startDate
     * @param endDate
     * @param proline
     * @param station
     * @param equipment
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopWeek(Connection con, Date startDate, Date endDate,String proline,String station,String equipment,String banci,String whereSQL) throws Exception {
    	CQExp cqExp = CQExp.instance();
    	
    	String filed = "";
    	if(!"".equals(proline)){
    		filed += ",EVENTDATA4";
    	}
    	if(!"".equals(station)){
    		filed += ",EVENTDATA5";
    	}
    	cqExp.select("select EVENTDATA7,EVENTDATA8,");
    	cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,BANCI,EVENTDATA5,");
    	cqExp.select("count(*) stopcount"+filed);
    	cqExp.select(" from tabStopSta");
    	
    	cqExp.where();
		cqExp.select(whereSQL);
    	cqExp.filed(startDate == null ? null : "convert(varchar(20),WORKDATE,23)", cqExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATE));
    	cqExp.filed(endDate == null ? null : "convert(varchar(20),WORKDATE,23)", cqExp.LESS, DateUtils.format(endDate, DictConstants.FORMAT_DATE));
    	cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
    	cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
    	cqExp.filed("".equals(equipment)?null:"EVENTDATA5", cqExp.EQ, equipment);
    	cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
    	cqExp.group("EVENTDATA7,BANCI,EVENTDATA8,EVENTDATA5"+filed);
    	logger.info("查询停机时间和次数(周)："+cqExp.getSql());
    	return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
	public static List queryPmcEquipmentStopWeekAll(Connection con, Date startDate, Date endDate,String proline,String station,String equipment,String whereSQL) throws Exception {
		CQExp cqExp = CQExp.instance();

		String filed = "";
		if(!"".equals(proline)){
			filed += ",EVENTDATA4";
		}
		if(!"".equals(station)){
			filed += ",EVENTDATA5";
		}
		cqExp.select("select EVENTDATA7,EVENTDATA8,");
		cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,'全部' AS BANCI,EVENTDATA5,");
		cqExp.select("count(*) stopcount"+filed);
		cqExp.select(" from tabStopSta");

		cqExp.where();
		cqExp.select(whereSQL);
		cqExp.filed(startDate == null ? null : "convert(varchar(20),WORKDATE,23)", cqExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATE));
		cqExp.filed(endDate == null ? null : "convert(varchar(20),WORKDATE,23)", cqExp.LESS, DateUtils.format(endDate, DictConstants.FORMAT_DATE));
		cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
		cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
		cqExp.filed("".equals(equipment)?null:"EVENTDATA5", cqExp.EQ, equipment);
		cqExp.group("EVENTDATA7,EVENTDATA8,EVENTDATA5"+filed);
		logger.info("查询停机时间和次数(周)："+cqExp.getSql());
		return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
	}
    
    /**
     * 周数据查询
     * @param con
     * @param startDate
     * @param endDate
     * @param proline
     * @param station
     * @param equipment
     * @return
     * @throws Exception
     */
    public static List queryPmcEquipmentStopWeekEq(Connection con, Date startDate, Date endDate,String proline,String station,String equipment,String banci,String whereSQL) throws Exception {
    	CQExp cqExp = CQExp.instance();
    	
    	String filed = "";
    	if(!"".equals(proline)){
    		filed += ",EVENTDATA4";
    	}
    	if(!"".equals(station)){
    		filed += ",EVENTDATA5";
    	}
    	cqExp.select("select EVENTDATA7,eventdata3,EVENTDATA8,");
    	cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,BANCI,EVENTDATA5,");
    	cqExp.select("count(*) stopcount"+filed);
    	cqExp.select(" from tabStopSta");
    	
    	cqExp.where();
		cqExp.select(whereSQL);
    	cqExp.filed(startDate == null ? null : "convert(varchar(20),eventdate,23)", cqExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATE));
    	cqExp.filed(endDate == null ? null : "convert(varchar(20),eventdate,23)", cqExp.LESS, DateUtils.format(endDate, DictConstants.FORMAT_DATE));
    	
    	cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
    	cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
    	cqExp.filed("".equals(equipment)?null:"EVENTDATA5", cqExp.EQ, equipment);
    	cqExp.filed("".equals(banci)? null : "BANCI", CQExp.EQ, banci);
    	cqExp.group("EVENTDATA7,EVENTDATA8,BANCI,eventdata3,EVENTDATA5"+filed);
    	logger.info("查询设备停机时间(周)："+cqExp.getSql());
    	return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
    }
	public static List queryPmcEquipmentStopWeekEqAll(Connection con, Date startDate, Date endDate,String proline,String station,String equipment,String whereSQL) throws Exception {
		CQExp cqExp = CQExp.instance();

		String filed = "";
		if(!"".equals(proline)){
			filed += ",EVENTDATA4";
		}
		if(!"".equals(station)){
			filed += ",EVENTDATA5";
		}
		cqExp.select("select EVENTDATA7,eventdata3,EVENTDATA8,");
		cqExp.select("round(sum(datediff(ss, EventDate, EventDate1))/60.0,2) STOPTIME,'全部' AS BANCI,EVENTDATA5,");
		cqExp.select("count(*) stopcount"+filed);
		cqExp.select(" from tabStopSta");

		cqExp.where();
		cqExp.select(whereSQL);
		cqExp.filed(startDate == null ? null : "convert(varchar(20),eventdate,23)", cqExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATE));
		cqExp.filed(endDate == null ? null : "convert(varchar(20),eventdate,23)", cqExp.LESS, DateUtils.format(endDate, DictConstants.FORMAT_DATE));

		cqExp.filed("".equals(proline)?null:"EVENTDATA8", cqExp.EQ, proline);
		cqExp.filed("".equals(station)?null:"EVENTDATA5", cqExp.EQ, station);
		cqExp.filed("".equals(equipment)?null:"EVENTDATA5", cqExp.EQ, equipment);
		cqExp.group("EVENTDATA7,EVENTDATA8,eventdata3,EVENTDATA5"+filed);
		logger.info("查询设备停机时间(周)："+cqExp.getSql());
		return cqExp.getDynaBeanMapList("PMC_EQUIPMENT_STOP", con);
	}
    
    public static int countPmcEquipmentStopWeek (Connection con, Date startDate, Date endDate,String proline) throws Exception {
    	CQExp cqExp = CQExp.instance();
        cqExp.select("select count(*) as TOTAL_NUM ");
        cqExp.select(" from PMC_EQUIPMENT_STOP");
        cqExp.where();
        cqExp.filed("convert(varchar(20),PPDATE,23)", CQExp.GREATER_EQ, DateUtils.format(startDate, DictConstants.FORMAT_DATE));
        cqExp.filed("convert(varchar(20),PPDATE,23)", CQExp.LESS_EQ, DateUtils.format(endDate, DictConstants.FORMAT_DATE));
        cqExp.filed("".equals(proline) ? null : "PRODUCTIONLINENAME", CQExp.EQ, proline);
        cqExp.group("PRODUCTIONLINENAME, PRODUCTIONLINE,STATION");
        cqExp.orderByAsc("PRODUCTIONLINENAME");
        logger.debug(" PMC_EQUIPMENT_STOP查询所有【工位设备停机报表:PMC_EQUIPMENT_STOP】总数:  " + cqExp.getSql());
        return Integer.parseInt(String.valueOf(cqExp.getDynaBeanMap("PMC_EQUIPMENT_STOP", con).get("TOTAL_NUM")));
    }
}
