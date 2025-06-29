package com.example.manttoprev.Presentador;

import java.util.List;

public interface VibracionAdminContract {
    interface View{
        void mostrarAreas(List<String> areas);
        void mostrarSecciones(List<String> secciones);
        void mostrarEquipos(List<String> equipos);
        void mostrarMaquinas(List<String> maquinas);
        void mostrarMotores(List<String> maquinas);
        void setValoresSeleccionV(String area, String seccion, String equipo, String maquina, String motor);
    }

    interface Presenter{
        void obtenerAreas();
        void obtenerSecciones(String areaSeleccionada);
        void obtenerEquipos(String equipoSeleccionado);
        void obtenerMaquinas(String maquinaSeleccionada);
        void obtenerMotores(String maquinaSeleccionada);
        void guardarVibracion(String area, String seccion, String equipo, String maquina, String motor,
                              Double horisoc, Double horbduc, Double horgc, Double verisoc, Double verbduc,
                              Double vergc, Double axiisoc, Double axibduc, Double axigc, Double horisov,
                              Double horbduv, Double horgv, Double verisov, Double verbduv, Double vergv,
                              Double axiisov, Double axibduv, Double axigv);
        void procesarPushIdV(String pushId);

        void guardarAlerta(String fechaPeru, String prediccion, String string, String string1, String s);
    }
}
