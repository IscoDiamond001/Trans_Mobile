package com.example.trans_mobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LesNotifs extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_les_notifs);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getString(R.string.notifications));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00FFFF")));
        actionBar.setDisplayHomeAsUpEnabled(true);

        messageContent();
    }

    public void messageContent(){

        final String url = "http://192.168.69.59:8000/api/messages";
        RequestQueue queue = Volley.newRequestQueue(LesNotifs.this);

// prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    final List<Message>messages = new ArrayList<>();
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i<response.length(); i++){
                                JSONObject comp = response.getJSONObject(i);

                                Message message = new Message();
                                String content = comp.getString("contenu");
                                int id = comp.getInt("idM");

                                message.setContenu(content);
                                message.setId(id);

                                messages.add(message);

                                ArrayAdapter<Message> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, messages);
                                listview = findViewById(R.id.list);
                                listview.setAdapter(adapter);
                                listview.setOnItemClickListener(this::onItemClick);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // display response
                        Log.d("Response", response.toString());
                    }

                    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Message m = messages.get(i);
                        Intent intent = new Intent(LesNotifs.this, messageDetailActivity.class);
                        intent.putExtra("message", m);
                        startActivity(intent);
                    }
                },
                error -> Log.d("Response", error.toString())
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}