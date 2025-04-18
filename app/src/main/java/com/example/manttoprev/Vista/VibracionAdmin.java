package com.example.manttoprev.Vista;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.example.manttoprev.Presentador.VibracionAdminContract;
import com.example.manttoprev.Presentador.VibracionAdminPresenter;
import com.example.manttoprev.R;

import java.io.File;
import java.util.List;

public class VibracionAdmin extends AppCompatActivity implements VibracionAdminContract.View {

    Spinner cboAreaV;
    Spinner cboSeccionesV;
    Spinner cboEquiposV;
    Spinner cboMaquinasV;
    Spinner cboMotorV;
    TableLayout tbVibracion;

    EditText etHorIsoC;
    EditText etHorBduC;
    EditText etHorGC;

    EditText etVerIsoC;
    EditText etVerBduC;
    EditText etVerGC;

    EditText etAxiIsoC;
    EditText etAxiBduC;
    EditText etAxiGC;

    EditText etHorIsoV;
    EditText etHorBduV;
    EditText etHorGV;

    EditText etVerIsoV;
    EditText etVerBduV;
    EditText etVerGV;

    EditText etAxiIsoV;
    EditText etAxiBduV;
    EditText etAxiGV;

    Button btnGuardarVibracion;



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

        etHorIsoC = findViewById(R.id.etHorIsoC);
        etHorBduC = findViewById(R.id.etHorBduC);
        etHorGC = findViewById(R.id.etHorGC);

        etVerIsoC = findViewById(R.id.etVerIsoC);
        etVerBduC = findViewById(R.id.etVerBduC);
        etVerGC = findViewById(R.id.etVerGC);

        etAxiIsoC = findViewById(R.id.etAxiIsoC);
        etAxiBduC = findViewById(R.id.etAxiBduC);
        etAxiGC = findViewById(R.id.etAxiGC);

        etHorIsoV = findViewById(R.id.etHorIsoV);
        etHorBduV = findViewById(R.id.etHorBduV);
        etHorGV = findViewById(R.id.etHorGV);

        etVerIsoV = findViewById(R.id.etVerIsoV);
        etVerBduV = findViewById(R.id.etVerBduV);
        etVerGV = findViewById(R.id.etVerGV);

        etAxiIsoV = findViewById(R.id.etAxiIsoV);
        etAxiBduV = findViewById(R.id.etAxiBduV);
        etAxiGV = findViewById(R.id.etAxiGV);

        btnGuardarVibracion = findViewById(R.id.btnGuardarVibracion);

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

        btnGuardarVibracion.setOnClickListener(v -> {
            String area = cboAreaV.getSelectedItem().toString().trim();
            String seccion = cboSeccionesV.getSelectedItem().toString().trim();
            String equipo = cboEquiposV.getSelectedItem().toString().trim();
            String maquina = cboMaquinasV.getSelectedItem().toString().trim();
            String motor = cboMotorV.getSelectedItem().toString().trim();

            Double horisoc = Double.parseDouble(etHorIsoC.getText().toString().trim());
            Double horbduc = Double.parseDouble(etHorBduC.getText().toString().trim());
            Double horgc = Double.parseDouble(etHorGC.getText().toString().trim());

            Double verisoc = Double.parseDouble(etVerIsoC.getText().toString().trim());
            Double verbduc = Double.parseDouble(etVerBduC.getText().toString().trim());
            Double vergc = Double.parseDouble(etVerGC.getText().toString().trim());

            Double axiisoc = Double.parseDouble(etAxiIsoC.getText().toString().trim());
            Double axibduc = Double.parseDouble(etAxiBduC.getText().toString().trim());
            Double axigc = Double.parseDouble(etAxiGC.getText().toString().trim());


            Double horisov = Double.parseDouble(etHorIsoV.getText().toString().trim());
            Double horbduv = Double.parseDouble(etHorBduV.getText().toString().trim());
            Double horgv = Double.parseDouble(etHorGV.getText().toString().trim());

            Double verisov = Double.parseDouble(etVerIsoV.getText().toString().trim());
            Double verbduv = Double.parseDouble(etVerBduV.getText().toString().trim());
            Double vergv = Double.parseDouble(etVerGV.getText().toString().trim());

            Double axiisov = Double.parseDouble(etAxiIsoV.getText().toString().trim());
            Double axibduv = Double.parseDouble(etAxiBduV.getText().toString().trim());
            Double axigv = Double.parseDouble(etAxiGV.getText().toString().trim());

            presenter.guardarVibracion(area, seccion, equipo, maquina, motor, horisoc, horbduc, horgc,
                    verisoc, verbduc, vergc, axiisoc, axibduc, axigc, horisov, horbduv, horgv, verisov,
                    verbduv, vergv, axiisov, axibduv, axigv);


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

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        clearEditTextFields();
    }

    public void clearEditTextFields() {
        cboAreaV.setSelection(0);
        cboSeccionesV.setSelection(0);
        cboEquiposV.setSelection(0);
        cboMaquinasV.setSelection(0);
        cboMotorV.setSelection(0);
        etHorIsoC.setText("");
        etHorBduC.setText("");
        etHorGC.setText("");
        etVerIsoC.setText("");
        etVerBduC.setText("");
        etVerGC.setText("");
        etAxiIsoC.setText("");
        etAxiBduC.setText("");
        etAxiGC.setText("");
        etHorIsoV.setText("");
        etHorBduV.setText("");
        etHorGV.setText("");
        etVerIsoV.setText("");
        etVerBduV.setText("");
        etVerGV.setText("");
        etAxiIsoV.setText("");
        etAxiBduV.setText("");
        etAxiGV.setText("");
    }

    public void mostrarPDF(String rutaPDF) {
        File pdfFile = new File(rutaPDF);

        // Crea un intent para abrir el PDF con el visor de PDF instalado
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(this, "com.example.manttoprev.fileprovider", pdfFile);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Necesario para Android 7.0 y superior

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Maneja excepciones si no hay aplicaciones de visor de PDF instaladas
            Log.e("TuClase", "No se encontró una aplicación para abrir el PDF", e);
            Toast.makeText(this, "No se encontró una aplicación para abrir el PDF", Toast.LENGTH_SHORT).show();
        }
    }

}