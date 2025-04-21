package com.example.manttoprev.Vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.manttoprev.Modelo.Seccion;
import com.example.manttoprev.Presentador.SeccionAdminContract;
import com.example.manttoprev.Presentador.SeccionAdminPresenter;
import com.example.manttoprev.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeccionAdmin extends AppCompatActivity implements SeccionAdminContract.View {

    private static final String NOMBRE = "nombre";
    private ListView lvListadoSeccion;
    private AutoCompleteTextView etSeccion;
    Spinner cboAreaSeccion;
    EditText etDescripcionSeccion;
    Button btnAgregarSeccion;
    Button btnConsultarSeccion;
    Button btnEditarSeccion;
    Button btnBorrarSeccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seccion_admin);

        Toolbar toolbar = findViewById(R.id.toolbarSeccionAdmin);
        setSupportActionBar(toolbar);

        // Habilitar la flecha de retroceso en la barra de acción
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v ->  {

            Intent intent = new Intent(SeccionAdmin.this, Mantenimiento.class);
            startActivity(intent);
        });

        SeccionAdminContract.Presenter presenter;

        lvListadoSeccion = findViewById(R.id.lvListadoSeccion);
        etSeccion = findViewById(R.id.etSeccion);
        cboAreaSeccion = findViewById(R.id.cboAreaSeccion);
        etDescripcionSeccion = findViewById(R.id.etDescripcionSeccion);
        btnAgregarSeccion = findViewById(R.id.btnAgregarSeccion);
        btnConsultarSeccion = findViewById(R.id.btnConsultarSeccion);
        btnEditarSeccion = findViewById(R.id.btnEditarSeccion);
        btnBorrarSeccion = findViewById(R.id.btnBorrarSeccion);

        presenter = new SeccionAdminPresenter(this);
        presenter.listarSecciones();
        presenter.obtenerAreas();

        //Detecta cuando se selecciona un elemento de la lista
        lvListadoSeccion.setOnItemClickListener((parent, view, position, id) -> {
            Map<String, Object> seccion = (Map<String, Object>) parent.getItemAtPosition(position);
            String nombreSeccion = (String) seccion.get(NOMBRE);

            // Llama al método del presentador para obtener detalles de la sección
            presenter.clicItemListaSeccion(nombreSeccion);
        });

        //Detecta los cambios en el texto del AutoCompleteTextView
        etSeccion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textoBusqueda = s.toString().trim();
                presenter.autocompletarSeccion(textoBusqueda);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        });

        btnAgregarSeccion.setOnClickListener(v -> {
            String seccion = etSeccion.getText().toString().trim();
            String area = cboAreaSeccion.getSelectedItem().toString().trim();
            String descripcion = etDescripcionSeccion.getText().toString().trim();
            presenter.agregarSeccion(seccion, area, descripcion);
        });


        btnConsultarSeccion.setOnClickListener(v -> {
            String nombreSeccion = etSeccion.getText().toString().trim();
            presenter.consultarSeccion(nombreSeccion);
        });

        btnEditarSeccion.setOnClickListener(v -> {
            String nombre = etSeccion.getText().toString().trim();
            String area = cboAreaSeccion.getSelectedItem().toString().trim();
            String descripcion = etDescripcionSeccion.getText().toString().trim();
            presenter.editarSeccion(nombre, area, descripcion);
        });


        btnBorrarSeccion.setOnClickListener(v -> {
            String nombre = etSeccion.getText().toString().trim();
            presenter.borrarSeccion(nombre);
            clearEditTextFields();
        });

    }

    public void showSecciones(List<Seccion> secciones) {
        // Crear un adaptador personalizado para mostrar los seccions en la ListView
        List<Map<String, Object>> seccionesMapList = new ArrayList<>();
        for (Seccion seccion : secciones) {
            Map<String, Object> seccionMap = new HashMap<>();
            seccionMap.put(NOMBRE, seccion.getNombre());
            seccionesMapList.add(seccionMap);
        }
        String[] from = {NOMBRE};
        int[] to = {R.id.tvNombreSeccion};
        SimpleAdapter adapter = new SimpleAdapter(this, seccionesMapList,
                R.layout.lista_seccion_item, from, to);
        lvListadoSeccion.setAdapter(adapter);
    }


    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        clearEditTextFields();
    }
    public void clearEditTextFields() {
        etSeccion.setText("");
        cboAreaSeccion.setSelection(0);
        etDescripcionSeccion.setText("");
    }
    @Override
    public void showDetallesSeccionSeleccionado(String nombreSeccion, String areaSeccion, String descripcionSeccion) {
        // Mostrar la información en los campos correspondientes
        etSeccion.setText(nombreSeccion);
        // Obtener el índice de la sección seleccionada en el Spinner
        int index = obtenerIndiceArea(areaSeccion);
        cboAreaSeccion.setSelection(index);
        etDescripcionSeccion.setText(descripcionSeccion);
    }
    private int obtenerIndiceArea(String area) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cboAreaSeccion.getAdapter();
        return adapter.getPosition(area);
    }
    @Override
    public void showSeccionesEncontradosAutocompletado(List<String> secciones) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, secciones);
        etSeccion.setAdapter(adapter);
    }


    @Override
    public void showConsultarSeccion(Seccion seccion) {
        // Obtener el índice del área seleccionada en el Spinner
        int index = obtenerIndiceArea(seccion.getArea());
        etSeccion.setText(String.valueOf(seccion.getNombre()));
        cboAreaSeccion.setSelection(index);
        etDescripcionSeccion.setText(String.valueOf(seccion.getDescripcion()));
    }


    @Override
    public void mostrarAreas(List<String> areas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboAreaSeccion.setAdapter(adapter);
    }
}