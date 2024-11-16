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
    private static final String EQUIPO = "Equipo";
    private static final String MAQUINA = "Maquina";
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

    @Override
    public void obtenerEquipos( String equipoSeleccionado) {
        final List<String> nombresEquipos = new ArrayList<>();
        nombresEquipos.add(EQUIPO);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("equipos");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombresEquipos.clear(); // Limpiar la lista antes de agregar los nuevos equipos
                nombresEquipos.add(EQUIPO); // Agregar la opción "Seleccionar" nuevamente

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreEquipo = snapshot.child(DESCRIPCION).getValue(String.class);
                    String seccionEquipo = snapshot.child("seccion").getValue(String.class);

                    if (nombreEquipo != null && seccionEquipo != null && seccionEquipo.equals(equipoSeleccionado)) {
                        nombresEquipos.add(nombreEquipo);
                    }
                }
                view.mostrarEquipos(nombresEquipos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error si es necesario
            }
        });
    }

    @Override
    public void obtenerMaquinas(String maquinaSeleccionada) {
        final List<String> nombresMaquinas = new ArrayList<>();
        nombresMaquinas.add(EQUIPO);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("maquinas");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombresMaquinas.clear(); // Limpiar la lista antes de agregar los nuevos equipos
                nombresMaquinas.add(MAQUINA); // Agregar la opción "Seleccionar" nuevamente

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreMaquina = snapshot.child(DESCRIPCION).getValue(String.class);
                    String equipoMaquina = snapshot.child("equipo").getValue(String.class);

                    if (nombreMaquina != null && equipoMaquina != null && equipoMaquina.equals(maquinaSeleccionada)) {
                        nombresMaquinas.add(nombreMaquina);
                    }
                }
                view.mostrarMaquinas(nombresMaquinas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error si es necesario
            }
        });
    }
}
