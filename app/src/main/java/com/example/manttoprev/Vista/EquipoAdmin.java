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

import com.example.manttoprev.Modelo.Equipo;
import com.example.manttoprev.Presentador.EquipoAdminContract;
import com.example.manttoprev.Presentador.EquipoAdminPresenter;
import com.example.manttoprev.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipoAdmin extends AppCompatActivity implements EquipoAdminContract.View {

    private static final String NOMBRE = "nombre";
    private ListView lvListadoEquipo;
    private AutoCompleteTextView etEquipo;
    Spinner cboAreaEquipo;
    EditText etDescripcionEquipo;
    Button btnAgregarEquipo;
    Button btnConsultarEquipo;
    Button btnEditarEquipo;
    Button btnBorrarEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_admin);

        EquipoAdminContract.Presenter presenter;

        lvListadoEquipo = findViewById(R.id.lvListadoEquipo);
        etEquipo = findViewById(R.id.etEquipo);
        cboAreaEquipo = findViewById(R.id.cboAreaEquipo);
        etDescripcionEquipo = findViewById(R.id.etDescripcionEquipo);
        btnAgregarEquipo = findViewById(R.id.btnAgregarEquipo);
        btnConsultarEquipo = findViewById(R.id.btnConsultarEquipo);
        btnEditarEquipo = findViewById(R.id.btnEditarEquipo);
        btnBorrarEquipo = findViewById(R.id.btnBorrarEquipo);

        presenter = new EquipoAdminPresenter(this);
        presenter.listarEquipos();
        presenter.obtenerAreas();

        //Detecta cuando se selecciona un elemento de la lista
        lvListadoEquipo.setOnItemClickListener((parent, view, position, id) -> {
            Map<String, Object> equipo = (Map<String, Object>) parent.getItemAtPosition(position);
            String nombreEquipo = (String) equipo.get(NOMBRE);

            // Llama al método del presentador para obtener detalles de la categoría
            presenter.clicItemListaEquipo(nombreEquipo);
        });

        //Detecta los cambios en el texto del AutoCompleteTextView
        etEquipo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textoBusqueda = s.toString().trim();
                presenter.autocompletarEquipo(textoBusqueda);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnAgregarEquipo.setOnClickListener(v -> {
            String equipo = etEquipo.getText().toString().trim();
            String area = cboAreaEquipo.getSelectedItem().toString().trim();
            String descripcion = etDescripcionEquipo.getText().toString().trim();
            presenter.agregarEquipo(equipo, area, descripcion);
        });


        btnConsultarEquipo.setOnClickListener(v -> {
            String nombreEquipo = etEquipo.getText().toString().trim();
            presenter.consultarEquipo(nombreEquipo);
        });

        btnEditarEquipo.setOnClickListener(v -> {
            String nombre = etEquipo.getText().toString().trim();
            String area = cboAreaEquipo.getSelectedItem().toString().trim();
            String descripcion = etDescripcionEquipo.getText().toString().trim();
            presenter.editarEquipo(nombre, area, descripcion);
        });


        btnBorrarEquipo.setOnClickListener(v -> {
            String nombre = etEquipo.getText().toString().trim();
            presenter.borrarEquipo(nombre);
            clearEditTextFields();
        });

    }

    public void showEquipos(List<Equipo> equipos) {
        // Crear un adaptador personalizado para mostrar los equipos en la ListView
        List<Map<String, Object>> equiposMapList = new ArrayList<>();
        for (Equipo equipo : equipos) {
            Map<String, Object> equipoMap = new HashMap<>();
            equipoMap.put(NOMBRE, equipo.getNombre());
            equiposMapList.add(equipoMap);
        }
        String[] from = {NOMBRE};
        int[] to = {R.id.tvNombreEquipo};
        SimpleAdapter adapter = new SimpleAdapter(this, equiposMapList,
                R.layout.lista_equipo_item, from, to);
        lvListadoEquipo.setAdapter(adapter);
    }


    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        clearEditTextFields();
    }
    public void clearEditTextFields() {
        etEquipo.setText("");
        cboAreaEquipo.setSelection(0);
        etDescripcionEquipo.setText("");
    }
    @Override
    public void showDetallesEquipoSeleccionado(String nombreEquipo, String areaEquipo, String descripcionEquipo) {
        // Mostrar la información en los campos correspondientes
        etEquipo.setText(nombreEquipo);
        // Obtener el índice de la categoría seleccionada en el Spinner
        int index = obtenerIndiceArea(areaEquipo);
        cboAreaEquipo.setSelection(index);
        etDescripcionEquipo.setText(descripcionEquipo);
    }
    private int obtenerIndiceArea(String area) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cboAreaEquipo.getAdapter();
        return adapter.getPosition(area);
    }
    @Override
    public void showEquiposEncontradosAutocompletado(List<String> equipos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, equipos);
        etEquipo.setAdapter(adapter);
    }


    @Override
    public void showConsultarEquipo(Equipo equipo) {
        // Obtener el índice del área seleccionada en el Spinner
        int index = obtenerIndiceArea(equipo.getArea());
        etEquipo.setText(String.valueOf(equipo.getNombre()));
        cboAreaEquipo.setSelection(index);
        etDescripcionEquipo.setText(String.valueOf(equipo.getDescripcion()));
    }


    @Override
    public void mostrarAreas(List<String> areas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboAreaEquipo.setAdapter(adapter);
    }
}