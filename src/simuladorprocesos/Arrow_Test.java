package simuladorprocesos;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Arrow_Test extends JPanel {

    ArrayList<Arrow> arrow;

    /**
     * Arrow beetwen components
     *
     * @param x
     * @param y
     * @param x0
     * @param y0
     */
    public Arrow_Test() {
        arrow = new ArrayList<>();
    }

    public void addArrow(int x, int y, int x0, int y0, int type) {
        arrow.add(new Arrow(x, y, x0, y0, type));
    }

    public void updateArrows(int i, int x, int y, int x1, int y1) {
        arrow.get(i).updateData(x, y, x1, y1);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setStroke(new BasicStroke(4));
        AffineTransform at = new AffineTransform();
        g2d.setTransform(at);

        for (int i = 0; i < arrow.size(); i++) {
            g2d.draw(arrow.get(i));
        }
        g2d.dispose();
    }

}

class Arrow extends Path2D.Double {

    public int x, y, x1, y1, type;

    public Arrow(int x, int y, int x1, int y1, int type) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.type = type;
        switch (type) {
            case 0:
                updateStrokeRight();
                break;
            case 1:
                updateStrokeBidirectional();
                break;
            case 2:
                updateStrokeLeft();
                break;
        }

    }

    //->
    void updateStrokeRight() {
        //Esta linea - es la que realmente importa
        /*moveTo(0, 10);
            lineTo(36, 10);*/
        moveTo(x, y);
        lineTo(x1, y1);
        // es \ esta linea
        moveTo(x1 - 10, y1 - 15);
        lineTo(x1, y1);
        /*moveTo(36 - 16, 0);
            lineTo(36, 10);*/
        //es esta / linea
        moveTo(x1 - 10, y1 + 15);
        lineTo(x1, y1);
    }

    //<->
    void updateStrokeBidirectional() {
        moveTo(x, y);
        lineTo(x1, y1);
        // es \ esta linea
        moveTo(x1 - 10, y1 - 10);
        lineTo(x1, y1);
        /*moveTo(36 - 16, 0);
            lineTo(36, 10);*/
        //es esta / linea
        moveTo(x1 - 10, y1 + 10);
        lineTo(x1, y1);
        //Inverse
        moveTo(x + 10, y - 10);
        lineTo(x, y);
        moveTo(x + 10, y + 10);
        lineTo(x, y);
    }

    //<-
    void updateStrokeLeft() {
        moveTo(x, y);
        lineTo(x1, y1);
        moveTo(x + 10, y - 10);
        lineTo(x, y);
        moveTo(x + 10, y + 10);
        lineTo(x, y);
    }

    public void updateData(int x, int y, int x1, int y1) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        switch (type) {
            case 0:
                updateStrokeRight();
                break;
            case 1:
                updateStrokeBidirectional();
                break;
            case 2:
                updateStrokeLeft();
                break;
        }
    }
}
