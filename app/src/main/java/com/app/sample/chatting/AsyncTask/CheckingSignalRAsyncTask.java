package com.app.sample.chatting.AsyncTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.app.sample.chatting.Activity.ActivityMain;
import com.app.sample.chatting.Activity.ActivitySplash;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiver.CheckConnection;
import com.app.sample.chatting.SignalR.Services.SignalRConnectionService;

import java.util.Timer;
import java.util.TimerTask;

public class CheckingSignalRAsyncTask
{

 /*   CheckConnection checkConnection=new CheckConnection();
    private Context context;
    public static boolean SignalRConnectionFlag;

    BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub
            // Get extra data included in the Intent
            boolean message = intent.getBooleanExtra("SignalRConnection",false);
            SignalRConnectionFlag= message;
            Log.d("receiver", "Got message: " + SignalRConnectionFlag);
        }
    };
    public CheckingSignalRAsyncTask(Context ctx) {
        this.context = ctx;
    }

    @Override
    protected Boolean doInBackground(String... strings)
    {
        final int checkconnectionflag=checkConnection.connnectioncheck(context);
        if((true))
        {
            startService();
            TimerTask task=new TimerTask()
            {
                @Override
                public void run()
                {
                    while(true)
                    {
                        if(SignalRConnectionFlag==true)
                        {
                            break;
                        }

                    }

                }
            };
            new Timer().schedule(task, 2000);
            return SignalRConnectionFlag;
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean s)
    {
        super.onPostExecute(s);
        if(s==true){
            Intent i = new Intent(context, ActivityMain.class);
            context.startActivity(i);
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
        public void startService()
        {
            try
            {
                //startService(new Intent(context, SignalRConnectionService.class));
                Intent intent=new Intent();
                intent.setAction(".SignalR.Services.SignalRConnectionService");
                context.startActivity(intent);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }

        // Method to stop the service
        public void stopService()
        {
            try
            {
                //stopService(new Intent(context, SignalRConnectionService.class));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        */
}
