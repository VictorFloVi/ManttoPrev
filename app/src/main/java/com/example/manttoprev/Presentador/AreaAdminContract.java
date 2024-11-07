package com.example.manttoprev.Presentador;

import com.example.manttoprev.Modelo.Area;

import java.util.List;

public interface AreaAdminContract {
    interface View{

        void showDetallesAreaSeleccionado(String nombre, String descripcionArea);
        void showAreasEncontradosAutocompletado(List<String> areas);
        void showConsultarArea(Area area);
    }

    interface Presenter{
        void listarAreas();
        void agregarArea(String area, String descripcion);
        void clicItemListaArea(String nombreArea);
        void autocompletarArea(String textoBusqueda);
        void consultarArea(String nombreArea);
        void editarArea(String nombre, String descripcion);
        void borrarArea(String nombre);

    }
}
