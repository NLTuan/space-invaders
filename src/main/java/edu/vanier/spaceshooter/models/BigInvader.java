/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.helpers.AudioPlayer;
import edu.vanier.spaceshooter.SpaceShooterApp;
import edu.vanier.spaceshooter.controllers.MainAppFXMLController;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

/**
 *
 * @author letua
 */
public class BigInvader extends Invader{
    
    Sprite target;
    double momentum;
    
    private String laserString = "bulletBig.png";
    int xDir = 1;
    
    public BigInvader(int x, int y, int width, int height, String type, String imagePath, double speed, double bulletSpeed, Sprite target, double momentum) {
        super(x, y, width, height, type, imagePath, speed, bulletSpeed);
        setFiringCooldown(0.75);
        setMovementCooldown(3);
        setPauseCooldown(0.5);
        
        this.target = target;
        this.momentum = momentum;
        
        setHitpoints(50);
        setHpMax(50);
        
        setDeltaClock(Math.random() * 5);

    }

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

    public void updateMovement(){
        if (((getTranslateX() + getFitWidth() > SpaceShooterApp.screenWidth * 0.8) && xDir > 0)
                || ((getTranslateX() < SpaceShooterApp.screenWidth * 0.2) && xDir < 0)
        ){
            xDir = -xDir;
        }
        setDirection(new Vector(xDir, 0));

    }
    
}
