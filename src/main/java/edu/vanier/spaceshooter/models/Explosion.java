/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.spaceshooter.models;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author letua
 */
public class Explosion extends ImageView {
    private double timeLeft;
    double scale = 4;
    
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
    
    public Explosion(Sprite sprite, double w, double h, String path, double duration) {
        timeLeft = duration;
        
        setImage(new Image(this.getClass().getResource(path).toExternalForm()));
        setTranslateX(sprite.getTranslateX() + sprite.getFitWidth()/2 - w/2);
        setTranslateY(sprite.getTranslateY() - h/2);
        setFitWidth(w);
        setFitHeight(h);
    }

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
