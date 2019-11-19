package com.hanthink.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.util.Log;

import cn.boho.framework.po.DynaBeanMap;

public class ExcelUtil {

    public static void exportExcel(String title, String[] headers, String[] columns, int[] widths, List dataset, OutputStream out) throws IOException {

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        
        // 设置表格默认列宽度为15个字节
        // sheet.setDefaultColumnWidth(20);
        
        //表头颜色
        HSSFPalette palette = workbook.getCustomPalette();  
        palette.setColorAtIndex((short) 50, (byte)(0xff), (byte)(0xc0), (byte)(0x00));

        //表头样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor((short) 50);
        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        //表头字体
        HSSFFont headFont = workbook.createFont();
        headFont.setFontHeightInPoints((short) 11);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headStyle.setFont(headFont);

        //标题样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        //标题字体
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleStyle.setFont(titleFont);
        
        //内容样式
        HSSFCellStyle columnStyle = workbook.createCellStyle();
        columnStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        columnStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        columnStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        columnStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        //内容字体
        HSSFFont columnFont = workbook.createFont();
        columnFont.setFontHeightInPoints((short) 11);
        columnFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        columnStyle.setFont(columnFont);

        //标题
        HSSFRow row = sheet.createRow(0);
        for(int i = 0; i < columns.length; i++){
        	HSSFCell cell = row.createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,columns.length-1));
        HSSFCell cellTitle = sheet.getRow(0).getCell(0);
        cellTitle.setCellStyle(titleStyle);
        cellTitle.setCellValue(title);
        
        //表头
        row = sheet.createRow(1);
        row.setHeight((short) 360);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            sheet.setColumnWidth(i, widths[i] * 45);
        }
     
        //内容
        for (int i = 0; i < dataset.size() ; i++) {
            Map dataMap = (Map) dataset.get(i);
            row = sheet.createRow(i + 2);
            row.setHeight((short) 360);
            
            for (int k = 0; k < columns.length; k++) {
                HSSFCell cell = row.createCell(k);
                cell.setCellStyle(columnStyle);
                
                String textValue = Tools.getStr(dataMap.get(columns[k]), "");
                HSSFRichTextString text = new HSSFRichTextString(textValue);
                cell.setCellValue(text);
            }
        }
        workbook.write(out);
        //写入数据  
		out.close();
    }
    /*这是用来导出XLSX的方法*/
    public static void exportExcelXlsx(String title, String[] headers, String[] columns, int[] widths, List dataset, OutputStream out) throws IOException {

        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
        
        // 生成一个表格
        Sheet sheet = workbook.createSheet();
        
        // 设置表格默认列宽度为15个字节
        // sheet.setDefaultColumnWidth(20);
        
        //表头颜色
//          XSSFPalette palette = workbook.getCustomPalette();  
//          palette.setColorAtIndex((short) 50, (byte)(0xff), (byte)(0xc0), (byte)(0x00));

//        //表头样式
          CellStyle headStyle = workbook.createCellStyle();
          headStyle.setFillForegroundColor((short) 50);
          headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
          headStyle.setBorderBottom(CellStyle.BORDER_THIN);
          headStyle.setBorderLeft(CellStyle.BORDER_THIN);
          headStyle.setBorderRight(CellStyle.BORDER_THIN);
          headStyle.setBorderTop(CellStyle.BORDER_THIN);
          headStyle.setAlignment(CellStyle.ALIGN_CENTER);
          

        //表头字体
        Font headFont = workbook.createFont();
        headFont.setFontHeightInPoints((short) 11);
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headStyle.setFont(headFont);
//
//        //标题样式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(CellStyle.BORDER_THIN);
        titleStyle.setBorderRight(CellStyle.BORDER_THIN);
        titleStyle.setBorderTop(CellStyle.BORDER_THIN);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        
        //标题字体
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleStyle.setFont(titleFont);
        
        //内容样式
        CellStyle columnStyle = workbook.createCellStyle();
        columnStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        columnStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        columnStyle.setBorderBottom(CellStyle.BORDER_THIN);
        columnStyle.setBorderLeft(CellStyle.BORDER_THIN);
        columnStyle.setBorderRight(CellStyle.BORDER_THIN);
        columnStyle.setBorderTop(CellStyle.BORDER_THIN);
        columnStyle.setAlignment(CellStyle.ALIGN_LEFT);
        columnStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        //内容字体
        Font columnFont = workbook.createFont();
        columnFont.setFontHeightInPoints((short) 11);
        columnFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        columnStyle.setFont(columnFont);

        //标题
          Row row = sheet.createRow(0);
        for(int i = 0; i < columns.length; i++){
        	Cell cell = row.createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,columns.length-1));
        Cell cellTitle = sheet.getRow(0).getCell(0);
        cellTitle.setCellStyle(titleStyle);
        cellTitle.setCellValue(title);
