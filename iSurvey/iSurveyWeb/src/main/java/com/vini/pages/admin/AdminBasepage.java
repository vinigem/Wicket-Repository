package com.vini.pages.admin;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import com.vini.pages.Basepage;
import com.vini.pages.admin.CreateSurvey;
import com.vini.pages.admin.UploadQuestions;

@AuthorizeInstantiation("ADMIN")
public class AdminBasepage extends Basepage {
    
    private static final long serialVersionUID = 1L;
    
    public AdminBasepage() {
        addMenuLinks();
        add(new BookmarkablePageLink<Void>("homeLink", Application.get().getHomePage()));
    }
    
    /**
     * Method to add menu links.
     */
    private void addMenuLinks(){
        add(new BookmarkablePageLink<Void>("createSurvey", CreateSurvey.class));
        add(new BookmarkablePageLink<Void>("uploadQuestions", UploadQuestions.class));
        add(new BookmarkablePageLink<Void>("linkSurvey", LinkSurvey.class));
        add(new BookmarkablePageLink<Void>("viewSummary", ViewSummary.class));
    }
    
    
}
