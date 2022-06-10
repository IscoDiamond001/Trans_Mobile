package com.example.trans_mobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trans_mobile.Compagnie.Compagnie;
import com.example.trans_mobile.Compagnie.trajet;

import org.json.JSONException;
import org.json.JSONObject;

public class FormulaireActivity extends AppCompatActivity {

    private EditText idPrenom,idNom,idGenre,idEmail,idNum,idVille,idQuartier,txtPlace,idCarte,idCode,idDate;
    private TextView somme;
    Compagnie compagnie;
    trajet t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);

        t = (trajet) getIntent().getSerializableExtra("trajet");
        compagnie = (Compagnie) getIntent().getSerializableExtra("Compagnie");
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Infos client");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00FFFF")));

        idPrenom = findViewById(R.id.idPrenom);
        idNom = findViewById(R.id.idNom);
        idGenre = findViewById(R.id.idGenre);
        idEmail = findViewById(R.id.idEmail);
        idNum = findViewById(R.id.idNum);
        idVille = findViewById(R.id.idVille);
        idQuartier = findViewById(R.id.idQuartier);
        txtPlace = findViewById(R.id.txtPlace);
        somme =  findViewById(R.id.somme);
        idCarte = findViewById(R.id.idCarte);
        idCode = findViewById(R.id.idCode);
        idDate = findViewById(R.id.idDate);


        txtPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && !editable.toString().isEmpty()) calculTotal(t, Double.parseDouble(editable.toString()));
            }
        });

        TextView button = findViewById(R.id.button);

        button.setOnClickListener(view -> {
            envoi(idPrenom.getText().toString(),idNom.getText().toString(),idGenre.getText().toString(),idEmail.getText().toString(),idNum.getText().toString(),
                    idVille.getText().toString(),idQuartier.getText().toString(),txtPlace.getText().toString(),somme.getText().toString());
            PostconnetionDB(compagnie,t);
        });
    }

    @SuppressLint("DefaultLocale")
    private void calculTotal(trajet t1, Double b ){
        double total;
        total = b *t1.getPrix();
        somme.setText(String.format("%.2f XOF",total));
    }

    JSONObject jsonObject = new JSONObject();

    public void PostconnetionDB(Compagnie c,trajet t){

        int idCp = c.getId();
        int idT = t.getId();

        try {
            jsonObject.put("idAgent", "1");
            jsonObject.put("idCp", idCp);
            jsonObject.put("idT",idT);
            jsonObject.put("prenom", idPrenom.getText().toString().trim());
            jsonObject.put("nom", idNom.getText().toString().trim());
            jsonObject.put("numTel", idNum.getText().toString().trim());
            jsonObject.put("email", idEmail.getText().toString().trim());
            jsonObject.put("genre", idGenre.getText().toString().trim());
            jsonObject.put("quartier", idQuartier.getText().toString().trim());
            jsonObject.put("ville", idVille.getText().toString().trim());
            jsonObject.put("place", txtPlace.getText().toString().trim());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String url = "http://192.168.69.59:8000/api/formulaire";
        RequestQueue queue = Volley.newRequestQueue(FormulaireActivity.this);

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    // display response
                    Log.d("Response", response.toString());
                },
                error -> Log.d("Response", error.toString())
        );

// add it to the RequestQueue
        queue.add(getRequest);

        Payement(t);
    }


    JSONObject jObject = new JSONObject();

    public void Payement(trajet t){
        double d = Double.parseDouble(txtPlace.getText().toString())*t.getPrix();
        try {
            jObject.put("somme",d);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String url = "http://192.168.69.59:8000/api/payement";
        RequestQueue queue = Volley.newRequestQueue(FormulaireActivity.this);

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, jObject,
                response -> {
                    // display response
                    Toast.makeText(FormulaireActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Log.d("Response", response.toString());
                },
                error -> {
                    Log.d("Response", error.toString());
                    Toast.makeText(FormulaireActivity.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();

                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }

    private void envoi(String prenom,String nom,String genre,String email,String numTel,String ville,String quartier,String place,String somme){
        Client client = new Client();
        client.setPrenom(prenom);
        client.setNom(nom);
        client.setGenre(genre);
        client.setEmail(email);
        client.setNumTel(numTel);
        client.setVille(ville);
        client.setQuartier(quartier);
        client.setPlace(place);
        client.setSomme(somme);

        if (TextUtils.isEmpty(idPrenom.getText().toString())){
            idPrenom.setError(getString(R.string.vv_prenom));
            return;
        }else if (TextUtils.isEmpty(idNom.getText().toString())){
            idNom.setError(getString(R.string.vv_nom));
            return;
        }else if (TextUtils.isEmpty(idGenre.getText().toString())){
            idGenre.setError(getString(R.string.vv_genre));
            return;
        }else if (TextUtils.isEmpty(idEmail.getText().toString())){
            idEmail.setError(getString(R.string.vv_email));
            return;
        }else if (TextUtils.isEmpty(idNum.getText().toString())){
            idNum.setError(getString(R.string.vv_numTel));
            return;
        }else if (TextUtils.isEmpty(idVille.getText().toString())){
            idVille.setError(getString(R.string.vv_ville));
            return;
        }else if (TextUtils.isEmpty(idQuartier.getText().toString())){
            idQuartier.setError(getString(R.string.vv_quartier));
            return;
        }else if (TextUtils.isEmpty(txtPlace.getText().toString())){
            txtPlace.setError(getString(R.string.v_nb_place));
            return;
        }

        envoi2();

        System.out.println();

        Intent i = new Intent(FormulaireActivity.this,PayementActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("serialzable", client);
        i.putExtras(bundle);
        startActivity(i);
    }

    private void envoi2(){
        if (TextUtils.isEmpty(idCarte.getText().toString())){
            idCarte.setError(getString(R.string.vv_numCard));
        }else if (TextUtils.isEmpty(idCode.getText().toString())){
            idCode.setError(getString(R.string.vv_pinCard));
        }else if (TextUtils.isEmpty(idDate.getText().toString())){
            idDate.setError(getString(R.string.vv_dlcard));
        }
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