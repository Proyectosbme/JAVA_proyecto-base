/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.seguridad.busquedas;

import com.sistema.contable.seguridad.entidades.Segmodulo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author BME_PERSONAL
 */
@Stateless
public class SegmoduloBusqueda implements SegmoduloBusquedaLocal {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;

    @Override
    public List<Segmodulo> buscarModulos() throws NullPointerException, Exception {
        try {
            List<Segmodulo> lstModulos = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT M FROM Segmodulo M ");
            sql.append("ORDER BY m.codmod");
            Query result = em.createQuery(sql.toString());
            result.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            lstModulos = result.getResultList();
            return lstModulos;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<Segmodulo> buscarModulo(Map parametros)
            throws NullPointerException, Exception {
        List<Segmodulo> lstModulos = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT s FROM Segmodulo s ");
            sql.append(" WHERE 1=1");
            if (parametros.containsKey("codmod")) {
                sql.append(" AND S.codmod = :codmod");
            }
            if (parametros.containsKey("nonmodulo")) {
                sql.append(" AND UPPER(s.nommodulo) LIKE UPPER(:nonmodulo)");
            }
            if (parametros.containsKey("urldirec")) {
                sql.append(" AND UPPER(s.urldirecc) LIKE UPPER(:urldirec)");
            }
            if (parametros.containsKey("catalogo")) {
                sql.append(" AND S.gencatalogosList = :catalogo");
            }
            sql.append(" ORDER BY s.codmod ASC");
            Query result = em.createQuery(sql.toString());
            if (parametros.containsKey("codmod")) {
                result.setParameter("codmod", parametros.get("codmod"));
            }
            if (parametros.containsKey("nonmodulo")) {
                result.setParameter("nonmodulo", "%" + parametros.get("nonmodulo") + "%");
            }
            if (parametros.containsKey("urldirec")) {
                result.setParameter("urldirec", "%" + parametros.get("urldirec") + "%");
            }
            if (parametros.containsKey("catalogo")) {
                result.setParameter("catalogo", parametros.get("catalogo"));
            }

            lstModulos = result.getResultList();
        } catch (NullPointerException ne) {
            throw ne;
        } catch (Exception ex) {
            throw ex;
        }
        return lstModulos;
    }
}
