package com.app.sample.chatting.FragmentClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.sample.chatting.ActivityClasses.ActivityGroupDetails;
import com.app.sample.chatting.ActivityClasses.ActivitySplash;
import com.app.sample.chatting.AsyncTaskLoaderClasses.UserGroupLoader;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.ViewModelClasses.UserGroupViewModel;
import com.app.sample.chatting.AdapterClasses.GroupGridAdapter;
import com.app.sample.chatting.widgetClasses.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class GroupsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<UserGroupViewModel>> {

    RecyclerView recyclerView;

    private ProgressBar progressBar;
    private View view;
    private LinearLayout lyt_not_found;
    Context context;
    public GroupGridAdapter sampleAdapter;
    private ProgressDialog progressDialog;

    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        try
        {
            view = inflater.inflate(R.layout.fragment_groups, container, false);
            context= inflater.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewgroup);
            progressBar  = (ProgressBar) view.findViewById(R.id.progressBar);
            lyt_not_found   = (LinearLayout) view.findViewById(R.id.lyt_not_foundgroup);
            SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
            {
                @Override
                public void onRefresh()
                {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.groupfragment, new GroupsFragment())
                            .commit();
                }
            });
            //progressDialog= DialogueUtil.showProgressDialog(getActivity(),"Loading");
            // progressDialog=new ProgressDialog();
            //LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), Tools.getGridSpanCount(getActivity()));
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new
                    DividerItemDecoration(getActivity(),
                    DividerItemDecoration.VERTICAL_LIST));

            CheckConnection checkConnection = new CheckConnection();
            final int checkconnectionflag=checkConnection.connnectioncheck(context);
            if(checkconnectionflag==1)
            {
                getLoaderManager().initLoader(1993,null,this).forceLoad();
            }
            else {
                Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context,ActivitySplash.class));
            }



            sampleAdapter=new GroupGridAdapter(getActivity(),new ArrayList<UserGroupViewModel>());
            recyclerView.setAdapter(sampleAdapter);
            sampleAdapter.setOnItemClickListener(new GroupGridAdapter.OnItemClickListener()
            {
                @Override
                public void onItemClick(View view, UserGroupViewModel obj, int position)
                {
                    //Toast.makeText(context,"Hello Clicked",Toast.LENGTH_LONG).show();
                    String GroupID= String.valueOf(obj.UserId);
                    String GroupName=String.valueOf(obj.UserName);
                    //temp.savePreferences(context,"GroupId",ID);
                    //Log.d("Groupid",ID+"");
                    Intent notificationIntent = new Intent(getActivity(),ActivityGroupDetails.class);
                    notificationIntent.putExtra("KEY_GID",GroupID);
                    notificationIntent.putExtra("KEY_GNAME","Test");
                    getActivity().startActivity(notificationIntent);
                    //ActivityGroupDetails.navigate((ActivityMain) getActivity(), view.findViewById(R.id.lyt_parent), obj,GroupID,GroupName);


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
        try
        {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume()
    {
        try
        {
            sampleAdapter.notifyDataSetChanged();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        super.onResume();
    }


    @Override
    public Loader<List<UserGroupViewModel>> onCreateLoader(int id, Bundle args)
    {
        return new UserGroupLoader(context);
    }

    @Override
    public void onLoadFinished(Loader<List<UserGroupViewModel>> loader, List<UserGroupViewModel> data)
    {
        try
        {
            sampleAdapter.setGroup(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<UserGroupViewModel>> loader)
    {

    }
}
