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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author BME_PERSONAL
 */
@Stateless
public class SegBusqueda implements SegBusquedaLocal {

    @PersistenceContext(unitName = "sysContable-ejbPU")
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(SegBusqueda.class.getName());

    /**
     * Meotod que buysca los menus que pertenenecen al perfil
     *
     * @param codPerfil codigo del perfil para bsuacr emnu
     * @return retorna una lis ade segmenu
     * @throws java.lang.Exception excepcion que puede lanzar
     */
    @Override
    public List<Segmenu> findMenusByPerfil(BigInteger codPerfil)
            throws NullPointerException, Exception {
        try {
            TypedQuery<Segmenu> query = em.createNamedQuery("Segmenu.findByPerfil", Segmenu.class);
            query.setParameter("codperfil", codPerfil);
            query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            return query.getResultList();
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Metodo que busca el menu por su codigo de menu
     *
     * @param codmenu codigo del menu a buscar
     * @return retorna una lista de menus
     * @throws NullPointerException excepcion por datos nulos
     * @throws Exception excepcion general
     */
    @Override
    public List<Segmenu> findByCodmenu(BigInteger codmenu)
            throws NullPointerException, Exception {
        try {
            TypedQuery<Segmenu> query = em.createNamedQuery("Segmenu.findByCodmenu", Segmenu.class);
            query.setParameter("codmenu", codmenu);
            query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Meotod que buysca los menus que no pertenenecen al perfil
     *
     * @param codPerfil codigo del perfil para bsuacr emnu
     * @return retorna una lis ade segmenu
     * @throws java.lang.Exception excepcion que puede lanzar
     */
    @Override
    public List<Segmenu> findMenusNotByPerfil(BigInteger codPerfil)
            throws NullPointerException, Exception {
        try {
            Segperfiles perfil = em.find(Segperfiles.class, codPerfil);
            TypedQuery<Segmenu> query = em.createNamedQuery("Segmenu.findNotByPerfil", Segmenu.class);
            query.setParameter("perfil", perfil);
            query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Método para buscar menús por perfil.
     *
     * @param codPerfil El código del perfil del usuario.
     * @return Una lista de menús asociados al perfil.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    @Override
    public List<Segmenu> buscarMenuXPerfil(BigInteger codPerfil)
            throws NullPointerException, Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT m.* FROM SEGMENU m ")
                    .append("INNER JOIN SEGMENUXPERFIL mp ON m.CODMENU = mp.CODMENU ")
                    .append("INNER JOIN SEGPERFILES s ON s.CODPERFIL = mp.CODPERFIL ")
                    .append("WHERE  m.JERARQUIA =0 AND mp.CODPERFIL = ?1 ")
                    .append("ORDER BY m.CODMENU,m.ORDENES");

            Query result = em.createNativeQuery(sql.toString(), Segmenu.class);
            result.setParameter(1, codPerfil);

            return result.getResultList();
        } catch (NoResultException e) {
            return null;// Retornar una lista vacía si no hay resultados
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Método para buscar todos los menús según los parámetros especificados.
     *
     * @param parametros Mapa de parámetros para filtrar la búsqueda.
     * @return Una lista de menús que cumplen con los criterios de búsqueda.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    @Override
    public List<Segmenu> buscarTodosMenu(Map parametros)
            throws NullPointerException, Exception {
        try {
            List<Segmenu> lstSegmenus = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM SEGMENU m ");
            sql.append("WHERE 1=1 ");
            if (parametros.containsKey("codmenu")) {
                sql.append("AND m.CODMENU =?codmenu ");
            }
            if (parametros.containsKey("codmod")) {
                sql.append("AND  m.CODMOD =?codmod ");
            }
            if (parametros.containsKey("codpant")) {
                sql.append("AND  m.CODPANT =?codpant ");
            }
            if (parametros.containsKey("jerarquia")) {
                sql.append("AND  m.JERARQUIA =?jerarquia ");
            }
            if (parametros.containsKey("codmenupadre")) {
                sql.append("AND  m.CODMENUPADRE =?codmenupadre ");
            }
            sql.append("order by m.CODMENU,m.ORDENES");
            Query result = em.createNativeQuery(sql.toString(), Segmenu.class);
            if (parametros.containsKey("codmenu")) {
                result.setParameter("codmenu", parametros.get("codmenu"));
            }
            if (parametros.containsKey("codmod")) {
                result.setParameter("codmod", parametros.get("codmod"));
            }
            if (parametros.containsKey("codpant")) {
                result.setParameter("codpant", parametros.get("codpant"));
            }
            if (parametros.containsKey("jerarquia")) {
                result.setParameter("jerarquia", parametros.get("jerarquia"));
            }
            if (parametros.containsKey("codmenupadre")) {
                result.setParameter("codmenupadre", parametros.get("codmenupadre"));
            }
            lstSegmenus = result.getResultList();
            return lstSegmenus;
        } catch (NoResultException e) {
            return null;// Retornar una lista vacía si no hay resultados
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Método para buscar menús raíz según el código de módulo.
     *
     * @param codmod El código del módulo.
     * @return Una lista de menús raíz asociados al módulo.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    @Override
    public List<Segmenu> busMenuRaiz(BigInteger codmod)
            throws NullPointerException, Exception {
        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT m.* FROM SEGMENU m ")
                    .append("WHERE  (m.JERARQUIA =0 OR m.CODPANT =0) AND m.CODMOD =?1");
            Query result = em.createNativeQuery(sql.toString(), Segmenu.class);
            result.setParameter(1, codmod);

            return result.getResultList();
        } catch (NoResultException e) {
            return null;// Retornar una lista vacía si no hay resultados
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Método para buscar menús por perfil y código de menú padre.
     *
     * @param codPerfil El código del perfil del usuario.
     * @param codMenuPadre El código del menú padre.
     * @return Una lista de menús asociados al perfil y al código del menú
     * padre.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    @Override
    public List<Segmenu> busMenuXPerfilXCodPadre(BigInteger codPerfil, BigInteger codMenuPadre)
            throws NullPointerException, Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT m.* FROM SEGMENU m ")
                    .append("INNER JOIN SEGMENUXPERFIL mp ON m.CODMENU = mp.CODMENU ")
                    .append("INNER JOIN SEGPERFILES s ON s.CODPERFIL = mp.CODPERFIL ")
                    .append("WHERE m.JERARQUIA !=0 AND mp.CODPERFIL =?1 AND m.CODMENUPADRE = ?2 ")
                    .append("ORDER BY m.CODMENU,m.ORDENES");

            Query result = em.createNativeQuery(sql.toString(), Segmenu.class);
            result.setParameter(1, codPerfil);
            result.setParameter(2, codMenuPadre);

            return result.getResultList();
        } catch (NoResultException e) {
            return null;// Retornar una lista vacía si no hay resultados
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Método para buscar submenús según el código de menú padre.
     *
     * @param codmenu El código del menú padre.
     * @return Una lista de submenús asociados al código del menú padre.
     * @throws NullPointerException Si se produce una excepción de tipo
     * NullPointerException.
     * @throws Exception Si se produce una excepción de tipo Exception.
     */
    @Override
    public List<Segmenu> buscarSubMenu(BigInteger codmenu)
            throws NullPointerException, Exception {
        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT m.* FROM SEGMENU m ")
                    .append("WHERE m.JERARQUIA !=0 AND m.CODMENUPADRE = ?1 ")
                    .append("ORDER BY m.CODMENU,m.ORDENES");

            Query result = em.createNativeQuery(sql.toString(), Segmenu.class);
            result.setParameter(1, codmenu);

            return result.getResultList();
        } catch (NoResultException e) {
            return null;// Retornar una lista vacía si no hay resultados
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Metodo que busca los modulos por tipo de datos que se le encia
     *
     * @param parametros objeto llave valor con los campos necesarios
     * @return una lista de segmodulo
     * @throws NullPointerException validaciones nulas
     * @throws Exception errores generales
     */
    @Override
    public List<Segmodulo> buscarModulo(Map parametros)
            throws NullPointerException, Exception {
        List<Segmodulo> lstModulos = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT s FROM Segmodulo s ");
            sql.append(" WHERE 1=1");
            if (parametros.containsKey("codmod")) {
                sql.append(" AND S.codmod = :codmod");
            }
            if (parametros.containsKey("nonmodulo")) {
                sql.append(" AND UPPER(s.nommodulo) LIKE UPPER(:nonmodulo)");
            }
            if (parametros.containsKey("urldirec")) {
                sql.append(" AND UPPER(s.urldirecc) LIKE UPPER(:urldirec)");
            }
            if (parametros.containsKey("catalogo")) {
                sql.append(" AND S.gencatalogosList = :catalogo");
            }
            sql.append(" ORDER BY s.codmod ASC");
            Query result = em.createQuery(sql.toString());
            if (parametros.containsKey("codmod")) {
                result.setParameter("codmod", parametros.get("codmod"));
            }
            if (parametros.containsKey("nonmodulo")) {
                result.setParameter("nonmodulo", "%" + parametros.get("nonmodulo") + "%");
            }
            if (parametros.containsKey("urldirec")) {
                result.setParameter("urldirec", "%" + parametros.get("urldirec") + "%");
            }
            if (parametros.containsKey("catalogo")) {
                result.setParameter("catalogo", parametros.get("catalogo"));
            }
            result.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            lstModulos = result.getResultList();
        } catch (NullPointerException ne) {
            LOGGER.log(Level.SEVERE, "Error al buscar módulos", ne);
            throw ne;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al buscar módulos", ex);
            throw ex;
        }
        return lstModulos;
    }

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
    public BigInteger maxCodPantalla(BigInteger codModulo)
            throws ValidacionesException, NullPointerException, Exception {
        if (codModulo == null) {
            throw new ValidacionesException("El código del módulo está vacío", "Seleccione un módulo");
        }
        try {
            String queryString = "SELECT  MAX(s.CODPANTALLA)  FROM SEGPANTALLAS s "
                    + "WHERE s.CODMOD =?1";
            Query query = em.createNativeQuery(queryString);
            query.setParameter(1, codModulo);

            Long result = (Long) query.getSingleResult();
            return new BigInteger(result.toString());
        } catch (NoResultException e) {
            return BigInteger.ZERO;
        } catch (NullPointerException ne) {
            throw new NullPointerException("Error de datos nulos en obtener el codigo maximo de la pantalla");
        } catch (Exception e) {
            throw new Exception("Error al obtener el código máximo de pantalla", e);
        }
    }

    /**
     * Metodo que buscara los perfiles segun los parametros que se le envien, si
     * no se envia parametros los buscara todos
     *
     * @param parametros objeto llave valor de donde se obtendran los parametros
     * @return retornara una lis de segperfiles
     * @throws NullPointerException excepciones que puede lanza
     * @throws Exception excepcion general que puede lanzar
     */
    @Override
    public List<Segperfiles> buscarPerfiles(Map parametros) throws NullPointerException, Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT s FROM Segperfiles s ");
            sql.append("WHERE 1=1 ");
            if (parametros.containsKey("codperfil")) {
                sql.append("AND s.codperfil=:codperfil ");
            }
            if (parametros.containsKey("nombreperfil")) {
                sql.append("AND LOWER(s.nombreperfil) LIKE LOWER(:nombreperfil) ");
            }
            sql.append("ORDER BY s.codperfil");
            Query result = em.createQuery(sql.toString(), Segperfiles.class);
            if (parametros.containsKey("codperfil")) {
                result.setParameter("codperfil", parametros.get("codperfil"));
            }
            if (parametros.containsKey("nombreperfil")) {
                result.setParameter("nombreperfil", "%" + parametros.get("nombreperfil") + "%");
            }
            result.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            return result.getResultList();
        } catch (NoResultException e) {
            return null;// Retornar una lista vacía si no hay resultados
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

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
    @Override
    public List<Segusuarios> buscarUsuarios(Map elementos) throws NullPointerException, Exception {

        List<Segusuarios> usuario = new ArrayList();
        StringBuilder sql = new StringBuilder();

        try {
            sql.append("SELECT U FROM Segusuarios U");
            sql.append(" WHERE 1 = 1");
            if (elementos.containsKey("usuario")) {
                sql.append(" AND u.coduser = :usuario");
            }
            if (elementos.containsKey("password")) {
                sql.append(" AND u.clave = :password");
            }
            if (elementos.containsKey("estado")) {
                sql.append(" AND u.estado = :estado");
            }
            if (elementos.containsKey("nombre")) {
                sql.append(" AND UPPER(u.persona.nomcom) LIKE UPPER(:nombre)");
            }
            if (elementos.containsKey("corsucursal")) {
                sql.append(" AND u.persona.corrper = :corsucursal");
            }
            if (elementos.containsKey("persona")) {
                sql.append(" AND u.persona = :persona");
            }
            Query consulta = em.createQuery(sql.toString());
            if (elementos.containsKey("usuario")) {
                consulta.setParameter("usuario", elementos.get("usuario"));
            }
            if (elementos.containsKey("password")) {
                consulta.setParameter("password", elementos.get("password"));
            }
            if (elementos.containsKey("estado")) {
                consulta.setParameter("estado", elementos.get("estado"));
            }
            if (elementos.containsKey("nombre")) {
                consulta.setParameter("nombre", "%" + elementos.get("nombre").toString().toUpperCase() + "%");
            }
            if (elementos.containsKey("corsucursal")) {
                consulta.setParameter("corsucursal", elementos.get("corsucursal"));
            }
            if (elementos.containsKey("persona")) {
                consulta.setParameter("persona", elementos.get("persona"));
            }
            consulta.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            usuario = consulta.getResultList();
            return usuario;
        } catch (NoResultException e) {
            // Registrar el caso en el que no se encuentra ningún resultado
            LOGGER.log(Level.INFO, "No se encontró ningún usuario con los parámetros proporcionados: {0}", elementos);
            return null;
        } catch (NullPointerException e) {
            //Registra el caso de datos vacios
            LOGGER.log(Level.SEVERE, "Ocurrió un error de dato nulo al buscar el usuario: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            // Registrar cualquier otra excepción que ocurra
            LOGGER.log(Level.SEVERE, "Ocurrió un error al buscar el usuario: " + e.getMessage(), e);
            throw e;
        }
    }
}
