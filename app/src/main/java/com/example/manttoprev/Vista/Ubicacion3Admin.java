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

import com.example.manttoprev.Modelo.Ubicacion3;
import com.example.manttoprev.Presentador.Ubicacion3AdminContract;
import com.example.manttoprev.Presentador.Ubicacion3AdminPresenter;
import com.example.manttoprev.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ubicacion3Admin extends AppCompatActivity implements Ubicacion3AdminContract.View {

    private static final String NOMBRE = "nombre";
    private ListView lvListadoUbicacion3;
    private AutoCompleteTextView etUbicacion3;
    Spinner cboUbicacion3Ubicacion2;
    Spinner cboUbicacion3Ubicacion;
    EditText etDescripcionUbicacion3;
    Button btnAgregarUbicacion3;
    Button btnConsultarUbicacion3;
    Button btnEditarUbicacion3;
    Button btnBorrarUbicacion3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion3_admin);

        Ubicacion3AdminContract.Presenter presenter;
        lvListadoUbicacion3 = findViewById(R.id.lvListadoUbicacion3);
        etUbicacion3 = findViewById(R.id.etUbicacion3);
        cboUbicacion3Ubicacion2 = findViewById(R.id.cboUbicacion3Ubicacion2);
        cboUbicacion3Ubicacion = findViewById(R.id.cboUbicacion3Ubicacion);
        etDescripcionUbicacion3 = findViewById(R.id.etDescripcionUbicacion3);
        btnAgregarUbicacion3 = findViewById(R.id.btnAgregarUbicacion3);
        btnConsultarUbicacion3 = findViewById(R.id.btnConsultarUbicacion3);
        btnEditarUbicacion3 = findViewById(R.id.btnEditarUbicacion3);
        btnBorrarUbicacion3 = findViewById(R.id.btnBorrarUbicacion3);

        presenter = new Ubicacion3AdminPresenter(this);

        presenter.listarUbicacion3();
        presenter.obtenerUbicacion2();
        presenter.obtenerUbicacion();

        //Detecta cuando se selecciona un elemento de la lista
        lvListadoUbicacion3.setOnItemClickListener((parent, view, position, id) -> {
            Map<String, Object> ubicacion3 = (Map<String, Object>) parent.getItemAtPosition(position);
            String nombreUbicacion3 = (String) ubicacion3.get(NOMBRE);

            // Llama al método del presentador para obtener detalles de la categoría
            presenter.clicItemListaUbicacion3(nombreUbicacion3);
        });


        //Detecta los cambios en el texto del AutoCompleteTextView
        etUbicacion3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textoBusqueda = s.toString().trim();
                presenter.autocompletarUbicacion3(textoBusqueda);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnAgregarUbicacion3.setOnClickListener(v -> {
            String ubicacion3 = etUbicacion3.getText().toString().trim();
            String ubicacion2 = cboUbicacion3Ubicacion2.getSelectedItem().toString().trim();
            String ubicacion = cboUbicacion3Ubicacion.getSelectedItem().toString().trim();
            String descripcion = etDescripcionUbicacion3.getText().toString().trim();
            presenter.agregarUbicacion3(ubicacion3, ubicacion2, ubicacion, descripcion);
        });

        btnConsultarUbicacion3.setOnClickListener(v -> {
            String nombreUbicacion2 = etUbicacion3.getText().toString().trim();
            presenter.consultarUbicacion3(nombreUbicacion2);
        });

        btnEditarUbicacion3.setOnClickListener(v -> {
            String nombre = etUbicacion3.getText().toString().trim();
            String ubicacion2 = cboUbicacion3Ubicacion2.getSelectedItem().toString().trim();
            String ubicacion = cboUbicacion3Ubicacion.getSelectedItem().toString().trim();
            String descripcion = etDescripcionUbicacion3.getText().toString().trim();
            presenter.editarUbicacion3(nombre, ubicacion2, ubicacion, descripcion);
        });

        btnBorrarUbicacion3.setOnClickListener(v -> {
            String nombre = etUbicacion3.getText().toString().trim();
            presenter.borrarUbicacion3(nombre);
            clearEditTextFields();
        });

    }



    public void showUbicacion3(List<Ubicacion3> ubicacion3s) {
        // Crear un adaptador personalizado para mostrar las máquinas en la ListView
        List<Map<String, Object>> ubicacion3MapList = new ArrayList<>();
        for (Ubicacion3 ubicacion3 : ubicacion3s) {
            Map<String, Object> ubicacion3Map = new HashMap<>();
            ubicacion3Map.put(NOMBRE, ubicacion3.getNombre());
            ubicacion3MapList.add(ubicacion3Map);
        }
        String[] from = {NOMBRE};
        int[] to = {R.id.tvNombreUbicacion3};
        SimpleAdapter adapter = new SimpleAdapter(this, ubicacion3MapList,
                R.layout.lista_ubicacion3_item, from, to);
        lvListadoUbicacion3.setAdapter(adapter);
    }

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        clearEditTextFields();
    }
    public void clearEditTextFields() {
        etUbicacion3.setText("");
        cboUbicacion3Ubicacion2.setSelection(0);
        cboUbicacion3Ubicacion.setSelection(0);
        etDescripcionUbicacion3.setText("");
    }

    @Override
    public void showDetallesUbicacion3Seleccionado(String nombreUbicacion3, String Ubicacion2Ubicacion3, String UbicacionUbicacion3, String descripcionUbicacion3) {
        // Mostrar la información en los campos correspondientes
        etUbicacion3.setText(nombreUbicacion3);
        // Obtener el índice del equipo seleccionado en el Spinner
        int index = obtenerIndiceUbicacion2(Ubicacion2Ubicacion3);
        int indexa = obtenerIndiceUbicacion(UbicacionUbicacion3);
        cboUbicacion3Ubicacion2.setSelection(index);
        cboUbicacion3Ubicacion.setSelection(indexa);
        etDescripcionUbicacion3.setText(descripcionUbicacion3);
    }
    private int obtenerIndiceUbicacion2(String ubicacion2) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cboUbicacion3Ubicacion2.getAdapter();
        return adapter.getPosition(ubicacion2);
    }
    private int obtenerIndiceUbicacion(String ubicacion) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cboUbicacion3Ubicacion.getAdapter();
        return adapter.getPosition(ubicacion);
    }

    @Override
    public void showUbicacion3EncontradosAutocompletado(List<String> ubicacion3) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, ubicacion3);
        etUbicacion3.setAdapter(adapter);
    }

    @Override
    public void showConsultarUbicacion3(Ubicacion3 ubicacion3) {
        // Obtener el índice de la máquina seleccionada en el Spinner
        int index = obtenerIndiceUbicacion2(ubicacion3.getUbicacion2());
        int indexa = obtenerIndiceUbicacion(ubicacion3.getUbicacion());
        etUbicacion3.setText(String.valueOf(ubicacion3.getNombre()));
        cboUbicacion3Ubicacion2.setSelection(index);
        cboUbicacion3Ubicacion.setSelection(indexa);
        etDescripcionUbicacion3.setText(String.valueOf(ubicacion3.getDescripcion()));
    }

    @Override
    public void mostrarUbicacion2(List<String> ubicacion2) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ubicacion2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboUbicacion3Ubicacion2.setAdapter(adapter);
    }
    @Override
    public void mostrarUbicacion(List<String> ubicacion) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ubicacion);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboUbicacion3Ubicacion.setAdapter(adapter);
    }
}