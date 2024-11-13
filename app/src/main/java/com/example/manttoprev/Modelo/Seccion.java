package com.example.manttoprev.Modelo;

public class Seccion {
    private String nombre;
    private String area;
    private String descripcion;

    public Seccion(String nombre, String area, String descripcion) {
        this.nombre = nombre;
        this.area = area;
        this.descripcion = descripcion;
    }

    public Seccion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
