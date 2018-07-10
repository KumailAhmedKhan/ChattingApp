package com.app.sample.chatting.Activity;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.app.sample.chatting.AsyncTaskLoader.FriendsChatLoader;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiver.CheckConnection;
import com.app.sample.chatting.Fragments.FriendsFragment;
import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.Classes.WebSocketHubConnection;
import com.app.sample.chatting.SignalR.Interface.HubConnection;
import com.app.sample.chatting.URL.httpUrl;
import com.app.sample.chatting.ViewModelClasses.FriendChatViewModel;
import com.app.sample.chatting.ViewModelClasses.FriendsRecentChatViewModel;
import com.app.sample.chatting.ViewModelClasses.FriendsViewModel;
import com.app.sample.chatting.adapter.FriendsChatAdapter;
import com.app.sample.chatting.data.Constant;
import com.app.sample.chatting.data.Tools;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityChatDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<FriendChatViewModel>>
{

    public static String KEY_FRIEND     = "com.app.sample.chatting.FRIEND";
    public static String KEY_SNIPPET   = "com.app.sample.chatting.SNIPPET";
    public static String KEY_FNAME   = "com.app.sample.chatting.FNAME";
    public static String KEY_FID   = "com.app.sample.chatting.FID";
    private Button btn_send;
    private EditText et_content;
    public  FriendsChatAdapter madapter;
    public com.app.sample.chatting.AsyncTask.FriendChatMessageStatusAsyncTask messagestatus=new com.app.sample.chatting.AsyncTask.FriendChatMessageStatusAsyncTask();
    private ListView listview;
    private ActionBar actionBar;
    private FriendsViewModel friend;
    private  List<FriendChatViewModel> message=new ArrayList<>();
    private View parent_view;
    public Context context;
    private static boolean flag=true;
    TemporaryStorageSharedPreferences temp=new TemporaryStorageSharedPreferences() ;
    private String authHeader = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6Ijc5NzhjMjI3LWViMGItNGMwOS1iYWEyLTEwYmE0MjI4YWE4OSIsImNlcnRzZXJpYWxudW1iZXIiOiJtYWNfYWRkcmVzc19vZl9waG9uZSIsInNlY3VyaXR5U3RhbXAiOiJlMTAxOWNiYy1jMjM2LTQ0ZTEtYjdjYy0zNjMxYTYxYzMxYmIiLCJuYmYiOjE1MDYyODQ4NzMsImV4cCI6NDY2MTk1ODQ3MywiaWF0IjoxNTA2Mjg0ODczLCJpc3MiOiJCbGVuZCIsImF1ZCI6IkJsZW5kIn0.QUh241IB7g3axLcfmKR2899Kt1xrTInwT6BBszf6aP4";

    httpUrl httpUrl=new httpUrl();


    private HubConnection connection;
    private BroadcastReceiver broadcastReceiver;
    protected static String FriendID;
    String Name;
    private ProgressDialog progressDialog;
    CheckConnection checkConnection=new CheckConnection();


    // give preparation animation activity transition
    public static void navigate(AppCompatActivity activity, View transitionImage,  String snippet,String Name,String Id)
    {
        Intent intent = new Intent(activity, ActivityChatDetails.class);
        //intent.putExtra(KEY_FRIEND, obj);
        intent.putExtra(KEY_SNIPPET, snippet);
        intent.putExtra(KEY_FID,Id);
        intent.putExtra(KEY_FNAME,Name);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, KEY_FRIEND);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);

        try
        {
            connection=new WebSocketHubConnection( httpUrl.getHubURl(),"",this);
            this.context=getApplicationContext();
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(MessageReceiver, new IntentFilter("MessageFromFriend"));
            LocalBroadcastManager.getInstance(context).registerReceiver(nMessageReceiver, new IntentFilter("OfflineFriend"));
            progressDialog=new ProgressDialog(this);
            parent_view = findViewById(android.R.id.content);
            this.context=getApplicationContext();
            /*SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swiperefresh);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
            {
                @Override
                public void onRefresh()
                {
                    Intent intent=new Intent(ActivityChatDetails.this,ActivityChatDetails.class);
                    startActivity(intent);
                }
            });*/
            // animation transition
            ViewCompat.setTransitionName(parent_view, KEY_FRIEND);
            Intent intent = getIntent();
            Bundle bd=intent.getExtras();
            FriendID= String.valueOf(bd.get("KEY_FID"));
            String UserID=String.valueOf(bd.get("UserId"));
            Name=String.valueOf(bd.get("KEY_FNAME"));
            String ActiveModal="I_"+ FriendID;
            temp.savePreferences(context,"ActiveModal",ActiveModal);
            ///////////////////////////making Bundle/////////////////////////////////////////////
            Bundle bundle=new Bundle();
            bundle.putString("KEY_FID",FriendID);
            //////////////////////////////Loader Initiator////////////////////////////////////////
            final int checkconnectionflag=checkConnection.connnectioncheck(context);
            if(checkconnectionflag==1)
            {
                getLoaderManager().initLoader(1996, bundle, this).forceLoad();
            }
            else {

                    Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context,ActivitySplash.class));

            }
            String temp1= temp.getPreferences(context,"OnlineFriendIds");
            initToolbar(temp1);
            iniComponen();

            ////////////////////////////////setting adapter in listview///////////////////////
            madapter=new FriendsChatAdapter(this,message);

            listview.setAdapter(madapter);

            listview.setSelectionFromTop(madapter.getCount(), 0);
            listview.requestFocus();
            registerForContextMenu(listview);
            madapter.setOnItemClickListener(new FriendsChatAdapter.OnItemClickListener()
            {
                @Override
                public void onItemClick(View view, FriendChatViewModel obj, int position)
                {
                    Toast.makeText(context,"chatclick",Toast.LENGTH_SHORT).show();
                }
            });

            registerNetworkBroadcast();
            // for system bar in lollipop
            Tools.systemBarLolipop(this);
