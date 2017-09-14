package com.cco.cristiancarlosjohn.ccogestion.Tools;

/**
 * Clase que contiene los códigos usados en "I Wish" para
 * mantener la integridad en las interacciones entre actividades
 * y fragmentos
 */
public class Constantes {

    //Constantes para servicio HTTP - by Cristian
    private static final String URL = "https://ptcazz.000webhostapp.com";

    public static final String GET_USERS = URL + "/cristian/obtener_usuario_por_id.php";
    public static final String REGISTER_TOKEN = URL + "/cristian/register_token.php";
    public static String GROUP_TEST = "test";

    //Nombres para Json
    public static final String PERFILES = "perfiles";
    public static final String RADICADO = "idradicado";
    public static final String COD_EVENTO = "cod_evento";
    public static final String VIA = "via";
    public static final String SECTOR = "kilo_sector";
    //Confirmación
    public static final String IDRADICADO = "IdRadicado";
    public static final String FECHA_CREACION = "Fecha_Creacion";
    public static final String COD_EVENTO2 = "Cod_Evento";
    public static final String SUB_EVENTO = "SUB_EVENTO";
    public static final String OBSERVACIONES = "OBSERVACIONES";
    public static final String ESTADO = "Estado";
    public static final String USUARIO = "Usuario";
    public static final String FECHA_ING_SISTEMA = "FechaIngSistema";
    public static final String PERFILES_NOTI = "perfilesNoti";
    public static final String ACCION = "Accion";
    public static final String UNIDAD = "Unidades";

    //Constantes - by Carlos Cortinez
    public static final int CODIGO_DETALLE = 100;
    public static final int CODIGO_ACTUALIZACION = 101;

    //CARLOS
    //URLs del Web Service
    private static final String URL_C = "http://ptcazz.000webhostapp.com/Carlos_C";
    public static final String GET_EVENTOS_CURSO = URL_C +"/obtener_radicados.php";
    public static final String INSERT_EVENTOS_CURSO = URL_C +"/insertar_radicado.php";
    public static final String RESPUESTAS_UNIDADES = URL_C +"/RespuestasUnidades.php";
    public static final String RESPUESTAS_UNIDADES2 = URL_C +"/RespuestasUnidades.php";

    //NOTIFICACIONES
    public static final String GET_NOTIFICACIONES = "https://ptcazz.000webhostapp.com/cristian/push_notification.php";

}
