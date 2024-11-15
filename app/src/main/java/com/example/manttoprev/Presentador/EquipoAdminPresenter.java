package com.example.manttoprev.Presentador;


import androidx.annotation.NonNull;

import com.example.manttoprev.Modelo.Equipo;
import com.example.manttoprev.Vista.EquipoAdmin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EquipoAdminPresenter implements EquipoAdminContract.Presenter{

    private static final String EQUIPOS = "equipos";
    private static final String SECCION = "seccion";
    private static final String AREA = "area";
    private static final String NOMBRE = "nombre";
    private static final String SELECCIONAR = "Seleccionar";
    private static final String DESCRIPCION = "descripcion";
    private final EquipoAdmin view;
    private DatabaseReference mDatabase;

    public EquipoAdminPresenter(EquipoAdmin view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void listarEquipos() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(EQUIPOS);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Equipo> equipos = new ArrayList<>();

                for (DataSnapshot equipoSnapshot : dataSnapshot.getChildren()) {
                    String nombre = equipoSnapshot.child(DESCRIPCION).getValue(String.class);
                    Equipo equipo = new Equipo(nombre);
                    equipos.add(equipo);
                }
                // Llama al método de la Vista para mostrar los proveedores
                view.showEquipos(equipos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Llama al método de la Vista para mostrar mensajes de error
                view.showErrorMessage("Error al cargar las máquinas: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void obtenerSecciones() {
        List<String> nombresSecciones = new ArrayList<>();
        nombresSecciones.add(SELECCIONAR);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("secciones");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombresSecciones.clear(); // Limpiar la lista antes de agregar las nuevas áreas
                nombresSecciones.add(SELECCIONAR); // Agregar la opción "Seleccionar" nuevamente
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreSeccion = snapshot.child(DESCRIPCION).getValue(String.class);
                    if (nombreSeccion != null) {
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
    public void obtenerAreas() {
        List<String> nombresAreas = new ArrayList<>();
        nombresAreas.add(SELECCIONAR);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("areas");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error si es necesario
            }
        });
    }

    @Override
    public void clicItemListaEquipo(String nombreEquipo) {
        // Obtener la descripción desde la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(EQUIPOS);
        Query query = mDatabase.orderByChild(DESCRIPCION).equalTo(nombreEquipo);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreEquipo = snapshot.child(NOMBRE).getValue(String.class);
                    String seccionEquipo = snapshot.child(SECCION).getValue(String.class);
                    String areaEquipo = snapshot.child(AREA).getValue(String.class);
                    String descripcionEquipo = snapshot.child(DESCRIPCION).getValue(String.class);
                    // Notificar a la vista con los detalles
                    view.showDetallesEquipoSeleccionado(nombreEquipo, seccionEquipo, areaEquipo, descripcionEquipo);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    public void autocompletarEquipo(String textoBusqueda) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(EQUIPOS);

        Query query = mDatabase.orderByChild(NOMBRE).startAt(textoBusqueda).endAt(textoBusqueda + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String>equiposEncontrados = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    equiposEncontrados.add(nombre);
                }

                // Notifica a la vista con los equipos encontrados
                view.showEquiposEncontradosAutocompletado(equiposEncontrados);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }


    @Override
    public void agregarEquipo(String nombre, String seccion, String area, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || seccion.isEmpty() || area.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(EQUIPOS);

            // Realizar una consulta para verificar si ya existe un equipo con el mismo nombre
            Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Ya existe un equipo con el mismo nombre, muestra un mensaje de error
                        view.showErrorMessage("Ya existe una máquina con ese nombre");
                    } else {
                        // No existe una máquina con el mismo nombre, procede a agregarla

                        // Crear un objeto para la máquina
                        Equipo equipo = new Equipo(nombre,seccion,area,descripcion);

                        // Agrega máquina con la URL de la imagen a la base de datos
                        mDatabase.push().setValue(equipo);

                        // Notifica a la vista de éxito
                        view.showSuccessMessage("Máquina agregada con éxito.");

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar el error de Firebase aquí
                }
            });
        }
    }

    @Override
    public void consultarEquipo(String nombreEquipo) {
        // Validar que el nombre de la máquina no sea nulo o esté vacío
        if (nombreEquipo == null || nombreEquipo.trim().isEmpty()) {
            view.showErrorMessage("Ingrese un nombre de máquina");
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child(EQUIPOS);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombreEquipo);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    String seccion = snapshot.child(SECCION).getValue(String.class);
                    String area = snapshot.child(AREA).getValue(String.class);
                    String descripcion = snapshot.child(DESCRIPCION).getValue(String.class);

                    // Crear un objeto Seccion con la información obtenida
                    Equipo maquina = new Equipo(nombre, seccion, area, descripcion);

                    // Notificar a la vista con el equipo obtenido
                    view.showConsultarEquipo(maquina);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    @Override
    public void editarEquipo(String nombre, String seccion, String area, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || seccion.isEmpty() || area.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
            return;
        }

        // Obtener la referencia a la máquina en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(EQUIPOS);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // Actualiza la máquina
                    Equipo equipoActualizado = new Equipo(nombre, seccion, area, descripcion);
                    snapshot.getRef().setValue(equipoActualizado);

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Seccion actualizada con éxito.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
                view.showErrorMessage("Error al editar la máquina: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void borrarEquipo(String nombre) {
        // Validar el nombre del equipo

        if (nombre.isEmpty()) {
            view.showErrorMessage("Nombre de máquina inválido");
            return;
        }

        // Obtener la referencia a la máquina en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(EQUIPOS);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String equipoId = snapshot.getKey();

                    // Borrar la máquina de la base de datos
                    assert equipoId != null;
                    mDatabase.child(equipoId).removeValue();

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Máquina eliminada con éxito.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
                view.showErrorMessage("Error al borrar la máquina: " + databaseError.getMessage());
            }
        });
    }


}
