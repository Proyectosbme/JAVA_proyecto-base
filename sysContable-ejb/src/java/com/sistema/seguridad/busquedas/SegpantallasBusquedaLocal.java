/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.busquedas;

import com.sistema.general.validaciones.ValidacionesException;
import java.math.BigInteger;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface SegpantallasBusquedaLocal {

    /**
     * Metodo que obtiene el valor maximo de la pantallas por modulo
     * @param codModulo modulo al que pertencen las pantallas
     * @return un numero que es el valor maximo de las pantallas
     * @throws ValidacionesException validacion personalizada
     * @throws NullPointerException retorna zero en
     * @throws Exception 
     */
     public BigInteger maxCodPantalla(BigInteger codModulo)
            throws ValidacionesException, NullPointerException, Exception;
}
