/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorprocesos;

import java.util.ArrayList;

/**
 *
 * @author pablo
 */
public class EjecutarServicios extends ArrayList<Aplicacion> {

    /**
     * estado -1 no esta corriendo nada (modo espera) 0 se esta ejecutando en
     * modo async 1 se ejecuta en modo cola 2 se ejecuta en modo bla
     */
    int estado;
    //Esta variable nos sirve para los schedulers no para el estado en que esta corriendo el programa

    public EjecutarServicios() {
        estado = -1;
    }

    public void addProceso() {
        System.out.println("Añadiendo proceso");
        add(new GenerarServicios().generateServicio(size()));
    }

    public void moveToQueue() {
        if (estado != 1) {
            if (estado == 0) {
                for (int i = 0; i < size(); i++) {
                    get(i).terminado = true;
                }
            }
            estado = 1;
            ejecutarProcesosSyncronos();
        }
    }

    synchronized public void ejecutarProcesosAsync() {
        System.out.println("ASync");
        new Thread(() -> {
            int i = 0;
            while (estado != 0) {
                if (get(i).isAlive()) {
                    get(i).start();
                }
                i++;
                if (i >= size()) {
                    break;
                }
            }
            System.out.println("EOL Async");
            estado = -1;
        }).start();
    }

    synchronized public void ejecutarProcesosSyncronos() {
        System.out.println("Sync");
        if (estado != 2) {
            System.out.println("estado");
            estado = 2;
            new Thread(() -> {
                System.out.println("hilo");
                int i;
                for (i = 0; i < size(); i++) {
                    get(i).forzarTerminar = true;
                }//terminamos el hilo del servicio
                i = 0;
                try {
                    while (estado == 2) {
                        Thread.sleep(10);
                        get(i).progreso++;
                        if (get(i).progreso % 100 == 0) {
                            get(i).estado = (int) (Math.random() * 3);
                            if (get(i).estado == 1) {
                                GestorGUI.barrs.updateProgress(i);
                                System.out.println("bloqueado");
                                Thread.sleep((int) ((Math.random() * 2000) + 100));//bloqueado
                                if ((int) (Math.random() * 2) == 0) {
                                    get(i).estado = 0;
                                } else {
                                    get(i).estado = 2;
                                }
                            }
                        }
                        GestorGUI.barrs.updateProgress(i);
                        if (get(i).progreso >= get(i).duracion) {
                            get(i).estado = 3;
                            GestorGUI.barrs.updateProgress(i);
                            get(i).estado = 0;
                            i++;
                        }
                        if (i >= size()) {
                            get(i).estado = 3;
                            System.out.println("EOL");
                            GestorGUI.barrs.updateProgress(i);
                            get(i).estado = 0;
                            break;
                        }
                    }//si no ya no lo hagas
                } catch (Exception e) {

                }
                estado = -1;
            }).start();
        }
    }

    int quantum = 500;

    public void schedRoundRobbin() {
        new Thread(() -> {
            int quantumCheck = 0;
            int pivote = (int) Math.random() * size();
            try {
                while (estado == 3) {
                    if (get(pivote).progreso <= get(pivote).duracion) {
                        pivote = (int) Math.random() * size();
                    }
                    get(pivote).progreso++;
                    Thread.sleep(10);
                    quantumCheck += 10;
                    if (quantumCheck <= quantum) {
                        pivote = (int) Math.random() * size();
                    }
                }
            } catch (Exception e) {

            }
        });
    }

    public void schedLTRF() {
        sort((o1, o2) -> {
            return Integer.compare(o2.duracion, o1.duracion);
        });

    }
}
