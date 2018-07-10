package com.app.sample.chatting.SignalR.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.app.sample.chatting.Activity.ActivityMain;
import com.app.sample.chatting.AsyncTask.UserNotificationAsyncTask;
import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.Classes.HubMessage;
import com.app.sample.chatting.SignalR.Classes.WebSocketHubConnection;
import com.app.sample.chatting.SignalR.Interface.HubConnection;
import com.app.sample.chatting.SignalR.Interface.HubConnectionListener;
import com.app.sample.chatting.SignalR.Interface.HubEventListener;
import com.app.sample.chatting.SignalR.RegisteredEvent.CancelFriendRequestEvent;
import com.app.sample.chatting.SignalR.RegisteredEvent.ConfirmFriendRequestEvent;
import com.app.sample.chatting.SignalR.RegisteredEvent.ConnectEvent;
import com.app.sample.chatting.SignalR.RegisteredEvent.FriendsNowEvent;
import com.app.sample.chatting.SignalR.RegisteredEvent.SendFriendRequestEvent;
import com.app.sample.chatting.SignalR.RegisteredEvent.UseronlineEvent;
import com.app.sample.chatting.SignalR.RegisteredEvent.DisconnectEvent;
import com.app.sample.chatting.SignalR.RegisteredEvent.GetOnlineUserEvent;
import com.app.sample.chatting.SignalR.RegisteredEvent.SendMessageToFriendEvent;
import com.app.sample.chatting.SignalR.RegisteredEvent.SendMessageToGroupEvent;

import java.util.Timer;
import java.util.TimerTask;
import com.app.sample.chatting.URL.httpUrl;

public class SignalRConnectionService extends Service implements HubConnectionListener,HubEventListener {



    public Context context;
    public static boolean SignalRConnectionFlag;
    TemporaryStorageSharedPreferences temp=new TemporaryStorageSharedPreferences() ;
    static httpUrl httpUrl=new httpUrl();
    private String authHeader = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6Ijc5NzhjMjI3LWViMGItNGMwOS1iYWEyLTEwYmE0MjI4YWE4OSIsImNlcnRzZXJpYWxudW1iZXIiOiJtYWNfYWRkcmVzc19vZl9waG9uZSIsInNlY3VyaXR5U3RhbXAiOiJlMTAxOWNiYy1jMjM2LTQ0ZTEtYjdjYy0zNjMxYTYxYzMxYmIiLCJuYmYiOjE1MDYyODQ4NzMsImV4cCI6NDY2MTk1ODQ3MywiaWF0IjoxNTA2Mjg0ODczLCJpc3MiOiJCbGVuZCIsImF1ZCI6IkJsZW5kIn0.QUh241IB7g3axLcfmKR2899Kt1xrTInwT6BBszf6aP4";
    private HubConnection connection=new WebSocketHubConnection(httpUrl.getHubURl(), "", this);;



    public SignalRConnectionService(Context context) {
        this.context = context;
    }

