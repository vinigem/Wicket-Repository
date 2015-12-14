package com.vini.pages.survey;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.vini.pages.Basepage;

@AuthorizeInstantiation("USER")
public class SurveyBasepage extends Basepage {
    private static final long serialVersionUID = 1L;
    
    
    public SurveyBasepage() {
        super();
    }
    
}
