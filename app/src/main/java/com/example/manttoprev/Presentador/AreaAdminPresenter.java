package com.example.manttoprev.Presentador;

import com.example.manttoprev.Modelo.Area;
import com.example.manttoprev.Vista.AreaAdmin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AreaAdminPresenter implements AreaAdminContract.Presenter{
    private static final String AREAS = "areas";
    private static final String NOMBRE = "nombre";
    private static final String DESCRIPCION = "descripcion";
    private AreaAdmin view;
    private DatabaseReference mDatabase;

    public AreaAdminPresenter(AreaAdmin view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void listarAreas() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(AREAS);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Area> areas = new ArrayList<>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String nombre = areaSnapshot.child(DESCRIPCION).getValue(String.class);
                    Area area = new Area(nombre);
                    areas.add(area);
                }
                // Llama al método de la Vista para mostrar los proveedores
                view.showAreas(areas);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Llama al método de la Vista para mostrar mensajes de error
                view.showErrorMessage("Error al cargar las áreas: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void clicItemListaArea(String nombreArea) {
        // Obtener la descripción desde la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(AREAS);
        Query query = mDatabase.orderByChild(DESCRIPCION).equalTo(nombreArea);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreArea = snapshot.child(NOMBRE).getValue(String.class);
                    String descripcionArea = snapshot.child(DESCRIPCION).getValue(String.class);
                    // Notificar a la vista con los detalles
                    view.showDetallesAreaSeleccionado(nombreArea, descripcionArea);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }


    @Override
    public void agregarArea(String nombre, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(AREAS);

            // Realizar una consulta para verificar si ya existe una area con el mismo nombre
            Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Ya existe una area con el mismo nombre, muestra un mensaje de error
                        view.showErrorMessage("Ya existe una area con ese nombre");
                    } else {
                        // No existe una area con el mismo nombre, procede a agregarla

                        // Crear un objeto para el area
                        Area area = new Area(nombre,descripcion);

                        // Agrega area con la URL de la imagen a la base de datos
                        mDatabase.push().setValue(area);

                        // Notifica a la vista de éxito
                        view.showSuccessMessage("Area agregada con éxito.");

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Manejar el error de Firebase aquí
                }
            });
        }
    }

    public void autocompletarArea(String textoBusqueda) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(AREAS);

        Query query = mDatabase.orderByChild(NOMBRE).startAt(textoBusqueda).endAt(textoBusqueda + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> areasEncontradas = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    areasEncontradas.add(nombre);
                }

                // Notifica a la vista con las áreas encontradas
                view.showAreasEncontradosAutocompletado(areasEncontradas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    @Override
    public void consultarArea(String nombreArea) {
        // Validar que el nombre del area no sea nulo o esté vacío
        if (nombreArea == null || nombreArea.trim().isEmpty()) {
            view.showErrorMessage("Ingrese un nombre de área");
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child(AREAS);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombreArea);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child(NOMBRE).getValue(String.class);
                    String descripcion = snapshot.child(DESCRIPCION).getValue(String.class);

                    // Crear un objeto Area con la información obtenida
                    Area area = new Area(nombre, descripcion);

                    // Notificar a la vista con el area obtenida
                    view.showConsultarArea(area);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
            }
        });
    }

    @Override
    public void editarArea(String nombre, String descripcion) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
            return;
        }

        // Obtener la referencia al area en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(AREAS);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    // Actualiza al area
                    Area areaActualizado = new Area(nombre, descripcion);
                    snapshot.getRef().setValue(areaActualizado);

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Área actualizada con éxito.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error de Firebase aquí
                view.showErrorMessage("Error al editar el area: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void borrarArea(String nombre) {
        // Validar el nombre del área

        if (nombre.isEmpty()) {
            view.showErrorMessage("Nombre de área inválida");
            return;
        }

        // Obtener la referencia al área en la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference().child(AREAS);
        Query query = mDatabase.orderByChild(NOMBRE).equalTo(nombre);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String areaId = snapshot.getKey();

                    // Borrar el área de la base de datos
                    assert areaId != null;
                    mDatabase.child(areaId).removeValue();

                    // Notificar a la vista de éxito
                    view.showSuccessMessage("Área eliminada con éxito.");
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
