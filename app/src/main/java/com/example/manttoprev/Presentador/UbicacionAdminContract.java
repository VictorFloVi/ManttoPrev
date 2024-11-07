package com.example.manttoprev.Presentador;

import com.example.manttoprev.Modelo.Ubicacion;

import java.util.List;

public interface UbicacionAdminContract {
    interface View{

        void showDetallesUbicacionSeleccionada(String nombre, String descripcionUbicacion);
        void showUbicacionEncontradaAutocompletado(List<String> ubicacion);
        void showConsultarUbicacion(Ubicacion ubicacion);
    }

    interface Presenter{
        void listarUbicacion();
        void agregarUbicacion(String ubicacion, String descripcion);
        void clicItemListaUbicacion(String nombreUbicacion);
        void autocompletarUbicacion(String textoBusqueda);
        void consultarUbicacion(String nombreUbicacion);
        void editarUbicacion(String nombre, String descripcion);
        void borrarUbicacion(String nombre);

    }
}
