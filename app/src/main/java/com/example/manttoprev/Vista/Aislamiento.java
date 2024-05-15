package com.example.manttoprev.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.manttoprev.Presentador.AislamientoContract;
import com.example.manttoprev.Presentador.AislamientoPresenter;
import com.example.manttoprev.R;

import java.util.List;

public class Aislamiento extends AppCompatActivity implements AislamientoContract.View {

    Spinner cboArea;
    Spinner cboEquipos;
    Spinner cboMaquinas;
    Spinner cboMotor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aislamiento);

        AislamientoContract.Presenter presenter;

        cboArea = findViewById(R.id.cboArea);
        cboEquipos = findViewById(R.id.cboEquipos);
        cboMaquinas = findViewById(R.id.cboMaquinas);
        cboMotor = findViewById(R.id.cboMotor);

        presenter = new AislamientoPresenter(this);

        //Ver la lista de áreas en el Spinner
        presenter.obtenerAreas();



        cboArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el área seleccionada
                String areaSeleccionada = parent.getItemAtPosition(position).toString();

                // Llamar al método obtenerEquipos() con el área seleccionada
                presenter.obtenerEquipos(areaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejar el caso en que no se seleccione nada en el Spinner
            }
        });


        String[] fuenteDato3 = getResources().getStringArray(R.array.lista_maquinas);
        ArrayAdapter<String> adp3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fuenteDato3);
        cboMaquinas.setAdapter(adp3);

        String[] fuenteDato4 = getResources().getStringArray(R.array.lista_motores);
        ArrayAdapter<String> adp4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fuenteDato4);
        cboMotor.setAdapter(adp4);
    }

    @Override
    public void mostrarAreas(List<String> areas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboArea.setAdapter(adapter);
    }
    @Override
    public void mostrarEquipos(List<String> equipos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboEquipos.setAdapter(adapter);
    }


}