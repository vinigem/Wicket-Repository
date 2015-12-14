package com.vini.components;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.IModel;

import com.vini.notification.NotificationItem;
import com.vini.notification.NotificationType;
import com.vini.util.FeedbackUtil;

public class FeedbackTextField<T> extends RequiredTextField<T> implements INotificationFeedback{
    
    private static final long serialVersionUID = 1L;
    
    public FeedbackTextField(final String id){
        super(id);
    }
    
    public FeedbackTextField(final String id, final Class<T> type){
        super(id, type);
    }
    
    public FeedbackTextField(final String id, final IModel<T> model){
        super(id, model);
    }
    
    
    public FeedbackTextField(final String id, IModel<T> model, Class<T> type){
        super(id, model, type);
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
