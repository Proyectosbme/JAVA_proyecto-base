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

    /**
     * Busca los menús asociados a un perfil específico.
     *
     * @param codPerfil El código del perfil del usuario.
     * @return Una lista de menús asociados al perfil.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    public List<Segmenu> buscarMenuXPerfil(BigInteger codPerfil)
            throws NullPointerException, Exception;

    /**
     * Busca todos los menús que coincidan con los parámetros especificados.
     *
     * @param parametros Un mapa que contiene los parámetros de búsqueda.
     * @return Una lista de menús que cumplen con los criterios de búsqueda
     * especificados.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    public List<Segmenu> buscarTodosMenu(Map parametros)
            throws NullPointerException, Exception;

    /**
     * Busca los menús asociados a un perfil y un código de menú padre
     * específicos.
     *
     * @param codPerfil El código del perfil del usuario.
     * @param codMenuPadre El código del menú padre.
     * @return Una lista de menús asociados al perfil y al código del menú
     * padre.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    public List<Segmenu> busMenuXPerfilXCodPadre(BigInteger codPerfil, BigInteger codMenuPadre)
            throws NullPointerException, Exception;

    /**
     * Busca los submenús asociados a un menú padre específico.
     *
     * @param codmenu El código del menú padre.
     * @return Una lista de submenús asociados al menú padre.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    public List<Segmenu> buscarSubMenu(BigInteger codmenu)
            throws NullPointerException, Exception;

    /**
     * Busca los menús raíz asociados a un módulo específico.
     *
     * @param codmod El código del módulo.
     * @return Una lista de menús raíz asociados al módulo.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    public List<Segmenu> busMenuRaiz(BigInteger codmod)
            throws NullPointerException, Exception;

}
