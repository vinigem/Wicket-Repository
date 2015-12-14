package com.vini;

import java.util.HashMap;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;

public class SurveySession extends AuthenticatedWebSession {
    private static final long serialVersionUID = 1L;
    
    private String user;
    private static final String WICKET = "wicket";
    
    private final HashMap<String, Object> atrributes = new HashMap<String, Object>();
    
    public SurveySession(Request request) {
        super(request);
        Injector.get().inject(this);
    }
    
    
    public static SurveySession get() {
        return (SurveySession)Session.get();
    }
    
    public Object getSessionAttribute(String key){
        return atrributes.get(key);
    }
    
    public void setSessionAttribute(String key, Object value){
        atrributes.put(key, value);
    }
    
    public String getUser(){
        return user;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    
    
    @Override
    public boolean authenticate(String userName, String password) {
        if (user == null && WICKET.equalsIgnoreCase(userName) && WICKET.equalsIgnoreCase(password)) {
            user = userName;
        }
        return user != null;
    }
    
    
    @Override
    public Roles getRoles() {
        return null;
    }
    
    
}
