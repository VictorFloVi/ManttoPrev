package com.example.manttoprev.Modelo;

import java.util.Date;

public class Vibracion {
    private String area;
    private String seccion;
    private String equipo;
    private String maquina;
    private String motor;

    private Double horisoc;
    private Double horbduc;
    private Double horgc;
    private Double verisoc;
    private Double verbduc;
    private Double vergc;
    private Double axiisoc;
    private Double axibduc;
    private Double axigc;

    private Double horisov;
    private Double horbduv;
    private Double horgv;
    private Double verisov;
    private Double verbduv;
    private Double vergv;
    private Double axiisov;
    private Double axibduv;
    private Double axigv;
    private Date fecha;

    public Vibracion(String area, String seccion, String equipo, String maquina, String motor,
                     Double horisoc, Double horbduc, Double horgc, Double verisoc, Double verbduc,
                     Double vergc, Double axiisoc, Double axibduc, Double axigc, Double horisov,
                     Double horbduv, Double horgv, Double verisov, Double verbduv, Double vergv,
                     Double axiisov, Double axibduv, Double axigv, Date fecha) {
        this.area = area;
        this.seccion = seccion;
        this.equipo = equipo;
        this.maquina = maquina;
        this.motor = motor;
        this.horisoc = horisoc;
        this.horbduc = horbduc;
        this.horgc = horgc;
        this.verisoc = verisoc;
        this.verbduc = verbduc;
        this.vergc = vergc;
        this.axiisoc = axiisoc;
        this.axibduc = axibduc;
        this.axigc = axigc;
        this.horisov = horisov;
        this.horbduv = horbduv;
        this.horgv = horgv;
        this.verisov = verisov;
        this.verbduv = verbduv;
        this.vergv = vergv;
        this.axiisov = axiisov;
        this.axibduv = axibduv;
        this.axigv = axigv;
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

    public Double getHorisoc() {
        return horisoc;
    }

    public void setHorisoc(Double horisoc) {
        this.horisoc = horisoc;
    }

    public Double getHorbduc() {
        return horbduc;
    }

    public void setHorbduc(Double horbduc) {
        this.horbduc = horbduc;
    }

    public Double getHorgc() {
        return horgc;
    }

    public void setHorgc(Double horgc) {
        this.horgc = horgc;
    }

    public Double getVerisoc() {
        return verisoc;
    }

    public void setVerisoc(Double verisoc) {
        this.verisoc = verisoc;
    }

    public Double getVerbduc() {
        return verbduc;
    }

    public void setVerbduc(Double verbduc) {
        this.verbduc = verbduc;
    }

    public Double getVergc() {
        return vergc;
    }

    public void setVergc(Double vergc) {
        this.vergc = vergc;
    }

    public Double getAxiisoc() {
        return axiisoc;
    }

    public void setAxiisoc(Double axiisoc) {
        this.axiisoc = axiisoc;
    }

    public Double getAxibduc() {
        return axibduc;
    }

    public void setAxibduc(Double axibduc) {
        this.axibduc = axibduc;
    }

    public Double getAxigc() {
        return axigc;
    }

    public void setAxigc(Double axigc) {
        this.axigc = axigc;
    }

    public Double getHorisov() {
        return horisov;
    }

    public void setHorisov(Double horisov) {
        this.horisov = horisov;
    }

    public Double getHorbduv() {
        return horbduv;
    }

    public void setHorbduv(Double horbduv) {
        this.horbduv = horbduv;
    }

    public Double getHorgv() {
        return horgv;
    }

    public void setHorgv(Double horgv) {
        this.horgv = horgv;
    }

    public Double getVerisov() {
        return verisov;
    }

    public void setVerisov(Double verisov) {
        this.verisov = verisov;
    }

    public Double getVerbduv() {
        return verbduv;
    }

    public void setVerbduv(Double verbduv) {
        this.verbduv = verbduv;
    }

    public Double getVergv() {
        return vergv;
    }

    public void setVergv(Double vergv) {
        this.vergv = vergv;
    }

    public Double getAxiisov() {
        return axiisov;
    }

    public void setAxiisov(Double axiisov) {
        this.axiisov = axiisov;
    }

    public Double getAxibduv() {
        return axibduv;
    }

    public void setAxibduv(Double axibduv) {
        this.axibduv = axibduv;
    }

    public Double getAxigv() {
        return axigv;
    }

    public void setAxigv(Double axigv) {
        this.axigv = axigv;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
