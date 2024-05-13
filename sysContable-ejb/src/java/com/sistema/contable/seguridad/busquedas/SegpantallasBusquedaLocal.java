/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.seguridad.busquedas;

import com.sistema.contable.seguridad.entidades.Segpantallas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface SegpantallasBusquedaLocal {

    void create(Segpantallas segpantallas);

    void edit(Segpantallas segpantallas);

    void remove(Segpantallas segpantallas);

    Segpantallas find(Object id);

    List<Segpantallas> findAll();

    List<Segpantallas> findRange(int[] range);

    int count();
    
}
