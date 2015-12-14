package com.vini.panels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.ws.api.WebSocketBehavior;
import org.apache.wicket.protocol.ws.api.WebSocketRequestHandler;
import org.apache.wicket.protocol.ws.api.event.WebSocketPushPayload;
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vini.notification.NotificationItem;
import com.vini.notification.NotificationType;

public class NotificationPanel extends Panel{
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationPanel.class);
    
    private final HashSet<NotificationItem> validationMessages = new HashSet<NotificationItem>();
    private NotificationItem infoMessage;
    private NotificationItem successMessage;
    private NotificationItem warningMessage;
    private NotificationItem errorMessage;
    
    private WebMarkupContainer validationContainer;
    private WebMarkupContainer notificationContainer;
    
    public NotificationPanel(String id) {
        super(id);
        
        setDefaultModel(new CompoundPropertyModel<NotificationPanel>(this));
        
        validationContainer = new WebMarkupContainer("validationContainer");
        add(validationContainer.setOutputMarkupId(true));
        
        WebMarkupContainer validationMessageContainer = new WebMarkupContainer("validationMessageContainer"){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onConfigure() {
                setVisible(!validationMessages.isEmpty());
            }
        };
        
        validationMessageContainer.add(new AjaxLink<Void>("validationClose") {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void onClick(AjaxRequestTarget target) {
                validationMessages.clear();
                target.add(validationContainer);
            }
        });
        
        validationContainer.add(validationMessageContainer);
        
        notificationContainer = new WebMarkupContainer("notificationContainer");
        notificationContainer.add(new AjaxLink<Void>("notificationClose") {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void onClick(AjaxRequestTarget target) {
                clearNotifications();
                target.add(notificationContainer);
            }
            
            @Override
            protected void onConfigure() {
                setVisible(infoMessage != null || successMessage != null || warningMessage != null || errorMessage != null);
            }
        });
        add(notificationContainer.setOutputMarkupId(true));
        
        Label info = new Label("infoMessage", new PropertyModel<String>(this, "infoMessage.message")){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onConfigure() {
                setVisible(infoMessage != null);
            }
        };
        info.setOutputMarkupId(true);
        
        Label success = new Label("successMessage", new PropertyModel<String>(this, "successMessage.message")){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onConfigure() {
                setVisible(successMessage != null);
            }
        };
        success.setOutputMarkupId(true);
        
        Label warning = new Label("warningMessage", new PropertyModel<String>(this, "warningMessage.message")){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onConfigure() {
                setVisible(warningMessage != null);
            }
        };
        warning.setOutputMarkupId(true);
        
        Label error = new Label("errorMessage", new PropertyModel<String>(this, "errorMessage.message")){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onConfigure() {
                setVisible(errorMessage != null);
            }
        };
        error.setOutputMarkupId(true);
        
        notificationContainer.add(info);
        notificationContainer.add(success);
        notificationContainer.add(warning);
        notificationContainer.add(error);
        
        IModel<List<NotificationItem>> validationModel = new PropertyModel<List<NotificationItem>>(this, "validationMessages"){
            private static final long serialVersionUID = 1L;
            
            @Override
            public List<NotificationItem> getObject() {
                return new ArrayList<NotificationItem>(validationMessages);
            };
        };
        
        validationMessageContainer.add(new ListView<NotificationItem>("validationMessages", validationModel) {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void populateItem(ListItem<NotificationItem> item) {
                item.setDefaultModel(CompoundPropertyModel.of(item.getDefaultModel()));
                item.add(new Label("validationMessage", item.getModelObject().getMessage()));
            }
        });
        
        add(new WebSocketBehavior() {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onConnect(ConnectedMessage message) {
                super.onConnect(message);
                LOGGER.info("Client connected");
            }
            
            @Override
            public void onException(Component component, RuntimeException exception) {
                LOGGER.warn("Got exception", exception);
            }
        });
    }
    
    /**
     * Method to clear notifications
     */
    private void clearNotifications() {
        setInfoMessage(null);
        setSuccessMessage(null);
        setWarningMessage(null);
        setErrorMessage(null);
    }
    
    
    @Override
    public void onEvent(IEvent<?> event) {
        if (event.getPayload() instanceof WebSocketPushPayload) {
            WebSocketPushPayload wsEvent = (WebSocketPushPayload) event.getPayload();
            handleMessage(wsEvent.getHandler(),  (NotificationItem) wsEvent.getMessage());
        }
    }
    
    /**
     * Method to handle push messages
     * @param handler
     * @param notificationItem
     */
    public void handleMessage(WebSocketRequestHandler handler, NotificationItem notificationItem) {
        pushMessage(notificationItem);
        handler.add(validationContainer);
        handler.add(notificationContainer);
    }
    
    /**
     * Method to set push message as per their type
     * @param notificationItem
     */
    private void pushMessage(NotificationItem notificationItem) {
        if(NotificationType.VALIDATION == notificationItem.getNotificationType()){
            this.validationMessages.add(notificationItem);
            validationContainer.modelChanged();
            
        }else if(NotificationType.INFO == notificationItem.getNotificationType()){
            setInfoMessage(notificationItem);
            notificationContainer.modelChanged();
            
        }else if(NotificationType.SUCCESS == notificationItem.getNotificationType()){
            setSuccessMessage(notificationItem);
            notificationContainer.modelChanged();
            
        }else if(NotificationType.WARNING == notificationItem.getNotificationType()){
            setWarningMessage(notificationItem);
            notificationContainer.modelChanged();
            
        }else if(NotificationType.ERROR == notificationItem.getNotificationType()){
            setErrorMessage(notificationItem);
            notificationContainer.modelChanged();
        }
    }
    
    
    public Set<NotificationItem> getValidationMessages() {
        return validationMessages;
    }
    
    public NotificationItem getInfoMessage() {
        return infoMessage;
    }
    
    
    public void setInfoMessage(NotificationItem infoMessage) {
        this.infoMessage = infoMessage;
    }
    
    
    public NotificationItem getSuccessMessage() {
        return successMessage;
    }
    
    
    public void setSuccessMessage(NotificationItem successMessage) {
        this.successMessage = successMessage;
    }
    
    
    public NotificationItem getWarningMessage() {
        return warningMessage;
    }
    
    
    public void setWarningMessage(NotificationItem warningMessage) {
        this.warningMessage = warningMessage;
    }
    
    
    public NotificationItem getErrorMessage() {
        return errorMessage;
    }
    
    
    public void setErrorMessage(NotificationItem errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}
