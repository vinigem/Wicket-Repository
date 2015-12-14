package com.vini.pages.admin;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.validator.StringValidator;

import com.vini.components.FeedbackAutoCompleteTextField;
import com.vini.components.FeedbackDateTextField;
import com.vini.components.FeedbackTextArea;
import com.vini.constants.SurveyConstant;
import com.vini.notification.NotificationType;
import com.vini.service.ISurveyService;
import com.vini.vo.SurveyVO;

public class CreateSurvey extends AdminBasepage {
    private static final long serialVersionUID = 1L;
    
    @SpringBean
    private ISurveyService surveyService;
    
    public CreateSurvey() {
        final Form<SurveyVO> surveyForm = new Form<SurveyVO>("form", new CompoundPropertyModel<SurveyVO>(new SurveyVO()));
        surveyForm.setOutputMarkupId(true);
        
        final FeedbackAutoCompleteTextField<String> surveyName = new FeedbackAutoCompleteTextField<String>(SurveyConstant.SURVEY_NAME){
            
            private static final long serialVersionUID = 1L;
            
            @Override
            protected Iterator<String> getChoices(String input) {
                if (Strings.isEmpty(input)){
                    List<String> emptyList = Collections.emptyList();
                    return emptyList.iterator();
                }
                
                List<String> surveys = surveyService.getSurveyNames(input);
                return surveys.iterator();
            }
            
        };
        surveyName.setRequired(true);
        surveyName.setOutputMarkupId(true);
        
        surveyName.add(new AjaxFormComponentUpdatingBehavior(SurveyConstant.ON_CHANGE) {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                String srvName = surveyName.getModelObject();
                SurveyVO surveyVO = surveyService.getSurveyByName(srvName);
                if(surveyVO != null){
                    surveyForm.setModelObject(surveyVO);
                }
                target.add(surveyForm);
            }
        });
        
        final FeedbackDateTextField startDate = new FeedbackDateTextField("startDate", new PatternDateConverter("dd-MM-yyyy", true));
        startDate.add(getDatePicker());
        startDate.setRequired(true);
        
        final FeedbackDateTextField endDate = new FeedbackDateTextField("endDate", new PatternDateConverter("dd-MM-yyyy", true));
        endDate.add(getDatePicker());
        endDate.setRequired(true);
        
        IFormValidator validator = new AbstractFormValidator() {
            private static final long serialVersionUID = 1L;
            
            @Override
            public FormComponent<?>[] getDependentFormComponents() {
                return new FormComponent[] { startDate, endDate };
            }
            
            @Override
            public void validate(Form<?> form) {
                Date start = DateUtils.truncate(startDate.getConvertedInput(), Calendar.DAY_OF_MONTH);
                Date end =  DateUtils.truncate(endDate.getConvertedInput(), Calendar.DAY_OF_MONTH);
                Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
                
                if(start.compareTo(today) < 0){
                    error(startDate, "star_date_is_before_todays_date");
                }
                if(end.compareTo(today) < 0){
                    error(endDate, "end_date_is_before_todays_date");
                }
                if (end.compareTo(start) < 0){
                    error(endDate, "end_date_is_before_start_date");
                }
            }
        };
        surveyForm.add(validator);
        
        FeedbackTextArea<String> description = new FeedbackTextArea<String>("description");
        description.setRequired(true);
        description.add(StringValidator.lengthBetween(4, 500));
        
        AjaxSubmitLink submitLink = new AjaxSubmitLink("submit") {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                SurveyVO surveyVO = (SurveyVO) form.getModelObject();
                if(surveyVO.getSurveyId() != null){
                    surveyVO = surveyService.updateSurvey((SurveyVO)form.getModelObject());
                }else{
                    surveyVO = surveyService.createSurvey((SurveyVO)form.getModelObject());
                }
                surveyForm.setModelObject(new SurveyVO());
                target.add(surveyForm);
                broadcast(NotificationType.SUCCESS, surveyVO.getSurveyName() + " Created/Updated Successfully..!!");
            }
        };
        
        surveyForm.add(surveyName);
        surveyForm.add(startDate);
        surveyForm.add(endDate);
        surveyForm.add(description);
        surveyForm.add(submitLink);
        add(surveyForm);
    }
    
}
