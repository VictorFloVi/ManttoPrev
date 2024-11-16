package com.example.manttoprev.Presentador;


import androidx.annotation.NonNull;
import com.example.manttoprev.Modelo.Motor;
import com.example.manttoprev.Vista.MotorAdmin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MotorAdminPresenter implements MotorAdminContract.Presenter{

    private static final String MOTORES = "motores";
    private static final String MAQUINA = "maquina";
    private static final String EQUIPO = "equipo";
    private static final String SECCION = "seccion";
    private static final String AREA = "area";
    private static final String NOMBRE = "nombre";
    private static final String SELECCIONAR = "Seleccionar";
    private static final String DESCRIPCION = "descripcion";
    private final MotorAdmin view;
    private DatabaseReference mDatabase;

    public MotorAdminPresenter(MotorAdmin view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void listarMotores() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(MOTORES);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Motor> motores = new ArrayList<>();

                for (DataSnapshot motoresSnapshot : dataSnapshot.getChildren()) {
                    String nombre = motoresSnapshot.child(DESCRIPCION).getValue(String.class);
                    Motor maquina = new Motor(nombre);
                    motores.add(maquina);
                }
                // Llama al método de la Vista para mostrar los proveedores
                view.showMotores(motores);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Llama al método de la Vista para mostrar mensajes de error
                view.showErrorMessage("Error al cargar las máquinas: " + databaseError.getMessage());
            }
        });
    }


    @Override
    public void obtenerMaquinas( String maquinaSeleccionada) {
        final List<String> nombresMaquinas = new ArrayList<>();
        nombresMaquinas.add(MAQUINA);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("maquinas");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombresMaquinas.clear(); // Limpiar la lista antes de agregar los nuevos equipos
                nombresMaquinas.add(SELECCIONAR); // Agregar la opción "Seleccionar" nuevamente

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreMaquina = snapshot.child(DESCRIPCION).getValue(String.class);
                    String equipoMaquina = snapshot.child(EQUIPO).getValue(String.class);

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
    @Override
    public void obtenerEquipos( String equipoSeleccionado) {
        final List<String> nombresEquipos = new ArrayList<>();
        nombresEquipos.add(EQUIPO);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("equipos");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombresEquipos.clear(); // Limpiar la lista antes de agregar los nuevos equipos
                nombresEquipos.add(SELECCIONAR); // Agregar la opción "Seleccionar" nuevamente

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreEquipo = snapshot.child(DESCRIPCION).getValue(String.class);
                    String seccionEquipo = snapshot.child(SECCION).getValue(String.class);

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
    public void obtenerSecciones(final String areaSeleccionada) {
        final List<String> nombresSecciones = new ArrayList<>();
        nombresSecciones.add(SECCION);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("secciones");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombresSecciones.clear(); // Limpiar la lista antes de agregar los nuevos equipos
                nombresSecciones.add(SELECCIONAR); // Agregar la opción "Seleccionar" nuevamente

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreSeccion = snapshot.child(DESCRIPCION).getValue(String.class);
                    String areaSeccion = snapshot.child(AREA).getValue(String.class);

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
    public void clicItemListaMotor(String nombreMaquina) {
        // Obtener la descripción desde la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(MOTORES);
        Query query = mDatabase.orderByChild(DESCRIPCION).equalTo(nombreMaquina);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreMotor = snapshot.child(NOMBRE).getValue(String.class);
                    String maquinaMotor = snapshot.child(MAQUINA).getValue(String.class);
                    String equipoMotor = snapshot.child(EQUIPO).getValue(String.class);
                    String seccionMotor = snapshot.child(SECCION).getValue(String.class);
                    String areaMotor = snapshot.child(AREA).getValue(String.class);
                    String descripcionMotor = snapshot.child(DESCRIPCION).getValue(String.class);
                    // Notificar a la vista con los detalles
                    view.showDetallesMotorSeleccionado(nombreMotor, maquinaMotor, equipoMotor, seccionMotor, areaMotor, descripcionMotor);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    public void autocompletarMotor(String textoBusqueda) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(MOTORES);

        Query query = mDatabase.orderByChild(NOMBRE).startAt(textoBusqueda).endAt(textoBusqueda + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> motoresEncontrados = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    motoresEncontrados.add(nombre);
                }

                // Notifica a la vista con los equipos encontrados
                view.showMotoresEncontradosAutocompletado(motoresEncontrados);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    @Override
    public void agregarMotor(String nombre, String maquina, String equipo, String seccion, String area, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || maquina.isEmpty() || equipo.isEmpty() || seccion.isEmpty() || area.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(MOTORES);

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
                        Motor motor = new Motor(nombre,maquina,equipo,seccion,area,descripcion);

                        // Agrega máquina con la URL de la imagen a la base de datos
                        mDatabase.push().setValue(motor);

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
    public void consultarMotor(String nombreMotor) {
        // Validar que el nombre de la máquina no sea nulo o esté vacío
        if (nombreMotor == null || nombreMotor.trim().isEmpty()) {
            view.showErrorMessage("Ingrese un nombre de máquina");
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child(MOTORES);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombreMotor);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    String maquina = snapshot.child(MAQUINA).getValue(String.class);
                    String equipo = snapshot.child(EQUIPO).getValue(String.class);
                    String seccion = snapshot.child(SECCION).getValue(String.class);
                    String area = snapshot.child(AREA).getValue(String.class);
                    String descripcion = snapshot.child(DESCRIPCION).getValue(String.class);

                    // Crear un objeto Seccion con la información obtenida
                    Motor motor = new Motor(nombre, maquina, equipo, seccion, area, descripcion);

                    // Notificar a la vista con el equipo obtenido
                    view.showConsultarMotor(motor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    @Override
    public void editarMotor(String nombre, String maquina, String equipo, String seccion, String area, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || maquina.isEmpty() || equipo.isEmpty() || seccion.isEmpty() || area.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
            return;
        }

        // Obtener la referencia a la máquina en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(MOTORES);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // Actualiza la máquina
                    Motor motorActualizado = new Motor(nombre, maquina, equipo, seccion, area, descripcion);
                    snapshot.getRef().setValue(motorActualizado);

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
    public void borrarMotor(String nombre) {
        // Validar el nombre del equipo

        if (nombre.isEmpty()) {
            view.showErrorMessage("Nombre de máquina inválido");
            return;
        }

        // Obtener la referencia a la máquina en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(MOTORES);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String motorId = snapshot.getKey();

                    // Borrar la máquina de la base de datos
                    assert motorId != null;
                    mDatabase.child(motorId).removeValue();

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
