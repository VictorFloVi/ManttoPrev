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
import android.widget.Toast;

import com.example.manttoprev.Modelo.Ubicacion;
import com.example.manttoprev.Presentador.UbicacionAdminContract;
import com.example.manttoprev.Presentador.UbiicacionAdminPresenter;
import com.example.manttoprev.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UbicacionAdmin extends AppCompatActivity implements UbicacionAdminContract.View {

    private static final String NOMBRE = "nombre";
    private ListView lvListadoUbicacion;
    private AutoCompleteTextView etUbicacion;
    EditText etDescripcionUbicacion;
    Button btnAgregarUbicacion;
    Button btnConsultarUbicacion;
    Button btnEditarUbicacion;
    Button btnBorrarUbicacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_admin);

        UbicacionAdminContract.Presenter presenter;

        lvListadoUbicacion = findViewById(R.id.lvListadoUbicacion);
        etUbicacion = findViewById(R.id.etUbicacion);
        etDescripcionUbicacion = findViewById(R.id.etDescripcionUbicacion);
        btnAgregarUbicacion = findViewById(R.id.btnAgregarUbicacion);
        btnConsultarUbicacion = findViewById(R.id.btnConsultarUbicacion);
        btnEditarUbicacion = findViewById(R.id.btnEditarUbicacion);
        btnBorrarUbicacion = findViewById(R.id.btnBorrarUbicacion);

        presenter = new UbiicacionAdminPresenter(this);
        presenter.listarUbicacion();

        //Detecta cuando se selecciona un elemento de la lista
        lvListadoUbicacion.setOnItemClickListener((parent, view, position, id) -> {
            Map<String, Object> ubicacion = (Map<String, Object>) parent.getItemAtPosition(position);
            String nombreUbicacion = (String) ubicacion.get(NOMBRE);

            // Llama al método del presentador para obtener detalles de la categoría
            presenter.clicItemListaUbicacion(nombreUbicacion);
        });

        //Detecta los cambios en el texto del AutoCompleteTextView
        etUbicacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textoBusqueda = s.toString().trim();
                presenter.autocompletarUbicacion(textoBusqueda);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        btnAgregarUbicacion.setOnClickListener(v -> {
            String ubicacion = etUbicacion.getText().toString().trim();
            String descripcion = etDescripcionUbicacion.getText().toString().trim();
            presenter.agregarUbicacion(ubicacion, descripcion);
        });

        btnConsultarUbicacion.setOnClickListener(v -> {
            String nombreUbicacion = etUbicacion.getText().toString().trim();
            presenter.consultarUbicacion(nombreUbicacion);
        });

        btnEditarUbicacion.setOnClickListener(v -> {
            String nombre = etUbicacion.getText().toString().trim();
            String descripcion = etDescripcionUbicacion.getText().toString().trim();
            presenter.editarUbicacion(nombre, descripcion);
        });

        btnBorrarUbicacion.setOnClickListener(v -> {
            String nombre = etUbicacion.getText().toString().trim();
            presenter.borrarUbicacion(nombre);
            clearEditTextFields();
        });

    }


    public void showUbicacion(List<Ubicacion> ubicacions) {
        // Crear un adaptador personalizado para mostrar las ubicaciones en la ListView
        List<Map<String, Object>> ubicacionMapList = new ArrayList<>();
        for (Ubicacion ubicacion : ubicacions) {
            Map<String, Object> ubicacionMap = new HashMap<>();
            ubicacionMap.put(NOMBRE, ubicacion.getNombre());
            ubicacionMapList.add(ubicacionMap);
        }
        String[] from = {NOMBRE};
        int[] to = {R.id.tvNombreEquipo};
        SimpleAdapter adapter = new SimpleAdapter(this, ubicacionMapList,
                R.layout.lista_ubicacion_item, from, to);
        lvListadoUbicacion.setAdapter(adapter);
    }

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        clearEditTextFields();
    }

    public void clearEditTextFields() {
        etUbicacion.setText("");
        etDescripcionUbicacion.setText("");
    }

    @Override
    public void showDetallesUbicacionSeleccionada(String nombreUbicacion, String descripcionUbicacion) {
        // Mostrar la información en los campos correspondientes
        etUbicacion.setText(nombreUbicacion);
        etDescripcionUbicacion.setText(descripcionUbicacion);

    }

    @Override
    public void showUbicacionEncontradaAutocompletado(List<String> ubicacion) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, ubicacion);
        etUbicacion.setAdapter(adapter);

    }

    @Override
    public void showConsultarUbicacion(Ubicacion ubicacion) {
        etUbicacion.setText(String.valueOf(ubicacion.getNombre()));
        etDescripcionUbicacion.setText(String.valueOf(ubicacion.getDescripcion()));
    }

}