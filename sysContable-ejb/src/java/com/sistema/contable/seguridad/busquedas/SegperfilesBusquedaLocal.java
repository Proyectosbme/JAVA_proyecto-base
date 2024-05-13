/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.seguridad.busquedas;

import com.sistema.contable.seguridad.entidades.Segperfiles;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface SegperfilesBusquedaLocal {

    void create(Segperfiles segperfiles);

    void edit(Segperfiles segperfiles);

    void remove(Segperfiles segperfiles);

    Segperfiles find(Object id);

    List<Segperfiles> findAll();

    List<Segperfiles> findRange(int[] range);

    int count();
    
}
