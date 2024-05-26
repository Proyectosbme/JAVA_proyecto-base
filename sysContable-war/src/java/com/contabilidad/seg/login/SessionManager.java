/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.seg.login;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SessionManager es una clase que gestiona las sesiones activas de los usuarios.
 * Se asegura de que cada usuario tenga solo una sesión activa a la vez.
 */
@Named
@ApplicationScoped
public class SessionManager {
    /**
     * Mapa para almacenar las sesiones activas por usuario.
     * La clave es el ID del usuario y el valor es la sesión HTTP correspondiente.
     */
    private Map<String, HttpSession> activeSessions;

    /**
     * Método de inicialización que se ejecuta después de la construcción del bean.
     * Inicializa el mapa de sesiones activas.
     */
    @PostConstruct
    public void init() {
        activeSessions = new ConcurrentHashMap<>();
    }

    /**
     * Añade una nueva sesión para un usuario específico.
     * Si el usuario ya tiene una sesión activa, la invalida antes de añadir la nueva sesión.
     *
     * @param userId El ID del usuario.
     * @param session La nueva sesión HTTP que se va a añadir.
     */
    public synchronized void addSession(String userId, HttpSession session) {
        // Cierra la sesión existente si ya existe
    if (activeSessions.containsKey(userId)) {
        HttpSession existingSession = activeSessions.get(userId);
        if (existingSession != null) {
            try {
                // Verifica si la sesión aún no ha expirado
                existingSession.getLastAccessedTime(); // Esto lanzará una excepción si la sesión ha expirado
                // Si no se lanza una excepción, la sesión aún está activa y se puede invalidar
                existingSession.invalidate();
            } catch (IllegalStateException e) {
                // La sesión ha expirado, no es necesario invalidarla
            }
        }
    }
    // Añade la nueva sesión al mapa
    activeSessions.put(userId, session);
    }

    /**
     * Elimina la sesión activa de un usuario específico.
     *
     * @param userId El ID del usuario cuya sesión se va a eliminar.
     */
    public synchronized void removeSession(String userId) {
        activeSessions.remove(userId);
    }
}
