package com.example.manttoprev.Presentador;

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
    private static final String NOMBRE = "nombre";
    private static final String AREA = "area";
    private static final String DESCRIPCION = "descripcion";
    private static final String SELECCIONAR = "Seleccionar";
    private EquipoAdmin view;
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
            public void onDataChange(DataSnapshot dataSnapshot) {
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
            public void onCancelled(DatabaseError databaseError) {
                // Llama al método de la Vista para mostrar mensajes de error
                view.showErrorMessage("Error al cargar las áreas: " + databaseError.getMessage());
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
    public void clicItemListaEquipo(String nombreEquipo) {
        // Obtener la descripción desde la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(EQUIPOS);
        Query query = mDatabase.orderByChild(DESCRIPCION).equalTo(nombreEquipo);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreEquipo = snapshot.child(NOMBRE).getValue(String.class);
                    String areaEquipo = snapshot.child(AREA).getValue(String.class);
                    String descripcionEquipo = snapshot.child(DESCRIPCION).getValue(String.class);
                    // Notificar a la vista con los detalles
                    view.showDetallesEquipoSeleccionado(nombreEquipo, areaEquipo, descripcionEquipo);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    public void autocompletarEquipo(String textoBusqueda) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(EQUIPOS);

        Query query = mDatabase.orderByChild(NOMBRE).startAt(textoBusqueda).endAt(textoBusqueda + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> equiposEncontrados = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    equiposEncontrados.add(nombre);
                }

                // Notifica a la vista con los equipos encontrados
                view.showEquiposEncontradosAutocompletado(equiposEncontrados);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }


    @Override
    public void agregarEquipo(String nombre, String area, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || area.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(EQUIPOS);

            // Realizar una consulta para verificar si ya existe un equipo con el mismo nombre
            Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Ya existe un equipo con el mismo nombre, muestra un mensaje de error
                        view.showErrorMessage("Ya existe una area con ese nombre");
                    } else {
                        // No existe un equipo con el mismo nombre, procede a agregarla

                        // Crear un objeto para el equipo
                        Equipo equipo = new Equipo(nombre,area,descripcion);

                        // Agrega equipo con la URL de la imagen a la base de datos
                        mDatabase.push().setValue(equipo);

                        // Notifica a la vista de éxito
                        view.showSuccessMessage("Equipo agregado con éxito.");

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Manejar el error de Firebase aquí
                }
            });
        }
    }



    @Override
    public void consultarEquipo(String nombreEquipo) {
        // Validar que el nombre del equipo no sea nulo o esté vacío
        if (nombreEquipo == null || nombreEquipo.trim().isEmpty()) {
            view.showErrorMessage("Ingrese un nombre de equipo");
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child(EQUIPOS);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombreEquipo);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    String area = snapshot.child(AREA).getValue(String.class);
                    String descripcion = snapshot.child(DESCRIPCION).getValue(String.class);

                    // Crear un objeto Equipo con la información obtenida
                    Equipo equipo = new Equipo(nombre, area, descripcion);

                    // Notificar a la vista con el equipo obtenido
                    view.showConsultarEquipo(equipo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    @Override
    public void editarEquipo(String nombre, String area, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || area.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
            return;
        }

        // Obtener la referencia al equipo en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(EQUIPOS);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // Actualiza el equipo
                    Equipo equipoActualizado = new Equipo(nombre, area, descripcion);
                    snapshot.getRef().setValue(equipoActualizado);

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Equipo actualizada con éxito.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
                view.showErrorMessage("Error al editar el equipo: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void borrarEquipo(String nombre) {
        // Validar el nombre del equipo

        if (nombre.isEmpty()) {
            view.showErrorMessage("Nombre de equipo inválido");
            return;
        }

        // Obtener la referencia al equipo en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(EQUIPOS);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String equipoId = snapshot.getKey();

                    // Borrar el equipo de la base de datos
                    assert equipoId != null;
                    mDatabase.child(equipoId).removeValue();

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Equipo eliminado con éxito.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
                view.showErrorMessage("Error al borrar el área: " + databaseError.getMessage());
            }
        });
    }

}
