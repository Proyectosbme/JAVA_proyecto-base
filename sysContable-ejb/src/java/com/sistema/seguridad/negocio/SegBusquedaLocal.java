/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seguridad.negocio;

import com.sistema.general.validaciones.ValidacionesException;
import com.sistema.seguridad.entidades.Segmenu;
import com.sistema.seguridad.entidades.Segmodulo;
import com.sistema.seguridad.entidades.Segperfiles;
import com.sistema.seguridad.entidades.Segusuarios;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author BME_PERSONAL
 */
@Local
public interface SegBusquedaLocal {

    public List<Segmenu> findByCodmenu(BigInteger codmenu)
            throws NullPointerException, Exception;

    /**
     *
     * @param codPerfil
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    public List<Segmenu> findMenusNotByPerfil(BigInteger codPerfil)
            throws NullPointerException, Exception;

    /**
     *
     * @param codPerfil
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    public List<Segmenu> findMenusByPerfil(BigInteger codPerfil) throws NullPointerException, Exception;

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
    
    
    /**
     * Metodo que busca los modulos por tipo de datos que se le encia
     * @param parametros objeto llave valor con los campos necesarios
     * @return una lista de segmodulo
     * @throws com.sistema.general.validaciones.ValidacionesException
     * @throws NullPointerException validaciones nulas
     * @throws Exception errores generales
     */
    public List<Segmodulo> buscarModulo(Map parametros)
           throws ValidacionesException, NullPointerException, Exception ;
    
        /**
     * Metodo que obtiene el valor maximo de la pantallas por modulo
     * @param codModulo modulo al que pertencen las pantallas
     * @return un numero que es el valor maximo de las pantallas
     * @throws ValidacionesException validacion personalizada
     * @throws NullPointerException retorna zero en
     * @throws Exception 
     */
     public BigInteger maxCodPantalla(BigInteger codModulo)
            throws ValidacionesException, NullPointerException, Exception;

         /**
     * Metodo que buscara los perfiles segun los parametros que se le envien,
     * si no se envia parametros los buscara todos
     * @param parametros objeto llave valor de donde se obtendran los parametros
     * @return retornara una lis de segperfiles
     * @throws NullPointerException excepciones que puede lanza
     * @throws Exception excepcion general que puede lanzar
     */
    public List<Segperfiles> buscarPerfiles(Map parametros)
            throws NullPointerException,Exception;
    
    
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
    public Segusuarios buscarUsuarios(Map elementos) throws NullPointerException,Exception;
}
