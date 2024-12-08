package com.example.manttoprev.Presentador;

import androidx.annotation.NonNull;
import com.example.manttoprev.R;
import com.example.manttoprev.Vista.GraficosAdmin;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class GraficosAdminPresenter implements GraficosAdminContract.Presenter{
    private final GraficosAdmin view;
    private DatabaseReference mDatabase;

    public GraficosAdminPresenter(GraficosAdmin view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void listarMotores() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("aislamiento");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> motores = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String motor = snapshot.child("motor").getValue(String.class);
                    String maquina = snapshot.child("maquina").getValue(String.class);
                    String equipo = snapshot.child("equipo").getValue(String.class);

                    if (motor != null && maquina != null && equipo != null) {
                        String motorMaquinaEquipo = motor + " / " + maquina + " / " + equipo;
                        if (!motores.contains(motorMaquinaEquipo)) {
                            motores.add(motorMaquinaEquipo);
                        }
                    }
                }

                // Notificar a la vista con los motores obtenidos
                view.showMotores(motores);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejo de errores en la consulta
            }
        });
    }

    public void showGraficoMotorSeleccionado(String selectedMotor) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("aislamiento");

        String[] parts = selectedMotor.split(" / ");
        String motorSeleccionado = parts.length > 0 ? parts[0] : "";
        String maquinaSeleccionada = parts.length > 1 ? parts[1] : "";
        String equipoSeleccionado = parts.length > 2 ? parts[2] : "";

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Entry> entries = new ArrayList<>();
                int index = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (coincideMotor(snapshot, motorSeleccionado, maquinaSeleccionada, equipoSeleccionado)) {
                        agregarEntradas(snapshot, entries, index);
                        index += 3; // Incrementar el índice después de agregar las entradas
                    }
                }
                crearMostrarGrafico(entries, motorSeleccionado);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                view.showErrorMessage("No se encontraron datos del motor seleccionado: " + databaseError.getMessage());
            }
        });
    }

    private boolean coincideMotor(DataSnapshot snapshot, String motorSeleccionado, String maquinaSeleccionada, String equipoSeleccionado) {
        String motor = snapshot.child("motor").getValue(String.class);
        String maquina = snapshot.child("maquina").getValue(String.class);
        String equipo = snapshot.child("equipo").getValue(String.class);

        return motor != null && maquina != null && equipo != null &&
                motor.equals(motorSeleccionado) &&
                maquina.equals(maquinaSeleccionada) &&
                equipo.equals(equipoSeleccionado);
    }

    private void agregarEntradas(DataSnapshot snapshot, List<Entry> entries, float i) {
        double megadou = getDoubleValue(snapshot, "megadou");
        double megadov = getDoubleValue(snapshot, "megadov");
        double megadow = getDoubleValue(snapshot, "megadow");

        entries.add(new Entry(i, (float) megadou));
        entries.add(new Entry(i + 1, (float) megadov));
        entries.add(new Entry(i + 2, (float) megadow));

    }

    private double getDoubleValue(DataSnapshot snapshot, String key) {
        Double value = snapshot.child(key).getValue(Double.class);
        return value != null ? value : 0.0;
    }

    private void crearMostrarGrafico(List<Entry> entries, String motorSeleccionado) {
        LineDataSet lineDataSet = new LineDataSet(entries, "Valores Megado - " + motorSeleccionado);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setColor(view.getColor(R.color.purple_200));
        lineDataSet.setDrawCircles(true);
        lineDataSet.setCircleColor(view.getColor(R.color.rojo));
        lineDataSet.setLineWidth(2f);
        view.showDatosGrafico(lineDataSet);
    }
}


