package com.sistema.general.negocio;

import com.sistema.general.entidades.Gencorrelativos;
import com.sistema.general.entidades.Genpersonas;
import com.sistema.general.entidades.Genpuntoventas;
import com.sistema.general.validaciones.ValidacionesException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
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
public class GenBusqueda<T> implements GenBusquedadLocal<T> {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    /**
     * Objeto de la entidas
     */
    private EntityManager em;
    /**
     * Objeto generico
     */
    private Class<T> entityClass;
    /**
     * Maneja los errores
     */
    private static final Logger LOGGER = Logger.getLogger(GenBusqueda.class.getName());

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
            LOGGER.log(Level.SEVERE, "NullPointerException", ne);
            return null;
        } catch (Exception ex) {
            // Si ocurre cualquier otra excepción, se relanza
            LOGGER.log(Level.SEVERE, "Error general", ex);
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
            LOGGER.log(Level.SEVERE, "NullPointerException", ne);
            return null;
        } catch (Exception ex) {
            // Si ocurre cualquier otra excepción, se relanza
            LOGGER.log(Level.SEVERE, "Error general", ex);
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
            LOGGER.log(Level.SEVERE, "NullPointerException", ne);
            return null;
        } catch (Exception ex) {
            // Si ocurre cualquier otra excepción, se relanza
            LOGGER.log(Level.SEVERE, "Error general", ex);
            throw ex;
        }
    }

    /**
     * Obtiene el siguiente valor de correlativo para una entidad especificada.
     *
     * @param nombreEntidad el nombre de la entidad para la cual se desea
     * obtener el correlativo
     * @return el siguiente valor de correlativo
     * @throws ValidacionesException si no se encuentra la entidad en la base de
     * datos
     * @throws NullPointerException si se produce una excepción de puntero nulo
     * @throws Exception si se produce una excepción no controlada
     */
    @Override
    public BigInteger obtenerCorrelativo(String nombreEntidad)
            throws NullPointerException, Exception {
        try {
            // Buscar la entidad por su nombre con bloqueo pesimista
            Gencorrelativos genCorr = em.find(Gencorrelativos.class, nombreEntidad, LockModeType.PESSIMISTIC_WRITE);
            if (genCorr != null) {
                BigInteger currentCorrValue;
                if (genCorr.getNumactual() != null) {
                    currentCorrValue = genCorr.getNumactual();
                    genCorr.setNumactual(genCorr.getNumactual().add(genCorr.getMultiplo()));
                } else {
                    currentCorrValue = genCorr.getNuminic().add(genCorr.getMultiplo());
                    genCorr.setNumactual(genCorr.getNuminic().add(genCorr.getMultiplo()));
                }

                em.persist(genCorr);
                return currentCorrValue;
            } else {
                throw new ValidacionesException("Error no existe la entidad",
                        "Consulte con el equipo de informatica");
            }

        } catch (ValidacionesException ve) {
            LOGGER.log(Level.SEVERE, "NullPointerException", ve);
            throw ve;
        } catch (NullPointerException np) {
            LOGGER.log(Level.SEVERE, "NullPointerException", np);
            throw np;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Excepción no controlada", ex);
            throw ex;
        }
    }

    @Override
    public List<Genpuntoventas> buscarPuntoVenta(Map parametros) throws NullPointerException, Exception {
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT p FROM Genpuntoventas p ");
            jpql.append("WHERE 1=1 ");
            if (parametros.containsKey("corrpventa")) {
                jpql.append("AND p.corrpventa=:corrpventa ");
            }

            Query q = em.createQuery(jpql.toString(), Genpuntoventas.class);
            if (parametros.containsKey("corrpventa")) {
                q.setParameter("corrpventa", parametros.get("corrpventa"));
            }
            return q.getResultList();
        } catch (NoResultException e) {
            return null;// Retornar una lista vacía si no hay resultados
        } catch (NullPointerException ne) {
            LOGGER.log(Level.SEVERE, "Excepción por datos nulos en : buscarPuntoVenta", ne);
            throw ne;

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Excepción no controlada en :buscarPuntoVenta", ex);
            throw ex;
        }
    }

    @Override
    public List<Genpersonas> buscarPersona(Map parametros) throws NullPointerException, Exception {
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT p FROM Genpersonas p ");
            jpql.append("WHERE 1=1 ");
            if (parametros.containsKey("corrper")) {
                jpql.append("AND p.corrper=:corrper ");
            }
            if (parametros.containsKey("prinombre")) {
                jpql.append("AND UPPER(p.prinombre) LIKE UPPER(:prinombre) ");
            }
            if (parametros.containsKey("segnombre")) {
                jpql.append("AND UPPER(p.segnombre) LIKE UPPER(:segnombre) ");
            }
            if (parametros.containsKey("priapellido")) {
                jpql.append("AND UPPER(p.priapellido) LIKE UPPER(:priapellido) ");
            }
            if (parametros.containsKey("dui")) {
                jpql.append("AND p.dui=:dui ");
            }
            if (parametros.containsKey("nomcom")) {
                jpql.append("AND UPPER(p.nomcom) LIKE UPPER(:nomcom) ");
            }
            if (parametros.containsKey("puntoventa")) {
                jpql.append("AND p.puntoventa.corrpventa=:puntoventa ");
            }

            Query q = em.createQuery(jpql.toString());

            if (parametros.containsKey("corrper")) {
                q.setParameter("corrper", parametros.get("corrper"));
            }
            if (parametros.containsKey("prinombre")) {
                q.setParameter("prinombre", "%" + parametros.get("prinombre").toString().toUpperCase() + "%");
            }
            if (parametros.containsKey("segnombre")) {
                q.setParameter("segnombre", "%" + parametros.get("segnombre").toString().toUpperCase() + "%");
            }
            if (parametros.containsKey("priapellido")) {
                q.setParameter("priapellido", "%" + parametros.get("priapellido").toString().toUpperCase() + "%");
            }
            if (parametros.containsKey("dui")) {
                q.setParameter("dui", parametros.get("dui"));
            }
            if (parametros.containsKey("nomcom")) {
                q.setParameter("nomcom", "%" + parametros.get("nomcom").toString().toUpperCase() + "%");
            }
            if (parametros.containsKey("puntoventa")) {
                q.setParameter("puntoventa", parametros.get("puntoventa"));
            }
            List<Genpersonas> lstPersonas = new ArrayList();
            return lstPersonas = q.getResultList();
        } catch (NullPointerException ne) {
            LOGGER.log(Level.SEVERE, "Excepción por datos nulos en : buscarPersona", ne);
            throw ne;

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Excepción no controlada en :buscarPersona", ex);
            throw ex;
        }
    }
}
