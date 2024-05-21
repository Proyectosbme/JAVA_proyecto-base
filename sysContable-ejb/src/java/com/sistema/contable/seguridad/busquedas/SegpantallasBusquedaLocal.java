/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.seguridad.busquedas;

import com.sistema.contable.general.validaciones.ValidacionesException;
import java.math.BigInteger;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface SegpantallasBusquedaLocal {

     public BigInteger maxCodPantalla(BigInteger codModulo)
    throws ValidacionesException, NullPointerException, Exception;
}