///////////////////////////////////////////////////////////////////yahan par kaam karna ha jo shuru karnay ki koshish kar rha/////////////////////////////////
}
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void initToolbar(String temp1)
    {
        try
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent(ActivityChatDetails.this,ActivityFriendDetails.class);
                    startActivity(intent);
                }
            });
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            String SenderID=FriendID;
            String SenderName= Name;

            temp1=temp1.replaceAll("\\[","").replaceAll("\\]","");
            String[] OnlineFriends = temp1.split(",");
            actionBar.setTitle(SenderName);
            int sendrid=0;
            boolean flag=true;
            for(int i=0;i<OnlineFriends.length;i++)
            {
                if(OnlineFriends[i].equalsIgnoreCase(SenderID))
                {
                    flag=false;
                    actionBar.setSubtitle("online");

                }
                else if(SenderID!=OnlineFriends[i] && flag==true)
                {
                    actionBar.setSubtitle("offline");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void bindView()
    {
        try
        {
            madapter.notifyDataSetChanged();
            listview.setSelectionFromTop(madapter.getCount(), 0);
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
            listview = (ListView) findViewById(R.id.listview);
            btn_send = (Button) findViewById(R.id.btn_send);
            et_content = (EditText) findViewById(R.id.text_content);
            final boolean[] flag = {true};
            btn_send.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int  UserId= Integer.parseInt(temp.getPreferences(context,"Userdata"));
                    String editTextValue=et_content.getText().toString();
                    int frndId= Integer.parseInt(FriendID);
                    Log.d("FriendID",frndId+" ");
                    Log.d("UserID",UserId+" ");
                    String conid=temp.getPreferences(context,"ConnectionID");
                    //int position=madapter.getCount();
                  /*  FriendChatViewModel friendChatViewModel1=new FriendChatViewModel();
                    friendChatViewModel1.setContent(editTextValue);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String Date=dateFormat.format(date);
                    friendChatViewModel1.setDate(Date);
                    friendChatViewModel1.setRecId(frndId);
                    ///madapter.add(friendChatViewModel1);*/

                    connection.invoke("sendMessageAsync",conid,frndId,editTextValue);
                    et_content.setText("");
                    flag[0]=true;
                    bindView();
                    hideKeyboard();

                }
            });
            et_content.addTextChangedListener(contentWatcher);
            if (et_content.length() == 0)
            {
                btn_send.setEnabled(false);
            }
            else {
                    hideKeyboard();

            }
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
            String ReceiverID=intent.getStringExtra("MessageReceiverID");
            String SenderId=intent.getStringExtra("MessageSenderID");
            FriendChatViewModel friendChatViewModel = new FriendChatViewModel();
            friendChatViewModel.setContent(Text);
            friendChatViewModel.setRecId(Long.parseLong(ReceiverID));
            friendChatViewModel.setSendId(Long.parseLong(SenderId));
            friendChatViewModel.setDate(Date);
            madapter.add(friendChatViewModel);
            madapter.notifyDataSetChanged();
        }
    };
    BroadcastReceiver nMessageReceiver=new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
           //initToolbar();
            String OnlineFriendsId=intent.getStringExtra("OfflineFriendsId");
            initToolbar(OnlineFriendsId);
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

         }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private TextWatcher contentWatcher = new TextWatcher()
    {
        @Override
        public void afterTextChanged(Editable etd)
        {
            try
            {
                if (etd.toString().trim().length() == 0)
                {
                btn_send.setEnabled(true);
                }
                else
                {
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
        getMenuInflater().inflate(R.menu.menu_chat_details, menu);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
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
                Snackbar.make(parent_view, item.getTitle() + " Clicked ", Snackbar.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public Loader<List<FriendChatViewModel>> onCreateLoader(int id, Bundle args)
    {
        progressDialog.setMessage("Loading");
        progressDialog.show();
        return new FriendsChatLoader(context ,args);
    }



    @Override
    public void onLoadFinished(Loader<List<FriendChatViewModel>> loader, List<FriendChatViewModel> data) {
        try
        {
            madapter.adaptercleaR();
            madapter.setfriendchat(data);
            //message.addAll(data);
            listview.setSelection(madapter.getCount() - 1);
            ////////////////////////////////////////////for setting last position//////////////////////////////////////////
            listview.post(new Runnable() {
                @Override
                public void run() {
                    listview.setSelection(madapter.getCount() - 1);
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
    public void onLoaderReset(Loader<List<FriendChatViewModel>> loader)
    {
        //try{}catch (Exception e){e.printStackTrace();}

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
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        //try{}catch (Exception e){e.printStackTrace();}
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    messagestatus.friendChatViewModel.setMessageId(Long.parseLong("101010101"));
                    messagestatus.friendChatViewModel.setDate("2020");
                    messagestatus.friendChatViewModel.setContent("_+&^%^&%^$%^");
                    messagestatus.frnd.add(messagestatus.friendChatViewModel);
                    //madapter.notifyAll();
                    madapter.notifyDataSetChanged();
                }
            });

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unregisterNetworkChanges();
        String ActiveModal="I_"+null;
        temp.savePreferences(getApplicationContext(),"ActiveModal",ActiveModal);
        message.clear();

    }
}