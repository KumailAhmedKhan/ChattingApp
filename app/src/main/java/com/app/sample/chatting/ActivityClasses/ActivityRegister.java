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

import com.app.sample.chatting.AsyncTaskClasses.RegisterAsyncTask;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.R;
import com.app.sample.chatting.ViewModelClasses.Data.Tools;

public class ActivityRegister extends AppCompatActivity {
    private EditText Name;
    private EditText Email;
    private EditText PhoneNo;
    private EditText Password;
    private RegisterAsyncTask registerAyscTask;
    private Button RegisterBtn;
    private TextView idlogin;
    private BroadcastReceiver broadcastReceiver;
    Context context;
    CheckConnection checkConnection=new CheckConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        try
        {
            this.context=getApplicationContext();
            PhoneNo = (EditText) findViewById(R.id.phoneno);
            Password = (EditText) findViewById(R.id.Password);
            Name = (EditText) findViewById(R.id.input_name);
            Email = (EditText) findViewById(R.id.input_email);
            RegisterBtn = (Button)findViewById(R.id.btn_Register);
            idlogin=(TextView)findViewById(R.id.idsignin);
            broadcastReceiver=new CheckConnection();
            registerNetworkBroadcast();
            hideKeyboard();

            // for system bar in lollipop
            Tools.systemBarLolipop(this);
            RegisterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phoneNo=PhoneNo.getText().toString();
                    String password=Password.getText().toString();
                    String name=Name.getText().toString();
                    String email=Email.getText().toString();
                    if(checkConnection.isOnline(context)==true)
                    {
                        CheckConnection checkConnection = new CheckConnection();
                        final int checkconnectionflag=checkConnection.connnectioncheck(context);
                        if(checkconnectionflag==1)
                        {
                            hideKeyboard();
                            registerAyscTask = (RegisterAsyncTask) new RegisterAsyncTask(name,phoneNo,email,password,getApplicationContext(),ActivityRegister.this).execute("","","");
                        }
                        else {
                            Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
                            context.startActivity(new Intent(context,ActivitySplash.class));
                        }

                    }
                    else
                    {
                        Toast.makeText(context,"Internet not connected Try Again",Toast.LENGTH_SHORT).show();;
                    }
                }
            });
            idlogin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(ActivityRegister.this, ActivityLogin.class);
                    startActivity(i);
                }
            });
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

}
