/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorprocesos;

/**
 *
 * @author pablo
 */
public class GenerarServicios {

    public Aplicacion generateServicio(int n) {
        int duracion, prioridad = 0;
        String nombre;
        duracion = (int) ((Math.random() * 700) + 100);
        nombre = "Proceso #" + (n + 1);
        switch ((int) (Math.random() * 3)) {
            case 0:
                prioridad = Thread.MAX_PRIORITY;
                break;
            case 1:
                prioridad = Thread.MIN_PRIORITY;
                break;
            case 2:
                prioridad = Thread.NORM_PRIORITY;
                break;
        }
        return new Aplicacion(nombre, duracion, prioridad, n);
    }

    public Aplicacion generateServicio(String nombre, int duracion, int prioridad, int n) {
        return new Aplicacion(nombre, duracion, prioridad, n);
    }
}
