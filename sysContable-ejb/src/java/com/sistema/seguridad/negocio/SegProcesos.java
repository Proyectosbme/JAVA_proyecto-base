/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.negocio;

import com.sistema.seguridad.entidades.Segmenu;
import com.sistema.seguridad.entidades.Segperfiles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author BME_PERSONAL
 */
@Stateless
public class SegProcesos implements SegProcesosLocal {

    @EJB
    private SegBusquedaLocal segmenuBusqueda;

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;

    @Override
    public void guardarMenuSeleccionado(Map parametros) throws NullPointerException, Exception {
        try {
            Segperfiles perfilSelect = (Segperfiles) parametros.get("perfil");
            List<Segmenu> asiganado = (List<Segmenu>) parametros.get("menusAsignados");
            List<Segmenu> lstMenuTodos = (List<Segmenu>) parametros.get("todos");

            List<Segmenu> persistir = new ArrayList<>();

            for (Segmenu smt : lstMenuTodos) {
                if (asiganado.contains(smt)) {
                    if (!smt.getLstPerfiles().contains(perfilSelect)) {
                        smt.getLstPerfiles().add(perfilSelect);
                        persistir.add(smt);
                    }

                } else {
                    if (smt.getLstPerfiles().contains(perfilSelect)) {
                        smt.getLstPerfiles().remove(perfilSelect);
                        persistir.add(smt);
                    }
                }
            }

            for (Segmenu sm : persistir) {
                em.merge(sm);
            }
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

}
