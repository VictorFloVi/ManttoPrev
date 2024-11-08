package com.example.manttoprev.Presentador;


import com.example.manttoprev.Modelo.Ubicacion2;

import java.util.List;

public interface Ubicacion2AdminContract {
    interface View{
        void showDetallesUbicacion2Seleccionado(String nombre, String ubicacionUbicacion2, String descripcionUbicacion2);
        void showUbicacion2EncontradosAutocompletado(List<String> ubicacion2);
        void showConsultarUbicacion2(Ubicacion2 ubicacion2);
        void mostrarUbicacion(List<String> ubicacion);
    }
    interface Presenter{
        void listarUbicacion2();
        void clicItemListaUbicacion2(String nombreUbicacion2);
        void autocompletarUbicacion2(String textoBusqueda);
        void obtenerUbicacion();
        void agregarUbicacion2(String ubicacion2, String ubicacion, String descripcion);
        void consultarUbicacion2(String nombreUbicacion2);
        void editarUbicacion2(String nombre, String ubicacion, String descripcion);
        void borrarUbicacion2(String nombre);

    }
}
