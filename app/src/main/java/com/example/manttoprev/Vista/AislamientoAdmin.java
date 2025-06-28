package com.example.manttoprev.Vista;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;
import com.example.manttoprev.Presentador.AislamientoAdminContract;
import com.example.manttoprev.Presentador.AislamientoAdminPresenter;
import com.example.manttoprev.R;
import java.io.File;
import java.util.List;


public class AislamientoAdmin extends AppCompatActivity implements AislamientoAdminContract.View {

    Spinner cboArea;
    Spinner cboSecciones;
    Spinner cboEquipos;
    Spinner cboMaquinas;
    Spinner cboMotor;
    TableLayout tbAislamiento;

    EditText etMegadoU;
    EditText etMegadoV;
    EditText etMegadoW;

    EditText etResistenciaU;
    EditText etResistenciaV;
    EditText etResistenciaW;

    EditText etAmperajeU;
    EditText etAmperajeV;
    EditText etAmperajeW;

    Button btnGuardarAislamiento;

    ImageButton imaEscanearQR;

    private AislamientoAdminContract.Presenter presenter;
    private String areaSeleccionada;
    private String seccionSeleccionada;
    private String equipoSeleccionado;
    private String maquinaSeleccionada;
    private String motorSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aislamiento_admin);

        Toolbar toolbar = findViewById(R.id.toolbarAislamiento);
        setSupportActionBar(toolbar);

        // Habilitar la flecha de retroceso en la barra de acción
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v ->  {

            Intent intent = new Intent(AislamientoAdmin.this, InterfazPrincipal.class);
            startActivity(intent);

        });

        cboArea = findViewById(R.id.cboArea);
        cboSecciones = findViewById(R.id.cboSecciones);
        cboEquipos = findViewById(R.id.cboEquipos);
        cboMaquinas= findViewById(R.id.cboMaquinas);
        cboMotor = findViewById(R.id.cboMotor);
        tbAislamiento = findViewById(R.id.tbAislamiento);

        etMegadoU = findViewById(R.id.etMegadoU);
        etMegadoV = findViewById(R.id.etMegadoV);
        etMegadoW = findViewById(R.id.etMegadoW);

        etMegadoW.setOnEditorActionListener((v, actionId, event) -> {
            etResistenciaU.requestFocus(); // Mueve el cursor
            return true;
        });


        etResistenciaU = findViewById(R.id.etResistenciaU);
        etResistenciaV = findViewById(R.id.etResistenciaV);
        etResistenciaW = findViewById(R.id.etResistenciaW);

        etResistenciaW.setOnEditorActionListener((v, actionId, event) -> {
            etAmperajeU.requestFocus(); // Mueve el cursor
            return true;
        });

        etAmperajeU = findViewById(R.id.etAmperajeU);
        etAmperajeV = findViewById(R.id.etAmperajeV);
        etAmperajeW = findViewById(R.id.etAmperajeW);

        btnGuardarAislamiento = findViewById(R.id.btnGuardarAislamiento);
        imaEscanearQR = findViewById(R.id.imaEscanearQR);


        presenter = new AislamientoAdminPresenter(this);

        tbAislamiento.setVisibility(View.GONE);

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
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        cboEquipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        cboMaquinas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        cboMotor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String motorSeleccionado = parent.getItemAtPosition(position).toString();

                // Haz visible la tabla cuando se seleccione un motor
                if (!motorSeleccionado.equals("Motor")) {
                    tbAislamiento.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        imaEscanearQR.setOnClickListener(v ->
                startActivityForResult(
                        new Intent(this, ScannerActivity.class), 2001)
        );


        btnGuardarAislamiento.setOnClickListener(v -> {
            String area = cboArea.getSelectedItem().toString().trim();
            String seccion = cboSecciones.getSelectedItem().toString().trim();
            String equipo = cboEquipos.getSelectedItem().toString().trim();
            String maquina = cboMaquinas.getSelectedItem().toString().trim();
            String motor = cboMotor.getSelectedItem().toString().trim();

            Double megadou = Double.parseDouble(etMegadoU.getText().toString().trim());
            Double megadov = Double.parseDouble(etMegadoV.getText().toString().trim());
            Double megadow = Double.parseDouble(etMegadoW.getText().toString().trim());

            Double resistenciau = Double.parseDouble(etResistenciaU.getText().toString().trim());
            Double resistenciav = Double.parseDouble(etResistenciaV.getText().toString().trim());
            Double resistenciaw = Double.parseDouble(etResistenciaW.getText().toString().trim());

            Double amperajeu = Double.parseDouble(etAmperajeU.getText().toString().trim());
            Double amperajev = Double.parseDouble(etAmperajeV.getText().toString().trim());
            Double amperajew = Double.parseDouble(etAmperajeW.getText().toString().trim());

            presenter.guardarAislamiento(area, seccion, equipo, maquina, motor, megadou, megadov, megadow, resistenciau, 
                    resistenciav, resistenciaw, amperajeu, amperajev, amperajew);
        });
    }


    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if (req == 2001 && res == RESULT_OK && data != null) {
            String pushId = data.getStringExtra("QR_CONTENT");
            if (pushId != null) presenter.procesarPushId(pushId);
        }
    }

    @Override
    public void setValoresSeleccion(String area, String seccion, String equipo, String maquina, String motor) {
        this.areaSeleccionada = area;
        this.seccionSeleccionada = seccion;
        this.equipoSeleccionado = equipo;
        this.maquinaSeleccionada = maquina;
        this.motorSeleccionado = motor;
    }


    private void seleccionarSpinnerPorValor(Spinner sp, String valor) {
        if (valor == null) return;
        ArrayAdapter<?> ad = (ArrayAdapter<?>) sp.getAdapter();
        if (ad == null) return;
        for (int i = 0; i < ad.getCount(); i++) {
            if (valor.equals(ad.getItem(i))) {
                sp.setSelection(i);
                break;
            }
        }
    }


    @Override
    public void mostrarAreas(List<String> areas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboArea.setAdapter(adapter);

        seleccionarSpinnerPorValor(cboArea, areaSeleccionada);
        presenter.obtenerSecciones(areaSeleccionada);
    }
    @Override
    public void mostrarSecciones(List<String> secciones) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, secciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboSecciones.setAdapter(adapter);

        seleccionarSpinnerPorValor(cboSecciones, seccionSeleccionada);
        presenter.obtenerEquipos(seccionSeleccionada);
    }

    @Override
    public void mostrarEquipos(List<String> equipos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboEquipos.setAdapter(adapter);

        seleccionarSpinnerPorValor(cboEquipos, equipoSeleccionado);
        presenter.obtenerMaquinas(equipoSeleccionado);
    }
    @Override
    public void mostrarMaquinas(List<String> maquinas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, maquinas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboMaquinas.setAdapter(adapter);

        seleccionarSpinnerPorValor(cboMaquinas, maquinaSeleccionada);
        presenter.obtenerMotores(maquinaSeleccionada);
    }

    @Override
    public void mostrarMotores(List<String> motores) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, motores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboMotor.setAdapter(adapter);

        seleccionarSpinnerPorValor(cboMotor, motorSeleccionado);

    }

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        clearEditTextFields();
    }

    public void clearEditTextFields() {
        cboArea.setSelection(0);
        cboSecciones.setSelection(0);
        cboEquipos.setSelection(0);
        cboMaquinas.setSelection(0);
        cboMotor.setSelection(0);
        etMegadoU.setText("");
        etMegadoV.setText("");
        etMegadoW.setText("");
        etResistenciaU.setText("");
        etResistenciaV.setText("");
        etResistenciaW.setText("");
        etAmperajeU.setText("");
        etAmperajeV.setText("");
        etAmperajeW.setText("");
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