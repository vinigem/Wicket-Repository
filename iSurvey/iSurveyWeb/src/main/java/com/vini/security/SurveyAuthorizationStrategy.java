package com.vini.security;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;

import com.vini.SurveySession;
import com.vini.pages.admin.AdminBasepage;
import com.vini.pages.admin.SignIn;

public class SurveyAuthorizationStrategy implements IAuthorizationStrategy {
    
    @Override
    public boolean isActionAuthorized(Component component, Action action) {
        return true;
    }
    
    /**
     * Method to check authorization
     */
    @Override
    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
        if (AdminBasepage.class.isAssignableFrom(componentClass)){
            if (((SurveySession)Session.get()).isSignedIn()){
                return true;
            }
            throw new RestartResponseAtInterceptPageException(SignIn.class);
        }
        return true;
    }
    
    
}
