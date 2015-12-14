package com.vini.components;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.ws.IWebSocketSettings;
import org.apache.wicket.protocol.ws.api.WebSocketPushBroadcaster;

public interface INotificationFeedback {
    
    IWebSocketSettings webSocketSettings = IWebSocketSettings.Holder.get(Application.get());
    WebSocketPushBroadcaster broadcaster = new WebSocketPushBroadcaster(webSocketSettings.getConnectionRegistry());
    
    void broadcast(String message);
    
}
