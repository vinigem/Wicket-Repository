package com.vini.pages.admin;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.vini.components.FeedbackDropDownChoice;
import com.vini.constants.SurveyConstant;
import com.vini.notification.NotificationType;
import com.vini.service.IQuestionSetService;
import com.vini.service.ISurveyService;
import com.vini.util.renderer.SurveyRenderer;
import com.vini.vo.QuestionSetVO;
import com.vini.vo.SurveyVO;

public class LinkSurvey extends AdminBasepage {
    private static final long serialVersionUID = 1L;
    
    @SpringBean
    private IQuestionSetService questionSetService;
    
    @SpringBean
    private ISurveyService surveyService;
    
    private SurveyVO selectedSurvey;
    
    public LinkSurvey() {
        Form<Void> form = new StatelessForm<Void>("form");
        form.setOutputMarkupId(true);
        add(form);
        
        ListModel<SurveyVO> surveyModel = new ListModel<SurveyVO>(surveyService.getAllSurveys());
        
        final FeedbackDropDownChoice<SurveyVO> surveys = new FeedbackDropDownChoice<SurveyVO>("surveys", 
                new PropertyModel<SurveyVO>(this, "selectedSurvey"), surveyModel, new SurveyRenderer());
        surveys.setNullValid(true);
        surveys.setRequired(true);
        form.add(surveys);
        
        final WebMarkupContainer questionSetContainer = new WebMarkupContainer("questionSetContainer"){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onConfigure() {
                setVisible(getSelectedSurvey() != null);
                super.onConfigure();
            }
        };
        questionSetContainer.setOutputMarkupId(true);
        questionSetContainer.setOutputMarkupPlaceholderTag(true);
        questionSetContainer.add(getAllQuestionSetOptions());
        form.add(questionSetContainer);
        
        form.add(new AjaxSubmitLink("submit"){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if(getSelectedSurvey().getQuestionSetId() != null){
                    surveyService.linkSurveyQuestionSet(selectedSurvey.getQuestionSetId(), selectedSurvey.getSurveyId());
                    broadcast(NotificationType.SUCCESS, "Question Set successfully linked to " + selectedSurvey.getSurveyName());
                }
            }
        });
        
        surveys.add(new AjaxFormComponentUpdatingBehavior(SurveyConstant.ON_CHANGE){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(questionSetContainer);
            }
        });
    }
    
    /**
     * Method to create survey question's options
     * @return options
     */
    private RadioGroup<Long> getAllQuestionSetOptions() {
        RadioGroup<Long> questionSetGroup = new RadioGroup<Long>("questionSets", new PropertyModel<Long>(this, "selectedSurvey.questionSetId"));
        
        ListModel<QuestionSetVO> questionSetModel = new ListModel<QuestionSetVO>(questionSetService.getAllQuestionsSets());
        
        ListView<QuestionSetVO> questionSetListView = new ListView<QuestionSetVO>("questionSetListView", questionSetModel) {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void populateItem(ListItem<QuestionSetVO> item) {
                item.add(new Radio<Long>("questionSetId", new Model<Long>(item.getModelObject().getQuestionSetId())));
                item.add(new Label(SurveyConstant.QUESTION_SET_NAME, new PropertyModel<String>(item.getModelObject(), "questionSetName")));
            }
        };
        questionSetGroup.add(questionSetListView);
        return questionSetGroup;
    }
    
    public SurveyVO getSelectedSurvey() {
        return selectedSurvey;
    }
    
    public void setSelectedSurvey(SurveyVO selectedSurvey) {
        this.selectedSurvey = selectedSurvey;
    }
    
    
}
