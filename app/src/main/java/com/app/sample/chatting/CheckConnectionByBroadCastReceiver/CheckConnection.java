package com.app.sample.chatting.CheckConnectionByBroadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.app.sample.chatting.SignalR.Services.SignalRConnectionService;

public class CheckConnection extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try
        {
            if (isOnline(context))
            {
                //Toast.makeText(context,"Connection Established",Toast.LENGTH_LONG).show();
            }
            else {

                Toast.makeText(context,"Internet Not Connected",Toast.LENGTH_LONG).show();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public int connnectioncheck(Context context )
    {
        if(isOnline(context))
        {
                 return 1;
        }
        else {
                 return 0;
        }
    }


}
