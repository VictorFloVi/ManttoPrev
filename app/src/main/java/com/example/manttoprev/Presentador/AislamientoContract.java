package com.example.manttoprev.Presentador;

import java.util.List;

public interface AislamientoContract {
    interface View{
        void mostrarAreas(List<String> areas);
        void mostrarSecciones(List<String> secciones);
        void mostrarEquipos(List<String> equipos);
        void mostrarMaquinas(List<String> maquinas);
    }
    interface Presenter{
        void obtenerAreas();
        void obtenerSecciones(String areaSeleccionada);
        void obtenerEquipos(String equipoSeleccionado);
        void obtenerMaquinas(String maquinaSeleccionada);
    }
}
