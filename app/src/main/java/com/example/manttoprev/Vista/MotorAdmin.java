package com.example.manttoprev.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.manttoprev.Modelo.Motor;
import com.example.manttoprev.Presentador.MotorAdminContract;
import com.example.manttoprev.Presentador.MotorAdminPresenter;
import com.example.manttoprev.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MotorAdmin extends AppCompatActivity implements MotorAdminContract.View {

    private static final String NOMBRE = "nombre";
    private ListView lvListadoMotor;
    private AutoCompleteTextView etMotor;
    Spinner cboMotorArea;
    Spinner cboMotorSeccion;
    Spinner cboMotorEquipo;
    Spinner cboMotorMaquina;
    EditText etDescripcionMotor;
    Button btnAgregarMotor;
    Button btnConsultarMotor;
    Button btnEditarMotor;
    Button btnBorrarMotor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor_admin);

        MotorAdminContract.Presenter presenter;

        lvListadoMotor = findViewById(R.id.lvListadoMotor);
        etMotor = findViewById(R.id.etMotor);
        cboMotorArea = findViewById(R.id.cboMotorArea);
        cboMotorSeccion = findViewById(R.id.cboMotorSeccion);
        cboMotorEquipo = findViewById(R.id.cboMotorEquipo);
        cboMotorMaquina = findViewById(R.id.cboMotorMaquina);
        etDescripcionMotor = findViewById(R.id.etDescripcionMotor);
        btnAgregarMotor = findViewById(R.id.btnAgregarMotor);
        btnConsultarMotor = findViewById(R.id.btnConsultarMotor);
        btnEditarMotor = findViewById(R.id.btnEditarMotor);
        btnBorrarMotor = findViewById(R.id.btnBorrarMotor);

        presenter = new MotorAdminPresenter(this);

        presenter.listarMotores();
        presenter.obtenerAreas();

        cboMotorArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        cboMotorSeccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seccionSeleccionada = parent.getItemAtPosition(position).toString();
                presenter.obtenerEquipos(seccionSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        cboMotorEquipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String maquinaSeleccionada = parent.getItemAtPosition(position).toString();
                presenter.obtenerMaquinas(maquinaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Detecta cuando se selecciona un elemento de la lista
        lvListadoMotor.setOnItemClickListener((parent, view, position, id) -> {
            Map<String, Object> motor = (Map<String, Object>) parent.getItemAtPosition(position);
            String nombreMotor= (String) motor.get(NOMBRE);

            // Llama al método del presentador para obtener detalles de la categoría
            presenter.clicItemListaMotor(nombreMotor);
        });

        //Detecta los cambios en el texto del AutoCompleteTextView
        etMotor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textoBusqueda = s.toString().trim();
                presenter.autocompletarMotor(textoBusqueda);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnAgregarMotor.setOnClickListener(v -> {
            String motor = etMotor.getText().toString().trim();
            String maquina = cboMotorMaquina.getSelectedItem().toString().trim();
            String equipo = cboMotorEquipo.getSelectedItem().toString().trim();
            String seccion = cboMotorSeccion.getSelectedItem().toString().trim();
            String area = cboMotorArea.getSelectedItem().toString().trim();
            String descripcion = etDescripcionMotor.getText().toString().trim();
            presenter.agregarMotor(motor, maquina, equipo, seccion, area, descripcion);
        });

        btnConsultarMotor.setOnClickListener(v -> {
            String nombreMotor = etMotor.getText().toString().trim();
            presenter.consultarMotor(nombreMotor);
        });

        btnEditarMotor.setOnClickListener(v -> {
            String nombre = etMotor.getText().toString().trim();
            String maquina = cboMotorMaquina.getSelectedItem().toString().trim();
            String equipo = cboMotorEquipo.getSelectedItem().toString().trim();
            String seccion = cboMotorSeccion.getSelectedItem().toString().trim();
            String area = cboMotorArea.getSelectedItem().toString().trim();
            String descripcion = etDescripcionMotor.getText().toString().trim();
            presenter.editarMotor(nombre, maquina, equipo, seccion, area, descripcion);
        });

        btnBorrarMotor.setOnClickListener(v -> {
            String nombre = etMotor.getText().toString().trim();
            presenter.borrarMotor(nombre);
            clearEditTextFields();
        });
    }


    public void showMotores(List<Motor> motores) {
        // Crear un adaptador personalizado para mostrar las máquinas en la ListView
        List<Map<String, Object>> motoresMapList = new ArrayList<>();
        for (Motor motor : motores) {
            Map<String, Object> motorMap = new HashMap<>();
            motorMap.put(NOMBRE, motor.getNombre());
            motoresMapList.add(motorMap);
        }
        String[] from = {NOMBRE};
        int[] to = {R.id.tvNombreMotor};
        SimpleAdapter adapter = new SimpleAdapter(this, motoresMapList,
                R.layout.lista_motor_item, from, to);
        lvListadoMotor.setAdapter(adapter);
    }


    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        clearEditTextFields();
    }

    public void clearEditTextFields() {
        etMotor.setText("");
        cboMotorArea.setSelection(0);
        cboMotorSeccion.setSelection(0);
        cboMotorEquipo.setSelection(0);
        cboMotorMaquina.setSelection(0);
        etDescripcionMotor.setText("");
    }

    @Override
    public void showDetallesMotorSeleccionado(String nombreMotor, String maquinaMotor, String equipoMotor, String seccionMotor, String areaMotor, String descripcionMotor) {
        // Mostrar la información en los campos correspondientes
        etMotor.setText(nombreMotor);
        // Obtener el índice del equipo seleccionado en el Spinner
        int index = obtenerIndiceMaquina(maquinaMotor);
        int indexe = obtenerIndiceEquipo(equipoMotor);
        int indexs = obtenerIndiceSeccion(seccionMotor);
        int indexa = obtenerIndiceArea(areaMotor);
        cboMotorMaquina.setSelection(index);
        cboMotorEquipo.setSelection(indexe);
        cboMotorSeccion.setSelection(indexs);
        cboMotorArea.setSelection(indexa);
        etDescripcionMotor.setText(descripcionMotor);
    }
    private int obtenerIndiceMaquina(String maquina) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cboMotorMaquina.getAdapter();
        return adapter.getPosition(maquina);
    }
    private int obtenerIndiceEquipo(String equipo) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cboMotorEquipo.getAdapter();
        return adapter.getPosition(equipo);
    }

    private int obtenerIndiceSeccion(String seccion) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cboMotorSeccion.getAdapter();
        return adapter.getPosition(seccion);
    }
    private int obtenerIndiceArea(String area) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) cboMotorArea.getAdapter();
        return adapter.getPosition(area);
    }
    @Override
    public void showConsultarMotor(Motor motor) {
        // Obtener el índice de la máquina seleccionada en el Spinner
        int index = obtenerIndiceMaquina(motor.getMaquina());
        int indexe = obtenerIndiceEquipo(motor.getEquipo());
        int indexs = obtenerIndiceSeccion(motor.getSeccion());
        int indexa = obtenerIndiceArea(motor.getArea());
        etMotor.setText(String.valueOf(motor.getNombre()));
        cboMotorMaquina.setSelection(index);
        cboMotorEquipo.setSelection(indexe);
        cboMotorSeccion.setSelection(indexs);
        cboMotorArea.setSelection(indexa);
        etDescripcionMotor.setText(String.valueOf(motor.getDescripcion()));
    }

    @Override
    public void showMotoresEncontradosAutocompletado(List<String> motores) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, motores);
        etMotor.setAdapter(adapter);
    }


    @Override
    public void mostrarMaquinas(List<String> maquinas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, maquinas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboMotorMaquina.setAdapter(adapter);
    }
    @Override
    public void mostrarEquipos(List<String> equipos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboMotorEquipo.setAdapter(adapter);
    }
    @Override
    public void mostrarSecciones(List<String> secciones) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, secciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboMotorSeccion.setAdapter(adapter);
    }
    @Override
    public void mostrarAreas(List<String> areas) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboMotorArea.setAdapter(adapter);
    }
}