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
import com.example.manttoprev.Modelo.Maquina;
import com.example.manttoprev.Presentador.MaquinaAdminContract;
import com.example.manttoprev.Presentador.MaquinaAdminPresenter;
import com.example.manttoprev.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaquinaAdmin extends AppCompatActivity implements MaquinaAdminContract.View {

    private static final String NOMBRE = "nombre";
    private ListView lvListadoMaquina;
    private AutoCompleteTextView etMaquina;
    Spinner cboMaquinaEquipo;
    Spinner cboMaquinaArea;
    EditText etDescripcionMaquina;
    Button btnAgregarMaquina;
    Button btnConsultarMaquina;
    Button btnEditarMaquina;
    Button btnBorrarMaquina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maquina_admin);

        MaquinaAdminContract.Presenter presenter;
        lvListadoMaquina = findViewById(R.id.lvListadoMaquina);
        etMaquina = findViewById(R.id.etMaquina);
        cboMaquinaEquipo = findViewById(R.id.cboMaquinaEquipo);
        cboMaquinaArea = findViewById(R.id.cboMaquinaArea);
        etDescripcionMaquina = findViewById(R.id.etDescripcionMaquina);
        btnAgregarMaquina = findViewById(R.id.btnAgregarMaquina);
        btnConsultarMaquina = findViewById(R.id.btnConsultarMaquina);
        btnEditarMaquina = findViewById(R.id.btnEditarMaquina);
        btnBorrarMaquina = findViewById(R.id.btnBorrarMaquina);

        presenter = new MaquinaAdminPresenter(this);

        presenter.listarMaquinas();
        presenter.obtenerEquipos();
        presenter.obtenerAreas();

        //Detecta cuando se selecciona un elemento de la lista
        lvListadoMaquina.setOnItemClickListener((parent, view, position, id) -> {
            Map<String, Object> maquina = (Map<String, Object>) parent.getItemAtPosition(position);
            String nombreMaquina = (String) maquina.get(NOMBRE);

            // Llama al método del presentador para obtener detalles de la categoría
            presenter.clicItemListaMaquina(nombreMaquina);
        });


        //Detecta los cambios en el texto del AutoCompleteTextView
        etMaquina.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textoBusqueda = s.toString().trim();
                presenter.autocompletarMaquina(textoBusqueda);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnAgregarMaquina.setOnClickListener(v -> {
            String maquina = etMaquina.getText().toString().trim();
            String equipo = cboMaquinaEquipo.getSelectedItem().toString().trim();
            String area = cboMaquinaArea.getSelectedItem().toString().trim();
            String descripcion = etDescripcionMaquina.getText().toString().trim();
            presenter.agregarMaquina(maquina, equipo, area, descripcion);
        });

        btnConsultarMaquina.setOnClickListener(v -> {
            String nombreEquipo = etMaquina.getText().toString().trim();
            presenter.consultarMaquina(nombreEquipo);
        });

        btnEditarMaquina.setOnClickListener(v -> {
            String nombre = etMaquina.getText().toString().trim();
            String equipo = cboMaquinaEquipo.getSelectedItem().toString().trim();
            String area = cboMaquinaArea.getSelectedItem().toString().trim();
            String descripcion = etDescripcionMaquina.getText().toString().trim();
            presenter.editarMaquina(nombre, equipo, area, descripcion);
        });

        btnBorrarMaquina.setOnClickListener(v -> {
            String nombre = etMaquina.getText().toString().trim();
            presenter.borrarMaquina(nombre);
            clearEditTextFields();
        });

    }



    public void showMaquinas(List<Maquina> maquinas) {
        // Crear un adaptador personalizado para mostrar las máquinas en la ListView
        List<Map<String, Object>> maquinasMapList = new ArrayList<>();
        for (Maquina maquina : maquinas) {
            Map<String, Object> maquinaMap = new HashMap<>();
            maquinaMap.put(NOMBRE, maquina.getNombre());
            maquinasMapList.add(maquinaMap);
        }
        String[] from = {NOMBRE};
        int[] to = {R.id.tvNombreMaquina};
        SimpleAdapter adapter = new SimpleAdapter(this, maquinasMapList,
                R.layout.lista_maquina_item, from, to);
        lvListadoMaquina.setAdapter(adapter);
    }

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        clearEditTextFields();
    }
    public void clearEditTextFields() {
        etMaquina.setText("");
        cboMaquinaEquipo.setSelection(0);
        cboMaquinaArea.setSelection(0);
        etDescripcionMaquina.setText("");
    }

    @Override
    public void showDetallesMaquinaSeleccionado(String nombreMaquina, String equipoMaquina, String areaMaquina, String descripcionMaquina) {
        // Mostrar la información en los campos correspondientes
        etMaquina.setText(nombreMaquina);
        // Obtener el índice del equipo seleccionado en el Spinner
        int index = obtenerIndiceEquipo(equipoMaquina);
        int indexa = obtenerIndiceArea(areaMaquina);
        cboMaquinaEquipo.setSelection(index);
        cboMaquinaArea.setSelection(indexa);
        etDescripcionMaquina.setText(descripcionMaquina);
    }
    private int obtenerIndiceEquipo(String equipo) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cboMaquinaEquipo.getAdapter();
        return adapter.getPosition(equipo);
    }
    private int obtenerIndiceArea(String area) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cboMaquinaArea.getAdapter();
        return adapter.getPosition(area);
    }

    @Override
    public void showMaquinasEncontradosAutocompletado(List<String> maquinas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, maquinas);
        etMaquina.setAdapter(adapter);
    }

    @Override
    public void showConsultarMaquina(Maquina maquina) {
        // Obtener el índice de la máquina seleccionada en el Spinner
        int index = obtenerIndiceEquipo(maquina.getEquipo());
        int indexa = obtenerIndiceArea(maquina.getArea());
        etMaquina.setText(String.valueOf(maquina.getNombre()));
        cboMaquinaEquipo.setSelection(index);
        cboMaquinaArea.setSelection(indexa);
        etDescripcionMaquina.setText(String.valueOf(maquina.getDescripcion()));
    }

    @Override
    public void mostrarEquipos(List<String> equipos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboMaquinaEquipo.setAdapter(adapter);
    }
    @Override
    public void mostrarAreas(List<String> areas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboMaquinaArea.setAdapter(adapter);
    }
}