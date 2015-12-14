package com.vini.panels;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.OddEvenListItem;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.vini.pages.survey.SurveyEnd;
import com.vini.service.IUserSurveyDetailsService;
import com.vini.vo.AnswerVO;
import com.vini.vo.UserSurveyDetailsVO;

public class SurveyPanel extends Panel {
    
    private static final long serialVersionUID = 1L;
    
    @SpringBean
    private IUserSurveyDetailsService userSurveyDetailsService;
    
    private static int MAX = 6;
    private UserSurveyDetailsVO userSurveyDetailsVO;
    private List<AnswerVO> answers;
    private ListModel<AnswerVO> answerModel = new ListModel<AnswerVO>();
    
    public SurveyPanel(String id, Long userId, Long surveyId, final Long questionSetId) {
        super(id);
        
        // check if user survey details exist or not. if yes, fetch the existing otherwise save new.
        userSurveyDetailsVO = fetchOrCreateUserSurveyDetails(userId, surveyId);
        fetchAnswers(questionSetId);
        
        Form<Void> questionForm = new StatelessForm<Void>("questionForm");
        questionForm.setOutputMarkupId(true);
        add(questionForm);
        
        final ListView<AnswerVO> questionView = new ListView<AnswerVO>("questionView", answerModel) {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void populateItem(ListItem<AnswerVO> item) {
                item.add(new Label("question", new Model<String>(item.getModelObject().getQuestion())));
                item.add(getOptions(item.getModelObject()));
            }
            
            @Override
            protected ListItem<AnswerVO> newItem(int index, IModel<AnswerVO> itemModel) {
                return new OddEvenListItem<AnswerVO>(index, itemModel);
            }
        };
        questionView.setOutputMarkupId(true);
        questionForm.add(questionView);
        
        AjaxSubmitLink next = new AjaxSubmitLink("next") {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                List<AnswerVO> answerVOs = questionView.getModelObject();
                boolean allAnswered = true;
                for(AnswerVO answer : answerVOs){
                    if(answer.getOption() == null){
                        allAnswered = false;
                        break;
                    }
                }
                if(!allAnswered){
                    error("Please answer all the questions.");
                    target.add(form);
                }else{
                    userSurveyDetailsVO.addAnswers(answerVOs);
                    userSurveyDetailsService.saveUserAnswers(userSurveyDetailsVO);
                    fetchAnswers(questionSetId);
                    target.add(form);
                }
            }
            
        };
        
        questionForm.add(next);
        questionForm.add(new FeedbackPanel("feedback"));
        
    }
    
    /**
     * Method to fetch answers for the question set
     * @param questionSetId
     */
    private void fetchAnswers(Long questionSetId) {
        answers = userSurveyDetailsService.fetchNextAnswers(questionSetId, userSurveyDetailsVO.getAnswers().size(), MAX);
        answerModel.setObject(answers);
        if(answers.isEmpty()){
            setResponsePage(SurveyEnd.class);
        }
    }
    
    /**
     * Fetch user survey details if exist otherwise create
     * @param userId
     * @param surveyId
     * @return
     */
    private UserSurveyDetailsVO fetchOrCreateUserSurveyDetails(Long userId, Long surveyId) {
        return userSurveyDetailsService.fetchOrCreateUserSurveyDetails(userId, surveyId);
    }
    
    /**
     * Method to create options
     * @param answerVO
     * @return options
     */
    public RadioGroup<Integer> getOptions(AnswerVO answerVO){
        RadioGroup<Integer> options = new RadioGroup<Integer>("options", new PropertyModel<Integer>(answerVO, "option"));
        options.add(new Radio<Integer>("1", new Model<Integer>(1)));
        options.add(new Radio<Integer>("2", new Model<Integer>(2)));
        options.add(new Radio<Integer>("3", new Model<Integer>(3)));
        options.add(new Radio<Integer>("4", new Model<Integer>(4)));
        options.add(new Radio<Integer>("5", new Model<Integer>(5)));
        return options;
    }
    
}
