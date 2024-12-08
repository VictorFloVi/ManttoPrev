package com.example.manttoprev.Vista;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.manttoprev.Presentador.GraficosAdminContract;
import com.example.manttoprev.Presentador.GraficosAdminPresenter;
import com.example.manttoprev.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraficosAdmin extends AppCompatActivity implements GraficosAdminContract.View {

    private ListView lvMotores;
    private LineChart chMegado;
    private final ArrayList<String> motores = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private String seleccionarMotor = "";
    ImageButton btnCerrarGrafico;
    SearchView svMotores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos_admin);

        GraficosAdminContract.Presenter presenter;

        lvMotores = findViewById(R.id.lvMotores);
        svMotores = findViewById(R.id.svMotores);
        chMegado = findViewById(R.id.chMegado);
        btnCerrarGrafico = findViewById(R.id.btnCerrarGrafico);

        chMegado.setVisibility(View.GONE);

        btnCerrarGrafico.setVisibility(View.GONE);
        btnCerrarGrafico.setOnClickListener(v -> cerrarGrafico());

        presenter = new GraficosAdminPresenter(this);

        // Configurar el adaptador para ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, motores);
        lvMotores.setAdapter(adapter);

        // Configurar el SearchView para filtrar motores
        svMotores.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        // Manejar clics en los elementos del ListView
        lvMotores.setOnItemClickListener((parent, view, position, id) -> {
            seleccionarMotor = adapter.getItem(position);  // Obtener el motor seleccionado
            presenter.showGraficoMotorSeleccionado(seleccionarMotor);  // Mostrar el gráfico para el motor seleccionado
            showGrafico();
        });
        presenter.listarMotores();
    }

    public void showMotores(List<String> motores) {
        this.motores.clear();
        this.motores.addAll(motores);
        adapter.notifyDataSetChanged();  // Notificar cambios al adaptador
    }

    public void showGrafico(){
        lvMotores.setVisibility(View.GONE); // Ocultar ListView
        chMegado.setVisibility(View.VISIBLE); // Mostrar el gráfico
        btnCerrarGrafico.setVisibility(View.VISIBLE); // Mostrar el botón "Cerrar"
    }

    @Override
    public void showDatosGrafico(LineDataSet lineDataSet) {
        LineData lineData = new LineData(lineDataSet);
        chMegado.setData(lineData);
        chMegado.setPinchZoom(true);  // Habilitar el zoom usando los dos dedos
        chMegado.setScaleEnabled(true);
        chMegado.getXAxis().setValueFormatter(new IndexAxisValueFormatter(Arrays.asList("U", "V", "W")));
        chMegado.getAxisLeft().setAxisMinimum(0f);
        chMegado.getAxisRight().setEnabled(false);
        chMegado.animateX(1000);  // Animación para la carga del gráfico
        chMegado.invalidate();  // Refrescar el gráfico
    }

    private void cerrarGrafico() {
        chMegado.setVisibility(View.GONE); // Ocultar el gráfico
        btnCerrarGrafico.setVisibility(View.GONE); // Ocultar el botón "Cerrar"
        lvMotores.setVisibility(View.VISIBLE); // Mostrar el ListView
        svMotores.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}