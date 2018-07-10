package com.app.sample.chatting.SignalR.Interface;

import com.app.sample.chatting.SignalR.Classes.HubMessage;

public interface HubConnectionListener {
    void onConnected();

    void onDisconnected();

    void onMessage(HubMessage message);

    void onError(Exception exception);
}
