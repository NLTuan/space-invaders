package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {

    private boolean dead = false;
    private final String type;
    
    private double speed = 20;
    
    private Vector direction;

    private double internalClock;
    public Sprite(int x, int y, int width, int height, String type, Color color, double speed) {
        super(width, height, color);

        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
        
        this.speed = speed;
        direction = new Vector(0, 0);

    }

    public Sprite(int x, int y, int width, int height, String type, Color color, double speed, Vector direction) {
        super(width, height, color);

        this.type = type;
        setTranslateX(x);
        setTranslateY(y);

        this.speed = speed;
        this.direction = direction.normalized();
    }

    public void move(double elapsedTime){
        Vector move = direction.scalarMultiply(speed).scalarMultiply(elapsedTime);
        Vector position = new Vector(getTranslateX(), getTranslateY());
        Vector finalPos = position.add(move);
        setTranslateX(finalPos.getX());
        setTranslateY(finalPos.getY());
        
    }

    public boolean isDead() {
        return dead;
    }

    public String getType() {
        return type;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction.normalized();
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
