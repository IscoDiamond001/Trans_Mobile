package com.example.trans_mobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.trans_mobile.Compagnie.Compagnie;
import com.example.trans_mobile.Compagnie.trajet;
import com.example.trans_mobile.adapters.TrajetListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompagnieTrajetActivity extends AppCompatActivity implements TrajetListAdapter.trajetListClickListener {

    Compagnie compagnie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compagnie_trajet);

        compagnie = (Compagnie) getIntent().getSerializableExtra("Compagnie");
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getString(R.string.trajets));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00FFFF")));


        TrajetconnetionDB(compagnie);

    }


    public void TrajetconnetionDB(Compagnie c1){
        int c = c1.getId();

        final String url = "http://192.168.69.59:8000/api/trajets/"+c;
        RequestQueue queue = Volley.newRequestQueue(CompagnieTrajetActivity.this);

// prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    final List<trajet> trajetList = new ArrayList<>();
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i<response.length(); i++){
                                trajet trajet = new trajet();
                                JSONObject Trajet =response.getJSONObject(i);

                                int id = Trajet.getInt("idT");
                                String tj = Trajet.getString("trajet");
                                String heure = Trajet.getString("heure");
                                double prix = Trajet.getDouble("prix");

                                trajet.setId(id);
                                trajet.setName(tj);
                                trajet.setHeure(heure);
                                trajet.setPrix(prix);

                                trajetList.add(trajet);
                                initRecyclerView(trajetList);
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

    private void initRecyclerView(List<trajet> trajetList){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        TrajetListAdapter trajetListAdapter = new TrajetListAdapter(trajetList, this);
        recyclerView.setAdapter(trajetListAdapter);
    }


    @Override
    public void onItemClick(trajet trajet) {
        Intent intent =  new Intent(CompagnieTrajetActivity.this,FormulaireActivity.class);
        intent.putExtra("trajet", trajet);
        intent.putExtra("Compagnie", compagnie);
        startActivity(intent);
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