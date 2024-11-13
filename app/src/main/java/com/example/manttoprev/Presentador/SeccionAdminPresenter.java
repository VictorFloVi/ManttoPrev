package com.example.manttoprev.Presentador;

import androidx.annotation.NonNull;

import com.example.manttoprev.Modelo.Seccion;
import com.example.manttoprev.Vista.SeccionAdmin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeccionAdminPresenter implements SeccionAdminContract.Presenter{

    private static final String SECCION = "secciones";
    private static final String NOMBRE = "nombre";
    private static final String AREA = "area";
    private static final String DESCRIPCION = "descripcion";
    private static final String SELECCIONAR = "Seleccionar";
    private final SeccionAdmin view;
    private DatabaseReference mDatabase;

    public SeccionAdminPresenter(SeccionAdmin view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void listarSecciones() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(SECCION);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Seccion> secciones = new ArrayList<>();

                for (DataSnapshot seccionSnapshot : dataSnapshot.getChildren()) {
                    String nombre = seccionSnapshot.child(DESCRIPCION).getValue(String.class);
                    Seccion seccion = new Seccion(nombre);
                    secciones.add(seccion);
                }
                // Llama al método de la Vista para mostrar las secciones
                view.showSecciones(secciones);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Llama al método de la Vista para mostrar mensajes de error
                view.showErrorMessage("Error al cargar las secciones: " + databaseError.getMessage());
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
    public void clicItemListaSeccion(String nombreSeccion) {
        // Obtener la descripción desde la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(SECCION);
        Query query = mDatabase.orderByChild(DESCRIPCION).equalTo(nombreSeccion);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreSeccion = snapshot.child(NOMBRE).getValue(String.class);
                    String areaSeccion = snapshot.child(AREA).getValue(String.class);
                    String descripcionSeccion = snapshot.child(DESCRIPCION).getValue(String.class);
                    // Notificar a la vista con los detalles
                    view.showDetallesSeccionSeleccionado(nombreSeccion, areaSeccion, descripcionSeccion);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    public void autocompletarSeccion(String textoBusqueda) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(SECCION);

        Query query = mDatabase.orderByChild(NOMBRE).startAt(textoBusqueda).endAt(textoBusqueda + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> seccionesEncontrados = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    seccionesEncontrados.add(nombre);
                }

                // Notifica a la vista con las secciones encontradas
                view.showSeccionesEncontradosAutocompletado(seccionesEncontrados);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }


    @Override
    public void agregarSeccion(String nombre, String area, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || area.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(SECCION);

            // Realizar una consulta para verificar si ya existe una seccion con el mismo nombre
            Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Ya existe una sección con el mismo nombre, muestra un mensaje de error
                        view.showErrorMessage("Ya existe una sección con ese nombre");
                    } else {
                        // No existe un seccion con el mismo nombre, procede a agregarla

                        // Crear un objeto para el seccion
                        Seccion seccion = new Seccion(nombre,area,descripcion);

                        // Agrega seccion con la URL de la imagen a la base de datos
                        mDatabase.push().setValue(seccion);

                        // Notifica a la vista de éxito
                        view.showSuccessMessage("Seccion agregado con éxito.");

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
    public void consultarSeccion(String nombreSeccion) {
        // Validar que el nombre del sección no sea nulo o esté vacío
        if (nombreSeccion == null || nombreSeccion.trim().isEmpty()) {
            view.showErrorMessage("Ingrese un nombre de sección");
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child(SECCION);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombreSeccion);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    String area = snapshot.child(AREA).getValue(String.class);
                    String descripcion = snapshot.child(DESCRIPCION).getValue(String.class);

                    // Crear un objeto Seccion con la información obtenida
                    Seccion seccion = new Seccion(nombre, area, descripcion);

                    // Notificar a la vista con el seccion obtenido
                    view.showConsultarSeccion(seccion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    @Override
    public void editarSeccion(String nombre, String area, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || area.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
            return;
        }

        // Obtener la referencia a la sección en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(SECCION);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // Actualiza el equipo
                    Seccion seccionActualizado = new Seccion(nombre, area, descripcion);
                    snapshot.getRef().setValue(seccionActualizado);

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Seccion actualizada con éxito.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
                view.showErrorMessage("Error al editar el equipo: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void borrarSeccion(String nombre) {
        // Validar el nombre del sección

        if (nombre.isEmpty()) {
            view.showErrorMessage("Nombre de sección inválida");
            return;
        }

        // Obtener la referencia a la sección en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(SECCION);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String seccionId = snapshot.getKey();

                    // Borrar la sección de la base de datos
                    assert seccionId != null;
                    mDatabase.child(seccionId).removeValue();

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Seccion eliminado con éxito.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
                view.showErrorMessage("Error al borrar el área: " + databaseError.getMessage());
            }
        });
    }

}
