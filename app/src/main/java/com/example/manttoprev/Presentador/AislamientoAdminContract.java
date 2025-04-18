package com.example.manttoprev.Presentador;

import java.util.List;

public interface AislamientoAdminContract {
    interface View{
        void mostrarAreas(List<String> areas);
        void mostrarSecciones(List<String> secciones);
        void mostrarEquipos(List<String> equipos);
        void mostrarMaquinas(List<String> maquinas);
        void mostrarMotores(List<String> maquinas);

    }
    interface Presenter{
        void obtenerAreas();
        void obtenerSecciones(String areaSeleccionada);
        void obtenerEquipos(String equipoSeleccionado);
        void obtenerMaquinas(String maquinaSeleccionada);
        void obtenerMotores(String maquinaSeleccionada);
        void guardarAislamiento(String area, String seccion, String equipo, String maquina, String motor,
                                Double megadou, Double megadov, Double megadow,
                                Double resistenciau, Double resistenciav, Double resistenciaw,
                                Double amperajeu, Double amperajev, Double amperajew);

    }
}
