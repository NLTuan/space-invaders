/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.manager;

import edu.vanier.geometry.Vector;
import edu.vanier.spaceshooter.SpaceShooterApp;
import edu.vanier.spaceshooter.controllers.MainAppFXMLController;
import edu.vanier.spaceshooter.models.BigInvader;
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
                        Sprite invader = new SmallInvader(
                                (int)topLeft.getX() + i * spacingX,
                                (int)topLeft.getY() + j * spacingY, 30, 30, "enemy",
                                MainAppFXMLController.getSpriteMap().get("smallInvader"),
                                controller.getSmallInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                        );
                        animPane.getChildren().add(invader);
                    }
                }
            }
            case 2 -> {
                enemiesPerRow = 7;
                numOfRows = 3;
                int spacingX = (int)animPane.getWidth()/enemiesPerRow;
                int spacingY = (int)(animPane.getHeight() * 0.5 / numOfRows);
                Vector topLeft = new Vector(spacingX/2, spacingY/2);
                for(int j = 0; j < numOfRows; j++){
                    for (int i = 0; i < enemiesPerRow; i++) {
                        Sprite invader;
                        if(j % 2 == 0){
                            invader = new SmallInvader(
                                (int)topLeft.getX() + i * spacingX,
                                (int)topLeft.getY() + j * spacingY, 30, 30, "enemy",
                                MainAppFXMLController.getSpriteMap().get("smallInvader"),
                                controller.getSmallInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }
                        else{
                            invader = new MediumInvader(
                                (int)topLeft.getX() + i * spacingX,
                                (int)topLeft.getY() + j * spacingY, 30, 30, "enemy",
                                MainAppFXMLController.getSpriteMap().get("mediumInvader"),
                                controller.getMediumInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }

                        animPane.getChildren().add(invader);
                    }
                }
               
            }
            default -> {
                enemiesPerRow = 11;
                numOfRows = 3;
                int spacingX = (int)animPane.getWidth()/enemiesPerRow;
                int spacingY = (int)(animPane.getHeight() * 0.5 / numOfRows);
                Vector topLeft = new Vector(spacingX/2, spacingY/2);
                for(int j = 0; j < numOfRows; j++){
                    for (int i = 0; i < enemiesPerRow; i++) {
                        Sprite invader = new BigInvader(
                            (int)topLeft.getX() + i * spacingX,
                            (int)topLeft.getY() + j * spacingY, 30, 30, "enemy",
                            MainAppFXMLController.getSpriteMap().get("bigInvader"),
                            controller.getMediumInvaderSpeed(),
                            controller.getEnemyBulletSpeed(),
                            controller.getSpaceShip(),
                            0.01
                        );
                        animPane.getChildren().add(invader);
                    }
                }
            }

        }


//        for (int i = 0; i < enemiesPerRow; i++) {
//            Sprite invader = new MediumInvader(
//                    (int)topLeft.getX() + i * spacingX,
//                    (int)topLeft.getY() + spacingY, 50, 50, "enemy",
//                    spriteMap.get("mediumInvader"),
//                    mediumInvaderSpeed,
//                    enemyBulletSpeed
//            );
//            animationPanel.getChildren().add(invader);
//        }
//        Sprite bigInvader = new BigInvader(
//                SpaceShooterApp.screenWidth/2 - 35,
//                35, 70, 70, "enemy",
//                spriteMap.get("bigInvader"),
//                bigInvaderSpeed,
//                enemyBulletSpeed,
//                spaceShip,
//                0.011
//            );
//        animationPanel.getChildren().add(bigInvader);
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
    
}
