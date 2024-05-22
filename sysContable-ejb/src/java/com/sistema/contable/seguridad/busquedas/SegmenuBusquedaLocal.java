/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.contable.seguridad.busquedas;

import com.sistema.contable.seguridad.entidades.Segmenu;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface SegmenuBusquedaLocal {

    public List<Segmenu> buscarMenu(BigInteger codPerfil);

    public List<Segmenu> buscarTodosMenu();

    public List<Segmenu> buscarSubMenu(BigInteger codPerfil, BigInteger codmenu);

}
