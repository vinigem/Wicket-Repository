package com.vini.pages.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.OddEvenListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.vini.SurveySession;
import com.vini.constants.SurveyConstant;
import com.vini.notification.NotificationType;
import com.vini.service.IQuestionSetService;
import com.vini.util.CustomPagingNavigator;

public class Questions extends AdminBasepage {
    
    private static final long serialVersionUID = 1L;
    
    @SpringBean
    private IQuestionSetService questionSetService;
    
    @SuppressWarnings("unchecked")
    public Questions() {
        final WebMarkupContainer questionContainer = new WebMarkupContainer("questionContainer");
        questionContainer.setOutputMarkupId(true);
        
        final List<String> questions = (List<String>) SurveySession.get().getSessionAttribute("questions");
        
        final ListModel<String> questionModel = new ListModel<String>(new ArrayList<String>(questions == null ? new ArrayList<String>() : questions));
        
        final PageableListView<String> questionView = new PageableListView<String>("questionView", questionModel, 15) {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void populateItem(final ListItem<String> item) {
                item.add(new Label("index", item.getIndex() + 1));
                item.add(new Label("question", item.getModelObject()));
                
                item.add(new AjaxLink<String>("delete", new Model<String>(item.getModelObject())) {
                    private static final long serialVersionUID = 1L;
                    
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        questionModel.getObject().remove(getModelObject());
                        target.add(questionContainer);
                    }
                });
            }
            
            @Override
            protected ListItem<String> newItem(int index, IModel<String> itemModel) {
                return new OddEvenListItem<String>(index, itemModel);
            }
        };
        questionView.setOutputMarkupId(true);
        questionContainer.add(new CustomPagingNavigator("pagingNavigator", questionView, false));
        questionContainer.add(questionView);
        
        questionContainer.add(new AjaxLink<Void>("reset"){
            private static final long serialVersionUID = 1L;
            
            @Override
            public void onClick(AjaxRequestTarget target) {
                questionModel.setObject( new ArrayList<String>(questions));
                target.add(questionContainer);
            }
        });
        
        questionContainer.add(new AjaxLink<Void>("submit"){
            private static final long serialVersionUID = 1L;
            
            @Override
            public void onClick(AjaxRequestTarget target) {
                String questionSetName = (String) SurveySession.get().getSessionAttribute(SurveyConstant.QUESTION_SET_NAME);
                boolean status = questionSetService.createQuestionSet(questionSetName, questionModel.getObject());
                if(status){
                    broadcast(NotificationType.SUCCESS, questionSetName + " uploaded successfully..!!");
                }
            }
        });
        
        add(questionContainer);
    }
    
}
