package com.vini.notification;

import java.io.Serializable;
import org.apache.wicket.protocol.ws.api.message.IWebSocketPushMessage;

public class NotificationItem implements IWebSocketPushMessage, Serializable {
    private static final long serialVersionUID = 1L;
    
    private String message;
    private long validity;
    private NotificationType notificationType;
    
    public NotificationItem(NotificationType notificationType, String message){
        this.setMessage(message);
        this.setNotificationType(notificationType);
        setValidity();
    }
    
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public long getValidity() {
        return validity;
    }
    
    public void setValidity(long validity) {
        this.validity = validity;
    }
    
    public NotificationType getNotificationType() {
        return notificationType;
    }
    
    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
    
    /**
     * Method to set default validity of the push messages
     */
    private void setValidity() {
        if(NotificationType.VALIDATION == this.notificationType){
            this.setValidity(System.currentTimeMillis() + (15 * 1000 )); // 15 seconds
            
        }else if(NotificationType.ERROR == this.notificationType){
            this.setValidity(System.currentTimeMillis() + (10 * 1000 )); // 10 seconds
            
        }else{
            this.setValidity(System.currentTimeMillis() + (5 * 1000 )); // 5 seconds
        }
    }
    
    @Override
    public String toString() {
        return "NotificationItem [message=" + message + ", validity=" + validity + ", notificationType="
                + notificationType + "]";
    }
    
    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            NotificationItem notificationItem = (NotificationItem) object;
            if (this.message.equals(notificationItem.getMessage())) {
                result = true;
            }
        }
        return result;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 7 * hash + this.message.hashCode();
        return hash;
    }
    
    
    
}
