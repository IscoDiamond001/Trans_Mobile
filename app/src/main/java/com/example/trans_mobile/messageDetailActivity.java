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

public class messageDetailActivity extends AppCompatActivity {

    Message message;
    TextView contenu;

    public messageDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getString(R.string.notifications));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00FFFF")));
        actionBar.setDisplayHomeAsUpEnabled(true);

        contenu = findViewById(R.id.contenu);

        message = (Message) getIntent().getSerializableExtra("message");

        contenu.setText(message.getContenu());

        message = (Message) getIntent().getSerializableExtra("message");

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