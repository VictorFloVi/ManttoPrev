package com.example.manttoprev.Presentador;


import com.example.manttoprev.Modelo.Equipo;

import java.util.List;

public interface EquipoAdminContract {
    interface View{
        void showDetallesEquipoSeleccionado(String nombre, String areaEquipo, String descripcionEquipo);
        void showEquiposEncontradosAutocompletado(List<String> equipos);
        void showConsultarEquipo(Equipo equipo);
        void mostrarAreas(List<String> areas);
    }
    interface Presenter{
        void listarEquipos();
        void clicItemListaEquipo(String nombreEquipo);
        void autocompletarEquipo(String textoBusqueda);
        void obtenerAreas();
        void agregarEquipo(String equipo, String area, String descripcion);
        void consultarEquipo(String nombreEquipo);
        void editarEquipo(String nombre, String area, String descripcion);
        void borrarEquipo(String nombre);

    }
}
