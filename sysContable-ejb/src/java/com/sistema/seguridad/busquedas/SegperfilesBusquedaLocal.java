/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.busquedas;

import com.sistema.seguridad.entidades.Segperfiles;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface SegperfilesBusquedaLocal {

    /**
     * Metodo que buscara los perfiles segun los parametros que se le envien,
     * si no se envia parametros los buscara todos
     * @param parametros objeto llave valor de donde se obtendran los parametros
     * @return retornara una lis de segperfiles
     * @throws NullPointerException excepciones que puede lanza
     * @throws Exception excepcion general que puede lanzar
     */
    public List<Segperfiles> buscarPerfiles(Map parametros)
            throws NullPointerException,Exception;
   
}
