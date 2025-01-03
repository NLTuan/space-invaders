package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.helpers.AudioPlayer;
import edu.vanier.spaceshooter.SpaceShooterApp;

import java.util.ArrayList;
import java.util.Random;

/**
 * Small Invader #1: shoots a star straight down, moves in random directions and pauses
 * @author Le Tuan Huy Nguyen
 */
public class SmallInvader1 extends Invader{
    private String laserString = "bulletSmall.png";
    
    /**
     * Constructor for SmallInvader1
     * @param x the x position of the sprite
     * @param y the y position of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param imagePath the path of the image sprite
     * @param speed the speed of the invader
     * @param bulletSpeed the bullet speed
     */
    public SmallInvader1(int x, int y, int width, int height, String imagePath, double speed, double bulletSpeed) {
        super(x, y, width, height, imagePath, speed, bulletSpeed);
        setFiringCooldown(3);
        setMovementCooldown(2);
        setPauseCooldown(0.8);
        setHitpoints(2);
        setHpMax(2);
        
        setDeltaClock(Math.random() * 3); // offset the movement of the small invaders
    }

    /**
     * Shoot method for Small Invader #1. Shoots a square projectile straight down
     * @return an ArrayList of bullets of type Sprite
     */
    public ArrayList<Sprite> shoot() {
        AudioPlayer smallLaserTune = new AudioPlayer("/sfx/smallInvaderLaser.wav");
        smallLaserTune.play();
        
        int width = 40;
        int height = 40;
        ArrayList<Sprite> bullets = new ArrayList<>();
        bullets.add(new Sprite(
                (int) (getTranslateX() + getFitWidth()/2 - (double) width /2),
                (int) getTranslateY(),
                width, height,
                getType() + "bullet",
                laserString,
                getBulletSpeed(),
                new Vector(0, 1)
            )
        );

        return bullets;
    }

    /**
     * Movement update method for Small Invader #1. This invader moves in random
     * directions. If it is about to go out of the screen, its direction is flipped
     * to make it go back in.
     */
    public void updateMovement(){
        Random random = new Random();
        Vector direction = new Vector(random.nextDouble() * 2 - 1, random.nextDouble() * 2 - 1);
        if((getTranslateX() < SpaceShooterApp.screenWidth * 0.3 && direction.getX() < 0)
            || (getTranslateX() + getFitWidth() > SpaceShooterApp.screenWidth * 0.7 && direction.getX() > 0)){
            direction.setX(-direction.getX());
        }
        if((getTranslateY() < SpaceShooterApp.screenHeight * 0.3 && direction.getY() < 0)
            || (getTranslateY() + getFitHeight() > SpaceShooterApp.screenHeight * 0.7 && direction.getY() > 0)){
            direction.setY(-direction.getY());
        }
        setDirection(direction);
    }
    
}
