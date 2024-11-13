package com.example.manttoprev.Modelo;

public class Equipo {
    private String nombre;
    private String seccion;
    private String area;
    private String descripcion;

    public Equipo(String nombre, String seccion, String area, String descripcion) {
        this.nombre = nombre;
        this.seccion = seccion;
        this.area = area;
        this.descripcion = descripcion;
    }

    public Equipo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
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
