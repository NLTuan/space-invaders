package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.helpers.AudioPlayer;
import edu.vanier.spaceshooter.SpaceShooterApp;

import java.util.ArrayList;
import java.util.Random;

/**
 * Medium Invader #2: Moves either to the side or down, once it reached a certain point, it moves up
 * Shoots bullets in a plus (+) or cross (x) or 8-star formation, 
 * but smaller bullets and faster cooldown than Medium Invader #1
 * @author Le Tuan Huy Nguyen
 */
public class MediumInvader2 extends Invader {
    
    private boolean movingUp = false;
    
    private String laserString = "bulletMedium.png";

    /**
     * Constructor for MediumInvader2
     * @param x the x position of the sprite
     * @param y the y position of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param imagePath the path of the image sprite
     * @param speed the speed of the invader
     * @param bulletSpeed the bullet speed
     */
    public MediumInvader2(int x, int y, int width, int height, String imagePath, double speed, double bulletSpeed) {
        super(x, y, width, height, imagePath, speed, bulletSpeed);
        setFiringCooldown(3);
        setMovementCooldown(0.2);
        setPauseCooldown(0.01);
        setHitpoints(5);
        setHpMax(5);
        
        setDeltaClock(Math.random() * 3);
    }

    /**
     * Shoot method for Medium Invader #2. Shoots 4 small projectiles
     * it can either be in a plus (+) formation, cross (x) formation, or in an
     * 8 point star formation.
     * @return an ArrayList of bullets of type Sprite
     */
    public ArrayList<Sprite> shoot() {
        
        AudioPlayer mediumLaserTune = new AudioPlayer("/sfx/mediumInvaderLaser.wav");
        mediumLaserTune.play();
        
        int width = 30;
        int height = 10;
        ArrayList<Sprite> bullets = new ArrayList<>();
        
        Random random = new Random();
        int cond = random.nextInt(6) - 3;
        if (cond < 0) {
            for(int i = -1; i < 2; i+=2){
                bullets.add(new Sprite(
                (int) (getTranslateX() + getFitWidth()/2 - (double) width /2),
                (int) getTranslateY(),
                width, height,
                getType() + "bullet",
                laserString,
                getBulletSpeed(),
                new Vector(i, 0)
                ));

                bullets.add(new Sprite(
                (int) (getTranslateX() + getFitWidth()/2 - (double) width /2),
                (int) getTranslateY(),
                width, height,
                getType() + "bullet",
                laserString,
                getBulletSpeed(),
                new Vector(0, i)
                ));
            }
        }

        else if (cond > 0){
                for(int i = -1; i < 2; i+=2){
                    for (int j = -1; j < 2; j+=2) {
                        bullets.add(new Sprite(
                        (int) (getTranslateX() + getFitWidth()/2 - (double) width /2),
                        (int) getTranslateY(),
                        width, height,
                        getType() + "bullet",
                        laserString,
                        getBulletSpeed(),
                        new Vector(i, j)
                        ));
                    }
                }
        }
                
        else if(cond == 0){
            for(int i = -1; i < 2; i++){
                for (int j = -1; j < 2; j++) {
                    if (i == 0 && j == 0){
                        continue;
                    }
                    bullets.add(new Sprite(
                    (int) (getTranslateX() + getFitWidth()/2 - (double) width /2),
                    (int) getTranslateY(),
                    width, height,
                    getType() + "bullet",
                    laserString,
                    getBulletSpeed(),
                    new Vector(i, j)
                    ));
                }
            }
        }
        return bullets;

        }


    /**
     * Movement update method for Medium Invader #2. This invader moves diagonally
     * up and down. This invader moves down until a certain point, then goes back
     * up, then goes back down at a certain point. On each time this method is 
     * called, the invader changes direction to left or right.
     */
    public void updateMovement(){
        if (getTranslateY() + getFitHeight() > SpaceShooterApp.screenHeight * 0.8){
            movingUp = true;
        }
        else if (getTranslateY() < SpaceShooterApp.screenHeight * 0.2){
            movingUp = false;
        }
        Random random = new Random();
        int cond = random.nextInt(4);
        double x = 0, y = movingUp ? -1: 1;
        switch (cond){
            case 0 -> {
                x = 1;
                if (getTranslateX()+ getFitWidth() > SpaceShooterApp.screenWidth * 0.8){
                    x = -1;
                }
            }
            case 1 -> {
                x = -1;
                if (getTranslateX() < SpaceShooterApp.screenWidth * 0.2){
                    x = 1;
                }
            }
        }
        setDirection(new Vector(x, y));
    }
}
