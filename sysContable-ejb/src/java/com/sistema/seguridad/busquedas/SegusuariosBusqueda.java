/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.busquedas;

import com.sistema.seguridad.entidades.Segusuarios;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Stateless session bean para buscar usuarios en la base de datos. Proporciona
 * métodos para realizar búsquedas de usuarios con parámetros dinámicos. Utiliza
 * JPA para la interacción con la base de datos.
 *
 * @autor BME_PERSONAL
 */
@Stateless
public class SegusuariosBusqueda implements SegusuariosBusquedaLocal {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;

    // Logger para registrar información y excepciones
    private static final Logger LOGGER = Logger.getLogger(SegusuariosBusqueda.class.getName());

    /**
     * Busca usuarios en la base de datos según los parámetros proporcionados en
     * el mapa.
     *
     * @param elementos Mapa que contiene los parámetros de búsqueda (usuario,
     * password, estado).
     * @return El usuario encontrado o null si no se encuentra ningún usuario
     * que cumpla los criterios.
     * @throws NullPointerException si los parámetros proporcionados son nulos.
     * @throws Exception si ocurre cualquier otro error durante la búsqueda.
     */
    @Override
    public Segusuarios buscarUsuarios(Map elementos) throws NullPointerException, Exception {

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
            // Registrar el caso en el que no se encuentra ningún resultado
            LOGGER.log(Level.INFO, "No se encontró ningún usuario con los parámetros proporcionados: {0}", elementos);
            return null;
        } catch (NullPointerException e) {
            //Registra el caso de datos vacios
            LOGGER.log(Level.SEVERE, "Ocurrió un error de dato nulo al buscar el usuario: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            // Registrar cualquier otra excepción que ocurra
            LOGGER.log(Level.SEVERE, "Ocurrió un error al buscar el usuario: " + e.getMessage(), e);
            throw e;
        }
    }
}
