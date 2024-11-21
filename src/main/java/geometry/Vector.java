/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package geometry;

/**
 *
 * @author 2387900
 */
public class Vector {
    private double x;
    private double y;
    
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public Vector add(Vector other){
        return new Vector(x + other.getX(), y + other.getY());
    }
    
    public Vector negative(){
        return new Vector(-x, -y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    
}
