package com.example.manttoprev.Presentador;

import com.example.manttoprev.Modelo.Equipo;
import com.example.manttoprev.Modelo.Maquina;
import com.example.manttoprev.Vista.MaquinaAdmin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MaquinaAdminPresenter implements MaquinaAdminContract.Presenter{

    private static final String MAQUINAS = "maquinas";
    private static final String EQUIPO = "equipo";
    private static final String AREA = "area";
    private static final String NOMBRE = "nombre";
    private static final String SELECCIONAR = "Seleccionar";
    private static final String DESCRIPCION = "descripcion";
    private MaquinaAdmin view;
    private DatabaseReference mDatabase;

    public MaquinaAdminPresenter(MaquinaAdmin view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void listarMaquinas() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(MAQUINAS);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Maquina> maquinas = new ArrayList<>();

                for (DataSnapshot maquinaSnapshot : dataSnapshot.getChildren()) {
                    String nombre = maquinaSnapshot.child(DESCRIPCION).getValue(String.class);
                    Maquina maquina = new Maquina(nombre);
                    maquinas.add(maquina);
                }
                // Llama al método de la Vista para mostrar los proveedores
                view.showMaquinas(maquinas);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Llama al método de la Vista para mostrar mensajes de error
                view.showErrorMessage("Error al cargar las máquinas: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void obtenerEquipos() {
        List<String> nombresEquipos = new ArrayList<>();
        nombresEquipos.add(SELECCIONAR);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("equipos");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nombresEquipos.clear(); // Limpiar la lista antes de agregar las nuevas áreas
                nombresEquipos.add(SELECCIONAR); // Agregar la opción "Seleccionar" nuevamente
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreEquipo = snapshot.child(DESCRIPCION).getValue(String.class);
                    if (nombreEquipo != null) {
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
    public void clicItemListaMaquina(String nombreMaquina) {
        // Obtener la descripción desde la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(MAQUINAS);
        Query query = mDatabase.orderByChild(DESCRIPCION).equalTo(nombreMaquina);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreMaquina = snapshot.child(NOMBRE).getValue(String.class);
                    String equipoMaquina = snapshot.child(EQUIPO).getValue(String.class);
                    String areaMaquina = snapshot.child(AREA).getValue(String.class);
                    String descripcionEquipo = snapshot.child(DESCRIPCION).getValue(String.class);
                    // Notificar a la vista con los detalles
                    view.showDetallesMaquinaSeleccionado(nombreMaquina, equipoMaquina, areaMaquina, descripcionEquipo);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    public void autocompletarMaquina(String textoBusqueda) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(MAQUINAS);

        Query query = mDatabase.orderByChild(NOMBRE).startAt(textoBusqueda).endAt(textoBusqueda + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> maquinasEncontrados = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    maquinasEncontrados.add(nombre);
                }

                // Notifica a la vista con los equipos encontrados
                view.showMaquinasEncontradosAutocompletado(maquinasEncontrados);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }


    @Override
    public void agregarMaquina(String nombre, String equipo, String area, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || equipo.isEmpty() || area.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(MAQUINAS);

            // Realizar una consulta para verificar si ya existe un equipo con el mismo nombre
            Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Ya existe un equipo con el mismo nombre, muestra un mensaje de error
                        view.showErrorMessage("Ya existe una máquina con ese nombre");
                    } else {
                        // No existe una máquina con el mismo nombre, procede a agregarla

                        // Crear un objeto para la máquina
                        Maquina maquina = new Maquina(nombre, equipo,area,descripcion);

                        // Agrega máquina con la URL de la imagen a la base de datos
                        mDatabase.push().setValue(maquina);

                        // Notifica a la vista de éxito
                        view.showSuccessMessage("Máquina agregada con éxito.");

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
    public void consultarMaquina(String nombreMaquina) {
        // Validar que el nombre de la máquina no sea nulo o esté vacío
        if (nombreMaquina == null || nombreMaquina.trim().isEmpty()) {
            view.showErrorMessage("Ingrese un nombre de máquina");
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child(MAQUINAS);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombreMaquina);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    String equipo = snapshot.child(EQUIPO).getValue(String.class);
                    String area = snapshot.child(AREA).getValue(String.class);
                    String descripcion = snapshot.child(DESCRIPCION).getValue(String.class);

                    // Crear un objeto Equipo con la información obtenida
                    Maquina maquina = new Maquina(nombre, equipo, area, descripcion);

                    // Notificar a la vista con el equipo obtenido
                    view.showConsultarMaquina(maquina);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    @Override
    public void editarMaquina(String nombre, String equipo, String area, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || equipo.isEmpty() || area.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
            return;
        }

        // Obtener la referencia a la máquina en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(MAQUINAS);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // Actualiza la máquina
                    Maquina maquinaActualizado = new Maquina(nombre, equipo, area, descripcion);
                    snapshot.getRef().setValue(maquinaActualizado);

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Equipo actualizada con éxito.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
                view.showErrorMessage("Error al editar la máquina: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void borrarMaquina(String nombre) {
        // Validar el nombre del equipo

        if (nombre.isEmpty()) {
            view.showErrorMessage("Nombre de máquina inválido");
            return;
        }

        // Obtener la referencia a la máquina en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(MAQUINAS);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String maquinaId = snapshot.getKey();

                    // Borrar la máquina de la base de datos
                    assert maquinaId != null;
                    mDatabase.child(maquinaId).removeValue();

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Máquina eliminada con éxito.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
                view.showErrorMessage("Error al borrar la máquina: " + databaseError.getMessage());
            }
        });
    }


}
