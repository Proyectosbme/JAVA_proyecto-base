/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.seguridad.busquedas;

import com.sistema.contable.seguridad.entidades.Segmenu;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface SegmenuBusquedaLocal {

    public List<Segmenu> buscarMenuXPerfil(BigInteger codPerfil)
            throws NullPointerException, Exception;

    public List<Segmenu> buscarTodosMenu(Map parametros)
            throws NullPointerException, Exception;

    public List<Segmenu> busMenuXPerfilXCodPadre(BigInteger codPerfil, BigInteger codMenuPadre)
            throws NullPointerException, Exception;

    public List<Segmenu> buscarSubMenu(BigInteger codmenu)
            throws NullPointerException, Exception;

    public List<Segmenu> busMenuRaiz(BigInteger codmod)
            throws NullPointerException, Exception;
}
