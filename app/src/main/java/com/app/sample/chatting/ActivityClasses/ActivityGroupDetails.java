package com.app.sample.chatting.ActivityClasses;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sample.chatting.AsyncTaskClasses.GroupChatMessageStatusAsyncTask;
import com.app.sample.chatting.AsyncTaskLoaderClasses.GroupChatLoader;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.R;
//import com.app.sample.chatting.SignalR.SignalRHubConnection;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.Classes.WebSocketHubConnection;
import com.app.sample.chatting.SignalR.Interface.HubConnection;
import com.app.sample.chatting.URLClass.HTTPURL;
import com.app.sample.chatting.ViewModelClasses.GroupChatViewModel;
import com.app.sample.chatting.ViewModelClasses.UserGroupViewModel;
import com.app.sample.chatting.AdapterClasses.GroupChatAdapter;

import com.app.sample.chatting.ViewModelClasses.Data.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActivityGroupDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<GroupChatViewModel>> {

    private BroadcastReceiver broadcastReceiver;
    private String authHeader = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6Ijc5NzhjMjI3LWViMGItNGMwOS1iYWEyLTEwYmE0MjI4YWE4OSIsImNlcnRzZXJpYWxudW1iZXIiOiJtYWNfYWRkcmVzc19vZl9waG9uZSIsInNlY3VyaXR5U3RhbXAiOiJlMTAxOWNiYy1jMjM2LTQ0ZTEtYjdjYy0zNjMxYTYxYzMxYmIiLCJuYmYiOjE1MDYyODQ4NzMsImV4cCI6NDY2MTk1ODQ3MywiaWF0IjoxNTA2Mjg0ODczLCJpc3MiOiJCbGVuZCIsImF1ZCI6IkJsZW5kIn0.QUh241IB7g3axLcfmKR2899Kt1xrTInwT6BBszf6aP4";
    private HubConnection connection;
    public static String KEY_GROUP = "com.app.sample.chatting.UserGroupViewModel";
    public static String KEY_GROUPID = "com.app.sample.chatting.GroupId";
    public static String KEY_GROUPName = "com.app.sample.chatting.GroupName";
    TemporaryStorageSharedPreferences temp=new TemporaryStorageSharedPreferences();
    private Button btn_send;
    private EditText et_content;
    public  GroupChatAdapter madapter;
    private List<GroupChatViewModel> message=new ArrayList<>();
    private ListView listview;
    private ActionBar actionBar;
    private UserGroupViewModel group;
    private View parent_view;
    public Context context;
    static String groupid;
    public static String groupName;
    private ProgressDialog progressDialog;
    public GroupChatMessageStatusAsyncTask groupChatMessageStatusAsyncTask=new GroupChatMessageStatusAsyncTask();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
       // connection=new WebSocketHubConnection( HTTPURL.getHubURl(),"",this);
        super.onCreate(savedInstanceState);
        this.context=getApplicationContext();
        setContentView(R.layout.activity_group_details);

        try {
            connection=new WebSocketHubConnection( HTTPURL.getHubURl(),"",this);
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(MessageReceiver, new IntentFilter("FriendFromGroupMessage"));
            broadcastReceiver = new CheckConnection();
            registerNetworkBroadcast();
            progressDialog = new ProgressDialog(this);
            /*/SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swiperefresh);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
            {
                @Override
                public void onRefresh()
                {
                    Intent intent=new Intent(ActivityGroupDetails.this,ActivityGroupDetails.class);
                    startActivity(intent);
                }
            });*/

            parent_view = findViewById(android.R.id.content);
            ViewCompat.setTransitionName(parent_view, KEY_GROUP);
            Intent intent = getIntent();
            Bundle bd = intent.getExtras();
            groupid = String.valueOf(bd.get("KEY_GID"));

            groupName = String.valueOf(bd.get("KEY_GNAME"));
            String ActiveModal = "G_" + groupid;
            temp.savePreferences(context, "ActiveModalGroup", ActiveModal);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_GROUPID", groupid);
            group = (UserGroupViewModel) intent.getExtras().getSerializable(KEY_GROUP);
            CheckConnection checkConnection = new CheckConnection();
            final int checkconnectionflag=checkConnection.connnectioncheck(context);
                if(checkconnectionflag==1)
                {
                     getLoaderManager().initLoader(1994, bundle, this).forceLoad();
                }
                else
                    {
                        Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
                        context.startActivity(new Intent(context,ActivitySplash.class));
                }


            initToolbar();
            iniComponen();
            madapter=new GroupChatAdapter(this,new ArrayList<GroupChatViewModel>());
            listview.setAdapter(madapter);
            listview.requestFocus();
            registerForContextMenu(listview);
            Tools.systemBarLolipop(this);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void initToolbar()
    {
        try
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
//        actionBar.setTitle(group.getName());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {

        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                groupChatMessageStatusAsyncTask.groupChatViewModel.setMessageID(Long.parseLong("101010101"));
                groupChatMessageStatusAsyncTask.groupChatViewModel.setDate("2020");
                groupChatMessageStatusAsyncTask.groupChatViewModel.setContent("_+&*^(%)$%#");
                groupChatMessageStatusAsyncTask.group.add(groupChatMessageStatusAsyncTask.groupChatViewModel);
                madapter.notifyDataSetChanged();
            }
        });

        return super.dispatchTouchEvent(ev);
    }

    public void bindView()
    {
        try
        {
            madapter.notifyDataSetChanged();
            listview.setSelectionFromTop(madapter.getCount()-1, 0);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void iniComponen()
    {
        try
        {
            final Random r = new Random();
            listview = (ListView) findViewById(R.id.listview);
            btn_send = (Button) findViewById(R.id.btn_send);
            et_content = (EditText) findViewById(R.id.text_content);
            final boolean[] flag = {true};
            btn_send.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    String editTextValue=et_content.getText().toString();
                    int  UserId= Integer.parseInt(temp.getPreferences(context,"Userdata"));
                    int groupId= Integer.parseInt(groupid);
                    String conid=temp.getPreferences(context,"ConnectionID");
                    connection.invoke("sendGroupMessageAsync",conid,groupId,editTextValue);
                    /*listview.setBottom(madapter.getCount());
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String Date=dateFormat.format(date);
                    GroupChatViewModel groupChatViewModel=new GroupChatViewModel();
                    groupChatViewModel.setContent(editTextValue);
                    groupChatViewModel.setCreatedDate(Date);
                    groupChatViewModel.setId(UserId);
                   // groupChatViewModel.setSenderName(group.getName());
                    //madapter.add(groupChatViewModel);
*/
                    et_content.setText("");
                    flag[0]=true;
                    bindView();
                    hideKeyboard();}

            });
            et_content.addTextChangedListener(contentWatcher);
            if (et_content.length() == 0)
            {
//
                btn_send.setEnabled(false);
            }
            hideKeyboard();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    BroadcastReceiver MessageReceiver=new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String Text=intent.getStringExtra("MessageText");
            String Date=intent.getStringExtra("MessageDate");
            String GroupId=intent.getStringExtra("GroupID");
            String SenderName=intent.getStringExtra("SenderName");
            String SenderID=intent.getStringExtra("SenderID");
            GroupChatViewModel groupChatViewModel=new GroupChatViewModel();
            groupChatViewModel.setSenderName(SenderName);
            groupChatViewModel.setCreatedDate(Date);
            groupChatViewModel.setContent(Text);
            groupChatViewModel.setId(Long.parseLong(GroupId));
            groupChatViewModel.setSenderId(Long.parseLong(SenderID));
            madapter.add(groupChatViewModel);
            madapter.notifyDataSetChanged();
        }
    };

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

    private TextWatcher contentWatcher = new TextWatcher()
    {

        @Override
        public void afterTextChanged(Editable etd)
        {
            try{
                if (etd.toString().trim().length() == 0)
                {
                    btn_send.setEnabled(false);
                }
                else {
                    btn_send.setEnabled(true);
                }
                //draft.setContent(etd.toString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
        {

        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
        {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_group_details, menu);
        return true;
    }

    /**
     * Handle click on action bar
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_sample:
                Snackbar.make(parent_view, item.getTitle() + " HClicked ", Snackbar.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<List<GroupChatViewModel>> onCreateLoader(int id, Bundle args)
    {
        progressDialog.setMessage("Loading");
        progressDialog.show();
        return new GroupChatLoader(context,args);
    }



    @Override
    public void onLoadFinished(Loader<List<GroupChatViewModel>> loader, List<GroupChatViewModel> data)
    {
        try
        {
            madapter.setchat(data);
            //message.addAll(data);
            listview.setSelection(madapter.getCount()-5);
            listview.post(new Runnable()
            {
                @Override
                public void run()
                {
                    listview.setSelection(madapter.getCount()-5);
                }
            });
            progressDialog.dismiss();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<GroupChatViewModel>> loader)
    {

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
        String ActiveModal=null;
        temp.savePreferences(context,"ActiveModalGroup",ActiveModal);
    }
}
