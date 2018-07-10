package com.app.sample.chatting.AdapterClasses;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.ViewModelClasses.FriendsViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewUserAdapter extends BaseAdapter {

    private List<FriendsViewModel> msg=new ArrayList<>();
    TemporaryStorageSharedPreferences temporary=new TemporaryStorageSharedPreferences();
    private Context ctx;
    private SparseBooleanArray selectedItems;
    // for item click listener
    //private OnItemClickListener mOnItemClickListener;
    private boolean clicked = false;
    private LayoutInflater inflater;

    public NewUserAdapter(Context ctx, ArrayList<FriendsViewModel> newuserViewModels) {
        this.msg = newuserViewModels;
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
            FriendsViewModel userViewModel=(FriendsViewModel) getItem(position);
            if (convertView == null)
            {
                try{
                    convertView = inflater.inflate(R.layout.newuser, null);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
            TextView Friends=(TextView)convertView.findViewById(R.id.FriendName) ;
            Friends.setText(String.valueOf(userViewModel.getName()));
            TextView friendid = (TextView) convertView.findViewById(R.id.FriendEmail);
            friendid.setText(String.valueOf(userViewModel.getEmail()));
            TextView friendname = (TextView) convertView.findViewById(R.id.FriendPhone);
            friendname.setText(String.valueOf(userViewModel.getPhoneNo()));
            TextView friendId = (TextView) convertView.findViewById(R.id.FriendID);
            friendId.setText(String.valueOf(userViewModel.getId()));



        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return convertView;
    }
    public void setUser(List<FriendsViewModel> data)
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
