package com.vini;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.core.util.file.WebApplicationPath;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.AccessDeniedPage;
import org.apache.wicket.markup.html.pages.InternalErrorPage;
import org.apache.wicket.markup.html.pages.PageExpiredErrorPage;
import org.apache.wicket.protocol.ws.IWebSocketSettings;
import org.apache.wicket.protocol.ws.api.WebSocketPushBroadcaster;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.file.Folder;

import com.vini.pages.admin.CreateSurvey;
import com.vini.pages.admin.LinkSurvey;
import com.vini.pages.admin.Questions;
import com.vini.pages.admin.SignIn;
import com.vini.pages.admin.UploadQuestions;
import com.vini.pages.admin.ViewSummary;
import com.vini.pages.survey.SurveyEnd;
import com.vini.pages.survey.TakeSurvey;
import com.vini.security.SurveyAuthorizationStrategy;

public class SurveyApplication extends AuthenticatedWebApplication {
    
    private Folder uploadFolder = null;
    private WebSocketPushBroadcaster broadcaster;
    
    @Override
    public Class<? extends Page> getHomePage() {
        return CreateSurvey.class;
    }
    
    @Override
    protected void init() {
        // For static resources
        getResourceSettings().getResourceFinders().add(new WebApplicationPath(getServletContext(), "html"));
        getResourceSettings().getResourceFinders().add(new WebApplicationPath(getServletContext(), "properties"));
        
        // For spring integration
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        
        mountPages();
        initExceptionPages();
        getDebugSettings().setDevelopmentUtilitiesEnabled(true);
        
        // Create file upload folder
        uploadFolder = new Folder(System.getProperty("java.io.tmpdir"), "iSurvey-uploads");
        uploadFolder.mkdirs();
        
        getMarkupSettings().setDefaultAfterDisabledLink(null);
        getMarkupSettings().setDefaultBeforeDisabledLink(null);
        getMarkupSettings().setCompressWhitespace(true);
        
        // Set authorization strategy
        getSecuritySettings().setAuthorizationStrategy(new SurveyAuthorizationStrategy());
        
        // Sets whether wicket should provide updates about the upload progress or not.
        getApplicationSettings().setUploadProgressUpdatesEnabled(true);
        
        // To get client browser details
        getRequestCycleSettings().setGatherExtendedBrowserInfo(true);
        
        // init Push Message Broadcaster
        initPushMessageBroadcaster();
    }
    
    /**
     * Method to initialize push message broadcaster
     */
    private void initPushMessageBroadcaster() {
        IWebSocketSettings webSocketSettings = IWebSocketSettings.Holder.get(this);
        setBroadcaster(new WebSocketPushBroadcaster(webSocketSettings.getConnectionRegistry()));
        
    }
    
    /**
     * Method to initialize exception pages
     */
    private void initExceptionPages() {
        getApplicationSettings().setAccessDeniedPage(AccessDeniedPage.class);
        getApplicationSettings().setPageExpiredErrorPage(PageExpiredErrorPage.class);
        getApplicationSettings().setInternalErrorPage(InternalErrorPage.class);
    }
    
    /**
     * Methods to mount page to a url
     */
    private void mountPages() {
        mountPage("/Admin/SignIn", SignIn.class);
        mountPage("/Admin/CreateSurvey", CreateSurvey.class);
        mountPage("/Admin/LinkSurvey", LinkSurvey.class);
        mountPage("/Admin/UploadQuestions", UploadQuestions.class);
        mountPage("/Admin/ViewQuestions", Questions.class);
        mountPage("/Admin/ViewSummary", ViewSummary.class);
        mountPage("/Survey/TakeSurvey/${URID}", TakeSurvey.class);
        mountPage("/Survey/SurveyEnd", SurveyEnd.class);
    }
    
    public Folder getUploadFolder() {
        return uploadFolder;
    }
    
    @Override
    public Session newSession(Request request, Response response) {
        return new SurveySession(request);
    }
    
    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return SignIn.class;
    }
    
    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return SurveySession.class;
    }
    
    public WebSocketPushBroadcaster getBroadcaster() {
        return broadcaster;
    }
    
    public void setBroadcaster(WebSocketPushBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }
    
    
}


