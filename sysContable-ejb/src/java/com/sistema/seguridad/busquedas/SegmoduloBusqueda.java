/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.busquedas;

import com.sistema.seguridad.entidades.Segmodulo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static final Logger LOGGER = Logger.getLogger(SegmoduloBusqueda.class.getName());

   
     /**
     * Metodo que busca los modulos por tipo de datos que se le encia
     * @param parametros objeto llave valor con los campos necesarios
     * @return una lista de segmodulo
     * @throws com.sistema.contable.general.validaciones.ValidacionesException
     * @throws NullPointerException validaciones nulas
     * @throws Exception errores generales
     */
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
            result.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            lstModulos = result.getResultList();
        } catch (NullPointerException ne) {
            LOGGER.log(Level.SEVERE, "Error al buscar módulos", ne);
            throw ne;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al buscar módulos", ex);
            throw ex;
        }
        return lstModulos;
    }
}
