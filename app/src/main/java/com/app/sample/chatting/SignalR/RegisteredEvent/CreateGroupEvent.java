package com.app.sample.chatting.SignalR.RegisteredEvent;

import android.content.Context;
import android.util.Log;

import com.app.sample.chatting.SignalR.Classes.HubMessage;
import com.app.sample.chatting.SignalR.Interface.HubEventListener;
import com.google.gson.JsonElement;

public class CreateGroupEvent implements HubEventListener {
    private Context context;
    public CreateGroupEvent(Context ctx){
        this.context=ctx.getApplicationContext();
    }
    @Override
    public void onEventMessage(HubMessage message) {
        Log.d("myApp onEventMessage =>", message.getTarget()+"onEventMessage Entering");
        JsonElement[] temp= message.getArguments();
    }
}
