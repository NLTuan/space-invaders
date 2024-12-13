package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.helpers.AudioPlayer;
import edu.vanier.spaceshooter.SpaceShooterApp;
import java.util.ArrayList;

/**
 * BigInvader or Boss: Shoots 5 homing (BigBullet) missiles at the player, moves side to side only
 * 3 missiles are big but slow, 2 missiles are smaller but faster
 * @author Le Tuan Huy Nguyen
 */
public class BigInvader extends Invader{
    
    Sprite target;
    double momentum;
    
    private String laserString = "bulletBig.png";
    int xDir = 1;
    
    /**
     * Constructor for BigInvader
     * @param x the x position of the sprite
     * @param y the y position of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param imagePath the path of the image sprite
     * @param speed the speed of the invader
     * @param bulletSpeed the bullet speed
     * @param target the target that the BigInvader shoots at 
     * @param momentum lagging term for the BigBullet
     */
    
    public BigInvader(int x, int y, int width, int height, String imagePath, double speed, double bulletSpeed, Sprite target, double momentum) {
        super(x, y, width, height, imagePath, speed, bulletSpeed);
        setFiringCooldown(0.75);
        setMovementCooldown(3);
        setPauseCooldown(0.5);
        
        this.target = target;
        this.momentum = momentum;
        
        setHitpoints(50);
        setHpMax(50);
        
        setDeltaClock(Math.random() * 5);

    }

    /**
     * Shoot method for Big Invader. Shoots 5 BigBullets
     * 3 big but slower direction bullets, and 2 smaller but quicker bullets.
     * The bullets spread out at first then follow the player.
     * @return an ArrayList of bullets of type Sprite
     */
    public ArrayList<Sprite> shoot() {
        
        AudioPlayer bigLaserTune = new AudioPlayer("/sfx/bigInvaderLaser.wav");
        bigLaserTune.play();
        
        int width = 80;
        int height = 20;
        ArrayList<Sprite> bullets = new ArrayList<>();
        bullets.add(new BigBullet(
                (int) (getTranslateX() + getFitWidth()/2 - (double) width /2),
                (int) getTranslateY(),
                width, height,
                getType() + "bullet", 
                laserString,
                getBulletSpeed(),
                target,
                momentum
            )
        );
        
        bullets.add(new BigBullet(
                (int) (getTranslateX() + getFitWidth()/2 - (double) width /2),
                (int) getTranslateY(),
                width / 2, height / 2,
                getType() + "bullet", 
                laserString,
                getBulletSpeed(),
                new Vector(-1, 0),
                target,
                momentum * 1.7
            )
        );
        bullets.add(new BigBullet(
                (int) (getTranslateX() + getFitWidth()/2 - (double) width /2),
                (int) getTranslateY(),
                width / 2, height / 2,
                getType() + "bullet", 
                laserString,
                getBulletSpeed(),
                new Vector(1, 0),
                target,
                momentum * 1.7
            )
        );
        
        bullets.add(new BigBullet(
                (int) (getTranslateX() + getFitWidth()/2 - (double) width /2),
                (int) getTranslateY(),
                width, height,
                getType() + "bullet", 
                laserString,
                getBulletSpeed(),
                new Vector(-1, 1),
                target,
                momentum
            )
        );
        bullets.add(new BigBullet(
                (int) (getTranslateX() + getFitWidth()/2 - (double) width /2),
                (int) getTranslateY(),
                width, height,
                getType() + "bullet", 
                laserString,
                getBulletSpeed(),
                new Vector(1, 1),
                target,
                momentum
            )
        );

        return bullets;
    }

    /**
     * Movement update method for Big Invader. This invader moves 
     * left or right and switches direction at a certain width of the screen.
     */
    public void updateMovement(){
        if (((getTranslateX() + getFitWidth() > SpaceShooterApp.screenWidth * 0.8) && xDir > 0)
                || ((getTranslateX() < SpaceShooterApp.screenWidth * 0.2) && xDir < 0)
        ){
            xDir = -xDir;
        }
        setDirection(new Vector(xDir, 0));

    }
    
}
