/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.seguridad.busquedas;

import com.sistema.contable.seguridad.entidades.Segmodulo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
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

            return lstModulos = result.getResultList();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
