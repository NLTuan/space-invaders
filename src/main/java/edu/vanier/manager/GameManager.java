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
import edu.vanier.spaceshooter.models.MediumInvader;
import edu.vanier.spaceshooter.models.SmallInvader;
import edu.vanier.spaceshooter.models.Sprite;
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
        switch (level){
            case 1 -> {
                enemiesPerRow = 5;
                numOfRows = 3;
                int spacingX = (int)animPane.getWidth()/enemiesPerRow;
                int spacingY = (int)(animPane.getHeight() * 0.5 / numOfRows);
                Vector topLeft = new Vector(spacingX/2, spacingY/2);
                for(int j = 0; j < numOfRows; j++){
                    for (int i = 0; i < enemiesPerRow; i++) {
                        Invader invader = new SmallInvader(
                                (int)topLeft.getX() + i * spacingX,
                                (int)topLeft.getY() + j * spacingY, 30, 30, "enemy",
                                "smallInvader.png",
                                controller.getSmallInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                        );
                        animPane.getChildren().add(invader);
                        animPane.getChildren().add(invader.getHpBar());
                    }
                }
            }
            case 2 -> {
                enemiesPerRow = 5;
                numOfRows = 5;
                int spacingX = (int)animPane.getWidth()/enemiesPerRow;
                int spacingY = (int)(animPane.getHeight() * 0.5 / numOfRows);
                Vector topLeft = new Vector(spacingX/2, spacingY/2);
                for(int j = 0; j < numOfRows; j++){
                    for (int i = 0; i < enemiesPerRow; i++) {
                        Invader invader;
                        if((i+j) % 2 == 0){
                            invader = new SmallInvader(
                                (int)topLeft.getX() + i * spacingX,
                                (int)topLeft.getY() + j * spacingY, 30, 30, "enemy",
                                "smallInvader.png",
                                controller.getSmallInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }
                        else{
                            invader = new MediumInvader(
                                (int)topLeft.getX() + i * spacingX,
                                (int)topLeft.getY() + j * spacingY, 30, 30, "enemy",
                                "mediumInvader.png",
                                controller.getMediumInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }

                        animPane.getChildren().add(invader);
                        animPane.getChildren().add(invader.getHpBar());
                    }
                }
               
            }
            default -> {
                enemiesPerRow = (level * 2) + 1;
                numOfRows = 3;
                int spacingX = (int)animPane.getWidth()/enemiesPerRow;
                int spacingY = (int)(animPane.getHeight() * 0.5 / numOfRows);
                Vector topLeft = new Vector(spacingX/2, spacingY/2);
                for(int j = 0; j < numOfRows; j++){
                    for (int i = 0; i < enemiesPerRow; i++) {
                        Invader invader = null;
                        if (j == 0){
                            if (i % 7 == 3){
                                invader = new BigInvader(
                                    (int)topLeft.getX() + i * spacingX,
                                    (int)topLeft.getY() + j * spacingY, 60, 60, "enemy",
                                    "bigInvader.png",
                                    controller.getBigInvaderSpeed(),
                                    controller.getEnemyBulletSpeed(),
                                    controller.getSpaceShip(),
                                    0.01
                                );
                            }
                        }
                        else if (j == 1){
                            invader = new SmallInvader(
                                (int)topLeft.getX() + i * spacingX,
                                (int)topLeft.getY() + j * spacingY, 30, 30, "enemy",
                                "smallInvader.png",
                                controller.getSmallInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }
                        else{
                            invader = new MediumInvader(
                                (int)topLeft.getX() + i * spacingX,
                                (int)topLeft.getY() + j * spacingY, 30, 30, "enemy",
                                "mediumInvader.png",
                                controller.getMediumInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }
                        if (invader != null){
                            animPane.getChildren().add(invader);
                            animPane.getChildren().add(invader.getHpBar());
                        }
                    }
                }
            }

        }
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
