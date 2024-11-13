package com.example.manttoprev.Presentador;

import java.util.List;

public interface AislamientoContract {
    interface View{
        void mostrarAreas(List<String> areas);
        void mostrarSecciones(List<String> secciones);
    }
    interface Presenter{
        void obtenerAreas();
        void obtenerSecciones(String areaSeleccionada);
    }
}
