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
    Spinner cboSecciones;
    Spinner cboEquipos;
    Spinner cboMaquinas;
    Spinner cboMotor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aislamiento);

        AislamientoContract.Presenter presenter;

        cboArea = findViewById(R.id.cboArea);
        cboSecciones = findViewById(R.id.cboSecciones);
        cboEquipos = findViewById(R.id.cboEquipos);
        cboMaquinas= findViewById(R.id.cboMaquinas);
        cboMotor = findViewById(R.id.cboMotor);

        presenter = new AislamientoPresenter(this);

        //Ver la lista de áreas en el Spinner
        presenter.obtenerAreas();



        cboArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el área seleccionada
                String areaSeleccionada = parent.getItemAtPosition(position).toString();

                // Llamar al método obtenerSeccioness() con el área seleccionada
                presenter.obtenerSecciones(areaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejar el caso en que no se seleccione nada en el Spinner
            }
        });

        cboSecciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seccionSeleccionada = parent.getItemAtPosition(position).toString();
                presenter.obtenerEquipos(seccionSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        cboEquipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String equipoSeleccionado = parent.getItemAtPosition(position).toString();
                presenter.obtenerMaquinas(equipoSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    @Override
    public void mostrarAreas(List<String> areas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboArea.setAdapter(adapter);
    }
    @Override
    public void mostrarSecciones(List<String> secciones) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, secciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboSecciones.setAdapter(adapter);
    }

    @Override
    public void mostrarEquipos(List<String> equipos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboEquipos.setAdapter(adapter);
    }
    @Override
    public void mostrarMaquinas(List<String> maquinas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, maquinas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboMaquinas.setAdapter(adapter);
    }
}