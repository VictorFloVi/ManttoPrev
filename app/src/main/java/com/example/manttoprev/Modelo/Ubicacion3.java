package com.example.manttoprev.Modelo;

public class Ubicacion3 {
    private String nombre;
    private String Ubicacion2;
    private String Ubicacion;
    private String descripcion;

    public Ubicacion3(String nombre, String Ubicacion2, String Ubicacion, String descripcion) {
        this.nombre = nombre;
        this.Ubicacion2 = Ubicacion2;
        this.Ubicacion = Ubicacion;
        this.descripcion = descripcion;
    }

    public Ubicacion3(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion2() {
        return Ubicacion2;
    }

    public void setUbicacion2(String ubicacion2) {
        this.Ubicacion2 = ubicacion2;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.Ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
