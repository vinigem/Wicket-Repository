package com.vini.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.request.WebClientInfo;

import com.vini.SurveySession;
import com.vini.util.ClientInfoUtil;
import com.vini.vo.ReportVO;

public class ReportPanel extends Panel {
    private static final long serialVersionUID = 1L;
    
    private String screenshotFilePath;
    
    public ReportPanel(String id, String screenshotFilePath) {
        super(id);
        this.screenshotFilePath = screenshotFilePath;
        
        addPanelContent();
    }
    
    private void addPanelContent() {
        Form<Void> reportForm = new Form<Void>("form");
        
        final TextArea<String> messageBox = new TextArea<String>("message", new Model<String>());
        messageBox.setRequired(true);
        
        reportForm.add(new AjaxSubmitLink("send") {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                String message = messageBox.getModelObject();
                createAndSendReport(message);
                ModalWindow modalWindow = (ModalWindow) ReportPanel.this.getParent();
                modalWindow.close(target);
            }
        });
        
        reportForm.add(messageBox);
        add(new FeedbackPanel("feedback"));
        add(reportForm);
    }
    
    /**
     * Method to create and send report
     * @param message
     */
    private void createAndSendReport(String message) {
        ReportVO report = new ReportVO();
        report.setMessage(message);
        report.setScreenshotFilePath(screenshotFilePath);
        addClientDetails(report);
    }
    
    /**
     * Method to add client details to the report
     * @param report
     */
    private void addClientDetails(ReportVO report) {
        WebClientInfo clientInfo = SurveySession.get().getClientInfo();
        ClientInfoUtil.addClientInfo(clientInfo, report);
    }
    
}
