package com.test.gibook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button btn_nanum02;
    private Button btn_nanum01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_nanum02 = findViewById(R.id.btn_nanum02);
        btn_nanum02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, To_Donation.class);
                startActivity(intent);

            }
        });

        btn_nanum01 = findViewById(R.id.btn_nanum01);
        btn_nanum01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Lobby.class);
                startActivity(intent);

            }
        });
    }
}