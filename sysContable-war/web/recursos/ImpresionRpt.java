/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author RESolutions
 */
public class ImpresionRpt {

    @SuppressWarnings("CallToThreadDumpStack")
    public String ImprimeReporteXLS(String datSource, String url,
            Map parameters) throws IOException, NamingException,
            SQLException, Exception {
        Connection connection = null;
        String archJasper;
        URI uriObj = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource ds = (DataSource) initialContext.lookup(datSource);
            connection = ds.getConnection();
            uriObj = getClass().getResource(url).toURI();
            File reporte = new File(uriObj);
            archJasper = reporte.getAbsolutePath();
            JasperPrint jasperPrint = null;

            jasperPrint = JasperFillManager
                    .fillReport(archJasper, parameters, connection);
            String xlsPath = archJasper;
            String xlsFileName = "";
            xlsFileName = "rpt.xls";

            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                    xlsPath + xlsFileName);
            exporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, true);
            //exporter.setParameter(JRXlsAbstractExporterParameter
            //.IS_WHITE_PAGE_BACKGROUND, false);
            exporter.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BORDER, false);
            exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
            exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
            exporter.exportReport();
            connection.close();
            return xlsPath + xlsFileName;
        } catch (NamingException ne) {
            Logger.getLogger(ImpresionRpt.class.getName()).log(Level.SEVERE,
                    null, ne);
            ne.printStackTrace();
            throw new Exception("Error al accededer al DataSource "
                    + ne.getMessage());
        } catch (SQLException sqle) {
            Logger.getLogger(ImpresionRpt.class.getName()).log(Level.SEVERE,
                    null, sqle);
            sqle.printStackTrace();
            throw new Exception("SQL Exception " + sqle.getMessage());
        } catch (JRException ex) {
            Logger.getLogger(ImpresionRpt.class.getName())
                    .log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            throw new Exception("JRException " + ex.getMessage());
        } catch (Exception e) {
            Logger.getLogger(ImpresionRpt.class.getName())
                    .log(Level.SEVERE, null, e);
            e.printStackTrace();
            throw new Exception("Exception " + e.getMessage());
        } finally {
            connection.close();
        }
    }

    public String ImprimeReporteDOCX(String datSource, String url,
            Map parameters) throws IOException, NamingException,
            SQLException, Exception {
        Connection connection = null;
        String archJasper;
        URI uriObj = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource ds = (DataSource) initialContext.lookup(datSource);
            connection = ds.getConnection();
            uriObj = getClass().getResource(url).toURI();
            File reporte = new File(uriObj);
            archJasper = reporte.getAbsolutePath();
            JasperPrint jasperPrint = null;

            jasperPrint = JasperFillManager
                    .fillReport(archJasper, parameters, connection);
            String docxPath = archJasper;
            String docxFileName = "";
            docxFileName = "rpt.docx";
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                    docxPath + docxFileName);
            exporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, true);
            //exporter.setParameter(JRXlsAbstractExporterParameter
            //.IS_WHITE_PAGE_BACKGROUND, false);
            exporter.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BORDER, false);
            exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
            exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
            exporter.exportReport();
            connection.close();
            return docxPath + docxFileName;
        } catch (NamingException ne) {
            Logger.getLogger(ImpresionRpt.class.getName()).log(Level.SEVERE,
                    null, ne);
            ne.printStackTrace();
            throw new Exception("Error al accededer al DataSource "
                    + ne.getMessage());
        } catch (SQLException sqle) {
            Logger.getLogger(ImpresionRpt.class.getName()).log(Level.SEVERE,
                    null, sqle);
            sqle.printStackTrace();
            throw new Exception("SQL Exception " + sqle.getMessage());
        } catch (JRException ex) {
            Logger.getLogger(ImpresionRpt.class.getName())
                    .log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            throw new Exception("JRException " + ex.getMessage());
        } catch (Exception e) {
            Logger.getLogger(ImpresionRpt.class.getName())
                    .log(Level.SEVERE, null, e);
            e.printStackTrace();
            throw new Exception("Exception " + e.getMessage());
        } finally {
            connection.close();
        }
    }

    /**
     * Metodo que ejecuta un reporte y lo exporta en pdf.
     *
     * @param datSource String nombre del DataSource.
     * @param url Direccion uri donde esta el jsaper.
     * @param parameters map de los parametros del reporte.
     * @throws java.lang.Exception error general.
     */
    public byte[] ImprimeReportePDF(String datSource, String url,
            Map parameters) throws IOException, NamingException,
            SQLException, Exception {
        Connection connection = null;
        String archJasper;
        URI uriObj = null;
        byte[] reporteByte = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource ds = (DataSource) initialContext.lookup(datSource);
            connection = ds.getConnection();
            uriObj = getClass().getResource(url).toURI();
            File reporte = new File(uriObj);
            archJasper = reporte.getAbsolutePath();
            JasperPrint impresion = null;
            JRExporter exporter = new JRPdfExporter();
            ByteArrayOutputStream reportePDF = new ByteArrayOutputStream();
            reporteByte = JasperRunManager.runReportToPdf(reporte.getPath(),
                    parameters, connection);
            impresion =
                    JasperFillManager.fillReport(archJasper, parameters, connection);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, impresion);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, reportePDF);
            connection.close();
        } catch (NamingException ne) {
            Logger.getLogger(ImpresionRpt.class.getName()).log(Level.SEVERE,
                    null, ne);
            ne.printStackTrace();
            throw new Exception("Error al accededer al DataSource "
                    + ne.getMessage());
        } catch (SQLException sqle) {
            Logger.getLogger(ImpresionRpt.class.getName()).log(Level.SEVERE,
                    null, sqle);
            sqle.printStackTrace();
            throw new Exception("SQL Exception " + sqle.getMessage());
        } catch (JRException ex) {
            Logger.getLogger(ImpresionRpt.class.getName())
                    .log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            throw new Exception("JRException " + ex.getMessage());
        } catch (Exception e) {
            Logger.getLogger(ImpresionRpt.class.getName())
                    .log(Level.SEVERE, null, e);
            e.printStackTrace();
            throw new Exception("Exception " + e.getMessage());
        } finally {
            connection.close();
        }
        return reporteByte;
    }

    /**
     * Metodo que ejecuta un reporte y lo exporta en pdf.
     *
     * @param datSource String nombre del DataSource.
     * @param url Direccion uri donde esta el jsaper.
     * @param parameters map de los parametros del reporte.
     * @throws java.lang.Exception error general.
     */
    public byte[] GuardarReportePDF(String datSource, String url,
            Map parameters) throws IOException, NamingException,
            SQLException, Exception {
        Connection connection = null;
        URI uriObj = null;
        byte[] reporteByte = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource ds = (DataSource) initialContext.lookup(datSource);
            connection = ds.getConnection();
            uriObj = getClass().getResource(url).toURI();
            File reporte = new File(uriObj);
            reporteByte = JasperRunManager.runReportToPdf(reporte.getPath(),
                    parameters, connection);
            connection.close();
        } catch (NamingException ne) {
            Logger.getLogger(ImpresionRpt.class.getName()).log(Level.SEVERE,
                    null, ne);
            ne.printStackTrace();
            throw new Exception("Error al accededer al DataSource "
                    + ne.getMessage());
        } catch (SQLException sqle) {
            Logger.getLogger(ImpresionRpt.class.getName()).log(Level.SEVERE,
                    null, sqle);
            sqle.printStackTrace();
            throw new Exception("SQL Exception " + sqle.getMessage());
        } catch (JRException ex) {
            Logger.getLogger(ImpresionRpt.class.getName())
                    .log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            throw new Exception("JRException " + ex.getMessage());
        } catch (Exception e) {
            Logger.getLogger(ImpresionRpt.class.getName())
                    .log(Level.SEVERE, null, e);
            e.printStackTrace();
            throw new Exception("Exception " + e.getMessage());
        } finally {
            connection.close();
        }
        return reporteByte;
    }

    public String ImprimeReporteCSV(String datSource, String url,
            Map parameters) throws IOException, NamingException,
            SQLException, Exception {
        Connection connection = null;
        String archJasper;
        URI uriObj = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource ds = (DataSource) initialContext.lookup(datSource);
            connection = ds.getConnection();
            uriObj = getClass().getResource(url).toURI();
            File reporte = new File(uriObj);
            archJasper = reporte.getAbsolutePath();
            JasperPrint jasperPrint = null;

            jasperPrint = JasperFillManager
                    .fillReport(archJasper, parameters, connection);
            String csvPath = archJasper;
            String csvFileName = "";
            csvFileName = "rpt.csv";

            JRCsvExporter exporter = new JRCsvExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                    csvPath + csvFileName);
            exporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, true);
            /*HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.setContentType("application/csv; charset=cp1252");
            httpServletResponse.setCharacterEncoding("cp1252");
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=nameoffile.csv");
            httpServletResponse.addHeader("Content-type", "application/csv; charset=" + Charset.forName("utf-8").displayName());
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
*/
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "cp1252");
            //exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            exporter.setParameter(JRCsvExporterParameter.CHARACTER_ENCODING, "cp1252");
            exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, ",");
            //exporter.setParameter(JRXlsAbstractExporterParameter
            //.IS_WHITE_PAGE_BACKGROUND, false);
           /* exporter.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BORDER, false);
             exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
             exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);*/
             exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, false);
            exporter.exportReport();
            connection.close();
            return csvPath + csvFileName;
        } catch (NamingException ne) {
            Logger.getLogger(ImpresionRpt.class.getName()).log(Level.SEVERE,
                    null, ne);
            ne.printStackTrace();
            throw new Exception("Error al accededer al DataSource "
                    + ne.getMessage());
        } catch (SQLException sqle) {
            Logger.getLogger(ImpresionRpt.class.getName()).log(Level.SEVERE,
                    null, sqle);
            sqle.printStackTrace();
            throw new Exception("SQL Exception " + sqle.getMessage());
        } catch (JRException ex) {
            Logger.getLogger(ImpresionRpt.class.getName())
                    .log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            throw new Exception("JRException " + ex.getMessage());
        } catch (Exception e) {
            Logger.getLogger(ImpresionRpt.class.getName())
                    .log(Level.SEVERE, null, e);
            e.printStackTrace();
            throw new Exception("Exception " + e.getMessage());
        } finally {
            connection.close();
        }
    }

    public String ImprimeReporteXLSX(String datSource, String url,
            Map parameters) throws IOException, NamingException,
            SQLException, Exception {
        Connection connection = null;
        String archJasper;
        URI uriObj = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource ds = (DataSource) initialContext.lookup(datSource);
            connection = ds.getConnection();
            uriObj = getClass().getResource(url).toURI();
            File reporte = new File(uriObj);
            archJasper = reporte.getAbsolutePath();
            JasperPrint jasperPrint = null;

            jasperPrint = JasperFillManager
                    .fillReport(archJasper, parameters, connection);
            String xlsPath = archJasper;
            String xlsFileName = "";
            xlsFileName = "rpt.xlsx";

            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                    xlsPath + xlsFileName);
            exporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, true);
            //exporter.setParameter(JRXlsAbstractExporterParameter
            //.IS_WHITE_PAGE_BACKGROUND, false);
            exporter.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BORDER, false);
            exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
            exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
            exporter.exportReport();
            connection.close();
            return xlsPath + xlsFileName;
        } catch (NamingException ne) {
            Logger.getLogger(ImpresionRpt.class.getName()).log(Level.SEVERE,
                    null, ne);
            ne.printStackTrace();
            throw new Exception("Error al accededer al DataSource "
                    + ne.getMessage());
        } catch (SQLException sqle) {
            Logger.getLogger(ImpresionRpt.class.getName()).log(Level.SEVERE,
                    null, sqle);
            sqle.printStackTrace();
            throw new Exception("SQL Exception " + sqle.getMessage());
        } catch (JRException ex) {
            Logger.getLogger(ImpresionRpt.class.getName())
                    .log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            throw new Exception("JRException " + ex.getMessage());
        } catch (Exception e) {
            Logger.getLogger(ImpresionRpt.class.getName())
                    .log(Level.SEVERE, null, e);
            e.printStackTrace();
            throw new Exception("Exception " + e.getMessage());
        } finally {
            connection.close();
        }
    }
}
