/**
 * 
 *
 * @File name:  QueryPmcandonStoplineAction.java 
 * @Create on:  2014-03-16 16:40:100
 * @Author   :  leeyao
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 */

package com.hanthink.zzcj.action;

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


import com.hanthink.zzcj.dao.PmcAndonStoplineDao;
import com.hanthink.pmc.dao.PmcSystemReportDao;
import com.hanthink.util.DictConstants;
import com.hanthink.util.Tools;

public class QueryPmcAndonStoplineAction extends ActionImp {
    private Connection con = null;
    private Date ppdate;
    private Date nextPpdates;
    private java.lang.String queryType;
    private Integer banci;
    private String newbanci;

    @Override
    protected void doException(ActionContext atx, Exception ex) {
        atx.setErrorContext("EC_COMMON_1004", MessageService.getMessage("EC_COMMON_1004"), "【andon停线报表】", ex);
    }

    @SuppressWarnings("deprecation")
	@Override
    protected int performExecute(ActionContext atx) throws Exception {
//        List list = PmcAndonStoplineDao.queryPmcAndonStopline(con, ppdate);
        List list = new LinkedList();
        
        if("0".equals(queryType)){  //日
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),DictConstants.FORMAT_DATE);
          
            //转换日期，变为年-月-日 08:00:00
            Calendar  calendar = Calendar.getInstance();
            calendar.setTime(ppdate);
            calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 8, 0);
            ppdate = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 1); 
            calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 8, 0);
            nextPpdates = calendar.getTime();
            list = PmcAndonStoplineDao.queryPmcAndonStopline(con, ppdate, nextPpdates,banci,newbanci);

            
        }else if("1".equals(queryType)){
            //月
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),"yyyy-MM");
            list = PmcAndonStoplineDao.queryMonPmcAndonStopline(con, ppdate,banci,newbanci);
        }else if("2".equals(queryType)){
            //年
            ppdate = DateUtils.parse(atx.getStringValue("PPDATE"),"yyyy");
            list = PmcAndonStoplineDao.queryYearPmcAndonStopline(con, ppdate,banci,newbanci);
        }else if("3".equals(queryType)){
            //周
            int week  = Tools.getInt(atx.getStringValue("PPDATE",""),0);
            Date date = new Date();
            Calendar ca = Calendar.getInstance();
            String year = DateUtils.format(date,"yyyy");
            String firstDay = year + "-01-01";
            Date first = DateUtils.parse(firstDay,"yyyy-MM-dd");
            ca.setTime(first);
            ca.add(Calendar.WEEK_OF_YEAR, week);
            int weekTemp = ca.get(Calendar.DAY_OF_WEEK) - 2;
            ca.add(Calendar.DAY_OF_YEAR, -weekTemp);
            Date firstOfWeek = ca.getTime();
            ca.add(Calendar.DAY_OF_YEAR, 6);
            Date lastOfWeek = ca.getTime();
            
            list = PmcAndonStoplineDao.queryWeekPmcAndonStopline(con, firstOfWeek, lastOfWeek,banci,newbanci);
        }
