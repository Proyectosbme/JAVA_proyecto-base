/*
 * Clase ValidacionMensajes
 * Esta clase proporciona métodos para manejar y mostrar mensajes de error en un contexto de aplicación web.
 */
package com.sistema.gen.utilidades;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

public class ValidacionMensajes {

    // Lista para almacenar los mensajes a mostrar
    private List<FacesMessage> messages = new ArrayList<>();
    /**
     * Variables para manejar los logs en consola
     */
      private static Logger LOGGER;

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
    }

   /**
     * Método mostrarErrorDetallado
     * Muestra un mensaje de error detallado con la información, el tipo de error y el mensaje especificados.
    * @param informacion informacion a mostrar
    * @param tipoError tipo de error 
    * @param mesjError  mensaje para descripcion del error
    */
    private void mostrarErrorDetallado(String informacion, String tipoError, String mesjError) {
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
        StackTraceElement[] stackTrace = ex.getStackTrace();
        String claseError = stackTrace.length > 0 ? stackTrace[0].getClassName() : "Clase no disponible";

        // Obtener la clase desde donde se llamó el método manejarExcepcion
        String claseLlamadora = Thread.currentThread().getStackTrace()[2].getClassName();
        Logger logger = Logger.getLogger(claseLlamadora);

        // Mostrar un mensaje de error detallado
        mostrarErrorDetallado(informacion, tipoError, mensajeError);

        // Loguear el error
        logger.log(Level.SEVERE, "Información adicional: " + informacion);
        logger.log(Level.SEVERE, "Tipo de error: " + tipoError);
        logger.log(Level.SEVERE, "Mensaje de error: " + mensajeError);
        logger.log(Level.SEVERE, "Clase donde ocurrió el error: " + claseError);
        ex.printStackTrace();
    }

    public List<FacesMessage> getMessages() {
        return messages;
    }
    
    
}
