/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.manager;

import edu.vanier.geometry.Vector;
import edu.vanier.spaceshooter.SpaceShooterApp;
import edu.vanier.spaceshooter.controllers.MainAppFXMLController;
import edu.vanier.spaceshooter.models.BigInvader;
import edu.vanier.spaceshooter.models.Invader;
import edu.vanier.spaceshooter.models.MediumInvader1;
import edu.vanier.spaceshooter.models.MediumInvader2;
import edu.vanier.spaceshooter.models.SmallInvader1;
import edu.vanier.spaceshooter.models.SmallInvader2;
import edu.vanier.spaceshooter.models.Sprite;
import java.util.Random;
import javafx.scene.layout.Pane;

/**
 *
 * @author letua
 */
public class GameManager {
    private int level;
    
    private MainAppFXMLController controller;
    private Pane animPane;
    private int score = 0;
    
    private boolean gameOver = false;

    public GameManager(MainAppFXMLController controller, Pane animPane) {
        level = 0;
        this.controller = controller;
        this.animPane = animPane;
    }
    
    public void spawnInvaders(){
        int enemiesPerRow;
        int numOfRows;
        switch (level % 3){
            case 1 -> {
                enemiesPerRow = (level/3 + 2) + 5;
                numOfRows = 3;
                int spacingX = (int)animPane.getWidth()/enemiesPerRow;
                int spacingY = (int)(animPane.getHeight() * 0.5 / numOfRows);
                Vector topLeft = new Vector(spacingX/2, spacingY/2);
    
                for(int j = 0; j < numOfRows; j++){
                    for (int i = 0; i < enemiesPerRow; i++) {
                        // This is only to follow the requirements, I personally don't like random spawning positions
                        Vector randomOffset = new Vector(
                            (Math.random() * 0.5 - 0.25) * spacingX, 
                            (Math.random() * 0.5 - 0.25) * spacingY
                        );
                        Invader invader = getRandomSmallInvader(
                                (int)Math.round(Math.random()), 
                                (int)(topLeft.getX() + i * spacingX + randomOffset.getX()),
                                (int)(topLeft.getY() + j * spacingY + randomOffset.getY()),
                                30, 30,
                                controller.getSmallInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                        );
                        
                        if (level > 3){
                            invader.speedMultiplier((level - 3) * 0.2);
                            invader.firingCooldownMult((level - 3) * 0.2);
                        }
                        animPane.getChildren().add(invader);
                        animPane.getChildren().add(invader.getHpBar());
                    }
                }
            }
            case 2 -> {
                enemiesPerRow = (level/3 + 2) + 3;
                numOfRows = 5;
                int spacingX = (int)animPane.getWidth()/enemiesPerRow;
                int spacingY = (int)(animPane.getHeight() * 0.5 / numOfRows);
                Vector topLeft = new Vector(spacingX/2, spacingY/2);
                for(int j = 0; j < numOfRows; j++){
                    for (int i = 0; i < enemiesPerRow; i++) {
                        Invader invader;
                        
                        Vector randomOffset = new Vector(
                            (Math.random() * 0.5 - 0.25) * spacingX, 
                            (Math.random() * 0.5 - 0.25) * spacingY
                        );
                        if((i+j) % 2 == 0){
                            invader = getRandomSmallInvader(
                                (int)Math.round(Math.random()), 
                                (int)(topLeft.getX() + i * spacingX + randomOffset.getX()),
                                (int)(topLeft.getY() + j * spacingY + randomOffset.getY()),
                                30, 30,
                                controller.getSmallInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }
                        else{
                            invader = getRandomMediumInvader(
                                (int)Math.round(Math.random()), 
                                (int)(topLeft.getX() + i * spacingX + randomOffset.getX()),
                                (int)(topLeft.getY() + j * spacingY + randomOffset.getY()),
                                30, 30,
                                controller.getMediumInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }

                        if (level > 3){
                            invader.speedMultiplier((level - 3) * 0.2);
                            invader.firingCooldownMult((level - 3) * 0.2);
                        }
                        animPane.getChildren().add(invader);
                        animPane.getChildren().add(invader.getHpBar());
                    }
                }
               
            }
            case 0 -> {
                enemiesPerRow = ((level/3) * 2) + 5;
                numOfRows = 3;
                int spacingX = (int)(animPane.getWidth() /enemiesPerRow);
                int spacingY = (int)(animPane.getHeight() * 0.5 / numOfRows);
                Vector topLeft = new Vector(spacingX/2, spacingY/2);
                for(int j = 0; j < numOfRows; j++){
                    for (int i = 0; i < enemiesPerRow; i++) {
                        Invader invader = null;
                        if (j == 0){
                            if (i == (enemiesPerRow / 2)){
                                invader = new BigInvader(
                                    (int)topLeft.getX() + i * spacingX,
                                    (int)topLeft.getY() + j * spacingY, 60, 60,
                                    "bigInvader.png",
                                    controller.getBigInvaderSpeed(),
                                    controller.getEnemyBulletSpeed(),
                                    controller.getSpaceShip(),
                                    0.01
                                );
                            }
                        }
                        else if (j == 1){
                            invader = getRandomSmallInvader(
                                (int)Math.round(Math.random()), 
                                (int)topLeft.getX() + i * spacingX,
                                (int)topLeft.getY() + j * spacingY, 30, 30,
                                controller.getSmallInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }
                        else{
                            invader = getRandomMediumInvader(
                                (int)Math.round(Math.random()), 
                                (int)topLeft.getX() + i * spacingX,
                                (int)topLeft.getY() + j * spacingY, 30, 30,
                                controller.getSmallInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }
                        if (invader != null){
                            if (level > 3){
                                invader.speedMultiplier((level - 3) * 0.2);
                                invader.firingCooldownMult((level - 3) * 0.2);
                            }
                            
                            animPane.getChildren().add(invader);
                            animPane.getChildren().add(invader.getHpBar());
                        }
                    }
                }
            }



        }
    }
    
    public Invader getRandomSmallInvader(int nSmall, int x, int y, int width, int height, double speed, double bulletSpeed){
        return switch (nSmall) {
            case 0 -> new SmallInvader1(x, y, width, height, "smallInvader1.png", speed, bulletSpeed);
            case 1 -> new SmallInvader2(x, y, width, height, "smallInvader2.png", speed, bulletSpeed);
            default -> null;
        };
    }
    
    public Invader getRandomMediumInvader(int nSmall, int x, int y, int width, int height, double speed, double bulletSpeed){
        return switch (nSmall) {
            case 0 -> new MediumInvader1(x, y, width, height, "mediumInvader1.png", speed, bulletSpeed);
            case 1 -> new MediumInvader2(x, y, width, height, "mediumInvader2.png", speed, bulletSpeed);
            default -> null;
        };
    }
    
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public void levelUp(){
        level++;
    }    

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void increaseScore(int score){
        this.score += score;
        if (this.score < 0){
            this.score = 0;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    public void reset(){
        gameOver = false;
        level = 0;
        score = 0;
    }
}
