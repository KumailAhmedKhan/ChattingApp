package com.app.sample.chatting.AdapterClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sample.chatting.AsyncTaskClasses.GroupChatMessageStatusAsyncTask;
import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.ViewModelClasses.GroupChatViewModel;

import java.util.ArrayList;
import java.util.List;
public class GroupChatAdapter extends BaseAdapter {

    TemporaryStorageSharedPreferences temp=new TemporaryStorageSharedPreferences();
    private int userId;
    private GroupChatViewModel groupChatViewModel;
    private List<GroupChatViewModel> message=new ArrayList<>();
    public Context context;
    GroupChatMessageStatusAsyncTask groupChatMessageStatusAsyncTask=new GroupChatMessageStatusAsyncTask();
    BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub


            //userGroupViewModel.setDate(intent.getStringExtra("MessageDate"));
            // userGroupViewModel.set(intent.getStringExtra("MessageText"));
            String senderID=intent.getStringExtra("Message");
            groupChatViewModel.setId(Integer.parseInt(senderID));
            notifyDataSetChanged();
            //friendsRecentChatViewModel=intent.get("FriendMessage");
            //friendsRecentChatViewModel=bundle;
            // Get extra data included in the Intent
            // Frien message = intent.getBooleanExtra("CUSTOM_INTENT",false);
            // fl= message;
            // Log.d("receiver", "Got message: " + fl);
        }
    };
    public GroupChatAdapter(Context ctx,List<GroupChatViewModel> msg){
        this.context=ctx.getApplicationContext();
        this.message=msg;
    }
    @Override
    public int getCount() {
        return message.size();
    }

    @Override
    public Object getItem(int position) {
        return message.get(position);
    }

    @Override
    public long getItemId(int position) {
        return message.get(position).getId();
    }
    public void setchat(List<GroupChatViewModel> data)throws Exception{
        try{
            message.clear();
            message.addAll(data);
            notifyDataSetChanged();;
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * remove data item from messageAdapter
     *
     **/
    public void remove(int position)throws Exception
    {

        try{
            message.remove(position);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * add data item to messageAdapter
     *
     **/
    public void add(GroupChatViewModel msg)
    throws Exception{
        try{
            message.add(msg);
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    // LocalBroadcastManager.getInstance(ctx).registerReceiver(mMessageReceiver, new IntentFilter("FriendFromGroupMessage"));
        userId= Integer.parseInt(temp.getPreferences(context,"Userdata"));
        //GroupChatViewModel chat=(GroupChatViewModel) getItem(position);
       GroupChatViewModel groupChatViewModel=(GroupChatViewModel) getItem(position);
       final GroupChatAdapter.ViewHolder holder;
        if(convertView == null){

                holder 				= new GroupChatAdapter.ViewHolder();
                convertView			= LayoutInflater.from(context).inflate(R.layout.row_group_details, parent, false);
                holder.senderName 		= (TextView) convertView.findViewById(R.id.sender);
                holder.time 		= (TextView) convertView.findViewById(R.id.text_time);
                holder.message 		= (TextView) convertView.findViewById(R.id.text_content);
                holder.lyt_thread 	= (CardView) convertView.findViewById(R.id.lyt_thread);
                holder.lyt_parent 	= (LinearLayout) convertView.findViewById(R.id.lyt_parent);
                holder.image_status	= (ImageView) convertView.findViewById(R.id.imagegreenpoint);
                convertView.setTag(holder);


        }else{

                holder = (GroupChatAdapter.ViewHolder) convertView.getTag();


        }
        Log.d("Message ID",groupChatViewModel.getId()+"Dynamic Message");
        Log.d("Message ID",groupChatMessageStatusAsyncTask.groupChatViewModel.getMessageID()+"Static Message");
        Log.d("Message List",groupChatMessageStatusAsyncTask.group+"Static Message");
       // for(int i=0;i<groupChatMessageStatusAsyncTask.group.size();i++)
        {
        if(groupChatViewModel.getSenderId()!=userId)
        {
            //if(chat.getMessageID()==groupChatMessageStatusAsyncTask.group.get(i).getMessageID() && chat.getMessageStatus==2)
               /* if((chat.getMessageID()==(groupChatMessageStatusAsyncTask.group.get(i).getMessageID()))
                        || (chat.getContent().contains(groupChatMessageStatusAsyncTask.group.get(i).getContent()))
                        && chat.getDate().contains(groupChatMessageStatusAsyncTask.group.get(i).getDate()) )*/
                            //if((groupChatViewModel.getContent().contains(groupChatMessageStatusAsyncTask.group.get(i).getContent())))

                                try{
                                    holder.image_status.setVisibility(View.INVISIBLE);
                                    holder.senderName.setVisibility(View.VISIBLE);
                                    holder.senderName.setText(groupChatViewModel.getSenderName());
                                    holder.senderName.setTextColor(Color.parseColor("#000000"));
                                    holder.message.setText(groupChatViewModel.getContent());
                                    holder.time.setText(groupChatViewModel.getCreatedDate());
                                    holder.message.setTextColor(Color.parseColor("#FFFFFF"));
                                   // holder.image_status.setVisibility(View.VISIBLE);
                                    holder.lyt_parent.setPadding(15, 10, 100, 10);
                                    holder.lyt_parent.setGravity(Gravity.LEFT);
                                    holder.time.setTextColor(Color.parseColor("#FFFFFF"));
                                    holder.lyt_thread.setCardBackgroundColor(context.getResources().getColor(R.color.blue_fb));
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }






        }
        else
            {


                try
                {
                    holder.image_status.setVisibility(View.INVISIBLE);
                    holder.senderName.setText(groupChatViewModel.getSenderName());
                    holder.message.setText(groupChatViewModel.getContent());
                    holder.time.setText(groupChatViewModel.getCreatedDate());
                    holder.senderName.setVisibility(View.GONE);
                    holder.lyt_parent.setPadding(100, 10, 15, 10);
                    holder.lyt_parent.setGravity(Gravity.RIGHT);
                    holder.message.setTextColor(Color.parseColor("#000000"));
                    holder.time.setTextColor(Color.parseColor("#000000"));
                    holder.lyt_thread.setCardBackgroundColor(Color.parseColor("#E6E6E6"));
                    //holder.image_status.setImageResource(android.R.color.transparent);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }



            }
        }
        return convertView;
    }
    private static class ViewHolder{

        TextView senderName;
        TextView time;
        TextView message;
        LinearLayout lyt_parent;
        CardView lyt_thread;
        ImageView image_status;
    }


}
