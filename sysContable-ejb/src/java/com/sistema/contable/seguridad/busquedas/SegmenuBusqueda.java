/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.seguridad.busquedas;

import com.sistema.contable.seguridad.entidades.Segmenu;
import java.math.BigInteger;
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
public class SegmenuBusqueda implements SegmenuBusquedaLocal {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;

    @Override
    public List<Segmenu> buscarMenu(BigInteger codPerfil) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m.* FROM SEGMENU m ")
                .append("INNER JOIN SEGMENUXPERFIL mp ON m.CODMENU = mp.CODMENU ")
                .append("INNER JOIN SEGPERFILES s ON s.CODPERFIL = mp.CODPERFIL ")
                .append("WHERE  m.JERARQUIA =0 AND mp.CODPERFIL = ?1");

        Query result = em.createNativeQuery(sql.toString(), Segmenu.class);
        result.setParameter(1, codPerfil);

        return result.getResultList();
    }

    @Override
    public List<Segmenu> buscarTodosMenu() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m.* FROM SEGMENU m ")
                .append("WHERE  m.JERARQUIA =0 ");
        Query result = em.createNativeQuery(sql.toString(), Segmenu.class);

        return result.getResultList();
    }

    @Override
    public List<Segmenu> buscarSubMenu(BigInteger codPerfil, BigInteger codmenu) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m.* FROM SEGMENU m ")
                .append("INNER JOIN SEGMENUXPERFIL mp ON m.CODMENU = mp.CODMENU ")
                .append("INNER JOIN SEGPERFILES s ON s.CODPERFIL = mp.CODPERFIL ")
                .append("WHERE m.JERARQUIA !=0 AND mp.CODPERFIL =?1 AND m.CODMENUPADRE = ?2");

        Query result = em.createNativeQuery(sql.toString(), Segmenu.class);
        result.setParameter(1, codPerfil);
        result.setParameter(2, codmenu);

        return result.getResultList();
    }

}
