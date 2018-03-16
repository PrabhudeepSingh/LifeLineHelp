package com.auribises.lifelinehelp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auribises.lifelinehelp.R;

import java.util.HashMap;
import java.util.Map;


public class AddNeedActivity extends AppCompatActivity {

    EditText eTxtName, eTxtContact, eTxtAddress, eTxtDescription;
    Button btnSubmit;

    String name, contact, address, description;


    private static String url ="https://podgier-woman.000webhostapp.com/modify.php";

    void initViews(){
        eTxtName = findViewById(R.id.editTextAddName);
        eTxtContact = findViewById(R.id.editTextAddContact);
        eTxtAddress = findViewById(R.id.editTextAddAddress);
        eTxtDescription = findViewById(R.id.editTextAddDescription);

        btnSubmit = findViewById(R.id.buttonAddSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = eTxtName.getText().toString().trim();
                contact = eTxtContact.getText().toString().trim();
                address = eTxtAddress.getText().toString().trim();
                description = eTxtDescription.getText().toString().trim();

                sendData();

                Intent intent = new Intent(AddNeedActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });
    }
    void sendData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("address", address);
                map.put("mobileno", contact);
                map.put("descrip", description);



                return  map;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_need);
        initViews();
    }
}
