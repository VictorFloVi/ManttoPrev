package com.example.manttoprev.Presentador;

import com.example.manttoprev.Modelo.Ubicacion3;

import java.util.List;

public interface Ubicacion3AdminContract {
    interface View{
        void showDetallesUbicacion3Seleccionado(String nombreUbicacion3, String Ubicacion2Ubicacion3, String UbicacionUbicacion3, String descripcionUbicacion3);
        void showUbicacion3EncontradosAutocompletado(List<String> ubicacion3);
        void showConsultarUbicacion3(Ubicacion3 ubicacion3);
        void mostrarUbicacion2(List<String> ubicacion2);
        void mostrarUbicacion(List<String> ubicacion);
    }
    interface Presenter{
        void listarUbicacion3();
        void clicItemListaUbicacion3(String nombreUbicacion3);
        void autocompletarUbicacion3(String textoBusqueda);
        void obtenerUbicacion2();
        void obtenerUbicacion();
        void agregarUbicacion3(String ubicacion2, String ubicacion3, String ubicacion, String descripcion);
        void consultarUbicacion3(String nombreUbicacion3);
        void editarUbicacion3(String nombre, String ubicacion2, String ubicacion, String descripcion);
        void borrarUbicacion3(String nombre);
    }
}
