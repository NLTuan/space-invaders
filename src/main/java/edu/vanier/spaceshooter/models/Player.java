package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Player extends FiringSprite{
    private double stage = 2;

    private double internalShootingClock;
    private double shootCooldown = 0.5;

    public Player(int x, int y, int width, int height, String type, Color color, double speed, double bulletSpeed) {
        super(x, y, width, height, type, color, speed, bulletSpeed);
        setFiringCooldown(0.1);
    }

    public void updateStage(){
        if(stage != 3){
            stage += 1;
        }
        else {
            stage = 1;
        }
    }

    public ArrayList<Sprite> shoot(){
        int width = 5;
        int height = 20;
        ArrayList<Sprite> bullets = new ArrayList<>();
        if (stage == 1){
            bullets.add(new Sprite(
                    (int) (getTranslateX() + getWidth()/2 - (double) width /2),
                    (int) getTranslateY(),
                    width, height,
                    getType() + "bullet", Color.BLACK, getBulletSpeed(),
                    new Vector(0, -1)
                )
            );
        }
        else if (stage == 2){
            bullets.add(new Sprite(
                    (int) (getTranslateX() + getWidth()/3 - (double) width/2),
                    (int) getTranslateY(),
                    width, height,
                    getType() + "bullet", Color.BLACK, getBulletSpeed(),
                    new Vector(-0.25, -1)));

            bullets.add(new Sprite(
                    (int) (getTranslateX() + getWidth()* 2/3 - (double) width/2),
                    (int) getTranslateY(),
                    width, height,
                    getType() + "bullet", Color.BLACK, getBulletSpeed(),
                    new Vector(0, -1)));

            bullets.add(new Sprite(
                    (int) (getTranslateX() + getWidth()* 2/3 - (double) width/2),
                    (int) getTranslateY(),
                    width, height,
                    getType() + "bullet", Color.BLACK, getBulletSpeed(),
                    new Vector(0.25, -1)));

        }
        return bullets;
    }

    public double getStage() {
        return stage;
    }

    public void setStage(double stage) {
        this.stage = stage;
    }

    public double getInternalShootingClock() {
        return internalShootingClock;
    }

    public void setInternalShootingClock(double internalShootingClock) {
        this.internalShootingClock = internalShootingClock;
    }

    public double getShootCooldown() {
        return shootCooldown;
    }

    public void setShootCooldown(double shootCooldown) {
        this.shootCooldown = shootCooldown;
    }
}
