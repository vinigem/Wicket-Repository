package com.vini.components;

import org.apache.wicket.Application;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.model.IModel;

import com.vini.notification.NotificationItem;
import com.vini.notification.NotificationType;
import com.vini.util.FeedbackUtil;

public abstract class FeedbackAutoCompleteTextField<T> extends AutoCompleteTextField<T> implements INotificationFeedback{
    private static final long serialVersionUID = 1L;
    
    
    public FeedbackAutoCompleteTextField(final String id, final Class<T> type){
        super(id, type);
    }
    
    
    public FeedbackAutoCompleteTextField(final String id, final IModel<T> model, final Class<T> type, final AutoCompleteSettings settings){
        super(id, model, type, settings);
    }
    
    
    public FeedbackAutoCompleteTextField(final String id, final IModel<T> model, final AutoCompleteSettings settings){
        super(id, model, settings);
    }
    
    
    public FeedbackAutoCompleteTextField(final String id, final IModel<T> model){
        super(id, model);
    }
    
    
    public FeedbackAutoCompleteTextField(final String id, final AutoCompleteSettings settings){
        super(id, settings);
    }
    
    
    public FeedbackAutoCompleteTextField(final String id){
        super(id);
    }
    
    
    public FeedbackAutoCompleteTextField(final String id, final IAutoCompleteRenderer<T> renderer){
        super(id, renderer);
    }
    
    
    public FeedbackAutoCompleteTextField(final String id, final Class<T> type, final IAutoCompleteRenderer<T> renderer)	{
        super(id, type, renderer);
    }
    
    
    public FeedbackAutoCompleteTextField(final String id, final IModel<T> model, final IAutoCompleteRenderer<T> renderer){
        super(id, model, renderer);
    }
    
    
    public FeedbackAutoCompleteTextField(final String id, final IModel<T> model, final Class<T> type, final IAutoCompleteRenderer<T> renderer, 
            final AutoCompleteSettings settings)	{
        super(id, model, type, renderer, settings);
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
