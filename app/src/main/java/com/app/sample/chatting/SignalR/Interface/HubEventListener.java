package com.app.sample.chatting.SignalR.Interface;

import com.app.sample.chatting.SignalR.Classes.HubMessage;

public interface HubEventListener {
    void onEventMessage(HubMessage message);
}
