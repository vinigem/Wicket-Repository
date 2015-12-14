package com.vini.security;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import com.vini.SurveySession;

public class UserRolesAuthorizer implements IRoleCheckingStrategy {
    
    @Override
    public boolean hasAnyRole(Roles roles) {
        SurveySession authSession = (SurveySession)Session.get();
        return authSession.getRoles().hasAnyRole(roles);
    }
    
}
