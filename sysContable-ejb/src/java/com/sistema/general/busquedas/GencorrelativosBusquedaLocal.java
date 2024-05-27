/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
/*
 * Este archivo es parte del paquete de búsqueda de correlativos del sistema contable.
 * Proporciona una interfaz para buscar correlativos para entidades específicas.
 */
package com.sistema.general.busquedas;

import com.sistema.general.validaciones.ValidacionesException;
import java.math.BigInteger;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL Interfaz local para buscar correlativos en el sistema
 * contable.
 *
 */
@Local
public interface GencorrelativosBusquedaLocal {

    /**
     * Obtiene el siguiente valor de correlativo para la entidad especificada.
     *
     * @param nombreEntidad el nombre de la entidad para la cual se desea
     * obtener el correlativo
     * @return el siguiente valor de correlativo
     * @throws ValidacionesException si ocurre un error de validación
     * @throws NullPointerException si se pasa un nombre de entidad nulo
     * @throws Exception si ocurre una excepción no controlada
     */
    public BigInteger obtenerCorrelativo(String nombreEntidad)
            throws ValidacionesException, NullPointerException, Exception;
}
