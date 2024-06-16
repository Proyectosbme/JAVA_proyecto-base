/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.general.negocio;

import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface GenProcesosLocal<T> {

    void create(T entity) throws Exception;

    void edit(T entity) throws Exception;

    void remove(T entity) throws Exception;

    public void refreshAllEntities() throws Exception;
}
