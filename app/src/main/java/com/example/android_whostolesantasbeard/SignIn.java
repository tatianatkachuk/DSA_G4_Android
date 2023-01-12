package com.example.android_whostolesantasbeard;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.content.Intent;
import android.widget.Toast;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
    // From activity
    TextView usernameVal;
    TextView passwordVal;
    Button signInButton;
    Button setSpanish;
    Button setEnglish;
    TextView swapToRegisterButton;
    CheckBox rememberMeButton;
    // API client
    IWSSBService service;
    String selectedLanguage;
    // Shared preferences
    SharedPreferences shPrefs;
    public static final String PREFERENCES = "prefs";

    // Loader
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Loader
        progressBar = findViewById(R.id.loaderToMain);
        // Finish
        progressBar.setVisibility(View.GONE);

        service = APIClient.getClient().create(IWSSBService.class);

        usernameVal = (EditText) findViewById(R.id.usernameVal);
        passwordVal = (EditText) findViewById(R.id.passwordVal);
        signInButton = (Button) findViewById(R.id.signinButton);
        swapToRegisterButton = (TextView) findViewById(R.id.swapToRegisterButton);
        rememberMeButton = (CheckBox) findViewById(R.id.rememberMeButton);
        // Shared preferences. It can only be accessed by the calling application
        shPrefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        setEnglish = (Button) findViewById(R.id.button_ToEnglish);
        setSpanish = (Button) findViewById(R.id.button_setSpanish);

        // Obtains saved prefs from file and set them to the values
        String usr = shPrefs.getString("username", "");
        usernameVal.setText(usr);
        String psw = shPrefs.getString("password","");
        passwordVal.setText(psw);

        setEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedLanguage = "en";
                Call<User> call2 = service.updateLanguageUsed(selectedLanguage);
                saveIntoShPrefsString("LANG",selectedLanguage);
                updateLang();
            }
        });
        setSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedLanguage = "es";
                Call<User> call2 = service.updateLanguageUsed(selectedLanguage);
                call2.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("TAGG","WORKED");
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("TAGG","FAILED");
                    }
                });
                saveIntoShPrefsString("LANG",selectedLanguage);
                updateLang();
            }
        });
        // Swap to register or main
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Loader
                progressBar = findViewById(R.id.loaderToMain);

                loginToApp();
            }
        });
        swapToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterView(view);
            }
        });


    }
    public void loginToApp(){
        String username = usernameVal.getText().toString();
        String pass = passwordVal.getText().toString();
        User signInCreds = new User("", username, pass, "", "");
        Call<User> call = service.loginUser(signInCreds);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    User user = response.body();
                    String username = user.getUsername();
                    String pass = user.getPassword();
                    String id = user.getId();
                    String email = user.getMail();
                    String coins = user.getCoins();

                    Log.d("User",username+" "+pass+" "+id+" " + email + " " + coins);

                    // Save to shared preferences IF the user wants to
                    if(rememberMeButton.isChecked()){
                        saveIntoShPrefs(username, pass);
                        saveIntoShPrefsBoolean("isLogged",true);
                    }

                    // Open main
                    openApp(username);


                    return;
                }
                if(response.code()==404){
                    Log.d("Error","User not found");
                    Toast toast = Toast.makeText(getApplicationContext(),"This user doesn't exist! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {toast.show();}
                    });
                    return;
                }
                if(response.code()==405){
                    Log.d("Error","Wrong password");
                    Toast toast = Toast.makeText(getApplicationContext(),"The password is wrong! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });
                    return;
                }

                if(response.code()==500){
                    Log.d("Error","Invalid credentials");
                    Toast toast = Toast.makeText(getApplicationContext(),"Invalid credentials! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });
                    return;
                }

                else{
                    Log.d("Error","Login failed");
                    Toast toast = Toast.makeText(getApplicationContext(),"Login failed! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
                Log.d("Error","Failure");
            }

        });
    }

    public void openApp(String username) {
        Intent intent = new Intent(this, Main.class);
        intent.putExtra("username", username);
        startActivity(intent);

    }

    public void openRegisterView(View view) {
        Intent intent = new Intent(SignIn.this, Register.class);
        this.startActivity(intent);
    }


    //Shared Preferences
    public void saveIntoShPrefs(String username, String password){
        SharedPreferences shPrefs;
        shPrefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shPrefs.edit();

        // Storing strings
        editor.putString("username", username);
        editor.putString("password", password);

        // Commit changes
        editor.commit();

    }
    public void saveIntoShPrefsBoolean(String key, boolean value){
        SharedPreferences shPrefs;
        shPrefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shPrefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public void saveIntoShPrefsString(String key, String value){
        SharedPreferences shPrefs;
        shPrefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shPrefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void updateLang(){
        String languageToLoad  = selectedLanguage; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
       // getBaseContext().getResources().updateConfiguration(config,
          //      getBaseContext().getResources().getDisplayMetrics());
        Intent refresh = new Intent(this, this.getClass());
        startActivity(refresh);
        finish();
    }
    public String GetStringFromPref(String key){
        shPrefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String result =  shPrefs.getString(key,"");
        return result;
    }

}