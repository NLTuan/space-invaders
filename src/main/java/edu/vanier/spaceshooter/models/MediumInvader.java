package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.spaceshooter.SpaceShooterApp;
import edu.vanier.spaceshooter.controllers.MainAppFXMLController;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class MediumInvader extends Invader {

    public MediumInvader(int x, int y, int width, int height, String type, Color color, double speed, double bulletSpeed) {
        super(x, y, width, height, type, color, speed, bulletSpeed);
        setFiringCooldown(0.1);
        setMovementCooldown(0.01);
        setPauseCooldown(0.01);
    }

    public ArrayList<Sprite> shoot() {
        int width = 5;
        int height = 20;
        ArrayList<Sprite> bullets = new ArrayList<>();
        bullets.add(new Sprite(
                        (int) (getTranslateX() + getWidth()/2 - (double) width /2),
                        (int) getTranslateY(),
                        width, height,
                        getType() + "bullet", Color.BLACK,
                        getBulletSpeed(),
                        new Vector(0, 1)
                )
        );

        return bullets;
    }

    public void updateMovement(){
        Random random = new Random();
        int cond = random.nextInt(4);
        double x = 0, y = 0;
        switch (cond){
            case 0:
                x = 1;
                if (getX() + getWidth() > 700){
                    x = -1;
                }
                break;
            case 2:
                x = -1;
                if (getX() < 300){
                    x = 1;
                }
                break;
            default:
                 y = 1;
        }
        setDirection(new Vector(x, y));
    }
}
