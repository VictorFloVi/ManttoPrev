package com.example.manttoprev.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.manttoprev.R;

public class Aislamiento extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aislamiento);

        Spinner cboArea = findViewById(R.id.cboArea);
        Spinner cboEquipos = findViewById(R.id.cboEquipos);
        Spinner cboMaquinas = findViewById(R.id.cboMaquinas);
        Spinner cboMotor = findViewById(R.id.cboMotor);


        String[] fuenteDatos = getResources().getStringArray(R.array.lista_areas);
        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fuenteDatos);
        cboArea.setAdapter(adp);

        String[] fuenteDato2 = getResources().getStringArray(R.array.lista_equipos);
        ArrayAdapter<String> adp2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fuenteDato2);
        cboEquipos.setAdapter(adp2);

        String[] fuenteDato3 = getResources().getStringArray(R.array.lista_maquinas);
        ArrayAdapter<String> adp3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fuenteDato3);
        cboMaquinas.setAdapter(adp3);

        String[] fuenteDato4 = getResources().getStringArray(R.array.lista_motores);
        ArrayAdapter<String> adp4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fuenteDato4);
        cboMotor.setAdapter(adp4);
    }
}