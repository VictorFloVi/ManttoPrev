package com.example.manttoprev.Vista;


import androidx.activity.result.ActivityResultLauncher;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.example.manttoprev.Presentador.VibracionAdminContract;
import com.example.manttoprev.Presentador.VibracionAdminPresenter;
import com.example.manttoprev.R;

import org.json.JSONObject;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    ImageButton imaEscanearQRV;

    TextView resultTextView;

    private VibracionAdminContract.Presenter presenter;

    private String areaSeleccionadaScan;
    private String seccionSeleccionadaScan;
    private String equipoSeleccionadoScan;
    private String maquinaSeleccionadaScan;
    private String motorSeleccionadoScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityResultLauncher<Intent> qrScannerLauncher;
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


        qrScannerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String pushId = data.getStringExtra("QR_CONTENT");
                            if (pushId != null) presenter.procesarPushIdV(pushId);
                        }
                    }
                }
        );




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

        imaEscanearQRV = findViewById(R.id.imaEscanearQRV);

        resultTextView = findViewById(R.id.resultTextView);

        btnGuardarVibracion.setOnClickListener(view -> enviarDatos());

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


        imaEscanearQRV.setOnClickListener(v -> {
            Intent intent = new Intent(this, ScannerActivity.class);
            qrScannerLauncher.launch(intent);
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

            enviarDatos();

            presenter.guardarVibracion(area, seccion, equipo, maquina, motor, horisoc, horbduc, horgc,
                    verisoc, verbduc, vergc, axiisoc, axibduc, axigc, horisov, horbduv, horgv, verisov,
                    verbduv, vergv, axiisov, axibduv, axigv);


        });
    }


    @Override
    public void setValoresSeleccionV(String area, String seccion, String equipo, String maquina, String motor) {
        this.areaSeleccionadaScan = area;
        this.seccionSeleccionadaScan = seccion;
        this.equipoSeleccionadoScan = equipo;
        this.maquinaSeleccionadaScan = maquina;
        this.motorSeleccionadoScan = motor;
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




    private void enviarDatos() {


        new Thread(() -> {

            try {

                URL url = new URL("https://prediccion-motor-527278766855.us-central1.run.app/predict");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("g_hor_car", Double.parseDouble(etHorGC.getText().toString()));
                jsonParam.put("g_axi_car", Double.parseDouble(etAxiGC.getText().toString()));
                jsonParam.put("g_ver_car", Double.parseDouble(etVerGC.getText().toString()));
                jsonParam.put("g_hor_ven", Double.parseDouble(etHorGV.getText().toString()));
                jsonParam.put("g_axi_ven", Double.parseDouble(etAxiGV.getText().toString()));
                jsonParam.put("g_ver_ven", Double.parseDouble(etVerGV.getText().toString()));

                jsonParam.put("iso_hor_car", Double.parseDouble(etHorIsoC.getText().toString()));
                jsonParam.put("iso_axi_car", Double.parseDouble(etAxiIsoC.getText().toString()));
                jsonParam.put("iso_ver_car", Double.parseDouble(etVerIsoC.getText().toString()));
                jsonParam.put("iso_hor_ven", Double.parseDouble(etHorIsoV.getText().toString()));
                jsonParam.put("iso_axi_ven", Double.parseDouble(etAxiIsoV.getText().toString()));
                jsonParam.put("iso_ver_ven", Double.parseDouble(etVerIsoV.getText().toString()));

                jsonParam.put("bdu_hor_car", Double.parseDouble(etHorBduC.getText().toString()));
                jsonParam.put("bdu_axi_car", Double.parseDouble(etAxiBduC.getText().toString()));
                jsonParam.put("bdu_ver_car", Double.parseDouble(etVerBduC.getText().toString()));
                jsonParam.put("bdu_hor_ven", Double.parseDouble(etHorBduV.getText().toString()));
                jsonParam.put("bdu_axi_ven", Double.parseDouble(etAxiBduV.getText().toString()));
                jsonParam.put("bdu_ver_ven", Double.parseDouble(etVerBduV.getText().toString()));

                OutputStream os = conn.getOutputStream();
                os.write(jsonParam.toString().getBytes(StandardCharsets.UTF_8));
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    // Leer la respuesta del servidor
                    java.io.InputStream is = conn.getInputStream();
                    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
                    String result = s.hasNext() ? s.next() : "";

                    // Parsear el JSON de la respuesta
                    JSONObject responseJson = new JSONObject(result);
                    String prediccion = responseJson.getString("prediccion");

                    // Mostrar la predicción en el TextView
                    runOnUiThread(() -> {
                        Log.d("PREDICCION", "Resultado recibido: " + prediccion);
                        resultTextView.setText("Predicción: " + prediccion);

                        /*  NUEVO: crear alerta si la clase != 0  */
                        if (!"Motor en condiciones normales".equals(prediccion)) {
                            String fechaPeru = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
                                    Locale.US).format(new Date());

                            presenter.guardarAlerta(
                                    fechaPeru,
                                    prediccion,                                    // mensaje
                                    cboMotorV.getSelectedItem().toString(),        // motor
                                    cboMaquinasV.getSelectedItem().toString(),     // máquina
                                    ""                                            // aún sin URL
                            );
                        }
                    });



                    // Limpiar los EditText
                    etHorGC.setText("");
                    etAxiGC.setText("");
                    etVerGC.setText("");
                    etHorGV.setText("");
                    etAxiGV.setText("");
                    etVerGV.setText("");

                    etHorIsoC.setText("");
                    etAxiIsoC.setText("");
                    etVerIsoC.setText("");
                    etHorIsoV.setText("");
                    etAxiIsoV.setText("");
                    etVerIsoV.setText("");

                    etHorBduC.setText("");
                    etAxiBduC.setText("");
                    etVerBduC.setText("");
                    etHorBduV.setText("");
                    etAxiBduV.setText("");
                    etVerBduV.setText("");


                } else {
                    runOnUiThread(() -> Toast.makeText(VibracionAdmin.this, "Error en la conexión: " + responseCode, Toast.LENGTH_SHORT).show());
                }
                conn.disconnect();


            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(VibracionAdmin.this, "Excepción: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
                e.printStackTrace();
            }
        }).start();
    }




    @Override
    public void mostrarAreas(List<String> areas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboAreaV.setAdapter(adapter);

        seleccionarSpinnerPorValor(cboAreaV, areaSeleccionadaScan);
        presenter.obtenerSecciones(areaSeleccionadaScan);


    }
    @Override
    public void mostrarSecciones(List<String> secciones) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, secciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboSeccionesV.setAdapter(adapter);

        seleccionarSpinnerPorValor(cboSeccionesV, seccionSeleccionadaScan);
        presenter.obtenerEquipos(seccionSeleccionadaScan);


    }
    @Override
    public void mostrarEquipos(List<String> equipos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboEquiposV.setAdapter(adapter);

        seleccionarSpinnerPorValor(cboEquiposV, equipoSeleccionadoScan);
        presenter.obtenerMaquinas(equipoSeleccionadoScan);


    }
    @Override
    public void mostrarMaquinas(List<String> maquinas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, maquinas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboMaquinasV.setAdapter(adapter);

        seleccionarSpinnerPorValor(cboMaquinasV, maquinaSeleccionadaScan);
        presenter.obtenerMotores(maquinaSeleccionadaScan);


    }
    @Override
    public void mostrarMotores(List<String> motores) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, motores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboMotorV.setAdapter(adapter);

        seleccionarSpinnerPorValor(cboMotorV, motorSeleccionadoScan);
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