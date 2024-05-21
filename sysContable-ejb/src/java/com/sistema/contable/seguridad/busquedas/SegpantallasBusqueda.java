/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.seguridad.busquedas;

import com.sistema.contable.general.validaciones.ValidacionesException;
import com.sistema.contable.seguridad.entidades.Segpantallas;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class SegpantallasBusqueda implements SegpantallasBusquedaLocal {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;

    @Override
    public BigInteger maxCodPantalla(BigInteger codModulo)
            throws ValidacionesException, NullPointerException, Exception {
        try {
            if (codModulo == null) {
                throw new ValidacionesException("El codigo del modulo esta vacio",
                        "Seleccione un modulo");
            } else {
                BigDecimal result2 = BigDecimal.ONE;
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT NVL(MAX(s.CODPANTALLA),0) ");
                sql.append(" FROM SEGPANTALLAS s ");
                sql.append(" WHERE s.CODMOD = ?1 ");
                Query result = em.createNativeQuery(sql.toString());
                result.setParameter(1, codModulo);
                result2 = (BigDecimal) result.getSingleResult();
                BigInteger resp = result2.toBigInteger();
                return resp;
            }
        } catch (ValidacionesException ve) {
            throw ve;
        } catch (NullPointerException ne) {
            throw ne;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
