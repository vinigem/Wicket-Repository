package com.vini.pages.survey;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class SurveyEnd extends SurveyBasepage {
    
    private static final long serialVersionUID = 1L;
    
    public SurveyEnd() {
        add(new Label("message", new Model<String>("Thank you for taking the survey.")));
    }
    
}