    public SignalRConnectionService() {
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        this.context = getApplicationContext();

        //checkingSignalRAsyncTask=(CheckingSignalRAsyncTask)new CheckingSignalRAsyncTask().execute();

        ///////////////////////////////add Listener to connection////////////////////////////
        connection.addListener(this);
        ////////////////////////////////intializing registered  Event////////////////////////
        ConnectEvent connectEvent = new ConnectEvent(this);
        final UseronlineEvent useronlineEvent = new UseronlineEvent(this);
        DisconnectEvent disconnectEvent = new DisconnectEvent(this);
        SendMessageToFriendEvent sendMessage = new SendMessageToFriendEvent(this);
        GetOnlineUserEvent getOnlineUserEvent = new GetOnlineUserEvent(this);
        SendMessageToGroupEvent sendMessageToGroupEvent = new SendMessageToGroupEvent(this);
        //SendFriendRequestEvent sendFriendRequestEvent=new SendFriendRequestEvent(this);
        CancelFriendRequestEvent cancelFriendRequestEvent = new CancelFriendRequestEvent(this);
        ConfirmFriendRequestEvent confirmFriendRequestEvent = new ConfirmFriendRequestEvent(this);
        //FriendsNowEvent friendsNowEvent=new FriendsNowEvent(this);
        ////////////////////////////////////Subscribe to Event/////////////////////////////////
        connection.subscribeToEvent("receivegroupmsg", sendMessageToGroupEvent);
        connection.subscribeToEvent("receivemsg", sendMessage);
        connection.subscribeToEvent("useronline", useronlineEvent);
        connection.subscribeToEvent("ondisconnect", disconnectEvent);
        connection.subscribeToEvent("getonlineusers", getOnlineUserEvent);
        connection.subscribeToEvent("connect", connectEvent);
        connection.subscribeToEvent("removeNotification", cancelFriendRequestEvent);
        connection.subscribeToEvent("receiveNotification", confirmFriendRequestEvent);

        //new CheckingSignalRAsyncTask().execute();
/*
        connection.connect();
        /*
        while (true) {


            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (SignalRConnectionFlag == true) {
                        //String UserId = temp.getPreferences(context, "Userdata");
                        //////////////////////////////////start signalr Asyntask//////////////////////////////
                       // connection.invoke("onConnectAsync", UserId);
                        // Intent i = new Intent(context, ActivityMain.class);
                        // context.startActivity(i);
                       // Intent intent1 = new Intent("NewActivity");
                       // intent1.putExtra("SignalR", SignalRConnectionFlag);
                      //  LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);

                    }

                }
            };

            new Timer().schedule(task, 2000);
            break;
        }


        BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                // TODO Auto-generated method stub
                // Get extra data included in the Intent
                boolean message = intent.getBooleanExtra("SignalR",false);
                SignalRConnectionFlag= message;
                Log.d("receiver", "Got message: " + SignalRConnectionFlag);
                if(SignalRConnectionFlag)
                {
                    String UserId = temp.getPreferences(context, "Userdata");
                    connection.invoke("onConnectAsync", UserId);
                    Intent intent1 = new Intent("NewActivity");
                    intent1.putExtra("SignalR", SignalRConnectionFlag);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
                }
            }
        };
        LocalBroadcastManager.getInstance(context).registerReceiver(
                mMessageReceiver, new IntentFilter("SignalRConnection"));
*//**/
        connection.connect();
        BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                // TODO Auto-generated method stub
                // Get extra data included in the Intent
                boolean message = intent.getBooleanExtra("SignalR",false);
                SignalRConnectionFlag= message;
                Log.d("receiver", "Got message: " + SignalRConnectionFlag);
            }
        };
        LocalBroadcastManager.getInstance(context).registerReceiver(
                mMessageReceiver, new IntentFilter("SignalRConnection"));
        //addNotification();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
               // while (true)
                {

                    if (SignalRConnectionFlag == true) {
                        String UserId = temp.getPreferences(context, "Userdata");
                        //////////////////////////////////start signalr Asyntask//////////////////////////////
                        connection.invoke("onConnectAsync", UserId);
                       // break;
                    }
                }
            } };
        // Show splash screen for 1 seconds
        new Timer().schedule(task, 7000);

        return  super.onStartCommand(intent,flags,startId);

        /////////////////////////////////////////connect to Connection///////////////////////////






    }






    @Override
    public void onDestroy() {

        super.onDestroy();
        connection.disconnect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //Intent intent = new Intent(this, SignalRConnectionService.class);
        //bindService(intent, m_serviceConnection, BIND_AUTO_CREATE);

        return null;
    }

    @Override
    public void onEventMessage(HubMessage message) {
        Log.d("myApp onEventMessage =>", "onEventMessage Entering");
    }

    @Override
    public void onConnected() {
       // connection.connect();
        Log.d("myApp onConnected =>", "onConnected Entering");
    }

    @Override
    public void onDisconnected() {
        Log.d("myApp onDisconnected =>", "onDisconnected Entering");
    }

    @Override
    public void onMessage(HubMessage message) {
        Log.d("myApp onMessage =>", message.getTarget().toString());
    }
    @Override
    public void onError(Exception exception) {
        Log.d("myApp onError =>", "onError Entering -- "+exception.getMessage());
    }
    /*
    public class CheckingSignalRAsyncTask extends AsyncTask<String,String,Boolean>{

        @Override
        protected Boolean doInBackground(String... strings)
        {
            connection.connect();
                    while(true)
                    {
                        TimerTask task=new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                if (SignalRConnectionFlag == true)
                                {
                                    String UserId = temp.getPreferences(context, "Userdata");
                                    //////////////////////////////////start signalr Asyntask//////////////////////////////
                                    connection.invoke("onConnectAsync", UserId);
                                    Intent i = new Intent(context, ActivityMain.class);
                                    context.startActivity(i);

                                }
                            }


                        };
                        new Timer().schedule(task, 2000);
                        break;
                    }


           return SignalRConnectionFlag;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean==true)
            {
                Intent i = new Intent(context, ActivityMain.class);
                context.startActivity(i);
            }
        }

    }
    */
 }



