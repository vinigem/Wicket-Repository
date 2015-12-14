package com.vini.util;

import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessages;

public final class FeedbackUtil {
    
    private FeedbackUtil() {}
    
    public static final String getMessage(FeedbackMessages feedbackMessages, Component component){
        for(FeedbackMessage message : feedbackMessages){
            if(message.getReporter().equals(component) && message.isError()){
                return message.getMessage().toString();
            }
        }
        return null;
    }
    
}
