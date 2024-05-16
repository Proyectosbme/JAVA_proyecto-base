package com.sistema.contable.general.busquedas;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class Genbusqueda<T> implements GenBusquedadLocal<T> {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;
    private Class<T> entityClass;

    @Override
    public void create(T entity) {
        em.persist(entity);
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
    public T find(Object id) {
        return em.find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<T> findRange(int[] range) {
         javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
