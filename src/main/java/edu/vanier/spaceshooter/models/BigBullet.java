package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;

/**
 * Sprite class for the bullet shot by the BigInvader (boss)
 * This bullet takes a target and follows it with a lagging direction
 * @author Le Tuan Huy Nguyen
 */
public class BigBullet extends Sprite{

    Sprite target;
    double momentum;
    
    /**
     * Constructor with no initial direction ((0, 1) by default)
     * @param x initial x position
     * @param y initial y position
     * @param width the width
     * @param height the height
     * @param type the type as a string
     * @param imagePath the path of the big bullet image
     * @param speed the speed of the bullet
     * @param target the target sprite
     * @param momentum the lagging term
     */
    public BigBullet(int x, int y, int width, int height, String type, String imagePath, double speed, Sprite target, double momentum) {
        super(x, y, width, height, type, imagePath, speed);
        setDirection(new Vector(0, 1));
        this.target = target;
        this.momentum = momentum;
    }
    
    /**
     * Constructor with a directions
     * @param x initial x position
     * @param y initial y position
     * @param width the width
     * @param height the height
     * @param type the type as a string
     * @param imagePath the path of the big bullet image
     * @param speed the speed of the bullet
     * @param direction the initial direction
     * @param target the target sprite
     * @param momentum the lagging term
     */
    public BigBullet(int x, int y, int width, int height, String type, String imagePath, double speed, Vector direction,
            Sprite target, double momentum) {
        super(x, y, width, height, type, imagePath, speed);
        setDirection(direction);
        this.target = target;
        this.momentum = momentum;
    }
    
    /**
     * Updates the direction of the bullet based on where the target's position and a lagging term
     */
    public void updateDirection(){
        Vector position = new Vector(getTranslateX() + getFitWidth()/2, getTranslateY() + getFitHeight()/2);
        Vector targetPosition = new Vector(
                target.getTranslateX() + target.getFitWidth()/2,
                target.getTranslateY() + target.getFitHeight()/2
        );
        Vector newDirection = targetPosition.add(position.negative()).normalized();
        Vector finalDirection = ((getDirection().scalarMultiply(1 - momentum)).add(newDirection.scalarMultiply(momentum))).normalized();
        setDirection(finalDirection);
    }
}
