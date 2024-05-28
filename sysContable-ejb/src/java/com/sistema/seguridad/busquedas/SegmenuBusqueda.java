/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.busquedas;

import com.sistema.seguridad.entidades.Segmenu;
import com.sistema.seguridad.entidades.Segperfiles;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author BME_PERSONAL
 */
@Stateless
public class SegmenuBusqueda implements SegmenuBusquedaLocal {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;

    /**
     *
     * @param codPerfil
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public List<Segmenu> findMenusByPerfil(BigInteger codPerfil)
            throws NullPointerException, Exception {
        try {
            TypedQuery<Segmenu> query = em.createNamedQuery("Segmenu.findByPerfil", Segmenu.class);
            query.setParameter("codperfil", codPerfil);
            query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            return query.getResultList();
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Segmenu> findByCodmenu(BigInteger codmenu)
            throws NullPointerException, Exception {
        try {
            TypedQuery<Segmenu> query = em.createNamedQuery("Segmenu.findByCodmenu", Segmenu.class);
            query.setParameter("codmenu", codmenu);
            query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *
     * @param codPerfil
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public List<Segmenu> findMenusNotByPerfil(BigInteger codPerfil)
            throws NullPointerException, Exception {
        try {
            Segperfiles perfil = em.find(Segperfiles.class, codPerfil);
            TypedQuery<Segmenu> query = em.createNamedQuery("Segmenu.findNotByPerfil", Segmenu.class);
            query.setParameter("perfil", perfil);
            query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Método para buscar menús por perfil.
     *
     * @param codPerfil El código del perfil del usuario.
     * @return Una lista de menús asociados al perfil.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    @Override
    public List<Segmenu> buscarMenuXPerfil(BigInteger codPerfil)
            throws NullPointerException, Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT m.* FROM SEGMENU m ")
                    .append("INNER JOIN SEGMENUXPERFIL mp ON m.CODMENU = mp.CODMENU ")
                    .append("INNER JOIN SEGPERFILES s ON s.CODPERFIL = mp.CODPERFIL ")
                    .append("WHERE  m.JERARQUIA =0 AND mp.CODPERFIL = ?1 ")
                    .append("ORDER BY m.CODMENU,m.ORDENES");

            Query result = em.createNativeQuery(sql.toString(), Segmenu.class);
            result.setParameter(1, codPerfil);

            return result.getResultList();
        } catch (NoResultException e) {
            return null;// Retornar una lista vacía si no hay resultados
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Método para buscar todos los menús según los parámetros especificados.
     *
     * @param parametros Mapa de parámetros para filtrar la búsqueda.
     * @return Una lista de menús que cumplen con los criterios de búsqueda.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    @Override
    public List<Segmenu> buscarTodosMenu(Map parametros)
            throws NullPointerException, Exception {
        try {
            List<Segmenu> lstSegmenus = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM SEGMENU m ");
            sql.append("WHERE 1=1 ");
            if (parametros.containsKey("codmenu")) {
                sql.append("AND m.CODMENU =?codmenu ");
            }
            if (parametros.containsKey("codmod")) {
                sql.append("AND  m.CODMOD =?codmod ");
            }
            if (parametros.containsKey("codpant")) {
                sql.append("AND  m.CODPANT =?codpant ");
            }
            if (parametros.containsKey("jerarquia")) {
                sql.append("AND  m.JERARQUIA =?jerarquia ");
            }
            if (parametros.containsKey("codmenupadre")) {
                sql.append("AND  m.CODMENUPADRE =?codmenupadre ");
            }
            sql.append("order by m.CODMENU,m.ORDENES");
            Query result = em.createNativeQuery(sql.toString(), Segmenu.class);
            if (parametros.containsKey("codmenu")) {
                result.setParameter("codmenu", parametros.get("codmenu"));
            }
            if (parametros.containsKey("codmod")) {
                result.setParameter("codmod", parametros.get("codmod"));
            }
            if (parametros.containsKey("codpant")) {
                result.setParameter("codpant", parametros.get("codpant"));
            }
            if (parametros.containsKey("jerarquia")) {
                result.setParameter("jerarquia", parametros.get("jerarquia"));
            }
            if (parametros.containsKey("codmenupadre")) {
                result.setParameter("codmenupadre", parametros.get("codmenupadre"));
            }
            lstSegmenus = result.getResultList();
            return lstSegmenus;
        } catch (NoResultException e) {
            return null;// Retornar una lista vacía si no hay resultados
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Método para buscar menús raíz según el código de módulo.
     *
     * @param codmod El código del módulo.
     * @return Una lista de menús raíz asociados al módulo.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    @Override
    public List<Segmenu> busMenuRaiz(BigInteger codmod)
            throws NullPointerException, Exception {
        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT m.* FROM SEGMENU m ")
                    .append("WHERE  (m.JERARQUIA =0 OR m.CODPANT =0) AND m.CODMOD =?1");
            Query result = em.createNativeQuery(sql.toString(), Segmenu.class);
            result.setParameter(1, codmod);

            return result.getResultList();
        } catch (NoResultException e) {
            return null;// Retornar una lista vacía si no hay resultados
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Método para buscar menús por perfil y código de menú padre.
     *
     * @param codPerfil El código del perfil del usuario.
     * @param codMenuPadre El código del menú padre.
     * @return Una lista de menús asociados al perfil y al código del menú
     * padre.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    @Override
    public List<Segmenu> busMenuXPerfilXCodPadre(BigInteger codPerfil, BigInteger codMenuPadre)
            throws NullPointerException, Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT m.* FROM SEGMENU m ")
                    .append("INNER JOIN SEGMENUXPERFIL mp ON m.CODMENU = mp.CODMENU ")
                    .append("INNER JOIN SEGPERFILES s ON s.CODPERFIL = mp.CODPERFIL ")
                    .append("WHERE m.JERARQUIA !=0 AND mp.CODPERFIL =?1 AND m.CODMENUPADRE = ?2 ")
                    .append("ORDER BY m.CODMENU,m.ORDENES");

            Query result = em.createNativeQuery(sql.toString(), Segmenu.class);
            result.setParameter(1, codPerfil);
            result.setParameter(2, codMenuPadre);

            return result.getResultList();
        } catch (NoResultException e) {
            return null;// Retornar una lista vacía si no hay resultados
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Método para buscar submenús según el código de menú padre.
     *
     * @param codmenu El código del menú padre.
     * @return Una lista de submenús asociados al código del menú padre.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    @Override
    public List<Segmenu> buscarSubMenu(BigInteger codmenu)
            throws NullPointerException, Exception {
        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT m.* FROM SEGMENU m ")
                    .append("WHERE m.JERARQUIA !=0 AND m.CODMENUPADRE = ?1 ")
                    .append("ORDER BY m.CODMENU,m.ORDENES");

            Query result = em.createNativeQuery(sql.toString(), Segmenu.class);
            result.setParameter(1, codmenu);

            return result.getResultList();
        } catch (NoResultException e) {
            return null;// Retornar una lista vacía si no hay resultados
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }
}
