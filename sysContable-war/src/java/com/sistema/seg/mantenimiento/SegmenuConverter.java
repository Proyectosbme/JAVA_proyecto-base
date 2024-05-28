/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.seg.mantenimiento;
import com.sistema.seguridad.entidades.Segmenu;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.math.BigInteger;

@FacesConverter("segmenuConverter")
public class SegmenuConverter implements Converter {
  // Este método convierte una cadena en un objeto Segmenu
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) { // Verifica si la cadena no es nula ni vacía
            // Crea un nuevo objeto Segmenu utilizando el valor de la cadena como su identificador
            return new Segmenu(new BigInteger(value)); // Suponiendo que el constructor de Segmenu toma un BigInteger como parámetro
        } else {
            return null; // Devuelve null si la cadena es nula o vacía
        }
    }

    // Este método convierte un objeto Segmenu en una cadena
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) { // Verifica si el objeto no es nulo
            // Devuelve una cadena que representa el identificador del objeto Segmenu
            return String.valueOf(((Segmenu) object).getCodmenu()); // Suponiendo que getCodmenu() devuelve el identificador como un BigInteger
        } else {
            return null; // Devuelve null si el objeto es nulo
        }
    }
}