/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.general.busquedas;

import com.sistema.contable.general.entidades.Gencorrelativos;
import com.sistema.contable.general.validaciones.ValidacionesException;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface GencorrelativosBusquedaLocal {

    public BigInteger obtenerCorrelativo(String nombreEntidad)
            throws ValidacionesException, NullPointerException, Exception;
}
