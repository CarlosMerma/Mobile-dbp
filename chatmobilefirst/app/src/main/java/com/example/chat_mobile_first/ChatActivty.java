package com.example.chat_mobile_first;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class ChatActivty extends AppCompatActivity {
    private int userFromId;
    private int userToId;
    private String username;
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.userFromId = getIntent().getExtras().getInt("userFromId");
        this.userToId = getIntent().getExtras().getInt("userToId");
        this.username = getIntent().getExtras().getString("username");
        this.mRecyclerView = findViewById(R.id.messages_recycler_view);
        setTitle(this.username);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getMessages();
    }

    public Activity getActivity(){ return this; }

    public void getMessages(){
        String url = "http://10.0.2.2:8080/messages/<user_from>/<user_to>";
        url = url.replace("<user_from>", Integer.toString(userFromId));
        url = url.replace("<user_to>", Integer.toString(userToId));
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                new JSONArray(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // TODO
                        mAdapter = new MessageAdapter(response, getActivity(), userFromId);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}