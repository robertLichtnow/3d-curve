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

    public PolygonObject(int[] x, int[] y, Color c){
        Polygon p = new Polygon();
        p.xpoints = x;
        p.ypoints = y;
        p.npoints = x.length;
        this.polygon = p;
        this.color = c;
    }

    void drawPolygon(Graphics g){
        g.setColor(this.color);
        g.drawPolygon(this.polygon);
    }

}