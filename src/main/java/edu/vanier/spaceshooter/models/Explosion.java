package edu.vanier.spaceshooter.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class for regulating explosion effects. Keep track of when the explosion animation
 * ends and clears itself from the screen by the MainFXMLController 
 * removeDeadSprites() method.
 * @author 
 */
public class Explosion extends ImageView {
    private double timeLeft;
    double scale = 4;
    
    /**
     * Constructor for Explosion, used for exploding Invaders.
     * @param sprite the exploding sprite
     * @param path the path of the animation gif
     * @param duration the duration of the explosion gif
     */
    public Explosion(Sprite sprite, String path, double duration) {
        timeLeft = duration;
        double w = sprite.getFitWidth();
        double h = sprite.getFitHeight();
        
        setImage(new Image(this.getClass().getResource(path).toExternalForm()));
        setTranslateX(sprite.getTranslateX() - w * (scale - 1)/2);
        setTranslateY(sprite.getTranslateY() - h * (scale - 1)/2);
        setFitWidth(w * scale);
        setFitHeight(h * scale);
    }
    
    /**
     * Constructor for Explosion, used for exploding bullets
     * @param sprite the exploding sprite
     * @param w the width of the explosion
     * @param h the height of the explosion
     * @param path the path of the animation gif
     * @param duration the duration of the explosion gif
     */
    public Explosion(Sprite sprite, double w, double h, String path, double duration) {
        timeLeft = duration;
        
        setImage(new Image(this.getClass().getResource(path).toExternalForm()));
        setTranslateX(sprite.getTranslateX() + sprite.getFitWidth()/2 - w/2);
        setTranslateY(sprite.getTranslateY() - h/2);
        setFitWidth(w);
        setFitHeight(h);
    }

    /**
     * Quick method for reducing the life left of the animation
     * @param elapsedTime the time passed since last frame
     */
    public void decreaseTime(double elapsedTime){
        timeLeft -= elapsedTime;
    }
    
    public double getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(double timeLeft) {
        this.timeLeft = timeLeft;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
