/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;
import java.util.Calendar;
import java.util.Date;

public class Tiempo {

    public static int getAnio(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMes(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1; // Sumamos 1 para ajustar al rango de 1 a 12.
    }

    public static int getDia(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    // Ejemplo de uso:
    public static void main(String[] args) {
        Date currentDate = new Date();
        System.out.println("Año: " + getAnio(currentDate));
        System.out.println("Mes: " + getMes(currentDate));
        System.out.println("Día: " + getDia(currentDate));
    }
}
