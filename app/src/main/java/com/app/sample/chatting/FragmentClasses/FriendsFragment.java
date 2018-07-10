package com.app.sample.chatting.FragmentClasses;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.sample.chatting.ActivityClasses.ActivityChatDetails;

import com.app.sample.chatting.ActivityClasses.ActivitySplash;
import com.app.sample.chatting.AsyncTaskLoaderClasses.FriendsLoader;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.R;
import com.app.sample.chatting.ViewModelClasses.FriendsViewModel;
import com.app.sample.chatting.AdapterClasses.FriendsListAdapter;
import com.app.sample.chatting.widgetClasses.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<FriendsViewModel>>{

    private RecyclerView recyclerView;
    public FriendsListAdapter mAdapter;
    private ProgressBar progressBar;
    View view;
    Context context;
    private  ProgressDialog progressDialog;
    FriendsViewModel friendsViewModel=new FriendsViewModel();
    BroadcastReceiver FriendRequestNotification = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub
            String name=intent.getStringExtra("SENDERNAME");
            String Id=intent.getStringExtra("SENDERID");
            Long ID= Long.valueOf(Id);

            if(ID==0 && name.equals("SLC"))
            {
               /* getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.friendfragment, new FriendsFragment())
                        .commit();
                        */
            }
            else{
                friendsViewModel.setName(name);
                friendsViewModel.setId(ID);
                mAdapter.AddFriend(friendsViewModel);
                mAdapter.notifyDataSetChanged();
            }


        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        try
        {
            LocalBroadcastManager.getInstance(context).registerReceiver(FriendRequestNotification, new IntentFilter("FriendsAddInAdapter"));
            view = inflater.inflate(R.layout.fragment_friends, container, false);
            this.context= inflater.getContext();
            getLoaderManager().initLoader(1997, null, this).forceLoad();
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewfrnd);
            progressBar  = (ProgressBar) view.findViewById(R.id.progressBar);
            SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
            {
                @Override
                public void onRefresh()
                {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.friendfragment, new FriendsFragment())
                            .commit();
                }
            });
            //progressDialog= DialogueUtil.showProgressDialog(getActivity(),"Loading");
            // use a linear layout manager
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

            /////////////////////////////////////////////////////////////////
      /*  getLoaderManager().initLoader(0, null,  this);
        getLoaderManager().getLoader(0).startLoading();*/
            CheckConnection checkConnection = new CheckConnection();
            final int checkconnectionflag=checkConnection.connnectioncheck(context);
            if(checkconnectionflag==1)
            {
                getLoaderManager().initLoader(1990,null,this).forceLoad();
            }
            else {
                Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context,ActivitySplash.class));
            }



            // specify an adapter (see also next example)
            // mAdapter =  new FriendsAdapter(context, new ArrayList<FriendsViewModel>());
            mAdapter = new FriendsListAdapter(context, new ArrayList<FriendsViewModel>());
            recyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new FriendsListAdapter.OnItemClickListener()
            {
                @Override
                public void onItemClick(View v, FriendsViewModel obj, int position)
                {
                    // ActivityFriendDetails.navigate((ActivityMain) getActivity(), v.findViewById(R.id.image), obj);
                    String SenderID,SenderName;
                    SenderID= String.valueOf(obj.getId());
                    SenderName=String.valueOf(obj.getName());
                    //temp.savePreferences(context,"SenderID",SenderID);
                    // temp.savePreferences(context,"SenderName",SenderName);
                    ///clicklistener for chat

                    Intent notificationIntent = new Intent(getActivity(),ActivityChatDetails.class);
                    notificationIntent.putExtra("KEY_FID",SenderID);
                    notificationIntent.putExtra("KEY_FNAME",SenderName);
                    getActivity().startActivity(notificationIntent);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return view;
    }

    public void onRefreshLoading()
    {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onResume()
    {
        try
        {
            mAdapter.notifyDataSetChanged();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    public Loader<List<FriendsViewModel>> onCreateLoader(int id, Bundle args)
    {
        return new FriendsLoader(context);

    }

    @Override
    public void onLoadFinished(Loader<List<FriendsViewModel>> loader, List<FriendsViewModel> data)
    {
        try
        {
            mAdapter.setFriends(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<FriendsViewModel>> loader)
    {

    }
    public View getfriendFragment(View views)
    {
        return views.findViewById(R.id.lyt_parent);
    }


}
