package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import javafx.scene.paint.Color;

public abstract class Invader extends FiringSprite {

    private double movementCooldown = 1; // When this timer hits, the invader changes its movement
    private boolean movementUpdated = false; // When this is true, the movement just got updated
    private double pauseCooldown = 0.01; // When this timer hits, the invader remains stationary
    private boolean pauseUpdated = true; // When this is true, the invader just got updated to pause

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

    public double getPauseCooldown() {
        return pauseCooldown;
    }

    public void setPauseCooldown(double pauseCooldown) {
        this.pauseCooldown = pauseCooldown;
    }

    public boolean isMovementUpdated() {
        return movementUpdated;
    }

    public void setMovementUpdated(boolean movementUpdated) {
        this.movementUpdated = movementUpdated;
    }

    public boolean isPauseUpdated() {
        return pauseUpdated;
    }

    public void setPauseUpdated(boolean pauseUpdated) {
        this.pauseUpdated = pauseUpdated;
    }
}
