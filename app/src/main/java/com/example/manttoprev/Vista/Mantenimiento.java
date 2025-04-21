package com.example.manttoprev.Vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.manttoprev.R;

public class Mantenimiento extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento);

        Toolbar toolbar = findViewById(R.id.toolbarMantenimiento);
        setSupportActionBar(toolbar);

        // Habilitar la flecha de retroceso en la barra de acción
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v ->  {

            Intent intent = new Intent(Mantenimiento.this, InterfazPrincipal.class);
            startActivity(intent);
        });

        Button btnNuevoUsuario = findViewById(R.id.btnNuevoUsuario);
        btnNuevoUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(Mantenimiento.this, RegistroUsuario.class);
            startActivity(intent);
        });

        Button btnRegistrarArea = findViewById(R.id.btnRegistrarArea);
        btnRegistrarArea.setOnClickListener(v -> {
            Intent intent = new Intent(Mantenimiento.this, AreaAdmin.class);
            startActivity(intent);
        });

        Button btnRegistrarSeccion = findViewById(R.id.btnRegistrarSeccion);
        btnRegistrarSeccion.setOnClickListener(v -> {
            Intent intent = new Intent(Mantenimiento.this, SeccionAdmin.class);
            startActivity(intent);
        });

        Button btnRegistrarEquipo = findViewById(R.id.btnRegistrarEquipo);
        btnRegistrarEquipo.setOnClickListener(v -> {
            Intent intent = new Intent(Mantenimiento.this, EquipoAdmin.class);
            startActivity(intent);
        });

        Button btnRegistrarMaquina = findViewById(R.id.btnRegistrarMaquina);
        btnRegistrarMaquina.setOnClickListener(v -> {
            Intent intent = new Intent(Mantenimiento.this, MaquinaAdmin.class);
            startActivity(intent);
        });

        Button btnRegistrarMotor = findViewById(R.id.btnRegistrarMotor);
        btnRegistrarMotor.setOnClickListener(v -> {
            Intent intent = new Intent(Mantenimiento.this, MotorAdmin.class);
            startActivity(intent);
        });
    }
}