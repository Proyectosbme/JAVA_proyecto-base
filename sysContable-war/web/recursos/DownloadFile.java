/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author rodolfo.retana
 */
public class DownloadFile extends HttpServlet{
   /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
        ImpresionRpt imprpt = new ImpresionRpt();
        byte[] reporteByte = null;

        String url = (String) request.getSession().getAttribute("url");
        String format = (String) request.getSession().getAttribute("format");
        Map parameters = (Map) request.getSession().getAttribute("parameters");

    

        if (url == null || url.equals("")) {
            PrintWriter out = response.getWriter();
            out.println("<h2>El parametro URL del reporte no es valida </h2>");
            out.close();
            return;
        }

        if (format == null || format.equals("")) {
            PrintWriter out = response.getWriter();
            out.println("<h2>El formato del reporte no fue especificado</h2>");
            out.close();
            return;
        }
        
        try {
            URI uriObj = getClass().getResource(url).toURI();
            if (format.equals("PDF")) {
                System.out.println("URL: " + uriObj.getPath());
                Archivo file= new Archivo();
                reporteByte = file.openFile(uriObj.getPath());
                response.setContentType("application/pdf");
                response.setContentLength(reporteByte.length);
                response.getOutputStream().write(reporteByte);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
         
        } catch (Exception ex) {
            Logger.getLogger(ImpRpts.class.getName())
                    .log(Level.SEVERE, null, ex);
            PrintWriter out = response.getWriter();
            out.println("<H2> Error: " + ex.getMessage() + "</H2>");
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold> 
}
