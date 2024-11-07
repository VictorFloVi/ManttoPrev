package com.example.manttoprev.Modelo;

public class Maquina {
    private String nombre;
    private String equipo;
    private String area;
    private String descripcion;

    public Maquina(String nombre, String equipo, String area, String descripcion) {
        this.nombre = nombre;
        this.equipo = equipo;
        this.area = area;
        this.descripcion = descripcion;
    }

    public Maquina(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
