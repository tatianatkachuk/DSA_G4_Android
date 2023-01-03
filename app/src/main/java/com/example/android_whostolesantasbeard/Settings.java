package com.example.android_whostolesantasbeard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Settings extends Activity {

    ImageView buttonToBack;
    IWSSBService service;

    TextView oldName;
    TextView newName;
    TextView oldPass;
    TextView newPass;
    TextView userDel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        service = APIClient.getClient().create(IWSSBService.class);

        buttonToBack = (ImageView) findViewById(R.id.buttonBack);
        oldName = (TextView) findViewById(R.id.oldName);
        newName = (TextView) findViewById(R.id.newName);
        oldPass = (TextView) findViewById(R.id.oldPass);
        newPass = (TextView) findViewById(R.id.newPass);
        userDel = (TextView) findViewById(R.id.userDel);

        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });

    }

    public void backToMain() {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
}
