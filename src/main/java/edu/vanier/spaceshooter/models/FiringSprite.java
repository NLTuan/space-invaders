package edu.vanier.spaceshooter.models;

import java.util.ArrayList;

/**
 * The class that allows its child classes to shoot a bullet. It contains a bullet
 * speed and a cooldown and a an abstract shoot method.
 * @author letua
 */
public abstract class FiringSprite extends Sprite{
    private double bulletSpeed;

    private double firingCooldown;

    public FiringSprite(int x, int y, int width, int height, String type, String imagePath, double speed, double bulletSpeed) {
        super(x, y, width, height, type, imagePath, speed);
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
