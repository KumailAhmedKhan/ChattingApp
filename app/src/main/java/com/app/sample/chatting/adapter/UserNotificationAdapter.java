package com.app.sample.chatting.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.app.sample.chatting.Activity.UserNotificationsActivity;
import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.ViewModelClasses.UserNotificationViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserNotificationAdapter extends BaseAdapter {
    private List<UserNotificationViewModel> msg=new ArrayList<>();
    TemporaryStorageSharedPreferences temporary=new TemporaryStorageSharedPreferences();
    private Context ctx;
    private SparseBooleanArray selectedItems;
    // for item click listener
    //private OnItemClickListener mOnItemClickListener;
    private boolean clicked = false;
    private LayoutInflater inflater;

    public UserNotificationAdapter(Context ctx, ArrayList<UserNotificationViewModel> userNotificationViewModels) {
        this.msg = userNotificationViewModels;
        inflater = LayoutInflater.from(ctx);
    }


    @Override
    public int getCount() {
        return msg.size();
    }

    @Override
    public Object getItem(int position) {
        return msg.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{
            UserNotificationViewModel userNotificationViewModel=(UserNotificationViewModel) getItem(position);
            if (convertView == null)
            {
                try{
                    convertView = inflater.inflate(R.layout.notificationdata, null);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
            TextView friendemail=(TextView)convertView.findViewById(R.id.FriendName) ;
            friendemail.setText(String.valueOf(userNotificationViewModel.getSenderName()));
            TextView friendid = (TextView) convertView.findViewById(R.id.Date);
            friendid.setText(String.valueOf(userNotificationViewModel.getNotificationCreatedDate()));
            TextView friendname = (TextView) convertView.findViewById(R.id.Message);
            friendname.setText(String.valueOf(userNotificationViewModel.getText()));
            TextView friendemai=(TextView)convertView.findViewById(R.id.FriendID) ;
            friendemai.setText(String.valueOf(userNotificationViewModel.getSenderId()));
            TextView friendi = (TextView) convertView.findViewById(R.id.NotificationType);
            friendi.setText(String.valueOf(userNotificationViewModel.getNotificationType()));



        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return convertView;
    }
    public void setUserserNotification(List<UserNotificationViewModel> data)
    {
        try{
            msg.addAll(data);
            notifyDataSetChanged();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
