package com.sistema.contable.general.busquedas;

import java.util.List;
import javax.ejb.Local;

@Local
public interface GenBusquedadLocal<T> {
    
    void create(T entity);

    void edit(T entity);

    void remove(T entity);

    T find(Object id);

    List<T> findAll();

    List<T> findRange(int[] range);

    int count();
    
}
