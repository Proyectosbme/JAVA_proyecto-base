/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.seguridad.busquedas;

import com.sistema.contable.seguridad.entidades.Segmodulo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface SegmoduloBusquedaLocal {

    void create(Segmodulo segmodulo);

    void edit(Segmodulo segmodulo);

    void remove(Segmodulo segmodulo);

    Segmodulo find(Object id);

    List<Segmodulo> findAll();

    List<Segmodulo> findRange(int[] range);

    int count();
    
    public List<Segmodulo> buscarModulos() throws NullPointerException,Exception;
    
}
