/*
 * Clase ValidacionMensajes
 * Esta clase proporciona métodos para manejar y mostrar mensajes de error en un contexto de aplicación web.
 */
package com.sistema.contable.general.validaciones;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

public class ValidacionMensajes {

    // Lista para almacenar los mensajes a mostrar
    private List<FacesMessage> messages = new ArrayList<>();

    /*
     * Enumeración Severidad
     * Enumera los distintos niveles de severidad para los mensajes.
     */
    public enum Severidad {
        INFO, // Mensaje informativo
        WARN, // Advertencia
        FATAL, // Error fatal
        ERROR   // Error
    }

    /**
     *  * Método mostrarMsj Muestra los mensajes almacenados en la lista
     * messages.
     */
    public void mostrarMsj() {
        PrimeFaces.current().executeScript("PF('dlgMensajes').show();"); // Muestra el diálogo de mensajes
        FacesContext context = FacesContext.getCurrentInstance();
        messages.forEach((message) -> {
            context.addMessage(null, message); // Agrega los mensajes al contexto de Faces
        });
        messages.clear(); // Limpia la lista de mensajes después de mostrarlos
    }

    /**
     *  *
     * Método agregarMsj Agrega un nuevo mensaje a la lista messages con la
     * severidad y el mensaje especificados.
     *
     * @param severidad severidad del mensaje
     * @param msj mensaje a mostrar
     */
    public void agregarMsj(Severidad severidad, String msj) {
        FacesMessage facesMessage = null;
        switch (severidad) {
            case INFO:
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, null, msj);
                break;
            case WARN:
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, null, msj);
                break;
            case FATAL:
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, null, msj);
                break;
            case ERROR:
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msj);
                break;
        }
        if (facesMessage != null) {
            messages.add(facesMessage); // Agrega el mensaje a la lista de mensajes
        }
        mostrarMsj(); // Muestra los mensajes después de agregarlos
    }

   /**
     * Método mostrarErrorDetallado
     * Muestra un mensaje de error detallado con la información, el tipo de error y el mensaje especificados.
    * @param informacion informacion a mostrar
    * @param tipoError tipo de error 
    * @param mesjError  mensaje para descripcion del error
    */
    public void mostrarErrorDetallado(String informacion, String tipoError, String mesjError) {
        String mensajeDetallado;
        if (mesjError != null && !mesjError.isEmpty()) {
            mensajeDetallado = String.format("[%s] %s: %s", tipoError, informacion, mesjError);
        } else {
            mensajeDetallado = String.format("[%s] %s", tipoError, informacion);
        }
        agregarMsj(Severidad.ERROR, mensajeDetallado); // Agrega y muestra el mensaje de error detallado
        mostrarMsj(); // Muestra los mensajes después de agregarlos
    }


    /**
     * Método manejarExcepcion
     * Maneja una excepción proporcionada, obteniendo su tipo de error y mensaje, y mostrando un mensaje de error detallado.
     * @param ex error enviado
     * @param informacion informacion para mostrar con el error
     */
    public void manejarExcepcion(Throwable ex, String informacion) {
        String tipoError = ex.getClass().getSimpleName(); // Obtiene el tipo de error de la excepción
        String mensajeError = ex.getMessage(); // Obtiene el mensaje de error de la excepción
        mostrarErrorDetallado(informacion, tipoError, mensajeError); // Muestra un mensaje de error detallado
    }
}
