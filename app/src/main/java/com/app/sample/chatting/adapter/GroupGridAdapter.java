package com.app.sample.chatting.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sample.chatting.R;
import com.app.sample.chatting.ViewModelClasses.UserGroupViewModel;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class GroupGridAdapter extends RecyclerView.Adapter<GroupGridAdapter.ViewHolder>  {


    private List<UserGroupViewModel> userGroupViewModelList=new ArrayList<>();
    private List<UserGroupViewModel> orignalItem=new ArrayList<>();
    private List<UserGroupViewModel> filtereditem=new ArrayList<>();
    public ItemFilter itemFilter=new ItemFilter();
    private LayoutInflater layoutInflater;
    // for item click listener
    private OnItemClickListener mOnItemClickListener;
    private boolean clicked = false;
    private int lastPosition = -1;
    private Context context;
    private ItemFilter mFilter = new ItemFilter();
    public  UserGroupViewModel userGroupViewModel=new UserGroupViewModel();
    BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub


            //userGroupViewModel.setDate(intent.getStringExtra("MessageDate"));
           // userGroupViewModel.set(intent.getStringExtra("MessageText"));
            String senderID=intent.getStringExtra("Message");
            userGroupViewModel.setAdminId(Integer.parseInt(senderID));
            notifyDataSetChanged();
            //friendsRecentChatViewModel=intent.get("FriendMessage");
            //friendsRecentChatViewModel=bundle;
            // Get extra data included in the Intent
            // Frien message = intent.getBooleanExtra("CUSTOM_INTENT",false);
            // fl= message;
            // Log.d("receiver", "Got message: " + fl);
        }
    };

    public interface OnItemClickListener {
        //try{}catch(Exception e){e.printStackTrace();}
        void onItemClick(View view, UserGroupViewModel obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        try{
            this.mOnItemClickListener = mItemClickListener;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    /*public void deleteGroup(List<UserGroupViewModel> data)
    {
        userGroupViewModelList.remove(data);
    }*/
    public void setGroup(List<UserGroupViewModel> data){
        /*int a=lastPosition;
        for(int i=0;i<=lastPosition;i++){
           if (data.get(i).getId()==data.get(a+i).getId()){
        }*/
        try{
            userGroupViewModelList.clear();
            userGroupViewModelList.addAll(data);
            notifyDataSetChanged();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //notifyItemInserted(lastPosition);
    }//}
    public GroupGridAdapter(Context ctx, List<UserGroupViewModel> userGroupViewModels){

             setHasStableIds(true);
        this.userGroupViewModelList=userGroupViewModels;
        layoutInflater=LayoutInflater.from(ctx);
        this.context=ctx.getApplicationContext();
    }

    @Override
    public GroupGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver, new IntentFilter("GroupMessage"));
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_groups, parent, false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GroupGridAdapter.ViewHolder holder, final int position) {

        try{
            final UserGroupViewModel temp=userGroupViewModelList.get(position);
            String name=temp.getName();
            long ID=temp.getId();
            if(temp.getId()==userGroupViewModel.getAdminId()){
                holder.imageView.setVisibility(View.VISIBLE);
            }
            holder.title.setText(temp.getName());
            holder.title.setGravity(Gravity.LEFT);
           // holder.imageView.setImageResource(R.drawable.group2);
            setAnimation(holder.itemView, position);
            holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!clicked && mOnItemClickListener != null) {
                        clicked = true;
                        mOnItemClickListener.onItemClick(view, temp, position);
                    }
                }
            });

            clicked = false;
            // view detail message conversation
        }catch(Exception e)
        {
            e.printStackTrace();
        }



    }
    public Filter getFilter() {
        return mFilter;
    }

    @Override
    public long getItemId(int position) {
        return userGroupViewModelList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return userGroupViewModelList.size();
    }
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            try{
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    public class  ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView title;
        public LinearLayout lyt_parent;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.title);
            lyt_parent = (LinearLayout) itemView.findViewById(R.id.lyt_parent);
            imageView=(ImageView)itemView.findViewById(R.id.bullet);



        }
    }
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final List<UserGroupViewModel> list = orignalItem;
            final List<UserGroupViewModel> result_list = new ArrayList<>(list.size());

            for (int i = 0; i < list.size(); i++) {
                String str_title = list.get(i).getName();
                if (str_title.toLowerCase().contains(query)) {
                    result_list.add(list.get(i));
                }
            }

            results.values = result_list;
            results.count = result_list.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtereditem = (List<UserGroupViewModel>) results.values;
            notifyDataSetChanged();
        }

    }
   // try{}catch(Exception e){e.printStackTrace();}
}
