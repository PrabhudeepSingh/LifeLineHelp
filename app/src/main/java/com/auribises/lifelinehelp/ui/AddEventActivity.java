package com.auribises.lifelinehelp.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auribises.lifelinehelp.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddEventActivity extends AppCompatActivity {

    EditText eTxtEventName, eTxtEventNameOfNGO, eTxtEventTopic, eTxtEventPlace;
    Button btnSelectDate, btnSelectFromTiming, btnSelectToTiming, btnSubmitEvent;

    String eventName, eventNGO, eventTopic, eventPlace, eventDate, eventFrom, eventTo;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timeFromPickerDialog, timeToPickerDialog;
    Calendar calendar;

    private static String url ="https://podgier-woman.000webhostapp.com/abc.php";

    void initViews(){

        eTxtEventName = findViewById(R.id.editTextEventName);
        eTxtEventNameOfNGO = findViewById(R.id.editTextEventNameOfNGO);
        eTxtEventTopic = findViewById(R.id.editTextEventTopic);
        eTxtEventPlace = findViewById(R.id.editTextEventPlace);

        calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yy = calendar.get(Calendar.YEAR);

        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);


        datePickerDialog = new DatePickerDialog(this, dateSetListener, yy, mm, dd);
        timeFromPickerDialog = new TimePickerDialog(this, timeFromSetListener, hr, min, true);
        timeToPickerDialog = new TimePickerDialog(this, timeToSetListener, hr, min, true);


        btnSelectDate = findViewById(R.id.buttonSelectDate);
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        btnSelectFromTiming = findViewById(R.id.buttonFromTiming);
        btnSelectFromTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeFromPickerDialog.show();
            }
        });

        btnSelectToTiming = findViewById(R.id.buttonToTiming);
        btnSelectToTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeToPickerDialog.show();
            }
        });

        btnSubmitEvent = findViewById(R.id.buttonEventSubmit);
        btnSubmitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventName = eTxtEventName.getText().toString().trim();
                eventNGO = eTxtEventNameOfNGO.getText().toString().trim();
                eventTopic = eTxtEventTopic.getText().toString().trim();
                eventPlace = eTxtEventPlace.getText().toString().trim();
                eventDate = btnSelectDate.getText().toString().trim();
                eventFrom = btnSelectFromTiming.getText().toString().trim();
                eventTo = btnSelectToTiming.getText().toString().trim();

                sendData();

                Intent intent = new Intent(AddEventActivity.this, AddNeedActivity.class);
                startActivity(intent);
            }
        });
    }

    TimePickerDialog.OnTimeSetListener timeFromSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = hourOfDay+":"+minute;
            btnSelectFromTiming.setText(time);
        }
    };


    TimePickerDialog.OnTimeSetListener timeToSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = hourOfDay+":"+minute;
            btnSelectToTiming.setText(time);
        }
    };

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = dayOfMonth+"/"+(month+1)+"/"+year;
            btnSelectDate.setText(date);
        }
    };
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
                map.put("name", eventName);
                map.put("ngo", eventNGO);
                map.put("topic", eventTopic);
                map.put("place", eventPlace);
                map.put("fromm", eventFrom);
                map.put("toh", eventTo);
                map.put("date", eventDate);

                return  map;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initViews();
    }
}
