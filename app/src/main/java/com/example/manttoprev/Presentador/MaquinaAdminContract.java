package com.example.manttoprev.Presentador;

import com.example.manttoprev.Modelo.Maquina;

import java.util.List;

public interface MaquinaAdminContract {
    interface View{
        void showDetallesMaquinaSeleccionada(String nombreMaquina, String equipoMaquina, String seccionMaquina, String areaMaquina, String descripcionMaquina);
        void showMaquinasEncontradosAutocompletado(List<String> maquinas);
        void showConsultarMaquina(Maquina maquina);
        void mostrarEquipos(List<String> equipos);
        void mostrarSecciones(List<String> secciones);
        void mostrarAreas(List<String> areas);

    }
    interface Presenter{
        void listarMaquinas();
        void clicItemListaMaquina(String nombreMaquina);
        void autocompletarMaquina(String textoBusqueda);
        void obtenerEquipos(String equipoSeleccionado);
        void obtenerSecciones(String areaSeleccionada);
        void obtenerAreas();
        void agregarMaquina(String maquina, String equipo, String seccion, String area, String descripcion);
        void consultarMaquina(String nombreMaquina);
        void editarMaquina(String nombre, String equipo, String seccion, String area, String descripcion);
        void borrarMaquina(String nombre);
    }
}
