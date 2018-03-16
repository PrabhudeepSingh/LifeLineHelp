package com.auribises.lifelinehelp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.auribises.lifelinehelp.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    EditText eTxtUserName, eTxtEmail, eTxtPassword, eTxtConfirmPassword, eTxtPhone, eTxtAddress;
    Button btnSubmit;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    void initViews(){

        eTxtUserName = findViewById(R.id.editTextUsername);
        eTxtEmail = findViewById(R.id.editTextEmail);
        eTxtPassword = findViewById(R.id.editTextPassword);
        eTxtConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        eTxtPhone = findViewById(R.id.editTextPhone);
        eTxtAddress = findViewById(R.id.editTextAddress);

        btnSubmit = findViewById(R.id.buttonSubmit);
        btnSubmit.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("Users",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSubmit){
            String userName = eTxtUserName.getText().toString().trim();
            String email = eTxtEmail.getText().toString().trim();
            String password = eTxtPassword.getText().toString().trim();
            String confirmPassword = eTxtConfirmPassword.getText().toString().trim();
            String phone = eTxtPhone.getText().toString().trim();
            String address = eTxtAddress.getText().toString().trim();

            String keyUserName = "userName"+email;
            String keyEmail = "email"+email;
            String keyPassword = "password"+email;
            String keyPhone = "phone"+email;
            String keyAddress = "address"+email;

            if (userName.isEmpty()){
                Toast.makeText(this,"Enter your username",Toast.LENGTH_LONG).show();
            }
            else if (password.isEmpty()){
                Toast.makeText(this,"Enter password",Toast.LENGTH_LONG).show();
            }
            else if (!password.equals(confirmPassword)){
                Toast.makeText(this,"Password doesn't match!",Toast.LENGTH_LONG).show();
            }
            else if(phone.isEmpty()){
                Toast.makeText(this,"Enter Phone",Toast.LENGTH_LONG).show();
            }
            else if(address.isEmpty()){
                Toast.makeText(this,"Enter Address",Toast.LENGTH_LONG).show();
            }
            else if(sharedPreferences.contains(keyEmail)){
                Toast.makeText(this, "You Already Have an account", Toast.LENGTH_LONG).show();
            }
            else {
                editor.putString(keyUserName, userName);
                editor.putString(keyEmail, email);
                editor.putString(keyPassword, password);
                editor.putString(keyPhone, phone);
                editor.putString(keyAddress, address);

                editor.apply();
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class );
                intent.putExtra("keyEmail", email);
                intent.putExtra("keyPassword", password);
                LoginActivity.flag=true;
                startActivity(intent);
            }
        }

    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);

        startActivity(intent);
        finish();
    }
}
