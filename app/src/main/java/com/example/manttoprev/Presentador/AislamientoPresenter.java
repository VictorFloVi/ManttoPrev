package com.example.manttoprev.Presentador;

import com.example.manttoprev.Vista.Aislamiento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AislamientoPresenter implements AislamientoContract.Presenter{
    private static final String DESCRIPCION = "descripcion";
    private static final String SELECCIONAR = "Seleccionar";
    private Aislamiento view;
    private DatabaseReference mDatabase;
    public AislamientoPresenter(Aislamiento view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void obtenerAreas() {
        List<String> nombresAreas = new ArrayList<>();
        nombresAreas.add(SELECCIONAR);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("areas");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nombresAreas.clear(); // Limpiar la lista antes de agregar las nuevas áreas
                nombresAreas.add(SELECCIONAR); // Agregar la opción "Seleccionar" nuevamente
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreArea = snapshot.child(DESCRIPCION).getValue(String.class);
                    if (nombreArea != null) {
                        nombresAreas.add(nombreArea);
                    }
                }
                view.mostrarAreas(nombresAreas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error si es necesario
            }
        });
    }


    @Override
    public void obtenerEquipos(final String areaSeleccionada) {
        final List<String> nombresEquipos = new ArrayList<>();
        nombresEquipos.add(SELECCIONAR);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("equipos");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nombresEquipos.clear(); // Limpiar la lista antes de agregar los nuevos equipos
                nombresEquipos.add(SELECCIONAR); // Agregar la opción "Seleccionar" nuevamente

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreEquipo = snapshot.child(DESCRIPCION).getValue(String.class);
                    String areaEquipo = snapshot.child("area").getValue(String.class);

                    if (nombreEquipo != null && areaEquipo != null && areaEquipo.equals(areaSeleccionada)) {
                        nombresEquipos.add(nombreEquipo);
                    }
                }
                view.mostrarEquipos(nombresEquipos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error si es necesario
            }
        });
    }

}
