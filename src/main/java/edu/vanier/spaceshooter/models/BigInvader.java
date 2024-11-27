/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author letua
 */
public class BigInvader extends Invader{
    
    Sprite target;
    double momentum;
    
    public BigInvader(int x, int y, int width, int height, String type, Color color, double speed, double bulletSpeed, Sprite target, double momentum) {
        super(x, y, width, height, type, color, speed, bulletSpeed);
        setFiringCooldown(0.1);
        setMovementCooldown(0.01);
        setPauseCooldown(0.01);
        
        this.target = target;
        this.momentum = momentum;
    }

    public ArrayList<Sprite> shoot() {
        int width = 60;
        int height = 30;
        ArrayList<Sprite> bullets = new ArrayList<>();
        bullets.add(new BigBullet(
                (int) (getTranslateX() + getWidth()/2 - (double) width /2),
                (int) getTranslateY(),
                width, height,
                getType() + "bullet", Color.BLACK,
                getBulletSpeed(),
                target,
                momentum
            )
        );

        return bullets;
    }

    public void updateMovement(){
        setDirection(new Vector(0, 0));
    }
    
}
