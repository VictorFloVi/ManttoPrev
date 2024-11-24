package com.example.manttoprev.Modelo;

import java.util.Date;

public class Aislamiento {
    private String area;
    private String seccion;
    private String equipo;
    private String maquina;
    private String motor;
    private Double megadou;
    private Double megadov;
    private Double megadow;
    private Double resistenciau;
    private Double resistenciav;
    private Double resistenciaw;
    private Double amperajeu;
    private Double amperajev;
    private Double amperajew;
    private Date fecha;

    public Aislamiento(String area, String seccion, String equipo, String maquina, String motor,
                       Double megadou, Double megadov, Double megadow,
                       Double resistenciau, Double resistenciav, Double resistenciaw,
                       Double amperajeu, Double amperajev, Double amperajew, Date fecha) {
        this.area = area;
        this.seccion = seccion;
        this.equipo = equipo;
        this.maquina = maquina;
        this.motor = motor;
        this.megadou = megadou;
        this.megadov = megadov;
        this.megadow = megadow;
        this.resistenciau = resistenciau;
        this.resistenciav = resistenciav;
        this.resistenciaw = resistenciaw;
        this.amperajeu = amperajeu;
        this.amperajev = amperajev;
        this.amperajew = amperajew;
        this.fecha = fecha;
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

    public Double getMegadou() {
        return megadou;
    }

    public void setMegadou(Double megadou) {
        this.megadou = megadou;
    }

    public Double getMegadov() {
        return megadov;
    }

    public void setMegadov(Double megadov) {
        this.megadov = megadov;
    }

    public Double getMegadow() {
        return megadow;
    }

    public void setMegadow(Double megadow) {
        this.megadow = megadow;
    }

    public Double getResistenciau() {
        return resistenciau;
    }

    public void setResistenciau(Double resistenciau) {
        this.resistenciau = resistenciau;
    }

    public Double getResistenciav() {
        return resistenciav;
    }

    public void setResistenciav(Double resistenciav) {
        this.resistenciav = resistenciav;
    }

    public Double getResistenciaw() {
        return resistenciaw;
    }

    public void setResistenciaw(Double resistenciaw) {
        this.resistenciaw = resistenciaw;
    }

    public Double getAmperajeu() {
        return amperajeu;
    }

    public void setAmperajeu(Double amperajeu) {
        this.amperajeu = amperajeu;
    }

    public Double getAmperajev() {
        return amperajev;
    }

    public void setAmperajev(Double amperajev) {
        this.amperajev = amperajev;
    }

    public Double getAmperajew() {
        return amperajew;
    }

    public void setAmperajew(Double amperajew) {
        this.amperajew = amperajew;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
