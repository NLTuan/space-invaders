package edu.vanier.spaceshooter.models;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class FiringSprite extends Sprite{
    private double bulletSpeed;

    private double firingCooldown;

    public FiringSprite(int x, int y, int width, int height, String type, Color color, double speed, double bulletSpeed) {
        super(x, y, width, height, type, color, speed);
        this.bulletSpeed = bulletSpeed;
    }

    public abstract ArrayList<Sprite> shoot();

    public double getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(double bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public double getFiringCooldown() {
        return firingCooldown;
    }

    public void setFiringCooldown(double firingCooldown) {
        this.firingCooldown = firingCooldown;
    }
}
