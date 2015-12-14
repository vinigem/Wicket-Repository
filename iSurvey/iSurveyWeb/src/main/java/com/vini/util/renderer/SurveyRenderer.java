package com.vini.util.renderer;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

import com.vini.vo.SurveyVO;

public class SurveyRenderer implements IChoiceRenderer<SurveyVO> {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public Object getDisplayValue(SurveyVO surveyVO) {
        return surveyVO.getSurveyName();
    }
    
    @Override
    public String getIdValue(SurveyVO surveyVO, int index) {
        return String.valueOf(surveyVO.getSurveyId());
    }
    
}
