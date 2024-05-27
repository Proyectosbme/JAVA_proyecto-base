/*
 * Esta clase maneja las solicitudes para imprimir reportes en diferentes formatos.
 * Los formatos admitidos son PDF, DOCX, XLS, XLSX y CSV.
 */
package recursos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImpresionReporteServlet
 */
public class ImpresionReporteServlet extends HttpServlet {
// Declaración de variables de instancia

    /**
     * 
     */
    private String ds;
    /**
     * 
     */
    private String url;
    private String format;
    private Map<String, String> parameters;

    protected void extraerParametros(HttpServletRequest request) {
        // Extrae los parámetros de la sesión
        this.ds = (String) request.getSession().getAttribute("ds");
        this.url = (String) request.getSession().getAttribute("url");
        this.format = (String) request.getSession().getAttribute("format");
        this.parameters = (Map<String, String>) request.getSession().getAttribute("parameters");
    }

    /**
     * Procesa las solicitudes GET y POST.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Inicializa el objeto para la impresión de reportes
        ImpresionRpt imprpt = new ImpresionRpt();
        byte[] reporteByte = null;

        // Extrae y valida los parámetros
        extraerParametros(request);
        if (!validarParametros(response)) {
            return;
        }

        try {
            // Procesa la solicitud dependiendo del formato del reporte
            switch (format) {
                case "PDF":
                    reporteByte = imprpt.ImprimeReportePDF(ds, url, parameters);
                    response.setContentType("application/pdf");
                    break;
                case "DOCX":
                    reporteByte = imprpt.ImprimeReporteDOCX(ds, url, parameters);
                    response.setHeader("Content-Disposition", "attachment; filename=\"rpt.docx\"");
                    break;
                case "XLS":
                case "XLSX":
                case "CSV":
                    reporteByte = imprpt.ImprimeReporteXLS(ds, url, parameters);
                    response.setHeader("Content-Disposition", "attachment; filename=\"rpt." + format.toLowerCase() + "\"");
                    break;
                default:
                    // Formato no compatible
                    PrintWriter out = response.getWriter();
                    out.println("Formato de reporte no admitido");
                    out.close();
                    return;
            }

            // Envía el reporte al cliente
            response.setContentLength(reporteByte.length);
            response.getOutputStream().write(reporteByte);
            response.getOutputStream().flush();
            response.getOutputStream().close();

        } catch (NamingException | SQLException ex) {
            // Registra el error en el registro de errores
            Logger.getLogger(ImpresionReporteServlet.class.getName()).log(Level.SEVERE, "Error de base de datos", ex);
            // Devuelve un mensaje de error al cliente
            PrintWriter out = response.getWriter();
            out.println("Se produjo un error en la base de datos: " + ex.getMessage());
            out.close();

        } catch (Exception ex) {
            // Registra el error en el registro de errores
            Logger.getLogger(ImpresionReporteServlet.class.getName()).log(Level.SEVERE, "Error general", ex);
            // Devuelve un mensaje de error al cliente
            PrintWriter out = response.getWriter();
            out.println("Se produjo un error: " + ex.getMessage());
            out.close();
        }
    }

    // Métodos doGet, doPost y getServletInfo proporcionados por HttpServlet
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para imprimir reportes en diferentes formatos";
    }

    protected boolean validarParametros(HttpServletResponse response) throws ServletException, IOException {
        // Se realizan comprobaciones de validez de los parámetros
        if (ds == null || ds.isEmpty() || url == null || url.isEmpty() || format == null || format.isEmpty() || parameters == null) {
            PrintWriter out = response.getWriter();
            out.println("Parámetros de reporte inválidos");
            out.close();
            return false;
        }
        return true;
    }
}
