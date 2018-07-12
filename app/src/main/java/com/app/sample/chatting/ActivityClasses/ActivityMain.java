package com.app.sample.chatting.ActivityClasses;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.app.sample.chatting.AsyncTaskClasses.ClearNotificationAsyncTask;
import com.app.sample.chatting.AsyncTaskClasses.GroupChatMessageStatusAsyncTask;
import com.app.sample.chatting.AsyncTaskClasses.UserNotificationAsyncTask;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
//import com.app.sample.chatting.SignalR.Activity.SignalR;
import com.app.sample.chatting.SignalR.Services.SignalRConnectionService;
import com.app.sample.chatting.FragmentClasses.ChatsFragment;
import com.app.sample.chatting.FragmentClasses.FragmentAdapter;
import com.app.sample.chatting.FragmentClasses.FriendsFragment;
import com.app.sample.chatting.FragmentClasses.GroupsFragment;

import org.json.JSONObject;

public class ActivityMain extends AppCompatActivity{

    static MenuItem Item;
    public static String TAG_FRAGMENT="Tag_Fragment";
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private DrawerLayout drawerLayout;
    public FloatingActionButton fab;
    private Toolbar searchToolbar;
    private static ViewPager viewPager;
    private BroadcastReceiver broadcastReceiver;
    private boolean isSearch = false;
    public static TabLayout tabLayout;
    private FriendsFragment f_friends;
    private ChatsFragment f_chats;
    private GroupsFragment f_groups;
    private View parent_view;
    private View view1;
    private View parent_view1;
    FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
    TemporaryStorageSharedPreferences temp=new TemporaryStorageSharedPreferences();
    public Context context;
    private Menu menu1;
    static String  NotificationCounter;
    int count;
    ClearNotificationAsyncTask clearNotificationAsyncTask=new ClearNotificationAsyncTask();

