package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.helpers.AudioPlayer;
import edu.vanier.spaceshooter.controllers.MainAppFXMLController;
import java.io.File;

import java.util.ArrayList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Player extends FiringSprite{
    private int stage = 1;
    private int maxStage = 0;

    private double internalShootingClock;
    
    private String laserString = "playerLaser.png";
    
    private int lives = 5;
    
    private boolean crazyMode; 

    public Player(int x, int y, int width, int height, String type, String imagePath, double speed, double bulletSpeed) {
        super(x, y, width, height, type, imagePath, speed, bulletSpeed);
    }

    public void updateStage(){
        if(maxStage == 1){return;}
        
        if(stage != maxStage){
            stage += 1;
        }
        else {
            stage = 1;
        }
    }

    public ArrayList<Sprite> shoot(){
        
        AudioPlayer laserTune = new AudioPlayer("/sfx/playerLaser.wav");
        laserTune.setVolume(0.1);
        laserTune.play();
        
        int width = 50;
        int height = 10;
        ArrayList<Sprite> bullets = new ArrayList<>();
        switch (stage) {
            case 1 -> {
                setFiringCooldown(0.1);
                bullets.add(new Sprite(
                        (int) (getTranslateX() + getFitWidth()/2 - (double) width /2),
                        (int) getTranslateY(),
                        width, height,
                        getType() + "bullet", 
                        laserString,
                        getBulletSpeed(),
                        new Vector(0, -1)
                    )
                );
            }
            case 2 -> {
                setFiringCooldown(0.3);
                for (double i = -0.25; i < 0.26; i+=0.25) {
                    bullets.add(new Sprite(
                            (int) (getTranslateX() + getFitWidth()/3 - (double) width/2),
                            (int) getTranslateY(),
                            width, height,
                            getType() + "bullet",
                            laserString,
                            getBulletSpeed(),
                            new Vector(i, -1)));
                }
            }
            case 3 -> {
                setFiringCooldown(0.8);
                for (double i = -1; i < 1.1; i+=0.50) {
                    for (double j = -1; j < 1.1; j+=0.50) {
                        if (i == 0 && j==0) {
                            continue;
                        }
                        bullets.add(new Sprite(
                                (int) (getTranslateX() + getFitWidth()/3 - (double) width/2),
                                (int) getTranslateY(),
                                width, height,
                                getType() + "bullet", 
                                laserString,
                                getBulletSpeed(),
                                new Vector(i, j)));
                    }
                    
                }
            }
        }
        
        if (crazyMode){
            setFiringCooldown(0.02);
        }
        return bullets;
    }

    public void toggleCrazyMode(){
        crazyMode = !crazyMode;
        if (crazyMode){
            lives += 50000;
        }
        else{
            lives = 5;
        }
    }
    
    public void levelUp(){
        if(maxStage < 3){
            maxStage += 1;
            stage = maxStage;
        }
    }
    
    public double getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public double getInternalShootingClock() {
        return internalShootingClock;
    }

    public void setInternalShootingClock(double internalShootingClock) {
        this.internalShootingClock = internalShootingClock;
    }

    public int getMaxStage() {
        return maxStage;
    }

    public void setMaxStage(int maxStage) {
        this.maxStage = maxStage;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    
    public void reset(){
        lives = 5;
    }
    
}
