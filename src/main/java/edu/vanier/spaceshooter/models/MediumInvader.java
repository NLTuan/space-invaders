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
        setFiringCooldown(0.5);
        setMovementCooldown(0.2);
        setPauseCooldown(0.01);
    }

    public ArrayList<Sprite> shoot() {
        int width = 10;
        int height = 10;
        ArrayList<Sprite> bullets = new ArrayList<>();
        bullets.add(new Sprite(
                        (int) (getTranslateX() + getWidth()/2 - (double) width /2),
                        (int) getTranslateY(),
                        width, height,
                        getType() + "bullet", Color.BLACK,
                        getBulletSpeed(),
                        new Vector(0, movingUp ? -1 : 1)
                )
        );

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
