package edu.udc.cg;

/**
 * Calculator
 */
public class Calculator {

    static double drawX = 0, drawY = 0;

    static double calculatePositionX(double viewFrom[], double viewTo[], double x, double y, double z){
        setVariables(viewFrom, viewTo, x, y, z);
        return drawX;
    }
    
    static double calculatePositionY(double viewFrom[], double viewTo[], double x, double y, double z){
        setVariables(viewFrom, viewTo, x, y, z);
        return drawY;
    }
    
    static void setVariables(double viewFrom[], double viewTo[], double x, double y, double z){
        Vector viewVector = new Vector(viewTo[0]-viewFrom[0], viewTo[1]-viewFrom[1], viewTo[2]-viewFrom[2]);

        Vector directionVector = new Vector(1,1,1);

        Vector plane1 = viewVector.crossProduct(directionVector);
        Vector plane2 = viewVector.crossProduct(plane1);

        Vector viewToPoint = new Vector(x - viewFrom[0], y - viewFrom[1], z - viewFrom[2]);
        
        // Parametrizando a função para descobrir aonde o ponto calculado colido com o vetor view to point nos planos 
		double t = (viewVector.x * viewTo[0] + viewVector.y*viewTo[1] + viewVector.z*viewTo[2]
			   	-  (viewVector.x * viewFrom[0] + viewVector.y*viewFrom[1] + viewVector.z*viewFrom[2]))
				/  (viewVector.x * viewToPoint.x + viewVector.y*viewToPoint.y + viewVector.z*viewToPoint.z);
		
		x = viewFrom[0] + viewToPoint.x * t;
		y = viewFrom[1] + viewToPoint.y * t;
		z = viewFrom[2] + viewToPoint.z * t;
        
        // Se for menor que zero, o ponto está atrás da visão
		if(t > 0)
		{
			drawX = plane2.x * x + plane2.y * y + plane2.z * z;
			drawY = plane1.x * x + plane1.y * y + plane1.z * z;
		}
    }
}