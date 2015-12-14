package com.vini.components;

import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import com.vini.notification.NotificationItem;
import com.vini.notification.NotificationType;
import com.vini.util.FeedbackUtil;

public class FeedbackDropDownChoice<T> extends DropDownChoice<T> implements INotificationFeedback{
    private static final long serialVersionUID = 1L;
    
    public FeedbackDropDownChoice(final String id){
        super(id);
    }
    
    
    public FeedbackDropDownChoice(final String id, final List<? extends T> choices){
        super(id, choices);
    }
    
    
    public FeedbackDropDownChoice(final String id, final List<? extends T> choices,	final IChoiceRenderer<? super T> renderer){
        super(id, choices, renderer);
    }
    
    
    public FeedbackDropDownChoice(final String id, IModel<T> model, final List<? extends T> choices){
        super(id, model, choices);
    }
    
    
    public FeedbackDropDownChoice(final String id, IModel<T> model, final List<? extends T> choices, final IChoiceRenderer<? super T> renderer) {
        super(id, model, choices, renderer);
    }
    
    
    public FeedbackDropDownChoice(String id, IModel<? extends List<? extends T>> choices){
        super(id, choices);
    }
    
    
    public FeedbackDropDownChoice(String id, IModel<T> model, IModel<? extends List<? extends T>> choices){
        super(id, model, choices);
    }
    
    
    public FeedbackDropDownChoice(String id, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer){
        super(id, choices, renderer);
    }
    
    
    public FeedbackDropDownChoice(String id, IModel<T> model, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer){
        super(id, model, choices, renderer);
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
