package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.helpers.AudioPlayer;
import edu.vanier.spaceshooter.SpaceShooterApp;

import java.util.ArrayList;
import java.util.Random;

/**
 * Small Invader #2: shoots 2 stars diagonally down, moves in random directions and pauses
 * @author Le Tuan Huy Nguyen
 */
public class SmallInvader2 extends Invader{
    private String laserString = "bulletSmall.png";
    
    /**
     * Constructor for SmallInvader2
     * @param x the x position os the sprite
     * @param y the y position of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param imagePath the path of the image sprite
     * @param speed the speed of the invader
     * @param bulletSpeed the bullet speed
     */
    public SmallInvader2(int x, int y, int width, int height, String imagePath, double speed, double bulletSpeed) {
        super(x, y, width, height, imagePath, speed, bulletSpeed);
        setFiringCooldown(3);
        setMovementCooldown(2);
        setPauseCooldown(0.8);
        setHitpoints(2);
        setHpMax(2);
        
        setDeltaClock(Math.random() * 3); // offset the movement of the small invaders
    }

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
                new Vector(1, 1)
            )
        );
        bullets.add(new Sprite(
                (int) (getTranslateX() + getFitWidth()/2 - (double) width /2),
                (int) getTranslateY(),
                width, height,
                getType() + "bullet",
                laserString,
                getBulletSpeed(),
                new Vector(-1, 1)
            )
        );

        return bullets;
    }

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
