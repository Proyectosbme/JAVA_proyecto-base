package com.sistema.contable.general.busquedas;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Bean de sesión sin estado para operaciones de búsqueda genérica.
 *
 * @param <T> el tipo de entidad
 */
@Stateless
public class Genbusqueda<T> implements GenBusquedadLocal<T> {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;
    private Class<T> entityClass;

    /**
     * Busca una entidad por su clave primaria.
     *
     * @param <T> el tipo de entidad
     * @param entityClass la clase de la entidad
     * @param id la clave primaria de la entidad
     * @return la entidad encontrada, o null si no se encontró
     * @throws Exception si ocurre un error durante la búsqueda
     */
    @Override
    public <T> T buscarPorPK(Class<T> entityClass, Object id)
            throws NullPointerException, Exception {
        try {
            // Busca la entidad por su clave primaria
            return em.find(entityClass, id);
        } catch (NullPointerException ne) {
            // Si no se encuentra la entidad, se devuelve null
            ne.printStackTrace();
            return null;
        } catch (Exception ex) {
            // Si ocurre cualquier otra excepción, se relanza
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Busca todas las entidades de una tabla.
     *
     * @param <T> el tipo de entidad
     * @param entityClass la clase de la entidad
     * @return una lista de todas las entidades de la tabla
     * @throws java.lang.Exception
     */
    @Override
    public <T> List<T> buscarTodos(Class<T> entityClass)
            throws NullPointerException, Exception {
        try {
            // Construye la consulta para buscar todas las entidades de la tabla
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
            Root<T> root = criteriaQuery.from(entityClass);
            criteriaQuery.select(root);
            // Ejecuta la consulta y devuelve los resultados
            return em.createQuery(criteriaQuery).getResultList();
        } catch (NullPointerException ne) {
            // Si no se encuentra la entidad, se devuelve null
            ne.printStackTrace();
            return null;
        } catch (Exception ex) {
            // Si ocurre cualquier otra excepción, se relanza
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * Cuenta el número de filas de una tabla.
     *
     * @param entityClass la clase de la entidad
     * @return el número de filas de la tabla
     * @throws java.lang.Exception
     */
    @Override
    public BigInteger contarFilas(Class<?> entityClass)
            throws NullPointerException, Exception {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<BigInteger> cq = cb.createQuery(BigInteger.class);
            Root<?> root = cq.from(entityClass);
            cq.select(cb.construct(BigInteger.class, cb.count(root)));
            Query query = em.createQuery(cq);
            return (BigInteger) query.getSingleResult();
        } catch (NullPointerException ne) {
            // Si no se encuentra la entidad, se devuelve null
            ne.printStackTrace();
            return null;
        } catch (Exception ex) {
            // Si ocurre cualquier otra excepción, se relanza
            ex.printStackTrace();
            throw ex;
        }
    }

}
