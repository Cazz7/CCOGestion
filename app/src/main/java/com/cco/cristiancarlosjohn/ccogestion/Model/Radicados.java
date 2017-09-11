package com.cco.cristiancarlosjohn.ccogestion.Model;

/**
 * Created by jonathan on 21/08/2017.
 */

public class Radicados {
    private String IdRadicado;
    private String Cod_Evento;
    private String Fecha_Creacion;
    private String Via;
    private String kiloSector;
    private String kilometro;
    private String Estado;
    private String Usuario;
    private String Nombre;
    private String Contacto;
    private String FechaCierreEvento;

    public String getIdRadicado() {
        return IdRadicado;
    }

    public void setIdRadicado(String idRadicado) {
        IdRadicado = idRadicado;
    }

    public String getCod_Evento() {
        return Cod_Evento;
    }

    public void setCod_Evento(String cod_Evento) {
        Cod_Evento = cod_Evento;
    }

    public String getFecha_Creacion() {
        return Fecha_Creacion;
    }

    public void setFecha_Creacion(String fecha_Creacion) {
        Fecha_Creacion = fecha_Creacion;
    }

    public String getVia() {
        return Via;
    }

    public void setVia(String via) {
        Via = via;
    }

    public String getKiloSector() {
        return kiloSector;
    }

    public void setKiloSector(String kiloSector) {
        this.kiloSector = kiloSector;
    }

    public String getKilometro() {
        return kilometro;
    }

    public void setKilometro(String kilometro) {
        this.kilometro = kilometro;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String contacto) {
        Contacto = contacto;
    }

    public String getFechaCierreEvento() {
        return FechaCierreEvento;
    }

    public void setFechaCierreEvento(String fechaCierreEvento) {
        FechaCierreEvento = fechaCierreEvento;
    }

    public Radicados(String idRadicado, String cod_Evento,
                     String fecha_Creacion, String via,
                     String kiloSector, String kilometro,
                     String estado, String usuario, String nombre, String contacto, String fechaCierreEvento) {
        IdRadicado = idRadicado;
        Cod_Evento = cod_Evento;
        Fecha_Creacion = fecha_Creacion;
        Via = via;
        this.kiloSector = kiloSector;
        this.kilometro = kilometro;
        Estado = estado;
        Usuario = usuario;
        Nombre = nombre;
        Contacto = contacto;
        FechaCierreEvento = fechaCierreEvento;

    }


}
