package com.hanthink.util;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot; 
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Rotation;

import cn.boho.framework.po.DynaBeanMap;

/**
 * 使用 Jfreechart
 * 
 * @author luoshw
 * 
 */
public class JfreeChartUtil {
    /**
     * 创建CategoryDataset对象
     * 
     * @author luoshw
     * @param dataList
     *            数据list
     * @param legendKeys
     *            图例名称
     * @param dataKeys
     *            数据字段名称
     * @param xAixIndex
     *            横坐标字段名称组合（顺序取出，空则不加入）
     * @return
     * @throws Exception
     */
    public static CategoryDataset createDataset(List dataList, String[] legendKeys, String[] dataKeys, String[] xAixIndex) throws Exception {

        double[][] data = new double[dataKeys.length][dataList.size()];
        String[] colKeys = new String[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            DynaBeanMap beanMap = (DynaBeanMap) dataList.get(i);
            StringBuffer xaix = new StringBuffer();
            for (int j = 0; j < xAixIndex.length; j++) {
                if (beanMap.get(xAixIndex[j]) != null) {
                    xaix.append(String.valueOf(beanMap.get(xAixIndex[j])) + " ");
                }
            }
            colKeys[i] = xaix.toString();

            for (int j = 0; j < dataKeys.length; j++) {
//                data[j][i] = Tools.getDouble(beanMap.get(dataKeys[j]),0.0);
            	data[j][i] = Tools.getDouble((null == beanMap.get(dataKeys[j])||"".equals(beanMap.get(dataKeys[j]))) ? "0" : beanMap.get(dataKeys[j]));
            }
        }
        List<String> list = new LinkedList<String>();  
    	for(int m = 0; m < colKeys.length; m++) {  
    		if(!list.contains(colKeys[m].trim())) {  
    				list.add(colKeys[m].trim());  
    		}else{
    			list.add(colKeys[m].trim()+"1");
    		}
    	}
    	String[] colKeys1 = new String[list.size()];
    	for(int m = 0; m < list.size(); m++) { 
    		colKeys1[m] = list.get(m);
    	}
        System.out.println("*****************************");
        for(int i = 0 ;i<colKeys1.length;i++){
        	System.out.println(colKeys1[i]);
        }
        System.out.println("*****************************");
        
        return DatasetUtilities.createCategoryDataset(legendKeys, colKeys1, data);
    }

    //饼图数据集
    public static DefaultPieDataset createPieDataset(List dataList, String[] legendKeys, String[] dataKeys, String[] xAixIndex) throws Exception {

    	DefaultPieDataset pieDataset = new DefaultPieDataset();
    
        double[] data = new double[dataKeys.length];
        String[] colKeys = new String[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            DynaBeanMap beanMap = (DynaBeanMap) dataList.get(i);
            StringBuffer xaix = new StringBuffer();
            for (int j = 0; j < xAixIndex.length; j++) {
                if (beanMap.get(xAixIndex[j]) != null) {
                    xaix.append(String.valueOf(beanMap.get(xAixIndex[j])) + " ");
                }
            }
            colKeys[i] = xaix.toString();

            for (int j = 0; j < dataKeys.length; j++) {
            	if(null == beanMap.get(dataKeys[j])){
            		data[j] = 0.0;
            		continue;
            	}
                data[j] = Tools.getDouble(beanMap.get(dataKeys[j]));
            }
            
            pieDataset.setValue(colKeys[i], data[0]);
        }
      
        return pieDataset;
    }
    
