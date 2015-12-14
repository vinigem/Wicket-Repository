package com.vini.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphUtil.class);
    
    static final String STRONGLY_DISAGREE = "Strongly Disagree";
    static final String DISAGREE = "Disagree";
    static final String NO_OPINION = "No Opinion";
    static final String AGREE = "Agree";
    static final String STRONGLY_AGREE = "Strongly Agree";
    
    static final int STRONGLY_DISAGREE_CODE = 1;
    static final int DISAGREE_CODE = 2;
    static final int NO_OPINION_CODE = 3;
    static final int AGREE_CODE = 4;
    static final int STRONGLY_AGREE_CODE = 5;
    
    /**
     * Method to create pie graph
     * @param options
     * @param height
     * @param width
     * @return pie graph file
     */
    public File createPieGraph(List<Integer> options, int height, int width){
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        
        Map<String, Integer> count = getOptionsCount(options);
        
        pieDataset.setValue(STRONGLY_DISAGREE, count.get(STRONGLY_DISAGREE) == 0 ? 0.0 : options.size() / count.get(STRONGLY_DISAGREE));
        pieDataset.setValue(DISAGREE, count.get(DISAGREE) == 0 ? 0.0 : options.size() / count.get(DISAGREE));
        pieDataset.setValue(NO_OPINION, count.get(NO_OPINION) == 0 ? 0.0 : options.size() / count.get(NO_OPINION));
        pieDataset.setValue(AGREE, count.get(AGREE) == 0 ? 0.0 : options.size() / count.get(AGREE));
        pieDataset.setValue(STRONGLY_AGREE, count.get(STRONGLY_AGREE) == 0 ? 0.0 : options.size() / count.get(STRONGLY_AGREE));
        
        JFreeChart pieChart = ChartFactory.createPieChart3D("Response Summary", pieDataset, true, true, false);
        
        File chartImageFile = new File( "PieChart.jpeg" ); 
        try {
            ChartUtilities.saveChartAsJPEG( chartImageFile , pieChart , width , height );
        } catch (IOException e) {
            LOGGER.error("Error while creating pie graph. {}", e);
        }
        return chartImageFile;
    }
    
    /**
     * Method to create bar graph
     * @param options
     * @param height
     * @param width
     * @return bar graph file
     */
    public File createBarGraph(List<Integer> options, int height, int width) {
        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
        
        Map<String, Integer> count = getOptionsCount(options);
        
        categoryDataset.addValue( count.get(STRONGLY_DISAGREE) , STRONGLY_DISAGREE, "");
        categoryDataset.addValue( count.get(DISAGREE), DISAGREE, "");
        categoryDataset.addValue( count.get(NO_OPINION) , NO_OPINION, "");
        categoryDataset.addValue( count.get(AGREE) , AGREE, "" );
        categoryDataset.addValue( count.get(STRONGLY_AGREE) , STRONGLY_AGREE, "");
        
        JFreeChart barChart = ChartFactory.createBarChart3D("Response Summary", "Options", "Total Response", categoryDataset, PlotOrientation.VERTICAL, true, true, false);
        
        File chartImageFile = new File( "BarChart.jpeg" ); 
        try {
            ChartUtilities.saveChartAsJPEG( chartImageFile , barChart , width , height );
        } catch (IOException e) {
            LOGGER.error("Error while creating bar graph. {}", e);
        }
        return chartImageFile;
    }
    
    
    /**
     * Method to get options count
     * @param options
     * @return counts
     */
    private Map<String, Integer> getOptionsCount(List<Integer> options) {
        Map<String, Integer> counts = new HashMap<String, Integer>();
        
        int stronglyDisagreeCount = 0;
        int disagreeCount = 0;
        int noOpinionCount = 0;
        int agreeCount = 0;
        int stronglyAgreeCount = 0;
        
        for(int option : options){
            if(option == 1){
                stronglyDisagreeCount++;
            }else if(option == 2){
                disagreeCount++;
            }else if(option == 3){
                noOpinionCount++;
            }else if(option == 4){
                agreeCount++;
            }else if(option == 5){
                stronglyAgreeCount++;
            }
        }
        counts.put(STRONGLY_DISAGREE, stronglyDisagreeCount);
        counts.put(DISAGREE, disagreeCount);
        counts.put(NO_OPINION, noOpinionCount);
        counts.put(AGREE, agreeCount);
        counts.put(STRONGLY_AGREE, stronglyAgreeCount);
        
        return counts;
    }
    
}
