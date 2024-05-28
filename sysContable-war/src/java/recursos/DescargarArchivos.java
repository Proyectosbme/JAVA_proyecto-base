package recursos;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet para descargar archivos.
 * @author BME_PERSONAL
 */
public class DescargarArchivos extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(DescargarArchivos.class.getName());
    
    /**
     * Procesa las solicitudes HTTP GET y POST.
     *
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtiene los parámetros de sesión necesarios
        String url = (String) request.getSession().getAttribute("url");
        String format = (String) request.getSession().getAttribute("format");
        Map<String, Object> parameters = (Map<String, Object>) request.getSession().getAttribute("parameters");

        // Verifica que la URL del reporte sea válida
        if (url == null || url.isEmpty()) {
            sendErrorMessage(response, "El parámetro URL del reporte no es válido");
            return;
        }

        // Verifica que el formato del reporte sea especificado
        if (format == null || format.isEmpty()) {
            sendErrorMessage(response, "El formato del reporte no fue especificado");
            return;
        }

        try {
            URI uriObj = getClass().getResource(url).toURI();
            if ("PDF".equalsIgnoreCase(format)) {
                byte[] reporteByte = new Archivo().openFile(uriObj.getPath());
                sendPDFResponse(response, reporteByte);
            } else {
                sendErrorMessage(response, "Formato no soportado: " + format);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            sendErrorMessage(response, "Error: " + ex.getMessage());
        }
    }

    /**
     * Envía un mensaje de error al cliente.
     *
     * @param response respuesta del servlet
     * @param message mensaje de error
     * @throws IOException si ocurre un error de entrada/salida
     */
    private void sendErrorMessage(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<h2>" + message + "</h2>");
        }
    }

    /**
     * Envía un archivo PDF en la respuesta.
     *
     * @param response respuesta del servlet
     * @param reporteByte contenido del archivo PDF
     * @throws IOException si ocurre un error de entrada/salida
     */
    private void sendPDFResponse(HttpServletResponse response, byte[] reporteByte) throws IOException {
        response.setContentType("application/pdf");
        response.setContentLength(reporteByte.length);
        response.getOutputStream().write(reporteByte);
        response.getOutputStream().flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para descargar archivos.";
    }
}
