package com.example.manttoprev.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.manttoprev.Presentador.InterfazPrincipalContract;
import com.example.manttoprev.Presentador.InterfazPrincipalPresenter;
import com.example.manttoprev.R;


public class InterfazPrincipal extends AppCompatActivity implements InterfazPrincipalContract.View{

    TextView tvMantenimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_principal);

        InterfazPrincipalContract.Presenter presenter = new InterfazPrincipalPresenter(this);

        tvMantenimiento = findViewById(R.id.tvMantenimiento);
        tvMantenimiento.setOnClickListener(v -> {
            Intent intent = new Intent(InterfazPrincipal.this, Mantenimiento.class);
            startActivity(intent);
        });



        presenter.obtenerRolUsuario();



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


    public void showRegistrarTextView(boolean show) {
        if (show) {
            tvMantenimiento.setVisibility(View.VISIBLE); // Mostrar el TextView de registro
        } else {
            tvMantenimiento.setVisibility(View.GONE); // Ocultar el TextView de registro
        }
    }

}