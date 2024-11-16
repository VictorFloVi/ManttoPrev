package com.example.manttoprev.Modelo;

public class Motor {
    private String nombre;
    private String maquina;
    private String equipo;
    private String seccion;
    private String area;
    private String descripcion;

    public Motor(String nombre, String maquina, String equipo, String seccion, String area, String descripcion) {
        this.nombre = nombre;
        this.maquina = maquina;
        this.equipo = equipo;
        this.seccion = seccion;
        this.area = area;
        this.descripcion = descripcion;
    }
    public Motor(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
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
