package com.cco.cristiancarlosjohn.ccogestion.Model;

/**
 * Reflejo de la tabla 'desaeventos' en la base de datos
 */

public class DesaEventos {

    private static final String TAG = DesaEventos.class.getSimpleName();

    /*
        Atributos de la tabla desaeventos
    */
    private String IdDesaeventos;
    private String IdRadicado;
    private String FECHA;
    private String COD_EVENTO;
    private String SUB_EVENTO;
    private String OBSERVACIONES;
    private String USUARIO;
    private String Estado;
    private String FechaIngSistema;

    public DesaEventos(String idDesaeventos, String idRadicado, String FECHA, String COD_EVENTO, String SUB_EVENTO, String OBSERVACIONES, String USUARIO, String estado, String fechaIngSistema) {
        IdDesaeventos = idDesaeventos;
        IdRadicado = idRadicado;
        this.FECHA = FECHA;
        this.COD_EVENTO = COD_EVENTO;
        this.SUB_EVENTO = SUB_EVENTO;
        this.OBSERVACIONES = OBSERVACIONES;
        this.USUARIO = USUARIO;
        Estado = estado;
        FechaIngSistema = fechaIngSistema;
    }

    public String getIdDesaeventos() {
        return IdDesaeventos;
    }

    public void setIdDesaeventos(String idDesaeventos) {
        IdDesaeventos = idDesaeventos;
    }

    public String getIdRadicado() {
        return IdRadicado;
    }

    public void setIdRadicado(String idRadicado) {
        IdRadicado = idRadicado;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public String getCOD_EVENTO() {
        return COD_EVENTO;
    }

    public void setCOD_EVENTO(String COD_EVENTO) {
        this.COD_EVENTO = COD_EVENTO;
    }

    public String getSUB_EVENTO() {
        return SUB_EVENTO;
    }

    public void setSUB_EVENTO(String SUB_EVENTO) {
        this.SUB_EVENTO = SUB_EVENTO;
    }

    public String getOBSERVACIONES() {
        return OBSERVACIONES;
    }

    public void setOBSERVACIONES(String OBSERVACIONES) {
        this.OBSERVACIONES = OBSERVACIONES;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getFechaIngSistema() {
        return FechaIngSistema;
    }

    public void setFechaIngSistema(String fechaIngSistema) {
        FechaIngSistema = fechaIngSistema;
    }

    /**
     * Compara los atributos de dos metas
     *
     * @param desaEventos Meta externa
     * @return true si son iguales, false si hay cambios
     */
    public boolean compararCon(DesaEventos desaEventos) {

        return  this.IdDesaeventos.compareTo(desaEventos.IdDesaeventos) == 0 &&
                this.IdRadicado.compareTo(desaEventos.IdRadicado) == 0 &&
                this.FECHA.compareTo(desaEventos.FECHA) == 0 &&
                this.COD_EVENTO.compareTo(desaEventos.COD_EVENTO) == 0 &&
                this.SUB_EVENTO.compareTo(desaEventos.SUB_EVENTO) == 0 &&
                this.OBSERVACIONES.compareTo(desaEventos.OBSERVACIONES) == 0 &&
                this.USUARIO.compareTo(desaEventos.USUARIO) == 0 &&
                this.Estado.compareTo(desaEventos.Estado) == 0 &&
                this.FechaIngSistema.compareTo(desaEventos.FechaIngSistema) == 0;

    }
}
