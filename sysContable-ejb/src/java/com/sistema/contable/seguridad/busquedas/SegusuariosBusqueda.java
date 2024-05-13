/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.seguridad.busquedas;

import com.sistema.contable.general.AbstractFacade;
import com.sistema.contable.seguridad.entidades.Segusuarios;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author BME_PERSONAL
 */
@Stateless
public class SegusuariosBusqueda extends AbstractFacade<Segusuarios> implements SegusuariosBusquedaLocal {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegusuariosBusqueda() {
        super(Segusuarios.class);
    }

    @Override
    public Segusuarios buscarSubMenu(Map elementos) throws NullPointerException, Exception {

        Segusuarios usuario = new Segusuarios();
        StringBuilder sql = new StringBuilder();

        try {

            sql.append("SELECT U FROM Segusuarios U");
            sql.append(" WHERE 1 = 1");
            if (elementos.containsKey("usuario")) {
                sql.append(" AND u.coduser = :usuario");
            }
            if (elementos.containsKey("password")) {
                sql.append(" AND u.clave = :password");
            }

            if (elementos.containsKey("estado")) {
                sql.append(" AND u.estado = :estado");
            }

            Query consulta = em.createQuery(sql.toString());
            if (elementos.containsKey("usuario")) {
                consulta.setParameter("usuario", elementos.get("usuario"));
            }
            if (elementos.containsKey("password")) {
                consulta.setParameter("password", elementos.get("password"));
            }
            if (elementos.containsKey("estado")) {
                consulta.setParameter("estado", elementos.get("estado"));
            }

            usuario = (Segusuarios) consulta.getSingleResult();
            return usuario;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la traza de la excepción para depuración.
            return null;
        }

    }

}
