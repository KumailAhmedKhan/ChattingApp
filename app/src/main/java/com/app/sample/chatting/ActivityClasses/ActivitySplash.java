package com.app.sample.chatting.ActivityClasses;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.app.sample.chatting.AsyncTaskClasses.UserNotificationAsyncTask;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.Services.SignalRConnectionService;


public class ActivitySplash extends AppCompatActivity
{
    UserNotificationAsyncTask userNotificationAsyncTask=new UserNotificationAsyncTask();
    private BroadcastReceiver broadcastReceiver;
    Context context;
    public static boolean SignalRConnectionFlag;
    CheckConnection checkConnection=new CheckConnection();
    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
    public String tempo="99";
    public int temporary=99;
    public static final String CUSTOM_INTENT = "com.app.sample.chatting.SignalR.Classes";
    //CheckingSignalRAsyncTask checkingSignalRAsyncTask=new CheckingSignalRAsyncTask(this);

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
            Intent i = new Intent(ActivitySplash.this, ActivityMain.class);
            startActivity(i);
        }
    };



    public ActivitySplash(Context ctx) {
        context = ctx.getApplicationContext();
    }

    public ActivitySplash() {
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final CoordinatorLayout coordinatorLayout=(CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);

            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                    mMessageReceiver, new IntentFilter("NewActivity"));
            broadcastReceiver=new CheckConnection();
            //registerNetworkBroadcast();

            final int checkconnectionflag=checkConnection.connnectioncheck(getApplicationContext());
            bindLogo();
            tempo = temp.getPreferences(getApplicationContext(), "Logindata");
            if(tempo.equals("99")|| tempo.isEmpty())
            {

                    tempo="99";
                    Intent i = new Intent(ActivitySplash.this, ActivityLogin.class);
                    startActivity(i);
                    finish();


            }
            temporary = Integer.parseInt(tempo);
            if (temporary == 100)
            {
                if((checkconnectionflag==1))
                {
                   /* if(isServiceRunning(SignalRConnectionService.class)==false){
                        startService();
                    }*/
                    startService();
                    //checkingSignalRAsyncTask=(CheckingSignalRAsyncTask)new CheckingSignalRAsyncTask(getApplicationContext()).execute();
                    userNotificationAsyncTask=(UserNotificationAsyncTask) new UserNotificationAsyncTask(getApplicationContext()).execute();
                       // TimerTask task = new TimerTask()
                        {
                            //@Override
                           // public void run()
                            {
                               // while(true)
                                {
                                    //if(SignalRConnectionFlag==true)
                                    {
                                        Intent i = new Intent(ActivitySplash.this, ActivityMain.class);
                                        startActivity(i);
                                        //break;
                                    }
                                    //else
                                    {
                                        //Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please Wait", Snackbar.LENGTH_INDEFINITE);
                                       // snackbar.show();
                                    }
                                }

                            }

                        };

                    //new Timer().schedule(task, 2000);

                }

                else
                {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Internet Not Connected or Have Some Problem With Server Response", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                Intent intent=new Intent(ActivitySplash.this,ActivitySplash.class);
                                startActivity(intent);

                            }
                        });

                snackbar.show();
            }
            }


    }



    public void startService()
    {
        try
        {
            startService(new Intent(getApplicationContext(), SignalRConnectionService.class));
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
            stopService(new Intent(getApplicationContext(), SignalRConnectionService.class));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    /*
    private boolean isServiceRunning(Class<?> serviceClass) {
        final String serviceName="SignalR.Services.SignalRConnectionService";
        ActivityManager manager = (ActivityManager) getSystemService(serviceName);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(service.service.getClassName()))
            {
                return true;
            }
        }
        return true;
    }*/
    private void bindLogo()
    {
         // Start animating the image
            final ImageView splash = (ImageView) findViewById(R.id.splash);
            final AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
            animation1.setDuration(700);
            final AlphaAnimation animation2 = new AlphaAnimation(1.0f, 0.2f);
            animation2.setDuration(700);
            //animation1 AnimationListener
            animation1.setAnimationListener(new Animation.AnimationListener()
            {
                @Override
                public void onAnimationEnd(Animation arg0)
                {
                    // start animation2 when animation1 ends (continue)
                    splash.startAnimation(animation2);
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {}
                @Override
                public void onAnimationStart(Animation arg0) {}
            });

            //animation2 AnimationListener
            animation2.setAnimationListener(new Animation.AnimationListener()
            {
                @Override
                public void onAnimationEnd(Animation arg0)
                {
                    // start animation1 when animation2 ends (repeat)
                    splash.startAnimation(animation1);
                }
                @Override
                public void onAnimationRepeat(Animation arg0) {}
                @Override
                public void onAnimationStart(Animation arg0) {}
            });

            splash.startAnimation(animation1);


    }
    private void registerNetworkBroadcast()
    {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {
                registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }


    }
    protected void unregisterNetworkChanges()
    {
        try
        {
            unregisterReceiver(broadcastReceiver);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }

        //unregisterReceiver(broadcastReceiver);

    }


    @Override
    protected void onDestroy() {
        unregisterNetworkChanges();
        super.onDestroy();
    }

}
/*
 TimerTask task = new TimerTask()
                        {


                            @Override
                            public void run()
                            {
                                //while(true)
                                {
                                    if(checkconnectionflag==1)
                                    // (SignalRConnectionFlag==true)
                                    {
                                        Intent i = new Intent(ActivitySplash.this, ActivityMain.class);
                                        startActivity(i);
                                        //break;
                                    }
                                    else {
                                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please Wait", Snackbar.LENGTH_INDEFINITE);
                                                snackbar.show();
                                    }
                                }

                            }
                        };

                    new Timer().schedule(task, 1000);
 */

/*
final Handler handler = new Handler();
final Runnable uiRunnable = new Runnable() {
	@Override
	public void run() {
	 while(true)
                                    {
                                        //(checkconnectionflag==1)
                                        if(SignalRConnectionFlag==true)
                                        {
                                            Intent i = new Intent(ActivitySplash.this, ActivityMain.class);
                                            startActivity(i);
                                            break;
                                        }
                                        else {
                                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please Wait", Snackbar.LENGTH_INDEFINITE);
                                            snackbar.show();
                                        }
                                    }
                                }
		 //mTextView.setText(mResult);
	}
};
 */