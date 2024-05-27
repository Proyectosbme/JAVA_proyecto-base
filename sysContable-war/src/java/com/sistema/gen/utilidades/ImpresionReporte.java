/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.gen.utilidades;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;

/**
 *
 * @author BME_PERSONAL
 */
public class ImpresionReporte {

    private static final ValidacionMensajes validar = new ValidacionMensajes();

    public static void imprimirReporte(String nombreReporte, String urlReporte, Map<String, Object> parametros, String formato) {
        try {
            // Validar que los parámetros no sean nulos
            if (nombreReporte == null || urlReporte == null || parametros == null || formato == null) {
                throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
            }

            // Validar que los parámetros no estén vacíos
            if (nombreReporte.isEmpty() || urlReporte.isEmpty() || parametros.isEmpty() || formato.isEmpty()) {
                throw new IllegalArgumentException("Los parámetros no pueden estar vacíos");
            }

            // Obtener el contexto de Faces y la solicitud HTTP
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

            // Configurar los atributos de sesión necesarios para el servlet
            String servletUrl = request.getContextPath() + "/ImpresionReporteServlet";
            request.getSession().setAttribute("ds", "jdbc/_contabilidad");
            request.getSession().setAttribute("url", urlReporte + nombreReporte+".jasper");
            request.getSession().setAttribute("parameters", parametros);
            request.getSession().setAttribute("format", formato.toUpperCase());

            // Generar el código JavaScript para abrir una nueva ventana del navegador con el reporte
            String javascriptCode = String.format(
                    "window.open('%s','Rpt','location=0,menubar=0,resizable=1,status=0,toolbar=0');",
                    servletUrl
            );

            // Ejecutar el código JavaScript usando PrimeFaces
            PrimeFaces.current().executeScript(javascriptCode);
        } catch (IllegalArgumentException ex) {
            // Si se lanzó una excepción de argumento ilegal, registrarla en el log
            validar.manejarExcepcion(ex, "Error por elementos incorrectos");

        } catch (Exception ex) {
            // Imprimir la traza de la excepción para el diagnóstico de errores
            validar.manejarExcepcion(ex, "Error inesperado");
        }
    }

    public static ValidacionMensajes getValidar() {
        return validar;
    }

}
