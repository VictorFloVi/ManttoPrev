package com.example.manttoprev.Presentador;

import com.example.manttoprev.Modelo.Maquina;

import java.util.List;

public interface MaquinaAdminContract {
    interface View{
        void showDetallesMaquinaSeleccionado(String nombreMaquina, String equipoMaquina, String areaMaquina, String descripcionMaquina);
        void showMaquinasEncontradosAutocompletado(List<String> maquinas);
        void showConsultarMaquina(Maquina maquina);
        void mostrarEquipos(List<String> equipos);
        void mostrarAreas(List<String> areas);
    }
    interface Presenter{
        void listarMaquinas();
        void clicItemListaMaquina(String nombreMaquina);
        void autocompletarMaquina(String textoBusqueda);
        void obtenerEquipos();
        void obtenerAreas();
        void agregarMaquina(String equipo,String maquina, String area, String descripcion);
        void consultarMaquina(String nombreMaquina);
        void editarMaquina(String nombre, String equipo, String area, String descripcion);
        void borrarMaquina(String nombre);
    }
}
