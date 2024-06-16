/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.negocio;

import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface SegProcesosLocal {
    
    public void guardarMenuSeleccionado(Map parametros) throws NullPointerException, Exception;
}
