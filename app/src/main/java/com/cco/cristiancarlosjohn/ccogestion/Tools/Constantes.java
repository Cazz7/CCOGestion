package com.cco.cristiancarlosjohn.ccogestion.Tools;

/**
 * Clase que contiene los códigos usados en "I Wish" para
 * mantener la integridad en las interacciones entre actividades
 * y fragmentos
 */
public class Constantes {
    /**
     * Transición Home -> Detalle
     */
    public static final int CODIGO_DETALLE = 100;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION = 101;
    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta carácteristica ":63343";.
     */
    //private static final String PUERTO_HOST = "";
    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String URL = "https://ptcazz.000webhostapp.com";

    //Constantes para servicio HTTP - by Cristian
    public static final String GET_USERS = URL + "/cristian/obtener_usuario_por_id.php";
    public static final String REGISTER_TOKEN = URL + "/cristian/register_token.php";
    public static String GROUP_TEST = "test";
    public static final String RADICADO = "idradicado";
    public static final String COD_EVENTO = "cod_evento";
    public static final String VIA = "via";
    public static final String SECTOR = "kilo_sector";

}
