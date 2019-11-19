package com.hanthink.util;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.jfree.chart.JFreeChart;

/**
 * excel导出JfreeChart图片工具类
 * 
 * @author luoshw
 * 
 */
public class JfreeChartPicturePO {

    private JFreeChart freeChart;
    private HSSFClientAnchor anchor;

    public JFreeChart getFreeChart() {
        return freeChart;
    }

    public void setFreeChart(JFreeChart freeChart) {
        this.freeChart = freeChart;
    }

    public HSSFClientAnchor getAnchor() {
        return anchor;
    }

    public void setAnchor(HSSFClientAnchor anchor) {
        this.anchor = anchor;
    }

}
