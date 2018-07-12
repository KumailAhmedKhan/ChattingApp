package com.app.sample.chatting.ActivityClasses;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sample.chatting.AsyncTaskClasses.CancelFriendRequestAsyncTask;

import com.app.sample.chatting.AsyncTaskClasses.ConfirmFriendRequestAsyncTask;
import com.app.sample.chatting.AsyncTaskClasses.SendFriendRequestAsyncTask;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.URLClass.HTTPURL;
import com.app.sample.chatting.ViewModelClasses.FriendsViewModel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class ActivityFriendDetails extends AppCompatActivity //implements LoaderManager.LoaderCallbacks<String>
{

    public static final String EXTRA_OBJCT = "com.app.sample.chatting";
    private BroadcastReceiver broadcastReceiver;
    Context context;
    TemporaryStorageSharedPreferences temp=new TemporaryStorageSharedPreferences();
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FriendsViewModel friend;
    private View parent_view;

    public String getFriendsShipStatus() {
        return FriendsShipStatus;
    }

    public void setFriendsShipStatus(String friendsShipStatus) {
        FriendsShipStatus = friendsShipStatus;
    }

    public String FriendsShipStatus=null;
    String  UserId;
    static String ReceiverId;
    Long ReceiverID;
    SendFriendRequestAsyncTask sendFriendRequestAsyncTask=new SendFriendRequestAsyncTask();
    ConfirmFriendRequestAsyncTask confirmFriendRequestAsyncTask=new ConfirmFriendRequestAsyncTask();
    CancelFriendRequestAsyncTask cancelFriendRequestAsyncTask =new CancelFriendRequestAsyncTask();
    String NotificationId;
    String NotificationName;
    String NotificationType;
    Button FriendShipStatus;
    private ProgressDialog dialogue;
     //private CheckFriendShipStatusAsyncTask checkFriendShipStatusAsyncTask;
    String Name;
    String ID = null;
    @SuppressWarnings("ConstantConditions")
    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);
        final Bundle bundle=getIntent().getExtras();
       // ReceiverId=bundle.getString("ReceiverId");
        ReceiverID=bundle.getLong("ReceiverId");
        ID= String.valueOf(ReceiverID);
        ReceiverId=ID;
        // getLoaderManager().initLoader(1996, bundle, this).forceLoad();
        // ReceiverId=bundle.getString("ReceiverId");
        final Intent inte=getIntent();

            new CheckFriendShipStatusAsyncTask(getApplicationContext(),inte).execute(ID);


        //checkFriendShipStatusAsyncTask=(CheckFriendShipStatusAsyncTask) new CheckFriendShipStatusAsyncTask(getApplicationContext(),inte).execute(ReceiverId);

        //FriendsShipStatus= getFriendsShipStatus();//bundle.getString("FriendshipStatus");
