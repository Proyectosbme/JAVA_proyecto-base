/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.seguridad.busquedas;

import com.sistema.contable.seguridad.entidades.Segusuarios;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface SegusuariosBusquedaLocal {

    void create(Segusuarios segusuarios);

    void edit(Segusuarios segusuarios);

    void remove(Segusuarios segusuarios);

    Segusuarios find(Object id);

    List<Segusuarios> findAll();

    List<Segusuarios> findRange(int[] range);

    int count();

    public Segusuarios buscarSubMenu(Map elementos) throws NullPointerException,Exception;

}
