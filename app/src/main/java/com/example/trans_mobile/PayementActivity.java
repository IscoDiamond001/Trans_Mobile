package com.example.trans_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class PayementActivity extends AppCompatActivity {
    private Client client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payement);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getString(R.string.payement));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00FFFF")));

        TextView Prenom = findViewById(R.id.prenom);
        TextView Nom = findViewById(R.id.nom);
        TextView Genre = findViewById(R.id.genre);
        TextView Email = findViewById(R.id.email);
        TextView NumTel = findViewById(R.id.num);
        TextView Ville = findViewById(R.id.ville);
        TextView Quartier = findViewById(R.id.quartier);
        TextView Place = findViewById(R.id.place);
        TextView Somme = findViewById(R.id.somme);
        client = (Client) getIntent().getSerializableExtra("serialzable");
        String prenom = client.getPrenom();
        String nom = client.getNom();
        String genre = client.getGenre();
        String email = client.getEmail();
        String numTel = client.getNumTel();
        String ville = client.getVille();
        String quartier = client.getQuartier();
        String place = client.getPlace();
        String somme = client.getSomme();
        Prenom.setText(prenom);
        Nom.setText(nom);
        Genre.setText(genre);
        Email.setText(email);
        NumTel.setText(numTel);
        Ville.setText(ville);
        Quartier.setText(quartier);
        Place.setText(place);
        Somme.setText(somme);

    }

    @Override
    protected void onPause() {
        super.onPause();
        client = null;
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