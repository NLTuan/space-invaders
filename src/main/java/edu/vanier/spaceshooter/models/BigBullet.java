/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.spaceshooter.SpaceShooterApp;
import javafx.scene.paint.Color;

/**
 *
 * @author letua
 */
public class BigBullet extends Sprite{

    Sprite target;
    double momentum;
    public BigBullet(int x, int y, int width, int height, String type, Color color, double speed, Sprite target, double momentum) {
        super(x, y, width, height, type, color, speed);
        setDirection(new Vector(0, 1));
        this.target = target;
        this.momentum = momentum;
    }
    
    public void updateDirection(){
        Vector position = new Vector(getTranslateX() + getWidth()/2, getTranslateY() + getHeight()/2);
        Vector targetPosition = new Vector(
                target.getTranslateX() + target.getWidth()/2,
                target.getTranslateY() + target.getHeight()/2
        );
        Vector newDirection = targetPosition.add(position.negative()).normalized();
        System.out.println(getDirection().scalarMultiply(1 - momentum).toString());
        Vector finalDirection = ((getDirection().scalarMultiply(1 - momentum)).add(newDirection.scalarMultiply(momentum))).normalized();
        setDirection(finalDirection);
        
    }
}
