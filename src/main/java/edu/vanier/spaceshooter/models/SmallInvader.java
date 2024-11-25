package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class SmallInvader extends Invader{
    public SmallInvader(int x, int y, int width, int height, String type, Color color, double speed, double bulletSpeed) {
        super(x, y, width, height, type, color, speed, bulletSpeed);
        setFiringCooldown(0.5);
        setMovementCooldown(2);
        setPauseCooldown(0.8);
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
        setDirection(new Vector(random.nextDouble() * 2 - 1, random.nextDouble() * 2 - 1));
    }
}
