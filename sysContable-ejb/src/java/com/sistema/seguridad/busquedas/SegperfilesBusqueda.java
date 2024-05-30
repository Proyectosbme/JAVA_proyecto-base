/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.busquedas;

import com.sistema.seguridad.entidades.Segperfiles;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author BME_PERSONAL
 */
@Stateless
public class SegperfilesBusqueda implements SegperfilesBusquedaLocal {

    /**
     * Persitencia
     */
    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;

    /**
     * Metodo que buscara los perfiles segun los parametros que se le envien, si
     * no se envia parametros los buscara todos
     *
     * @param parametros objeto llave valor de donde se obtendran los parametros
     * @return retornara una lis de segperfiles
     * @throws NullPointerException excepciones que puede lanza
     * @throws Exception excepcion general que puede lanzar
     */
    @Override
    public List<Segperfiles> buscarPerfiles(Map parametros) throws NullPointerException, Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT s FROM Segperfiles s ");
            sql.append("WHERE 1=1 ");
            if (parametros.containsKey("codperfil")) {
                sql.append("AND s.codperfil=:codperfil ");
            }
            if (parametros.containsKey("nombreperfil")) {
                sql.append("AND LOWER(s.nombreperfil) LIKE LOWER(:nombreperfil) ");
            }
            sql.append("ORDER BY s.codperfil");
            Query result = em.createQuery(sql.toString(), Segperfiles.class);
            if (parametros.containsKey("codperfil")) {
                result.setParameter("codperfil", parametros.get("codperfil"));
            }
            if (parametros.containsKey("nombreperfil")) {
                result.setParameter("nombreperfil", "%" + parametros.get("nombreperfil") + "%");
            }
            result.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
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
