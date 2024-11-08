package com.example.manttoprev.Vista;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.manttoprev.Modelo.Ubicacion2;
import com.example.manttoprev.Presentador.Ubicacion2AdminContract;
import com.example.manttoprev.Presentador.Ubicacion2AdminPresenter;
import com.example.manttoprev.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ubicacion2Admin extends AppCompatActivity implements Ubicacion2AdminContract.View {

    private static final String NOMBRE = "nombre";
    private ListView lvListadoUbicacion2;
    private AutoCompleteTextView etUbicacion2;
    Spinner cboUbicacionUbicacion2;
    EditText etDescripcionUbicacion2;
    Button btnAgregarUbicacion2;
    Button btnConsultarUbicacion2;
    Button btnEditarUbicacion2;
    Button btnBorrarUbicacion2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion2_admin);

        Ubicacion2AdminContract.Presenter presenter;

        lvListadoUbicacion2 = findViewById(R.id.lvListadoUbicacion2);
        etUbicacion2 = findViewById(R.id.etUbicacion2);
        cboUbicacionUbicacion2 = findViewById(R.id.cboUbicacionUbicacion2);
        etDescripcionUbicacion2 = findViewById(R.id.etDescripcionUbicacion2);
        btnAgregarUbicacion2 = findViewById(R.id.btnAgregarUbicacion2);
        btnConsultarUbicacion2 = findViewById(R.id.btnConsultarUbicacion2);
        btnEditarUbicacion2 = findViewById(R.id.btnEditarUbicacion2);
        btnBorrarUbicacion2 = findViewById(R.id.btnBorrarUbicacion2);

        presenter = new Ubicacion2AdminPresenter(this);
        presenter.listarUbicacion2();
        presenter.obtenerUbicacion();

        //Detecta cuando se selecciona un elemento de la lista
        lvListadoUbicacion2.setOnItemClickListener((parent, view, position, id) -> {
            Map<String, Object> ubicacion2 = (Map<String, Object>) parent.getItemAtPosition(position);
            String nombreUbicacion2 = (String) ubicacion2.get(NOMBRE);

            // Llama al método del presentador para obtener detalles de la ubicación
            presenter.clicItemListaUbicacion2(nombreUbicacion2);
        });

        //Detecta los cambios en el texto del AutoCompleteTextView
        etUbicacion2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textoBusqueda = s.toString().trim();
                presenter.autocompletarUbicacion2(textoBusqueda);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnAgregarUbicacion2.setOnClickListener(v -> {
            String ubicacion2 = etUbicacion2.getText().toString().trim();
            String ubicacion = cboUbicacionUbicacion2.getSelectedItem().toString().trim();
            String descripcion = etDescripcionUbicacion2.getText().toString().trim();
            presenter.agregarUbicacion2(ubicacion2, ubicacion, descripcion);
        });


        btnConsultarUbicacion2.setOnClickListener(v -> {
            String nombreUbcacion2 = etUbicacion2.getText().toString().trim();
            presenter.consultarUbicacion2(nombreUbcacion2);
        });

        btnEditarUbicacion2.setOnClickListener(v -> {
            String nombre = etUbicacion2.getText().toString().trim();
            String ubicacion = cboUbicacionUbicacion2.getSelectedItem().toString().trim();
            String descripcion = etDescripcionUbicacion2.getText().toString().trim();
            presenter.editarUbicacion2(nombre, ubicacion, descripcion);
        });


        btnBorrarUbicacion2.setOnClickListener(v -> {
            String nombre = etUbicacion2.getText().toString().trim();
            presenter.borrarUbicacion2(nombre);
            clearEditTextFields();
        });

    }

    public void showUbicacion2(List<Ubicacion2> ubicacion2s) {
        // Crear un adaptador personalizado para mostrar los ubicaciones en la ListView
        List<Map<String, Object>> equiposMapList = new ArrayList<>();
        for (Ubicacion2 ubicacion2 : ubicacion2s) {
            Map<String, Object> ubicacion2Map = new HashMap<>();
            ubicacion2Map.put(NOMBRE, ubicacion2.getNombre());
            equiposMapList.add(ubicacion2Map);
        }
        String[] from = {NOMBRE};
        int[] to = {R.id.tvNombreEquipo};
        SimpleAdapter adapter = new SimpleAdapter(this, equiposMapList,
                R.layout.lista_ubicacion2_item, from, to);
        lvListadoUbicacion2.setAdapter(adapter);
    }


    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        clearEditTextFields();
    }
    public void clearEditTextFields() {
        etUbicacion2.setText("");
        cboUbicacionUbicacion2.setSelection(0);
        etDescripcionUbicacion2.setText("");
    }
    @Override
    public void showDetallesUbicacion2Seleccionado(String nombreUbicacion2, String ubicacionUbicacion2, String descripcionUbicacion2) {
        // Mostrar la información en los campos correspondientes
        etUbicacion2.setText(nombreUbicacion2);
        // Obtener el índice de la categoría seleccionada en el Spinner
        int index = obtenerIndiceUbicacion(ubicacionUbicacion2);
        cboUbicacionUbicacion2.setSelection(index);
        etDescripcionUbicacion2.setText(descripcionUbicacion2);
    }
    private int obtenerIndiceUbicacion(String ubicacion) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cboUbicacionUbicacion2.getAdapter();
        return adapter.getPosition(ubicacion);
    }
    @Override
    public void showUbicacion2EncontradosAutocompletado(List<String> ubicacion2) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, ubicacion2);
        etUbicacion2.setAdapter(adapter);
    }


    @Override
    public void showConsultarUbicacion2(Ubicacion2 ubicacion2) {
        // Obtener el índice del área seleccionada en el Spinner
        int index = obtenerIndiceUbicacion(ubicacion2.getUbicacion());
        etUbicacion2.setText(String.valueOf(ubicacion2.getNombre()));
        cboUbicacionUbicacion2.setSelection(index);
        etDescripcionUbicacion2.setText(String.valueOf(ubicacion2.getDescripcion()));
    }


    @Override
    public void mostrarUbicacion(List<String> ubicacion) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ubicacion);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboUbicacionUbicacion2.setAdapter(adapter);
    }
}