/**
 * 
 *
 * @File name:  QueryPmcEquipmentStopAction.java 
 * @Create on:  2014-03-16 16:26:909
 * @Author   :  taofl
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.cycj.action;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.boho.framework.actions.ActionImp;
import cn.boho.framework.context.ActionContext;
import cn.boho.framework.po.DynaBeanMap;
import cn.boho.framework.service.MessageService;
import cn.boho.framework.utils.DateUtils;

import com.hanthink.cycj.dao.PmcEquipmentStopDao;
import com.hanthink.util.Tools;

public class QueryPmcEquipmentStopAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private java.lang.String queryType;
    private java.lang.String proline;		//工段
    private java.lang.String station;		//站点(工位)
    private java.lang.String equipmentstop;	//设备
    private DynaBeanMap dbm = new DynaBeanMap();
    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【工位设备停机表】", ex);
    }

    @SuppressWarnings("unchecked")
	@Override
    protected int performExecute(ActionContext atx) throws Exception {
    	List returnList = new LinkedList();
        List list = new LinkedList();
        List listEq = new LinkedList();
        if ("0".equals(queryType)) { // 日
//            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"), DictConstants.FORMAT_DATE);
            String ppdate = atx.getStringValue("PPDATE");
            proline =  atx.getStringValue("PRODUCTIONLINENAME", "").trim();
//            list = PmcEquipmentStopDao.queryPmcEquipmentStop(con, ppdate,proline);
            list = PmcEquipmentStopDao.queryPmcEquipmentStopAgen(con, ppdate,proline,station,equipmentstop);
            listEq = PmcEquipmentStopDao.queryPmcEquipmentStopAgenEq(con, ppdate,proline,station,equipmentstop);
            int i = 0;
        } else if ("1".equals(queryType)) { // 月
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"), "yyyy-MM");
            proline =  atx.getStringValue("PRODUCTIONLINENAME", "").trim();
//            list = PmcEquipmentStopDao.queryMonPmcEquipmentStop(con, ppdate,proline);
            list = PmcEquipmentStopDao.queryPmcEquipmentStopMonth(con, ppdate,proline,station,equipmentstop);
            listEq = PmcEquipmentStopDao.queryPmcEquipmentStopMonthEq(con, ppdate,proline,station,equipmentstop);
        } else if ("2".equals(queryType)) {  // 年
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"), "yyyy");
            proline =  atx.getStringValue("PRODUCTIONLINENAME", "").trim();
//            list = PmcEquipmentStopDao.queryYearPmcEquipmentStop(con, ppdate,proline);
            list = PmcEquipmentStopDao.queryPmcEquipmentStopYear(con, ppdate,proline,station,equipmentstop);
            listEq = PmcEquipmentStopDao.queryPmcEquipmentStopYearEq(con, ppdate,proline,station,equipmentstop);
        } else if ("3".equals(queryType)) { // 周
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
            /*System.out.println("**********************************");
            System.out.println(firstOfWeek);
            System.out.println(lastOfWeek);
            System.out.println("**********************************");*/
            proline =  atx.getStringValue("PRODUCTIONLINENAME", "").trim();
