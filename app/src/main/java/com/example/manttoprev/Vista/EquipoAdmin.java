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
    Spinner cboEquipoSeccion;
    Spinner cboEquipoArea;
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
        cboEquipoSeccion = findViewById(R.id.cboEquipoSeccion);
        cboEquipoArea = findViewById(R.id.cboEquipoArea);
        etDescripcionEquipo = findViewById(R.id.etDescripcionEquipo);
        btnAgregarEquipo = findViewById(R.id.btnAgregarEquipo);
        btnConsultarEquipo = findViewById(R.id.btnConsultarEquipo);
        btnEditarEquipo = findViewById(R.id.btnEditarEquipo);
        btnBorrarEquipo = findViewById(R.id.btnBorrarEquipo);

        presenter = new EquipoAdminPresenter(this);

        presenter.listarEquipos();
        presenter.obtenerSecciones();
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
            String seccion = cboEquipoSeccion.getSelectedItem().toString().trim();
            String area = cboEquipoArea.getSelectedItem().toString().trim();
            String descripcion = etDescripcionEquipo.getText().toString().trim();
            presenter.agregarEquipo(equipo, seccion, area, descripcion);
        });

        btnConsultarEquipo.setOnClickListener(v -> {
            String nombreEquipo = etEquipo.getText().toString().trim();
            presenter.consultarEquipo(nombreEquipo);
        });

        btnEditarEquipo.setOnClickListener(v -> {
            String nombre = etEquipo.getText().toString().trim();
            String seccion = cboEquipoSeccion.getSelectedItem().toString().trim();
            String area = cboEquipoArea.getSelectedItem().toString().trim();
            String descripcion = etDescripcionEquipo.getText().toString().trim();
            presenter.editarEquipo(nombre, seccion, area, descripcion);
        });

        btnBorrarEquipo.setOnClickListener(v -> {
            String nombre = etEquipo.getText().toString().trim();
            presenter.borrarEquipo(nombre);
            clearEditTextFields();
        });

    }



    public void showEquipos(List<Equipo> equipos) {
        // Crear un adaptador personalizado para mostrar las máquinas en la ListView
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
        cboEquipoSeccion.setSelection(0);
        cboEquipoArea.setSelection(0);
        etDescripcionEquipo.setText("");
    }

    @Override
    public void showDetallesEquipoSeleccionado(String nombreEquipo, String seccionEquipo, String areaEquipo, String descripcionEquipo) {
        // Mostrar la información en los campos correspondientes
        etEquipo.setText(nombreEquipo);
        // Obtener el índice del equipo seleccionado en el Spinner
        int index = obtenerIndiceSeccion(seccionEquipo);
        int indexa = obtenerIndiceArea(areaEquipo);
        cboEquipoSeccion.setSelection(index);
        cboEquipoArea.setSelection(indexa);
        etDescripcionEquipo.setText(descripcionEquipo);
    }
    private int obtenerIndiceSeccion(String seccion) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cboEquipoSeccion.getAdapter();
        return adapter.getPosition(seccion);
    }
    private int obtenerIndiceArea(String area) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cboEquipoArea.getAdapter();
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
        // Obtener el índice de la máquina seleccionada en el Spinner
        int index = obtenerIndiceSeccion(equipo.getSeccion());
        int indexa = obtenerIndiceArea(equipo.getArea());
        etEquipo.setText(String.valueOf(equipo.getNombre()));
        cboEquipoSeccion.setSelection(index);
        cboEquipoArea.setSelection(indexa);
        etDescripcionEquipo.setText(String.valueOf(equipo.getDescripcion()));
    }

    @Override
    public void mostrarSecciones(List<String> secciones) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, secciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboEquipoSeccion.setAdapter(adapter);
    }
    @Override
    public void mostrarAreas(List<String> areas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboEquipoArea.setAdapter(adapter);
    }
}