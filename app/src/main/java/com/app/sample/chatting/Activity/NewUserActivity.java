package com.app.sample.chatting.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.app.sample.chatting.AsyncTaskLoader.NewUserLoader;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiver.CheckConnection;
import com.app.sample.chatting.R;
import com.app.sample.chatting.ViewModelClasses.FriendsViewModel;
import com.app.sample.chatting.adapter.NewUserAdapter;
import com.app.sample.chatting.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static android.widget.GridLayout.HORIZONTAL;

public class NewUserActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<FriendsViewModel>> {
    Context context;

    private ListView listView;
   private View parent_view;
    private NewUserAdapter mAdapter;
    private ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        this.context=getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        listView = (ListView) findViewById(R.id.listview);
        getLoaderManager().initLoader(30,null,this).forceLoad();
        listView.setAdapter(mAdapter);
        listView.requestFocus();
        listView.setDividerHeight(2);
        registerForContextMenu(listView);
        mAdapter = new NewUserAdapter(this, new ArrayList<FriendsViewModel>());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Email=((TextView)view.findViewById(R.id.FriendEmail)).getText().toString();
                String Name=((TextView)view.findViewById(R.id.FriendName)).getText().toString();
                String Phone=((TextView)view.findViewById(R.id.FriendPhone)).getText().toString();
                String UnknownUserId=((TextView)view.findViewById(R.id.FriendID)).getText().toString();
                Long ID= Long.valueOf(UnknownUserId);
                Intent intent=new Intent(NewUserActivity.this,ActivityFriendDetails.class);
                intent.putExtra("Email",Email);
                intent.putExtra("Name",Name);
                intent.putExtra("Phone",Phone);
                intent.putExtra("ReceiverId",ID);

                //checkFriendShipStatusAsyncTask=(CheckFriendShipStatusAsyncTask) new CheckFriendShipStatusAsyncTask(context,intent,ActivityFriendDetails.this).execute(UnknownUserId);
                startActivity(intent);
            }
        });
    }

    @Override
    public android.content.Loader<List<FriendsViewModel>> onCreateLoader(int id, Bundle args) {
        return new NewUserLoader(context);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<FriendsViewModel>> loader, List<FriendsViewModel> data) {
        mAdapter.setUser(data);
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<FriendsViewModel>> loader) {

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
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

}


