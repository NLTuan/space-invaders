package edu.vanier.spaceshooter.models;

import edu.vanier.geometry.Vector;
import edu.vanier.spaceshooter.SpaceShooterApp;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The class that allows any image to move given a direction. It can be used for
 * the player, enemy, or bullet.
 * @author Le Tuan Huy Nguyen
 */
public class Sprite extends ImageView {

    private boolean dead = false;
    private final String type;
    
    private double speed = 20;
    
    private Vector direction;

    /**
     * Constructor for sprite
     * @param x the x position of the sprite
     * @param y the y position of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param type the type of the sprite as a string (enemy, bullet, player)
     * @param imagePath the path of the image sprite
     * @param speed the speed of the sprite
     */
    public Sprite(int x, int y, int width, int height, String type, String imagePath, double speed) {
        super();
        setImage(new Image(getClass().getResource("/spriteimages/" + imagePath).toExternalForm()));
        setFitWidth(width);
        setFitHeight(height);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
        
        this.speed = speed;
        direction = new Vector(0, 0);
        rotateBullet();
    }

    /**
     * Constructor for sprite with initial direction
     * @param x the x position of the sprite
     * @param y the y position of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param type the type of the sprite as a string (enemy, bullet, player)
     * @param imagePath the path of the image sprite
     * @param speed the speed of the sprite
     * @param direction the initial direction of the sprite
     */
    public Sprite(int x, int y, int width, int height, String type, String imagePath ,double speed, Vector direction) {
        super();
        setImage(new Image(getClass().getResource("/spriteimages/" + imagePath).toExternalForm()));
        setFitWidth(width);
        setFitHeight(height);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);

        this.speed = speed;
        this.direction = direction.normalized();
        rotateBullet();
    }

    /**
     * Moves a sprite by a certain amount in the sprite's direction given how
     * much time has passed since the last frame.
     * @param elapsedTime the amount of time passed since the last frame in seconds
     */
    public void move(double elapsedTime){
        // Movement
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
    
    /**
     * Apply rotation on a bullet depending on their direction.
     */
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

    /**
     * Setter for direction, normalizes the direction after it is being set
     * @param direction the direction vector.
     */
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
