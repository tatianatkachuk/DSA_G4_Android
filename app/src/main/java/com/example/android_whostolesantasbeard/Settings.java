package com.example.android_whostolesantasbeard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_whostolesantasbeard.entities.PasswordUpdate;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings extends Activity {

    ImageView buttonToBack;
    IWSSBService service;

    TextView oldName;
    TextView newName;
    TextView oldPass;
    TextView newPass;
    TextView userDel;

    Button confirmDel;
    Button confirmPass;
    Button confirmUsername;

    // Shared preferences
    public static final String PREFERENCES = "prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        service = APIClient.getClient().create(IWSSBService.class);

        String mail = getIntent().getExtras().getString("mail");
        String id = getIntent().getExtras().getString("id");
        String username = getIntent().getExtras().getString("username");

        buttonToBack = (ImageView) findViewById(R.id.buttonBack);
        oldName = (TextView) findViewById(R.id.oldName);
        newName = (TextView) findViewById(R.id.newName);
        oldPass = (TextView) findViewById(R.id.oldPass);
        newPass = (TextView) findViewById(R.id.newPass);
        userDel = (TextView) findViewById(R.id.userDel);

        confirmDel = (Button) findViewById(R.id.confirmDelButton);
        confirmPass = (Button) findViewById(R.id.confirmPassButton);
        confirmUsername = (Button) findViewById(R.id.confirmUsernameButton);

        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain(username);
            }
        });

        confirmDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userToDelete = userDel.getText().toString();
                if(userToDelete.equals(username)){
                    deleteUser(id, username);}
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),"This is not your username", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {toast.show();}
                    });
                }
            }
        });

        confirmUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldUser = oldName.getText().toString();
                String newUser = newName.getText().toString();
                if(oldUser.equals(username)){
                    updateUsername(username, newUser);}
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),"This is not your username", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {toast.show();}
                    });
                }
            }
        });

        confirmPass.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPass.getText().toString();
                String newPassword = newPass.getText().toString();

                Call<User> call = service.getUserbyUserName(username);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("TAG",response.code()+"");
                        if(response.code()==201){
                            User currentUser = response.body();
                            String currentPass = currentUser.getPassword();

                            if(oldPassword.equals(currentPass)){
                                updatePass(id, oldPassword, newPassword);
                            };

                            return;
                        }
                        else{
                            Toast toast = Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {toast.show();}
                            });
                            return;
                        }

                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        call.cancel();
                        Log.d("Error","Failure");
                    }
                });

            }
        }));
    }

    public void updateUsername(String oldUsername, String newUsername){
        Call<User> call = service.updateUserName(oldUsername, newUsername);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==201){
                    Toast toast = Toast.makeText(getApplicationContext(),"Username "+ oldUsername +" was successfully updated to " + newUsername + ".", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {toast.show();}
                    });
                    logOut();
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
                else{
                    Log.d("Error","Username error");
                    Toast toast = Toast.makeText(getApplicationContext(),"This user doesn't exist! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {toast.show();}
                    });
                    return;
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
                Log.d("Error","Failure");
            }
        });
    }

    public void updatePass(String id, String oldPassword, String newPassword){
        PasswordUpdate passwordUpd = new PasswordUpdate(id, oldPassword, newPassword);
        Call<User> call = service.updatePassword(passwordUpd);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==201){
                    Toast toast = Toast.makeText(getApplicationContext(),"Password was successfully updated.", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {toast.show();}
                    });
                    logOut();
                    return;
                }
                if(response.code()==407){
                    Log.d("Error","Wrong password.");
                    Toast toast = Toast.makeText(getApplicationContext(),"Wrong password.", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {toast.show();}
                    });
                    return;
                }
                else{
                    Log.d("Error","Error");
                    Toast toast = Toast.makeText(getApplicationContext(),"This user doesn't exist! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {toast.show();}
                    });
                    return;
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
                Log.d("Error","Failure");
            }
        });
    }

    public void deleteUser(String idToDelete, String username){
        Call<User> call = service.deleteUser(idToDelete);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==201){
                    Toast toast = Toast.makeText(getApplicationContext(),"User "+ username +" was successfully deleted.", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {toast.show();}
                    });
                    logOut();
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
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
                Log.d("Error","Failure");
            }
        });
    }

    public void backToMain(String username) {
        Intent intent = new Intent(this, Main.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void logOut() {
        SharedPreferences shPrefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shPrefs.edit();
        editor.putString("username", "");
        editor.putString("password", "");
        editor.commit();
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
        finish();
    }


}
