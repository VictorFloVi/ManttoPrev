package com.example.manttoprev.Presentador;

import java.util.List;

public interface AislamientoContract {
    interface View{
        void mostrarAreas(List<String> areas);
        void mostrarEquipos(List<String> equipos);
    }
    interface Presenter{
        void obtenerAreas();
        void obtenerEquipos(String areaSeleccionada);
    }
}
