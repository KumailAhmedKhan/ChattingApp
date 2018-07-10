package com.app.sample.chatting.ActivityClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sample.chatting.AsyncTaskClasses.LoginAsyncTask;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;

public class ActivityLogin extends AppCompatActivity {
    private EditText PhoneNo;
    private EditText Password;
    private LoginAsyncTask loginAyscTask;
    private TextView registertext;
    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
    public String tempo="99";
    public int temporary=99;
    public Button idbuttonlogin;
    private BroadcastReceiver broadcastReceiver;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try
        {
            this.context=getApplicationContext();
            broadcastReceiver=new CheckConnection();
            registerNetworkBroadcast();
            hideKeyboard();
            final CheckConnection checkConnection=new CheckConnection();
            // for system bar in lollipop
            // Tools.systemBarLolipop(this);
            Context mcontext = this.getApplicationContext();
            tempo = temp.getPreferences(mcontext, "Logindata");
            if(tempo.isEmpty())
            {
                tempo="99";
            }
            temporary = Integer.parseInt(tempo);
            if (temporary == 100)
            {

                startActivity(new Intent(ActivityLogin.this,ActivitySplash.class));
                finish();
            }
            else
                {
                PhoneNo = (EditText) findViewById(R.id.phoneno);
                Password = (EditText) findViewById(R.id.Password);
                idbuttonlogin=(Button)findViewById(R.id.btn_login) ;
                idbuttonlogin.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String phoneNo=PhoneNo.getText().toString();
                        String password=Password.getText().toString();

                        if(checkConnection.isOnline(context)==true)
                        {
                            CheckConnection checkConnection = new CheckConnection();
                            final int checkconnectionflag=checkConnection.connnectioncheck(context);
                            if(checkconnectionflag==1)
                            {
                                hideKeyboard();
                                loginAyscTask = (LoginAsyncTask) new LoginAsyncTask(phoneNo,password,getApplicationContext(),ActivityLogin.this).execute("","","");
                            }
                            else {
                                Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
                                context.startActivity(new Intent(context,ActivitySplash.class));
                            }

                        }
                        else {
                            Toast.makeText(context,"Internet not connected Try Again",Toast.LENGTH_SHORT).show();;
                        }

                    }
                });
                registertext = (TextView) findViewById(R.id.id_login);
                registertext.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        startActivity(new Intent(ActivityLogin.this, ActivityRegister.class));

                    }
                });
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    private void hideKeyboard()
    {
        try
        {
            View view = this.getCurrentFocus();
            if (view != null)
            {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    private void registerNetworkBroadcast()
    {
        try
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
        catch (Exception e)
        {
            e.printStackTrace();
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
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unregisterNetworkChanges();
    }

}
