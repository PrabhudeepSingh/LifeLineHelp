package com.auribises.lifelinehelp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.auribises.lifelinehelp.R;
import com.auribises.lifelinehelp.utility.Util;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText username,password;
    Button login,signup;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Intent rcv;
    public static boolean flag=false;

    void initViews(){
        username = (EditText)findViewById(R.id.editTextUsernameLogin);
        password = (EditText)findViewById(R.id.editTextPasswordLogin);

        login = (Button)findViewById(R.id.buttonLogin);
        signup = (Button)findViewById(R.id.buttonSignup);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("Users",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(flag){
            rcv=getIntent();
            username.setText(rcv.getStringExtra("keyEmail"));
            password.setText(rcv.getStringExtra("keyPassword"));
            flag=false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonLogin){
            String email = username.getText().toString();
            String pwd = password.getText().toString();

            String keyUserName = "userName"+email;
            String keyEmail = "email"+email;
            String keyPassword = "password"+email;
            String keyPhone = "phone"+email;
            String keyAddress = "address"+email;

            if(sharedPreferences.contains(keyEmail)){
                String strPwd = sharedPreferences.getString(keyPassword, "");
                if(pwd.equals(strPwd)){
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                    editor.putInt("session", 1);
                    editor.apply();

                    Util.username = sharedPreferences.getString(keyUserName,"");
                    Util.email = sharedPreferences.getString(keyEmail,"");
                    Util.password = sharedPreferences.getString(keyPassword,"");
                    Util.phone = sharedPreferences.getString(keyPhone,"");
                    Util.address = sharedPreferences.getString(keyAddress,"");

                }else Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(this, "Account doesn't exits.", Toast.LENGTH_SHORT).show();
        }
        else if (v.getId() == R.id.buttonSignup){
            Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        LoginActivity.super.onBackPressed();
                    }
                }).create().show();
    }

}
