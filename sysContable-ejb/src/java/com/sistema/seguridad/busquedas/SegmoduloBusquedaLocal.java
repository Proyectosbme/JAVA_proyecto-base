/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.busquedas;

import com.sistema.general.validaciones.ValidacionesException;
import com.sistema.seguridad.entidades.Segmodulo;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface SegmoduloBusquedaLocal {

    /**
     * Metodo que busca los modulos por tipo de datos que se le encia
     * @param parametros objeto llave valor con los campos necesarios
     * @return una lista de segmodulo
     * @throws com.sistema.general.validaciones.ValidacionesException
     * @throws NullPointerException validaciones nulas
     * @throws Exception errores generales
     */
    public List<Segmodulo> buscarModulo(Map parametros)
           throws ValidacionesException, NullPointerException, Exception ;

}
