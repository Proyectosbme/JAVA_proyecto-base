/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.busquedas;

import com.sistema.seguridad.entidades.Segusuarios;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface SegusuariosBusquedaLocal {

     /**
     * Busca usuarios en la base de datos según los parámetros proporcionados en
     * el mapa.
     *
     * @param elementos Mapa que contiene los parámetros de búsqueda (usuario,
     * password, estado).
     * @return El usuario encontrado o null si no se encuentra ningún usuario
     * que cumpla los criterios.
     * @throws NullPointerException si los parámetros proporcionados son nulos.
     * @throws Exception si ocurre cualquier otro error durante la búsqueda.
     */
    public Segusuarios buscarUsuarios(Map elementos) throws NullPointerException,Exception;

}
