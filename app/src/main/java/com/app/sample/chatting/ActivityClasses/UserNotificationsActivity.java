package com.app.sample.chatting.ActivityClasses;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.app.sample.chatting.AsyncTaskClasses.UserNotificationAsyncTaskLoader;
import com.app.sample.chatting.R;
import com.app.sample.chatting.ViewModelClasses.UserNotificationViewModel;
import com.app.sample.chatting.AdapterClasses.UserNotificationAdapter;
import com.app.sample.chatting.widgetClasses.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.LinearLayoutCompat.HORIZONTAL;

public class UserNotificationsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<UserNotificationViewModel>> {

    private Context context;
    private ProgressDialog progressDialog;
    public ListView listView;
    private LinearLayoutManager mLayoutManager;
    public UserNotificationAdapter mAdapter;
    private ProgressBar progressBar;
    private ActionMode actionMode;
    private List<UserNotificationViewModel> items = new ArrayList<>();
    //private GestureDetectorCompat gestureDetector;
    View view;
    private ActionBar actionBar;
    private View parent_view;
    //Context context;
    //private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notifications);
        try
        {
            parent_view = findViewById(android.R.id.content);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            progressDialog=new ProgressDialog(this);
            listView = (ListView) findViewById(R.id.listview);
            getLoaderManager().initLoader(1998,null,this).forceLoad();
            listView.setAdapter(mAdapter);
            listView.requestFocus();
            listView.setDividerHeight(2);
            DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
            registerForContextMenu(listView);
            mAdapter = new UserNotificationAdapter(this, new ArrayList<UserNotificationViewModel>());
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String Name=((TextView)view.findViewById(R.id.FriendName)).getText().toString();
                    String Message=((TextView)view.findViewById(R.id.Message)).getText().toString();
                    String ID=((TextView)view.findViewById(R.id.FriendID)).getText().toString();
                    String Date=((TextView)view.findViewById(R.id.Date)).getText().toString();
                    Long Id= Long.valueOf(ID);
                    String NotificationType=((TextView)view.findViewById(R.id.NotificationType)).getText().toString();
                    if(NotificationType.equals("2")||NotificationType.equals("1"))
                    {
                        Intent intent = new Intent(UserNotificationsActivity.this, ActivityFriendDetails.class);
                        // intent.putExtra("Email",Email);
                        intent.putExtra("Name", Name);
                       // intent.putExtra("Phone",Phone);
                        intent.putExtra("ReceiverId", Id);
                        startActivity(intent);
                        //checkFriendShipStatusAsyncTask = (CheckFriendShipStatusAsyncTask) new CheckFriendShipStatusAsyncTask(getApplicationContext(), intent).execute(ID);
                    }


                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public Loader<List<UserNotificationViewModel>> onCreateLoader(int id, Bundle args)
    {
       // progressDialog.setMessage("Loading");
       // progressDialog.show();
        return new UserNotificationAsyncTaskLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<List<UserNotificationViewModel>> loader, List<UserNotificationViewModel> data)
    {
        try
        {
            mAdapter.setUserserNotification(data);
            progressDialog.dismiss();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<UserNotificationViewModel>> loader)
    {

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
