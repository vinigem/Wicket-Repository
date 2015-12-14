package com.vini.components;

import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;

import com.vini.notification.NotificationItem;
import com.vini.notification.NotificationType;
import com.vini.util.FeedbackUtil;

public class FeedbackFileUploadField extends FileUploadField implements INotificationFeedback{
    
    private static final long serialVersionUID = 1L;
    
    
    public FeedbackFileUploadField(final String id)	{
        super(id);
    }
    
    public FeedbackFileUploadField(final String id, IModel<List<FileUpload>> model)	{
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
