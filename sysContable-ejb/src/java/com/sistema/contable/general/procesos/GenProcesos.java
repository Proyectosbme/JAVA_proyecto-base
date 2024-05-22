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
 *
 * @author BME_PERSONAL
 */
@Stateless
public class GenProcesos<T> implements GenProcesosLocal<T> {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;
    private Class<T> entityClass;

    @Override
    public void create(T entity) throws Exception {
        try {
            em.persist(entity);
        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public void edit(T entity) {
        em.merge(entity);
    }

    @Override
    public void remove(T entity) {
        em.remove(em.merge(entity));
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void refreshAllEntities() throws Exception {
        try {

            em.getEntityManagerFactory().getMetamodel().getEntities().forEach(entityType -> {
                em.createQuery("SELECT e FROM " + entityType.getName() + " e").getResultList().forEach(entity -> {
                    em.refresh(entity);
                });
            });
        } catch (Exception ex) {
            throw ex;
        }
    }
}
