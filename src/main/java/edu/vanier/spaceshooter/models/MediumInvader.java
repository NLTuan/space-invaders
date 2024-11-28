package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.spaceshooter.SpaceShooterApp;
import edu.vanier.spaceshooter.controllers.MainAppFXMLController;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class MediumInvader extends Invader {
    
    private boolean movingUp = false;

    public MediumInvader(int x, int y, int width, int height, String type, Color color, double speed, double bulletSpeed) {
        super(x, y, width, height, type, color, speed, bulletSpeed);
        setFiringCooldown(1);
        setMovementCooldown(0.2);
        setPauseCooldown(0.01);
    }

    public ArrayList<Sprite> shoot() {
        int width = 10;
        int height = 5;
        ArrayList<Sprite> bullets = new ArrayList<>();
        
        Random random = new Random();
        int cond = random.nextInt(3);
        switch (cond) {
            case 0:
                for(int i = -1; i < 2; i+=2){
                    bullets.add(new Sprite(
                    (int) (getTranslateX() + getWidth()/2 - (double) width /2),
                    (int) getTranslateY(),
                    width, height,
                    getType() + "bullet", Color.BLACK,
                    getBulletSpeed(),
                    new Vector(i, 0)
                    ));
                    
                    bullets.add(new Sprite(
                    (int) (getTranslateX() + getWidth()/2 - (double) width /2),
                    (int) getTranslateY(),
                    width, height,
                    getType() + "bullet", Color.BLACK,
                    getBulletSpeed(),
                    new Vector(0, i)
                    ));
                }
                break;

            case 1:
                for(int i = -1; i < 2; i+=2){
                    for (int j = -1; j < 2; j+=2) {
                        bullets.add(new Sprite(
                        (int) (getTranslateX() + getWidth()/2 - (double) width /2),
                        (int) getTranslateY(),
                        width, height,
                        getType() + "bullet", Color.BLACK,
                        getBulletSpeed(),
                        new Vector(i, j)
                        ));
                    }
                }
                break;
                
            default:
                for(int i = -1; i < 2; i++){
                    for (int j = -1; j < 2; j++) {
                        if (i == 0 && j == 0){
                            continue;
                        }
                        bullets.add(new Sprite(
                        (int) (getTranslateX() + getWidth()/2 - (double) width /2),
                        (int) getTranslateY(),
                        width, height,
                        getType() + "bullet", Color.BLACK,
                        getBulletSpeed(),
                        new Vector(i, j)
                        ));
                    }
                }
        }

        return bullets;
    }

    public void updateMovement(){
        if (getTranslateY() + getHeight() > SpaceShooterApp.screenHeight * 0.8){
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
                if (getTranslateX()+ getWidth() > SpaceShooterApp.screenWidth * 0.8){
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
