package edu.udc.cg;

import java.awt.Color;

/**
 * PolygonObject3D
 */
public class PolygonObject3D {

    Color color;
    double[] x,y,z;

    public PolygonObject3D(double[] x, double[] y, double[] z, Color c){
        this.color = c;
        this.x = x;
        this.y = y;
        this.z = z;
        createPolygon();
    }

    void createPolygon(){
        double[] calculatedX = new double[x.length];
        double[] calculatedY = new double[y.length];
        for(int i=0; i<calculatedX.length;i++){
            calculatedX[i] = 200 * Calculator.calculatePositionX(Screen.viewFrom, Screen.viewTo, x[i], y[i], z[i]);
            calculatedY[i] = 200 * Calculator.calculatePositionY(Screen.viewFrom, Screen.viewTo, x[i], y[i], z[i]);
        }
        Screen.drawablePolygons[Screen.numberOfPolygons] = new PolygonObject(calculatedX, calculatedY, this.color);
    }

}