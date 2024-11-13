package com.example.manttoprev.Presentador;

import com.example.manttoprev.Modelo.Ubicacion3;
import com.example.manttoprev.Vista.Ubicacion3Admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Ubicacion3AdminPresenter implements Ubicacion3AdminContract.Presenter{

    private static final String UBICACION3 = "ubicacion3";
    private static final String UBICACION2 = "ubicacion2";
    private static final String UBICACION = "ubicacion";
    private static final String NOMBRE = "nombre";
    private static final String SELECCIONAR = "Seleccionar";
    private static final String DESCRIPCION = "descripcion";
    private Ubicacion3Admin view;
    private DatabaseReference mDatabase;

    public Ubicacion3AdminPresenter(Ubicacion3Admin view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void listarUbicacion3() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION3);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Ubicacion3> ubicacion3s = new ArrayList<>();

                for (DataSnapshot maquinaSnapshot : dataSnapshot.getChildren()) {
                    String nombre = maquinaSnapshot.child(DESCRIPCION).getValue(String.class);
                    Ubicacion3 ubicacion3 = new Ubicacion3(nombre);
                    ubicacion3s.add(ubicacion3);
                }
                // Llama al método de la Vista para mostrar los proveedores
                view.showUbicacion3(ubicacion3s);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Llama al método de la Vista para mostrar mensajes de error
                view.showErrorMessage("Error al cargar las ubicaciones: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void obtenerUbicacion2() {
        List<String> nombresUbicacion2 = new ArrayList<>();
        nombresUbicacion2.add(SELECCIONAR);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION2);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nombresUbicacion2.clear(); // Limpiar la lista antes de agregar las nuevas ubicaciones
                nombresUbicacion2.add(SELECCIONAR); // Agregar la opción "Seleccionar" nuevamente
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreUbicacion2 = snapshot.child(DESCRIPCION).getValue(String.class);
                    if (nombreUbicacion2 != null) {
                        nombresUbicacion2.add(nombreUbicacion2);
                    }
                }
                view.mostrarUbicacion2(nombresUbicacion2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error si es necesario
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
    public void clicItemListaUbicacion3(String nombreUbicacion3) {
        // Obtener la descripción desde la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION3);
        Query query = mDatabase.orderByChild(DESCRIPCION).equalTo(nombreUbicacion3);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreUbicacion3 = snapshot.child(NOMBRE).getValue(String.class);
                    String ubicacion2Ubicacion3 = snapshot.child(UBICACION2).getValue(String.class);
                    String ubicacionUbicacion3 = snapshot.child(UBICACION).getValue(String.class);
                    String descripcionUbicacion3 = snapshot.child(DESCRIPCION).getValue(String.class);
                    // Notificar a la vista con los detalles
                    view.showDetallesUbicacion3Seleccionado(nombreUbicacion3, ubicacion2Ubicacion3, ubicacionUbicacion3, descripcionUbicacion3);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    public void autocompletarUbicacion3(String textoBusqueda) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION3);

        Query query = mDatabase.orderByChild(NOMBRE).startAt(textoBusqueda).endAt(textoBusqueda + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> ubicacion3Encontrados = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    ubicacion3Encontrados.add(nombre);
                }

                // Notifica a la vista con los equipos encontrados
                view.showUbicacion3EncontradosAutocompletado(ubicacion3Encontrados);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }


    @Override
    public void agregarUbicacion3(String nombre, String ubicacion2, String ubicacion, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || ubicacion2.isEmpty() || ubicacion.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION3);

            // Realizar una consulta para verificar si ya existe un ubicacion2 con el mismo nombre
            Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Ya existe un ubicacion2 con el mismo nombre, muestra un mensaje de error
                        view.showErrorMessage("Ya existe una ubicación con ese nombre");
                    } else {
                        // No existe una ubicación con el mismo nombre, procede a agregarla

                        // Crear un objeto para la ubicación
                        Ubicacion3 ubicacion3 = new Ubicacion3(nombre, ubicacion2, ubicacion,descripcion);

                        // Agrega ubicación con la URL de la imagen a la base de datos
                        mDatabase.push().setValue(ubicacion3);

                        // Notifica a la vista de éxito
                        view.showSuccessMessage("Ubicación agregada con éxito.");

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
    public void consultarUbicacion3(String nombreUbicacion3) {
        // Validar que el nombre de la ubicación no sea nulo o esté vacío
        if (nombreUbicacion3 == null || nombreUbicacion3.trim().isEmpty()) {
            view.showErrorMessage("Ingrese un nombre de ubicación");
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION3);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombreUbicacion3);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    String ubicacion2 = snapshot.child(UBICACION2).getValue(String.class);
                    String ubicacion = snapshot.child(UBICACION).getValue(String.class);
                    String descripcion = snapshot.child(DESCRIPCION).getValue(String.class);

                    // Crear un objeto Ubicacion2 con la información obtenida
                    Ubicacion3 ubicacion3 = new Ubicacion3(nombre, ubicacion2, ubicacion, descripcion);

                    // Notificar a la vista con el ubicacion2 obtenido
                    view.showConsultarUbicacion3(ubicacion3);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    @Override
    public void editarUbicacion3(String nombre, String ubicacion2, String ubicacion, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || ubicacion2.isEmpty() || ubicacion.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
            return;
        }

        // Obtener la referencia a la máquina en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION3);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // Actualiza la máquina
                    Ubicacion3 ubicacion3Actualizado = new Ubicacion3(nombre, ubicacion2, ubicacion, descripcion);
                    snapshot.getRef().setValue(ubicacion3Actualizado);

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Ubicacion2 actualizada con éxito.");
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
    public void borrarUbicacion3(String nombre) {
        // Validar el nombre del equipo

        if (nombre.isEmpty()) {
            view.showErrorMessage("Nombre de ubicación inválido");
            return;
        }

        // Obtener la referencia a la máquina en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION3);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String ubicacion3Id = snapshot.getKey();

                    // Borrar la ubicación de la base de datos
                    assert ubicacion3Id != null;
                    mDatabase.child(ubicacion3Id).removeValue();

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Ubicación eliminada con éxito.");
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
