package com.example.manttoprev.Modelo;

public class Motor {
    private String nombre;
    private String area;
    private String seccion;
    private String equipo;
    private String motor;
    private String descripcion;

    public Motor(String nombre, String area, String seccion, String equipo, String motor, String descripcion) {
        this.nombre = nombre;
        this.area = area;
        this.seccion = seccion;
        this.equipo = equipo;
        this.motor = motor;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
