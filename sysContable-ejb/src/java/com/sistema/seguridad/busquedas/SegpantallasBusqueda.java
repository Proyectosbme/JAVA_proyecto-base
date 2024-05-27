/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.busquedas;

import com.sistema.general.validaciones.ValidacionesException;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class SegpantallasBusqueda implements SegpantallasBusquedaLocal {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;

    /**
     * Metodo que obtiene el valor maximo de la pantallas por modulo
     *
     * @param codModulo modulo al que pertencen las pantallas
     * @return un numero que es el valor maximo de las pantallas
     * @throws ValidacionesException validacion personalizada
     * @throws NullPointerException retorna zero en
     * @throws Exception
     */
    @Override
    public BigInteger maxCodPantalla(BigInteger codModulo) throws ValidacionesException, NullPointerException, Exception {
        if (codModulo == null) {
            throw new ValidacionesException("El código del módulo está vacío", "Seleccione un módulo");
        }
        try {
            String queryString = "SELECT NVL(MAX(s.CODPANTALLA), 0) FROM SEGPANTALLAS s WHERE s.CODMOD = :codModulo";
            Query query = em.createNativeQuery(queryString);
            query.setParameter("codModulo", codModulo);

            BigDecimal result = (BigDecimal) query.getSingleResult();
            return result.toBigInteger();
        } catch (NoResultException e) {
            return BigInteger.ZERO;
        } catch (NullPointerException ne) {
            throw new NullPointerException("Error de datos nulos en obtener el codigo maximo de la pantalla");
        } catch (Exception e) {
            throw new Exception("Error al obtener el código máximo de pantalla", e);
        }
    }
}