//      这里把list打散开
      int j1=0;
      int j2=0;
      int j3=0;
      int j4=0;
      int j5=0;
      int trim1=0;
      int trim2=0;
      int chas1=0;
      int chas2=0;
      int finalx=0;
      for (int i=0; i<list.size();i++) {
      	DynaBeanMap dbm = (DynaBeanMap) list.get(i);
      	String gwdata=dbm.get("GW").toString();
      	String timedata=dbm.get("TIME").toString();
      	if(dbm.get("GD").equals("内饰1"))
      	{
      		if(trim1==0){j1=i;}
      	dbm=(DynaBeanMap) list.get(i-j1);
      	dbm.put("GW1", gwdata);
      	dbm.put("TIME1", timedata);
      	trim1++;
      	continue;
      	}
      	if(dbm.get("GD").equals("内饰2"))
      	{
      		if(trim2==0){j2=i;}
      	dbm=(DynaBeanMap) list.get(i-j2);
      	dbm.put("GW2", gwdata);
      	dbm.put("TIME2", timedata);
      	trim2++;
      	continue;
      	}
      	if(dbm.get("GD").equals("底盘1"))
      	{
      		if(chas1==0){j3=i;}
      	dbm=(DynaBeanMap) list.get(i-j3);
      	dbm.put("GW3", gwdata);
      	dbm.put("TIME3", timedata);
      	chas1++;
      	continue;
      	}
      	if(dbm.get("GD").equals("底盘2"))
      	{
      		if(chas2==0){j4=i;}
      	dbm=(DynaBeanMap) list.get(i-j4);
      	dbm.put("GW4", gwdata);
      	dbm.put("TIME4", timedata);
      	chas2++;
      	continue;
      	}
      	if(dbm.get("GD").equals("最终线"))
      	{
      		if(finalx==0){j5=i;}
      	dbm=(DynaBeanMap) list.get(i-j5);
      	dbm.put("GW5", gwdata);
      	dbm.put("TIME5", timedata);
      	finalx++;
      	continue;
      	}

      }
//    	求和加排序
      String[] gongduan=new String[6];
      	String tempgongdong="";
      	double[] timetotal=new double[]{0,0,0,0,0,0};
      	boolean flagwrite=true;
    	for (int j=0; j<list.size();j++) {
    		DynaBeanMap deldbm = (DynaBeanMap) list.get(j);
    		if(!(deldbm.get("TIME1").toString().isEmpty())){timetotal[1]=timetotal[1]+Double.valueOf(deldbm.get("TIME1").toString());}
    		if(!(deldbm.get("TIME2").toString().isEmpty())){timetotal[2]=timetotal[2]+Double.valueOf(deldbm.get("TIME2").toString());}
    		if(!(deldbm.get("TIME3").toString().isEmpty())){timetotal[3]=timetotal[3]+Double.valueOf(deldbm.get("TIME3").toString());}
    		if(!(deldbm.get("TIME4").toString().isEmpty())){timetotal[4]=timetotal[4]+Double.valueOf(deldbm.get("TIME4").toString());}
    		if(!(deldbm.get("TIME5").toString().isEmpty())){timetotal[5]=timetotal[5]+Double.valueOf(deldbm.get("TIME5").toString());}
    		//排序一下
    		for(int ii=1;ii<6;ii++){
    			int rank=1;
    			for(int jj=1;jj<6;jj++){   		      	
    				if(timetotal[ii]<timetotal[jj]){rank=rank+1;}
    				gongduan[ii]="第"+rank+"位";
    			}
    		}
    		if(deldbm.get("GW1").equals(deldbm.get("GW2"))&&deldbm.get("GW3").equals(deldbm.get("GW4"))&&deldbm.get("GW4").equals(deldbm.get("GW5"))&&flagwrite){
    			flagwrite=false;
    			deldbm.put("GW1", gongduan[1]);
    			deldbm.put("TIME1", timetotal[1]);
    			deldbm.put("GW2", gongduan[2]);
    			deldbm.put("TIME2", timetotal[2]);
    			deldbm.put("GW3", gongduan[3]);
    			deldbm.put("TIME3", timetotal[3]);
    			deldbm.put("GW4", gongduan[4]);
    			deldbm.put("TIME4", timetotal[4]);
    			deldbm.put("GW5", gongduan[5]);
    			deldbm.put("TIME5", timetotal[5]);
    		}
    	}
        atx.setValue("PMC_ANDON_STOPLINE", list.toArray());
        return 1;
    }

    @Override
    protected int verifyParameters(ActionContext atx) throws Exception {
        con = atx.getConection();
        
        queryType = atx.getStringValue("QUERY_TYPE","").trim();
        banci=Integer.parseInt(atx.getStringValue("BANCI","5").trim());
        newbanci=atx.getStringValue("NEWBANCI","").trim();
        return 1;
    }
}
