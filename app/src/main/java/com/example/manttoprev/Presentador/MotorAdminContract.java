package com.example.manttoprev.Presentador;


import com.example.manttoprev.Modelo.Motor;

import java.util.List;

public interface MotorAdminContract {
    interface View{
        void showDetallesMotorSeleccionado(String nombreMotor, String maquinaMotor, String equipoMotor, String seccionMotor, String areaMotor, String descripcionMotor);
        void showMotoresEncontradosAutocompletado(List<String> motores);
        void showConsultarMotor(Motor motor);
        void mostrarMaquinas(List<String> maquinas);
        void mostrarEquipos(List<String> equipos);
        void mostrarSecciones(List<String> secciones);
        void mostrarAreas(List<String> areas);

    }
    interface Presenter{
        void listarMotores();
        void clicItemListaMotor(String nombreMaquina);
        void autocompletarMotor(String textoBusqueda);
        void obtenerMaquinas(String maquinaSeleccionada);
        void obtenerEquipos(String equipoSeleccionado);
        void obtenerSecciones(String areaSeleccionada);
        void obtenerAreas();
        void agregarMotor(String motor, String maquina, String equipo, String seccion, String area, String descripcion);
        void consultarMotor(String nombreMotor);
        void editarMotor(String nombre, String maquina, String equipo, String seccion, String area, String descripcion);
        void borrarMotor(String nombre);
    }
}
