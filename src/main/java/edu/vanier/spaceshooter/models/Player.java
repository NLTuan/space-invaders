package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.spaceshooter.controllers.MainAppFXMLController;
import java.io.File;

import java.util.ArrayList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Player extends FiringSprite{
    private int stage = 1;
    private int maxStage = 0;

    private double internalShootingClock;
    
    private String laserString = MainAppFXMLController.getSpriteMap().get("playerLaser");
    
    private int lives = 5;

    public Player(int x, int y, int width, int height, String type, String imagePath, double speed, double bulletSpeed) {
        super(x, y, width, height, type, imagePath, speed, bulletSpeed);
    }

    public void updateStage(){
        if(maxStage == 1){return;}

        String musicFile = "/sfx/changeWeapon.wav";
        Media sound = new Media(getClass().getResource(musicFile).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        if(stage != maxStage){
            stage += 1;
        }
        else {
            stage = 1;
        }
    }

    public ArrayList<Sprite> shoot(){
        
        String musicFile = "/sfx/playerLaser.wav";     // For example

        Media sound = new Media(getClass().getResource(musicFile).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        int width = 50;
        int height = 10;
        ArrayList<Sprite> bullets = new ArrayList<>();
        switch (stage) {
            case 1 -> {
                setFiringCooldown(0.001);
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
                setFiringCooldown(0.5);
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
                setFiringCooldown(0.7);
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
        return bullets;
    }

    public void levelUp(){
        
        String musicFile = "/sfx/levelUp.wav";     // For example

        Media sound = new Media(getClass().getResource(musicFile).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
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
