package com.vini.pages.survey;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.pages.AccessDeniedPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.vini.panels.SurveyPanel;
import com.vini.service.ISurveyService;
import com.vini.service.IUserService;
import com.vini.util.SurveyUtil;
import com.vini.vo.SurveyVO;
import com.vini.vo.UserVO;

public class TakeSurvey extends SurveyBasepage {
    private static final long serialVersionUID = 1L;
    
    @SpringBean
    private IUserService userService;
    
    @SpringBean
    private ISurveyService surveyService;
    
    public TakeSurvey(PageParameters parameters) {
        if(!parameters.isEmpty() && !parameters.get("URID").isEmpty()){
            // extract the URID from the url params and decrypt to get registrationId
            long registrationId = SurveyUtil.decryptURID(parameters.get("URID").toString());
            UserVO userVO = userService.getUserByRegistrationId(registrationId);
            
            if(userVO == null){
                throw new RestartResponseException(AccessDeniedPage.class);
            }
            
            SurveyVO surveyVO = surveyService.getSurveyById(userVO.getSurveyId());
            add(new SurveyPanel("surveyPanel", userVO.getUserId(), surveyVO.getSurveyId(), surveyVO.getQuestionSetId()));
        }else{
            throw new RestartResponseException(AccessDeniedPage.class);
        }
    }
    
}
