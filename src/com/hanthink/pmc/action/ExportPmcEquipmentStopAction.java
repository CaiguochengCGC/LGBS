package com.hanthink.pmc.action;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;

import cn.boho.framework.utils.DateUtils;
import com.hanthink.pmc.dao.PmcEquipmentStopDao;
import com.hanthink.pub.dao.SysParamDao;
import com.hanthink.util.ExcelUtil;
import com.hanthink.util.Tools;

public class ExportPmcEquipmentStopAction extends ActionImp {
    private Connection con = null;
//    private String ppdate;
private Date ppdate;
    private java.lang.String proline;
    private java.lang.String station;
    private java.lang.String equipmentstop;
    private String banci;
    private DynaBeanMap dbm = new DynaBeanMap();
	private java.lang.String queryType;
	private String queryTypeT;

    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【工位设备停机日表】", ex);
    }

    protected int performExecute(ActionContext atx) throws Exception {
		String whereSQL="";
		if("1".equals(queryTypeT)){
			whereSQL=" AND (EventData8='DLHD3' or EventData8='FDRDL' or EventData8='FDRDR' or EventData8='FRDL2' or EventData8='FRDL3' or EventData8='FRDR2' or EventData8='HDTG2' or EventData8='XHDTG') ";
		}else if("2".equals(queryTypeT)){
			whereSQL=" AND EventData8!='DLHD3' AND EventData8!='FDRDL' AND EventData8!='FDRDR' AND EventData8!='FRDL2' AND EventData8!='FRDL3' AND EventData8!='FRDR2' AND EventData8 !='HDTG2' AND EventData8 !='XHDTG' ";
		}
        String[] widthTmps = Tools.getStrs(atx.getArrayValue("WIDTH"));
    	List returnList = new LinkedList();
        
        String[] columns ={"PRODUCTIONLINENAME", "STATION","EVENTDATA5", "STOPTIME","STOPCOUNT", "DATA8", "DATA9", "DATA10", "DATA11", "DATA12", "DATA13", "DATA14", "DATA15", "DATA16","DATA17","DATA18", "DATA19", "DATA20", "DATA21", "DATA22", "DATA23", "DATA24", "DATA25", "DATA26","DATA27","DATA28", "DATA29", "DATA30", "DATA31", "DATA32", "DATA33", "DATA34", "DATA35", "DATA36","DATA37","DATA38","DATA39"};
    	String[] headers = {"工段名字", "工位","设备", "停机总时间（分钟）","停机总次数", "ANDON","APC故障", "AVI故障", "CO2焊故障", "DNET故障", "PLC故障", "安全设备故障", "冲压设备故障", "电源故障", "定位销故障","阀岛故障","焊机故障","焊枪故障","换枪盘故障","机器人","激光焊设备故障","急停","夹紧翻转缸故障","冷却水故障","螺柱焊故障","模块故障","气压低故障","视觉设备故障","输送设备传感器故障","输送设备故障","停放架故障","涂胶设备故障","物料传感器故障","修磨器故障","以太网故障","在线测量","转台故障"};
        int[] widths = new int[widthTmps.length];
        for (int i = 0; i < widthTmps.length; i++) {
            widths[i] = Tools.getInt(widthTmps[i], Integer.valueOf(100)).intValue();
        }

        String fileName="";
//        List list = PmcEquipmentStopDao.queryPmcEquipmentStopAgen(con, ppdate,proline,station,"",banci);
//        List listEq = PmcEquipmentStopDao.queryPmcEquipmentStopAgenEq(con, ppdate,proline,station,"",banci);
		List list = new LinkedList();
		List listEq = new LinkedList();

		if ("0".equals(queryType)) { // 日
			fileName = "工位设备停机日报表";
			String ppdate = atx.getStringValue("PPDATE");
			proline =  atx.getStringValue("PRODUCTIONLINENAME", "").trim();
			if ("1".equals(banci)||"2".equals(banci)) {
				list = PmcEquipmentStopDao.queryPmcEquipmentStopAgen(con, ppdate, proline, station, equipmentstop, banci,whereSQL);
				listEq = PmcEquipmentStopDao.queryPmcEquipmentStopAgenEq(con, ppdate, proline, station, equipmentstop, banci,whereSQL);
			}else{
				list = PmcEquipmentStopDao.queryPmcEquipmentStopAgenAll(con, ppdate, proline, station, equipmentstop,whereSQL);
				listEq = PmcEquipmentStopDao.queryPmcEquipmentStopAgenEqAll(con, ppdate, proline, station, equipmentstop,whereSQL);
			}
		} else if ("1".equals(queryType)) { // 月
			fileName = "工位设备停机月报表";
			ppdate = DateUtils.parse(atx.getStringValue("PPDATE"), "yyyy-MM");
			proline =  atx.getStringValue("PRODUCTIONLINENAME", "").trim();
			if ("1".equals(banci)||"2".equals(banci)) {
				list = PmcEquipmentStopDao.queryPmcEquipmentStopMonth(con, ppdate, proline, station, equipmentstop, banci,whereSQL);
				listEq = PmcEquipmentStopDao.queryPmcEquipmentStopMonthEq(con, ppdate, proline, station, equipmentstop, banci,whereSQL);
			}else {
				list = PmcEquipmentStopDao.queryPmcEquipmentStopMonthAll(con, ppdate, proline, station, equipmentstop,whereSQL);
				listEq = PmcEquipmentStopDao.queryPmcEquipmentStopMonthEqAll(con, ppdate, proline, station, equipmentstop,whereSQL);
			}
		} else if ("2".equals(queryType)) {  // 年
			fileName = "工位设备停机年报表";
			ppdate = DateUtils.parse(atx.getStringValue("PPDATE"), "yyyy");
			proline =  atx.getStringValue("PRODUCTIONLINENAME", "").trim();
			if ("1".equals(banci)||"2".equals(banci)) {
				list = PmcEquipmentStopDao.queryPmcEquipmentStopYear(con, ppdate, proline, station, equipmentstop, banci,whereSQL);
				listEq = PmcEquipmentStopDao.queryPmcEquipmentStopYearEq(con, ppdate, proline, station, equipmentstop, banci,whereSQL);
			}else{
				list = PmcEquipmentStopDao.queryPmcEquipmentStopYearAll(con, ppdate, proline, station, equipmentstop,whereSQL);
				listEq = PmcEquipmentStopDao.queryPmcEquipmentStopYearEqAll(con, ppdate, proline, station, equipmentstop,whereSQL);
			}
		} else if ("3".equals(queryType)) { // 周
			fileName = "工位设备停机周报表";
			logger.debug("------------------------第几周-------："+atx.getStringValue("PPDATE", ""));
			int week = Tools.getInt(atx.getStringValue("PPDATE", ""), 0);
			Date date = new Date();
			Calendar ca = Calendar.getInstance();
			String year = DateUtils.format(date, "yyyy");
			String firstDay = year + "-01-01";

			//当年的第一天的日期
			Date first = DateUtils.parse(firstDay, "yyyy-MM-dd");
			ca.setTime(first);
			ca.add(Calendar.WEEK_OF_YEAR, week);
			int weekTemp = ca.get(Calendar.DAY_OF_WEEK) - 2;
			ca.add(Calendar.DAY_OF_YEAR, -weekTemp);

			//第week周第一天日期和第7天日期
			Date firstOfWeek = ca.getTime();
			ca.add(Calendar.DAY_OF_YEAR, 6);
			Date lastOfWeek = ca.getTime();
			proline =  atx.getStringValue("PRODUCTIONLINENAME", "").trim();
			if ("1".equals(banci)||"2".equals(banci)) {
				list = PmcEquipmentStopDao.queryPmcEquipmentStopWeek(con, firstOfWeek, lastOfWeek, proline, station, equipmentstop, banci,whereSQL);
				listEq = PmcEquipmentStopDao.queryPmcEquipmentStopWeekEq(con, firstOfWeek, lastOfWeek, proline, station, equipmentstop, banci,whereSQL);
			}else{
				list = PmcEquipmentStopDao.queryPmcEquipmentStopWeekAll(con, firstOfWeek, lastOfWeek, proline, station, equipmentstop,whereSQL);
				listEq = PmcEquipmentStopDao.queryPmcEquipmentStopWeekEqAll(con, firstOfWeek, lastOfWeek, proline, station, equipmentstop,whereSQL);
			}
		}

		if ("".equals(station) && "".equals(proline)){
			for(int i=0; i<list.size(); i++){
				DynaBeanMap dbmList = (DynaBeanMap) list.get(i);
				dbm = new DynaBeanMap();
				String dbPr = Tools.getStr(dbmList.get("EVENTDATA7"),"");//线体中文名
				String dbprE = Tools.getStr(dbmList.get("EVENTDATA8"),"");//线体英文名
				String dbBANCI = Tools.getStr(dbmList.get("BANCI"),"");//线体英文名
				String dbstation=Tools.getStr(dbmList.get("EVENTDATA5"),"");//工位
				double stopTime = Tools.getDouble(dbmList.get("STOPTIME"), 0.0);//停线时间
				int stopCount = Tools.getInt(dbmList.get("stopcount"), 0);//停线次数
				for(int j=0; j<listEq.size(); j++){
					DynaBeanMap eqList = (DynaBeanMap) listEq.get(j);
					String eqPr = Tools.getStr(eqList.get("EVENTDATA7"),"");//线体中文名
					String eqprE = Tools.getStr(eqList.get("EVENTDATA8"),"");//线体英文名
					String eqstation=Tools.getStr(eqList.get("EVENTDATA5"),"");//工位
					String eqType = Tools.getStr(eqList.get("eventdata3"),"");//32类
					String eqBANCI = Tools.getStr(eqList.get("BANCI"),"");//32类
					double eqstoptime = Tools.getDouble(eqList.get("STOPTIME"), 0.0);//停线时间
					if(dbprE.equals(eqprE) && dbPr.equals(eqPr)&&dbBANCI.equals(eqBANCI)&&dbstation.equals(eqstation)){
						dbm.put("PRODUCTIONLINE", dbprE);//线体英文名
						dbm.put("PRODUCTIONLINENAME", eqPr);//线体中文名
						dbm.put("EVENTDATA5", "全部");//设备
						dbm.put("STATION", eqstation);//站点(工位)
						dbm.put("STOPTIME", stopTime);//停机总时间
						dbm.put("STOPCOUNT", stopCount);//停机总次数
						dbm.put("BANCI", eqBANCI);//停机总次数
						return32Type(dbm,eqType,eqstoptime);
					}
				}
				returnList.add(dbm);
			}
		}else if ("".equals(station) && !"".equals(proline)){
			for(int i=0; i<list.size(); i++){
				DynaBeanMap dbmList = (DynaBeanMap) list.get(i);
				dbm = new DynaBeanMap();
				String dbStation = Tools.getStr(dbmList.get("EVENTDATA5"),"");//站点
				String dbPr = Tools.getStr(dbmList.get("EVENTDATA7"),"");//线体中文名
				String dbprE = Tools.getStr(dbmList.get("EVENTDATA8"),"");//线体英文名
				double stopTime = Tools.getDouble(dbmList.get("STOPTIME"), 0.0);//停线时间
				int stopCount = Tools.getInt(dbmList.get("stopcount"), 0);//停线次数
				String dbBANCI = Tools.getStr(dbmList.get("BANCI"),"");//线体英文名
				for(int j=0; j<listEq.size(); j++){
					DynaBeanMap eqList = (DynaBeanMap) listEq.get(j);
					String eqStation = Tools.getStr(eqList.get("EVENTDATA5"),"");//站点
					String eqPr = Tools.getStr(eqList.get("EVENTDATA7"),"");//线体中文名
					String eqprE = Tools.getStr(eqList.get("EVENTDATA8"),"");//线体英文名
					String eqType = Tools.getStr(eqList.get("eventdata3"),"");//32类
					String eqBANCI = Tools.getStr(eqList.get("BANCI"),"");//32类
					double eqstoptime = Tools.getDouble(eqList.get("STOPTIME"), 0.0);//停线时间
					if(dbprE.equals(eqprE) && dbStation.equals(eqStation) && dbPr.equals(eqPr)&&dbBANCI.equals(eqBANCI)){
						dbm.put("PRODUCTIONLINE", dbprE);//线体英文名
						dbm.put("PRODUCTIONLINENAME", eqPr);//线体中文名
						dbm.put("EVENTDATA5", "全部");//设备
						dbm.put("STATION", eqStation);//站点(工位)
						dbm.put("STOPTIME", stopTime);//停机总时间
						dbm.put("STOPCOUNT", stopCount);//停机总次数
						dbm.put("BANCI", eqBANCI);//
						return32Type(dbm,eqType,eqstoptime);
					}
				}
				returnList.add(dbm);
			}
		}else {
			for(int i=0; i<list.size(); i++){
				DynaBeanMap dbmList = (DynaBeanMap) list.get(i);
				dbm = new DynaBeanMap();
				String dbStation = Tools.getStr(dbmList.get("EVENTDATA5"),"");//站点
				String dbEqu = Tools.getStr(dbmList.get("EVENTDATA5"),"");//设备
				String dbPr = Tools.getStr(dbmList.get("EVENTDATA7"),"");//线体中文名
				String dbprE = Tools.getStr(dbmList.get("EVENTDATA8"),"");//线体英文名
				double stopTime = Tools.getDouble(dbmList.get("STOPTIME"), 0.0);//停线时间
				int stopCount = Tools.getInt(dbmList.get("stopcount"), 0);//停线次数
				String dbBANCI = Tools.getStr(dbmList.get("BANCI"),"");//线体英文名
				for(int j=0; j<listEq.size(); j++){
					DynaBeanMap eqList = (DynaBeanMap) listEq.get(j);
					String eqStation = Tools.getStr(eqList.get("EVENTDATA5"),"");//站点
					String eqEqu = Tools.getStr(eqList.get("EVENTDATA5"),"");//设备
					String eqBANCI = Tools.getStr(eqList.get("BANCI"),"");//32类
					String eqPr = Tools.getStr(eqList.get("EVENTDATA7"),"");//线体中文名
					String eqprE = Tools.getStr(eqList.get("EVENTDATA8"),"");//线体英文名
					String eqType = Tools.getStr(eqList.get("eventdata3"),"");//32类
					double eqstoptime = Tools.getDouble(eqList.get("STOPTIME"), 0.0);//停线时间
					if(dbprE.equals(eqprE) && dbStation.equals(eqStation) && dbEqu.equals(eqEqu) && dbPr.equals(eqPr)&&dbBANCI.equals(eqBANCI)){
						dbm.put("PRODUCTIONLINE", dbprE);//线体英文名
						dbm.put("PRODUCTIONLINENAME", eqPr);//线体中文名
						dbm.put("EVENTDATA5", eqEqu);//设备
						dbm.put("STATION", eqStation);//站点(工位)
						dbm.put("STOPTIME", stopTime);//停机总时间
						dbm.put("STOPCOUNT", stopCount);//停机总次数
						dbm.put("BANCI", eqBANCI);//停机总次数
						return32Type(dbm,eqType,eqstoptime);
					}
				}
				returnList.add(dbm);
			}
		}

        atx.getHttpResponse().setContentType("application/vnd.ms-excel;charset=utf-8");
        atx.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO_8859_1") + ".xls");
        ServletOutputStream out = atx.getHttpResponse().getOutputStream();

        ExcelUtil.exportExcel(fileName, headers, columns, widths, returnList, out);
        out.flush();

        return 1;
    }

    @SuppressWarnings("unchecked")
	private void return32Type(DynaBeanMap dbm,String type, Double stopTime) throws Exception{
		if("ANDON".equals(type)){
			dbm.put("DATA8",stopTime);
		}else if("APC故障".equals(type)){
			dbm.put("DATA9",stopTime);
		}else if("AVI故障".equals(type)){ //
			dbm.put("DATA10",stopTime);
		}else if("CO2焊故障".equals(type)){
			dbm.put("DATA11",stopTime);
		}else if("DNET故障".equals(type)){
			dbm.put("DATA12",stopTime);
		}else if("PLC故障".equals(type)){
			dbm.put("DATA13",stopTime);
		}else if("安全设备故障".equals(type)){
			dbm.put("DATA14",stopTime);
		}else if("冲压设备故障".equals(type)){
			dbm.put("DATA15",stopTime);
		}else if("电源故障".equals(type)){
			dbm.put("DATA16",stopTime);
		}else if("定位销故障".equals(type)){ //
			dbm.put("DATA17",stopTime);
		}else if("阀岛故障".equals(type)){
			dbm.put("DATA18",stopTime);
		}else if("焊机故障".equals(type)){
			dbm.put("DATA19",stopTime);
		}else if("焊枪故障".equals(type)){
			dbm.put("DATA20",stopTime);
		}else if("换枪盘故障".equals(type)){
			dbm.put("DATA21",stopTime);
		}else if("机器人".equals(type)){  //
			dbm.put("DATA22",stopTime);
		}else if("激光焊设备故障".equals(type)){
			dbm.put("DATA23",stopTime);
		}else if("急停".equals(type)){   //
			dbm.put("DATA24",stopTime);
		}else if("夹紧翻转缸故障".equals(type)){
			dbm.put("DATA25",stopTime);
		}else if("冷却水故障".equals(type)){   //
			dbm.put("DATA26",stopTime);
		}else if("螺柱焊故障".equals(type)){
			dbm.put("DATA27",stopTime);
		}else if("模块故障".equals(type)){   //
			dbm.put("DATA28",stopTime);
		}else if("气压低故障".equals(type)){
			dbm.put("DATA29",stopTime);
		}else if("视觉设备故障".equals(type)){ //
			dbm.put("DATA30",stopTime);
		}else if("输送设备传感器故障".equals(type)){
			dbm.put("DATA31",stopTime);
		}else if("输送设备故障".equals(type)){
			dbm.put("DATA32",stopTime);
		}else if("停放架故障".equals(type)){
			dbm.put("DATA33",stopTime);
		}else if("涂胶设备故障".equals(type)){
			dbm.put("DATA34",stopTime);
		}else if("物料传感器故障".equals(type)){
			dbm.put("DATA35",stopTime);
		}else if("修磨器故障".equals(type)){  //
			dbm.put("DATA36",stopTime);
		}else if("以太网故障".equals(type)){
			dbm.put("DATA37",stopTime);
		}else if("在线测量".equals(type)){
			dbm.put("DATA38",stopTime);
		}else if("转台故障".equals(type)){
			dbm.put("DATA39",stopTime);
		}
    }
    
    protected int verifyParameters(ActionContext atx) throws Exception {
		queryTypeT=atx.getStringValue("queryType", "");
		queryType = atx.getStringValue("QUERY_TYPE", "").trim();
        con = atx.getConection();
//        ppdate = atx.getStringValue("PPDATE");
        proline=java.net.URLDecoder.decode(atx.getStringValue("PRODUCTIONLINENAME", "").trim(),"UTF-8");
        banci=atx.getStringValue("BANCI", "");
        station = atx.getStringValue("STATION", "").trim();
        equipmentstop = java.net.URLDecoder.decode(atx.getStringValue("EQUIPMENTSTOP", "").trim(),"UTF-8");
        return 1;
    }
}
