package com.vini.pages.admin;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authentication.IAuthenticationStrategy;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

import com.vini.pages.Basepage;

public class SignIn extends Basepage {
    private static final long serialVersionUID = 1L;
    
    private boolean rememberMe = true;
    private String password;
    private String username;
    
    
    public SignIn() {
        add(new FeedbackPanel("feedback"));
        add(new SignInForm("signInForm"));
    }
    
    
    public final class SignInForm extends StatelessForm<SignInPanel>{
        private static final long serialVersionUID = 1L;
        
        /**
         * Method to create sign in form
         * @param id
         */
        public SignInForm(final String id){
            super(id);
            
            setDefaultModel(new CompoundPropertyModel<SignIn>(SignIn.this));
            
            add(new TextField<String>("username"));
            add(new PasswordTextField("password"));
            
            WebMarkupContainer rememberMeRow = new WebMarkupContainer("rememberMeRow");
            add(rememberMeRow);
            
            rememberMeRow.add(new CheckBox("rememberMe"));
            
            AjaxSubmitLink signInLink = new AjaxSubmitLink("submit") {
                private static final long serialVersionUID = 1L;
                
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    IAuthenticationStrategy strategy = getApplication().getSecuritySettings().getAuthenticationStrategy();
                    
                    if (signIn(getUsername(), getPassword())){
                        if (rememberMe == true)	{
                            strategy.save(username, password);
                        }else{
                            strategy.remove();
                        }
                        onSignInSucceeded();
                    }else{
                        onSignInFailed();
                        strategy.remove();
                    }
                }
            };
            add(signInLink);
            setDefaultButton(signInLink);
        }
        
        @Override
        protected void onConfigure(){
            if (isSignedIn() == false){
                IAuthenticationStrategy authenticationStrategy = getApplication().getSecuritySettings().getAuthenticationStrategy();
                String[] data = authenticationStrategy.load();
                
                if ((data != null) && (data.length > 1)){
                    if (signIn(data[0], data[1])){
                        username = data[0];
                        password = data[1];
                        
                        onSignInRemembered();
                    }else{
                        authenticationStrategy.remove();
                    }
                }
            }
            super.onConfigure();
        }
    }
    
    private boolean isSignedIn(){
        return AuthenticatedWebSession.get().isSignedIn();
    }
    
    /**
     * Method to redirect to original page if sign in remember option was selected.
     */
    protected void onSignInRemembered(){
        continueToOriginalDestination();
        throw new RestartResponseException(getApplication().getHomePage());
    }
    
    /**
     * Method to perform sign in.
     */
    private boolean signIn(String username, String password){
        return AuthenticatedWebSession.get().signIn(username, password);
    }
    
    /**
     * Method to redirect to original page if sign in is successful.
     */
    protected void onSignInSucceeded(){
        continueToOriginalDestination();
        setResponsePage(getApplication().getHomePage());
    }
    
    /**
     * Method to show error message if sign in is unsuccessful.
     */
    protected void onSignInFailed(){
        error(getLocalizer().getString("signInFailed", this, "Sign in failed"));
    }
    
    public String getPassword(){
        return password;
    }
    
    
    public void setPassword(final String password){
        this.password = password;
    }
    
    
    public String getUsername(){
        return username;
    }
    
    
    public void setUsername(final String username){
        this.username = username;
    }
    
    
    public boolean getRememberMe(){
        return rememberMe;
    }
    
    
    public void setRememberMe(final boolean rememberMe){
        this.rememberMe = rememberMe;
    }
    
    
}


