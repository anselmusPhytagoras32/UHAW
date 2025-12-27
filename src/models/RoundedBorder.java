package models;

import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * RoundedBorder - Custom border with rounded corners.
 * 
 * This class extends AbstractBorder to provide a custom border with rounded corners.
 * It's used to create visually appealing UI components with customizable corner radius
 * and border color.
 */
public class RoundedBorder extends AbstractBorder {
    private int radius;
    private Color color;
    
    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }
    
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(this.color);
        g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
        g2.dispose();
    }
    
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(2, 2, 2, 2);
    }
    
    @Override
    public Insets getBorderInsets(Component c, Insets i) {
        i.left = i.right = i.bottom = i.top = 2;
        return i;
    }
}
