package edu.vanier.spaceshooter.models;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Hp Bar for Invaders. This method is a Pane containing 
 * 2 rectangles that is used to illustrate how much health left an Invader has, 
 * is updated each time the Invader is damaged. It gets cleared out when the 
 * Invader dies.
 * @author Le Tuan Huy Nguyen
 */
public class HpBar extends Pane{

    private Invader invader;
    
    private Rectangle hpFull;
    private Rectangle hpCurr;
    
    private boolean dead = false;

    /**
     * Constructor for HpBar
     * @param invader the invader of which to keep track the hp of
     */
    public HpBar(Invader invader) {
        this.invader = invader;
        hpFull = new Rectangle(0, 0, invader.getFitWidth() * 1.5, invader.getFitHeight() * 0.2);
        hpFull.setFill(Color.RED);
        getChildren().add(hpFull);
        hpCurr = new Rectangle(0, 0, invader.getFitWidth() * 1.5, invader.getFitHeight() * 0.2);
        hpCurr.setFill(Color.GREENYELLOW);
        getChildren().add(hpCurr);
    }
    
    /**
     * Change how much the bar is filled depending on the health of the invader
     * being kept track of
     */
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