//        
        //表头
        row = sheet.createRow(1);
        row.setHeight((short) 360);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            
            cell.setCellValue(headers[i]);
            sheet.setColumnWidth(i, widths[i] * 45);
        }
        Log.info("标题样式准备完成！");
        //内容
        for (int i = 0; i < dataset.size() ; i++) {
            Map dataMap = (Map) dataset.get(i);
            row = sheet.createRow(i + 2);
            row.setHeight((short) 360);
            
            for (int k = 0; k < columns.length; k++) {
                Cell cell = row.createCell(k);
                cell.setCellStyle(columnStyle);
                String textValue = Tools.getStr(dataMap.get(columns[k]), "");
                cell.setCellValue(textValue);
            }
        }
        Log.info("内容填充准备完成！");
        workbook.write(out);
        Log.info("导出成功！");
        workbook.dispose();
        //写入数据  
		out.close();
    }
    
    /*这是用来导出双层标题的方法*/
    
    public static void exportExcel2(String title, String[] headers, String[] columns, int[] widths, List dataset, OutputStream out) throws IOException {

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet();

        // 设置表格默认列宽度为15个字节
        // sheet.setDefaultColumnWidth(20);
        
        //表头颜色
        HSSFPalette palette = workbook.getCustomPalette();  
        palette.setColorAtIndex((short) 50, (byte)(0xff), (byte)(0xc0), (byte)(0x00));

        //表头样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor((short) 50);
        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        //表头字体
        HSSFFont headFont = workbook.createFont();
        headFont.setFontHeightInPoints((short) 11);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headStyle.setFont(headFont);

        //标题样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        //标题字体
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleStyle.setFont(titleFont);
        
        //内容样式
        HSSFCellStyle columnStyle = workbook.createCellStyle();
        columnStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        columnStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        columnStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        columnStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        //内容字体
        HSSFFont columnFont = workbook.createFont();
        columnFont.setFontHeightInPoints((short) 11);
        columnFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        columnStyle.setFont(columnFont);

        //标题
        HSSFRow row = sheet.createRow(0);
        for(int i = 0; i < columns.length; i++){
        	HSSFCell cell = row.createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,columns.length-1));
        HSSFCell cellTitle = sheet.getRow(0).getCell(0);
        cellTitle.setCellStyle(titleStyle);
        cellTitle.setCellValue(title);
        
        //表头
        row = sheet.createRow(2);
        row.setHeight((short) 360);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            sheet.setColumnWidth(i, widths[i] * 45);
        }
        //第二层标题
        row = sheet.createRow(1);
        row.setHeight((short) 360);
        String[] gongduan=new String[]{"内饰1","内饰2","底盘1","底盘2","最终线"};
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            if(i%2==0){
            HSSFRichTextString text = new HSSFRichTextString(gongduan[i/2]);
            cell.setCellValue(text);
            }            
            sheet.setColumnWidth(i, widths[i] * 45);
        }
        //内容
        for (int i = 0; i < dataset.size() ; i++) {
        	if(dataset.size() > 65532){
        		break;
        	}
            Map dataMap = (Map) dataset.get(i);
            row = sheet.createRow(i + 3);
            row.setHeight((short) 360);
            
            for (int k = 0; k < columns.length; k++) {
                HSSFCell cell = row.createCell(k);
                cell.setCellStyle(columnStyle);
                
                String textValue = Tools.getStr(dataMap.get(columns[k]), "");
                HSSFRichTextString text = new HSSFRichTextString(textValue);
                cell.setCellValue(text);
            }
        }
        workbook.write(out);
        //写入数据  
		out.close();
    }

    /**
     * 读取excel数据(特定的Excel格式)
     * @author 王凤
     * @create_date 2014-05-12 下午
     * @param is 文件流
     * @param columns 列对应字段
     * @param startRow 开始行
     * @param startColumn 开始列
     * @return
     * @throws IOException
     */  
	public static List importExcelForSpecial(InputStream is, String[] columns, int startRow, int startColumn) throws IOException {

        List excelDataList = new ArrayList();     

        // 循环工作表Sheet
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        if (hssfSheet == null) {
           return excelDataList;
        }
 
        //取值
        String date = getValue(hssfSheet.getRow(1).getCell(1));	//2015-07
        String dayStartTime0 = getValue(hssfSheet.getRow(2).getCell(1));//7:00:00	1班开始时间
        String dayEndTime0 = getValue(hssfSheet.getRow(2).getCell(3));//1班结束时间
        String nightStartTime0 = getValue(hssfSheet.getRow(2).getCell(9));//2班开始时间
        String nightEndTime0 = getValue(hssfSheet.getRow(2).getCell(15));//2班结束时间
        
        //获取休息时间
        String oneresttime = getValue(hssfSheet.getRow(6).getCell(2));//白班休息时间
        String twoRestTime = getValue(hssfSheet.getRow(6).getCell(5));//晚班休息时间
        System.out.println(oneresttime+"--------------------"+twoRestTime);
        //初始化
        String dayStartTime = "";
        String dayEndTime = "";
        String nightStartTime = "";
        String nightEndTime = "";
        int columnOfDay = 3;
        String day = getValue(hssfSheet.getRow(6).getCell(columnOfDay));
        String dates = date.concat("/"+day);
        //设置Excel表格第二行最后为“32”，以便统一格式读取所有值。
//        hssfSheet.getRow(9).getCell(65).setCellValue("31");
//        hssfSheet.getRow(7).getCell(65).setCellValue("32"); 
        System.out.println("-----------------------------------");
        int i=0;
        // 循环行Row
        for (int rowNum = startRow; rowNum <= startRow; rowNum++) {
//            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            HSSFRow hssfRow1 = hssfSheet.getRow(rowNum);
            HSSFRow hssfRow2 = hssfSheet.getRow(++rowNum);
            HSSFRow hssfRow3 = hssfSheet.getRow(++rowNum);
            HSSFRow hssfRow4 = hssfSheet.getRow(++rowNum);
            HSSFRow hssfRow5 = hssfSheet.getRow(++rowNum);
            HSSFRow hssfRow6 = hssfSheet.getRow(++rowNum);
            HSSFRow hssfRow7 = hssfSheet.getRow(++rowNum);
            HSSFRow hssfRow8 = hssfSheet.getRow(++rowNum);
            HSSFRow hssfRow9 = hssfSheet.getRow(++rowNum);
            
            // 循环列Cell           
            for (int cellNum = startColumn; cellNum <= 64; cellNum++) {
            	
            	DynaBeanMap dataMap = new DynaBeanMap();
            	excelDataList.add(dataMap); 
            	
                //取值
//                String restTime = getValue(hssfRow.getCell(cellNum));
                String IP21 = getValue(hssfRow1.getCell(cellNum));
                String IP22 = getValue(hssfRow2.getCell(cellNum));
                String IP23 = getValue(hssfRow3.getCell(cellNum));
                String ZP11 = getValue(hssfRow4.getCell(cellNum));
                String BP31 = getValue(hssfRow5.getCell(cellNum));
                String IP24 = getValue(hssfRow6.getCell(cellNum));
                String BP32 = getValue(hssfRow7.getCell(cellNum));
                String AS21 = getValue(hssfRow8.getCell(cellNum));
                String OTHER = getValue(hssfRow9.getCell(cellNum));

                //日期赋值
                dataMap.put(columns[0], null == dates||dates.length()<=0||dates.equals("") ? '0' : dates);
                
                day = getValue(hssfSheet.getRow(6).getCell(columnOfDay));	                
                dates = date.concat("/"+day);
                if(day.length() == 0){
                	String datess = dates.substring(0, dates.indexOf('/'));
                	int ss = Tools.getInt(dates.substring(dates.indexOf('/')+1, dates.lastIndexOf('/')),0);
                	dates = datess+"/"+(ss+1)+"/"+"1";
                	String sss = dates;
                }
                //白班晚班循环赋值
                if (0 == cellNum % 2){
                	//连接日期和时间
                	nightEndTime = dates.concat(" "+nightEndTime0);
                	
                    dataMap.put(columns[1], null == nightStartTime||nightStartTime.length()<=0 ? "0" : nightStartTime);
                    dataMap.put(columns[2], null == nightEndTime||nightEndTime.length()<=0 ? "0" : nightEndTime);  
                    dataMap.put(columns[4], '2');
                    dataMap.put(columns[3], twoRestTime);   
                    System.out.println("2---"+twoRestTime+"----"+nightStartTime+"---"+nightEndTime);
                }else{
                    //连接日期和时间
                    dayStartTime = dates.concat(" "+dayStartTime0);
                    dayEndTime = dates.concat(" "+dayEndTime0);
                	nightStartTime = dates.concat(" "+nightStartTime0);
                    
                    dataMap.put(columns[1], null == dayStartTime||dayStartTime.length()<=0 ? '0' : dayStartTime);
                    dataMap.put(columns[2], null == dayEndTime||dayEndTime.length()<=0 ? '0' : dayEndTime);
                    dataMap.put(columns[4], '1');
                    dataMap.put(columns[3], oneresttime);    
                    columnOfDay += 2;
                    //System.out.println("1---"+oneresttime+"----"+dayStartTime+"---"+dayEndTime);
                }
                //赋值
                dataMap.put(columns[5], null == IP21||IP21.length()<=0 ? '0' : IP21);
                dataMap.put(columns[6], null == IP22||IP22.length()<=0 ? '0' : IP22);
                dataMap.put(columns[7], null == IP23||IP23.length()<=0 ? '0' : IP23);
                dataMap.put(columns[8], null == ZP11||ZP11.length()<=0 ? '0' : ZP11);
                dataMap.put(columns[9], null == BP31||BP31.length()<=0 ? '0' : BP31);
                dataMap.put(columns[10], null == IP24||IP24.length()<=0 ? '0' : IP24);
                dataMap.put(columns[11], null == BP32||BP32.length()<=0 ? '0' : BP32);
                dataMap.put(columns[12], null == AS21||AS21.length()<=0 ? '0' : AS21);
                dataMap.put(columns[13], null == OTHER||OTHER.length()<=0 ? '0' : OTHER);
                int plantotal = 0;
                if(!(null == IP21||IP21.length()<=0)){
                	plantotal += Tools.getInt(IP21,0);
                }
                if(!(null == IP22||IP22.length()<=0)){
                	plantotal += Tools.getInt(IP22,0);
                }
                if(!(null == IP23||IP23.length()<=0)){
                	plantotal += Tools.getInt(IP23,0);
                }
                if(!(null == ZP11||ZP11.length()<=0)){
                	plantotal += Tools.getInt(ZP11,0);
                }
                if(!(null == BP31||BP31.length()<=0)){
                	plantotal += Tools.getInt(BP31,0);
                }
                if(!(null == IP24||IP24.length()<=0)){
                	plantotal += Tools.getInt(IP24,0);
                }
                if(!(null == BP32||BP32.length()<=0)){
                	plantotal += Tools.getInt(BP32,0);
                }
                if(!(null == AS21||AS21.length()<=0)){
                	plantotal += Tools.getInt(AS21,0);
                }
                if(!(null == OTHER||OTHER.length()<=0)){
                	plantotal += Tools.getInt(OTHER,0);
                }
                
                dataMap.put(columns[14], plantotal);
                if(plantotal == 0){               	
                	excelDataList.remove(dataMap);
                } 
            }           
        }       
        return excelDataList;
    }
	/**
     * 读取excel数据(特定的冲压车间Excel格式)
     * @author 李垚
     * @create_date 2017-03-17 下午
     * @param is 文件流
     * @param columns 列对应字段
     * @param startRow 开始行
     * @param startColumn 开始列
     * @return
     * @throws IOException
     */  
	public static List importExcelForCycj(InputStream is, String[] columns, int startRow, int startColumn) throws IOException {

		List excelDataList = new ArrayList();     

        // 循环工作表Sheet
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        if (hssfSheet == null) {
           return excelDataList;
        }
 
        //取值
        String date = getValue(hssfSheet.getRow(1).getCell(1));	//2015-07
        String dayStartTime0 = getValue(hssfSheet.getRow(2).getCell(1));//7:00:00	1班开始时间
        String dayEndTime0 = getValue(hssfSheet.getRow(2).getCell(3));//1班结束时间
        String nightStartTime0 = getValue(hssfSheet.getRow(2).getCell(9));//2班开始时间
        String nightEndTime0 = getValue(hssfSheet.getRow(2).getCell(15));//2班结束时间
        
        //获取休息时间
        String oneresttime = getValue(hssfSheet.getRow(6).getCell(2));//白班休息时间
        String twoRestTime = getValue(hssfSheet.getRow(6).getCell(5));//晚班休息时间
        System.out.println(oneresttime+"--------------------"+twoRestTime);
        //初始化
        String dayStartTime = "";
        String dayEndTime = "";
        String nightStartTime = "";
        String nightEndTime = "";
        int columnOfDay = 3;
        String day = getValue(hssfSheet.getRow(6).getCell(columnOfDay));
        String dates = date.concat("/"+day);
        //设置Excel表格第二行最后为“32”，以便统一格式读取所有值。
//        hssfSheet.getRow(9).getCell(65).setCellValue("31");
//        hssfSheet.getRow(7).getCell(65).setCellValue("32"); 
        System.out.println("-----------------------------------");
        int i=0;
        // 循环行Row
        for (int rowNum = startRow; rowNum <= startRow; rowNum++) {
//            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            HSSFRow hssfRow1 = hssfSheet.getRow(rowNum);
            HSSFRow hssfRow2 = hssfSheet.getRow(++rowNum);
            HSSFRow hssfRow3 = hssfSheet.getRow(++rowNum);
            HSSFRow hssfRow4 = hssfSheet.getRow(++rowNum);
//            HSSFRow hssfRow5 = hssfSheet.getRow(++rowNum);
//            HSSFRow hssfRow6 = hssfSheet.getRow(++rowNum);
//            HSSFRow hssfRow7 = hssfSheet.getRow(++rowNum);
//            HSSFRow hssfRow8 = hssfSheet.getRow(++rowNum);
//            HSSFRow hssfRow9 = hssfSheet.getRow(++rowNum);
            
            // 循环列Cell           
            for (int cellNum = startColumn; cellNum <= 64; cellNum++) {
            	
            	DynaBeanMap dataMap = new DynaBeanMap();
            	excelDataList.add(dataMap); 
            	
                //取值
//                String restTime = getValue(hssfRow.getCell(cellNum));
                String IP21 = getValue(hssfRow1.getCell(cellNum));
                String IP22 = getValue(hssfRow2.getCell(cellNum));
                String IP23 = getValue(hssfRow3.getCell(cellNum));
                String ZP11 = getValue(hssfRow4.getCell(cellNum));
//                String BP31 = getValue(hssfRow5.getCell(cellNum));
//                String IP24 = getValue(hssfRow6.getCell(cellNum));
//                String BP32 = getValue(hssfRow7.getCell(cellNum));
//                String AS21 = getValue(hssfRow8.getCell(cellNum));
//                String OTHER = getValue(hssfRow9.getCell(cellNum));

                //日期赋值
                dataMap.put(columns[0], null == dates||dates.length()<=0||dates.equals("") ? '0' : dates);
                
                day = getValue(hssfSheet.getRow(6).getCell(columnOfDay));	                
                dates = date.concat("/"+day);
                if(day.length() == 0){
                	String datess = dates.substring(0, dates.indexOf('/'));
                	int ss = Tools.getInt(dates.substring(dates.indexOf('/')+1, dates.lastIndexOf('/')),0);
                	dates = datess+"/"+(ss+1)+"/"+"1";
                	String sss = dates;
                }
                //白班晚班循环赋值
                if (0 == cellNum % 2){
                	//连接日期和时间
                	nightEndTime = dates.concat(" "+nightEndTime0);
                	
                    dataMap.put(columns[1], null == nightStartTime||nightStartTime.length()<=0 ? "0" : nightStartTime);
                    dataMap.put(columns[2], null == nightEndTime||nightEndTime.length()<=0 ? "0" : nightEndTime);  
                    dataMap.put(columns[4], '2');
                    dataMap.put(columns[3], twoRestTime);   
                    System.out.println("2---"+twoRestTime+"----"+nightStartTime+"---"+nightEndTime);
                }else{
                    //连接日期和时间
                    dayStartTime = dates.concat(" "+dayStartTime0);
                    dayEndTime = dates.concat(" "+dayEndTime0);
                	nightStartTime = dates.concat(" "+nightStartTime0);
                    
                    dataMap.put(columns[1], null == dayStartTime||dayStartTime.length()<=0 ? '0' : dayStartTime);
                    dataMap.put(columns[2], null == dayEndTime||dayEndTime.length()<=0 ? '0' : dayEndTime);
                    dataMap.put(columns[4], '1');
                    dataMap.put(columns[3], oneresttime);    
                    columnOfDay += 2;
                    //System.out.println("1---"+oneresttime+"----"+dayStartTime+"---"+dayEndTime);
                }
                //赋值
                dataMap.put(columns[5], null == IP21||IP21.length()<=0 ? '0' : IP21);
                dataMap.put(columns[6], null == IP22||IP22.length()<=0 ? '0' : IP22);
                dataMap.put(columns[7], null == IP23||IP23.length()<=0 ? '0' : IP23);
                dataMap.put(columns[8], null == ZP11||ZP11.length()<=0 ? '0' : ZP11);
//                dataMap.put(columns[9], null == BP31||BP31.length()<=0 ? '0' : BP31);
//                dataMap.put(columns[10], null == IP24||IP24.length()<=0 ? '0' : IP24);
//                dataMap.put(columns[11], null == BP32||BP32.length()<=0 ? '0' : BP32);
//                dataMap.put(columns[12], null == AS21||AS21.length()<=0 ? '0' : AS21);
//                dataMap.put(columns[13], null == OTHER||OTHER.length()<=0 ? '0' : OTHER);
                int plantotal = 0;
                if(!(null == IP21||IP21.length()<=0)){
                	plantotal += Tools.getInt(IP21,0);
                }
                if(!(null == IP22||IP22.length()<=0)){
                	plantotal += Tools.getInt(IP22,0);
                }
                if(!(null == IP23||IP23.length()<=0)){
                	plantotal += Tools.getInt(IP23,0);
                }
                if(!(null == ZP11||ZP11.length()<=0)){
                	plantotal += Tools.getInt(ZP11,0);
                }
//                if(!(null == BP31||BP31.length()<=0)){
//                	plantotal += Tools.getInt(BP31,0);
//                }
//                if(!(null == IP24||IP24.length()<=0)){
//                	plantotal += Tools.getInt(IP24,0);
//                }
//                if(!(null == BP32||BP32.length()<=0)){
//                	plantotal += Tools.getInt(BP32,0);
//                }
//                if(!(null == AS21||AS21.length()<=0)){
//                	plantotal += Tools.getInt(AS21,0);
//                }
//                if(!(null == OTHER||OTHER.length()<=0)){
//                	plantotal += Tools.getInt(OTHER,0);
//                }
                
//                dataMap.put(columns[14], plantotal);
                if(plantotal == 0){               	
                	excelDataList.remove(dataMap);
                } 
            }           
        }       
        return excelDataList;
    }
	private static String getValue2(HSSFCell hssfCell) {
		switch (hssfCell.getCellType()) {
        case HSSFCell.CELL_TYPE_NUMERIC:
        	if(HSSFDateUtil.isCellDateFormatted(hssfCell)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				return sdf.format(HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue())).toString();
			}
            return String.valueOf(hssfCell.getNumericCellValue());
        
        case HSSFCell.CELL_TYPE_STRING:
            return String.valueOf(hssfCell.getStringCellValue());
        
        case HSSFCell.CELL_TYPE_FORMULA:
            return String.valueOf(hssfCell.getNumericCellValue());
            
        case HSSFCell.CELL_TYPE_BOOLEAN:
            return String.valueOf(hssfCell.getStringCellValue());
        
        default:
            return "";
        } 
	}

	/**
     * 读取excel数据
     * @author 兰永明
     * @create_date 2013-11-10 下午11:35:19
     * @param is 文件流
     * @param columns 列对应字段
     * @param startRow 开始行
     * @param startColumn 开始列
     * @return
     * @throws IOException
     */
    public static List importExcel(InputStream is, String[] columns, int startRow, int startColumn) throws IOException {

        List excelDataList = new ArrayList();

        // 循环工作表Sheet
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        if (hssfSheet == null) {
           return excelDataList;
        }

        // 循环行Row
        for (int rowNum = startRow; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow == null) {
                continue;
            }

            // 循环列Cell
            DynaBeanMap dataMap = new DynaBeanMap();
            excelDataList.add(dataMap);
            for (int cellNum = startColumn; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
               
                //超过字段数
                if (cellNum >= (columns.length + startColumn)) {
                    break;
                }
                
                HSSFCell hssfCell = hssfRow.getCell(cellNum);
                if (hssfCell == null) {
                    continue;
                }
                
                dataMap.put(columns[cellNum - startColumn], getValue(hssfCell));
            }
        }
        
        return excelDataList;
    }

    /**
     * 获取单元格的值
     * @author 兰永明
     * @create_date 2013-11-10 下午11:40:12
     * @param hssfCell
     * @return
     */
    private static String getValue(HSSFCell hssfCell) {
        switch (hssfCell.getCellType()) {
        case HSSFCell.CELL_TYPE_NUMERIC:
            return String.valueOf(hssfCell.getNumericCellValue());
        
        case HSSFCell.CELL_TYPE_STRING:
            return String.valueOf(hssfCell.getStringCellValue());
        
        case HSSFCell.CELL_TYPE_FORMULA:
            return String.valueOf(hssfCell.getNumericCellValue());
            
        case HSSFCell.CELL_TYPE_BOOLEAN:
            return String.valueOf(hssfCell.getBooleanCellValue());
        
        default:
            return "";
        } 
    }
    
    /**
     * 追加sheet到workbook
     * 
     * @author luoshw
     * @param workbook
     * @param sheetName
     * @param headers
     * @param columns
     * @param widths
     * @param dataset
     * @return
     * @throws Exception
     */
    public static void addExcelSheet(HSSFWorkbook workbook, String sheetName, String[] headers, String[] columns, int[] widths, List dataset) throws Exception {
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetName);

        // 设置表格默认列宽度为15个字节
        // sheet.setDefaultColumnWidth(20);

        // 表头颜色
        HSSFPalette palette = workbook.getCustomPalette();
        palette.setColorAtIndex((short) 50, (byte) (0xff), (byte) (0xc0), (byte) (0x00));

        // 表头样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor((short) 50);
        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 表头字体
        HSSFFont headFont = workbook.createFont();
        headFont.setFontHeightInPoints((short) 11);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headStyle.setFont(headFont);

        // 内容样式
        HSSFCellStyle columnStyle = workbook.createCellStyle();
        columnStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        columnStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        columnStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        columnStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 内容字体
        HSSFFont columnFont = workbook.createFont();
        columnFont.setFontHeightInPoints((short) 11);
        columnFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        columnStyle.setFont(columnFont);

        // 表头
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 360);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            sheet.setColumnWidth(i, widths[i] * 45);
        }

        // 内容
        for (int i = 0; i < dataset.size(); i++) {
            Map dataMap = (Map) dataset.get(i);
            row = sheet.createRow(i + 1);
            row.setHeight((short) 360);

            for (int k = 0; k < columns.length; k++) {
                HSSFCell cell = row.createCell(k);
                cell.setCellStyle(columnStyle);

                String textValue = Tools.getStr(dataMap.get(columns[k]), "");
                HSSFRichTextString text = new HSSFRichTextString(textValue);
                cell.setCellValue(text);
            }
        }
    }

    /**
     * 读取Excel合并单元格,都当做string读取
     * 
     * @author luoshw
     * @param is
     * @param excelDataList
     * @param startRow
     * @param columns
     * @param columnNames
     * @return
     * @throws Exception
     */
    public static boolean excelMergedRegion(InputStream is, List excelDataList, int startRow, int[] columns, String[] columnNames) throws Exception {

        if (columns.length != columnNames.length) {
            return false;
        }
        if (columns.length <= 0) {
            return false;
        }

        // 循环工作表中合并单元格
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            for (int j = 0; j < columns.length; j++) {
                if (columns[j] >= firstColumn && columns[j] <= lastColumn) {
                    String cellValue = getValue(sheet.getRow(firstRow).getCell(columns[j]));
                    for (int k = firstRow + 1; k <= lastRow; k++) {
                        DynaBeanMap beanMap = (DynaBeanMap) excelDataList.get(k - startRow);
                        beanMap.put(columnNames[j], cellValue);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 导出图片到excel
     * 
     * @author luoshw
     * @param freeChart
     *            图表
     * @param out
     *            输出流
     * @param width
     *            图片宽度
     * @param height
     *            图片高度
     * @param sheetName
     *            excel 工作表名称
     * @param anchor
     *            图片位置
     * @throws Exception
     */
    public static void exportPicturesExcel(List<JfreeChartPicturePO> chartPictures, ServletOutputStream out, int width, int height, String sheetName) throws Exception {

    	// 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetName);
        
        //创建一个插入图片的画板
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

        for (JfreeChartPicturePO picturePO : chartPictures) {
            JFreeChart freeChart = picturePO.getFreeChart();
            HSSFClientAnchor anchor = picturePO.getAnchor();
            ByteArrayOutputStream outPNG = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsPNG(outPNG, freeChart, width, height);

            // 插入图片
            patriarch.createPicture(anchor, workbook.addPicture(outPNG.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)).resize(0.7);
        }

        // 将excel写入输出流中
        workbook.write(out);
    }
    
    //一起导出表格数据和图片
    public static void exportExcelAll(String title, String[] headers, String[] columns, int[] widths, List dataset, OutputStream out,
    		List<JfreeChartPicturePO> chartPictures, ServletOutputStream out1, int width, int height, String sheetName) throws IOException {

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetName);
        
        //创建一个插入图片的画板
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

        // 设置表格默认列宽度为15个字节
        //sheet.setDefaultColumnWidth(20);
        
        //表头颜色
        HSSFPalette palette = workbook.getCustomPalette();  
        palette.setColorAtIndex((short) 50, (byte)(0xff), (byte)(0xc0), (byte)(0x00));

        //表头样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor((short) 50);
        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       
        //表头字体
        HSSFFont headFont = workbook.createFont();
        headFont.setFontHeightInPoints((short) 11);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headStyle.setFont(headFont);

        //标题样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        //标题字体
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleStyle.setFont(titleFont);
        
        //内容样式
        HSSFCellStyle columnStyle = workbook.createCellStyle();
        columnStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        columnStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        columnStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        columnStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        //内容字体
        HSSFFont columnFont = workbook.createFont();
        columnFont.setFontHeightInPoints((short) 11);
        columnFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        columnStyle.setFont(columnFont);

        //标题
        HSSFRow row = sheet.createRow(0);
        for(int i = 0; i < columns.length; i++){
        	HSSFCell cell = row.createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,columns.length-1));
        HSSFCell cellTitle = sheet.getRow(0).getCell(0);
        cellTitle.setCellStyle(titleStyle);
        cellTitle.setCellValue(title);
        
        //表头
        row = sheet.createRow(1);
        row.setHeight((short) 360);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            sheet.setColumnWidth(i, widths[i] * 45);
        }

        //内容
        for (int i = 0; i < dataset.size(); i++) {
            Map dataMap = (Map) dataset.get(i);
            row = sheet.createRow(i + 2);
            row.setHeight((short) 360);
            
            for (int k = 0; k < columns.length; k++) {
                HSSFCell cell = row.createCell(k);
                cell.setCellStyle(columnStyle);
                
                String textValue = Tools.getStr(dataMap.get(columns[k]), "");
                HSSFRichTextString text = new HSSFRichTextString(textValue);
                cell.setCellValue(text);
            }
        }
             
        for (JfreeChartPicturePO picturePO : chartPictures) {
            JFreeChart freeChart = picturePO.getFreeChart();
            HSSFClientAnchor anchor = picturePO.getAnchor();
            ByteArrayOutputStream outPNG = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsPNG(outPNG, freeChart, width, height);

            // 插入图片
            patriarch.createPicture(anchor, workbook.addPicture(outPNG.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)).resize(0.7);
        }

        // 将excel写入输出流中
        workbook.write(out1);
        
        //写入数据
        workbook.write(out);
    }
    
    public static void exportExcel(String title, String[] headers, String[] columns, int[] widths, List dataset, OutputStream out, Double compare) throws IOException {

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet();

        // 设置表格默认列宽度为15个字节
        // sheet.setDefaultColumnWidth(20);
        
        //表头颜色
        HSSFPalette palette = workbook.getCustomPalette();  
        palette.setColorAtIndex((short) 50, (byte)(0xff), (byte)(0xc0), (byte)(0x00));

        //表头样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor((short) 50);
        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        //表头字体
        HSSFFont headFont = workbook.createFont();
        headFont.setFontHeightInPoints((short) 11);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headStyle.setFont(headFont);

        //标题样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        //标题字体
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleStyle.setFont(titleFont);
        
        //内容样式
        HSSFCellStyle columnStyle = workbook.createCellStyle();
        columnStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        columnStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        columnStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        columnStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        //内容字体
        HSSFFont columnFont = workbook.createFont();
        columnFont.setFontHeightInPoints((short) 11);
        columnFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        columnStyle.setFont(columnFont);
        
        //内容样式
        HSSFCellStyle columnStyleColor = workbook.createCellStyle();
        columnStyleColor.setFillForegroundColor(HSSFColor.WHITE.index);
        columnStyleColor.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        columnStyleColor.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        columnStyleColor.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        columnStyleColor.setBorderRight(HSSFCellStyle.BORDER_THIN);
        columnStyleColor.setBorderTop(HSSFCellStyle.BORDER_THIN);
        columnStyleColor.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        columnStyleColor.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        columnStyleColor.setFillForegroundColor(HSSFColor.RED.index);
        //内容字体
        HSSFFont columnFontColor = workbook.createFont();
        columnFontColor.setFontHeightInPoints((short) 11);
        columnFontColor.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        columnStyleColor.setFont(columnFontColor);

        //标题
        HSSFRow row = sheet.createRow(0);
        for(int i = 0; i < columns.length; i++){
        	HSSFCell cell = row.createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,columns.length-1));
        HSSFCell cellTitle = sheet.getRow(0).getCell(0);
        cellTitle.setCellStyle(titleStyle);
        cellTitle.setCellValue(title);
        
        //表头
        row = sheet.createRow(1);
        row.setHeight((short) 360);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            sheet.setColumnWidth(i, widths[i] * 45);
        }
        
        double compareDb = 0.0;
        //内容
        for (int i = 0; i < dataset.size() ; i++) {
        	if(dataset.size() > 65532){
        		break;
        	}
            Map dataMap = (Map) dataset.get(i);
            row = sheet.createRow(i + 2);
            row.setHeight((short) 360);
            compareDb = 0.0;
            for (int k = 0; k < columns.length; k++) {
                HSSFCell cell = row.createCell(k);
                if(k == 1){
                	compareDb = Tools.getDouble(dataMap.get(columns[k]),0.0);
                }
                String textValue = Tools.getStr(dataMap.get(columns[k]), "");
                HSSFRichTextString text = new HSSFRichTextString(textValue);
                if(Tools.getIsNumber(textValue) && k > 1){
                	if(compareDb <= Tools.getDouble(textValue,0.0)){
                		cell.setCellStyle(columnStyleColor);
                	}else{
                		cell.setCellStyle(columnStyle);
                	}
                }else{
                	cell.setCellStyle(columnStyle);
                }
                cell.setCellValue(text);
            }
        }
        workbook.write(out);
        //写入数据  
		out.close();
    }
    
}
