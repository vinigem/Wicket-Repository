package com.vini.pages.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vini.components.FeedbackDropDownChoice;
import com.vini.constants.SurveyConstant;
import com.vini.service.ISurveyService;
import com.vini.service.IUserSurveyDetailsService;
import com.vini.util.CustomPagingNavigator;
import com.vini.util.GraphUtil;
import com.vini.util.renderer.SurveyRenderer;
import com.vini.vo.SummaryVO;
import com.vini.vo.SurveyVO;

public class ViewSummary extends AdminBasepage {
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewSummary.class);
    
    @SpringBean
    private ISurveyService surveyService;
    
    @SpringBean
    private IUserSurveyDetailsService surveyDetailsService;
    
    private SurveyVO selectedSurvey;
    
    private static final String PIE_GRAPH = "Pie Graph";
    private static final String BAR_GRAPH = "Bar Graph";
    
    private String selectedGraphType = PIE_GRAPH;
    private WebMarkupContainer graphContainer;
    
    public ViewSummary() {
        graphContainer = new WebMarkupContainer("graphContainer");
        add(graphContainer.setOutputMarkupId(true));
        
        StatelessForm<Void> form = new StatelessForm<Void>("form");
        add(form);
        
        ListModel<SurveyVO> surveyModel = new ListModel<SurveyVO>(surveyService.getAllSurveys());
        
        final FeedbackDropDownChoice<SurveyVO> surveys = new FeedbackDropDownChoice<SurveyVO>("surveys", new PropertyModel<SurveyVO>(this, "selectedSurvey"), surveyModel, new SurveyRenderer());
        surveys.setNullValid(true);
        form.add(surveys);
        
        form.add(getGraphTypes());
        
        final ListModel<SummaryVO> userSurveyDetailModel = new ListModel<SummaryVO>();
        PageableListView<SummaryVO> userSurveyDetailView = new PageableListView<SummaryVO>("userSurveyDetailView", userSurveyDetailModel, 1) {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void populateItem(final ListItem<SummaryVO> item) {
                item.add(new Label("question", item.getModelObject().getQuestion()));
                
                item.add(new Image("image", new AbstractReadOnlyModel<DynamicImageResource>(){
                    private static final long serialVersionUID = 1L;
                    
                    @Override 
                    public DynamicImageResource getObject() {
                        DynamicImageResource dir = new DynamicImageResource() {
                            private static final long serialVersionUID = 1L;
                            
                            @Override
                            protected byte[] getImageData(Attributes attributes) {
                                return getGraph(selectedGraphType, item.getModelObject().getOptions(), 300, 1000);
                            }
                        };
                        dir.setFormat("image/jpeg");
                        return dir;
                    }
                }));
            }
        };
        graphContainer.add(new CustomPagingNavigator("pagingNavigator", userSurveyDetailView, Arrays.asList(1, 5, 10, 20, 25, 20), true));
        graphContainer.add(userSurveyDetailView);
        
        surveys.add(new AjaxFormComponentUpdatingBehavior(SurveyConstant.ON_CHANGE){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                if(selectedSurvey != null){
                    List<SummaryVO> userSurveyDetailsVOs = surveyDetailsService.getAllUserSurveyDetails(selectedSurvey.getSurveyId());
                    userSurveyDetailModel.setObject(userSurveyDetailsVOs);
                    target.add(graphContainer);
                }
            }
        });
    }
    
    /**
     * Method to create graph
     * 
     * @param selectedGraphType
     * @param options
     * @param height
     * @param width
     * @return graph image byte[]
     */
    private byte[] getGraph(String selectedGraphType, List<Integer> options, int height, int width) {
        if(selectedGraphType.equals(PIE_GRAPH)){
            return getFileAsBytes(new GraphUtil().createPieGraph(options, height, width));
        }else if(selectedGraphType.equals(BAR_GRAPH)){
            return getFileAsBytes(new GraphUtil().createBarGraph(options, height, width));
        }else{
            return new byte[0];
        }
    }
    
    public RadioGroup<Integer> getGraphTypes(){
        RadioGroup<Integer> graphTypes = new RadioGroup<Integer>("graphTypes", new PropertyModel<Integer>(this, "selectedGraphType"));
        graphTypes.add(new Radio<String>("pie", new Model<String>(PIE_GRAPH)));
        graphTypes.add(new Radio<String>("bar", new Model<String>(BAR_GRAPH)));
        
        graphTypes.add(new AjaxFormChoiceComponentUpdatingBehavior() {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(graphContainer);
            }
        });
        
        return graphTypes;
    }
    
    /**
     * Method to convert file to byte array
     * @param file
     * @return byte array
     */
    private byte[] getFileAsBytes(File file) {
        if(file == null){
            return new byte[0];
        }
        try {
            return IOUtils.toByteArray(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            LOGGER.error("Error while converting file to byte[]. {}", e);
        } catch (IOException e) {
            LOGGER.error("Error while converting file to byte[]. {}", e);
        }
        return new byte[0];
    }
    
    public SurveyVO getSelectedSurvey() {
        return selectedSurvey;
    }
    
    public void setSelectedSurvey(SurveyVO selectedSurvey) {
        this.selectedSurvey = selectedSurvey;
    }
    
    public String getSelectedGraphType() {
        return selectedGraphType;
    }
    
    public void setSelectedGraphType(String selectedGraphType) {
        this.selectedGraphType = selectedGraphType;
    }
    
}
