package com.vini.components;

import java.util.Date;

import org.apache.wicket.Application;
import org.apache.wicket.datetime.DateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.model.IModel;

import com.vini.notification.NotificationItem;
import com.vini.notification.NotificationType;
import com.vini.util.FeedbackUtil;



public class FeedbackDateTextField extends DateTextField implements INotificationFeedback{
    private static final long serialVersionUID = 1L;
    
    
    public FeedbackDateTextField(String id, IModel<Date> model, DateConverter converter){
        super(id, model, converter);
    }
    
    public FeedbackDateTextField(String id, DateConverter converter){
        super(id, converter);
    }
    
    @Override
    protected void onInvalid() {
        String errorMessage = FeedbackUtil.getMessage(getFeedbackMessages(), this);
        if(errorMessage != null){
            broadcast(errorMessage);
        }
    }
    
    @Override
    public void broadcast(String message) {
        broadcaster.broadcastAll(Application.get(),  new NotificationItem(NotificationType.VALIDATION, message));		
    }
    
    
}
