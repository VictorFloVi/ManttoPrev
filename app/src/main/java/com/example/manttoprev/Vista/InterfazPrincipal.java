package com.example.manttoprev.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.manttoprev.R;

public class InterfazPrincipal extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_principal);



        TextView tvAislamiento = findViewById(R.id.tvAislamiento);
        tvAislamiento.setOnClickListener(v -> {
            Intent intent = new Intent(InterfazPrincipal.this, Aislamiento.class);
            startActivity(intent);
        });

        TextView tvManttoMotor = findViewById(R.id.tvManttoMotor);
        tvManttoMotor.setOnClickListener(v -> {
            Intent intent = new Intent(InterfazPrincipal.this, ManttoMotor.class);
            startActivity(intent);
        });

        TextView tvAlertas = findViewById(R.id.tvAlertas);
        tvAlertas.setOnClickListener(v -> {
            Intent intent = new Intent(InterfazPrincipal.this, Alertas.class);
            startActivity(intent);
        });

        TextView tvGraficos = findViewById(R.id.tvGraficos);
        tvGraficos.setOnClickListener(v -> {
            Intent intent = new Intent(InterfazPrincipal.this, Graficos.class);
            startActivity(intent);
        });

        TextView tvHistorial = findViewById(R.id.tvHistorial);
        tvHistorial.setOnClickListener(v -> {
            Intent intent = new Intent(InterfazPrincipal.this, Historial.class);
            startActivity(intent);
        });

        TextView tvReportes = findViewById(R.id.tvReportes);
        tvReportes.setOnClickListener(v -> {
            Intent intent = new Intent(InterfazPrincipal.this, Reportes.class);
            startActivity(intent);
        });
    }
}