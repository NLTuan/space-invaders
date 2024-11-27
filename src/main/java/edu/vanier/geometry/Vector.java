/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.geometry;

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
    
    public Vector scalarMultiply(double scalar){
        return new Vector(scalar * x, scalar * y);
    }
    
    public Vector negative(){
        return new Vector(-x, -y);
    }

    public Vector normalized(){
        if (x == 0 && y == 0){
            return this;
        }
        double norm = Math.sqrt(x * x + y * y);
        return scalarMultiply(1/norm);
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

    @Override
    public String toString() {
        return "Vector{" + "x=" + x + ", y=" + y + '}';
    }
    
}
