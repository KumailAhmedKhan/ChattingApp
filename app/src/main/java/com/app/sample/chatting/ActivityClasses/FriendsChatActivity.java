package com.app.sample.chatting.ActivityClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.app.sample.chatting.R;
import com.app.sample.chatting.AdapterClasses.FriendsListAdapter;

public class FriendsChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public FriendsListAdapter mAdapter;
    private ProgressBar progressBar;
    View view;
    Context context;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_chat);
    }
}
