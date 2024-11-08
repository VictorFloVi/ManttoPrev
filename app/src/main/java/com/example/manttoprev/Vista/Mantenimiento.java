package com.example.manttoprev.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.manttoprev.R;

public class Mantenimiento extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento);

        Button btnNuevoUsuario = findViewById(R.id.btnNuevoUsuario);
        btnNuevoUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(Mantenimiento.this, RegistroUsuario.class);
            startActivity(intent);
        });

        Button btnRegistrarArea = findViewById(R.id.btnRegistrarArea);
        btnRegistrarArea.setOnClickListener(v -> {
            Intent intent = new Intent(Mantenimiento.this, UbicacionAdmin.class);
            startActivity(intent);
        });

        Button btnRegistrarEquipo = findViewById(R.id.btnRegistrarEquipo);
        btnRegistrarEquipo.setOnClickListener(v -> {
            Intent intent = new Intent(Mantenimiento.this, Ubicacion2Admin.class);
            startActivity(intent);
        });

        Button btnRegistrarZona = findViewById(R.id.btnRegistrarMaquina);
        btnRegistrarZona.setOnClickListener(v -> {
            Intent intent = new Intent(Mantenimiento.this, MaquinaAdmin.class);
            startActivity(intent);
        });
    }
}