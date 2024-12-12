package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.helpers.AudioPlayer;
import edu.vanier.spaceshooter.SpaceShooterApp;
import edu.vanier.spaceshooter.controllers.MainAppFXMLController;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediumInvader2 extends Invader {
    
    private boolean movingUp = false;
    
    private String laserString = "bulletMedium.png";

    public MediumInvader2(int x, int y, int width, int height, String type, String imagePath, double speed, double bulletSpeed) {
        super(x, y, width, height, type, imagePath, speed, bulletSpeed);
        setFiringCooldown(3);
        setMovementCooldown(0.2);
        setPauseCooldown(0.01);
        setHitpoints(5);
        setHpMax(5);
        
        setDeltaClock(Math.random() * 3);
    }

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


    public void updateMovement(){
        if (getTranslateY() + getFitHeight() > SpaceShooterApp.screenHeight * 0.8){
            movingUp = true;
        }
        else if (getTranslateY() < SpaceShooterApp.screenHeight * 0.2){
            movingUp = false;
        }
        Random random = new Random();
        int cond = random.nextInt(4);
        double x = 0, y = 0;
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
            default -> y = movingUp ? -1: 1;
        }
        setDirection(new Vector(x, y));
    }
}
