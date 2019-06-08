package edu.udc.cg;

/**
 * Vector
 */
public class Vector {
    double x, y, z;

    public Vector(double x, double y, double z){
        double length = Math.sqrt(x*x + y*y + z*z);
        if(length > 0){
            this.x = x/length;
            this.y = y/length;
            this.z = z/length;
        }
        else{
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }
        
    }

    Vector crossProduct(Vector v){
        return new Vector(
            y*v.z - z * v.y, 
            z*v.x - x * v.z, 
            x*v.y - y * v.x
        );
    }

}