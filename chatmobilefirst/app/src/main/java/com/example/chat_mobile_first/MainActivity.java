package com.example.chat_mobile_first;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showMessage(String string) {
        Toast.makeText(this,string,Toast.LENGTH_LONG).show();

    }

    public void goContactsActivity(String username , int id) {
        Intent intent = new Intent(this,ContactsActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void onLoginClicked(View view) {
        //TODO implement Login
        EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        //Construir Json message
        Map<String, String> message = new HashMap<String, String>();
        message.put("username", username);//{"username":"luise"}
        message.put("password", password);//{"username" : "luise" , "password" : "1234"}

        JSONObject jsonMessage = new JSONObject(message);
        Toast.makeText(this, jsonMessage.toString(), Toast.LENGTH_LONG).show();

        //Construir request object
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8080/authenticate",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO when ok response
                        showMessage("welcome!");
                        showMessage(response.toString());
                        //Opening a new activity contacts
                        try {
                            String username = response.getString("username");
                            int id = response.getInt("id");
                            goContactsActivity(username,id);
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        showMessage("SORRY:(");
                        showMessage(error.getMessage());
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    public void onRegisterClicked(View view){

    }
}
