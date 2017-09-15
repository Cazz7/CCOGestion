package com.cco.cristiancarlosjohn.ccogestion.Model;

/**
 * Created by cristian.zapata on 15/09/2017.
 */

 public class DatosUsuario {

    private static String usuario;
    private static String password;
    private static String perfil;

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String usuario) {
        DatosUsuario.usuario = usuario;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DatosUsuario.password = password;
    }

    public static String getPerfil() {
        return perfil;
    }

    public static void setPerfil(String perfil) {
        DatosUsuario.perfil = perfil;
    }
}