/*
                if(FriendsShipStatus.equals("1"))
                {
                    //FriendShipStatus.setText("Sending Request");
                    FriendShipStatus.setVisibility(parent_view.GONE);
                    ConfirmRequest.setVisibility(View.GONE);
                    Friends.setVisibility(View.GONE);
                    CancelFriendRequest.setVisibility(View.VISIBLE);
                    CancelFriendRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            if(FriendsShipStatus.equals("1"))
                            {
                                cancelFriendRequestAsyncTask=(CancelFriendRequestAsyncTask) new CancelFriendRequestAsyncTask(getApplicationContext(),parent_view).execute(UserId,ReceiverId);
                                //FriendShipStatus.setVisibility(View.VISIBLE);
                                //connection.invoke("cancellFriendRequestAsync",conid,RecID);
                            }
                        }
                    });
                }
                else if(FriendsShipStatus.equals("2"))
                {
                    //FriendShipStatus.setText("Send Friend Request");
                    FriendShipStatus.setVisibility(View.VISIBLE);
                    //FriendShipStatus.setVisibility(parent_view.GONE);
                    ConfirmRequest.setVisibility(View.GONE);
                    Friends.setVisibility(View.GONE);
                    CancelFriendRequest.setVisibility(View.GONE);
                    FriendShipStatus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(FriendsShipStatus.equals("2"))
                            {
                                sendFriendRequestAsyncTask=(SendFriendRequestAsyncTask) new SendFriendRequestAsyncTask(getApplicationContext(),inte,parent_view).execute(UserId,ReceiverId);
                                //CancelFriendRequest.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
                else if(FriendsShipStatus.equals("3"))
                {
                    FriendShipStatus.setVisibility(View.GONE);
                    //FriendShipStatus.setVisibility(parent_view.GONE);
                    ConfirmRequest.setVisibility(View.VISIBLE);
                    Friends.setVisibility(View.GONE);
                    CancelFriendRequest.setVisibility(View.GONE);
                    //FriendShipStatus.setText("Confirm Friend Request");

                    ConfirmRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(FriendsShipStatus.equals("3"))
                            {
                                confirmFriendRequestAsyncTask=(ConfirmFriendRequestAsyncTask)new ConfirmFriendRequestAsyncTask(getApplicationContext(),parent_view).execute(UserId,ReceiverId);
                            }
                        }
                    });
                }
                else if(FriendsShipStatus.equals("4"))
                {
                    FriendShipStatus.setVisibility(View.GONE);
                    //FriendShipStatus.setVisibility(parent_view.GONE);
                    ConfirmRequest.setVisibility(View.GONE);
                    Friends.setVisibility(View.VISIBLE);
                    CancelFriendRequest.setVisibility(View.GONE);
                    //FriendShipStatus.setText("FRIENDS");
                }
                else{
                    Toast.makeText(getApplicationContext(),"Try Again Please Wait",Toast.LENGTH_LONG).show();
                }
                Name =  bundle.getString("Name");
                textView2.setText((bundle.getString("Name")));
                String Email = bundle.getString("Email");
                textView3.setText((bundle.getString("Email")));
               // textView3.setVisibility(View.INVISIBLE);
                String Phone =  bundle.getString("Phone");
                textView1.setText(( bundle.getString("Phone")));
               //textView1.setVisibility(View.INVISIBLE);
                try
                {
                        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(nMessageReceiver, new IntentFilter("CancelFriendRequestNotification"));
                        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(MessageReceiver, new IntentFilter("SendFriendRequestNotification"));
                        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver, new IntentFilter("FriendNow"));
                        parent_view = findViewById(android.R.id.content);
                        broadcastReceiver=new CheckConnection();
                        registerNetworkBroadcast();

                        // animation transition
                        this.context=context;
                       // ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_OBJCT);
                        //progressDialog=new ProgressDialog(this);
                        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setTitle("");
                    CancelFriendRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                           // if(FriendsShipStatus.contains("1"))
                            {
                                cancelFriendRequestAsyncTask=(CancelFriendRequestAsyncTask) new CancelFriendRequestAsyncTask(getApplicationContext(),parent_view).execute(UserId,ReceiverId);
                                //FriendShipStatus.setVisibility(View.VISIBLE);
                                //connection.invoke("cancellFriendRequestAsync",conid,RecID);
                            }
                        }
                    });
                    ConfirmRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           // if(FriendsShipStatus.contains("3"))
                            {
                                confirmFriendRequestAsyncTask=(ConfirmFriendRequestAsyncTask)new ConfirmFriendRequestAsyncTask(getApplicationContext(),parent_view).execute(UserId,ReceiverId);
                            }
                        }
                    });
                    FriendShipStatus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //if(FriendsShipStatus.contains("2"))
                            {
                                sendFriendRequestAsyncTask=(SendFriendRequestAsyncTask) new SendFriendRequestAsyncTask(getApplicationContext(),inte,parent_view).execute(UserId,ReceiverId);
                                //CancelFriendRequest.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                        //friend = (FriendsViewModel) getIntent().getSerializableExtra(EXTRA_OBJCT);
                        ///collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            //        collapsingToolbarLayout.setTitle(friend.getName());
                        ////////////////////////// for image ///////////////////////////////
                  /* ((ImageView) findViewById(R.id.image)).setImageResource(friend.getPhoto());
                    ((Button) findViewById(R.id.bt_view_photos)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(parent_view, "View Photos Clicked ", Snackbar.LENGTH_SHORT).show();
                        }
                    });

                }
                catch (Exception e)
                 {
                    e.printStackTrace();
                 }
*/

    }

    public void initialComponent(String s)
            throws Exception
    {
        UserId= (temp.getPreferences(getApplicationContext(),"Userdata"));
        final  TextView textView1=(TextView) findViewById(R.id.Phone) ;
        final TextView textView2=(TextView) findViewById(R.id.Name) ;
        final TextView textView3=(TextView) findViewById(R.id.Email) ;
        final Button FriendShipStatus=(Button) findViewById(R.id.FriendRequestButton);
        final Button CancelFriendRequest=(Button) findViewById(R.id.CancelFriendRequestButton);
        final Button ConfirmRequest=(Button) findViewById(R.id.ConfirmFriendRequestButton);
        final Button Friends=(Button) findViewById(R.id.FriendButton);
        final Bundle bundle=getIntent().getExtras();
       // ReceiverId=bundle.getString("ReceiverId");
        // getLoaderManager().initLoader(1996, bundle, this).forceLoad();
        // ReceiverId=bundle.getString("ReceiverId");
        final Intent inte=getIntent();
        dialogue=new ProgressDialog(ActivityFriendDetails.this);
        FriendsShipStatus=s;
        if(FriendsShipStatus.equals("1"))
        {
            //FriendShipStatus.setText("Sending Request");
            FriendShipStatus.setVisibility(parent_view.GONE);
            ConfirmRequest.setVisibility(View.GONE);
            Friends.setVisibility(View.GONE);
            CancelFriendRequest.setVisibility(View.VISIBLE);
            CancelFriendRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(FriendsShipStatus.equals("1"))
                    {
                        cancelFriendRequestAsyncTask=(CancelFriendRequestAsyncTask) new CancelFriendRequestAsyncTask(getApplicationContext(),parent_view).execute(UserId,ReceiverId);
                        //FriendShipStatus.setVisibility(View.VISIBLE);
                        //connection.invoke("cancellFriendRequestAsync",conid,RecID);
                    }
                }
            });
        }
        else if(FriendsShipStatus.equals("2"))
        {
            //FriendShipStatus.setText("Send Friend Request");
            FriendShipStatus.setVisibility(View.VISIBLE);
            //FriendShipStatus.setVisibility(parent_view.GONE);
            ConfirmRequest.setVisibility(View.GONE);
            Friends.setVisibility(View.GONE);
            CancelFriendRequest.setVisibility(View.GONE);
            FriendShipStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(FriendsShipStatus.equals("2"))
                    {
                        sendFriendRequestAsyncTask=(SendFriendRequestAsyncTask) new SendFriendRequestAsyncTask(getApplicationContext(),inte,parent_view).execute(UserId,ReceiverId);
                        //CancelFriendRequest.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        else if(FriendsShipStatus.equals("3"))
        {
            FriendShipStatus.setVisibility(View.GONE);
            //FriendShipStatus.setVisibility(parent_view.GONE);
            ConfirmRequest.setVisibility(View.VISIBLE);
            Friends.setVisibility(View.GONE);
            CancelFriendRequest.setVisibility(View.GONE);
            //FriendShipStatus.setText("Confirm Friend Request");

            ConfirmRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(FriendsShipStatus.equals("3"))
                    {
                        confirmFriendRequestAsyncTask=(ConfirmFriendRequestAsyncTask)new ConfirmFriendRequestAsyncTask(getApplicationContext(),parent_view).execute(UserId,ReceiverId);
                    }
                }
            });
        }
        else if(FriendsShipStatus.equals("4"))
        {
            FriendShipStatus.setVisibility(View.GONE);
            //FriendShipStatus.setVisibility(parent_view.GONE);
            ConfirmRequest.setVisibility(View.GONE);
            Friends.setVisibility(View.VISIBLE);
            CancelFriendRequest.setVisibility(View.GONE);
            //FriendShipStatus.setText("FRIENDS");
        }
        else{
            Toast.makeText(getApplicationContext(),"Try Again Please Wait",Toast.LENGTH_LONG).show();
        }
        Name =  bundle.getString("Name");
        textView2.setText((bundle.getString("Name")));
        String Email = bundle.getString("Email");
        textView3.setText((bundle.getString("Email")));
        // textView3.setVisibility(View.INVISIBLE);
        String Phone =  bundle.getString("Phone");
        textView1.setText(( bundle.getString("Phone")));
        //textView1.setVisibility(View.INVISIBLE);
        try
        {
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(nMessageReceiver, new IntentFilter("CancelFriendRequestNotification"));
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(MessageReceiver, new IntentFilter("SendFriendRequestNotification"));
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver, new IntentFilter("FriendNow"));
            parent_view = findViewById(android.R.id.content);
            broadcastReceiver=new CheckConnection();
            registerNetworkBroadcast();

            // animation transition
            this.context=context;
            // ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_OBJCT);
            //progressDialog=new ProgressDialog(this);
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            CancelFriendRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    // if(FriendsShipStatus.contains("1"))
                    {
                        cancelFriendRequestAsyncTask=(CancelFriendRequestAsyncTask) new CancelFriendRequestAsyncTask(getApplicationContext(),parent_view).execute(UserId,ReceiverId);
                        //FriendShipStatus.setVisibility(View.VISIBLE);
                        //connection.invoke("cancellFriendRequestAsync",conid,RecID);
                    }
                }
            });
            ConfirmRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // if(FriendsShipStatus.contains("3"))
                    {
                        confirmFriendRequestAsyncTask=(ConfirmFriendRequestAsyncTask)new ConfirmFriendRequestAsyncTask(getApplicationContext(),parent_view).execute(UserId,ReceiverId);
                    }
                }
            });
            FriendShipStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if(FriendsShipStatus.contains("2"))
                    {
                        sendFriendRequestAsyncTask=(SendFriendRequestAsyncTask) new SendFriendRequestAsyncTask(getApplicationContext(),inte,parent_view).execute(UserId,ReceiverId);
                        //CancelFriendRequest.setVisibility(View.VISIBLE);
                    }
                }
            });

            //friend = (FriendsViewModel) getIntent().getSerializableExtra(EXTRA_OBJCT);
            ///collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            //        collapsingToolbarLayout.setTitle(friend.getName());
            ////////////////////////// for image ///////////////////////////////
                  /* ((ImageView) findViewById(R.id.image)).setImageResource(friend.getPhoto());
                    ((Button) findViewById(R.id.bt_view_photos)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(parent_view, "View Photos Clicked ", Snackbar.LENGTH_SHORT).show();
                        }
                    });*/

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent)
    {

            return super.dispatchTouchEvent(motionEvent);

    }

    BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub
            NotificationType=intent.getStringExtra("NotificationType");
            NotificationName=intent.getStringExtra("SenderName");
            NotificationId=intent.getStringExtra("SenderId");
            final Button FriendShipStatus=(Button) findViewById(R.id.FriendRequestButton);
            final Button CancelFriendRequest=(Button) findViewById(R.id.CancelFriendRequestButton);
            final Button ConfirmRequest=(Button) findViewById(R.id.ConfirmFriendRequestButton);
            final Button Friends=(Button) findViewById(R.id.FriendButton);
            int Type= Integer.parseInt(NotificationType);
            if((Name.contains(NotificationName))&&(NotificationId.contains(ReceiverId)))
            {
                if(Type==1)
                {
                    FriendShipStatus.setVisibility(View.GONE);
                    //FriendShipStatus.setVisibility(parent_view.GONE);
                    ConfirmRequest.setVisibility(View.VISIBLE);
                    Friends.setVisibility(View.GONE);
                    CancelFriendRequest.setVisibility(View.GONE);
                    //FriendShipStatus=(Button) findViewById(R.id.FriendRequestButton);
                    //FriendShipStatus.setText("Confirm Friend Request");

                }
                else if(Type==2)
                {
                    FriendShipStatus.setVisibility(View.GONE);
                    //FriendShipStatus.setVisibility(parent_view.GONE);
                    ConfirmRequest.setVisibility(View.GONE);
                    Friends.setVisibility(View.VISIBLE);
                    CancelFriendRequest.setVisibility(View.GONE);
                    //FriendShipStatus=(Button) findViewById(R.id.FriendRequestButton);
                    //FriendShipStatus.setText("Friends");
                }
                else if (Type==3)
                {
                    FriendShipStatus.setVisibility(View.GONE);
                    //FriendShipStatus.setVisibility(parent_view.GONE);
                    ConfirmRequest.setVisibility(View.GONE);
                    Friends.setVisibility(View.VISIBLE);
                    CancelFriendRequest.setVisibility(View.GONE);
                    //FriendShipStatus=(Button) findViewById(R.id.FriendRequestButton);
                   // FriendShipStatus.setText("Friends");
                }


            }



        }
    };
    BroadcastReceiver MessageReceiver =new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String USERID=intent.getStringExtra("UserId");
            String TYpe=intent.getStringExtra("NotificationType");
            String uSerId=temp.getPreferences(context,"UserData");
            final Button FriendShipStatus=(Button) findViewById(R.id.FriendRequestButton);
            final Button CancelFriendRequest=(Button) findViewById(R.id.CancelFriendRequestButton);
            final Button ConfirmRequest=(Button) findViewById(R.id.ConfirmFriendRequestButton);
            final Button Friends=(Button) findViewById(R.id.FriendButton);
            if(USERID.equals(uSerId))
            {
                if(TYpe.equals("1"))
                {
                    FriendShipStatus.setVisibility(View.GONE);
                    //FriendShipStatus.setVisibility(parent_view.GONE);
                    ConfirmRequest.setVisibility(View.VISIBLE);
                    Friends.setVisibility(View.GONE);
                    CancelFriendRequest.setVisibility(View.GONE);
                }
                else if(TYpe.equals("2"))
                {
                    FriendShipStatus.setVisibility(View.GONE);
                    ConfirmRequest.setVisibility(View.GONE);
                    Friends.setVisibility(View.VISIBLE);
                    CancelFriendRequest.setVisibility(View.GONE);
                }
                else if(TYpe.equals("3"))
                {
                    FriendShipStatus.setVisibility(View.GONE);
                    ConfirmRequest.setVisibility(View.GONE);
                    Friends.setVisibility(View.VISIBLE);
                    CancelFriendRequest.setVisibility(View.GONE);
                }
                else if(TYpe.equals("4"))
                {
                    FriendShipStatus.setVisibility(View.GONE);
                    ConfirmRequest.setVisibility(View.VISIBLE);
                    Friends.setVisibility(View.GONE);
                    CancelFriendRequest.setVisibility(View.GONE);
                }

            }
        }

    };
    BroadcastReceiver nMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String TYpe=intent.getStringExtra("NotificationType");
            String Success=intent.getStringExtra("NotificationSuccess");
            final Button FriendShipStatus=(Button) findViewById(R.id.FriendRequestButton);
            final Button CancelFriendRequest=(Button) findViewById(R.id.CancelFriendRequestButton);
            final Button ConfirmRequest=(Button) findViewById(R.id.ConfirmFriendRequestButton);
            final Button Friends=(Button) findViewById(R.id.FriendButton);
            if(TYpe.equals("1")&&(Success.equals("true")))
            {
                FriendShipStatus.setVisibility(View.VISIBLE);
                ConfirmRequest.setVisibility(View.GONE);
                Friends.setVisibility(View.GONE);
                CancelFriendRequest.setVisibility(View.GONE);
            }

        }
    };
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        else if(item.getItemId() == R.id.action_send_message)
        {
            Intent i = new Intent(getApplicationContext(), ActivityChatDetails.class);
            String ID=temp.getPreferences(getApplicationContext(),"Userdata");
            //temp.getPreferences(getApplicationContext(),"Userdata");
            //i.putExtra("")
            i.putExtra("KEY_FID",ReceiverId);
            i.putExtra("UserId",ID);
            i.putExtra("KEY_FNAME",Name);
            if(FriendsShipStatus.equals("4"))//||FriendsShipStatus.equals("3"))
            {
                startActivity(i);
            }
            else{
                Toast.makeText(getApplicationContext(),"You Have to make Friend First",Toast.LENGTH_LONG).show();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_friend_details, menu);
        //MenuItem   menuItem=menu.findItem(R.id.action_send_message);
        return true;
    }
    private void registerNetworkBroadcast()
            throws Exception
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
            throws Exception
    {

            unregisterReceiver(broadcastReceiver);

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        try {
            unregisterNetworkChanges();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*

     */

/*
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        //dialogue=new ProgressDialog(ActivityFriendDetails.this);
        dialogue.setMessage("Loading");
        dialogue.show();
        return new checkFriendShipStatusAsyncTaskLoader(getApplicationContext() ,args);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        FriendsShipStatus=data;
        setFriendsShipStatus(data);
       dialogue.dismiss();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
    */

    public class CheckFriendShipStatusAsyncTask extends AsyncTask<String,String,String> {
        private Context context;
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
        HTTPURL httpurl=new HTTPURL();
        String UserId;
        private Intent intent;
        CheckConnection checkConnection=new CheckConnection();
        Activity activity;



        public CheckFriendShipStatusAsyncTask (Context ctx, Intent in){
            context=ctx.getApplicationContext();
            this.intent=in;


        }
        public CheckFriendShipStatusAsyncTask(Context context, Bundle args){}

        @Override
        protected String doInBackground(String... strings) {

            final int checkconnectionflag=checkConnection.connnectioncheck(context);
            if(checkconnectionflag==1) {
                String UnknownUserId=strings[0];
                UserId = temp.getPreferences(context, "Userdata");
                try {
                    String Url = httpurl.getURl();
                    int user2id= Integer.parseInt(UnknownUserId);
                    URL myUrl = new URL(Url + "/CheckFriendshipStatus?UserId="+UserId+"&User2Id="+user2id);
                    //Create a connection
                    HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                    //Set methods and timeouts
                    connection.setRequestMethod(REQUEST_METHOD);
                    connection.setReadTimeout(READ_TIMEOUT);
                    connection.setConnectTimeout(CONNECTION_TIMEOUT);
                    //Connect to our url
                    connection.connect();
                    //Create a new InputStreamReader
                    InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                    //Create a new buffered reader and String Builder
                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    //Check if the line we are reading is not null
                    String inputLine = "";
                    while ((inputLine = reader.readLine()) != null) {
                        stringBuilder.append(inputLine);
                    }
                    //Close our InputStream and Buffered reader
                    reader.close();
                    streamReader.close();

                    String Data = stringBuilder.toString();
                    JSONObject jsonData = new JSONObject(Data);
                    //JSONArray jsonArr = jsonData.getJSONArray("Model");
                    //JSONObject jsonObj = jsonArr.getJSONObject(0);
                    String StatusCode=jsonData.getString("StatusCode");
                    return StatusCode;



                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context,ActivitySplash.class));
            }
            return null;
        }

        @Override
        protected void onPreExecute() {


            dialogue=new ProgressDialog(ActivityFriendDetails.this);
            dialogue.setMessage("Loading");
            dialogue.show();
            super.onPreExecute();
            //this.dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            if (dialogue.isShowing()) {
                dialogue.dismiss();
            }
           // intent.putExtra("FriendshipStatus",s);
            //setFriendsShipStatus(s);
            try {
                initialComponent(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //context.startActivity(intent);
            //Intent newIntent= new Intent(context, ActivitySplash.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           // context.startActivity(intent);
            //Intent intent1 = new Intent("FriendShipStatus");
            //intent1.putExtra("FSStatus",s);
            //LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);

            super.onPostExecute(s);
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while(itr.hasNext()){

                String key= itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }


    }

}
