/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.general.validaciones;

import java.util.List;

/**
 * Excepción lanzada para manejar errores de validación en el sistema contable.
 * @author BME_PERSONAL
 */
public class ValidacionesException extends Exception {

    /**
     * Lista de mensajes al usuario final
     */
    private List<String> mensajes;
    /**
     * Mensaje general para el usuario final
     */
    private String mensaje;

    /**
     * Metodo que obtiene el mensaje y lo manda a la clase padre
     * @param error tipo de error para el mensaje
     */
    public ValidacionesException(String error) {
        super(error);
    }

    /**
     * Obtiene el error y mensaje al usairo
     * @param error tipo de error
     * @param mensajes mensjae
     */
    public ValidacionesException(String error, String mensajes) {
        super(error);
        this.mensaje = mensajes;
    }

    /**
     * obtiene el error y una lista de mensajes 
     * @param error tipo de error
     * @param mensajes lista de mensajes
     */
    public ValidacionesException(String error, List<String> mensajes) {
        super(error);
        this.mensajes = mensajes;
    }

    /**
     * Retorna los msj
     * @return 
     */
    public List<String> getMensajes() {
        return mensajes;
    }

    /**
     * Retona la lista de msjs
     * @return 
     */
    public String getMensaje() {
        return mensaje;
    }

}
