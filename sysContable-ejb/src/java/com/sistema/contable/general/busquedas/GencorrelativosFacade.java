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
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author BME_PERSONAL
 */
@Stateless
public class GencorrelativosFacade implements GencorrelativosFacadeLocal {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(GencorrelativosFacade.class.getName());

    public BigInteger obtenerYActualizarNumeroActual(String nombreEntidad)
            throws ValidacionesException, NullPointerException, Exception {
        try {
            // Buscar la entidad por su nombre con bloqueo pesimista
            Gencorrelativos entidad = em.find(Gencorrelativos.class, nombreEntidad, LockModeType.PESSIMISTIC_WRITE);

            // Verificar si la entidad existe
            if (entidad != null) {
                // Verificar y actualizar el número actual según las condiciones especificadas
                BigInteger numeroActual = calcularNumeroActual(entidad);
                return numeroActual;
            } else {
                // Manejar el caso cuando la entidad no existe
                throw new ValidacionesException("La entidad no existe.",
                        "Verifique con el equipo informatico");
            }
        } catch (ValidacionesException ve) {
            LOGGER.log(Level.SEVERE, "Error de validación", ve);
            throw ve;
        } catch (OptimisticLockException ex) {
            LOGGER.log(Level.SEVERE, "Error de concurrencia al actualizar el correlativo", ex);
            throw ex;
        } catch (NullPointerException np) {
            LOGGER.log(Level.SEVERE, "NullPointerException", np);
            throw np;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Excepción no controlada", ex);
            throw ex;
        }
    }

    private BigInteger calcularNumeroActual(Gencorrelativos entidad)
            throws ValidacionesException, NullPointerException, Exception {
        try {

            BigInteger numactual = entidad.getNumactual();
            BigInteger numinic = entidad.getNuminic();
            BigInteger multiplo = entidad.getMultiplo();
            BigInteger numfinal = entidad.getNumfinal();

            if (numactual == null) {
                numactual = numinic;
            }

            if (numactual.compareTo(numfinal) > 0) {
                throw new ValidacionesException("El número actual es mayor que el número final",
                        "Consultar con el equipo de informatica");
            }

            BigInteger nuevoNumeroActual = numactual.add(multiplo);
            entidad.setNumactual(nuevoNumeroActual);

            // Actualizar la entidad en la base de datos
            em.merge(entidad);

            return nuevoNumeroActual;
        } catch (ValidacionesException ve) {
            LOGGER.log(Level.SEVERE, "Error de validación", ve);
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
