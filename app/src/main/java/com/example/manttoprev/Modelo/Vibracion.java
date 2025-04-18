package com.example.manttoprev.Modelo;

public class Vibracion {
    private String area;
    private String seccion;
    private String equipo;
    private String maquina;
    private String motor;

    public Vibracion(String area, String seccion, String equipo, String maquina, String motor) {
        this.area = area;
        this.seccion = seccion;
        this.equipo = equipo;
        this.maquina = maquina;
        this.motor = motor;
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

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }
}
