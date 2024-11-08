package com.example.manttoprev.Presentador;

import com.example.manttoprev.Modelo.Ubicacion2;
import com.example.manttoprev.Vista.Ubicacion2Admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Ubicacion2AdminPresenter implements Ubicacion2AdminContract.Presenter{

    private static final String UBICACION2 = "ubicacion2";
    private static final String NOMBRE = "nombre";
    private static final String UBICACION = "ubicacion";
    private static final String DESCRIPCION = "descripcion";
    private static final String SELECCIONAR = "Seleccionar";
    private Ubicacion2Admin view;
    private DatabaseReference mDatabase;

    public Ubicacion2AdminPresenter(Ubicacion2Admin view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void listarUbicacion2() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION2);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Ubicacion2> ubicacion2s = new ArrayList<>();

                for (DataSnapshot equipoSnapshot : dataSnapshot.getChildren()) {
                    String nombre = equipoSnapshot.child(DESCRIPCION).getValue(String.class);
                    Ubicacion2 ubicacion2 = new Ubicacion2(nombre);
                    ubicacion2s.add(ubicacion2);
                }
                // Llama al método de la Vista para mostrar los proveedores
                view.showUbicacion2(ubicacion2s);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Llama al método de la Vista para mostrar mensajes de error
                view.showErrorMessage("Error al cargar las ubicaciones: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void obtenerUbicacion() {
        List<String> nombresUbicacion = new ArrayList<>();
        nombresUbicacion.add(SELECCIONAR);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nombresUbicacion.clear(); // Limpiar la lista antes de agregar las nuevas ubicaciones
                nombresUbicacion.add(SELECCIONAR); // Agregar la opción "Seleccionar" nuevamente
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreUbicacion = snapshot.child(DESCRIPCION).getValue(String.class);
                    if (nombreUbicacion != null) {
                        nombresUbicacion.add(nombreUbicacion);
                    }
                }
                view.mostrarUbicacion(nombresUbicacion);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error si es necesario
            }
        });
    }

    @Override
    public void clicItemListaUbicacion2(String nombreUbicacion2) {
        // Obtener la descripción desde la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION2);
        Query query = mDatabase.orderByChild(DESCRIPCION).equalTo(nombreUbicacion2);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreUbicacion2 = snapshot.child(NOMBRE).getValue(String.class);
                    String ubicacionUbicacion2 = snapshot.child(UBICACION).getValue(String.class);
                    String descripcionUbicacion2 = snapshot.child(DESCRIPCION).getValue(String.class);
                    // Notificar a la vista con los detalles
                    view.showDetallesUbicacion2Seleccionado(nombreUbicacion2, ubicacionUbicacion2, descripcionUbicacion2);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    public void autocompletarUbicacion2(String textoBusqueda) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION2);

        Query query = mDatabase.orderByChild(NOMBRE).startAt(textoBusqueda).endAt(textoBusqueda + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> ubicacion2Encontrados = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    ubicacion2Encontrados.add(nombre);
                }

                // Notifica a la vista con los equipos encontrados
                view.showUbicacion2EncontradosAutocompletado(ubicacion2Encontrados);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }


    @Override
    public void agregarUbicacion2(String nombre, String ubicacion, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || ubicacion.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION2);

            // Realizar una consulta para verificar si ya existe una ubicación2 con el mismo nombre
            Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Ya existe una ubicación2 con el mismo nombre, muestra un mensaje de error
                        view.showErrorMessage("Ya existe una ubicacion con ese nombre");
                    } else {
                        // No existe una ubicación2 con el mismo nombre, procede a agregarla

                        // Crear un objeto para el ubicacion2
                        Ubicacion2 ubicacion2 = new Ubicacion2(nombre, ubicacion,descripcion);

                        // Agrega ubicacion2 con la URL de la imagen a la base de datos
                        mDatabase.push().setValue(ubicacion2);

                        // Notifica a la vista de éxito
                        view.showSuccessMessage("Ubicacion2 agregado con éxito.");

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
    public void consultarUbicacion2(String nombreUbicacion2) {
        // Validar que el nombre del equipo no sea nulo o esté vacío
        if (nombreUbicacion2 == null || nombreUbicacion2.trim().isEmpty()) {
            view.showErrorMessage("Ingrese un nombre de ubicación2");
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION2);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombreUbicacion2);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    String ubicacion = snapshot.child(UBICACION).getValue(String.class);
                    String descripcion = snapshot.child(DESCRIPCION).getValue(String.class);

                    // Crear un objeto Ubicacion2 con la información obtenida
                    Ubicacion2 ubicacion2 = new Ubicacion2(nombre, ubicacion, descripcion);

                    // Notificar a la vista con el ubicacion2 obtenido
                    view.showConsultarUbicacion2(ubicacion2);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    @Override
    public void editarUbicacion2(String nombre, String ubicacion, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || ubicacion.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
            return;
        }

        // Obtener la referencia al equipo en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION2);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // Actualiza el equipo
                    Ubicacion2 ubicacion2Actualizado = new Ubicacion2(nombre, ubicacion, descripcion);
                    snapshot.getRef().setValue(ubicacion2Actualizado);

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Ubicacion2 actualizada con éxito.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
                view.showErrorMessage("Error al editar la ubicación2: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void borrarUbicacion2(String nombre) {
        // Validar el nombre de la ubicación

        if (nombre.isEmpty()) {
            view.showErrorMessage("Nombre de ubicacion2 inválido");
            return;
        }

        // Obtener la referencia al equipo en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION2);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String ubicacion2Id = snapshot.getKey();

                    // Borrar la ubicacion2 de la base de datos
                    assert ubicacion2Id != null;
                    mDatabase.child(ubicacion2Id).removeValue();

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Ubicacion2 eliminado con éxito.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
                view.showErrorMessage("Error al borrar la ubicación: " + databaseError.getMessage());
            }
        });
    }

}
