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
        level = 1;
        this.controller = controller;
        this.animPane = animPane;
    }
    
    public void spawnInvaders(){
        int enemiesPerRow = 5;
        int numOfRows = 5;
        int spacingX = SpaceShooterApp.screenWidth/enemiesPerRow;
        int spacingY = (int)(SpaceShooterApp.screenHeight * 0.5 / numOfRows);
        Vector topLeft = new Vector(spacingX/2, spacingY/2);
        for (int i = 0; i < enemiesPerRow; i++) {
            Sprite invader = new SmallInvader(
                    (int)topLeft.getX() + i * spacingX,
                    (int)topLeft.getY(), 30, 30, "enemy",
                    MainAppFXMLController.getSpriteMap().get("smallInvader"),
                    controller.getSmallInvaderSpeed(),
                    controller.getEnemyBulletSpeed()
            );
            animPane.getChildren().add(invader);
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
}
