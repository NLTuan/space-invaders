package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.spaceshooter.SpaceShooterApp;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends ImageView {

    private boolean dead = false;
    private final String type;
    
    private double speed = 20;
    
    private Vector direction;

    public Sprite(int x, int y, int width, int height, String type, String imagePath, double speed) {
        super();
        setImage(new Image(getClass().getResource("/sprite_images/" + imagePath).toExternalForm()));
        setFitWidth(width);
        setFitHeight(height);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
        
        this.speed = speed;
        direction = new Vector(0, 0);
        rotateBullet();
    }

    public Sprite(int x, int y, int width, int height, String type, String imagePath ,double speed, Vector direction) {
        super();
        setImage(new Image(getClass().getResource("/sprite_images/" + imagePath).toExternalForm()));
        setFitWidth(width);
        setFitHeight(height);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);

        this.speed = speed;
        this.direction = direction.normalized();
        rotateBullet();
    }

    public void move(double elapsedTime){
        Vector move = direction.scalarMultiply(speed).scalarMultiply(elapsedTime);
        Vector position = new Vector(getTranslateX(), getTranslateY());
        Vector finalPos = position.add(move);
        
        // Handle player from going out of bounds
        if(this instanceof Player){
            if ((finalPos.getX() < 0 || finalPos.getX() + getFitWidth() > SpaceShooterApp.screenWidth)
                    && (finalPos.getY() < 0 || finalPos.getY() + getFitHeight()> SpaceShooterApp.screenHeight)){}
            else if (finalPos.getX() < 0 || finalPos.getX() + getFitWidth() > SpaceShooterApp.screenWidth){
                setTranslateY(finalPos.getY());
            }
            else if (finalPos.getY() < 0 || finalPos.getY() + getFitHeight()> SpaceShooterApp.screenHeight){
                setTranslateX(finalPos.getX());
            }
            else{
                setTranslateX(finalPos.getX());
                setTranslateY(finalPos.getY());
            }
        }
        else{
            setTranslateX(finalPos.getX());
            setTranslateY(finalPos.getY());
        }
        
        rotateBullet();
    }
    
    private void rotateBullet(){
        // Handle bullet rotation
        if(getType().contains("bullet")){
            if (direction.getX() != 0){
                if (direction.getX() < 0){
                    setRotate(180 + (Math.atan(direction.getY()/direction.getX()) * 180 / Math.PI));
                }
                else{
                    setRotate(Math.atan(direction.getY()/direction.getX()) * 180 / Math.PI);
                }
            }
            else if (direction.getX() != 0){
                setRotate(Math.atan(direction.getY()/direction.getX()) * 180 / Math.PI);
            }
            else if (direction.getX() == 0 && direction.getY() != 0){
                setRotate(direction.getY() * 90);
            }
            else{
                setRotate(0);
            }
        }
    }

    

    public boolean isDead() {
        return dead;
    }

    public String getType() {
        return type;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction.normalized();
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    
}
