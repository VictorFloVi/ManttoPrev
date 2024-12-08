package com.example.manttoprev.Modelo;


public class Alertas {
    private String fechap;
    private String mensaje;
    private String motor;
    private String maquina;

    public Alertas() {

    }
    public Alertas(String fechap, String mensaje, String motor, String maquina) {
        this.fechap = fechap;
        this.mensaje = mensaje;
        this.motor = motor;
        this.maquina = maquina;
    }
    public String getFechap() {
        return fechap;
    }

    public void setFechap(String fechap) {
        this.fechap = fechap;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }
}
