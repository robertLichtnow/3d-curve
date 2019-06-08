package edu.udc.cg;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Screen
 */
public class Screen extends JPanel{

    private static final long serialVersionUID = 1L;
    PolygonObject polygon;

    public Screen(){
        this.polygon = new PolygonObject(new int[]{10,200,10}, new int[]{10,200,400}, Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        polygon.drawPolygon(g);
    }
    
}