/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.seguridad.busquedas;

import com.sistema.contable.general.AbstractFacade;
import com.sistema.contable.seguridad.entidades.Segpantallas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author BME_PERSONAL
 */
@Stateless
public class SegpantallasBusqueda extends AbstractFacade<Segpantallas> implements SegpantallasBusquedaLocal {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegpantallasBusqueda() {
        super(Segpantallas.class);
    }
    
}
