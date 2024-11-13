package com.example.manttoprev.Presentador;


import com.example.manttoprev.Modelo.Seccion;

import java.util.List;

public interface SeccionAdminContract {
    interface View{
        void showDetallesSeccionSeleccionado(String nombre, String areaSeccion, String descripcionSeccion);
        void showSeccionesEncontradosAutocompletado(List<String> secciones);
        void showConsultarSeccion(Seccion seccion);
        void mostrarAreas(List<String> areas);
    }
    interface Presenter{
        void listarSecciones();
        void clicItemListaSeccion(String nombreSeccion);
        void autocompletarSeccion(String textoBusqueda);
        void obtenerAreas();
        void agregarSeccion(String seccion, String area, String descripcion);
        void consultarSeccion(String nombreSeccion);
        void editarSeccion(String nombre, String area, String descripcion);
        void borrarSeccion(String nombre);

    }
}
