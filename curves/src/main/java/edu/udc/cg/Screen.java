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

    static double[] viewFrom = new double[]{10,10,10};
    static double[] viewTo = new double[]{5,0,0};

    PolygonObject3D polygon3D;
    static PolygonObject[] drawablePolygons = new PolygonObject[100];
    static int numberOfPolygons = 0;

    public Screen(){
        this.polygon3D = new PolygonObject3D(new double[]{2,4,2}, new double[]{2,4,6}, new double[]{5,5,5}, Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        for(int i=0;i<numberOfPolygons;i++){
            drawablePolygons[i].drawPolygon(g);
        }
    }
    
}