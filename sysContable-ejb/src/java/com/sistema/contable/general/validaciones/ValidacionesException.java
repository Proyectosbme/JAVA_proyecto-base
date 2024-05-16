/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.general.validaciones;

import java.util.List;

/**
 *
 * @author BME_PERSONAL
 */
public class ValidacionesException extends Exception {

    private List<String> mensajes;
    private String mensaje;

    public ValidacionesException(String error) {
        super(error);
    }

    public ValidacionesException(String error, String mensajes) {
        super(error);
        this.mensaje = mensajes;
    }

    public ValidacionesException(String error, List<String> mensajes) {
        super(error);
        this.mensajes = mensajes;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public String getMensaje() {
        return mensaje;
    }

}
