package com.example.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;


import java.util.Arrays;
import java.util.List;

public class ItemViewing extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    static final int AUTOCOMPLETE_REQUEST_CODE=100;
    EditText phone_edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_viewing);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        EditText name = findViewById(R.id.name);
        EditText location = findViewById(R.id.location);
        EditText phone = findViewById(R.id.phone);
        EditText price = findViewById(R.id.price);
        EditText checkin = findViewById(R.id.checkin);
        name.setText(getIntent().getStringExtra("nameFood"));
        location.setText(getIntent().getStringExtra("locationFood"));
        phone.setText(getIntent().getStringExtra("phoneFood"));
        price.setText(getIntent().getStringExtra("priceFood"));
        checkin.setText(getIntent().getStringExtra("checkinFood"));
        Log.d("Position", getIntent().getStringExtra("positionClick"));

        final String message = getIntent().getStringExtra("positionClick");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = findViewById(R.id.name);
                EditText location = findViewById(R.id.location);
                EditText phone = findViewById(R.id.phone);
                EditText price = findViewById(R.id.price);
                EditText checkin = findViewById(R.id.checkin);
                String backStringname = name.getText().toString();
                String backStringlocation = location.getText().toString();
                String backStringphone = phone.getText().toString();
                String backStringprice = price.getText().toString();
                String backStringcheckin = checkin.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("nameFoodBack", backStringname);
                intent.putExtra("locationFoodBack", backStringlocation);
                intent.putExtra("phoneFoodBack", backStringphone);
                intent.putExtra("priceFoodBack", backStringprice);
                intent.putExtra("checkinFoodBack", backStringcheckin);
                intent.putExtra("positionClickBack", message);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        ImageView phonecall;
        phone_edittext = findViewById(R.id.phone);
        phonecall = findViewById(R.id.res_hotline);
        Log.d("Check", "ss");
        phonecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "Button pressed");
                makePhonecall();
            }
        });


    }

// Create a new Places client instance.


    static final int PICK_MAP_POINT_REQUEST = 999;  // The request code
    private void makePhonecall() {
        String number = phone_edittext.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(ItemViewing.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ItemViewing.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel: " + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(this,"Enter phone number",Toast.LENGTH_SHORT).show();
        }
    }
//    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
////            if (resultCode == RESULT_OK) {
//////                Place place = Autocomplete.getPlaceFromIntent(data);
////                String place=data.getStringExtra("Location");
////                Log.i("Hoho", "Place: " + place);
////                EditText myLocation=findViewById(R.id.location);
////                myLocation.setText(place);
////            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
////                // TODO: Handle the error.
////                Status status = Autocomplete.getStatusFromIntent(data);
////                Log.i("t NE", status.getStatusMessage());
////            } else if (resultCode == RESULT_CANCELED) {
////                // The user canceled the operation.
////            }
////        }
////        super.onActivityResult(requestCode,resultCode,data);
////    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhonecall();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