    /**
     * 根据CategoryDataset创建JFreeChart对象（柱状图）
     * 
     * @author luoshw
     * @param categoryDataset
     *            数据源
     * @param title
     *            标题
     * @param xAixLabel
     *            横坐标名称
     * @param yAixLabel
     *            纵坐标名称
     * @param fontLabel
     *            label字体
     * @param fontxAix
     *            横坐标字体
     * @param colors
     *            柱体颜色
     * @param isItemLabelsVisible
     *            柱体上文字是否显示
     * @return
     */
    public static JFreeChart createBarChart(CategoryDataset categoryDataset, String title, String xAixLabel, String yAixLabel, Font fontLabel, Font fontxAix, String[] colors,
            boolean isItemLabelsVisible) {

        // 创建JFreeChart对象：ChartFactory.createLineChart
        JFreeChart jfreechart = ChartFactory.createBarChart(title, // 标题
                xAixLabel, yAixLabel, categoryDataset, PlotOrientation.VERTICAL, true, // legend
                false, // tooltips
                false); // URLs

        // 使用CategoryPlot设置各种参数。以下设置可以省略。
        CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();

        // 背景色 透明度
        plot.setBackgroundAlpha(0.5f);

        // 前景色 透明度
        plot.setForegroundAlpha(0.5f);

        // 其他设置 参考 CategoryPlot类
        plot.getDomainAxis().setLabelFont(fontLabel);
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        plot.getDomainAxis().setTickLabelFont(fontxAix);

        plot.getRangeAxis().setLabelFont(fontLabel);

        // 设置标题中文
        jfreechart.getTitle().setFont(fontLabel);

        // 设置legend文字
        jfreechart.getLegend().setItemFont(fontLabel);

        // 设置柱状图上值显示
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(isItemLabelsVisible);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        renderer.setItemLabelAnchorOffset(10D);

        // 设置柱体颜色
        for (int i = 0; i < colors.length; i++) {
            renderer.setSeriesPaint(i, Color.decode(colors[i]));
        }

        return jfreechart;
    }

    /**
     * 根据CategoryDataset创建JFreeChart对象（折线图）
     * 
     * @author luoshw
     * @param categoryDataset
     *            数据源
     * @param title
     *            标题
     * @param xAixLabel
     *            横坐标名称
     * @param yAixLabel
     *            纵坐标名称
     * @param fontLabel
     *            label字体
     * @param fontxAix
     *            横坐标字体
     * @param colors
     *            折线颜色
     * @param isItemLabelsVisible
     *            折线上值是否显示
     * @return
     */
    public static JFreeChart createLineChart(CategoryDataset categoryDataset, String title, String xAixLabel, String yAixLabel, Font fontLabel, Font fontxAix, String[] colors,
            boolean isItemLabelsVisible) {

        // 创建JFreeChart对象：ChartFactory.createLineChart
        JFreeChart jfreechart = ChartFactory.createLineChart(title, // 标题
                xAixLabel, yAixLabel, categoryDataset, PlotOrientation.VERTICAL, true, // legend
                false, // tooltips
                false); // URLs

        // 使用CategoryPlot设置各种参数。以下设置可以省略。
        CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();

        // 背景色 透明度
        plot.setBackgroundAlpha(0.5f);

        // 前景色 透明度
        plot.setForegroundAlpha(0.5f);

        // 其他设置 参考 CategoryPlot类
        plot.getDomainAxis().setLabelFont(fontLabel);
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        plot.getDomainAxis().setTickLabelFont(fontxAix);

        plot.getRangeAxis().setLabelFont(fontLabel);

        // 设置标题中文
        jfreechart.getTitle().setFont(fontLabel);

        // 设置legend文字
        jfreechart.getLegend().setItemFont(fontLabel);

        // 设置折线图上值显示
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(isItemLabelsVisible);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        renderer.setItemLabelAnchorOffset(10D);

        // 设置柱体颜色
        for (int i = 0; i < colors.length; i++) {
            renderer.setSeriesPaint(i, Color.decode(colors[i]));
        }

        return jfreechart;
    }
    
