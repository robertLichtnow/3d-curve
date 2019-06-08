package edu.udc.cg;

import java.awt.Color;
import java.awt.Toolkit;

/**
 * PolygonObject3D
 */
public class PolygonObject3D {

    Color color;
    double[] x,y,z;
    int poly = 0;
    int multiplyFactor = 50;
    int xOffset = Toolkit.getDefaultToolkit().getScreenSize().width/2 - 200;
    int yOffset = Toolkit.getDefaultToolkit().getScreenSize().height/2;

    public PolygonObject3D(double[] x, double[] y, double[] z, Color c){
        Screen.numberOfPolygons3D++;
        this.color = c;
        this.x = x;
        this.y = y;
        this.z = z;
        createPolygon();
    }

    void createPolygon(){
        poly = Screen.numberOfPolygons;
        Screen.drawablePolygons[Screen.numberOfPolygons] = new PolygonObject(new double[]{}, new double[]{}, this.color);
        Screen.drawablePolygons[poly].avgDistance = getDistance();
        updatePolygon();
    }

    Vector getMiddlePoint(){
        double totalX = 0, totalY = 0, totalZ = 0;
        for(int i=0;i<x.length;i++){
            totalX += x[i];
            totalY += y[i];
            totalZ += z[i];
        }
        return new Vector(totalX/x.length, totalY/y.length, totalZ/z.length);
    }

    void updatePolygon(){
        double[] calculatedX = new double[x.length];
        double[] calculatedY = new double[y.length];
        for(int i=0; i<calculatedX.length;i++){
            calculatedX[i] =  xOffset + multiplyFactor * Calculator.calculatePositionX(Screen.viewFrom, Screen.viewTo, x[i], y[i], z[i]);
            calculatedY[i] =  yOffset + multiplyFactor * Calculator.calculatePositionY(Screen.viewFrom, Screen.viewTo, x[i], y[i], z[i]);
        }
        Screen.drawablePolygons[poly] = new PolygonObject(calculatedX, calculatedY, this.color);
        Screen.drawablePolygons[poly].avgDistance = getDistance();
        Screen.numberOfPolygons--;
    }

    double getDistance(){
        double total = 0;
        for(int i=0;i<x.length;i++){
            total += getDistanceToP(i); 
        }
        return total/x.length;
    }

    double getDistanceToP(int i){
        return Math.sqrt(
				(Screen.viewFrom[0] - x[i])*(Screen.viewFrom[0] - x[i]) +
				(Screen.viewFrom[1] - y[i])*(Screen.viewFrom[1] - y[i]) +
				(Screen.viewFrom[2] - z[i])*(Screen.viewFrom[2] - z[i]));
    }

    public void translate(Vector factor){
        for(int i=0; i<this.x.length;i++){
            this.x[i] += factor.x;
            this.y[i] += factor.y;
            this.z[i] += factor.z;
        }        
    }

}