package com.vini.components;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

import com.vini.notification.NotificationItem;
import com.vini.notification.NotificationType;
import com.vini.util.FeedbackUtil;

public class FeedbackTextArea<T> extends TextArea<T> implements INotificationFeedback{
    private static final long serialVersionUID = 1L;
    
    
    public FeedbackTextArea(final String id){
        super(id);
    }
    
    public FeedbackTextArea(final String id, final IModel<T> model){
        super(id, model);
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
