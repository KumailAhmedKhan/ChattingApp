package com.app.sample.chatting.ActivityClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.R;
import com.app.sample.chatting.ViewModelClasses.FriendsViewModel;
import com.app.sample.chatting.AdapterClasses.FriendsListAdapter;
import com.app.sample.chatting.ViewModelClasses.Data.Constant;
import com.app.sample.chatting.widgetClasses.DividerItemDecoration;

public class ActivitySelectFriend extends AppCompatActivity {

    private ActionBar actionBar;
    private RecyclerView recyclerView;
    private FriendsListAdapter mAdapter;
    private SearchView search;
    private BroadcastReceiver broadcastReceiver;
    Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_chat);
		try
        {
            broadcastReceiver=new CheckConnection();
            registerNetworkBroadcast();
            initToolbar();
            initComponent();
            // specify an adapter (see also next example)
            mAdapter = new FriendsListAdapter(this, Constant.getFriendsData(this));
            recyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new FriendsListAdapter.OnItemClickListener()
            {
                @Override
                public void onItemClick(View v, FriendsViewModel obj, int position)
                {
                    //ActivityChatDetails.navigate(ActivitySelectFriend.this, v.findViewById(R.id.lyt_parent), obj, null);
                }
            });

            // for system bar in lollipop
//        Tools.systemBarLolipop(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

	}

    private void initComponent()throws Exception
    {
	    try
        {
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            // use a linear layout manager
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        }
        catch (Exception e)
        {
	        e.printStackTrace();
        }

    }

    public void initToolbar()throws Exception
    {
	    try
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setSubtitle(Constant.getFriendsData(this).size()+" friends");
	    }
	    catch (Exception e)
        {
	        e.printStackTrace();
        }

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new_chat, menu);
        search = (SearchView) menu.findItem(R.id.action_search1).getActionView();
        search.setIconified(false);
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
                mAdapter.getFilter().filter(s);
                return true;
            }
        });
        search.onActionViewCollapsed();
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_search:
                {
                // this do magic
                supportInvalidateOptionsMenu();
                return true;
            }
        }
        return false;
    }
    private void registerNetworkBroadcast()throws Exception
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
        catch(Exception e )
        {
	        e.printStackTrace();
        }

    }
}
