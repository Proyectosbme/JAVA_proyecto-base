/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.general.procesos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase genérica para procesos de gestión de entidades.
 *
 * @param <T> El tipo de entidad manejada.
 * @author BME_PERSONAL
 */
@Stateless
public class GenProcesos<T> implements GenProcesosLocal<T> {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    /**
     * comenta las variables con este formato
     */
    private EntityManager em;
    private Class<T> entityClass;

    /**
     * Crea una nueva entidad en la base de datos.
     *
     * @param entity La entidad a crear.
     * @throws Exception Si ocurre un error durante la creación.
     */
    @Override
    public void create(T entity) throws Exception {
        try {
            em.persist(entity);
        } catch (Exception ex) {
            throw ex;
        }

    }

    /**
     * Actualiza una entidad existente en la base de datos.
     *
     * @param entity La entidad a actualizar.
     * @throws java.lang.Exception
     */
    @Override
    public void edit(T entity) throws Exception {
        try {
            em.merge(entity);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Elimina una entidad existente de la base de datos.
     *
     * @param entity La entidad a eliminar.
     * @throws java.lang.Exception
     */
    @Override
    public void remove(T entity) throws Exception {
        try {
            em.remove(em.merge(entity));
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Refresca todas las entidades gestionadas en el contexto de persistencia.
     *
     * @throws Exception Si ocurre un error durante la actualización.
     */
    @Override
    public void refreshAllEntities() throws Exception {
        try {
            em.getEntityManagerFactory()
                    .getMetamodel().getEntities()
                    .forEach(entityType -> {
                        em.createQuery("SELECT e FROM " + entityType.getName() + " e")
                                .getResultList().forEach(entity -> {
                                    em.refresh(entity);
                                });
                    });
        } catch (Exception ex) {
            throw ex;
        }
    }
}