   ////////////////////////////////Broadcast Receiver////////////////////////////////////////
    BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub
            NotificationCounter=intent.getStringExtra("NotificationCounter");
            //int temp= Integer.parseInt(NotificationCounter);
            //Item.setIcon(Converter.convertLayoutToImage(context, Integer.parseInt(NotificationCounter), R.drawable.ic_notif));


        }
    };
    BroadcastReceiver MessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub

           //Item.setIcon(Converter.convertLayoutToImage(context, 0, R.drawable.ic_notif));


        }
    };
    BroadcastReceiver FriendRequestNotification = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub
            String Id1= temp.getPreferences(context, "Logindata");
            String UserId=intent.getStringExtra("UserId");
           // int temp= Integer.parseInt(NotificationCounter);
            if(UserId.equals(Id1)==true) {
                //Item.setIcon(Converter.convertLayoutToImage(context, Integer.parseInt(NotificationCounter), R.drawable.ic_notif));
            }

        }
    };
    /////////////////////////////////////////Constructor///////////////////////////////////////////////////////
    public ActivityMain()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try
        {
            //LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver, new IntentFilter("FriendNow"));
            //Bundle bundle=getIntent().getExtras();
            //NotificationCounter=bundle.getString("NotificationCount");

            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(FriendRequestNotification, new IntentFilter("SendFriendRequestNotification"));
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(MessageReceiver, new IntentFilter("NotificationCountClear"));
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver, new IntentFilter("NotificationCount"));
            NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view) ;
            parent_view1=navigationView.getHeaderView(0);
            TabItem tabfrnd=(TabItem)findViewById(R.id.tabfriends);
            TabItem tabchat=(TabItem)findViewById(R.id.tabchats);
            TabItem tabgroup=(TabItem)findViewById(R.id.tabgroups);
            toolbar = (Toolbar) findViewById(R.id.toolbar_viewpager);
            appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
            searchToolbar = (Toolbar) findViewById(R.id.toolbar_search);
            fab = (FloatingActionButton) findViewById(R.id.fab);
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            TextView textView=(TextView) parent_view1.findViewById(R.id.Name) ;
            TextView textView1=(TextView)parent_view1.findViewById(R.id.Email);
            //ViewGroup.
            ////////////////////////////////////////////////////////////////////////////////////////////////////////list main kuch dalna ha////////////////////////////////////////////
           // if(messagestatus.frnd.size()==0)
         /*   {
                messagestatus.friendChatViewModel.setContent("_+&*^(%)$%#");
                messagestatus.friendChatViewModel.setDate("2020");
                messagestatus.friendChatViewModel.setMessageId(Long.parseLong("101010101"));
                messagestatus.frnd.add(messagestatus.friendChatViewModel);
            }
            if(groupChatMessageStatusAsyncTask.group.size()==0)
            {
                groupChatMessageStatusAsyncTask.groupChatViewModel.setMessageID(Long.parseLong("101010101"));
                groupChatMessageStatusAsyncTask.groupChatViewModel.setDate("2020");
                groupChatMessageStatusAsyncTask.groupChatViewModel.setContent("_+&*^(%)$%#");
                groupChatMessageStatusAsyncTask.group.add(groupChatMessageStatusAsyncTask.groupChatViewModel);
            }
*/

            this.context=getApplicationContext();
            broadcastReceiver=new CheckConnection();
            //String  UserId= temp.getPreferences(context,"Userdata");
            //////////////////////////////////start signalr Asyntask//////////////////////////////
            ///connection.invoke("onConnectAsync", UserId);
            //int a=checkConnection.connnectioncheck(context);
            ///registerNetworkBroadcast();
            ///////////////////////////////Starting Service//////////////////////////////////
           // startService();
            // viewPager.SetAdapter(new MyPagerAdapter(getSupportFragmentManager()));
            parent_view = findViewById(R.id.main_content);

            view1=findViewById(R.id.lyt_parent);
            setupDrawerLayout();
            tabfrnd.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    viewPager.setCurrentItem(0);
                }
            });
            tabchat.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    viewPager.setCurrentItem(1);
                }
            });
            tabgroup.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    viewPager.setCurrentItem(2);
                }
            });

            prepareActionBar(toolbar);

            if (viewPager != null)
            {
                setupViewPager(viewPager);
            }

            initAction();
            parent_view1=(View)findViewById(R.id.menuhead) ;
            String abc=temp.getPreferences(context,"UserModel");
            JSONObject jsonData= null;
            jsonData = new JSONObject(abc);
            //JSONObject model=null;
           // model=jsonData.getJSONObject("Model");
            String Name;
            Name=jsonData.getString("Name");

            String email;
            email=jsonData.getString("Email");
            String phoneNo;
            phoneNo=jsonData.getString("PhoneNo");
            textView.setText(Name+"  ("+phoneNo+")");
            textView1.setText(email);
            //abc=jsonData.getJSONObject("Model");*/
            String ActiveModal="G_10";
            temp.savePreferences(context,"ActiveModalGroup",ActiveModal);
            String ActiveModal1="G_10";
            temp.savePreferences(context,"ActiveModal",ActiveModal1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
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

    @Override
    public void setTheme(int resid)
    {
        super.setTheme(resid);
    }



    private void initAction()throws Exception
    {
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switch (viewPager.getCurrentItem())
                {
                    case 0:
                       // Snackbar.make(parent_view, "Add Friend Clicked", Snackbar.LENGTH_SHORT).show();
                        Intent intent=new Intent(ActivityMain.this,NewUserActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        FriendsFragment fragment = new FriendsFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.drawer_layout, fragment,TAG_FRAGMENT).addToBackStack(TAG_FRAGMENT);
                        transaction.commit();
                        break;
                    case 2:
                        //Snackbar.make(parent_view, "Add Group Clicked", Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                try {
                    closeSearch();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition())
                {
                    case 0:
                        viewPager.setCurrentItem(0);
                        //userNotificationAsyncTask=(UserNotificationAsyncTask) new UserNotificationAsyncTask(getApplicationContext()).execute();
                        //FriendsFragment friendsFragment=new FriendsFragment();
                        fab.setImageResource(R.drawable.ic_add_friend);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        //userNotificationAsyncTask=(UserNotificationAsyncTask) new UserNotificationAsyncTask(getApplicationContext()).execute();

                        //ChatsFragment chatsFragment=new ChatsFragment();
                        fab.setImageResource(R.drawable.ic_create);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        //userNotificationAsyncTask=(UserNotificationAsyncTask) new UserNotificationAsyncTask(getApplicationContext()).execute();

                        fab.setImageResource(R.drawable.ic_add_group);
                        //fab.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
                //viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
               // viewPager.setCurrentItem(tab.getPosition());
            }
        });
        viewPager.setCurrentItem(1);
    }



    public void removeFragment(Fragment fragment)throws Exception
    {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    private void setupViewPager(ViewPager viewPager)throws Exception
    {

            if (f_friends == null)
            {
                f_friends = new FriendsFragment();
                //removeFragment(f_chats);
                //removeFragment(f_groups);
            }
            if (f_chats == null)
            {
                f_chats = new ChatsFragment();
                //removeFragment(f_groups);
                //removeFragment(f_friends);
            }
            if (f_groups == null)
            {
                f_groups = new GroupsFragment();
                //removeFragment(f_friends);
                // removeFragment(f_chats);
            }

            adapter.addFragment(f_friends, getString(R.string.tab_friends));
            adapter.addFragment(f_chats, getString(R.string.tab_chats));
            adapter.addFragment(f_groups, getString(R.string.tab_groups));

            viewPager.setAdapter(adapter);


    }

    private void prepareActionBar(Toolbar toolbar) throws Exception
    {

            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            if (!isSearch)
            {
                settingDrawer();
            }


    }

    public void setVisibilityAppBar(boolean visible) throws Exception
    {

            CoordinatorLayout.LayoutParams layout_visible = new CoordinatorLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            CoordinatorLayout.LayoutParams layout_invisible = new CoordinatorLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            if(visible)
            {
                appBarLayout.setLayoutParams(layout_visible);
                fab.show();
            }
            else {
                appBarLayout.setLayoutParams(layout_invisible);
                fab.hide();
            }


    }

    private void settingDrawer() throws Exception
    {

            mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
            {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }};
            // Set the drawer toggle as the DrawerListener
            drawerLayout.setDrawerListener(mDrawerToggle);


    }

    private void setupDrawerLayout() throws Exception
    {

            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            final NavigationView view = (NavigationView) findViewById(R.id.nav_view);
            view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
            {
                @SuppressLint("ResourceType")
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem)
                {
                    //menuItem.setChecked(true);
                    drawerLayout.closeDrawers();
                    //Fragment fragment = null;
                    //Class fragmentClass = null;
                    String drawer= (String) menuItem.getTitle();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    switch(drawer){
                        case("Profile"):
                           // startActivity(new Intent(ActivityMain.this,ActivityFriendDetails.class));
                            //CheckFriendShipStatusAsyncTask checkFriendShipStatusAsyncTask=new CheckFriendShipStatusAsyncTask();
                            //checkFriendShipStatusAsyncTask=(CheckFriendShipStatusAsyncTask) new CheckFriendShipStatusAsyncTask(context,)
                            break;
                        case("Friends"):

                            viewPager.setCurrentItem(0);
                            break;

                        case("Groups"):
                            viewPager.setCurrentItem(2);
                            break;
                        case ("Recent Chat"):
                            viewPager.setCurrentItem(1);
                            break;
                        case("Connect"):
                            // startActivity(new Intent(ActivityMain.this,SignalR.class));
                            break;
                        case("Logout"):

                            try {
                                temp.savePreferences(context,"Userdata", String.valueOf(0));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                temp.savePreferences(context,"Logindata",String.valueOf(99));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                temp.savePreferences(context,"Logindata",String.valueOf(99));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent("ServiceStopped");
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            startActivity(new Intent(ActivityMain.this,ActivityLogin.class));
                            stopService();
                            finish();
                    }

                    return true;
                }
            });


    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        try
        {
            super.onConfigurationChanged(newConfig);
            if (!isSearch)
            {
            mDrawerToggle.onConfigurationChanged(newConfig);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getMenuInflater();
        this.menu1=menu;
        inflater.inflate( R.menu.menu_main, menu);
        inflater.inflate(R.menu.menu_search_toolbar,menu);
        //getMenuInflater().inflate(R.menu.menu_search_toolbar, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
       // menuItem[0] = menu.findItem(R.id.action_search);
      //  menuItem[1] = menu.findItem(R.id.action_notif);
     //   menuItem[2] = menu.findItem(R.string.tab_chats);
      //  menuItem[3] = menu.findItem(R.string.tab_friends);
      //  menuItem[4] = menu.findItem(R.string.tab_groups);
         final MenuItem menuItem=menu.findItem(R.id.action_notif);
        // final MenuItem menuItem1=menu.findItem(R.id.action_search1);

        //menuItem.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_notif));

       // UserNotificationAsyncTaskLoader userNotificationAsyncTaskLoader=new UserNotificationAsyncTaskLoader(context.getApplicationContext());
         //int count= Integer.parseInt(NotificationCounter);
            Item=menuItem;
            //menuItem.setIcon(Converter.convertLayoutToImage(getApplicationContext(), 0, R.drawable.ic_notif));

//        menuItem1.setIcon(Converter.convertLayoutToImage(ActivityMain.this,cart_count,R.string.tab_chats));
 //       menuItem2.setIcon(Converter.convertLayoutToImage(ActivityMain.this,cart_count,R.string.tab_friends));
 //       menuItem3.setIcon(Converter.convertLayoutToImage(ActivityMain.this,cart_count,R.string.tab_groups));
       // MenuItem menuItem = menu.findItem(R.id.action_notif);
       // menuItem.setIcon(buildCounterDrawable(5, R.drawable.ic_notif));
        if (isSearch==true)
        {
            //Toast.makeText(getApplicationContext(), "Search " + isSearch, Toast.LENGTH_SHORT).show();
            final SearchView search = (SearchView) menu.findItem(R.id.action_search1).getActionView();
            search.setIconified(false);
            switch (viewPager.getCurrentItem())
            {
                case 0:
                    search.setQueryHint("Search friends...");
                    break;
                case 1:
                    search.setQueryHint("Search chats...");
                    break;
                case 2:
                    search.setQueryHint("Search groups...");
                    break;
            }
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener()
            {
                @Override
                public boolean onQueryTextSubmit(String s)
                {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s)
                {
                    switch (viewPager.getCurrentItem())
                    {
                        case 0:
                            f_friends.mAdapter.getFilter().filter(s);
                            break;
                        case 1:
                            f_chats.mAdapter.getFilter().filter(s);
                            break;
                        case 2:
                            f_groups.sampleAdapter.getFilter().filter(s);
                            break;
                    }
                    return true;
                }
            });
            search.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override

                public boolean onClose()
                {
                    try {
                        closeSearch();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_search:
                {
                isSearch = true;
                searchToolbar.setVisibility(View.VISIBLE);
                    try {
                        prepareActionBar(searchToolbar);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    supportInvalidateOptionsMenu();
                return true;
            }
            case android.R.id.home:
                try {
                    closeSearch();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.action_notif:
                {
                //cart_count++;
                //Menu menu =null;
                //onCreateOptionsMenu(menu);
               // menuItem[1].setIcon(Converter.convertLayoutToImage(ActivityMain.this,cart_count,R.drawable.ic_notif));
                    clearNotificationAsyncTask=(ClearNotificationAsyncTask) new ClearNotificationAsyncTask(context).execute();
                    NotificationCounter=null;
                Intent intent=new Intent(ActivityMain.this,UserNotificationsActivity.class);
                startActivity(intent);

                //Snackbar.make(parent_view, "Notifications Clicked", Snackbar.LENGTH_SHORT).show();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closeSearch() throws Exception
    {
         if (isSearch)
            {
            isSearch = false;
            if (viewPager.getCurrentItem() == 0)
            {
                //f_message.mAdapter.getFilter().filter("");
            } else
                {
                //f_contact.mAdapter.getFilter().filter("");
            }
            prepareActionBar(toolbar);
            searchToolbar.setVisibility(View.GONE);
            supportInvalidateOptionsMenu();
            }


    }


    private long exitTime = 0;

    public void doExitApp() throws Exception
    {

                if ((System.currentTimeMillis() - exitTime) > 2000)
                {
                    Toast.makeText(this, R.string.press_again_exit_app, Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                }
                else {
                    //finish();
                }


    }

    @Override
    public void onBackPressed()
    {
       try
       {
           if (getFragmentManager().getBackStackEntryCount() > 0)
           {
           getFragmentManager().popBackStack();
           }
            else
            {
           super.onBackPressed();
            }
            doExitApp();
       }
       catch(Exception e)
       {
            e.printStackTrace();
       }

    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId)throws Exception {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.counter_menuitem_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    private void doIncrease()throws Exception {
        count++;
        invalidateOptionsMenu();
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        String abc=null;
        String ActiveModal=null;
        try {
            temp.savePreferences(context.getApplicationContext(),"ActiveModal",ActiveModal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            temp.savePreferences(context.getApplicationContext(),"OnlineFriendIds",abc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // unregisterNetworkChanges();
    }

}
