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

    static double[] viewFrom = new double[] { 10, 10, 10 };
    static double[] viewTo = new double[] { -10, 0, 0 };

    static PolygonObject3D[] polygons3D = new PolygonObject3D[100];
    static PolygonObject[] drawablePolygons = new PolygonObject[100];
    static int numberOfPolygons = 0;
    static int numberOfPolygons3D = 0;
    int[] newOrder;

    public Screen() {
        polygons3D[numberOfPolygons3D] = new PolygonObject3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2},
                new double[] {0, 0, 0, 0 }, Color.GRAY);
        polygons3D[numberOfPolygons3D] = new PolygonObject3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 2, 2},
                new double[] {3, 3, 3, 3 }, Color.GRAY);
        polygons3D[numberOfPolygons3D] = new PolygonObject3D(new double[]{0, 2, 2, 0}, new double[]{0, 0, 0, 0},  
                new double[]{0, 0, 3, 3}, Color.GRAY);
        polygons3D[numberOfPolygons3D] = new PolygonObject3D(new double[]{0, 2, 2, 0}, new double[]{2, 2, 2, 2},  
                new double[]{0, 0, 3, 3}, Color.gray);
        polygons3D[numberOfPolygons3D] = new PolygonObject3D(new double[]{0, 0, 0, 0}, new double[]{0, 2, 2, 0},  
                new double[]{0, 0, 3, 3}, Color.gray);
        polygons3D[numberOfPolygons3D] = new PolygonObject3D(new double[]{2, 2, 2, 2}, new double[]{0, 2, 2, 0},  
                new double[]{0, 0, 3, 3}, Color.gray);

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

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            viewFrom[0]--;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            viewFrom[0]++;
        if(e.getKeyCode() == KeyEvent.VK_UP)
            viewFrom[1]++;
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            viewFrom[1]--;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}