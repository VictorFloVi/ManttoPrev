package com.example.manttoprev.Presentador;

import com.example.manttoprev.Modelo.Ubicacion;
import com.example.manttoprev.Vista.UbicacionAdmin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class UbiicacionAdminPresenter implements UbicacionAdminContract.Presenter{
    private static final String UBICACION = "ubicacion";
    private static final String NOMBRE = "nombre";
    private static final String DESCRIPCION = "descripcion";
    private UbicacionAdmin view;
    private DatabaseReference mDatabase;

    public UbiicacionAdminPresenter(UbicacionAdmin view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void listarUbicacion() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Ubicacion> ubicacions = new ArrayList<>();

                for (DataSnapshot ubicacionSnapshot : dataSnapshot.getChildren()) {
                    String nombre = ubicacionSnapshot.child(DESCRIPCION).getValue(String.class);
                    Ubicacion ubicacion = new Ubicacion(nombre);
                    ubicacions.add(ubicacion);
                }
                // Llama al método de la Vista para mostrar los proveedores
                view.showUbicacion(ubicacions);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Llama al método de la Vista para mostrar mensajes de error
                view.showErrorMessage("Error al cargar las ubicaciones: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void clicItemListaUbicacion(String nombreUbicacion) {
        // Obtener la descripción desde la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION);
        Query query = mDatabase.orderByChild(DESCRIPCION).equalTo(nombreUbicacion);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreUbicacion = snapshot.child(NOMBRE).getValue(String.class);
                    String descripcionUbicacion = snapshot.child(DESCRIPCION).getValue(String.class);
                    // Notificar a la vista con los detalles
                    view.showDetallesUbicacionSeleccionada(nombreUbicacion, descripcionUbicacion);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }


    @Override
    public void agregarUbicacion(String nombre, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION);

            // Realizar una consulta para verificar si ya existe una ubicación con el mismo nombre
            Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Ya existe una ubicación con el mismo nombre, muestra un mensaje de error
                        view.showErrorMessage("Ya existe una ubicación con ese nombre");
                    } else {
                        // No existe una ubicacion con el mismo nombre, procede a agregarla

                        // Crear un objeto para el ubicacion
                        Ubicacion ubicacion = new Ubicacion(nombre,descripcion);

                        // Agrega ubicacion con la URL de la imagen a la base de datos
                        mDatabase.push().setValue(ubicacion);

                        // Notifica a la vista de éxito
                        view.showSuccessMessage("Ubicacion agregada con éxito.");

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Manejar el error de Firebase aquí
                }
            });
        }
    }

    public void autocompletarUbicacion(String textoBusqueda) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION);

        Query query = mDatabase.orderByChild(NOMBRE).startAt(textoBusqueda).endAt(textoBusqueda + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> ubicacionEncontrada = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    ubicacionEncontrada.add(nombre);
                }

                // Notifica a la vista con las ubicaciones encontradas
                view.showUbicacionEncontradaAutocompletado(ubicacionEncontrada);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    @Override
    public void consultarUbicacion(String nombreUbicacion) {
        // Validar que el nombre de la ubicacion no sea nulo o esté vacío
        if (nombreUbicacion == null || nombreUbicacion.trim().isEmpty()) {
            view.showErrorMessage("Ingrese un nombre de ubicación");
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombreUbicacion);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    String descripcion = snapshot.child(DESCRIPCION).getValue(String.class);

                    // Crear un objeto Ubicacion con la información obtenida
                    Ubicacion ubicacion = new Ubicacion(nombre, descripcion);

                    // Notificar a la vista con el ubicacion obtenida
                    view.showConsultarUbicacion(ubicacion);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    @Override
    public void editarUbicacion(String nombre, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
            return;
        }

        // Obtener la referencia a la ubicación en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // Actualiza al area
                    Ubicacion ubicacionActualizado = new Ubicacion(nombre, descripcion);
                    snapshot.getRef().setValue(ubicacionActualizado);

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Ubicación actualizada con éxito.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
                view.showErrorMessage("Error al editar la ubicación: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void borrarUbicacion(String nombre) {
        // Validar el nombre de la ubicación

        if (nombre.isEmpty()) {
            view.showErrorMessage("Nombre de ubicación inválida");
            return;
        }

        // Obtener la referencia a la ubicación en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(UBICACION);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String ubicacionId = snapshot.getKey();

                    // Borrar el área de la base de datos
                    assert ubicacionId != null;
                    mDatabase.child(ubicacionId).removeValue();

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
