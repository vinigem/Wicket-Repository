package com.vini.pages;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.protocol.ws.api.WebSocketPushBroadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.jwicket.ui.datepicker.DatePicker;

import com.vini.SurveyApplication;
import com.vini.notification.NotificationItem;
import com.vini.notification.NotificationType;
import com.vini.panels.NotificationPanel;
import com.vini.panels.ReportPanel;

public class Basepage extends WebPage implements IAjaxIndicatorAware{
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Basepage.class);
    protected ModalWindow reportWindow;
    
    
    public Basepage() {
        addReportLink();
        add(getReportWindow());
        add(new NotificationPanel("notification"));
    }
    
    /**
     * Method to add Report Link
     */
    private void addReportLink() {
        add(new AjaxLink<Void>("report") {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void onClick(AjaxRequestTarget target) {
                try {
                    String screenshotFilePath = captureScreen(System.getProperty("java.io.tmpdir"), "JPG");
                    reportWindow.addOrReplace(new ReportPanel(reportWindow.getContentId(), screenshotFilePath));
                    reportWindow.show(target);
                    
                } catch (AWTException e) {
                    LOGGER.error("Error While capturing screenshot. {}", e);
                } catch (IOException e) {
                    LOGGER.error("Error While creating screenshot file. {}", e);
                }
            }
        });
        
    }
    
    public WebSocketPushBroadcaster getBroadcaster() {
        return ((SurveyApplication)Application.get()).getBroadcaster();
    }
    
    public void broadcast(NotificationType notificationType, String message){
        getBroadcaster().broadcastAll(Application.get(), new NotificationItem(notificationType, message));
    }
    
    
    /**
     * Method to create Report Modal Window
     * @return report modal window
     */
    private ModalWindow getReportWindow() {
        reportWindow = new ModalWindow("reportWindow");
        reportWindow.setTitle("Report");
        reportWindow.setInitialHeight(250);
        reportWindow.setInitialWidth(1200);
        reportWindow.setContent(new EmptyPanel(reportWindow.getContentId()));
        reportWindow.setResizable(false);
        return reportWindow;
    }
    
    /**
     * Method to add css files
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(CssReferenceHeaderItem.forUrl("css/style.css")); 
        response.render(CssReferenceHeaderItem.forUrl("css/menu.css")); 
        response.render(CssReferenceHeaderItem.forUrl("css/form.css")); 
        response.render(CssReferenceHeaderItem.forUrl("css/wicket.css")); 
    }
    
    /**
     * Method to create datepicker object
     * @return datepicker
     */
    protected DatePicker getDatePicker() {
        DatePicker datePicker= new DatePicker();
        datePicker.setDateFormat("dd-mm-yy");
        datePicker.setChangeYear(true);
        datePicker.setChangeMonth(true);
        datePicker.setYearRange("-0:+5");
        return datePicker;
    }
    
    /**
     * Method to capture Screenshot in supplied image format
     * @param path
     * @param format
     * @return Screenshot file path
     * @throws AWTException 
     * @throws IOException 
     */
    private String captureScreen(String path, String format) throws AWTException, IOException {
        String fileName = "Report_"+ new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
        File screenshot = new File(path+"/"+fileName+"."+format.toString().toLowerCase());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screen = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screen);
        ImageIO.write(image, format.toString(), screenshot);
        return screenshot.getPath();
    }
    
    
    @Override
    public String getAjaxIndicatorMarkupId() {
        return "";
    }
    
    
}
