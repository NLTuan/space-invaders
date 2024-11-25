package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import javafx.scene.paint.Color;

public abstract class Invader extends FiringSprite {

    private double movementCooldown; // When this timer hits, the invader changes its movement

    public Invader(int x, int y, int width, int height, String type, Color color, double speed, double bulletSpeed) {
        super(x, y, width, height, type, color, speed, bulletSpeed);
    }

    public abstract void updateMovement();

    public double getMovementCooldown() {
        return movementCooldown;
    }

    public void setMovementCooldown(double movementCooldown) {
        this.movementCooldown = movementCooldown;
    }

}