//            list = PmcEquipmentStopDao.queryWeekPmcEquipmentStop(con, firstOfWeek, lastOfWeek,proline);
            list = PmcEquipmentStopDao.queryPmcEquipmentStopWeek(con, firstOfWeek, lastOfWeek,proline,station,equipmentstop);
            listEq = PmcEquipmentStopDao.queryPmcEquipmentStopWeekEq(con, firstOfWeek, lastOfWeek,proline,station,equipmentstop);
        }

        if ("".equals(station) && "".equals(proline)){
        	for(int i=0; i<list.size(); i++){
        		DynaBeanMap dbmList = (DynaBeanMap) list.get(i);
        		dbm = new DynaBeanMap();
//        		String dbStation = Tools.getStr(dbmList.get("EVENTDATA4"),"");//站点
//        		String dbEqu = Tools.getStr(dbmList.get("EVENTDATA5"),"");//设备
        		String dbPr = Tools.getStr(dbmList.get("EVENTDATA7"),"");//线体中文名
        		String dbprE = Tools.getStr(dbmList.get("EVENTDATA8"),"");//线体英文名
        		double stopTime = Tools.getDouble(dbmList.get("STOPTIME"), 0.0);//停线时间
        		int stopCount = Tools.getInt(dbmList.get("stopcount"), 0);//停线次数
        		for(int j=0; j<listEq.size(); j++){
        			DynaBeanMap eqList = (DynaBeanMap) listEq.get(j);
//            		String eqStation = Tools.getStr(eqList.get("EVENTDATA4"),"");//站点
//            		String eqEqu = Tools.getStr(eqList.get("EVENTDATA5"),"");//设备
            		String eqPr = Tools.getStr(eqList.get("EVENTDATA7"),"");//线体中文名
            		String eqprE = Tools.getStr(eqList.get("EVENTDATA8"),"");//线体英文名
            		String eqType = Tools.getStr(eqList.get("eventdata3"),"");//32类
            		double eqstoptime = Tools.getDouble(eqList.get("STOPTIME"), 0.0);//停线时间
        			if(dbprE.equals(eqprE) && dbPr.equals(eqPr)){
        				dbm.put("PRODUCTIONLINE", dbprE);//线体英文名
        				dbm.put("PRODUCTIONLINENAME", eqPr);//线体中文名
        				dbm.put("EVENTDATA5", "全部");//设备
        				dbm.put("STATION", "全部");//站点(工位)
        				dbm.put("STOPTIME", stopTime);//停机总时间
        				dbm.put("STOPCOUNT", stopCount);//停机总次数
        				return32Type(dbm,eqType,eqstoptime);
        			}
        		}
        		returnList.add(dbm);
        	}
        }else if ("".equals(station) && !"".equals(proline)){
        	for(int i=0; i<list.size(); i++){
        		DynaBeanMap dbmList = (DynaBeanMap) list.get(i);
        		dbm = new DynaBeanMap();
        		String dbStation = Tools.getStr(dbmList.get("EVENTDATA4"),"");//站点
//        		String dbEqu = Tools.getStr(dbmList.get("EVENTDATA5"),"");//设备
        		String dbPr = Tools.getStr(dbmList.get("EVENTDATA7"),"");//线体中文名
        		String dbprE = Tools.getStr(dbmList.get("EVENTDATA8"),"");//线体英文名
        		double stopTime = Tools.getDouble(dbmList.get("STOPTIME"), 0.0);//停线时间
        		int stopCount = Tools.getInt(dbmList.get("stopcount"), 0);//停线次数
        		for(int j=0; j<listEq.size(); j++){
        			DynaBeanMap eqList = (DynaBeanMap) listEq.get(j);
            		String eqStation = Tools.getStr(eqList.get("EVENTDATA4"),"");//站点
//            		String eqEqu = Tools.getStr(eqList.get("EVENTDATA5"),"");//设备
            		String eqPr = Tools.getStr(eqList.get("EVENTDATA7"),"");//线体中文名
            		String eqprE = Tools.getStr(eqList.get("EVENTDATA8"),"");//线体英文名
            		String eqType = Tools.getStr(eqList.get("eventdata3"),"");//32类
            		double eqstoptime = Tools.getDouble(eqList.get("STOPTIME"), 0.0);//停线时间
        			if(dbprE.equals(eqprE) && dbStation.equals(eqStation) && dbPr.equals(eqPr)){
        				dbm.put("PRODUCTIONLINE", dbprE);//线体英文名
        				dbm.put("PRODUCTIONLINENAME", eqPr);//线体中文名
        				dbm.put("EVENTDATA5", "全部");//设备
        				dbm.put("STATION", eqStation);//站点(工位)
        				dbm.put("STOPTIME", stopTime);//停机总时间
        				dbm.put("STOPCOUNT", stopCount);//停机总次数
        				return32Type(dbm,eqType,eqstoptime);
        			}
        		}
        		returnList.add(dbm);
        	}
        }else {
        	for(int i=0; i<list.size(); i++){
        		DynaBeanMap dbmList = (DynaBeanMap) list.get(i);
        		dbm = new DynaBeanMap();
        		String dbStation = Tools.getStr(dbmList.get("EVENTDATA4"),"");//站点
        		String dbEqu = Tools.getStr(dbmList.get("EVENTDATA5"),"");//设备
        		String dbPr = Tools.getStr(dbmList.get("EVENTDATA7"),"");//线体中文名
        		String dbprE = Tools.getStr(dbmList.get("EVENTDATA8"),"");//线体英文名
        		double stopTime = Tools.getDouble(dbmList.get("STOPTIME"), 0.0);//停线时间
        		int stopCount = Tools.getInt(dbmList.get("stopcount"), 0);//停线次数
        		for(int j=0; j<listEq.size(); j++){
        			DynaBeanMap eqList = (DynaBeanMap) listEq.get(j);
            		String eqStation = Tools.getStr(eqList.get("EVENTDATA4"),"");//站点
            		String eqEqu = Tools.getStr(eqList.get("EVENTDATA5"),"");//设备
            		String eqPr = Tools.getStr(eqList.get("EVENTDATA7"),"");//线体中文名
            		String eqprE = Tools.getStr(eqList.get("EVENTDATA8"),"");//线体英文名
            		String eqType = Tools.getStr(eqList.get("eventdata3"),"");//32类
            		double eqstoptime = Tools.getDouble(eqList.get("STOPTIME"), 0.0);//停线时间
        			if(dbprE.equals(eqprE) && dbStation.equals(eqStation) && dbEqu.equals(eqEqu) && dbPr.equals(eqPr)){
        				dbm.put("PRODUCTIONLINE", dbprE);//线体英文名
        				dbm.put("PRODUCTIONLINENAME", eqPr);//线体中文名
        				dbm.put("EVENTDATA5", eqEqu);//设备
        				dbm.put("STATION", eqStation);//站点(工位)
        				dbm.put("STOPTIME", stopTime);//停机总时间
        				dbm.put("STOPCOUNT", stopCount);//停机总次数
        				return32Type(dbm,eqType,eqstoptime);
        			}
        		}
        		returnList.add(dbm);
        	}
        }

        atx.setValue("PMC_EQUIPMENT_STOP", returnList.toArray());
        return 1;
    }

    @SuppressWarnings("unchecked")
	private void return32Type(DynaBeanMap dbm,String type, Double stopTime) throws Exception{
    	if("动力电源".equals(type)){
    		dbm.put("DATA8",stopTime);
    	}else if("控制电源".equals(type)){
    		dbm.put("DATA9",stopTime);
    	}else if("I/O模块".equals(type)){
    		dbm.put("DATA10",stopTime);
    	}else if("DNET网络".equals(type)){
    		dbm.put("DATA11",stopTime);
    	}else if("以太网".equals(type)){
    		dbm.put("DATA12",stopTime);
    	}else if("物料传感器".equals(type)){
    		dbm.put("DATA13",stopTime);
    	}else if("安全设备".equals(type)){
    		dbm.put("DATA14",stopTime);
    	}else if("急停".equals(type)){
    		dbm.put("DATA15",stopTime);
    	}else if("机器人".equals(type)){
    		dbm.put("DATA16",stopTime);
    	}else if("点焊设备".equals(type)){
    		dbm.put("DATA17",stopTime);
    	}else if("螺柱焊".equals(type)){
    		dbm.put("DATA18",stopTime);
    	}else if("修磨器".equals(type)){
    		dbm.put("DATA19",stopTime);
    	}else if("换抢盘".equals(type)){
    		dbm.put("DATA20",stopTime);
    	}else if("停放架".equals(type)){
    		dbm.put("DATA21",stopTime);
    	}else if("气源".equals(type)){
    		dbm.put("DATA22",stopTime);
    	}else if("冷却水".equals(type)){
    		dbm.put("DATA23",stopTime);
    	}else if("夹头".equals(type)){
    		dbm.put("DATA24",stopTime);
    	}else if("定位销".equals(type)){
    		dbm.put("DATA25",stopTime);
    	}else if("气缸".equals(type)){
    		dbm.put("DATA26",stopTime);
    	}else if("阀岛".equals(type)){
    		dbm.put("DATA27",stopTime);
    	}else if("压机".equals(type)){
    		dbm.put("DATA28",stopTime);
    	}else if("输送设备".equals(type)){
    		dbm.put("DATA29",stopTime);
    	}else if("工装".equals(type)){
    		dbm.put("DATA30",stopTime);
    	}else if("转台".equals(type)){
    		dbm.put("DATA31",stopTime);
    	}else if("ANDON".equals(type)){
    		dbm.put("DATA32",stopTime);
    	}else if("AVI".equals(type)){
    		dbm.put("DATA33",stopTime);
    	}else if("激光焊".equals(type)){
    		dbm.put("DATA34",stopTime);
    	}else if("视觉防错".equals(type)){
    		dbm.put("DATA35",stopTime);
    	}else if("滑台".equals(type)){
    		dbm.put("DATA36",stopTime);
    	}else if("修模".equals(type)){
    		dbm.put("DATA37",stopTime);
    	}else if("CO2焊".equals(type)){
    		dbm.put("DATA38",stopTime);
    	}else if("涂胶设备".equals(type)){
    		dbm.put("DATA39",stopTime);
    	}
//    	return 1;
    }
    
    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        queryType = atx.getStringValue("QUERY_TYPE", "").trim();
        station = atx.getStringValue("STATION", "").trim();
        equipmentstop = atx.getStringValue("EQUIPMENTSTOP", "").trim();//设备
        return 1;
    }
}
