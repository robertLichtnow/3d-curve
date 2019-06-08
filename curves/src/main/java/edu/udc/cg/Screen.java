package edu.udc.cg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

/**
 * Screen
 */
public class Screen extends JPanel implements KeyListener {

    private static final long serialVersionUID = 1L;

    double sleepTime = 1000 / 30, lastRefresh = 0;

    PolygonObject polygon;

    static double[] viewFrom = new double[] { 1,10, 20};
    static double[] viewTo = new double[] { 1, 1, 1 }; //Olhando pro centro do cubo

    static PolygonObject3D[] polygons3D = new PolygonObject3D[100];
    static PolygonObject[] drawablePolygons = new PolygonObject[100];
    static int numberOfPolygons = 0;
    static int numberOfPolygons3D = 0;
    int[] newOrder;
    Vector middlePoint = new Vector(1,1,1);

    Vector[] bezierPoints;
    int currentBezierPoint = 0;

    double bezierParameter = 0;
    double bezierFactor = 0.05;

    Vector[] controlPoints = {new Vector(0.75,3.45,1), new Vector(0.5,14.35,0), new Vector(12.15,14.64,0), new Vector(10.95,2.56,0)};

    public Screen() {
        polygons3D[numberOfPolygons3D] = new PolygonObject3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2},
                new double[] {0, 0, 0, 0 }, Color.GRAY);
        polygons3D[numberOfPolygons3D] = new PolygonObject3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2},
                new double[] {2, 2, 2, 2 }, Color.GRAY);
        polygons3D[numberOfPolygons3D] = new PolygonObject3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 0, 0},  
                new double[]{0, 0, 2, 2}, Color.GRAY);
        polygons3D[numberOfPolygons3D] = new PolygonObject3D(new double[]{0, 2, 2, 0}, new double[]{2, 2, 2, 2},  
                new double[]{0, 0, 2, 2}, Color.gray);
        polygons3D[numberOfPolygons3D] = new PolygonObject3D(new double[]{0, 0, 0, 0}, new double[]{0, 2, 2, 0},  
                new double[]{0, 0, 2, 2}, Color.gray);
        polygons3D[numberOfPolygons3D] = new PolygonObject3D(new double[]{2, 2, 2, 2}, new double[]{0, 2, 2, 0},  
                new double[]{0, 0, 2, 2}, Color.gray);

        calculateBezier();
        startBezier();

        addKeyListener(this);
        setFocusable(true);
    }

    void setOrder(){
		double[] k = new double[numberOfPolygons];
		newOrder = new int[numberOfPolygons];
		
		for(int i = 0; i < numberOfPolygons; i++)
		{
			k[i] = drawablePolygons[i].avgDistance;	
			newOrder[i] = i;
		}
		
	    double temp;
	    int tempr;	    
		for (int a = 0; a < k.length-1; a++)
			for (int b = 0; b < k.length-1; b++)
				if(k[b] < k[b + 1])
				{
					temp = k[b];
					tempr = newOrder[b];
					newOrder[b] = newOrder[b + 1];
					k[b] = k[b + 1];
					   
					newOrder[b + 1] = tempr;
					k[b + 1] = temp;
				}
	}

    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, 2000, 1200);
        g.drawString(System.currentTimeMillis() + "", 20, 20);

        for (int i = 0; i < numberOfPolygons3D; i++) {
            polygons3D[i].updatePolygon();
        }

        setOrder();

        for (int i = 0; i < numberOfPolygons; i++) {
            drawablePolygons[newOrder[i]].drawPolygon(g);
        }
        sleepAndRefresh();
    }

    void sleepAndRefresh() {
        while (true) {
            if ((System.currentTimeMillis() - lastRefresh) > sleepTime) {
                lastRefresh = System.currentTimeMillis();
                repaint();
                break;
            } else {
                try {
                    Thread.sleep((long) (System.currentTimeMillis() - lastRefresh));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void updateBezierForwardPosition(){
        if(this.currentBezierPoint == 19)
            return;
        
        Vector factor = this.bezierPoints[currentBezierPoint+1].vectorDifference(this.bezierPoints[currentBezierPoint]);

        this.translateObject(factor);

        this.currentBezierPoint++;
    }

    void updateBezierBackwardPosition(){
        if(this.currentBezierPoint == 0)
            return;
        
        Vector factor = this.bezierPoints[currentBezierPoint-1].vectorDifference(this.bezierPoints[currentBezierPoint]);

        this.translateObject(factor.multiplyClone(3));

        this.currentBezierPoint--;
    }

    void startBezier(){
        Vector vector = new Vector(0,0,0);
        for(int i=0;i<numberOfPolygons3D;i++){
            Vector middle = polygons3D[i].getMiddlePoint();
            vector.x += middle.x;
            vector.y += middle.y;
            vector.z += middle.z;
        }
        vector.x /=numberOfPolygons3D;
        vector.y /=numberOfPolygons3D;
        vector.z /=numberOfPolygons3D;

        this.translateObject(vector.vectorDifference(controlPoints[0]));
        
        System.out.println(polygons3D[0].getMiddlePoint().toString());
    }

    void translateObject(Vector factor){
        for(int i=0;i<numberOfPolygons3D;i++){
            polygons3D[i].translate(factor);
        }
    }

    public void calculateBezier() {
        this.bezierPoints = new Vector[20];
        double t = 0;
        for(int i=0;i<20;i++){
            double tx = (Math.pow((1 - t), 3) * controlPoints[0].x + (3 * t) * Math.pow((1 - t), 2) * controlPoints[1].x
					+ Math.pow((3 * t), 2) * (1 - t) * controlPoints[2].x + Math.pow(t, 3) * controlPoints[3].x);
			double ty =  (Math.pow((1 - t), 3) * controlPoints[0].y + (3 * t) * Math.pow((1 - t), 2) * controlPoints[1].y
                    + Math.pow((3 * t), 2) * (1 - t) * controlPoints[2].y + Math.pow(t, 3) * controlPoints[3].y);
			double tz =  (Math.pow((1 - t), 3) * controlPoints[0].z + (3 * t) * Math.pow((1 - t), 2) * controlPoints[1].z
                    + Math.pow((3 * t), 2) * (1 - t) * controlPoints[2].z + Math.pow(t, 3) * controlPoints[3].z);

            this.bezierPoints[i] = new Vector(tx, ty, tz);
            System.out.println(this.bezierPoints[i].toString());
                    
            t += this.bezierFactor;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            updateBezierForwardPosition();   
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            updateBezierBackwardPosition();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}