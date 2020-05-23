/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorprocesos;

/**
 * estado 0 todo con normalidad estado 1 bloqueado estado 2 corriendo estado 3
 * terminado
 *
 * @author pablo
 */
public class Aplicacion extends Thread implements Runnable {

    public int duracion;
    public int prioridad;
    public int progreso;
    public boolean terminado;
    public boolean forzarTerminar;
    int estado;
    int id;

    public Aplicacion(String name, int duracion, int priorida, int n) {
        setName(name);
        this.duracion = duracion;
        prioridad = priorida;
        setPriority(priorida);
        estado = 0;
        terminado = false;
        forzarTerminar = false;
        id = n;
    }

    @Override
    public void run() {
        try {
            while (!(terminado || forzarTerminar)) {
                sleep(10);
                if (progreso % 100 == 0) {
                    estado = (int) (Math.random() * 3);
                    if (estado == 1) {
                        GestorGUI.barrs.updateProgress(id);
                        System.out.println("bloqueado");
                        sleep((int) ((Math.random() * 2000) + 100));//bloqueado
                        if ((int) (Math.random() * 2) == 0) {
                            estado = 0;
                        } else {
                            estado = 2;
                        }
                    }
                }
                if (progreso >= duracion) {
                    terminado = true;
                    estado = 3;
                    System.out.println("terminado " + getName());
                }
                GestorGUI.barrs.updateProgress(id);
                progreso++;
            }
            join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
