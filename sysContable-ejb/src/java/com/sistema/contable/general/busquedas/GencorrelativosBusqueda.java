/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.general.busquedas;

import com.sistema.contable.general.entidades.Gencorrelativos;
import com.sistema.contable.general.validaciones.ValidacionesException;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author BME_PERSONAL
 */
@Stateless
public class GencorrelativosBusqueda implements GencorrelativosBusquedaLocal {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(GencorrelativosBusqueda.class.getName());

    @Override
    public BigInteger obtenerCorrelativo(String nombreEntidad)
            throws NullPointerException, Exception {
        try {
            // Buscar la entidad por su nombre con bloqueo pesimista
            Gencorrelativos genCorr = em.find(Gencorrelativos.class, nombreEntidad, LockModeType.PESSIMISTIC_WRITE);
            if (genCorr != null) {
                BigInteger currentCorrValue;
                if (genCorr.getNumactual() != null) {
                    currentCorrValue = genCorr.getNumactual();
                    genCorr.setNumactual(genCorr.getNumactual().add(genCorr.getMultiplo()));
                } else {
                    currentCorrValue = genCorr.getNuminic().add(genCorr.getMultiplo());
                    genCorr.setNumactual(genCorr.getNuminic().add(genCorr.getMultiplo()));
                }

                em.persist(genCorr);
                return currentCorrValue;
            } else {
                throw new ValidacionesException("Error no existe la entidad",
                        "Consulte con el equipo de informatica");
            }

        } catch (ValidacionesException ve) {
            LOGGER.log(Level.SEVERE, "NullPointerException", ve);
            throw ve;
        } catch (NullPointerException np) {
            LOGGER.log(Level.SEVERE, "NullPointerException", np);
            throw np;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Excepción no controlada", ex);
            throw ex;
        }
    }

}
