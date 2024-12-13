package edu.vanier.spaceshooter.models;

import java.util.ArrayList;

/**
 * The class that allows its child classes to shoot a bullet. It contains a bullet
 * speed and a cooldown that restricts firing and a an abstract shoot method. 
 * This method enforces the object to fire an ArrayList of Sprites (which should
 * be bullets)
 * @author Le Tuan Huy Nguyen
 */
public abstract class FiringSprite extends Sprite{
    private double bulletSpeed;

    private double firingCooldown;

    /**
     * Constructor for FiringSprite
     * @param x the x position of the sprite
     * @param y the y position of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param type the type of the sprite as a string (enemy, bullet, player)
     * @param imagePath the path of the image sprite
     * @param speed the speed of the sprite
     * @param bulletSpeed the speed of the bullet being shot
     */
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
