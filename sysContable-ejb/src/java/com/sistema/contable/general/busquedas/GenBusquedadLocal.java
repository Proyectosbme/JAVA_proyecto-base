package com.sistema.contable.general.busquedas;

import java.util.List;
import javax.ejb.Local;

@Local
public interface GenBusquedadLocal<T> {
    
    T find(Object id);

    List<T> findAll();

    List<T> findRange(int[] range);

}
