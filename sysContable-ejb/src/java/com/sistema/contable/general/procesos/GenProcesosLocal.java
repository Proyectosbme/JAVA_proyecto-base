/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.general.procesos;

import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface GenProcesosLocal <T> {
    
    void create(T entity);
    
    void edit(T entity);

    void remove(T entity);
    
     int count();
     
      public void refreshAllEntities();
}
