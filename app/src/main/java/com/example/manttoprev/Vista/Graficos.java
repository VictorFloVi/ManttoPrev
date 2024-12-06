package com.example.manttoprev.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.manttoprev.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Graficos extends AppCompatActivity {

    private ListView lvMotores;
    private LineChart lineChart;


    private final ArrayList<String> motores = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private String selectedMotor = "";  // Motor seleccionado
    ImageButton btnCloseChart;
    Spinner spMaquinas;
    Spinner spEquipos;
    SearchView svMotores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos);

        lvMotores = findViewById(R.id.lvMotores);
        svMotores = findViewById(R.id.svMotores);
        lineChart = findViewById(R.id.lineChart);
        btnCloseChart = findViewById(R.id.btnCloseChart);
        spMaquinas = findViewById(R.id.spMaquinas);
        spEquipos = findViewById(R.id.spEquipos);

        lineChart.setVisibility(View.GONE);

        // Configurar el botón de cerrar
        btnCloseChart.setOnClickListener(v -> closeChart());

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

        // Obtener motores desde Firebase
        loadMotoresFromFirebase();

        // Ejemplo: Abrir gráfico al hacer clic en un elemento del ListView
        lvMotores.setOnItemClickListener((parent, view, position, id) -> {
            showChartForMotor();
            // Aquí puedes cargar datos para el gráfico basado en el ítem seleccionado
        });


        // Manejar clics en los elementos del ListView
        lvMotores.setOnItemClickListener((parent, view, position, id) -> {
            selectedMotor = adapter.getItem(position);  // Obtener el motor seleccionado
            showChartForMotor();  // Mostrar el gráfico para el motor seleccionado
        });
    }

    private void loadMotoresFromFirebase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("aislamiento");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                motores.clear(); // Limpia la lista de motores antes de agregar nuevos datos.
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String motor = snapshot.child("motor").getValue(String.class);
                    String maquina = snapshot.child("maquina").getValue(String.class);
                    String equipo = snapshot.child("equipo").getValue(String.class);

                    // Concatenar motor, máquina y equipo
                    if (motor != null && maquina != null && equipo != null) {
                        String motorMaquinaEquipo = motor + " / " + maquina + " / " + equipo; // Formato: "Motor / Máquina / Equipo"
                        if (!motores.contains(motorMaquinaEquipo)) {
                            motores.add(motorMaquinaEquipo);  // Agregar si no está duplicado.
                        }
                    }
                }
                adapter.notifyDataSetChanged();  // Notificar cambios al adaptador.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejo de errores en la consulta
            }
        });
    }


    private void showChartForMotor() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("aislamiento");

        lvMotores.setVisibility(View.GONE); // Ocultar ListView
        svMotores.setVisibility(View.GONE);
        spEquipos.setVisibility(View.GONE);
        spMaquinas.setVisibility(View.GONE);
        lineChart.setVisibility(View.VISIBLE); // Mostrar el gráfico
        btnCloseChart.setVisibility(View.VISIBLE); // Mostrar el botón "Cerrar"
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Entry> entries = new ArrayList<>();
                int index = 0;

                // Extraer solo el nombre del motor desde el valor seleccionado
                String[] parts = selectedMotor.split(" / ");
                String motorSeleccionado = parts.length > 0 ? parts[0] : "";
                String maquinaSeleccionada = parts.length > 1 ? parts[1] : "";
                String equipoSeleccionado = parts.length > 2 ? parts[2] : "";



                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String motor = snapshot.child("motor").getValue(String.class);
                    String maquina = snapshot.child("maquina").getValue(String.class);
                    String equipo = snapshot.child("equipo").getValue(String.class);

                    if (motor != null &&  maquina != null && equipo!= null &&motor.equals(motorSeleccionado)
                            && maquina.equals(maquinaSeleccionada) && equipo.equals(equipoSeleccionado)) {
                        double megadou = snapshot.child("megadou").getValue(Double.class) != null
                                ? snapshot.child("megadou").getValue(Double.class)
                                : 0.0;
                        double megadov = snapshot.child("megadov").getValue(Double.class) != null
                                ? snapshot.child("megadov").getValue(Double.class)
                                : 0.0;
                        double megadow = snapshot.child("megadow").getValue(Double.class) != null
                                ? snapshot.child("megadow").getValue(Double.class)
                                : 0.0;

                        entries.add(new Entry(index, (float) megadou));
                        entries.add(new Entry(index + 1, (float) megadov));
                        entries.add(new Entry(index + 2, (float) megadow));

                        index += 3;                    }


                }

                LineDataSet lineDataSet = new LineDataSet(entries, "Valores Megado - " + motorSeleccionado);
                lineDataSet.setColor(getResources().getColor(R.color.purple_200));
                lineDataSet.setDrawCircles(true);
                lineDataSet.setCircleColor(getResources().getColor(R.color.rojo));
                lineDataSet.setLineWidth(2f);

                LineData lineData = new LineData(lineDataSet);
                lineChart.setData(lineData);

                lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(Arrays.asList("Megadou", "Megadov", "Megadow")));
                lineChart.getAxisLeft().setAxisMinimum(0f);
                lineChart.getAxisRight().setEnabled(false);

                lineChart.animateX(1000);
                lineChart.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejo de errores en la consulta
            }
        });
    }

    private void closeChart() {
        lineChart.setVisibility(View.GONE); // Ocultar el gráfico
        btnCloseChart.setVisibility(View.GONE); // Ocultar el botón "Cerrar"
        lvMotores.setVisibility(View.VISIBLE); // Mostrar el ListView
        svMotores.setVisibility(View.VISIBLE);
        spEquipos.setVisibility(View.VISIBLE);
        spMaquinas.setVisibility(View.VISIBLE);

    }




}