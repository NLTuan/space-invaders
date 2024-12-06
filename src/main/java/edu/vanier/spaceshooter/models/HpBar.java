/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.spaceshooter.models;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author letua
 */
public class HpBar extends Pane{

    private Invader invader;
    
    private Rectangle hpFull;
    private Rectangle hpCurr;
    
    private boolean dead = false;

    public HpBar(Invader invader) {
        this.invader = invader;
        hpFull = new Rectangle(0, 0, invader.getFitWidth() * 1.5, invader.getFitHeight() * 0.2);
        hpFull.setFill(Color.RED);
        getChildren().add(hpFull);
        hpCurr = new Rectangle(0, 0, invader.getFitWidth() * 1.5, invader.getFitHeight() * 0.2);
        hpCurr.setFill(Color.GREENYELLOW);
        getChildren().add(hpCurr);
    }
    
    public void updateBar(){
        hpFull.setTranslateX(invader.getTranslateX()- invader.getFitWidth()/4);
        hpFull.setTranslateY(invader.getTranslateY()- invader.getFitHeight()/4);
        hpCurr.setTranslateX(invader.getTranslateX()- invader.getFitWidth()/4);
        hpCurr.setTranslateY(invader.getTranslateY()- invader.getFitHeight()/4);
        hpCurr.setWidth(hpFull.getWidth() * invader.getHitpoints()/invader.getHpMax());
    }

    public Rectangle getHpFull() {
        return hpFull;
    }

    public void setHpFull(Rectangle hpFull) {
        this.hpFull = hpFull;
    }

    public Rectangle getHpCurr() {
        return hpCurr;
    }

    public void setHpCurr(Rectangle hpCurr) {
        this.hpCurr = hpCurr;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
    
    
}
