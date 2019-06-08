package edu.udc.cg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 * PolygonObject
 */
public class PolygonObject {
    Polygon polygon;
    Color color;

    public PolygonObject(double[] x, double[] y, Color c){
        Screen.numberOfPolygons++;
        Polygon p = new Polygon();
        for(int i=0; i<x.length; i++){
            p.addPoint((int)x[i], (int)y[i]);
        }
        p.npoints = x.length;
        this.polygon = p;
        this.color = c;
    }

    void drawPolygon(Graphics g){
        g.setColor(this.color);
        g.fillPolygon(this.polygon);
    }

}