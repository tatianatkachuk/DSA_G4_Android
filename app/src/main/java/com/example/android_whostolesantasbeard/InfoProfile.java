package com.example.android_whostolesantasbeard;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InfoProfile extends AppCompatActivity {

    ImageView backButton;
    IWSSBService service;
    TextView emailVal;
    TextView userNameVal;
    TextView idVal;
    TextView coinsVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoprofile);

        service = APIClient.getClient().create(IWSSBService.class);

        String username = getIntent().getExtras().getString("username");
        String mail = getIntent().getExtras().getString("mail");
        String id = getIntent().getExtras().getString("id");
        String coins = getIntent().getExtras().getString("coins");

        backButton = (ImageView) findViewById(R.id.backButton);
        emailVal = (TextView) findViewById(R.id.emailValue);
        userNameVal = (TextView) findViewById(R.id.nameVal);
        idVal = (TextView) findViewById(R.id.idValue);
        coinsVal = (TextView) findViewById(R.id.coinsValue);

        emailVal.setText(mail);
        userNameVal.setText(username);
        idVal.setText(id);
        coinsVal.setText(coins);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {backToMain(username);}
        });
    }


    public void backToMain(String username) {
        Intent intent = new Intent(this, Main.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
