package com.vini.util.renderer;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

import com.vini.ec.QuestionSet;

public class QuestionSetRenderer implements IChoiceRenderer<QuestionSet> {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public Object getDisplayValue(QuestionSet questionSet) {
        return questionSet.getQuestionSetName();
    }
    
    @Override
    public String getIdValue(QuestionSet questionSet, int index) {
        return String.valueOf(questionSet.getQuestionSetId());
    }
    
    
    
}
