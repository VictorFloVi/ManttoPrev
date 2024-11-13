package com.example.manttoprev.Presentador;

import androidx.annotation.NonNull;

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
    private static final String AREA = "Área";
    private static final String SECCION = "Sección";
    private final Aislamiento view;
    private DatabaseReference mDatabase;
    public AislamientoPresenter(Aislamiento view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void obtenerAreas() {
        List<String> nombresAreas = new ArrayList<>();
        nombresAreas.add(AREA);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("areas");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombresAreas.clear(); // Limpiar la lista antes de agregar las nuevas áreas
                nombresAreas.add(AREA); // Agregar la opción "Seleccionar" nuevamente
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreArea = snapshot.child(DESCRIPCION).getValue(String.class);
                    if (nombreArea != null) {
                        nombresAreas.add(nombreArea);
                    }
                }
                view.mostrarAreas(nombresAreas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error si es necesario
            }
        });
    }


    @Override
    public void obtenerSecciones(final String areaSeleccionada) {
        final List<String> nombresSecciones = new ArrayList<>();
        nombresSecciones.add(SECCION);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("secciones");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombresSecciones.clear(); // Limpiar la lista antes de agregar los nuevos equipos
                nombresSecciones.add(SECCION); // Agregar la opción "Seleccionar" nuevamente

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreSeccion = snapshot.child(DESCRIPCION).getValue(String.class);
                    String areaSeccion = snapshot.child("area").getValue(String.class);

                    if (nombreSeccion != null && areaSeccion != null && areaSeccion.equals(areaSeleccionada)) {
                        nombresSecciones.add(nombreSeccion);
                    }
                }
                view.mostrarSecciones(nombresSecciones);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error si es necesario
            }
        });
    }

}
