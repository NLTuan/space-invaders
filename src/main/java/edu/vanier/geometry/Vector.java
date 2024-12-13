/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.geometry;

/**
 * A 2D vector class to handle direction when it comes to movement. 
 * Contains 2 variables x and y
 * @author Le Tuan Huy Nguyen
 */
public class Vector {
    private double x;
    private double y;
    
    /**
     * Constructor for Vector
     * @param x x component
     * @param y y component
     */
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * Adds 2 Vectors together
     * @param other the vector to be added to the current one
     * @return the other vector + this vector elementwise
     */
    public Vector add(Vector other){
        return new Vector(x + other.getX(), y + other.getY());
    }
    
    /**
     * Multiplies each element of the current vector by a scalar
     * @param scalar the scalar to be applied
     * @return The current vector with each of its components multiplied by the scalar
     */
    public Vector scalarMultiply(double scalar){
        return new Vector(scalar * x, scalar * y);
    }
    
    /**
     * The current vector with its values times -1
     * @return The current vector with its components * -1
     */
    public Vector negative(){
        return new Vector(-x, -y);
    }

    /**
     * Divides the vector by its norm to get a unit vector, very useful for 
     * directions when it comes to movement.
     * @return The current vector normalized.
     */
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
