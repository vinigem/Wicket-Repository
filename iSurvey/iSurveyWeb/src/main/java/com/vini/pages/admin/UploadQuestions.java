package com.vini.pages.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadProgressBar;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vini.SurveySession;
import com.vini.components.FeedbackAutoCompleteTextField;
import com.vini.components.FeedbackFileUploadField;
import com.vini.constants.SurveyConstant;
import com.vini.exceptions.ExcelUploadException;
import com.vini.notification.NotificationType;
import com.vini.service.IQuestionSetService;
import com.vini.util.ExcelUtil;
import com.vini.vo.QuestionSetVO;

public class UploadQuestions extends AdminBasepage {
    
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadQuestions.class);
    
    @SpringBean
    private IQuestionSetService questionSetService;
    
    public UploadQuestions() {
        final Form<QuestionSetVO> questionSetForm = new Form<QuestionSetVO>("form");
        questionSetForm.setOutputMarkupId(true);
        
        // set this form to multipart mode (always needed for uploads!)
        questionSetForm.setMultiPart(true);
        questionSetForm.setMaxSize(Bytes.megabytes(2));
        
        final FeedbackAutoCompleteTextField<String> questionSetName = new FeedbackAutoCompleteTextField<String>(SurveyConstant.QUESTION_SET_NAME, new Model<String>()){
            
            private static final long serialVersionUID = 1L;
            
            @Override
            protected Iterator<String> getChoices(String input) {
                if (Strings.isEmpty(input)){
                    List<String> emptyList = Collections.emptyList();
                    return emptyList.iterator();
                }
                
                List<String> questionSetNames = questionSetService.getQuestionSetNames(input);
                return questionSetNames.iterator();
            }
            
        };
        questionSetName.setRequired(true);
        questionSetName.setOutputMarkupId(true);
        
        DownloadLink template = new DownloadLink("template", new AbstractReadOnlyModel<File>() {
            private static final long serialVersionUID = 1L;
            
            @Override
            public File getObject() {
                return getTemplate();
            }
        });
        
        final FeedbackFileUploadField fileUpload= new FeedbackFileUploadField("upload");
        fileUpload.setRequired(true);
        
        final AjaxSubmitLink submit = new AjaxSubmitLink("submit") {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                final List<FileUpload> uploads = fileUpload.getFileUploads();
                if (uploads != null){
                    for (FileUpload upload : uploads){
                        try {
                            List<String> questions = ExcelUtil.extractQuestions(upload.getInputStream());
                            SurveySession.get().setSessionAttribute("questions", questions);
                            SurveySession.get().setSessionAttribute(SurveyConstant.QUESTION_SET_NAME, questionSetName.getModelObject());
                            setResponsePage(Questions.class);
                        } catch (ExcelUploadException e) {
                            LOGGER.error("{}. {}", getString(e.getErrorCode()), e.getCause());
                            broadcast(NotificationType.ERROR, getString(e.getErrorCode()));
                        } catch (IOException e) {
                            LOGGER.error("Error while uploading questions. {}", e);
                            broadcast(NotificationType.ERROR, e.getMessage());
                        }
                    }
                }
            }
            
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(form);
            }
        };
        submit.setOutputMarkupId(true);
        
        final AjaxLink<Void> viewQuestions  = new AjaxLink<Void>("view") {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void onClick(AjaxRequestTarget target) {
                setResponsePage(Questions.class);
            }
        };
        viewQuestions.setVisible(false);
        viewQuestions.setOutputMarkupId(true);
        
        questionSetName.add(new AjaxFormComponentUpdatingBehavior(SurveyConstant.ON_CHANGE) {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                String quesSetName = questionSetName.getModelObject();
                List<String> questions = questionSetService.getQuestionsBySetName(quesSetName);
                if(!questions.isEmpty()){
                    SurveySession.get().setSessionAttribute("questions", questions);
                    SurveySession.get().setSessionAttribute(SurveyConstant.QUESTION_SET_NAME, questionSetName.getModelObject());
                    submit.setVisible(false);
                    viewQuestions.setVisible(true);
                }else{
                    submit.setVisible(true);
                    viewQuestions.setVisible(false);
                }
                target.add(questionSetForm);
            }
        });
        
        questionSetForm.add(new UploadProgressBar("progress", questionSetForm, fileUpload));
        questionSetForm.add(questionSetName);
        questionSetForm.add(fileUpload);
        questionSetForm.add(submit);
        questionSetForm.add(viewQuestions);
        add(questionSetForm);
        add(template);
        
    }
    
    /**
     * Method to retrieve question template
     * @return
     */
    protected File getTemplate() {
        File tempFile = new File("QuestionSet_Template.xlsx");
        FileOutputStream fout = null;
        InputStream inStream = null;
        try {
            inStream = this.getClass().getResourceAsStream("/template/QuestionSet_Template.xlsx");
            fout = new FileOutputStream(tempFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inStream.read(buf)) != -1) {
                fout.write(buf, 0, len);
            }
            inStream.close();
            fout.close();
        } catch (FileNotFoundException e) {
            LOGGER.error("Error while uploading questions. {}", e);
            broadcast(NotificationType.ERROR, e.getMessage());
        } catch (IOException e) {
            LOGGER.error("Error while uploading questions. {}", e);
            broadcast(NotificationType.ERROR, e.getMessage());
        }
        return tempFile;
    }
    
}
