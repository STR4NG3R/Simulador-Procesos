/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorprocesos;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author pablo
 */
public class GraficasGUI {

    ArrayList<JLabel> barras;
    public ArrayList<Aplicacion> data;
    JScrollPane scrollPane;
    JPanel container = new JPanel();

 
    public GraficasGUI(ArrayList<Aplicacion> data) {
        barras = new ArrayList<>();
        container = new JPanel(null);
        scrollPane = new JScrollPane(container);
        System.out.println(getHeight());
        container.setPreferredSize(new Dimension(data.size() * 20, getHeight()));
        System.out.println(data);
        this.data = data;
        container.setBackground(Color.black);
        for (int i = 0; i < data.size(); i++) {
            barras.add(new JLabel());
            barras.get(i).setBounds((i * 20), 0, 15,
                    calcularPorcentaje(data.get(i).duracion, this.data.get(i).progreso));
            barras.get(i).setBackground(Color.BLUE);
            barras.get(i).setOpaque(true);
            container.add(barras.get(i));
        }
    }

    public void setData() {
        container.setPreferredSize(new Dimension(data.size() * 20, getHeight()));
        //Eliminamos todo
        if (barras.size() > 0) {
            for (int i = 0; i < barras.size(); i++) {
                barras.remove(i);
            }
        }
        container.setPreferredSize(new Dimension(data.size() * 20, getHeight()));
        for (int i = 0; i < data.size(); i++) {
            barras.add(new JLabel());
            barras.get(i).setOpaque(true);
            container.add(barras.get(i));
            //container.repaint();
        }
    }

    //Calculamos el porcentaje segun el progreso
    public int calcularPorcentaje(float duracion, float progreso) {
        if (progreso == 0) {
            return getHeight();
        } else {
            return (int) (getHeight() * (progreso / duracion));
        }
    }

    public void updateAllProgress() {
        for (int pivote = 0; pivote < data.size(); pivote++) {
            barras.get(pivote).setBounds((pivote * 20), 0, 20,
                    calcularPorcentaje(data.get(pivote).duracion, data.get(pivote).progreso));
            switch (data.get(pivote).estado) {
                case 0:
                    barras.get(pivote).setBackground(Color.blue);
                    break;
                case 1:
                    barras.get(pivote).setBackground(Color.red);
                    break;
                case 2:
                    barras.get(pivote).setBackground(Color.green);
                    break;
                case 3:
                    barras.get(pivote).setBackground(Color.yellow);
                    break;
            }

            //Solo repinta el cuadro especificado
            barras.get(pivote).repaint();
            container.repaint(barras.get(pivote).getX(), getHeight(), getWidth(), getHeight());
        }
    }

    public void updateProgress(int pivote) {
        barras.get(pivote).setBounds((pivote * 20), 0, 20,
                calcularPorcentaje(data.get(pivote).duracion, data.get(pivote).progreso));

        switch (data.get(pivote).estado) {
            case 0:
                barras.get(pivote).setBackground(Color.blue);
                break;
            case 1:
                barras.get(pivote).setBackground(Color.red);
                break;
            case 2:
                barras.get(pivote).setBackground(Color.green);
                break;
            case 3:
                barras.get(pivote).setBackground(Color.yellow);
                break;
        }

        //Solo repinta el cuadro especificado
        barras.get(pivote).repaint();
        container.repaint(barras.get(pivote).getX(), getHeight(), getWidth(), getHeight());

    }

    int getHeight() {
        return scrollPane.getHeight();
    }

    int getWidth() {
        return scrollPane.getWidth();
    }

    /**
     * Remover proceso junto con su barra
     */
    ArrayList<Aplicacion> removeBarAndData(int pivote) {
        container.remove(barras.get(pivote));
        data.remove(pivote);
        System.out.println(data.size());
        while (pivote < data.size()) {
            barras.get(pivote).setBounds(barras.get(pivote + 1).getBounds());//intercambiamos las dimensiones
            pivote++;
        }
        barras.remove(barras.size() - 1);
        container.setPreferredSize(new Dimension(data.size() * 20, getHeight()));
        container.repaint();
        return data;
    }
}
