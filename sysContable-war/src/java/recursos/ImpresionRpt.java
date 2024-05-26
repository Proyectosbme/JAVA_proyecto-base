package recursos;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;

public class ImpresionRpt {

    
    /**
     * Imprime y exporta un informe en formato XLS.
     * 
     * @param datSource nombre del DataSource.
     * @param url dirección URI donde se encuentra el archivo .jasper.
     * @param parameters parámetros del informe.
     * @return array de bytes del informe exportado.
     * @throws IOException si hay un error de E/S.
     * @throws NamingException si hay un error de acceso al contexto de nombres.
     * @throws SQLException si hay un error de acceso a la base de datos.
     * @throws JRException si hay un error durante la generación del informe.
     * @throws URISyntaxException si hay un error de sintaxis en la URI.
     */
    public byte[] ImprimeReporteXLS(String datSource, String url, Map parameters)
            throws IOException, NamingException, SQLException, JRException, URISyntaxException {
        try (Connection connection = getConnection(datSource);) {
            JasperPrint jasperPrint = fillReport(url, parameters, connection);
            return exportReportToXLS(jasperPrint);
        }
    }

       /**
     * Imprime y exporta un informe en formato DOCX.
     * 
     * @param datSource nombre del DataSource.
     * @param url dirección URI donde se encuentra el archivo .jasper.
     * @param parameters parámetros del informe.
     * @return array de bytes del informe exportado.
     * @throws IOException si hay un error de E/S.
     * @throws NamingException si hay un error de acceso al contexto de nombres.
     * @throws SQLException si hay un error de acceso a la base de datos.
     * @throws JRException si hay un error durante la generación del informe.
     * @throws URISyntaxException si hay un error de sintaxis en la URI.
     */
    public byte[] ImprimeReporteDOCX(String datSource, String url, Map parameters)
            throws IOException, NamingException, SQLException, JRException, URISyntaxException {
        try (Connection connection = getConnection(datSource);) {
            JasperPrint jasperPrint = fillReport(url, parameters, connection);
            return exportReportToDOCX(jasperPrint);
        }
    }

      /**
     * Imprime y exporta un informe en formato PDF.
     * 
     * @param datSource nombre del DataSource.
     * @param url dirección URI donde se encuentra el archivo .jasper.
     * @param parameters parámetros del informe.
     * @return array de bytes del informe exportado.
     * @throws IOException si hay un error de E/S.
     * @throws NamingException si hay un error de acceso al contexto de nombres.
     * @throws SQLException si hay un error de acceso a la base de datos.
     * @throws JRException si hay un error durante la generación del informe.
     * @throws URISyntaxException si hay un error de sintaxis en la URI.
     */
    public byte[] ImprimeReportePDF(String datSource, String url, Map parameters)
            throws IOException, NamingException, SQLException, JRException, URISyntaxException {
        try (Connection connection = getConnection(datSource);) {
            JasperPrint jasperPrint = fillReport(url, parameters, connection);
            return exportReportToPDF(jasperPrint);
        }
    }

    // Métodos auxiliares privados
    /**
     * Obtiene una conexión a la base de datos utilizando el DataSource
     * especificado.
     *
     * @param datSource nombre del DataSource.
     * @return conexión a la base de datos.
     * @throws NamingException si hay un error de acceso al contexto de nombres.
     * @throws SQLException si hay un error de acceso a la base de datos.
     */
    private Connection getConnection(String datSource) throws NamingException, SQLException {
        InitialContext initialContext = new InitialContext();
        DataSource ds = (DataSource) initialContext.lookup(datSource);
        return ds.getConnection();
    }

    /**
     * Genera un informe JasperPrint a partir de un archivo .jasper y unos
     * parámetros.
     *
     * @param url dirección URI donde se encuentra el archivo .jasper.
     * @param parameters parámetros del informe.
     * @param connection conexión a la base de datos.
     * @return objeto JasperPrint del informe generado.
     * @throws JRException si hay un error durante la generación del informe.
     * @throws IOException si hay un error de E/S.
     * @throws URISyntaxException si hay un error de sintaxis en la URI.
     */
    private JasperPrint fillReport(String url, Map parameters, Connection connection)
            throws JRException, IOException, URISyntaxException {
        URI uriObj = getClass().getResource(url).toURI();
        File reporte = new File(uriObj);
        String archJasper = reporte.getAbsolutePath();
        return JasperFillManager.fillReport(archJasper, parameters, connection);
    }

    /**
     * Exporta un informe JasperPrint a formato XLS.
     *
     * @param jasperPrint objeto JasperPrint del informe.
     * @return array de bytes del informe exportado.
     * @throws JRException si hay un error durante la exportación del informe.
     */
    private byte[] exportReportToXLS(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
        setXLSExporterParameters(exporter);
        exporter.exportReport();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Exporta un informe JasperPrint a formato DOCX.
     *
     * @param jasperPrint objeto JasperPrint del informe.
     * @return array de bytes del informe exportado.
     * @throws JRException si hay un error durante la exportación del informe.
     */
    private byte[] exportReportToDOCX(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
        exporter.exportReport();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Exporta un informe JasperPrint a formato PDF.
     *
     * @param jasperPrint objeto JasperPrint del informe.
     * @return array de bytes del informe exportado.
     * @throws JRException si hay un error durante la exportación del informe.
     */
    private byte[] exportReportToPDF(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
        exporter.exportReport();
        return byteArrayOutputStream.toByteArray();
    }

    // Método auxiliar para configurar parámetros comunes del exportador XLS
    /**
     * Configura los parámetros comunes del exportador XLS.
     *
     * @param exporter exportador XLS.
     */
    private void setXLSExporterParameters(JRXlsExporter exporter) {
        exporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, true);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BORDER, false);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
        exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
    }
}
