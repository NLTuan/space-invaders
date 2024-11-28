package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.spaceshooter.SpaceShooterApp;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class SmallInvader extends Invader{
    private String laserString = "/PNG/laserBlue01.png";
    
    public SmallInvader(int x, int y, int width, int height, String type, String imagePath, double speed, double bulletSpeed) {
        super(x, y, width, height, type, imagePath, speed, bulletSpeed);
        setFiringCooldown(2);
        setMovementCooldown(2);
        setPauseCooldown(0.8);
        
        setDeltaClock(Math.random() * 3); // offset the movement of the small invaders
    }

    public ArrayList<Sprite> shoot() {
        int width = 30;
        int height = 2;
        ArrayList<Sprite> bullets = new ArrayList<>();
        bullets.add(new Sprite(
                (int) (getTranslateX() + getFitWidth()/2 - (double) width /2),
                (int) getTranslateY(),
                width, height,
                getType() + "bullet", laserString,
                getBulletSpeed(),
                new Vector(0, 1)
            )
        );

        return bullets;
    }

    public void updateMovement(){
        Random random = new Random();
        Vector direction = new Vector(random.nextDouble() * 2 - 1, random.nextDouble() * 2 - 1);
        if((getTranslateX() < SpaceShooterApp.screenWidth * 0.3 && direction.getX() < 0)
            || (getTranslateX() + getFitWidth() > SpaceShooterApp.screenWidth * 0.3 && direction.getX() > 0)){
            direction.setX(-direction.getX());
        }
        else if((getTranslateY() < SpaceShooterApp.screenHeight * 0.3 && direction.getY() < 0)
            || (getTranslateY() + getFitHeight() > SpaceShooterApp.screenHeight * 0.7 && direction.getY() > 0)){
            direction.setY(-direction.getY());
        }
        setDirection(direction);
    }
    
}
