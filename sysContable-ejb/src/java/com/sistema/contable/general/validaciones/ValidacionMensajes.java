/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.general.validaciones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

public class ValidacionMensajes {

    private List<FacesMessage> messages = new ArrayList<>();

    public enum Severidad {
        INFO,
        WARN,
        FATAL,
        ERROR
    }

    public void mostrarMsj() {//dlg1
        PrimeFaces.current().executeScript("PF('dlgMensajes').show();");
        FacesContext context = FacesContext.getCurrentInstance();
        messages.forEach((message) -> {
            context.addMessage(null, message);
        });
        messages.clear();
    }

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
            messages.add(facesMessage);
        }
        mostrarMsj();
    }

    public void mostrarErrorDetallado(String informacion, String tipoError, String mesjError) {
        String mensajeDetallado;
        if (mesjError != null && !mesjError.isEmpty()) {
            mensajeDetallado = String.format("[%s] %s: %s",tipoError,informacion, mesjError);
        } else {
            mensajeDetallado = String.format("[%s] %s", tipoError,informacion);
        }
        agregarMsj(Severidad.ERROR, mensajeDetallado);
        mostrarMsj();
    }

    public void manejarExcepcion(Throwable ex, String informacion) {
        String tipoError = ex.getClass().getSimpleName();
        String mensajeError = ex.getMessage();
//        StackTraceElement[] stackTrace = ex.getStackTrace();
//        if (stackTrace.length > 0) {
//            StackTraceElement elemento = stackTrace[0];
//            String clase = elemento.getClassName();
//            String metodo = elemento.getMethodName();
//          //  String archivo = elemento.getFileName();
//            int linea = elemento.getLineNumber();
//
//            agregarMsj(Severidad.INFO, "Clase: " + clase);
//            agregarMsj(Severidad.INFO, "Método: " + metodo);
//         //   agregarMsj(Severidad.FATAL, "Archivo: " + archivo);
//            agregarMsj(Severidad.INFO, "Línea: " + linea);
//        }
        mostrarErrorDetallado(informacion, tipoError, mensajeError);
    }
}
