package com.app.sample.chatting.Fragments;




import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;


import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sample.chatting.Activity.ActivityChatDetails;
import com.app.sample.chatting.Activity.ActivityMain;
import com.app.sample.chatting.Activity.ActivityRegister;
import com.app.sample.chatting.Activity.ActivitySplash;
import com.app.sample.chatting.AsyncTask.ClearNotificationAsyncTask;
import com.app.sample.chatting.AsyncTask.RegisterAsyncTask;
import com.app.sample.chatting.AsyncTaskLoader.FriendRecentChatLoader;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiver.CheckConnection;
import com.app.sample.chatting.R;
import com.app.sample.chatting.ViewModelClasses.FriendsRecentChatViewModel;
import com.app.sample.chatting.adapter.FriendRecentChatAdapter;
import com.app.sample.chatting.widget.Converter;
import com.app.sample.chatting.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<FriendsRecentChatViewModel>> {

    public RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    public FriendRecentChatAdapter mAdapter;
    private ProgressBar progressBar;
    private ActionMode actionMode;
    private List<FriendsRecentChatViewModel> items = new ArrayList<>();
    private GestureDetectorCompat gestureDetector;
    View view;
    Context context;
    private ProgressDialog progressDialog;
    ClearNotificationAsyncTask clearNotificationAsyncTask=new ClearNotificationAsyncTask();

    //TemporaryStorageSharedPreferences temp=new TemporaryStorageSharedPreferences();
    public ChatsFragment()
    {

    }
    public View getChatFragment(View views)
    {
        return views.findViewById(R.id.lyt_parent);

    }
    public View getChatLayout(View views){
        return views.findViewById(R.id.lyt_not_found);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        try
        {
            view = inflater.inflate(R.layout.fragment_chat, container, false);

            // activate fragment menu
            setHasOptionsMenu(true);
            context = inflater.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewchat);
            TextView textView=(TextView) view.findViewById(R.id.EmptyTextview);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
            {
                @Override
                public void onRefresh()
                {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.chatfragment, new ChatsFragment())
                            .commit();
                }
            });
            // progressDialog= DialogueUtil.showProgressDialog(getActivity(),"Loading");
            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            CheckConnection checkConnection = new CheckConnection();
            final int checkconnectionflag=checkConnection.connnectioncheck(context);
                if(checkconnectionflag==1)
                {
                    getLoaderManager().initLoader(3,null,this).forceLoad();
                }
                else {
                    Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context,ActivitySplash.class));
                }

            //items = Constant.getChatsData(getActivity());

            // specify an adapter (see also next example)
            //mAdapter = new ChatsListAdapter(getActivity(), items);
            mAdapter = new FriendRecentChatAdapter(context, new ArrayList<FriendsRecentChatViewModel>());

            recyclerView.setAdapter(mAdapter);

            if(recyclerView==null || mAdapter.getItemCount()==0){
               // view = inflater.inflate(R.layout.fragment_chat, container, false);

                // activate fragment menu
                //setHasOptionsMenu(true);
                //context = inflater.getContext();
                //textView.setVisibility(View.VISIBLE);
                //recyclerView.
                getChatLayout(view);

            }

            mAdapter.setOnItemClickListener(new FriendRecentChatAdapter.OnItemClickListener()
            {
                @Override
                public void onItemClick(View v, FriendsRecentChatViewModel obj, int position)
                {
                    String SenderID,SenderName;
                    SenderID= String.valueOf(obj.getSenderId());
                    SenderName=String.valueOf(obj.getSenderName());
                    //temp.savePreferences(context,"SenderID",SenderID);
                    // temp.savePreferences(context,"SenderName",SenderName);
                    ///clicklistener for chat

                    Intent notificationIntent = new Intent(getActivity(),ActivityChatDetails.class);
                    notificationIntent.putExtra("KEY_FID",SenderID);
                    notificationIntent.putExtra("KEY_FNAME",SenderName);
                    getActivity().startActivity(notificationIntent);

                }
            });

            mAdapter.setOnItemLongClickListener(new FriendRecentChatAdapter.OnItemLongClickListener()
            {
                @Override
                public void onItemClick(View view, FriendsRecentChatViewModel obj, int position)
                {
                    actionMode = getActivity().startActionMode(modeCallBack);
                    myToggleSelection(position);
                }
            });

            bindView();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return view;
    }


    private void dialogDeleteMessageConfirm(final int count)
    {
        try
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Delete Confirmation");
            builder.setMessage("All chat from " + count + " selected item will be deleted?");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    mAdapter.removeSelectedItem();
                    mAdapter.notifyDataSetChanged();
                    Snackbar.make(view, "Delete " + count + " items success", Snackbar.LENGTH_SHORT).show();
                    modeCallBack.onDestroyActionMode(actionMode);
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
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
            mAdapter.notifyDataSetChanged();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void onRefreshLoading()
    {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }


    private void myToggleSelection(int idx)
    {
        try
        {
            mAdapter.toggleSelection(idx);
            String title = mAdapter.getSelectedItemCount() + " selected";
            actionMode.setTitle(title);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    private ActionMode.Callback modeCallBack = new ActionMode.Callback()
    {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
        {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.menu_multiple_select, menu);
            ((ActivityMain) getActivity()).setVisibilityAppBar(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu)
        {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
        {
            if (menuItem.getItemId() == R.id.action_delete && mAdapter.getSelectedItemCount() > 0)
            {
                dialogDeleteMessageConfirm(mAdapter.getSelectedItemCount());
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode act)
        {
            try
            {
                actionMode.finish();
                actionMode = null;
                mAdapter.clearSelections();
                ((ActivityMain) getActivity()).setVisibilityAppBar(true);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    };

    @Override
    public void onResume()
    {
        mAdapter.notifyDataSetChanged();
        super.onResume();
    }


    @Override
    public android.support.v4.content.Loader<List<FriendsRecentChatViewModel>> onCreateLoader(int id, Bundle args)
    {
      // progressDialog.show();
        return new FriendRecentChatLoader(context) ;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<FriendsRecentChatViewModel>> loader, List<FriendsRecentChatViewModel> data)
    {
        //if(progressDialog.isShowing()){
          //  progressDialog.dismiss();}
        try
        {
            mAdapter.setfchat(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<FriendsRecentChatViewModel>> loader)
    {

    }
}
