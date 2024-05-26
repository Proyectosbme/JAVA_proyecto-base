/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RESolutions.
 */
public class ImpRpts extends HttpServlet {

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

        String ds = (String) request.getSession().getAttribute("ds");
        String url = (String) request.getSession().getAttribute("url");
        String format = (String) request.getSession().getAttribute("format");
        Map parameters = (Map) request.getSession().getAttribute("parameters");

        if (ds == null || ds.equals("")) {
            PrintWriter out = response.getWriter();
            out.println("<h2>El parametro del Data Source no es valida</h2>");
            out.close();
            return;
        }

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

        if (parameters == null) {
            PrintWriter out = response.getWriter();
            out.println("<h2>Los parametro del reporte no son validos </h2>");
            out.close();
            return;
        }
        try {
            if (format.equals("PDF")) {
                reporteByte = imprpt.ImprimeReportePDF(ds, url, parameters);
                response.setContentType("application/pdf");
                response.setContentLength(reporteByte.length);
                response.getOutputStream().write(reporteByte);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
            if (format.equals("DOCX")) {
                String rptdocx = "";
                rptdocx = imprpt.ImprimeReporteDOCX(ds, url, parameters);
                response.reset();
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + "rpt.docx" + "\"");
                InputStream in = null;
                ServletOutputStream bufferSalida = null;
                in = new FileInputStream(rptdocx);
                bufferSalida = response.getOutputStream();
                //se transfieren los bytes
                byte[] buf = new byte[256];//256
                int len;
                while ((len = in.read(buf)) > 0) {
                    bufferSalida.write(buf, 0, len);
                }
                //vaciamos el buffer de salida y se envia el resultado
                in.close();
                bufferSalida.flush();
                bufferSalida.close();
            }
            if (format.equals("XLS")) {
                String rptxls = "";
                rptxls = imprpt.ImprimeReporteXLS(ds, url, parameters);
                response.reset();
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + "rpt.xls" + "\"");
                InputStream in = null;
                ServletOutputStream bufferSalida = null;
                in = new FileInputStream(rptxls);
                bufferSalida = response.getOutputStream();
                //se transfieren los bytes
                byte[] buf = new byte[256];//256
                int len;
                while ((len = in.read(buf)) > 0) {
                    bufferSalida.write(buf, 0, len);
                }
                //vaciamos el buffer de salida y se envia el resultado
                in.close();
                bufferSalida.flush();
                bufferSalida.close();
            }
            if (format.equals("XLSX")) {
                String rptxls = "";
                rptxls = imprpt.ImprimeReporteXLSX(ds, url, parameters);
                response.reset();
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + "rpt.xlsx" + "\"");
                InputStream in = null;
                ServletOutputStream bufferSalida = null;
                in = new FileInputStream(rptxls);
                bufferSalida = response.getOutputStream();
                //se transfieren los bytes
                byte[] buf = new byte[256];//256
                int len;
                while ((len = in.read(buf)) > 0) {
                    bufferSalida.write(buf, 0, len);
                }
                //vaciamos el buffer de salida y se envia el resultado
                in.close();
                bufferSalida.flush();
                bufferSalida.close();
            }
            if (format.equals("CSV")) {
                String rptcsv = "";
                rptcsv = imprpt.ImprimeReporteCSV(ds, url, parameters);
                response.reset();
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + "rpt.csv" + "\"");
                InputStream in = null;
                ServletOutputStream bufferSalida = null;
                in = new FileInputStream(rptcsv);
                bufferSalida = response.getOutputStream();
                //se transfieren los bytes
                byte[] buf = new byte[256];//256
                int len;
                while ((len = in.read(buf)) > 0) {
                    bufferSalida.write(buf, 0, len);
                }
                //vaciamos el buffer de salida y se envia el resultado
                in.close();
                bufferSalida.flush();
                bufferSalida.close();
            }
            if (format.equals("Bandesal")) {
                String op = "";
                String texto = "";
                if (request.getSession().getAttribute("op") != null) {
                    op = (String) request.getSession().getAttribute("op");
                }
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                String rptxls = "";
                rptxls = imprpt.ImprimeReporteXLS(ds, url, parameters);
                response.reset();
                String fechaString;
                if ((Integer)Tiempo.getMes(new Date()) < 10) {
                    fechaString = "0" + Tiempo.getMes(new Date());
                    if (c.get(Calendar.DAY_OF_MONTH) < 10) {
                        fechaString = fechaString + "0" + c.get(Calendar.DAY_OF_MONTH);
                    } else {
                        fechaString = fechaString + c.get(Calendar.DAY_OF_MONTH);
                    }
                } else {
                    fechaString = "" + Tiempo.getMes(new Date());
                    if (c.get(Calendar.DAY_OF_MONTH) < 10) {
                        fechaString = fechaString + "0" + c.get(Calendar.DAY_OF_MONTH);
                    } else {
                        fechaString = fechaString + c.get(Calendar.DAY_OF_MONTH);
                    }
                }
                if (op.equals("Inscripcion")) {
                    response.setHeader("Content-Disposition",
                            "attachment; filename=\"" + "NvasGar_"
                            + Tiempo.getAnio(new Date())
                            + fechaString + ".xls" + "\"");
                }
                if (op.equals("Actualizacion")) {
                    response.setHeader("Content-Disposition",
                            "attachment; filename=\"" + "Actsal_"
                            + Tiempo.getAnio(new Date())
                            + fechaString + ".xls" + "\"");
                }
                if (op.equals("Renovacion")) {
                    response.setHeader("Content-Disposition",
                            "attachment; filename=\"" + "Ren_"
                            + Tiempo.getAnio(new Date())
                            + fechaString + ".xls" + "\"");
                }
                InputStream in = null;
                ServletOutputStream bufferSalida = null;
                in = new FileInputStream(rptxls);
                bufferSalida = response.getOutputStream();
                //se transfieren los bytes
                byte[] buf = new byte[256];//256
                int len;
                while ((len = in.read(buf)) > 0) {
                    bufferSalida.write(buf, 0, len);
                }
                //vaciamos el buffer de salida y se envia el resultado
                in.close();
                bufferSalida.flush();
                bufferSalida.close();
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
