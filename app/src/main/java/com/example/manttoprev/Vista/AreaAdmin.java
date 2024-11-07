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

import com.example.manttoprev.Modelo.Area;
import com.example.manttoprev.Presentador.AreaAdminContract;
import com.example.manttoprev.Presentador.AreaAdminPresenter;
import com.example.manttoprev.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaAdmin extends AppCompatActivity implements AreaAdminContract.View {

    private static final String NOMBRE = "nombre";
    private ListView lvListadoArea;
    private AutoCompleteTextView etArea;
    EditText etDescripcionArea;
    Button btnAgregarArea;
    Button btnConsultarArea;
    Button btnEditarArea;
    Button btnBorrarArea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_admin);

        AreaAdminContract.Presenter presenter;

        lvListadoArea = findViewById(R.id.lvListadoArea);
        etArea = findViewById(R.id.etArea);
        etDescripcionArea = findViewById(R.id.etDescripcionArea);
        btnAgregarArea = findViewById(R.id.btnAgregarArea);
        btnConsultarArea = findViewById(R.id.btnConsultarArea);
        btnEditarArea = findViewById(R.id.btnEditarArea);
        btnBorrarArea = findViewById(R.id.btnBorrarArea);

        presenter = new AreaAdminPresenter(this);
        presenter.listarAreas();

        //Detecta cuando se selecciona un elemento de la lista
        lvListadoArea.setOnItemClickListener((parent, view, position, id) -> {
            Map<String, Object> area = (Map<String, Object>) parent.getItemAtPosition(position);
            String nombreArea = (String) area.get(NOMBRE);

            // Llama al método del presentador para obtener detalles de la categoría
            presenter.clicItemListaArea(nombreArea);
        });

        //Detecta los cambios en el texto del AutoCompleteTextView
        etArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textoBusqueda = s.toString().trim();
                presenter.autocompletarArea(textoBusqueda);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        btnAgregarArea.setOnClickListener(v -> {
            String area = etArea.getText().toString().trim();
            String descripcion = etDescripcionArea.getText().toString().trim();
            presenter.agregarArea(area, descripcion);
        });

        btnConsultarArea.setOnClickListener(v -> {
            String nombreArea = etArea.getText().toString().trim();
            presenter.consultarArea(nombreArea);
        });

        btnEditarArea.setOnClickListener(v -> {
            String nombre = etArea.getText().toString().trim();
            String descripcion = etDescripcionArea.getText().toString().trim();
            presenter.editarArea(nombre, descripcion);
        });

        btnBorrarArea.setOnClickListener(v -> {
            String nombre = etArea.getText().toString().trim();
            presenter.borrarArea(nombre);
            clearEditTextFields();
        });

    }


    public void showAreas(List<Area> areas) {
        // Crear un adaptador personalizado para mostrar las áreas en la ListView
        List<Map<String, Object>> areasMapList = new ArrayList<>();
        for (Area area : areas) {
            Map<String, Object> areaMap = new HashMap<>();
            areaMap.put(NOMBRE, area.getNombre());
            areasMapList.add(areaMap);
        }
        String[] from = {NOMBRE};
        int[] to = {R.id.tvNombreEquipo};
        SimpleAdapter adapter = new SimpleAdapter(this, areasMapList,
                R.layout.lista_area_item, from, to);
        lvListadoArea.setAdapter(adapter);
    }

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        clearEditTextFields();
    }

    public void clearEditTextFields() {
        etArea.setText("");
        etDescripcionArea.setText("");
    }

    @Override
    public void showDetallesAreaSeleccionado(String nombreArea, String descripcionArea) {
        // Mostrar la información en los campos correspondientes
        etArea.setText(nombreArea);
        etDescripcionArea.setText(descripcionArea);

    }

    @Override
    public void showAreasEncontradosAutocompletado(List<String> areas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, areas);
        etArea.setAdapter(adapter);

    }

    @Override
    public void showConsultarArea(Area area) {
        etArea.setText(String.valueOf(area.getNombre()));
        etDescripcionArea.setText(String.valueOf(area.getDescripcion()));
    }

}