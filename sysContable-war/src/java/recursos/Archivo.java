package recursos;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Clase para manejar operaciones de archivo, como la lectura de archivos en un array de bytes.
 */
public class Archivo {

    /**
     * Lee el archivo especificado por la ruta y devuelve su contenido como un array de bytes.
     *
     * @param filePath la ruta del archivo a leer
     * @return un array de bytes que contiene el contenido del archivo
     * @throws IOException si ocurre un error de entrada/salida durante la lectura del archivo
     */
    public byte[] openFile(String filePath) throws IOException {
        // Utiliza try-with-resources para asegurar el cierre automático de los streams
        try (InputStream inputStream = new FileInputStream(filePath);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            
            byte[] buffer = new byte[1024];  // Buffer para leer el archivo en trozos
            int bytesRead;
            // Lee el archivo en bloques de 1024 bytes y los escribe en el ByteArrayOutputStream
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            // Devuelve el contenido del archivo como un array de bytes
            return outputStream.toByteArray();
        }
    }
}
