package com.example.manttoprev.Presentador;

import com.example.manttoprev.Modelo.Equipo;

import java.util.List;

public interface EquipoAdminContract {
    interface View{
        void showDetallesEquipoSeleccionado(String nombreEquipo, String seccionEquipo, String areaEquipo, String descripcionEquipo);
        void showEquiposEncontradosAutocompletado(List<String> equipos);
        void showConsultarEquipo(Equipo equipo);
        void mostrarSecciones(List<String> secciones);
        void mostrarAreas(List<String> areas);
    }
    interface Presenter{
        void listarEquipos();
        void clicItemListaEquipo(String nombreEquipo);
        void autocompletarEquipo(String textoBusqueda);
        void obtenerSecciones(String areaSeleccionada);
        void obtenerAreas();
        void agregarEquipo(String seccion, String equipo, String area, String descripcion);
        void consultarEquipo(String nombreEquipo);
        void editarEquipo(String nombre, String seccion, String area, String descripcion);
        void borrarEquipo(String nombre);
    }
}