    //根据CategoryDataset创建JFreeChart对象（饼图）
    public static JFreeChart createPieChart(DefaultPieDataset Dataset, String title, String xAixLabel, String yAixLabel, Font fontLabel, Font fontxAix, String[] colors,
            boolean isItemLabelsVisible) {

        // 创建JFreeChart对象：ChartFactory.createLineChart
       JFreeChart jfreechart = ChartFactory.createPieChart(title, // 标题
                Dataset, true, true, false); 

        //设置标题字体,为了防止中文乱码
        jfreechart.setTitle(new TextTitle(title,new Font("黑体",Font.ITALIC,22)));
        //取得统计图标的第一个图例    
        LegendTitle legend=jfreechart.getLegend(0);
        //修改图例的字体,必须把显示图片设置为ture,否则会包空指针异常   
        legend.setItemFont(new Font("宋体",Font.BOLD,20));
        //取得图表显示对象(与柱状图和折线图不同)    
        PiePlot plot=(PiePlot) jfreechart.getPlot();
        //设置区块标签的字体==为了防止中文乱码：必须设置字体   
        plot.setLabelFont(new Font("隶书",Font.BOLD,22));  
        //图形边框颜色   
        plot.setBaseSectionOutlinePaint(Color.BLUE);    
        //图形边框粗细    
        plot.setBaseSectionOutlineStroke(new BasicStroke(0.5f));   
        //设置饼状图的绘制方向,可以按顺时针方向绘制,也可以按逆时针方向绘制   
        plot.setDirection(Rotation.ANTICLOCKWISE);
        //逆时针,Rotation.CLOCKWISE顺时针  
        //设置绘制角度(图形旋转角度)     
        plot.setStartAngle(70); 
        //设置突出显示的数据块   
        //plot.setExplodePercent(1, 0.5D);   
        //plot.setExplodePercent("One", 0.5D);    
        //扇区分离显示,对3D图不起效    
        plot.setExplodePercent(Dataset.getKey(0), 0.1d);   
        //分类标签与图的连接线颜色    
        plot.setLabelLinkPaint(Color.BLUE);   
        //分类标签边框颜色   
        plot.setLabelOutlinePaint(Color.black);   
        //分类标签阴影颜色   
        plot.setLabelShadowPaint(Color.RED);   
        //指定分类饼的颜色    
        plot.setSectionPaint(1, Color.BLACK);      
        //饼状图标签显示百分比 :自定义,{0}表示选项,{1}表示数值,{2}表示所占比例, 小数点后两位  
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(      "{0}:{1}\r\n{2}",NumberFormat.getNumberInstance(),new  DecimalFormat("0.00%")));        
        //图例显示百分比   
        plot.setLegendLabelGenerator(new  StandardPieSectionLabelGenerator("{0}={2}"));    
        //指定显示的拼图为:圆形(true),还是椭圆形(false)   
        plot.setCircular(true);   
        //没有数据的时候显示的内容    
        plot.setNoDataMessage("没有可用的数据...");       
        //设置鼠标悬停提示    
        plot.setToolTipGenerator(new StandardPieToolTipGenerator());   
        //设置热点链接   
        //plot.setURLGenerator(new StandardPieURLGenerator("detail.jsp"));
      
        return jfreechart;
   
    }
    
    //同一张图中含有柱状和折线
    public static JFreeChart createBarAndLineChart(CategoryDataset categoryDatasetBar, CategoryDataset categoryDatasetLine, String title, String xAixLabel, String yAixLabel, Font fontLabel, Font fontxAix, String[] colors,
            boolean isItemLabelsVisible) {

        // 创建JFreeChart对象：ChartFactory.createLineChart
        JFreeChart jfreechart = ChartFactory.createBarChart(title, // 标题
                xAixLabel, yAixLabel, categoryDatasetBar, PlotOrientation.VERTICAL, true, // legend
                false, // tooltips
                false); // URLs
        //新增一条Y轴
        
        // 使用CategoryPlot设置各种参数。以下设置可以省略。
        CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();
        
        //放折线图数据
        plot.setDataset(1, categoryDatasetLine);
        LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
        plot.setRenderer(1, lineandshaperenderer);
        
        // 背景色 透明度
        plot.setBackgroundAlpha(0.5f);

        // 前景色 透明度
        plot.setForegroundAlpha(0.5f);

        // 其他设置 参考 CategoryPlot类
        plot.getDomainAxis().setLabelFont(fontLabel);
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        plot.getDomainAxis().setTickLabelFont(fontxAix);

        plot.getRangeAxis().setLabelFont(fontLabel);

        // 设置标题中文
        jfreechart.getTitle().setFont(fontLabel);

        // 设置legend文字
        jfreechart.getLegend().setItemFont(fontLabel);

        // 设置柱状图上值显示
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(isItemLabelsVisible);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        renderer.setItemLabelAnchorOffset(10D);

        // 设置柱体颜色
        for (int i = 0; i < colors.length; i++) {
            renderer.setSeriesPaint(i, Color.decode(colors[i]));
        }
        
        ValueAxis valueAxis = plot.getRangeAxis();
        valueAxis.setLabelFont(new Font("黑体", Font.ITALIC, 18)); // 设置数据字体(纵轴)
        CategoryAxis categoryaxis = plot.getDomainAxis();
        categoryaxis.setLabelFont(new Font("黑体", Font.ITALIC, 18)); // 设置时字体（横轴）
        categoryaxis.setLowerMargin(0.0); // 柱状图和纵轴紧靠  
        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);//折线在柱面前面显示

        return jfreechart;
    }
    

}
