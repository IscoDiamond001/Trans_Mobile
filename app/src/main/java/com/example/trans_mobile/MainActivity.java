package com.example.trans_mobile;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.trans_mobile.Compagnie.Compagnie;
import com.example.trans_mobile.adapters.CompagniListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CompagniListAdapter.CompagnieListClickListener {

    //Compagnie compagnie = new Compagnie(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getString(R.string.compagnies));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00FFFF")));
        connetionDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.idNotif) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                    .setSmallIcon(R.drawable.notif).setContentTitle("New notification")
                    .setAutoCancel(true);
            Intent i = new Intent(MainActivity.this, LesNotifs.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, builder.build());
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void connetionDB(){

        final String url = "http://192.168.69.59:8000/api/compagnies";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

// prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    final List<Compagnie> compagnieList = new ArrayList<>();
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i<response.length(); i++){
                                JSONObject comp = response.getJSONObject(i);

                                Compagnie compagnie = new Compagnie();
                                String cpnom = comp.getString("cpnom");
                                String adresse = comp.getString("adresse");
                                int id = comp.getInt("idCp");

                                 compagnie.setName(cpnom);
                                 compagnie.setAdresse(adresse);
                                 compagnie.setId(id);

                                compagnieList.add(compagnie);
                                initRecyclerView(compagnieList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // display response
                        Log.d("Response", response.toString());
                    }
                },
                error -> Log.d("Response", error.toString())
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }
    private void initRecyclerView(List<Compagnie>compagnieList){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CompagniListAdapter adapter = new CompagniListAdapter(compagnieList,this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(Compagnie compagnie) {

        Intent intent =  new Intent(MainActivity.this,CompagnieTrajetActivity.class);
        intent.putExtra("Compagnie", compagnie);
        startActivity(intent);
    }
}