package com.example.manttoprev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.manttoprev.Vista.Login;

public class MainActivity extends AppCompatActivity {

    Button btnIngresarCredenciales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIngresarCredenciales=findViewById(R.id.btnIngresarCredenciales);

        btnIngresarCredenciales.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        });
    }
}