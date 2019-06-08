package edu.udc.cg;

import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * Hello world!
 *
 */
public class App extends JFrame
{

    private static final long serialVersionUID = 1L;
    static JFrame frame = new App();
    Screen screenObject = new Screen();

    public App(){
        add(screenObject);
        setUndecorated(true);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main( String[] args )
    {
        
    }
}
