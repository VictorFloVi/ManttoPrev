package com.example.manttoprev.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.manttoprev.Presentador.VibracionAdminContract;
import com.example.manttoprev.Presentador.VibracionAdminPresenter;
import com.example.manttoprev.R;

import java.util.List;

public class VibracionAdmin extends AppCompatActivity implements VibracionAdminContract.View {

    Spinner cboAreaV;
    Spinner cboSeccionesV;
    Spinner cboEquiposV;
    Spinner cboMaquinasV;
    Spinner cboMotorV;
    TableLayout tbVibracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibracion_admin);

        Toolbar toolbar = findViewById(R.id.toolbarVibracion);
        setSupportActionBar(toolbar);

        // Habilitar la flecha de retroceso en la barra de acción
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v ->  {

            Intent intent = new Intent(VibracionAdmin.this, InterfazPrincipal.class);
            startActivity(intent);

        });

        VibracionAdminContract.Presenter presenter;

        cboAreaV = findViewById(R.id.cboAreaV);
        cboSeccionesV = findViewById(R.id.cboSeccionesV);
        cboEquiposV = findViewById(R.id.cboEquiposV);
        cboMaquinasV= findViewById(R.id.cboMaquinasV);
        cboMotorV = findViewById(R.id.cboMotorV);
        tbVibracion = findViewById(R.id.tbVibracion);

        presenter = new VibracionAdminPresenter(this);

        tbVibracion.setVisibility(View.GONE);

        //Ver la lista de áreas en el Spinner
        presenter.obtenerAreas();

        cboAreaV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        cboSeccionesV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seccionSeleccionada = parent.getItemAtPosition(position).toString();
                presenter.obtenerEquipos(seccionSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        cboEquiposV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String equipoSeleccionado = parent.getItemAtPosition(position).toString();
                presenter.obtenerMaquinas(equipoSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Este método está vacío intencionalmente porque no se requiere un comportamiento específico.
            }
        });

        cboMaquinasV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String maquinaSeleccionada = parent.getItemAtPosition(position).toString();
                presenter.obtenerMotores(maquinaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        cboMotorV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String motorSeleccionado = parent.getItemAtPosition(position).toString();

                // Haz visible la tabla cuando se seleccione un motor
                if (!motorSeleccionado.equals("Motor")) {
                    tbVibracion.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
    }

    @Override
    public void mostrarAreas(List<String> areas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboAreaV.setAdapter(adapter);
    }
    @Override
    public void mostrarSecciones(List<String> secciones) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, secciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboSeccionesV.setAdapter(adapter);
    }
    @Override
    public void mostrarEquipos(List<String> equipos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboEquiposV.setAdapter(adapter);
    }
    @Override
    public void mostrarMaquinas(List<String> maquinas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, maquinas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboMaquinasV.setAdapter(adapter);
    }
    @Override
    public void mostrarMotores(List<String> motores) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, motores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboMotorV.setAdapter(adapter);
    }

}